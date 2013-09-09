package labs.fourpizza.dominio.cliente;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CLIENTES")
@NamedQueries({
	@NamedQuery(name="Clientes.buscaPorNome",query="SELECT c FROM Clientes c WHERE c.nome = :nome"),
	@NamedQuery(name="Clientes.buscaTelefones",query="SELECT c FROM Clientes c JOIN FETCH c.telefones WHERE c.nome = :nome")
})
public class Clientes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3672377741548640525L;
	
	@Id
	private int id;
	
	@Column(length=50, nullable=false)
	private String nome;
	
	@Embedded
	private Endereco endereco;
	
	@Temporal(TemporalType.DATE)
	private Date dtNasc;
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="id")
	private Usuarios usuario;
	
	@OneToMany(cascade={CascadeType.MERGE,CascadeType.REMOVE},fetch=FetchType.LAZY)
	@JoinColumn(name="id")
	private List<Telefone> telefones;
	
	public Clientes() {
		this.endereco = new Endereco();
		this.dtNasc = new Date();
	}
	
	public Clientes(String nome, Endereco endereco) {
		this.nome = nome;
		this.endereco = endereco;
		this.dtNasc = new Date();
	}
	
	public Clientes(int id, String nome, Endereco endereco) {
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.dtNasc = new Date();
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

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

}
