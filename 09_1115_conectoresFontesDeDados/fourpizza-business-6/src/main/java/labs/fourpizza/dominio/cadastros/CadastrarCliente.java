package labs.fourpizza.dominio.cadastros;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.jms.IllegalStateException;

import labs.fourpizza.dominio.cliente.Clientes;
import labs.fourpizza.dominio.cliente.Usuarios;
import labs.fourpizza.dominio.gerenciador.AcoesClientes;
import labs.fourpizza.dominio.gerenciador.ContadorClientes;

@Stateful
@LocalBean
public class CadastrarCliente{// implements CadastroClienteLocal{
	
	
	private Usuarios user;
	
	@EJB
	private AcoesClientes acoes;
	
	@EJB
	private ContadorClientes count;
	

	@Remove
	public void cadastrar(Clientes cliente) throws IllegalStateException {
		if(user.getEmail() == null || user.getEmail().equals("")){
			throw new IllegalStateException("Email não colocado") ;
		}
		cliente.setUsuario(user);
		user.setCliente(cliente);
		acoes.cadastrarClientes(cliente);
		//Add Singleton
		count.incrementa();
	}

	public void setUser(String email, String senha) {
		user = new Usuarios();
		user.setEmail(email);
		user.setSenha(senha);
	}

}
