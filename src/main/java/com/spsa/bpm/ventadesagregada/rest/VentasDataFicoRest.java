package com.spsa.bpm.ventadesagregada.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.spsa.bpm.ventadesagregada.clase.EjecutaCargaVentaReq;
import com.spsa.bpm.ventadesagregada.clase.ObtieneProcesoLocalReq;
import com.spsa.bpm.ventadesagregada.clase.RechazarToleranciaReq;
import com.spsa.bpm.ventadesagregada.clase.UpToleraVentasDataFicoReq;

public class VentasDataFicoRest {
	private Client client = ClientBuilder.newClient();
	public Response ObtieneVentasDataFico(String dproceso, Integer numlocal,String estado, String REST_URIObtieneProcesoLocal) {
		ObtieneProcesoLocalReq obtieneProcesoLocalReq = new ObtieneProcesoLocalReq();
		obtieneProcesoLocalReq.setDproceso(dproceso);
		obtieneProcesoLocalReq.setNumlocal(numlocal);	
		obtieneProcesoLocalReq.setEstado(estado);	
			
		WebTarget target = client.target(REST_URIObtieneProcesoLocal);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);				
		Response response = invocationBuilder.post(Entity.entity(obtieneProcesoLocalReq, MediaType.APPLICATION_JSON));
		
		return response;
	}
	
	public Response RegistrarVentaSap(String dproceso, Integer numlocal,String REST_URIRegistraVentaSap) {
		EjecutaCargaVentaReq ejecutaCargaVentaReq = new EjecutaCargaVentaReq();
		ejecutaCargaVentaReq.setDproceso(dproceso);
		ejecutaCargaVentaReq.setNumlocal(numlocal);	
			
		WebTarget target = client.target(REST_URIRegistraVentaSap);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);				
		Response response = invocationBuilder.post(Entity.entity(ejecutaCargaVentaReq, MediaType.APPLICATION_JSON));
		
		return response;
	}
	
	public Response UpdToleraVentasDataFico(String dproceso, Integer numlocal,Integer correlativo, String usuario, String REST_URIObtieneProcesoLocal) {
		UpToleraVentasDataFicoReq upToleraVentasDataFicoReq = new UpToleraVentasDataFicoReq();
		upToleraVentasDataFicoReq.setDproceso(dproceso);
		upToleraVentasDataFicoReq.setNumlocal(numlocal);	
		upToleraVentasDataFicoReq.setCorrelativo(correlativo);
		upToleraVentasDataFicoReq.setUsuario(usuario);
			
		WebTarget target = client.target(REST_URIObtieneProcesoLocal);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);				
		Response response = invocationBuilder.post(Entity.entity(upToleraVentasDataFicoReq, MediaType.APPLICATION_JSON));
		
		return response;
	}
	
	public Response RegistrarVentaSapLocal(String dproceso, Integer numlocal,String REST_URIRegistraVentaSapLocal) {
		EjecutaCargaVentaReq ejecutaCargaVentaReq = new EjecutaCargaVentaReq();
		ejecutaCargaVentaReq.setDproceso(dproceso);
		ejecutaCargaVentaReq.setNumlocal(numlocal);	
			
		WebTarget target = client.target(REST_URIRegistraVentaSapLocal);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);				
		Response response = invocationBuilder.post(Entity.entity(ejecutaCargaVentaReq, MediaType.APPLICATION_JSON));
		
		return response;
	}
	
	public Response RechazarTolerancia(String dproceso, Integer numlocal,Integer correlativo, String REST_URIRechazarTolerancia) {
		RechazarToleranciaReq rechazarToleranciaReq = new RechazarToleranciaReq();
		rechazarToleranciaReq.setDproceso(dproceso);
		rechazarToleranciaReq.setNumlocal(numlocal);	
		rechazarToleranciaReq.setCorrelativo(correlativo);
			
		WebTarget target = client.target(REST_URIRechazarTolerancia);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);				
		Response response = invocationBuilder.post(Entity.entity(rechazarToleranciaReq, MediaType.APPLICATION_JSON));
		
		return response;
	}
}
