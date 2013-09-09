package labs.fourpizza.dominio.cadastros;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import labs.fourpizza.dominio.cliente.Clientes;
import labs.fourpizza.dominio.cliente.Usuarios;
import labs.fourpizza.dominio.gerenciador.GerenciadorClientes;

@Stateful
public class CadastrarCliente implements CadastroClienteLocal{
	@EJB
	private GerenciadorClientes acoes;
	private Usuarios user;
	
	@Remove
	@Override
	public void cadastrarClientes(Clientes cliente)
			throws IllegalStateException {
		if(user.getEmail()==null || user.getEmail().trim().equals("")){
			throw new IllegalStateException("Email não colocado.");
		}else{
			cliente.setUsuario(user);
			user.setCliente(cliente);
			acoes.cadastrarClientes(cliente);
		}
	}

	@Override
	public void setUser(String email, String senha) {
		this.user = new Usuarios();		
		user.setEmail(email);
		user.setSenha(senha);
	}

}
