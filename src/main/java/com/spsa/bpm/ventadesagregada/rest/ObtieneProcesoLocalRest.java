package com.spsa.bpm.ventadesagregada.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.spsa.bpm.ventadesagregada.clase.ObtieneProcesoLocalReq;

public class ObtieneProcesoLocalRest {
	private Client client = ClientBuilder.newClient();
	public Response ObtieneProcesoLocal(String dproceso, Integer numlocal,String estado, String REST_URIObtieneProcesoLocal) {
		ObtieneProcesoLocalReq obtieneProcesoLocalReq = new ObtieneProcesoLocalReq();
		obtieneProcesoLocalReq.setDproceso(dproceso);
		obtieneProcesoLocalReq.setNumlocal(numlocal);	
		obtieneProcesoLocalReq.setEstado(estado);	
			
		WebTarget target = client.target(REST_URIObtieneProcesoLocal);//.path("enviar-auditoria");
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
				
		Response response = invocationBuilder.post(Entity.entity(obtieneProcesoLocalReq, MediaType.APPLICATION_JSON));
		
		return response;
	}
}
