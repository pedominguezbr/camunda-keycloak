package com.spsa.bpm.ventadesagregada.clase;

import java.io.Serializable;
import java.util.ArrayList;

public class ObtieneVentasDataFico implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 13467L;
	
	public ObtieneVentasDataFico()
	{
		
	}
			
	private String cod_ret;	
	/**
	 * @param cod_ret
	 * @param result
	 */
	public ObtieneVentasDataFico(String cod_ret, ArrayList<ObtieneVentasDataFicoResult> result) {
		this.cod_ret = cod_ret;
		this.result = result;
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
	public ArrayList<ObtieneVentasDataFicoResult> getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(ArrayList<ObtieneVentasDataFicoResult> result) {
		this.result = result;
	}
	private ArrayList<ObtieneVentasDataFicoResult> result;
}
