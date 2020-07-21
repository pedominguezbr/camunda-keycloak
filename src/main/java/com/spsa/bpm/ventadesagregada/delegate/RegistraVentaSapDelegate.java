package com.spsa.bpm.ventadesagregada.delegate;

import java.util.Map;

import javax.ws.rs.core.Response;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import com.spsa.bpm.ventadesagregada.rest.VentasDataFicoRest;
import com.spsa.bpm.ventadesagregada.util.Constantes;

public class RegistraVentaSapDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {		
		System.out.println("Inicio RegistraVentaSapDelegate..................................");
		
		Map<String, Object> getvariables = execution.getVariables();
		
		String dproceso = (String)getvariables.get("dproceso");
		String codlocal = (String)getvariables.get("codlocal");
		String REST_URIRegistraVentaSap = Constantes.URL_REG_VTA_SAP_API; //String.format("http://%s/registraventasap",apiHost);
		
		System.out.println(REST_URIRegistraVentaSap);
		
		VentasDataFicoRest clientRegistrarVentaSap = new VentasDataFicoRest();
		Integer numlocal = Integer.parseInt(codlocal); //99999; //Todos los locales
		
		Response response = clientRegistrarVentaSap.RegistrarVentaSap(dproceso,numlocal, REST_URIRegistraVentaSap);
		System.out.println("RegistraVentaSapDelegate.RegistrarVentaSap: "+ String.valueOf(response.getStatus()));
		String body = response.readEntity(String.class);

		System.out.println(body);
		System.out.println("Fin RegistraVentaSapDelegate................................");
	}

}
