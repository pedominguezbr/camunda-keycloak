package com.spsa.bpm.ventadesagregada.delegate;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.spsa.bpm.ventadesagregada.clase.CodResultRes;
import com.spsa.bpm.ventadesagregada.clase.ObtieneVentasDataFicoResult;
import com.spsa.bpm.ventadesagregada.rest.VentasDataFicoRest;
import com.spsa.bpm.ventadesagregada.util.Constantes;

public class EjecutaRechazadoTolerancia implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Inicio EjecutaRechazadoTolerancia..................................");
		Map<String, Object> variables = execution.getVariables();
		VentasDataFicoRest clientrechazarTolerancia = new VentasDataFicoRest();
		String REST_URIRechazarTolerancia = Constantes.URL_RECHAZAR_TOLERANCIA_API; // String.format("http://%s/updtoleraventasdatafico",apiHost);

		ObtieneVentasDataFicoResult p = (ObtieneVentasDataFicoResult) variables.get("element");

		String fecproceso = p.getFecproceso().substring(0, 10);
		System.out.println(p.getCorrelativo());
		System.out.println(p.getCodlocal());
		System.out.println(p.getLocaldescripcion());
		System.out.println(p.getSociedad());
		System.out.println(fecproceso);
	
		String dproceso = fecproceso.replace("-", "");

		String codlocal = p.getCodlocal().toString();
		Integer numlocal = Integer.parseInt(codlocal);
		Integer correlativo = Integer.parseInt(p.getCorrelativo().toString());

		Map<String, Object> variableput = new HashMap<String, Object>();
		variableput.put("fecproceso", dproceso);
		variableput.put("codlocal", codlocal);

		Response responserechazartolerancia = clientrechazarTolerancia.RechazarTolerancia(dproceso, numlocal,
				correlativo, REST_URIRechazarTolerancia);

		CodResultRes rechazartoleRes = responserechazartolerancia.readEntity(CodResultRes.class);

		System.out.println("rechazartoleRes.getCod_ret: " + rechazartoleRes.getCod_ret());
		System.out.println("rechazartoleRes.getResult: " + rechazartoleRes.getResult());

		//Si ejecuta ok se crea una nueva instancia con las variables de fecha y local
		if (rechazartoleRes.getCod_ret().contains("00")) {
			ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
			processEngine.getRuntimeService().startProcessInstanceByKey("ventadesagregada", variableput);
			System.out.println("INSTANCIA DE VENTA_DESAGREGADA INICIADA");			
		}
		
		System.out.println("Fin EjecutaRechazadoTolerancia..................................");
	}

}
