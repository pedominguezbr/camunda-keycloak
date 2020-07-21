package com.spsa.bpm.ventadesagregada.clase;

import java.io.Serializable;
import java.util.ArrayList;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CtrlProcesoLocal implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 12345678L;
	private String cod_ret;	
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

	private ArrayList<CtrlProcesoLocalResult> result;
	/**
	 * @return the result
	 */
	public ArrayList<CtrlProcesoLocalResult> getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(ArrayList<CtrlProcesoLocalResult> result) {
		this.result = result;
	}
}
