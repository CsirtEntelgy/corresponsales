package com.isban.corresponsalia.controllers.consultas;

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
import com.isban.corresponsalia.bo.consultas.BOLotesPorCompensar;
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
 * The Class ControllerLotesPorCompensar.
 */
@Controller
public class ControllerLotesPorCompensar extends ArchitechEBE {
	
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
	private static final String CONSULTA_LOTES = "LotesPorCompensar";
	
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
	private transient BOLotesPorCompensar bOLotesPorCompensar;

	/**
	 * Sets the bO lotes por compensar.
	 *
	 * @param bOLotesPorCompensar the new bo lotes por compensar
	 */
	public void setBOLotesPorCompensar(
			BOLotesPorCompensar bOLotesPorCompensar) {
		this.bOLotesPorCompensar = bOLotesPorCompensar;
	}

	
	/**
	 * Lotes Por Compensar.
	 *
	 * @param request the request
	 * @param response the response
	 * @return the model and view
	 * @throws BusinessException the Bussiness Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "LotesPorCompensar.do")
	public ModelAndView LotesPorCompensar(HttpServletRequest request,
			HttpServletResponse response) throws BusinessException {
		info("Inicia Lotes por Compensar");
		setArchitechBean((ArchitechSessionBean)request.getSession().getAttribute(NEW_ARCH));
		request.getSession().setAttribute("testVariable", null);
		final HashMap<String, Object> mapParametrosLotes = new HashMap<String, Object>();
		final BeanMttoConsultaCorresponsal beanConsultaLotes = new BeanMttoConsultaCorresponsal();
		final BeanConsultaMonitoreoOperaciones beanConsultaMonitoreoOperacionesLotes =
			new BeanConsultaMonitoreoOperaciones();

		request.getSession().removeAttribute("beanResultadoCorresponsalia");
		request.getSession().removeAttribute(LIST_CORRESPO);
		request.getSession().removeAttribute("listaOperacionesMonitoreoOperaciones");
		request.getSession().removeAttribute("listaSucursalesMonitoreoOperaciones");
		request.getSession().removeAttribute(LIST_REG_CORRESPO);
		request.getSession().removeAttribute(BEAN_CONSULTA);
		request.getSession().removeAttribute("disabled");

		beanConsultaLotes.setCodigoCorresponsalia(Parametros
				.getParametroAplicacion("CORRESPONSALIA"));
		beanConsultaLotes.setIndicadorPaginacion("N");
		beanConsultaLotes.setTipoConsulta("L");
		mapParametrosLotes.put(CLASE, "class='CamposCompletar'");
		
		final BeanResultadoCorresponsalia beanResultadoCorresponsaliaLotes = bOLotesPorCompensar
				.consultaCorresponsales(beanConsultaLotes, getArchitechBean());
		
		if (beanResultadoCorresponsaliaLotes.getRegistros().size() > 0) {
			request.getSession().setAttribute(LIST_CORRESPO,
					beanResultadoCorresponsaliaLotes.getRegistros());
		} else {
			mapParametrosLotes.put(COD_ERROR, beanResultadoCorresponsaliaLotes.getCodError());
			mapParametrosLotes.put(MSG_ERROR, beanResultadoCorresponsaliaLotes.getMsgError());
			request.getSession().setAttribute("listaCorresponsales",
					new ArrayList<BeanCorresponsal>());
		}
		request.getSession().setAttribute(BEAN_CONSULTA,
				beanConsultaMonitoreoOperacionesLotes);

		return new ModelAndView(CONSULTA_LOTES, mapParametrosLotes);
	}
	
	/**
	 * Alta masiva sucursales.
	 * @param request the request
	 * @param response the response
	 * @return the model and view
	 * @throws JRException the JRException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "consultaLotesPorCompensar.do")
	public ModelAndView consultaLotesPorCompensar(HttpServletRequest request,
			HttpServletResponse response) throws JRException {
		setArchitechBean((ArchitechSessionBean) request.getSession().getAttribute(
				NEW_ARCH));
		final HashMap<String, Object> mapParametrosLotes = new HashMap<String, Object>();
		int registrosCorrectos = 0;
		int registrosTotales = 0;
		String resultado = "";
		String codigoCorresponsal = "";
		
		if (request.getParameter("optCorresponsal1") == null) {
			codigoCorresponsal = "0";
		} else {
			codigoCorresponsal = request.getParameter("optCorresponsal1");
		}
		
		String nombreArchivo = codigoCorresponsal + "_ope_por_comp.txt";
		
		mapParametrosLotes.put(MSG_RESULTADO, null);
		mapParametrosLotes.put(ERROR_ARCHIVO, null);
		
		debug("Codigo Corresponsal a consultar: " + codigoCorresponsal);

				final String rutaDestino = Parametros.getParametroAplicacion("RUTA_INTERFACES_REMOTA");
				debug("RUTA_INTERFACES_REMOTA: " + rutaDestino);
				final String hostDestino = Parametros.getParametroAplicacion("HOST_INTERFACES_REMOTA");
				debug("HOST_INTERFACES_REMOTA: " + hostDestino);
				final String usr = Parametros.getParametroAplicacion("USR_INTERFACES");
				final String idFile = Parametros.getParametroAplicacion("KID_FILE");
					try {

						resultado = traerArchivo(rutaDestino, hostDestino, usr, idFile, nombreArchivo);
					
						request.getSession().setAttribute("testVariable", resultado);

						request.getSession().setAttribute("codSucursal", codigoCorresponsal);
				        
				        if (!"".equals(resultado)) {
				        	
				        	mapParametrosLotes.put(MSG_RESULTADO, 
				        			"El archivo ha sido depositado correctamente.");
				        	mapParametrosLotes.put("respuesta",resultado);
				        	
				        	debug("El archivo ha sido depositado correctamente.");
				        	
				        } else {
				        	
				        	mapParametrosLotes.put(MSG_RESULTADO, 
				        			"El archivo no fue encontrado.");
				        	mapParametrosLotes.put("respuesta", resultado);
				        	
				        	debug("El archivo no fue encontrado.");
				        }
						
					} catch (IOException e) {

						mapParametrosLotes.put(ERROR_ARCHIVO, e.getMessage());
						mapParametrosLotes.put(AVISO, ARCHIVO_TRASF

						+ REVISAR);
						
						debug("Error de tipo IOException: " + e);

					} catch (SftpException e) {

						mapParametrosLotes.put(ERROR_ARCHIVO, e.getMessage());
						mapParametrosLotes.put(AVISO, ARCHIVO_TRASF

						+ REVISAR);
						
						debug("Error de tipo SftpException: " + e);

					}
					
					mapParametrosLotes.put("totalRegistros", registrosTotales);
					mapParametrosLotes.put("totalExitosos", registrosCorrectos);
		
		debug("Regresando al jsp de consultaLotesPorCompensar... " + resultado);
		return new ModelAndView(CONSULTA_LOTES, mapParametrosLotes);
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
		JSch jschLotes = null;
		ChannelSftp channelSftpLotes = new ChannelSftp();
		Session sessionLotes = null;
		jschLotes = new JSch();
		
		try {
			if (file.exists()) {
				final byte[] prvkey = readMyPrivateKeyFromFile(file);
				
				jschLotes.addIdentity(username, prvkey, null, new byte[0]);
			} else {
				debug("No se encontró la llave privada: " + idFile);
			}
			
			sessionLotes = jschLotes.getSession(username, host, 22);
			final UserInfo userInfoLotes = new MyUserInfo();
			sessionLotes.setUserInfo(userInfoLotes);
			
			sessionLotes.connect();
			info("SFTP Sesión abierta.");
			
			sessionLotes.setConfig("PreferredAuthentications", "publickey");
			sessionLotes.setConfig("StrictHostKeyChecking", "no");
			
			channelSftpLotes = (ChannelSftp) sessionLotes.openChannel("sftp");
			
			channelSftpLotes.connect();
			
		} catch (JSchException jse) {
			channelSftpLotes.disconnect();
			debug("SFTP error: " + jse);
		}
		
		info("SFTP Canal abierto.");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		  BufferedOutputStream buff = new BufferedOutputStream(outputStream);
		  channelSftpLotes.cd(rutaDestino);
		  
		  try {
			  
			  channelSftpLotes.get(fileName, buff);
			
		} catch (SftpException e) {
			channelSftpLotes.disconnect();
			debug("SFTP no se encontro el archivo: " + e);
			return "";
		}
		  
		channelSftpLotes.disconnect();
		info("SFTP Archivo Descargado con éxito.");
		return outputStream.toString("UTF-8");
	}
	
	
	/**
	 * The Class MyUserInfo.
	 */
	public static class MyUserInfo implements UserInfo {

		/** The password. */
		private transient String password;

		/**
		 * Get Password
		 * 
		 * @return String
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * Prompt Yes or No
		 * 
		 * @param string
		 *            string
		 * @return boolean
		 */
		public boolean promptYesNo(String string) {
			return true;
		}

		/**
		 * Get Pass phrase
		 * 
		 * @return null
		 */
		public String getPassphrase() {
			return null;
		}

		/**
		 * Prompt Pass Phrase
		 * 
		 * @param messageString
		 *            the messageString
		 * @return boolean
		 */
		public boolean promptPassphrase(String messageString) {
			return true;
		}

		/**
		 * Prompt Password
		 * 
		 * @param messageString
		 *            the messageString
		 * @return boolean
		 */
		public boolean promptPassword(String messageString) {
			return true;
		}

		/**
		 * Show Message
		 * 
		 * @param messageStringLotes the messageStringLotes
		 */
		public void showMessage(String messageStringLotes) {
			throw new UnsupportedOperationException();
		}

		/**
		 * Prompt keyboard interactive lotes.
		 * 
		 * @param destinationLotes
		 *            the destinationLotes
		 * @param nameLotes
		 *            the nameLotes
		 * @param instructionLotes
		 *            the instructionLotes
		 * @param promptLotes
		 *            the promptLotes
		 * @param echoLotes
		 *            the echoLotes
		 * @return the string[]
		 */
		public String[] promptKeyboardInteractive(String destinationLotes,
				String nameLotes, String instructionLotes, String[] promptLotes, boolean[] echoLotes) {
			return new String[3];
		}
	}
	
	/**
	 * Read my private key from file.
	 * 
	 * @param fileLotes
	 *            the fileLotes
	 * @return the byte[]
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static byte[] readMyPrivateKeyFromFile(File fileLotes) throws IOException {
		final InputStream isLotes = new FileInputStream(fileLotes);

		// Obtener el tamaño del archivo
		final long length = fileLotes.length();

		// Se crea el arreglo en bytes para almacenar los datos
		final byte[] bytesLotes = new byte[(int) length];

		// Lectura de los bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytesLotes.length
				&& (numRead = isLotes.read(bytesLotes, offset, bytesLotes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Asegurarse todos los bytes han sido leidos
		if (offset < bytesLotes.length) {
			throw new IOException("Could not completely read file "
					+ fileLotes.getName());
		}

		// Se cierra el stream ingresado y se retornan los bytes
		isLotes.close();
		return bytesLotes;

	}
}
