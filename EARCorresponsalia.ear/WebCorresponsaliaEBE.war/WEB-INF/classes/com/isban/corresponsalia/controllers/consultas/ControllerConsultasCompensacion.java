package com.isban.corresponsalia.controllers.consultas;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.consultas.BeanConsultaBitacora;
import com.isban.corresponsalia.beans.consultas.BeanRegistroConsultaNoCompensacion;
import com.isban.corresponsalia.beans.consultas.RBeanAutorizaCompensacion;
import com.isban.corresponsalia.beans.consultas.RBeanConsultaNoCompensacion;
import com.isban.corresponsalia.beans.consultas.RBeanCorresponsalesConsultaCompensacion;
import com.isban.corresponsalia.beans.monitoreo.BeanMonitoreoCredito;
import com.isban.corresponsalia.beans.monitoreo.BeanRegistroMonitoreoCredito;
import com.isban.corresponsalia.beans.monitoreo.RBeanMonitoreoCredito;
import com.isban.corresponsalia.bo.consultas.BOConsultasCompensacion;
import com.isban.corresponsalia.bo.monitoreo.BOMonitoreoCredito;
import com.isban.corresponsalia.reportes.ExportaXLS;
import com.isban.corresponsalia.comunes.Parametros;
import com.isban.corresponsalia.controllers.consultas.ControllerLotesPorCompensar.MyUserInfo;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class ControllerConsultasCompensacion.
 */
@Controller
public class ControllerConsultasCompensacion extends ArchitechEBE {
	
	/** constante NEW_ARCH */
	private static final String NEW_ARCH = "NewArchitechSession";
	
	/** constante TXT_LIST. */
	private static final String TXT_LIST = "listaRegistrosCompensacion";
	
	/** constante TXT_COD_ERROR. */
	private static final String TXT_COD_ERROR = "codError";
	
	/** constante TXT_MSG_ERROR. */
	private static final String TXT_MSG_ERROR = "msgError";
	
	/** constante TXT_COD_AVISO. */
	private static final String TXT_COD_AVISO = "codAviso";
	
	/** constante TXT_MSG_AVISO. */
	private static final String TXT_MSG_AVISO = "msgAviso";
	
	
	
	
	
	/** constante TXT_C_COMPE. */
	private static final String TXT_C_COMPE = "ConsultaCompensacion";

	/** constante MSG_RESULTADO */
	private static final String MSG_RESULTADO = "msgResultado";

	/** constante ERROR_ARCHIVO */
	private static final String ERROR_ARCHIVO = "errorArchivo";

	/** constante AVISO */
	private static final String AVISO = "Aviso";

	/** constante ARCHIVO_TRANSF */
	private static final String ARCHIVO_TRANSF = "El archivo no fue transferido";

	/** constante REVISAR */
	private static final String REVISAR = "por favor revisar los errores e intentar de nuevo";
	

	/** The b o consultas compensacion. */
	transient private BOConsultasCompensacion bOConsultasCompensacion;
	
	/** The b o monitoreo credito. */
	transient private BOMonitoreoCredito bOMonitoreoCredito;
	
	
	/**
	 * Sets the bO monitoreo credito.
	 *
	 * @param bOMonitoreoCredito the new bO monitoreo credito
	 */
	public void setBOMonitoreoCredito(BOMonitoreoCredito bOMonitoreoCredito) {
		this.bOMonitoreoCredito = bOMonitoreoCredito;
	}

	/**
	 * Sets the bO consultas compensacion.
	 *
	 * @param bOConsultasCompensacion the new bO consultas compensacion
	 */
	public void setBOConsultasCompensacion(
			BOConsultasCompensacion bOConsultasCompensacion) {
		this.bOConsultasCompensacion = bOConsultasCompensacion;
	}

	/**
	 * Consulta corresponsales compensacion.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 * 
	 */
	@RequestMapping(value = "ConsultaCorresponsalesCompensacion.do")
	public ModelAndView consultaCorresponsalesCompensacion(
			HttpServletRequest req, HttpServletResponse res)  {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> lhsmParametros = new HashMap<String, Object>();
		RBeanCorresponsalesConsultaCompensacion rBeanCorresponsalesConsultaCompensacion = null;
		req.getSession().setAttribute("archivoTexto", null);
		String lstrCodErrorPage = null;
		String lstrMsgErrorPage = null;
		BeanConsultaBitacora beanConsulta = new BeanConsultaBitacora();
		beanConsulta.setEstatus("NIN");

		req.getSession().removeAttribute(TXT_LIST);
		req.getSession().removeAttribute("corresponsalCompensacion");

		try {
			rBeanCorresponsalesConsultaCompensacion = bOConsultasCompensacion
					.consultaCorresponsales(getArchitechBean());
			lstrCodErrorPage = rBeanCorresponsalesConsultaCompensacion
					.getCodError();
			lstrMsgErrorPage = rBeanCorresponsalesConsultaCompensacion
					.getMsgError();
			req.getSession().setAttribute(
					"listaCorresponsalesCompensacion",
					rBeanCorresponsalesConsultaCompensacion
							.getListaCorresponsales());
		} catch (BusinessException e) {
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			lhsmParametros.put(TXT_COD_ERROR, lstrCodErrorPage);
			lhsmParametros.put(TXT_MSG_ERROR, lstrMsgErrorPage);
		}

		debug("Codigo error :" + lstrCodErrorPage);
		debug("Mensaje error:" + lstrMsgErrorPage);

		return new ModelAndView(TXT_C_COMPE, lhsmParametros);
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "RealizaConsultaCompensacion.do")
	/**
	 * Realiza consulta compensacion.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	public ModelAndView realizaConsultaCompensacion(HttpServletRequest req,
			HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> lhsmParametros = new HashMap<String, Object>();
		final String lstrIdCorresponsal = req.getParameter("optCorresponsal");
		final String lstrAvanzarRetroceder = req.getParameter("opcAvanzarRetroceder");
		final List<BeanCorresponsal> listaCorresponsales = (List<BeanCorresponsal>) req
				.getSession().getAttribute("listaCorresponsalesCompensacion");
		req.getSession().setAttribute("archivoTexto", null);
		BeanCorresponsal beanCorresponsal = null;
		final String lstrFechaInferior = (String) req.getSession().getAttribute(
				"fechaInferior");
		final String lstrFechaSuperior = (String) req.getSession().getAttribute(
				"fechaSuperior");
		final BeanMonitoreoCredito beanMonitoreoCredito = new BeanMonitoreoCredito(
				Parametros.getParametroAplicacion("CORRESPONSALIA"));

		req.getSession().setAttribute(TXT_LIST, null);
		req.getSession().setAttribute("corresponsalCompensacion", null);

		debug("Id Corresponsal     :" + lstrIdCorresponsal);
		debug("Avanzar Retroceder  :" + lstrAvanzarRetroceder);
		debug("Lista Corresponsales:" + listaCorresponsales);

		req.setAttribute("reqCorresponsal", lstrIdCorresponsal);

		if (listaCorresponsales == null) {
			lhsmParametros.put(TXT_COD_AVISO, "DLE0001");
			lhsmParametros.put(TXT_MSG_AVISO,
					"Servicio No Dsiponible");
			return new ModelAndView(TXT_C_COMPE, lhsmParametros);
		}

		for (BeanCorresponsal bCorresponsalTemp : listaCorresponsales) {
			if (bCorresponsalTemp.getCodigoCorresponsal().equals(
					lstrIdCorresponsal)) {
				debug("Corresponsal encontrado en la lista de corresponsales");
				beanCorresponsal = bCorresponsalTemp;
				break;
			}
		}


		RBeanConsultaNoCompensacion rBeanConsultaNoCompensacion = null;
		String lstrCodErrorPage = "";
		String lstrMsgErrorPage = "";
		try {
			final RBeanMonitoreoCredito rBeanMonitoreoCredito = bOMonitoreoCredito
					.monitoreoCredito(getArchitechBean(), beanMonitoreoCredito);
			lstrCodErrorPage = rBeanMonitoreoCredito.getCodError();
			lstrMsgErrorPage = rBeanMonitoreoCredito.getMsgError();
			if ("MNCR0000".equals(lstrCodErrorPage)) {
				for (BeanRegistroMonitoreoCredito credito : rBeanMonitoreoCredito
						.getListaRegistrosMonitoreoCredito()) {
					if (credito.getCorresponsal().equals(
							beanCorresponsal.getCodigoCorresponsal())) {
						lhsmParametros.put("disponible", credito
								.getDisponibleCredito());
						lhsmParametros.put("autorizado", credito
								.getCreditoOtorgado());
						break;
					}
				}
			} else {
				lhsmParametros.put(TXT_COD_AVISO, lstrCodErrorPage);
				lhsmParametros.put(TXT_MSG_AVISO, lstrMsgErrorPage);
				return new ModelAndView(TXT_C_COMPE, lhsmParametros);
			}
			rBeanConsultaNoCompensacion = bOConsultasCompensacion
					.consultaNoCompensacion(getArchitechBean(),
							beanCorresponsal, lstrAvanzarRetroceder,
							lstrFechaInferior, lstrFechaSuperior);
			lstrCodErrorPage = rBeanConsultaNoCompensacion.getCodError();
			lstrMsgErrorPage = rBeanConsultaNoCompensacion.getMsgError();
			debug("Numero de registros:"
					+ rBeanConsultaNoCompensacion
							.getListaRegistrosConsultaNoCompensacion().size());
			if (rBeanConsultaNoCompensacion
					.getListaRegistrosConsultaNoCompensacion().size() == 0) {
				debug("Mostrar Avisos...");
				lhsmParametros.put(TXT_COD_AVISO, lstrCodErrorPage);
				lhsmParametros.put(TXT_MSG_AVISO, lstrMsgErrorPage);
			}
			req
					.getSession()
					.setAttribute(
							TXT_LIST,
							rBeanConsultaNoCompensacion
									.getListaRegistrosConsultaNoCompensacion()
									.size() == 0 ? null
									: rBeanConsultaNoCompensacion
											.getListaRegistrosConsultaNoCompensacion());
			req.getSession().setAttribute("corresponsalCompensacion",
					rBeanConsultaNoCompensacion.getDetalleCorresponsal());
			req.getSession().setAttribute("fechaInferior",
					rBeanConsultaNoCompensacion.getFechaInferior());
			req.getSession().setAttribute("fechaSuperior",
					rBeanConsultaNoCompensacion.getFechaSuperior());
		} catch (BusinessException e) {
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			lhsmParametros.put(TXT_COD_ERROR, lstrCodErrorPage);
			lhsmParametros.put(TXT_MSG_ERROR, lstrMsgErrorPage);
		}

		debug("Codigo error :" + lstrCodErrorPage);
		debug("Mensaje error:" + lstrMsgErrorPage);
		return new ModelAndView(TXT_C_COMPE, lhsmParametros);
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ExportarCompensacion.do")
	/**
	 * Exportar compensacion.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	public ModelAndView exportarCompensacion(HttpServletRequest req,
			HttpServletResponse res)  {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> map = new HashMap<String, Object>();
		final List<BeanRegistroConsultaNoCompensacion> listaRegistros = (List<BeanRegistroConsultaNoCompensacion>) req
				.getSession().getAttribute(TXT_LIST);

		if (listaRegistros == null
				|| (listaRegistros != null && listaRegistros.size() == 0)) {
			final HashMap<String, Object> lhsmParametros = new HashMap<String, Object>();
			lhsmParametros.put(TXT_COD_ERROR, "NOEXPORT");
			lhsmParametros.put(TXT_MSG_ERROR, "NO HAY REGISTROS PARA EXPORTAR");
			return new ModelAndView(TXT_C_COMPE, lhsmParametros);
		}
		final JRDataSource dataSource = new JRBeanArrayDataSource(listaRegistros
				.toArray());
		// pasamos la fecha al reporte
		final DateFormat Dformat = new  SimpleDateFormat("yyyy/MM/dd HH:mm:ss aa",
				new Locale("ES","MX")); 
		map.put("fechaReporte", Dformat.format(java.util.Calendar.getInstance().getTime())); 
		return new ModelAndView(
				new ExportaXLS(
						"consultanocompensadas",
						"/com/isban/corresponsalia/reportes/doctos/consultanocompensadas.jasper",
						dataSource), map);

	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "Autorizar.do")
	/**
	 * Autorizar.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	public ModelAndView autorizar(HttpServletRequest req,
			HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> lhsmParametros = new HashMap<String, Object>();
		final List<BeanRegistroConsultaNoCompensacion> listaRegistros = (List<BeanRegistroConsultaNoCompensacion>) req
				.getSession().getAttribute(TXT_LIST);
		final List<BeanCorresponsal> listaCorresponsales = (List<BeanCorresponsal>) req
				.getSession().getAttribute("listaCorresponsalesCompensacion");
		final String lstrCorresponsal = req.getParameter("optCorresponsal");
		String lstrCodErrorPage = null;
		String lstrMsgErrorPage = null;
		BeanCorresponsal beanCorresponsal = null;

		debug("Id Corresponsal:" + lstrCorresponsal);

		req.setAttribute("reqCorresponsal", lstrCorresponsal);

		if (lstrCorresponsal == null || "".equals(lstrCorresponsal)
				|| "--Seleccione un corresponsal".equals(lstrCorresponsal)) {
			lhsmParametros.put(TXT_COD_ERROR, "NO CORESPONSAL");
			lhsmParametros.put(TXT_MSG_ERROR,
					"NO SE HA SELECIONADO NINGUN CORRESPONSAL");
			return new ModelAndView(TXT_C_COMPE, lhsmParametros);

		} else if (listaCorresponsales == null
				|| listaCorresponsales.size() == 0) {
			lhsmParametros.put(TXT_COD_ERROR, "NO CORRESPONSALES");
			lhsmParametros.put(TXT_MSG_ERROR, "NO HAY CORRESPONSALES LISTADOS");
			return new ModelAndView(TXT_C_COMPE, lhsmParametros);
		} else if (listaRegistros == null || listaRegistros.size() == 0) {
			lhsmParametros.put(TXT_COD_ERROR, "NO REGISTROS");
			lhsmParametros.put(TXT_MSG_ERROR, "NO HAY OPERACIONES PARA AUTORIZAR");
			return new ModelAndView(TXT_C_COMPE, lhsmParametros);
		}

		for (BeanCorresponsal bCorresponsalTemp : listaCorresponsales) {
			if (bCorresponsalTemp.getCodigoCorresponsal().equals(
					lstrCorresponsal)) {
				debug("Corresponsal encontrado en la lista de corresponsales");
				beanCorresponsal = bCorresponsalTemp;
				break;
			}
		}

		if (beanCorresponsal == null) {
			lhsmParametros.put(TXT_COD_ERROR, "NO CORRESPONSAL");
			lhsmParametros.put(TXT_MSG_ERROR,
					"NO SE SELECCIONO UN CORRESPONSAL VALIDO");
			return new ModelAndView(TXT_C_COMPE, lhsmParametros);
		}

		try {
			RBeanAutorizaCompensacion rBeanAutorizaCompensacion = bOConsultasCompensacion
					.autorizaCompensacion(getArchitechBean(), beanCorresponsal);
			lstrCodErrorPage = rBeanAutorizaCompensacion.getCodError();
			lstrMsgErrorPage = rBeanAutorizaCompensacion.getMsgError();
			lhsmParametros.put(TXT_COD_AVISO, lstrCodErrorPage);
			lhsmParametros.put(TXT_MSG_AVISO, lstrMsgErrorPage);
		} catch (BusinessException e) {
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			lhsmParametros.put(TXT_COD_ERROR, lstrCodErrorPage);
			lhsmParametros.put(TXT_MSG_ERROR, lstrMsgErrorPage);
		}

		lhsmParametros
				.put("disponible", req.getParameter("txtSaldoDisponible"));
		lhsmParametros
				.put("autorizado", req.getParameter("txtSaldoAutorizado"));

		debug("Codigo error :" + lstrCodErrorPage);
		debug("Mensaje error:" + lstrMsgErrorPage);

		return new ModelAndView(TXT_C_COMPE, lhsmParametros);
	}
	
	/**
	 * Metodo obtener detalle
	 * @param httpServletRequest the httpServletRequest
	 * @param httpServletResponse the httpServletResponse
	 * @return ModelAndView the ModelAndView
	 */
	@RequestMapping(value = "ObtenerDetalle.do")
	public ModelAndView obtenerDetalle(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		
		setArchitechBean((ArchitechSessionBean)httpServletRequest.getSession().getAttribute(NEW_ARCH));
		HashMap<String, Object> hashMapObtenerDetalle = new HashMap<String, Object>();
		
		String resultado = "";
		String codigoCorresponsal = "";
		
		if (httpServletRequest.getParameter("optCorresponsal") == null) {
			codigoCorresponsal = "0";
		} else {
			codigoCorresponsal = httpServletRequest.getParameter("optCorresponsal");
		}
		
		String nombreArchivo = codigoCorresponsal + "_ope_por_comp.txt";
		
		hashMapObtenerDetalle.put(MSG_RESULTADO, null);
		hashMapObtenerDetalle.put(ERROR_ARCHIVO, null);
		
		debug("Codigo del corresponsal a consultar: " + codigoCorresponsal);
		
		final String rutaInterfaces = Parametros.getParametroAplicacion("RUTA_INTERFACES_REMOTA");
		final String hostDestino = Parametros.getParametroAplicacion("HOST_INTERFACES_REMOTA");
		final String usuario = Parametros.getParametroAplicacion("USR_INTERFACES");
		final String kidFile = Parametros.getParametroAplicacion("KID_FILE");
		
		try {
			resultado = traerArchivo(rutaInterfaces, hostDestino, usuario, kidFile, nombreArchivo);
			
			httpServletRequest.getSession().setAttribute("archivoTexto", resultado);
			httpServletRequest.getSession().setAttribute("codSucursal", codigoCorresponsal);
	        
	        if (!"".equals(resultado)) {
	        	
	        	hashMapObtenerDetalle.put(MSG_RESULTADO, "El archivo ha sido descargado correctamente.");
	        	hashMapObtenerDetalle.put("Respuesta", resultado);
	        	
	        	debug("El archivo ha sido descargado correctamente.");
	        	
	        } else {
	        	
	        	hashMapObtenerDetalle.put(MSG_RESULTADO, "El archivo no fue encontrado.");
	        	hashMapObtenerDetalle.put("respuesta", resultado);
	        	
	        	debug("El archivo no fue encontrado.");
	        }
		} catch (IOException e) {

			hashMapObtenerDetalle.put(ERROR_ARCHIVO, e.getMessage());
			hashMapObtenerDetalle.put(AVISO, ARCHIVO_TRANSF + REVISAR);
			
			debug("Error de tipo IOException: " + e);

		} catch (SftpException e) {

			hashMapObtenerDetalle.put(ERROR_ARCHIVO, e.getMessage());
			hashMapObtenerDetalle.put(AVISO, ARCHIVO_TRANSF + REVISAR);
			
			debug("Error de tipo SftpException: " + e);

		}
		
		return new ModelAndView(TXT_C_COMPE, hashMapObtenerDetalle);
	}

	/**
	 * Metodo traerArchivo
	 * @param rutaInterfaces the rutaInterfaces
	 * @param hostDestino the hostDestino
	 * @param usuario the usuario
	 * @param kidFile the kidFile
	 * @param nombreArchivo the nombreArchivo
	 * @return String the File from rutaInterfaces
	 * @throws SftpException the SftpException
	 * @throws IOException the IOException
	 */
	private String traerArchivo(String rutaInterfaces, String hostDestino, String usuario, String kidFile,
			String nombreArchivo) throws SftpException, IOException {
		
		File file = null;
		file = new File(kidFile);
		JSch jschCompensacion = null;
		ChannelSftp channelSftpCompensacion = new ChannelSftp();
		Session sessionCompensacion = null;
		jschCompensacion = new JSch();
		
		try {
			if (file.exists()) {
				final byte[] prvkey = readMyPrivateKeyFromFile(file);
				
				jschCompensacion.addIdentity(usuario, prvkey, null, new byte[0]);
			} else {
				debug("No se encontro la llave privada: " + kidFile);
			}
			
			sessionCompensacion = jschCompensacion.getSession(usuario, hostDestino, 22);
			final UserInfo userInfoLotes = new MyUserInfo();
			sessionCompensacion.setUserInfo(userInfoLotes);

			sessionCompensacion.connect();
			info("SFTP Sesión abierta.");
			
			sessionCompensacion.setConfig("PreferredAuthentications", "publickey");
			sessionCompensacion.setConfig("StrictHostKeyChecking", "no");
			
			channelSftpCompensacion = (ChannelSftp) sessionCompensacion.openChannel("sftp");
			
			channelSftpCompensacion.connect();
			
		} catch (JSchException jse) {
			channelSftpCompensacion.disconnect();
			debug("SFTP error: " + jse);
		}
		
		info("SFTP Canal abierto.");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		  BufferedOutputStream buff = new BufferedOutputStream(outputStream);
		  channelSftpCompensacion.cd(rutaInterfaces);
		  
		  try {
			  
			  channelSftpCompensacion.get(nombreArchivo, buff);
			
		} catch (SftpException e) {
			channelSftpCompensacion.disconnect();
			debug("SFTP no se encontro el archivo: " + e);
			return "";
		}
		  
		  channelSftpCompensacion.disconnect();
		info("SFTP Archivo Descargado con éxito.");
		return outputStream.toString("UTF-8");
	}

	/**
	 * readMyPrivateKeyFromFile the KidFile
	 * @param fileCompensacion the fileCompensacion
	 * @return bytesCompensacion the bytesCompensacion
	 * @throws IOException the IOException
	 */
	private byte[] readMyPrivateKeyFromFile(File fileCompensacion) throws IOException {
		
		final InputStream isCompensacion = new FileInputStream(fileCompensacion);

		// Obtener el tamaño del archivo
		final long length = fileCompensacion.length();

		// Se crea el arreglo en bytes para almacenar los datos
		final byte[] bytesCompensacion = new byte[(int) length];

		// Lectura de los bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytesCompensacion.length
				&& (numRead = isCompensacion.read(bytesCompensacion, offset, bytesCompensacion.length - offset)) >= 0) {
			offset += numRead;
		}

		// Asegurarse todos los bytes han sido leidos
		if (offset < bytesCompensacion.length) {
			throw new IOException("No se puede completar la lectura del archivo "
					+ fileCompensacion.getName());
		}

		// Se cierra el stream ingresado y se retornan los bytes
		isCompensacion.close();
		return bytesCompensacion;
	}
}
