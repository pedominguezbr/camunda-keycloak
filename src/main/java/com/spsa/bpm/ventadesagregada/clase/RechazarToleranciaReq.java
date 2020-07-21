package com.spsa.bpm.ventadesagregada.clase;

import java.io.Serializable;

public class RechazarToleranciaReq implements Serializable{
	
	public RechazarToleranciaReq() {}
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
	/**
	 * @return the numlocal
	 */
	public Integer getNumlocal() {
		return numlocal;
	}
	/**
	 * @param numlocal the numlocal to set
	 */
	public void setNumlocal(Integer numlocal) {
		this.numlocal = numlocal;
	}
	/**
	 * @return the correlativo
	 */
	public Integer getCorrelativo() {
		return correlativo;
	}
	/**
	 * @param correlativo the correlativo to set
	 */
	public void setCorrelativo(Integer correlativo) {
		this.correlativo = correlativo;
	}
	private static final long serialVersionUID = 567805L;
	String dproceso;
	Integer numlocal;
	Integer correlativo;
}
