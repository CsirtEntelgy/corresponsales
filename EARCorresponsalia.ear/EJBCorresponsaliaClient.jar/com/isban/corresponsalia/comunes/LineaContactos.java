package com.isban.corresponsalia.comunes;

import java.util.TreeMap;

import com.isban.ebe.commons.architech.ArchitechEBE;

public class LineaContactos extends ArchitechEBE {

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
			if (linea.length() != 297) {
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
		
		if (!valida.validaCadena(linea.substring(11, 16),"[A-Za-z0-9]")) { // Codigo zona
			this.listaErrores.put(i + "-5", "C&oacute;digo de zona del inv&aacute;lido:"
					+ linea.substring(11, 16));
			errorLinea = true;
		}
		
		if (!( "CS2".equalsIgnoreCase(linea.substring(16, 19))|| "CS4"
				.equalsIgnoreCase(linea.substring(16, 19)))) { // Identificador
			// de Estatus del Corresponsal
			this.listaErrores.put(i + "-6",
					"Identificador de Estatus del Corresponsal inv&aacute;lido:"
							+ linea.substring(16, 19));
			errorLinea = true;
		}

		if (!valida.validaCadena(linea.substring(19, 79),null)) { // Nombre del corresponsal
			this.listaErrores.put(i + "-7",
					"Nombre del Corresponsal inv&aacute;lido:"
							+ linea.substring(19, 79));
			errorLinea = true;
		}

		if (!valida.validaCadena(linea.substring(79, 99), null)) { // Puesto del
			// contacto
			this.listaErrores.put(i + "-8", "Puesto del Contacto inv&aacute;lido:"
					+ linea.substring(79, 99));
			errorLinea = true;
		}

		if (!valida.validaCadena(linea.substring(99, 109), null)) { // Area del
			// contacto
			this.listaErrores.put(i + "-9", "Area del Contacto inv&aacute;lido:"
					+ linea.substring(99, 109));
			errorLinea = true;
		}

		if (!valida.validaCadena(linea.substring(109, 129).trim(), null)) { // Calle del domicilio
			// del contacto
			this.listaErrores.put(i + "-10", "Calle del Domicilio del Contacto inv&aacute;lido:"
					+ linea.substring(109, 129));
			errorLinea = true;
		}

		if (!valida.validaCadena(linea.substring(129, 134),null)) { // Numero externo
			// del domicilio
			// del contacto
			this.listaErrores.put(i + "-11",
					"Numero externo inv&aacute;lido:"
							+ linea.substring(129, 134));
			errorLinea = true;
		}
		
		if (!valida.validaCadena(linea.substring(134, 139),null)) { // Numero interno
			// del domicilio
			this.listaErrores.put(i + "-12",
					"Numero Interno inv&aacute;lido:"
							+ linea.substring(134, 139));
			errorLinea = true;
		}
		
		if (!valida.validaCadena(linea.substring(139, 159).trim(), null)) { // Domicilio
			// del
			// Contacto
			// COLONIA
			this.listaErrores.put(i + "-13",
					"Domicilio del Contacto, Colonia inv&aacute;lida:"
							+ linea.substring(139, 159));
			errorLinea = true;
		}
		
		if (!valida.validaCadena(linea.substring(159, 179), null)) {
			// Domicilio del Contacto DELEGACION / MUNICIPIO
			this.listaErrores.put(i + "-14",
					"Domicilio del Contacto, Delegaci&oacute;n / Municipio inv&aacute;lido:"
							+ linea.substring(159, 179));
			errorLinea = true;
		}
		
		if (!valida.validaCadena(linea.substring(179, 199), null)) {
			// Domicilio del Contacto CIUDAD
			this.listaErrores.put(i + "-15",
					"Domicilio del Contacto, Ciudad inv&aacute;lido:"
							+ linea.substring(179, 199));
			errorLinea = true;
		}
		
		if (!valida.validaCadena(linea.substring(199, 219), "[A-Za-z .]")) {
			// Domicilio del Contacto ENTIDAD FEDERATIVA
			this.listaErrores.put(i + "-16",
					"Domicilio del Contacto, Entidad Federativa inv&aacute;lida:"
							+ linea.substring(199, 219));
			errorLinea = true;
		}
		
		if (!valida.validaCadena(linea.substring(219, 227).trim(),CONS_0A9)) // Codigo Postal
			// del Contacto
			{ 
			this.listaErrores.put(i + "-17",
						"C&oacute;digo Postal del contacto inv&aacute;lido:"
								+ linea.substring(219, 227));
				errorLinea = true;
			}else if(linea.substring(219, 227).trim().length()<5){
				this.listaErrores.put(i + "-17", "C&oacute;digo Postal del contacto inv&aacute;lido:"
						+ linea.substring(219, 227)+ TXT_CEROS);
				errorLinea = true;
			}
		
		if (!valida.isDecimal(linea.substring(227, 237))) // Telefono oficina
			// del Contacto
			{ 
			this.listaErrores.put(i + "-18",
						"Tel&eacute;fono de Oficina del contacto inv&aacute;lido:"
								+ linea.substring(227, 237));
				errorLinea = true;
			}else if(linea.substring(227, 237).trim().length()<10){
				this.listaErrores.put(i + "-18", "Tel&eacute;fono de Oficina del contacto inv&aacute;lido:"
						+ linea.substring(227, 237)+ TXT_CEROS);
				errorLinea = true;
			}
		
		if (!valida.isDecimal(linea.substring(237, 247))) // Telefono movil
			// del Contacto
			{ 
			this.listaErrores.put(i + "-19",
						"Tel&eacute;fono Movil del contacto inv&aacute;lido:"
								+ linea.substring(237, 247));
				errorLinea = true;
			}else if(linea.substring(237, 247).trim().length()<10){
				this.listaErrores.put(i + "-19", "Tel&eacute;fono Movil del contacto inv&aacute;lido:"
						+ linea.substring(237, 247)+ TXT_CEROS);
				errorLinea = true;
			}
		
		if (!valida.isDecimal(linea.substring(247, 257))) // Fax
			// del Contacto
			{ 
			this.listaErrores.put(i + "-20",
						"Fax del contacto inv&aacute;lido:"
								+ linea.substring(247, 257));
				errorLinea = true;
			}else if(linea.substring(247, 257).trim().length()<10){
				this.listaErrores.put(i + "-20", "Fax del contacto inv&aacute;lido:"
						+ linea.substring(247, 257)+ TXT_CEROS);
				errorLinea = true;
			}

		if (!valida.validaCadena(
				linea.substring(257, 277).trim(),
				"^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			// Correo electronico principal
			this.listaErrores.put(i + "-21",
					"Correo electr&oacute;nico principal invalido:"
							+ linea.substring(257, 277));
			errorLinea = true;
		}

		if (!valida.validaCadena(
				linea.substring(277, 297).trim(),
				"^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			// Correo electronico alterno
			this.listaErrores.put(i + "-22",
					"Correo electr&oacute;nico Alterno invalido:"
							+ linea.substring(277, 297));
			errorLinea = true;
		}
	}
}
