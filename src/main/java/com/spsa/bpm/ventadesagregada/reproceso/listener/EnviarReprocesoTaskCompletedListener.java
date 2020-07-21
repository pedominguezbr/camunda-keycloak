package com.spsa.bpm.ventadesagregada.reproceso.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.core.Response;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import com.spsa.bpm.ventadesagregada.clase.CodResultRes;
import com.spsa.bpm.ventadesagregada.clase.RegistraVentaSapLocalRes;
import com.spsa.bpm.ventadesagregada.rest.ReprocesarRest;
import com.spsa.bpm.ventadesagregada.rest.VentasDataFicoRest;
import com.spsa.bpm.ventadesagregada.util.Constantes;
import com.spsa.bpm.ventadesagregada.util.FuncionesGenerales;

public class EnviarReprocesoTaskCompletedListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		System.out.println("Inicio reproceso.EnviarReprocesoTaskCompletedListener..................................");
		/*
		Random rando = new Random();
		Boolean controlTotalesRepro = rando.nextBoolean();
		Boolean toleranciaIgvRepro = rando.nextBoolean();
		Boolean enviadoSapRepro = rando.nextBoolean();
		
		Map<String, Object> vars = new HashMap<>();
		
	    vars.put("controlTotalesRepro", controlTotalesRepro);
	    vars.put("toleranciaIgvRepro", toleranciaIgvRepro);
	    vars.put("enviadoSapRepro", enviadoSapRepro);
	    
	    delegateTask.setVariables(vars);
	    */
		//////------------------------------------------------------------------------------
		try {
	    ReprocesarRest client = new ReprocesarRest();
		//VentasDataFicoRest clientRegistrarVentaSap = new VentasDataFicoRest();	
		
		Boolean controlTotalesRepro=false;
		Boolean toleranciaIgvRepro=false;
		Boolean enviadoSapRepro=false;
		Boolean ejecutaRepro=false;

		String REST_URIEjecutaReproceso = Constantes.URL_EJECUTA_REPROCESO_API;		
		String REST_URIRegistraVentaSapRepro = Constantes.URL_REG_VTA_SAP_REPROCESO_API;
		String mensajesap = Constantes.EMPTY_STRING;
		
		System.out.println(REST_URIEjecutaReproceso);
		
		Map<String, Object> variablesLocal = delegateTask.getVariablesLocal();
		
		String codlocal = (String) variablesLocal.get("codlocal");
		String localdescripcion = (String) variablesLocal.get("localdescripcion");
		String sociedad = (String) variablesLocal.get("sociedad");
		String fecproceso = (String) variablesLocal.get("fecproceso");
		String tipestado = (String) variablesLocal.get("tipestado");
		System.out.println(codlocal);
		System.out.println(localdescripcion);
		System.out.println(sociedad);
		System.out.println(fecproceso);
		System.out.println(tipestado);
		
		String dproceso = fecproceso.replace("-", "");
		Integer numlocal = Integer.parseInt(codlocal);
		
		Response response = client.EjecutarReproceso(dproceso, numlocal, REST_URIEjecutaReproceso);
		
		CodResultRes ejecutaReprocesoRes = response.readEntity(CodResultRes.class);
		
		System.out.println("getStatus: "+ String.valueOf(response.getStatus()) );
		
		System.out.println("getCod_ret: "+ ejecutaReprocesoRes.getCod_ret() );
		System.out.println("getResult: "+ ejecutaReprocesoRes.getResult() );
		
		if (ejecutaReprocesoRes.getCod_ret().contains("00") || ejecutaReprocesoRes.getCod_ret().contains("-3"))
		{
			controlTotalesRepro=true;
			toleranciaIgvRepro =true;
			ejecutaRepro=true;
			//Ejecutar Envio de Datos procesados a Sap
			System.out.println(REST_URIRegistraVentaSapRepro);
			Response responseVentaSap = client.RegistrarVentaSapReproceso(dproceso, numlocal, REST_URIRegistraVentaSapRepro);
			System.out.println("EnviarReprocesoTaskCompletedListener.RegistrarVentaSap: "+ String.valueOf(responseVentaSap.getStatus()));
			
			RegistraVentaSapLocalRes registraVentaSapLocalRes = responseVentaSap.readEntity(RegistraVentaSapLocalRes.class);
			
			System.out.println("RegistrarVentaSapRes.getCod_ret: "+ registraVentaSapLocalRes.getCod_ret() );
			System.out.println("RegistrarVentaSapRes.getResult.getEstadoSap: "+ registraVentaSapLocalRes.getResult().getEstadoSap() );
			System.out.println("RegistrarVentaSapRes.getResult.getMsgResSap: "+ registraVentaSapLocalRes.getResult().getMsgResSap() );
						
			//Si se envia ok a Sap se marca como ok a 
			if (registraVentaSapLocalRes.getCod_ret().contains("00") && (registraVentaSapLocalRes.getResult().getEstadoSap().contains("I")  || registraVentaSapLocalRes.getResult().getEstadoSap().contains("NR") ))			
			{	
				enviadoSapRepro = true;
			}
			else {
				enviadoSapRepro = false;
				FuncionesGenerales.NotificaObservadoSap(dproceso,numlocal,Constantes.TITULO_CORREO_OBSERVADOSAP);
			}				
		}else if (ejecutaReprocesoRes.getCod_ret().contains("-5")) //Error pasa a Control Tolerancia IGV
		{
			controlTotalesRepro=true;
			ejecutaRepro=true;
			toleranciaIgvRepro=false;
			FuncionesGenerales.NotificaToleranciaIgv(dproceso,numlocal,Constantes.TITULO_CORREO_TOLERANCIAIGV);
		}
		else if (ejecutaReprocesoRes.getCod_ret().contains("-4"))
		{
			//ejecutaCargaVentaSubsanarRes.getCod_ret().contains("-4") -- se mantiene en error Control total
			controlTotalesRepro=false;
			ejecutaRepro=true;
			FuncionesGenerales.NotificaControlTotales(dproceso,numlocal,Constantes.TITULO_CORREO_CONTROLTOTALES);
		}else
		{
			//Otro tipo de error
			ejecutaRepro=false;
			throw new Exception(ejecutaReprocesoRes.getCod_ret() + " "+ ejecutaReprocesoRes.getResult());			
		}

		delegateTask.removeVariable("controlTotalesRepro");
		delegateTask.removeVariable("enviadoSapRepro");
		delegateTask.removeVariable("toleranciaIgvRepro");
		delegateTask.removeVariable("ejecutaRepro");
		delegateTask.removeVariable("mensajesap");
		
		System.out.println("controlTotalesRepro " + controlTotalesRepro.toString());
		System.out.println("enviadoSapRepro " + enviadoSapRepro.toString());
		System.out.println("toleranciaIgvRepro " + toleranciaIgvRepro.toString());
		System.out.println("ejecutaRepro " + ejecutaRepro.toString());
		
		delegateTask.setVariable("controlTotalesRepro", controlTotalesRepro);
		delegateTask.setVariable("enviadoSapRepro", enviadoSapRepro);
		delegateTask.setVariable("toleranciaIgvRepro", toleranciaIgvRepro);
		delegateTask.setVariable("ejecutaRepro", ejecutaRepro);
		delegateTask.setVariable("mensajesap", mensajesap);
		
		System.out.println("getAuthenticatedUserId: "+ FuncionesGenerales.currentUser());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Fin reproceso.EnviarReprocesoTaskCompletedListener..................................");
	
	}

}
