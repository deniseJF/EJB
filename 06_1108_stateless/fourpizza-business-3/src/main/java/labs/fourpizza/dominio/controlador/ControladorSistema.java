package labs.fourpizza.dominio.controlador;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import labs.fourpizza.dominio.cliente.Clientes;
import labs.fourpizza.dominio.gerenciador.GerenciadorClientes;

@Stateless
public class ControladorSistema implements ControlarSistema{
	@EJB
	GerenciadorClientes acoes;

	@Override
	public void cadastrarClientes(Clientes cliente) {
		this.acoes.cadastrarClientes(cliente);
	}

	@Override
	public Clientes pesquisarClientes(int id) {
		return this.acoes.pesquisarClientes(id);
	}

	@Override
	public void excluirClientes(Clientes cliente) {
		this.acoes.excluirClientes(cliente);
	}

	@Override
	public void atualizarClientes(Clientes cliente) {
		this.acoes.atualizarClientes(cliente);
	}

	@Override
	public Clientes pesquisarPorNome(String nome) {
		return this.acoes.pesquisarPorNomeClientes(nome);
	}

	@Override
	public Clientes pesquisarPorTelefone(String telefone) {
		return this.acoes.pesquisarPorTelefone(telefone);
	}
	

}
