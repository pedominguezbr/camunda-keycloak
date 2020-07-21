package com.spsa.bpm.ventadesagregada;

import javax.ws.rs.core.Response;

import com.spsa.bpm.ventadesagregada.clase.CodResultRes;
import com.spsa.bpm.ventadesagregada.clase.RegistraVentaSapLocalRes;
import com.spsa.bpm.ventadesagregada.rest.EjecutarCargaVentaRest;
import com.spsa.bpm.ventadesagregada.rest.VentasDataFicoRest;

public class RegistraVentaSapLocal {
	
	public static void main(String[] args) {
	VentasDataFicoRest client = new VentasDataFicoRest();
	String REST_URIRegistraVentaSapLocal = "http://ws-rest-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/registraventasaplocal";

	String dproceso = "20200416";
	Integer numlocal = 1;
	 
	Response response = client.RegistrarVentaSapLocal(dproceso, numlocal, REST_URIRegistraVentaSapLocal);
	
	RegistraVentaSapLocalRes registraVentaSapLocalRes = response.readEntity(RegistraVentaSapLocalRes.class);
	
	System.out.println("getStatus: "+ String.valueOf(response.getStatus()) );
	
	System.out.println("getCod_ret: "+ registraVentaSapLocalRes.getCod_ret() );
	System.out.println("getResult().getEstadoSap(): "+ registraVentaSapLocalRes.getResult().getEstadoSap());
	
	if (registraVentaSapLocalRes.getCod_ret().contains("00") && (registraVentaSapLocalRes.getResult().getEstadoSap().contains("I") || registraVentaSapLocalRes.getResult().getEstadoSap().contains("NR") )) {		
		System.out.println("proceso ok");
	}
	else
	{
		System.out.println("proceso fallo: " + registraVentaSapLocalRes.getResult().getMsgResSap());
	}
	}
}
