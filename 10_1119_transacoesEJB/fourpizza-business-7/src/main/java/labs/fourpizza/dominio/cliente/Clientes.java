package labs.fourpizza.dominio.cliente;

import java.io.Serializable;
import java.util.ArrayList;
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
@NamedQueries(value={
	@NamedQuery(name="Clientes.buscaPorNome", query = "Select c from Clientes c where c.nome = :nome"),
	@NamedQuery(name="Clientes.buscaTelefones", query = "Select c from Clientes c join fetch c.telefones where c.nome = :nome")
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
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)//, optional = false)
	@JoinColumn(name="id")//, columnDefinition = "id", referencedColumnName = "id")
	private Usuarios usuario;
	
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinColumn(name="id")
	private List<Telefone> telefones = new ArrayList<Telefone>();
	
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

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	
	
}
