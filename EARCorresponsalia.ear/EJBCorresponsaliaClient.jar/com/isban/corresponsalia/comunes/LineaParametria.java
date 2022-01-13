package com.isban.corresponsalia.comunes;

import java.util.TreeMap;

import com.isban.ebe.commons.architech.ArchitechEBE;

public class LineaParametria extends ArchitechEBE {

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
			if (linea.length() != 138) {
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
		
		if (!valida.validaCadena(linea.substring(0, 2),null)||(!"01".equalsIgnoreCase(linea.substring(0, 2))
				&& !"02".equalsIgnoreCase(linea.substring(0, 2)) && !"03"
				.equalsIgnoreCase(linea.substring(0, 2)))) { // operacion
			listaErrores.put(i + "-1", "Operaci&oacute;n inv&aacute;lida :"
					+ linea.substring(0, 2));
			errorLinea = true;
		}
		
		if (!valida.isNumero(linea.substring(2, 6))) { // entidad banco
			listaErrores.put(i + "-2", TXT_ENTI_IN
					+ linea.substring(2, 6));
			errorLinea = true;
		}if (!valida.isNumero(linea.substring(6, 8))) // Codigo de Identificacion
		// del canal Corresponsalia
		{
			listaErrores.put(i + "-3",
					TXT_E_CANAL
							+ linea.substring(6, 8));
			errorLinea = true;
		}else if(linea.substring(6, 8).trim().length()<2){
			listaErrores.put(i + "-3", TXT_E_CANAL
					+ linea.substring(6, 8)+ TXT_CEROS);
			errorLinea = true;
		}

		if (!valida.isNumero(linea.substring(8, 12))) // Codigo de Identificacion 
			// del Corresponsal (Centro de Costos)
			{
				listaErrores.put(i + "-4",
						TXT_E_CORERESP
								+ linea.substring(8, 12));
				errorLinea = true;
			}else if(linea.substring(8, 12).trim().length()<2){
				listaErrores.put(i + "-4", TXT_E_CORERESP
						+ linea.substring(8, 12)+ TXT_CEROS);
				errorLinea = true;
		}

		if (!valida.validaCadena(linea.substring(12, 22).trim(),CONS_0A9)) // Codigo de Identificacion 
			// de la Sucursal
			{
				listaErrores.put(i + "-5",
						"C&oacute;digo de Identificaci&oacute;n de la Sucursal inv&aacute;lido:"
								+ linea.substring(12, 22));
				errorLinea = true;
			}
		
		if (!valida.validaCadena(linea.substring(22, 24),null)||(!"01".equalsIgnoreCase(linea.substring(22, 24))
				&& !"02".equalsIgnoreCase(linea.substring(22, 24)) && !"03"
				.equalsIgnoreCase(linea.substring(22, 24)))) { // nivel de Parametria
			listaErrores.put(i + "-6", "Nivel de Parametr&iacute;a inv&aacute;lido: "
					+ linea.substring(22, 24));
			errorLinea = true;
		}

		if (!valida.validaCadena(linea.substring(24, 30).trim(),CONS_0A9)) // Tipo Operacion 
			// Canal
			{
				listaErrores.put(i + "-7",
						"Tipo de operaci&oacute;n Canal inv&aacute;lido:"
								+ linea.substring(24, 30));
				errorLinea = true;
			}
		
		if (!valida.validaCadena(linea.substring(30, 40).trim(),CONS_0A9)) // Tipo Operacion 
			// Corresponsal
			{
				listaErrores.put(i + "-8",
						"Tipo de operaci&oacute;n Corresponsal inv&aacute;lido:"
								+ linea.substring(30, 40));
				errorLinea = true;
			}
		
		if (!valida.validaCadena(linea.substring(40, 44).trim(),"[A-Za-z0-9]")) // Clave transaccion 
			{
				listaErrores.put(i + "-9",
						"Clave Transacci&oacute;n inv&aacute;lida: "
								+ linea.substring(40, 44));
				errorLinea = true;
			}
		
		if (!valida.validaCadena(linea.substring(44, 47),null)||(!"CS2".equalsIgnoreCase(linea.substring(44, 47))
				&& !"CS3".equalsIgnoreCase(linea.substring(44, 47)) && !"CS4"
				.equalsIgnoreCase(linea.substring(44, 47)))) { // Estatus de Parametria
			listaErrores.put(i + "-10", "Estatus de Parametr&iacute;a inv&aacute;lido: "
					+ linea.substring(44, 47));
			errorLinea = true;
		}
		
		if (!valida.validaCadena(linea.substring(47, 50),null)||(!"DV1".equalsIgnoreCase(linea.substring(47, 50)))){ 
			// Divisa
			listaErrores.put(i + "-11", "Divisa inv&aacute;lida: "
					+ linea.substring(47, 50));
			errorLinea = true;
		}

		if (!valida.isNumero(linea.substring(50, 56))) // Hora inicio
			{
				listaErrores.put(i + "-12",
						"Hora inicio inv&aacute;lida: "
								+ linea.substring(50, 56));
				errorLinea = true;
			}else if(linea.substring(50, 56).trim().length()<6){
				listaErrores.put(i + "-12", "Hora inicio inv&aacute;lida: "
						+ linea.substring(50, 56)+ TXT_CEROS);
				errorLinea = true;
		}

		if (!valida.isNumero(linea.substring(56, 62))) // Hora fin
			{
				listaErrores.put(i + "-13",
						"Hora fin inv&aacute;lida: "
								+ linea.substring(56, 62));
				errorLinea = true;
			}else if(linea.substring(56, 62).trim().length()<6){
				listaErrores.put(i + "-13", "Hora fin inv&aacute;lida: "
						+ linea.substring(56, 62)+ TXT_CEROS);
				errorLinea = true;
		}

		if (!valida.isDecimal(linea.substring(62, 77))) // Importe minimo 
			{
				listaErrores.put(i + "-14",
						"Importe M&iacute;nimo inv&aacute;lido: "
								+ linea.substring(62, 77));
				errorLinea = true;
			}else if(linea.substring(62, 77).trim().length()<15){
				listaErrores.put(i + "-14", "Importe M&iacute;nimo inv&aacute;lido: "
						+ linea.substring(62, 77)+ TXT_CEROS);
				errorLinea = true;
		}

		if (!valida.isDecimal(linea.substring(77, 92))) // Importe maximo 
			{
				listaErrores.put(i + "-15",
						"Importe M&aacute;ximo inv&aacute;lido: "
								+ linea.substring(77, 92));
				errorLinea = true;
			}else if(linea.substring(77, 92).trim().length()<15){
				listaErrores.put(i + "-15", "Importe M&aacute;ximo inv&aacute;lido: "
						+ linea.substring(77, 92)+ TXT_CEROS);
				errorLinea = true;
		}

		if (!valida.isDecimal(linea.substring(92, 107))) // Importe maximo diario 
			{
				listaErrores.put(i + "-16",
						"Importe M&aacute;ximo diario inv&aacute;lido: "
								+ linea.substring(92, 107));
				errorLinea = true;
			}else if(linea.substring(92, 107).trim().length()<15){
				listaErrores.put(i + "-16", "Importe M&aacute;ximo diario inv&aacute;lido: "
						+ linea.substring(92, 107)+ TXT_CEROS);
				errorLinea = true;
		}

		if (!valida.isDecimal(linea.substring(107, 122))) // Importe maximo mensual 
			{
				listaErrores.put(i + "-17",
						"Importe M&aacute;ximo mensual inv&aacute;lido: "
								+ linea.substring(107, 122));
				errorLinea = true;
			}else if(linea.substring(107, 122).trim().length()<15){
				listaErrores.put(i + "-17", "Importe M&aacute;ximo mensual inv&aacute;lido: "
						+ linea.substring(107, 122)+ TXT_CEROS);
				errorLinea = true;
		}
		
		if (linea.substring(122, 126).trim().length()!=0) { // Clave de operacion 1
			if (!valida.isNumero(linea.substring(122, 126))){
				listaErrores.put(i + "-18", "Clave de Operaci&oacute;n 1 inv&aacute;lida: "
						+ linea.substring(122, 126));
				errorLinea = true;
			}else if(linea.substring(122, 126).trim().length()<4){
				listaErrores.put(i + "-18", "Clave de Operaci&oacute;n 1 inv&aacute;lida: "
						+ linea.substring(122, 126)+ TXT_CEROS);
				errorLinea = true;
				}
		}
		
		if (linea.substring(126, 130).trim().length()!=0) { // Clave de contra operacion 1
			if (!valida.isNumero(linea.substring(126, 130))){
				listaErrores.put(i + "-19", "Clave de Contra Operaci&oacute;n 1 inv&aacute;lida: "
						+ linea.substring(126, 130));
				errorLinea = true;
			}else if(linea.substring(126, 130).trim().length()<4){
				listaErrores.put(i + "-19", "Clave de Contra Operaci&oacute;n 1 inv&aacute;lida: "
						+ linea.substring(126, 130)+ TXT_CEROS);
				errorLinea = true;
				}
		}
		
		if (linea.substring(130, 134).trim().length()!=0) { // Clave de operacion 2
			if (!valida.isNumero(linea.substring(130, 134))){
				listaErrores.put(i + "-20", "Clave de Operaci&oacute;n 2 inv&aacute;lida: "
						+ linea.substring(130, 134));
				errorLinea = true;
			}else if(linea.substring(130, 134).trim().length()<4){
				listaErrores.put(i + "-20", "Clave de Operaci&oacute;n 2 inv&aacute;lida: "
						+ linea.substring(130, 134)+ TXT_CEROS);
				errorLinea = true;
				}
		}
		
		if (linea.substring(134, 138).trim().length()!=0) { // Clave de contra operacion 2
			if (!valida.isNumero(linea.substring(134, 138))){
				listaErrores.put(i + "-21", "Clave de Contra Operaci&oacute;n 2 inv&aacute;lida: "
						+ linea.substring(134, 138));
				errorLinea = true;
			}else if(linea.substring(134, 138).trim().length()<4){
				listaErrores.put(i + "-21", "Clave de Contra Operaci&oacute;n 2 inv&aacute;lida: "
						+ linea.substring(134, 138)+ TXT_CEROS);
				errorLinea = true;
				}
		}
	}
}
