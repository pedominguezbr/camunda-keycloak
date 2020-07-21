package com.spsa.bpm.ventadesagregada.reproceso.listener;

import java.util.Map;
import java.util.Random;

import javax.ws.rs.core.Response;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import com.spsa.bpm.ventadesagregada.clase.CodResultRes;
import com.spsa.bpm.ventadesagregada.clase.RegistraVentaSapLocalRes;
import com.spsa.bpm.ventadesagregada.rest.EjecutarCargaVentaRest;
import com.spsa.bpm.ventadesagregada.rest.ReprocesarRest;
import com.spsa.bpm.ventadesagregada.rest.VentasDataFicoRest;
import com.spsa.bpm.ventadesagregada.util.Constantes;
import com.spsa.bpm.ventadesagregada.util.FuncionesGenerales;

public class ControlTotalTaskCompletedListener implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		System.out.println("Inicio ControlTotalTaskCompletedListener..................................");
		
		/*
		Random rando = new Random();
		Boolean subSanadoOk = rando.nextBoolean();
		Boolean toleraIgv = rando.nextBoolean();
		Boolean enviadoSapOk = rando.nextBoolean();
		
		System.out.println("subSanadoOk " + subSanadoOk.toString());
		System.out.println("toleraIgv " + toleraIgv.toString());
		System.out.println("enviadoSapOk " + enviadoSapOk.toString());
		
		delegateTask.setVariable("toleraIgv", toleraIgv);
		delegateTask.setVariable("subSanadoOk", subSanadoOk);
		delegateTask.setVariable("enviadoSapOk", enviadoSapOk);
		
		*/
		
		//==========================================================
		ReprocesarRest client = new ReprocesarRest();
		//EjecutarCargaVentaRest client = new EjecutarCargaVentaRest();
		//VentasDataFicoRest clientRegistrarVentaSap = new VentasDataFicoRest();	
		
		Boolean subSanadoOk=false;
		Boolean toleraIgv=false;
		Boolean enviadoSapOk=false;

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
		
		//Response response = client.EjecutarCargaVenta(dproceso, numlocal, REST_URIEjecutaCargaVenta);
		Response response = client.EjecutarReproceso(dproceso, numlocal, REST_URIEjecutaReproceso);
		
		CodResultRes ejecutaCargaVentaSubsanarRes = response.readEntity(CodResultRes.class);
		
		System.out.println("getStatus: "+ String.valueOf(response.getStatus()) );
		
		System.out.println("getCod_ret: "+ ejecutaCargaVentaSubsanarRes.getCod_ret() );
		System.out.println("getResult: "+ ejecutaCargaVentaSubsanarRes.getResult() );
		
		if (ejecutaCargaVentaSubsanarRes.getCod_ret().contains("00") || ejecutaCargaVentaSubsanarRes.getCod_ret().contains("-3"))
		{
			subSanadoOk=true;
			toleraIgv =true;
			//Ejecutar Envio de Datos procesados a Sap
			System.out.println(REST_URIRegistraVentaSapRepro);
			Response responseVentaSap = client.RegistrarVentaSapReproceso(dproceso,numlocal, REST_URIRegistraVentaSapRepro);
			System.out.println("ReproLocalCargaTaskCompletedListener.RegistrarVentaSap: "+ String.valueOf(responseVentaSap.getStatus()));
			
			RegistraVentaSapLocalRes registraVentaSapLocalRes = responseVentaSap.readEntity(RegistraVentaSapLocalRes.class);
			
			System.out.println("RegistrarVentaSapRes.getCod_ret: "+ registraVentaSapLocalRes.getCod_ret() );
			System.out.println("RegistrarVentaSapRes.getResult.getEstadoSap: "+ registraVentaSapLocalRes.getResult().getEstadoSap() );
			System.out.println("RegistrarVentaSapRes.getResult.getMsgResSap: "+ registraVentaSapLocalRes.getResult().getMsgResSap() );
						
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

		delegateTask.removeVariable("subSanadoOk");
		delegateTask.removeVariable("enviadoSapOk");
		delegateTask.removeVariable("toleraIgv");
		delegateTask.removeVariable("mensajesap");
		
		System.out.println("subSanadoOk " + subSanadoOk.toString());
		System.out.println("enviadoSapOk " + enviadoSapOk.toString());
		System.out.println("toleraIgv " + toleraIgv.toString());
		
		delegateTask.setVariable("subSanadoOk", subSanadoOk);
		delegateTask.setVariable("enviadoSapOk", enviadoSapOk);
		delegateTask.setVariable("toleraIgv", toleraIgv);
		delegateTask.setVariable("mensajesap", mensajesap);
		
		System.out.println("getAuthenticatedUserId: "+ FuncionesGenerales.currentUser());
		System.out.println("fin ControlTotalTaskCompletedListener..................................");
		
	}

}
