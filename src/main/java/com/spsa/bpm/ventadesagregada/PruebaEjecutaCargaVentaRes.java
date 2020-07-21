package com.spsa.bpm.ventadesagregada;

import javax.ws.rs.core.Response;

import com.spsa.bpm.ventadesagregada.clase.CtrlProcesoLocal;
import com.spsa.bpm.ventadesagregada.clase.CodResultRes;
import com.spsa.bpm.ventadesagregada.rest.EjecutarCargaVentaRest;

public class PruebaEjecutaCargaVentaRes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EjecutarCargaVentaRest client = new EjecutarCargaVentaRest();
		Boolean subSanadoOk=false;
		String REST_URIEjecutaCargaVenta = "http://ws-rest-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/ejecutacargaventasubsanar";
		
		String fproceso = "2020-03-16";		
		String dproceso = fproceso.replace("-", "");
		System.out.println("dproceso "  + dproceso);
		
		
		Integer numlocal = 195;
		
		Response response = client.EjecutarCargaVenta(dproceso, numlocal, REST_URIEjecutaCargaVenta);
		
		CodResultRes ejecutaCargaVentaSubsanarRes = response.readEntity(CodResultRes.class);
		
		System.out.println("getStatus: "+ String.valueOf(response.getStatus()) );
		
		System.out.println("getCod_ret: "+ ejecutaCargaVentaSubsanarRes.getCod_ret() );
		System.out.println("getResult: "+ ejecutaCargaVentaSubsanarRes.getResult() );
		
		if (ejecutaCargaVentaSubsanarRes.getCod_ret().contains("0") || ejecutaCargaVentaSubsanarRes.getCod_ret().contains("-3"))
		{
			subSanadoOk=true;
		}
		else
		{
			subSanadoOk=false;
		}
		
		System.out.println("subSanadoOk " + subSanadoOk.toString());	
	}

}
