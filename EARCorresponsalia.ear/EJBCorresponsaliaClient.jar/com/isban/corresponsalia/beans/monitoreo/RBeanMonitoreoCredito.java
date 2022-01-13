package com.isban.corresponsalia.beans.monitoreo;

import java.io.Serializable;
import java.util.ArrayList;

import com.isban.ebe.commons.interfaces.BeanResultBO;

// TODO: Auto-generated Javadoc
/**
 * The Class RBeanMonitoreoCredito.
 */
public class RBeanMonitoreoCredito  implements  BeanResultBO, Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 575370468542931076L;

	/** The lista registros monitoreo credito. */
	private ArrayList<BeanRegistroMonitoreoCredito> listaRegistrosMonitoreoCredito;
	
	/** The cod error. */
	private String                                  codError;
	
	/** The msg error. */
	private String                                  msgError;

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
	 * @see com.isban.ebe.commons.interfaces.BeanResultBO#setCodError(java.lang.String)
	 */
	@Override
	public void setCodError(String codError) {
		this.codError = codError;
	}

	/* (sin Javadoc)
	 * @see com.isban.ebe.commons.interfaces.BeanResultBO#setMsgError(java.lang.String)
	 */
	@Override
	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}

	/**
	 * Gets the lista registros monitoreo credito.
	 *
	 * @return el listaRegistrosMonitoreoCredito
	 */
	public ArrayList<BeanRegistroMonitoreoCredito> getListaRegistrosMonitoreoCredito() {
		return listaRegistrosMonitoreoCredito;
	}

	/**
	 * Sets the lista registros monitoreo credito.
	 *
	 * @param listaRegistrosMonitoreoCredito el listaRegistrosMonitoreoCredito a establecer
	 */
	public void setListaRegistrosMonitoreoCredito(
			ArrayList<BeanRegistroMonitoreoCredito> listaRegistrosMonitoreoCredito) {
		this.listaRegistrosMonitoreoCredito = listaRegistrosMonitoreoCredito;
	}

}
