package com.isban.corresponsalia.beans.consultas;

import java.io.Serializable;
import java.util.List;

import com.isban.ebe.commons.interfaces.BeanResultBO;

// TODO: Auto-generated Javadoc
/**
 * The Class RBeanAnulacion.
 */
public class RBeanAnulacion  implements  BeanResultBO, Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 575370468542931076L;

	/** The cod error. */
	private String                 codError;
	
	/** The msg error. */
	private String                 msgError;
	
	/** The lista anulaciones exitosas. */
	transient private List<BeanRegistroConsultaBitacora> listaAnulacionesExitosas;
	
	/** The lista anulaciones erroneas. */
	transient private List<BeanRegistroConsultaBitacora> listaAnulacionesErroneas;
	
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
	 * Gets the lista anulaciones exitosas.
	 *
	 * @return el listaAnulacionesExitosas
	 */
	public List<BeanRegistroConsultaBitacora> getListaAnulacionesExitosas() {
		return listaAnulacionesExitosas;
	}

	/**
	 * Sets the lista anulaciones exitosas.
	 *
	 * @param listaAnulacionesExitosas el listaAnulacionesExitosas a establecer
	 */
	public void setListaAnulacionesExitosas(
			List<BeanRegistroConsultaBitacora> listaAnulacionesExitosas) {
		this.listaAnulacionesExitosas = listaAnulacionesExitosas;
	}

	/**
	 * Gets the lista anulaciones erroneas.
	 *
	 * @return el listaAnulacionesErroneas
	 */
	public List<BeanRegistroConsultaBitacora> getListaAnulacionesErroneas() {
		return listaAnulacionesErroneas;
	}

	/**
	 * Sets the lista anulaciones erroneas.
	 *
	 * @param listaAnulacionesErroneas el listaAnulacionesErroneas a establecer
	 */
	public void setListaAnulacionesErroneas(
			List<BeanRegistroConsultaBitacora> listaAnulacionesErroneas) {
		this.listaAnulacionesErroneas = listaAnulacionesErroneas;
	}

}
