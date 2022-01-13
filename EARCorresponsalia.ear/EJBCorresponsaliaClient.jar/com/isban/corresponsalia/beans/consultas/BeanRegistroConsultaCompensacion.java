package com.isban.corresponsalia.beans.consultas;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class BeanRegistroConsultaCompensacion.
 */
public class BeanRegistroConsultaCompensacion implements  Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4639640298639616230L;
	
	/** The Mensaje aviso. */
	private String MensajeAviso;
	
	/** The Descripcion. */
	private String Descripcion;
	
	/**
	 * Gets the mensaje aviso.
	 *
	 * @return el mensajeAviso
	 */
	public String getMensajeAviso() {
		return MensajeAviso;
	}
	
	/**
	 * Sets the mensaje aviso.
	 *
	 * @param mensajeAviso el mensajeAviso a establecer
	 */
	public void setMensajeAviso(String mensajeAviso) {
		MensajeAviso = mensajeAviso;
	}
	
	/**
	 * Gets the descripcion.
	 *
	 * @return el descripcion
	 */
	public String getDescripcion() {
		return Descripcion;
	}
	
	/**
	 * Sets the descripcion.
	 *
	 * @param descripcion el descripcion a establecer
	 */
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
}
