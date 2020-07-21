package com.spsa.bpm.ventadesagregada.listener;

import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import com.spsa.bpm.ventadesagregada.clase.ObtieneVentasDataFicoResult;
import com.spsa.bpm.ventadesagregada.util.Constantes;

public class ReproObsSapTaskCreatedListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
System.out.println("Inicio ReproObsSapTaskCreatedListener..................................");
		
		Map<String, Object> variables = delegateTask.getVariables();
		String mensajesap = Constantes.EMPTY_STRING;
		
		ObtieneVentasDataFicoResult p = (ObtieneVentasDataFicoResult) variables.get("element");
		
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
		
		System.out.println("Fin ReproObsSapTaskCreatedListener..................................");
	}

}
