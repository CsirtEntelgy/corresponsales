package com.isban.corresponsalia.beans.monitoreo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class BeanMonitorLineaCred.
 */
public class BeanMonitorLineaCred implements  Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2083084908789240201L;
	
	/** The cod corresponsal. */
	String codCorresponsal;
	
	/** The nom corresponsal. */
	String nomCorresponsal;
	
	/** The saldo cheques. */
	String saldoCheques;
	
	/** The cred otorgado. */
	String credOtorgado;
	
	/** The cred dispuesto. */
	String credDispuesto;
	
	/** The cred disponible. */
	String credDisponible;
	
	/** The pendiente comp. */
	String pendienteComp;
	
	/** The limite alerta. */
	String limiteAlerta;
	
	/** The inicador alerta. */
	boolean inicadorAlerta;
	
	/**
	 * Gets the cod corresponsal.
	 *
	 * @return the cod corresponsal
	 */
	public String getCodCorresponsal() {
		return codCorresponsal;
	}
	
	/**
	 * Sets the cod corresponsal.
	 *
	 * @param codCorresponsal the new cod corresponsal
	 */
	public void setCodCorresponsal(String codCorresponsal) {
		this.codCorresponsal = codCorresponsal;
	}
	
	/**
	 * Gets the nom corresponsal.
	 *
	 * @return the nom corresponsal
	 */
	public String getNomCorresponsal() {
		return nomCorresponsal;
	}
	
	/**
	 * Sets the nom corresponsal.
	 *
	 * @param nomCorresponsal the new nom corresponsal
	 */
	public void setNomCorresponsal(String nomCorresponsal) {
		this.nomCorresponsal = nomCorresponsal;
	}
	
	/**
	 * Gets the saldo cheques.
	 *
	 * @return the saldo cheques
	 */
	public String getSaldoCheques() {
		return saldoCheques;
	}
	
	/**
	 * Sets the saldo cheques.
	 *
	 * @param saldoCheques the new saldo cheques
	 */
	public void setSaldoCheques(String saldoCheques) {
		this.saldoCheques = saldoCheques;
	}
	
	/**
	 * Gets the cred otorgado.
	 *
	 * @return the cred otorgado
	 */
	public String getCredOtorgado() {
		return credOtorgado;
	}
	
	/**
	 * Sets the cred otorgado.
	 *
	 * @param credOtorgado the new cred otorgado
	 */
	public void setCredOtorgado(String credOtorgado) {
		this.credOtorgado = credOtorgado;
	}
	
	/**
	 * Gets the cred dispuesto.
	 *
	 * @return the cred dispuesto
	 */
	public String getCredDispuesto() {
		return credDispuesto;
	}
	
	/**
	 * Sets the cred dispuesto.
	 *
	 * @param credDispuesto the new cred dispuesto
	 */
	public void setCredDispuesto(String credDispuesto) {
		this.credDispuesto = credDispuesto;
	}
	
	/**
	 * Gets the cred disponible.
	 *
	 * @return the cred disponible
	 */
	public String getCredDisponible() {
		return credDisponible;
	}
	
	/**
	 * Sets the cred disponible.
	 *
	 * @param credDisponible the new cred disponible
	 */
	public void setCredDisponible(String credDisponible) {
		this.credDisponible = credDisponible;
	}
	
	/**
	 * Gets the pendiente comp.
	 *
	 * @return the pendiente comp
	 */
	public String getPendienteComp() {
		return pendienteComp;
	}
	
	/**
	 * Sets the pendiente comp.
	 *
	 * @param pendienteComp the new pendiente comp
	 */
	public void setPendienteComp(String pendienteComp) {
		this.pendienteComp = pendienteComp;
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
	 * Checks if is inicador alerta.
	 *
	 * @return true, if is inicador alerta
	 */
	public boolean isInicadorAlerta() {
		return inicadorAlerta;
	}
	
	/**
	 * Sets the inicador alerta.
	 *
	 * @param inicadorAlerta the new inicador alerta
	 */
	public void setInicadorAlerta(boolean inicadorAlerta) {
		this.inicadorAlerta = inicadorAlerta;
	}

}
