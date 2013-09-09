package labs.fourpizza.dominio.controlador;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import labs.fourpizza.dominio.cliente.Clientes;
import labs.fourpizza.dominio.gerenciador.GerenciadorClientes;

@Stateless
public class ControladorSistema implements ControlarSistema{
	
	@EJB
	private GerenciadorClientes acoes;
	
	public void cadastrarClientes(Clientes cliente) {
		acoes.cadastrarClientes(cliente);
	}


	public Clientes pesquisarClientes(int id) {
		return acoes.pesquisarClientes(id);
	}

	public void excluirClientes(Clientes cliente) {
		acoes.excluirClientes(cliente);
	}

	public void atualizarClientes(Clientes cliente) {
		acoes.atualizarClientes(cliente);
	}
	
	public Clientes pesquisarPorNome(String nome){
		return acoes.pesquisarPorNomeClientes(nome);
	}
	
	public Clientes pesquisarPorTelefone(String nome){
		return acoes.pesquisarPorTelefone(nome);
	}

}
