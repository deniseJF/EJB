package labs.fourpizza.dominio.cliente;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="USUARIOS")
public class Usuarios implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4622724191198914272L;
	
	
	@Id
	private int id;
	
	@Column(name="e_mail", length=30, nullable=false)
	private String email;
	
	@Column(nullable=false)
	private String senha;
	
	@OneToOne(mappedBy="usuario")
	private Clientes cliente;
	
	public Usuarios() {
	}
	
	public Usuarios(int id, String email, String senha) {
		this.id = id;
		this.email = email;
		this.senha = senha;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		Usuarios other = (Usuarios) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Clientes getCliente() {
		return cliente;
	}

	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}

}
