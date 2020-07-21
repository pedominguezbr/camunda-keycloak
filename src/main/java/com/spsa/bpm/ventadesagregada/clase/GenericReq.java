package com.spsa.bpm.ventadesagregada.clase;

import java.io.Serializable;

public class GenericReq implements Serializable{
	private static final long serialVersionUID = 789543L;
	
	private String dproceso;

	/**
	 * @return the dproceso
	 */
	public String getDproceso() {
		return dproceso;
	}

	/**
	 * @param dproceso the dproceso to set
	 */
	public void setDproceso(String dproceso) {
		this.dproceso = dproceso;
	}
}
