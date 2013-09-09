package labs.fourpizza.dominio.gerenciador;

import java.util.Calendar;
import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import junit.framework.Assert;
import labs.fourpizza.dominio.cliente.Clientes;
import labs.fourpizza.dominio.cliente.Endereco;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@Stateless
@LocalBean
//@RunAs("usuarios")
public class AcoesClienteIT {
	private EJBContainer ejbContainer;
	private Context context;
	private AcoesClientes ac;

	@Before
	public void inicializar() throws Exception {
		Properties properties = new Properties();
		properties.put("fourpizzaDatabase", "new://Resource?type=DataSource");
		properties.put("fourpizzaDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
		properties.put("fourpizzaDatabase.JdbcUrl",
				"jdbc:hsqldb:mem:fourpizzadb");

		ejbContainer = EJBContainer.createEJBContainer(properties);
		context = ejbContainer.getContext();
		ac = (AcoesClientes) context.lookup("java:global/fourpizza-business/AcoesClientes");
	}
	
	@Test
	public void adicionarNovoCliente() throws Exception {
		addClienteTeste(new Clientes(10, "Vinicius Vale", new Endereco()));
		excluirClienteTeste();
		addClienteTeste(new Clientes(10, "Vinicius Vale", new Endereco()));
		atualizarClienteTeste();
	}
	
	public void pesquisarClienteTeste(){
		Clientes c2 = getAc().pesquisarPorNomeClientes("Vinicius Vale");
		Assert.assertNotNull(c2);
		
		Clientes c3 = getAc().pesquisarPorTelefone("Vinicius Aquino");
		Assert.assertNotNull(c3);
	}
	
	public void excluirClienteTeste(){
		Clientes c = getAc().pesquisarClientes(10);
		getAc().excluirClientes(c);
		Clientes c2 = getAc().pesquisarClientes(10);
		Assert.assertNull(c2);
	}
	
	public void addClienteTeste(Clientes c){
		c.setDtNasc(Calendar.getInstance().getTime());
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
