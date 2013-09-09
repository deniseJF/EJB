package labs.fourpizza.dominio.cliente;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Pais")
public class Pais implements Serializable{
	private static final long serialVersionUID = 6728437600465489323L;
	
	@Id
	private int idPai;
	private String name;
	@OneToMany(mappedBy = "pai")
	private List<Filhos> filho;
	
	public Pais() {
		// TODO Auto-generated constructor stub
	}
	
	public Pais(int idPai, String name) {
		this.idPai = idPai;
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIdPai() {
		return idPai;
	}
	public void setIdPai(int idPai) {
		this.idPai = idPai;
	}
	public List<Filhos> getFilho() {
		return filho;
	}
	public void setFilho(List<Filhos> filho) {
		this.filho = filho;
	}

}
