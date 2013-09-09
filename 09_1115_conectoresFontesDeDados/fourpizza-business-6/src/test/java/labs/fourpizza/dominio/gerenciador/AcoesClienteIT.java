package labs.fourpizza.dominio.gerenciador;

import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;
import labs.fourpizza.dominio.cliente.Clientes;
import labs.fourpizza.dominio.cliente.Endereco;
import labs.fourpizza.dominio.cliente.Telefone;
import labs.fourpizza.dominio.cliente.TipoTelefone;
import labs.fourpizza.dominio.cliente.chaves.TelefonePK;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@Stateless
@LocalBean
public class AcoesClienteIT {
	private EJBContainer ejbContainer;
	private Context context;
	private AcoesClientes ac;
	private AcoesClienteIT test;

	@PersistenceContext(unitName = "fourpizza-persistenceunit")
	public EntityManager entityManager;

	@Before
	public void inicializar() throws Exception {
		Properties properties = new Properties();
		properties.put("fourpizzaDatabase", "new://Resource?type=DataSource");
		properties.put("fourpizzaDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
		properties.put("fourpizzaDatabase.JdbcUrl",
				"jdbc:hsqldb:mem:fourpizzadb");

		ejbContainer = EJBContainer.createEJBContainer(properties);
		context = ejbContainer.getContext();
		test = (AcoesClienteIT) context.lookup("java:global/fourpizza-business/AcoesClienteIT");
		test.inicializarTeste();
	}
	
	@Test
	public void adicionarNovoCliente() throws Exception {
		test.addClienteTeste(new Clientes(10, "Vinicius Vale", new Endereco()));
		test.excluirClienteTeste();
		test.addClienteTeste(new Clientes(10, "Vinicius Vale", new Endereco()));
		test.atualizarClienteTeste();
	}
	
	@Test
	public void pesquisarCliente() throws Exception {
		Clientes c = new Clientes(110, "Vinicius Aquino", new Endereco());
		test.addClienteTeste(new Clientes(100, "Vinicius Vale", new Endereco()));
		test.addClienteTeste(c);
		test.adicionarTelefoneClienteTeste(110);
		test.pesquisarClienteTeste();
	}
	
	public void adicionarTelefoneClienteTeste(int id) throws Exception {
		Clientes cliente1 = entityManager.find(Clientes.class, id);
		cliente1.getTelefones().add(new Telefone(new TelefonePK(cliente1.getId(), TipoTelefone.RESIDENCIAL)));
		cliente1.getTelefones().add(new Telefone(new TelefonePK(cliente1.getId(), TipoTelefone.COMERCIAL)));
		cliente1.getTelefones().add(new Telefone(new TelefonePK(cliente1.getId(), TipoTelefone.CELULAR)));
		entityManager.merge(cliente1);
	}
	
	
	public void pesquisarClienteTeste(){
		Clientes c2 = getAc().pesquisarPorNomeClientes("Vinicius Vale");
		Assert.assertNotNull(c2);
		
		Clientes c3 = getAc().pesquisarPorTelefone("Vinicius Aquino");
		Assert.assertNotNull(c3);
	}
	
	public void inicializarTeste(){
		setAc(new AcoesClientes());
		getAc().setEntityManager(entityManager);
	}
	
	public void excluirClienteTeste(){
		Clientes c = getAc().pesquisarClientes(10);
		getAc().excluirClientes(c);
		Clientes c2 = getAc().pesquisarClientes(10);
		Assert.assertNull(c2);
	}
	
	public void addClienteTeste(Clientes c){
		getAc().cadastrarClientes(c);
		Clientes c2 = getAc().pesquisarClientes(c.getId());
		Assert.assertNotNull(c2);
	}
	
	public void atualizarClienteTeste(){
		Clientes c = getAc().pesquisarClientes(10);
		c.setNome("Teste");
		getAc().atualizarClientes(c);
		Clientes c2 = getAc().pesquisarClientes(10);
		Assert.assertEquals("Teste", c2.getNome());
	}
	

	@After
	public void finalizar() throws Exception {
		ejbContainer.close();
	}

	public AcoesClientes getAc() {
		return ac;
	}

	public void setAc(AcoesClientes ac) {
		this.ac = ac;
	}
}
