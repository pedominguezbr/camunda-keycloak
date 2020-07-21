package com.spsa.bpm.ventadesagregada.util;

public class Constantes {
	public static String URL_SENDMAIL_API = System.getenv("URL_SENDMAIL_API")!=null?System.getenv("URL_SENDMAIL_API"):"https://ws-envio-correo-qa.spsa-api.lblapiqa.spsa.xyz/send-mail";
	public static String URL_GET_FECHA_PROCESS_API = System.getenv("URL_GET_FECHA_PROCESS_API")!=null?System.getenv("URL_GET_FECHA_PROCESS_API"):"http://ws-rest-sp-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/obtienefechaproceso";
	public static String URL_EJECUTA_CARGA_VENTA_API = System.getenv("URL_EJECUTA_CARGA_VENTA_API")!=null?System.getenv("URL_EJECUTA_CARGA_VENTA_API"):"http://ws-rest-sp-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/ejecutacargaventa";
	public static String URL_EJECUTA_CARGA_VENTA_SUBSANAR_API = System.getenv("URL_EJECUTA_CARGA_VENTA_SUBSANAR_API")!=null?System.getenv("URL_EJECUTA_CARGA_VENTA_SUBSANAR_API"):"http://ws-rest-sp-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/ejecutacargaventasubsanar";
	public static String URL_OBTIENE_PROCE_LOCAL_API = System.getenv("URL_OBTIENE_PROCE_LOCAL_API")!=null?System.getenv("URL_OBTIENE_PROCE_LOCAL_API"):"http://ws-rest-sp-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/obtieneprocesolocal";
	public static String URL_GET_VENTA_DATA_FICO_API = System.getenv("URL_GET_VENTA_DATA_FICO_API")!=null?System.getenv("URL_GET_VENTA_DATA_FICO_API"):"http://ws-rest-sp-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/obtieneventasdatafico";
	public static String URL_REG_VTA_SAP_API = System.getenv("URL_REG_VTA_SAP_API")!=null?System.getenv("URL_REG_VTA_SAP_API"):"http://ws-rest-sp-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/registraventasap";
	public static String URL_REG_VTA_SAP_LOCAL_API = System.getenv("URL_REG_VTA_SAP_LOCAL_API")!=null?System.getenv("URL_REG_VTA_SAP_LOCAL_API"):"http://ws-rest-sp-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/registraventasaplocal";
	public static String URL_UP_TOLE_VTA_FICO_API = System.getenv("URL_UP_TOLE_VTA_FICO_API")!=null?System.getenv("URL_UP_TOLE_VTA_FICO_API"):"http://ws-rest-sp-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/updtoleraventasdatafico";
	public static String URL_DETERM_REPROCESO_API = System.getenv("URL_DETERM_REPROCESO_API")!=null?System.getenv("URL_DETERM_REPROCESO_API"):"http://ws-rest-sp-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/ejecutadeterminadiferencia";
	public static String URL_OBTIENE_LOCAL_REPRO_API = System.getenv("URL_OBTIENE_LOCAL_REPRO_API")!=null?System.getenv("URL_OBTIENE_LOCAL_REPRO_API"):"http://ws-rest-sp-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/obtienelocalreproceso";
	public static String URL_EJECUTA_REPROCESO_API = System.getenv("URL_EJECUTA_REPROCESO_API")!=null?System.getenv("URL_EJECUTA_REPROCESO_API"):"http://ws-rest-sp-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/ejecutaenviarreproceso";
	public static String URL_REG_VTA_SAP_REPROCESO_API = System.getenv("URL_REG_VTA_SAP_REPROCESO_API")!=null?System.getenv("URL_REG_VTA_SAP_REPROCESO_API"):"http://ws-rest-sp-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/registraventasapreproceso";
	public static String URL_UP_TOLE_VTA_FICO_REPRO_API = System.getenv("URL_UP_TOLE_VTA_FICO_REPRO_API")!=null?System.getenv("URL_UP_TOLE_VTA_FICO_REPRO_API"):"http://ws-rest-sp-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/updtoleraventasdataficorepro";
	public static String URL_RECHAZAR_TOLERANCIA_API = System.getenv("URL_RECHAZAR_TOLERANCIA_API")!=null?System.getenv("URL_RECHAZAR_TOLERANCIA_API"):"http://ws-rest-sp-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/rechazartolerancia";
	public static String URL_RECHAZAR_TOLERANCIA_REPRO_API = System.getenv("URL_RECHAZAR_TOLERANCIA_REPRO_API")!=null?System.getenv("URL_RECHAZAR_TOLERANCIA_REPRO_API"):"http://ws-rest-sp-venta-desagregada-qa.spsa-api.lblapiqa.spsa.xyz/rechazartoleranciareproceso";
	
	public static String EMPTY_STRING = "";
	
	public static String URL_VENTA_DESAGREGADA = System.getenv("URL_VENTA_DESAGREGADA")!=null?System.getenv("URL_VENTA_DESAGREGADA"):"https://sp-bpm-venta-desagregada-qa.lblapiqa.spsa.xyz/camunda/app/tasklist/default/";
	public static String ID_INT_CORREO_SOPORTESAP = System.getenv("ID_INT_CORREO_SOPORTESAP")!=null?System.getenv("ID_INT_CORREO_SOPORTESAP"):"I0436-SOPORTESAP";
	public static String ID_INT_CORREO_SOPORTECT = System.getenv("ID_INT_CORREO_SOPORTECT")!=null?System.getenv("ID_INT_CORREO_SOPORTECT"):"I0436-SOPORTECT";
	public static String ID_INT_CORREO_USUARIO = System.getenv("ID_INT_CORREO_USUARIO")!=null?System.getenv("ID_INT_CORREO_USUARIO"):"I0436-USUARIO";
	public static String ID_INT_CORREO_REPROCESO = System.getenv("ID_INT_CORREO_REPROCESO")!=null?System.getenv("ID_INT_CORREO_REPROCESO"):"I0436-REPROCESO";
	
	public static String TITULO_CORREO_TOLERANCIAIGV = "[SPSA] Venta desagregada - Error en Tolerancia IGV";
	public static String TITULO_CORREO_OBSERVADOSAP = "[SPSA] Venta desagregada - Error en Contabilización SAP";
	public static String TITULO_CORREO_CONTROLTOTALES = "[SPSA] Venta desagregada - Error en Totales";
	public static String TITULO_CORREO_REPROCESO = "[SPSA] Venta desagregada - Reproceso de Locales";
	public static String ESTADO_REPRO = "R";
	
	public static String FORMAT_DOUBLE = "%.2f";
}

