package labs.fourpizza.dominio.interceptadores;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class AcoesAuditoria {
	
	@AroundInvoke
	public Object auditoria(InvocationContext ic) throws Exception{
		System.out.println("Entrou na auditoria");
		Object obj = ic.proceed();
		System.out.println("Saiu da auditoria");
		return obj;
	}

}
