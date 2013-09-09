package labs.fourpizza.dominio.cadastros;

import javax.annotation.Resource;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.IllegalStateException;
import javax.transaction.UserTransaction;

import labs.fourpizza.dominio.cliente.Clientes;
import labs.fourpizza.dominio.cliente.Usuarios;
import labs.fourpizza.dominio.gerenciador.AcoesClientes;
import labs.fourpizza.dominio.gerenciador.ContadorClientes;

@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
@RunAs("usuarios")
public class CadastrarCliente{// implements CadastroClienteLocal{
	
	
	private Usuarios user;
	
	@Resource
	private UserTransaction tx;
	
	@EJB
	private AcoesClientes acoes;
	
	@EJB
	private ContadorClientes count;
	
	
	
	@Remove
	@PermitAll
	public void cadastrarSemSeguranca(Clientes cliente) throws IllegalStateException {
		this.cadastrar(cliente);
	}

	@Remove
	@DenyAll
	public void cadastrar(Clientes cliente) throws IllegalStateException {
		if(user.getEmail() == null || user.getEmail().equals("")){
			throw new IllegalStateException("Email não colocado") ;
		}
		cliente.setUsuario(user);
		user.setCliente(cliente);
		try{
			tx.begin();
			acoes.cadastrarClientes(cliente);
			//Add Singleton
			count.incrementa();
			tx.commit();
		}catch(Exception e){
			try {
				tx.rollback();
			} catch (Exception e1) {
			}
		}
	}

	public void setUser(String email, String senha) {
		user = new Usuarios();
		user.setEmail(email);
		user.setSenha(senha);
	}

}
