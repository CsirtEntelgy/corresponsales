package com.isban.corresponsalia.beans.comunes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class BeanOperacion.
 */
public class BeanOperacion   implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5659392850808098790L;

	/** The entidad. */
	private String entidad = "";
	
	/** The id canal. */
	private String idCanal = "";
	
	/** The id corresponsal. */
	private String idCorresponsal = "";
	
	/** The id sucursal. */
	private String idSucursal = "";
	
	/** The nivel parametria. */
	private String nivelParametria = "";
	
	/** The des corta. */
	private String desCorta= "";
	
	/** The codigo operacion. */
	private String codigoOperacion = "";
	
	/** The cve transaccion. */
	private String cveTransaccion = "";
	
	/** The hora inicio. */
	private String horaInicio = "";
	
	/** The hora inicio front. */
	private String horaInicioFront = "";
	
	/** The hora final. */
	private String horaFinal = "";
	
	/** The hora final front. */
	private String horaFinalFront = "";
	
	/** The importe minimo operacion. */
	private String importeMinimoOperacion = "";
	
	/** The importe minimo operacion front. */
	private String importeMinimoOperacionFront = "";
	
	/** The importe maximo operacion. */
	private String importeMaximoOperacion = "";
	
	/** The importe maximo operacion front. */
	private String importeMaximoOperacionFront = "";
	
	/** The acumulado diario. */
	private String acumuladoDiario = "";
	
	/** The acumulado diario front. */
	private String acumuladoDiarioFront = "";
	
	/** The acumulado mensual. */
	private String acumuladoMensual = "";
	
	/** The acumulado mensual front. */
	private String acumuladoMensualFront = "";
	
	/** The codigo operacion transaccion. */
	private String codigoOperacionTransaccion = "";
	
	/** The codigo operacion contra oper. */
	private String codigoOperacionContraOper = "";
	
	/** The tipo operacion corresponsal. */
	private String tipoOperacionCorresponsal = "";
	//Por definir
	/** The nombre operacion. */
	private String nombreOperacion = "";
	
	/** The equivalencia. */
	private String equivalencia = "";
	
	/** The limite importe maximo diario acum. */
	private String limiteImporteMaximoDiarioAcum = "";
	
	/** The limite importe maximo mensual acum. */
	private String limiteImporteMaximoMensualAcum = "";

	
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
	 * @param entidadParam the new entidad
	 */
	public void setEntidad(String entidadParam) {
		this.entidad = entidadParam;
	}
	
	/**
	 * Gets the id canal.
	 *
	 * @return the id canal
	 */
	public String getIdCanal() {
		return idCanal;
	}
	
	/**
	 * Sets the id canal.
	 *
	 * @param canalParam the new id canal
	 */
	public void setIdCanal(String canalParam) {
		this.idCanal = canalParam;
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
	 * Gets the id sucursal.
	 *
	 * @return the id sucursal
	 */
	public String getIdSucursal() {
		return idSucursal;
	}
	
	/**
	 * Sets the id sucursal.
	 *
	 * @param idSucursal the new id sucursal
	 */
	public void setIdSucursal(String idSucursal) {
		this.idSucursal = idSucursal;
	}

	/**
	 * Gets the nivel parametria.
	 *
	 * @return the nivel parametria
	 */
	public String getNivelParametria() {
		return nivelParametria;
	}
	
	/**
	 * Sets the nivel parametria.
	 *
	 * @param nivelParametriaParam the new nivel parametria
	 */
	public void setNivelParametria(String nivelParametriaParam) {
		this.nivelParametria = nivelParametriaParam;
	}
	
	/**
	 * Gets the des corta.
	 *
	 * @return the des corta
	 */
	public String getDesCorta() {
		return desCorta;
	}
	
	/**
	 * Sets the des corta.
	 *
	 * @param desCortaParam the new des corta
	 */
	public void setDesCorta(String desCortaParam) {
		this.desCorta = desCortaParam;
	}
	
	/**
	 * Gets the codigo operacion.
	 *
	 * @return the codigo operacion
	 */
	public String getCodigoOperacion() {
		return codigoOperacion;
	}
	
	/**
	 * Sets the codigo operacion.
	 *
	 * @param codigoOperacionParam the new codigo operacion
	 */
	public void setCodigoOperacion(String codigoOperacionParam) {
		this.codigoOperacion = codigoOperacionParam;
	}
	
	/**
	 * Gets the cve transaccion.
	 *
	 * @return the cve transaccion
	 */
	public String getCveTransaccion() {
		return cveTransaccion;
	}
	
	/**
	 * Sets the cve transaccion.
	 *
	 * @param cveTransaccionParam the new cve transaccion
	 */
	public void setCveTransaccion(String cveTransaccionParam) {
		this.cveTransaccion = cveTransaccionParam;
	}
	
	/**
	 * Gets the hora inicio.
	 *
	 * @return the hora inicio
	 */
	public String getHoraInicio() {
		return horaInicio;
	}
	
	/**
	 * Gets the hora inicio formateada.
	 *
	 * @return the hora inicio formateada
	 */
	public String getHoraInicioFormateada() {
		
		String horaFormateada = horaInicio;
		SimpleDateFormat formatIni = new SimpleDateFormat("HHmmss");
		SimpleDateFormat formatFin = new SimpleDateFormat("HH:mm");
		
		try {
			Date fechaParse = formatIni.parse(horaInicio);
			horaFormateada = formatFin.format(fechaParse);
		} catch (Exception e) {
			System.out.println("No fue posible formatear la fecha");
		}
		
		return horaFormateada;
	}
	
	/**
	 * Sets the hora inicio.
	 *
	 * @param horaInicioParam the new hora inicio
	 */
	public void setHoraInicio(String horaInicioParam) {
		this.horaInicio = horaInicioParam;
	}
	
	/**
	 * Gets the hora final.
	 *
	 * @return the hora final
	 */
	public String getHoraFinal() {
		return horaFinal;
	}
	
	/**
	 * Gets the hora final formateada.
	 *
	 * @return the hora final formateada
	 */
	public String getHoraFinalFormateada() {
		
		String horaFormateada = horaFinal;
		SimpleDateFormat formatIni = new SimpleDateFormat("HHmmss");
		SimpleDateFormat formatFin = new SimpleDateFormat("HH:mm");
		
		try {
			Date fechaParse = formatIni.parse(horaFinal);
			horaFormateada = formatFin.format(fechaParse);
		} catch (Exception e) {
			System.out.println("No fue posible formatear la fecha");
		}
		
		return horaFormateada;
	}
	
	/**
	 * Sets the hora final.
	 *
	 * @param horaFinalParam the new hora final
	 */
	public void setHoraFinal(String horaFinalParam) {
		this.horaFinal = horaFinalParam;
	}
	
	/**
	 * Gets the importe minimo operacion.
	 *
	 * @return the importe minimo operacion
	 */
	public String getImporteMinimoOperacion() {
		return importeMinimoOperacion;
	}
	
	/**
	 * Gets the importe minimo operacion formateado.
	 *
	 * @return the importe minimo operacion formateado
	 */
	public String getImporteMinimoOperacionFormateado() {
		String importeFormateado = importeMinimoOperacion;
		try{
			importeFormateado =String.valueOf((Integer.parseInt(importeFormateado) / 100));			
		}
		catch(Exception e){
			System.out.println("No fue posible formatear el importe");
		}
		return importeFormateado;
	}

	/**
	 * Sets the importe minimo operacion.
	 *
	 * @param importeMinimoOperacionParam the new importe minimo operacion
	 */
	public void setImporteMinimoOperacion(String importeMinimoOperacionParam) {
		this.importeMinimoOperacion = importeMinimoOperacionParam;
	}
	
	/**
	 * Gets the importe maximo operacion.
	 *
	 * @return the importe maximo operacion
	 */
	public String getImporteMaximoOperacion() {
		return importeMaximoOperacion;
	}
	
	/**
	 * Gets the importe maximo operacion formateado.
	 *
	 * @return the importe maximo operacion formateado
	 */
	public String getImporteMaximoOperacionFormateado() {
		String importeFormateado = importeMaximoOperacion;
		try{
			importeFormateado =String.valueOf((Integer.parseInt(importeFormateado) / 100));			
		}
		catch(Exception e){
			System.out.println("No fue posible formatear el importe");
		}
		return importeFormateado;
	}

	/**
	 * Sets the importe maximo operacion.
	 *
	 * @param importeMaximoOperacionParam the new importe maximo operacion
	 */
	public void setImporteMaximoOperacion(String importeMaximoOperacionParam) {
		this.importeMaximoOperacion = importeMaximoOperacionParam;
	}
	
	/**
	 * Gets the acumulado diario.
	 *
	 * @return the acumulado diario
	 */
	public String getAcumuladoDiario() {
		return acumuladoDiario;
	}
	
	/**
	 * Gets the acumulado diario formateado.
	 *
	 * @return the acumulado diario formateado
	 */
	public String getAcumuladoDiarioFormateado() {
		String importeFormateado = limiteImporteMaximoDiarioAcum;
		System.out.println("AcumuladoDiario: " + acumuladoDiario);
		try{
			importeFormateado =String.valueOf((Integer.parseInt(importeFormateado) / 100));			
		}
		catch(Exception e){
			System.out.println("No fue posible formatear el importe");
		}
		return importeFormateado;
	}

	/**
	 * Sets the acumulado diario.
	 *
	 * @param acumuladoDiarioParam the new acumulado diario
	 */
	public void setAcumuladoDiario(String acumuladoDiarioParam) {
		this.acumuladoDiario = acumuladoDiarioParam;
	}
	
	/**
	 * Gets the acumulado mensual.
	 *
	 * @return the acumulado mensual
	 */
	public String getAcumuladoMensual() {
		return acumuladoMensual;
	}
	
	/**
	 * Gets the acumulado mensual formateado.
	 *
	 * @return the acumulado mensual formateado
	 */
	public String getAcumuladoMensualFormateado() {
		String importeFormateado = limiteImporteMaximoMensualAcum;
		System.out.println("AcumuladoMensual: " + acumuladoMensual);
		try{
			importeFormateado =String.valueOf((Integer.parseInt(importeFormateado) / 100));			
		}
		catch(Exception e){
			System.out.println("No fue posible formatear el importe");
		}
		return importeFormateado;
	}
	
	/**
	 * Sets the acumulado mensual.
	 *
	 * @param acumuladoMensualParam the new acumulado mensual
	 */
	public void setAcumuladoMensual(String acumuladoMensualParam) {
		this.acumuladoMensual = acumuladoMensualParam;
	}
	
	/**
	 * Gets the codigo operacion transaccion.
	 *
	 * @return the codigo operacion transaccion
	 */
	public String getCodigoOperacionTransaccion() {
		return codigoOperacionTransaccion;
	}
	
	/**
	 * Sets the codigo operacion transaccion.
	 *
	 * @param codigoOperacionTransaccionParam the new codigo operacion transaccion
	 */
	public void setCodigoOperacionTransaccion(String codigoOperacionTransaccionParam) {
		this.codigoOperacionTransaccion = codigoOperacionTransaccionParam;
	}
	
	/**
	 * Gets the codigo operacion contra oper.
	 *
	 * @return the codigo operacion contra oper
	 */
	public String getCodigoOperacionContraOper() {
		return codigoOperacionContraOper;
	}
	
	/**
	 * Sets the codigo operacion contra oper.
	 *
	 * @param codigoOperacionContraOperParam the new codigo operacion contra oper
	 */
	public void setCodigoOperacionContraOper(String codigoOperacionContraOperParam) {
		this.codigoOperacionContraOper = codigoOperacionContraOperParam;
	}
	
	/**
	 * Gets the nombre operacion.
	 *
	 * @return the nombre operacion
	 */
	public String getNombreOperacion() {
		return nombreOperacion;
	}
	
	/**
	 * Sets the nombre operacion.
	 *
	 * @param nombreOperacionParam the new nombre operacion
	 */
	public void setNombreOperacion(String nombreOperacionParam) {
		this.nombreOperacion = nombreOperacionParam;
	}
	
	/**
	 * Gets the tipo operacion corresponsal.
	 *
	 * @return the tipo operacion corresponsal
	 */
	public String getTipoOperacionCorresponsal() {
		return tipoOperacionCorresponsal;
	}
	
	/**
	 * Sets the tipo operacion corresponsal.
	 *
	 * @param tipoOperacionCorresponsalParam the new tipo operacion corresponsal
	 */
	public void setTipoOperacionCorresponsal(String tipoOperacionCorresponsalParam) {
		this.tipoOperacionCorresponsal = tipoOperacionCorresponsalParam;
	}
	
	/**
	 * Gets the equivalencia.
	 *
	 * @return the equivalencia
	 */
	public String getEquivalencia() {
		return equivalencia;
	}
	
	/**
	 * Sets the equivalencia.
	 *
	 * @param equivalenciaParam the new equivalencia
	 */
	public void setEquivalencia(String equivalenciaParam) {
		this.equivalencia = equivalenciaParam;
	}
	
	/**
	 * Gets the limite importe maximo diario acum.
	 *
	 * @return the limite importe maximo diario acum
	 */
	public String getLimiteImporteMaximoDiarioAcum() {
		return limiteImporteMaximoDiarioAcum;
	}
	
	/**
	 * Sets the limite importe maximo diario acum.
	 *
	 * @param limiteImporteMaximoDiarioAcumParam the new limite importe maximo diario acum
	 */
	public void setLimiteImporteMaximoDiarioAcum(
			String limiteImporteMaximoDiarioAcumParam) {
		this.limiteImporteMaximoDiarioAcum = limiteImporteMaximoDiarioAcumParam;
	}
	
	/**
	 * Gets the limite importe maximo mensual acum.
	 *
	 * @return the limite importe maximo mensual acum
	 */
	public String getLimiteImporteMaximoMensualAcum() {
		return limiteImporteMaximoMensualAcum;
	}
	
	/**
	 * Sets the limite importe maximo mensual acum.
	 *
	 * @param limiteImporteMaximoMensualAcumParam the new limite importe maximo mensual acum
	 */
	public void setLimiteImporteMaximoMensualAcum(
			String limiteImporteMaximoMensualAcumParam) {
		this.limiteImporteMaximoMensualAcum = limiteImporteMaximoMensualAcumParam;
	}
	
	/**
	 * Gets the hora inicio front.
	 *
	 * @return the hora inicio front
	 */
	public String getHoraInicioFront() {
		return horaInicioFront;
	}
	
	/**
	 * Sets the hora inicio front.
	 *
	 * @param horaInicioFront the new hora inicio front
	 */
	public void setHoraInicioFront(String horaInicioFront) {
		this.horaInicioFront = horaInicioFront;
	}
	
	/**
	 * Gets the hora final front.
	 *
	 * @return the hora final front
	 */
	public String getHoraFinalFront() {
		return horaFinalFront;
	}
	
	/**
	 * Sets the hora final front.
	 *
	 * @param horaFinalFront the new hora final front
	 */
	public void setHoraFinalFront(String horaFinalFront) {
		this.horaFinalFront = horaFinalFront;
	}
	
	/**
	 * Gets the importe minimo operacion front.
	 *
	 * @return the importe minimo operacion front
	 */
	public String getImporteMinimoOperacionFront() {
		return importeMinimoOperacionFront;
	}
	
	/**
	 * Sets the importe minimo operacion front.
	 *
	 * @param importeMinimoOperacionFront the new importe minimo operacion front
	 */
	public void setImporteMinimoOperacionFront(String importeMinimoOperacionFront) {
		this.importeMinimoOperacionFront = importeMinimoOperacionFront;
	}
	
	/**
	 * Gets the importe maximo operacion front.
	 *
	 * @return the importe maximo operacion front
	 */
	public String getImporteMaximoOperacionFront() {
		return importeMaximoOperacionFront;
	}
	
	/**
	 * Sets the importe maximo operacion front.
	 *
	 * @param importeMaximoOperacionFront the new importe maximo operacion front
	 */
	public void setImporteMaximoOperacionFront(String importeMaximoOperacionFront) {
		this.importeMaximoOperacionFront = importeMaximoOperacionFront;
	}
	
	/**
	 * Gets the acumulado diario front.
	 *
	 * @return the acumulado diario front
	 */
	public String getAcumuladoDiarioFront() {
		return acumuladoDiarioFront;
	}
	
	/**
	 * Sets the acumulado diario front.
	 *
	 * @param acumuladoDiarioFront the new acumulado diario front
	 */
	public void setAcumuladoDiarioFront(String acumuladoDiarioFront) {
		this.acumuladoDiarioFront = acumuladoDiarioFront;
	}
	
	/**
	 * Gets the acumulado mensual front.
	 *
	 * @return the acumulado mensual front
	 */
	public String getAcumuladoMensualFront() {
		return acumuladoMensualFront;
	}
	
	/**
	 * Sets the acumulado mensual front.
	 *
	 * @param acumuladoMensualFront the new acumulado mensual front
	 */
	public void setAcumuladoMensualFront(String acumuladoMensualFront) {
		this.acumuladoMensualFront = acumuladoMensualFront;
	}
	
}
