package com.spsa.bpm.ventadesagregada;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import com.spsa.bpm.ventadesagregada.clase.CtrlProcesoLocal;
import com.spsa.bpm.ventadesagregada.clase.CtrlProcesoLocalResult;
import com.spsa.bpm.ventadesagregada.clase.ObtieneVentasDataFico;
import com.spsa.bpm.ventadesagregada.clase.ObtieneVentasDataFicoResult;
import com.spsa.bpm.ventadesagregada.rest.ObtieneProcesoLocalRest;
import com.spsa.bpm.ventadesagregada.rest.VentasDataFicoRest;
import com.spsa.bpm.ventadesagregada.util.FuncionesGenerales;

public class PruebaNotifiCt2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String REST_URIObtieneVentasFico = "http://ws-rest-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/obtieneventasdatafico";
		String REST_URIEnvioCorreo = "https://ws-envio-correo-qa.spsa-api.lblapiqa.spsa.xyz/send-mail";
		String TituloCorreo = "[SPSA] Venta desagregada - Error en Tolerancia IGV";
		String idIntegracion = "I0436-CAMUNDA-VTA-DESAGREGADA-ALERTA";
		StringBuilder resultMessage = new StringBuilder();
		Map<String, String> variable = new HashMap<String, String>();
		Boolean hayObservadosToleIgv = false;//rando.nextBoolean();		
		
		VentasDataFicoRest clientObtieneVentasDataFico = new VentasDataFicoRest();
		
		String dproceso = "20200316";
		//Integer numlocal = 195;
		Integer numlocal = 99999;
		String estado = "O";
		
		Response response = clientObtieneVentasDataFico.ObtieneVentasDataFico(dproceso, numlocal, estado,
				REST_URIObtieneVentasFico);
		
		System.out.println("NotificaToleranciaIgvDelegate.execute: " + String.valueOf(response.getStatus()));
	
		//Map<String, String> variables = new HashMap<String, String>();
		ObtieneVentasDataFico obtieneVentasDataFico = response.readEntity(ObtieneVentasDataFico.class);
		
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
				"  margin-bottom: 10px;\r\n" + 
				"}\r\n" + 
				".table > thead > tr > th,\r\n" + 
				".table > tbody > tr > th,\r\n" + 
				".table > tfoot > tr > th,\r\n" + 
				".table > thead > tr > td,\r\n" + 
				".table > tbody > tr > td,\r\n" + 
				".table > tfoot > tr > td {\r\n" + 
				"  padding: 3px;\r\n" + 
				"  line-height: 1.42857143;\r\n" + 
				"  vertical-align: top;\r\n" + 
				"  border-top: 1px solid #dddddd;\r\n" + 
				"}\r\n" + 
				"</style>");
		
		if (obtieneVentasDataFico.getResult().size()>0)
		{
			hayObservadosToleIgv = true;
			resultMessage.append("Se informa a los interesados de " + obtieneVentasDataFico.getResult().size() +" errores reportados por el proceso de Tolerancia de IGV con fecha " + obtieneVentasDataFico.getResult().get(0).getFecproceso().substring(0, 10) +":<p>");
		}
		
		resultMessage.append("<table class=\"table\"><caption>Errores en validación de totales</caption><tbody>");		
		resultMessage.append("<tr><th bgcolor=\"#cccccc\">Fecha Proceso</th><th bgcolor=\"#cccccc\">Sociedad</th><th bgcolor=\"#cccccc\">Cod Local</th><th bgcolor=\"#cccccc\">Desc Local</th><th bgcolor=\"#cccccc\">Total Vta</th><th bgcolor=\"#cccccc\">IGV Informado A</th><th bgcolor=\"#cccccc\">IGV Calculado B</th><th bgcolor=\"#cccccc\">Diferencia B-A</th><th bgcolor=\"#cccccc\">Estado</th></tr>");

		for (ObtieneVentasDataFicoResult p : obtieneVentasDataFico.getResult()) {
			System.out.println("===========================================");
			//String observacion = p.getDesobservacion()!= null? p.getDesobservacion() : "";
			
			System.out.println(p.getCorrelativo().toString());
			System.out.println(p.getLocaldescripcion());
			System.out.println(p.getSociedad());			
			System.out.println(p.getFecproceso());
			System.out.println(p.getEstado());
			System.out.println(p.getMensajesap());
			System.out.println(p.getDocumentosap());
			System.out.println(p.getFeccrea());
			System.out.println(p.getUsucrea());
			System.out.println(p.getFecmodifica());
			System.out.println(p.getUsumodifica());
			System.out.println(p.getTotalvta().toString());
			System.out.println(p.getIgvinformado().toString());
			System.out.println(p.getIgvcalculado().toString());
			System.out.println(p.getIgvdiferencia().toString());
			System.out.println(p.getCentrocosto());
			
			/*
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
			*/
			resultMessage.append("<tr><td>"+p.getFecproceso().substring(0,10) +"</td><td>"+p.getSociedad()+"</td><td>"+ p.getCodlocal()+"</td><td>"+p.getLocaldescripcion() +"</td><td>Total Vta</td><td>IGV Informado A</td><td>IGV Calculado B</td><td>Diferencia B-A</td><td>Estado</td></tr>");
			
			hayObservadosToleIgv = true;
		}		
		resultMessage.append("</tbody></table>");
		if (hayObservadosToleIgv)
		{
			//FuncionesGenerales.SendMail(idIntegracion, "SP",TituloCorreo, resultMessage.toString(), "TMPL0001",REST_URIEnvioCorreo);	
		}
		

		Map<String, Object> vars = new HashMap<>();
	    vars.put("elementosTolerancia", obtieneVentasDataFico.getResult());

		//vars.put("elements", ctrlProcesoLocal.getResult());
	    System.out.println("hayObservadosCT " + hayObservadosToleIgv.toString());
	    /*
	    execution.setVariable("hayObservadosCT", hayObservadosToleIgv);
	    execution.setVariables(vars);
	    */

	}

}
