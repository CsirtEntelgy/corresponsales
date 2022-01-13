package com.isban.corresponsalia.controllers.corresponsales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.isban.corresponsalia.comunes.Parametros;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class ControllerCorresponsalesAltaMasiva.
 */
@Controller
public class ControllerCorresponsalesAltaMasiva extends ArchitechEBE {

	/** The Constant MSG_RESULTADO. */
	private static final String MSG_RESULTADO = "msgResultado";

	/** The Constant ERROR_ARCHIVO. */
	private static final String ERROR_ARCHIVO = "errorArchivo";

	/** The Constant AVISO. */
	private static final String AVISO = "aviso";

	/** The Constant ARCHIVO_TRASF. */
	private static final String ARCHIVO_TRASF = "El Archivo no fue transferido, ";

	/** The Constant REVISAR. */
	private static final String REVISAR = " favor de revisar los errores e intentar de nuevo";

	/**
	 * Alta masiva corresponsales.
	 * 
	 * @param req
	 *            the req
	 * @param res
	 *            the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "AltaMasivaCorresponsales.do")
	public ModelAndView altaMasivaCorresponsales(HttpServletRequest req,
			HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean) req.getSession().getAttribute(
				"NewArchitechSession"));
		int registrosCorrectos = 0;
		int registrosTotales = 0;
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();
		TreeMap<String, String> listaErrores = new TreeMap<String, String>();
		String operacion = req.getParameter("operacion");
		mapParametros.put(MSG_RESULTADO, null);
		mapParametros.put(ERROR_ARCHIVO, null);
		if (operacion == null) {
			/*try {
				operacion = (new Utils()).getParameterMultipart("operacion",
						req);
			} catch (FileUploadException e) {*/
				operacion = "inicial";
			//}
			/*if (operacion == null) {
				operacion = "envioArchivo";
			}*/
		}
		
		info("Operacion: " + operacion);
		Object[] salida = null;
		if ("envioArchivo".equals(operacion)) {
			final FileItem archivo = obtenerArchivo(req);
			debug("Tamano archivo: " + archivo.getSize());
			final StringTokenizer contenidoPath = new StringTokenizer(archivo
					.getName(), "/\\");
			String nombreArchivo = null;
			while (contenidoPath.hasMoreTokens()) {
				nombreArchivo = contenidoPath.nextToken();
			}
			if (!"text/plain".equalsIgnoreCase(archivo.getContentType())) {
				listaErrores
						.put("0",
								"Tipo de archivo incorrecto, solo se aceptan archivo de texto plano");
				info(archivo.getName());
			} else if (!"correspo.txt".equals(nombreArchivo)) {
				listaErrores
						.put("0-1",
								"Nombre archivo incorrecto, solo se acepta 'correspo.txt'");
				info(archivo.getName());
			} else {
				salida = desentramaArchivoCorresponsales(archivo);
			}
			if (salida != null) {
				registrosCorrectos = (Integer) (salida[0]);
				registrosTotales = (Integer) (salida[1]);
				listaErrores = (TreeMap<String, String>) salida[2];
				if (listaErrores.isEmpty()) {
					final String rutaDestino = Parametros
							.getParametroAplicacion("RUTA_INTERFACES_REMOTA");
					final String hostDestino = Parametros
							.getParametroAplicacion("HOST_INTERFACES_REMOTA");
					final String usr = Parametros
							.getParametroAplicacion("USR_INTERFACES");
					final String idFile = Parametros
							.getParametroAplicacion("KID_FILE");
					try {
						copiaArchivo(rutaDestino, hostDestino, usr, archivo,
								idFile, "correspo.txt");
						mapParametros.put(MSG_RESULTADO,
								"El archivo ha sido depositado correctamente.");
					} catch (JSchException e) {
						mapParametros.put(ERROR_ARCHIVO, e.getMessage());
					} catch (SftpException e) {
						mapParametros.put(ERROR_ARCHIVO, e.getMessage());
					} catch (IOException e) {
						mapParametros.put(ERROR_ARCHIVO, e.getMessage());
					}
				} else {
					mapParametros.put(AVISO, ARCHIVO_TRASF + REVISAR);
				}
				mapParametros.put("totalRegistros", registrosTotales);
				mapParametros.put("totalExitosos", registrosCorrectos);
			} else {
				mapParametros.put(AVISO, ARCHIVO_TRASF + REVISAR);
			}
		}
		if (listaErrores != null) {
			mapParametros.put("errores", listaErrores);
			mapParametros.put("totalErrores", listaErrores.size());
		}
		debug("Regresando al jsp...");
		return new ModelAndView("AltaMasivaCorresponsales", mapParametros);
	}

	/**
	 * Desentrama archivo corresponsales.
	 * 
	 * @param item
	 *            the item
	 * @return the object[]
	 */
	private Object[] desentramaArchivoCorresponsales(FileItem item) {
		final Object[] salida = new Object[3];
		final TreeMap<String, String> listaErrores = new TreeMap<String, String>();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(item
					.getInputStream()));
		} catch (IOException e) {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ex) {
				}
			}
			return new Object[0];
		}
		String linea = "";
		int registrosCorrectos = 0;
		boolean errorLinea = false;
		int i = 0;
		for (i = 1; linea != null; i++) {
			try {
				linea = reader.readLine();
				if (linea == null) {

					continue;
				}
			} catch (IOException e) {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException ex) {
					}
				}
				return null;
			}
			if (linea.length() != 180) {
				listaErrores.put((Integer.valueOf(i)).toString(),
						"La longitud (" + linea.length()
								+ ")de la l&iacute;nea " + i
								+ " es inv&aacute;lida.");
				continue;
			}

			if (!validaCadena(linea.substring(0, 1),null) /*"A".equalsIgnoreCase(linea.substring(0, 1))
					|| "B".equalsIgnoreCase(linea.substring(0, 1)) || "M"
					.equalsIgnoreCase(linea.substring(0, 1)))*/) { // operacion
				listaErrores.put(i + "-1", "Operaci&oacute;n inv&aacute;lida :"
						+ linea.substring(0, 1));
				errorLinea = true;

			}

			if (!isNumero(linea.substring(1, 5))) { // entidad banco
				listaErrores.put(i + "-2", "Entidad bancaria inv&aacute;lida:"
						+ linea.substring(1, 5));
				errorLinea = true;
			}

			if (!isNumero(linea.substring(5, 7))) // Codigo de Identificacion
			// del canal Corresponsalia
			{
				listaErrores.put(i + "-3",
						"Código de Identificaci&oacute;n del canal Corresponsalia inv&aacute;lida:"
								+ linea.substring(5, 7));
				errorLinea = true;
			}

			if (!( "CS2".equalsIgnoreCase(linea.substring(11, 14))
					|| "CS3".equalsIgnoreCase(linea.substring(11, 14)) || "CS4"
					.equalsIgnoreCase(linea.substring(11, 14)))) { // Identificador
				// de Estatus del Corresponsal
				listaErrores.put(i + "-4",
						"Identificador de Estatus del Corresponsal inv&aacute;lido:"
								+ linea.substring(11, 14));
				errorLinea = true;
			}

			if (!isNumero(linea.substring(14, 17))) { // Secuencia de Domicilio
				listaErrores.put(i + "-5",
						"Secuencia de Domicilio inv&aacute;lida:"
								+ linea.substring(14, 17));
				errorLinea = true;
			}

			if (!validaCadena(linea.substring(17, 57), null)) { // Nombre del
				// corresponsal
				listaErrores.put(i + "-6", "Nombre  inv&aacute;lido:"
						+ linea.substring(17, 57));
				errorLinea = true;
			}

			if (!isNumero(linea.substring(57, 61))) { // Giro de la
				// empresa según
				// PROSA
				listaErrores.put(i + "-7",
						"Giro de la empresa seg&uacute;n PROSA inv&aacute;lido:"
								+ linea.substring(57, 61));
				errorLinea = true;
			}
			if (!validaCadena(linea.substring(61, 62),null)) { // Identificador de Tipo
				// de Conciliacion
				listaErrores.put(i + "-8",
						"Identificador de Tipo de Conciliaci&oacute;n inv&aacute;lido:"
								+ linea.substring(61, 62));
				errorLinea = true;
			}
			if (!isNumero(linea.substring(62, 65).trim())) { // Parametros Dias
				// pendientes de
				// conciliar
				listaErrores.put(i + "-9",
						"Parametro D&iacute;as pendientes de conciliar inv&aacute;lido:"
								+ linea.substring(62, 65));
				errorLinea = true;
			}
			if (!isNumero(linea.substring(65, 68).trim())) { // Parametros Dias
				// pendientes de
				// compensar
				listaErrores.put(i + "-10",
						"Parametro D&iacute;as pendientes de compensar inv&aacute;lido:"
								+ linea.substring(65, 68));
				errorLinea = true;
			}

			if (!validaCadena(linea.substring(68, 69),null)) { // Identificador
				// para validar COMISION
				listaErrores.put(i + "-11",
						"Identificador para validar Comisi&oacute;n inv&aacute;lido:"
								+ linea.substring(68, 69));
				errorLinea = true;
			}

			if (!validaCadena(linea.substring(69, 89).trim(), "[0-9]{20}")) { // Cuenta
				// de
				// cheques
				listaErrores.put(i + "-12",
						"Cuenta de cheques inv&aacute;lida:"
								+ linea.substring(69, 89));
				errorLinea = true;
			}

			if (!validaCadena(linea.substring(89, 92), null)) {// Divisa
				// relacionada a la cuenta de cheques
				listaErrores.put(i + "-13",
						"Divisa cuenta de cheques inv&aacute;lida:"
								+ linea.substring(89, 92));
				errorLinea = true;
			}

			if (!validaCadena(linea.substring(92, 112).trim(), "[0-9]{20}")) { // Cuenta
				// de LINEA de Credito
				listaErrores.put(i + "-14",
						"Cuenta de L&iacute;nea de Cr&eacute;dito inv&aacute;lida:"
								+ linea.substring(92, 112));
				errorLinea = true;
			}

			if (!validaCadena(linea.substring(112, 115), null)) {
				// relacionada a la LINEA de credito
				listaErrores.put(i + "-15",
						"Divisa relacionada a la L&iacute;nea de cr&eacute;dito inv&aacute;lida:"
								+ linea.substring(112, 115));
				errorLinea = true;
			}

			if (!isDecimal(linea.substring(115, 130).trim())) { // Limite de
				// ALERTA
				listaErrores.put(i + "-16",
						"L&iacute;mite de Alerta inv&aacute;lida:"
								+ linea.substring(115, 130));
				errorLinea = true;
			}

			if (!validaCadena(
					linea.substring(130, 150).trim(),
					"^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
				// Correo electronico principal
				listaErrores.put(i + "-17",
						"Correo electr&oacute;nico principal invalido:"
								+ linea.substring(130, 150));
				errorLinea = true;
			}

			if (!validaCadena(
					linea.substring(150, 170).trim(),
					"^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
				// Correo electronico alterno
				listaErrores.put(i + "-18",
						"Correo electr&oacute;nico Alterno invalido:"
								+ linea.substring(150, 170));
				errorLinea = true;
			}

			debug("Linea: " + linea);
			if (!validaCadena(linea.substring(170, 180).trim(), "[0-9]")) {
				// Telefono
				listaErrores.put(i + "-19", "Tel&eacute;fono inv&aacute;lido:"
						+ linea.substring(170, 180));
				errorLinea = true;
			}
			if (!errorLinea) {
				registrosCorrectos++;
			}
			errorLinea = false;
		}
		salida[0] = Integer.valueOf(registrosCorrectos);
		salida[1] = Integer.valueOf(i - 2);
		salida[2] = listaErrores;
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
		return salida;
	}

	/**
	 * Alta masiva sucursales.
	 * 
	 * @param req
	 *            the req
	 * @param res
	 *            the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "AltaMasivaSucursales.do")
	public ModelAndView altaMasivaSucursales(HttpServletRequest req,
			HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean) req.getSession().getAttribute(
				"NewArchitechSession"));
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();
		TreeMap<String, String> listaErrores = new TreeMap<String, String>();
		int registrosCorrectos = 0;
		int registrosTotales = 0;
		String operacion = req.getParameter("operacion");
		mapParametros.put(MSG_RESULTADO, null);
		mapParametros.put(ERROR_ARCHIVO, null);
		if (operacion == null) {
			/*try {
				operacion = (new Utils()).getParameterMultipart("operacion",
						req);
			} catch (FileUploadException e) {
				operacion = "inicial";
			}
			if (operacion == null) {*/
				operacion = "inicial";
			//}
		}
		info("Operacion: " + operacion);
		if ("envioArchivo".equals(operacion)) {
			final FileItem archivo = obtenerArchivo(req);
			debug("Tamano archivo: " + archivo.getSize());
			Object[] salida = null;
			final StringTokenizer contenidoPath = new StringTokenizer(archivo
					.getName(), "/\\");
			String nombreArchivo = null;
			while (contenidoPath.hasMoreTokens()) {
				nombreArchivo = contenidoPath.nextToken();
			}
			if (!"text/plain".equalsIgnoreCase(archivo.getContentType())) {
				listaErrores
						.put("0",
								"Tipo de archivo incorrecto, solo se aceptan archivo de texto plano");
			} else if (!"oxxo_suc.txt".equals(nombreArchivo)) {
				listaErrores
						.put("0-1",
								"Nombre archivo incorrecto, solo se acepta 'oxxo_suc.txt'");
			} else {
				salida = desentramaArchivoSucursales(archivo);
			}
			if (salida != null) {
				registrosCorrectos = (Integer) (salida[0]);
				registrosTotales = (Integer) (salida[1]);
				listaErrores = (TreeMap<String, String>) salida[2];
				if (listaErrores.isEmpty()) {

					final String rutaDestino = Parametros

					.getParametroAplicacion("RUTA_INTERFACES_REMOTA");

					final String hostDestino = Parametros

					.getParametroAplicacion("HOST_INTERFACES_REMOTA");

					final String usr = Parametros

					.getParametroAplicacion("USR_INTERFACES");

					final String idFile = Parametros

					.getParametroAplicacion("KID_FILE");

					try {

						copiaArchivo(rutaDestino, hostDestino, usr, archivo,
								idFile, "oxxo_suc.txt");

						mapParametros.put(MSG_RESULTADO,

						"El archivo ha sido depositado correctamente.");

					} catch (IOException e) {

						mapParametros.put(ERROR_ARCHIVO, e.getMessage());
						mapParametros.put(AVISO, ARCHIVO_TRASF

						+ REVISAR);

					} catch (JSchException e) {

						mapParametros.put(ERROR_ARCHIVO, e.getMessage());
						mapParametros.put(AVISO, ARCHIVO_TRASF

						+ REVISAR);

					} catch (SftpException e) {

						mapParametros.put(ERROR_ARCHIVO, e.getMessage());
						mapParametros.put(AVISO, ARCHIVO_TRASF

						+ REVISAR);

					}

				} else {

					mapParametros.put(AVISO, ARCHIVO_TRASF

					+ REVISAR);

				}
			} else {
				mapParametros.put(AVISO, ARCHIVO_TRASF + REVISAR);
			}
		}
		if (listaErrores != null) {
			mapParametros.put("totalRegistros", registrosTotales);
			mapParametros.put("totalExitosos", registrosCorrectos);

		}
		if (listaErrores != null) {
			mapParametros.put("errores", listaErrores);
			mapParametros.put("totalErrores", listaErrores.size());

		}
		debug("Regresando al jsp...");
		return new ModelAndView("AltaMasivaSucursales", mapParametros);
	}

	/**
	 * Desentrama archivo sucursales.
	 * 
	 * @param item
	 *            the item
	 * @return the object[]
	 */
	private Object[] desentramaArchivoSucursales(FileItem item) {
		final Object[] salida = new Object[3];
		final TreeMap<String, String> listaErrores = new TreeMap<String, String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(item
					.getInputStream()));
		} catch (IOException e) {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ex) {
				}
			}
			return null;
		}
		String linea = "";
		boolean errorLinea = false;
		int registrosCorrectos = 0;
		int i = 0;
		for (i = 1; linea != null; i++) {
			debug("linea: " + linea);
			try {
				linea = reader.readLine();
				if (linea == null) {
					continue;
				}
			} catch (IOException e) {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException ex) {
					}
				}
				return null;
			}
			if (linea.length() != 296) {
				listaErrores.put((Integer.valueOf(i)).toString(),
						"La longitud (" + linea.length()
								+ ")de la l&iacute;nea " + i
								+ " es inv&aacute;lida.");
				continue;
			}

			if (!("01".equalsIgnoreCase(linea.substring(0, 2))
					|| "02".equalsIgnoreCase(linea.substring(0, 2)) || "03"
					.equalsIgnoreCase(linea.substring(0, 2)))) { // operacion
				listaErrores.put(i + "-1", "Operaci&oacute;n inv&aacute;lida :"
						+ linea.substring(0, 2));
				errorLinea = true;
			}

			if (!isNumero(linea.substring(2, 6))) { // entidad banco
				listaErrores.put(i + "-2", "Entidad bancaria inv&aacute;lida:"
						+ linea.substring(2, 6));
				errorLinea = true;
			}

			if (!isNumero(linea.substring(6, 8))) // Codigo de Identificacion
			// del canal Corresponsalia
			{
				listaErrores
						.put(
								i + "-3",
								"C&oacute;digo de Identificaci&oacute;n del canal Corresponsal&iacute;a inv&aacute;lido:"
										+ linea.substring(6, 8));
				errorLinea = true;
			}

			if (!isNumero(linea.substring(8, 12))) { // Identificador
				// de Estatus del Corresponsal
				listaErrores
						.put(
								i + "-4",
								"C&oacute;digo de Identificaci&oacute;n del Corresponsal (Centro de Costos) inv&aacute;lido:"
										+ linea.substring(8, 12));
				errorLinea = true;
			}

			if (!isNumero(linea.substring(12, 22).trim())) { // Secuencia de
																// Domicilio
				listaErrores.put(i + "-5",
						"C&oacute;digo de Sucursal inv&aacute;lido:"
								+ linea.substring(12, 22));
				errorLinea = true;
			}
			if (!("CS2".equalsIgnoreCase(linea.substring(22, 25))
					|| "CS3".equalsIgnoreCase(linea.substring(22, 25)) || "CS4"
					.equalsIgnoreCase(linea.substring(22, 25)))) { // operacion
				listaErrores.put(i + "-6",
						"Identificador de Estatus de Sucursal inv&aacute;lido:"
								+ linea.substring(22, 25));
				errorLinea = true;
			}
			if (!validaCadena(linea.substring(25, 38), null)) { // RFC de la
				// Sucursal
				listaErrores.put(i + "-7",
						"RFC de la Sucursal  inv&aacute;lido:"
								+ linea.substring(25, 38));
				errorLinea = true;
			}
			if (!validaCadena(linea.substring(38, 42), null)) { // Region donde
				// se ubica la
				// Sucursal
				listaErrores.put(i + "-8",
						"Regi&oacute;n donde se ubica la Sucursal inv&aacute;lida:"
								+ linea.substring(38, 42));
				errorLinea = true;
			}

			if (!validaCadena(linea.substring(42, 47), null)) { // Zona donde se
				// ubica la
				// Sucursal
				listaErrores.put(i + "-9",
						"Zona donde se ubica la Sucursal inv&aacute;lida:"
								+ linea.substring(42, 47));
				errorLinea = true;
			}

			if (!validaCadena(linea.substring(47, 77).trim(), null)) { // Parametros
				// Dias
				// pendientes de
				// conciliar
				listaErrores.put(i + "-10",
						"Descripci&oacute;n de la Zona donde se ubica la sucursal inv&aacute;lida:"
								+ linea.substring(47, 77));
				errorLinea = true;
			}
			if (!("S".equalsIgnoreCase(linea.substring(77, 78)) || "N"
					.equalsIgnoreCase(linea.substring(77, 78)))) { // Indicador
				// de Zona
				// Fronteriza
				// (S, N)
				listaErrores.put(i + "-11",
						"Indicador de Zona Fronteriza (S, N) inv&aacute;lido:"
								+ linea.substring(77, 78));
				errorLinea = true;
			}
			if (!validaCadena(linea.substring(78, 118).trim(), null)) { // Nombre
				// de la
				// SUCURSAL
				listaErrores.put(i + "-12",
						"Nombre de la Sucursal inv&aacute;lido:"
								+ linea.substring(78, 118));
				errorLinea = true;
			}
			if (!validaCadena(linea.substring(118, 128).trim(), null)) { // Código
				// interno
				// de
				// la
				// Sucursal
				listaErrores.put(i + "-13",
						"C&oacute;digo interno de la Sucursal inv&aacute;lido:"
								+ linea.substring(118, 128));
				errorLinea = true;
			}

			if (!validaCadena(linea.substring(128, 148).trim(), null)) { // Domicilio
				// de
				// Sucursal
				// CALLE
				// /
				// AVENIDA
				listaErrores.put(i + "-14",
						"Domicilio de Sucursal, Calle / Avenida inv&aacute;lida:"
								+ linea.substring(128, 148));
				errorLinea = true;
			}

			if (!validaCadena(linea.substring(148, 153), null)) {// Domicilio de
				// Sucursal
				// NUMERO
				// EXTERIOR
				listaErrores.put(i + "-15",
						"Domicilio de Sucursal, N&uacute;mero Exterior inv&aacute;lido:"
								+ linea.substring(148, 153));
				errorLinea = true;
			}

			if (!validaCadena(linea.substring(153, 158), null)) {// Domicilio de
				// Sucursal
				// NUMERO
				// EXTERIOR
				listaErrores.put(i + "-16",
						"Domicilio de Sucursal, N&uacute;mero Interior inv&aacute;lido:"
								+ linea.substring(153, 158));
				errorLinea = true;
			}

			if (!validaCadena(linea.substring(158, 178).trim(), null)) { // Domicilio
				// de
				// Sucursal
				// COLONIA
				listaErrores.put(i + "-17",
						"Domicilio de Sucursal, Colonia inv&aacute;lida:"
								+ linea.substring(158, 178));
				errorLinea = true;
			}

			if (!validaCadena(linea.substring(178, 198), null)) {
				// Domicilio de Sucursal DELEGACION / MUNICIPIO
				listaErrores.put(i + "-18",
						"Domicilio de Sucursal, Delegaci&oacute;n / Municipio inv&aacute;lido:"
								+ linea.substring(178, 198));
				errorLinea = true;
			}
			if (!validaCadena(linea.substring(198, 218), null)) {
				// Domicilio de Sucursal CIUDAD
				listaErrores.put(i + "-19",
						"Domicilio de Sucursal, Ciudad inv&aacute;lido:"
								+ linea.substring(198, 218));
				errorLinea = true;
			}
			if (!validaCadena(linea.substring(218, 238), null)) {
				// Domicilio de Sucursal ENTIDAD FEDERATIVA
				listaErrores.put(i + "-20",
						"Domicilio de Sucursal, Entidad Federativa inv&aacute;lida:"
								+ linea.substring(218, 238));
				errorLinea = true;
			}
			if (!isNumero(linea.substring(238, 246).trim())) { // Secuencia de
				// Domicilio
				listaErrores.put(i + "-21",
						"C&oacute;digo de Sucursal inv&aacute;lida:"
								+ linea.substring(238, 246));
				errorLinea = true;
			}
			if (!validaCadena(linea.substring(246, 286), null)) {
				// Domicilio de Sucursal ENTIDAD FEDERATIVA
				listaErrores.put(i + "-22",
						"Domicilio de Sucursal, Entidad Federativa inv&aacute;lida:"
								+ linea.substring(246, 286));
				errorLinea = true;
			}
			debug("Linea: " + linea);
			if (!("".equalsIgnoreCase(linea.substring(286, 296).trim()) || validaCadena(
					linea.substring(286, 296).trim(), "[0-9]+"))) {
				// Teléfono
				listaErrores.put(i + "-23", "Tel&eacute;fono inv&aacute;lido:"
						+ linea.substring(286, 296));
				errorLinea = true;
			}
			if (!errorLinea) {
				registrosCorrectos++;
			}
			errorLinea = false;
		}
		salida[0] = Integer.valueOf(registrosCorrectos);
		salida[1] = Integer.valueOf(i - 2);
		salida[2] = listaErrores;
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
		return salida;
	}

	/**
	 * Obtener archivo.
	 * 
	 * @param req
	 *            the req
	 * @return the file item
	 */
	@SuppressWarnings("unchecked")
	private FileItem obtenerArchivo(HttpServletRequest req) {
		FileItem item = null;
		try {
			final DiskFileItemFactory factory = new DiskFileItemFactory();
			final ServletFileUpload upload = new ServletFileUpload(factory);
			final List<FileItem> items = upload.parseRequest(req);
			final Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
				item = (FileItem) iter.next();
				if (!item.isFormField()) {
					debug("Archivo: " + item.getName());
				}
			}
		} catch (FileUploadException e) {
			error("Error al obtener archivo: " + e.getMessage());
		}
		return item;

	}

	/**
	 * Copia archivo.
	 * 
	 * @param rutaDestino
	 *            the ruta destino
	 * @param host
	 *            the host
	 * @param username
	 *            the username
	 * @param archivoItem
	 *            the archivo item
	 * @param idFile
	 *            the id file
	 * @param FileName
	 *            the file name
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws JSchException
	 *             the j sch exception
	 * @throws SftpException
	 *             the sftp exception
	 */
	private void copiaArchivo(String rutaDestino, String host, String username,
			FileItem archivoItem, String idFile, String FileName)
			throws IOException, JSchException, SftpException {
		JSch jsch = null;
		ChannelSftp channel = null;
		Session session = null;
		File file = null;

		file = new File(idFile);
		jsch = new JSch();

		consoleCommPrint();

		if (file.exists()) {
			final byte[] prvkey = readMyPrivateKeyFromFile(file);

			jsch.addIdentity(username, prvkey, null, new byte[0]);
		} else {
			debug("No se encontró la llave privada: " + idFile);
		}

		session = jsch.getSession(username, host, 22);

		final UserInfo ui = new MyUserInfo();
		session.setUserInfo(ui);

		session.connect();
		info("SFTP Sesión abierta.");
		debug("SFTP Sesión abierta.");

		session.setConfig("PreferredAuthentications", "publickey");
		session.setConfig("StrictHostKeyChecking", "no");

		channel = (ChannelSftp) session.openChannel("sftp");

		channel.connect();
		info("SFTP Canal abierto.");
		channel.cd(rutaDestino);
		
		try {
			channel.put(archivoItem.getInputStream(), FileName != null
					|| !"".equals(FileName) ? FileName : archivoItem.getName());
			
		} catch (SftpException e) {
			channel.disconnect();
			debug("SFTP no se pudo enviar el archivo: " + e);
		}
		
		channel.disconnect();
		info("SFTP Archivo enviado con éxito.");

		/*
		 * Session session = jsch.getSession(usr, srvDestino, 22);
		 * session.setUserInfo(new HardcodedUserInfo(pwd)); Properties config =
		 * new Properties(); config.setProperty("StrictHostKeyChecking", "no");
		 * session.setConfig(config); session.connect(); ChannelSftp channel =
		 * (ChannelSftp) session.openChannel("sftp"); channel.connect();
		 * channel.cd(rutaDestino); channel.put(archivoItem.getInputStream(),
		 * archivoItem.getName()); channel.disconnect(); session.disconnect();
		 */
	}

	/**
	 * Read my private key from file.
	 * 
	 * @param file
	 *            the file
	 * @return the byte[]
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static byte[] readMyPrivateKeyFromFile(File file) throws IOException {
		final InputStream is = new FileInputStream(file);

		// Get the size of the file
		final long length = file.length();

		/*
		 * if (length > Integer.MAX_VALUE) { // File is too large }
		 */

		// Create the byte array to hold the data
		final byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;

	}

	/**
	 * The Class MyUserInfo.
	 */
	public static class MyUserInfo implements UserInfo {

		/** The passwd. */
		transient String passwd;

		/**
		 * get Password
		 * 
		 * @return String
		 */
		public String getPassword() {
			return passwd;
		}

		/**
		 * prompt Yes No
		 * 
		 * @param str
		 *            string
		 * @return boolean
		 */
		public boolean promptYesNo(String str) {

			return true;
		}

		/**
		 * get Pass phrase
		 * 
		 * @return String
		 */
		public String getPassphrase() {
			return null;
		}

		/**
		 * prompt Pass phrase
		 * 
		 * @param message
		 *            the message
		 * @return boolean
		 */
		public boolean promptPassphrase(String message) {
			return true;
		}

		/**
		 * prompt Password
		 * 
		 * @param message
		 *            the message
		 * @return boolean
		 */
		public boolean promptPassword(String message) {
			// System.out.println(“promptPassword”);
			// passwd = message;
			// passwd = “turkcell”;
			return true;
		}

		/**
		 * showMessage
		 * 
		 * @param message
		 */
		public void showMessage(String message) {

		}

		/**
		 * Prompt keyboard interactive.
		 * 
		 * @param destination
		 *            the destination
		 * @param name
		 *            the name
		 * @param instruction
		 *            the instruction
		 * @param prompt
		 *            the prompt
		 * @param echo
		 *            the echo
		 * @return the string[]
		 */
		public String[] promptKeyboardInteractive(String destination,
				String name, String instruction, String[] prompt, boolean[] echo) {
			return new String[3];
		}
	}

	/**
	 * Console comm print.
	 */
	private void consoleCommPrint() {

		String s = null;
		try {

			String comando = "pwd";

			Process p = Runtime.getRuntime().exec(comando);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(
					p.getErrorStream()));

			info("Ésta es la salida standard del comando: " + comando + "\n");
			while ((s = stdInput.readLine()) != null) {
				info(s);
			}

			debug("Ésta es la salida standard de error del comando (si la hay):\n");
			while ((s = stdError.readLine()) != null) {
				info(s);
			}

			comando = "id";
			s = null;
			p = Runtime.getRuntime().exec(comando);
			stdInput = new BufferedReader(new InputStreamReader(p
					.getInputStream()));
			stdError = new BufferedReader(new InputStreamReader(p
					.getErrorStream()));

			info("Salida estandar del comando: " + comando + "\n");
			while ((s = stdInput.readLine()) != null) {
				info(s);
			}
			debug("Salida estandar de error del comando (si la hay):\n");
			while ((s = stdError.readLine()) != null) {
				info(s);
			}

		} catch (IOException e) {
			info("Excepción: " + e.getMessage());
		}
	}

	/**
	 * Valida cadena.
	 * 
	 * @param entrada
	 *            the entrada
	 * @param patronOpc
	 *            the patron opc
	 * @return true, if successful
	 */
	public boolean validaCadena(String entrada, String patronOpc) {
		boolean respuesta = false;
		if (patronOpc == null || (patronOpc != null && "".equals(patronOpc))) {
			patronOpc = "[a-zA-Z0-9 ]*";
		}
		if (entrada.matches(patronOpc)) {
			respuesta = true;
		} else {
			respuesta = false;
		}
		return respuesta;
	}

	/**
	 * Checks if is numero.
	 * 
	 * @param cadena
	 *            the cadena
	 * @return true, if is numero
	 */
	private boolean isNumero(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	/**
	 * Checks if is decimal.
	 * 
	 * @param cadena
	 *            the cadena
	 * @return true, if is decimal
	 */
	private boolean isDecimal(String cadena) {
		try {
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

}