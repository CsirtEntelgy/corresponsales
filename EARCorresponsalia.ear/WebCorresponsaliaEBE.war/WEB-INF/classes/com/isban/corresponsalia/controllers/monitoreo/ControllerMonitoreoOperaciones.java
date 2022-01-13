package com.isban.corresponsalia.controllers.monitoreo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.isban.corresponsalia.beans.comunes.BeanOperacion;
import com.isban.corresponsalia.beans.comunes.BeanOperacionCatalogo;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.beans.monitoreo.BeanConsultaMonitoreoOperaciones;
import com.isban.corresponsalia.beans.monitoreo.BeanMonitoreoOperaciones;
import com.isban.corresponsalia.beans.monitoreo.BeanResultadoMonitoreoOperaciones;
import com.isban.corresponsalia.beans.monitoreo.RBeanOperacionesSucursalesMonitoreoOperaciones;
import com.isban.corresponsalia.bo.monitoreo.BOMonitoreoOperaciones;
import com.isban.corresponsalia.comunes.Parametros;
import com.isban.corresponsalia.reportes.ExportaXLS;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;

/**
 * The Class ControllerMonitoreoOperaciones.
 */
@Controller
public class ControllerMonitoreoOperaciones extends ArchitechEBE {
	
	/** The Constant NEW_ARCH. */
	private static final String NEW_ARCH = "NewArchitechSession";
	
	/** The Constant HORA_INICIO. */
	private static final String HORA_INICIO = "08:00";	
	
	/** The Constant HORA_FIN. */
	private static final String HORA_FIN = "20:00";		
	
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
	private static final String CONSULTA_MONITOREO = "ConsultaMonitoreoOperaciones";
	
	/** The Constant OPT_CORRESPO. */
	private static final String OPT_CORRESPO = "optCorresponsal";
	
	/** The Constant OPT_SUCURSAL. */
	private static final String OPT_SUCURSAL = "optSucursal" ;
	
	/** The Constant OPT_OPERACIONES. */
	private static final String OPT_OPERACIONES = "optOperacion" ;
	
	/** The Constant TXT_HORA_INI. */
	private static final String TXT_HORA_INI = "txtHoraInicio";
	
	/** The Constant TXT_HORA_FIN. */
	private static final String TXT_HORA_FIN = "txtHoraFin";
	
	/** The Constant TXT_IMP_MIN. */
	private static final String TXT_IMP_MIN = "txtImporteMinimo";
	
	/** The Constant TXT_IMP_MAX. */
	private static final String TXT_IMP_MAX = "txtImporteMaximo" ;
	
	/** The Constant PAGINA. */
	private static final String PAGINA = "pagina";
	 
	/** The b o monitoreo operaciones. */
	private transient BOMonitoreoOperaciones bOMonitoreoOperaciones;

	/**
	 * Sets the bO monitoreo operaciones.
	 *
	 * @param bOMonitoreoOperaciones the new bO monitoreo operaciones
	 */
	public void setBOMonitoreoOperaciones(
			BOMonitoreoOperaciones bOMonitoreoOperaciones) {
		this.bOMonitoreoOperaciones = bOMonitoreoOperaciones;
	}

	/**
	 * Monitoreo operaciones.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 * @throws BusinessException the business exception
	 */
	@RequestMapping(value = "MonitoreoOperaciones.do")
	public ModelAndView monitoreoOperaciones(HttpServletRequest req,
			HttpServletResponse res) throws BusinessException {
		info("Inicia Muestra Consulta Monitoreo Operaciones");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();
		final BeanMttoConsultaCorresponsal beanConsulta = new BeanMttoConsultaCorresponsal();
		final BeanConsultaMonitoreoOperaciones beanConsultaMonitoreoOperaciones =
			new BeanConsultaMonitoreoOperaciones();
		/*
			req.getSession().getAttribute(BEAN_CONSULTA) == null ? new BeanConsultaMonitoreoOperaciones()
		: (BeanConsultaMonitoreoOperaciones) req.getSession()
				.getAttribute(BEAN_CONSULTA);
		*/

		req.getSession().removeAttribute("beanResultadoCorresponsalia");
		req.getSession().removeAttribute(
				LIST_CORRESPO);
		req.getSession()
				.removeAttribute("listaOperacionesMonitoreoOperaciones");
		req.getSession().removeAttribute("listaSucursalesMonitoreoOperaciones");
		req.getSession().removeAttribute(LIST_REG_CORRESPO);
		req.getSession().removeAttribute(BEAN_CONSULTA);
		req.getSession().removeAttribute("disabled");

		beanConsulta.setCodigoCorresponsalia(Parametros
				.getParametroAplicacion("CORRESPONSALIA"));
		beanConsulta.setIndicadorPaginacion("N");
		beanConsulta.setTipoConsulta("L");
		mapParametros.put(CLASE, "class='CamposCompletar'");
		final BeanResultadoCorresponsalia beanResultadoCorresponsalia = bOMonitoreoOperaciones
				.consultaCorresponsales(beanConsulta, getArchitechBean());
		if (beanResultadoCorresponsalia.getRegistros().size() > 0) {
			req.getSession().setAttribute(
					LIST_CORRESPO,
					beanResultadoCorresponsalia.getRegistros());
		} else {
			mapParametros.put(COD_ERROR, beanResultadoCorresponsalia
					.getCodError());
			mapParametros.put(MSG_ERROR, beanResultadoCorresponsalia
					.getMsgError());
			req.getSession().setAttribute("listaCorresponsales",
					new ArrayList<BeanCorresponsal>());
		}
		

		beanConsultaMonitoreoOperaciones.setHoraInicioFront(HORA_INICIO);
		beanConsultaMonitoreoOperaciones.setHoraFinFront(HORA_FIN);
		req.getSession().setAttribute(BEAN_CONSULTA,
				beanConsultaMonitoreoOperaciones);

		return new ModelAndView(CONSULTA_MONITOREO, mapParametros);
	}

	/**
	 * Monitoreo operaciones consulta sucursales operaciones.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "MonitoreoOperacionesConsultaSucursalesOperaciones.do")
	public ModelAndView monitoreoOperacionesConsultaSucursalesOperaciones(
			HttpServletRequest req, HttpServletResponse res)  {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> lhsmParametros = new HashMap<String, Object>();
		final List<BeanCorresponsal> listaCorresponsales = (List<BeanCorresponsal>) req
				.getSession().getAttribute(
						LIST_CORRESPO);
		final List<BeanOperacionCatalogo> lstOper = new ArrayList<BeanOperacionCatalogo>();
		List<BeanOperacion> lstOperCorres = null;
		final String lstrIdCorresponsal = req.getParameter(OPT_CORRESPO) == null ? ""
				: req.getParameter(OPT_CORRESPO);
		final String lstrIdSucursal = req.getParameter(OPT_SUCURSAL) == null ? ""
				: req.getParameter(OPT_SUCURSAL);
		final String lstrIdOperacion = req.getParameter(OPT_OPERACIONES) == null ? ""
				: req.getParameter(OPT_OPERACIONES);
		final String lstrHoraInicio = req.getParameter(TXT_HORA_INI) == null ? ""
				: req.getParameter(TXT_HORA_INI);
		final String lstrHoraFin = req.getParameter(TXT_HORA_FIN) == null ? "" : req
				.getParameter(TXT_HORA_FIN);
		final String lstrImporteMaximo = req.getParameter(TXT_IMP_MIN) == null ? ""
				: req.getParameter(TXT_IMP_MIN);
		final String lstrImporteMinimo = req.getParameter(TXT_IMP_MAX) == null ? ""
				: req.getParameter(TXT_IMP_MAX);
		final BeanConsultaMonitoreoOperaciones beanConsultaMonitoreoOperaciones = req
				.getSession().getAttribute(BEAN_CONSULTA) == null ? new BeanConsultaMonitoreoOperaciones()
				: (BeanConsultaMonitoreoOperaciones) req.getSession()
						.getAttribute(BEAN_CONSULTA);

		debug("Id Corresponsal     :" + lstrIdCorresponsal);
		debug("Lista Corresponsales:" + listaCorresponsales);

		beanConsultaMonitoreoOperaciones
				.setIdentificacionCorresponsal(lstrIdCorresponsal);
		beanConsultaMonitoreoOperaciones
				.setClaveReferenteTipoOperacion(lstrIdOperacion);
		beanConsultaMonitoreoOperaciones
				.setCodigoIdentificacionSucursal(lstrIdSucursal);
		beanConsultaMonitoreoOperaciones.setHoraInicio(lstrHoraInicio);
		beanConsultaMonitoreoOperaciones.setHoraFin(lstrHoraFin);
		beanConsultaMonitoreoOperaciones.setImporteInicial(lstrImporteMinimo);
		beanConsultaMonitoreoOperaciones.setImporteFinal(lstrImporteMaximo);

		req.getSession().setAttribute(BEAN_CONSULTA,
				beanConsultaMonitoreoOperaciones);

		lhsmParametros.put(CLASE, "class=\"CamposCompletar\"");

		if (listaCorresponsales == null) {
			lhsmParametros.put(COD_ERROR, "DLE001");
			lhsmParametros.put(MSG_ERROR, "Servicio No Disponible");
			return new ModelAndView(CONSULTA_MONITOREO,
					lhsmParametros);
		}

		for (BeanCorresponsal bCorresponsalTemp : listaCorresponsales) {
			if (bCorresponsalTemp.getCodigoCorresponsal().equals(
					lstrIdCorresponsal)) {
				debug("Corresponsal encontrado en la lista de corresponsales");
//				beanCorresponsal = bCorresponsalTemp;
				break;
			}
		}

		RBeanOperacionesSucursalesMonitoreoOperaciones rBeanOperacionesSucursalesMonitoreoOperaciones = null;
		String lstrCodErrorPage = "";
		String lstrMsgErrorPage = "";
		try {
			rBeanOperacionesSucursalesMonitoreoOperaciones = bOMonitoreoOperaciones
					.consultaOperacionesSucursales(
							beanConsultaMonitoreoOperaciones,
							getArchitechBean());
			lstrCodErrorPage = rBeanOperacionesSucursalesMonitoreoOperaciones
					.getCodError();
			lstrMsgErrorPage = rBeanOperacionesSucursalesMonitoreoOperaciones
					.getMsgError();
			lstOperCorres = (rBeanOperacionesSucursalesMonitoreoOperaciones
					.getListaOperacionesCorres());
			if (lstOperCorres != null) {
				for (BeanOperacion oper : lstOperCorres) {
					final BeanOperacionCatalogo operCat = new BeanOperacionCatalogo();
					operCat.setClaveTransaccionAsociada(oper
							.getCveTransaccion());
					operCat.setConsecutivoOperacionNivelCanal(oper
							.getCodigoOperacion());
					operCat.setDescripcionCorta(oper.getDesCorta());
					lstOper.add(operCat);
				}
				req.getSession().setAttribute(
						"listaOperacionesMonitoreoOperaciones", lstOper);
				req.getSession().setAttribute(
						"listaSucursalesMonitoreoOperaciones",
						rBeanOperacionesSucursalesMonitoreoOperaciones
								.getListaSucursales());

			} else {
				lhsmParametros.put(COD_ERROR, "ERR0002");
				lhsmParametros.put(MSG_ERROR,
						"No existen operaciones asignadas");
			}

		} catch (BusinessException e) {
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			lhsmParametros.put(COD_ERROR, lstrCodErrorPage);
			lhsmParametros.put(MSG_ERROR, lstrMsgErrorPage);
		}
		debug("Codigo error :" + lstrCodErrorPage);
		debug("Mensaje error:" + lstrMsgErrorPage);

		return new ModelAndView(CONSULTA_MONITOREO, lhsmParametros);
	}

	/**
	 * Realiza consulta monitoreo operaciones.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "RealizaConsultaMonitoreoOperaciones.do")
	public ModelAndView realizaConsultaMonitoreoOperaciones(
			HttpServletRequest req, HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> lhsmParametros = new HashMap<String, Object>();
		final List<BeanCorresponsal> listaCorresponsales = (List<BeanCorresponsal>) req
				.getSession().getAttribute(
						LIST_CORRESPO);
		//BeanCorresponsal beanCorresponsal = null;
		final String lstrIdCorresponsal = req.getParameter(OPT_CORRESPO) == null ? ""
				: req.getParameter(OPT_CORRESPO);
		final String lstrIdSucursal = req.getParameter(OPT_SUCURSAL) == null ? ""
				: req.getParameter(OPT_SUCURSAL);
		final String lstrIdOperacion = req.getParameter(OPT_OPERACIONES) == null ? ""
				: req.getParameter(OPT_OPERACIONES);
		final String lstrHoraInicio = req.getParameter(TXT_HORA_INI) == null ? ""
				: req.getParameter(TXT_HORA_INI);
		final String lstrHoraFin = req.getParameter(TXT_HORA_FIN) == null ? "" : req
				.getParameter(TXT_HORA_FIN);
		final String lstrImporteMaximo = req.getParameter(TXT_IMP_MAX) == null ? ""
				: req.getParameter(TXT_IMP_MAX);
		final String lstrImporteMinimo = req.getParameter(TXT_IMP_MIN) == null ? ""
				: req.getParameter(TXT_IMP_MIN);
		final String lstrAdelanteAtras = req.getParameter("opcAvanzarRetroceder") == null ? ""
				: req.getParameter("opcAvanzarRetroceder");
		int pagina = req.getParameter(PAGINA) == null
				|| "".equals(req.getParameter(PAGINA)) ? 0 : Integer
				.parseInt(req.getParameter(PAGINA));
		final BeanConsultaMonitoreoOperaciones beanConsultaMonitoreoOperaciones = req
				.getSession().getAttribute(BEAN_CONSULTA) == null ? new BeanConsultaMonitoreoOperaciones()
				: (BeanConsultaMonitoreoOperaciones) req.getSession()
						.getAttribute(BEAN_CONSULTA);
		double totalOperado = 0;

		debug("Id Corresponsal     :" + lstrIdCorresponsal);
		debug("Lista Corresponsales:" + listaCorresponsales);

		beanConsultaMonitoreoOperaciones
				.setIdentificacionCorresponsal(lstrIdCorresponsal);
		beanConsultaMonitoreoOperaciones
				.setCodigoIdentificacionSucursal(lstrIdSucursal);
		beanConsultaMonitoreoOperaciones.setHoraInicioFront(lstrHoraInicio);
		beanConsultaMonitoreoOperaciones.setHoraFinFront(lstrHoraFin);
		beanConsultaMonitoreoOperaciones
				.setClaveReferenteTipoOperacion(lstrIdOperacion);
		beanConsultaMonitoreoOperaciones
				.setImporteInicialFront(lstrImporteMinimo);
		beanConsultaMonitoreoOperaciones
				.setImporteFinalFront(lstrImporteMaximo);
		beanConsultaMonitoreoOperaciones
				.setCodigoIdentificacionSucursalIndicadorPag("");
		beanConsultaMonitoreoOperaciones
				.setClaveReferenteTipoOperacionIndicadorPag("");
		beanConsultaMonitoreoOperaciones.setHora("");

		beanConsultaMonitoreoOperaciones
				.setIndicadorPaginacionAvanzaRetrocede("B"
						.equals(lstrAdelanteAtras.trim())
						&& (pagina <= 1) ? "" : lstrAdelanteAtras.trim());
		if (!"".equals(beanConsultaMonitoreoOperaciones
				.getIndicadorPaginacionAvanzaRetrocede())) {
			beanConsultaMonitoreoOperaciones.setIndicadorPaginacion("P");
			if ("B".equals(beanConsultaMonitoreoOperaciones
					.getIndicadorPaginacionAvanzaRetrocede())) {
				pagina--;
				beanConsultaMonitoreoOperaciones
						.setCodigoIdentificacionSucursalIndicadorPag(req
								.getParameter("referenciaRetroceder"));
				beanConsultaMonitoreoOperaciones
						.setClaveReferenteTipoOperacionIndicadorPag(req
								.getParameter("operacionRetroceder"));
				beanConsultaMonitoreoOperaciones.setHora(req
						.getParameter("horaRetroceder"));
			} else {
				pagina++;
				beanConsultaMonitoreoOperaciones
						.setCodigoIdentificacionSucursalIndicadorPag(req
								.getParameter("referenciaAvanzar"));
				beanConsultaMonitoreoOperaciones
						.setClaveReferenteTipoOperacionIndicadorPag(req
								.getParameter("operacionAvanzar"));
				beanConsultaMonitoreoOperaciones.setHora(req
						.getParameter("horaAvanzar"));
			}

		}

		req.getSession().setAttribute(BEAN_CONSULTA,
				beanConsultaMonitoreoOperaciones);
		req.getSession().removeAttribute(LIST_REG_CORRESPO);
		req.getSession().removeAttribute("paginar");
		req.getSession().removeAttribute("totalOperado");
		lhsmParametros.put(CLASE, "class=\"CamposCompletar\"");
			for (BeanCorresponsal bCorresponsalTemp : listaCorresponsales) {
				if (bCorresponsalTemp.getCodigoCorresponsal().equals(
						lstrIdCorresponsal)) {
					debug("Corresponsal encontrado en la lista de corresponsales");
//					beanCorresponsal = bCorresponsalTemp;
					break;
				}
			}
		/*if (beanCorresponsal == null) {
			lhsmParametros.put(COD_ERROR, "Cores9999");
			lhsmParametros.put(MSG_ERROR, "Corresponsal valido");
			return new ModelAndView(CONSULTA_MONITOREO,
					lhsmParametros);
		}*/

		BeanResultadoMonitoreoOperaciones beanResultadoMonitoreoOperaciones = null;
		String lstrCodErrorPage = "";
		String lstrMsgErrorPage = "";
		try {
			beanResultadoMonitoreoOperaciones = bOMonitoreoOperaciones
					.consultaMonitoreoOpearciones(
							beanConsultaMonitoreoOperaciones,
							getArchitechBean());
			lstrCodErrorPage = beanResultadoMonitoreoOperaciones.getCodError();
			lstrMsgErrorPage = beanResultadoMonitoreoOperaciones.getMsgError();

			if (beanResultadoMonitoreoOperaciones.getRegistros().size() > 0) {
				debug("Codigo de Aviso      :"
						+ beanResultadoMonitoreoOperaciones.getCodError());
				debug("Mensaje de Aviso     :"
						+ beanResultadoMonitoreoOperaciones.getMsgError());
				debug("Registros a Mostrar  :"
						+ beanResultadoMonitoreoOperaciones.getRegistros()
								.size());
				debug("Referencia Avanzar   :"
						+ beanResultadoMonitoreoOperaciones
								.getReferenciaAvanzar());
				debug("Referencia Retroceder:"
						+ beanResultadoMonitoreoOperaciones
								.getReferenciaRetroceder());

				req.getSession().setAttribute("paginar",
						"DLA0005".equals(lstrCodErrorPage) ? "SI" : null);
				req.getSession().setAttribute(
						LIST_REG_CORRESPO,
						beanResultadoMonitoreoOperaciones.getRegistros());
				lhsmParametros.put(CLASE, "class=\"Campos_Des\"");
				lhsmParametros.put("disabled", "disabled=\"disabled\"");
				for (BeanMonitoreoOperaciones operSuma : beanResultadoMonitoreoOperaciones
						.getRegistros()) {
					totalOperado = totalOperado
							+ Double.parseDouble(operSuma
									.getImporteTotalOperacionesFront().trim());
				}
				req.getSession().setAttribute("totalOperado", totalOperado);
				beanConsultaMonitoreoOperaciones
						.setReferenciaAvanzar(beanResultadoMonitoreoOperaciones
								.getReferenciaAvanzar());
				beanConsultaMonitoreoOperaciones
						.setReferenciaRetroceder(beanResultadoMonitoreoOperaciones
								.getReferenciaRetroceder());
				req.getSession().setAttribute(
						BEAN_CONSULTA,
						beanConsultaMonitoreoOperaciones);
				lhsmParametros.put("referenciaAvanzar",
						beanResultadoMonitoreoOperaciones
								.getReferenciaAvanzar());
				lhsmParametros.put("operacionAvanzar",
						beanResultadoMonitoreoOperaciones.getOperAvanzar());
				lhsmParametros.put("horaAvanzar",
						beanResultadoMonitoreoOperaciones.getHoraAvanzar());
				lhsmParametros.put("referenciaRetroceder",
						beanResultadoMonitoreoOperaciones
								.getReferenciaRetroceder());
				lhsmParametros.put("operacionRetroceder",
						beanResultadoMonitoreoOperaciones.getOperRetroceder());
				lhsmParametros.put("horaRetroceder",
						beanResultadoMonitoreoOperaciones.getHoraRetroceder());
				lhsmParametros.put(PAGINA, pagina);
			} else {
				lhsmParametros.put("codAviso", lstrCodErrorPage);
				lhsmParametros.put("msgAviso", lstrMsgErrorPage);
			}
		} catch (BusinessException e) {
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			lhsmParametros.put(COD_ERROR, lstrCodErrorPage);
			lhsmParametros.put(MSG_ERROR, lstrMsgErrorPage);
		}

		debug("Codigo error :" + lstrCodErrorPage);
		debug("Mensaje error:" + lstrMsgErrorPage);

		return new ModelAndView(CONSULTA_MONITOREO, lhsmParametros);
	}

	/**
	 * Exportar consulta monitoreo operaciones.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ExportarConsultaMonitoreoOperaciones.do")
	public ModelAndView exportarConsultaMonitoreoOperaciones(
			HttpServletRequest req, HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));		
		final String idCorresponsal = req.getParameter(OPT_CORRESPO);
		final HashMap<String, Object> map = new HashMap<String, Object>();
		final List<BeanMonitoreoOperaciones> listaRegistros = (List<BeanMonitoreoOperaciones>) req
				.getSession()
				.getAttribute(LIST_REG_CORRESPO);
		for (BeanMonitoreoOperaciones operaciones : listaRegistros) {
			operaciones.setIdCorresponsal(idCorresponsal);
		}
		if (listaRegistros == null
				|| (listaRegistros != null && listaRegistros.isEmpty())) {
			info("Registro para exportar" + listaRegistros.size());
			final HashMap<String, Object> lhsmParametros = new HashMap<String, Object>();
			lhsmParametros.put(COD_ERROR, "NOEXPORT");
			lhsmParametros.put(MSG_ERROR, "NO HAY REGISTROS PARA EXPORTAR");
			return new ModelAndView(CONSULTA_MONITOREO,
					lhsmParametros);
		}
		final JRDataSource dataSource = new JRBeanArrayDataSource(listaRegistros
				.toArray());
		final DateFormat Dformat = 
			new  SimpleDateFormat("yyyy/MM/dd HH:mm:ss aa", new Locale("ES","MX")); 
		map.put("fechaReporte", Dformat.format(java.util.Calendar.getInstance().getTime())); // Pasamos la fecha al reporte
		return new ModelAndView(
				new ExportaXLS(
						"MonitoreoOperaciones",
						"/com/isban/corresponsalia/reportes/doctos/monitoreooperaciones.jasper",
						dataSource), map);
	}
	
	
}
