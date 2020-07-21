package com.spsa.bpm.ventadesagregada;

import javax.ws.rs.core.Response;
import com.spsa.bpm.ventadesagregada.clase.ObtenerFechaProcesoRes;
import com.spsa.bpm.ventadesagregada.rest.EjecutarCargaVentaRest;

public class ObtenerFechaProceso {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String apiHost = "ws-rest-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz";
		
		String REST_URIObtenerFechaProceso = String.format("http://%s/obtienefechaproceso",apiHost) ;
		
		System.out.println(REST_URIObtenerFechaProceso);
		EjecutarCargaVentaRest client = new EjecutarCargaVentaRest();
		Response response = client.ObtenerFechaProceso(REST_URIObtenerFechaProceso);
		

		ObtenerFechaProcesoRes obtenerFechaProcesoRes = response.readEntity(ObtenerFechaProcesoRes.class);
		
		System.out.println("getStatus: "+ String.valueOf(response.getStatus()) );
		
		System.out.println("getCod_ret: "+ obtenerFechaProcesoRes.getCod_ret() );
		System.out.println("getResult: "+ obtenerFechaProcesoRes.getResult() );
	}

}
