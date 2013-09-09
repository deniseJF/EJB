package labs.fourpizza.dominio.cliente;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name="CLIENTES")
public class Clientes implements Serializable{
	private static final long serialVersionUID = 4019043826836984191L;
	
	@Id
	private int id;
	@Column(nullable=false,length=50)
	private String nome;
	@Temporal(TemporalType.DATE)
	private Date dtNasc;
	@Embedded
	private Endereco endereco;
//	@JoinColumn 
//	@OneToMany(mappedBy="cliente")
//	private List<Telefone> telefone;
	
	public Clientes(){
		this.endereco = new Endereco();
		this.dtNasc = new Date();
	}
	
	public Clientes(String nome, Endereco endereco){
		this.nome = nome;
		this.endereco = endereco;
	}
	
	public Clientes(int id, String nome, Endereco endereco){
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDtNasc() {
		return dtNasc;
	}
	public void setDtNasc(Date dtNasc) {
		this.dtNasc = dtNasc;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Clientes other = (Clientes) obj;
		if (id != other.id)
			return false;
		return true;
	}

//	public List<Telefone> getTelefone() {
//		return telefone;
//	}
//
//	public void setTelefone(List<Telefone> telefone) {
//		this.telefone = telefone;
//	}
	
}
