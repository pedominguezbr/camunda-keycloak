package com.spsa.bpm.ventadesagregada.reproceso.delegate;


import java.util.Map;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.spsa.bpm.ventadesagregada.clase.LocalesReproceso;

public class ValidarRechazadoTolerancia implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Inicio ValidarRechazadoTolerancia..................................");
		Map<String, Object> variables = execution.getVariables();
		//Map<String, Object> variables = new HashMap<>();
		LocalesReproceso p =(LocalesReproceso) variables.get("element");
		Boolean rechazadoTolerancia = false;
		
		System.out.println(p.getCodproceso());
		System.out.println(p.getCodlocal());
		System.out.println(p.getLocaldescripcion());
		System.out.println(p.getFecproceso());
		System.out.println(p.getTipestado());
		
		if (p.getTipestado().equalsIgnoreCase("E"))
		{
			rechazadoTolerancia = true;
			System.out.println("es rechazado tolerancia " + rechazadoTolerancia.toString());
		}
		
		execution.removeVariable("rechazadoTolerancia");		
		System.out.println("rechazadoTolerancia " + rechazadoTolerancia.toString());
		
		execution.setVariable("rechazadoTolerancia", rechazadoTolerancia);
		System.out.println("Fin ValidarRechazadoTolerancia..................................");
		
	}

}
