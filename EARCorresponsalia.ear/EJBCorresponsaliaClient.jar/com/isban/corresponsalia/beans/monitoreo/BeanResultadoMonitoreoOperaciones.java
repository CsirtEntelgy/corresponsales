package com.isban.corresponsalia.beans.monitoreo;

import java.io.Serializable;
import java.util.ArrayList;

import com.isban.ebe.commons.interfaces.BeanResultBO;


// TODO: Auto-generated Javadoc
/**
 * The Class BeanResultadoMonitoreoOperaciones.
 */
public class BeanResultadoMonitoreoOperaciones implements  BeanResultBO, Serializable{

	/**
	 * Monitoreo Operaciones.
	 */
	private static final long serialVersionUID = -6099227249270463833L;
	
	/** The Constant CODE_EXITO_CONSULTA. */
	public static final String CODE_EXITO_CONSULTA         = "CCONT000";
	
	/** The Constant CODE_ERROR_CONSULTA. */
	public static final String CODE_ERROR_CONSULTA         = "CCONT999";

	
	/** The Registros. */
	private ArrayList<BeanMonitoreoOperaciones> Registros        = new ArrayList<BeanMonitoreoOperaciones>();
	
	/** The Cod error. */
	private String                          CodError             = "";
	
	/** The Msg error. */
	private String                          MsgError             = "";
	
	/** The referencia avanzar. */
	private String                          referenciaAvanzar    = "";
	
	/** The referencia retroceder. */
	private String                          referenciaRetroceder = "";
	
	/** The oper avanzar. */
	private String                          operAvanzar          = "";
	
	/** The hora avanzar. */
	private String                          horaAvanzar          = "";
	
	/** The oper retroceder. */
	private String                          operRetroceder  = "";
	
	/** The hora retroceder. */
	private String                          horaRetroceder       = "";
	
	/** The pag no. */
	private String                          pagNo                = "";
	
	/**
	 * Sets the registros.
	 *
	 * @param pValue the new registros
	 */
	public void setRegistros(ArrayList<BeanMonitoreoOperaciones> pValue){
		Registros = pValue;
	}

	/**
	 * Gets the registros.
	 *
	 * @return the registros
	 */
	public ArrayList<BeanMonitoreoOperaciones> getRegistros(){
		return Registros;
	}

	/**
	 * Gets the numero registros.
	 *
	 * @return the numero registros
	 */
	public int getNumeroRegistros(){
		return Registros.size();
	}


	/* (sin Javadoc)
	 * @see com.isban.ebe.commons.interfaces.BeanResultBO#getCodError()
	 */
	@Override
	public String getCodError() {
		return CodError;
	}

	/* (sin Javadoc)
	 * @see com.isban.ebe.commons.interfaces.BeanResultBO#getMsgError()
	 */
	@Override
	public String getMsgError() {
		return MsgError;
	}

	/* (sin Javadoc)
	 * @see com.isban.ebe.commons.interfaces.BeanResultBO#setCodError(java.lang.String)
	 */
	@Override
	public void setCodError(String codOper) {
		CodError = codOper;
		
	}

	/* (sin Javadoc)
	 * @see com.isban.ebe.commons.interfaces.BeanResultBO#setMsgError(java.lang.String)
	 */
	@Override
	public void setMsgError(String msgOper) {
		MsgError = msgOper;
		
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

	/**
	 * Gets the oper avanzar.
	 *
	 * @return the oper avanzar
	 */
	public String getOperAvanzar() {
		return operAvanzar;
	}

	/**
	 * Sets the oper avanzar.
	 *
	 * @param operAvanzar the new oper avanzar
	 */
	public void setOperAvanzar(String operAvanzar) {
		this.operAvanzar = operAvanzar;
	}

	/**
	 * Gets the hora avanzar.
	 *
	 * @return the hora avanzar
	 */
	public String getHoraAvanzar() {
		return horaAvanzar;
	}

	/**
	 * Sets the hora avanzar.
	 *
	 * @param horaAvanzar the new hora avanzar
	 */
	public void setHoraAvanzar(String horaAvanzar) {
		this.horaAvanzar = horaAvanzar;
	}

	/**
	 * Gets the oper retroceder.
	 *
	 * @return the oper retroceder
	 */
	public String getOperRetroceder() {
		return operRetroceder;
	}

	/**
	 * Sets the oper retroceder.
	 *
	 * @param operRetroceder the new oper retroceder
	 */
	public void setOperRetroceder(String operRetroceder) {
		this.operRetroceder = operRetroceder;
	}

	/**
	 * Gets the hora retroceder.
	 *
	 * @return the hora retroceder
	 */
	public String getHoraRetroceder() {
		return horaRetroceder;
	}

	/**
	 * Sets the hora retroceder.
	 *
	 * @param horaRetroceder the new hora retroceder
	 */
	public void setHoraRetroceder(String horaRetroceder) {
		this.horaRetroceder = horaRetroceder;
	}

	/**
	 * Gets the pag no.
	 *
	 * @return the pag no
	 */
	public String getPagNo() {
		return pagNo;
	}

	/**
	 * Sets the pag no.
	 *
	 * @param pagNo the new pag no
	 */
	public void setPagNo(String pagNo) {
		this.pagNo = pagNo;
	}

}

