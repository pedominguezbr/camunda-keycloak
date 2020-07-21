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

public class NotificaToleranciaIgvDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Inicio NotificaToleranciaIgvDelegate..................................");
		Map<String, Object> getvariables = execution.getVariables();

		String dproceso = (String)getvariables.get("dproceso");
		String codlocal = (String)getvariables.get("codlocal");
		String REST_URIObtieneVentasFico = Constantes.URL_GET_VENTA_DATA_FICO_API; //String.format("http://%s/obtieneventasdatafico",apiHost);
		
		System.out.println(REST_URIObtieneVentasFico);	

		
		//Map<String, String> variable = new HashMap<String, String>();
		Boolean hayObservadosToleIgv = false;//rando.nextBoolean();		
		
		VentasDataFicoRest clientObtieneVentasDataFico = new VentasDataFicoRest();		
		
		Integer numlocal = Integer.parseInt(codlocal);
		String estado = "E";// "N";
		
		Response response = clientObtieneVentasDataFico.ObtieneVentasDataFico(dproceso, numlocal, estado,
				REST_URIObtieneVentasFico);
		
		System.out.println("NotificaToleranciaIgvDelegate.execute: " + String.valueOf(response.getStatus()));
	
		//Map<String, String> variables = new HashMap<String, String>();
		ObtieneVentasDataFico obtieneVentasDataFico = response.readEntity(ObtieneVentasDataFico.class);
		
		//Enviar Correo Notificacion		
		hayObservadosToleIgv = FuncionesGenerales.EnviarCorreoNotificaTolerancia(Constantes.TITULO_CORREO_TOLERANCIAIGV,obtieneVentasDataFico);
		
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
			System.out.println(p.getFeccrea());
			System.out.println(p.getUsucrea());
			System.out.println(p.getFecmodifica());
			System.out.println(p.getUsumodifica());
			System.out.println(String.format(Constantes.FORMAT_DOUBLE, p.getTotalvta()));
			System.out.println(String.format(Constantes.FORMAT_DOUBLE, p.getIgvinformado())); 
			System.out.println(String.format(Constantes.FORMAT_DOUBLE, p.getIgvcalculado()));
			System.out.println(String.format(Constantes.FORMAT_DOUBLE, p.getIgvdiferencia()));
			System.out.println(String.format(Constantes.FORMAT_DOUBLE, p.getTolerancia()));
			
			hayObservadosToleIgv = true;
		}	

		Map<String, Object> vars = new HashMap<>();
	    vars.put("elementosTolerancia", obtieneVentasDataFico.getResult());

		//vars.put("elements", ctrlProcesoLocal.getResult());
	    System.out.println("hayObservadosToleIgv " + hayObservadosToleIgv.toString());
	    
	    execution.setVariable("hayObservadosToleIgv", hayObservadosToleIgv);
	    execution.setVariables(vars);
		
		/*
		Random rando = new Random();
		
		Boolean hayObservadosToleIgv = rando.nextBoolean();		
		System.out.println("hayObservadosToleIgv " + hayObservadosToleIgv.toString());
		
		 execution.setVariable("hayObservadosToleIgv", hayObservadosToleIgv);
	
		
		 Map<String, Object> vars = new HashMap<>();
	    ArrayList<Integer> elements = new ArrayList<Integer>();
	    elements.add(4);
	    elements.add(5);
	    elements.add(6);
	    vars.put("elementosTolerancia", elements);
	    execution.setVariables(vars);
	    */
		System.out.println("Fin NotificaToleranciaIgvDelegate..................................");
		
	}

}
