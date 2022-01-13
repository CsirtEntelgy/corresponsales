package com.isban.corresponsalia.controllers.corresponsales;

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
import com.isban.corresponsalia.beans.comunes.BeanSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoSucursal;
import com.isban.corresponsalia.bo.corresponsales.BOCorresponsalesSucursalesFix;
import com.isban.corresponsalia.comunes.Parametros;
import com.isban.corresponsalia.reportes.ExportaXLS;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;

/**
 * The Class ControllerCorresponsalesSucursalesFix.
 */
@Controller
public class ControllerCorresponsalesSucursalesFix extends ArchitechEBE {
	
	/** The Constant NEW_ARCH. */
	private static final String NEW_ARCH = "NewArchitechSession";
	
	/** The Constant CADENA_VACIA. */
	private static final String CADENA_VACIA = "";
	
	/** The Constant REGISTRO_SUCURSALES. */
	private static final String REGISTRO_SUCURSALES =  "registrosSucursales";
	
	/** The Constant COD_AVISO. */
	private static final String COD_AVISO = "codAviso";
	
	/** The Constant MSG_AVISO. */
	private static final String MSG_AVISO = "msgAviso";
	
	/** The Constant COD_ERROR. */
	private static final String COD_ERROR = "codError";
	
	/** The Constant MSG_ERROR. */
	private static final String MSG_ERROR = "msgError";
	
	/** The Constant CODIGO_ERROR. */
	private static final String CODIGO_ERROR = "Codigo error :";
	
	/** The Constant MENSAJE_ERROR. */
	private static final String MENSAJE_ERROR = "Mensaje error:";
	
	/** The Constant CONSULTA_SUCURSAL. */
	private static final String CONSULTA_SUCURSAL = "ConsultaSucursal";

	/** The Constant CMB_CORRESPONSAL. */
	private static final String CMB_CORRESPONSAL = "cmbNombreCorresponsal";
	
	/** The Constant MAPA_PARAM. */
	private static final String MAPA_PARAM =  "mapParametros";
	
	/** The Constant CORRESPONSAL_ENCONTRADO. */
	private static final String CORRESPONSAL_ENCONTRADO =  "Corresponsal encontrado en la lista de corresponsales";
	
	/** The Constant CODIGO_CORRESPONSAL. */
	private static final String CODIGO_CORRESPONSAL =  "Codigo Corresponsal:";
	
	/** The Constant NOMBRE_CORRESPONSAL. */
	private static final String NOMBRE_CORRESPONSAL =  "Nombre Corresponsal:";
	
	/** The Constant CORRESPONSAL_SEL. */
	private static final String CORRESPONSAL_SEL = "corresponsalSel";
	
	/** The Constant NOMBRE_SUCURSAL. */
	private static final String NOMBRE_SUCURSAL = "nombreSucursal";
	
	/** The Constant NUM_ID. */
	private static final String NUM_ID = "numId";
	 
	/** The Constant LISTA_CORR_SUC. */
	private static final String LISTA_CORR_SUC = "listaCorresponsalesSucursales";
	
	/** The Constant BEAN_CONSULTA_SUC. */
	private static final String BEAN_CONSULTA_SUC = "beanConsultaSucursal" ;
	
	/** The Constant PAGINA_NUM */
	private static final String PAGINA_NUM = "paginaNum";
	
	/** The Constant REF_AVANZAR */
	private static final String REF_AVANZAR = "referenciaAvanzar";
	
	/** The Constant REF_RETROCEDER */
	private static final String REF_RETROCEDER = "referenciaRetroceder";
	
	/** The Constant HAY_ATRAS */
	private static final String HAY_ATRAS = "hayAtras";
	
	/** The Constant HAY_ADELANTE */
	private static final String HAY_ADELANTE = "hayAdelante";
	
	/** The Constant NOMBRE_SUC_ATRAS */
	private static final String NOMBRE_SUC_ATRAS = "nombreSucursalAtras";
	
	/** The Constant SUC_SELEC */
	private static final String SUC_SELEC = "sucursalSel";
	
	/** The b o corresponsales sucursales fix. */
	private transient BOCorresponsalesSucursalesFix bOCorresponsalesSucursalesFix;

	/**
	 * Sets the bO corresponsales sucursales fix.
	 *
	 * @param bOCorresponsalesSucursales the new bO corresponsales sucursales fix
	 */
	public void setBOCorresponsalesSucursalesFix(
			BOCorresponsalesSucursalesFix bOCorresponsalesSucursales) {
		this.bOCorresponsalesSucursalesFix = bOCorresponsalesSucursales;
	}

	/**
	 * Muestra corresponsales sucursales.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value = "MuestraCorresponsalesSucursales.do")
	public ModelAndView muestraCorresponsalesSucursales(HttpServletRequest req,
			HttpServletResponse res) {
		info("Inicia Consulta Sucursales Corresponsalia");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();
		final BeanMttoConsultaCorresponsal beanConsultaCorresponsal = new BeanMttoConsultaCorresponsal();
		final String codigoCorresponsalia = Parametros
				.getParametroAplicacion("CORRESPONSALIA");
		String lstrCodErrorPage = "";
		String lstrMsgErrorPage = "";

		debug("codigoCorresponsalia:" + codigoCorresponsalia);

		beanConsultaCorresponsal.setCodigoCorresponsalia(codigoCorresponsalia);
		beanConsultaCorresponsal.setTipoConsulta("L");
		beanConsultaCorresponsal.setIndicadorPaginacion("N");
		req.getSession().removeAttribute(LISTA_CORR_SUC);
		req.getSession().removeAttribute(BEAN_CONSULTA_SUC);
		req.getSession().removeAttribute(REGISTRO_SUCURSALES);

		try {
			final BeanResultadoCorresponsalia beanRegresoCorresponsalias = bOCorresponsalesSucursalesFix
					.consultaCorresponsales(beanConsultaCorresponsal,
							getArchitechBean());
			lstrCodErrorPage = beanRegresoCorresponsalias.getCodError();
			lstrMsgErrorPage = beanRegresoCorresponsalias.getMsgError();
			if (beanRegresoCorresponsalias.getRegistros().size() == 0) {
				mapParametros.put(COD_AVISO, lstrCodErrorPage);
				mapParametros.put(MSG_AVISO, lstrMsgErrorPage);
			} else{
				req.getSession().setAttribute(LISTA_CORR_SUC,
						beanRegresoCorresponsalias.getRegistros());
			}

		} catch (BusinessException e) {
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			mapParametros.put(COD_ERROR, lstrCodErrorPage);
			mapParametros.put(MSG_ERROR, lstrMsgErrorPage);
		}

		debug(CODIGO_ERROR + lstrCodErrorPage);
		debug(MENSAJE_ERROR + lstrMsgErrorPage);

		info("Fin Consulta Sucursales Corresponsalia");
		return new ModelAndView(CONSULTA_SUCURSAL, mapParametros);
	}

	
	/**
	 * Metodo para consulta sucursal
	 * 
	 * @param req the req
	 * @param res the res
	 * @return ModelAndView the Model and View
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ConsultaSucursal.do")
	public ModelAndView consultaSucursal(HttpServletRequest req,
			HttpServletResponse res)  {
		BeanResultadoSucursal beanRegresoSucursales = new BeanResultadoSucursal();
		info("Inicia Consulta Comisiones Corresponsalia");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();
		final BeanConsultaSucursal beanConsulta = req.getSession().getAttribute(
				BEAN_CONSULTA_SUC) == null ? new BeanConsultaSucursal()
				: (BeanConsultaSucursal) req.getSession().getAttribute(
						BEAN_CONSULTA_SUC);
		final String nombreCorresponsal = req.getParameter(CMB_CORRESPONSAL) == null ? ""
				: req.getParameter(CMB_CORRESPONSAL).trim();
		final String adelanteAtras = req.getParameter("adelanteAtras") == null ? ""
				: req.getParameter("adelanteAtras").trim();
		final String paginaNum = req.getParameter(PAGINA_NUM) == null ? "" : req
				.getParameter(PAGINA_NUM).toString();
		
		debug("Corresponsal a consultar:" + nombreCorresponsal);
		debug("Paginar Adelante/Atras  :" + adelanteAtras);
		debug("Referencia Adelante     :"
				+ beanConsulta.getLimiteSuperiorConsulta());
		debug("Referencia Atras        :"
				+ beanConsulta.getLimiteInferiorConsulta());
		debug("Controller nombre sucursal: " + beanConsulta.getNombreSucursal());
		
		req.getSession().removeAttribute(REGISTRO_SUCURSALES);

		if (!beanConsulta.getCodigoCorresponsalia().equals(nombreCorresponsal)) {
			beanConsulta.setCodigoCorresponsalia(nombreCorresponsal);
		}

		if (!"".equals(adelanteAtras)) {
			beanConsulta.setIndicadorPaginacion("P");
			beanConsulta.setIndPaginacion(adelanteAtras);
		} else {
			beanConsulta.setIndicadorPaginacion("N");
		}
		
		beanConsulta.setIndicadorFuncional("L");

		final String lstrCodAviso = req.getAttribute(COD_AVISO) == null ? ""
				: (String) req.getAttribute(COD_AVISO);
		final String lstrMsgAviso = req.getAttribute(MSG_AVISO) == null ? ""
				: (String) req.getAttribute(MSG_AVISO);
		debug("Codigo aviso pasado :" + lstrCodAviso);
		debug("Mensaje aviso pasado:" + lstrMsgAviso);
		if (!"".equals(lstrCodAviso)) {
			mapParametros.put(COD_AVISO, lstrCodAviso);
			mapParametros.put(MSG_AVISO, lstrMsgAviso);
		}

		String lstrCodErrorPage = req.getAttribute(COD_ERROR) == null ? ""
				: (String) req.getAttribute(COD_ERROR);
		String lstrMsgErrorPage = req.getAttribute(MSG_ERROR) == null ? ""
				: (String) req.getAttribute(MSG_ERROR);
		debug("Codigo error pasado :" + lstrCodErrorPage);
		debug("Mensaje error pasado:" + lstrMsgErrorPage);

		try {
			if ("1".equalsIgnoreCase(paginaNum.trim())) {
				beanConsulta.setIndicadorPaginacion("N");
				beanConsulta.setLimiteInferiorConsulta("");
				beanConsulta.setLimiteSuperiorConsulta("");

			}
			
			debug("TEST - NombreSucursalAtras: " + beanConsulta.getNombreSucursalAtras());
			debug("TEST - NombreSucursal: " + beanConsulta.getNombreSucursal());
			debug("TEST - Numero de página: " + paginaNum.trim());
			debug("TEST =========================> consulta al BO...");
			
			
			
			
			
			if ("".equals(beanConsulta.getIndPaginacion())) {
				
				debug("TEST | vacio | sucursal anterior: " + beanConsulta.getNombreSucursalAtras());
				debug("TEST | vacio | sucursal a mandar: " + beanConsulta.getNombreSucursal());
				
				beanRegresoSucursales = bOCorresponsalesSucursalesFix
						.consultaSucursalCorresponsalia(beanConsulta,
								getArchitechBean());
				
				if ("".equals(lstrCodErrorPage)) {
					lstrCodErrorPage = beanRegresoSucursales.getCodError();
					lstrMsgErrorPage = beanRegresoSucursales.getMsgError();
				} else {
					mapParametros.put(COD_ERROR, lstrCodErrorPage);
					mapParametros.put(MSG_ERROR, lstrMsgErrorPage);
				}

				if (beanRegresoSucursales.getRegistros().size() == 0) {
					mapParametros.put(COD_AVISO, lstrCodErrorPage);
					mapParametros.put(MSG_AVISO, lstrMsgErrorPage);
					return altaSucursal(req, res);
				} else {
					req.getSession().setAttribute(REGISTRO_SUCURSALES,
							beanRegresoSucursales.getRegistros());
					
					beanConsulta.setLimiteSuperiorConsulta(beanRegresoSucursales
							.getReferenciaAdelante());
					beanConsulta.setLimiteInferiorConsulta(beanRegresoSucursales
							.getReferenciaAtras());
					
					//Asignamos el campo nombreSucursal
					beanConsulta.setNombreSucursalAtras("");
					beanConsulta.setNombreSucursal(beanRegresoSucursales.getNombreSucursal());
					
					
					debug("TEST vacio - beanRegreso | ReferenciaAdelante: " + beanRegresoSucursales.getReferenciaAdelante());
					debug("TEST vacio - beanRegreso | ReferenciaAtras: " + beanRegresoSucursales.getReferenciaAtras());
					
					mapParametros.put(REF_AVANZAR, beanRegresoSucursales
							.getReferenciaAdelante());
					mapParametros.put(REF_RETROCEDER, beanRegresoSucursales
							.getReferenciaAtras());
					mapParametros.put(HAY_ATRAS, beanRegresoSucursales
							.isMasAtras());
					mapParametros.put(HAY_ADELANTE, beanRegresoSucursales
							.isMasAdelante());
					mapParametros.put(PAGINA_NUM, paginaNum);
					
					//Asignamos el campo nombreSucursal
					mapParametros.put(NOMBRE_SUC_ATRAS, beanRegresoSucursales.getNombreSucursalAtras());
					mapParametros.put(NOMBRE_SUCURSAL, beanRegresoSucursales.getNombreSucursal());
					
					debug("Controller vacio beanRegresoSucursales.getNombreSucursalAtras(): " + beanRegresoSucursales.getNombreSucursalAtras());
					debug("Controller vacio beanRegresoSucursales.getNombreSucursal(): " + beanRegresoSucursales.getNombreSucursal());
					
					debug("TEST | vacio | sucursal anterior: " + beanConsulta.getNombreSucursalAtras());
					debug("TEST | vacio | sucursal actual: " + beanConsulta.getNombreSucursal());
				}
				
				
			}
			
			if ("A".equals(beanConsulta.getIndPaginacion())) {
				
				debug("TEST | A | sucursal anterior: " + beanConsulta.getNombreSucursalAtras());
				debug("TEST | A | sucursal a mandar: " + beanConsulta.getNombreSucursal());
				
				beanRegresoSucursales = bOCorresponsalesSucursalesFix
						.consultaSucursalCorresponsalia(beanConsulta,
								getArchitechBean());
				
				if ("".equals(lstrCodErrorPage)) {
					lstrCodErrorPage = beanRegresoSucursales.getCodError();
					lstrMsgErrorPage = beanRegresoSucursales.getMsgError();
				} else {
					mapParametros.put(COD_ERROR, lstrCodErrorPage);
					mapParametros.put(MSG_ERROR, lstrMsgErrorPage);
				}

				if (beanRegresoSucursales.getRegistros().size() == 0) {
					mapParametros.put(COD_AVISO, lstrCodErrorPage);
					mapParametros.put(MSG_AVISO, lstrMsgErrorPage);
					return altaSucursal(req, res);
				} else {
					req.getSession().setAttribute(REGISTRO_SUCURSALES,
							beanRegresoSucursales.getRegistros());
					
					beanConsulta.setLimiteSuperiorConsulta(beanRegresoSucursales
							.getReferenciaAdelante());
					beanConsulta.setLimiteInferiorConsulta(beanRegresoSucursales
							.getReferenciaAtras());
					
					//Asignamos el campo nombreSucursal
					beanConsulta.setNombreSucursalAtras(beanRegresoSucursales.getNombreSucursalAtras());
					beanConsulta.setNombreSucursal(beanRegresoSucursales.getNombreSucursal());
					
					debug("TEST A - beanRegreso | ReferenciaAdelante: " + beanRegresoSucursales.getReferenciaAdelante());
					debug("TEST A - beanRegreso | ReferenciaAtras: " + beanRegresoSucursales.getReferenciaAtras());
					
					mapParametros.put(REF_AVANZAR, beanRegresoSucursales
							.getReferenciaAdelante());
					mapParametros.put(REF_RETROCEDER, beanRegresoSucursales
							.getReferenciaAtras());
					mapParametros.put(HAY_ATRAS, beanRegresoSucursales
							.isMasAtras());
					mapParametros.put(HAY_ADELANTE, beanRegresoSucursales
							.isMasAdelante());
					mapParametros.put(PAGINA_NUM, paginaNum);
					
					//Asignamos el campo nombreSucursal
					mapParametros.put(NOMBRE_SUC_ATRAS, beanRegresoSucursales.getNombreSucursalAtras());
					mapParametros.put(NOMBRE_SUCURSAL, beanRegresoSucursales.getNombreSucursal());
					
					debug("Controller A beanRegresoSucursales.getNombreSucursalAtras(): " + beanRegresoSucursales.getNombreSucursalAtras());
					debug("Controller A beanRegresoSucursales.getNombreSucursal(): " + beanRegresoSucursales.getNombreSucursal());
					
					debug("TEST | A | sucursal anterior: " + beanConsulta.getNombreSucursalAtras());
					debug("TEST | A | sucursal actual: " + beanConsulta.getNombreSucursal());
					
				}
				
				
			}
			
			if ("B".equals(beanConsulta.getIndPaginacion())) {
				
				if ("1".equals(paginaNum.trim())) {
					beanConsulta.setNombreSucursalAtras("");
				}
				
				beanConsulta.setNombreSucursal(beanConsulta.getNombreSucursalAtras());
				
				debug("TEST - adelanteAtras es B! | beanConsulta sucursalAnterior: " + beanConsulta.getNombreSucursalAtras());
				debug("TEST - adelanteAtras es B! | beanConsulta sucursal a mandar: " + beanConsulta.getNombreSucursal());
				
				
				beanRegresoSucursales = bOCorresponsalesSucursalesFix
						.consultaSucursalCorresponsalia(beanConsulta,
								getArchitechBean());
				
				if ("".equals(lstrCodErrorPage)) {
					lstrCodErrorPage = beanRegresoSucursales.getCodError();
					lstrMsgErrorPage = beanRegresoSucursales.getMsgError();
				} else {
					mapParametros.put(COD_ERROR, lstrCodErrorPage);
					mapParametros.put(MSG_ERROR, lstrMsgErrorPage);
				}

				if (beanRegresoSucursales.getRegistros().size() == 0) {
					mapParametros.put(COD_AVISO, lstrCodErrorPage);
					mapParametros.put(MSG_AVISO, lstrMsgErrorPage);
					return altaSucursal(req, res);
				} else {
					req.getSession().setAttribute(REGISTRO_SUCURSALES,
							beanRegresoSucursales.getRegistros());
					
					beanConsulta.setLimiteSuperiorConsulta(beanRegresoSucursales
							.getReferenciaAdelante());
					beanConsulta.setLimiteInferiorConsulta(beanRegresoSucursales
							.getReferenciaAtras());
					
					// Asignamos el campo nombreSucursal
					
					beanConsulta.setNombreSucursalAtras(beanRegresoSucursales.getNombreSucursalAtras());
					beanConsulta.setNombreSucursal(beanRegresoSucursales.getNombreSucursal());
					
					debug("TEST B pagina " + paginaNum.trim() + " beanRegreso | ReferenciaAdelante: " + beanRegresoSucursales.getReferenciaAdelante());
					debug("TEST B pagina " + paginaNum.trim() + " beanRegreso | ReferenciaAtras: " + beanRegresoSucursales.getReferenciaAtras());
					
					mapParametros.put(REF_AVANZAR, beanRegresoSucursales
							.getReferenciaAdelante());
					mapParametros.put(REF_RETROCEDER, beanRegresoSucursales
							.getReferenciaAtras());
					mapParametros.put(HAY_ATRAS, beanRegresoSucursales
							.isMasAtras());
					mapParametros.put(HAY_ADELANTE, beanRegresoSucursales
							.isMasAdelante());
					mapParametros.put(PAGINA_NUM, paginaNum);
					
					//Asignamos el campo nombreSucursal
					mapParametros.put(NOMBRE_SUC_ATRAS, beanRegresoSucursales.getNombreSucursalAtras());
					mapParametros.put(NOMBRE_SUCURSAL, beanRegresoSucursales.getNombreSucursal());
					
					debug("Controller B pagina " + paginaNum.trim() + " beanRegresoSucursales.getNombreSucursalAtras(): " + beanRegresoSucursales.getNombreSucursalAtras());
					debug("Controller B pagina " + paginaNum.trim() + " beanRegresoSucursales.getNombreSucursal(): " + beanRegresoSucursales.getNombreSucursal());
					
					debug("TEST | B pagina " + paginaNum.trim() + " | sucursal anterior: " + beanConsulta.getNombreSucursalAtras());
					debug("TEST | B pagina " + paginaNum.trim() + " | sucursal actual: " + beanConsulta.getNombreSucursal());
					
				}
			}
			
		} catch (BusinessException e) {
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			mapParametros.put(COD_ERROR, lstrCodErrorPage);
			mapParametros.put(MSG_ERROR, lstrMsgErrorPage);
		}

		debug(CODIGO_ERROR + lstrCodErrorPage);
		debug(MENSAJE_ERROR + lstrMsgErrorPage);
		if(req.getAttribute(MAPA_PARAM)!= null) {
			final HashMap<String, Object> maParametros = (HashMap<String, Object>)req.getAttribute(MAPA_PARAM);
			mapParametros.put(COD_ERROR,maParametros.get(COD_ERROR));
			mapParametros.put(MSG_ERROR,maParametros.get(MSG_ERROR));
			mapParametros.put(COD_AVISO,maParametros.get(COD_AVISO));
			mapParametros.put(MSG_AVISO,maParametros.get(MSG_AVISO));
			req.removeAttribute(MAPA_PARAM);			
		}
		req.getSession().setAttribute(BEAN_CONSULTA_SUC, beanConsulta);
		return new ModelAndView(CONSULTA_SUCURSAL, mapParametros);
	}

	/**
	 * Detalles sucursal.
	 *
	 * @param codCorresponsal the cod corresponsal
	 * @param codSucursal the cod sucursal
	 * @param codErr the cod err
	 * @param msgErr the msg err
	 * @return the bean sucursal
	 */
	private BeanSucursal detallesSucursal(String codCorresponsal,
			String codSucursal, StringBuffer codErr, StringBuffer msgErr) {
		BeanSucursal beanRegreso = null;
		final BeanConsultaSucursal beanConsulta = new BeanConsultaSucursal();
		beanConsulta.setCodigoCorresponsalia(codCorresponsal);
		beanConsulta.setCodigoSucursal(codSucursal);
		beanConsulta.setIndicadorPaginacion("N");
		beanConsulta.setIndicadorFuncional("D");
		try {
			final BeanResultadoSucursal beansRegreso = bOCorresponsalesSucursalesFix
					.consultaSucursalCorresponsalia(beanConsulta,
							getArchitechBean());
			codErr.append(beansRegreso.getCodError());
			msgErr.append(beansRegreso.getMsgError());
			debug(CODIGO_ERROR + codErr);
			debug(MENSAJE_ERROR + msgErr);
			if (beansRegreso.getRegistros().size() == 1) {
				beanRegreso = beansRegreso.getRegistros().get(0);
			} else{
				beanRegreso = null;
			}
		} catch (BusinessException e) {
			showException(e);
			codErr.append(e.getCode());
			msgErr.append(e.getMessage());
			beanRegreso = null;
		}
		return beanRegreso;
	}

	/**
	 * Detalle sucursal.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "DetalleSucursal.do")
	public ModelAndView detalleSucursal(HttpServletRequest req,
			HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		info("Inicia DetalleSucursales");
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();
		final List<BeanSucursal> listaSucursales = (List<BeanSucursal>) req
				.getSession().getAttribute(REGISTRO_SUCURSALES);
		final String lstrSucursalSel = req.getParameter(SUC_SELEC);
		BeanSucursal beanSucursal = null;
		final String codCorresponsal = req.getParameter(CMB_CORRESPONSAL);
		final List<BeanCorresponsal> listaCorresponsales = (List<BeanCorresponsal>) req
				.getSession().getAttribute(LISTA_CORR_SUC);
		debug("Detalle - Registro seleccionado:" + lstrSucursalSel);
		try {
			final int iSucursalSel = Integer.parseInt(lstrSucursalSel);
			beanSucursal = listaSucursales.get(iSucursalSel);
		} catch (NumberFormatException e) {
			mapParametros.put(COD_ERROR, "NOSEL");
			mapParametros.put(MSG_ERROR, "NO se seleciono ningun registro");
			mapParametros.put(CMB_CORRESPONSAL, codCorresponsal);
			return new ModelAndView(CONSULTA_SUCURSAL, mapParametros);
		}

		for (BeanCorresponsal bCorresponsalTemp : listaCorresponsales) {
			if (bCorresponsalTemp.getCodigoCorresponsal().equals(
					codCorresponsal)) {
				debug(CORRESPONSAL_ENCONTRADO);
				debug(CODIGO_CORRESPONSAL
						+ bCorresponsalTemp.getCodigoCorresponsal());
				debug(NOMBRE_CORRESPONSAL
						+ bCorresponsalTemp.getNombreCorresponsal());
				mapParametros.put(CORRESPONSAL_SEL, bCorresponsalTemp);
				break;
			}
		}

		final StringBuffer codError = new StringBuffer();
		final StringBuffer msgError = new StringBuffer();
		final BeanSucursal BeanSucursal = detallesSucursal(codCorresponsal,
				beanSucursal.getNumId(), codError, msgError);
		if (beanSucursal != null) {
			mapParametros.put("detalleSucursal", BeanSucursal);
			return new ModelAndView("DetalleSucursal", mapParametros);
		} else {
			debug(CODIGO_ERROR + codError);
			debug(MENSAJE_ERROR + msgError);
			mapParametros.put(COD_ERROR, codError.toString());
			mapParametros.put(MSG_ERROR, msgError.toString());
		}
		mapParametros.put(CMB_CORRESPONSAL, codCorresponsal);
		return new ModelAndView(CONSULTA_SUCURSAL, mapParametros);
	}

	/**
	 * Modificar sucursal.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ModificarSucursal.do")
	public ModelAndView modificarSucursal(HttpServletRequest req,
			HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		info("Inicia DetalleSucursales");
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();
		final List<BeanSucursal> listaSucursales = (List<BeanSucursal>) req
				.getSession().getAttribute(REGISTRO_SUCURSALES);
		final String lstrSucursalSel = req.getParameter(SUC_SELEC);
		BeanSucursal beanSucursal = null;
		final String codCorresponsal = req.getParameter(CMB_CORRESPONSAL);
		final List<BeanCorresponsal> listaCorresponsales = (List<BeanCorresponsal>) req
				.getSession().getAttribute(LISTA_CORR_SUC);

		debug("Modificar - Registro seleccionado:" + lstrSucursalSel);
		try {
			final int iSucursalSel = Integer.parseInt(lstrSucursalSel);
			beanSucursal = listaSucursales.get(iSucursalSel);
		} catch (NumberFormatException e) {
			mapParametros.put(COD_ERROR, "NOSEL");
			mapParametros.put(MSG_ERROR, "NO se seleciono ningun registro");
			mapParametros.put(CMB_CORRESPONSAL, codCorresponsal);
			return new ModelAndView(CONSULTA_SUCURSAL, mapParametros);
		}

		for (BeanCorresponsal bCorresponsalTemp : listaCorresponsales) {
			if (bCorresponsalTemp.getCodigoCorresponsal().equals(
					codCorresponsal)) {
				debug(CORRESPONSAL_ENCONTRADO);
				debug(CODIGO_CORRESPONSAL
						+ bCorresponsalTemp.getCodigoCorresponsal());
				debug(NOMBRE_CORRESPONSAL
						+ bCorresponsalTemp.getNombreCorresponsal());
				mapParametros.put(CORRESPONSAL_SEL, bCorresponsalTemp);
				break;
			}
		}

		final StringBuffer codError = new StringBuffer();
		final StringBuffer msgError = new StringBuffer();
		final  BeanSucursal BeanSucursal = detallesSucursal(codCorresponsal,
				beanSucursal.getNumId(), codError, msgError);
		if (beanSucursal != null) {
			mapParametros.put("detalleSucursal", BeanSucursal);
			return new ModelAndView("ModificarSucursal", mapParametros);
		} else {
			debug(CODIGO_ERROR + codError);
			debug(MENSAJE_ERROR + msgError);
			mapParametros.put(COD_ERROR, codError.toString());
			mapParametros.put(MSG_ERROR, msgError.toString());

		}
		mapParametros.put(CMB_CORRESPONSAL, codCorresponsal);

		return new ModelAndView(CONSULTA_SUCURSAL, mapParametros);
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "RealizaModificacionSucursal.do")
	/**
	 * Realiza modificacion sucursal.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	public ModelAndView realizaModificacionSucursal(HttpServletRequest req,
			HttpServletResponse res) {
		info("Inicia Modificacion Sucursal");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();
		final BeanSucursal beanModifica = new BeanSucursal();
		final String idCorresponsal = req.getParameter(CMB_CORRESPONSAL);
		final String nombreSucursal = req.getParameter(NOMBRE_SUCURSAL);
		final String numId = req.getParameter(NUM_ID);
		final String codigoEstatus = req.getParameter("codigoEstatus");
		final String rfc = req.getParameter("rfc");
		final String codigoInterno = req.getParameter("codigoInterno");
		final String telefono = req.getParameter("telefono");
		final String calle = req.getParameter("calle");
		final String noInterior = req.getParameter("noInterior");
		final String noExterior = req.getParameter("noExterior");
		final String colonia = req.getParameter("colonia");
		final String entreCalles = req.getParameter("entreCalles");
		final String delegMunicipio = req.getParameter("delegMunicipio");
		final String ciudad = req.getParameter("ciudad");
		final String estado = req.getParameter("estado");
		final String codigoPostal = req.getParameter("codigoPostal");
		final String fronteriza = req.getParameter("fronteriza");
		final String zonaGeografica = req.getParameter("zonaGeografica");
		final String descZona = req.getParameter("descZona");
		final String region = req.getParameter("region");
		final String idOperacion = "M";
		String lstrCodErrorPage = "";
		String lstrMsgErrorPage = "";
		final List<BeanCorresponsal> listaCorresponsales = (List<BeanCorresponsal>) req
				.getSession().getAttribute(LISTA_CORR_SUC);

		for (BeanCorresponsal bCorresponsalTemp : listaCorresponsales) {
			if (bCorresponsalTemp.getCodigoCorresponsal()
					.equals(idCorresponsal)) {
				debug(CORRESPONSAL_ENCONTRADO);
				debug(CODIGO_CORRESPONSAL
						+ bCorresponsalTemp.getCodigoCorresponsal());
				debug(NOMBRE_CORRESPONSAL
						+ bCorresponsalTemp.getNombreCorresponsal());
				mapParametros.put(CORRESPONSAL_SEL, bCorresponsalTemp);
				break;
			}
		}

		debug("Id Corresponsal		:" + idCorresponsal);
		debug("Nombre Sucursal		:" + nombreSucursal);
		debug("Numero Identificacion:" + numId);
		debug("Codigo Estatus		:" + codigoEstatus);
		debug("RFC					:" + rfc);
		debug("Codigo Interno		:" + codigoInterno);
		debug("Operacion			:" + idOperacion);

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

		mapParametros.put("detalleSucursal", beanModifica);

		try {
			final BeanResultadoSucursal beanRegreso = bOCorresponsalesSucursalesFix
					.altaSucursalCorresponsalia(beanModifica,
							getArchitechBean());
			lstrCodErrorPage = beanRegreso.getCodError();
			lstrMsgErrorPage = beanRegreso.getMsgError();
			mapParametros.put(COD_AVISO, lstrCodErrorPage);
			mapParametros.put(MSG_AVISO, lstrMsgErrorPage);
		} catch (BusinessException e) {
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			mapParametros.put(COD_ERROR, lstrCodErrorPage);
			mapParametros.put(MSG_ERROR, lstrMsgErrorPage);
		}

		debug(CODIGO_ERROR + lstrCodErrorPage);
		debug(MENSAJE_ERROR + lstrMsgErrorPage);
		//return new ModelAndView(CONSULTA_SUCURSAL, mapParametros);
		req.setAttribute(MAPA_PARAM, mapParametros);
		return consultaSucursal(req, res);
	}

	/**
	 * Eliminar sucursal.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "EliminarSucursal.do")
	public ModelAndView eliminarSucursal(HttpServletRequest req,
			HttpServletResponse res){
		info("Inicia Eliminar Sucursal");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();
		final List<BeanSucursal> listaSucursales = (List<BeanSucursal>) req
				.getSession().getAttribute(REGISTRO_SUCURSALES);
		final String lstrSucursalSel = req.getParameter(SUC_SELEC);
		BeanSucursal beanSucursal = null;
		final String codCorresponsal = req.getParameter(CMB_CORRESPONSAL);
		String lstrCodErrorPage = "";
		String lstrMsgErrorPage = "";

		debug("Eliminar - Registro seleccionado:" + lstrSucursalSel);
		try {
			final int iSucursalSel = Integer.parseInt(lstrSucursalSel);
			beanSucursal = listaSucursales.get(iSucursalSel);
		} catch (NumberFormatException e) {
			mapParametros.put(COD_ERROR, "NOSEL");
			mapParametros.put(MSG_ERROR, "NO se seleciono ningun registro");
			mapParametros.put(CMB_CORRESPONSAL, codCorresponsal);
			return new ModelAndView(CONSULTA_SUCURSAL, mapParametros);

		}

		try {
			beanSucursal.setIdOperacion("B");
			beanSucursal.setIdCorresponsal(codCorresponsal);
			final BeanResultadoSucursal beanRegreso = bOCorresponsalesSucursalesFix
					.altaSucursalCorresponsalia(beanSucursal,
							getArchitechBean());
			lstrCodErrorPage = beanRegreso.getCodError();
			lstrMsgErrorPage = beanRegreso.getMsgError();
			req.setAttribute(COD_AVISO, lstrCodErrorPage);
			req.setAttribute(MSG_AVISO, lstrMsgErrorPage);
		} catch (BusinessException e) {
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			mapParametros.put(COD_ERROR, lstrCodErrorPage);
			mapParametros.put(MSG_ERROR, lstrMsgErrorPage);
		}

		debug(CODIGO_ERROR + lstrCodErrorPage);
		debug(MENSAJE_ERROR + lstrMsgErrorPage);
		mapParametros.put(CMB_CORRESPONSAL, codCorresponsal);

		//return consultaSucursal(req, res);
		return muestraCorresponsalesSucursales(req, res);
	}

	/**
	 * Alta sucursal.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "AltaSucursal.do")
	public ModelAndView altaSucursal(HttpServletRequest req,
			HttpServletResponse res){
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();
		final String codCorresponsal = req.getParameter(CMB_CORRESPONSAL);
		final List<BeanCorresponsal> listaCorresponsales = (List<BeanCorresponsal>) req
				.getSession().getAttribute(LISTA_CORR_SUC);

		for (BeanCorresponsal bCorresponsalTemp : listaCorresponsales) {
			if (bCorresponsalTemp.getCodigoCorresponsal().equals(
					codCorresponsal)) {
				debug(CORRESPONSAL_ENCONTRADO);
				debug(CODIGO_CORRESPONSAL
						+ bCorresponsalTemp.getCodigoCorresponsal());
				debug(NOMBRE_CORRESPONSAL
						+ bCorresponsalTemp.getNombreCorresponsal());
				mapParametros.put(CORRESPONSAL_SEL, bCorresponsalTemp);
				break;
			}
		}
		return new ModelAndView("AltaSucursal", mapParametros);

	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "RealizaAltaSucursal.do")
	/**
	 * Realiza alta sucursal.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	public ModelAndView realizaAltaSucursal(HttpServletRequest req,
			HttpServletResponse res)  {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();

		final BeanSucursal beanAlta = new BeanSucursal();
		final List<BeanCorresponsal> listaCorresponsales = (List<BeanCorresponsal>) req
				.getSession().getAttribute(LISTA_CORR_SUC);
		final String idCorresponsal = req.getParameter(CMB_CORRESPONSAL) == null ? CADENA_VACIA
				: req.getParameter(CMB_CORRESPONSAL);
		final String nombreSucursal = req.getParameter(NOMBRE_SUCURSAL) == null ? CADENA_VACIA
				: req.getParameter(NOMBRE_SUCURSAL);
		final String numId = req.getParameter(NUM_ID) == null ? CADENA_VACIA : req
				.getParameter(NUM_ID);
		final String codigoEstatus = req.getParameter("codigoEstatus") == null ? CADENA_VACIA
				: req.getParameter("codigoEstatus");
		final String rfc = req.getParameter("rfc") == null ? CADENA_VACIA : req
				.getParameter("rfc");
		final String codigoInterno = req.getParameter("codigoInterno") == null ? CADENA_VACIA
				: req.getParameter("codigoInterno");
		final String telefono = req.getParameter("telefono") == null ? CADENA_VACIA
				: req.getParameter("telefono");
		final String calle = req.getParameter("calle") == null ? CADENA_VACIA : req
				.getParameter("calle");
		final String noInterior = req.getParameter("noInterior") == null ? CADENA_VACIA
				: req.getParameter("noInterior");
		final String noExterior = req.getParameter("noExterior") == null ? CADENA_VACIA
				: req.getParameter("noExterior");
		final String colonia = req.getParameter("colonia") == null ? CADENA_VACIA
				: req.getParameter("colonia");
		final String entreCalles = req.getParameter("entreCalles") == null ? CADENA_VACIA
				: req.getParameter("entreCalles");
		final String delegMunicipio = req.getParameter("delegMunicipio") == null ? CADENA_VACIA
				: req.getParameter("delegMunicipio");
		final String ciudad = req.getParameter("ciudad") == null ? CADENA_VACIA : req
				.getParameter("ciudad");
		final String estado = req.getParameter("estado") == null ? CADENA_VACIA : req
				.getParameter("estado");
		final String codigoPostal = req.getParameter("codigoPostal") == null ? CADENA_VACIA
				: req.getParameter("codigoPostal");
		final String fronteriza = req.getParameter("fronteriza") == null ? CADENA_VACIA
				: req.getParameter("fronteriza");
		final String zonaGeografica = req.getParameter("zonaGeografica") == null ? CADENA_VACIA
				: req.getParameter("zonaGeografica");
		final String descZona = req.getParameter("descZona") == null ? CADENA_VACIA
				: req.getParameter("descZona");
		final String region = req.getParameter("region") == null ? CADENA_VACIA : req
				.getParameter("region");
		final String idOperacion = "A";

		mapParametros.put(CMB_CORRESPONSAL, idCorresponsal);

		String lstrCodErrorPage = "";
		String lstrMsgErrorPage = "";

		info("Id Corresponsal		:" + idCorresponsal);
		info("Nombre Sucursal		:" + nombreSucursal);
		info("Numero Identificacion	:" + numId);
		info("Codigo Estatus		:" + codigoEstatus);
		info("RFC					:" + rfc);
		info("Codigo Interno		:" + codigoInterno);
		info("Operacion				:" + idOperacion);

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

		try {
			final BeanResultadoSucursal beanRegreso = bOCorresponsalesSucursalesFix
					.altaSucursalCorresponsalia(beanAlta, getArchitechBean());
			lstrCodErrorPage = beanRegreso.getCodError();
			lstrMsgErrorPage = beanRegreso.getMsgError();
			req.setAttribute(COD_AVISO, beanRegreso.getCodError());
			req.setAttribute(MSG_AVISO, beanRegreso.getMsgError());
		} catch (BusinessException e) {
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			mapParametros.put(COD_ERROR, lstrCodErrorPage);
			mapParametros.put(MSG_ERROR, lstrMsgErrorPage);
			mapParametros.put("beanAlta", beanAlta);
			for (BeanCorresponsal bCorresponsalTemp : listaCorresponsales) {
				if (bCorresponsalTemp.getCodigoCorresponsal().equals(
						idCorresponsal)) {
					debug(CORRESPONSAL_ENCONTRADO);
					debug(CODIGO_CORRESPONSAL
							+ bCorresponsalTemp.getCodigoCorresponsal());
					debug(NOMBRE_CORRESPONSAL
							+ bCorresponsalTemp.getNombreCorresponsal());
					mapParametros.put(CORRESPONSAL_SEL, bCorresponsalTemp);
					break;
				}
			}
			return new ModelAndView("MuestraCorresponsalesSucursales", mapParametros);
		}

		debug(CODIGO_ERROR + lstrCodErrorPage);
		debug(MENSAJE_ERROR + lstrMsgErrorPage);

		//return consultaSucursal(req, res);
		return muestraCorresponsalesSucursales(req, res);
	}

	/**
	 * Activar desactivar sucursal.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ActivarDesactivarSucursal.do")
	public ModelAndView activarDesactivarSucursal(HttpServletRequest req,
			HttpServletResponse res) {
		info("Inicia Baja Operaciones Corresponsalia");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> mapParametros = new HashMap<String, Object>();
		final String regSel = req.getParameter("regSel") == null ? CADENA_VACIA : req
				.getParameter("regSel");
		final String idCorresponsal = req.getParameter(CMB_CORRESPONSAL) == null ? CADENA_VACIA
				: req.getParameter(CMB_CORRESPONSAL);
		String estadoActual = req.getParameter("estadoActual") == null ? CADENA_VACIA
				: req.getParameter("estadoActual");
		final List<BeanSucursal> listaSucursales = (List<BeanSucursal>) req
				.getSession().getAttribute(REGISTRO_SUCURSALES);
		BeanSucursal beanSucursal = new BeanSucursal();
		String lstrCodErrorPage = "";
		String lstrMsgErrorPage = "";

		debug("Registro a Modificar:" + regSel);

		try {
			beanSucursal = listaSucursales.get(Integer.parseInt(regSel));
		} catch (NumberFormatException e) {
			debug("No se pudo obtener el bean");
		}
		final StringBuffer codError = new StringBuffer();
		final StringBuffer msgError = new StringBuffer();
		final BeanSucursal BeanSucursal = detallesSucursal(idCorresponsal,
				beanSucursal.getNumId(), codError, msgError);
		if (beanSucursal != null) {
			BeanSucursal.setIdOperacion("M");
			if ("CS2".equalsIgnoreCase(estadoActual)) {
				estadoActual = "CS3";
			} else {
				estadoActual = "CS2";
			}
			BeanSucursal.setCodigoEstatus(estadoActual);
		} else {
			debug(CODIGO_ERROR + codError);
			debug(MENSAJE_ERROR + msgError);
			mapParametros.put(COD_ERROR, codError.toString());
			mapParametros.put(MSG_ERROR, msgError.toString());
		}
		try {
			final BeanResultadoSucursal beanRegreso = bOCorresponsalesSucursalesFix
					.altaSucursalCorresponsalia(BeanSucursal,
							getArchitechBean());
			lstrCodErrorPage = beanRegreso.getCodError();
			lstrMsgErrorPage = beanRegreso.getMsgError();
			req.setAttribute(COD_AVISO, lstrCodErrorPage);
			req.setAttribute(MSG_AVISO, lstrMsgErrorPage);
		} catch (BusinessException e) {
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			// mapParametros.put(COD_ERROR, lstrCodErrorPage);
			// mapParametros.put(MSG_ERROR, lstrMsgErrorPage);
			req.setAttribute(COD_ERROR, lstrCodErrorPage);
			req.setAttribute(MSG_ERROR, lstrMsgErrorPage);
		}

		debug(CODIGO_ERROR + lstrCodErrorPage);
		debug(MENSAJE_ERROR + lstrMsgErrorPage);

		return consultaSucursal(req, res);
	}

	/**
	 * Exporta sucursales.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ExportaSucursales.do")
	public ModelAndView exportaSucursales(HttpServletRequest req,
			HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final HashMap<String, Object> map = new HashMap<String, Object>();
		final HashMap<String, Object> lhsmParametros = new HashMap<String, Object>();
		final List<BeanSucursal> listaSucursales = (List<BeanSucursal>) req
				.getSession().getAttribute(REGISTRO_SUCURSALES);
		final String codCorresponsal = req.getParameter(CMB_CORRESPONSAL);
		String nombreCorr = null;
		final List<BeanCorresponsal> listaCorresponsales = (List<BeanCorresponsal>) req
		.getSession().getAttribute(LISTA_CORR_SUC);
		
		lhsmParametros.put(CMB_CORRESPONSAL, codCorresponsal);
		
		for (BeanCorresponsal bCorresponsalTemp : listaCorresponsales) {
			if (bCorresponsalTemp.getCodigoCorresponsal().equals(codCorresponsal)) {
				info(CORRESPONSAL_ENCONTRADO);				
				info(NOMBRE_CORRESPONSAL + bCorresponsalTemp.getNombreCorresponsal());
				nombreCorr = bCorresponsalTemp.getNombreCorresponsal();
				break;
			}
		}
		if (listaSucursales == null	|| (listaSucursales != null && listaSucursales.isEmpty())) {
			lhsmParametros.put(COD_ERROR, "NOEXPORT");
			lhsmParametros.put(MSG_ERROR, "NO HAY REGISTROS PARA EXPORTAR");
			return new ModelAndView(CONSULTA_SUCURSAL, lhsmParametros);
		}
		
		final JRDataSource dataSource = new JRBeanArrayDataSource(listaSucursales.toArray());
		map.put("titulos", new String[] { "Codigo", "Nombre", "Estatus" });
		map.put("propiedades", new String[] { NUM_ID, NOMBRE_SUCURSAL, "descEstatus" });
		final DateFormat Dformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss aa", new Locale("ES","MX")); 
		map.put("fechaReporte", Dformat.format(java.util.Calendar.getInstance().getTime()));
		map.put("nombreCorr", nombreCorr);
		return new ModelAndView(new ExportaXLS("Sucursales",
				"/com/isban/corresponsalia/reportes/doctos/sucursales.jasper",
				dataSource), map);
	}
	
}