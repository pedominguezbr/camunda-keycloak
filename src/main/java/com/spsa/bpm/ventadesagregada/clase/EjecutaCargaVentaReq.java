package com.spsa.bpm.ventadesagregada.clase;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EjecutaCargaVentaReq implements Serializable {
	
	private static final long serialVersionUID = 1234567L;
	
	public EjecutaCargaVentaReq()
	{
		
	}
	 /**
	 * @param dproceso
	 * @param numlocal
	 */
	public EjecutaCargaVentaReq(String dproceso, Integer numlocal) {
		this.dproceso = dproceso;
		this.numlocal = numlocal;
	}
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
	private String dproceso;
	 private Integer numlocal;
}
