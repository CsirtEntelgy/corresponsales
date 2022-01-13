package com.isban.corresponsalia.beans.corresponsales;

import java.io.Serializable;
import java.util.ArrayList;

import com.isban.corresponsalia.beans.comunes.BeanSucursal;
import com.isban.ebe.commons.interfaces.BeanResultBO;


// TODO: Auto-generated Javadoc
/**
 * The Class BeanResultadoSucursal.
 */
public class BeanResultadoSucursal implements  BeanResultBO, Serializable{

	/** Corresponsalia. */
	private static final long serialVersionUID = -6099227249270463833L;
	
	/** The Constant CODE_EXITO_CONSULTA. */
	public static final String CODE_EXITO_CONSULTA         = "CCONT000";
	
	/** The Constant CODE_ERROR_CONSULTA. */
	public static final String CODE_ERROR_CONSULTA         = "CCONT999";

	/** The Registros. */
	private ArrayList<BeanSucursal>         Registros         = new ArrayList<BeanSucursal>();
	
	/** The Cod error. */
	private String                          CodError          = "";
	
	/** The Msg error. */
	private String                          MsgError          = "";
	
	/** The Referencia adelante. */
	private String                          ReferenciaAdelante= "";
	
	/** The Referencia atras. */
	private String                          ReferenciaAtras   = "";
	
	/** The mas atras. */
	private boolean							masAtras;
	
	/** The mas adelante. */
	private boolean							masAdelante;
	
	/** The nombre sucursal paginacion. */
	private String                          nombreSucursal   = "";
	
	/** The nombre sucursal paginacion atras. */
	private String                          nombreSucursalAtras   = "";
	
	/**
	 * Checks if is mas atras.
	 *
	 * @return true, if is mas atras
	 */
	public boolean isMasAtras() {
		return masAtras;
	}

	/**
	 * Sets the mas atras.
	 *
	 * @param masAtras the new mas atras
	 */
	public void setMasAtras(boolean masAtras) {
		this.masAtras = masAtras;
	}

	/**
	 * Checks if is mas adelante.
	 *
	 * @return true, if is mas adelante
	 */
	public boolean isMasAdelante() {
		return masAdelante;
	}

	/**
	 * Sets the mas adelante.
	 *
	 * @param masAdelante the new mas adelante
	 */
	public void setMasAdelante(boolean masAdelante) {
		this.masAdelante = masAdelante;
	}

	/**
	 * Sets the registros.
	 *
	 * @param pValue the new registros
	 */
	public void setRegistros(ArrayList<BeanSucursal> pValue){
		Registros = pValue;
	}

	/**
	 * Gets the registros.
	 *
	 * @return the registros
	 */
	public ArrayList<BeanSucursal> getRegistros(){
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
	 * Gets the referencia adelante.
	 *
	 * @return el referenciaAdelante
	 */
	public String getReferenciaAdelante() {
		return ReferenciaAdelante;
	}

	/**
	 * Sets the referencia adelante.
	 *
	 * @param referenciaAdelante el referenciaAdelante a establecer
	 */
	public void setReferenciaAdelante(String referenciaAdelante) {
		ReferenciaAdelante = referenciaAdelante;
	}

	/**
	 * Gets the referencia atras.
	 *
	 * @return el referenciaAtras
	 */
	public String getReferenciaAtras() {
		return ReferenciaAtras;
	}

	/**
	 * Sets the referencia atras.
	 *
	 * @param referenciaAtras el referenciaAtras a establecer
	 */
	public void setReferenciaAtras(String referenciaAtras) {
		ReferenciaAtras = referenciaAtras;
	}

	/**
	 * @return the nombreSucursal
	 */
	public String getNombreSucursal() {
		return nombreSucursal;
	}

	/**
	 * @param nombreSucursal the nombreSucursal to set
	 */
	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}

	/**
	 * @return the nombreSucursalAtras
	 */
	public String getNombreSucursalAtras() {
		return nombreSucursalAtras;
	}

	/**
	 * @param nombreSucursalAtras the nombreSucursalAtras to set
	 */
	public void setNombreSucursalAtras(String nombreSucursalAtras) {
		this.nombreSucursalAtras = nombreSucursalAtras;
	}

}
