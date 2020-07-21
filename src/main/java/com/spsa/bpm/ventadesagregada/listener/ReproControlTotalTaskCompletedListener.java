package com.spsa.bpm.ventadesagregada.listener;

import java.util.Map;


import javax.ws.rs.core.Response;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import com.spsa.bpm.ventadesagregada.clase.CodResultRes;
import com.spsa.bpm.ventadesagregada.clase.RegistraVentaSapLocalRes;
import com.spsa.bpm.ventadesagregada.rest.EjecutarCargaVentaRest;
import com.spsa.bpm.ventadesagregada.rest.VentasDataFicoRest;
import com.spsa.bpm.ventadesagregada.util.Constantes;
import com.spsa.bpm.ventadesagregada.util.FuncionesGenerales;


public class ReproControlTotalTaskCompletedListener implements TaskListener {
	
	
	@Override
	public void notify(DelegateTask delegateTask) {
		
		try {
			System.out.println("Inicio ReproControlTotalTaskCompletedListener..................................");
			EjecutarCargaVentaRest client = new EjecutarCargaVentaRest();
			VentasDataFicoRest clientRegistrarVentaSap = new VentasDataFicoRest();
			
			
			Boolean subSanadoOk=false;
			Boolean toleraIgv=false;
			Map<String, Object> getvariables = delegateTask.getVariables();
			//String apiHost = (String)getvariables.get("apiHost");
			String REST_URIEjecutaCargaVenta = Constantes.URL_EJECUTA_CARGA_VENTA_SUBSANAR_API; // String.format("http://%s/ejecutacargaventasubsanar",apiHost);
			//String REST_URIRegistraVentaSap = String.format("http://%s/registraventasap",apiHost);
			String REST_URIRegistraVentaSapLocal = Constantes.URL_REG_VTA_SAP_LOCAL_API; // String.format("http://%s/registraventasaplocal",apiHost);
			String mensajesap = Constantes.EMPTY_STRING;
			Boolean enviadoSapOk=false;
			
			System.out.println(REST_URIEjecutaCargaVenta);
			
			//String REST_URIEjecutaCargaVenta = "http://ws-rest-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/ejecutacargaventasubsanar";
			
			//Map<String, Object> variables = delegateTask.getVariables();
			
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
			
			//Random rando = new Random();
			//Boolean subSanadoOk = rando.nextBoolean();			
			
			String dproceso = fecproceso.replace("-", "");
			Integer numlocal = Integer.parseInt(codlocal);
			
			Response response = client.EjecutarCargaVenta(dproceso, numlocal, REST_URIEjecutaCargaVenta);
			
			CodResultRes ejecutaCargaVentaSubsanarRes = response.readEntity(CodResultRes.class);
			
			System.out.println("getStatus: "+ String.valueOf(response.getStatus()) );
			
			System.out.println("getCod_ret: "+ ejecutaCargaVentaSubsanarRes.getCod_ret() );
			System.out.println("getResult: "+ ejecutaCargaVentaSubsanarRes.getResult() );
			
			if (ejecutaCargaVentaSubsanarRes.getCod_ret().contains("00") || ejecutaCargaVentaSubsanarRes.getCod_ret().contains("-3"))
			{
				subSanadoOk=true;
				toleraIgv =true;
				//Ejecutar Envio de Datos procesados a Sap
				System.out.println(REST_URIRegistraVentaSapLocal);
				Response responseVentaSap = clientRegistrarVentaSap.RegistrarVentaSapLocal(dproceso,numlocal, REST_URIRegistraVentaSapLocal);
				System.out.println("ReproLocalCargaTaskCompletedListener.RegistrarVentaSap: "+ String.valueOf(responseVentaSap.getStatus()));
				
				RegistraVentaSapLocalRes registraVentaSapLocalRes = responseVentaSap.readEntity(RegistraVentaSapLocalRes.class);
				
				System.out.println("RegistrarVentaSapRes.getCod_ret: "+ registraVentaSapLocalRes.getCod_ret() );
				System.out.println("RegistrarVentaSapRes.getResult.getEstadoSap: "+ registraVentaSapLocalRes.getResult().getEstadoSap() );
				System.out.println("RegistrarVentaSapRes.getResult.getMsgResSap: "+ registraVentaSapLocalRes.getResult().getMsgResSap() );
				mensajesap = registraVentaSapLocalRes.getResult().getMsgResSap();
				//Si se envia ok a Sap se marca como ok a 
				if (registraVentaSapLocalRes.getCod_ret().contains("00") && (registraVentaSapLocalRes.getResult().getEstadoSap().contains("I")  || registraVentaSapLocalRes.getResult().getEstadoSap().contains("NR") ))			
				{	
					enviadoSapOk = true;
				}
				else {
					enviadoSapOk = false;
					FuncionesGenerales.NotificaObservadoSap(dproceso,numlocal,Constantes.TITULO_CORREO_OBSERVADOSAP);
				}				
			}else if (ejecutaCargaVentaSubsanarRes.getCod_ret().contains("-5")) //Error pasa a Control Tolerancia IGV
			{
				subSanadoOk=true;
				toleraIgv=false;
				FuncionesGenerales.NotificaToleranciaIgv(dproceso,numlocal,Constantes.TITULO_CORREO_TOLERANCIAIGV);
			}
			else
			{
				//ejecutaCargaVentaSubsanarRes.getCod_ret().contains("-4") -- se mantiene en error Control total
				subSanadoOk=false;
				FuncionesGenerales.NotificaControlTotales(dproceso,numlocal,Constantes.TITULO_CORREO_CONTROLTOTALES);
			}
			
			//delegateTask.removeVariables();
			delegateTask.removeVariable("subSanadoOk");
			delegateTask.removeVariable("enviadoSapOk");
			delegateTask.removeVariable("toleraIgv");
			delegateTask.removeVariable("mensajesap");
			
			//subSanadoOk = rando.nextBoolean();
			System.out.println("subSanadoOk " + subSanadoOk.toString());
			System.out.println("enviadoSapOk " + enviadoSapOk.toString());
			System.out.println("toleraIgv " + toleraIgv.toString());
			//delegateTask.setVariable("subSanadoOk", subSanadoOk);			
			
			delegateTask.setVariable("subSanadoOk", subSanadoOk);
			delegateTask.setVariable("enviadoSapOk", enviadoSapOk);
			delegateTask.setVariable("toleraIgv", toleraIgv);
			delegateTask.setVariable("mensajesap", mensajesap);
			
			System.out.println("getAuthenticatedUserId: "+ FuncionesGenerales.currentUser());
			
			System.out.println("Fin ReproControlTotalTaskCompletedListener..................................");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
