/*
 * 
 */
package com.isban.corresponsalia.controllers.corresponsales;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCorresponsal;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanResultadoOperacionesCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanComisiones;
import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.comunes.BeanOperacion;
import com.isban.corresponsalia.beans.corresponsales.BeanAltaModificaComisiones;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaComision;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoComision;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.bo.corresponsales.BOCorresponsalesComisiones;
import com.isban.corresponsalia.bo.corresponsales.BOCorresponsalesOperaciones;
import com.isban.corresponsalia.comunes.Parametros;
import com.isban.corresponsalia.comunes.Utils;
import com.isban.corresponsalia.reportes.ExportaXLS;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;

/**
 * The Class ControllerCorresponsalesComisiones.
 */
@Controller
public class ControllerCorresponsalesComisiones extends ArchitechEBE{
	
	/** The cadena vacia. */
	transient private static final String CADENAVACIA = "";
		
	/** The Constant NEW_ARCH. */
	private static final String NEW_ARCH = "NewArchitechSession";
	
	/** The Constant LISTA_COR_COM. */
	private static final String LISTA_COR_COM = "listaCorresponsalesComision";
	
	/** The Constant REG_COM. */
	private static final String REG_COM = "registrosComisiones";
	
	/** The Constant CORRESPONSALIA. */
	private static final String CORRESPONSALIA = "CORRESPONSALIA";
	
	/** The Constant COD_AVISO. */
	private static final String COD_AVISO = "codAviso";
	
	/** The Constant MSG_AVISO. */
	private static final String MSG_AVISO = "msgAviso";
	
	/** The Constant COD_ERROR. */
	private static final String COD_ERROR = "codError";
	
	/** The Constant MSG_ERROR. */
	private static final String MSG_ERROR = "msgError";
	
	/** The Constant CORRESP_SEL. */
	private static final String CORRESP_SEL = "corresponsaliaSeleccionada" ;
	
	/** The Constant CMB_NOMBRE_CORR. */
	private static final String CMB_NOMBRE_CORR = "cmbNombreCorresponsal";
	
	/** The Constant ENTIDAD_BANCO. */
	private static final String ENTIDAD_BANCO = "ENTIDAD_BANCO";
	
	/** The Constant CMB_NOMBRE_OPER. */
	private static final String CMB_NOMBRE_OPER =  "cmbNombreOperacion";
	
	/** The b o corresponsales comisiones. */
	transient private BOCorresponsalesComisiones bOCorresponsalesComisiones;	
	
	/** The b o corresponsales operaciones. */
	transient private BOCorresponsalesOperaciones bOCorresponsalesOperaciones;


	/**
	 * Sets the bO corresponsales operaciones.
	 *
	 * @param bOCorresponsalesOperaciones the new bO corresponsales operaciones
	 */
	public void setBOCorresponsalesOperaciones(BOCorresponsalesOperaciones bOCorresponsalesOperaciones){
		this.bOCorresponsalesOperaciones = bOCorresponsalesOperaciones;
	}

	/**
	 * Sets the bO corresponsales comisiones.
	 *
	 * @param bOCorresponsalesComisiones the new bO corresponsales comisiones
	 */
	public void setBOCorresponsalesComisiones(BOCorresponsalesComisiones bOCorresponsalesComisiones){
		this.bOCorresponsalesComisiones = bOCorresponsalesComisiones;
	}	


	/**
	 * Muestra corresponsales comision.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	
	 */
	@RequestMapping(value="MuestraCorresponsalesComision.do")
	public ModelAndView muestraCorresponsalesComision(HttpServletRequest req, HttpServletResponse res){
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		info("Inicia Consulta Comisiones Corresponsalia");
	
		req.getSession().removeAttribute(LISTA_COR_COM);
		req.getSession().removeAttribute(REG_COM);
		
		final HashMap<String,Object>       mapParametros = new HashMap<String,Object>();
		final BeanMttoConsultaCorresponsal beanConsultaCorresponsal = new BeanMttoConsultaCorresponsal();
		
	
		beanConsultaCorresponsal.setCodigoCorresponsalia(Parametros.getParametroAplicacion(CORRESPONSALIA));
		beanConsultaCorresponsal.setTipoConsulta("L");
		beanConsultaCorresponsal.setIndicadorPaginacion("N");
		String lstrCodErrorPage = "";
		String lstrMsgErrorPage = "";
		
		try{
			final BeanResultadoCorresponsalia beanRegresoCorresponsalias = bOCorresponsalesComisiones.consultaCorresponsales(beanConsultaCorresponsal, getArchitechBean());
			if(beanRegresoCorresponsalias.getRegistros().isEmpty()){
				lstrCodErrorPage = beanRegresoCorresponsalias.getCodError();
				lstrMsgErrorPage = beanRegresoCorresponsalias.getMsgError();
				mapParametros.put(COD_AVISO, lstrCodErrorPage);
				mapParametros.put(MSG_AVISO, lstrMsgErrorPage);
			}
			else{
				req.getSession().setAttribute(LISTA_COR_COM, beanRegresoCorresponsalias.getRegistros());
			}
		}
		catch(BusinessException e){
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			mapParametros.put(COD_ERROR, lstrCodErrorPage);
			mapParametros.put(MSG_ERROR, lstrMsgErrorPage);
		}
		mapParametros.put(CORRESP_SEL, 0);
		return new ModelAndView("ConsultaComisiones", mapParametros);
	}	
	
	/**
	 * Consulta comisiones.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="ConsultaComisiones.do")
	public ModelAndView consultaComisiones(HttpServletRequest req, HttpServletResponse res){
		info("Inicia Consulta Comisiones Corresponsalia");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String,Object>  mapParametros        = new HashMap<String,Object>();
		String                  lstrCodErrorPage     = "";
		String                  lstrMsgErrorPage     = "";
		final BeanConsultaComision    beanConsulta         = new BeanConsultaComision();
		final String                  idCorresponsal       = req.getParameter(CMB_NOMBRE_CORR) == null ? CADENAVACIA : req.getParameter(CMB_NOMBRE_CORR);
		final String                  avanzarRetroceder    = req.getParameter("opcAvanzarRetroceder") == null ? CADENAVACIA : req.getParameter("opcAvanzarRetroceder").trim();
		final String                  lstrCodError         = req.getAttribute(COD_ERROR) == null ? CADENAVACIA : (String)req.getAttribute(COD_ERROR);
		final String                  lstrMsgError         = req.getAttribute(MSG_ERROR) == null ? CADENAVACIA : (String)req.getAttribute(MSG_ERROR);
		final String                  lstrCodAviso         = req.getAttribute(COD_AVISO) == null ? CADENAVACIA : (String)req.getAttribute(COD_AVISO);
		final String                  lstrMsgAviso         = req.getAttribute(MSG_AVISO) == null ? CADENAVACIA : (String)req.getAttribute(MSG_AVISO);

		
		debug("Nombre de Corresponsal:" + idCorresponsal);
		debug("AvanzarRetroceder     :" + avanzarRetroceder);

		beanConsulta.setEntidad(Parametros.getParametroAplicacion(ENTIDAD_BANCO));
		beanConsulta.setCanalCorresponsal(Parametros.getParametroAplicacion(CORRESPONSALIA));
		beanConsulta.setIdentificacionCorresponsal(idCorresponsal);		
		beanConsulta.setIndicadorPaginacion("N");
		//beanConsulta.setIndicadorAvanceRetroceso("A");
		beanConsulta.setTipoOperacion("C");
		
		req.getSession().removeAttribute(REG_COM);
		mapParametros.put(CORRESP_SEL, idCorresponsal);	
		
		try{
			final BeanResultadoComision beanRegresoComisiones = bOCorresponsalesComisiones.consultaComisiones(beanConsulta, getArchitechBean());
			if( beanRegresoComisiones.getRegistros().isEmpty()){
				lstrCodErrorPage = beanRegresoComisiones.getCodError();
				lstrMsgErrorPage = beanRegresoComisiones.getMsgError();
				
				mapParametros.put(COD_AVISO , lstrCodErrorPage);
				mapParametros.put(MSG_AVISO , lstrMsgErrorPage);

			}
			else{
				req.getSession().setAttribute(REG_COM, beanRegresoComisiones.getRegistros());
			}
		}
		catch(BusinessException e){
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			mapParametros.put(COD_ERROR, lstrCodErrorPage);
			mapParametros.put(MSG_ERROR, lstrMsgErrorPage);
		}
		
		debug("codError:" + lstrCodErrorPage);
		debug("msgError:" + lstrMsgErrorPage);
		
		if(!"".equals(lstrCodErrorPage) && !"".equals(lstrCodAviso)){
			mapParametros.put(COD_AVISO , lstrCodAviso);
			mapParametros.put(MSG_AVISO , lstrMsgAviso);
			
		}
		else if(!"".equals(lstrCodError)){
			mapParametros.put(COD_ERROR , lstrCodError);
			mapParametros.put(MSG_ERROR , lstrMsgError);
			
		}

		return new ModelAndView("ConsultaComisiones", mapParametros);
	}	
	
	
	/**
	 * Modificar comision.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="ModificarComision.do")
	public ModelAndView modificarComision(HttpServletRequest req,HttpServletResponse res){
		info("Inicia Modificar Comision");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String,Object>  mapParametros        = new HashMap<String,Object>();
		BeanCorresponsal        beanCorresponsal     = new BeanCorresponsal();
		final List<BeanCorresponsal>  listaCorresponsales  = (List<BeanCorresponsal>)req.getSession().getAttribute(LISTA_COR_COM);
		final List<BeanComisiones>    registros            = (List<BeanComisiones>)req.getSession().getAttribute(REG_COM);
		BeanComisiones          beanComisiones		 = new BeanComisiones();
		final String                  regMod               = req.getParameter("regMod") == null ? CADENAVACIA : req.getParameter("regMod");
		final String                  idCorresponsal       = req.getParameter(CMB_NOMBRE_CORR) == null ? CADENAVACIA : req.getParameter(CMB_NOMBRE_CORR);
		
		debug("Id Corresponsal        :" + idCorresponsal);
		debug("No. Registro Modificar :" + regMod);
		
		try{
			beanComisiones = registros.get(Integer.parseInt(regMod));
		}
		catch(IndexOutOfBoundsException  e){
			debug("No fue posible obtener el bean de comision");
		}
		
		for(BeanCorresponsal bCorresponsalTemp:listaCorresponsales){
			if(bCorresponsalTemp.getCodigoCorresponsal().equals(idCorresponsal)){
				debug("Corresponsal encontrado en la lista de corresponsales");
				beanCorresponsal = bCorresponsalTemp;
				break;
			}
		}

		mapParametros.put("beanCorresponsal", beanCorresponsal);
		mapParametros.put("beanComisiones"  , beanComisiones);
		
		return new ModelAndView("AltaModificacionComisiones", mapParametros);
	}
	
	/**
	 * Realiza modificacion comision.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="RealizaModificacionComision.do")
	public ModelAndView realizaModificacionComision(HttpServletRequest req,	HttpServletResponse res){
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		info("Inicia Modificar Comisiones");
		final HashMap<String,Object>     mapParametros        = new HashMap<String,Object>();
		final BeanAltaModificaComisiones beanAltaModificacion = new BeanAltaModificaComisiones();
		BeanError                  beanRespuesta        = new BeanError();
		
		final String codigoCorresponsal   = req.getParameter(CMB_NOMBRE_CORR) == null ? CADENAVACIA : req.getParameter(CMB_NOMBRE_CORR);
		final String nombreOperacion      = req.getParameter(CMB_NOMBRE_OPER) == null ? CADENAVACIA : req.getParameter(CMB_NOMBRE_OPER);	
		final String comisionCliente      = req.getParameter("importeComisionCliente") == null ? CADENAVACIA : req.getParameter("importeComisionCliente");	
		final String comisionCorresponsal = req.getParameter("importeComisionCorresponsal") == null ? CADENAVACIA : req.getParameter("importeComisionCorresponsal");	
		final String comisionTotal        = req.getParameter("importeComisionTotal") == null ? CADENAVACIA : req.getParameter("importeComisionTotal");	
		String lstrCodErrorPage     = "";
		String lstrMsgErrorPage     = "";
		String lstrFecha            = "";
		
		info("codigoCorresponsal:" + codigoCorresponsal);
		info("nombreOperacion   :" + nombreOperacion);

		//req.setAttribute(CMB_NOMBRE_CORR     , codigoCorresponsal);
		//req.setAttribute(CORRESP_SEL, codigoCorresponsal);
		
		
		final  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",new Locale("ES","MX"));
		lstrFecha = format.format(new Date());
		
		beanAltaModificacion.setEntidad(Parametros.getParametroAplicacion(ENTIDAD_BANCO));
		beanAltaModificacion.setCanalCorresponsal(Parametros.getParametroAplicacion(CORRESPONSALIA));
		beanAltaModificacion.setIdentificacionCorresponsal(codigoCorresponsal);	
		beanAltaModificacion.setTipoOperacion("M");
		beanAltaModificacion.setClaveReferenteTipoOperacion(nombreOperacion);
		beanAltaModificacion.setImporteComisionBancoMontoFijoFront(comisionCorresponsal);
		beanAltaModificacion.setImporteComisionClienteMontoFijoFront(comisionCliente);
		beanAltaModificacion.setImporteComisionTotalFront(comisionTotal);
		beanAltaModificacion.setNivelComision("02");
		beanAltaModificacion.setInicioVigencia(lstrFecha);
		beanAltaModificacion.setFinVigencia("9999-12-31");
		try{
			
			final BeanConsultaOperacionesCorresponsal beanConsultaOper = new BeanConsultaOperacionesCorresponsal();
			beanConsultaOper.setEntidad("0014");
			beanConsultaOper.setCanal("14");
			beanConsultaOper.setNivelParametria("02");
			beanConsultaOper.setIdCorresponsal(codigoCorresponsal);
			beanConsultaOper.setTipoConsulta("L");
		    beanConsultaOper.setIndicadorPag("N");
			beanConsultaOper.setIndicadorDirreccion("A");
			
			final BeanResultadoOperacionesCorresponsal operaciones = bOCorresponsalesOperaciones.consultaOperacionesCorresponsal(beanConsultaOper, getArchitechBean());
			
			String claveOper = null;
			for (BeanOperacion operRes : operaciones.getRegistros()) {
				debug(">>Descripcion corta: " + operRes.getDesCorta().trim());
				debug(">>Clave Referente TIOPE: " + beanAltaModificacion.getClaveReferenteTipoOperacion().trim());
				if (operRes.getDesCorta().trim().equals(beanAltaModificacion.getClaveReferenteTipoOperacion().trim())) {
					claveOper = operRes.getCodigoOperacion();
					debug(">>SI LO ENCONTRO:");
					break;
				}
			}
			beanAltaModificacion.setClaveReferenteTipoOperacion(claveOper);
			beanRespuesta = bOCorresponsalesComisiones.altaModificaComisiones(beanAltaModificacion, getArchitechBean());
			mapParametros.put(CORRESP_SEL, codigoCorresponsal);
			lstrCodErrorPage = beanRespuesta.getCodigoError();
			lstrMsgErrorPage = beanRespuesta.getMsgError();
			mapParametros.put(COD_AVISO, lstrCodErrorPage);
			mapParametros.put(MSG_AVISO, lstrMsgErrorPage);
			req.setAttribute(COD_AVISO, lstrCodErrorPage);
			req.setAttribute(MSG_AVISO, lstrMsgErrorPage);
		
		}
		catch(BusinessException e){
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			//mapParametros.put(COD_ERROR, lstrCodErrorPage);
			//mapParametros.put(MSG_ERROR, lstrMsgErrorPage);			
			//return new ModelAndView("AltaModificacionComisiones", mapParametros);
			req.setAttribute(COD_ERROR, lstrCodErrorPage);
			req.setAttribute(MSG_ERROR, lstrMsgErrorPage);
		}
		
		return consultaComisiones(req, res);
	}
	
	/**
	 * Asignar comision.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="AsignarComision.do")
	public ModelAndView asignarComision(HttpServletRequest req, HttpServletResponse res){
		info("Inicia Asignacion Comisiones");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String,Object>           mapParametros            = new HashMap<String,Object>();
		BeanCorresponsal                 beanCorresponsal         = new BeanCorresponsal();
		final List<BeanCorresponsal>           listaCorresponsales      = (List<BeanCorresponsal>)req.getSession().getAttribute(LISTA_COR_COM);
		//final List<BeanOperacion>              registros                =  new ArrayList<BeanOperacion>();
		final String                           idCorresponsal           = req.getParameter(CMB_NOMBRE_CORR) == null ? CADENAVACIA : req.getParameter(CMB_NOMBRE_CORR);
		final BeanConsultaOperacionesCorresponsal  beanConsultaOperaciones  = new BeanConsultaOperacionesCorresponsal();
		BeanResultadoOperacionesCorresponsal beanResultadoOperaciones = new BeanResultadoOperacionesCorresponsal();
		
		debug("Id Corresponsal        :" + idCorresponsal);
		
		if(listaCorresponsales != null) {
		for(BeanCorresponsal bCorresponsalTemp:listaCorresponsales){
			if(bCorresponsalTemp.getCodigoCorresponsal().equals(idCorresponsal)){
				debug("Corresponsal encontrado en la lista de corresponsales");
				beanCorresponsal = bCorresponsalTemp;
				break;
			}
		}

		mapParametros.put("beanCorresponsal", beanCorresponsal);
		
		beanConsultaOperaciones.setEntidad(Parametros.getParametroAplicacion(ENTIDAD_BANCO));
		beanConsultaOperaciones.setCanal(beanCorresponsal.getCodigoCorresponsalia());
		beanConsultaOperaciones.setTipoConsulta("L");
		beanConsultaOperaciones.setNivelParametria("02");
		beanConsultaOperaciones.setIndicadorPag("N");
		beanConsultaOperaciones.setIdCorresponsal(beanCorresponsal.getCodigoCorresponsal());
		
		try{
			beanResultadoOperaciones = bOCorresponsalesComisiones.consultaOperaciones(beanConsultaOperaciones, getArchitechBean());
			if(beanResultadoOperaciones.getNumeroRegistros() > 0){
				mapParametros.put("listaOperacionesCatalogo" , beanResultadoOperaciones.getRegistros());
			}
		}
		catch(BusinessException e){
			showException(e);		
		}
			return new ModelAndView("AsignacionComisiones", mapParametros);
		}
		mapParametros.put(COD_AVISO, "DLE001");

		mapParametros.put(MSG_AVISO, "Servicio No Disponible");

		return new ModelAndView("ConsultaComisiones", mapParametros);
	}
	
	/**
	 * Realiza asignacion comision.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="RealizaAsignacionComision.do")
	public ModelAndView realizaAsignacionComision(HttpServletRequest req, HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		info("Inicia Realiza Asignacion Comision");
		
		final HashMap<String,Object>     mapParametros        = new HashMap<String,Object>();
		final BeanAltaModificaComisiones beanAltaModificacion = new BeanAltaModificaComisiones();
		BeanError                  beanRespuesta        = new BeanError();
		
		req.removeAttribute(COD_AVISO);
		req.removeAttribute(MSG_AVISO);
		req.removeAttribute(COD_ERROR);
		req.removeAttribute(MSG_ERROR);
		
		final String codigoCorresponsal   = req.getParameter(CMB_NOMBRE_CORR) == null ? CADENAVACIA : req.getParameter(CMB_NOMBRE_CORR);
		final String codigoOperacion      = req.getParameter(CMB_NOMBRE_OPER) == null ? CADENAVACIA : req.getParameter(CMB_NOMBRE_OPER);
		final String montoCliente         = req.getParameter("txtMontoCliente") == null ? CADENAVACIA : req.getParameter("txtMontoCliente");
		final String montoBanco           = req.getParameter("txtMontoBanco") == null ? CADENAVACIA : req.getParameter("txtMontoBanco");
		final String total                = req.getParameter("txtTotal") == null ? CADENAVACIA : req.getParameter("txtTotal");
		String lstrCodErrorPage     = "";
		String lstrMsgErrorPage     = "";
		
		debug("codigoCorresponsalia:" + codigoCorresponsal);
		debug("montoCliente        :" + montoCliente);
		debug("montoBanco          :" + montoBanco);
		debug("total               :" + total);

		//req.setAttribute(CMB_NOMBRE_CORR, codigoCorresponsal);
		//req.setAttribute(CORRESP_SEL, codigoCorresponsal);
		
		beanAltaModificacion.setIdentificacionCorresponsal(codigoCorresponsal);
		beanAltaModificacion.setClaveReferenteTipoOperacion(codigoOperacion);
		beanAltaModificacion.setImporteComisionClienteMontoFijoFront(montoCliente);
		beanAltaModificacion.setImporteComisionBancoMontoFijoFront(montoBanco);
		beanAltaModificacion.setImporteComisionTotalFront(total);
		beanAltaModificacion.setTipoComision("M");
		beanAltaModificacion.setEntidad(Parametros.getParametroAplicacion(ENTIDAD_BANCO));
		beanAltaModificacion.setCanalCorresponsal(Parametros.getParametroAplicacion(CORRESPONSALIA));
		beanAltaModificacion.setNivelComision("02");
		beanAltaModificacion.setTipoOperacion("A"); 
		beanAltaModificacion.setInicioVigencia(Utils.getFechaSys());
		
		try{
			beanRespuesta = bOCorresponsalesComisiones.altaModificaComisiones(beanAltaModificacion, getArchitechBean());
			mapParametros.put(CORRESP_SEL, codigoCorresponsal);
			lstrCodErrorPage = beanRespuesta.getCodigoError();
			lstrMsgErrorPage = beanRespuesta.getMsgError();
			//mapParametros.put(COD_AVISO, lstrCodErrorPage);
			//mapParametros.put(MSG_AVISO, lstrMsgErrorPage);
			req.setAttribute(COD_AVISO, lstrCodErrorPage);
			req.setAttribute(MSG_AVISO, lstrMsgErrorPage);
		
		}
		catch(BusinessException e){
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			//mapParametros.put(COD_ERROR, lstrCodErrorPage);
			//mapParametros.put(MSG_ERROR, lstrMsgErrorPage);			
			//return new ModelAndView("AltaModificacionComisiones", mapParametros);
			req.setAttribute(COD_ERROR, lstrCodErrorPage);
			req.setAttribute(MSG_ERROR, lstrMsgErrorPage);
		}
		
		
		return consultaComisiones(req, res);
	}
	
	/**
	 * Exportar comisiones.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="ExportarComisiones.do")
	public ModelAndView exportarComisiones(HttpServletRequest req, HttpServletResponse res){
		final HttpSession             session        = req.getSession(); 
		final HashMap<String, Object> map            = new HashMap<String, Object>();
		final List<BeanComisiones>    listaRegistros =  (List<BeanComisiones>) session.getAttribute(REG_COM);		
		final String                  idCorresponsal       = req.getParameter(CMB_NOMBRE_CORR) == null ? CADENAVACIA : req.getParameter(CMB_NOMBRE_CORR);
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final List<BeanCorresponsal>  listaCorresponsales  = (List<BeanCorresponsal>)req.getSession().getAttribute(LISTA_COR_COM);
		BeanCorresponsal        beanCorresponsal     = new BeanCorresponsal();
		
		for(BeanCorresponsal bCorresponsalTemp:listaCorresponsales){
			if(bCorresponsalTemp.getCodigoCorresponsal().equals(idCorresponsal)){
				debug("Corresponsal encontrado en la lista de corresponsales");
				beanCorresponsal = bCorresponsalTemp;
				break;
			}
		}		
		if(listaRegistros==null ||( listaRegistros != null && listaRegistros.isEmpty())){
			final HashMap<String,Object>  lhsmParametros   = new HashMap<String,Object>();
			lhsmParametros.put(COD_ERROR, "NOEXPORT");
			lhsmParametros.put(MSG_ERROR, "NO HAY REGISTROS PARA EXPORTAR");
			return new ModelAndView("MonitoreoCredito",lhsmParametros);			
		}
		final JRDataSource dataSource = new JRBeanArrayDataSource(listaRegistros.toArray());
		final DateFormat Dformat = new  SimpleDateFormat("yyyy/MM/dd HH:mm:ss aa",new Locale("ES","MX")); 
		map.put("fechaReporte", Dformat.format(java.util.Calendar.getInstance().getTime()));
		map.put("nombreCorr", beanCorresponsal.getNombreCorresponsal());
		return new ModelAndView(new ExportaXLS("comisiones","/com/isban/corresponsalia/reportes/doctos/comisiones.jasper",dataSource), map);	
	}
}

