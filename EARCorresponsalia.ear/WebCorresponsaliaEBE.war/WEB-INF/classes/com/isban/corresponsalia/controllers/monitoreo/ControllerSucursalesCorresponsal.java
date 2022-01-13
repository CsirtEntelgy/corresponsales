package com.isban.corresponsalia.controllers.monitoreo;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.beans.monitoreo.BeanConsultaMonitoreoOperaciones;
import com.isban.corresponsalia.bo.monitoreo.BOSucursalesCorresponsal;
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
 * The Class ControllerSucursalesCorresponsal.
 */
@Controller
public class ControllerSucursalesCorresponsal extends ArchitechEBE {
	
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
	private static final String CONSULTA_SUC_CORRESPONSALES = "SucursalesCorresponsal";
	
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
	 
	 
	/** The bo lotes por compensar. */
	private transient BOSucursalesCorresponsal bOSucursalesCorresponsal;

	/**
	 * Sets the bO lotes por compensar.
	 *
	 * @param bOSucursalesCorresponsal the new bo lotes por compensar
	 */
	public void setBOSucursalesCorresponsal(
			BOSucursalesCorresponsal bOSucursalesCorresponsal) {
		this.bOSucursalesCorresponsal = bOSucursalesCorresponsal;
	}

	
	/**
	 * Lotes Por Compensar.
	 *
	 * @param request the request
	 * @param response the response
	 * @return the model and view
	 * @throws BusinessException the Business Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "SucursalesCorresponsal.do")
	public ModelAndView SucursalesCorresponsal(HttpServletRequest request,
			HttpServletResponse response) throws BusinessException {
		info("Inicia Sucursales Corresponsal");
		setArchitechBean((ArchitechSessionBean)request.getSession().getAttribute(NEW_ARCH));
		request.getSession().setAttribute("testVariable", null);
		final HashMap<String, Object> mapParametrosSucursal = new HashMap<String, Object>();
		final BeanMttoConsultaCorresponsal beanConsultaSucursal = new BeanMttoConsultaCorresponsal();
		final BeanConsultaMonitoreoOperaciones beanConsultaMonitoreoOperacionesSuc =
			new BeanConsultaMonitoreoOperaciones();

		request.getSession().removeAttribute("beanResultadoCorresponsalia");
		request.getSession().removeAttribute(
				LIST_CORRESPO);
		request.getSession()
				.removeAttribute("listaOperacionesMonitoreoOperaciones");
		request.getSession().removeAttribute("listaSucursalesMonitoreoOperaciones");
		request.getSession().removeAttribute(LIST_REG_CORRESPO);
		//
		request.getSession().removeAttribute(BEAN_CONSULTA);
		request.getSession().removeAttribute("disabled");
		//

		beanConsultaSucursal.setCodigoCorresponsalia(Parametros
				.getParametroAplicacion("CORRESPONSALIA"));
		beanConsultaSucursal.setIndicadorPaginacion("N");
		beanConsultaSucursal.setTipoConsulta("L");
		mapParametrosSucursal.put(CLASE, "class='CamposCompletar'");
		final BeanResultadoCorresponsalia beanResultadoCorresponsalia = bOSucursalesCorresponsal
				.consultaCorresponsales(beanConsultaSucursal, getArchitechBean());
		if (beanResultadoCorresponsalia.getRegistros().size() > 0) {
			request.getSession().setAttribute(
					LIST_CORRESPO,
					beanResultadoCorresponsalia.getRegistros());
		} else {
			mapParametrosSucursal.put(COD_ERROR, beanResultadoCorresponsalia
					.getCodError());
			mapParametrosSucursal.put(MSG_ERROR, beanResultadoCorresponsalia
					.getMsgError());
			request.getSession().setAttribute("listaCorresponsales",
					new ArrayList<BeanCorresponsal>());
		}
		request.getSession().setAttribute(BEAN_CONSULTA,
				beanConsultaMonitoreoOperacionesSuc);

		return new ModelAndView(CONSULTA_SUC_CORRESPONSALES, mapParametrosSucursal);
	}
	
	
	/**
	 * Alta masiva sucursales.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 * @throws JRException the JRException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "consultaSucCorresponsal.do")
	public ModelAndView consultaSucCorresponsal(HttpServletRequest request,
			HttpServletResponse response) throws JRException {
		setArchitechBean((ArchitechSessionBean) request.getSession().getAttribute(
				NEW_ARCH));
		final HashMap<String, Object> mapParametrosSucursal = new HashMap<String, Object>();
		int registrosCorrectos = 0;
		int registrosTotales = 0;
		String resultado = "";
		String codigoCorresponsal = "";
		
		if (request.getParameter("optCorresponsal1") == null) {
			codigoCorresponsal = "0";
		} else {
			codigoCorresponsal = request.getParameter("optCorresponsal1");
		}
		
		String nombreArchivo = codigoCorresponsal + "_suc.txt";
		
		mapParametrosSucursal.put(MSG_RESULTADO, null);
		mapParametrosSucursal.put(ERROR_ARCHIVO, null);
		
		debug("Codigo Corresponsal a consultar: " + codigoCorresponsal);

				final String rutaDestinoSuc = Parametros.getParametroAplicacion("RUTA_INTERFACES_REMOTA");
				debug("RUTA_INTERFACES_REMOTA: " + rutaDestinoSuc);
				final String hostDestinoSuc = Parametros.getParametroAplicacion("HOST_INTERFACES_REMOTA");
				debug("HOST_INTERFACES_REMOTA: " + hostDestinoSuc);
				final String usr = Parametros.getParametroAplicacion("USR_INTERFACES");
				final String idFile = Parametros.getParametroAplicacion("KID_FILE");
				
					try {

						resultado = traerArchivo(rutaDestinoSuc, hostDestinoSuc, usr, idFile, nombreArchivo);
						
						request.getSession().setAttribute("testVariable", resultado);
						
						request.getSession().setAttribute("codSucursal", codigoCorresponsal);
				        
				        if (!"".equals(resultado)) {
				        	
				        	mapParametrosSucursal.put(MSG_RESULTADO,"El archivo ha sido depositado correctamente.");
				        	mapParametrosSucursal.put("respuesta",resultado);
				        	
				        	debug("El archivo ha sido depositado correctamente.");
				        	
				        } else {
				        	
				        	mapParametrosSucursal.put(MSG_RESULTADO, "El archivo no fue encontrado.");
				        	mapParametrosSucursal.put("respuesta", resultado);
				        	
				        	debug("El archivo no fue encontrado.");
				        }

					} catch (IOException e) {

						mapParametrosSucursal.put(ERROR_ARCHIVO, e.getMessage());
						mapParametrosSucursal.put(AVISO, ARCHIVO_TRASF

						+ REVISAR);
						
						debug("Error de tipo IOException: " + e);

					} catch (SftpException e) {

							mapParametrosSucursal.put(ERROR_ARCHIVO, e.getMessage());
							mapParametrosSucursal.put(AVISO, ARCHIVO_TRASF

							+ REVISAR);
							
							debug("Error de tipo SftpException: " + e);

						}
					
					mapParametrosSucursal.put("totalRegistros", registrosTotales);
					mapParametrosSucursal.put("totalExitosos", registrosCorrectos);
					
		debug("Regresando al jsp de consultaSucCorresponsal... " + resultado);
		return new ModelAndView(CONSULTA_SUC_CORRESPONSALES, mapParametrosSucursal);
	}
	
	/**
	 * Get archivo.
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
	private String traerArchivo(String rutaDestino, String host, String username, String idFile, String fileName)
			throws IOException, SftpException {
		File file = null;
		
		file = new File(idFile);
		JSch jschSuc = null;
		ChannelSftp channelSuc = new ChannelSftp();
		Session sessionSuc = null;
		jschSuc = new JSch();
		
		try {
			if (file.exists()) {
				final byte[] prvkey = readMyPrivateKeyFromFile(file);
				
				jschSuc.addIdentity(username, prvkey, null, new byte[0]);
			} else {
				debug("No se encontró la llave privada: " + idFile);
			}
			sessionSuc = jschSuc.getSession(username, host, 22);
			final UserInfo ui = new MyUserInfo();
			debug("ui " + ui);
			sessionSuc.setUserInfo(ui);
			debug("Paso UserInfo");
			
			sessionSuc.connect();
			info("SFTP Sesión abierta.");
			
			sessionSuc.setConfig("PreferredAuthentications", "publickey");
			sessionSuc.setConfig("StrictHostKeyChecking", "no");
			
			channelSuc = (ChannelSftp) sessionSuc.openChannel("sftp");
			
			channelSuc.connect();
			
		} catch (JSchException jse) {
			channelSuc.disconnect();
			debug("SFTP error: " + jse);
		}
		
		info("SFTP Canal abierto.");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		BufferedOutputStream buff = new BufferedOutputStream(outputStream);
		channelSuc.cd(rutaDestino);
		  
		try {
			channelSuc.get(fileName, buff);
				
		} catch (SftpException e) {
			channelSuc.disconnect();
			debug("SFTP no se encontro el archivo: " + e);
			return "";
		}
		  
		channelSuc.disconnect();
		info("SFTP Archivo Descargado con éxito.");
		return outputStream.toString("UTF-8");
	}
	
	
	/**
	 * The Class MyUserInfo.
	 */
	public static class MyUserInfo implements UserInfo {

		/** The passwd. */
		private transient String password;

		/**
		 * get Password
		 * 
		 * @return String
		 */
		public String getPassword() {
			return password;
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
			return true;
		}

		/**
		 * showMessage
		 * 
		 * @param message the message
		 */
		public void showMessage(String message) {
			throw new UnsupportedOperationException();
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
	 * Read my private key from file.
	 * 
	 * @param fileSuc
	 *            the fileSuc
	 * @return the byte[]
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static byte[] readMyPrivateKeyFromFile(File fileSuc) throws IOException {
		final InputStream isSuc = new FileInputStream(fileSuc);

		// Obtener el tamaño del archivo
		final long length = fileSuc.length();

		// Se crea el arreglo en bytes para almacenar los datos
		final byte[] bytesSuc = new byte[(int) length];

		// Lectura de los bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytesSuc.length
				&& (numRead = isSuc.read(bytesSuc, offset, bytesSuc.length - offset)) >= 0) {
			offset += numRead;
		}

		// Asegurarse todos los bytes han sido leidos
		if (offset < bytesSuc.length) {
			throw new IOException("Could not completely read file "
					+ fileSuc.getName());
		}

		// Se cierra el stream ingresado y se retornan los bytes
		isSuc.close();
		return bytesSuc;

	}
}
