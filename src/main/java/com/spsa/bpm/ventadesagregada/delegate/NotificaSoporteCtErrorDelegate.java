package com.spsa.bpm.ventadesagregada.delegate;


import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Response;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.spsa.bpm.ventadesagregada.util.Constantes;
import com.spsa.bpm.ventadesagregada.clase.CtrlProcesoLocal;
import com.spsa.bpm.ventadesagregada.clase.CtrlProcesoLocalResult;
import com.spsa.bpm.ventadesagregada.rest.ObtieneProcesoLocalRest;
import com.spsa.bpm.ventadesagregada.util.FuncionesGenerales;

public class NotificaSoporteCtErrorDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Inicio NotificaSoporteCtErrorDelegate..................................");
		
		Map<String, Object> getvariables = execution.getVariables();
		//String apiHost = (String)getvariables.get("apiHost");
		//String apiCorreo = (String)getvariables.get("apiCorreo");
		String dproceso = (String)getvariables.get("dproceso");
		String codlocal = (String)getvariables.get("codlocal");
		String REST_URIObtieneProcesoLocal = Constantes.URL_OBTIENE_PROCE_LOCAL_API;
		
		System.out.println(REST_URIObtieneProcesoLocal);
				
		Map<String, String> variable = new HashMap<String, String>();
		Boolean hayObservadosCT = false;//rando.nextBoolean();		
		
		ObtieneProcesoLocalRest clientObtieneProcesoLocal = new ObtieneProcesoLocalRest();
		
		Integer numlocal = Integer.parseInt(codlocal);
		String estado = "E";
		
		Response response = clientObtieneProcesoLocal.ObtieneProcesoLocal(dproceso, numlocal, estado,
				REST_URIObtieneProcesoLocal);
		
		System.out.println("NotificaSoporteCtErrorDelegate.execute: " + String.valueOf(response.getStatus()));
	
		//Map<String, String> variables = new HashMap<String, String>();
		CtrlProcesoLocal ctrlProcesoLocal = response.readEntity(CtrlProcesoLocal.class);
		
		//Enviar Correo Notificacion		
		hayObservadosCT = FuncionesGenerales.EnviarCorreoNotificaControlTotales(Constantes.TITULO_CORREO_CONTROLTOTALES, ctrlProcesoLocal);
				
		for (CtrlProcesoLocalResult p : ctrlProcesoLocal.getResult()) {
			System.out.println("===========================================");
			String observacion = p.getDesobservacion()!= null? p.getDesobservacion() : "";
			
			System.out.println(p.getCodproceso());
			System.out.println(p.getLocaldescripcion());
			System.out.println(p.getSociedad());
			System.out.println(p.getCodusuariocrea());
			
			System.out.println(p.getFecproceso());
			System.out.println(p.getTipestado());
			System.out.println(observacion);
			System.out.println(p.getFecinicio());
			System.out.println(p.getFecfin());
			System.out.println(p.getCodusuariocrea());
			System.out.println(p.getFeccreacion().substring(0, 10));
			System.out.println(p.getFecmodificacion());
			System.out.println(p.getCodusuariomodificacion());

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
			
			hayObservadosCT = true;
		}		
		
		
		//delegateTask.setVariables(variables);
		
		/*
		Map<String, Object> vars = new HashMap<>();
	    ArrayList<Integer> elements = new ArrayList<Integer>();
	    elements.add(4);
	    elements.add(5);
	    elements.add(6);
	    vars.put("elements", elements);
	    */
	    
		Map<String, Object> vars = new HashMap<>();
	    vars.put("elements", ctrlProcesoLocal.getResult());
		
	    //ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("simple_multi", vars);		
		//aqui quedo
		
		//vars.put("elements", ctrlProcesoLocal.getResult());
	    System.out.println("hayObservadosCT " + hayObservadosCT.toString());
	    execution.setVariable("hayObservadosCT", hayObservadosCT);
	    execution.setVariables(vars);
	    
		System.out.println("Fin NotificaSoporteCtErrorDelegate..................................");
		
	}

}
