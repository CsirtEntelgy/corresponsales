package com.isban.corresponsalia.comunes;

import java.util.TreeMap;

import com.isban.ebe.commons.architech.ArchitechEBE;

public class LineaComisiones extends ArchitechEBE {

	/** The Constant TXT_ENTI_IN. */
	private static final String TXT_ENTI_IN = "Entidad bancaria inv&aacute;lida: ";

	/** The Constant TXT_CEROS. */
	private static final String TXT_CEROS = " completar con ceros a la izquierda";

	/** The Constant TXT_E_CANAL. */
	private static final String TXT_E_CANAL = "C&oacute;digo de Identificaci&oacute;n del canal Corresponsalia inv&aacute;lida: ";

	/** The Constant TXT_E_CORERESP. */
	private static final String TXT_E_CORERESP = "C&oacute;digo de Identificaci&oacute;n del Corresponsal inv&aacute;lido: ";
	
	/** The Constant CONS_0A9. */
	private static final String CONS_0A9 = "[0-9]";
	
	/** The GlobalArchitech para las validaciones generales*/
	private GlobalArchitech valida = new GlobalArchitech();
	
	/** The errorLinea para ver si hay o no algun error en las lineas*/
	public boolean errorLinea;
	
	/** The listaErrores Lista de errores en todas las lineas*/
	public TreeMap<String, String> listaErrores = new TreeMap<String, String>();
	
	
	/**
	 * @return listaErrores
	 */
	public TreeMap<String, String> getListaErrores() {
		return listaErrores;
	}
	
	/**
	 * @param listaErrores
	 */
	public void setListaErrores(TreeMap<String, String> listaErrores) {
		this.listaErrores = listaErrores;
	}
	
	/**
	 * @return errorLinea
	 */
	public boolean isErrorLinea() {
		return errorLinea;
	}
	
	/**
	 * @param errorLinea
	 */
	public void setErrorLinea(boolean errorLinea) {
		this.errorLinea = errorLinea;
	}
	
	/**
	 * @param linea linea a evaluar
	 * @param i numero de la linea
	 */
	public void validaciones (String linea, int i){		
		try{
			if (linea.length() != 194) {
				this.listaErrores.put((Integer.valueOf(i)).toString(),
						"La longitud (" + linea.length()
								+ ")de la l&iacute;nea " + i
								+ " es inv&aacute;lida.");
				errorLinea = true;
			}
			else{
				valCampos(linea, i);
			}
		}catch (Exception ex){
			error("Error al validar el nombre del archivo: " + ex.getMessage());
		}
	}	
	private void valCampos(String linea, int i){
		
		if (!valida.validaCadena(linea.substring(0, 1),null)||(!"A".equalsIgnoreCase(linea.substring(0, 1))
				&& !"B".equalsIgnoreCase(linea.substring(0, 1)) && !"M"
				.equalsIgnoreCase(linea.substring(0, 1)))) { // operacion
			this.listaErrores.put(i + "-1", "Operaci&oacute;n inv&aacute;lida :"
					+ linea.substring(0, 1));
			errorLinea = true;
		}

		if (!valida.isNumero(linea.substring(1, 5))) { // entidad banco
			this.listaErrores.put(i + "-2", TXT_ENTI_IN
					+ linea.substring(1, 5));
			errorLinea = true;
		}else if(linea.substring(1, 5).trim().length()<4){
			this.listaErrores.put(i + "-2", TXT_ENTI_IN
					+ linea.substring(1, 5)+ TXT_CEROS);
			errorLinea = true;
		}

		if (!valida.isNumero(linea.substring(5, 7))) // Codigo de Identificacion
		// del canal Corresponsalia
		{
			this.listaErrores.put(i + "-3",
					TXT_E_CANAL
							+ linea.substring(5, 7));
			errorLinea = true;
		}else if(linea.substring(5, 7).trim().length()<2){
			this.listaErrores.put(i + "-3", TXT_E_CANAL
					+ linea.substring(5, 7)+ TXT_CEROS);
			errorLinea = true;
		}

		if (!valida.isNumero(linea.substring(7, 11))) // Codigo de Identificacion 
			// del Corresponsal (Centro de Costos)
			{
			this.listaErrores.put(i + "-4",
						TXT_E_CORERESP
								+ linea.substring(7, 11));
				errorLinea = true;
			}else if(linea.substring(7, 11).trim().length()<2){
				this.listaErrores.put(i + "-4", TXT_E_CORERESP
						+ linea.substring(7, 11)+ TXT_CEROS);
				errorLinea = true;
		}

		if (!valida.validaCadena(linea.substring(11, 21).trim(),CONS_0A9)) // Codigo de Identificacion 
			// de la Sucursal
			{
			this.listaErrores.put(i + "-5",
						"C&oacute;digo de Identificaci&oacute;n de la Sucursal inv&aacute;lido:"
								+ linea.substring(11, 21));
				errorLinea = true;
			}

		if (!valida.isNumero(linea.substring(21, 27))) // Tipo Operacion 
			// Corresponsal
			{
			this.listaErrores.put(i + "-6",
						"Tipo de operaci&oacute;n Corresponsal inv&aacute;lido:"
								+ linea.substring(21, 27));
				errorLinea = true;
			}else if(linea.substring(21, 27).trim().length()<6){
				this.listaErrores.put(i + "-6", "Tipo de operaci&oacute;n Corresponsal inv&aacute;lido:"
						+ linea.substring(21, 27)+ TXT_CEROS);
				errorLinea = true;
		}

		if (!valida.isDecimal(linea.substring(27, 37))) // Tipo Operacion 
			// Canal
			{
			this.listaErrores.put(i + "-7",
						"Tipo de operaci&oacute;n Canal inv&aacute;lido:"
								+ linea.substring(27, 37));
				errorLinea = true;
			}
		
		if (!valida.validaCadena(linea.substring(37, 39),null)||(!"01".equalsIgnoreCase(linea.substring(37, 39))
				&& !"02".equalsIgnoreCase(linea.substring(37, 39)) && !"03"
				.equalsIgnoreCase(linea.substring(37, 39)))) { // nivel de Comision
			this.listaErrores.put(i + "-8", "Nivel de Comisi&oacute;n inv&aacute;lido: "
					+ linea.substring(37, 39));
			errorLinea = true;
		}
		
		if (!valida.validaCadena(linea.substring(39, 43),null)) { // Region de la Sucursal
			this.listaErrores.put(i + "-9", "Region de la Sucursal inv&aacute;lida: "
					+ linea.substring(39, 43));
			errorLinea = true;
		}
		
		if (!valida.validaCadena(linea.substring(43, 48),null)) { // Zona de la Sucursal
			this.listaErrores.put(i + "-10", "Zona de la Sucursal inv&aacute;lida: "
					+ linea.substring(43, 48));
			errorLinea = true;
		}

		if (!valida.isDecimal(linea.substring(48, 63))) // Importe Rango Maximo
			{
			this.listaErrores.put(i + "-11",
						"Importe Rango M&aacute;ximo inv&aacute;lido: "
								+ linea.substring(48, 63));
				errorLinea = true;
			}else if(linea.substring(48, 63).trim().length()<15){
				this.listaErrores.put(i + "-11", "Importe Rango M&aacute;ximo inv&aacute;lido: "
						+ linea.substring(48, 63)+ TXT_CEROS);
				errorLinea = true;
		}
		
		if (!valida.validaCadena(linea.substring(63, 73),"(00|99|19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])")) { 
			// Fecha inicio parametria
			this.listaErrores.put(i + "-12", "Fecha inicio de parametr&iacute;a inv&aacute;lida: "
					+ linea.substring(63, 73));
			errorLinea = true;
		}

		if (!valida.validaCadena(linea.substring(73, 83),"(00|99|19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])")) { 
			// Fecha inicio parametria
			this.listaErrores.put(i + "-13", "Fecha Fin de parametr&iacute;a inv&aacute;lida: "
					+ linea.substring(73, 83));
			errorLinea = true;
		}
		
		if (!valida.validaCadena(linea.substring(83, 86),null)||(!"CM1".equalsIgnoreCase(linea.substring(83, 86))
				&& !"CM2".equalsIgnoreCase(linea.substring(83, 86)))) { // Tipo de Comision
			this.listaErrores.put(i + "-14", "Tipo de Comisi&oacute;n inv&aacute;lido: "
					+ linea.substring(83, 86));
			errorLinea = true;
		}
		
		if (!valida.validaCadena(linea.substring(86, 89),null)||(!"PC1".equalsIgnoreCase(linea.substring(86, 89))
				&& !"PC2".equalsIgnoreCase(linea.substring(86, 89)) && !"PC3"
				.equalsIgnoreCase(linea.substring(86, 89)))) { // Identificador estado de param
			this.listaErrores.put(i + "-15", "Identificador del estatus inv&aacute;lido: "
					+ linea.substring(86, 89));
			errorLinea = true;
		}

		if (!valida.isDecimal(linea.substring(89, 104))) // Importe Comision Total
			{
			this.listaErrores.put(i + "-16",
						"Importe Comisi&oacute;n Total inv&aacute;lido: "
								+ linea.substring(89, 104));
				errorLinea = true;
			}else if(linea.substring(89, 104).trim().length()<15){
				this.listaErrores.put(i + "-16", "Importe Comisi&oacute;n Total inv&aacute;lido: "
						+ linea.substring(89, 104)+ TXT_CEROS);
				errorLinea = true;
		}

		if (!valida.isDecimal(linea.substring(104, 119))) // Importe Comision Banco
			{
			this.listaErrores.put(i + "-17",
						"Importe Comisi&oacute;n Banco inv&aacute;lido: "
								+ linea.substring(104, 119));
				errorLinea = true;
			}else if(linea.substring(104, 119).trim().length()<15){
				this.listaErrores.put(i + "-17", "Importe Comisi&oacute;n Banco inv&aacute;lido: "
						+ linea.substring(104, 119)+ TXT_CEROS);
				errorLinea = true;
		}

		if (!valida.isDecimal(linea.substring(119, 134))) // Importe Comision Corresponsal
			{
			this.listaErrores.put(i + "-18",
						"Importe Comisi&oacute;n Corresponsal inv&aacute;lido: "
								+ linea.substring(119, 134));
				errorLinea = true;
			}else if(linea.substring(119, 134).trim().length()<15){
				this.listaErrores.put(i + "-18", "Importe Comisi&oacute;n Corresponsal inv&aacute;lido: "
						+ linea.substring(119, 134)+ TXT_CEROS);
				errorLinea = true;
		}

		if (!valida.isDecimal(linea.substring(134, 149))) // Importe Comision Cliente
			{
			this.listaErrores.put(i + "-19",
						"Importe Comisi&oacute;n Clienet inv&aacute;lido: "
								+ linea.substring(134, 149));
				errorLinea = true;
			}else if(linea.substring(134, 149).trim().length()<15){
				this.listaErrores.put(i + "-19", "Importe Comisi&oacute;n Cliente inv&aacute;lido: "
						+ linea.substring(134, 149)+ TXT_CEROS);
				errorLinea = true;
		}

		if (!valida.isDecimal(linea.substring(149, 164))) // Porcentaje comision banco
			{
			this.listaErrores.put(i + "-20",
						"Porcentaje Comisi&oacute;n Banco inv&aacute;lido: "
								+ linea.substring(149, 164));
				errorLinea = true;
			}else if(linea.substring(149, 164).trim().length()<15){
				this.listaErrores.put(i + "-20", "Porcentaje Comisi&oacute;n Banco inv&aacute;lido: "
						+ linea.substring(149, 164)+ TXT_CEROS);
				errorLinea = true;
		}

		if (!valida.isDecimal(linea.substring(164, 179))) // Porcentaje comision corresponsal
			{
			this.listaErrores.put(i + "-21",
						"Porcentaje Comisi&oacute;n Corresponsal inv&aacute;lido: "
								+ linea.substring(164, 179));
				errorLinea = true;
			}else if(linea.substring(164, 179).trim().length()<15){
				this.listaErrores.put(i + "-21", "Porcentaje Comisi&oacute;n Corresponsal inv&aacute;lido: "
						+ linea.substring(164, 179)+ TXT_CEROS);
				errorLinea = true;
		}

		if (!valida.isDecimal(linea.substring(179, 194))) // Porcentaje comision cliente
			{
			this.listaErrores.put(i + "-22",
						"Porcentaje Comisi&oacute;n Cliente inv&aacute;lido: "
								+ linea.substring(179, 194));
				errorLinea = true;
			}else if(linea.substring(179, 194).trim().length()<15){
				this.listaErrores.put(i + "-22", "Porcentaje Comisi&oacute;n Clienet inv&aacute;lido: "
						+ linea.substring(179, 194)+ TXT_CEROS);
				errorLinea = true;
		}
		
	}
}
