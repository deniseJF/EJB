package labs.fourpizza.dominio.controlador;

public class Comprovante {
	
	private String retorno; 

	public Comprovante() {
	}
	
	public Comprovante(String retorno) {
		this.retorno = retorno;
	}
	
	@Override
	public String toString() {
		return retorno;
	}
}
