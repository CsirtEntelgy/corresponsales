package com.isban.corresponsalia.controllers.consultas;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.isban.corresponsalia.comunes.Parametros;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;

import net.sf.jasperreports.engine.JRException;

/**
 * The Class ControllerConciliacionManual.
 */
@Controller
public class ControllerConciliacionManual extends ArchitechEBE {
	
	/** The Constant NEW_ARCH. */
	private static final String NEW_ARCH = "NewArchitechSession";	
	
	/** The Constant LIST_REG_CORRESPO. */
	private static final String LIST_REG_CORRESPO =  "listaRegistrosMonitoreoOperaciones";
	/** The Constant LIST_CORRESPO. */
	private static final String LIST_CORRESPO = "listaCorresponsalesMonitoreoOperaciones";
	
	/** The Constant BEAN_CONSULTA. */
	private static final String BEAN_CONSULTA = "beanConsultaMonitoreoOperaciones" ;
	
	/** The Constant CLASE. */
	private static final String CLASE = "clase";
	
	/** The Constant COD_ERROR. */
	private static final String COD_ERROR = "codError" ;
	
	/** The Constant MSG_ERROR. */
	private static final String MSG_ERROR = "msgError";
	
	/** The Constant CONSULTA_MONITOREO. */
	private static final String CONSULTA_CONCILIACION = "ConciliacionManual";
	
	
	/** The Constant MSG_RESULTADO. */
	private static final String MSG_RESULTADO = "msgResultado";

	/** The Constant ERROR_ARCHIVO. */
	private static final String ERROR_ARCHIVO = "errorArchivo";

	/** The Constant AVISO. */
	private static final String AVISO = "aviso";

	/** The Constant ARCHIVO_TRASF. */
	private static final String ARCHIVO_TRASF = "El archivo no fue transferido, ";

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
	@RequestMapping(value = "ConciliacionManual.do")
	public ModelAndView ConciliacionManual(HttpServletRequest req,
			HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean) req.getSession().getAttribute(
				"NewArchitechSession"));
		String resultado = "";
		int registrosCorrectos = 0;
		int registrosTotales = 0;
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();
		TreeMap<String, String> listaErrores = new TreeMap<String, String>();
		String operacion = req.getParameter("operacion");
		mapParametros.put(MSG_RESULTADO, null);
		mapParametros.put(ERROR_ARCHIVO, null);
		if (operacion == null) {
				operacion = "inicial";
		}
		
		info("Operacion: " + operacion);
		Object[] salida = null;
		if ("envioArchivo".equals(operacion)) {
			final FileItem archivo = obtenerArchivoConciliacion(req);
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
				info("Tipo de archivo incorrecto, solo se aceptan archivo de texto plano");
			} else if (!"conc_manual.txt".equals(nombreArchivo)) {
				listaErrores
						.put("0-1",
								"Nombre archivo incorrecto, solo se acepta 'conc_manual.txt'");
				info("Nombre archivo incorrecto, solo se acepta 'conc_manual.txt'. Nombre del archivo: " + archivo.getName());
			} else {
				salida = desentramaArchivoConciliacion(archivo);
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
						
						copiaArchivoConciliacion(rutaDestino, hostDestino, usr, archivo,
								idFile, "conc_manual.txt");
						
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
		return new ModelAndView("ConciliacionManual", mapParametros);
	}

	
	
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "consultaArchivoConciliacion.do", method= RequestMethod.GET)
	public String consultaArchivoConciliacion(HttpServletRequest req,
			HttpServletResponse res) throws JRException {
		setArchitechBean((ArchitechSessionBean) req.getSession().getAttribute(
				"NewArchitechSession"));
		return "Hola Mundo";
	}
	
	/**
	 * Obtener archivo.
	 * 
	 * @param req
	 *            the req
	 * @return the file item
	 */
	@SuppressWarnings("unchecked")
	private FileItem obtenerArchivoConciliacion(HttpServletRequest req) {
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
	 * Desentrama archivo corresponsales.
	 * 
	 * @param item
	 *            the item
	 * @return the object[]
	 */
	private Object[] desentramaArchivoConciliacion(FileItem item) {
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
			
			/* Inicio validacion de cadena */
			
			if (linea.length() != 70) {
				listaErrores.put((Integer.valueOf(i)).toString(),
						"La longitud (" + linea.length()
								+ ")de la l&iacute;nea " + i
								+ " es inv&aacute;lida.");
				continue;
			}

			// ENTIDAD - Número de entidad
			if (!validaCadenaConciliacion(linea.substring(0, 4), null)) {
				
				listaErrores.put(i + "-1",
						"ENTIDAD - N&uacute;mero de entidad inv&aacute;lido: "
								+ linea.substring(0, 4));
				errorLinea = true;
			}
			
			// COD-CRC - Número de entidad corresponsal
			if (!validaCadenaConciliacion(linea.substring(4, 6), null)) {

				listaErrores.put(i + "-2",
						"COD-CRC - N&uacute;mero de entidad corresponsal inv&aacute;lido: "
								+ linea.substring(4, 6));
				errorLinea = true;
			}

			// COD-CORRESP - Código de corresponsal
			if (!validaCadenaConciliacion(linea.substring(6, 10), null)) {

				listaErrores.put(i + "-3",
						"COD-CORRESP - C&oacute;digo de corresponsal inv&aacute;lido: "
								+ linea.substring(6, 10));
				errorLinea = true;
			}
			
			// COD-SUCURSAL - Código de sucursal
			if (!validaCadenaConciliacion(linea.substring(10, 20).trim(), null)) {

				listaErrores.put(i + "-4",
						"COD-SUCURSAL - C&oacute;digo de sucursal inv&aacute;lido: "
								+ linea.substring(10, 20));
				errorLinea = true;
			}
			
			// NUM-REFOPE - Número de referencia de operación
			if (!validaCadenaConciliacion(linea.substring(20, 40).trim(), null)) {

				listaErrores.put(i + "-5",
						"NUM-REFOPE - N&uacute;mero de referencia de operaci&oacute;n inv&aacute;lido: "
								+ linea.substring(20, 40));
				errorLinea = true;
			}
			
			// FOLIO-OPER - Folio de operación
			if (!validaCadenaConciliacion(linea.substring(40, 60).trim(), null)) {

				listaErrores.put(i + "-6",
						"FOLIO-OPER - Folio de operaci&oacute;n inv&aacute;lido: "
								+ linea.substring(40, 60));
				errorLinea = true;
			}

			// FECHA-ALTA - Fecha de alta
			if (!validaCadenaConciliacion(linea.substring(60, 70).trim(), null)) {

				listaErrores.put(i + "-7",
						"FECHA-ALTA - Fecha de alta inv&aacute;lida: "
								+ linea.substring(60, 70));
				errorLinea = true;
			}

			debug("Linea: " + linea);
			
			/* Fin validacion de cadena */
			
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
	private void copiaArchivoConciliacion(String rutaDestino, String host, String username,
			FileItem archivoItem, String idFile, String FileName)
			throws IOException, JSchException, SftpException {
		JSch jsch = null;
		ChannelSftp channel = null;
		Session session = null;
		File file = null;

		file = new File(idFile);
		jsch = new JSch();
		
		consoleCommPrintConciliacion();

		if (file.exists()) {
			final byte[] prvkey = readMyPrivateKeyFromFile(file);

			jsch.addIdentity(username, prvkey, null, new byte[0]);
		} else {
			debug("No se encontró la llave privada: " + idFile);
		}

		session = jsch.getSession(username, host, 22);

		final UserInfo ui = new MyUserInfo();
		session.setUserInfo(ui);
		//session.setPassword(Parametros.getParametroAplicacion("PASSWORD"));

		session.connect();
		info("SFTP Sesión abierta.");

		session.setConfig("PreferredAuthentications", "publickey");
		session.setConfig("StrictHostKeyChecking", "no");

		channel = (ChannelSftp) session.openChannel("sftp");

		channel.connect();
		info("SFTP Canal abierto.");
		
		channel.cd(rutaDestino);
		
		info("Proceso de carga de archivo iniciada...");
		channel.put(archivoItem.getInputStream(), FileName != null
				|| !"".equals(FileName) ? FileName : archivoItem.getName());
		info("Carga de archivo completada.");
			
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
	 * Valida cadena conciliacion
	 * 
	 * @param entrada
	 *            the entrada
	 * @param patronOpc
	 *            the patron opc
	 * @return true, if successful
	 */
	public boolean validaCadenaConciliacion(String entrada, String patronOpc) {
		boolean respuesta = false;
		if (patronOpc == null || (patronOpc != null && "".equals(patronOpc))) {
			patronOpc = "[a-zA-Z0-9-]*";
		}
		if (entrada.matches(patronOpc)) {
			respuesta = true;
		} else {
			respuesta = false;
		}
		return respuesta;
	}
	
	/**
	 * Console comm print.
	 */
	private void consoleCommPrintConciliacion() {

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
			// System.out.println("promptPassword");
			// passwd = message;
			// passwd = "turkcell";
			return true;
		}

		/**
		 * showMessage
		 * 
		 * @param message
		 */
		public void showMessage(String message) {

		}

	}
}
