package com.isban.corresponsalia.beans.comunes;

import java.io.Serializable;


// TODO: Auto-generated Javadoc
/**
 * The Class BeanSucursal.
 */
public class BeanSucursal implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6858515588419827702L;
	
	/** The linea. */
	private int linea; 					//solo para alta masiva.
	
	/** The entidad. */
	private String entidad;
	
	/** The canal. */
	private String canal;
	
	/** The id corresponsal. */
	private String idCorresponsal = ""; //identificacion del Corresponsal
	
	/** The nombre sucursal. */
	private String nombreSucursal = ""; //nombre de la sucursal
	
	/** The num id. */
	private String numId = "";			//codigo de sucursal
	
	/** The codigo estatus. */
	private String codigoEstatus = "";  //identificador de estatus
	
	/** The desc estatus. */
	private String descEstatus= "";		//descripcion de estatus DLA6
	
	/** The rfc. */
	private String rfc = "";			//rfc de la sucursal
	
	/** The codigo interno. */
	private String codigoInterno = "";	//codigo Interno
	
	/** The telefono. */
	private String telefono = "";
	
	/** The calle. */
	private String calle = "";
	
	/** The no exterior. */
	private String noExterior = "";
	
	/** The no interior. */
	private String noInterior = "";
	
	/** The colonia. */
	private String colonia = "";
	
	/** The entre calles. */
	private String entreCalles = "";
	
	/** The deleg municipio. */
	private String delegMunicipio = "";
	
	/** The ciudad. */
	private String ciudad = "";
	
	/** The estado. */
	private String estado = "";
	
	/** The codigo postal. */
	private String codigoPostal = "";
	
	/** The fronteriza. */
	private String fronteriza = "";
	
	/** The zona geografica. */
	private String zonaGeografica = "";
	
	/** The desc zona. */
	private String descZona = "";
	
	/** The region. */
	private String region = "";
	
	/** The id operacion. */
	private String idOperacion = "";	//Operación a realizar A = Alta, B = Baja y M = Modificación
	
	/**
	 * Gets the linea.
	 *
	 * @return the linea
	 */
	public int getLinea() {
		return linea;
	}
	
	/**
	 * Sets the linea.
	 *
	 * @param linea the new linea
	 */
	public void setLinea(int linea) {
		this.linea = linea;
	}
	
	/**
	 * Gets the entidad.
	 *
	 * @return the entidad
	 */
	public String getEntidad() {
		return entidad;
	}
	
	/**
	 * Sets the entidad.
	 *
	 * @param entidad the new entidad
	 */
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	
	/**
	 * Gets the canal.
	 *
	 * @return the canal
	 */
	public String getCanal() {
		return canal;
	}
	
	/**
	 * Sets the canal.
	 *
	 * @param canal the new canal
	 */
	public void setCanal(String canal) {
		this.canal = canal;
	}
	
	/**
	 * Gets the id corresponsal.
	 *
	 * @return the id corresponsal
	 */
	public String getIdCorresponsal() {
		return idCorresponsal;
	}
	
	/**
	 * Sets the id corresponsal.
	 *
	 * @param idCorresponsal the new id corresponsal
	 */
	public void setIdCorresponsal(String idCorresponsal) {
		this.idCorresponsal = idCorresponsal;
	}
	
	/**
	 * Gets the nombre sucursal.
	 *
	 * @return the nombre sucursal
	 */
	public String getNombreSucursal() {
		return nombreSucursal;
	}
	
	/**
	 * Sets the nombre sucursal.
	 *
	 * @param nombreSucursal the new nombre sucursal
	 */
	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}
	
	/**
	 * Gets the num id.
	 *
	 * @return the num id
	 */
	public String getNumId() {
		return numId;
	}
	
	/**
	 * Sets the num id.
	 *
	 * @param numId the new num id
	 */
	public void setNumId(String numId) {
		this.numId = numId;
	}
	
	/**
	 * Gets the codigo estatus.
	 *
	 * @return the codigo estatus
	 */
	public String getCodigoEstatus() {
		return codigoEstatus;
	}
	
	/**
	 * Sets the codigo estatus.
	 *
	 * @param codigoEstatus the new codigo estatus
	 */
	public void setCodigoEstatus(String codigoEstatus) {
		this.codigoEstatus = codigoEstatus;
	}
	
	/**
	 * Gets the desc estatus.
	 *
	 * @return the desc estatus
	 */
	public String getDescEstatus() {
		return descEstatus;
	}
	
	/**
	 * Sets the desc estatus.
	 *
	 * @param descEstatus the new desc estatus
	 */
	public void setDescEstatus(String descEstatus) {
		this.descEstatus = descEstatus;
	}
	
	/**
	 * Gets the rfc.
	 *
	 * @return the rfc
	 */
	public String getRfc() {
		return rfc;
	}
	
	/**
	 * Sets the rfc.
	 *
	 * @param rfc the new rfc
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
	/**
	 * Gets the codigo interno.
	 *
	 * @return the codigo interno
	 */
	public String getCodigoInterno() {
		return codigoInterno;
	}
	
	/**
	 * Sets the codigo interno.
	 *
	 * @param codigoInterno the new codigo interno
	 */
	public void setCodigoInterno(String codigoInterno) {
		this.codigoInterno = codigoInterno;
	}
	
	/**
	 * Gets the telefono.
	 *
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}
	
	/**
	 * Sets the telefono.
	 *
	 * @param telefono the new telefono
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	/**
	 * Gets the calle.
	 *
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}
	
	/**
	 * Sets the calle.
	 *
	 * @param calle the new calle
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}
	
	/**
	 * Gets the no interior.
	 *
	 * @return the no interior
	 */
	public String getNoInterior() {
		return noInterior;
	}
	
	/**
	 * Sets the no interior.
	 *
	 * @param noInterior the new no interior
	 */
	public void setNoInterior(String noInterior) {
		this.noInterior = noInterior;
	}
	
	/**
	 * Gets the no exterior.
	 *
	 * @return the no exterior
	 */
	public String getNoExterior() {
		return noExterior;
	}
	
	/**
	 * Sets the no exterior.
	 *
	 * @param noExterior the new no exterior
	 */
	public void setNoExterior(String noExterior) {
		this.noExterior = noExterior;
	}
	
	/**
	 * Gets the colonia.
	 *
	 * @return the colonia
	 */
	public String getColonia() {
		return colonia;
	}
	
	/**
	 * Sets the colonia.
	 *
	 * @param colonia the new colonia
	 */
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	
	/**
	 * Gets the entre calles.
	 *
	 * @return the entre calles
	 */
	public String getEntreCalles() {
		return entreCalles;
	}
	
	/**
	 * Sets the entre calles.
	 *
	 * @param entreCalles the new entre calles
	 */
	public void setEntreCalles(String entreCalles) {
		this.entreCalles = entreCalles;
	}
	
	/**
	 * Gets the deleg municipio.
	 *
	 * @return the deleg municipio
	 */
	public String getDelegMunicipio() {
		return delegMunicipio;
	}
	
	/**
	 * Sets the deleg municipio.
	 *
	 * @param delegMunicipio the new deleg municipio
	 */
	public void setDelegMunicipio(String delegMunicipio) {
		this.delegMunicipio = delegMunicipio;
	}
	
	/**
	 * Gets the ciudad.
	 *
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}
	
	/**
	 * Sets the ciudad.
	 *
	 * @param ciudad the new ciudad
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	/**
	 * Gets the estado.
	 *
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	
	/**
	 * Sets the estado.
	 *
	 * @param estado the new estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	/**
	 * Gets the codigo postal.
	 *
	 * @return the codigo postal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}
	
	/**
	 * Sets the codigo postal.
	 *
	 * @param codigoPostal the new codigo postal
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	
	/**
	 * Gets the fronteriza.
	 *
	 * @return the fronteriza
	 */
	public String getFronteriza() {
		return fronteriza;
	}
	
	/**
	 * Sets the fronteriza.
	 *
	 * @param fronteriza the new fronteriza
	 */
	public void setFronteriza(String fronteriza) {
		this.fronteriza = fronteriza;
	}
	
	/**
	 * Gets the zona geografica.
	 *
	 * @return the zona geografica
	 */
	public String getZonaGeografica() {
		return zonaGeografica;
	}
	
	/**
	 * Sets the zona geografica.
	 *
	 * @param zonaGeografica the new zona geografica
	 */
	public void setZonaGeografica(String zonaGeografica) {
		this.zonaGeografica = zonaGeografica;
	}
	
	/**
	 * Gets the desc zona.
	 *
	 * @return the desc zona
	 */
	public String getDescZona() {
		return descZona;
	}
	
	/**
	 * Sets the desc zona.
	 *
	 * @param descZona the new desc zona
	 */
	public void setDescZona(String descZona) {
		this.descZona = descZona;
	}
	
	/**
	 * Gets the region.
	 *
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	
	/**
	 * Sets the region.
	 *
	 * @param region the new region
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	
	/**
	 * Gets the id operacion.
	 *
	 * @return the id operacion
	 */
	public String getIdOperacion() {
		return idOperacion;
	}
	
	/**
	 * Sets the id operacion.
	 *
	 * @param idOperacion the new id operacion
	 */
	public void setIdOperacion(String idOperacion) {
		this.idOperacion = idOperacion;
	}	

}
