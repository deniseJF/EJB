package labs.fourpizza.cozinha;

import java.util.List;
import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import labs.fourpizza.dominio.cliente.Clientes;
import labs.fourpizza.dominio.cliente.Endereco;
import labs.fourpizza.dominio.cliente.Filhos;
import labs.fourpizza.dominio.cliente.Pais;

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
		test.testeJoin();
	}
	
	public void adicionarNovoClienteTeste() throws Exception {
		Clientes cliente1 = new Clientes(10, "Vinicius Vale", new Endereco());
		entityManager.persist(cliente1);
	}
	
	public void acharNovoClienteTeste() throws Exception {
		Clientes cliente1 = entityManager.find(Clientes.class, 10);
		Assert.assertEquals("Vinicius Vale", cliente1.getNome());
	}
	
	public void testeJoin() throws Exception {
		Pais p = new Pais(1, "papai");
		entityManager.persist(p);
		entityManager.flush();
		
		Filhos f1 = new Filhos(1, "f1", p);
		entityManager.persist(f1);
		entityManager.flush();
		
		Filhos f2 = new Filhos(2, "f2", p);		
		entityManager.persist(f2);
		entityManager.flush();
		
		entityManager.refresh(p);
		
//		System.out.println("VAI IMPRIMIR");
//		System.out.println();
//		System.out.println();
//		System.out.println();
//		entityManager.refresh(p);
//		for(Filhos f:p.getFilho())
//			System.out.println(f.getName());
		
		System.out.println();
		System.out.println();
		
		Query query = entityManager.createQuery("select d from Pais d join fetch d.filho");
		List<Pais> dep = query.getResultList();
		for (Pais p1 : dep) {
			System.out.println("Nome pai: " + p1.getName());
			
			for (Filhos f : p1.getFilho()) {
				System.out.println("Filho: " + f.getName());
			}

		}

		
	}

	@After
	public void finalizar() throws Exception {
		ejbContainer.close();
	}
}