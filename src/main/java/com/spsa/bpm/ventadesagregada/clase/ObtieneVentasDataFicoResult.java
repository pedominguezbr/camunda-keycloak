package com.spsa.bpm.ventadesagregada.clase;

import java.io.Serializable;

public class ObtieneVentasDataFicoResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 123445L;
	private Integer correlativo;
	
	public ObtieneVentasDataFicoResult()
	{}
	

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
	/**
	 * @return the codlocal
	 */
	public Integer getCodlocal() {
		return codlocal;
	}
	/**
	 * @param codlocal the codlocal to set
	 */
	public void setCodlocal(Integer codlocal) {
		this.codlocal = codlocal;
	}
	/**
	 * @return the localdescripcion
	 */
	public String getLocaldescripcion() {
		return localdescripcion;
	}
	/**
	 * @param localdescripcion the localdescripcion to set
	 */
	public void setLocaldescripcion(String localdescripcion) {
		this.localdescripcion = localdescripcion;
	}
	/**
	 * @return the fecproceso
	 */
	public String getFecproceso() {
		return fecproceso;
	}
	/**
	 * @param fecproceso the fecproceso to set
	 */
	public void setFecproceso(String fecproceso) {
		this.fecproceso = fecproceso;
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
	 * @return the mensajesap
	 */
	public String getMensajesap() {
		return mensajesap;
	}
	/**
	 * @param mensajesap the mensajesap to set
	 */
	public void setMensajesap(String mensajesap) {
		this.mensajesap = mensajesap;
	}
	/**
	 * @return the documentosap
	 */
	public String getDocumentosap() {
		return documentosap;
	}
	/**
	 * @param documentosap the documentosap to set
	 */
	public void setDocumentosap(String documentosap) {
		this.documentosap = documentosap;
	}
	/**
	 * @return the feccrea
	 */
	public String getFeccrea() {
		return feccrea;
	}
	/**
	 * @param feccrea the feccrea to set
	 */
	public void setFeccrea(String feccrea) {
		this.feccrea = feccrea;
	}
	/**
	 * @return the usucrea
	 */
	public String getUsucrea() {
		return usucrea;
	}
	/**
	 * @param usucrea the usucrea to set
	 */
	public void setUsucrea(String usucrea) {
		this.usucrea = usucrea;
	}
	/**
	 * @return the fecmodifica
	 */
	public String getFecmodifica() {
		return fecmodifica;
	}
	/**
	 * @param fecmodifica the fecmodifica to set
	 */
	public void setFecmodifica(String fecmodifica) {
		this.fecmodifica = fecmodifica;
	}
	/**
	 * @return the usumodifica
	 */
	public String getUsumodifica() {
		return usumodifica;
	}
	/**
	 * @param usumodifica the usumodifica to set
	 */
	public void setUsumodifica(String usumodifica) {
		this.usumodifica = usumodifica;
	}
	
	/**
	 * @return the totalvta
	 */
	public Double getTotalvta() {
		return totalvta;
	}


	/**
	 * @param totalvta the totalvta to set
	 */
	public void setTotalvta(Double totalvta) {
		this.totalvta = totalvta;
	}


	/**
	 * @return the igvinformado
	 */
	public Double getIgvinformado() {
		return igvinformado;
	}


	/**
	 * @param igvinformado the igvinformado to set
	 */
	public void setIgvinformado(Double igvinformado) {
		this.igvinformado = igvinformado;
	}


	/**
	 * @return the igvcalculado
	 */
	public Double getIgvcalculado() {
		return igvcalculado;
	}


	/**
	 * @param igvcalculado the igvcalculado to set
	 */
	public void setIgvcalculado(Double igvcalculado) {
		this.igvcalculado = igvcalculado;
	}


	/**
	 * @return the igvdiferencia
	 */
	public Double getIgvdiferencia() {
		return igvdiferencia;
	}


	/**
	 * @param igvdiferencia the igvdiferencia to set
	 */
	public void setIgvdiferencia(Double igvdiferencia) {
		this.igvdiferencia = igvdiferencia;
	}		

	/**
	 * @return the centrocosto
	 */
	public String getCentrocosto() {
		return centrocosto;
	}


	/**
	 * @param centrocosto the centrocosto to set
	 */
	public void setCentrocosto(String centrocosto) {
		this.centrocosto = centrocosto;
	}
	
	

	/**
	 * @return the tolerancia
	 */
	public Double getTolerancia() {
		return tolerancia;
	}


	/**
	 * @param tolerancia the tolerancia to set
	 */
	public void setTolerancia(Double tolerancia) {
		this.tolerancia = tolerancia;
	}
	
	private String sociedad;
	private Integer codlocal;
	private String localdescripcion;
	private String fecproceso;
	private String estado;
	private String mensajesap;
	private String documentosap;
	private String feccrea;
	private String usucrea;
	private String fecmodifica;
	private String usumodifica;
	private Double totalvta;
	private Double igvinformado;
	private Double igvcalculado;
	private Double igvdiferencia;
	private String centrocosto;
	private Double tolerancia;
}
