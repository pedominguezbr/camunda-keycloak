package com.spsa.bpm.ventadesagregada.clase;

import java.io.Serializable;

public class RegistraVentaSapLocalRes implements Serializable{
	private static final long serialVersionUID = 13467568L;
	public RegistraVentaSapLocalRes() {
	}
	
	public class LocalResResult {
		public LocalResResult()
		{}
		
		String estadoSap;
		/**
		 * @return the estadoSap
		 */
		public String getEstadoSap() {
			return estadoSap;
		}
		/**
		 * @param estadoSap the estadoSap to set
		 */
		public void setEstadoSap(String estadoSap) {
			this.estadoSap = estadoSap;
		}
		/**
		 * @return the msgResSap
		 */
		public String getMsgResSap() {
			return msgResSap;
		}
		/**
		 * @param msgResSap the msgResSap to set
		 */
		public void setMsgResSap(String msgResSap) {
			this.msgResSap = msgResSap;
		}
		String msgResSap;
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
	public LocalResResult getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(LocalResResult result) {
		this.result = result;
	}
	String cod_ret;
	LocalResResult result;
	
}


