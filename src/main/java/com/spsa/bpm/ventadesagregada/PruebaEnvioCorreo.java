package com.spsa.bpm.ventadesagregada;

import com.spsa.bpm.ventadesagregada.util.FuncionesGenerales;

public class PruebaEnvioCorreo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
				"  padding: 8px;\r\n" + 
				"  line-height: 1.42857143;\r\n" + 
				"  vertical-align: top;\r\n" + 
				"  border-top: 1px solid #dddddd;\r\n" + 
				"}\r\n" + 
				"</style>");
		resultMessage.append("<p>");
		resultMessage.append("<table class=\"table\"><caption>Errores en totales</caption><tbody>");
		resultMessage.append("<tr><th>Fecha Proceso</th><th>Sociedad</th><th>Cod Local</th><th>Desc Local</th><th>Tipo Estado</th><th>Des_observación</th></tr>");
		
		resultMessage.append("<tr><td>fechaproceso</td><td>sociedad</td><td>codlocal</td><td>desclocal</td><td>tipoestado</td><td>descobservacion</td></tr>");
		resultMessage.append("<tr><td>fechaproceso</td><td>sociedad</td><td>codlocal</td><td>desclocal</td><td>tipoestado</td><td>descobservacion</td></tr>");
		resultMessage.append("<tr><td>fechaproceso</td><td>sociedad</td><td>codlocal</td><td>desclocal</td><td>tipoestado</td><td>descobservacion</td></tr>");
		
		resultMessage.append("</tbody></table>");
		FuncionesGenerales.SendMail(idIntegracion, "SP",TituloCorreo, resultMessage.toString(), "TMPL0001",REST_URIEnvioCorreo);
		
		
		
		/*
		resultMessage.append("\n").append("TRANSFER_HOUR=").append(stringLocalDateTime).append("\n")
		.append("ID=").append(transferItem.getTransferId()).append("\n").append("SOURCE_FILENAME=")
		.append(transferItem.getSourceFilename()).append("\n").append("DESTINATION_FILENAME=")
		.append(transferItem.getDestinationFilename()).append("\n").append("ERROR=")
		.append(transferItem.getResultText()).append("\n");
		*/
	}

}
