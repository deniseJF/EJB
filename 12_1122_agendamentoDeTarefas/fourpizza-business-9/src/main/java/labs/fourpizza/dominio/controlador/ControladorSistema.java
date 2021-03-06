package labs.fourpizza.dominio.controlador;

import java.util.concurrent.Future;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RunAs;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import labs.fourpizza.dominio.cliente.Clientes;
import labs.fourpizza.dominio.gerenciador.AcoesClientes;

@Stateless
@LocalBean
@RunAs("usuarios")
public class ControladorSistema{
	
	@EJB
//	private GerenciadorClientes acoes; // Trocar por
	private AcoesClientes acoes; 

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void cadastrarClientes(Clientes cliente) {
		acoes.cadastrarClientes(cliente);
	}
	
	@Asynchronous
	public Future<Comprovante> gerarFolhaDePagamento(int id){
		if(acoes.folhaDePagamento(id)){
			return new AsyncResult<Comprovante>(new Comprovante("Feito"));
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Clientes pesquisarClientes(int id) {
		return acoes.pesquisarClientes(id);
	}

	@DenyAll	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void excluirClientes(Clientes cliente) {
		acoes.excluirClientes(cliente);
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	public void atualizarClientes(Clientes cliente) {
		acoes.atualizarClientes(cliente);
	}
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Clientes pesquisarPorNome(String nome){
		return acoes.pesquisarPorNomeClientes(nome);
	}
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Clientes pesquisarPorTelefone(String nome){
		return acoes.pesquisarPorTelefone(nome);
	}

}
