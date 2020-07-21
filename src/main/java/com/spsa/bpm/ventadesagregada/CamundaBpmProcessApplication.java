package com.spsa.bpm.ventadesagregada;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.engine.ProcessEngine;



/**
 * Process Application exposing this application's resources the process engine. 
 */
@ProcessApplication
public class CamundaBpmProcessApplication extends ServletProcessApplication {

  private static final String PROCESS_DEFINITION_KEY = "ventadesagregada";

  /**
   * In a @PostDeploy Hook you can interact with the process engine and access 
   * the processes the application has deployed. 
   */
  @PostDeploy
  public void onDeploymentFinished(ProcessEngine processEngine) {

    // start an initial process instance
//    Map<String, Object> variables = new HashMap<String, Object>();
//    variables.put("name", "John");
//    
//    processEngine.getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY, variables);
  }
	protected void addVariables(Map<String, Object> filterProperties) {
		//List<Object> variables = new ArrayList<Object>();

		//addVariable(variables, "local", "Local");
		//addVariable(variables, "fechaproceso", "Fecha");

		//filterProperties.put("variables", variables);
	}
	
	protected void addVariable(List<Object> variables, String name, String label) {
		Map<String, String> variable = new HashMap<String, String>();
		variable.put("name", name);
		variable.put("label", label);
		variables.add(variable);
	}

}
