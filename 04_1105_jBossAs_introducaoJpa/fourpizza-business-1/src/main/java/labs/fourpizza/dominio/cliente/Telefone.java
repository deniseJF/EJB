package labs.fourpizza.dominio.cliente;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import labs.fourpizza.dominio.cliente.chaves.TelefonePK;

@Entity
public class Telefone implements Serializable{
	private static final long serialVersionUID = 3759397390114613106L;
	
	@EmbeddedId
	private TelefonePK id;
//	@Id
//	private int id;
	@Column(length=10)
	private String telefone;
	@Column(length=3)
	private String ddd;
	@Enumerated(EnumType.STRING)
	private TipoOperadora operadora;
//	@ManyToOne
//	@JoinColumn(name="idCliente")
//	private Clientes cliente;
	
	public Telefone() {
		this.id = new TelefonePK();
	}

	
	public Telefone(TelefonePK id) {
		this.id = id;
	}
	
	public TelefonePK getId() {
		return id;
	}
	public void setId(TelefonePK id) {
		this.id = id;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getDdd() {
		return ddd;
	}
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	public TipoOperadora getOperadora() {
		return operadora;
	}
	public void setOperadora(TipoOperadora operadora) {
		this.operadora = operadora;
	}
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
//	public Clientes getCliente() {
//		return cliente;
//	}
//	public void setCliente(Clientes cliente) {
//		this.cliente = cliente;
//	}


//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((id == null) ? 0 : id.hashCode());
//		return result;
//	}
//
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Telefone other = (Telefone) obj;
//		if (id == null) {
//			if (other.id != null)
//				return false;
//		} else if (!id.equals(other.id))
//			return false;
//		return true;
//	}
	
	
}
