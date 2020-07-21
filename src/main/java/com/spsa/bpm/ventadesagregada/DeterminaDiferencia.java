package com.spsa.bpm.ventadesagregada;

import javax.ws.rs.core.Response;

import com.spsa.bpm.ventadesagregada.clase.CodResultRes;
import com.spsa.bpm.ventadesagregada.rest.ReprocesarRest;
import com.spsa.bpm.ventadesagregada.util.Constantes;

public class DeterminaDiferencia {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReprocesarRest client = new ReprocesarRest();
		String REST_URIDeterminaDiferencia = Constantes.URL_DETERM_REPROCESO_API; //  "http://ws-rest-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/ejecutadeterminadiferencia";

		String dproceso = "20200416";
		
		Response response = client.DeterminarDiferencia(dproceso, REST_URIDeterminaDiferencia);
		CodResultRes ejecutaCargaVentaSubsanarRes = response.readEntity(CodResultRes.class);
		
		System.out.println("getStatus: "+ String.valueOf(response.getStatus()) );
		
		System.out.println("getCod_ret: "+ ejecutaCargaVentaSubsanarRes.getCod_ret() );
		System.out.println("getResult: "+ ejecutaCargaVentaSubsanarRes.getResult() );
		
		
	}

}
