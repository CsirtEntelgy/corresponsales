package com.isban.corresponsalia.beans.consultas;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class BeanRegistroConsultaBitacora.
 */
public class BeanFiltroConsultaLogTrx implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6431893151458552688L;
	
	
	/** The fecha inicio. */
	String fechaInicio;
	
	/** The fecha final. */
	String fechaFinal;
	
	/** The folio. */
	String folio;
	
	/** The numero tarjeta. */
	String numeroTarjeta;
	
	/** The status operacion. */
	String statusOperacion;
	
	/** The pagina  */
	int 	pagina;
	
	/** The renglones por pagina. */
	int 	renglonesPorPagina = 20;
	
	/** The operacion. */
	String operacion ;
	

	/**
	 * Gets the fecha inicio.
	 *
	 * @return the fecha inicio
	 */
	public String getFechaInicio() {
		return fechaInicio;
	}
	
	/**
	 * Sets the fecha inicio.
	 *
	 * @param fechaInicio the new fecha inicio
	 */
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	/**
	 * Gets the fecha final.
	 *
	 * @return the fecha final
	 */
	public String getFechaFinal() {
		return fechaFinal;
	}
	
	/**
	 * Sets the fecha final.
	 *
	 * @param fechaFinal the new fecha final
	 */
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	
	/**
	 * Gets the folio.
	 *
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}
	
	/**
	 * Sets the folio.
	 *
	 * @param folio the new folio
	 */
	public void setFolio(String folio) {
		this.folio = folio;
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
	 * Gets the status operacion.
	 *
	 * @return the status operacion
	 */
	public String getStatusOperacion() {
		return statusOperacion;
	}
	
	/**
	 * Sets the status operacion.
	 *
	 * @param statusOperacion the new status operacion
	 */
	public void setStatusOperacion(String statusOperacion) {
		this.statusOperacion = statusOperacion;
	}
	
	/**
	 * Gets the pagina anterior.
	 *
	 * @return the pagina anterior
	 */
	public int getPagina() {
		return pagina;
	}
	
	/**
	 * Sets the pagina anterior.
	 *
	 * @param paginaAnterior the new pagina anterior
	 */
	public void setPagina(int pagina) {
		this.pagina = pagina;
	}
	
	/**
	 * Gets the renglones por pagina.
	 *
	 * @return the renglones por pagina
	 */
	public int getRenglonesPorPagina() {
		return renglonesPorPagina;
	}
	
	/**
	 * Sets the renglones por pagina.
	 *
	 * @param renglonesPorPagina the new renglones por pagina
	 */
	public void setRenglonesPorPagina(int renglonesPorPagina) {
		this.renglonesPorPagina = renglonesPorPagina;
	}
	
	/**
	 * Gets the operacion.
	 *
	 * @return the operacion
	 */
	public String getOperacion() {
		return operacion;
	}

	/**
	 * Sets the operacion.
	 *
	 * @param operacion the new operacion
	 */
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	
}
