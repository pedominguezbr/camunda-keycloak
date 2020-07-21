package com.spsa.bpm.ventadesagregada.listener;

import java.util.Map;

import javax.ws.rs.core.Response;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import com.spsa.bpm.ventadesagregada.clase.CtrlProcesoLocalResult;
import com.spsa.bpm.ventadesagregada.clase.ObtieneVentasDataFico;
import com.spsa.bpm.ventadesagregada.clase.ObtieneVentasDataFicoResult;
import com.spsa.bpm.ventadesagregada.rest.VentasDataFicoRest;
import com.spsa.bpm.ventadesagregada.util.Constantes;

public class ReproControlTotalObSapTaskCreatedListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		System.out.println("Inicio ReproControlTotalObSapTaskCreatedListener..................................");
		
		Map<String, Object> variables = delegateTask.getVariables();
		VentasDataFicoRest clientObtieneVentasDataFico = new VentasDataFicoRest();
		String mensajesap = Constantes.EMPTY_STRING;
		
		CtrlProcesoLocalResult pelement =(CtrlProcesoLocalResult) variables.get("element");
		
		String codlocal = pelement.getCodlocal().toString();
		String fecproceso = pelement.getFecproceso().substring(0, 10);
		String dproceso = fecproceso.replace("-", "");
		Integer numlocal = Integer.parseInt(codlocal);		
		
		String estado = "O";
		
		//Obtener Datos de Observados Sap
		String REST_URIObtieneVentasFico = Constantes.URL_GET_VENTA_DATA_FICO_API; // String.format("http://%s/obtieneventasdatafico",apiHost);
		
		System.out.println(REST_URIObtieneVentasFico);
		Response response = clientObtieneVentasDataFico.ObtieneVentasDataFico(dproceso, numlocal, estado,
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
				System.out.println(p.getEstado());
				System.out.println(p.getCentrocosto());
				
				
				if (variables.get("mensajesap") != null) {
					System.out.println("variable delegate existe");
					mensajesap = (String) variables.get("mensajesap");			
				}else {
					System.out.println("variable elemento existe");
					mensajesap = p.getMensajesap();
				}				
				
				System.out.println(mensajesap);		
				
				variables.put("correlativo", p.getCorrelativo());
				variables.put("codlocal", p.getCodlocal().toString());
				variables.put("localdescripcion",p.getLocaldescripcion().toString());
				variables.put("sociedad", p.getSociedad().toString());			
				variables.put("fecproceso", p.getFecproceso().substring(0, 10));
				variables.put("estado", p.getEstado());
				variables.put("centrocosto", p.getCentrocosto());		
				
				//variables.put("mensajesap", p.getMensajesap());		
				variables.put("mensajesap", mensajesap);
				
				delegateTask.setVariablesLocal(variables);				
			}
		}
		
		
		System.out.println("Fin ReproControlTotalObSapTaskCreatedListener..................................");

	}

}
