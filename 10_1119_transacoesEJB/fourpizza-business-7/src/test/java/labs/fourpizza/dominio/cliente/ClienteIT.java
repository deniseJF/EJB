package labs.fourpizza.dominio.cliente;

import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import labs.fourpizza.dominio.cliente.chaves.TelefonePK;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@Stateless
@LocalBean
public class ClienteIT {
	private EJBContainer ejbContainer;
	private Context context;
	private ClienteIT test;

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
		test = (ClienteIT) context.lookup("java:global/fourpizza-business/ClienteIT");
	}
	
	@Test
	public void clientesIguais() throws Exception {
		Clientes cliente1 = new Clientes(10, "Vinicius Vale", new Endereco());
		Clientes cliente2 = new Clientes(10, "Vinicius Vale", new Endereco());
		Assert.assertEquals(true, cliente1.equals(cliente2));
	}

	@Test
	public void adicionarNovoCliente() throws Exception {
		test.adicionarNovoClienteTeste();
		test.acharNovoClienteTeste();
	}
	
	@Test
	public void adicionarTelefoneCliente() throws Exception {
		test.adicionarNovoClienteTeste();
		test.adicionarTelefoneClienteTeste();
		test.contaTotalTelefone();
	}
	
	@Test
	public void consultaCliente() throws Exception {
		test.adicionarNovoClienteTeste();
		test.adicionarTelefoneClienteTeste();
		test.consultaClienteTeste();
	}
	
	
	public void consultaClienteTeste(){
		Query qry = montaQuery("Clientes.buscaPorNome", "Vinicius Vale");
		Clientes c = (Clientes)qry.getSingleResult();
		Assert.assertNotNull(c);
		qry = montaQuery("Clientes.buscaTelefones", "Vinicius Vale");
		Assert.assertEquals(3, c.getTelefones().size());
	}
	
	public Query montaQuery(String query, String nome){
		Query qry = entityManager.createNamedQuery(query);
		qry.setParameter("nome", nome);
		return qry;
	}
	
	public void contaTotalTelefone(){
		Clientes cliente1 = entityManager.find(Clientes.class, 10);
		Assert.assertEquals(3, cliente1.getTelefones().size());
		for (Telefone tel : cliente1.getTelefones()) {
			if(tel.getId().getTipo() == TipoTelefone.RESIDENCIAL){
				entityManager.remove(tel);
			}
		}
		entityManager.flush();
		entityManager.refresh(cliente1);
		Assert.assertEquals(2, cliente1.getTelefones().size());
	}
	
	public void adicionarTelefoneClienteTeste() throws Exception {
		Clientes cliente1 = entityManager.find(Clientes.class, 10);
		cliente1.getTelefones().add(new Telefone(new TelefonePK(cliente1.getId(), TipoTelefone.RESIDENCIAL)));
		cliente1.getTelefones().add(new Telefone(new TelefonePK(cliente1.getId(), TipoTelefone.COMERCIAL)));
		cliente1.getTelefones().add(new Telefone(new TelefonePK(cliente1.getId(), TipoTelefone.CELULAR)));
		entityManager.merge(cliente1);
	}
	
	public void adicionarNovoClienteTeste() throws Exception {
		Clientes cliente1 = new Clientes(10, "Vinicius Vale", new Endereco());
		Usuarios user = new Usuarios(cliente1.getId(), "aquino.vale@gmail.com", "123");
		user.setCliente(cliente1);
		cliente1.setUsuario(user);
		entityManager.persist(cliente1);
	}
	
	public void acharNovoClienteTeste() throws Exception {
		Clientes cliente1 = entityManager.find(Clientes.class, 10);
		Assert.assertEquals("Vinicius Vale", cliente1.getNome());
		Assert.assertEquals("aquino.vale@gmail.com", cliente1.getUsuario().getEmail());
	}

	@After
	public void finalizar() throws Exception {
		ejbContainer.close();
	}
}
