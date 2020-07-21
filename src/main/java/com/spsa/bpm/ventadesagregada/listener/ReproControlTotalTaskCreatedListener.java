package com.spsa.bpm.ventadesagregada.listener;


import java.util.Map;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import com.spsa.bpm.ventadesagregada.clase.CtrlProcesoLocalResult;

public class ReproControlTotalTaskCreatedListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		try {
			System.out.println("Inicio ReproControlTotalTaskCreatedListener..................................");
			
			Map<String, Object> variables = delegateTask.getVariables();
			
			CtrlProcesoLocalResult p =(CtrlProcesoLocalResult) variables.get("element");
			
			System.out.println(p.getCodproceso());
			System.out.println(p.getCodlocal());
			System.out.println(p.getLocaldescripcion());
			System.out.println(p.getSociedad());			
			System.out.println(p.getFecproceso().substring(0, 10));
			System.out.println(p.getTipestado());
			System.out.println(p.getDesobservacion());
			System.out.println(p.getFecinicio());
			System.out.println(p.getFecfin());
			System.out.println(p.getCodusuariocrea());
			System.out.println(p.getFeccreacion());
			System.out.println(p.getFecmodificacion());
			System.out.println(p.getCodusuariomodificacion());
			
			
			variables.put("codproceso", p.getCodproceso().toString());
			variables.put("codlocal", p.getCodlocal().toString());
			variables.put("localdescripcion",p.getLocaldescripcion().toString());
			variables.put("sociedad", p.getSociedad().toString());			
			variables.put("fecproceso", p.getFecproceso().substring(0, 10));
			variables.put("tipestado", p.getTipestado());
			variables.put("tipestadodescripcion", p.getTipestadodescripcion());
			variables.put("desobservacion", p.getDesobservacion());
			variables.put("fecinicio", p.getFecinicio());
			variables.put("fecfin", p.getFecfin());
			variables.put("feccreacion", p.getFeccreacion());
			variables.put("codusuariocrea", p.getCodusuariocrea());			
			variables.put("fecmodificacion", p.getFecmodificacion());
			variables.put("codusuariomodificacion", p.getCodusuariomodificacion());
			
			//delegateTask.setVariables(variables);
			delegateTask.setVariablesLocal(variables);
			System.out.println("Fin ReproControlTotalTaskCreatedListener..................................");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
