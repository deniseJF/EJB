package labs.fourpizza.dominio.mensagens;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import labs.fourpizza.dominio.cliente.Clientes;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "FilaRelacionamento") })
public class Relacionamento implements MessageListener {
	
	private static final Logger log = Logger.getLogger(Relacionamento.class
			.getCanonicalName());

	@PersistenceContext(unitName = "fourpizza-persistenceunit")
	private EntityManager entityManager;

	@Resource
	private ConnectionFactory connectionFactory;

	@Resource(name = "FilaNotificacaoPainel")
	private Topic filaNotificacaoPainel;

	@Override
	public void onMessage(Message message) {
		ObjectMessage omsg = (ObjectMessage) message;
		Clientes c = null;
		try {
			c = (Clientes) omsg.getObject();
			avisarDisponibilidaDeCurso(c);
		} catch (JMSException e) {
			log.warning("Erro ao tentar ler #" + c.getNome());
		}

	}

	private void avisarDisponibilidaDeCurso(Clientes cliente) throws JMSException {
		// Altera o estado da ordem e persiste mudança
		cliente.getEndereco().setUf("SP");
		entityManager.merge(cliente);
		notificarEntrega(cliente);
	}

	private void notificarEntrega(Clientes cliente) throws JMSException {
		Connection conn = null;
		Session session = null;

		try {
			// Cria conexao a partir da fabrica de conexões
			conn = connectionFactory.createConnection();

			// Cria uma sessão indicando que não será transacional
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Cria um produtor para a enviar a mensagem para o tópico
			MessageProducer producer = session
					.createProducer(filaNotificacaoPainel);

			// Cria mensagem de texto para ser enviada
			TextMessage message = session.createTextMessage();
			message.setText(cliente.getNome());

			// Envia mensagem com o produtor
			producer.send(message);
		} finally {
			conn.close();
			session.close();
		}

	}
}
