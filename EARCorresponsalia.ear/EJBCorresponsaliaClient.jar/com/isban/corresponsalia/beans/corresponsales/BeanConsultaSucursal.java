package com.isban.corresponsalia.beans.corresponsales;

import java.io.Serializable;

/**
 * The Class BeanConsultaSucursal.
 */
public class BeanConsultaSucursal implements Serializable{
	
	/** The Constant serialVersionUID. */	
	private static final long serialVersionUID = -6858515588419827702L;

	/** The codigo corresponsalia. */
	private String codigoCorresponsalia = "";
	
	/** The codigo sucursal. */
	private String codigoSucursal = "";
	
	/** The id estatus suc. */
	private String idEstatusSuc = "";
	
	/** The region sucursal. */
	private String regionSucursal = "";
	
	/** The zona sucursal. */
	private String zonaSucursal = "";
	
	/** The indicador funcional. */
	private String indicadorFuncional = "";
	
	/** The indicador paginacion. */
	private String indicadorPaginacion = "";
	
	/** The ind paginacion. */
	private String indPaginacion = "";
	
	/** The limite inferior consulta. */
	private String limiteInferiorConsulta = "";
	
	/** The limite superior consulta. */
	private String limiteSuperiorConsulta = "";
	
	/** The paginada. */
	private boolean paginada = true;
	
	/** The nombre sucursal pagina. */
	private String nombreSucursal = "";
	
	/** The nombre sucursal pagina. */
	private String nombreSucursalAtras = "";
	
	/**
	 * Checks if is paginada.
	 *
	 * @return true, if is paginada
	 */
	public boolean isPaginada() {
		return paginada;
	}
	
	/**
	 * Sets the paginada.
	 *
	 * @param paginada the new paginada
	 */
	public void setPaginada(boolean paginada) {
		this.paginada = paginada;
	}
	
	/**
	 * Gets the codigo corresponsalia.
	 *
	 * @return the codigo corresponsalia
	 */
	public String getCodigoCorresponsalia() {
		return codigoCorresponsalia;
	}
	
	/**
	 * Sets the codigo corresponsalia.
	 *
	 * @param codigoCorresponsalia the new codigo corresponsalia
	 */
	public void setCodigoCorresponsalia(String codigoCorresponsalia) {
		this.codigoCorresponsalia = codigoCorresponsalia;
	}
	
	/**
	 * Gets the codigo sucursal.
	 *
	 * @return the codigo sucursal
	 */
	public String getCodigoSucursal() {
		return codigoSucursal;
	}
	
	/**
	 * Sets the codigo sucursal.
	 *
	 * @param codigoSucursal the new codigo sucursal
	 */
	public void setCodigoSucursal(String codigoSucursal) {
		this.codigoSucursal = codigoSucursal;
	}
	
	/**
	 * Gets the id estatus suc.
	 *
	 * @return the id estatus suc
	 */
	public String getIdEstatusSuc() {
		return idEstatusSuc;
	}
	
	/**
	 * Sets the id estatus suc.
	 *
	 * @param idEstatusSuc the new id estatus suc
	 */
	public void setIdEstatusSuc(String idEstatusSuc) {
		this.idEstatusSuc = idEstatusSuc;
	}
	
	/**
	 * Gets the region sucursal.
	 *
	 * @return the region sucursal
	 */
	public String getRegionSucursal() {
		return regionSucursal;
	}
	
	/**
	 * Sets the region sucursal.
	 *
	 * @param regionSucursal the new region sucursal
	 */
	public void setRegionSucursal(String regionSucursal) {
		this.regionSucursal = regionSucursal;
	}
	
	/**
	 * Gets the zona sucursal.
	 *
	 * @return the zona sucursal
	 */
	public String getZonaSucursal() {
		return zonaSucursal;
	}
	
	/**
	 * Sets the zona sucursal.
	 *
	 * @param zonaSucursal the new zona sucursal
	 */
	public void setZonaSucursal(String zonaSucursal) {
		this.zonaSucursal = zonaSucursal;
	}
	
	/**
	 * Gets the indicador funcional.
	 *
	 * @return the indicador funcional
	 */
	public String getIndicadorFuncional() {
		return indicadorFuncional;
	}
	
	/**
	 * Sets the indicador funcional.
	 *
	 * @param indicadorFuncional the new indicador funcional
	 */
	public void setIndicadorFuncional(String indicadorFuncional) {
		this.indicadorFuncional = indicadorFuncional;
	}
	
	/**
	 * Gets the indicador paginacion.
	 *
	 * @return the indicador paginacion
	 */
	public String getIndicadorPaginacion() {
		return indicadorPaginacion;
	}
	
	/**
	 * Sets the indicador paginacion.
	 *
	 * @param indicadorPaginacion the new indicador paginacion
	 */
	public void setIndicadorPaginacion(String indicadorPaginacion) {
		this.indicadorPaginacion = indicadorPaginacion;
	}
	
	/**
	 * Gets the ind paginacion.
	 *
	 * @return the ind paginacion
	 */
	public String getIndPaginacion() {
		return indPaginacion;
	}
	
	/**
	 * Sets the ind paginacion.
	 *
	 * @param indPaginacion the new ind paginacion
	 */
	public void setIndPaginacion(String indPaginacion) {
		this.indPaginacion = indPaginacion;
	}
	
	/**
	 * Gets the limite inferior consulta.
	 *
	 * @return the limite inferior consulta
	 */
	public String getLimiteInferiorConsulta() {
		return limiteInferiorConsulta;
	}
	
	/**
	 * Sets the limite inferior consulta.
	 *
	 * @param limiteInferiorConsulta the new limite inferior consulta
	 */
	public void setLimiteInferiorConsulta(String limiteInferiorConsulta) {
		this.limiteInferiorConsulta = limiteInferiorConsulta;
	}
	
	/**
	 * Gets the limite superior consulta.
	 *
	 * @return the limite superior consulta
	 */
	public String getLimiteSuperiorConsulta() {
		return limiteSuperiorConsulta;
	}
	
	/**
	 * Sets the limite superior consulta.
	 *
	 * @param limiteSuperiorConsulta the new limite superior consulta
	 */
	public void setLimiteSuperiorConsulta(String limiteSuperiorConsulta) {
		this.limiteSuperiorConsulta = limiteSuperiorConsulta;
	}
	
	/**
	 * Gets the nombre sucursal pagina.
	 *
	 * @return the nombre sucursal pagina
	 */
	public String getNombreSucursal() {
		return nombreSucursal;
	}
	
	/**
	 * Sets the nombre sucursal pagina.
	 *
	 * @param nombreSucursal the new nombre sucursal pagina
	 */
	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}

	/**
	 * @return the nombreSucursalAtras
	 */
	public String getNombreSucursalAtras() {
		return nombreSucursalAtras;
	}

	/**
	 * @param nombreSucursalAtras the nombreSucursalAtras to set
	 */
	public void setNombreSucursalAtras(String nombreSucursalAtras) {
		this.nombreSucursalAtras = nombreSucursalAtras;
	}	
}
