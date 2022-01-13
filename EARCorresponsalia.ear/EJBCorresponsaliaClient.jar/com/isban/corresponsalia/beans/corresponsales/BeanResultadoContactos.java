package com.isban.corresponsalia.beans.corresponsales;

import java.io.Serializable;
import java.util.ArrayList;

import com.isban.corresponsalia.beans.comunes.BeanContacto;
import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.ebe.commons.interfaces.BeanResultBO;


// TODO: Auto-generated Javadoc
/**
 * The Class BeanResultadoContactos.
 */
public class BeanResultadoContactos implements  BeanResultBO, Serializable{

	/** Corresponsalia. */
	private static final long serialVersionUID = -6099227249270463833L;
	
	/** The Constant CODE_EXITO_CONSULTA. */
	public static final String CODE_EXITO_CONSULTA         = "CCONT000";
	
	/** The Constant CODE_ERROR_CONSULTA. */
	public static final String CODE_ERROR_CONSULTA         = "CCONT999";

	
	/** The Registros. */
	private ArrayList<BeanContacto> Registros        = new ArrayList<BeanContacto>();
	
	/** The Cod error. */
	private String                          CodError         		= "";
	
	/** The Msg error. */
	private String                          MsgError         		= "";
	
	/** The referencia avanzar. */
	private String                          referenciaAvanzar 		= "";
	
	/** The referencia retroceder. */
	private String                          referenciaRetroceder 	= "";
	
	/** The mas atras. */
	private boolean							masAtras;
	
	/** The mas adelante. */
	private boolean							masAdelante;
	
	
	
	/**
	 * Sets the registros.
	 *
	 * @param pValue the new registros
	 */
	public void setRegistros(ArrayList<BeanContacto> pValue){
		Registros = pValue;
	}

	/**
	 * Gets the registros.
	 *
	 * @return the registros
	 */
	public ArrayList<BeanContacto> getRegistros(){
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
	 * @return the referencia avanzar
	 */
	public String getReferenciaAvanzar() {
		return referenciaAvanzar;
	}

	/**
	 * Sets the referencia avanzar.
	 *
	 * @param referenciaAvanzar the new referencia avanzar
	 */
	public void setReferenciaAvanzar(String referenciaAvanzar) {
		this.referenciaAvanzar = referenciaAvanzar;
	}

	/**
	 * Gets the referencia retroceder.
	 *
	 * @return the referencia retroceder
	 */
	public String getReferenciaRetroceder() {
		return referenciaRetroceder;
	}

	/**
	 * Sets the referencia retroceder.
	 *
	 * @param referenciaRetroceder the new referencia retroceder
	 */
	public void setReferenciaRetroceder(String referenciaRetroceder) {
		this.referenciaRetroceder = referenciaRetroceder;
	}

	/**
	 * Gets the mas atras.
	 *
	 * @return the mas atras
	 */
	public boolean getMasAtras() {
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
	 * Gets the mas adelante.
	 *
	 * @return the mas adelante
	 */
	public boolean getMasAdelante() {
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

}

