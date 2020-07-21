package com.spsa.bpm.ventadesagregada.delegate;

import java.util.Map;

import javax.ws.rs.core.Response;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.spsa.bpm.ventadesagregada.rest.EjecutarCargaVentaRest;
import com.spsa.bpm.ventadesagregada.util.Constantes;

public class EjecutaCargaVentaDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub	
		System.out.println("Inicio EjecutaCargaVentaDelegate..................................");
		EjecutarCargaVentaRest client = new EjecutarCargaVentaRest();
		Map<String, Object> variables = execution.getVariables();
		
		String dproceso = (String)variables.get("dproceso");		
		String codlocal = (String)variables.get("codlocal");
		
		String REST_URIEjecutaCargaVenta = Constantes.URL_EJECUTA_CARGA_VENTA_API; //String.format("http://%s/ejecutacargaventa",apiHost);
		
		System.out.println(REST_URIEjecutaCargaVenta);

		Integer numlocal = Integer.parseInt(codlocal);
		
		Response response = client.EjecutarCargaVenta(dproceso, numlocal, REST_URIEjecutaCargaVenta);
		System.out.println("EjecutaCargaVentaDelegate.EjecutarCargaVenta"+ String.valueOf(response.getStatus()) );
		String body = response.readEntity(String.class);

		System.out.println(body);
		System.out.println("Fin EjecutaCargaVentaDelegate..................................");
		
		execution.setVariable("dproceso", dproceso);
	}

}
