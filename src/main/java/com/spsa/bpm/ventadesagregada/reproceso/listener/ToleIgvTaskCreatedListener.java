package com.spsa.bpm.ventadesagregada.reproceso.listener;

import java.util.Map;

import javax.ws.rs.core.Response;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import com.spsa.bpm.ventadesagregada.clase.CtrlProcesoLocalResult;
import com.spsa.bpm.ventadesagregada.clase.LocalesReproceso;
import com.spsa.bpm.ventadesagregada.clase.ObtieneVentasDataFico;
import com.spsa.bpm.ventadesagregada.clase.ObtieneVentasDataFicoResult;
import com.spsa.bpm.ventadesagregada.rest.VentasDataFicoRest;
import com.spsa.bpm.ventadesagregada.util.Constantes;

public class ToleIgvTaskCreatedListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		
		System.out.println("Inicio ToleIgvTaskCreatedListener..................................");
		
		Map<String, Object> variables = delegateTask.getVariables();
		VentasDataFicoRest clientObtieneVentasDataFico = new VentasDataFicoRest();		
		
		LocalesReproceso pelement =(LocalesReproceso) variables.get("element");
		
		String codlocal = pelement.getCodlocal().toString();
		String fecproceso = pelement.getFecproceso().substring(0, 10);
		String dproceso = fecproceso.replace("-", "");
		Integer numlocal = Integer.parseInt(codlocal);
		String estado = "E";
		
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
				System.out.println(p.getTotalvta().toString());
				System.out.println(p.getIgvinformado().toString());
				System.out.println(p.getIgvcalculado().toString());
				System.out.println(p.getIgvdiferencia().toString());
				System.out.println(p.getTolerancia());
				
				variables.put("correlativo", p.getCorrelativo());
				variables.put("codlocal", p.getCodlocal().toString());
				variables.put("localdescripcion",p.getLocaldescripcion().toString());
				variables.put("sociedad", p.getSociedad().toString());			
				variables.put("fecproceso", p.getFecproceso().substring(0, 10));
				variables.put("totalvta", String.format(Constantes.FORMAT_DOUBLE, p.getTotalvta()));
				variables.put("igvinformado", String.format(Constantes.FORMAT_DOUBLE, p.getIgvinformado()));
				variables.put("igvcalculado", String.format(Constantes.FORMAT_DOUBLE, p.getIgvcalculado()));
				variables.put("igvdiferencia", String.format(Constantes.FORMAT_DOUBLE, p.getIgvdiferencia()));
				variables.put("estado", p.getEstado());
				variables.put("tolerancia", p.getTolerancia());
				
				delegateTask.removeVariablesLocal();
				delegateTask.setVariablesLocal(variables);				
			}
		}
		
		System.out.println("fin ToleIgvTaskCreatedListener..................................");

	}

}
