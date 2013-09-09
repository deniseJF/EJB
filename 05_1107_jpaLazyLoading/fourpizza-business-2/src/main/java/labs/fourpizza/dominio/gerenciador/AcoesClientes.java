package labs.fourpizza.dominio.gerenciador;

import javax.ejb.EJBException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import labs.fourpizza.dominio.cliente.Clientes;


public class AcoesClientes implements GerenciadorClientes{
	@PersistenceContext
	private
	EntityManager entityManager;
	
	public Clientes pesquisarPorNomeClientes(String nome){
		return this.pesquisar("Clientes.buscaPorNome", nome);
	}
	
	public Clientes pesquisarPorTelefone(String nome){
		return this.pesquisar("Clientes.buscaTelefones", nome);
	}

	@Override
	public void cadastrarClientes(Clientes cliente) throws EJBException{
		getEntityManager().persist(cliente);		
	}

	@Override
	public Clientes pesquisarClientes(int id) {
		return getEntityManager().find(Clientes.class, id);
	}

	@Override
	public Clientes pesquisar(String qry, String nome) {
		Query query = getEntityManager().createNamedQuery(qry);
		query.setParameter("nome", nome);
		try{
			return (Clientes)query.getSingleResult();			
		}catch(NoResultException e){
			return null;
		}
	}

	@Override
	public void atualizarClientes(Clientes cliente) throws EJBException {
		getEntityManager().merge(cliente);		
	}

	@Override
	public void excluirClientes(Clientes cliente) throws EJBException{
		Clientes c = getEntityManager().find(Clientes.class, cliente.getId());
		if(c==null)
			throw new EJBException("Erro ao cadastrar cliente.");
		else
			getEntityManager().remove(c);
		
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
}
