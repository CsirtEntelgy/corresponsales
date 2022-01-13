package com.isban.corresponsalia.beans.consultas;

import java.io.Serializable;
import java.util.List;

import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.ebe.commons.interfaces.BeanResultBO;

// TODO: Auto-generated Javadoc
/**
 * The Class RBeanCorresponsalesConsultaCompensacion.
 */
public class RBeanCorresponsalesConsultaCompensacion  implements  BeanResultBO, Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 575370468542931076L;

	/** The cod error. */
	private String                 codError;
	
	/** The msg error. */
	private String                 msgError;
	
	/** The lista corresponsales. */
	private List<BeanCorresponsal> listaCorresponsales;
	
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
	 * Gets the lista corresponsales.
	 *
	 * @return el listaCorresponsales
	 */
	public List<BeanCorresponsal> getListaCorresponsales() {
		return listaCorresponsales;
	}

	/**
	 * Sets the lista corresponsales.
	 *
	 * @param listaCorresponsales el listaCorresponsales a establecer
	 */
	public void setListaCorresponsales(List<BeanCorresponsal> listaCorresponsales) {
		this.listaCorresponsales = listaCorresponsales;
	}

}
