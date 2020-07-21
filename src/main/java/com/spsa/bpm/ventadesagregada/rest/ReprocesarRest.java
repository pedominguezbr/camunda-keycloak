package com.spsa.bpm.ventadesagregada.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.spsa.bpm.ventadesagregada.clase.ListaLocalesReprocesoReq;
import com.spsa.bpm.ventadesagregada.clase.RechazarToleranciaReq;
import com.spsa.bpm.ventadesagregada.clase.UpToleraVentasDataFicoReproReq;
import com.spsa.bpm.ventadesagregada.clase.EjecutaCargaVentaReq;
import com.spsa.bpm.ventadesagregada.clase.EjecutaReprocesoReq;
import com.spsa.bpm.ventadesagregada.clase.GenericReq;

public class ReprocesarRest {
	private Client client = ClientBuilder.newClient();
	public Response DeterminarDiferencia(String dproceso, String REST_URIDeterminaDiferencia) {
		GenericReq genericReq = new GenericReq();
		genericReq.setDproceso(dproceso);		
			
		WebTarget target = client.target(REST_URIDeterminaDiferencia);//.path("enviar-auditoria");
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
				
		Response response = invocationBuilder.post(Entity.entity(genericReq, MediaType.APPLICATION_JSON));
		
		return response;
	}
	
	public Response ObtenerLocalReproceso(ListaLocalesReprocesoReq listaLocalesReprocesoReq, String REST_URIDeterminaDiferencia) {
					
		WebTarget target = client.target(REST_URIDeterminaDiferencia);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
				
		Response response = invocationBuilder.post(Entity.entity(listaLocalesReprocesoReq, MediaType.APPLICATION_JSON));
		
		return response;
	}
	
	public Response EjecutarReproceso(String dproceso,Integer numlocal, String REST_URIEjecutaReproceso) {
		EjecutaReprocesoReq ejecutaReprocesoReq = new EjecutaReprocesoReq();
		ejecutaReprocesoReq.setDproceso(dproceso);	
		ejecutaReprocesoReq.setNumlocal(numlocal);
			
		WebTarget target = client.target(REST_URIEjecutaReproceso);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
				
		Response response = invocationBuilder.post(Entity.entity(ejecutaReprocesoReq, MediaType.APPLICATION_JSON));
		
		return response;
	}
	
	public Response RegistrarVentaSapReproceso(String dproceso, Integer numlocal,String REST_URIRegistraVentaSapRepro) {
		EjecutaCargaVentaReq ejecutaCargaVentaReq = new EjecutaCargaVentaReq();
		ejecutaCargaVentaReq.setDproceso(dproceso);
		ejecutaCargaVentaReq.setNumlocal(numlocal);	
			
		WebTarget target = client.target(REST_URIRegistraVentaSapRepro);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);				
		Response response = invocationBuilder.post(Entity.entity(ejecutaCargaVentaReq, MediaType.APPLICATION_JSON));
		
		return response;
	}
	
	public Response UpdToleraVentasDataFicoRepro(String dproceso, Integer numlocal,Integer correlativo, String usuario, String estado, String REST_URIUpdVentasRepro) {
		UpToleraVentasDataFicoReproReq upToleraVentasDataFicoReproReq = new UpToleraVentasDataFicoReproReq();
		upToleraVentasDataFicoReproReq.setDproceso(dproceso);
		upToleraVentasDataFicoReproReq.setNumlocal(numlocal);	
		upToleraVentasDataFicoReproReq.setCorrelativo(correlativo);
		upToleraVentasDataFicoReproReq.setUsuario(usuario);
		upToleraVentasDataFicoReproReq.setEstado(estado);
			
		WebTarget target = client.target(REST_URIUpdVentasRepro);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);				
		Response response = invocationBuilder.post(Entity.entity(upToleraVentasDataFicoReproReq, MediaType.APPLICATION_JSON));
		
		return response;
	}
	
	public Response RechazarToleranciaReproceso(String dproceso, Integer numlocal,Integer correlativo, String REST_URIRechazarToleranciaRepro) {
		RechazarToleranciaReq rechazarToleranciaReq = new RechazarToleranciaReq();
		rechazarToleranciaReq.setDproceso(dproceso);
		rechazarToleranciaReq.setNumlocal(numlocal);	
		rechazarToleranciaReq.setCorrelativo(correlativo);
			
		WebTarget target = client.target(REST_URIRechazarToleranciaRepro);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);				
		Response response = invocationBuilder.post(Entity.entity(rechazarToleranciaReq, MediaType.APPLICATION_JSON));
		
		return response;
	}
}
