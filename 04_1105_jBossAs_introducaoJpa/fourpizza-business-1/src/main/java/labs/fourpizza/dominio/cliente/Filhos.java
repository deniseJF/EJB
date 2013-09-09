package labs.fourpizza.dominio.cliente;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Filhos implements Serializable{
	private static final long serialVersionUID = 9190307188431589716L;
	@Id
	private int idFilho;
	private String name;
	@ManyToOne
	@JoinColumn(name="idPai")
	private Pais pai;
	
	public Filhos() {
		// TODO Auto-generated constructor stub
	}
	
	public Filhos(int idFilho, String name, Pais pai) {
		this.idFilho = idFilho;
		this.name = name;
		this.pai = pai;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIdFilho() {
		return idFilho;
	}
	public void setIdFilho(int idFilho) {
		this.idFilho = idFilho;
	}
	public Pais getPai() {
		return pai;
	}
	public void setPai(Pais pai) {
		this.pai = pai;
	}

}
