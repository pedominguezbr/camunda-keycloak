package com.spsa.bpm.ventadesagregada.delegate;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.spsa.bpm.ventadesagregada.clase.ObtieneVentasDataFico;
import com.spsa.bpm.ventadesagregada.clase.ObtieneVentasDataFicoResult;
import com.spsa.bpm.ventadesagregada.rest.VentasDataFicoRest;
import com.spsa.bpm.ventadesagregada.util.Constantes;
import com.spsa.bpm.ventadesagregada.util.FuncionesGenerales;

public class NotificaSoporteSapDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Inicio NotificaSoporteSapDelegate..................................");
		Map<String, Object> getvariables = execution.getVariables();
		//String apiHost = (String)getvariables.get("apiHost");
		//String apiCorreo = (String)getvariables.get("apiCorreo");
		String dproceso = (String)getvariables.get("dproceso");
		String codlocal = (String)getvariables.get("codlocal");
		String REST_URIObtieneVentasFico = Constantes.URL_GET_VENTA_DATA_FICO_API; // String.format("http://%s/obtieneventasdatafico",apiHost);
		
		System.out.println(REST_URIObtieneVentasFico);		
		
		//Map<String, String> variable = new HashMap<String, String>();
		Boolean hayObservadosSap = false;//rando.nextBoolean();		
		
		VentasDataFicoRest clientObtieneVentasDataFico = new VentasDataFicoRest();
		
		//String dproceso = "20200316";
		//Integer numlocal = 195;
		Integer numlocal = Integer.parseInt(codlocal);
		String estado = "O";// "O" Observados en Sap;
		
		Response response = clientObtieneVentasDataFico.ObtieneVentasDataFico(dproceso, numlocal, estado,
				REST_URIObtieneVentasFico);
		
		ObtieneVentasDataFico obtieneVentasDataFico = response.readEntity(ObtieneVentasDataFico.class);
		
		//Enviar Correo Notificacion
		hayObservadosSap =	FuncionesGenerales.EnviarCorreoNotificaSoporteSap( Constantes.TITULO_CORREO_OBSERVADOSAP,obtieneVentasDataFico);
				
		for (ObtieneVentasDataFicoResult p : obtieneVentasDataFico.getResult()) {
			System.out.println("===========================================");
			//String observacion = p.getDesobservacion()!= null? p.getDesobservacion() : "";
			
			System.out.println(p.getCorrelativo().toString());
			System.out.println(p.getLocaldescripcion());
			System.out.println(p.getSociedad());			
			System.out.println(p.getFecproceso());
			System.out.println(p.getEstado());
			System.out.println(p.getMensajesap());
			System.out.println(p.getDocumentosap());
			
			/*
			variable.put("codlocal", p.getCodlocal().toString());
			variable.put("codproceso", p.getCodproceso().toString());
			variable.put("fecproceso", p.getFecproceso());
			variable.put("tipestado", p.getTipestado());
			variable.put("desobservacion", p.getDesobservacion());
			variable.put("fecinicio", p.getFecinicio());
			variable.put("fecfin", p.getFecfin());
			variable.put("codusuariocrea", p.getCodusuariocrea());
			variable.put("feccreacion", p.getFeccreacion());
			variable.put("fecmodificacion", p.getFecmodificacion());
			variable.put("codusuariomodificacion", p.getCodusuariomodificacion());
			*/
			
			hayObservadosSap = true;
		}		
			

		Map<String, Object> vars = new HashMap<>();
	    vars.put("elementosObsSap", obtieneVentasDataFico.getResult());

		//vars.put("elements", ctrlProcesoLocal.getResult());
	    System.out.println("hayObservadosSap " + hayObservadosSap.toString());
	    
	    execution.setVariable("hayObservadosSap", hayObservadosSap);
	    execution.setVariables(vars);
		System.out.println("NotificaSoporteSapDelegate.execute: " + String.valueOf(response.getStatus()));
		
		
		System.out.println("Fin NotificaSoporteSapDelegate..................................");
		
	}

}
