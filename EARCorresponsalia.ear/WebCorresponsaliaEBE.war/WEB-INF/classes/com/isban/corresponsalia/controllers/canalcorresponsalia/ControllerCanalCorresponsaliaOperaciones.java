package com.isban.corresponsalia.controllers.canalcorresponsalia;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanABMMantenimientoOperCorresponsal;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanAltaOperacionesCatalogo;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCatalogo;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCorresponsal;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanResultadoOperacionesCatalogo;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanResultadoOperacionesCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.comunes.BeanOperacion;
import com.isban.corresponsalia.beans.comunes.BeanOperacionCatalogo;
import com.isban.corresponsalia.bo.canalcorresponsalia.BOCorresponsaliaOperaciones;
import com.isban.corresponsalia.comunes.Utils;
import com.isban.corresponsalia.reportes.ExportaXLS;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

// TODO: Auto-generated Javadoc
/**
 * The Class ControllerCanalCorresponsaliaOperaciones.
 */
@Controller
public class ControllerCanalCorresponsaliaOperaciones extends ArchitechEBE{

	/** The Constant NEW_ACHI. */
	private static final String NEW_ACHI = "NewArchitechSession";
	
	/** The Constant CODIGO_OPERACION. */
	private static final String CODIGO_OPERACION= "codigoOperacion";
	
	/** The Constant OPERACION. */
	private static final String OPERACION ="operacion";
	
	/** The Constant ENTIDAD. */
	private static final String ENTIDAD = "0014";
	
	/** The Constant CANAL. */
	private static final String CANAL = "14";
	
	/** The Constant TXT_IMP_MAX. */
	private static final String TXT_IMP_MAX = "txtImporteMax";
	
	/** The Constant TXT_IMP_MIN. */
	private static final String TXT_IMP_MIN = "txtImporteMin";
	
	/** The Constant TXT_ACUM_DIARIO. */
	private static final String TXT_ACUM_DIARIO = "txtAcumuladoDiario";
	
	/** The Constant TXT_HORA_INI. */
	private static final String TXT_HORA_INI = "txtHoraIni";
	
	/** The Constant TXT_HORA_FIN. */
	private static final String TXT_HORA_FIN = "txtHoraFin"; 
	
	/** The Constant TXT_ACUM_MES. */
	private static final String TXT_ACUM_MES = "txtAcumuladoMes";
	
	/** The Constant TXT_CAN. */
	private static final String TXT_CAN ="can";
	
	/** The Constant TXT_NI_PAR. */
	private static final String TXT_NI_PAR = "nivPar";
	
	/** The Constant TXT_TIPO_OPER. */
	private static final String TIPO_OPER = "tipoOperacion";
	
	/** The Constant TXT_TIPO_OPER. */
	private static final String TXT_TIPO_OPER = "txtTipoOperacion";
	 
	
	/** The Constant TXT_LLAMA_BO. */
	private static final String TXT_LLAMA_BO = "Llama BO Corresponsalia"; 
	
	/** The Constant TXT_REGRESA. */
	private static final String TXT_REGRESA = "Regreso BO Corresponsalia :";
	
	/** The Constant TXT_NOMBRE. */
	private static final String TXT_NOMBRE = "txtNombre";
	
	/** The Constant TXT_CANAL. */
	private static final String TXT_CANAL ="txtCanal";
	
	/** The Constant TXT_NIVEL_PARAM. */
	private static final String TXT_NIVEL_PARAM ="txtNivelParametria"; 
	
	/** The Constant TXT_0000. */
	private static final String TXT_0000 ="0000";
	
	/** The Constant TXT_MSG_RESULTADO. */
	private static final String TXT_MSG_RESULTADO ="msgResultado";
	
	/** The Constant REF_RETRO. */
	private static final String REF_RETRO ="referenciaRetroceder";
	
	/** The Constant TXT_ENTIDAD. Entidad del banco*/
	private static final String  TXT_ENTIDAD = "txtEntidad";
	
	/** The Constant TXT_ASOC. Transaccion asociada*/
	private static final String  TXT_TRX_ASOC = "trxAsoc";
	
	/** The Constant TXT_OPER_CAN. Consecutivo de operacion por canal*/
	private static final String  TXT_OPER_CAN ="consecOperCanal";
	
	/** The Constant TXT_EQUI_OPER_UNO. Tipo de operacion*/
	private static final String  TXT_EQUI_OPER_UNO ="equiTipOperUno";
	
	/** The Constant TXT_EQUI_OPER_TRES. Tipo de operacion tres*/
	private static final String  TXT_EQUI_OPER_TRES ="equiTipOperTres";
	
	/** The Constant TXT_FEC_ALTA. Fecha de alta*/
	private static final String  TXT_FEC_ALTA ="fechAlta";
	
	/** The Constant TXT_SES. Sesion*/
	private static final String  TXT_SES ="SES";
	
	/** The Constant TXT_DESC. Descripcion de la operacion*/
	private static final String  TXT_DESC ="txtDescripcion";
	
	/** The Constant TXT_DESC_COR. Descripcion corta de la operacion*/
	private static final String  TXT_DESC_COR ="txtDescripcionCorta";
	
	/** The Constant TXT_R26. Valor en R26 de la operacion*/
	private static final String  TXT_R26 = "txtValorR26";
	
	
	
	

	/** The cadena vacia. */
	private static String cadenaVacia = "";

	/** The b o corresponsalia operaciones. */
	transient private BOCorresponsaliaOperaciones bOCorresponsaliaOperaciones;
	
	
	/**
	 * * * Sets the bO corresponsalia operaciones.
	 *
	 * @param bOCorresponsaliaOperaciones the new bO corresponsalia operaciones
	 */
	public void setBOCorresponsaliaOperaciones(BOCorresponsaliaOperaciones bOCorresponsaliaOperaciones){
		this.bOCorresponsaliaOperaciones = bOCorresponsaliaOperaciones;
	}
	
	@RequestMapping(value="MuestraAltaModificacion.do")
	/**
	 * * * muestraAltaModificacion.
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	public ModelAndView muestraAltaModificacion(HttpServletRequest req,
			HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ACHI ));
		String codigoOperacion = req.getParameter(CODIGO_OPERACION) == null ? "" : req.getParameter(CODIGO_OPERACION);
		String operacion = null;
		if (req.getParameter(OPERACION) != null) {
			operacion = req.getParameter(OPERACION);
		} else if (req.getAttribute(OPERACION) != null) {
			operacion = req.getAttribute(OPERACION).toString();
		} else {
			operacion = cadenaVacia;
		}
		final HashMap<String,Object> mapParametros = new HashMap<String,Object>();
		String existenRegistros = "";
		if(req.getAttribute("existenRegistros") != null){
			existenRegistros = req.getAttribute("existenRegistros").toString();
		}
		if("A".equals(operacion) || "false".equals(existenRegistros)){
//			final BeanConsultaOperacionesCatalogo beanConsultaOperaciones = new BeanConsultaOperacionesCatalogo();
			BeanResultadoOperacionesCatalogo beanResultadoOperaciones = new BeanResultadoOperacionesCatalogo();
//			
//			beanConsultaOperaciones.setTipoOperacion("C");
//			beanConsultaOperaciones.setEntidad(ENTIDAD);
//			beanConsultaOperaciones.setCanalCorresponsal(CANAL);
//			beanConsultaOperaciones.setNivelRegistroOperacion("01");
//			beanConsultaOperaciones.setIndicadorPaginacion("N");
			
			final String importeMaximo = req.getParameter(TXT_IMP_MAX ) == null ? cadenaVacia : 
				req.getParameter(TXT_IMP_MAX );
			final String importeMinimo = req.getParameter(TXT_IMP_MIN) == null ? cadenaVacia : 
				req.getParameter(TXT_IMP_MIN);
			final String acumuladoDiario = req.getParameter(TXT_ACUM_DIARIO) == null ? cadenaVacia : 
				req.getParameter(TXT_ACUM_DIARIO);
			final String horaInicio = req.getParameter(TXT_HORA_INI) == null ? cadenaVacia : 
				req.getParameter(TXT_HORA_INI);
			final String horaFin = req.getParameter(TXT_HORA_FIN) == null ? cadenaVacia : 
				req.getParameter(TXT_HORA_FIN);
			final String acumuladoMensual = req.getParameter(TXT_ACUM_MES) == null ? cadenaVacia : 
				req.getParameter(TXT_ACUM_MES);
			
//			info("Llama BO Consulta Operacion Catalogo");
//			beanResultadoOperaciones = bOCorresponsaliaOperaciones.ConsultaOperacionesCatalogo(
//					beanConsultaOperaciones, getArchitechBean());
			beanResultadoOperaciones = getOperacionesCat();
			String[] paramOperacion = {cadenaVacia};
			if(!"".equals(codigoOperacion)){
				paramOperacion = codigoOperacion.split("\\|");
			}
			mapParametros.put(CODIGO_OPERACION , paramOperacion[0]);
			mapParametros.put("listaOperacionesCatalogo" , beanResultadoOperaciones.getRegistros());
			mapParametros.put(TXT_IMP_MAX , importeMaximo);
			mapParametros.put(TXT_IMP_MIN, importeMinimo);
			mapParametros.put(TXT_ACUM_DIARIO, acumuladoDiario);
			mapParametros.put(TXT_HORA_INI, horaInicio);
			mapParametros.put(TXT_HORA_FIN, horaFin);
			mapParametros.put(TXT_ACUM_MES, acumuladoMensual);
			
			info("Regreso BO Consulta Operacion Catalogo" + beanResultadoOperaciones.getNumeroRegistros());
			return new ModelAndView("AltaOperaciones", mapParametros);
		}
			
		if("M".equals(operacion)) {
					
			final BeanConsultaOperacionesCorresponsal beanConsulta = new BeanConsultaOperacionesCorresponsal();
			codigoOperacion = req.getParameter(CODIGO_OPERACION) == null ? cadenaVacia : 
				req.getParameter(CODIGO_OPERACION);
			final String canal = req.getParameter(TXT_CAN) == null ? cadenaVacia : 
				req.getParameter(TXT_CAN);
			final String nivelParametria = req.getParameter(TXT_NI_PAR) == null ? cadenaVacia : 
				req.getParameter(TXT_NI_PAR);
			final String tipoOperacion = req.getParameter(TIPO_OPER) == null ? cadenaVacia : 
				req.getParameter(TIPO_OPER);
			
			
			String tipoConsulta = req.getParameter("tipoConsulta") == null ? cadenaVacia : 
				req.getParameter("tipoConsulta");
			
			BeanResultadoOperacionesCatalogo beanResultadoOperaciones = new BeanResultadoOperacionesCatalogo();

			beanResultadoOperaciones = getOperacionesCat();
			
			debug ("Codigo de error CAT: " + beanResultadoOperaciones.getCodError());
			debug ("Desc error CAT" + beanResultadoOperaciones.getMsgError());
			debug ("Numero de registros CAT" + beanResultadoOperaciones.getNumeroRegistros());
			
			final BeanOperacionCatalogo operacionesCat = getOperacionCat(beanResultadoOperaciones,codigoOperacion);		
			
//			if (operacionesCat!=null){
				mapParametros.put(TXT_TRX_ASOC,operacionesCat.getClaveTransaccionAsociada());
				mapParametros.put("trxAsocAnt",operacionesCat.getClaveTransaccionAsociadaAnterior());
				mapParametros.put(TXT_OPER_CAN,operacionesCat.getConsecutivoOperacionNivelCanal());
				mapParametros.put("descCorta",operacionesCat.getDescripcionCorta());
				mapParametros.put("descLarga",operacionesCat.getDescripcionLarga());
				mapParametros.put("equiTipOperDos",operacionesCat.getEquivalenciaTipoOeracionDos());
				mapParametros.put(TXT_EQUI_OPER_UNO,operacionesCat.getEquivalenciaTipoOeracionUno());
				mapParametros.put(TXT_EQUI_OPER_TRES,operacionesCat.getEquivalenciaTipoOeracionTres());
				mapParametros.put(TXT_FEC_ALTA,operacionesCat.getFechaAltaOperacion());
				
				debug(TXT_TRX_ASOC + "--> " + operacionesCat.getClaveTransaccionAsociada());
				debug("trxAsocAnt--> " + operacionesCat.getClaveTransaccionAsociadaAnterior());
				debug(TXT_OPER_CAN + "--> " + operacionesCat.getConsecutivoOperacionNivelCanal());
				debug("descCorta--> " + operacionesCat.getDescripcionCorta());
				debug("descLarga--> " + operacionesCat.getDescripcionLarga());
				debug("equiTipOperDos--> " + operacionesCat.getEquivalenciaTipoOeracionDos());
				debug(TXT_EQUI_OPER_UNO + "--> " + operacionesCat.getEquivalenciaTipoOeracionUno());
				debug(TXT_EQUI_OPER_TRES + "--> " + operacionesCat.getEquivalenciaTipoOeracionTres());
				debug(TXT_FEC_ALTA + "--> " + operacionesCat.getFechaAltaOperacion());
				
				debug("CodigoOperacion             :" + codigoOperacion);
				debug("Canal               : " + canal);
				debug("Nivel de Parametria : " + nivelParametria);
				debug("Tipo Operacion      : " + tipoOperacion);
				debug("Tipo Consulta       : " + tipoConsulta);
				
				tipoConsulta = "detalle";			
				beanConsulta.setEntidad(ENTIDAD);
				beanConsulta.setCanal(CANAL);
				beanConsulta.setNivelParametria("01");
				beanConsulta.setTipoConsulta("D");
				beanConsulta.setCodigoOperacion(codigoOperacion);
				info(TXT_LLAMA_BO);
				
				final BeanResultadoOperacionesCorresponsal beanRegreso = bOCorresponsaliaOperaciones.consultaOperacionesCorresponsalia(beanConsulta, getArchitechBean());
				info(TXT_LLAMA_BO);
				info(TXT_REGRESA + beanRegreso.getNumeroRegistros());
			
				if(beanRegreso.getNumeroRegistros() > 0){
								
					mapParametros.put("codPagError" , beanRegreso.getCodError());
					mapParametros.put("msgPageError" , beanRegreso.getMsgError());
					mapParametros.put("beanResultado" , beanRegreso.getRegistros().get(0));
					mapParametros.put(CODIGO_OPERACION , beanRegreso.getRegistros().get(0).getCodigoOperacion());
					mapParametros.put("nombre" , beanRegreso.getRegistros().get(0).getDesCorta());
					mapParametros.put("importeMaximo" , beanRegreso.getRegistros().get(0).getImporteMaximoOperacionFront());
					mapParametros.put("importeMinimo" , beanRegreso.getRegistros().get(0).getImporteMinimoOperacionFront());
					mapParametros.put("acumuladoDiario" , beanRegreso.getRegistros().get(0).getAcumuladoDiarioFront());
					mapParametros.put("horaInicio" , beanRegreso.getRegistros().get(0).getHoraInicioFront());
					mapParametros.put("horaFinal" , beanRegreso.getRegistros().get(0).getHoraFinalFront());
					mapParametros.put("acumuladoMensual" , beanRegreso.getRegistros().get(0).getAcumuladoMensualFront());
					return new ModelAndView("ModificacionOperaciones", mapParametros);
				}
//			}else{
//				mapParametros.put("MSGERR" , ERR0052);
//				mapParametros.put("msgError" , DescERR0052);
//			}
		} 
		return new ModelAndView("ConsultaOperaciones");
	}
	
	/** altaOperaciones
	 * @param req
	 * @param res
	 * @return consulta de operaciones 
	 */
	@RequestMapping(value="AltaOperaciones.do")
	public ModelAndView altaOperaciones(HttpServletRequest req,
			HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ACHI ));
		final HashMap<String,Object> mapParametros = new HashMap<String,Object>();
		info("Inicia Alta Operaciones Corresponsalia");
		
		String[] paramOperacion= null;
		final BeanABMMantenimientoOperCorresponsal beanAlta = new BeanABMMantenimientoOperCorresponsal();
		final String nombre = req.getParameter(TXT_NOMBRE) == null ? cadenaVacia : 
			req.getParameter(TXT_NOMBRE);
		final String importeMaximo = req.getParameter(TXT_IMP_MAX ) == null ? cadenaVacia : 
			req.getParameter(TXT_IMP_MAX );
		final String importeMinimo = req.getParameter(TXT_IMP_MIN) == null ? cadenaVacia : 
			req.getParameter(TXT_IMP_MIN);
		final String acumuladoDiario = req.getParameter(TXT_ACUM_DIARIO) == null ? cadenaVacia : 
			req.getParameter(TXT_ACUM_DIARIO);
		final String horaInicio = req.getParameter(TXT_HORA_INI) == null ? cadenaVacia : 
			req.getParameter(TXT_HORA_INI);
		final String horaFin = req.getParameter(TXT_HORA_FIN) == null ? cadenaVacia : 
			req.getParameter(TXT_HORA_FIN);
		final String acumuladoMensual = req.getParameter(TXT_ACUM_MES) == null ? cadenaVacia : 
			req.getParameter(TXT_ACUM_MES);
		final String tipoOperacion = req.getParameter(TXT_TIPO_OPER) == null ? cadenaVacia : 
			req.getParameter(TXT_TIPO_OPER);
		final String entidad = req.getParameter(TXT_ENTIDAD) == null ? cadenaVacia : 
			req.getParameter(TXT_ENTIDAD);
		final String canal = req.getParameter(TXT_CANAL) == null ? cadenaVacia : 
			req.getParameter(TXT_CANAL);
		final String nivelParametria= req.getParameter(TXT_NIVEL_PARAM) == null ? "01" : 
			req.getParameter(TXT_NIVEL_PARAM);
		final String codigoOperacion = req.getParameter("cmbOperacionCatalogo") == null ? "" : 
			req.getParameter("cmbOperacionCatalogo");
		if(!"".equals(codigoOperacion)){
			paramOperacion = codigoOperacion.split("\\|");
			info(paramOperacion[0]);
			info(paramOperacion[1]);
			info(paramOperacion[2]);
		}

		debug("Nombre            :" + nombre);
		debug("Importe Maximo    :" + importeMaximo);
		debug("Importe Minimo    :" + importeMinimo);
		debug("Acumulado Diario  :" + acumuladoDiario);
		debug("Hora Inicio       :" + horaInicio);
		debug("Hora Fin          :" + horaFin);
		debug("Acumulado Mensual :" + acumuladoMensual);
		debug("Tipo Operacion     :" + tipoOperacion);
		debug("Entidad            :" + entidad);
		debug("Canal              :" + canal);
		debug("Nivel de Parametria:" + nivelParametria);
		//Definir match con Front
		//beanAlta.setNombre(nombre);
		beanAlta.setImporteMaximoOperacionFront(importeMaximo);
		beanAlta.setImporteMinimoOperacionFront(importeMinimo);
		beanAlta.setAcumuladoDiarioFront(acumuladoDiario);
		beanAlta.setHoraInicioFront(horaInicio);
		beanAlta.setHoraFinalFront(horaFin);
		beanAlta.setAcumuladoMensualFront(acumuladoMensual);
		beanAlta.setTipoMovimiento("A");
		beanAlta.setTipoOperacionCorr(tipoOperacion);
		beanAlta.setEntidad(entidad);
		beanAlta.setCanal(canal);
		beanAlta.setNivelParametria(nivelParametria);
		beanAlta.setEntidad(ENTIDAD);
		beanAlta.setCanal(CANAL);
		beanAlta.setTransaccion("TX99");	//hardcode
		beanAlta.setCodigoOperacion(paramOperacion[0]);	//hardcode
		beanAlta.setCodigoOperacionTrx(paramOperacion[1]);
		beanAlta.setCodigoOperacionTrxSec(paramOperacion[2]);
		beanAlta.setTipoOperacionCorr("0123456789");	//hardcode
		beanAlta.setDivisa("DV1");
		beanAlta.setCodigoOperacionTrx(TXT_0000);//borrar
		beanAlta.setCodigoOperacionTrxSec(TXT_0000);//borrar		
		
		
		mapParametros.put(CODIGO_OPERACION , beanAlta.getCodigoOperacion());
		//mapParametros.put("nombre" , beanAlta.getDesCorta());
		mapParametros.put("importeMaximo" , beanAlta.getImporteMaximoOperacionFront());
		mapParametros.put("importeMinimo" , beanAlta.getImporteMinimoOperacionFront());
		mapParametros.put("acumuladoDiario" , beanAlta.getAcumuladoDiarioFront());
		mapParametros.put("horaInicio" , beanAlta.getHoraInicioFront());
		mapParametros.put("horaFinal" , beanAlta.getHoraFinalFront());
		mapParametros.put("acumuladoMensual" , beanAlta.getAcumuladoMensualFront());
				
		
		info(TXT_LLAMA_BO);
		final BeanResultadoOperacionesCorresponsal beanRegreso = 
			bOCorresponsaliaOperaciones.altaOperacionesCorresponsalia(beanAlta, getArchitechBean());
		info(TXT_REGRESA + beanRegreso.getCodError());
		
		if(!cadenaVacia.equals(beanRegreso.getCodError())){
			req.setAttribute(TXT_MSG_RESULTADO, beanRegreso.getCodError() + ":" + beanRegreso.getMsgError());
		}		
		//Agregar los datos del combo
		info("Finaliza Alta Operaciones Corresponsalia");
		
		
		if (beanRegreso.getCodError().indexOf("E") >= 0) {
			req.setAttribute(OPERACION, 'A');
			return muestraAltaModificacion(req, res);
		}		
		return consultaOperaciones(req, res);
	}	
	
	/**consultaOperaciones.
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="ConsultaOperaciones.do",method = RequestMethod.POST)
	public ModelAndView consultaOperaciones(HttpServletRequest req,
			HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ACHI ));
		info("Inicia Consulta Operaciones Corresponsalia");
		final HashMap<String,Object> mapParametros = new HashMap<String,Object>();
		final HttpSession session = req.getSession(); 
		final BeanConsultaOperacionesCorresponsal beanConsulta = new BeanConsultaOperacionesCorresponsal();
		
		final String	lstrOpcionPag        = req.getParameter("opcAvanzarRetroceder")==null?"":req.getParameter("opcAvanzarRetroceder");
		final String	referenciaAvanzar    = req.getParameter("referenciaAvanzar")==null?"":req.getParameter("referenciaAvanzar");
		final String	referenciaRetroceder = req.getParameter(REF_RETRO)==null?"":req.getParameter(REF_RETRO);
		int refContadorPag		 = req.getParameter("refContadorPag") == null ? 0 : Integer.parseInt(req.getParameter("refContadorPag"));
				
				
		beanConsulta.setEntidad(ENTIDAD);
		beanConsulta.setCanal(CANAL);
		beanConsulta.setNivelParametria("01");
		
		beanConsulta.setTipoConsulta("L");
		if(!"".equals(lstrOpcionPag.trim())){
			beanConsulta.setIndicadorPag("P");
			beanConsulta.setLimiteInferiorConsulta(referenciaAvanzar);
			beanConsulta.setLimiteSuperiorConsulta(referenciaRetroceder);
			beanConsulta.setIndicadorDirreccion(lstrOpcionPag);
			if ("A".equals(lstrOpcionPag.trim())) {
				refContadorPag++;
			} else {
				refContadorPag--;
			}
		} else {
			beanConsulta.setIndicadorPag("N");
		}

		info(TXT_LLAMA_BO);
		final BeanResultadoOperacionesCorresponsal beanRegreso = bOCorresponsaliaOperaciones.consultaOperacionesCorresponsalia(beanConsulta, getArchitechBean());
		info(TXT_LLAMA_BO);
		info(TXT_REGRESA + beanRegreso.getNumeroRegistros());
		if(beanRegreso.getNumeroRegistros() > 0){
			session.setAttribute("listaRegistrosCanalCorresponsalia", beanRegreso.getRegistros());
			mapParametros.put("registros" , beanRegreso.getRegistros().iterator());
			mapParametros.put("referenciaAvanzar"   , beanRegreso.getReferenciaAvanzar());
			mapParametros.put(REF_RETRO, beanRegreso.getReferenciaRetroceder());
			
		}		
		
		if (refContadorPag == 0) {			
			mapParametros.put(REF_RETRO, null);
		}
		
		if(req.getAttribute(TXT_MSG_RESULTADO) != null){
			mapParametros.put(TXT_MSG_RESULTADO , req.getAttribute(TXT_MSG_RESULTADO));
		}
		
		if (beanRegreso.getCodError().indexOf("DLE") < -1 && beanRegreso.getNumeroRegistros() == 0) {
			info("Finaliza Consulta Operaciones Corresponsalia No Existe Registros");
			req.setAttribute("existenRegistros", "false");
			req.setAttribute(TXT_SES, req.getParameter(TXT_SES));
			return muestraAltaModificacion(req, res);
		}
		if(beanRegreso.getCodError().indexOf("DLE") > -1) {
			mapParametros.put("msgError", beanRegreso.getMsgError());
			mapParametros.put("MSGERR", beanRegreso.getCodError());
		}			
		mapParametros.put("refContadorPag", refContadorPag);
		info("Finaliza Consulta Operaciones Corresponsalia");
		req.setAttribute(TXT_SES, req.getParameter(TXT_SES));
		mapParametros.put(TXT_SES, req.getParameter(TXT_SES));

		return new ModelAndView("ConsultaOperaciones", mapParametros);
	}
	
	/** detalleOperacion.
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="DetalleOperacion.do")
	public ModelAndView detalleOperacion(HttpServletRequest req,
			HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ACHI ));
		info("Inicia Consulta Detalle Operaciones Corresponsalia");
		final HashMap<String,Object> mapParametros = new HashMap<String,Object>();
		
		final BeanConsultaOperacionesCorresponsal beanConsulta = new BeanConsultaOperacionesCorresponsal();
		final String codigoOperacion = req.getParameter(CODIGO_OPERACION) == null ? cadenaVacia : 
			req.getParameter(CODIGO_OPERACION);
		final String canal = req.getParameter(TXT_CAN) == null ? cadenaVacia : 
			req.getParameter(TXT_CAN);
		final String nivelParametria = req.getParameter(TXT_NI_PAR) == null ? cadenaVacia : 
			req.getParameter(TXT_NI_PAR);
		final String tipoOperacion = req.getParameter(TIPO_OPER) == null ? cadenaVacia : 
			req.getParameter(TIPO_OPER);
		
		
		debug("codigoOperacion             :" + codigoOperacion);
		debug("Canal               :" + canal);
		debug("Nivel de Parametria :" + nivelParametria);
		debug("Tipo Operacion      :" + tipoOperacion);
		//Eliminar Info
		info("codigoOperacion             :" + codigoOperacion);
		info("Canal               :" + canal);
		info("Nivel de Parametria :" + nivelParametria);
		info("Tipo Operacion      :" + tipoOperacion);
		
		BeanResultadoOperacionesCatalogo beanResultadoOperaciones = new BeanResultadoOperacionesCatalogo();

		beanResultadoOperaciones = getOperacionesCat();
		
		debug ("Codigo de error CAT: " + beanResultadoOperaciones.getCodError());
		debug ("Desc error CAT" + beanResultadoOperaciones.getMsgError());
		debug ("Numero de registros CAT" + beanResultadoOperaciones.getNumeroRegistros());
		
		final BeanOperacionCatalogo operacionesCat = getOperacionCat(beanResultadoOperaciones,codigoOperacion);		
	
		mapParametros.put(TXT_TRX_ASOC,operacionesCat.getClaveTransaccionAsociada());
		mapParametros.put("trxAsocAnt",operacionesCat.getClaveTransaccionAsociadaAnterior());
		mapParametros.put(TXT_OPER_CAN,operacionesCat.getConsecutivoOperacionNivelCanal());
		mapParametros.put("descCorta",operacionesCat.getDescripcionCorta());
		mapParametros.put("descLarga",operacionesCat.getDescripcionLarga());
		mapParametros.put("equiTipOperDos",operacionesCat.getEquivalenciaTipoOeracionDos());
		mapParametros.put(TXT_EQUI_OPER_UNO,operacionesCat.getEquivalenciaTipoOeracionUno());
		mapParametros.put(TXT_EQUI_OPER_TRES,operacionesCat.getEquivalenciaTipoOeracionTres());
		mapParametros.put(TXT_FEC_ALTA,operacionesCat.getFechaAltaOperacion());
			
		debug(TXT_TRX_ASOC + "--> " + operacionesCat.getClaveTransaccionAsociada());
		debug("trxAsocAnt--> " + operacionesCat.getClaveTransaccionAsociadaAnterior());
		debug(TXT_OPER_CAN + "--> " + operacionesCat.getConsecutivoOperacionNivelCanal());
		debug("descCorta--> " + operacionesCat.getDescripcionCorta());
		debug("descLarga--> " + operacionesCat.getDescripcionLarga());
		debug("equiTipOperDos--> " + operacionesCat.getEquivalenciaTipoOeracionDos());
		debug(TXT_EQUI_OPER_UNO + "--> " + operacionesCat.getEquivalenciaTipoOeracionUno());
		debug(TXT_EQUI_OPER_TRES + "--> " + operacionesCat.getEquivalenciaTipoOeracionTres());
		debug(TXT_FEC_ALTA + "--> " + operacionesCat.getFechaAltaOperacion());
		
		
		beanConsulta.setEntidad(ENTIDAD);
		beanConsulta.setCanal(CANAL);
		beanConsulta.setNivelParametria("01");
		beanConsulta.setTipoConsulta("D");
		beanConsulta.setCodigoOperacion(codigoOperacion);
		info(TXT_LLAMA_BO);
		final BeanResultadoOperacionesCorresponsal beanRegreso = bOCorresponsaliaOperaciones.consultaOperacionesCorresponsalia(beanConsulta, getArchitechBean());
		info(TXT_LLAMA_BO);
		info(TXT_REGRESA + beanRegreso.getRegistros().size());

		if(!cadenaVacia.equals(beanRegreso.getCodError())){
			req.setAttribute(TXT_MSG_RESULTADO, beanRegreso.getCodError() + ":" + beanRegreso.getMsgError());
		}
		
		if(beanRegreso.getNumeroRegistros()>0){
			mapParametros.put("beanResultado" , beanRegreso.getRegistros().get(0));
			mapParametros.put("nombre" , beanRegreso.getRegistros().get(0).getDesCorta());
			mapParametros.put("importeMaximo" , beanRegreso.getRegistros().get(0).getImporteMaximoOperacionFront());
			mapParametros.put("importeMinimo" , beanRegreso.getRegistros().get(0).getImporteMinimoOperacionFront());
			mapParametros.put("acumuladoDiario" , beanRegreso.getRegistros().get(0).getAcumuladoDiarioFront());
			mapParametros.put("horaInicio" , beanRegreso.getRegistros().get(0).getHoraInicioFront());
			mapParametros.put("horaFinal" , beanRegreso.getRegistros().get(0).getHoraFinalFront());
			mapParametros.put("acumuladoMensual" , beanRegreso.getRegistros().get(0).getAcumuladoMensualFront());
		}			
		
		info("Finaliza Consulta Detalle Operaciones Corresponsalia");
		return new ModelAndView("DetalleOperacion", mapParametros);
	}

	/** modificacionOperaciones.
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="ModificacionOperaciones.do")
	public ModelAndView modificacionOperaciones(HttpServletRequest req,
			HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ACHI ));
		info("Inicia Modificacion Operaciones Corresponsalia");
		
		final BeanABMMantenimientoOperCorresponsal beanAlta = new BeanABMMantenimientoOperCorresponsal();

		final String trxAsoc = req.getParameter(TXT_TRX_ASOC) == null ? cadenaVacia : 
			req.getParameter(TXT_TRX_ASOC);
		final String trxAsocAnt = req.getParameter("trxAsocAnt") == null ? cadenaVacia : 
			req.getParameter("trxAsocAnt");
		final String consecOperCanal = req.getParameter(TXT_OPER_CAN) == null ? cadenaVacia : 
			req.getParameter(TXT_OPER_CAN);
		final String equiTipOperUno = req.getParameter(TXT_EQUI_OPER_UNO) == null ? cadenaVacia : 
			req.getParameter(TXT_EQUI_OPER_UNO);
		final String equiTipOperTres = req.getParameter(TXT_EQUI_OPER_TRES) == null ? cadenaVacia : 
			req.getParameter(TXT_EQUI_OPER_TRES);
		final String fechAlta = req.getParameter(TXT_FEC_ALTA) == null ? cadenaVacia : 
			req.getParameter(TXT_FEC_ALTA);
		final String txtDescripcion = req.getParameter(TXT_DESC) == null ? cadenaVacia : 
			req.getParameter(TXT_DESC);
		final String txtDescripcionCorta = req.getParameter(TXT_DESC_COR) == null ? cadenaVacia : 
			req.getParameter(TXT_DESC_COR);
		final String txtValorR26 = req.getParameter(TXT_R26) == null ? cadenaVacia : 
			req.getParameter(TXT_R26);
		
		
		final BeanAltaOperacionesCatalogo beanModCat = new BeanAltaOperacionesCatalogo();
		BeanError beanResultado = new BeanError();
		
		beanModCat.setTipoOpcion("M");
		beanModCat.setEntidad(ENTIDAD);
		beanModCat.setCanalCorresponsal(CANAL);
		beanModCat.setIdentificacionCorresponsal(TXT_0000);
		beanModCat.setFechAlta(fechAlta);
		beanModCat.setNivelRegistroOperacion("01");
		beanModCat.setConsecutivoIdentificacionOperacion(consecOperCanal);
		beanModCat.setDescripcionLarga(txtDescripcion);
		beanModCat.setDescripcionCorta(txtDescripcionCorta);
		beanModCat.setEquivalenciaTipoOperacionUno(equiTipOperUno);
		beanModCat.setEquivalenciaTipoOperacionDos(txtValorR26);
		beanModCat.setEquivalenciaTipoOperacionTres(equiTipOperTres);
		beanModCat.setClaveTransaccionAsociada(trxAsoc);
		beanModCat.setClaveTransaccionAsociadaAnterior(trxAsocAnt);
		
		info("Llama BO Alta Operacion Catalogo");
		beanResultado = bOCorresponsaliaOperaciones.altaOperacionesCatalogo(beanModCat, getArchitechBean());

		info("Regreso BO Alta Operacion Catalogo");
		if(!"".equals(beanResultado.getCodigoError())){
//			mapParametros.put(TXT_MSG_RESULTADO , beanResultado.getCodigoError() + ":"+ beanResultado.getMsgError());
//			info("Finaliza Alta Operaciones Operaciones Catalogo");
//		
//			req.setAttribute(TXT_MSG_RESULTADO, beanResultado.getCodigoError() + ":"+ beanResultado.getMsgError());
		}
		
		
		final String nombre = req.getParameter(TXT_NOMBRE) == null ? cadenaVacia : 
			req.getParameter(TXT_NOMBRE);
		final String importeMaximo = req.getParameter("txtImporteMaximo") == null ? cadenaVacia : 
			req.getParameter("txtImporteMaximo");
		final String importeMinimo = req.getParameter("txtImporteMinimo") == null ? cadenaVacia : 
			req.getParameter("txtImporteMinimo");
		final String acumuladoDiario = req.getParameter(TXT_ACUM_DIARIO) == null ? cadenaVacia : 
			req.getParameter(TXT_ACUM_DIARIO);
		final String horaInicio = req.getParameter("txtHoraInicio") == null ? cadenaVacia : 
			req.getParameter("txtHoraInicio");
		final String horaFin = req.getParameter("txtHoraFinal") == null ? cadenaVacia : 
			req.getParameter("txtHoraFinal");
		final String acumuladoMensual = req.getParameter("txtAcumuladoMensual") == null ? cadenaVacia : 
			req.getParameter("txtAcumuladoMensual");
		final String tipoOperacion = req.getParameter(TXT_TIPO_OPER) == null ? cadenaVacia : 
			req.getParameter(TXT_TIPO_OPER);
		final String entidad = req.getParameter(TXT_ENTIDAD) == null ? cadenaVacia : 
			req.getParameter(TXT_ENTIDAD);
		final String canal = req.getParameter(TXT_CANAL) == null ? cadenaVacia : 
			req.getParameter(TXT_CANAL);
		final String nivelParametria= req.getParameter(TXT_NIVEL_PARAM) == null ? "01" : 
			req.getParameter(TXT_NIVEL_PARAM);
		final String codigoOperacion= req.getParameter(CODIGO_OPERACION) == null ? cadenaVacia : 
			req.getParameter(CODIGO_OPERACION);
		
		
		debug(" Entidad            : " + entidad);
		debug(" Canal              : " + canal);
		debug(" Nivel de Parametria: " + nivelParametria);
		debug("N ombre            :" + nombre);
		debug(" Importe Maximo    : " + importeMaximo);
		debug(" Importe Minimo    : " + importeMinimo);
		debug(" Acumulado Diario  : " + acumuladoDiario);
		debug(" Hora Inicio       : " + horaInicio);
		debug(" Hora Fin          : " + horaFin);
		debug(" Acumulado Mensual : " + acumuladoMensual);
		debug(" Tipo Operacion     : " + tipoOperacion);
		
		beanAlta.setEntidad(ENTIDAD);
		beanAlta.setCanal(CANAL);
		beanAlta.setNivelParametria("01");
		beanAlta.setImporteMaximoOperacionFront(importeMaximo);
		beanAlta.setImporteMinimoOperacionFront(importeMinimo);
		beanAlta.setAcumuladoDiarioFront(acumuladoDiario);
		beanAlta.setAcumuladoMensualFront(acumuladoMensual);
		beanAlta.setCodigoOperacion(codigoOperacion);
		beanAlta.setHoraInicioFront(horaInicio);
		beanAlta.setHoraFinalFront(horaFin);
		beanAlta.setTipoOperacionCorr(tipoOperacion);
		beanAlta.setTransaccion(TXT_0000);
		beanAlta.setCodigoOperacion(codigoOperacion);
		beanAlta.setCodigoOperacionTrx(TXT_0000);	
		beanAlta.setCodigoOperacionTrxSec(TXT_0000);
		beanAlta.setCodigoContratoTrx(TXT_0000);
		beanAlta.setCodigoContratoTrxSec(TXT_0000);
		//beanAlta.setTipoOperacionCorr("0123456789");
		beanAlta.setDivisa("DV1");
		beanAlta.setTipoMovimiento("M");
		

		info(TXT_LLAMA_BO);
		final BeanResultadoOperacionesCorresponsal beanRegreso = 
			bOCorresponsaliaOperaciones.modificacionOperacionesCorresponsalia(beanAlta, getArchitechBean());
		info(TXT_REGRESA + beanRegreso.getCodError());

		req.setAttribute(TXT_MSG_RESULTADO, beanRegreso.getCodError() + ":" + beanRegreso.getMsgError());
		//Agregar los datos del comboM
		info("Finaliza Modificacion Operaciones Corresponsalia");
		
		
		if (beanRegreso.getCodError().indexOf("E") >= 0) {
			req.setAttribute(OPERACION, 'M');
			return muestraAltaModificacion(req, res);
		}
		
		return consultaOperaciones(req, res);
		
	}
	
	/**
	 * * * Eliminacion operaciones.
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="EliminacionOperaciones.do")
	public ModelAndView eliminacionOperaciones(HttpServletRequest req,
			HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ACHI ));
		final HashMap<String,Object> mapParametros = new HashMap<String,Object>();
		info("Inicia Modificacion Operaciones Corresponsalia");
		
		final BeanABMMantenimientoOperCorresponsal beanBaja = new BeanABMMantenimientoOperCorresponsal();
		
		final String entidad = req.getParameter(TXT_ENTIDAD) == null ? "0" : 
			req.getParameter(TXT_ENTIDAD);
		final String canal = req.getParameter(TXT_CANAL) == null ? "0" : 
			req.getParameter(TXT_CANAL);
		final String nivelParametria= req.getParameter(TXT_NIVEL_PARAM) == null ? "0" : 
			req.getParameter(TXT_NIVEL_PARAM);
		final String tipoOperacion = req.getParameter(TXT_TIPO_OPER) == null ? "0" : 
			req.getParameter(TXT_TIPO_OPER);
		final String codigoOperacion= req.getParameter(CODIGO_OPERACION) == null ? "01" : 
			req.getParameter(CODIGO_OPERACION);

		debug("E ntidad            :" + entidad);
		debug("C anal              :" + canal);
		debug("N ivel de Parametria:" + nivelParametria);
		debug("C odigo Operacion     :" + codigoOperacion);
		
		beanBaja.setEntidad(ENTIDAD);
		beanBaja.setCanal(CANAL);
		beanBaja.setNivelParametria("01");
		beanBaja.setCodigoOperacion(codigoOperacion);
		beanBaja.setTipoMovimiento("B");
		beanBaja.setTransaccion(TXT_0000);
		beanBaja.setCodigoOperacionTrx(TXT_0000);	
		beanBaja.setCodigoOperacionTrxSec(TXT_0000);
		beanBaja.setCodigoContratoTrx(TXT_0000);
		beanBaja.setCodigoContratoTrxSec(TXT_0000);
		beanBaja.setCodigoContratoTrxSec("01");
		beanBaja.setTipoOperacionCorr("0123456789");
		beanBaja.setDivisa("DV1");
		beanBaja.setTipoOperacionCorr(tipoOperacion);
		
		

		info("Llama BO Corresponsalia Eliminacion");
		final BeanResultadoOperacionesCorresponsal beanRegreso = 
			bOCorresponsaliaOperaciones.eliminaOperacionesCorresponsalia(beanBaja, getArchitechBean());

		info("Regreso BO Corresponsalia Eliminacion" + beanRegreso.getRegistros().size());
		
		req.setAttribute(TXT_MSG_RESULTADO , beanRegreso.getCodError() + ":" + beanRegreso.getMsgError());
		mapParametros.put("registros" , beanRegreso.getRegistros().iterator());
		info("Finaliza Eliminacion Operaciones Corresponsalia");
	
		return consultaOperaciones(req, res);
	}
	
	/**
	 * * * Excepcion operaciones.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="ExcepcionOperaciones.do")
	public ModelAndView excepcionOperaciones(HttpServletRequest req,
			HttpServletResponse res) {
		return new ModelAndView("ExcepcionOperaciones");
	}
	
	/**
	 * * * Alta operaciones catalogo.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="AltaOperacionesCatalogo.do")
	public ModelAndView altaOperacionesCatalogo(HttpServletRequest req,
			HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ACHI ));
		final HashMap<String,Object> mapParametros = new HashMap<String,Object>();
		info("Inicia Alta Operaciones Operaciones Catalogo");
		
		final BeanAltaOperacionesCatalogo beanAlta = new BeanAltaOperacionesCatalogo();
		BeanError beanResultado = new BeanError();
		final String descripcionLarga = req.getParameter(TXT_DESC) == null ? "0" : 
			req.getParameter(TXT_DESC);
		final String descripcionCorta = req.getParameter(TXT_DESC_COR) == null ? "0" : 
			req.getParameter(TXT_DESC_COR);
		final String valorR26= req.getParameter(TXT_R26) == null ? "0" : 
			req.getParameter(TXT_R26);
		beanAlta.setTipoOpcion("A");
		beanAlta.setEntidad(ENTIDAD);
		beanAlta.setCanalCorresponsal(CANAL);
		beanAlta.setIdentificacionCorresponsal(TXT_0000); // 3ca El Corresponsal se debe informar en ceros
		beanAlta.setNivelRegistroOperacion("01");
		beanAlta.setDescripcionLarga(descripcionLarga);
		beanAlta.setFechAlta(Utils.getFechaSys());
		beanAlta.setDescripcionCorta(descripcionCorta);
		beanAlta.setEquivalenciaTipoOperacionUno("0000000000"); //3ca Este dato se debe informar en ceros
		beanAlta.setEquivalenciaTipoOperacionDos(valorR26); // 3ca Valor en R26
		beanAlta.setEquivalenciaTipoOperacionTres("0000000000"); //3ca Este dato se debe informar en ceros
		beanAlta.setClaveTransaccionAsociada("TRX0");
		beanAlta.setClaveTransaccionAsociadaAnterior("TRX3");
		
		info("Llama BO Alta Operacion Catalogo");
		beanResultado = bOCorresponsaliaOperaciones.altaOperacionesCatalogo(beanAlta, getArchitechBean());

		info("Regreso BO Alta Operacion Catalogo");
		if(!"".equals(beanResultado.getCodigoError())){
			mapParametros.put(TXT_MSG_RESULTADO , beanResultado.getCodigoError() + ":"+ beanResultado.getMsgError());
			info("Finaliza Alta Operaciones Operaciones Catalogo");
		
			req.setAttribute(TXT_MSG_RESULTADO, beanResultado.getCodigoError() + ":"+ beanResultado.getMsgError());
		}
		req.setAttribute(OPERACION, 'A');		
		return muestraAltaModificacion(req, res);
		//consultaOperaciones(req, res);
	}
	
	/**
	 * * * Consulta operaciones catalogo.
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="ConsultaOperacionesCatalogo.do")
	public ModelAndView consultaOperacionesCatalogo(HttpServletRequest req,
			HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ACHI ));
		final HashMap<String,Object> mapParametros = new HashMap<String,Object>();
		info("Inicia Consulta Operaciones Operaciones Catalogo");
		
		final BeanConsultaOperacionesCatalogo beanConsulta = new BeanConsultaOperacionesCatalogo();
		BeanResultadoOperacionesCatalogo beanResultado = new BeanResultadoOperacionesCatalogo();
		
		beanConsulta.setTipoOperacion("C");
		beanConsulta.setEntidad(ENTIDAD);
		beanConsulta.setCanalCorresponsal(CANAL);
		beanConsulta.setNivelRegistroOperacion("01");
		beanConsulta.setIndicadorPaginacion("N");
		
		info("Llama BO Consulta Operacion Catalogo");
		beanResultado = bOCorresponsaliaOperaciones.consultaOperacionesCatalogo(beanConsulta, getArchitechBean());
		mapParametros.put("listaOperacionesCatalogo" , beanResultado.getRegistros());
		
		info("Regreso BO Consulta Operacion Catalogo" + beanResultado.getNumeroRegistros());

		mapParametros.put(TXT_MSG_RESULTADO , beanResultado.getCodError() + ":"+ beanResultado.getMsgError());
		
		info("Finaliza Consulta Operaciones Operaciones Catalogo");
		
		return consultaOperaciones(req, res);
	}
	
	/**
	 * * * Muestra alta operaciones catalogo.
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="MuestraAltaOperacionesCatalogo.do")
	public ModelAndView muestraAltaOperacionesCatalogo(HttpServletRequest req,
			HttpServletResponse res){
		info("Inicia Muestra Alta Operaciones Operaciones Catalogo");
		
		info("Finaliza Muestra Alta Operaciones Operaciones Catalogo");
	
		return new ModelAndView("AltaCorresponsalOperaciones");
	}
		
	/**
	 * * * Exportar operaciones.
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="exportarOperaciones.do")
	public ModelAndView exportarOperaciones(HttpServletRequest req,
			HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ACHI ));
		final HttpSession session = req.getSession(); 
		final HashMap<String, Object> map = new HashMap<String, Object>();
		final List<BeanOperacion>  listaRegistros = 
			(List<BeanOperacion>) session.getAttribute("listaRegistrosCanalCorresponsalia");
		final String charString = "'";
		
		if(listaRegistros==null || (listaRegistros != null && listaRegistros.isEmpty())){
			info("Registro para exportar 0");
			final HashMap<String,Object>  lhsmParametros   = new HashMap<String,Object>();
			lhsmParametros.put("codError", "NOEXPORT");
			lhsmParametros.put("msgError", "NO HAY REGISTROS PARA EXPORTAR");
			return new ModelAndView("ConsultaOperaciones",lhsmParametros);			
		}
				
		for (BeanOperacion codigo : listaRegistros) {
			codigo.setCodigoOperacion(charString + codigo.getCodigoOperacion().toString());
		}
		final JRDataSource dataSource = new JRBeanArrayDataSource(listaRegistros.toArray());
		
		final DateFormat Dformat = new  SimpleDateFormat("yyyy/MM/dd HH:mm:ss aa",new Locale("la","MX")); 
		map.put("fechaReporte", Dformat.format(java.util.Calendar.getInstance().getTime()));  //  pasamos la fecha al reporte
		return new ModelAndView(new ExportaXLS("OperacionesCanal","/com/isban/corresponsalia/reportes/doctos/operacionescanal.jasper",dataSource), map);
	
	}
	
	/**
	 * getOperacionesCat Obtiene las operaciones
	 * @return beanResultadoOperaciones
	 */
	public BeanResultadoOperacionesCatalogo getOperacionesCat(){
		
		final BeanConsultaOperacionesCatalogo beanConsultaOperaciones = new BeanConsultaOperacionesCatalogo();
		BeanResultadoOperacionesCatalogo beanResultadoOperaciones = new BeanResultadoOperacionesCatalogo();
		
		beanConsultaOperaciones.setTipoOperacion("C");
		beanConsultaOperaciones.setEntidad(ENTIDAD);
		beanConsultaOperaciones.setCanalCorresponsal(CANAL);
		beanConsultaOperaciones.setNivelRegistroOperacion("01");
		beanConsultaOperaciones.setIndicadorPaginacion("N");
		
		info("Llama BO Consulta Operacion Catalogo");
		beanResultadoOperaciones = bOCorresponsaliaOperaciones.consultaOperacionesCatalogo(
				beanConsultaOperaciones, getArchitechBean());
		
		return beanResultadoOperaciones;
	}
	
	/**
	 *  getOperacionCat obtiene la operacion del catalogo
	 * @param beanResultadoOperaciones
	 * @param codigoOperacion
	 * @return operacionesCat
	 */
	public BeanOperacionCatalogo getOperacionCat(BeanResultadoOperacionesCatalogo beanResultadoOperaciones, String codigoOperacion){
		for(BeanOperacionCatalogo operacionesCat : beanResultadoOperaciones.getRegistros()){
			if(codigoOperacion.equals(operacionesCat.getConsecutivoOperacionNivelCanal())){
				operacionesCat.setClaveTransaccionAsociada(operacionesCat.getClaveTransaccionAsociada().trim());
				operacionesCat.setClaveTransaccionAsociadaAnterior(operacionesCat.getClaveTransaccionAsociadaAnterior().trim());
				operacionesCat.setConsecutivoOperacionNivelCanal(operacionesCat.getConsecutivoOperacionNivelCanal().trim());
				operacionesCat.setDescripcionCorta(operacionesCat.getDescripcionCorta().trim());
				operacionesCat.setDescripcionLarga(operacionesCat.getDescripcionLarga().trim());
				operacionesCat.setEquivalenciaTipoOeracionDos(operacionesCat.getEquivalenciaTipoOeracionDos().trim());
				operacionesCat.setEquivalenciaTipoOeracionTres(operacionesCat.getEquivalenciaTipoOeracionTres().trim());
				operacionesCat.setEquivalenciaTipoOeracionUno(operacionesCat.getEquivalenciaTipoOeracionUno().trim());
				operacionesCat.setFechaAltaOperacion(operacionesCat.getFechaAltaOperacion().trim());
				return operacionesCat;
			}
		}
		return null;
	}
}

