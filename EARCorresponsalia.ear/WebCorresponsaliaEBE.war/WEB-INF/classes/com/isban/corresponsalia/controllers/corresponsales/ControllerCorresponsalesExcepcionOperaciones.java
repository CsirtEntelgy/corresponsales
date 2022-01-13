package com.isban.corresponsalia.controllers.corresponsales;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanABMMantenimientoOperCorresponsal;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCorresponsal;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanResultadoOperacionesCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.comunes.BeanOperacion;
import com.isban.corresponsalia.beans.comunes.BeanSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoSucursal;
import com.isban.corresponsalia.bo.corresponsales.BOCorresponsalesOperaciones;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;


/**
 * The Class ControllerCorresponsalesExcepcionOperaciones.
 */
@Controller
public class ControllerCorresponsalesExcepcionOperaciones extends ArchitechEBE{

	/** Constant NEW_ARCH nueva sesion en arquitectura */
	private static final String NEW_ARCH = "NewArchitechSession"; 
	
	/** Constant TXT_LIST_SUC. Lista de sucurdsales */
	private static final String TXT_LIST_SUC = "listaSuc";
	
	/** Constant TXT_LISTA. Lista de sucurdsales */
	private static final String TXT_LISTA = "lista";
	
	/** Constant TXT_LISTA_COR. Lista de corresponsales */
	private static final String TXT_LISTA_COR = "listaCorresponsales";
	
	/** Constant TXT_RESP. mensaje de respuesta */
	private static final String TXT_RESP = "mensajeRespuesta";
	
	/** Constant TXT_EXC_OPER. excepcion de operaciones*/
	private static final String TXT_EXC_OPER = "ExcepcionOperaciones";
	
	/** Constant TXT_CORRESP. excepcion de operaciones*/
	private static final String TXT_CORRESP = "corresponsal";
	
	/** Constant TXT_COR_BUSC. corresponsal buscar*/
	private static final String TXT_COR_BUSC = "corresponsalBuscar";
	
	/** Constant TXT_SUC. sucursal*/
	private static final String TXT_SUC = "sucursal";
	
	/** Constant TXT_ENTIDAD. entidad*/
	private static final String TXT_ENTIDAD = "0014";

	/** Constant TXT_ASOCIAR. asociar*/
	private static final String TXT_ASOCIAR = "asociar";
	
	
	
	/** The BOCorresponsalesOperaciones. */
	transient private BOCorresponsalesOperaciones bOCorresponsalesOperaciones;
	
	/**
	 *  setBOCorresponsalesOperaciones.
	 * @param bOCorresponsalesOperaciones the new bO corresponsales operaciones
	 */
	public void setBOCorresponsalesOperaciones(BOCorresponsalesOperaciones bOCorresponsalesOperaciones){
		this.bOCorresponsalesOperaciones = bOCorresponsalesOperaciones;
	}
	
	/** 
     *  excepcionOperaciones.
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="ExcepcionOperacionesCorresponsal.do")
	public ModelAndView excepcionOperaciones(HttpServletRequest req,
			HttpServletResponse res){
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String,Object> mapParametros = new HashMap<String,Object>();		
		
		final BeanMttoConsultaCorresponsal beanConsulta = new BeanMttoConsultaCorresponsal();
		beanConsulta.setCodigoCorresponsalia("14");
		beanConsulta.setTipoConsulta("L");
		beanConsulta.setIndicadorPaginacion("N");		
		final BeanResultadoCorresponsalia salida = bOCorresponsalesOperaciones.consultaCorresponsales(beanConsulta, getArchitechBean());
		debug("Codigo de Error: <" + salida.getCodError() + ">");
		mapParametros.put(TXT_LIST_SUC, new ArrayList<BeanSucursal>());
		req.getSession().removeAttribute("msjRespuesta");
		if("".equals(salida.getCodError())) {
			mapParametros.put(TXT_LISTA, salida.getRegistros());
			req.getSession().setAttribute(TXT_LISTA_COR, salida.getRegistros());
			debug("--> Cantidad de corresponsales: " + salida.getRegistros().size());
		}
		else {
			mapParametros.put(TXT_RESP, salida.getCodError() + " : " + salida.getMsgError());			
		}
		
		return new ModelAndView(TXT_EXC_OPER, mapParametros);
	}	

	/**
	 * * Excepcion operaciones suc.
	 * *
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="ExcepcionOperacionesSuc.do")
	public ModelAndView excepcionOperacionesSuc(HttpServletRequest req,
			HttpServletResponse res) {
	
		final HashMap<String,Object> mapParametros = new HashMap<String,Object>();		
		
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		BeanConsultaSucursal beanConsulta = new BeanConsultaSucursal();
		beanConsulta.setIndicadorPaginacion("N");
		beanConsulta.setIndicadorFuncional("L");
		beanConsulta.setPaginada(false);
		beanConsulta.setCodigoCorresponsalia(req.getParameter(TXT_CORRESP));

		if(beanConsulta.getCodigoCorresponsalia().trim().length() == 0) {
			mapParametros.put(TXT_LISTA, req.getSession().getAttribute(TXT_LISTA_COR));
			mapParametros.put(TXT_LIST_SUC, new ArrayList<BeanSucursal>());
			mapParametros.put(TXT_COR_BUSC, req.getParameter(TXT_CORRESP));
		
			return new ModelAndView(TXT_EXC_OPER, mapParametros);
			
		}
		
		//setde parametros
		info("Llama BO Mantenimiento Sucursal");
		BeanResultadoSucursal beanRegreso = bOCorresponsalesOperaciones.consultaSucursalCorresponsalia(beanConsulta, getArchitechBean());
		info("Regreso BO Mantenimiento Sucursal :" + beanRegreso.getRegistros().size());
		
		mapParametros.put(TXT_LISTA, req.getSession().getAttribute(TXT_LISTA_COR));
		mapParametros.put(TXT_LIST_SUC, beanRegreso.getRegistros());
		mapParametros.put(TXT_COR_BUSC, req.getParameter(TXT_CORRESP));
		req.getSession().setAttribute(TXT_LIST_SUC, beanRegreso.getRegistros());
		
		return new ModelAndView(TXT_EXC_OPER, mapParametros);
	}	

	/**
	 * * Excepcion operaciones ope.
	 * *
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view

	 */
	@RequestMapping(value="ExcepcionOperacionesOpe.do")
	public ModelAndView excepcionOperacionesOpe(HttpServletRequest req,
			HttpServletResponse res){
	
		final HashMap<String,Object> mapParametros = new HashMap<String,Object>();		
		
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));		
		final String corresponsal = req.getParameter(TXT_CORRESP);
		final String sucursal = req.getParameter(TXT_SUC);
		
		BeanConsultaOperacionesCorresponsal beanConsultaOper = new BeanConsultaOperacionesCorresponsal();
		beanConsultaOper.setEntidad(TXT_ENTIDAD);
		beanConsultaOper.setCanal("14");
		beanConsultaOper.setNivelParametria("02");
		beanConsultaOper.setIdCorresponsal(corresponsal);
		beanConsultaOper.setCodigoSucursal(sucursal);
		beanConsultaOper.setTipoConsulta("L");
		final BeanResultadoOperacionesCorresponsal operaciones = bOCorresponsalesOperaciones.consultaOperacionesCorresponsal(beanConsultaOper, getArchitechBean());
		debug("Total de Operaciones: " + operaciones.getRegistros().size());

		
		final BeanConsultaOperacionesCorresponsal beanConsultaOperSuc = new BeanConsultaOperacionesCorresponsal();
		beanConsultaOperSuc.setEntidad(TXT_ENTIDAD);
		beanConsultaOperSuc.setCanal("14");
		beanConsultaOperSuc.setNivelParametria("03");
		beanConsultaOperSuc.setIdCorresponsal(corresponsal);
		beanConsultaOperSuc.setCodigoSucursal(sucursal);
		beanConsultaOperSuc.setTipoConsulta("L");
		final BeanResultadoOperacionesCorresponsal operacionesSuc = bOCorresponsalesOperaciones.consultaOperacionesCorresponsal(beanConsultaOperSuc, getArchitechBean());
		debug("Total de Operaciones Corresponsal: " + operacionesSuc.getRegistros().size());
		if (operaciones.getRegistros().size()<1){
			mapParametros.put(TXT_RESP, "No existen Operaciones disponible para el corresponsal");			
		}
		for(int i = 0; i < operacionesSuc.getRegistros().size(); i++) {
			final BeanOperacion opeCorr = operacionesSuc.getRegistros().get(i);
			for(int j = 0; j < operaciones.getRegistros().size(); j++) {
				final BeanOperacion ope = operaciones.getRegistros().get(j);
				if(opeCorr.getCodigoOperacion().equals(ope.getCodigoOperacion())) {
					operaciones.getRegistros().remove(j);
					j = operaciones.getRegistros().size();
				}
			}
		}
		
		mapParametros.put("operaciones", operaciones.getRegistros());
		mapParametros.put("operacionesSuc", operacionesSuc.getRegistros());

		mapParametros.put(TXT_LISTA, req.getSession().getAttribute(TXT_LISTA_COR));
		mapParametros.put(TXT_LIST_SUC, req.getSession().getAttribute(TXT_LIST_SUC));
		mapParametros.put("sucursalBuscar", req.getParameter(TXT_SUC));
		mapParametros.put(TXT_COR_BUSC, req.getParameter(TXT_CORRESP));
		
		return new ModelAndView(TXT_EXC_OPER, mapParametros);
	}	

	
	/**
	 *  excepcionOperacionesAsc Excepcion operaciones asc.
	 * @param req the req
	 * @param res the res
	 * @return the model and view 
	 */
	@RequestMapping(value="ExcepcionOperacionesAsc.do")
	public ModelAndView excepcionOperacionesAsc(HttpServletRequest req,
			HttpServletResponse res){
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String,Object> mapParametros = new HashMap<String,Object>();	
		final String codigoOperacionNoAsociado = req.getParameter("lbTmplNoAsoc");
		final String codigoOperacionAsociado = req.getParameter("lbTmplAsoc");
		final String operacion = req.getParameter("operacion");		
		final String corresponsal = req.getParameter(TXT_CORRESP);
		final String sucursal = req.getParameter(TXT_SUC);		
		String tipoOperacionCorresponsal ="";		
		final BeanConsultaOperacionesCorresponsal beanConsultaOper = new BeanConsultaOperacionesCorresponsal();
		
		beanConsultaOper.setEntidad(TXT_ENTIDAD);
		beanConsultaOper.setCanal("14");
		beanConsultaOper.setNivelParametria("02");
		beanConsultaOper.setIdCorresponsal(corresponsal);
		beanConsultaOper.setTipoConsulta("L");
		BeanResultadoOperacionesCorresponsal operaciones = bOCorresponsalesOperaciones.consultaOperacionesCorresponsal(beanConsultaOper, getArchitechBean());
		debug("Total de Operaciones: " + operaciones.getRegistros().size());

		final BeanConsultaOperacionesCorresponsal beanConsultaOperSuc = new BeanConsultaOperacionesCorresponsal();
		beanConsultaOperSuc.setEntidad(TXT_ENTIDAD);
		beanConsultaOperSuc.setCanal("14");
		beanConsultaOperSuc.setNivelParametria("03");
		beanConsultaOperSuc.setIdCorresponsal(corresponsal);
		beanConsultaOperSuc.setCodigoSucursal(sucursal);
		beanConsultaOperSuc.setTipoConsulta("L");
		final BeanResultadoOperacionesCorresponsal operacionesSuc = bOCorresponsalesOperaciones.consultaOperacionesCorresponsal(beanConsultaOperSuc, getArchitechBean());
		debug("Total de Operaciones Corresponsal: " + operacionesSuc.getRegistros().size());
		/*obtenemos el tipo de operacion para la operacion que acabamos de asociar*/
		for(BeanOperacion ope: operaciones.getRegistros()) {			
			if(ope.getCodigoOperacion().equalsIgnoreCase(codigoOperacionNoAsociado))
			{				
				tipoOperacionCorresponsal = ope.getTipoOperacionCorresponsal();
				break;
			}			
		}
		/*en caso de que sea una desasociacion buscamos el tipo de */
		if("".equalsIgnoreCase(tipoOperacionCorresponsal)){
			for(BeanOperacion ope: operacionesSuc.getRegistros()) {			
				if(ope.getCodigoOperacion().equalsIgnoreCase(codigoOperacionAsociado))
				{
					tipoOperacionCorresponsal = ope.getTipoOperacionCorresponsal();
					break;
				}			
			}			
		}
		for(int i = 0; i < operacionesSuc.getRegistros().size(); i++) {
			BeanOperacion opeCorr = operacionesSuc.getRegistros().get(i);
			for(int j = 0; j < operaciones.getRegistros().size(); j++) {
				BeanOperacion ope = operaciones.getRegistros().get(j);
				if(opeCorr.getCodigoOperacion().equals(ope.getCodigoOperacion())) {
					operaciones.getRegistros().remove(j);
					j = operaciones.getRegistros().size();
				}
			}
		}
		if(operacion == null) {
			mapParametros.put(TXT_RESP, "Ha elegido una opci&oacute;n inv&aacute;lida.");
		}
		else {
			final BeanABMMantenimientoOperCorresponsal beanOper = new BeanABMMantenimientoOperCorresponsal();
			if(TXT_ASOCIAR.equals(operacion)) {
				beanOper.setTipoMovimiento("A");
				beanOper.setCodigoSucursal(sucursal);
				beanOper.setIdCorresponsal(corresponsal);
				beanOper.setCodigoOperacion(req.getParameter("lbTmplNoAsoc"));
			}
			else {
				beanOper.setTipoMovimiento("B");
				beanOper.setCodigoSucursal(sucursal);
				beanOper.setIdCorresponsal(corresponsal);
				beanOper.setCodigoOperacion(req.getParameter("lbTmplAsoc"));
			}
			beanOper.setTipoOperacionCorr(tipoOperacionCorresponsal.trim());//beanOper.setTipoOperacionCorr("0123456789");//hardcode
			beanOper.setEntidad(TXT_ENTIDAD);
			beanOper.setCanal("14");
			beanOper.setNivelParametria("03");
			
			final BeanError respuesta = bOCorresponsalesOperaciones.actualizaOperacion(beanOper, getArchitechBean());
			//if(respuesta.getCodigoError().indexOf("DLE") > -1) {
			mapParametros.put(TXT_RESP, respuesta.getMsgError());
			req.getSession().setAttribute("msjRespuesta", respuesta.getMsgError());
			//}
		}	
		mapParametros.put("operaciones", operaciones.getRegistros());
		mapParametros.put("operacionesSuc", operacionesSuc.getRegistros());
		mapParametros.put(TXT_LISTA, req.getSession().getAttribute(TXT_LISTA_COR));
		mapParametros.put(TXT_LIST_SUC, req.getSession().getAttribute(TXT_LIST_SUC));
		mapParametros.put("sucursalBuscar", req.getParameter(TXT_SUC));
		mapParametros.put(TXT_COR_BUSC, req.getParameter(TXT_CORRESP));		
		return excepcionOperacionesOpe(req,res); 
		//return new ModelAndView("ExcepcionOperaciones", mapParametros);
	}		
}