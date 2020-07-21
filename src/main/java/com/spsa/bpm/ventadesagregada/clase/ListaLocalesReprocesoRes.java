package com.spsa.bpm.ventadesagregada.clase;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaLocalesReprocesoRes implements Serializable {
	private static final long serialVersionUID = 4532172L;

	String cod_ret;
	private ArrayList<LocalesReproceso> result;

	public ListaLocalesReprocesoRes() {

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
	public ArrayList<LocalesReproceso> getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(ArrayList<LocalesReproceso> result) {
		this.result = result;
	}

}
