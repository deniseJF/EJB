package labs.fourpizza.dominio.gerenciador;

import javax.ejb.Local;

import labs.fourpizza.dominio.cliente.Clientes;

@Local
public interface GerenciadorClientes {
	
	void cadastrarClientes(Clientes cliente);
	Clientes pesquisarClientes(int id);
	Clientes pesquisar(String qry, String nome);
	void excluirClientes(Clientes cliente);
	
	Clientes pesquisarPorNomeClientes(String nome);
	Clientes pesquisarPorTelefone(String nome);	
	
	void atualizarClientes(Clientes cliente);
}
