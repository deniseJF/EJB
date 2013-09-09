package labs.fourpizza.dominio.controlador;

import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import labs.fourpizza.dominio.cliente.Clientes;
import labs.fourpizza.dominio.gerenciador.AcoesClientes;

@LocalBean
@Stateless
public class ControladorSistema{
	
	@EJB
	private AcoesClientes acoes;
	
	@Asynchronous
	public Future<Comprovante> gerarFolhaDePagamento(int id){
		if(acoes.folhaDePagamento(id)){
			return new AsyncResult<Comprovante>(new Comprovante("Feito"));
		}
		return null;
	}
	
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
