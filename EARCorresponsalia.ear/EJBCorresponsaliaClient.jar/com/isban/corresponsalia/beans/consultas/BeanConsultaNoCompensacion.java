package com.isban.corresponsalia.beans.consultas;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class BeanConsultaNoCompensacion.
 */
public class BeanConsultaNoCompensacion implements  Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7170510219708726424L;
	
	/** The Id corresponsal. */
	private String IdCorresponsal;
	
	/** The Clave estado. */
	private String ClaveEstado;
	
	/** The Clave tipo oper. */
	private String ClaveTipoOper;
	
	/** The Fecha conciliacion. */
	private String FechaConciliacion;
	
	/** The Fecha compensacion. */
	private String FechaCompensacion;
	
	/** The Indicador pagina. */
	private String IndicadorPagina;

	/**
	 * Gets the id corresponsal.
	 *
	 * @return el idCorresponsal
	 */
	public String getIdCorresponsal() {
		return IdCorresponsal;
	}
	
	/**
	 * Sets the id corresponsal.
	 *
	 * @param idCorresponsal el idCorresponsal a establecer
	 */
	public void setIdCorresponsal(String idCorresponsal) {
		IdCorresponsal = idCorresponsal;
	}
	
	/**
	 * Gets the clave estado.
	 *
	 * @return el claveEstado
	 */
	public String getClaveEstado() {
		return ClaveEstado;
	}
	
	/**
	 * Sets the clave estado.
	 *
	 * @param claveEstado el claveEstado a establecer
	 */
	public void setClaveEstado(String claveEstado) {
		ClaveEstado = claveEstado;
	}
	
	/**
	 * Gets the clave tipo oper.
	 *
	 * @return el claveTipoOper
	 */
	public String getClaveTipoOper() {
		return ClaveTipoOper;
	}
	
	/**
	 * Sets the clave tipo oper.
	 *
	 * @param claveTipoOper el claveTipoOper a establecer
	 */
	public void setClaveTipoOper(String claveTipoOper) {
		ClaveTipoOper = claveTipoOper;
	}
	
	/**
	 * Gets the fecha conciliacion.
	 *
	 * @return el fechaConciliacion
	 */
	public String getFechaConciliacion() {
		return FechaConciliacion;
	}
	
	/**
	 * Sets the fecha conciliacion.
	 *
	 * @param fechaConciliacion el fechaConciliacion a establecer
	 */
	public void setFechaConciliacion(String fechaConciliacion) {
		FechaConciliacion = fechaConciliacion;
	}
	
	/**
	 * Gets the fecha compensacion.
	 *
	 * @return el fechaCompensacion
	 */
	public String getFechaCompensacion() {
		return FechaCompensacion;
	}
	
	/**
	 * Sets the fecha compensacion.
	 *
	 * @param fechaCompensacion el fechaCompensacion a establecer
	 */
	public void setFechaCompensacion(String fechaCompensacion) {
		FechaCompensacion = fechaCompensacion;
	}
	
	/**
	 * Gets the indicador pagina.
	 *
	 * @return el indicadorPagina
	 */
	public String getIndicadorPagina() {
		return IndicadorPagina;
	}
	
	/**
	 * Sets the indicador pagina.
	 *
	 * @param indicadorPagina el indicadorPagina a establecer
	 */
	public void setIndicadorPagina(String indicadorPagina) {
		IndicadorPagina = indicadorPagina;
	}
	
}
