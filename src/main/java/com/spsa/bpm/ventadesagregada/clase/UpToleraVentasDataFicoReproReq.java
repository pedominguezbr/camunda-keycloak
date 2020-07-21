package com.spsa.bpm.ventadesagregada.clase;

public class UpToleraVentasDataFicoReproReq {
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
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
	String dproceso;
	Integer numlocal;
	Integer correlativo;
	String usuario;
	String estado;
}
