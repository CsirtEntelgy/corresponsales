package com.isban.corresponsalia.beans.monitoreo;

import java.io.Serializable;
import java.util.List;

import com.isban.corresponsalia.beans.comunes.BeanOperacion;
import com.isban.corresponsalia.beans.comunes.BeanOperacionCatalogo;
import com.isban.corresponsalia.beans.comunes.BeanSucursal;
import com.isban.ebe.commons.interfaces.BeanResultBO;

// TODO: Auto-generated Javadoc
/**
 * The Class RBeanOperacionesSucursalesMonitoreoOperaciones.
 */
public class RBeanOperacionesSucursalesMonitoreoOperaciones  implements  BeanResultBO, Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 575370468542931076L;

	/** The cod error. */
	private String                      codError;
	
	/** The msg error. */
	private String                      msgError;
	
	/** The lista sucursales. */
	private List<BeanSucursal>          listaSucursales;
	
	/** The lista operaciones. */
	private List<BeanOperacionCatalogo> listaOperaciones;
	
	/** The lista operaciones corres. */
	private List<BeanOperacion> listaOperacionesCorres;
	
	/* (sin Javadoc)
	 * @see com.isban.ebe.commons.interfaces.BeanResultBO#getCodError()
	 */
	@Override
	public String getCodError() {
		return codError;
	}

	/* (sin Javadoc)
	 * @see com.isban.ebe.commons.interfaces.BeanResultBO#getMsgError()
	 */
	@Override
	public String getMsgError() {
		return msgError;
	}
	
	/* (sin Javadoc)
	 * @see com.isban.ebe.commons.interfaces.BeanResultBO#setMsgError(java.lang.String)
	 */
	@Override
	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}

	/* (sin Javadoc)
	 * @see com.isban.ebe.commons.interfaces.BeanResultBO#setCodError(java.lang.String)
	 */
	@Override
	public void setCodError(String codError) {
		this.codError = codError;
	}


	/**
	 * Gets the lista sucursales.
	 *
	 * @return el listaSucursales
	 */
	public List<BeanSucursal> getListaSucursales() {
		return listaSucursales;
	}

	/**
	 * Sets the lista sucursales.
	 *
	 * @param listaSucursales el listaSucursales a establecer
	 */
	public void setListaSucursales(List<BeanSucursal> listaSucursales) {
		this.listaSucursales = listaSucursales;
	}

	/**
	 * Gets the lista operaciones.
	 *
	 * @return el listaOperaciones
	 */
	public List<BeanOperacionCatalogo> getListaOperaciones() {
		return listaOperaciones;
	}

	/**
	 * Sets the lista operaciones.
	 *
	 * @param listaOperaciones el listaOperaciones a establecer
	 */
	public void setListaOperaciones(List<BeanOperacionCatalogo> listaOperaciones) {
		this.listaOperaciones = listaOperaciones;
	}

	/**
	 * Sets the lista operaciones corres.
	 *
	 * @param listaOperacionesCorres the new lista operaciones corres
	 */
	public void setListaOperacionesCorres(List<BeanOperacion> listaOperacionesCorres) {
		this.listaOperacionesCorres = listaOperacionesCorres;
	}

	/**
	 * Gets the lista operaciones corres.
	 *
	 * @return the lista operaciones corres
	 */
	public List<BeanOperacion> getListaOperacionesCorres() {
		return listaOperacionesCorres;
	}

}
