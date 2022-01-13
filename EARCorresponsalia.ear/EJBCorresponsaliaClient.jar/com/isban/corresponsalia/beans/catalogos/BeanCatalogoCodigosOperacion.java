package com.isban.corresponsalia.beans.catalogos;

import java.io.Serializable;
import java.util.List;

/**
 * Stefanini® 2015
 * Clase de guardado de acceso a variables para el catalogo de 
 * codigos de operacion 
 * @author Irvin Misael Herrera Chávez
 */
public class BeanCatalogoCodigosOperacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1831277871662234574L;

	/**
	 * Acceso a la variable idAplicacion
	 */
	private String idAplicacion;
	
	/**
	 * Acceso a la variable codigoOperacion
	 */
	private String codigoOperacion;
	
	/**
	 * Acceso a la variable descripcionCodigo
	 */
	private String descripcionCodigoOperacion;
	
	/**
	 * Acceso a la variable codigoIso
	 */
	private String codigoIso;
	
	/**
	 * Acceso a la variable descripcionIso
	 */
	private String descripcionIso;
	
	/**
	 * Acceso a la variable listaBeanCatalogoCodigosOperacion
	 */
	private List<BeanCatalogoCodigosOperacion> listaBeanCatalogoCodigosOperacion;
	
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
	 * 
	 * @param idAplicacion Asigna el valor de idAplicacion
	 */
	public void setIdAplicacion(String idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	/**
	 * Devuelve el valor de codigoOperacion
	 * @return codigoOperacion
	 */
	public String getCodigoOperacion() {
		return codigoOperacion;
	}

	/**
	 * 
	 * @param codigoOperacion Asigna el valor de codigoOperacion
	 */
	public void setCodigoOperacion(String codigoOperacion) {
		this.codigoOperacion = codigoOperacion;
	}

	/**
	 * 
	 * @return descripcionCodigo Devuelve el valor de descripcionCodigo
	 */
	public String getDescripcionCodigoOperacion() {
		return descripcionCodigoOperacion;
	}

	/**
	 * 
	 * @param descripcionCodigo Asigna el valor de descripcionCodigo
	 */
	public void setDescripcionCodigoOperacion(String descripcionCodigoOperacion) {
		this.descripcionCodigoOperacion = descripcionCodigoOperacion;
	}

	/**
	 * Devuelve el valor de codigoIso
	 * @return codigoIso
	 */
	public String getCodigoIso() {
		return codigoIso;
	}

	/**
	 * 
	 * @param codigoIso Asigna el valor de codigoIso
	 */
	public void setCodigoIso(String codigoIso) {
		this.codigoIso = codigoIso;
	}

	/**
	 * Devuelve el valor de descripcionIso
	 * @return descripcionIso
	 */
	public String getDescripcionIso() {
		return descripcionIso;
	}

	/**
	 * 
	 * @param descripcionIso Asigna el valor de descripcionIso
	 */
	public void setDescripcionIso(String descripcionIso) {
		this.descripcionIso = descripcionIso;
	}
		
	/**
	 * Devuelve el valor de listaBeanCatalogoCodigosOperacion
	 * @return listaBeanCatalogoCodigosOperacion
	 */
	public List<BeanCatalogoCodigosOperacion> getListaBeanCatalogoCodigosOperacion() {
		return listaBeanCatalogoCodigosOperacion;
	}

	/**
	 * 
	 * @param listaBeanCatalogoCodigosOperacion Asigna el valor de listaBeanCatalogoCodigosOperacion
	 */
	public void setListaBeanCatalogoCodigosOperacion(
			List<BeanCatalogoCodigosOperacion> listaBeanCatalogoCodigosOperacion) {
		this.listaBeanCatalogoCodigosOperacion = listaBeanCatalogoCodigosOperacion;
	}

	/**
	 * Devuelve el valor de errorCode
	 * @return errorCode
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
	 * Devuelve el valor de errorDesc
	 * @return errorDesc
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
