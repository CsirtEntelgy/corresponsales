/*
 * 
 */
package com.isban.corresponsalia.controllers.consultas;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.isban.corresponsalia.beans.consultas.BeanRegistroConsultaBitacora;
import com.isban.corresponsalia.beans.consultas.RBeanAnulacion;
import com.isban.corresponsalia.beans.consultas.RBeanConsultaBitacora;
import com.isban.corresponsalia.beans.consultas.RBeanCorresponsalesConsultaBitacora;
import com.isban.corresponsalia.beans.consultas.RBeanOperacionesSucursalesConsultaBitacora;
import com.isban.corresponsalia.bo.consultas.BOConsultasBitacora;
import com.isban.corresponsalia.reportes.ExportaXLS;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;


/**
 * The Class ControllerConsultasBitacora.
 */
@Controller
public class ControllerConsultasBitacora extends ArchitechEBE{
	
	
	/** The Constant NEW_ARCH. */
	private static final String NEW_ARCH = "NewArchitechSession"; 
	
	/** The Constant BEAN_CONSULTA_BITACORA. */
	private static final String BEAN_CONSULTA_BITACORA = "beanConsultaBitacora";
	
	/** The Constant LISTA_ERG_BITA. */
	private static final String LISTA_ERG_BITA = "listaRegistrosBitacora";
	
	/** The Constant COD_ERROR. */
	private static final String COD_ERROR = "codError";
	
	/** The Constant MSG_ERROR. */
	private static final String MSG_ERROR = "msgError";
	
	/** The Constant CODIGO_ERROR. */
	private static final String CODIGO_ERROR = "Codigo error :";
	
	/** The Constant MENSAJE_ERROR. */
	private static final String MENSAJE_ERROR = "Mensaje error:";
	
	/** The Constant CONSULTA_BITA. */
	private static final String CONSULTA_BITA = "ConsultaBitacora";
	
	/** The Constant OPT_CORRESPONSAL. */
	private static final String OPT_CORRESPONSAL = "optCorresponsal"; 
	
	/** The Constant TXT_FECHA. */
	private static final String TXT_FECHA = "txtFecha";
	
	/** The Constant TXT_FOLIO. */
	private static final String TXT_FOLIO = "txtFolio";
	
	/** The Constant TXT_REF. */
	private static final String TXT_REF = "txtReferencia"; 
	
	/** The Constant TXT_CUENTA. */
	private static final String TXT_CUENTA = "txtCuentaTarjeta";
	
	/** The Constant TXT_IMPORTE. */
	private static final String TXT_IMPORTE = "txtImporte";
	
	/** The Constant OPC_AVANZAR_RETRO. */
	private static final String OPC_AVANZAR_RETRO = "opcAvanzarRetroceder"; 
	
	/** The Constant OPT_ESTATUS. */
	private static final String OPT_ESTATUS = "optEstatus";

	/** The b o consultas bitacora. */
	transient private BOConsultasBitacora bOConsultasBitacora;

	/**
	 * Sets the bO consultas bitacora.
	 *
	 * @param bOConsultasBitacora the new bO consultas bitacora
	 */
	public void setBOConsultasBitacora(BOConsultasBitacora bOConsultasBitacora){
		this.bOConsultasBitacora = bOConsultasBitacora;
	}
	
	/**
	 * Bitacora consulta corresponsales.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="BitacoraConsultaCorresponsales.do")
	public ModelAndView bitacoraConsultaCorresponsales(HttpServletRequest req, HttpServletResponse res){
		debug("BitacoraConsultaCorresponsales.do");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));
		final HashMap<String,Object>  lhsmParametros   = new HashMap<String,Object>();

		req.getSession().removeAttribute(BEAN_CONSULTA_BITACORA);
		req.getSession().removeAttribute(LISTA_ERG_BITA);
	
		RBeanCorresponsalesConsultaBitacora rBeanCorresponsalesConsultaBitacora = null;
		String lstrCodErrorPage = "";
		String lstrMsgErrorPage = "";
		try{
			rBeanCorresponsalesConsultaBitacora = bOConsultasBitacora.consultaCorresponsales(getArchitechBean());
			lstrCodErrorPage = rBeanCorresponsalesConsultaBitacora.getCodError();
			lstrMsgErrorPage = rBeanCorresponsalesConsultaBitacora.getMsgError();
			if(rBeanCorresponsalesConsultaBitacora!= null && !rBeanCorresponsalesConsultaBitacora.getListaCorresponsales().isEmpty()) {
				req.getSession().setAttribute("listaCorresponsalesBitacora", rBeanCorresponsalesConsultaBitacora.getListaCorresponsales());
			}else{
				if(rBeanCorresponsalesConsultaBitacora== null ) {						
						lhsmParametros.put(COD_ERROR, "DLE000");
						lhsmParametros.put(MSG_ERROR, "Error en consulta de corresponsales");
				}			
			}
		}
		catch(BusinessException e){
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			lhsmParametros.put(COD_ERROR, lstrCodErrorPage);
			lhsmParametros.put(MSG_ERROR, lstrMsgErrorPage);
		}
		
		debug(CODIGO_ERROR + lstrCodErrorPage);
		debug(MENSAJE_ERROR + lstrMsgErrorPage);	
		
		return new ModelAndView(CONSULTA_BITA,lhsmParametros);
	}	
	
	/**
	 * Bitacora consulta operaciones sucursales.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="BitacoraConsultaOperacionesSucursales.do")
	public ModelAndView bitacoraConsultaOperacionesSucursales(HttpServletRequest req, HttpServletResponse res){
		debug("BitacoraConsultaOperacionesSucursales.do");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));		
		final HashMap<String,Object>  lhsmParametros      = new HashMap<String,Object>();
		final String                  lstrIdCorresponsal  = req.getParameter(OPT_CORRESPONSAL);
		final List<BeanCorresponsal>  listaCorresponsales = (List<BeanCorresponsal>) req.getSession().getAttribute("listaCorresponsalesBitacora");
		BeanCorresponsal        beanCorresponsal    = null;
		final String                  lstrCorresponsal       = req.getParameter(OPT_CORRESPONSAL)==null?"":req.getParameter(OPT_CORRESPONSAL);
		final String                  lstrFecha              = req.getParameter(TXT_FECHA)==null?"":req.getParameter(TXT_FECHA);
		final String                  lstrFolio              = req.getParameter(TXT_FOLIO)==null?"":req.getParameter(TXT_FOLIO);
		final String                  lstrReferencia         = req.getParameter(TXT_REF)==null?"":req.getParameter(TXT_REF);
		final String                  lstrCuentaTarjeta      = req.getParameter(TXT_CUENTA)==null?"":req.getParameter(TXT_CUENTA);
		final String                  lstrImporte            = req.getParameter(TXT_IMPORTE)==null?"":req.getParameter(TXT_IMPORTE);
		final String                  lstrAvanzarRetroceder  = req.getParameter(OPC_AVANZAR_RETRO)==null?"":req.getParameter(OPC_AVANZAR_RETRO);
		final String                  lstrEtatus             = req.getParameter(OPT_ESTATUS)==null?"":req.getParameter(OPT_ESTATUS);
		final BeanConsultaBitacora    beanConsultaBitacora   = req.getSession().getAttribute(BEAN_CONSULTA_BITACORA)==null?new BeanConsultaBitacora():(BeanConsultaBitacora) req.getSession().getAttribute(BEAN_CONSULTA_BITACORA);
		
		
	
		debug("Id Corresponsal     :" + lstrIdCorresponsal);
		debug("Lista Corresponsales:" + listaCorresponsales);
		
		beanConsultaBitacora.setFechaAlta(lstrFecha);
		beanConsultaBitacora.setIdCorresponsal(lstrCorresponsal);
		beanConsultaBitacora.setImporteOperacion(lstrImporte);
		beanConsultaBitacora.setNumReferenciaOper(lstrReferencia);
		beanConsultaBitacora.setFolio(lstrFolio);
		beanConsultaBitacora.setCuentaTarjeta(lstrCuentaTarjeta);
		beanConsultaBitacora.setIndicadorPag(lstrAvanzarRetroceder);
		beanConsultaBitacora.setIdCorresponsal(lstrIdCorresponsal);
		beanConsultaBitacora.setEstatus(lstrEtatus);
		req.getSession().setAttribute(BEAN_CONSULTA_BITACORA, beanConsultaBitacora);
		
		if(listaCorresponsales==null){			
			lhsmParametros.put("codAviso", "Cores9999");
			lhsmParametros.put("msgAviso", "Servicio No Disponible");
			return new ModelAndView(CONSULTA_BITA,lhsmParametros);
		}
		
		for(BeanCorresponsal bCorresponsalTemp:listaCorresponsales){
			if(bCorresponsalTemp.getCodigoCorresponsal().equals(lstrIdCorresponsal)){
				debug("Corresponsal encontrado en la lista de corresponsales");
				beanCorresponsal = bCorresponsalTemp;
				;
			}
		}		
		RBeanOperacionesSucursalesConsultaBitacora rBeanOperacionesSucursalesConsultaBitacora = new RBeanOperacionesSucursalesConsultaBitacora();
		String lstrCodErrorPage = "";
		String lstrMsgErrorPage = "";
		try{
			rBeanOperacionesSucursalesConsultaBitacora = bOConsultasBitacora.consultaOperacionesSucursales(getArchitechBean(),beanCorresponsal);
			lstrCodErrorPage = rBeanOperacionesSucursalesConsultaBitacora.getCodError();
			lstrMsgErrorPage = rBeanOperacionesSucursalesConsultaBitacora.getMsgError();
			req.getSession().setAttribute("listaOperacionesBitacora"   , rBeanOperacionesSucursalesConsultaBitacora.getListaOperaciones());
			req.getSession().setAttribute("listaSucursalesBitacora"    , rBeanOperacionesSucursalesConsultaBitacora.getListaSucursales());
		}
		catch(BusinessException e){
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			lhsmParametros.put(COD_ERROR, lstrCodErrorPage);
			lhsmParametros.put(MSG_ERROR, lstrMsgErrorPage);
		}
		
		debug(CODIGO_ERROR + lstrCodErrorPage);
		debug(MENSAJE_ERROR + lstrMsgErrorPage);
				
		return new ModelAndView(CONSULTA_BITA,lhsmParametros);
	}	
	
	/**
	 * Realiza consulta bitacora.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 * 
	 */
	@RequestMapping(value="RealizaConsultaBitacora.do")
	public ModelAndView realizaConsultaBitacora(HttpServletRequest req, HttpServletResponse res){
		debug("RealizaConsultaBitacora.do");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));
		final String                 lstrCorresponsal       = req.getParameter(OPT_CORRESPONSAL);
		final String                 lstrOperacion          = req.getParameter("optOperacion")==null?"":req.getParameter("optOperacion");
		final String                 lstrSucursal           = req.getParameter("optSucursal")==null?"":req.getParameter("optSucursal");
		final String                 lstrFecha              = req.getParameter(TXT_FECHA)==null?"":req.getParameter(TXT_FECHA);
		final String                 lstrFolio              = req.getParameter(TXT_FOLIO)==null?"":req.getParameter(TXT_FOLIO);
		final String                 lstrReferencia         = req.getParameter(TXT_REF)==null?"":req.getParameter(TXT_REF);
		final String                 lstrCuentaTarjeta      = req.getParameter(TXT_CUENTA)==null?"":req.getParameter(TXT_CUENTA);
		final String                 lstrAvanzarRetroceder  = req.getParameter(OPC_AVANZAR_RETRO)==null?"":req.getParameter(OPC_AVANZAR_RETRO);
		final String                 lstrEtatus             = req.getParameter(OPT_ESTATUS)==null?"":req.getParameter(OPT_ESTATUS);
		final  BeanConsultaBitacora   beanConsultaBitacora   = req.getSession().getAttribute(BEAN_CONSULTA_BITACORA)==null?new BeanConsultaBitacora():(BeanConsultaBitacora) req.getSession().getAttribute(BEAN_CONSULTA_BITACORA);
		final HashMap<String,Object> lhsmParametros         = new HashMap<String,Object>();
		String                 lstrImporte            = req.getParameter(TXT_IMPORTE)==null?"":req.getParameter(TXT_IMPORTE);		
		req.getSession().setAttribute(LISTA_ERG_BITA, null);

		debug("Corresponsal        :" + lstrCorresponsal);
		debug("Operacion           :" + lstrOperacion);
		debug("Sucursal            :" + lstrSucursal);
		debug("Fecha               :" + lstrFecha);
		debug("Folio               :" + lstrFolio);
		debug("Referencia          :" + lstrReferencia);
		debug("CuentaTarjeta       :" + lstrCuentaTarjeta);
		debug("Importe             :" + lstrImporte);
		debug("AvanzarRetroceder   :" + lstrAvanzarRetroceder);
		debug("Estatus             :" + lstrEtatus);
		
		if("todas".equals(lstrOperacion)){
			beanConsultaBitacora.setClaveTipoOperacion("");
		}
		else{
			beanConsultaBitacora.setClaveTipoOperacion(lstrOperacion);
		}
		
		if("todas".equals(lstrSucursal)){
			beanConsultaBitacora.setCodIdSucursal("");
		}
		else{
			beanConsultaBitacora.setCodIdSucursal(lstrSucursal);
		}
		
		beanConsultaBitacora.setIdOperacion(lstrOperacion);
		try{
			final SimpleDateFormat formatDateI = 
				new	SimpleDateFormat("dd/MM/yyyy",new Locale("ES","MX"));
			final SimpleDateFormat formatDateF = 
				new SimpleDateFormat("yyyy-MM-dd", new Locale("ES","MX"));
			final Date   fechaSinFormato = formatDateI.parse(lstrFecha);
			final String fechaConFormato = formatDateF.format(fechaSinFormato);
			beanConsultaBitacora.setFechaAlta(fechaConFormato);
		}
		catch(ParseException e){
			debug("Error al formatear fecha");
		}
		
		beanConsultaBitacora.setIdCorresponsal(lstrCorresponsal);
		beanConsultaBitacora.setImporteOperacion(lstrImporte);
		if(!"".equals(lstrImporte)){
			int indexChar = 0;						
			try {
				lstrImporte = lstrImporte.trim();				
				indexChar = lstrImporte.indexOf('.');
				if (indexChar != -1) {
					final String substIni = lstrImporte.substring(0,indexChar);
					if (indexChar + 1 == lstrImporte.length()) {
						beanConsultaBitacora.setImporteOperacion(substIni + "00");
					} else if (indexChar + 2 == lstrImporte.length()) {
						final String substLast = lstrImporte.substring(indexChar + 1,lstrImporte.length());
						beanConsultaBitacora.setImporteOperacion(substIni + substLast + "0");
					} else {
						final String substLast = lstrImporte.substring(indexChar + 1,lstrImporte.length());
						beanConsultaBitacora.setImporteOperacion(substIni + substLast);
					}
				} else {
					beanConsultaBitacora.setImporteOperacion(lstrImporte + "00");
				}
				//float fImporte = Float.parseFloat(lstrImporte);
				//fImporte =  fImporte * 100;
				//lstrImporte = String.valueOf(fImporte);
				
			} catch (IndexOutOfBoundsException  e) {
				debug("No se pudo convertir el importe");
			}
		}
		
		beanConsultaBitacora.setNumReferenciaOper(lstrReferencia);
		beanConsultaBitacora.setFolio(lstrFolio);
		beanConsultaBitacora.setCuentaTarjeta(lstrCuentaTarjeta);
		beanConsultaBitacora.setIndicadorPag(lstrAvanzarRetroceder);
		beanConsultaBitacora.setEstatus(lstrEtatus);
		
		if("sinsel".equals(lstrCorresponsal)){
			beanConsultaBitacora.setFechaAlta(lstrFecha);
			req.getSession().setAttribute(BEAN_CONSULTA_BITACORA  , beanConsultaBitacora);
			lhsmParametros.put(COD_ERROR, "SELCOR01");
			lhsmParametros.put(MSG_ERROR, "NO SE HA SELECCIONADO NINGUN CORRESPONSAL");
			return new ModelAndView(CONSULTA_BITA,lhsmParametros);			
			
		}
		
		RBeanConsultaBitacora rBeanConsultaBitacora = null;
		String lstrCodErrorPage = null;
		String lstrMsgErrorPage = null;
		String lstrCodAvisoPage = null;
		String lstrMsgAvisoPage = null;

		try{
			if("NIN".equals(lstrEtatus)){
				rBeanConsultaBitacora = bOConsultasBitacora.consultaBitacoraSinReferencia(getArchitechBean(), beanConsultaBitacora);
			}
			else{
				rBeanConsultaBitacora = bOConsultasBitacora.consultaBitacoraConReferencia(getArchitechBean(), beanConsultaBitacora);
			}			
			lstrCodErrorPage = rBeanConsultaBitacora.getCodError();
			lstrMsgErrorPage = rBeanConsultaBitacora.getMsgError();
			lstrCodAvisoPage = rBeanConsultaBitacora.getCodAviso();
			lstrMsgAvisoPage = rBeanConsultaBitacora.getMsgAviso();
			
			beanConsultaBitacora.setReferenciaAvanzar(rBeanConsultaBitacora.getReferenciaAvanzar());
			beanConsultaBitacora.setReferenciaRetroceder(rBeanConsultaBitacora.getReferenciaRetroceder());
			
			lstrImporte = beanConsultaBitacora.getImporteOperacion();
			if(!"".equals(lstrImporte)){				
				try {
					lstrImporte = lstrImporte.trim();
					final String substIni = lstrImporte.substring(0, lstrImporte.length()-2);
					final String substLast = lstrImporte.substring(lstrImporte.length() - 2, lstrImporte.length());
					
					beanConsultaBitacora.setImporteOperacion(substIni + "." + substLast);
				} catch (IndexOutOfBoundsException e) {
					debug("No se pudo convertir el importe");
				}
			} else {
				beanConsultaBitacora.setImporteOperacion("");
			}			
			if (!("DLA0005".equalsIgnoreCase(lstrCodAvisoPage)||"DLA0004".equalsIgnoreCase(lstrCodAvisoPage))) {
				lhsmParametros.put("codAviso", lstrCodAvisoPage);
				lhsmParametros.put("msgAviso", lstrMsgAvisoPage);
			}
			if(rBeanConsultaBitacora!= null && rBeanConsultaBitacora.getListaRegistrosConsultaBitacora()!= null) {
				req.getSession().setAttribute(BEAN_CONSULTA_BITACORA, beanConsultaBitacora);
				req.getSession().setAttribute(LISTA_ERG_BITA, rBeanConsultaBitacora.getListaRegistrosConsultaBitacora());
			}else {
				lhsmParametros.put("codAviso", "DLE0001");
				lhsmParametros.put("msgAviso", "Servicio No Disponible");

			}
		}
		catch(BusinessException e){
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			lhsmParametros.put(COD_ERROR, lstrCodErrorPage);
			lhsmParametros.put(MSG_ERROR, lstrMsgErrorPage);
		}
		
		debug(CODIGO_ERROR + lstrCodErrorPage);
		debug(MENSAJE_ERROR + lstrMsgErrorPage);
		
		beanConsultaBitacora.setFechaAlta(lstrFecha);
		req.getSession().setAttribute(BEAN_CONSULTA_BITACORA  , beanConsultaBitacora);
			
		return new ModelAndView(CONSULTA_BITA,lhsmParametros);
	}	
	
	/**
	 * Cambiaformato flotante.
	 *
	 * @param importe the importe
	 * @return the string
	 */
	private String cambiaformatoFlotante(String importe) {
		float fImporte = Float.parseFloat(importe);
		if(fImporte != 0) {		
			fImporte =  fImporte / 100;
		}		
		importe = String.valueOf(fImporte);		
		return importe;
	}

	/**
	 * Bitacora anular.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 * 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="BitacoraAnular.do")
	public ModelAndView bitacoraAnular(HttpServletRequest req, HttpServletResponse res) {
		debug("BitacoraAnular.do");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));
		final HashMap<String,Object>              lhsmParametros      = new HashMap<String,Object>();
		final List<BeanRegistroConsultaBitacora>  listaRegistros      = (List<BeanRegistroConsultaBitacora>) req.getSession().getAttribute(LISTA_ERG_BITA);
		final String                              lstrRegistroAnular  =  req.getParameter("regAnular");    
		final BeanConsultaBitacora                beanConsultaBitacora= (BeanConsultaBitacora) req.getSession().getAttribute(BEAN_CONSULTA_BITACORA);

		debug("Registro a Anular  :" + lstrRegistroAnular);
		debug("Lista Registros    :" + listaRegistros);
		
		final BeanRegistroConsultaBitacora beanRegistroAnular = listaRegistros.get(Integer.parseInt(lstrRegistroAnular));

		try{
			//String lstrFecha = beanConsultaBitacora.getFechaAlta();
			//SimpleDateFormat formatDateI = new SimpleDateFormat("dd/MM/yyyy");
			final SimpleDateFormat formatDateF = new SimpleDateFormat("yyyy-MM-dd", new Locale("ES","MX"));
			//Date   fechaSinFormato = formatDateI.parse(lstrFecha);
			//String fechaConFormato = formatDateF.format(fechaSinFormato);
			final String fechaConFormato = formatDateF.format(new Date());
			//beanConsultaBitacora.setFechaAlta(fechaConFormato);
			beanRegistroAnular.setFechaAlta(fechaConFormato);
			
			String lstrImporte = beanRegistroAnular.getImporteOperacion();
			if(!"".equals(lstrImporte)){
				int indexChar = 0;						
				try {
					lstrImporte = lstrImporte.trim();				
					indexChar = lstrImporte.indexOf('.');
					if (indexChar != -1) {
						final String substIni = lstrImporte.substring(0,indexChar);
						if (indexChar + 1 == lstrImporte.length()) {
							beanConsultaBitacora.setImporteOperacion(substIni + "00");
						} else if (indexChar + 2 == lstrImporte.length()) {
							final String substLast = lstrImporte.substring(indexChar + 1,lstrImporte.length());
							beanConsultaBitacora.setImporteOperacion(substIni + substLast + "0");
						} else {
							final String substLast = lstrImporte.substring(indexChar + 1,lstrImporte.length());
							beanConsultaBitacora.setImporteOperacion(substIni + substLast);
						}
					} else {
						beanConsultaBitacora.setImporteOperacion(lstrImporte + "00");
					}
					//float fImporte = Float.parseFloat(lstrImporte);
					//fImporte =  fImporte * 100;
					//lstrImporte = String.valueOf(fImporte);
					
				} catch (IndexOutOfBoundsException  e) {
					debug("No se pudo convertir el importe");
				}
			}
		}
		catch(IllegalArgumentException  e){
			debug("Error al formatear fecha");
		}
		
		RBeanAnulacion rBeanAnulacion = new RBeanAnulacion();
		String lstrCodErrorPage = "";
		String lstrMsgErrorPage = "";
		try{
			
			rBeanAnulacion = bOConsultasBitacora.anulacion(getArchitechBean(),beanRegistroAnular);
			lstrCodErrorPage = rBeanAnulacion.getCodError();
			lstrMsgErrorPage = rBeanAnulacion.getMsgError();
			req.getSession().setAttribute("listaRegistros", rBeanAnulacion.getListaAnulacionesExitosas());
		}
		catch(BusinessException e){
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			lhsmParametros.put(COD_ERROR, lstrCodErrorPage);
			lhsmParametros.put(MSG_ERROR, lstrMsgErrorPage);
		}
		
		debug(CODIGO_ERROR + lstrCodErrorPage);
		debug(MENSAJE_ERROR + lstrMsgErrorPage);
		
		//return new ModelAndView("ConsultaBitacoraFix",lhsmParametros);
		req.setAttribute(COD_ERROR, lstrCodErrorPage);
		req.setAttribute(MSG_ERROR, lstrMsgErrorPage);
		return realizaConsultaBitacora(req,res);
	}	

	/**
	 * Exportar bitacora.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="ExportarBitacora.do")
	public ModelAndView exportarBitacora(HttpServletRequest req, HttpServletResponse res){
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));
		final HashMap<String, Object> map = new HashMap<String, Object>();
		final List<BeanRegistroConsultaBitacora>  listaRegistros      = (List<BeanRegistroConsultaBitacora>) req.getSession().getAttribute(LISTA_ERG_BITA);
		
		if(listaRegistros==null || ( listaRegistros != null &&listaRegistros.isEmpty())){
			final HashMap<String,Object>  lhsmParametros   = new HashMap<String,Object>();
			lhsmParametros.put(COD_ERROR, "NOEXPORT");
			lhsmParametros.put(MSG_ERROR, "NO HAY REGISTROS PARA EXPORTAR");
			return new ModelAndView(CONSULTA_BITA,lhsmParametros);			
		}
		
		for (BeanRegistroConsultaBitacora importe : listaRegistros) {
			importe.setNumRefPampa(importe.getNumRefPampa().trim());
			importe.setClaveRefTran(importe.getClaveRefTran().trim());
			importe.setNumRefOper(importe.getNumRefOper().trim());
			importe.setNumCuentaCheques(importe.getNumCuentaCheques().trim());
			importe.setNumeroTarjeta(importe.getNumeroTarjeta().trim());
			if (!"".equals(importe.getNumRefPampa())) {				
				importe.setNumRefPampa("'" + importe.getNumRefPampa());
			}
			if (!"".equals(importe.getClaveRefTran())) {				
				importe.setClaveRefTran("'" + importe.getClaveRefTran());
			}
			if (!"".equals(importe.getNumRefOper())) {				
				importe.setNumRefOper("'" + importe.getNumRefOper());
			}
			if (!"".equals(importe.getNumCuentaCheques())) {				
				importe.setNumCuentaCheques("'" + importe.getNumCuentaCheques());
			}
			if (!"".equals(importe.getNumeroTarjeta())) {
				importe.setNumeroTarjeta("'" + importe.getNumeroTarjeta());
			}
			importe.setImporteOperacion(cambiaformatoFlotante(importe.getImporteOperacion()));
			importe.setImporteComisionBanco((cambiaformatoFlotante(importe.getImporteComisionBanco())));
			importe.setImporteComisionCliente(cambiaformatoFlotante(importe.getImporteComisionCliente()));
			importe.setImporteComisionCorresponsal(cambiaformatoFlotante(importe.getImporteComisionCorresponsal()));
			importe.setImporteIvaBanco(cambiaformatoFlotante(importe.getImporteIvaBanco()));
			importe.setImporteIvaCliente(cambiaformatoFlotante(importe.getImporteIvaCliente()));
			importe.setImporteIvaCorresponsal(cambiaformatoFlotante(importe.getImporteIvaCorresponsal()));
		}
		final JRDataSource dataSource = new JRBeanArrayDataSource(listaRegistros.toArray());
	
		final DateFormat Dformat = new  SimpleDateFormat("yyyy/MM/dd HH:mm:ss aa", new Locale("ES","MX")); 
		map.put("fechaReporte", Dformat.format(java.util.Calendar.getInstance().getTime())); //  pasamos la fecha al reporte
		return new ModelAndView(new ExportaXLS("bitacora","/com/isban/corresponsalia/reportes/doctos/bitacora.jasper",dataSource), map);	
	}
		
}

