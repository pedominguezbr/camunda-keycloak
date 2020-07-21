package com.spsa.bpm.ventadesagregada.clase;

public class ListaLocalesReprocesoReq {
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
	 * @return the reproceso
	 */
	public Integer getReproceso() {
		return reproceso;
	}
	/**
	 * @param reproceso the reproceso to set
	 */
	public void setReproceso(Integer reproceso) {
		this.reproceso = reproceso;
	}
	private String dproceso;
	private Integer numlocal;
	private String estado;
	private Integer reproceso;
}
