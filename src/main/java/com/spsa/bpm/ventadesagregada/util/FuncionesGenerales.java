package com.spsa.bpm.ventadesagregada.util;

import static com.spsa.bpm.ventadesagregada.rest.ssl.JerseyHttpClientFactory.getJerseyHTTPSClient;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.runtime.VariableInstanceQuery;

import com.google.gson.Gson;
import com.spsa.bpm.ventadesagregada.clase.CtrlProcesoLocal;
import com.spsa.bpm.ventadesagregada.clase.CtrlProcesoLocalResult;
import com.spsa.bpm.ventadesagregada.clase.MailType;
import com.spsa.bpm.ventadesagregada.clase.ObtieneVentasDataFico;
import com.spsa.bpm.ventadesagregada.clase.ObtieneVentasDataFicoResult;
import com.spsa.bpm.ventadesagregada.clase.SendMailReq;
import com.spsa.bpm.ventadesagregada.clase.keysTypes;
import com.spsa.bpm.ventadesagregada.rest.ObtieneProcesoLocalRest;
import com.spsa.bpm.ventadesagregada.rest.VentasDataFicoRest;
import com.spsa.bpm.ventadesagregada.clase.ListaLocalesReprocesoRes;
import com.spsa.bpm.ventadesagregada.clase.LocalesReproceso;

public class FuncionesGenerales {

	public static Boolean SendMail(String AppicationCode, String Company, String TituloCorreo,
			String DetalleCorreo, String Template, String REST_URIEnvioCorreo) {
		String resultResponse = "";
		Boolean resultado = false;	
		
		try {
			SendMailReq sendMailobj = new SendMailReq();
			sendMailobj.setApplicationCode(AppicationCode);
			sendMailobj.setCompany(Company);
			MailType mailtype = new MailType();
			mailtype.setSubject(TituloCorreo);

			keysTypes keytypes = new keysTypes();
			keytypes.setName("content"); // Esto es por default
			keytypes.setDetail(DetalleCorreo);

			keysTypes[] keyarray = new keysTypes[1];
			keyarray[0] = keytypes;

			mailtype.setKeys(keyarray);
			mailtype.setTemplate(Template);
			mailtype.setTo("");
			sendMailobj.setEmail(mailtype);

			Gson gson = new Gson();
			String userJson = gson.toJson(sendMailobj);
			
			Client client = getJerseyHTTPSClient();

			try {
				resultResponse = client.target(REST_URIEnvioCorreo).request(MediaType.APPLICATION_JSON)
						.post(Entity.json(userJson)).readEntity(String.class);
			} finally {				

				System.out.println("Response "+  resultResponse);
				
				client.close();
			}

			resultado = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			
		}

		return resultado;

	}
	
	public static String currentUser() {
	    CommandContext commandContext = Context.getCommandContext();
	    if (commandContext != null) {
	      return commandContext.getAuthenticatedUserId();
	    }
	    else {
	      return null;
	    }
	  }

	public static Boolean EnviarCorreoNotificaControlTotales(String TituloCorreo, CtrlProcesoLocal ctrlProcesoLocal) {
		
		String REST_URIEnvioCorreo = Constantes.URL_SENDMAIL_API;
		Boolean enviaCorreoControltotal = false;
		String idIntegracion = Constantes.ID_INT_CORREO_SOPORTECT;
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
		
		if (ctrlProcesoLocal.getResult().size()>0)
		{
			enviaCorreoControltotal = true;
			resultMessage.append("Se informa a los interesados de " + ctrlProcesoLocal.getResult().size() +" errores reportados por el proceso de Venta Desagregada con fecha " + ctrlProcesoLocal.getResult().get(0).getFecproceso().substring(0, 10) +":<p>");
		}
		
		resultMessage.append("<table class=\"table\"><caption>Errores en validación de totales</caption><tbody>");		
		resultMessage.append("<tr><th bgcolor=\"#cccccc\">Fecha Proceso</th><th bgcolor=\"#cccccc\">Sociedad</th><th bgcolor=\"#cccccc\">Cod Local</th><th bgcolor=\"#cccccc\">Desc Local</th><th bgcolor=\"#cccccc\">Tipo Estado</th><th bgcolor=\"#cccccc\">Des_observación</th></tr>");

		for (CtrlProcesoLocalResult p : ctrlProcesoLocal.getResult()) {
			System.out.println("===========================================");
			String observacion = p.getDesobservacion()!= null? p.getDesobservacion() : "";
			
			resultMessage.append("<tr><td>"+p.getFecproceso().substring(0,10) +"</td><td>"+p.getSociedad()+"</td><td>"+ p.getCodlocal()+"</td><td>"+p.getLocaldescripcion() +"</td><td>"+p.getTipestado()+"</td><td>" + observacion+ "</td></tr>");
			
			enviaCorreoControltotal = true;
		}
		
		resultMessage.append("</tbody></table>");
		resultMessage.append("</br>");
		resultMessage.append("Luego de resolver las observaciones en los locales notificados ir al siguiente link para completar su procesamiento:&nbsp;");
		resultMessage.append(Constantes.URL_VENTA_DESAGREGADA);
		
		if (enviaCorreoControltotal)
		{
			SendMail(idIntegracion, "SP",TituloCorreo, resultMessage.toString(), "TMPL0001",REST_URIEnvioCorreo);	
		}
		
		return enviaCorreoControltotal;
		
	}
	
	public static Boolean EnviarCorreoNotificaTolerancia(String TituloCorreo,ObtieneVentasDataFico obtieneVentasDataFico) {
		Boolean EnvioCorreoTolerancia = false;
		StringBuilder resultMessage = new StringBuilder();
		String REST_URIEnvioCorreo = Constantes.URL_SENDMAIL_API;		
		String idIntegracion = Constantes.ID_INT_CORREO_USUARIO;
		
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
			EnvioCorreoTolerancia = true;
			resultMessage.append("Se informa a los interesados de " + obtieneVentasDataFico.getResult().size() +" errores reportados por el proceso de Tolerancia de IGV con fecha " + obtieneVentasDataFico.getResult().get(0).getFecproceso().substring(0, 10) +":<p>");
		}
		
		resultMessage.append("<table class=\"table\"><caption>Errores en validación de totales</caption><tbody>");		
		resultMessage.append("<tr><th bgcolor=\"#cccccc\">Fecha Proceso</th><th bgcolor=\"#cccccc\">Sociedad</th><th bgcolor=\"#cccccc\">Cod Local</th><th bgcolor=\"#cccccc\">Desc Local</th><th bgcolor=\"#cccccc\">Total Vta</th><th bgcolor=\"#cccccc\">IGV Informado A</th><th bgcolor=\"#cccccc\">IGV Calculado B</th><th bgcolor=\"#cccccc\">Diferencia B-A</th><th bgcolor=\"#cccccc\">Estado</th><th bgcolor=\"#cccccc\">Tolerancia</th></tr>");

		for (ObtieneVentasDataFicoResult p : obtieneVentasDataFico.getResult()) {		
			resultMessage.append("<tr><td>"+p.getFecproceso().substring(0,10) +"</td><td>"+p.getSociedad()+"</td><td>"+ p.getCodlocal()+"</td><td>"+p.getLocaldescripcion() +"</td><td>" +String.format(Constantes.FORMAT_DOUBLE, p.getTotalvta()) +"</td><td>" + String.format(Constantes.FORMAT_DOUBLE, p.getIgvinformado()) + "</td><td>"+ String.format(Constantes.FORMAT_DOUBLE, p.getIgvcalculado()) +"</td><td>" + p.getIgvdiferencia().toString() + "</td><td>" + p.getEstado() + "</td><td>" + p.getTolerancia() + "</td></tr>");
			
			EnvioCorreoTolerancia = true;
		}

		resultMessage.append("</tbody></table>");
		resultMessage.append("</br>");
		resultMessage.append("Luego de resolver las observaciones en los locales notificados ir al siguiente link para completar su procesamiento:&nbsp;");
		resultMessage.append(Constantes.URL_VENTA_DESAGREGADA);
		
		if (EnvioCorreoTolerancia)
		{
			SendMail(idIntegracion, "SP",TituloCorreo, resultMessage.toString(), "TMPL0001",REST_URIEnvioCorreo);	
		}
		
		return EnvioCorreoTolerancia;
	}

	public static Boolean EnviarCorreoNotificaSoporteSap(String TituloCorreo,ObtieneVentasDataFico obtieneVentasDataFico) {
		
		String REST_URIEnvioCorreo = Constantes.URL_SENDMAIL_API;		
		String idIntegracion = Constantes.ID_INT_CORREO_SOPORTESAP;
		StringBuilder resultMessage = new StringBuilder();
		Boolean EnvioCorreoSap = false;//rando.nextBoolean();		
		
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
			EnvioCorreoSap = true;
			resultMessage.append("Se informa a los interesados de " + obtieneVentasDataFico.getResult().size() +" errores reportados por el proceso de SAP con fecha " + obtieneVentasDataFico.getResult().get(0).getFecproceso().substring(0, 10) +":<p>");
		}
		
		resultMessage.append("<table class=\"table\"><caption>Errores en Proceso SAP</caption><tbody>");		
		resultMessage.append("<tr><th bgcolor=\"#cccccc\">Fecha Proceso</th><th bgcolor=\"#cccccc\">Sociedad</th><th bgcolor=\"#cccccc\">Centro Costo</th><th bgcolor=\"#cccccc\">Desc Local</th><th bgcolor=\"#cccccc\">Mensaje SAP</th><th bgcolor=\"#cccccc\">Estado</th></tr>");

		for (ObtieneVentasDataFicoResult p : obtieneVentasDataFico.getResult()) {
			
			resultMessage.append("<tr><td>"+p.getFecproceso().substring(0,10) +"</td><td>"+p.getSociedad()+"</td><td>" + p.getCentrocosto() + "</td><td>"+p.getLocaldescripcion() +"</td><td>" + p.getMensajesap() +"</td><td>" + p.getEstado() + "</td></tr>");
			
			EnvioCorreoSap = true;
		}		
		resultMessage.append("</tbody></table>");
		resultMessage.append("</br>");
		resultMessage.append("Luego de resolver las observaciones en los locales notificados ir al siguiente link para completar su procesamiento:&nbsp;");
		resultMessage.append(Constantes.URL_VENTA_DESAGREGADA);
		
		if (EnvioCorreoSap)
		{
			SendMail(idIntegracion, "SP",TituloCorreo, resultMessage.toString(), "TMPL0001",REST_URIEnvioCorreo);	
		}
		
		return EnvioCorreoSap;
		
	}
	
	public static Boolean EnviarCorreoNotificaReproceso(String TituloCorreo, ListaLocalesReprocesoRes listaLocalesReprocesoRes) {
		
		String REST_URIEnvioCorreo = Constantes.URL_SENDMAIL_API;
		Boolean enviaCorreoReproceso = false;
		String idIntegracion = Constantes.ID_INT_CORREO_REPROCESO;
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
		
		if (listaLocalesReprocesoRes.getResult().size()>0)
		{
			enviaCorreoReproceso = true;
			resultMessage.append("Se informa a los interesados de " + listaLocalesReprocesoRes.getResult().size() +" locales reportados con diferencias en registros ya informados a SAP:<p>");
		}
		
		resultMessage.append("<table class=\"table\"><caption>Locales con diferencias en registros</caption><tbody>");		
		resultMessage.append("<tr><th bgcolor=\"#cccccc\">Fecha Proceso</th><th bgcolor=\"#cccccc\">Sociedad</th><th bgcolor=\"#cccccc\">Cod Local</th><th bgcolor=\"#cccccc\">Desc Local</th><th bgcolor=\"#cccccc\">Tipo Estado</th><th bgcolor=\"#cccccc\">Des_observación</th></tr>");

		for (LocalesReproceso p : listaLocalesReprocesoRes.getResult()) {
			System.out.println("===========================================");
			String observacion = p.getDesobservacion()!= null? p.getDesobservacion() : "";
			
			resultMessage.append("<tr><td>"+p.getFecproceso().substring(0,10) +"</td><td>"+p.getSociedad()+"</td><td>"+ p.getCodlocal()+"</td><td>"+p.getLocaldescripcion() +"</td><td>"+p.getTipestado()+"</td><td>" + observacion+ "</td></tr>");
			
			enviaCorreoReproceso = true;
		}
		
		resultMessage.append("</tbody></table>");
		resultMessage.append("</br>");
		resultMessage.append("Luego de resolver las observaciones en los locales notificados ir al siguiente link para completar su procesamiento:&nbsp;");
		resultMessage.append(Constantes.URL_VENTA_DESAGREGADA);
		
		if (enviaCorreoReproceso)
		{
			SendMail(idIntegracion, "SP",TituloCorreo, resultMessage.toString(), "TMPL0001",REST_URIEnvioCorreo);	
		}
		
		return enviaCorreoReproceso;		
	}
	
	public static Boolean NotificaControlTotales(String dproceso, Integer numlocal,String TituloCorreo) {
		ObtieneProcesoLocalRest clientObtieneProcesoLocal = new ObtieneProcesoLocalRest();
		String estado = "E";
		
		Response response = clientObtieneProcesoLocal.ObtieneProcesoLocal(dproceso, numlocal, estado,
				Constantes.URL_OBTIENE_PROCE_LOCAL_API);
		CtrlProcesoLocal ctrlProcesoLocal = response.readEntity(CtrlProcesoLocal.class);
		
		return EnviarCorreoNotificaControlTotales(TituloCorreo, ctrlProcesoLocal);
	}
	
	public static Boolean NotificaToleranciaIgv(String dproceso, Integer numlocal,String TituloCorreo) {
		VentasDataFicoRest clientObtieneVentasDataFico = new VentasDataFicoRest();	
		String estado = "E";
		
		Response response = clientObtieneVentasDataFico.ObtieneVentasDataFico(dproceso, numlocal, estado,
				Constantes.URL_GET_VENTA_DATA_FICO_API);
		ObtieneVentasDataFico obtieneVentasDataFico = response.readEntity(ObtieneVentasDataFico.class);
		
		return EnviarCorreoNotificaTolerancia(TituloCorreo, obtieneVentasDataFico);
	}
	
	public static Boolean NotificaObservadoSap(String dproceso, Integer numlocal,String TituloCorreo) {
		VentasDataFicoRest clientObtieneVentasDataFico = new VentasDataFicoRest();	
		String estado = "O";
		
		Response response = clientObtieneVentasDataFico.ObtieneVentasDataFico(dproceso, numlocal, estado,
				Constantes.URL_GET_VENTA_DATA_FICO_API);
		ObtieneVentasDataFico obtieneVentasDataFico = response.readEntity(ObtieneVentasDataFico.class);
		
		return EnviarCorreoNotificaSoporteSap(TituloCorreo, obtieneVentasDataFico);
	}
	
	public static ArrayList<LocalesReproceso> ObtenerLocalesReprocesoSinDuplicar(ArrayList<LocalesReproceso> ListaLocalReprocesoOriginal, RuntimeService rs) {
		System.out.println("+++++++++++++++++++++++++++++++Inicio ObtenerLocalesReprocesoSinDuplicar+++++++++++++++++++++++++++++++");
		ArrayList<LocalesReproceso> ListaLocalReprocesoSinDuplicado = new ArrayList<LocalesReproceso>();
		boolean ExisteLocalenInstancia = false;
		List<ProcessInstance> processInstancesList = rs.createProcessInstanceQuery().active().processDefinitionKey("reprocesoventadesagregada").list();
		
		//Si no hay instancias generadas previamente se envia todos los locales obtenidos
		if (processInstancesList.size() ==0) {
			return ListaLocalReprocesoOriginal;
		}
		
		for (LocalesReproceso LocalReprocesoValidar : ListaLocalReprocesoOriginal) {
			System.out.println("=======================LOCAL TEST==============================================");
			System.out.println("Codlocal Original: " + LocalReprocesoValidar.getCodlocal() + " Sociedad Original: " + LocalReprocesoValidar.getSociedad() + " Fecproceso Original: " + LocalReprocesoValidar.getFecproceso().substring(0, 10));
			
			for (ProcessInstance pInstance : processInstancesList) {
				if(ExisteLocalenInstancia) break;
				System.out.println("++++++++++getProcessInstanceId: " + pInstance.getId());
				VariableInstanceQuery listvarInstance = rs.createVariableInstanceQuery().activityInstanceIdIn(pInstance.getId()).variableName("elements");
				for (VariableInstance variableinstance : listvarInstance.list()) {
					if(ExisteLocalenInstancia) break;
					System.out.println(variableinstance);
					ArrayList<LocalesReproceso> listaLocalesInstance = (ArrayList<LocalesReproceso>) variableinstance.getValue();
					
					for (LocalesReproceso p : listaLocalesInstance) {
						if(ExisteLocalenInstancia) break;
						System.out.println("=====Proceso TEST");
						System.out.println("Codlocal: " + p.getCodlocal() + " Sociedad: " + p.getSociedad() + " Fecproceso: " + p.getFecproceso().substring(0, 10));
						//System.out.println(p.getSociedad());								
						//System.out.println(p.getFecproceso().substring(0, 10));
						
						if (LocalReprocesoValidar.getCodlocal().intValue() == p.getCodlocal().intValue() &&  LocalReprocesoValidar.getSociedad().equals(p.getSociedad()) && LocalReprocesoValidar.getFecproceso().substring(0, 10).equals(p.getFecproceso().substring(0, 10)))
						{
							//Local Ya existe en variables
							ExisteLocalenInstancia = true;
							System.out.println("Existe en Instancias..!!");	
						}
					}
				}
				
				//Si local no existe en las instancias se agrega al array nuevo
				if (!ExisteLocalenInstancia) {
					System.out.println("Agregado porque no existe..!!");	
					ListaLocalReprocesoSinDuplicado.add(LocalReprocesoValidar);
				}
				
				//Se resetea la variable para el siguient flujo
				ExisteLocalenInstancia=false;
			}
		}
		//Return
		
		System.out.println("+++++++++++++++++++++++++++++++Fin ObtenerLocalesReprocesoSinDuplicar+++++++++++++++++++++++++++++++");
		return ListaLocalReprocesoSinDuplicado;
	}
}
