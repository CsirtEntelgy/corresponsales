package com.isban.corresponsalia.beans.monitoreo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class BeanRegistroMonitoreoCredito.
 */
public class BeanRegistroMonitoreoCredito implements  Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6431893151458552688L;
	
	/** The corresponsal. */
	String corresponsal;
	
	/** The disponible cheques. */
	String disponibleCheques;
	
	/** The disponible credito. */
	String disponibleCredito;
	
	/** The saldo operado. */
	String saldoOperado;
	
	/** The limite alerta. */
	String limiteAlerta;
	
	/** The credito otorgado. */
	String creditoOtorgado;
	
	/**
	 * Gets the credito otorgado.
	 *
	 * @return the credito otorgado
	 */
	public String getCreditoOtorgado() {
		return creditoOtorgado;
	}
	
	/**
	 * Sets the credito otorgado.
	 *
	 * @param creditoOtorgado the new credito otorgado
	 */
	public void setCreditoOtorgado(String creditoOtorgado) {
		this.creditoOtorgado = creditoOtorgado;
	}
	
	/**
	 * Gets the disponible cheques.
	 *
	 * @return the disponible cheques
	 */
	public String getDisponibleCheques() {
		return disponibleCheques;
	}
	
	/**
	 * Sets the disponible cheques.
	 *
	 * @param disponibleCheques the new disponible cheques
	 */
	public void setDisponibleCheques(String disponibleCheques) {
		this.disponibleCheques = disponibleCheques;
	}
	
	/**
	 * Gets the disponible credito.
	 *
	 * @return the disponible credito
	 */
	public String getDisponibleCredito() {
		return disponibleCredito;
	}
	
	/**
	 * Sets the disponible credito.
	 *
	 * @param disponibleCredito the new disponible credito
	 */
	public void setDisponibleCredito(String disponibleCredito) {
		this.disponibleCredito = disponibleCredito;
	}
	
	/**
	 * Gets the limite alerta.
	 *
	 * @return the limite alerta
	 */
	public String getLimiteAlerta() {
		return limiteAlerta;
	}
	
	/**
	 * Sets the limite alerta.
	 *
	 * @param limiteAlerta the new limite alerta
	 */
	public void setLimiteAlerta(String limiteAlerta) {
		this.limiteAlerta = limiteAlerta;
	}
	
	/**
	 * Gets the saldo operado.
	 *
	 * @return the saldo operado
	 */
	public String getSaldoOperado() {
		return saldoOperado;
	}
	
	/**
	 * Sets the saldo operado.
	 *
	 * @param saldoOperado the new saldo operado
	 */
	public void setSaldoOperado(String saldoOperado) {
		this.saldoOperado = saldoOperado;
	}
	
	/**
	 * Gets the corresponsal.
	 *
	 * @return the corresponsal
	 */
	public String getCorresponsal() {
		return corresponsal;
	}
	
	/**
	 * Sets the corresponsal.
	 *
	 * @param corresponsal the new corresponsal
	 */
	public void setCorresponsal(String corresponsal) {
		this.corresponsal = corresponsal;
	}	
}
