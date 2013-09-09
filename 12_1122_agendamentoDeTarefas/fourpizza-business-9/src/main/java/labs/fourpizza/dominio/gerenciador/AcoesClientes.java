package labs.fourpizza.dominio.gerenciador;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import labs.fourpizza.dominio.cliente.Clientes;

import org.jboss.logging.Logger;

@Stateless
@LocalBean
//@RolesAllowed({"usuarios"})
public class AcoesClientes{// implements GerenciadorClientes{
	
	private Logger log;
	
	@Resource
	private TimerService timerService;
	
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
		try {
			Thread.sleep(1000);
			
		} catch (InterruptedException ie) {
			
		}
		return true;
	}
	public void cadastrarClientes(Clientes cliente) {
		try {
			getEntityManager().persist(cliente);
			Calendar data = Calendar.getInstance();
			data.setTime(cliente.getDtNasc());
			aniver(cliente, data);
		} catch (Exception e) {
			throw new EJBException("Erro ao cadastrar cliente.");
		}		
	}
	
	public void aniver(Clientes cliente, Calendar data){
		ScheduleExpression aniver = new ScheduleExpression().dayOfMonth(data.get(GregorianCalendar.DAY_OF_MONTH)).month(data.get(GregorianCalendar.MONTH));
		timerService.createCalendarTimer(aniver, new TimerConfig(cliente, true));
	}
	
	@Timeout
	public void enviaEmail(Timer timer){
		Clientes cliente = (Clientes) timer.getInfo();
		if(cliente != null ){
			log.info("Enviar Email de Aniversário. (TimeOut)");
		}
	}
	
	@Schedule(dayOfWeek="Mon-Fri", hour="3,5")
	public void enviaEmailSchedule(){
		log.info("Enviar Email de Aniversário. (Schedule)");
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
