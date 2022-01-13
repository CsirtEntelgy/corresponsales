package com.isban.corresponsalia.controllers.corresponsales;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.isban.corresponsalia.beans.comunes.BeanContacto;
import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaContactos;
import com.isban.corresponsalia.beans.corresponsales.BeanAltaContactos;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoAltaContacto;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoContactos;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.bo.corresponsales.BOCorresponsalesContactos;
import com.isban.corresponsalia.reportes.ExportaXLS;

import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

/**
 * The Class ControllerCorresponsalesContactos.
 */
@Controller
public class ControllerCorresponsalesContactos extends ArchitechEBE {

	/** The Constant NEW_ARCH. */
	private static final String NEW_ARCH = "NewArchitechSession";
	
	/** The Constant EMAIL_GEN. */
	private static final String EMAIL_GEN = "a@oxxo.com";
	
	/** The Constant ID_CORRESPONSAL. */
	private static final String ID_CORRESPONSAL = "idCorresponsal";
	
	/** The Constant LISTA_CONTACTOS. */
	private static final String LISTA_CONTACTOS = "listaContactos";
	
	/** The Constant CONSECUTIVO. */
	private static final String CONSECUTIVO = "consecutivo";
	
	/** The Constant TXT_CORRES. */
	private static final String TXT_CORRES = "corresponsal";
	
	
	/** The b o corresponsales contactos. */
	transient private BOCorresponsalesContactos bOCorresponsalesContactos;

	/**
	 * Sets the bO corresponsales contactos.
	 *
	 * @param bOCorresponsalesContactos the new bO corresponsales contactos
	 */
	public void setBOCorresponsalesContactos(
			BOCorresponsalesContactos bOCorresponsalesContactos) {
		this.bOCorresponsalesContactos = bOCorresponsalesContactos;
	}

	/**
	 * Consulta contacto.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value = "ConsultaContacto.do")
	public ModelAndView consultaContacto(HttpServletRequest req,
			HttpServletResponse res)  {
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();
		return consultaContacto(req, res, mapParametros, null, null);
	}

	/**
	 * Consulta contacto.
	 *
	 * @param req the req
	 * @param res the res
	 * @param mapParametros the map parametros
	 * @param oper the oper
	 * @param idCorr the id corr
	 * @return the model and view
	 */
	public ModelAndView consultaContacto(HttpServletRequest req,
			HttpServletResponse res, Map<String, Object> mapParametros,
			String oper, String idCorr) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		String operacion;
		String direccion = req.getParameter("direccion");
		final String paginaNum = req.getParameter("paginaNum") == null ? "" : req
				.getParameter("paginaNum").toString();
		if (direccion == null) {
			direccion = "";
		}

		if (oper == null) {
			operacion = req.getParameter("operacion");
		}
		else {
			operacion = oper;
		}
		if (operacion == null) {
			operacion = "inicial";
		}
		info("Operacion: " + operacion);
		final BeanMttoConsultaCorresponsal beanConsulta = new BeanMttoConsultaCorresponsal();
		beanConsulta.setCodigoCorresponsalia("14");
		beanConsulta.setTipoConsulta("L");
		beanConsulta.setIndicadorPaginacion("N");
		final BeanResultadoCorresponsalia salida = bOCorresponsalesContactos
		.consultaCorresponsales(beanConsulta, getArchitechBean());
		
		req.getSession().setAttribute("LISTACORR", salida.getRegistros());
		
		debug("Codigo de Error: <" + salida.getCodError() + ">");
		if ("0".equals(salida.getCodError())) {
			mapParametros.put("lista", salida.getRegistros());
			debug("Cantidad de corresponsales: " + salida.getRegistros().size());
		}
		if ("consultarContactos".equals(operacion)) {
			debug("Ejecuta Consulta Contactos");
			String idCorresponsal = req.getParameter(TXT_CORRES);
			if (idCorresponsal == null || "".equals(idCorresponsal)) {
				idCorresponsal = req.getParameter(ID_CORRESPONSAL);
			}
			if (idCorr != null) {
				idCorresponsal = idCorr;
				req.getSession().setAttribute(TXT_CORRES, idCorresponsal);
			}
			debug("Corresponsal a buscar: " + idCorresponsal);
			mapParametros.put("corresponsalBuscar", idCorresponsal);
			for (int i = 0; i < salida.getRegistros().size(); i++) {
				if (salida.getRegistros().get(i).getCodigoCorresponsal()
						.equals(idCorresponsal)) {
					req.getSession().setAttribute(
							"nombre",salida.getRegistros().get(i)
							.getNombreCorresponsal());
					req.getSession().setAttribute(ID_CORRESPONSAL,idCorresponsal);
					debug("IDCorr: " + idCorresponsal);
					debug("Nombre: " + salida.getRegistros().get(i)
							.getNombreCorresponsal());
					i = salida.getRegistros().size();
				}
			}
			final BeanConsultaContactos beanConsultaCto = new BeanConsultaContactos();
			beanConsultaCto.setCanal("14");
			beanConsultaCto.setIdCorresponsal(idCorresponsal);
			beanConsultaCto.setZonaUbicacionContacto("00000"); 
			if ("".equals(direccion)) {
				beanConsultaCto.setIndPaginacion("N");
			}
			else {
				final BeanResultadoContactos anterior = (BeanResultadoContactos) req
				.getSession().getAttribute("resultado");
				beanConsultaCto.setIndPaginacion("P");
				beanConsultaCto.setLimInferior(anterior.getReferenciaAvanzar());
				beanConsultaCto.setLimSuperior(anterior
						.getReferenciaRetroceder());
			}
			beanConsultaCto.setIndDireccion(direccion);
			beanConsultaCto.setTipoConsulta("L");
			if ("1".equalsIgnoreCase(paginaNum.trim())) {
				beanConsultaCto.setIndPaginacion("N");
				beanConsultaCto.setLimInferior("");
				beanConsultaCto.setLimSuperior("");
			}
			final BeanResultadoContactos contactos = bOCorresponsalesContactos
			.consultaContactos(beanConsultaCto, getArchitechBean());
			if (contactos.getCodError().indexOf("DLA0004") >= 0
					|| "".equalsIgnoreCase(contactos.getCodError())) {
				debug("Total de Contactos: " + contactos.getRegistros().size());
				mapParametros.put(LISTA_CONTACTOS, contactos.getRegistros());
				mapParametros.put("resultado", contactos);
				mapParametros.put("hayAtras", contactos.getMasAtras());
				mapParametros.put("hayAdelante", contactos.getMasAdelante());
				mapParametros.put("paginaNum", paginaNum);
				req.getSession().setAttribute("resultado", contactos);
				req.getSession().setAttribute(LISTA_CONTACTOS,
						contactos.getRegistros());
			}
			else {
				mapParametros.put("codError", contactos.getCodError());
				mapParametros.put("msgError", contactos.getMsgError());
				req.getSession().removeAttribute(LISTA_CONTACTOS);
			}
		} else {
			req.getSession().removeAttribute(LISTA_CONTACTOS);
		}
		return new ModelAndView("ConsultaContacto", mapParametros);
	}

	/**
	 * Alta contacto.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value = "altContacto.do")
	public ModelAndView altaContacto(HttpServletRequest req, HttpServletResponse res)  {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();
		String operacion = req.getParameter("operacion");
		final BeanAltaContactos beanAlta = new BeanAltaContactos();

		if (operacion == null) {
			operacion = "inicial";
		}
		if ("altaContacto".equals(operacion)
				|| "modificaContacto".equals(operacion)
				|| "borraContacto".equals(operacion)) {
			beanAlta.setEntidad("0014");
			beanAlta.setCanal("14");
			if ("altaContacto".equals(operacion)) {
				beanAlta.setIdCorresponsal(req.getParameter(ID_CORRESPONSAL));
				beanAlta.setTipoOper("A");
				beanAlta.setEstatus("001"); // activo
			}
			if ("modificaContacto".equals(operacion)) {
				beanAlta.setIdCorresponsal(req.getParameter(ID_CORRESPONSAL));
				beanAlta.setTipoOper("M");
				beanAlta.setSeqId(req.getParameter(CONSECUTIVO));
			}
			if ("borraContacto".equals(operacion)) {
				beanAlta.setIdCorresponsal(req.getParameter(TXT_CORRES));
				beanAlta.setTipoOper("B");
				beanAlta.setEstatus("004"); // baja
				beanAlta.setSeqId(req.getParameter(CONSECUTIVO));
				beanAlta.setZonaUbicacion(req.getParameter("zonaUbic"));
			}

			debug("IDCorresponsal: " + beanAlta.getIdCorresponsal());
			final StringBuffer listaErrores = new StringBuffer();
			if (!"borraContacto".equals(operacion)) {
				beanAlta.setNombre(req.getParameter("txtNombre"));
				beanAlta.setPuesto(req.getParameter("txtPuesto"));
				beanAlta.setArea(req.getParameter("txtArea"));
				beanAlta.setTelOficina(req.getParameter("txtTelOf"));
				beanAlta.setTelMovil(req.getParameter("txtTelMov"));
//				beanAlta.setEmailPrin(req.getParameter("txtEmail"));
//				Se agrega este correo temporalmente para mitigar el problema de la longitud del campo en 390
				beanAlta.setEmailPrin(EMAIL_GEN);
				beanAlta.setTelFax(req.getParameter("txtTelFax"));
//				beanAlta.setEmailAlt(req.getParameter("txtEmailAlt"));
//				Se agrega este correo temporalmente para mitigar el problema de la longitud del campo en 390
				beanAlta.setEmailAlt(EMAIL_GEN);
				beanAlta.setCalle(req.getParameter("txtCalle"));
				beanAlta.setNumExt(req.getParameter("txtNumExt"));
				beanAlta.setNumInt(req.getParameter("txtNumInt"));
				beanAlta.setColonia(req.getParameter("txtColonia"));
				beanAlta.setDelegMcp(req.getParameter("txtDelegMunc"));
				beanAlta.setCiudad(req.getParameter("txtCiudad"));
				beanAlta.setEntidadFed(req.getParameter("txtEstado"));
				beanAlta.setCodPostal(req.getParameter("txtCodPostal"));
				if (beanAlta.getCodPostal().trim().length() == 0) {
					listaErrores
					.append("El campo C&oacute;digo Postal es obligatorio<br>");
				}
				else {
					try {
						Integer.parseInt(beanAlta.getCodPostal());
					} catch (NumberFormatException e) {
						listaErrores
						.append("El campo  C&oacute;digo Postal debe ser num&eacute;rico<br>");
					}
				}
				beanAlta.setZonaUbicacion(req.getParameter("txtZona"));
			}

			debug("Longitud Errores: " + listaErrores.length());
			debug("Errores: " + listaErrores.toString());
			debug("operacion: " + operacion);
			if (listaErrores.length() > 0) {
				mapParametros.put("mensajeRespuesta", listaErrores);
				mapParametros.put("beanContacto", beanAlta);

				if ("altaContacto".equals(operacion)) {
					return new ModelAndView("AltaContacto", mapParametros);
				}
				else {
					return detalleContacto(req, res, mapParametros, "modifica",
							req.getParameter("txtZona"), req
							.getParameter(CONSECUTIVO));
				}
			}
			else {
				final BeanResultadoAltaContacto resultado = bOCorresponsalesContactos
				.altaContacto(beanAlta, getArchitechBean());
				debug("Resultado: " + resultado.getMsgError());
				mapParametros.put("mensajeRespuesta", resultado.getMsgError());
				return consultaContacto(req, res, mapParametros,
						"consultarContactos", beanAlta.getIdCorresponsal());
			}
		}
		return new ModelAndView("AltaContacto");
	}

	/**
	 * Detalle contacto.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value = "detContacto.do")
	public ModelAndView detalleContacto(HttpServletRequest req,
			HttpServletResponse res){
		setArchitechBean((ArchitechSessionBean)req.getSession().
				getAttribute(NEW_ARCH));
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();

		return detalleContacto(req, res, mapParametros, null, null, null);
	}

	/**
	 * Detalle contacto.
	 *
	 * @param req the req
	 * @param res the res
	 * @param mapParametros the map parametros
	 * @param oper the oper
	 * @param zu the zu
	 * @param cn the cn
	 * @return the model and view
	 */
	public ModelAndView detalleContacto(HttpServletRequest req,
			HttpServletResponse res, Map<String, Object> mapParametros,
			String oper, String zu, String cn) {
		String operacion = req.getParameter("operacion");
		final String funcion = "DetalleContacto";
		if (oper != null) {
			operacion = oper;
		}
		if (operacion == null) {
			operacion = "inicial";
		}
		if ("detalle".equals(operacion) || "modifica".equals(operacion)) {
			final String idCorresponsal = (String) req.getSession().
				getAttribute(ID_CORRESPONSAL);
			String zonaUbic = (String) req.getParameter("zonaUbic");
			if (zu != null) {
				zonaUbic = zu;
			}
			String consecutivo = (String) req.getParameter(CONSECUTIVO);
			if (cn != null) {
				consecutivo = cn;
			}
			debug("IDCorresponsal: " + idCorresponsal);
			if ("detalle".equals(operacion)) {
				mapParametros.put("titulo", "Detalle de Contacto");
				mapParametros.put("disabled", "disabled=\"disabled\"");
			}
			else if ("modifica".equals(operacion)) {
				mapParametros.put("titulo", "Modificaci&oacute;n de Contacto");
				mapParametros.put("disabled", "");
				mapParametros.put("modificar", "on");
			}

			final BeanConsultaContactos beanConsultaCto = new BeanConsultaContactos();
			beanConsultaCto.setCanal("14");
			beanConsultaCto.setIdCorresponsal(idCorresponsal);
			beanConsultaCto.setZonaUbicacionContacto(zonaUbic);
			beanConsultaCto.setSeqIdContacto(consecutivo);
			beanConsultaCto.setIndPaginacion("N");
			beanConsultaCto.setTipoConsulta("D");
			final BeanResultadoContactos contactos = bOCorresponsalesContactos
			.consultaContactos(beanConsultaCto, getArchitechBean());
			if (contactos.getCodError().indexOf("ER") < 0) {
				for (BeanContacto listContac : contactos.getRegistros()) {
					listContac.setNombre(listContac.getNombre().trim());
					listContac.setArea(listContac.getArea().trim());
					listContac.setCalle(listContac.getCalle().trim());
					listContac.setCiudad(listContac.getCiudad().trim());
					listContac.setCodPostal(listContac.getCodPostal().trim());
					listContac.setColonia(listContac.getColonia().trim());
					listContac.setDelegMunic(listContac.getDelegMunic().trim());
					listContac.setEmail1(listContac.getEmail1().trim());
					listContac.setEmail2(listContac.getEmail2().trim());
					listContac.setEntFede(listContac.getEntFede().trim());
					listContac.setNumExt(listContac.getNumExt().trim());
					listContac.setNumInt(listContac.getNumInt().trim());
					listContac.setPuesto(listContac.getPuesto().trim());
					listContac.setTelefono(listContac.getTelefono().trim());
					listContac.setTelMovil(listContac.getTelMovil().trim());
					listContac.setTelFax(listContac.getTelFax().trim());
					listContac.setZonaUbic(listContac.getZonaUbic().trim());
				}
				debug("Total de Contactos: " + contactos.getRegistros().size());
				mapParametros.put("contacto", contactos.getRegistros().get(0));
				mapParametros.put("nombre", req.getSession().getAttribute(
				"nombre"));
			}
			else {
				return consultaContacto(req, res);
			}
		}
		return new ModelAndView(funcion, mapParametros);
	}
	
	/**
	 * Exportare contactos.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ExportarContactos.do")
	public ModelAndView exportareContactos(HttpServletRequest req,
			HttpServletResponse res)  {
		setArchitechBean((ArchitechSessionBean)req.getSession().
				getAttribute(NEW_ARCH));
		final HashMap<String, Object> map = new HashMap<String, Object>();
		final List<BeanCorresponsal>  listaCorresponsales  = (List<BeanCorresponsal>)
			req.getSession().getAttribute("LISTACORR");
		final ArrayList<BeanContacto> listaRegistros = (ArrayList<BeanContacto>) req.getSession().
			getAttribute(LISTA_CONTACTOS);
		String idCorresponsal = (String) req.getSession().getAttribute(TXT_CORRES);
		String nombreCorr = null;
				
		if (idCorresponsal == null || "".equals(idCorresponsal)) {
			idCorresponsal = (String) req.getSession().getAttribute(ID_CORRESPONSAL);
		}
		info("CORRESPONSAL::" + idCorresponsal);
		for (BeanCorresponsal beanCorresponsal : listaCorresponsales) {
			if (beanCorresponsal.getCodigoCorresponsal().equals(idCorresponsal)) {
				info("NombreCORR: " + beanCorresponsal.getNombreCorresponsal());
				nombreCorr = beanCorresponsal.getNombreCorresponsal();
			}
		}
		
		if (listaRegistros == null
				|| (listaRegistros != null && listaRegistros.isEmpty())) {
			final HashMap<String, Object> lhsmParametros = new HashMap<String, Object>();
			lhsmParametros.put("codError", "NOEXPORT");
			lhsmParametros.put("msgError", "NO HAY REGISTROS PARA EXPORTAR");
			return consultaContacto(req, res, lhsmParametros, null, null);
		}

		final JRDataSource dataSource = new JRBeanArrayDataSource(listaRegistros
				.toArray());
		final DateFormat Dformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss aa", new Locale("ES","MX"));
		map.put("fechaReporte", Dformat.format(java.util.Calendar.getInstance().getTime()));
		map.put("nombreCorr", nombreCorr);

		return new ModelAndView(new ExportaXLS("Contactos",
				"/com/isban/corresponsalia/reportes/doctos/contactos.jasper",
				dataSource), map);
	}
}