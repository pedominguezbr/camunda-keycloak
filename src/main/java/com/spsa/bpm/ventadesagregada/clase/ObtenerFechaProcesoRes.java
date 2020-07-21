package com.spsa.bpm.ventadesagregada.clase;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ObtenerFechaProcesoRes implements Serializable{
	private static final long serialVersionUID = 23412L;
	public ObtenerFechaProcesoRes()
	{ }
	/**
	 * @param cod_ret
	 * @param result
	 */
	public ObtenerFechaProcesoRes(String cod_ret, String result) {
		this.cod_ret = cod_ret;
		this.result = result;
	}
	@Override
	public String toString() {
		return "ObtenerFechaProcesoRes [cod_ret=" + cod_ret + ", result=" + result + "]";
	}
	/**
	 * @return the cod_ret
	 */
	public String getCod_ret() {
		return cod_ret;
	}
	/**
	 * @param cod_ret the cod_ret to set
	 */
	public void setCod_ret(String cod_ret) {
		this.cod_ret = cod_ret;
	}
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	String cod_ret;
	String result;
}
