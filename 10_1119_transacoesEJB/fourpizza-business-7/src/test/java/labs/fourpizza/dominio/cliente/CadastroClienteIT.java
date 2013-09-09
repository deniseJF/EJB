package labs.fourpizza.dominio.cliente;

import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import labs.fourpizza.dominio.cadastros.CadastrarCliente;
import labs.fourpizza.dominio.gerenciador.ContadorClientes;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CadastroClienteIT {

	
	
	private CadastrarCliente cadastroCliente1;
	private CadastrarCliente cadastroCliente2;
	
	private EJBContainer ejbContainer;
	private Context context;
	
	@Before
	public void inicializar() throws Exception {
		Properties properties = new Properties();
		properties.put("fourpizzaDatabase", "new://Resource?type=DataSource");
		properties.put("fourpizzaDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
		properties.put("fourpizzaDatabase.JdbcUrl",
				"jdbc:hsqldb:mem:fourpizzadb");
		ejbContainer = EJBContainer.createEJBContainer(properties);
		context = ejbContainer.getContext();
		cadastroCliente1 = (CadastrarCliente) context.lookup("java:global/fourpizza-business/CadastrarCliente");
		cadastroCliente2 = (CadastrarCliente) context.lookup("java:global/fourpizza-business/CadastrarCliente");

	}
	
	public void registrandoClienteSucesso(String user, String senha, Clientes cliente, CadastrarCliente cadastro) throws Exception {
		cadastro.setUser(user, senha);
		cadastro.cadastrar(cliente);
	}
	
	@Test
    public void totalClientes() throws Exception {
		try{
			registrandoClienteSucesso("vinicius.aquino@4linux.com.br", "123", preparaCliente(2, new Clientes("Vinicius 4Linux", new Endereco())), cadastroCliente1);
			registrandoClienteSucesso("vinicius.vale@4linux.com.br", "123", preparaCliente(1, new Clientes("Vinicius 4Linux2", new Endereco())), cadastroCliente2);
		}catch(Exception e){
			Assert.assertNull(null);
		}
		totalClientesSingleton(2);
	}
	
	public void totalClientesSingleton(int total)throws Exception {
		ContadorClientes count = (ContadorClientes) context.lookup("java:global/fourpizza-business/ContadorClientes");
		Assert.assertEquals(true, (count.getContador() == total));
	}
	
	public Clientes preparaCliente(int id, Clientes cliente){
		cliente.setId(id);
		return cliente;
	}

	@After
	public void finalizar() throws Exception {
		ejbContainer.close();
	}
}
