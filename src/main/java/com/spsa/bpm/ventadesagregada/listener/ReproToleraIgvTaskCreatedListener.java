package com.spsa.bpm.ventadesagregada.listener;

import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import com.spsa.bpm.ventadesagregada.clase.ObtieneVentasDataFicoResult;
import com.spsa.bpm.ventadesagregada.util.Constantes;

public class ReproToleraIgvTaskCreatedListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		System.out.println("Inicio ReproToleraIgvTaskCreatedListener..................................");
		
		Map<String, Object> variables = delegateTask.getVariables();
		
		ObtieneVentasDataFicoResult p = (ObtieneVentasDataFicoResult) variables.get("element");
		
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
		
		delegateTask.setVariablesLocal(variables);
		
		System.out.println("Fin ReproToleraIgvTaskCreatedListener..................................");		
	}

}
