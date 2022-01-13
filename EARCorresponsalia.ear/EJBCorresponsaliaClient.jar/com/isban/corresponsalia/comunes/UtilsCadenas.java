package com.isban.corresponsalia.comunes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.isban.corresponsalia.beans.comunes.BeanError;

// TODO: Auto-generated Javadoc
/**
 * Clase UtilsCadenas con metodos comunes para el manejo de cadenas de texto (valores de entrada).
 */
public class UtilsCadenas {

	/**
	 * Valida que la cadena contenga solo digitos.
	 *
	 * @param cadena the cadena
	 * @return true si la cadena contiene solo digitos.
	 */
	public static boolean esNumerico(String cadena) {
		Pattern patron = Pattern.compile("^\\d*$");
        Matcher match = patron.matcher(cadena);
		if (match.find()) {
			return true;
		} else {
			return false;
		}		
	}

	/**
	 * Valida que la cadena contenga un numero entero o un numero con decimales.
	 *
	 * @param cadena the cadena
	 * @param decimales the decimales
	 * @return true si la cadena es numero entero o un numero con decimales.
	 */
	public static boolean esNumDecimal(String cadena, int decimales) {
		Pattern patron = Pattern.compile("^\\d*\\.?\\d{1," + decimales + "}$");
        Matcher match = patron.matcher(cadena);
		if (match.find()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Verifica que la cadena no se encuentre vacia y que no contenga solo espacios
	 * en blanco.
	 *
	 * @param cadena the cadena
	 * @return true si la cadena no se encuentra vacia y no contiene espacios en blanco.
	 */
	public static boolean contieneDatos(String cadena) {
		Pattern patron = Pattern.compile("^\\s*$");
        Matcher match = patron.matcher(cadena);
		if (match.find()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Verifica que la cadena sea alfanumerica (que solo contenga letras y numeros).
	 *
	 * @param cadena the cadena
	 * @return true si la cadena es alfanumerica.
	 */
	public static boolean esAlfanumerico(String cadena) {
		Pattern patron = Pattern.compile("[^A-Za-z0-9]+");
        Matcher match = patron.matcher(cadena);
		if (match.find()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Invierte el formato de la fecha proporcionada.
	 *
	 * @param fecha the fecha
	 * @param separador the separador
	 * @return String con la fecha invertida.
	 */
	public static String invierteFecha(String fecha, String separador) {
		String[] fragmentosFecha = fecha.split(separador);
		ArrayUtils.reverse(fragmentosFecha);
		return StringUtils.join(fragmentosFecha, separador);
	} 

	/**
	 * Recorta una cadena al numero de caracteres indicados.
	 *
	 * @param cadena the cadena
	 * @param limite the limite
	 * @return String con la cadena recortada al limite especificado.
	 */
	public static String limitaCadena(String cadena, int limite) {
		if (cadena.length() > limite) {
			return cadena.substring(0, limite);
		} else {
			return cadena;
		}
	}
	
	/**
	 * Desentrama la respuesta cuando ha sucedido un Error.
	 *
	 * @param cadena the cadena
	 * @return the bean error
	 */
	public static BeanError desentramaError(String cadena) {
		BeanError beanError = new BeanError();
		if(cadena!=null && !cadena.equals("")){
			int posicionError = cadena.indexOf("@ER");

			if(posicionError > -1){
				beanError.setCodigoError(cadena.substring(posicionError + 3, posicionError + 10));
				beanError.setMsgError(cadena.substring(posicionError + 10,  cadena.length()-2));
			}
		}
		return beanError;
	}
	
}
