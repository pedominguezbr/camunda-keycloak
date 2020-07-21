package com.spsa.bpm.ventadesagregada.reproceso.delegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Response;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.spsa.bpm.ventadesagregada.clase.ListaLocalesReprocesoReq;
import com.spsa.bpm.ventadesagregada.clase.ListaLocalesReprocesoRes;
import com.spsa.bpm.ventadesagregada.clase.LocalesReproceso;

import com.spsa.bpm.ventadesagregada.rest.ReprocesarRest;
import com.spsa.bpm.ventadesagregada.util.Constantes;
import com.spsa.bpm.ventadesagregada.util.FuncionesGenerales;

public class NotificaReprocesoDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {		
		System.out.println("Inicio NotificaReprocesoDelegate..................................");
		Map<String, Object> vars = new HashMap<>();
		Map<String, Object> variables = execution.getVariables();
		String REST_URIObtieneProcesoLocal = Constantes.URL_OBTIENE_LOCAL_REPRO_API;
		
		System.out.println(REST_URIObtieneProcesoLocal);
		
		Boolean existenlocalesRepro = false;//rando.nextBoolean();		
		
		ReprocesarRest clienteprocesarRest = new ReprocesarRest();
		String dproceso = (String)variables.get("fecproceso");		
		if (dproceso == null) dproceso = Constantes.EMPTY_STRING;

		Integer numlocal = 99999;
		String estado = "T";
		
		ListaLocalesReprocesoReq listaLocalesReprocesoReq = new ListaLocalesReprocesoReq();
		listaLocalesReprocesoReq.setDproceso(dproceso);
		listaLocalesReprocesoReq.setNumlocal(numlocal);
		listaLocalesReprocesoReq.setEstado(estado);
		listaLocalesReprocesoReq.setReproceso(1);
		Response response = clienteprocesarRest.ObtenerLocalReproceso(listaLocalesReprocesoReq, REST_URIObtieneProcesoLocal);
		
		System.out.println("NotificaReprocesoDelegate.execute: " + String.valueOf(response.getStatus()));
	
		//Map<String, String> variables = new HashMap<String, String>();
		ListaLocalesReprocesoRes listaLocalesReprocesoRes = response.readEntity(ListaLocalesReprocesoRes.class);
		
		//Enviar Correo Notificacion		
		existenlocalesRepro = FuncionesGenerales.EnviarCorreoNotificaReproceso(Constantes.TITULO_CORREO_REPROCESO, listaLocalesReprocesoRes);
				
		for (LocalesReproceso p : listaLocalesReprocesoRes.getResult()) {
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
			
			//existenlocalesRepro = true;
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
	    
		
		//Obtener Solo locales que no existen en otras instancias y generar los nuevos formularios.
		ArrayList<LocalesReproceso> listaLocalesInstance = FuncionesGenerales.ObtenerLocalesReprocesoSinDuplicar(listaLocalesReprocesoRes.getResult(),execution.getProcessEngineServices().getRuntimeService());
		
		//Para no enviar al flujo de instancias de reproceso si no hay locales nuevos
		if (listaLocalesInstance.size() == 0)
		{
			existenlocalesRepro = false;			
		}
		
	    vars.put("elements", listaLocalesInstance);
	    execution.setVariables(vars);
	    
	    //ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("simple_multi", vars);		
		//aqui quedo
		
		//vars.put("elements", ctrlProcesoLocal.getResult());
	    System.out.println("existenlocalesRepro " + existenlocalesRepro.toString());
	    execution.setVariable("existenlocalesRepro", existenlocalesRepro);
	    
		System.out.println("Fin NotificaReprocesoDelegate..................................");
		
		
		
		
		/*
		System.out.println("+++++++++++++++++++++++++++++++Inicio processInstancesList+++++++++++++++++++++++++++++++");
		
		RuntimeService	rs = execution.getProcessEngineServices().getRuntimeService();
		List<ProcessInstance> processInstancesList = rs.createProcessInstanceQuery().active().processDefinitionKey("reprocesoventadesagregada").list();

		for (ProcessInstance pInstance : processInstancesList) {
			
			System.out.println("++++++++++getProcessInstanceId: " + pInstance.getId());
			VariableInstanceQuery listvarInstance = rs.createVariableInstanceQuery().activityInstanceIdIn(pInstance.getId()).variableName("elements");
			for (VariableInstance variableinstance : listvarInstance.list()) {
				System.out.println(variableinstance);
				ArrayList<LocalesReproceso> listaLocalesInstance = (ArrayList<LocalesReproceso>) variableinstance.getValue();
				
				for (LocalesReproceso p : listaLocalesInstance) {
					System.out.println("=======================Proceso TEST====================");
					System.out.println(p.getCodlocal());
					System.out.println(p.getSociedad());								
					System.out.println(p.getFecproceso().substring(0, 10));					
				}
			}
		}
		
		System.out.println("+++++++++++++++++++++++++++++++Fin processInstancesList+++++++++++++++++++++++++++++++");
		 */
		
		/*
		var rs = execution.processEngineServices.runtimeService
		var processInstancesList = rs.createProcessInstanceQuery().active().processDefinitionKey(“ApplicationApprove”).list()
		var applications = ArrayList()
		processInstancesList.forEach() {
		var amountFromProcess = rs.createVariableInstanceQuery().activityInstanceIdIn(it.id).variableName(“Amount”).singleResult().value as Long
		var idFromProcess = rs.createVariableInstanceQuery().activityInstanceIdIn(it.id).variableName(“ID”).singleResult().value as Long
		var descriptionFromProcess = rs.createVariableInstanceQuery().activityInstanceIdIn(it.id).variableName(“Description”).singleResult().value as String
		var processId = it.id
		*/
	}

}
