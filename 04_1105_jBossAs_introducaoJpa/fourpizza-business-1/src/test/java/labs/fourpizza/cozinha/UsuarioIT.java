package labs.fourpizza.cozinha;

import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import labs.fourpizza.dominio.cliente.Usuarios;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@Stateless
@LocalBean
public class UsuarioIT {
	private EJBContainer ejbContainer;
	private Context context;
	private UsuarioIT test;

	@PersistenceContext(unitName = "fourpizza-persistenceunit")
	private EntityManager entityManager;

	@Before
	public void inicializar() throws Exception {
		Properties properties = new Properties();
		properties.put("fourpizzaDatabase", "new://Resource?type=DataSource");
		properties.put("fourpizzaDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
		properties.put("fourpizzaDatabase.JdbcUrl",
				"jdbc:hsqldb:mem:fourpizzadb");

		ejbContainer = EJBContainer.createEJBContainer(properties);
		context = ejbContainer.getContext();
		test = (UsuarioIT) context.lookup("java:global/fourpizza-business/UsuarioIT");
	}
	
	@Test
	public void clientesIguais() throws Exception {
		Usuarios user1 = new Usuarios(10, "aquino.vale@gmail.com", "123456");
		Usuarios user2 = new Usuarios(10, "aquino.vale@gmail.com", "123456");
		Assert.assertEquals(true, user1.equals(user2));
	}	
	
	@Test
	public void adicionarNovoCliente() throws Exception {
		test.adicionarUsuario();
		test.achaUsuario();
	}
	
	public void adicionarUsuario() throws Exception {
		Usuarios user1 = new Usuarios(10, "aquino.vale@gmail.com", "123456");
		entityManager.persist(user1);
	}
	
	public void achaUsuario() throws Exception {
		Usuarios user1 = entityManager.find(Usuarios.class, 10);
		Assert.assertEquals("aquino.vale@gmail.com", user1.getEmail());
		
	}

	@After
	public void finalizar() throws Exception {
		ejbContainer.close();
	}
}