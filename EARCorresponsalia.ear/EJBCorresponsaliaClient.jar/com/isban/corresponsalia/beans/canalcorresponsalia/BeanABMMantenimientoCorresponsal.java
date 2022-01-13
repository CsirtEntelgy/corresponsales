package com.isban.corresponsalia.beans.canalcorresponsalia;

import java.io.Serializable;


// TODO: Auto-generated Javadoc
/**
 * The Class BeanABMMantenimientoCorresponsal.
 */
public class BeanABMMantenimientoCorresponsal implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6858515588419827702L;
	
	/** The linea. */
	private int linea = 0;	//exclusivo para alta masiva
	
	/** The codigo corresponsalia. */
	private String codigoCorresponsalia = "";
	
	/** The codigo corresponsal. */
	private String codigoCorresponsal = "";
	
	/** The estatus corresponsal. */
	private String estatusCorresponsal = "";
	
	/** The codigo cliente. */
	private String codigoCliente = "";
	
	/** The secuencia domicilio. */
	private String secuenciaDomicilio = "";
	
	/** The giro empresa. */
	private String giroEmpresa = "";
	
	/** The nombre corresponsal. */
	private String nombreCorresponsal = "";
	
	/** The indicador conciliacion. */
	private String indicadorConciliacion = "";
	
	/** The param dias pendientes conciliar. */
	private String paramDiasPendientesConciliar = "";
	
	/** The param dias pendientes compensar. */
	private String paramDiasPendientesCompensar = "";
	
	/** The indicador valida comision. */
	private String indicadorValidaComision = "";
	
	/** The cuenta cheques corresponsal. */
	private String cuentaChequesCorresponsal = "";
	
	/** The divisa cuenta cheques. */
	private String divisaCuentaCheques = "";
	
	/** The linea credito corresponsal. */
	private String lineaCreditoCorresponsal = "";
	
	/** The divisa asoc linea credito. */
	private String divisaAsocLineaCredito = "";
	
	/** The limite importe corresponsal. */
	private String limiteImporteCorresponsal = "";
	
	/** The limite importe corresponsal front. */
	private String limiteImporteCorresponsalFront = "";
	
	/** The correo contacto. */
	private String correoContacto = "";
	
	/** The correo alterno contacto. */
	private String correoAlternoContacto= "";
	
	/** The tipo operacion. */
	private String tipoOperacion = "";
	
	/** The centro costo. */
	private String centroCosto = "";
	
	/** The calle domicilio corresponsal. */
	private String calleDomicilioCorresponsal = "";
	
	/** The numero exterior corresponsal. */
	private String numeroExteriorCorresponsal = "";
	
	/** The numero interior corresponsal. */
	private String numeroInteriorCorresponsal = "";
	
	/** The colonia domicilio corresponsal. */
	private String coloniaDomicilioCorresponsal = "";
	
	/** The delegacion municipio corresponsal. */
	private String delegacionMunicipioCorresponsal = "";
	
	/** The ciudad corresponsal. */
	private String ciudadCorresponsal = "";
	
	/** The estado corresponsal. */
	private String estadoCorresponsal = "";
	
	/** The codigo postal corresponsal. */
	private String codigoPostalCorresponsal = "";
	
	/** The pais procedencia corresponsal. */
	private String paisProcedenciaCorresponsal = "";
	
	/** The telefono corresponsal. */
	private String telefonoCorresponsal = "";
	
	/** The telefono aclaracion. */
	private String telefonoAclaracion = "";
	
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
	 * Gets the codigo corresponsalia.
	 *
	 * @return the codigo corresponsalia
	 */
	public String getCodigoCorresponsalia() {
		return codigoCorresponsalia;
	}
	
	/**
	 * Sets the codigo corresponsalia.
	 *
	 * @param codigoCorresponsaliaParam the new codigo corresponsalia
	 */
	public void setCodigoCorresponsalia(String codigoCorresponsaliaParam) {
		this.codigoCorresponsalia = codigoCorresponsaliaParam;
	}
	
	/**
	 * Gets the codigo corresponsal.
	 *
	 * @return the codigo corresponsal
	 */
	public String getCodigoCorresponsal() {
		return codigoCorresponsal;
	}
	
	/**
	 * Sets the codigo corresponsal.
	 *
	 * @param codigoCorresponsalParam the new codigo corresponsal
	 */
	public void setCodigoCorresponsal(String codigoCorresponsalParam) {
		this.codigoCorresponsal = codigoCorresponsalParam; // debe ser el Centro de Costos
		
	}
	
	/**
	 * Gets the estatus corresponsal.
	 *
	 * @return the estatus corresponsal
	 */
	public String getEstatusCorresponsal() {
		return estatusCorresponsal;
	}
	
	/**
	 * Sets the estatus corresponsal.
	 *
	 * @param estatusCorresponsalParam the new estatus corresponsal
	 */
	public void setEstatusCorresponsal(String estatusCorresponsalParam) {
		this.estatusCorresponsal = estatusCorresponsalParam;
	}
	
	/**
	 * Gets the codigo cliente.
	 *
	 * @return the codigo cliente
	 */
	public String getCodigoCliente() {
		return codigoCliente;
	}
	
	/**
	 * Sets the codigo cliente.
	 *
	 * @param codigoClienteParam the new codigo cliente
	 */
	public void setCodigoCliente(String codigoClienteParam) {
		this.codigoCliente = codigoClienteParam;
	}
	
	/**
	 * Gets the secuencia domicilio.
	 *
	 * @return the secuencia domicilio
	 */
	public String getSecuenciaDomicilio() {
		return secuenciaDomicilio;
	}
	
	/**
	 * Sets the secuencia domicilio.
	 *
	 * @param secuenciaDomicilioParam the new secuencia domicilio
	 */
	public void setSecuenciaDomicilio(String secuenciaDomicilioParam) {
		this.secuenciaDomicilio = secuenciaDomicilioParam;
	}
	
	/**
	 * Gets the giro empresa.
	 *
	 * @return the giro empresa
	 */
	public String getGiroEmpresa() {
		return giroEmpresa;
	}
	
	/**
	 * Sets the giro empresa.
	 *
	 * @param giroEmpresaParam the new giro empresa
	 */
	public void setGiroEmpresa(String giroEmpresaParam) {
		this.giroEmpresa = giroEmpresaParam;
	}
	
	/**
	 * Gets the nombre corresponsal.
	 *
	 * @return the nombre corresponsal
	 */
	public String getNombreCorresponsal() {
		return nombreCorresponsal;
	}
	
	/**
	 * Sets the nombre corresponsal.
	 *
	 * @param nombreCorresponsalParam the new nombre corresponsal
	 */
	public void setNombreCorresponsal(String nombreCorresponsalParam) {
		this.nombreCorresponsal = nombreCorresponsalParam;
	}
	
	/**
	 * Gets the indicador conciliacion.
	 *
	 * @return the indicador conciliacion
	 */
	public String getIndicadorConciliacion() {
		return indicadorConciliacion;
	}
	
	/**
	 * Sets the indicador conciliacion.
	 *
	 * @param indicadorConciliacionParam the new indicador conciliacion
	 */
	public void setIndicadorConciliacion(String indicadorConciliacionParam) {
		this.indicadorConciliacion = indicadorConciliacionParam;
	}
	
	/**
	 * Gets the param dias pendientes conciliar.
	 *
	 * @return the param dias pendientes conciliar
	 */
	public String getParamDiasPendientesConciliar() {
		return paramDiasPendientesConciliar;
	}
	
	/**
	 * Sets the param dias pendientes conciliar.
	 *
	 * @param paramDiasPendientesConciliarParam the new param dias pendientes conciliar
	 */
	public void setParamDiasPendientesConciliar(String paramDiasPendientesConciliarParam) {
		this.paramDiasPendientesConciliar = paramDiasPendientesConciliarParam;
	}
	
	/**
	 * Gets the param dias pendientes compensar.
	 *
	 * @return the param dias pendientes compensar
	 */
	public String getParamDiasPendientesCompensar() {
		return paramDiasPendientesCompensar;
	}
	
	/**
	 * Sets the param dias pendientes compensar.
	 *
	 * @param paramDiasPendientesCompensarParam the new param dias pendientes compensar
	 */
	public void setParamDiasPendientesCompensar(String paramDiasPendientesCompensarParam) {
		this.paramDiasPendientesCompensar = paramDiasPendientesCompensarParam;
	}
	
	/**
	 * Gets the indicador valida comision.
	 *
	 * @return the indicador valida comision
	 */
	public String getIndicadorValidaComision() { 
		return indicadorValidaComision;
	}
	
	/**
	 * Sets the indicador valida comision.
	 *
	 * @param indicadorValidaComisionParam the new indicador valida comision
	 */
	public void setIndicadorValidaComision(String indicadorValidaComisionParam) {
		this.indicadorValidaComision = indicadorValidaComisionParam;
	}
	
	/**
	 * Gets the cuenta cheques corresponsal.
	 *
	 * @return the cuenta cheques corresponsal
	 */
	public String getCuentaChequesCorresponsal() {
		return cuentaChequesCorresponsal;
	}
	
	/**
	 * Sets the cuenta cheques corresponsal.
	 *
	 * @param cuentaChequesCorresponsalParam the new cuenta cheques corresponsal
	 */
	public void setCuentaChequesCorresponsal(String cuentaChequesCorresponsalParam) {
		this.cuentaChequesCorresponsal = cuentaChequesCorresponsalParam;
	}
	
	/**
	 * Gets the divisa cuenta cheques.
	 *
	 * @return the divisa cuenta cheques
	 */
	public String getDivisaCuentaCheques() {
		return divisaCuentaCheques;
	}
	
	/**
	 * Sets the divisa cuenta cheques.
	 *
	 * @param divisaCuentaChequesParam the new divisa cuenta cheques
	 */
	public void setDivisaCuentaCheques(String divisaCuentaChequesParam) {
		this.divisaCuentaCheques = divisaCuentaChequesParam;
	}
	
	/**
	 * Gets the linea credito corresponsal.
	 *
	 * @return the linea credito corresponsal
	 */
	public String getLineaCreditoCorresponsal() {
		return lineaCreditoCorresponsal;
	}
	
	/**
	 * Sets the linea credito corresponsal.
	 *
	 * @param lineaCreditoCorresponsalParam the new linea credito corresponsal
	 */
	public void setLineaCreditoCorresponsal(String lineaCreditoCorresponsalParam) {
		this.lineaCreditoCorresponsal = lineaCreditoCorresponsalParam;
	}
	
	/**
	 * Gets the divisa asoc linea credito.
	 *
	 * @return the divisa asoc linea credito
	 */
	public String getDivisaAsocLineaCredito() {
		return divisaAsocLineaCredito;
	}
	
	/**
	 * Sets the divisa asoc linea credito.
	 *
	 * @param divisaAsocLineaCreditoParam the new divisa asoc linea credito
	 */
	public void setDivisaAsocLineaCredito(String divisaAsocLineaCreditoParam) {
		this.divisaAsocLineaCredito = divisaAsocLineaCreditoParam;
	}
	
	/**
	 * Gets the limite importe corresponsal.
	 *
	 * @return the limite importe corresponsal
	 */
	public String getLimiteImporteCorresponsal() {
		return limiteImporteCorresponsal;
	}
	
	/**
	 * Sets the limite importe corresponsal.
	 *
	 * @param limiteImporteCorresponsalParam the new limite importe corresponsal
	 */
	public void setLimiteImporteCorresponsal(String limiteImporteCorresponsalParam) {
		this.limiteImporteCorresponsal = limiteImporteCorresponsalParam;
	}
	
	/**
	 * Gets the correo contacto.
	 *
	 * @return the correo contacto
	 */
	public String getCorreoContacto() {
		return correoContacto;
	}
	
	/**
	 * Sets the correo contacto.
	 *
	 * @param correoContactoParam the new correo contacto
	 */
	public void setCorreoContacto(String correoContactoParam) {
		this.correoContacto = correoContactoParam;
	}
	
	/**
	 * Gets the correo alterno contacto.
	 *
	 * @return the correo alterno contacto
	 */
	public String getCorreoAlternoContacto() {
		return correoAlternoContacto;
	}
	
	/**
	 * Sets the correo alterno contacto.
	 *
	 * @param correoAlternoContactoParam the new correo alterno contacto
	 */
	public void setCorreoAlternoContacto(String correoAlternoContactoParam) {
		this.correoAlternoContacto = correoAlternoContactoParam;
	}
	
	/**
	 * Gets the tipo operacion.
	 *
	 * @return the tipo operacion
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}
	
	/**
	 * Sets the tipo operacion.
	 *
	 * @param tipoOperacionParam the new tipo operacion
	 */
	public void setTipoOperacion(String tipoOperacionParam) {
		this.tipoOperacion = tipoOperacionParam;
	}
	
	/**
	 * Gets the centro costo.
	 *
	 * @return the centro costo
	 */
	public String getCentroCosto() {
		return centroCosto;
	}
	
	/**
	 * Sets the centro costo.
	 *
	 * @param centroCostoParam the new centro costo
	 */
	public void setCentroCosto(String centroCostoParam) {
		this.centroCosto = centroCostoParam; //El Centro de Costos es el mismo dato que el ID Corresponsal
	}
	
	/**
	 * Gets the calle domicilio corresponsal.
	 *
	 * @return the calle domicilio corresponsal
	 */
	public String getCalleDomicilioCorresponsal() {
		return calleDomicilioCorresponsal;
	}
	
	/**
	 * Sets the calle domicilio corresponsal.
	 *
	 * @param calleDomicilioCorresponsalParam the new calle domicilio corresponsal
	 */
	public void setCalleDomicilioCorresponsal(String calleDomicilioCorresponsalParam) {
		this.calleDomicilioCorresponsal = calleDomicilioCorresponsalParam;
	}
	
	/**
	 * Gets the numero exterior corresponsal.
	 *
	 * @return the numero exterior corresponsal
	 */
	public String getNumeroExteriorCorresponsal() {
		return numeroExteriorCorresponsal;
	}
	
	/**
	 * Sets the numero exterior corresponsal.
	 *
	 * @param numeroExteriorCorresponsalParam the new numero exterior corresponsal
	 */
	public void setNumeroExteriorCorresponsal(String numeroExteriorCorresponsalParam) {
		this.numeroExteriorCorresponsal = numeroExteriorCorresponsalParam;
	}
	
	/**
	 * Gets the numero interior corresponsal.
	 *
	 * @return the numero interior corresponsal
	 */
	public String getNumeroInteriorCorresponsal() {
		return numeroInteriorCorresponsal;
	}
	
	/**
	 * Sets the numero interior corresponsal.
	 *
	 * @param numeroInteriorCorresponsalParam the new numero interior corresponsal
	 */
	public void setNumeroInteriorCorresponsal(String numeroInteriorCorresponsalParam) {
		this.numeroInteriorCorresponsal = numeroInteriorCorresponsalParam;
	}
	
	/**
	 * Gets the colonia domicilio corresponsal.
	 *
	 * @return the colonia domicilio corresponsal
	 */
	public String getColoniaDomicilioCorresponsal() {
		return coloniaDomicilioCorresponsal;
	}
	
	/**
	 * Sets the colonia domicilio corresponsal.
	 *
	 * @param coloniaDomicilioCorresponsalParam the new colonia domicilio corresponsal
	 */
	public void setColoniaDomicilioCorresponsal(String coloniaDomicilioCorresponsalParam) {
		this.coloniaDomicilioCorresponsal = coloniaDomicilioCorresponsalParam;
	}
	
	/**
	 * Gets the delegacion municipio corresponsal.
	 *
	 * @return the delegacion municipio corresponsal
	 */
	public String getDelegacionMunicipioCorresponsal() {
		return delegacionMunicipioCorresponsal;
	}
	
	/**
	 * Sets the delegacion municipio corresponsal.
	 *
	 * @param delegacionMunicipioCorresponsalParam the new delegacion municipio corresponsal
	 */
	public void setDelegacionMunicipioCorresponsal(
			String delegacionMunicipioCorresponsalParam) {
		this.delegacionMunicipioCorresponsal = delegacionMunicipioCorresponsalParam;
	}
	
	/**
	 * Gets the ciudad corresponsal.
	 *
	 * @return the ciudad corresponsal
	 */
	public String getCiudadCorresponsal() {
		return ciudadCorresponsal;
	}
	
	/**
	 * Sets the ciudad corresponsal.
	 *
	 * @param ciudadCorresponsalParam the new ciudad corresponsal
	 */
	public void setCiudadCorresponsal(String ciudadCorresponsalParam) {
		this.ciudadCorresponsal = ciudadCorresponsalParam;
	}
	
	/**
	 * Gets the estado corresponsal.
	 *
	 * @return the estado corresponsal
	 */
	public String getEstadoCorresponsal() {
		return estadoCorresponsal;
	}
	
	/**
	 * Sets the estado corresponsal.
	 *
	 * @param estadoCorresponsalParam the new estado corresponsal
	 */
	public void setEstadoCorresponsal(String estadoCorresponsalParam) {
		this.estadoCorresponsal = estadoCorresponsalParam;
	}
	
	/**
	 * Gets the codigo postal corresponsal.
	 *
	 * @return the codigo postal corresponsal
	 */
	public String getCodigoPostalCorresponsal() {
		return codigoPostalCorresponsal;
	}
	
	/**
	 * Sets the codigo postal corresponsal.
	 *
	 * @param codigoPostalCorresponsalParam the new codigo postal corresponsal
	 */
	public void setCodigoPostalCorresponsal(String codigoPostalCorresponsalParam) {
		this.codigoPostalCorresponsal = codigoPostalCorresponsalParam;
	}
	
	/**
	 * Gets the pais procedencia corresponsal.
	 *
	 * @return the pais procedencia corresponsal
	 */
	public String getPaisProcedenciaCorresponsal() {
		return paisProcedenciaCorresponsal;
	}
	
	/**
	 * Sets the pais procedencia corresponsal.
	 *
	 * @param paisProcedenciaCorresponsalParam the new pais procedencia corresponsal
	 */
	public void setPaisProcedenciaCorresponsal(String paisProcedenciaCorresponsalParam) {
		this.paisProcedenciaCorresponsal = paisProcedenciaCorresponsalParam;
	}
	
	/**
	 * Gets the telefono corresponsal.
	 *
	 * @return the telefono corresponsal
	 */
	public String getTelefonoCorresponsal() {
		return telefonoCorresponsal;
	}
	
	/**
	 * Sets the telefono corresponsal.
	 *
	 * @param telefonoCorresponsalParam the new telefono corresponsal
	 */
	public void setTelefonoCorresponsal(String telefonoCorresponsalParam) {
		this.telefonoCorresponsal = telefonoCorresponsalParam;
	}
	
	/**
	 * Gets the telefono aclaracion.
	 *
	 * @return the telefono aclaracion
	 */
	public String getTelefonoAclaracion() {
		return telefonoAclaracion;
	}
	
	/**
	 * Sets the telefono aclaracion.
	 *
	 * @param telefonoAclaracionParam the new telefono aclaracion
	 */
	public void setTelefonoAclaracion(String telefonoAclaracionParam) {
		this.telefonoAclaracion = telefonoAclaracionParam;
	}
	
	/**
	 * Gets the limite importe corresponsal front.
	 *
	 * @return the limite importe corresponsal front
	 */
	public String getLimiteImporteCorresponsalFront() {
		return limiteImporteCorresponsalFront;
	}
	
	/**
	 * Sets the limite importe corresponsal front.
	 *
	 * @param limiteImporteCorresponsalFront the new limite importe corresponsal front
	 */
	public void setLimiteImporteCorresponsalFront(
			String limiteImporteCorresponsalFront) {
		this.limiteImporteCorresponsalFront = limiteImporteCorresponsalFront;
	}
	

}
