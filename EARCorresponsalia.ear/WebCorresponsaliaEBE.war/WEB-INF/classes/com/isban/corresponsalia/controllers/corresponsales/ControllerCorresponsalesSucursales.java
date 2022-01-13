package com.isban.corresponsalia.controllers.corresponsales;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.isban.corresponsalia.beans.comunes.BeanSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoSucursal;
import com.isban.corresponsalia.bo.corresponsales.BOCorresponsalesSucursales;
import com.isban.ebe.commons.architech.ArchitechEBE;

// TODO: Auto-generated Javadoc
/**
 * The Class ControllerCorresponsalesSucursales.
 */
@Controller
public class ControllerCorresponsalesSucursales extends ArchitechEBE{
	
	/** The cadena vacia. */
	private static final String CADENA_VACIA = "";
	
	/** The Constant CMB_CRRESPONSAL. */
	private static final String CMB_CRRESPONSAL ="cmbNombreCorresponsal";
	
	/** The Constant REG_CORRESPONSALES. */
	private static final String REG_CORRESPONSALES ="registrosCorresponsales";
	
	/** The Constant COD_ERROR. */
	private static final String COD_ERROR="codPagError";
	
	/** The Constant MSG_ERROR. */
	private static final String MSG_ERROR="msgPageError";
	
	/** The Constant CONSULTA_SUCURSAL. */
	private static final String CONSULTA_SUCURSAL="ConsultaSucursal";
	
	/** The Constant ENT. */
	private static final String ENT="ent";
	
	/** The Constant CAN. */
	private static final String CAN="can";
	
	/** The Constant NIV_PAR. */
	private static final String NIV_PAR="nivPar";
	
	/** The Constant ENTIDAD. */
	private static final String ENTIDAD="Entidad"; 
	
	/** The Constant NIVEL_PARAMETRIA. */
	private static final String NIVEL_PARAMETRIA="Nivel de Parametria :";
	
	/** The Constant TIPO_OPERACION. */
	private static final String TIPO_OPERACION="tipoOperacion";
	
	/** The Constant TIPO_CONSULTA. */
	private static final String TIPO_CONSULTA="tipoConsulta";
	
	/** The Constant NOMBRE_CORRESPONSALIA. */
	private static final String NOMBRE_CORRESPONSALIA="nombreCorresponsalia";
	
	/** The Constant ID_SUCURSAL. */
	private static final String ID_SUCURSAL="idSucursal";
	
	/** The Constant TXT_TIPO_OPERACION. */
	private static final String TXT_TIPO_OPERACION="Tipo Operacion:";
	
	/** The Constant TXT_TIPO_CONSULTA. */
	private static final String TXT_TIPO_CONSULTA="Tipo Consulta :";
	
	/** The Constant TXT_LLAMA. */
	private static final String TXT_LLAMA="Llama BO Corresponsalia";
	
	/** The Constant TXT_REGRESO. */
	private static final String TXT_REGRESO="Regreso BO Corresponsalia :";
	
	/** The Constant ID_CORRESPONSAL. */
	private static final String ID_CORRESPONSAL="idCorresponsal";
	
	/** The Constant NUM_ID. */
	private static final String NUM_ID="numId";
	
	/** The Constant CODIGO_ESTATUS. */
	private static final String CODIGO_ESTATUS="codigoEstatus";
	
	/** The Constant RFC. */
	private static final String RFC="rfc";
	
	/** The Constant REGION. */
	private static final String REGION="region";
	
	/** The Constant ZONA_GEOGRAFICA. */
	private static final String ZONA_GEOGRAFICA="zonaGeografica";
	
	/** The Constant FRONTERIZA. */
	private static final String FRONTERIZA="fronteriza";
	
	/** The Constant NOMBRE_SUCURSAL. */
	private static final String NOMBRE_SUCURSAL="nombreSucursal";
	
	/** The Constant CODIGO_INTERNO. */
	private static final String CODIGO_INTERNO="codigoInterno";
	
	/** The Constant CALLE. */
	private static final String CALLE="calle";
	
	/** The Constant NO_EXTERIOR. */
	private static final String NO_EXTERIOR="noExterior";
	
	/** The Constant NO_INTERIOR. */
	private static final String NO_INTERIOR="noInterior";
	
	/** The Constant COLONIA. */
	private static final String COLONIA="colonia";
	
	/** The Constant DELEGACION. */
	private static final String DELEGACION="delegMunicipio";
	
	/** The Constant CIUDAD. */
	private static final String CIUDAD="ciudad";
	
	/** The Constant CODIGO_POSTAL. */
	private static final String CODIGO_POSTAL="codigoPostal";
	
	/** The Constant TELEFONO. */
	private static final String TELEFONO="telefono";
	
	/** The Constant TXT_ENTIDAD. */
	private static final String TXT_ENTIDAD = "Entidad             :";
	
	/** The Constant TXT_CANAL. */
	private static final String TXT_CANAL = "Canal               :";
	
	/** The Constant TXT_ESTADO. */
	private static final String TXT_ESTADO = "estado";
	
	/** The Constant TXT_ENCALLES. */
	private static final String TXT_ENCALLES = "entreCalles";
	
	/** The Constant TXT_DESCZON. */
	private static final String TXT_DESCZON = "descZona";
	
	
		
	
	/** The b o corresponsales sucursales. */
	private transient BOCorresponsalesSucursales bOCorresponsalesSucursales;

	
	/**
	 * Sets the bO corresponsales sucursales.
	 *
	 * @param bOCorresponsalesSucursales the new bO corresponsales sucursales
	 */
	public void setBOCorresponsalesSucursales(BOCorresponsalesSucursales bOCorresponsalesSucursales){
		this.bOCorresponsalesSucursales = bOCorresponsalesSucursales;
	}	
	
	//private BeanComunCorresponsalia beanCorresponsal;
	
	/**
	 * Consulta sucursal.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="ConsultaSucursal.do")
	public ModelAndView consultaSucursal(HttpServletRequest req,
			HttpServletResponse res) {
		final Map<String,Object> mapParametros = new HashMap<String,Object>();
		final HttpSession session = req.getSession();
		final BeanMttoConsultaCorresponsal beanConsultaCorresponsal = new BeanMttoConsultaCorresponsal();
		info("Inicia Consulta Comisiones Corresponsalia");
		final BeanConsultaSucursal beanConsulta = new BeanConsultaSucursal();
		
		final String nombreCorresponsal = req.getParameter(CMB_CRRESPONSAL) == null ? "0001" : 
			req.getParameter(CMB_CRRESPONSAL);
		final String entidad = req.getParameter(ENTIDAD) == null ? CADENA_VACIA : 
			req.getParameter(ENTIDAD);
		final String canal = req.getParameter("canal") == null ? CADENA_VACIA : 
			req.getParameter("canal");
		
		debug("Nombre de Corresponsal:"+nombreCorresponsal);
		debug("Entidad:"+entidad);
		debug("Canal:"+canal);
		
		beanConsultaCorresponsal.setCodigoCorresponsalia("14");
		beanConsultaCorresponsal.setTipoConsulta("L");
		beanConsultaCorresponsal.setIndicadorPaginacion("N");
		
		beanConsulta.setCodigoCorresponsalia(nombreCorresponsal);
		beanConsulta.setIndicadorFuncional("L");
		beanConsulta.setIndicadorPaginacion("N");
		
		info("Llama BO Consulta Sucursales");
		final BeanResultadoSucursal beanRegresoSucursales = bOCorresponsalesSucursales.consultaSucursalCorresponsalia(beanConsulta, getArchitechBean());
		mapParametros.put("registrosSucursales", beanRegresoSucursales.getRegistros());
		mapParametros.put("consultaSucursalesCorresponsal", true);

		if(session.getAttribute(REG_CORRESPONSALES) != null){
			final BeanResultadoCorresponsalia beanRegresoCorresponsalias = (BeanResultadoCorresponsalia)session.getAttribute(REG_CORRESPONSALES);
			mapParametros.put("listaCorresponsales", beanRegresoCorresponsalias.getRegistros());
			final String corresponsaliaSeleccionada = req.getParameter(CMB_CRRESPONSAL) == null ? CADENA_VACIA : req.getParameter(CMB_CRRESPONSAL);
			mapParametros.put("corresponsaliaSeleccionada", corresponsaliaSeleccionada);	
		}		
		
		mapParametros.put(COD_ERROR , beanRegresoSucursales.getCodError());
		mapParametros.put(MSG_ERROR , beanRegresoSucursales.getMsgError());		
		
		
		info("Fin Consulta Sucursales Corresponsalia");
		return new ModelAndView(CONSULTA_SUCURSAL, mapParametros);
	}	

	/**
	 * Muestra corresponsales sucursales.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="MuestraCorresponsalesSucursales.do")
	public ModelAndView muestraCorresponsalesSucursales(HttpServletRequest req,
			HttpServletResponse res) {
		final HttpSession session = req.getSession();
		final Map<String,Object> mapParametros = new HashMap<String,Object>();
		final BeanMttoConsultaCorresponsal beanConsultaCorresponsal = new BeanMttoConsultaCorresponsal();
		info("Inicia Consulta Sucursales Corresponsalia");
		
		final String codigoCorresponsalia = req.getParameter("codigoCorresponsal") == null ? "0014" : 
			req.getParameter("codigoCorresponsal");
		final String nombreCorresponsal = req.getParameter("nombreCorresponsal") == null ? CADENA_VACIA : 
			req.getParameter("nombreCorresponsal");
		final String descripcionOperacion = req.getParameter("descripcionOperacion") == null ? CADENA_VACIA : 
			req.getParameter("descripcionOperacion");
		info("codigoCorresponsalia" + codigoCorresponsalia);
		info("nombreCorresponsal" + nombreCorresponsal);
		info("descripcionOperacion" + descripcionOperacion);
		
		// Pendiente de donde sale el codigo para la consulta
		beanConsultaCorresponsal.setCodigoCorresponsalia("14");
		
		beanConsultaCorresponsal.setTipoConsulta("L");
		beanConsultaCorresponsal.setIndicadorPaginacion("N");
		session.removeAttribute(REG_CORRESPONSALES);
		
		info("Llama BO Consulta Corresponsales");
		final BeanResultadoCorresponsalia beanRegresoCorresponsalias = bOCorresponsalesSucursales.consultaCorresponsales(beanConsultaCorresponsal, getArchitechBean());
		mapParametros.put("listaCorresponsales", beanRegresoCorresponsalias.getRegistros());
		mapParametros.put("consultaSucursalesCorresponsal", false);
		session.setAttribute(REG_CORRESPONSALES, beanRegresoCorresponsalias);
		info("Regreso BO Consulta Corresponsales");

		
		info("Fin Consulta Sucursales Corresponsalia");
		return new ModelAndView(CONSULTA_SUCURSAL, mapParametros);
	}		
	
	
	
	/**
	 * Muestra alta modificacion sucursales.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="ABModificacionSucursales.do")
	public ModelAndView muestraAltaModificacionSucursales(HttpServletRequest req,
			HttpServletResponse res) {
		
		final  Map<String,Object> mapParametros = new HashMap<String,Object>();
		
		String operacion1 = req.getParameter("sucursal");
		
		if(operacion1 == null) {
			operacion1 = "inicial";
		}
		
		final BeanMttoConsultaCorresponsal beanConsulta = new BeanMttoConsultaCorresponsal();
		beanConsulta.setCodigoCorresponsalia("14");
		beanConsulta.setTipoConsulta("L");
		beanConsulta.setIndicadorPaginacion("N");		
		final BeanResultadoCorresponsalia salida = bOCorresponsalesSucursales.consultaCorresponsales(beanConsulta, getArchitechBean());
		debug("Codigo de Error: <" + salida.getCodError() + ">");
		if("".equals(salida.getCodError())) {
			mapParametros.put("lista", salida.getRegistros());
			debug("Cantidad de corresponsales: " + salida.getRegistros().size());
		}		
		String operacion = req.getParameter("oper") == null ? CADENA_VACIA : 
			req.getParameter("oper");
		
		if("A".equals(operacion)){
			return new ModelAndView("AltaSucursal", mapParametros);
		}
		if("M".equals(operacion)){
			return new ModelAndView("ModificacionSucursal");
		}
		if("B".equals(operacion)){
			return new ModelAndView("BajaSucursal");
		}
		else{	
			return new ModelAndView(CONSULTA_SUCURSAL);
		}
	}

	/**
	 * DO para consulta Mantenimiento de Corresponsalia.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="ConsultaMttoSucursal.do")
	public ModelAndView consultaMttoCorresponsales(HttpServletRequest req,
			HttpServletResponse res){
		info("Inicia Consulta de Sucursales");
		final Map<String,Object> mapParametros = new HashMap<String,Object>();
		
		final BeanConsultaSucursal beanConsulta = new BeanConsultaSucursal();
		final String entidad = req.getParameter(ENT) == null ? CADENA_VACIA : 
			req.getParameter(ENT);
		final String canal = req.getParameter(CAN) == null ? CADENA_VACIA : 
			req.getParameter(CAN);
		final String nivelParametria = req.getParameter(NIV_PAR) == null ? CADENA_VACIA : 
			req.getParameter(NIV_PAR);
		
		debug(TXT_ENTIDAD + entidad);
		debug(TXT_CANAL+ canal);
		debug(NIVEL_PARAMETRIA + nivelParametria);
		//Eliminar Info
		info(TXT_ENTIDAD + entidad);
		info(TXT_CANAL+ canal);
		info(NIVEL_PARAMETRIA + nivelParametria);
		beanConsulta.setCodigoCorresponsalia("22");
		beanConsulta.setIndicadorPaginacion("N");

		
		//setde parametros
		info("Llama BO Mantenimiento Sucursal");
		final BeanResultadoSucursal beanRegreso = bOCorresponsalesSucursales.consultaSucursalCorresponsalia(beanConsulta, getArchitechBean());
		info("Regreso BO Mantenimiento Sucursal :" + beanRegreso.getRegistros().size());

		mapParametros.put(COD_ERROR , beanRegreso.getCodError());
		mapParametros.put(MSG_ERROR , beanRegreso.getMsgError());
		mapParametros.put("registros" , beanRegreso.getRegistros().iterator());
		
		if (beanRegreso.getNumeroRegistros() == 0) {
			info("Finaliza Consulta Operaciones Corresponsalia No Existe Registros");
			//return new ModelAndView("AltaCorresponsalOperaciones", mapParametros);
		}
			
		
		info("Finaliza Consulta Sucursal");
		return new ModelAndView(CONSULTA_SUCURSAL, mapParametros);
	}
	
	/**
	 * DO para consulta Mantenimiento Detalle de Corresponsalia.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="ModificarSucursal.do")
	public ModelAndView modificarSucursal(HttpServletRequest req,
			HttpServletResponse res) {
		info("Inicia Consulta Detalle a Modificar Sucursal");
		final Map<String,Object> mapParametros = new HashMap<String,Object>();
		BeanSucursal beanResultado;
		final BeanConsultaSucursal beanConsulta = new BeanConsultaSucursal();
		final String entidad = req.getParameter(ENT) == null ? CADENA_VACIA : 
			req.getParameter(ENT);
		final String canal = req.getParameter(CAN) == null ? CADENA_VACIA : 
			req.getParameter(CAN);
		final String nivelParametria = req.getParameter(NIV_PAR) == null ? CADENA_VACIA : 
			req.getParameter(NIV_PAR);
		final String tipoOperacion = req.getParameter(TIPO_OPERACION) == null ? CADENA_VACIA : 
			req.getParameter(TIPO_OPERACION);
		final String tipoConsulta = req.getParameter(TIPO_CONSULTA) == null ? CADENA_VACIA : 
			req.getParameter(TIPO_CONSULTA);
		final String nombreCorresponsalia = req.getParameter(NOMBRE_CORRESPONSALIA) == null ? "OXXO" : 
			req.getParameter(NOMBRE_CORRESPONSALIA);
		
		final String codigoCorresponsalia = req.getParameter(CMB_CRRESPONSAL) == null ? "0001" : 
			req.getParameter(CMB_CRRESPONSAL);	
		final String idStatusSuc = req.getParameter(ID_SUCURSAL) == null ? "0000000001" : 
			req.getParameter(ID_SUCURSAL);
		
		debug(TXT_ENTIDAD + entidad);
		debug(TXT_CANAL+ canal);
		debug(NIVEL_PARAMETRIA + nivelParametria);
		debug(TXT_TIPO_OPERACION + tipoOperacion);
		debug(TXT_TIPO_CONSULTA + tipoConsulta);
		//Eliminar Info
		info(TXT_ENTIDAD + entidad);
		info(TXT_CANAL+ canal);
		info(NIVEL_PARAMETRIA + nivelParametria);
		info(TXT_TIPO_OPERACION + tipoOperacion);
		info(TXT_TIPO_CONSULTA + tipoConsulta);
		
		beanConsulta.setCodigoCorresponsalia(codigoCorresponsalia);
		beanConsulta.setCodigoSucursal(idStatusSuc);
		beanConsulta.setIndicadorPaginacion("N");
		beanConsulta.setIndicadorFuncional("D");

		info(TXT_LLAMA);
		final BeanResultadoSucursal beanRegreso = bOCorresponsalesSucursales.consultaSucursalCorresponsalia(beanConsulta, getArchitechBean());
		info(TXT_LLAMA);
		info(TXT_REGRESO + beanRegreso.getRegistros().size());

		mapParametros.put(COD_ERROR , beanRegreso.getCodError());
		mapParametros.put(MSG_ERROR , beanRegreso.getMsgError());
		
		if(beanRegreso.getRegistros().size() > 0 ) {
			beanResultado =  beanRegreso.getRegistros().get(0);	
			mapParametros.put(NOMBRE_CORRESPONSALIA,nombreCorresponsalia);
			mapParametros.put(ID_CORRESPONSAL,beanResultado.getIdCorresponsal());
			mapParametros.put(NUM_ID,beanResultado.getNumId());
			mapParametros.put(CODIGO_ESTATUS,beanResultado.getCodigoEstatus());
			mapParametros.put("descEstatus",beanResultado.getDescEstatus());
			mapParametros.put(RFC,beanResultado.getRfc());
			mapParametros.put(REGION,beanResultado.getRegion());
			mapParametros.put(ZONA_GEOGRAFICA,beanResultado.getZonaGeografica());
			mapParametros.put(FRONTERIZA,beanResultado.getFronteriza());
			mapParametros.put(NOMBRE_SUCURSAL,beanResultado.getNombreSucursal());
			mapParametros.put(CODIGO_INTERNO,beanResultado.getCodigoInterno());
			mapParametros.put(CALLE,beanResultado.getCalle());
			mapParametros.put(NO_EXTERIOR,beanResultado.getNoExterior());
			mapParametros.put(NO_INTERIOR,beanResultado.getNoInterior());
			mapParametros.put(COLONIA,beanResultado.getColonia());
			mapParametros.put(DELEGACION,beanResultado.getDelegMunicipio());
			mapParametros.put(CIUDAD,beanResultado.getCiudad());
			mapParametros.put(TXT_ESTADO,beanResultado.getEstado());
			mapParametros.put(CODIGO_POSTAL,beanResultado.getCodigoPostal());
			mapParametros.put(TELEFONO,beanResultado.getTelefono());
			info("DETALLE -- Nombre de Sucursal :" + beanResultado.getNombreSucursal());
			
		}else{
			info("Finaliza Consulta a Detalle de Sucursales, No Existen Registros");
			req.setAttribute("codigoError", beanRegreso.getCodError());
			req.setAttribute("descError", beanRegreso.getMsgError());
			return consultaSucursal(req, res);			
		}
			
		info("Finaliza Consulta Detalle a Modificar Sucursal");
		return new ModelAndView("ModificarSucursal", mapParametros);
	}		
	
	/**
	 * DO para consulta Mantenimiento Detalle de Corresponsalia.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="DetalleSucursal.do")
	public ModelAndView detalleSucursal(HttpServletRequest req,
			HttpServletResponse res){
		info("Inicia Consulta Mantenimiento DetalleSucursales");
		final Map<String,Object> mapParametros = new HashMap<String,Object>();
		BeanSucursal beanResultado;
		final BeanConsultaSucursal beanConsulta = new BeanConsultaSucursal();
		final String entidad = req.getParameter(ENT) == null ? CADENA_VACIA : 
			req.getParameter(ENT);
		final String canal = req.getParameter(CAN) == null ? CADENA_VACIA : 
			req.getParameter(CAN);
		final String nivelParametria = req.getParameter(NIV_PAR) == null ? CADENA_VACIA : 
			req.getParameter(NIV_PAR);
		final String tipoOperacion = req.getParameter(TIPO_OPERACION) == null ? CADENA_VACIA : 
			req.getParameter(TIPO_OPERACION);
		final String tipoConsulta = req.getParameter(TIPO_CONSULTA) == null ? CADENA_VACIA : 
			req.getParameter(TIPO_CONSULTA);
		final String nombreCorresponsalia = req.getParameter(NOMBRE_CORRESPONSALIA) == null ? "OXXO" : 
			req.getParameter(NOMBRE_CORRESPONSALIA);
		
		final String codigoCorresponsalia = req.getParameter(CMB_CRRESPONSAL) == null ? "0001" : 
			req.getParameter(CMB_CRRESPONSAL);	
		final String idStatusSuc = req.getParameter(ID_SUCURSAL) == null ? "0000000001" : 
			req.getParameter(ID_SUCURSAL);
		
		debug(TXT_ENTIDAD + entidad);
		debug(TXT_CANAL+ canal);
		debug(NIVEL_PARAMETRIA + nivelParametria);
		debug(TXT_TIPO_OPERACION + tipoOperacion);
		debug(TXT_TIPO_CONSULTA + tipoConsulta);
		//Eliminar Info
		info(TXT_ENTIDAD + entidad);
		info(TXT_CANAL+ canal);
		info(NIVEL_PARAMETRIA + nivelParametria);
		info(TXT_TIPO_OPERACION + tipoOperacion);
		info(TXT_TIPO_CONSULTA + tipoConsulta);
		
		beanConsulta.setCodigoCorresponsalia(codigoCorresponsalia);
		beanConsulta.setCodigoSucursal(idStatusSuc);
		beanConsulta.setIndicadorPaginacion("N");
		beanConsulta.setIndicadorFuncional("D");

		info(TXT_LLAMA);
		final BeanResultadoSucursal beanRegreso = bOCorresponsalesSucursales.consultaSucursalCorresponsalia(beanConsulta, getArchitechBean());
		info(TXT_LLAMA);
		info(TXT_REGRESO + beanRegreso.getRegistros().size());

		mapParametros.put(COD_ERROR , beanRegreso.getCodError());
		mapParametros.put(MSG_ERROR , beanRegreso.getMsgError());
		
		if(beanRegreso.getRegistros().size() > 0 ) {
			beanResultado =  beanRegreso.getRegistros().get(0);	
			mapParametros.put(NOMBRE_CORRESPONSALIA,nombreCorresponsalia);
			mapParametros.put(ID_CORRESPONSAL,beanResultado.getIdCorresponsal());
			mapParametros.put(NUM_ID,beanResultado.getNumId());
			mapParametros.put(CODIGO_ESTATUS,beanResultado.getCodigoEstatus());
			mapParametros.put("descEstatus",beanResultado.getDescEstatus());
			mapParametros.put(RFC,beanResultado.getRfc());
			mapParametros.put(REGION,beanResultado.getRegion());
			mapParametros.put(ZONA_GEOGRAFICA,beanResultado.getZonaGeografica());
			mapParametros.put(FRONTERIZA,beanResultado.getFronteriza());
			mapParametros.put(NOMBRE_SUCURSAL,beanResultado.getNombreSucursal());
			mapParametros.put(CODIGO_INTERNO,beanResultado.getCodigoInterno());
			mapParametros.put(CALLE,beanResultado.getCalle());
			mapParametros.put(NO_EXTERIOR,beanResultado.getNoExterior());
			mapParametros.put(NO_INTERIOR,beanResultado.getNoInterior());
			mapParametros.put(COLONIA,beanResultado.getColonia());
			mapParametros.put(DELEGACION,beanResultado.getDelegMunicipio());
			mapParametros.put(CIUDAD,beanResultado.getCiudad());
			mapParametros.put(TXT_ESTADO,beanResultado.getEstado());
			mapParametros.put(CODIGO_POSTAL,beanResultado.getCodigoPostal());
			mapParametros.put(TELEFONO,beanResultado.getTelefono());
			info("DETALLE -- Nombre de Sucursal :" + beanResultado.getNombreSucursal());
			
		}else{
			info("Finaliza Consulta a Detalle de Sucursales, No Existen Registros");
			req.setAttribute("codigoError", beanRegreso.getCodError());
			req.setAttribute("descError", beanRegreso.getMsgError());
			return consultaSucursal(req, res);			
		}
			
		info("Finaliza Consulta a Detalle de Sucursales");
		return new ModelAndView("DetalleSucursal", mapParametros);
		//return new ModelAndView(CONSULTA_SUCURSAL, mapParametros);
	}	

	/**
	 * Eliminar sucursal.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="EliminarSucursal.do")
	public ModelAndView eliminarSucursal(HttpServletRequest req,
			HttpServletResponse res) {
		final Map<String,Object> mapParametros = new HashMap<String,Object>();
		info("Inicia Eliminar Sucursal");
		
		final BeanSucursal beanBaja = new BeanSucursal();
		final String idCorresponsal = req.getParameter("idCorresponsalia") == null ? CADENA_VACIA : 
			req.getParameter("idCorresponsalia");
		final String nombreSucursal = req.getParameter(NOMBRE_SUCURSAL) == null ? CADENA_VACIA : 
			req.getParameter(NOMBRE_SUCURSAL);
		final String numId = req.getParameter(ID_SUCURSAL) == null ? "0" : 
			req.getParameter(ID_SUCURSAL);
		
		String codigoEstatus = req.getParameter(CODIGO_ESTATUS);
		String descEstatus = "";
		if(codigoEstatus == null) {
			descEstatus = req.getParameter("descEstatus");
			
			if(descEstatus.startsWith("Imp")) {
				codigoEstatus = "CS1";
			} else if(descEstatus.startsWith("Act")) {
				codigoEstatus = "CS2";
			} else if(descEstatus.startsWith("Blo")) {
				codigoEstatus = "CS3";
			} else if(descEstatus.startsWith("Baj")) {
				codigoEstatus = "CS4";
			} else if(descEstatus.startsWith(CAN)) {
				codigoEstatus = "CS5";
			} else {
				codigoEstatus = "CS1";
			}
		}		

		String idOperacion= "B";

		
		info("Id Corresponsal		:" + idCorresponsal);
		info("Nombre Sucursal		:" + nombreSucursal);
		info("Numero Identificacion	:" + numId);
		info("Operacion				:" + idOperacion);
	
		// COLOCAMOS EL VALOR DE LOS CAMPOS EN EL BEAN SUCURSAL
		beanBaja.setIdCorresponsal(idCorresponsal);
		beanBaja.setCodigoEstatus(codigoEstatus);
		beanBaja.setNumId(numId);
		beanBaja.setIdOperacion(idOperacion);
		beanBaja.setFronteriza("N");
		
		
		info(TXT_LLAMA);
		final BeanResultadoSucursal beanRegreso = 
			bOCorresponsalesSucursales.altaSucursalCorresponsalia(beanBaja, getArchitechBean());
		info(TXT_REGRESO + beanRegreso.getCodError());

		mapParametros.put("codRegreso" , beanRegreso.getCodError());
		mapParametros.put("msgRegreso" , beanRegreso.getMsgError());
		
		req.setAttribute("codigoError", beanRegreso.getCodError());
		req.setAttribute("descError", beanRegreso.getMsgError());
		
		info("Finaliza Eliminar Sucursal");	
		return muestraCorresponsalesSucursales(req, res);
	}			
	
	
	/**
	 * Modifica sucursal envio.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="ModificaSucursalEnvio.do")
	public ModelAndView modificaSucursalEnvio(HttpServletRequest req,
			HttpServletResponse res) {
		final Map<String,Object> mapParametros = new HashMap<String,Object>();
		info("Inicia Modificacion Sucursal");
		
		final BeanSucursal beanModifica = new BeanSucursal();
		final String idCorresponsal = req.getParameter("codigoCorresponsalia") == null ? CADENA_VACIA : 
			req.getParameter("codigoCorresponsalia");
		final String nombreSucursal = req.getParameter(NOMBRE_SUCURSAL) == null ? CADENA_VACIA : 
			req.getParameter(NOMBRE_SUCURSAL);
		final String numId = req.getParameter(NUM_ID) == null ? "0" : 
			req.getParameter(NUM_ID);
		
		final String codigoEstatus = req.getParameter(CODIGO_ESTATUS);
		/*
		String descEstatus = "";
		if(codigoEstatus == null) {
			descEstatus = req.getParameter("descEstatus");
			
			if(descEstatus.startsWith("Imp")) {
				codigoEstatus = "CS1";
			} else if(descEstatus.startsWith("Act")) {
				codigoEstatus = "CS2";
			} else if(descEstatus.startsWith("Blo")) {
				codigoEstatus = "CS3";
			} else if(descEstatus.startsWith("Baj")) {
				codigoEstatus = "CS4";
			} else if(descEstatus.startsWith(CAN)) {
				codigoEstatus = "CS5";
			} else {
				codigoEstatus = "CS1";
			}
		}
			*/
		
		final String rfc = req.getParameter(RFC) == null ? CADENA_VACIA : 
			req.getParameter(RFC);
		final String codigoInterno = req.getParameter(CODIGO_INTERNO) == null ? CADENA_VACIA : 
			req.getParameter(CODIGO_INTERNO);
		final String telefono = req.getParameter(TELEFONO) == null ? CADENA_VACIA : 
			req.getParameter(TELEFONO);
		final String calle = req.getParameter(CALLE) == null ? CADENA_VACIA : 
			req.getParameter(CALLE);
		final String noInterior = req.getParameter(NO_INTERIOR) == null ? CADENA_VACIA : 
			req.getParameter(NO_INTERIOR);
		final String noExterior = req.getParameter(NO_EXTERIOR) == null ? CADENA_VACIA : 
			req.getParameter(NO_EXTERIOR);
		final String colonia= req.getParameter(COLONIA) == null ? CADENA_VACIA : 
			req.getParameter(COLONIA);
		final String entreCalles= req.getParameter(TXT_ENCALLES) == null ? CADENA_VACIA : 
			req.getParameter(TXT_ENCALLES);
		final String delegMunicipio= req.getParameter(DELEGACION) == null ? CADENA_VACIA : 
			req.getParameter(DELEGACION);
		final String ciudad= req.getParameter(CIUDAD) == null ? CADENA_VACIA : 
			req.getParameter(CIUDAD);
		final String estado= req.getParameter(TXT_ESTADO) == null ? CADENA_VACIA : 
			req.getParameter(TXT_ESTADO);
		final String codigoPostal= req.getParameter(CODIGO_POSTAL) == null ? CADENA_VACIA : 
			req.getParameter(CODIGO_POSTAL);
		final String fronteriza= req.getParameter(FRONTERIZA) == null ? CADENA_VACIA : 
			req.getParameter(FRONTERIZA);
		final String zonaGeografica= req.getParameter(ZONA_GEOGRAFICA) == null ? CADENA_VACIA : 
			req.getParameter(ZONA_GEOGRAFICA);
		final String descZona= req.getParameter(TXT_DESCZON) == null ? CADENA_VACIA : 
			req.getParameter(TXT_DESCZON);
		final String region= req.getParameter(REGION) == null ? CADENA_VACIA : 
			req.getParameter(REGION);
		final String idOperacion= "M";

		
		info("Id Corresponsal		:" + idCorresponsal);
		info("Nombre Sucursal		:" + nombreSucursal);
		info("Numero Identificacion	:" + numId);
		info("Codigo Estatus		:" + codigoEstatus);
		info("RFC					:" + rfc);
		info("Codigo Interno		:" + codigoInterno);
		info("Operacion				:" + idOperacion);
	
		// COLOCAMOS EL VALOR DE LOS CAMPOS EN EL BEAN SUCURSAL
		beanModifica.setIdCorresponsal(idCorresponsal);
		beanModifica.setNombreSucursal(nombreSucursal);
		beanModifica.setNumId(numId);
		beanModifica.setCodigoEstatus(codigoEstatus);
		beanModifica.setRfc(rfc);
		beanModifica.setCodigoInterno(codigoInterno);
		beanModifica.setTelefono(telefono);
		beanModifica.setCalle(calle);
		beanModifica.setNoInterior(noInterior);
		beanModifica.setNoExterior(noExterior);
		beanModifica.setColonia(colonia);
		beanModifica.setEntreCalles(entreCalles);
		beanModifica.setDelegMunicipio(delegMunicipio);
		beanModifica.setCiudad(ciudad);
		beanModifica.setEstado(estado);
		beanModifica.setCodigoPostal(codigoPostal);
		beanModifica.setFronteriza(fronteriza);
		beanModifica.setZonaGeografica(zonaGeografica);
		beanModifica.setDescZona(descZona);
		beanModifica.setRegion(region);
		beanModifica.setIdOperacion(idOperacion);
		
		
		info(TXT_LLAMA);
		final BeanResultadoSucursal beanRegreso = 
			bOCorresponsalesSucursales.altaSucursalCorresponsalia(beanModifica, getArchitechBean());
		info(TXT_REGRESO + beanRegreso.getCodError());

		mapParametros.put("codError" , beanRegreso.getCodError());
		mapParametros.put("msgError" , beanRegreso.getMsgError());
		
		req.setAttribute("codError", beanRegreso.getCodError());
		req.setAttribute("msgError", beanRegreso.getMsgError());
		
		info("Finaliza Modificacion Sucursal");	
		return muestraCorresponsalesSucursales(req, res);
	}		
	
	/**
	 * Alta sucursal.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="AltaSucursal.do")
	public ModelAndView altaSucursal(HttpServletRequest req,
			HttpServletResponse res) {
		final Map<String,Object> mapParametros = new HashMap<String,Object>();
		info("Inicia Alta Sucursal");		
		
		final BeanSucursal beanAlta = new BeanSucursal();
		final String idCorresponsal = req.getParameter(ID_CORRESPONSAL) == null ? CADENA_VACIA : 
			req.getParameter(ID_CORRESPONSAL);
		final String nombreSucursal = req.getParameter(NOMBRE_SUCURSAL) == null ? CADENA_VACIA : 
			req.getParameter(NOMBRE_SUCURSAL);
		final String numId = req.getParameter(NUM_ID) == null ? CADENA_VACIA : 
			req.getParameter(NUM_ID);
		final String codigoEstatus = req.getParameter(CODIGO_ESTATUS) == null ? CADENA_VACIA : 
			req.getParameter(CODIGO_ESTATUS);
		final String rfc = req.getParameter(RFC) == null ? CADENA_VACIA : 
			req.getParameter(RFC);
		final String codigoInterno = req.getParameter(CODIGO_INTERNO) == null ? CADENA_VACIA : 
			req.getParameter(CODIGO_INTERNO);
		final String telefono = req.getParameter(TELEFONO) == null ? CADENA_VACIA : 
			req.getParameter(TELEFONO);
		final String calle = req.getParameter(CALLE) == null ? CADENA_VACIA : 
			req.getParameter(CALLE);
		final String noInterior = req.getParameter(NO_INTERIOR) == null ? CADENA_VACIA : 
			req.getParameter(NO_INTERIOR);
		final String noExterior = req.getParameter(NO_EXTERIOR) == null ? CADENA_VACIA : 
			req.getParameter(NO_EXTERIOR);
		final String colonia= req.getParameter(COLONIA) == null ? CADENA_VACIA : 
			req.getParameter(COLONIA);
		final String entreCalles= req.getParameter(TXT_ENCALLES) == null ? CADENA_VACIA : 
			req.getParameter(TXT_ENCALLES);
		final String delegMunicipio= req.getParameter(DELEGACION) == null ? CADENA_VACIA : 
			req.getParameter(DELEGACION);
		final String ciudad= req.getParameter(CIUDAD) == null ? CADENA_VACIA : 
			req.getParameter(CIUDAD);
		final String estado= req.getParameter(TXT_ESTADO) == null ? CADENA_VACIA : 
			req.getParameter(TXT_ESTADO);
		final String codigoPostal= req.getParameter(CODIGO_POSTAL) == null ? CADENA_VACIA : 
			req.getParameter(CODIGO_POSTAL);
		final String fronteriza= req.getParameter(FRONTERIZA) == null ? CADENA_VACIA : 
			req.getParameter(FRONTERIZA);
		final String zonaGeografica= req.getParameter(ZONA_GEOGRAFICA) == null ? CADENA_VACIA : 
			req.getParameter(ZONA_GEOGRAFICA);
		final String descZona= req.getParameter(TXT_DESCZON) == null ? CADENA_VACIA : 
			req.getParameter(TXT_DESCZON);
		final String region= req.getParameter(REGION) == null ? CADENA_VACIA : 
			req.getParameter(REGION);
		final String idOperacion= "A";

		
		info("Id Corresponsal		:" + idCorresponsal);
		info("Nombre Sucursal		:" + nombreSucursal);
		info("Numero Identificacion	:" + numId);
		info("Codigo Estatus		:" + codigoEstatus);
		info("RFC					:" + rfc);
		info("Codigo Interno		:" + codigoInterno);
		info("Operacion				:" + idOperacion);
	
		// COLOCAMOS EL VALOR DE LOS CAMPOS EN EL BEAN SUCURSAL
		beanAlta.setIdCorresponsal(idCorresponsal);
		beanAlta.setNombreSucursal(nombreSucursal);
		beanAlta.setNumId(numId);
		beanAlta.setCodigoEstatus(codigoEstatus);
		beanAlta.setRfc(rfc);
		beanAlta.setCodigoInterno(codigoInterno);
		beanAlta.setTelefono(telefono);
		beanAlta.setCalle(calle);
		beanAlta.setNoInterior(noInterior);
		beanAlta.setNoExterior(noExterior);
		beanAlta.setColonia(colonia);
		beanAlta.setEntreCalles(entreCalles);
		beanAlta.setDelegMunicipio(delegMunicipio);
		beanAlta.setCiudad(ciudad);
		beanAlta.setEstado(estado);
		beanAlta.setCodigoPostal(codigoPostal);
		beanAlta.setFronteriza(fronteriza);
		beanAlta.setZonaGeografica(zonaGeografica);
		beanAlta.setDescZona(descZona);
		beanAlta.setRegion(region);
		beanAlta.setIdOperacion(idOperacion);
		
		
		info(TXT_LLAMA);
		final BeanResultadoSucursal beanRegreso = 
			bOCorresponsalesSucursales.altaSucursalCorresponsalia(beanAlta, getArchitechBean());
		info(TXT_REGRESO + beanRegreso.getCodError());

		mapParametros.put("codError" , beanRegreso.getCodError());
		mapParametros.put("msgError" , beanRegreso.getMsgError());
		
		info("Finaliza Alta Sucursal");	
		return consultaSucursal(req, res);
	}	
	
	
	/**
	 * Excepcion operaciones.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="ExcepcionSucursal.do")
	public ModelAndView excepcionOperaciones(HttpServletRequest req,
			HttpServletResponse res) {
		return new ModelAndView("ExcepcionSucursal");
	}

}