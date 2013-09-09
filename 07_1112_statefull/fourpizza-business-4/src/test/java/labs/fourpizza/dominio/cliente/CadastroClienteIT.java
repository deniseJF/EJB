package labs.fourpizza.dominio.cliente;

import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import labs.fourpizza.dominio.cadastros.CadastroClienteLocal;
import labs.fourpizza.dominio.gerenciador.GerenciadorClientes;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CadastroClienteIT {
	private EJBContainer ejbContainer;
	private Context context;
	private CadastroClienteLocal cadastroCliente;
	private GerenciadorClientes clientes;
	
	@Before
	public void inicializar() throws Exception {
		Properties properties = new Properties();
		properties.put("fourpizzaDatabase", "new://Resource?type=DataSource");
		properties.put("fourpizzaDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
		properties.put("fourpizzaDatabase.JdbcUrl",
				"jdbc:hsqldb:mem:fourpizzadb");
		
		ejbContainer = EJBContainer.createEJBContainer(properties);
		context = ejbContainer.getContext();
		cadastroCliente = (CadastroClienteLocal) context.lookup("java:global/fourpizza-business/CadastrarCliente!labs.fourpizza.dominio.cadastros.CadastroClienteLocal");
		clientes = (GerenciadorClientes) context.lookup("java:global/fourpizza-business/AcoesClientes!labs.fourpizza.dominio.gerenciador.GerenciadorClientes");
	}
	
	@Test
	public void registrandoClienteSucesso() throws Exception {
		cadastroCliente.setUser("vinicius.aquino@4linux.com.br", "123");
		cadastroCliente.cadastrarClientes(new Clientes("Vinicius 4Linux",  new Endereco()));
		Clientes persistido = clientes.pesquisarPorNomeClientes("Vinicius 4Linux");
		assertNotNull(persistido);
		Assert.assertEquals("vinicius.aquino@4linux.com.br", persistido.getUsuario().getEmail());
	}

	@After
	public void finalizar() throws Exception {
		ejbContainer.close();
	}
}
