package labs.fourpizza.dominio.gerenciador;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import labs.fourpizza.dominio.cliente.Clientes;

@LocalBean
@Stateless
public class AcoesClientes{
	
	@PersistenceContext(unitName = "fourpizza-persistenceunit")
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public boolean folhaDePagamento(int id){
		/*
		 * Realiza um processo super demorado de debito em conta do vendedor
		 * registrado.
		 */
		try{
			Thread.sleep(1000);
		}catch(InterruptedException ie){
			
		}
		return true;
	}

	public void cadastrarClientes(Clientes cliente) {
		try {
			getEntityManager().persist(cliente);
		} catch (Exception e) {
			throw new EJBException("Erro ao cadastrar cliente.");
		}		
	}

	public Clientes pesquisarClientes(int id) {
		return getEntityManager().find(Clientes.class, id);
	}
	
	public Clientes pesquisar(String qry, String nome){
		Query query = getEntityManager().createNamedQuery(qry);
		query.setParameter("nome", nome);
		return (Clientes)query.getSingleResult();
	}

	public Clientes pesquisarPorNomeClientes(String nome) {
		return pesquisar("Clientes.buscaPorNome", nome);
	}

	public Clientes pesquisarPorTelefone(String nome) {
		return pesquisar("Clientes.buscaTelefones", nome);
	}

	public void excluirClientes(Clientes cliente) {
		try {
			cliente = getEntityManager().find(Clientes.class, cliente.getId());
			if(cliente == null){
				throw new EJBException("Erro ao cadastrar cliente.");	
			}
			getEntityManager().remove(cliente);
		} catch (Exception e) {
			throw new EJBException("Erro ao cadastrar cliente.");
		}		
	}

	public void atualizarClientes(Clientes cliente) {
		try {
			getEntityManager().merge(cliente);
		} catch (Exception e) {
			throw new EJBException("Erro ao cadastrar cliente.");
		}	
	}

}
