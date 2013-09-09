package labs.fourpizza.dominio.cadastros;

import javax.ejb.Local;

import labs.fourpizza.dominio.cliente.Clientes;

@Local
public interface CadastroClienteLocal {
	public void cadastrarClientes(Clientes cliente) throws IllegalStateException;
	public void setUser(String email, String senha);
}
