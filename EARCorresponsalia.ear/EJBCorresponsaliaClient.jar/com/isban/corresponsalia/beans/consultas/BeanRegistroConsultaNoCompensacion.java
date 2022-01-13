package com.isban.corresponsalia.beans.consultas;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class BeanRegistroConsultaNoCompensacion.
 */
public class BeanRegistroConsultaNoCompensacion implements  Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3059953185636151765L;
	
	/** The fecha lote. */
	private String fechaLote            = "";
	
	/** The total operaciones. */
	private String totalOperaciones     = "";
	
	/** The importe total. */
	private String importeTotal         = "";
	
	/** The estatus. */
	private String estatus              = "";
	
	/** The cargo comisiones. */
	private String cargoComisiones      = "";
	
	/** The abono comisiones. */
	private String abonoComisiones      = "";
	
	/** The abono iva. */
	private String abonoIva             = "";
	
	/** The codigo corresponsalia. */
	private String codigoCorresponsalia = "";

	/**
	 * Formato importe.
	 *
	 * @param Format the format
	 * @return the string
	 */
	private String formatoImporte(String Format) {
		try{
			DecimalFormat myFormatter = new DecimalFormat("###,###.##");
			Format = myFormatter.format(Double.parseDouble(Format)/100);
		}
		catch(Exception e){
			System.out.println("No fue posible dar formato");
		}
		return Format;
	}
	
	/**
	 * Gets the fecha lote.
	 *
	 * @return el fechaLote
	 */
	public String getFechaLote() {
		return fechaLote;
	}
	
	/**
	 * Gets the fecha lote format.
	 *
	 * @return the fecha lote format
	 */
	public String getFechaLoteFormat() {
		String fechaConFormato = fechaLote;
		try{
			SimpleDateFormat formatDateI = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatDateF = new SimpleDateFormat("dd/MM/yyyy");
			Date   fechaSinFormato = formatDateI.parse(fechaLote);
			fechaConFormato = formatDateF.format(fechaSinFormato);
		}
		catch(Exception e){
			System.out.println("Error al formatear fecha");
		}
		return fechaConFormato;
	
	}
	
	/**
	 * Sets the fecha lote.
	 *
	 * @param fechaLote el fechaLote a establecer
	 */
	public void setFechaLote(String fechaLote) {
		this.fechaLote = fechaLote;
	}
	
	/**
	 * Gets the total operaciones.
	 *
	 * @return el totalOperaciones
	 */
	public String getTotalOperaciones() {
		return totalOperaciones;
	}
	
	/**
	 * Gets the total operaciones format.
	 *
	 * @return the total operaciones format
	 */
	public String getTotalOperacionesFormat() {
		return "" + (Integer.parseInt(totalOperaciones));
	}
	
	/**
	 * Sets the total operaciones.
	 *
	 * @param totalOperaciones el totalOperaciones a establecer
	 */
	public void setTotalOperaciones(String totalOperaciones) {
		this.totalOperaciones = totalOperaciones;
	}
	
	/**
	 * Gets the importe total format.
	 *
	 * @return el importeTotal
	 */
	public String getImporteTotalFormat() {
		return formatoImporte(importeTotal);
	}
	
	/**
	 * Gets the importe total.
	 *
	 * @return the importe total
	 */
	public String getImporteTotal() {
		return importeTotal;
	}
	
	/**
	 * Sets the importe total.
	 *
	 * @param importeTotal el importeTotal a establecer
	 */
	public void setImporteTotal(String importeTotal) {
		this.importeTotal = importeTotal;
	}
	
	/**
	 * Gets the estatus.
	 *
	 * @return el estatus
	 */
	public String getEstatus() {
		return estatus.trim();
	}
	
	/**
	 * Sets the estatus.
	 *
	 * @param estatus el estatus a establecer
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
	/**
	 * Gets the cargo comisiones.
	 *
	 * @return el cargoComisiones
	 */
	public String getCargoComisiones() {
		return cargoComisiones;
	}
	
	/**
	 * Gets the cargo comisiones format.
	 *
	 * @return the cargo comisiones format
	 */
	public String getCargoComisionesFormat() {		
		return formatoImporte(cargoComisiones);
	}
	
	/**
	 * Sets the cargo comisiones.
	 *
	 * @param cargoComisiones el cargoComisiones a establecer
	 */
	public void setCargoComisiones(String cargoComisiones) {
		this.cargoComisiones = cargoComisiones;
	}
	
	/**
	 * Gets the abono comisiones.
	 *
	 * @return el abonoComisiones
	 */
	public String getAbonoComisiones() {
		return abonoComisiones;
	}
	
	/**
	 * Gets the abono comisiones format.
	 *
	 * @return the abono comisiones format
	 */
	public String getAbonoComisionesFormat() {
		return formatoImporte(abonoComisiones);
	}
	
	/**
	 * Sets the abono comisiones.
	 *
	 * @param abonoComisiones el abonoComisiones a establecer
	 */
	public void setAbonoComisiones(String abonoComisiones) {
		this.abonoComisiones = abonoComisiones;
	}
	
	/**
	 * Gets the abono iva.
	 *
	 * @return el abonoIva
	 */
	public String getAbonoIva() {
		return abonoIva;
	}
	
	/**
	 * Gets the abono iva format.
	 *
	 * @return the abono iva format
	 */
	public String getAbonoIvaFormat() {
		return formatoImporte(abonoIva);		
		
	}
	
	/**
	 * Sets the abono iva.
	 *
	 * @param abonoIva el abonoIva a establecer
	 */
	public void setAbonoIva(String abonoIva) {
		
		this.abonoIva = abonoIva;
	}
	
	/**
	 * Gets the codigo corresponsalia.
	 *
	 * @return el codigoCorresponsalia
	 */
	public String getCodigoCorresponsalia() {
		return codigoCorresponsalia;
	}
	
	/**
	 * Sets the codigo corresponsalia.
	 *
	 * @param codigoCorresponsalia el codigoCorresponsalia a establecer
	 */
	public void setCodigoCorresponsalia(String codigoCorresponsalia) {
		this.codigoCorresponsalia = codigoCorresponsalia;
	}
}