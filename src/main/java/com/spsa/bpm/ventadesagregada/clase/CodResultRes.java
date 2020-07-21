package com.spsa.bpm.ventadesagregada.clase;

import java.io.Serializable;

public class CodResultRes implements Serializable {
	private static final long serialVersionUID = 12345L;
	
	private String cod_ret;
	public CodResultRes()
	{}
	/**
	 * @param cod_ret
	 * @param result
	 */
	public CodResultRes(String cod_ret, String result) {
		this.cod_ret = cod_ret;
		this.result = result;
	}
	@Override
	public String toString() {
		return "EjecutaCargaVentaSubsanarRes [cod_ret=" + cod_ret + ", result=" + result + "]";
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
	private String result;
}
