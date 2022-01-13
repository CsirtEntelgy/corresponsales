package com.isban.corresponsalia.beans.monitoreo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class BeanMonitoreoCredito.
 */
public class BeanMonitoreoCredito implements  Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -9210373263214191373L;
	
	/** The Id canal corresponsalia. */
	String IdCanalCorresponsalia;
	
	/**
	 * Instantiates a new bean monitoreo credito.
	 */
	public BeanMonitoreoCredito() {
		// TODO Apéndice de constructor generado automáticamente
		this.IdCanalCorresponsalia = "";
	}

	/**
	 * Instantiates a new bean monitoreo credito.
	 *
	 * @param canal the canal
	 */
	public BeanMonitoreoCredito(String canal) {
		this.IdCanalCorresponsalia=canal;// TODO Apéndice de constructor generado automáticamente
	}

	/**
	 * Gets the id canal corresponsalia.
	 *
	 * @return el idCorresponsasl
	 */
	public String getIdCanalCorresponsalia() {
		return IdCanalCorresponsalia;
	}

	/**
	 * Sets the id canal corresponsalia.
	 *
	 * @param idCanalCorresponsalia the new id canal corresponsalia
	 */
	public void setIdCanalCorresponsalia(String idCanalCorresponsalia) {
		IdCanalCorresponsalia = idCanalCorresponsalia;
	}

}
