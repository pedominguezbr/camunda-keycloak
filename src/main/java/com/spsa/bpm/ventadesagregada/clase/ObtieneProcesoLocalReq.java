package com.spsa.bpm.ventadesagregada.clase;

import java.io.Serializable;

public class ObtieneProcesoLocalReq implements Serializable{
	private static final long serialVersionUID = 54326;
	
	public ObtieneProcesoLocalReq()
	{
		
	}
	
	
	String dproceso;
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
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}


	/**
	 * @param dproceso
	 * @param numlocal
	 * @param estado
	 */
	public ObtieneProcesoLocalReq(String dproceso, Integer numlocal, String estado) {
		this.dproceso = dproceso;
		this.numlocal = numlocal;
		this.estado = estado;
	}


	Integer numlocal;
	String estado;
}
