package com.spsa.bpm.ventadesagregada.delegate;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.spsa.bpm.ventadesagregada.clase.CodResultRes;
import com.spsa.bpm.ventadesagregada.clase.CtrlProcesoLocalResult;
import com.spsa.bpm.ventadesagregada.clase.ObtieneVentasDataFico;
import com.spsa.bpm.ventadesagregada.clase.ObtieneVentasDataFicoResult;
import com.spsa.bpm.ventadesagregada.rest.VentasDataFicoRest;
import com.spsa.bpm.ventadesagregada.util.Constantes;

public class EjecutaRechazadoTotalTolerancia implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("Inicio EjecutaRechazadoTotalTolerancia..................................");		
		Map<String, Object> variables = execution.getVariables();
		VentasDataFicoRest clientrechazarTolerancia = new VentasDataFicoRest();		
		CtrlProcesoLocalResult pelement =(CtrlProcesoLocalResult) variables.get("element");
		
		String codlocal = pelement.getCodlocal().toString();
		String fecproceso = pelement.getFecproceso().substring(0, 10);
		Integer numlocal = Integer.parseInt(codlocal);		
		String dproceso = fecproceso.replace("-", "");
		Map<String, Object> variableput = new HashMap<String, Object>();
		variableput.put("fecproceso", dproceso);
		variableput.put("codlocal", codlocal);
	    
	    String estado = "E";
		
		//Obtener Datos de Observados Sap
		String REST_URIObtieneVentasFico = Constantes.URL_GET_VENTA_DATA_FICO_API; // String.format("http://%s/obtieneventasdatafico",apiHost);
		String REST_URIRechazarTolerancia = Constantes.URL_RECHAZAR_TOLERANCIA_API; // String.format("http://%s/updtoleraventasdatafico",apiHost);

		System.out.println(REST_URIObtieneVentasFico);
		Response response = clientrechazarTolerancia.ObtieneVentasDataFico(dproceso, numlocal, estado,
				REST_URIObtieneVentasFico);
		ObtieneVentasDataFico obtieneVentasDataFico = response.readEntity(ObtieneVentasDataFico.class);
		if (obtieneVentasDataFico.getResult().size()>0)
		{
			ObtieneVentasDataFicoResult p = obtieneVentasDataFico.getResult().get(0);
			if (p!=null) {
				System.out.println(p.getCorrelativo());
				System.out.println(p.getCodlocal());
				System.out.println(p.getLocaldescripcion());
				System.out.println(p.getSociedad());			
				System.out.println(p.getFecproceso().substring(0, 10));
				
				Integer correlativo = Integer.parseInt(p.getCorrelativo().toString());

				System.out.println(REST_URIRechazarTolerancia);
				Response responserechazartolerancia = clientrechazarTolerancia.RechazarTolerancia(dproceso, numlocal,
						correlativo, REST_URIRechazarTolerancia);
				CodResultRes rechazartoleRes = responserechazartolerancia.readEntity(CodResultRes.class);

				System.out.println("rechazartoleRes.getCod_ret: " + rechazartoleRes.getCod_ret());
				System.out.println("rechazartoleRes.getResult: " + rechazartoleRes.getResult());

				//Si ejecuta ok se genera la instancia a procesar
				if (rechazartoleRes.getCod_ret().contains("00")) {
					ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
					processEngine.getRuntimeService().startProcessInstanceByKey("ventadesagregada", variableput);
					System.out.println("INSTANCIA DE VENTA_DESAGREGADA INICIADA");			
				}
			}
		}				
	
	    
		System.out.println("Fin EjecutaRechazadoTotalTolerancia..................................");
	}

}
