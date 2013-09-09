package labs.fourpizza.dominio.controlador;

import javax.ejb.Local;

import labs.fourpizza.dominio.cliente.Clientes;

@Local
public interface ControlarSistema {	
	public void cadastrarClientes(Clientes cliente);
	public Clientes pesquisarClientes(int id);
	public void excluirClientes(Clientes cliente);
	public void atualizarClientes(Clientes cliente);
	public Clientes pesquisarPorNome(String nome);
	public Clientes pesquisarPorTelefone(String telefone);
	
}
