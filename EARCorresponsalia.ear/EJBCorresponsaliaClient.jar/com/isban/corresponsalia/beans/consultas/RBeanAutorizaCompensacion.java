package com.isban.corresponsalia.beans.consultas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.isban.ebe.commons.interfaces.BeanResultBO;

// TODO: Auto-generated Javadoc
/**
 * The Class RBeanAutorizaCompensacion.
 */
public class RBeanAutorizaCompensacion  implements  BeanResultBO, Serializable{


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 247084199264296290L;
	
	/** The cod error. */
	private String                                  codError;
	
	/** The msg error. */
	private String                                  msgError;
	
	/** The lista registros autorizados. */
	private List<BeanRegistroConsultaNoCompensacion> listaRegistrosAutorizados   = new ArrayList<BeanRegistroConsultaNoCompensacion>();
	
	/** The lista registros no autorizados. */
	private List<BeanRegistroConsultaNoCompensacion> listaRegistrosNoAutorizados = new ArrayList<BeanRegistroConsultaNoCompensacion>();
	

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
	 * Gets the lista registros autorizados.
	 *
	 * @return el listaRegistrosAutorizados
	 */
	public List<BeanRegistroConsultaNoCompensacion> getListaRegistrosAutorizados() {
		return listaRegistrosAutorizados;
	}

	/**
	 * Sets the lista registros autorizados.
	 *
	 * @param listaRegistrosAutorizados el listaRegistrosAutorizados a establecer
	 */
	public void setListaRegistrosAutorizados(
			List<BeanRegistroConsultaNoCompensacion> listaRegistrosAutorizados) {
		this.listaRegistrosAutorizados = listaRegistrosAutorizados;
	}

	/**
	 * Gets the lista registros no autorizados.
	 *
	 * @return el listaRegistrosNoAutorizados
	 */
	public List<BeanRegistroConsultaNoCompensacion> getListaRegistrosNoAutorizados() {
		return listaRegistrosNoAutorizados;
	}

	/**
	 * Sets the lista registros no autorizados.
	 *
	 * @param listaRegistrosNoAutorizados el listaRegistrosNoAutorizados a establecer
	 */
	public void setListaRegistrosNoAutorizados(
			List<BeanRegistroConsultaNoCompensacion> listaRegistrosNoAutorizados) {
		this.listaRegistrosNoAutorizados = listaRegistrosNoAutorizados;
	}

	
}
