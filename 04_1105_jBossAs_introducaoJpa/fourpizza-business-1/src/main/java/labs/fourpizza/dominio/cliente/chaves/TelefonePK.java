package labs.fourpizza.dominio.cliente.chaves;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import labs.fourpizza.dominio.cliente.TipoTelefone;

@Embeddable
public class TelefonePK implements Serializable{
	private static final long serialVersionUID = 8511094340705558856L;
	
	private int id;
	@Enumerated(EnumType.ORDINAL)
	private TipoTelefone tipo;
	
	public TelefonePK() {
		this.id = 0;
		this.tipo = TipoTelefone.RESIDENCIAL;
	}
	
	public TelefonePK(int id, TipoTelefone tipo) {
		this.id = id;
		this.tipo = tipo;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public TipoTelefone getTipo() {
		return tipo;
	}
	public void setTipo(TipoTelefone tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		TelefonePK other = (TelefonePK) obj;
		if (id != other.id)
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
}
