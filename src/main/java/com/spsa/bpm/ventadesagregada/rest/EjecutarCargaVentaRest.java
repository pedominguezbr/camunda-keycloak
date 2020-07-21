package com.spsa.bpm.ventadesagregada.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.spsa.bpm.ventadesagregada.clase.EjecutaCargaVentaReq;


public class EjecutarCargaVentaRest {
	private Client client = ClientBuilder.newClient();
	public Response ObtenerFechaProceso(String REST_URIObtenerFechaProceso) {
		WebTarget target = client.target(REST_URIObtenerFechaProceso);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
				
		Response response = invocationBuilder.get(); // post(Entity.entity(ejecutaCargaVentaReq, MediaType.APPLICATION_JSON));
		
		return response;
	}
	public Response EjecutarCargaVenta(String dproceso, Integer numlocal,String REST_URIEjecutaCargaVenta) {
		EjecutaCargaVentaReq ejecutaCargaVentaReq = new EjecutaCargaVentaReq();
		ejecutaCargaVentaReq.setDproceso(dproceso);
		ejecutaCargaVentaReq.setNumlocal(numlocal);		
			
		WebTarget target = client.target(REST_URIEjecutaCargaVenta);//.path("enviar-auditoria");
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
				
		Response response = invocationBuilder.post(Entity.entity(ejecutaCargaVentaReq, MediaType.APPLICATION_JSON));
		
		return response;
	}
}
