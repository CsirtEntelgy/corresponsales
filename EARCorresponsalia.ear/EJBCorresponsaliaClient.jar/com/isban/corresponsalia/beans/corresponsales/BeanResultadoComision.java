package com.isban.corresponsalia.beans.corresponsales;

import java.io.Serializable;
import java.util.ArrayList;

import com.isban.corresponsalia.beans.comunes.BeanComisiones;


// TODO: Auto-generated Javadoc
/**
 * The Class BeanResultadoComision.
 */
public class BeanResultadoComision implements Serializable{
	
	/** The Constant serialVersionUID. */	
	private static final long serialVersionUID = -6858515588419827702L;

	/** The Registros. */
	private ArrayList<BeanComisiones> Registros        = new ArrayList<BeanComisiones>();
	
	/** The Cod error. */
	private String                          CodError         = "";
	
	/** The Msg error. */
	private String                          MsgError         = "";
	
	/**
	 * Gets the registros.
	 *
	 * @return the registros
	 */
	public ArrayList<BeanComisiones> getRegistros() {
		return Registros;
	}
	
	/**
	 * Sets the registros.
	 *
	 * @param registros the new registros
	 */
	public void setRegistros(ArrayList<BeanComisiones> registros) {
		Registros = registros;
	}
	
	/**
	 * Gets the cod error.
	 *
	 * @return the cod error
	 */
	public String getCodError() {
		return CodError;
	}
	
	/**
	 * Sets the cod error.
	 *
	 * @param codError the new cod error
	 */
	public void setCodError(String codError) {
		CodError = codError;
	}
	
	/**
	 * Gets the msg error.
	 *
	 * @return the msg error
	 */
	public String getMsgError() {
		return MsgError;
	}
	
	/**
	 * Sets the msg error.
	 *
	 * @param msgError the new msg error
	 */
	public void setMsgError(String msgError) {
		MsgError = msgError;
	}
	
	/**
	 * Gets the total registros.
	 *
	 * @return the total registros
	 */
	public int getTotalRegistros() {
		return Registros.size();
	}
	
}
