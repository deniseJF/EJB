package labs.fourpizza.dominio.controlador;

import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRequiredException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.UserTransaction;

import junit.framework.Assert;
import labs.fourpizza.dominio.cliente.Clientes;
import labs.fourpizza.dominio.cliente.Endereco;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class TransacoesIT {
	private EJBContainer ejbContainer;
	private Context context;
	private TransacoesIT test;
	@EJB
	private ControladorSistema controladora;
	
	@Resource
	private UserTransaction tx;

	@Before
	public void inicializar() throws Exception {
		Properties properties = new Properties();
		properties.put("fourpizzaDatabase", "new://Resource?type=DataSource");
		properties.put("fourpizzaDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
		properties.put("fourpizzaDatabase.JdbcUrl",
				"jdbc:hsqldb:mem:fourpizzadb");

		ejbContainer = EJBContainer.createEJBContainer(properties);
		context = ejbContainer.getContext();
		InitialContext ic = new InitialContext();
		tx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
//		controladora = (ControladorSistema) context.lookup("java:global/fourpizza-business/ControladorSistema");
		test = (TransacoesIT) context.lookup("java:global/fourpizza-business/TransacoesIT");
	}
	
	@Test
	public void testaTransacao() throws Exception {
		test.chamaTransacaoInclusao();
	}
	
	@Test(expected=EJBException.class)
	public void testaTransacao2() throws Exception {
		test.chamaTransacaoAtualizacao();
	}
	
	@Test
	public void testaTransacao3() throws Exception {
		test.chamaTransacaoExclusao();
	}
	
	@Test(expected=EJBException.class)
	public void testaTransacao4() throws Exception{
		test.chamaTransacaoPesquisa();
	}

	
	public void chamaTransacaoPesquisa(){
		try{
			controladora.cadastrarClientes(new Clientes(10, "Vinicius Vale", new Endereco()));
			controladora.pesquisarPorNome("Vinicius Vale");
		}catch(EJBTransactionRequiredException e){
			throw new EJBException(); 
		}
	}
	
	public void chamaTransacaoInclusao() {
		try{
			tx.begin();
			controladora.cadastrarClientes(new Clientes(10, "Vinicius Vale", new Endereco()));
			Clientes c1 = controladora.pesquisarClientes(10);
			Assert.assertNotNull(c1);
			tx.commit();
		}catch(Exception e){
			Assert.assertNotNull(null);
		}
	}
	
	public void chamaTransacaoAtualizacao() {
		try{
			tx.begin();
			controladora.cadastrarClientes(new Clientes(10, "Vinicius Vale", new Endereco()));
			Clientes c1 = controladora.pesquisarClientes(10);
			Assert.assertNotNull(c1);
			c1.setNome("Teste");
			controladora.atualizarClientes(c1);
			tx.commit();
		}catch(EJBException e){
			throw new EJBException();
		}catch(Exception e){
			System.out.println();System.out.println();System.out.println();System.out.println();
			System.out.println("DISPAROU OUTRA EXCECAO");
			System.out.println();
			e.printStackTrace();
			Assert.assertNotNull(null);
		}
	}
	
	public void chamaTransacaoExclusao() {
		try{
			tx.begin();
			controladora.cadastrarClientes(new Clientes(10, "Vinicius Vale", new Endereco()));
			Clientes c1 = controladora.pesquisarClientes(10);
			Assert.assertNotNull(c1);
			controladora.excluirClientes(c1);
			Clientes c2 = controladora.pesquisarClientes(10);
			Assert.assertNull(c2);
			tx.commit();
		}catch(Exception e){
			Assert.assertNotNull(null);
		}
	}

	@After
	public void finalizar() throws Exception {
		ejbContainer.close();
	}
}

