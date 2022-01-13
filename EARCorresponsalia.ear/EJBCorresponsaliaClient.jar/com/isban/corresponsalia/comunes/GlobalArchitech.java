package com.isban.corresponsalia.comunes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import com.isban.ebe.commons.architech.ArchitechEBE;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;

public class GlobalArchitech extends ArchitechEBE {

	/** The Constant TXT_ACTIVO. */
	private static final String TXT_ACTIVO = "Archivo: ";
	
	/**
	 * Valida nombre del archivo.
	 * 
	 * @param operacion
	 *            la operacion que se realizara
	 * @param nombreArchivo
	 *            el nombre del archivo a validar
	 * @return the file item
	 */
	@SuppressWarnings("unchecked")
	public boolean validaNombre(String operacion, String nombreArchivo) {
		boolean nombreOk = false;
		try {
			if ("envioContactos".equals(operacion)){
				if ("contactos.txt".equals(nombreArchivo)) {
					debug(TXT_ACTIVO + nombreArchivo);
					nombreOk = true;
				}
			}else {
				if ("envioComisiones".equals(operacion)){
					if ("comisiones.txt".equals(nombreArchivo)) {
						debug(TXT_ACTIVO + nombreArchivo);
						nombreOk = true;
						}
				}else{
					if("envioParametria".equals(operacion)){
						if("parametria.txt".equals(nombreArchivo)){
							debug(TXT_ACTIVO + nombreArchivo);
							nombreOk = true;
						}
					}
				}
			}
		}catch(Exception e) {
			error("Error al validar el nombre del archivo: " + e.getMessage());
		}
		
		return nombreOk;

	}
	/**
	 * Obtener archivo.
	 * 
	 * @param req
	 *            the req
	 * @return the file item
	 */
	@SuppressWarnings("unchecked")
	public FileItem obtenerArchivo(HttpServletRequest req) {
		FileItem item = null;
		try {
			final DiskFileItemFactory factory = new DiskFileItemFactory();
			final ServletFileUpload upload = new ServletFileUpload(factory);
			final List<FileItem> items = upload.parseRequest(req);
			final Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
				item = (FileItem) iter.next();
				if (!item.isFormField()) {
					debug(TXT_ACTIVO + item.getName());
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
	public void copiaArchivo(String rutaDestino, String host, String username,
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

		session.setConfig("PreferredAuthentications", "publickey");
		session.setConfig("StrictHostKeyChecking", "no");

		channel = (ChannelSftp) session.openChannel("sftp");

		channel.connect();
		info("SFTP Canal abierto.");
		channel.cd(rutaDestino);
		channel.put(archivoItem.getInputStream(), FileName != null
				|| !"".equals(FileName) ? FileName : archivoItem.getName());
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
		 * @param message Message
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
	public void consoleCommPrint() {

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
			patronOpc = "[A-Za-z0-9 ]";
		}
		final Pattern patron = Pattern.compile(patronOpc);
		final Matcher encaja = patron.matcher(entrada);
		if (encaja.find()) {
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
	public boolean isNumero(String cadena) {
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
	public boolean isDecimal(String cadena) {
		try {
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

}
