package com.isban.corresponsalia.beans.consultas;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class BeanConsultaCompensacion.
 */
public class BeanConsultaCompensacion implements  Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8472372413106870556L;
	
	/** The Id entidad. */
	private String IdEntidad; 
	
	/** The Codigo tabla. */
	private String CodigoTabla;
	
	/** The Idioma tabla. */
	private String IdiomaTabla;
	
	/** The Clave registro. */
	private String ClaveRegistro;
	
	/** The Catalogo almacenamiento. */
	private String CatalogoAlmacenamiento;
	
	/**
	 * Gets the id entidad.
	 *
	 * @return el idEntidad
	 */
	public String getIdEntidad() {
		return IdEntidad;
	}
	
	/**
	 * Sets the id entidad.
	 *
	 * @param idEntidad el idEntidad a establecer
	 */
	public void setIdEntidad(String idEntidad) {
		IdEntidad = idEntidad;
	}
	
	/**
	 * Gets the codigo tabla.
	 *
	 * @return el codigoTabla
	 */
	public String getCodigoTabla() {
		return CodigoTabla;
	}
	
	/**
	 * Sets the codigo tabla.
	 *
	 * @param codigoTabla el codigoTabla a establecer
	 */
	public void setCodigoTabla(String codigoTabla) {
		CodigoTabla = codigoTabla;
	}
	
	/**
	 * Gets the idioma tabla.
	 *
	 * @return el idiomaTabla
	 */
	public String getIdiomaTabla() {
		return IdiomaTabla;
	}
	
	/**
	 * Sets the idioma tabla.
	 *
	 * @param idiomaTabla el idiomaTabla a establecer
	 */
	public void setIdiomaTabla(String idiomaTabla) {
		IdiomaTabla = idiomaTabla;
	}
	
	/**
	 * Gets the clave registro.
	 *
	 * @return el claveRegistro
	 */
	public String getClaveRegistro() {
		return ClaveRegistro;
	}
	
	/**
	 * Sets the clave registro.
	 *
	 * @param claveRegistro el claveRegistro a establecer
	 */
	public void setClaveRegistro(String claveRegistro) {
		ClaveRegistro = claveRegistro;
	}
	
	/**
	 * Gets the catalogo almacenamiento.
	 *
	 * @return el catalogoAlmacenamiento
	 */
	public String getCatalogoAlmacenamiento() {
		return CatalogoAlmacenamiento;
	}
	
	/**
	 * Sets the catalogo almacenamiento.
	 *
	 * @param catalogoAlmacenamiento el catalogoAlmacenamiento a establecer
	 */
	public void setCatalogoAlmacenamiento(String catalogoAlmacenamiento) {
		CatalogoAlmacenamiento = catalogoAlmacenamiento;
	}
	

}
