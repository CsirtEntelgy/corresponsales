package com.isban.corresponsalia.controllers.corresponsales;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.isban.corresponsalia.comunes.GlobalArchitech;
import com.isban.corresponsalia.comunes.LineaComisiones;
import com.isban.corresponsalia.comunes.LineaContactos;
import com.isban.corresponsalia.comunes.LineaParametria;
import com.isban.corresponsalia.comunes.Parametros;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

//TODO: Auto-generated Javadoc
/**
 * The Class ControllerCorresponsalesAltaMasiva.
 */
@Controller
public class ControllerCorresponsalesParametriaMasiva extends ArchitechEBE {

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

//	/** The Constant TXT_ACTIVO. */
//	private static final String TXT_ACTIVO = "Archivo: ";

	public GlobalArchitech valida = new GlobalArchitech();

	
	/**
	 * Parametria masiva.
	 * 
	 * @param req
	 *            the request
	 * @param res
	 *            the response
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ParaMasiva.do")
	public ModelAndView parametriaMasiva(HttpServletRequest req,
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
				operacion = "inicial";
		}
		
		info("Operacion: " + operacion);
		Object[] salida = null;
		if (operacion.contains("envio")) {
			final FileItem archivo = valida.obtenerArchivo(req);
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
			} else if (!valida.validaNombre(operacion,nombreArchivo)) {
				listaErrores
						.put("0-1",
								"Nombre archivo incorrecto, solo se acepta '"+ operacion.substring(5).toLowerCase()+ ".txt'");
				info(archivo.getName());
			} else {
				if ("envioContactos".equals(operacion)){
					salida = desentramaArchContactos(archivo);
				}else {
					if ("envioComisiones".equals(operacion)){
						salida = desentramaArchComisiones(archivo);
					}else{
						if("envioParametria".equals(operacion)){
							salida = desentramaArchParametria(archivo);
						}
					}
				}
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
						debug("PASANDO ARCHIVO: " + operacion.substring(5).toLowerCase()+ ".txt");
						valida.copiaArchivo(rutaDestino, hostDestino, usr, archivo,
								idFile, operacion.substring(5).toLowerCase()+ ".txt");
						mapParametros.put(MSG_RESULTADO,
								"El archivo " + operacion.substring(5).toLowerCase()+ ".txt" + " ha sido depositado correctamente.");
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
			mapParametros.put("totalErrores", registrosTotales-registrosCorrectos);
		}
		
		debug("Regresando al jsp...");
		return new ModelAndView("ParametriaMasiva", mapParametros);
	}

	/**
	 * Desentrama archivo de contactos.
	 * 
	 * @param item
	 *            the item
	 * @return the object[]
	 */
	private Object[] desentramaArchContactos(FileItem item) {
		final Object[] salida = new Object[3];
//		final TreeMap<String, String> listaErrores = new TreeMap<String, String>();
		BufferedReader reader = null;
		final LineaContactos lineaCon= new LineaContactos();

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
		int i = 0;
		int j = 2;
//		for (i = 1; linea != null; i++) {
		for (i = 1; i < j; i++) {
			try {
				linea = reader.readLine();
				if (linea != null) {
					lineaCon.validaciones(linea, i);	
					if (!lineaCon.errorLinea) {
						registrosCorrectos++;
					}
					lineaCon.setErrorLinea(false);
					j ++;
				}
				else{
					j = i-1;
					i--;
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
		}
		salida[0] = Integer.valueOf(registrosCorrectos);
		salida[1] = Integer.valueOf(i - 1);
		salida[2] = lineaCon.getListaErrores();
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
		return salida;
	}
	/**
	 * Desentrama archivo de comisiones.
	 * 
	 * @param item
	 *            the item
	 * @return the object[]
	 */
	private Object[] desentramaArchComisiones(FileItem item) {
		final Object[] salida = new Object[3];
//		final TreeMap<String, String> listaErrores = new TreeMap<String, String>();
		BufferedReader reader = null;
		final LineaComisiones lineaCon= new LineaComisiones();

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
		int i = 0;
		int j = 2;
		for (i = 1; i < j; i++) {
			try {
				linea = reader.readLine();
				if (linea != null) {
					lineaCon.validaciones(linea, i);
					if (!lineaCon.errorLinea) {
						registrosCorrectos++;
					}
					lineaCon.setErrorLinea(false);
					j ++;
				}
				else{
					j = i-1;
					i--;
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
		}
		salida[0] = Integer.valueOf(registrosCorrectos);
		salida[1] = Integer.valueOf(i - 1);
		salida[2] = lineaCon.getListaErrores();
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
		return salida;
	}/**
	 * Desentrama archivo de Parametria.
	 * 
	 * @param item
	 *            the item
	 * @return the object[]
	 */
	private Object[] desentramaArchParametria(FileItem item) {
		final Object[] salida = new Object[3];
//		final TreeMap<String, String> listaErrores = new TreeMap<String, String>();
		BufferedReader reader = null;
		final LineaParametria lineaCon= new LineaParametria();

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
		int j = 2;
		for (i = 1; i < j; i++) {
			try {
				linea = reader.readLine();
				if (linea != null) {
					lineaCon.validaciones(linea, i);
					if (!lineaCon.errorLinea) {
						registrosCorrectos++;
					}
					lineaCon.setErrorLinea(false);
					j ++;
				}
				else{
					j = i-1;
					i--;
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
		}
		salida[0] = Integer.valueOf(registrosCorrectos);
		salida[1] = Integer.valueOf(i - 1);
		salida[2] = lineaCon.getListaErrores();
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
		return salida;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	/**
//	 * Valida nombre del archivo.
//	 * 
//	 * @param operacion
//	 *            la operacion que se realizara
//	 * @param nombreArchivo
//	 *            el nombre del archivo a validar
//	 * @return the file item
//	 */
//	@SuppressWarnings("unchecked")
//	private boolean validaNombre(String operacion, String nombreArchivo) {
//		boolean nombreOk = false;
//		try {
//			if ("envioContactos".equals(operacion)){
//				if ("contactos.txt".equals(nombreArchivo)) {
//					debug(TXT_ACTIVO + nombreArchivo);
//					nombreOk = true;
//				}
//			}else {
//				if ("envioComisiones".equals(operacion)){
//					if ("comisiones.txt".equals(nombreArchivo)) {
//						debug(TXT_ACTIVO + nombreArchivo);
//						nombreOk = true;
//						}
//				}else{
//					if("envioParametria".equals(operacion)){
//						if("parametria.txt".equals(nombreArchivo)){
//							debug(TXT_ACTIVO + nombreArchivo);
//							nombreOk = true;
//						}
//					}
//				}
//			}
//		}catch(Exception e) {
//			error("Error al validar el nombre del archivo: " + e.getMessage());
//		}
//		
//		return nombreOk;
//
//	}
//	/**
//	 * Obtener archivo.
//	 * 
//	 * @param req
//	 *            the req
//	 * @return the file item
//	 */
//	@SuppressWarnings("unchecked")
//	private FileItem obtenerArchivo(HttpServletRequest req) {
//		FileItem item = null;
//		try {
//			final DiskFileItemFactory factory = new DiskFileItemFactory();
//			final ServletFileUpload upload = new ServletFileUpload(factory);
//			final List<FileItem> items = upload.parseRequest(req);
//			final Iterator<FileItem> iter = items.iterator();
//			while (iter.hasNext()) {
//				item = (FileItem) iter.next();
//				if (!item.isFormField()) {
//					debug(TXT_ACTIVO + item.getName());
//				}
//			}
//		} catch (FileUploadException e) {
//			error("Error al obtener archivo: " + e.getMessage());
//		}
//		return item;
//
//	}
//
//	/**
//	 * Copia archivo.
//	 * 
//	 * @param rutaDestino
//	 *            the ruta destino
//	 * @param host
//	 *            the host
//	 * @param username
//	 *            the username
//	 * @param archivoItem
//	 *            the archivo item
//	 * @param idFile
//	 *            the id file
//	 * @param FileName
//	 *            the file name
//	 * @throws IOException
//	 *             Signals that an I/O exception has occurred.
//	 * @throws JSchException
//	 *             the j sch exception
//	 * @throws SftpException
//	 *             the sftp exception
//	 */
//	private void copiaArchivo(String rutaDestino, String host, String username,
//			FileItem archivoItem, String idFile, String FileName)
//			throws IOException, JSchException, SftpException {
//		JSch jsch = null;
//		ChannelSftp channel = null;
//		Session session = null;
//		File file = null;
//
//		file = new File(idFile);
//		jsch = new JSch();
//
//		consoleCommPrint();
//
//		if (file.exists()) {
//			final byte[] prvkey = readMyPrivateKeyFromFile(file);
//
//			jsch.addIdentity(username, prvkey, null, new byte[0]);
//		} else {
//			debug("No se encontró la llave privada: " + idFile);
//		}
//
//		session = jsch.getSession(username, host, 22);
//
//		final UserInfo ui = new MyUserInfo();
//		session.setUserInfo(ui);
//
//		session.connect();
//		info("SFTP Sesión abierta.");
//
//		session.setConfig("PreferredAuthentications", "publickey");
//		session.setConfig("StrictHostKeyChecking", "no");
//
//		channel = (ChannelSftp) session.openChannel("sftp");
//
//		channel.connect();
//		info("SFTP Canal abierto.");
//		channel.cd(rutaDestino);
//		channel.put(archivoItem.getInputStream(), FileName != null
//				|| !"".equals(FileName) ? FileName : archivoItem.getName());
//		info("SFTP Archivo enviado con éxito.");
//
//		/*
//		 * Session session = jsch.getSession(usr, srvDestino, 22);
//		 * session.setUserInfo(new HardcodedUserInfo(pwd)); Properties config =
//		 * new Properties(); config.setProperty("StrictHostKeyChecking", "no");
//		 * session.setConfig(config); session.connect(); ChannelSftp channel =
//		 * (ChannelSftp) session.openChannel("sftp"); channel.connect();
//		 * channel.cd(rutaDestino); channel.put(archivoItem.getInputStream(),
//		 * archivoItem.getName()); channel.disconnect(); session.disconnect();
//		 */
//	}
//
//	/**
//	 * Read my private key from file.
//	 * 
//	 * @param file
//	 *            the file
//	 * @return the byte[]
//	 * @throws IOException
//	 *             Signals that an I/O exception has occurred.
//	 */
//	public static byte[] readMyPrivateKeyFromFile(File file) throws IOException {
//		final InputStream is = new FileInputStream(file);
//
//		// Get the size of the file
//		final long length = file.length();
//
//		/*
//		 * if (length > Integer.MAX_VALUE) { // File is too large }
//		 */
//
//		// Create the byte array to hold the data
//		final byte[] bytes = new byte[(int) length];
//
//		// Read in the bytes
//		int offset = 0;
//		int numRead = 0;
//		while (offset < bytes.length
//				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
//			offset += numRead;
//		}
//
//		// Ensure all the bytes have been read in
//		if (offset < bytes.length) {
//			throw new IOException("Could not completely read file "
//					+ file.getName());
//		}
//
//		// Close the input stream and return bytes
//		is.close();
//		return bytes;
//
//	}
//
//	/**
//	 * The Class MyUserInfo.
//	 */
//	public static class MyUserInfo implements UserInfo {
//
//		/** The passwd. */
//		transient String passwd;
//
//		/**
//		 * get Password
//		 * 
//		 * @return String
//		 */
//		public String getPassword() {
//			return passwd;
//		}
//
//		/**
//		 * prompt Yes No
//		 * 
//		 * @param str
//		 *            string
//		 * @return boolean
//		 */
//		public boolean promptYesNo(String str) {
//
//			return true;
//		}
//
//		/**
//		 * get Pass phrase
//		 * 
//		 * @return String
//		 */
//		public String getPassphrase() {
//			return null;
//		}
//
//		/**
//		 * prompt Pass phrase
//		 * 
//		 * @param message
//		 *            the message
//		 * @return boolean
//		 */
//		public boolean promptPassphrase(String message) {
//			return true;
//		}
//
//		/**
//		 * prompt Password
//		 * 
//		 * @param message
//		 *            the message
//		 * @return boolean
//		 */
//		public boolean promptPassword(String message) {
//			// System.out.println(“promptPassword”);
//			// passwd = message;
//			// passwd = “turkcell”;
//			return true;
//		}
//
//		/**
//		 * showMessage
//		 * @param message Message
//		 */
//		public void showMessage(String message) {
//
//		}
//
//		/**
//		 * Prompt keyboard interactive.
//		 * 
//		 * @param destination
//		 *            the destination
//		 * @param name
//		 *            the name
//		 * @param instruction
//		 *            the instruction
//		 * @param prompt
//		 *            the prompt
//		 * @param echo
//		 *            the echo
//		 * @return the string[]
//		 */
//		public String[] promptKeyboardInteractive(String destination,
//				String name, String instruction, String[] prompt, boolean[] echo) {
//			return new String[3];
//		}
//	}
//
//	/**
//	 * Console comm print.
//	 */
//	private void consoleCommPrint() {
//
//		String s = null;
//		try {
//
//			String comando = "pwd";
//
//			Process p = Runtime.getRuntime().exec(comando);
//			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
//					p.getInputStream()));
//			BufferedReader stdError = new BufferedReader(new InputStreamReader(
//					p.getErrorStream()));
//
//			info("Ésta es la salida standard del comando: " + comando + "\n");
//			while ((s = stdInput.readLine()) != null) {
//				info(s);
//			}
//
//			debug("Ésta es la salida standard de error del comando (si la hay):\n");
//			while ((s = stdError.readLine()) != null) {
//				info(s);
//			}
//
//			comando = "id";
//			s = null;
//			p = Runtime.getRuntime().exec(comando);
//			stdInput = new BufferedReader(new InputStreamReader(p
//					.getInputStream()));
//			stdError = new BufferedReader(new InputStreamReader(p
//					.getErrorStream()));
//
//			info("Salida estandar del comando: " + comando + "\n");
//			while ((s = stdInput.readLine()) != null) {
//				info(s);
//			}
//			debug("Salida estandar de error del comando (si la hay):\n");
//			while ((s = stdError.readLine()) != null) {
//				info(s);
//			}
//
//		} catch (IOException e) {
//			info("Excepción: " + e.getMessage());
//		}
//	}
//
//	/**
//	 * Valida cadena.
//	 * 
//	 * @param entrada
//	 *            the entrada
//	 * @param patronOpc
//	 *            the patron opc
//	 * @return true, if successful
//	 */
//	public boolean validaCadena(String entrada, String patronOpc) {
//		boolean respuesta = false;
//		if (patronOpc == null || (patronOpc != null && "".equals(patronOpc))) {
//			patronOpc = "[A-Za-z0-9 ]";
//		}
//		final Pattern patron = Pattern.compile(patronOpc);
//		final Matcher encaja = patron.matcher(entrada);
//		if (encaja.find()) {
//			respuesta = true;
//		} else {
//			respuesta = false;
//		}
//		return respuesta;
//	}
//
//	/**
//	 * Checks if is numero.
//	 * 
//	 * @param cadena
//	 *            the cadena
//	 * @return true, if is numero
//	 */
//	private boolean isNumero(String cadena) {
//		try {
//			Integer.parseInt(cadena);
//			return true;
//		} catch (NumberFormatException nfe) {
//			return false;
//		}
//	}
//
//	/**
//	 * Checks if is decimal.
//	 * 
//	 * @param cadena
//	 *            the cadena
//	 * @return true, if is decimal
//	 */
//	private boolean isDecimal(String cadena) {
//		try {
//			Double.parseDouble(cadena);
//			return true;
//		} catch (NumberFormatException nfe) {
//			return false;
//		}
//	}
}
