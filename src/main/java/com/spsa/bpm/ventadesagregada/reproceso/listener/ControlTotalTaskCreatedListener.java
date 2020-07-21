package com.spsa.bpm.ventadesagregada.reproceso.listener;

import java.util.Map;

import javax.ws.rs.core.Response;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import com.spsa.bpm.ventadesagregada.clase.CtrlProcesoLocal;
import com.spsa.bpm.ventadesagregada.clase.CtrlProcesoLocalResult;
import com.spsa.bpm.ventadesagregada.clase.ListaLocalesReprocesoReq;
import com.spsa.bpm.ventadesagregada.clase.LocalesReproceso;
import com.spsa.bpm.ventadesagregada.rest.ObtieneProcesoLocalRest;
import com.spsa.bpm.ventadesagregada.rest.ReprocesarRest;
import com.spsa.bpm.ventadesagregada.util.Constantes;

public class ControlTotalTaskCreatedListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		
		System.out.println("Inicio reproceso.ControlTotalTaskCreatedListener..................................");
		String REST_URIObtieneProcesoLocal = Constantes.URL_OBTIENE_LOCAL_REPRO_API;
		
		System.out.println(REST_URIObtieneProcesoLocal);
		Map<String, Object> variables = delegateTask.getVariables();
		//CtrlProcesoLocalResult pelement =(CtrlProcesoLocalResult) variables.get("element");
		LocalesReproceso pelement =(LocalesReproceso) variables.get("element");
		String codlocal = pelement.getCodlocal().toString();
		String fecproceso = pelement.getFecproceso().substring(0, 10);
		String dproceso = fecproceso.replace("-", "");
		Integer numlocal = Integer.parseInt(codlocal);
		String estado = "E";
		ReprocesarRest clienteprocesarRest = new ReprocesarRest();
		
		ListaLocalesReprocesoReq listaLocalesReprocesoReq = new ListaLocalesReprocesoReq();
		listaLocalesReprocesoReq.setDproceso(dproceso);
		listaLocalesReprocesoReq.setNumlocal(numlocal);
		listaLocalesReprocesoReq.setEstado(estado);
		listaLocalesReprocesoReq.setReproceso(1);
		Response response = clienteprocesarRest.ObtenerLocalReproceso(listaLocalesReprocesoReq, REST_URIObtieneProcesoLocal);		
		
		System.out.println("reproceso.ControlTotalTaskCreatedListener.execute: " + String.valueOf(response.getStatus()));
	
		//Map<String, String> variables = new HashMap<String, String>();
		CtrlProcesoLocal ctrlProcesoLocal = response.readEntity(CtrlProcesoLocal.class);
		
		if (ctrlProcesoLocal.getResult().size()>0)
		{
			CtrlProcesoLocalResult p = ctrlProcesoLocal.getResult().get(0);
			if (p!=null) {
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

				variables.put("codlocal", p.getCodlocal().toString());
				variables.put("sociedad", p.getSociedad().toString());
				variables.put("localdescripcion", p.getLocaldescripcion());
				variables.put("codproceso", p.getCodproceso().toString());
				variables.put("fecproceso", p.getFecproceso().substring(0, 10));
				variables.put("tipestado", p.getTipestado());
				variables.put("tipestadodescripcion", p.getTipestadodescripcion());
				variables.put("desobservacion", p.getDesobservacion());
				variables.put("fecinicio", p.getFecinicio());
				variables.put("fecfin", p.getFecfin());
				variables.put("codusuariocrea", p.getCodusuariocrea());
				variables.put("feccreacion", p.getFeccreacion());
				variables.put("fecmodificacion", p.getFecmodificacion());
				variables.put("codusuariomodificacion", p.getCodusuariomodificacion());
				
				delegateTask.removeVariablesLocal();
				delegateTask.setVariablesLocal(variables);
				
			}
		}
		
		System.out.println("fin reproceso.ControlTotalTaskCreatedListener..................................");
	
	}

}
