package com.spsa.bpm.ventadesagregada;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;


import com.spsa.bpm.ventadesagregada.clase.CtrlProcesoLocal;
import com.spsa.bpm.ventadesagregada.clase.CtrlProcesoLocalResult;
import com.spsa.bpm.ventadesagregada.rest.ObtieneProcesoLocalRest;
import com.spsa.bpm.ventadesagregada.util.FuncionesGenerales;

public class PruebaNotificacion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Inicio ReproLocalCargaTaskCreatedListener..................................");

		// Map<String, Object> variables = delegateTask.getVariables();
		ObtieneProcesoLocalRest clientObtieneProcesoLocal = new ObtieneProcesoLocalRest();
		Boolean hayObservadosCT = false;
		
		String REST_URIObtieneProcesoLocal = "http://ws-rest-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/obtieneprocesolocal";

		String dproceso = "20200316";
		//Integer numlocal = 195;
		 Integer numlocal = 99999;
		 String estado = "T";
		 
		Response response = clientObtieneProcesoLocal.ObtieneProcesoLocal(dproceso, numlocal, estado,
				REST_URIObtieneProcesoLocal);
		System.out.println("ReproLocalCargaTaskCreatedListener.notify" + String.valueOf(response.getStatus()));
		System.out.println("Fin ReproLocalCargaTaskCreatedListener..................................");

		Map<String, String> variable = new HashMap<String, String>();
		CtrlProcesoLocal ctrlProcesoLocal = response.readEntity(CtrlProcesoLocal.class);
	
		String REST_URIEnvioCorreo = "https://ws-envio-correo-qa.spsa-api.lblapiqa.spsa.xyz/send-mail";

		String TituloCorreo = "[SPSA] Venta desagregada - Error en Totales";
		String idIntegracion = "I0436-CAMUNDA-VTA-DESAGREGADA-ALERTA";
		StringBuilder resultMessage = new StringBuilder();
		resultMessage.append("<style type=\"text/css\">\r\n" + 
				"table {\r\n" + 
				"  max-width: 100%;\r\n" + 
				"  background-color: transparent;\r\n" + 
				"}\r\n" + 
				"th {\r\n" + 
				"  text-align: left;\r\n" + 
				"}\r\n" + 
				".table {\r\n" + 
				"  width: 100%;\r\n" + 
				"  margin-bottom: 20px;\r\n" + 
				"}\r\n" + 
				".table > thead > tr > th,\r\n" + 
				".table > tbody > tr > th,\r\n" + 
				".table > tfoot > tr > th,\r\n" + 
				".table > thead > tr > td,\r\n" + 
				".table > tbody > tr > td,\r\n" + 
				".table > tfoot > tr > td {\r\n" + 
				"  padding: 4px;\r\n" + 
				"  line-height: 1.42857143;\r\n" + 
				"  vertical-align: top;\r\n" + 
				"  border-top: 1px solid #dddddd;\r\n" + 
				"}\r\n" + 
				"</style>");
		if (ctrlProcesoLocal.getResult().size()>0)
		{
			hayObservadosCT = true;
			resultMessage.append("Se informa a los interesados los errores reportados por el proceso de Venta Desagregada con fecha " + ctrlProcesoLocal.getResult().get(0).getFecproceso().substring(0, 10) +":<p>");
		}
		resultMessage.append("<table class=\"table\"><caption>Errores en validación de totales</caption><tbody>");
		resultMessage.append("<tr><th>Fecha Proceso</th><th>Sociedad</th><th>Cod Local</th><th>Desc Local</th><th>Tipo Estado</th><th>Des_observación</th></tr>");
		
		
		
		for (CtrlProcesoLocalResult p : ctrlProcesoLocal.getResult()) {
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

			variable.put("codlocal", p.getCodlocal().toString());
			variable.put("codproceso", p.getCodproceso().toString());
			variable.put("fecproceso", p.getFecproceso());
			variable.put("tipestado", p.getTipestado());
			variable.put("desobservacion", p.getDesobservacion());
			variable.put("fecinicio", p.getFecinicio());
			variable.put("fecfin", p.getFecfin());
			variable.put("codusuariocrea", p.getCodusuariocrea());
			variable.put("feccreacion", p.getFeccreacion());
			variable.put("fecmodificacion", p.getFecmodificacion());
			variable.put("codusuariomodificacion", p.getCodusuariomodificacion());
			
			resultMessage.append("<tr><td>"+p.getFecproceso().substring(0,10) +"</td><td>"+p.getSociedad()+"</td><td>"+ p.getCodlocal()+"</td><td>"+p.getLocaldescripcion() +"</td><td>"+p.getTipestado()+"</td><td>" + observacion+ "</td></tr>");
			

		}
		
		resultMessage.append("</tbody></table>");
		//FuncionesGenerales.SendMail(idIntegracion, "SP",TituloCorreo, resultMessage.toString(), "TMPL0001",REST_URIEnvioCorreo);
		
		
		System.out.println(resultMessage.toString());
		
		// CtrlProcesoLocal ctrlProcesoLocal = response.readEntity(new
		// GenericType<CtrlProcesoLocal>() { });

		// Map<String,Object> temp = response.readEntity(new GenericType<HashMap<String,
		// Object>>() { });
		// String body = response.readEntity(String.class);

		// System.out.println(body);
		//long someLong = 1586198021000L;
		//Timestamp FECHA =  new Timestamp(someLong);
		//System.out.println("fecha." + FECHA.toString());
		
	}

}
