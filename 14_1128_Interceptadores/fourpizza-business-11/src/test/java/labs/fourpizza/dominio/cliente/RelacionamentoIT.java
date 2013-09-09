package labs.fourpizza.dominio.cliente;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.embeddable.EJBContainer;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;

import labs.fourpizza.dominio.gerenciador.AcoesClientes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@Stateless
@LocalBean
//@RunAs("usuarios")
public class RelacionamentoIT {
	private EJBContainer container;
	private Context context;
	
	private AcoesClientes acoesClientes;

	@Resource
	private ConnectionFactory connectionFactory;

	@Resource(name = "FilaNotificacaoPainel")
	private Topic filaNotificaoPanel;

	@Before
	public void iniciar() throws Exception {
		Properties props = new Properties();

		// Configurando uma fila para o setor de entregas no OpenEJB
		props.put("FilaRelacionamento", "new://Resource?type=javax.jms.Queue");

		// Configurando um topico para a notificacao de panel no
		// OpenEJB
		props.put("FilaNotificacaoPainel", "new://Resource?type="
				+ "javax.jms.Topic");

		// Configuando um banco de dados em memória
		props.put("fourpizzaDatabase", "new://Resource?type=DataSource");
		props.put("fourpizzaDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
		props.put("fourpizzaDatabase.JdbcUrl",	"jdbc:hsqldb:mem:fourpizzadb");

		container = EJBContainer.createEJBContainer(props);
		context = container.getContext();
		acoesClientes = (AcoesClientes) context.lookup("java:global/fourpizza-business/AcoesClientes");
	}

	@Test
	public void processandoRelacionamento() throws Exception {
		Clientes cliente = new Clientes(10, "Vinicius Vale" , new Endereco());
		cliente.setUsuario(new Usuarios(cliente.getId(), "aquino.vale@gmail.com", "123456"));
		acoesClientes.cadastrarClientes(cliente);

		RelacionamentoIT it = (RelacionamentoIT) context.lookup("java:global/fourpizza-business/RelacionamentoIT");
		it.verificarRelacionamento(cliente);
	}

	public void verificarRelacionamento(Clientes cliente) throws Exception {
		Connection conn = null;
		Session session = null;

		conn = connectionFactory.createConnection();
		// Para conexão que irão consumer mensagens, deve-se
		// iniciar a conexão.
		conn.start();

		session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

		MessageConsumer consumer = session
				.createConsumer(filaNotificaoPanel);

		// Espera um mensagem por 6 segundos antes de retornar
		// nulo.
		TextMessage message = (TextMessage) consumer.receive(6000);

		assertEquals(cliente.getNome(), message.getText());
	}

	@After
	public void encerrar() {
		container.close();
	}
}
