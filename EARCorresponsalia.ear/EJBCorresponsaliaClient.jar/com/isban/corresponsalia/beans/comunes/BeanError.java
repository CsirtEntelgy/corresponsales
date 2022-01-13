package com.isban.corresponsalia.beans.comunes;

import java.io.Serializable;


// TODO: Auto-generated Javadoc
/**
 * The Class BeanError.
 */
public class BeanError  implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8313678552308494022L;
	
	/** The codigo error. */
	private String codigoError = "";
	
	/** The msg error. */
	private String msgError = "";
	
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
	 * Gets the msg error.
	 *
	 * @return the msg error
	 */
	public String getMsgError() {
		return msgError;
	}
	
	/**
	 * Sets the msg error.
	 *
	 * @param msgError the new msg error
	 */
	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}
	
	
}


