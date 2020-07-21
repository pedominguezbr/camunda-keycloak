package com.spsa.bpm.ventadesagregada.reproceso.listener;

import java.util.Map;

import javax.ws.rs.core.Response;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import com.spsa.bpm.ventadesagregada.clase.CodResultRes;
import com.spsa.bpm.ventadesagregada.clase.RegistraVentaSapLocalRes;
import com.spsa.bpm.ventadesagregada.rest.ReprocesarRest;
import com.spsa.bpm.ventadesagregada.rest.VentasDataFicoRest;
import com.spsa.bpm.ventadesagregada.util.Constantes;
import com.spsa.bpm.ventadesagregada.util.FuncionesGenerales;

public class ObSapTaskCompletedListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		System.out.println("Inicio reproceso.ObSapTaskCompletedListener..................................");
		
		ReprocesarRest client = new ReprocesarRest();
		Map<String, Object> getvariables = delegateTask.getVariables();			
		String REST_URIupdVentasRepro = Constantes.URL_UP_TOLE_VTA_FICO_REPRO_API; //String.format("http://%s/updtoleraventasdatafico",apiHost);
		String REST_URIRegistraVentaSapRepro = Constantes.URL_REG_VTA_SAP_REPROCESO_API; // String.format("http://%s/registraventasaplocal",apiHost);
		System.out.println(REST_URIupdVentasRepro);
		
		Map<String, Object> variablesLocal = delegateTask.getVariablesLocal();
		
		String codlocal = (String) variablesLocal.get("codlocal");
		String localdescripcion = (String) variablesLocal.get("localdescripcion");
		String sociedad = (String) variablesLocal.get("sociedad");
		String fecproceso = (String) variablesLocal.get("fecproceso");
		Integer correlativo = (Integer) variablesLocal.get("correlativo");
		String mensajesap = Constantes.EMPTY_STRING;
		
		Boolean upObservaSapOk=false;
		
		System.out.println(codlocal);
		System.out.println(localdescripcion);
		System.out.println(sociedad);
		System.out.println(fecproceso);
		System.out.println(correlativo);
		String dproceso = fecproceso.replace("-", "");
		Integer numlocal = Integer.parseInt(codlocal);
		
		
		//Actualizar Estado N a CT2
		Response responseupToleraVentaSap = client.UpdToleraVentasDataFicoRepro(dproceso, numlocal, correlativo,FuncionesGenerales.currentUser(), Constantes.ESTADO_REPRO, REST_URIupdVentasRepro);
		System.out.println("clientRegistrarVentaSap.UpdToleraVentasDataFico: "+ String.valueOf(responseupToleraVentaSap.getStatus()));
		//String body = responseVentaSap.readEntity(String.class);
		CodResultRes upToleraVentaFicoRes = responseupToleraVentaSap.readEntity(CodResultRes.class);
		
		System.out.println("upToleraVentaFicoRes.getCod_ret: "+ upToleraVentaFicoRes.getCod_ret() );
		System.out.println("upToleraVentaFicoRes.getResult: "+ upToleraVentaFicoRes.getResult() );

		//Si ejecuta ok la actualizacion se envia a sap
		if (upToleraVentaFicoRes.getCod_ret().contains("00"))
		{
			//upObservaSapOk=true;
			//Enviar a Sap
			System.out.println(REST_URIRegistraVentaSapRepro);
			Response responseVentaSap = client.RegistrarVentaSapReproceso(dproceso,numlocal, REST_URIRegistraVentaSapRepro);
			System.out.println("ReproLocalCargaTaskCompletedListener.RegistrarVentaSap: "+ String.valueOf(responseVentaSap.getStatus()));
			
			RegistraVentaSapLocalRes registraVentaSapLocalRes = responseVentaSap.readEntity(RegistraVentaSapLocalRes.class);
			
			System.out.println("RegistrarVentaSapRes.getCod_ret: "+ registraVentaSapLocalRes.getCod_ret() );
			System.out.println("RegistrarVentaSapRes.getResult.getEstadoSap: "+ registraVentaSapLocalRes.getResult().getEstadoSap() );
			System.out.println("RegistrarVentaSapRes.getResult.getMsgResSap: "+ registraVentaSapLocalRes.getResult().getMsgResSap() );
			mensajesap = registraVentaSapLocalRes.getResult().getMsgResSap();
			//Si se envia ok a Sap se marca como ok a 
			if (registraVentaSapLocalRes.getCod_ret().contains("00") && (registraVentaSapLocalRes.getResult().getEstadoSap().contains("I")  || registraVentaSapLocalRes.getResult().getEstadoSap().contains("NR") )) 
			{					
				upObservaSapOk = true;
			} else {
				upObservaSapOk = false;
				FuncionesGenerales.NotificaObservadoSap(dproceso,numlocal,Constantes.TITULO_CORREO_OBSERVADOSAP);
			}
		} else {
			upObservaSapOk = false;
		}
		
		//Eliminar Variable upObservaSapOk para actualizarlo
		delegateTask.removeVariable("upObservaSapOk");
		delegateTask.removeVariable("mensajesap");
		
		System.out.println("upObservaSapOk " + upObservaSapOk.toString());
		//delegateTask.setVariable("upObservaSapOk", upObservaSapOk);
		delegateTask.setVariable("upObservaSapOk", upObservaSapOk);
		delegateTask.setVariable("mensajesap", mensajesap);
		
		System.out.println("getAuthenticatedUserId: "+ FuncionesGenerales.currentUser());
		System.out.println("fin reproceso.ObSapTaskCompletedListener..................................");
	
	}

}
