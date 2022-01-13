package com.isban.corresponsalia.beans.consultas;

import java.io.Serializable;
import java.math.BigDecimal;
import com.isban.ebe.commons.interfaces.BeanResultBO;

/**
 * The Class RBeanConsultaBitacora.
 */
public class BeanConsultaLogTrx  implements  BeanResultBO, Serializable{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 575370468542931076L;
	
	/** The cod error. */
	private String                                  codError;	
	/** The msg error. */
	private String                                  msgError;	
	/** The cod aviso. */
	private String 									codAviso;	
	/** The msg aviso. */
	private String                                  msgAviso;
	
	/** The folio. */
	private BigDecimal folio; //FOLIO	
	
	
	/** The tipo. */
	private String tipo;   			//TIPO
	
	/** The fecha inicio. */
	private java.sql.Timestamp fechaInicio;		//FCH_INICIO;
	
	/** The fecha fin. */
	private java.sql.Timestamp fechaFin; 			//FCH_FIN;
	
	/** The numero tarjeta. */
	private String numeroTarjeta; 	//NUM_TARJ;
	
	/** The numero cuenta. */
	private String numeroCuenta;	//NUM_CUENTA;
	
	/** The importe. */
	private String importe; 		//IMPORTE;
	
	/** The duracion. */
	private BigDecimal duracion; 		//DURACION;
	
	/** The codigo error. */
	private String codigoError; 	//COD_ERROR;
	
	/**	The descripcion error */
	private String descripcionError; //DESC_ERROR;
	
	/** The resultado. */
	private BigDecimal resultado; 		//RESULTADO;

	/**
	 * Gets the tipo.
	 *
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Sets the tipo.
	 *
	 * @param tipo the new tipo
	 */
	public void setTipo(BigDecimal tipo) {
		String tipoS ="";
		/*if(tipo.longValue() == 280000){
			tipoS ="Depósito en efectivo (TDC)";
		}*/
		switch (tipo.intValue()){
		case 210000:
			tipoS ="Depósito en efectivo (TDC)";
			break;
		case 280000:
			tipoS ="Depósito en efectivo (TDD)";
			break;
		case -210000:
			tipoS ="Anulación depósito en efectivo (TDC)";
			break;
		case -280000:
			tipoS ="Anulación depósito en efectivo (TDD)";
			break;
		case 340000:
			tipoS ="Depósito Cuenta Cheques";
			break;
		case -340000:
			tipoS ="Anulación de depósito Cuenta Cheques";
			break;
		case 320000:
			tipoS ="Depósito Celular";
			break;
		case -320000:
			tipoS ="Anulación de depósito Celular";
			break;

		}
		this.tipo = tipoS;
	}

	/**
	 * Gets the fecha inicio.
	 *
	 * @return the fecha inicio
	 */
	public java.sql.Timestamp getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Sets the fecha inicio.
	 *
	 * @param fechaInicio the new fecha inicio
	 */
	public void setFechaInicio(java.sql.Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Gets the fecha fin.
	 *
	 * @return the fecha fin
	 */
	public java.sql.Timestamp getFechaFin() {
		return fechaFin;
	}

	/**
	 * Sets the fecha fin.
	 *
	 * @param fechaFin the new fecha fin
	 */
	public void setFechaFin(java.sql.Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * Gets the numero tarjeta.
	 *
	 * @return the numero tarjeta
	 */
	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	/**
	 * Sets the numero tarjeta.
	 *
	 * @param numeroTarjeta the new numero tarjeta
	 */
	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	/**
	 * Gets the numero cuenta.
	 *
	 * @return the numero cuenta
	 */
	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	/**
	 * Sets the numero cuenta.
	 *
	 * @param numeroCuenta the new numero cuenta
	 */
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	/**
	 * Gets the importe.
	 *
	 * @return the importe
	 */
	public String getImporte() {
		return importe;
	}

	/**
	 * Sets the importe.
	 *
	 * @param importe the new importe
	 */
	public void setImporte(String importe) {
		this.importe = importe;
	}

	/**
	 * Gets the duracion.
	 *
	 * @return the duracion
	 */
	public BigDecimal getDuracion() {
		return duracion;
	}

	/**
	 * Sets the duracion.
	 *
	 * @param duracion the new duracion
	 */
	public void setDuracion(BigDecimal duracion) {
		this.duracion = duracion;
	}

	/**
	 * Gets the codigo error.
	 *
	 * @return the codigo error
	 */
	public String getCodigoError() {
		return codigoError;
	}

	/**
	 * Sets the codigo error.
	 *
	 * @param codigoError the new codigo error
	 */
	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	/**
	 * Gets the resultado.
	 *
	 * @return the resultado
	 */
	public BigDecimal getResultado() {
		return resultado;
	}

	/**
	 * Sets the resultado.
	 *
	 * @param resultado the new resultado
	 */
	public void setResultado(BigDecimal resultado) {
		this.resultado = resultado;
	}
	
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

	/**
	 * Gets the descripcionError
	 *
	 * @return the descripcionError
	 */
	public String getDescripcionError() {
		return descripcionError;
	}

	/**
	 * Sets the descripcionError
	 *
	 * @param descripcionError the new msg aviso
	 */
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}

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
	 * Gets the folio.
	 *
	 * @return the folio
	 */
	public BigDecimal getFolio() {
		return folio;
	}

	/**
	 * Sets the folio.
	 *
	 * @param folio the new folio
	 */
	public void setFolio(BigDecimal folio) {
		this.folio = folio;
	}

}
