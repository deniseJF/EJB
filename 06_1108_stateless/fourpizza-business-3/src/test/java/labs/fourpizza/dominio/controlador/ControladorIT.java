package labs.fourpizza.dominio.controlador;

import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import junit.framework.Assert;
import labs.fourpizza.dominio.cliente.Clientes;
import labs.fourpizza.dominio.cliente.Endereco;
import labs.fourpizza.dominio.cliente.Telefone;
import labs.fourpizza.dominio.cliente.TipoTelefone;
import labs.fourpizza.dominio.cliente.chaves.TelefonePK;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ControladorIT {
	private EJBContainer ejbContainer;
	private Context context;
	private ControlarSistema controladora;

	@Before
	public void inicializar() throws Exception {
		Properties properties = new Properties();
		properties.put("fourpizzaDatabase", "new://Resource?type=DataSource");
		properties.put("fourpizzaDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
		properties.put("fourpizzaDatabase.JdbcUrl",
				"jdbc:hsqldb:mem:fourpizzadb");

		ejbContainer = EJBContainer.createEJBContainer(properties);
		context = ejbContainer.getContext();
		controladora = (ControlarSistema) context.lookup("java:global/fourpizza-business/ControladorSistema!labs.fourpizza.dominio.controlador.ControlarSistema");
	}
	
	@Test
	public void adicionarNovoCliente() throws Exception {
		addClienteTeste(new Clientes(10, "Vinicius Vale", new Endereco()));
		excluirClienteTeste();
		addClienteTeste(new Clientes(10, "Vinicius Vale", new Endereco()));
		atualizarClienteTeste();
	}
	
	@Test
	public void pesquisarCliente() throws Exception {
		Clientes c = new Clientes(110, "Vinicius Aquino", new Endereco());
		addClienteTeste(new Clientes(100, "Vinicius Vale", new Endereco()));
		addClienteTeste(c);
//		adicionarTelefoneClienteTeste(110);
		pesquisarClienteTeste();
	}
	
	public void adicionarTelefoneClienteTeste(int id) throws Exception {
		Clientes cliente1 = controladora.pesquisarClientes(id);
		cliente1.getTelefones().add(new Telefone(new TelefonePK(cliente1.getId(), TipoTelefone.RESIDENCIAL)));
		cliente1.getTelefones().add(new Telefone(new TelefonePK(cliente1.getId(), TipoTelefone.COMERCIAL)));
		cliente1.getTelefones().add(new Telefone(new TelefonePK(cliente1.getId(), TipoTelefone.CELULAR)));
		controladora.atualizarClientes(cliente1);
	}
	
	
	public void pesquisarClienteTeste(){
		Clientes c2 = controladora.pesquisarPorNome("Vinicius Vale");
		Assert.assertNotNull(c2);
		
//		Clientes c3 = controladora.pesquisarPorTelefone("Vinicius Aquino");
//		Assert.assertNotNull(c3);
	}
	
	public void excluirClienteTeste(){
		Clientes c = controladora.pesquisarClientes(10);
		controladora.excluirClientes(c);
		Clientes c2 = controladora.pesquisarClientes(10);
		Assert.assertNull(c2);
	}
	
	public void addClienteTeste(Clientes c){
		controladora.cadastrarClientes(c);
		Clientes c2 = controladora.pesquisarClientes(c.getId());
		Assert.assertNotNull(c2);
	}
	
	public void atualizarClienteTeste(){
		Clientes c = controladora.pesquisarClientes(10);
		c.setNome("Teste");
		controladora.atualizarClientes(c);
		Clientes c2 = controladora.pesquisarClientes(10);
		Assert.assertEquals("Teste", c2.getNome());
	}
	

	@After
	public void finalizar() throws Exception {
		ejbContainer.close();
	}
}