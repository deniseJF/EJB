package labs.fourpizza.dominio.gerenciador;

import labs.fourpizza.dominio.cliente.Clientes;


public interface GerenciadorClientes {
	public void cadastrarClientes(Clientes cliente);
	public Clientes pesquisarClientes(int id);
	public Clientes pesquisar(String qry,String nome);
	public void atualizarClientes(Clientes cliente);
	public void excluirClientes(Clientes cliente);
}