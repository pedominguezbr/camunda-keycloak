package com.spsa.bpm.ventadesagregada;

import javax.ws.rs.core.Response;
import com.spsa.bpm.ventadesagregada.clase.CodResultRes;
import com.spsa.bpm.ventadesagregada.rest.EjecutarCargaVentaRest;
import com.spsa.bpm.ventadesagregada.rest.VentasDataFicoRest;

public class pruebaEjecucion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Inicio EjecutaCargaVentaDelegate..................................");
		EjecutarCargaVentaRest client = new EjecutarCargaVentaRest();
		
		String REST_URIEjecutaCargaVenta = "http://ws-rest-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/ejecutacargaventa";
		
		String dproceso = "20200316";
		Integer numlocal = 195;
		Boolean subSanadoOk=false;
		Response response = client.EjecutarCargaVenta(dproceso, numlocal, REST_URIEjecutaCargaVenta);
		System.out.println("ProcesarMonitoreo.RegistrarMonitoreo"+ String.valueOf(response.getStatus()) );
		System.out.println("Fin EjecutaCargaVentaDelegate..................................");
	
		System.out.println("Inicio RegistraVentaSapDelegate..................................");
		
		VentasDataFicoRest clientRegistrarVentaSap = new VentasDataFicoRest();
		
		String REST_URIRegistraVentaSap = "http://ws-rest-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/registraventasap";
		
		
		Response responseVentaSap = clientRegistrarVentaSap.RegistrarVentaSap(dproceso, numlocal, REST_URIRegistraVentaSap);
		System.out.println("RegistraVentaSapDelegate.RegistrarVentaSap"+ String.valueOf(responseVentaSap.getStatus()) );
		
		CodResultRes RegistrarVentaSapRes = responseVentaSap.readEntity(CodResultRes.class);
		
		System.out.println("RegistrarVentaSapRes.getCod_ret: "+ RegistrarVentaSapRes.getCod_ret() );
		System.out.println("RegistrarVentaSapRes.getResult: "+ RegistrarVentaSapRes.getResult() );
		if (RegistrarVentaSapRes.getCod_ret().contains("00"))
		{					
			subSanadoOk=true;
		}
		else {
			subSanadoOk=false;
		}
		System.out.println("subSanadoOk " + subSanadoOk.toString());
		System.out.println("Fin RegistraVentaSapDelegate..................................");
	
	
	}

}
