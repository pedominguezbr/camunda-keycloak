package com.spsa.bpm.ventadesagregada.delegate;

import java.util.Map;

import javax.ws.rs.core.Response;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.spsa.bpm.ventadesagregada.clase.ObtenerFechaProcesoRes;
import com.spsa.bpm.ventadesagregada.rest.EjecutarCargaVentaRest;
import com.spsa.bpm.ventadesagregada.util.Constantes;

public class ObtenerFechaProcesoDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Inicio ObtenerFechaProcesoDelegate................................");
		String REST_URIObtenerFechaProceso = Constantes.URL_GET_FECHA_PROCESS_API; //String.format("http://%s/obtienefechaproceso",apiHost);
		
		Map<String, Object> getvariables = execution.getVariables();

		String dproceso = (String)getvariables.get("fecproceso");
		String codlocal = (String)getvariables.get("codlocal");
		
		System.out.println("dproceso ini: " + dproceso);
		
		//Se obtiene la fecha de proceso si no se envia el parametro de fecha de proceso
		if (dproceso == null)
		{
			System.out.println(REST_URIObtenerFechaProceso);
			EjecutarCargaVentaRest client = new EjecutarCargaVentaRest();
			Response response = client.ObtenerFechaProceso(REST_URIObtenerFechaProceso);		

			ObtenerFechaProcesoRes obtenerFechaProcesoRes = response.readEntity(ObtenerFechaProcesoRes.class);
			
			System.out.println("getStatus: "+ String.valueOf(response.getStatus()) );
			
			System.out.println("getCod_ret: "+ obtenerFechaProcesoRes.getCod_ret() );
			System.out.println("getResult: "+ obtenerFechaProcesoRes.getResult() );
			
			dproceso = obtenerFechaProcesoRes.getResult();			
		}
	
		//se setea por default todos los locales si no se envia parametro de local al iniciar el proceso
		if (codlocal == null)
		{
			codlocal = "99999";
		}
		
		//execution.setVariable("apiHost", apiHost);
		//execution.setVariable("apiCorreo", apiCorreo);		
		execution.removeVariable("codlocal");
		execution.removeVariable("fecproceso");
		execution.removeVariable("dproceso");
		
		execution.setVariable("dproceso", dproceso);
		execution.setVariable("codlocal", codlocal);
		System.out.println("Fin ObtenerFechaProcesoDelegate................................");
		
	}

}
