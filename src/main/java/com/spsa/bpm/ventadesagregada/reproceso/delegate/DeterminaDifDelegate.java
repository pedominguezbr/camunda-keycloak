package com.spsa.bpm.ventadesagregada.reproceso.delegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.core.Response;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.spsa.bpm.ventadesagregada.clase.CodResultRes;
import com.spsa.bpm.ventadesagregada.rest.ReprocesarRest;
import com.spsa.bpm.ventadesagregada.util.Constantes;

public class DeterminaDifDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Inicio DeterminaDifDelegate..................................");
		
	    
	    //Aqui empieza----------------
	    Map<String, Object> getvariables = execution.getVariables();
	    String dproceso = (String)getvariables.get("fecproceso");
	    ReprocesarRest client = new ReprocesarRest();
		String REST_URIDeterminaDiferencia = Constantes.URL_DETERM_REPROCESO_API; //  "http://ws-rest-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/ejecutadeterminadiferencia";
		
		System.out.println(REST_URIDeterminaDiferencia);
		System.out.println("dproceso ini: " + dproceso);
		
		//Se obtiene la fecha de proceso si no se envia el parametro de fecha de proceso
		if (dproceso == null)
		{
			dproceso = Constantes.EMPTY_STRING;
		}
		
		Response response = client.DeterminarDiferencia(dproceso, REST_URIDeterminaDiferencia);
		CodResultRes ejecutaCargaVentaSubsanarRes = response.readEntity(CodResultRes.class);
		
		System.out.println("getStatus: "+ String.valueOf(response.getStatus()) );
		
		System.out.println("getCod_ret: "+ ejecutaCargaVentaSubsanarRes.getCod_ret() );
		System.out.println("getResult: "+ ejecutaCargaVentaSubsanarRes.getResult() );
		System.out.println("Fin DeterminaDifDelegate..................................");
	}

}