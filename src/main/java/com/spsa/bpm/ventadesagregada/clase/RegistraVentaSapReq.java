package com.spsa.bpm.ventadesagregada.clase;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegistraVentaSapReq implements Serializable{
	private static final long serialVersionUID = 1346756878L;
	
	public RegistraVentaSapReq() {
		
	}
	/**
	 * @param sociedad
	 */
	public RegistraVentaSapReq(String sociedad) {
		this.sociedad = sociedad;
	}

	String sociedad;

	/**
	 * @return the sociedad
	 */
	public String getSociedad() {
		return sociedad;
	}

	/**
	 * @param sociedad the sociedad to set
	 */
	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}

}
