package com.isban.corresponsalia.controllers.corresponsales;

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

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanABMMantenimientoOperCorresponsal;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCorresponsal;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanResultadoOperacionesCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.comunes.BeanOperacion;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.bo.corresponsales.BOCorresponsalesOperaciones;
import com.isban.corresponsalia.reportes.ExportaXLS;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

/**
 * The Class ControllerCorresponsalesOperaciones.
 */
@Controller
public class ControllerCorresponsalesOperaciones extends ArchitechEBE {

	/** The Constant NEW_ARCH. */
	private static final String NEW_ARCH ="NewArchitechSession";
	
	/** The Constant LISTA_OPERACIONES. */
	private static final String LISTA_OPERACIONES = "listaOperaciones";
	
	/** The Constant OPERACION. */
	private static final String OPERACION = "operacion";
	
	/** The Constant TXT_OPERACION. */
	private static final String TXT_OPERACION = "Operacion: ";
	
	/** The Constant LISTA. */
	private static final String LISTA = "lista";
	
	/** The Constant LISTA_CORRESPO. */
	private static final String LISTA_CORRESPO = "listaCorresponsales";
	
	/** The Constant COD_ERROR. */
	private static final String COD_ERROR = "codError";
	
	/** The Constant MSG_ERROR. */
	private static final String MSG_ERROR = "msgError";
	
	/** The Constant CORRESPONSAL. */
	private static final String CORRESPONSAL = "corresponsal" ;
	
	/** The Constant CORRESPONSAL_BUSCAR. */
	private static final String CORRESPONSAL_BUSCAR = "corresponsalBuscar";
	
	/** The Constant CANAL_0014. */
	private static final String CANAL_0014 = "0014" ;
	
	/** The Constant TOTAL_OPER. */
	private static final String TOTAL_OPER = "Total de Operaciones: " ;
	
	/** The Constant DLE. */
	private static final String DLE = "DLE";
	
	/** The Constant CONSULTA_CORRESPO. */
	private static final String CONSULTA_CORRESPO = "ConsultaOperacionesCorresponsal" ;
	
	/** The Constant ID_OPERACION. */
	private static final String ID_OPERACION = "idOperacion";	 
	
	/** The b o corresponsales operaciones. */
	transient private BOCorresponsalesOperaciones bOCorresponsalesOperaciones;

	/**
	 * Sets the bO corresponsales operaciones.
	 *
	 * @param bOCorresponsalesOperaciones the new bO corresponsales operaciones
	 */
	public void setBOCorresponsalesOperaciones(
			BOCorresponsalesOperaciones bOCorresponsalesOperaciones) {
		this.bOCorresponsalesOperaciones = bOCorresponsalesOperaciones;
	}

	/**
	 * Consulta operaciones corresponsal.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value = "ConsultaOperacionesCorresponsal.do")
	public ModelAndView consultaOperacionesCorresponsal(HttpServletRequest req,
			HttpServletResponse res)  {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		req.getSession().setAttribute(LISTA_OPERACIONES, null);
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();

		String operacion = req.getParameter(OPERACION);
		
		if (operacion == null) {
			// mapParametros.put(LISTA_OPERACIONES, null);
			operacion = "inicial";
		}
		info(TXT_OPERACION + operacion);
		String direccion = req.getParameter("direccion");
		if (direccion == null) {
			direccion = "";
		}

		final BeanMttoConsultaCorresponsal beanConsulta = new BeanMttoConsultaCorresponsal();
		beanConsulta.setCodigoCorresponsalia("14");
		beanConsulta.setTipoConsulta("L");
		beanConsulta.setIndicadorPaginacion("N");
		final BeanResultadoCorresponsalia salida = bOCorresponsalesOperaciones
				.consultaCorresponsales(beanConsulta, getArchitechBean());
		debug("Codigo de Error: <" + salida.getCodError() + ">");
		mapParametros.put(LISTA, salida.getRegistros());
		req.getSession().setAttribute(LISTA_CORRESPO,
				salida.getRegistros());
		if ("".equals(salida.getMsgError())) {
			mapParametros.put(LISTA, salida.getRegistros());
			debug("Cantidad de corresponsales: " + salida.getRegistros().size());
		} else {
			// implementar error
			debug("codError:" + salida.getCodError());
			debug("msgError:" + salida.getMsgError());

			if (!"".equals(salida.getMsgError())) {
				mapParametros.put(COD_ERROR, salida.getCodError());
				mapParametros.put("msgError", salida.getMsgError());
			}
		}
		debug(TXT_OPERACION + operacion);
		req.getSession().setAttribute(OPERACION, operacion);
		if ("consultarOperaciones".equals(operacion)) {
			debug("Ejecuta Consulta Operaciones");
			final String idCorresponsal = req.getParameter(CORRESPONSAL);
			debug("Corresponsal a buscar: " + idCorresponsal);
			mapParametros.put(CORRESPONSAL_BUSCAR, idCorresponsal);

			final BeanConsultaOperacionesCorresponsal beanConsultaOper = new BeanConsultaOperacionesCorresponsal();
			beanConsultaOper.setEntidad(CANAL_0014);
			beanConsultaOper.setCanal("14");
			beanConsultaOper.setNivelParametria("02");
			beanConsultaOper.setIdCorresponsal(idCorresponsal);
			beanConsultaOper.setTipoConsulta("L");

			if ("".equals(direccion)) {
				beanConsultaOper.setIndicadorPag("N");
			} else {
				final BeanResultadoOperacionesCorresponsal anterior = (BeanResultadoOperacionesCorresponsal) req
						.getSession().getAttribute("resultado");
				beanConsultaOper.setIndicadorPag("P");
				beanConsultaOper.setLimiteInferiorConsulta(anterior
						.getReferenciaRetroceder());
				// beanConsultaOper.setLimSuperior(anterior.getReferenciaAvanzar());
			}
			beanConsultaOper.setIndicadorDirreccion(direccion);

			final BeanResultadoOperacionesCorresponsal operaciones = bOCorresponsalesOperaciones
					.consultaOperacionesCorresponsal(beanConsultaOper,
							getArchitechBean());

			debug(TOTAL_OPER + operaciones.getRegistros().size());
			debug("codError:" + operaciones.getCodError());
			debug("msgError:" + operaciones.getMsgError());

			if (operaciones.getRegistros().isEmpty()
					&& operaciones.getCodError().indexOf(DLE) < 0) {
				mapParametros.put("codAviso", operaciones.getCodError());
				mapParametros.put("msgAviso", operaciones.getMsgError());
			} else if (!"".equals(operaciones.getCodError())) {
				mapParametros.put(COD_ERROR, operaciones.getCodError());
				mapParametros.put(MSG_ERROR, operaciones.getMsgError());
			}

			if (operaciones.getRegistros().size() <= 0) {
				info("Finaliza Consulta Operaciones por Corresponsalia No Existen Registros");
				mapParametros.put("hayRegistros", false);
			} else {
				mapParametros.put("hayRegistros", true);
			}

			req.getSession().setAttribute(LISTA_OPERACIONES,
					operaciones.getRegistros());
			req.getSession().setAttribute("resultado", operaciones);
			mapParametros.put(LISTA_OPERACIONES, operaciones.getRegistros());
		}

		return new ModelAndView(CONSULTA_CORRESPO,
				mapParametros);
	}

	/**
	 * Asoc operaciones corresponsal.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value = "AsocOperacionesCorresponsal.do")
	public ModelAndView asocOperacionesCorresponsal(HttpServletRequest req,
			HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();
		String operacion = req.getParameter(OPERACION);

		if (operacion == null) {
			operacion = "inicial";
		}
		info(TXT_OPERACION + operacion);

		final String idCorresponsal = req.getParameter(CORRESPONSAL);
		debug("Corresponsal a buscar: " + idCorresponsal);
		mapParametros.put(CORRESPONSAL_BUSCAR, idCorresponsal);

		if ("asociar".equals(operacion) || "desasociar".equals(operacion)) {
			debug("Ejecuta " + operacion + " Operaciones");

			final BeanABMMantenimientoOperCorresponsal beanOper = new BeanABMMantenimientoOperCorresponsal();
			if ("asociar".equals(operacion)) {
				beanOper.setTipoMovimiento("A");
				beanOper.setCodigoOperacion(req.getParameter("lbTmplNoAsoc"));
				// beanOper.setTipoOperacionCorr(req.getParameter("lbTmplNoAsoc"));
				// beanOper.setTipoOperacionCorr(req.getParameter("txtCveRelac"));
				// beanOper.setTipoOperacionCorr("0123456789");//hardcode
				beanOper.setTipoOperacionCorr(req.getParameter("txtCveRelac"));
				beanOper.setTransaccion(req.getParameter("txtTrx"));
				beanOper.setCodigoOperacionTrx(req.getParameter("txtClc"));
				beanOper.setCodigoContratoTrx(req.getParameter("txtClcCon"));

			} else {

				final BeanConsultaOperacionesCorresponsal beanConsultaOperCorr = new BeanConsultaOperacionesCorresponsal();
				beanConsultaOperCorr.setEntidad(CANAL_0014);
				beanConsultaOperCorr.setCanal("14");
				beanConsultaOperCorr.setNivelParametria("02");
				beanConsultaOperCorr.setIdCorresponsal(idCorresponsal);
				beanConsultaOperCorr.setTipoConsulta("L");
				final BeanResultadoOperacionesCorresponsal operacionesCorr = bOCorresponsalesOperaciones
						.consultaOperacionesCorresponsal(beanConsultaOperCorr,
								getArchitechBean());
				debug("Total de Operaciones Corresponsal: "
						+ operacionesCorr.getRegistros().size());

				for (int i = 0; i < operacionesCorr.getRegistros().size(); i++) {
					final BeanOperacion opeCorr = operacionesCorr.getRegistros().get(
							i);
					if (opeCorr.getCodigoOperacion().equals(
							req.getParameter("lbTmplAsoc"))) {
						// operaciones.getRegistros().remove(j);
						beanOper.setTipoOperacionCorr(opeCorr
								.getTipoOperacionCorresponsal());
						i = operacionesCorr.getRegistros().size();
					}
				}

				beanOper.setTipoMovimiento("B");
				beanOper.setCodigoOperacion(req.getParameter("lbTmplAsoc"));
				// beanOper.setTipoOperacionCorr(req.getParameter("lbTmplAsoc"));
				// beanOper.setTipoOperacionCorr("0123456789");//hardcode

			}

			beanOper.setEntidad(CANAL_0014);
			beanOper.setCanal("14");

			beanOper.setIdCorresponsal(idCorresponsal);
			beanOper.setNivelParametria("02");

			final BeanError respuesta = bOCorresponsalesOperaciones
					.actualizaOperacion(beanOper, getArchitechBean());
			if (respuesta.getCodigoError().indexOf(DLE) > -1) {
				mapParametros.put("error", respuesta.getMsgError());
			}

		}

		debug("Ejecuta Consulta Operaciones");

		final BeanConsultaOperacionesCorresponsal beanConsultaOper = new BeanConsultaOperacionesCorresponsal();
		beanConsultaOper.setEntidad(CANAL_0014);
		beanConsultaOper.setCanal("14");
		beanConsultaOper.setNivelParametria("01");
		beanConsultaOper.setTipoConsulta("L");
		final BeanResultadoOperacionesCorresponsal operaciones = bOCorresponsalesOperaciones
				.consultaOperacionesCorresponsal(beanConsultaOper,
						getArchitechBean());
		debug(TOTAL_OPER + operaciones.getRegistros().size());

		final BeanConsultaOperacionesCorresponsal beanConsultaOperCorr = new BeanConsultaOperacionesCorresponsal();
		beanConsultaOperCorr.setEntidad(CANAL_0014);
		beanConsultaOperCorr.setCanal("14");
		beanConsultaOperCorr.setNivelParametria("02");
		beanConsultaOperCorr.setIdCorresponsal(idCorresponsal);
		beanConsultaOperCorr.setTipoConsulta("L");
		final BeanResultadoOperacionesCorresponsal operacionesCorr = bOCorresponsalesOperaciones
				.consultaOperacionesCorresponsal(beanConsultaOperCorr,
						getArchitechBean());
		debug("Total de Operaciones Corresponsal: "
				+ operacionesCorr.getRegistros().size());

		for (int i = 0; i < operacionesCorr.getRegistros().size(); i++) {
			final BeanOperacion opeCorr = operacionesCorr.getRegistros().get(i);
			for (int j = 0; j < operaciones.getRegistros().size(); j++) {
				final BeanOperacion ope = operaciones.getRegistros().get(j);
				if (opeCorr.getCodigoOperacion().equals(
						ope.getCodigoOperacion())) {
					operaciones.getRegistros().remove(j);
					j = operaciones.getRegistros().size();
				}
			}
		}

		mapParametros.put("operaciones", operaciones.getRegistros());
		mapParametros.put("operacionesCorr", operacionesCorr.getRegistros());

		return new ModelAndView("AsocOperacionesCorresponsal", mapParametros);
	}

	/**
	 * Detalle operaciones corresponsal.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value = "DetalleOperacionesCorresponsal.do")
	public ModelAndView detalleOperacionesCorresponsal(HttpServletRequest req,
			HttpServletResponse res)  {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();
		String operacion = req.getParameter(OPERACION);
		final String idOperacion = req.getParameter(ID_OPERACION);
		final String idCorresponsal = req.getParameter(CORRESPONSAL);

		final BeanMttoConsultaCorresponsal beanConsulta = new BeanMttoConsultaCorresponsal();
		beanConsulta.setCodigoCorresponsalia("14");
		beanConsulta.setTipoConsulta("L");
		beanConsulta.setIndicadorPaginacion("N");
		final BeanResultadoCorresponsalia salida = bOCorresponsalesOperaciones
				.consultaCorresponsales(beanConsulta, getArchitechBean());
		debug("Codigo de Error: <" + salida.getCodError() + ">");
		mapParametros.put(LISTA, salida.getRegistros());
		mapParametros.put(ID_OPERACION, idOperacion);

		if ("".equals(salida.getCodError())) {
			mapParametros.put(LISTA, salida.getRegistros());
			debug("Cantidad de corresponsales: " + salida.getRegistros().size());
		} else {
			// implementar error
			debug("codError:" + salida.getCodError());
			debug("msgError:" + salida.getMsgError());

			if (!"".equals(salida.getMsgError())) {
				mapParametros.put(COD_ERROR, salida.getCodError());
				mapParametros.put(MSG_ERROR, salida.getMsgError());
			}
		}
		for (int i = 0; i < salida.getRegistros().size(); i++) {
			if (salida.getRegistros().get(i).getCodigoCorresponsal().equals(
					idCorresponsal)) {
				req.getSession().setAttribute("nombre",
						salida.getRegistros().get(i).getNombreCorresponsal());
				req.getSession().setAttribute("idCorresponsal", idCorresponsal);

				debug("IDCorr: " + idCorresponsal);
				debug("Nombre: "
						+ salida.getRegistros().get(i).getNombreCorresponsal());

				i = salida.getRegistros().size();
			}
		}

		if (operacion == null) {
			operacion = "inicial";
		}
		info(TXT_OPERACION + operacion);

		if ("detalle".equals(operacion)) {
			mapParametros.put("disabled", "disabled=\"disabled\"");

		}

		final BeanConsultaOperacionesCorresponsal beanConsultaOper = new BeanConsultaOperacionesCorresponsal();
		beanConsultaOper.setEntidad(CANAL_0014);
		beanConsultaOper.setCanal("14");
		beanConsultaOper.setNivelParametria("02");
		beanConsultaOper.setTipoConsulta("D");
		beanConsultaOper.setIdCorresponsal(idCorresponsal);
		beanConsultaOper.setCodigoOperacion(idOperacion);
		final BeanResultadoOperacionesCorresponsal operaciones = bOCorresponsalesOperaciones
				.consultaOperacionesCorresponsal(beanConsultaOper,
						getArchitechBean());
		debug(TOTAL_OPER + operaciones.getRegistros().size());

		if (operaciones.getRegistros().size() > 0) {
			mapParametros
					.put("operacionDet", operaciones.getRegistros().get(0));
		}

		return new ModelAndView("DetalleOperacionCorresponsal", mapParametros);
	}

	/**
	 * Exportar operaciones corresponsal.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ExportarOperacionesCorresponsal.do")
	public ModelAndView exportarOperacionesCorresponsal(HttpServletRequest req,
			HttpServletResponse res)  {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> map = new HashMap<String, Object>();
		final String idCorresponsal = req.getParameter(CORRESPONSAL);
		String nombreCorr = null;
		req.getSession().getAttribute(CORRESPONSAL);
		final ArrayList<BeanOperacion> listaRegistros = (ArrayList<BeanOperacion>) req
				.getSession().getAttribute(LISTA_OPERACIONES);
		
		map.put(CORRESPONSAL_BUSCAR, idCorresponsal);
		List <BeanCorresponsal> salida = (ArrayList<BeanCorresponsal>) req.getSession().getAttribute(LISTA_CORRESPO);
		map.put(LISTA, salida);
		
		for (BeanCorresponsal beanCorresponsal : salida) {
			if (beanCorresponsal.getCodigoCorresponsal().equals(idCorresponsal)) {
				nombreCorr = beanCorresponsal.getNombreCorresponsal();
			}
		}
		
		info("NOMBRE CORRESPON:" + nombreCorr);
		if (listaRegistros == null
				|| (listaRegistros != null && listaRegistros.isEmpty())) {
			map.put(COD_ERROR, "NOEXPORT");
			map.put(MSG_ERROR, "NO HAY REGISTROS PARA EXPORTAR");
			return new ModelAndView(CONSULTA_CORRESPO, map);
		}
		final JRDataSource dataSource = new JRBeanArrayDataSource(listaRegistros
				.toArray());
		final DateFormat Dformat = new  
			SimpleDateFormat("yyyy/MM/dd HH:mm:ss aa",new Locale("ES","MX")); 
		map.put("fechaReporte", Dformat.format(java.util.Calendar.getInstance().getTime()));
		map.put("nombreCorr", nombreCorr);
		
		return new ModelAndView(new ExportaXLS("Operaciones",
				"/com/isban/corresponsalia/reportes/doctos/operaciones.jasper",
				dataSource), map);
	}

	/**
	 * Elimina operaciones corresponsal.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value = "EliminaOperacionesCorresponsal.do")
	public ModelAndView eliminaOperacionesCorresponsal(HttpServletRequest req,
			HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();

		final String idCorresponsal = req.getParameter(CORRESPONSAL);
		debug("Corresponsal: " + idCorresponsal);
		mapParametros.put(CORRESPONSAL_BUSCAR, idCorresponsal);
		mapParametros.put(LISTA, req.getSession().getAttribute(
				LISTA_CORRESPO));

		final String idOperacion = req.getParameter(ID_OPERACION);
		debug(TXT_OPERACION + idOperacion);
		
		final String tipOperCorr =  req.getParameter("tipoOperCorr");

		final BeanABMMantenimientoOperCorresponsal beanOper = new BeanABMMantenimientoOperCorresponsal();

		beanOper.setTipoMovimiento("B");
		beanOper.setCodigoOperacion(idOperacion);
		// beanOper.setTipoOperacionCorr(req.getParameter("lbTmplAsoc"));
		beanOper.setTipoOperacionCorr(tipOperCorr.trim());

		beanOper.setEntidad(CANAL_0014);
		beanOper.setCanal("14");

		beanOper.setIdCorresponsal(idCorresponsal);
		beanOper.setNivelParametria("02");

		final BeanError respuesta = bOCorresponsalesOperaciones.actualizaOperacion(
				beanOper, getArchitechBean());
		if (respuesta.getCodigoError().indexOf(DLE) > -1) {
			mapParametros.put(MSG_ERROR, respuesta.getMsgError());
			mapParametros.put(COD_ERROR, respuesta.getCodigoError());
		}  else {
			mapParametros.put("msgAviso", respuesta.getMsgError());
			mapParametros.put("codAviso", respuesta.getCodigoError());			
		}

		final BeanConsultaOperacionesCorresponsal beanConsultaOper = new BeanConsultaOperacionesCorresponsal();
		beanConsultaOper.setEntidad(CANAL_0014);
		beanConsultaOper.setCanal("14");
		beanConsultaOper.setNivelParametria("02");
		beanConsultaOper.setIdCorresponsal(idCorresponsal);
		beanConsultaOper.setTipoConsulta("L");
		final BeanResultadoOperacionesCorresponsal operaciones = bOCorresponsalesOperaciones
				.consultaOperacionesCorresponsal(beanConsultaOper,
						getArchitechBean());
		debug(TOTAL_OPER + operaciones.getRegistros().size());
		req.getSession().setAttribute(LISTA_OPERACIONES,
				operaciones.getRegistros());
		mapParametros.put(LISTA_OPERACIONES, operaciones.getRegistros());
		
		return new ModelAndView(CONSULTA_CORRESPO,
				mapParametros);

	}

	/**
	 * Guarda operaciones corresponsal.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value = "GuardaOperacionesCorresponsal.do")
	public ModelAndView guardaOperacionesCorresponsal(HttpServletRequest req,
			HttpServletResponse res)  {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();

		final String idCorresponsal = req.getParameter(CORRESPONSAL);
		debug("Corresponsal: " + idCorresponsal);
		mapParametros.put(CORRESPONSAL_BUSCAR, idCorresponsal);
		mapParametros.put(LISTA, req.getSession().getAttribute(
				LISTA_CORRESPO));
		final String idOperacion = req.getParameter(ID_OPERACION);
		debug(TXT_OPERACION + idOperacion);

		final BeanABMMantenimientoOperCorresponsal beanOper = new BeanABMMantenimientoOperCorresponsal();

		beanOper.setTipoMovimiento("M");
		// beanOper.setCodigoOperacion(req.getParameter("txtCodigoOperacion"));
		beanOper.setCodigoOperacion(idOperacion);
		beanOper.setTransaccion(req.getParameter("txtCveTransaccion"));
		beanOper.setImporteMaximoOperacionFront(req
				.getParameter("txtImporteMaximoOperacionFormateado"));
		beanOper.setImporteMinimoOperacionFront(req
				.getParameter("txtImporteMinimoOperacionFormateado"));
		beanOper.setAcumuladoDiarioFront(req
				.getParameter("txtAcumuladoDiarioFormateado"));
		beanOper
				.setHoraInicioFront(req.getParameter("txtHoraInicioFormateada"));
		beanOper.setHoraFinalFront(req.getParameter("txtHoraFinalFormateada"));
		beanOper.setAcumuladoMensualFront(req
				.getParameter("txtAcumuladoMensualFormateado"));
		beanOper.setCodigoOperacionTrx(req
				.getParameter("txtCodigoOperacionTransaccion"));
		beanOper.setCodigoContratoTrx(req
				.getParameter("txtCodigoOperacionContraOper"));
		// beanOper.setCodigoOperacion(idOperacion);
		// beanOper.setTipoOperacionCorr(req.getParameter("lbTmplAsoc"));
		// beanOper.setTipoOperacionCorr("0123456789");//hardcode
		beanOper.setTipoOperacionCorr(req.getParameter("txtCodigoOperacion"));
		debug("operacionCorresponsal: "
				+ req.getParameter("txtCodigoOperacion"));

		beanOper.setEntidad(CANAL_0014);
		beanOper.setCanal("14");

		beanOper.setIdCorresponsal(idCorresponsal);
		beanOper.setNivelParametria("02");

		final BeanError respuesta = bOCorresponsalesOperaciones.actualizaOperacion(
				beanOper, getArchitechBean());
		debug("Salida: " + respuesta.getCodigoError());
		if (respuesta.getCodigoError().indexOf(DLE) > -1) {
			mapParametros.put(MSG_ERROR, respuesta.getMsgError());
			mapParametros.put(COD_ERROR, respuesta.getCodigoError());
		} else {
			mapParametros.put("msgAviso", respuesta.getMsgError());
			mapParametros.put("codAviso", respuesta.getCodigoError());			
		}
		return new ModelAndView(CONSULTA_CORRESPO,
				mapParametros);

	}
}
