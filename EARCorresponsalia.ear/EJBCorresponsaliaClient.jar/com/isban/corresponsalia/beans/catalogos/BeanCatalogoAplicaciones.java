package com.isban.corresponsalia.beans.catalogos;

import java.io.Serializable;
import java.util.List;

/**
 * Stefanini® 2015
 * Clase de guardado de acceso a variables para el catalogo de aplicaciones
 * @author Irvin Misael Herrera Chávez
 */
public class BeanCatalogoAplicaciones implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3387776618350837579L;

	/**
	 * Acceso a la variable idAplicacion
	 */
	private String idAplicacion;
	
	/**
	 * Acceso a la variable descripcion
	 */
	private String descripcion;
	
	/**
	 * Acceso a la lista de variables del BeanCatalogoAplicaciones
	 */
	private List<BeanCatalogoAplicaciones> listaBeanCatalogoAplicaciones;
	
	/**
	 * Acceso a la variable errorCode
	 */
	private String errorCode;
	
	/**
	 * Acceso a la variable errorDesc
	 */
	private String errorDesc;

	/**
	 * Devuelve el valor de idAplicacion
	 * @return idAplicacion
	 */
	public String getIdAplicacion() {
		return idAplicacion;
	}

	/**
	 * @param idAplicacion Asigna el valor de idAplicacion
	 */
	public void setIdAplicacion(String idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	/**
	 * 
	 * @return descripcion Devuelve el valor de descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * 
	 * @param descripcion Asigna el valor de descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	/**
	 * 
	 * @return listaBeanCatalogoAplicaciones Devuelve el valor de listaBeanCatalogoAplicaciones
	 */
	public List<BeanCatalogoAplicaciones> getListaBeanCatalogoAplicaciones() {
		return listaBeanCatalogoAplicaciones;
	}

	/**
	 * 
	 * @param listaBeanCatalogoAplicaciones Asigna el valor de listaBeanCatalogoAplicaciones
	 */
	public void setListaBeanCatalogoAplicaciones(
			List<BeanCatalogoAplicaciones> listaBeanCatalogoAplicaciones) {
		this.listaBeanCatalogoAplicaciones = listaBeanCatalogoAplicaciones;
	}

	/**
	 * 
	 * @return errorCode Devuelve el valor de errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * 
	 * @param errorCode Asigna el valor de errorCode
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 
	 * @return errorDesc Devuelve el valor de errorDesc
	 */
	public String getErrorDesc() {
		return errorDesc;
	}

	/**
	 * 
	 * @param errorDesc Asigna el valor de errorDesc
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	
}
