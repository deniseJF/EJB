package labs.fourpizza.dominio.gerenciador;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@LocalBean
@Startup
public class ContadorClientes{
	
	private int contador = 0;
	
	public void incrementa(){
		this.contador++;
	}
	
	public int getContador(){
		return this.contador;
	}
	

}
