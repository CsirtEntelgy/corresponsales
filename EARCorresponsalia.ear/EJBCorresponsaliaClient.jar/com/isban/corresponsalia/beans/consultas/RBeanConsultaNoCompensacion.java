package com.isban.corresponsalia.beans.consultas;

import java.io.Serializable;
import java.util.ArrayList;

import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.ebe.commons.interfaces.BeanResultBO;

// TODO: Auto-generated Javadoc
/**
 * The Class RBeanConsultaNoCompensacion.
 */
public class RBeanConsultaNoCompensacion  implements  BeanResultBO, Serializable{


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7110631550320332087L;
	
	/** The cod error. */
	private String                                        codError;
	
	/** The msg error. */
	private String                                        msgError;
	
	/** The lista registros consulta no compensacion. */
	private ArrayList<BeanRegistroConsultaNoCompensacion> listaRegistrosConsultaNoCompensacion;
	
	/** The detalle corresponsal. */
	private BeanCorresponsal                              detalleCorresponsal; 
	
	/** The fecha inferior. */
	private String                                        fechaInferior;
	
	/** The fecha superior. */
	private String                                        fechaSuperior;

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
	 * Gets the lista registros consulta no compensacion.
	 *
	 * @return el listaRegistrosConsultaCompensacion
	 */
	public ArrayList<BeanRegistroConsultaNoCompensacion> getListaRegistrosConsultaNoCompensacion() {
		return listaRegistrosConsultaNoCompensacion;
	}

	/**
	 * Sets the lista registros consulta no compensacion.
	 *
	 * @param listaRegistrosConsultaNoCompensacion the new lista registros consulta no compensacion
	 */
	public void setListaRegistrosConsultaNoCompensacion(
			ArrayList<BeanRegistroConsultaNoCompensacion> listaRegistrosConsultaNoCompensacion) {
		this.listaRegistrosConsultaNoCompensacion = listaRegistrosConsultaNoCompensacion;
	}

	/**
	 * Gets the detalle corresponsal.
	 *
	 * @return el detalleCorresponsal
	 */
	public BeanCorresponsal getDetalleCorresponsal() {
		return detalleCorresponsal;
	}

	/**
	 * Sets the detalle corresponsal.
	 *
	 * @param detalleCorresponsal el detalleCorresponsal a establecer
	 */
	public void setDetalleCorresponsal(BeanCorresponsal detalleCorresponsal) {
		this.detalleCorresponsal = detalleCorresponsal;
	}

	/**
	 * Gets the fecha inferior.
	 *
	 * @return el fechaInferior
	 */
	public String getFechaInferior() {
		return fechaInferior;
	}

	/**
	 * Sets the fecha inferior.
	 *
	 * @param fechaInferior el fechaInferior a establecer
	 */
	public void setFechaInferior(String fechaInferior) {
		this.fechaInferior = fechaInferior;
	}

	/**
	 * Gets the fecha superior.
	 *
	 * @return el fechaSuperior
	 */
	public String getFechaSuperior() {
		return fechaSuperior;
	}

	/**
	 * Sets the fecha superior.
	 *
	 * @param fechaSuperior el fechaSuperior a establecer
	 */
	public void setFechaSuperior(String fechaSuperior) {
		this.fechaSuperior = fechaSuperior;
	}

	
}
