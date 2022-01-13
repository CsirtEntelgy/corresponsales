package com.isban.corresponsalia.comunes;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class TratamientoTrama.
 */
public class TratamientoTrama {

	/** The error resp. */
	private static String ERROR_RESP  = "@ER"; 
	
	/** The aviso resp. */
	private static String AVISO_RESP  = "@AV"; 
	
	/** The delim resp. */
	private static String DELIM_RESP  = "@DC"; 
	
	/** The termi resp. */
	private static String TERMI_RESP  = "Ó"; 

	/**
	 * Es respuesta correcta.
	 *
	 * @param lstrTrama the lstr trama
	 * @return true, if successful
	 */
	public static boolean esRespuestaCorrecta(String lstrTrama){
		boolean isOK = true;
		
		if(lstrTrama.indexOf(ERROR_RESP)>-1)
			isOK = false;
		
		return isOK;
	}
	
	/**
	 * Gets the mensaje error.
	 *
	 * @param lstrTrama the lstr trama
	 * @return the mensaje error
	 */
	public static String getMensajeError(String lstrTrama){
		String lstrMensaje = "";
		if(lstrTrama==null || lstrTrama.equals("")){
			lstrMensaje = "No es posible obtener el mensaje de error";
		}
		else{
			int posicionError = lstrTrama.indexOf(ERROR_RESP);
			if(posicionError > -1){
				lstrMensaje = lstrTrama.substring(posicionError + 10,  lstrTrama.length()-2);
			}
		}
		
		return lstrMensaje;
	}
	
	/**
	 * Gets the codigo error.
	 *
	 * @param lstrTrama the lstr trama
	 * @return the codigo error
	 */
	public static String getCodigoError(String lstrTrama){
		String lstrCodigo = "";
		if(lstrTrama==null || lstrTrama.equals("")){
			lstrCodigo = "No es posible obtener el codigo de error";
		}
		else{
			int posicionError = lstrTrama.indexOf(ERROR_RESP);
			if(posicionError > -1){
				lstrCodigo = lstrTrama.substring(posicionError + 3, posicionError + 10);
			}
		}
		
		return lstrCodigo;
	}
	
	/**
	 * Gets the mensaje aviso.
	 *
	 * @param lstrTrama the lstr trama
	 * @return the mensaje aviso
	 */
	public static String getMensajeAviso(String lstrTrama){
		String lstrMensaje = "";
		if(lstrTrama==null || lstrTrama.equals("")){
			lstrMensaje = "No es posible obtener el mensaje de aviso";
		}
		else{
			int posicionAviso = lstrTrama.indexOf(AVISO_RESP);
			if(posicionAviso > -1){
				lstrMensaje = lstrTrama.substring(posicionAviso + 10, lstrTrama.indexOf(TERMI_RESP));
			}
		}
		
		return lstrMensaje;
	}
	
	/**
	 * Gets the codigo aviso.
	 *
	 * @param lstrTrama the lstr trama
	 * @return the codigo aviso
	 */
	public static String getCodigoAviso(String lstrTrama){
		String lstrCodigo = "";
		if(lstrTrama==null || lstrTrama.equals("")){
			lstrCodigo = "No es posible obtener el codigo de aviso";
		}
		else{
			int posicionAviso = lstrTrama.indexOf(AVISO_RESP);
			if(posicionAviso > -1){
				lstrCodigo = lstrTrama.substring(posicionAviso + 3, posicionAviso + 10);
			}
		}
		
		return lstrCodigo;
	}

	/**
	 * Gets the formato respuesta.
	 *
	 * @param lstrTrama the lstr trama
	 * @return the formato respuesta
	 */
	public static String getFormatoRespuesta(String lstrTrama){
		String lstrFormato = "";
		if(lstrTrama==null || lstrTrama.equals("")){
			lstrFormato = "No es posible obtener el formato";
		}
		else{
			int posicionFormato = lstrTrama.indexOf(DELIM_RESP);
			if(posicionFormato > -1){
				lstrFormato = lstrTrama.substring(posicionFormato + 3, posicionFormato + 10);
			}
		}
		
		return lstrFormato;
	}

	/**
	 * Gets the trama tratamiento.
	 *
	 * @param lstrTrama the lstr trama
	 * @return the trama tratamiento
	 */
	public static String getTramaTratamiento(String lstrTrama){
		String lstrTramaTratamiento = "";
		if(lstrTrama==null || lstrTrama.equals("")){
			lstrTramaTratamiento = "No es posible obtener una trama para tratar";
		}
		else{
			int posicionIniTrama = lstrTrama.indexOf(DELIM_RESP);
			
			if(posicionIniTrama > -1){
				lstrTramaTratamiento = lstrTrama.substring(posicionIniTrama);
			}
		}
		
		return lstrTramaTratamiento;
	}
	
	/**
	 * Parte trama.
	 *
	 * @param lstrTrama the lstr trama
	 * @param delimitador the delimitador
	 * @return the list
	 */
	public static List<String> parteTrama(String lstrTrama, String delimitador){
		
		ArrayList<String> listaRegistros = new ArrayList<String>();
		String arrstrRegistros[] = lstrTrama.split(delimitador);
		System.out.println("Numero de Registros:" + arrstrRegistros.length);
		for(int i=0;i<arrstrRegistros.length;i++){
			
			String lstrReg = arrstrRegistros[i];
			if(!lstrReg.equals(""))
				listaRegistros.add(lstrReg);
			
		}
		/*
		StringTokenizer sttRegistros = new StringTokenizer(lstrTrama,delimitador);
		while(sttRegistros.hasMoreElements()){
			String lstrReg =  sttRegistros.nextToken();
			registros.add(lstrReg);
		}
		*/
		
		return listaRegistros;
	}
	
	/**
	 * Gets the registro tratamiento.
	 *
	 * @param lstrTramaRegistro the lstr trama registro
	 * @return the registro tratamiento
	 */
	public static String getRegistroTratamiento(String lstrTramaRegistro){
		String lstrTramaTratamiento = "";
		if(lstrTramaRegistro==null || lstrTramaRegistro.equals("")){
			lstrTramaTratamiento = "No es posible realizar tratamiento a trama registro";
		}
		else{
			int posicionIniTrama = lstrTramaRegistro.indexOf(DELIM_RESP);
			
			if(posicionIniTrama > -1){
				String ltsrTemp = lstrTramaRegistro.substring(posicionIniTrama + 12);
				lstrTramaTratamiento = ltsrTemp.substring(1,ltsrTemp.indexOf(TERMI_RESP)-1);
			}
		}
		
		return lstrTramaTratamiento;
	}
	
	/**
	 * Parte trama.
	 *
	 * @param lstrTrama the lstr trama
	 * @param longitudTrama the longitud trama
	 * @return the list
	 */
	public static List<String> parteTrama(String lstrTrama, int longitudTrama){
		ArrayList<String> registros = new ArrayList<String>();
		if((lstrTrama.length()%longitudTrama)==0){
			int iReg = lstrTrama.length() / longitudTrama;
			for(int i=0;i<iReg;i++){
				int iPos=i*longitudTrama;
				String lstRegTemp = lstrTrama.substring(iPos, iPos+longitudTrama);
				registros.add(lstRegTemp);
			}
		}
		return registros;
	}
	
	/**
	 * Trama tiene formato paginacion.
	 *
	 * @param pstrTrama the pstr trama
	 * @param pstrFormato the pstr formato
	 * @return true, if successful
	 */
	public static boolean tramaTieneFormatoPaginacion(String pstrTrama, String pstrFormato){
		boolean esPaginada = false;
		if(pstrTrama.indexOf(pstrFormato)>-1)
			esPaginada = true;
		return esPaginada;
	}

	/**
	 * Obtener trama paginacion.
	 *
	 * @param pstrTrama the pstr trama
	 * @param pstrFormato the pstr formato
	 * @return the string
	 */
	public static String obtenerTramaPaginacion(String pstrTrama, String pstrFormato){
		String lstrTramaPag = "";
		
		int posicionIniTrama = pstrTrama.indexOf(pstrFormato);
		
		if(posicionIniTrama > -1){
			String ltsrTemp = pstrTrama.substring(posicionIniTrama + 12);
			lstrTramaPag = ltsrTemp.substring(0,ltsrTemp.indexOf(TERMI_RESP));
		}
		
		return lstrTramaPag;
	}

	/**
	 * Eliminar trama paginacion.
	 *
	 * @param pstrTrama the pstr trama
	 * @param pstrFormato the pstr formato
	 * @return the string
	 */
	public static String eliminarTramaPaginacion(String pstrTrama, String pstrFormato){
		
		String lstrTramaPag = "";
		
		int posicionIniTrama = pstrTrama.indexOf(pstrFormato);
		
		if(posicionIniTrama > -1)
			lstrTramaPag = pstrTrama.substring(0, posicionIniTrama);
		
		return lstrTramaPag;
	}
}
