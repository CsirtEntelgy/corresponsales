package com.isban.corresponsalia.comunes;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


// TODO: Auto-generated Javadoc
/**
 * Clase Utils con recopilacion de metodos comunes dentro de los diferentes
 * aplicativos.
 */
public class Utils{

	/**
	 * Metodo para eliminar el punto decimal de una cifra o cadena.
	 *
	 * @param cifra the cifra
	 * @return String con el valor de la cifra pero sin puntos dentro de la
	 * misma.
	 */
	public static String quitaPunto(String cifra) {
		int idx = 0;
		while (idx >= 0) {
			idx = cifra.indexOf(".");
			if (idx >= 0) {
				cifra = cifra.substring(0, idx)
						+ cifra.substring(idx + 1, cifra.length());
			}
		}
		return cifra;
	}

	/**
	 * Metodo para convertir el valor de una cadena nula a cadena vacia.
	 *
	 * @param cadena the cadena
	 * @return String equivalente a cadena vacia.
	 */
	public static String nullXEspacios(String cadena) {
		if (cadena == null) {
			return "";
		}
		return cadena;

	}

	/**
	 * Metodo para recortar desde el inicio de una cadena hasta el caracter
	 * limite especificado.
	 *
	 * @param cadena the cadena
	 * @param caracterLimite the caracter limite
	 * @return String con la cadena recortada.
	 */
	public static String recortaCadena(String cadena, char caracterLimite) {
		final int limite = cadena.indexOf(caracterLimite);

		if (limite > -1) {
			return cadena.substring(0, limite);
		} else {
			return cadena;
		}
	}

	/**
	 * Metodo para validar si la cadena introducida es una direccion de correo
	 * electronico (email).
	 *
	 * @param email the email
	 * @return Boolean - true si la direccion es valida.
	 */
	public static boolean isValidEmail(String email) {
		final String regex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[_A-Za-z0-9-]+)";
		return email.matches(regex);

	}

	/**
	 * Metodo para rellenar a la derecha una cadena con un caracter definido, de
	 * acuerdo al limite especificado.
	 *
	 * @param s the s
	 * @param c the c
	 * @param n the n
	 * @return the string
	 */
	public static String rpad(String s, String c, int n) {
		if (s == null)
			s = "";
		while (s.length() < n) {
			s = s + c;
		}
		return s;
	}

	/**
	 * Metodo para rellenar a la izquierda una cadena con un caracter definido,
	 * de acuerdo al limite especificado.
	 *
	 * @param s the s
	 * @param c the c
	 * @param n the n
	 * @return the string
	 */
	public static String lpad(String s, String c, int n) {
		if (s == null)
			s = "";
		while (s.length() < n) {
			s = c + s;
		}
		return s;
	}

	/**
	 * Metodo para verificar si una cadena es alfanumerica, que este formada por
	 * unicamente letras, numeros y/o espacios.
	 *
	 * @param s the s
	 * @return Boolean - true si la cadena definida es alfanumerica.
	 */
	public static boolean isAlfaNumeric(String s) {
		int i;
		char ch;
		int result = 0;
		for (i = 0; i < s.length(); i++) {
			ch = s.charAt(i);
			if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z')
					|| (ch >= 'A' && ch <= 'Z') || (ch == ' ')) {
				result = 1;
			} else {
				result = 0;
				break;
			}
		}
		if (result == 1)
			return true;
		else
			return false;
	}

	/**
	 * Metodo para verificar si una cadena es numerica con o sin punto decimal.
	 *
	 * @param s the s
	 * @return Boolean - true si la cadena definida es numerica con o sin punto
	 * deciaml.
	 */
	public static boolean isDouble(String s) {
		int i;
		char ch;
		int result = 0;
		int ispoint = 0;
		for (i = 0; i < s.length(); i++) {
			ch = s.charAt(i);
			if (ch >= '0' && ch <= '9' || ch == '.') {
				if (ch == '.')
					ispoint++;
				result = 1;
			} else {
				result = 0;
				break;
			}
		}
		if (ispoint > 1)
			result = 0;
		if (result == 1)
			return true;
		else
			return false;
	}

	/**
	 * Metodo para convertir una cadena con valor numericos al formato
	 * ###,###,###.##
	 *
	 * @param cantidad the cantidad
	 * @return String con la cadena numerica convertida al formato
	 * ###,###,###.##
	 */
	public static String formatoMoneda(String cantidad) {
		final String language = "la"; // ar
		final String country = "MX"; // AF
		final Locale local = new Locale(language, country);
		final double importeTemp = new Double(cantidad).doubleValue();
		final NumberFormat nf = NumberFormat.getCurrencyInstance(local);
		String formato = "";

		if (cantidad == null || cantidad.equals("")) {
			cantidad = "0.0";
		}
		if (importeTemp < 0) {
			try {
				formato = nf.format(new Double(cantidad).doubleValue());
				formato = " -" + formato.substring(2, formato.length());
			} catch (NumberFormatException e) {
				formato = "0.00";
			}
		} else {
			try {
				formato = nf.format(new Double(cantidad).doubleValue());
				formato = " " + formato.substring(1, formato.length());
			} catch (NumberFormatException e) {
				formato = "0.00";
			}
		}
		if (formato == null) {
			formato = "";
		}
		return formato;
	}

	/**
	 * Metodo para convertir un valor double al formato ###,###,###.##
	 *
	 * @param cantidad the cantidad
	 * @return String con la cadena numerica convertida al formato
	 * ###,###,###.##
	 */
	public static String formatoMoneda(double cantidad) {
		String formato = "";
		final String language = "la"; // la
		final String country = "MX"; // MX
		final Locale local = new Locale(language, country);
		final NumberFormat nf = NumberFormat.getCurrencyInstance(local);

		if (cantidad < 0) {
			formato = nf.format(cantidad);
			try {
				formato = " -" + formato.substring(2, formato.length());
			} catch (NumberFormatException e) {
			}
		} else {
			formato = nf.format(cantidad);
			try {
				formato = " " + formato.substring(1, formato.length());
			} catch (NumberFormatException e) {
			}
		}
		return formato;
	}

	/**
	 * Método para eliminar el signo "$" y "," de un string.
	 *
	 * @param cifra the cifra
	 * @return String con la cifra sin el signo de pesos y comas.
	 */
	public static String quitaPesoComa(String cifra) {
		int idx = 0;
		if (cifra.startsWith("$")) {
			// Elimina Signo de pesos
			cifra = cifra.substring(1, cifra.length());
		}
		while (idx >= 0) {
			idx = cifra.indexOf(",");
			if (idx >= 0) {
				cifra = cifra.substring(0, idx)
						+ cifra.substring(idx + 1, cifra.length());
			}
		}
		return cifra;
	}

	/**
	 * Metodo para devolver el indice de un elemento dentro de un array.
	 *
	 * @param combo the combo
	 * @param elemento the elemento
	 * @return int con el valor del indice del elemento buscado.
	 */
	public static int obtieneIndice(String[] combo, String elemento) {
		int idx = -1;
		int k;
		for (k = 0; k < combo.length; k++) {
			if (combo[k].equals(elemento)) {
				idx = k;
				break;
			}
		}
		return idx;
	}

	/**
	 * Metodo para obtener el timestamp con formato de 17 digitos.
	 * 
	 * @return String con el timestamp con formato de 17 digitos.
	 */
	public static String getTimestamp() {
		String timestamp = null;
		// Crea la instancia del calendario.
		final Calendar calendar = Calendar.getInstance();
		// Obtiene la fecha actual a partir de la instancia del calendario.
		final Date now = calendar.getTime();
		final Timestamp currentTimestamp = new Timestamp(now.getTime());

		timestamp = Utils.rpad(currentTimestamp.toString().replaceAll(
				"[\\s-:\\.]", ""), "0", 17);
		return timestamp;
	}

	/**
	 * Metodo para obtener una fecha con formato dd/mm/aaaa del campo Timestamp
	 * de 390.
	 *
	 * @param Timest the timest
	 * @return String con la fecha con formato dd/mm/aaaa.
	 */
	public static String timest2Fecha(String Timest) {
		String Fecha = "";
		if (Timest.trim().length() == 26) {
			Fecha = Timest.substring(8, 10) + "/" + Timest.substring(5, 7)
					+ "/" + Timest.substring(0, 4);
		}
		return Fecha;
	}

	/**
	 * Metodo para obtener la hora con formato "hh:mm:ss" del campo Timestamp de
	 * 390.
	 *
	 * @param Timest the timest
	 * @return String con la hora con formato "hh:mm:ss"
	 */
	public static String timest2Hora(String Timest) {
		String Hora = "";
		if (Timest.trim().length() == 26) {
			Hora = Timest.substring(11, 19);
			Hora = replace(Hora, ".", ":");
		}
		return Hora;
	}

	/**
	 * Metodo para reemplazar caracter dentro de una cadena por otra cadena
	 * distinta.
	 *
	 * @param s the s
	 * @param c1 the c1
	 * @param c2 the c2
	 * @return String con la nueva cadena resultante despues del reemplazo.
	 */
	public static String replace(String s, String c1, String c2) {
		int n;
		n = s.indexOf(c1);
		while (n > 0) {
			s = s.substring(0, n) + c2 + s.substring(n + 1, s.length());
			n = s.indexOf(c1);
		}
		return s;
	}

	/**
	 * Formatea dobles.
	 *
	 * @param valor the valor
	 * @return the string
	 */
	public static String formateaDobles(String valor) {
		StringBuffer valorRetorno = null;
		if (valor != null) {
			valorRetorno = new StringBuffer();
			java.util.StringTokenizer valores = new java.util.StringTokenizer(
					valor, "-");
			String signoStr = "";
			String valorStr = "";
			if (valores.countTokens() > 0 && valor.indexOf("-") > -1) {
				signoStr = "-";
				valorStr = valores.nextToken();
				valorRetorno.append(signoStr + valorStr.trim());
			} else {
				valorRetorno.append(valor);
			}
		}
		return valorRetorno.toString();
	}

	/**
	 * Metodo para convertir una cadena con un valor numerico a formato de 390.
	 *
	 * @param strNumero the str numero
	 * @param numDecimales the num decimales
	 * @return String con el valor numerico en formato de 390.
	 */
	public static String numeroFormato390(String strNumero, int numDecimales) {
		String sNumero390 = "";
		double dNumero390 = 0;
		long iNumero = 0;
		double Decimales = 1.0;
		int exponente;

		// quitar la coma jfg
		String numero = quitaPesoComa(strNumero);
		dNumero390 = Double.parseDouble(numero);

		if (numDecimales > 0) {
			for (exponente = 1; exponente <= numDecimales; exponente++)
				Decimales *= 10.0;
		}
		dNumero390 = dNumero390 * Decimales;
		iNumero = (long) dNumero390;
		sNumero390 = "" + iNumero;
		return sNumero390;
	}

	/**
	 * Metodo para verificar si existe la ruta de directorios especificada
	 * dentro del equipo.
	 *
	 * @param strRuta the str ruta
	 * @return boolean - true en caso de que si exista la ruta especificada.
	 */
	public static boolean validarRuta(String strRuta) {
		// Verificación de Rutas
		final File dir = new File(strRuta);
		final String[] Archivos = dir.list();
		return !(Archivos == null);
	}

	/**
	 * Metodo para verificar si existe el archivo especificado dentro de la ruta
	 * de directorios especificada dentro del equipo.
	 *
	 * @param strRuta the str ruta
	 * @param strArch the str arch
	 * @return boolean - true en caso de que si exista el archivo buscado.
	 */
	public static boolean validaArchivo(String strRuta, String strArch) {
		// Verificación de la existencia de archivos
		final File archivo = new File(strRuta, strArch);
		return (archivo.exists() && archivo.isFile());
	}

	/**
	 * Metodo para borrar un archivo contenido en una ruta especificada.
	 *
	 * @param strRuta the str ruta
	 * @param strArch the str arch
	 * @return boolean - true en caso de que se elimine correctamente el archivo
	 * especificado.
	 */
	public static boolean borraArchivo(String strRuta, String strArch) {
		// Verificación de la existencia de archivos
		File archivo = new File(strRuta, strArch);
		return archivo.delete();
	}

	/**
	 * Metodo para cambiar el formato de la fecha especificada al formato:.
	 *
	 * @param strFchEntrada the str fch entrada
	 * @param strFormatoEntrada the str formato entrada
	 * @param strFormatoSalida the str formato salida
	 * @return String con la fecha reformateada.
	 */
	public static String strReformatoFecha(String strFchEntrada,
			String strFormatoEntrada, String strFormatoSalida) {
		String strFchSalida = "";
		final String[] mayusMeses = { "Ene", "Feb", "Mar", "Abr", "May", "Jun",
				"Jul", "Ago", "Sep", "Oct", "Nov", "Dic" };
		if (!strFchEntrada.equals("")) {
			try {
				DateFormatSymbols simbMex = new DateFormatSymbols(new Locale(
						"es", "MX"));
				simbMex.setShortMonths(mayusMeses);

				final DateFormat frmFechaEntrada = new SimpleDateFormat(
						strFormatoEntrada);
				final DateFormat frmFechaSalida = new SimpleDateFormat(
						strFormatoSalida, simbMex);

				Date fchEntrada = null;

				fchEntrada = frmFechaEntrada.parse(strFchEntrada);
				strFchSalida = frmFechaSalida.format(fchEntrada);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return strFchSalida;
	}

	/**
	 * Metodo para convertir un String con formato numerico "#####" al formato
	 * "#,##0.0#" (Ej. 123456789 a 1,234,567.89)
	 *
	 * @param strImporteRep the str importe rep
	 * @param numDecimales the num decimales
	 * @return String con la cadena numerica formateada.
	 */
	public static String strNumeroFormatoRep(String strImporteRep,
			int numDecimales) {
		String numeroRep = "";
		double divDecimales = 1.0;
		int exponente;

		if (!strImporteRep.trim().equals("")) {
			if (numDecimales >= 0) {
				if (numDecimales > 0) {
					for (exponente = 1; exponente <= numDecimales; exponente++)
						divDecimales *= 10.0;
				}
				NumberFormat importFormat = new DecimalFormat("#,##0.#");
				importFormat.setMaximumFractionDigits(numDecimales);
				importFormat.setMinimumFractionDigits(numDecimales);
				numeroRep = importFormat.format(
						(new Double(strImporteRep.trim())).doubleValue()
								/ divDecimales).toString();
			}
		}
		return numeroRep;
	}

	/**
	 * Metodo para convertir un String con formato numerico "#####" al formato
	 * "###0.0#" (Ej. 123456789 a 1234567.89)
	 *
	 * @param strCadenaSinDecimal the str cadena sin decimal
	 * @param numDecimales the num decimales
	 * @return String con la cadena numerica formateada.
	 */
	public static String strNumeroFormatoDecimal(String strCadenaSinDecimal,
			int numDecimales) {
		String numeroDecimal = "";
		double divDecimales = 1.0;
		int exponente;

		if (UtilsCadenas.esNumerico(strCadenaSinDecimal.trim().toString())
				&& !strCadenaSinDecimal.trim().equals("")) {
			if (numDecimales >= 0) {
				if (numDecimales > 0) {
					for (exponente = 1; exponente <= numDecimales; exponente++) {
						divDecimales *= 10.0;
					}
				}
				NumberFormat importFormat = new DecimalFormat("#0.#");
				importFormat.setMaximumFractionDigits(numDecimales);
				importFormat.setMinimumFractionDigits(numDecimales);
				numeroDecimal = importFormat.format(
						(new Double(strCadenaSinDecimal.trim())).doubleValue()
								/ divDecimales).toString();
			}
		}
		return numeroDecimal;
	}

	/**
	 * Metodo para convertir un String con formato numerico "#####" al formato
	 * "#,##0.0#" (Ej. 123456789 a 123,456,789.00)
	 *
	 * @param strImporteRep the str importe rep
	 * @param numDecimales the num decimales
	 * @return String con la cadena numerica formateada.
	 */
	public static String strNumeroFormatoCat(String strImporteRep,
			int numDecimales) {
		String numeroRep = "";
		double divDecimales = 1.0;
		int exponente;
		if (!strImporteRep.equals("")) {
			if (numDecimales >= 0) {
				if (numDecimales > 0) {
					for (exponente = 1; exponente <= numDecimales; exponente++) {
						divDecimales *= 1.0;
					}
				}
				NumberFormat importFormat = new DecimalFormat("#,##0.00");
				importFormat.setMaximumFractionDigits(numDecimales);
				importFormat.setMinimumFractionDigits(numDecimales);
				numeroRep = importFormat.format((new Double(strImporteRep))
						.doubleValue());
			}
		}
		return numeroRep;
	}

	/**
	 * Metodo para obtener la fecha del sistema (formato aaaa-mm-dd).
	 * 
	 * @return String con la fecha del sistema.
	 */
	public static String getFechaSys() {
		final GregorianCalendar hoy = new GregorianCalendar();
		String fecha;
		try {
			fecha = Integer.toString(hoy.get(GregorianCalendar.YEAR))
					+ "-"
					+ Utils.lpad(Integer.toString(hoy
							.get(GregorianCalendar.MONTH) + 1), "0", 2)
					+ "-"
					+ Utils.lpad(Integer.toString(hoy
							.get(GregorianCalendar.DAY_OF_MONTH)), "0", 2);
		} catch (Exception e) {
			e.printStackTrace();
			fecha = "";
		}
		return fecha;
	}

	/**
	 * Metodo para obtener la hora del sistema (formato hh:mm:ss).
	 * 
	 * @return String con la hora del sistema.
	 */
	public static String getHora() {
		final GregorianCalendar hoy;
		String hora;
		try {
			hoy = new GregorianCalendar();
			hora = Utils.lpad(Integer.toString(hoy
					.get(GregorianCalendar.HOUR_OF_DAY)), "0", 2)
					+ ":"
					+ Utils.lpad(Integer.toString(hoy
							.get(GregorianCalendar.MINUTE)), "0", 2)
					+ ":"
					+ Utils.lpad(Integer.toString(hoy
							.get(GregorianCalendar.SECOND)), "0", 2);
		} catch (Exception e) {
			e.printStackTrace();
			hora = "";
		}
		return hora;
	}

	/**
	 * Método para formatear una cadena numerica eliminando los ceros de la
	 * izquierda.
	 *
	 * @param strEntrada the str entrada
	 * @return String con la cadena formateada (sin ceros a la izquierda).
	 */
	public static String strNumerico(String strEntrada) {
		String strSalida = "";
		if (UtilsCadenas.esNumerico(strEntrada.trim().toString())
				&& !strEntrada.trim().equals("")) {
			NumberFormat numberFormat = new DecimalFormat("##0");
			strSalida = numberFormat.format((new Double(strEntrada.trim())))
					.toString();
		}
		return strSalida;
	}

	/**
	 * Metodo para validar si el rango de fechas introducido es valido (fecha
	 * inicial menor a la fecha final).
	 *
	 * @param fecinicio the fecinicio
	 * @param fecfin the fecfin
	 * @return boolean - true si es un rango de fechas, y si la fecha inicial es
	 * menor a la fecha final.
	 */
	public static boolean isRangoFecha(String fecinicio, String fecfin) {
		boolean result = false;
		String[] fecha = null;
		String fecIni = "";
		String fecFin = "";

		if (fecinicio.length() == 10 && fecfin.length() == 10) {
			// es mayor la fecha final?
			fecha = fecinicio.split("/");
			fecIni = fecha[2] + fecha[1] + fecha[0];
			fecha = fecfin.split("/");
			fecFin = fecha[2] + fecha[1] + fecha[0];
			if (Integer.parseInt(fecIni) <= Integer.parseInt(fecFin)) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * Metodo para convertir el formato de hora hhmm a formato hh:mm:ss (Ej.
	 * 2345 a 23:45:00).
	 *
	 * @param hora the hora
	 * @return String con la hora formateada.
	 */
	public static String getHoraHHMM(String hora) {
		String horaHHDD = "";
		String tmp = "";
		try {
			// sólo para 4 posiciones
			tmp = hora.trim();
			if (tmp.length() == 4) {
				horaHHDD = tmp.substring(0, 2) + ":" + tmp.substring(2, 4)
						+ ":00";
			} else {
				horaHHDD = tmp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return horaHHDD;
	}

	/**
	 * Metodo para convertir el formato de hora hhmmss a formato hh:mm:ss (Ej.
	 * 023450 a 02:34:50).
	 *
	 * @param hora the hora
	 * @return String con la hora formateada.
	 */
	public static String getHoraHHMMSS(String hora) {
		String horaFormateada = "";
		String horaTemp = null;
		try {
			// sólo para 4 posiciones
			horaTemp = hora.trim();
			if (horaTemp.length() == 4) {
				horaFormateada = horaTemp.substring(0, 2) + ":"
						+ horaTemp.substring(2, 4) + ":00";
			} else if (horaTemp.length() == 6) {
				horaFormateada = horaTemp.substring(0, 2) + ":"
						+ horaTemp.substring(2, 4) + ":"
						+ horaTemp.substring(4, 6);
			} else {
				horaTemp = Utils.lpad(hora, "0", 6);
				horaFormateada = horaTemp.substring(0, 2) + ":"
						+ horaTemp.substring(2, 4) + ":"
						+ horaTemp.substring(4, 6);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return horaFormateada;
	}

	/**
	 * Metodo para armar la cabecera ps7 para consumir una transaccion de 390.
	 *
	 * @param canal the canal
	 * @param usuario390 the usuario390
	 * @param ser the ser
	 * @param esp the esp
	 * @param lon the lon
	 * @param tec the tec
	 * @return String con la trama de envio para 390.
	 */
	public static String armaCabeceraPS7(String canal, String usuario390,
			String ser, String esp, int lon, String tec) {
		final StringBuffer cabeza = new StringBuffer();
		if (canal.equals("MQ")) {
			// 8 Usuario CICS "CSOPNET " "B244661 ".
			cabeza.append("    " + usuario390);
			// 4 Codigo transaccion.
			cabeza.append(lpad(ser, " ", 4));
			// 4 Longitud del mensaje de entrada (cabecera + contenido),
			cabeza.append(lpad("" + (lon + 32), "0", 4));
			// 1 1 = Altamira gestina el commit, 0 = Altamira no gestiona el
			// commit.
			cabeza.append("1");
			// 5 Numero de secuencia.
			cabeza.append("12345");
			// 1 1 = incorporar datos 2 = autorizacion 4 = reanudar conversacion
			// 5 = continua conversacion 6 = autorizacion de transaccion en
			// conversacion.
			cabeza.append("1");
			// 1 O = on line F = off line.
			cabeza.append("O");
			// 2 00=enter 01..12=Fnn 13..=ShiftF1..F24 99=Clear.
			cabeza.append(lpad(tec, "0", 2));
			// 1 N = no impresora S = sí impresora.
			cabeza.append("N");
			// 1 2 = formato @DC 1 = mapas @PA y@LI.
			cabeza.append("2");
			return cabeza.toString();
		} else {
			// 4 Codigo espejo
			cabeza.append(lpad(esp, " ", 4));
			// 4 Terminal logico "PUXQ" "PP06".
			cabeza.append("PUTT");
			// 8 Usuario CICS "CSOPNET " "B244661 ".
			cabeza.append("CSOPNET ");
			// 4 Codigo transaccion.
			cabeza.append(lpad(ser, " ", 4));
			// 4 Longitud del mensaje de entrada (cabecera + contenido).
			cabeza.append(lpad("" + (lon + 32), "0", 4));
			// 1 1 = Altamira gestina el commit, 0 = Altamira no gestiona el
			// commit.
			cabeza.append("1");
			// 5 Numero de secuencia.
			cabeza.append("12345");
			// 1 1 = incorporar datos 2 = autorizacion 4 = reanudar conversacion
			// 5 = continua conversacion 6 = autorizacion de transaccion en
			// conversacion.
			cabeza.append("1");
			// 1 O = on line F = off line.
			cabeza.append("O");
			// 2 00=enter 01..12=Fnn 13..=ShiftF1..F24 99=Clear.
			cabeza.append(lpad(tec, "0", 2));
			// 1 N = no impresora S = sí impresora.
			cabeza.append("N");
			// 1 2 = formato @DC 1 = mapas @PA y@LI
			cabeza.append("2");
			return cabeza.toString();
		}
	}
	@SuppressWarnings("unchecked")
	public String getParameterMultipart(String parameterName, HttpServletRequest httpServletRequest) throws FileUploadException{
		//FileItem item = null;
		final DiskFileItemFactory factory = new DiskFileItemFactory();
			final ServletFileUpload upload = new ServletFileUpload(factory);
			final List<FileItem> items = upload.parseRequest(httpServletRequest);
			FileItem item = null;
			for(int i=0;i<items.size();i++ ){
				item  = items.get(i);
				if (item.isFormField()&&item.getFieldName().equals(parameterName)) {
					return item.getString();
				}
				
			}
			/*final Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
				item = iter.next();
				if (item.isFormField()&&item.getFieldName().equals(parameterName)) {
					return item.getString();
				}
			}*/
		return null;
	}
}
