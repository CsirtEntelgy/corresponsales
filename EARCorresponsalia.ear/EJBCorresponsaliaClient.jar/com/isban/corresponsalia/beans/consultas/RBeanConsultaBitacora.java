package com.isban.corresponsalia.beans.consultas;

import java.io.Serializable;
import java.util.ArrayList;

import com.isban.ebe.commons.interfaces.BeanResultBO;

// TODO: Auto-generated Javadoc
/**
 * The Class RBeanConsultaBitacora.
 */
public class RBeanConsultaBitacora  implements  BeanResultBO, Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 575370468542931076L;

	/** The lista registros consulta bitacora. */
	private ArrayList<BeanRegistroConsultaBitacora> listaRegistrosConsultaBitacora;
	
	/** The cod error. */
	private String                                  codError;
	
	/** The msg error. */
	private String                                  msgError;
	
	/** The cod aviso. */
	private String 									codAviso;
	
	/** The msg aviso. */
	private String                                  msgAviso;
	
	/**
	 * Gets the cod aviso.
	 *
	 * @return the cod aviso
	 */
	public String getCodAviso() {
		return codAviso;
	}

	/**
	 * Sets the cod aviso.
	 *
	 * @param codAviso the new cod aviso
	 */
	public void setCodAviso(String codAviso) {
		this.codAviso = codAviso;
	}

	/**
	 * Gets the msg aviso.
	 *
	 * @return the msg aviso
	 */
	public String getMsgAviso() {
		return msgAviso;
	}

	/**
	 * Sets the msg aviso.
	 *
	 * @param msgAviso the new msg aviso
	 */
	public void setMsgAviso(String msgAviso) {
		this.msgAviso = msgAviso;
	}

	/** The referencia avanzar. */
	private String                                  referenciaAvanzar;
	
	/** The referencia retroceder. */
	private String                                  referenciaRetroceder;

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
	 * Gets the lista registros consulta bitacora.
	 *
	 * @return el listaRegistrosConsultaBitacora
	 */
	public ArrayList<BeanRegistroConsultaBitacora> getListaRegistrosConsultaBitacora() {
		return listaRegistrosConsultaBitacora;
	}

	/**
	 * Sets the lista registros consulta bitacora.
	 *
	 * @param listaRegistrosConsultaBitacora el listaRegistrosConsultaBitacora a establecer
	 */
	public void setListaRegistrosConsultaBitacora(
			ArrayList<BeanRegistroConsultaBitacora> listaRegistrosConsultaBitacora) {
		this.listaRegistrosConsultaBitacora = listaRegistrosConsultaBitacora;
	}

	/**
	 * Gets the referencia avanzar.
	 *
	 * @return el referenciaAvanzar
	 */
	public String getReferenciaAvanzar() {
		return referenciaAvanzar;
	}

	/**
	 * Sets the referencia avanzar.
	 *
	 * @param referenciaAvanzar el referenciaAvanzar a establecer
	 */
	public void setReferenciaAvanzar(String referenciaAvanzar) {
		this.referenciaAvanzar = referenciaAvanzar;
	}

	/**
	 * Gets the referencia retroceder.
	 *
	 * @return el referenciaRetroceder
	 */
	public String getReferenciaRetroceder() {
		return referenciaRetroceder;
	}

	/**
	 * Sets the referencia retroceder.
	 *
	 * @param referenciaRetroceder el referenciaRetroceder a establecer
	 */
	public void setReferenciaRetroceder(String referenciaRetroceder) {
		this.referenciaRetroceder = referenciaRetroceder;
	}

}
