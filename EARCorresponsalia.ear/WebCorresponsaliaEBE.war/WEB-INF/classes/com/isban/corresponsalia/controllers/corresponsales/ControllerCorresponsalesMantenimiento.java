package com.isban.corresponsalia.controllers.corresponsales;

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
import org.springframework.web.servlet.ModelAndView;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanABMMantenimientoCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaPreAlta;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.bo.corresponsales.BOCorresponsalesMantenimiento;
import com.isban.corresponsalia.comunes.Parametros;
import com.isban.corresponsalia.comunes.Utils;
import com.isban.corresponsalia.reportes.ExportaXLS;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;

// TODO: Auto-generated Javadoc
/**
 * The Class ControllerCorresponsalesMantenimiento.
 */
@Controller
public class ControllerCorresponsalesMantenimiento extends ArchitechEBE {
	
	/** The Constant NEW_ARCH. */
	private static final String NEW_ARCH = "NewArchitechSession";
	
	/** The Constant EMAIL_GEN. */
	private static final String EMAIL_GEN = "a@oxxo.com";
	
	/** The Constant REG_SEL. */
	private static final String REG_SEL = "regSel";
	
	/** The Constant LISTA_REG_CANAL_CORR. */
	private static final String LISTA_REG_CANAL_CORR = "listaRegistrosCanalCorresponsalia";
	
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
	
	/** The Constant CORRESPONSALIA. */
	private static final String CORRESPONSALIA = "CORRESPONSALIA";
	
	/** The Constant ID_CORRESPO. */
	private static final String ID_CORRESPO = "idCorresponsal";
	
	/** The Constant TXT_CODIGO_CLIENTE. */
	private static final String TXT_CODIGO_CLIENTE = "txtCodigoCliente";
	
	/** The Constant TXT_SEC_DOMIC. */
	private static final String TXT_SEC_DOMIC = "txtSecuenciaDomicilio";
	
	/** The Constant TXT_GIRO_EMP. */
	private static final String TXT_GIRO_EMP = "txtGiroEmpresa";
	
	/** The Constant TXT_CENTRO_COSTOS. */
	private static final String TXT_CENTRO_COSTOS = "txtCentroConsto";
	
	/** The Constant TXT_NOMBRE_CORRESPO. */
	private static final String TXT_NOMBRE_CORRESPO = "txtNombreCorresponsal";
	
	/** The Constant TXT_CUENTA_CHEQUES. */
	private static final String TXT_CUENTA_CHEQUES = "txtCuentaCheques";
	
	/** The Constant TXT_CUENTA_CREDITO. */
	private static final String TXT_CUENTA_CREDITO = "txtCuentaCredito" ;
	
	/** The Constant TXT_LIMITE_IMPORTE. */
	private static final String TXT_LIMITE_IMPORTE ="txtLimiteImporteCorresponsal";
	
	/** The Constant TXT_DIAS_CONCILIAR. */
	private static final String TXT_DIAS_CONCILIAR = "txtDiasPendientesConciliar";
	
	/** The Constant TXT_DIAS_COMPENSAR. */
	private static final String TXT_DIAS_COMPENSAR =  "txtDiasPendientesCompensar";
	
	/** The Constant TXT_TEL_ACLARA. */
	private static final String TXT_TEL_ACLARA = "txtTelefonoAclaracionCorresponsal";
	
	/** The Constant TXT_CORREO_CONTACTO. */
	private static final String TXT_CORREO_CONTACTO = "txtCorreoContacto";
	
	/** The Constant TXT_CORREO_ALT_CONTANCTO. */
	private static final String TXT_CORREO_ALT_CONTANCTO = "txtCorreoAlternoContacto";
	
	/** The Constant TXT_CALLE_DOMICILIO. */
	private static final String TXT_CALLE_DOMICILIO = "txtCalleDomicilioCorresponsal" ;
	
	/** The Constant TXT_NUM_EXT. */
	private static final String TXT_NUM_EXT = "txtNumeroExteriorCorresponsal";
	
	/** The Constant TXT_NUM_INT. */
	private static final String TXT_NUM_INT = "txtNumeroInteriorCorresponsal" ;
	
	/** The Constant TXT_COLONIA. */
	private static final String TXT_COLONIA = "txtColoniaDomicilioCorresponsal";
	
	/** The Constant TXT_DELEGACION. */
	private static final String TXT_DELEGACION = "txtDelegacionMunicipioCorresponsal"; 
	
	/** The Constant TXT_CIUDAD. */
	private static final String TXT_CIUDAD = "txtCiudadCorresponsal";
	
	/** The Constant TXT_ESTADO. */
	private static final String TXT_ESTADO = "txtEstadoCorresponsal";
	
	/** The Constant TXT_CP. */
	private static final String TXT_CP = "txtCodigoCorresponsal" ;
	
	/** The Constant TXT_PAIS. */
	private static final String TXT_PAIS = "txtPaisProcedenciaCorresponsal";
	
	/** The Constant TXT_TELEFONO. */
	private static final String TXT_TELEFONO = "txtTelefonoCorresponsal" ;
	 
	/** The Constant TXT_MXP. */
	private static final String TXT_MXP=  "MXP";
	
	/** The Constant PREALTA. */
	private static final String  PRE_ALTA = "Prealta";
	/** The Constant CADENA_VACIA. */
	private static final String CADENA_VACIA = "";
	
	/** The b o corresponsales mantenimiento. */
	transient private BOCorresponsalesMantenimiento bOCorresponsalesMantenimiento;
	
	
	
	/**
	 * Sets the bO corresponsales mantenimiento.
	 *
	 * @param bOCorresponsalesMantenimiento the new bO corresponsales mantenimiento
	 */
	public void setBOCorresponsalesMantenimiento(BOCorresponsalesMantenimiento bOCorresponsalesMantenimiento){
		this.bOCorresponsalesMantenimiento = bOCorresponsalesMantenimiento;
	}	

	
	/**
	 * Baja mtto corresponsales.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="BajaMttoCorresponsales.do")
	public ModelAndView bajaMttoCorresponsales(HttpServletRequest req, HttpServletResponse res){
		info("Inicia Baja Operaciones Corresponsalia");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));


		final String                           regSel              = req.getParameter(REG_SEL) == null ? CADENA_VACIA : req.getParameter(REG_SEL);
		final List<BeanCorresponsal>           listaCorresponsales = (List<BeanCorresponsal>)req.getSession().getAttribute(LISTA_REG_CANAL_CORR);
		BeanCorresponsal                 beanCorresponsal    = null;
		String                           lstrCodErrorPage         = "";
		String                           lstrMsgErrorPage         = "";

		debug("Registro a Eliminar:"+regSel);

		try{
			beanCorresponsal = listaCorresponsales.get(Integer.parseInt(regSel));
		}
		catch(IndexOutOfBoundsException  e){
			debug("No se pudo obtener el bean");
		}
			
		debug("Codigo corresponsal a Eliminar:" + beanCorresponsal.getCodigoCorresponsal());
		
		try{
			final BeanResultadoCorresponsalia beanRegreso = bOCorresponsalesMantenimiento.eliminaMantenimientoCorresponsalia(beanCorresponsal, getArchitechBean());
			lstrCodErrorPage = beanRegreso.getCodError();
			lstrMsgErrorPage = beanRegreso.getMsgError();
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
			req.setAttribute(COD_ERROR, lstrCodErrorPage);
			req.setAttribute(MSG_ERROR, lstrMsgErrorPage);
		}
		
		debug(CODIGO_ERROR + lstrCodErrorPage);
		debug(MENSAJE_ERROR + lstrMsgErrorPage);

		
		return consultaMttoCorresponsales(req, res);
	}
	
	
	/**
	 * Consulta mtto corresponsales.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="ConsultaMttoCorresponsales.do")
	public ModelAndView consultaMttoCorresponsales(HttpServletRequest req, 	HttpServletResponse res) {
		info("Inicia Consulta de Corresponsales");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));	
		final HashMap<String,Object>       mapParametros        = new HashMap<String,Object>();
		final HttpSession                  session              = req.getSession(); 
		final BeanMttoConsultaCorresponsal beanConsulta         = new BeanMttoConsultaCorresponsal();
		final String                       lstrCodAviso         = req.getAttribute(COD_AVISO)==null?"":req.getAttribute(COD_AVISO).toString();
		final String                       lstrMsgAviso         = req.getAttribute(MSG_AVISO)==null?"":req.getAttribute(MSG_AVISO).toString();
		final String                       lstrCodError         = req.getAttribute(COD_ERROR)==null?"":req.getAttribute(COD_ERROR).toString();
		final String                       lstrMsgError         = req.getAttribute(MSG_ERROR)==null?"":req.getAttribute(MSG_ERROR).toString();
		final String                       paginaNum            = req.getParameter("paginaNum")==null?"":req.getParameter("paginaNum").toString();		
		final String                       lstrOpcionPag        = req.getParameter("opcAvanzarRetroceder")==null?"":req.getParameter("opcAvanzarRetroceder");
		final String                       referenciaAvanzar    = req.getParameter("referenciaAvanzar")==null?"":req.getParameter("referenciaAvanzar");
		final String                       referenciaRetroceder = req.getParameter("referenciaRetroceder")==null?"":req.getParameter("referenciaRetroceder");
		String                       lstrCodErrorPage     = "";
		String                       lstrMsgErrorPage     = "";

		beanConsulta.setCodigoCorresponsalia(Parametros.getParametroAplicacion(CORRESPONSALIA));
		
		beanConsulta.setTipoConsulta("L");
		if(!"".trim().equals(lstrOpcionPag)){
			beanConsulta.setIndicadorPaginacion("P");
			beanConsulta.setIndicadorDireccion(lstrOpcionPag);
			beanConsulta.setLimiteInferiorConsulta(referenciaRetroceder);
			beanConsulta.setLimiteSuperiorConsulta(referenciaAvanzar);			
		}
		else{
			beanConsulta.setIndicadorPaginacion("N");
		}
		
		try{
			if("1".equalsIgnoreCase(paginaNum.trim())){
				beanConsulta.setIndicadorPaginacion("N");
				beanConsulta.setLimiteInferiorConsulta("");
				beanConsulta.setLimiteSuperiorConsulta("");		
				
			}			
			final BeanResultadoCorresponsalia beanRegreso = bOCorresponsalesMantenimiento.consultaMantenimientoCorresponsalia(beanConsulta, getArchitechBean());

			if((beanRegreso instanceof BeanResultadoCorresponsalia)&& beanRegreso.getRegistros().size()>0){
				session.setAttribute(LISTA_REG_CANAL_CORR, beanRegreso.getRegistros());
				mapParametros.put("referenciaAvanzar"   ,beanRegreso.getReferenciaAvanzar());				
				mapParametros.put("referenciaRetroceder",beanRegreso.getReferenciaRetroceder());
				mapParametros.put("hayAtras", beanRegreso.isMasAtras());
				mapParametros.put("hayAdelante", beanRegreso.isMasAdelante());		
				mapParametros.put("paginaNum", paginaNum);
			}
			else{
				lstrCodErrorPage = beanRegreso.getCodError();
				lstrMsgErrorPage = beanRegreso.getMsgError();
				mapParametros.put(COD_ERROR,lstrCodErrorPage);
				mapParametros.put(MSG_ERROR,lstrMsgErrorPage);
			}
		}
		catch(BusinessException e){
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			mapParametros.put(COD_ERROR, lstrCodErrorPage);
			mapParametros.put(MSG_ERROR, lstrMsgErrorPage);
		}
		
		if(!"".equals(lstrCodErrorPage) && !"".equals(lstrCodAviso)){
			mapParametros.put(COD_AVISO , lstrCodAviso);
			mapParametros.put(MSG_AVISO , lstrMsgAviso);
			
		}
		else if(!"".equals(lstrCodError)){
			mapParametros.put(COD_ERROR , lstrCodError);
			mapParametros.put(MSG_ERROR , lstrMsgError);			
		}		
		mapParametros.put("SESION",req.getAttribute("SESION"));
		return new ModelAndView("ConsultaMttoCorresponsal", mapParametros);
	}
	
	/**
	 * Detalle mtto corresponsales.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 * @throws BusinessException the business exception
	 */
	@RequestMapping(value="DetalleMttoCorresponsales.do")
	public ModelAndView detalleMttoCorresponsales(HttpServletRequest req,
			HttpServletResponse res) throws BusinessException {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		info("Inicia Consulta Mantenimiento Detalle Corresponsalia");
		final HashMap<String,Object> mapParametros = new HashMap<String,Object>();
		BeanCorresponsal beanResultado;
		final BeanMttoConsultaCorresponsal beanConsulta = new BeanMttoConsultaCorresponsal();

		 String codigoCorresponsalia = req.getParameter("idCorresponsalia") == null ? CADENA_VACIA : 
			req.getParameter("idCorresponsalia");
		 String codigoCorresponsal = req.getParameter(ID_CORRESPO) == null ? CADENA_VACIA : 
			req.getParameter(ID_CORRESPO);
		
		if(codigoCorresponsalia.length()<2){
			codigoCorresponsalia = Utils.lpad(codigoCorresponsalia, "0", 2);
		}
		if(codigoCorresponsal.length()<4){
			codigoCorresponsal = Utils.lpad(codigoCorresponsal, "0", 4);
		}
		
		info("codigoCorresponsalia             :" + codigoCorresponsalia);
		info("codigoCorresponsal               :" + codigoCorresponsal);
		
		beanConsulta.setCodigoCorresponsalia(codigoCorresponsalia);
		beanConsulta.setCodigoCorresponsal(codigoCorresponsal);
		beanConsulta.setIndicadorPaginacion("N");
		beanConsulta.setTipoConsulta("D");
	
		//set parametros Bean
		info("Llama BO Corresponsalia");
		final BeanResultadoCorresponsalia beanRegreso = 
			bOCorresponsalesMantenimiento.consultaMantenimientoCorresponsalia(beanConsulta, getArchitechBean());
		info("Llama BO Corresponsalia");
		info("Regreso BO Corresponsalia :" + beanRegreso.getRegistros().size());

		mapParametros.put("codPagError" , beanRegreso.getCodError());
		mapParametros.put("msgPageError" , beanRegreso.getMsgError());
		if(beanRegreso.getRegistros().size() > 0 ) {
			beanResultado =  beanRegreso.getRegistros().get(0);
			mapParametros.put("beanResultado",beanResultado);
		} else {
			info("Finaliza Consulta Detalle Operaciones Corresponsalia No Existe Registros");
			req.setAttribute(COD_ERROR, beanRegreso.getCodError());
			req.setAttribute(MSG_ERROR, beanRegreso.getMsgError());
			return consultaMttoCorresponsales(req, res);
		}
			
		if(req.getAttribute("modificacion") != null){
			mapParametros.put(ID_CORRESPO,codigoCorresponsal);
			return new ModelAndView("ModificaMttoCorresponsal", mapParametros);
		}
		
		info("Finaliza Consulta Detalle Operaciones Corresponsalia");
		return new ModelAndView("DetalleMttoCorresponsal", mapParametros);
	}
	
	/**
	 * Alta mtto corresponsales.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="AltaMttoCorresponsales.do")
	public ModelAndView altaMttoCorresponsales(HttpServletRequest req, HttpServletResponse res) {
		info("Inicia Alta Operaciones Corresponsalia");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));		
		final BeanABMMantenimientoCorresponsal beanAlta                = new BeanABMMantenimientoCorresponsal();
		final String                           codigoCliente           = req.getParameter(TXT_CODIGO_CLIENTE) == null ? CADENA_VACIA :	req.getParameter(TXT_CODIGO_CLIENTE);
		final String                           secuenciaDomicilio      = req.getParameter(TXT_SEC_DOMIC) == null ? CADENA_VACIA : req.getParameter(TXT_SEC_DOMIC);
		final String                           giroEmpresa             = req.getParameter(TXT_GIRO_EMP) == null ? CADENA_VACIA : req.getParameter(TXT_GIRO_EMP);
		final String                           centroCosto             = req.getParameter(TXT_CENTRO_COSTOS) == null ? CADENA_VACIA : req.getParameter(TXT_CENTRO_COSTOS);
		final String                           nombreCorresponsal      = req.getParameter(TXT_NOMBRE_CORRESPO) == null ? CADENA_VACIA : req.getParameter(TXT_NOMBRE_CORRESPO);
		final String                           cuentaCheques           = req.getParameter(TXT_CUENTA_CHEQUES) == null ? CADENA_VACIA : req.getParameter(TXT_CUENTA_CHEQUES);
		final String                           cuentaCredito           = req.getParameter(TXT_CUENTA_CREDITO) == null ? CADENA_VACIA : req.getParameter(TXT_CUENTA_CREDITO);
		final String                           limiteAlterta           = req.getParameter(TXT_LIMITE_IMPORTE) == null ? CADENA_VACIA :	req.getParameter(TXT_LIMITE_IMPORTE);
		final String                           diasPendientesConciliar = req.getParameter(TXT_DIAS_CONCILIAR) == null ? CADENA_VACIA :	req.getParameter(TXT_DIAS_CONCILIAR);
		final String                           diasPendientesCompensar = req.getParameter(TXT_DIAS_COMPENSAR) == null ? CADENA_VACIA :	req.getParameter(TXT_DIAS_COMPENSAR);
		final String                           telefonoAclaracion      = req.getParameter(TXT_TEL_ACLARA) == null ? CADENA_VACIA : req.getParameter(TXT_TEL_ACLARA);
		final String                           correoContacto          = req.getParameter(TXT_CORREO_CONTACTO) == null ? CADENA_VACIA : req.getParameter(TXT_CORREO_CONTACTO);
		final String                           correoAlternoContacto   = req.getParameter(TXT_CORREO_ALT_CONTANCTO) == null ? CADENA_VACIA : req.getParameter(TXT_CORREO_ALT_CONTANCTO);
		final String                           calleDomicilio          = req.getParameter(TXT_CALLE_DOMICILIO) == null ? CADENA_VACIA : req.getParameter(TXT_CALLE_DOMICILIO);
		final String                           numeroExt               = req.getParameter(TXT_NUM_EXT) == null ? CADENA_VACIA : req.getParameter(TXT_NUM_EXT);
		final String                           numeroInt               = req.getParameter(TXT_NUM_INT) == null ? CADENA_VACIA : req.getParameter(TXT_NUM_INT);
		final String                           coloniaDomicilio        = req.getParameter(TXT_COLONIA) == null ? CADENA_VACIA : req.getParameter(TXT_COLONIA);
		final String                           delegacionMunicipio     = req.getParameter(TXT_DELEGACION) == null ? CADENA_VACIA : req.getParameter(TXT_DELEGACION);
		final String                           ciudad                  = req.getParameter(TXT_CIUDAD) == null ? CADENA_VACIA : req.getParameter(TXT_CIUDAD);
		final String                           estado                  = req.getParameter(TXT_ESTADO) == null ? CADENA_VACIA : req.getParameter(TXT_ESTADO);
		final String                           codigoPostal            = req.getParameter(TXT_CP) == null ? CADENA_VACIA : req.getParameter(TXT_CP);
		final String                           paisProcedencia         = req.getParameter(TXT_PAIS) == null ? CADENA_VACIA : req.getParameter(TXT_PAIS);
		final String                           telefonoCorresponsal    = req.getParameter(TXT_TELEFONO) == null ? CADENA_VACIA : req.getParameter(TXT_TELEFONO);
		final String                           informaComision         = req.getParameter("opcInformaComision") == null ? CADENA_VACIA : req.getParameter("opcInformaComision");
		String                           lstrCodErrorPage        = "";
		String                           lstrMsgErrorPage        = "";


		debug("CodigoCliente          :"+codigoCliente);
		debug("SecuenciaDomicilio     :"+secuenciaDomicilio);
		debug("GiroEmpresa            :"+giroEmpresa);
		debug("CentroCosto            :"+centroCosto);
		debug("NombreCorresponsal     :"+nombreCorresponsal);
		debug("InformaComision        :"+informaComision);
		debug("CuentaCheques          :"+cuentaCheques);
		debug("CuentaCredito          :"+cuentaCredito);
		debug("LimiteAlterta          :"+limiteAlterta);
		debug("DiasPendientesConciliar:"+diasPendientesConciliar);
		debug("DiasPendientesCompensar:"+diasPendientesCompensar);
		debug("TelefonoAclaracion     :"+telefonoAclaracion);
		debug("CorreoContacto         :"+correoContacto);
		debug("CorreoAlternoContacto  :"+correoAlternoContacto);
		debug("CalleDomicilio         :"+calleDomicilio);
		debug("NumeroExt              :"+numeroExt);
		debug("NumeroInt              :"+numeroInt);
		debug("ColoniaDomicilio       :"+coloniaDomicilio);
		debug("DelegacionMunicipio    :"+delegacionMunicipio);
		debug("Ciudad                 :"+ciudad);
		debug("Estado                 :"+estado);
		debug("CodigoPostal           :"+codigoPostal);
		debug("PaisProcedencia        :"+paisProcedencia);
		debug("TelefonoCorresponsal   :"+telefonoCorresponsal);
		debug("InformaComision        :"+informaComision);

		beanAlta.setCodigoCorresponsalia(Parametros.getParametroAplicacion(CORRESPONSALIA));
		beanAlta.setCodigoCorresponsal(centroCosto); //3ca, El Centro de Costos es el ID Corresponsal
		beanAlta.setEstatusCorresponsal("CS2");
		beanAlta.setIndicadorConciliacion("A");
		beanAlta.setIndicadorValidaComision(informaComision); // 3ca, Se debe informar el valor del Combo
		beanAlta.setDivisaCuentaCheques(TXT_MXP);
		beanAlta.setDivisaAsocLineaCredito(TXT_MXP);
		beanAlta.setCodigoCliente(codigoCliente);
		beanAlta.setSecuenciaDomicilio(secuenciaDomicilio);
		beanAlta.setGiroEmpresa(giroEmpresa);
		beanAlta.setCentroCosto(centroCosto);
		beanAlta.setNombreCorresponsal(nombreCorresponsal);
		beanAlta.setCuentaChequesCorresponsal(cuentaCheques);
		beanAlta.setLineaCreditoCorresponsal(cuentaCredito);
		beanAlta.setLimiteImporteCorresponsalFront(limiteAlterta);
		beanAlta.setParamDiasPendientesConciliar(diasPendientesConciliar);
		beanAlta.setParamDiasPendientesCompensar(diasPendientesCompensar);
		beanAlta.setTelefonoAclaracion(telefonoAclaracion);
//		beanAlta.setCorreoContacto(correoContacto);
		beanAlta.setCorreoContacto(EMAIL_GEN);//Se agrega este correo temporalmente para mitigar el problema de la longitud del campo en 390
//		beanAlta.setCorreoAlternoContacto(correoAlternoContacto);
		beanAlta.setCorreoAlternoContacto(EMAIL_GEN); //Se agrega este correo temporalmente para mitigar el problema de la longitud del campo en 390
		beanAlta.setCalleDomicilioCorresponsal(calleDomicilio);
		beanAlta.setNumeroExteriorCorresponsal(numeroExt);
		beanAlta.setNumeroInteriorCorresponsal(numeroInt);
		beanAlta.setColoniaDomicilioCorresponsal(coloniaDomicilio);
		beanAlta.setDelegacionMunicipioCorresponsal(delegacionMunicipio);
		beanAlta.setCiudadCorresponsal(ciudad);
		beanAlta.setEstadoCorresponsal(estado);
		beanAlta.setCodigoPostalCorresponsal(codigoPostal);
		beanAlta.setPaisProcedenciaCorresponsal(paisProcedencia);
		beanAlta.setTelefonoCorresponsal(telefonoCorresponsal);
		beanAlta.setTipoOperacion("A");

		try{
			final BeanResultadoCorresponsalia beanRegreso = bOCorresponsalesMantenimiento.altaMantenimientoCorresponsalia(beanAlta, getArchitechBean());

			lstrCodErrorPage = beanRegreso.getCodError();
			lstrMsgErrorPage = beanRegreso.getMsgError();
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
			req.setAttribute(COD_ERROR, lstrCodErrorPage);
			req.setAttribute(MSG_ERROR, lstrMsgErrorPage);
			return altaMttoCorresponsales(req, res);
		}

		debug(CODIGO_ERROR + lstrCodErrorPage);
		debug(MENSAJE_ERROR + lstrMsgErrorPage);
		
		return consultaMttoCorresponsales(req, res);
	}
	
	/**
	 * Muestra alta corresponsalia.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 * @throws BusinessException the business exception
	 */
	@RequestMapping(value="MuestraAltaCorresponsalia.do")
	public ModelAndView muestraAltaCorresponsalia(HttpServletRequest req, 
			HttpServletResponse res)throws BusinessException{
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));		
		final String operacion = req.getParameter("tipoAltaModifica") == null ? CADENA_VACIA :	req.getParameter("tipoAltaModifica");
		final HashMap<String,Object>           mapParametros        = new HashMap<String,Object>();
		if("A".equals(operacion)) {
			mapParametros.put(PRE_ALTA , "class=\"Campos_Des\" disabled=\"disabled\"");
			return new ModelAndView("AltaMttoCorresponsales",mapParametros);
		}
		if("M".equals(operacion)){
			req.setAttribute("modificacion", "true");
			return detalleMttoCorresponsales(req, res);
		} 
		else{
			return new ModelAndView("ConsultaMttoCorresponsales");
		}
	}
	
	/**
	 * Modifica mtto corresponsales.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="ModificaMttoCorresponsales.do")
	public ModelAndView modificaMttoCorresponsales(HttpServletRequest req, 
			HttpServletResponse res){ 
		info("Inicia Modificacion Operaciones Corresponsalia");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final BeanABMMantenimientoCorresponsal beanModifica            = new BeanABMMantenimientoCorresponsal();
		final String                           codigoCliente           = req.getParameter(TXT_CODIGO_CLIENTE) == null ? CADENA_VACIA : req.getParameter(TXT_CODIGO_CLIENTE);
		final String                           secuenciaDomicilio      = req.getParameter(TXT_SEC_DOMIC) == null ? CADENA_VACIA : req.getParameter(TXT_SEC_DOMIC);
		final String                           giroEmpresa             = req.getParameter(TXT_GIRO_EMP) == null ? CADENA_VACIA : req.getParameter(TXT_GIRO_EMP);
		final String                           centroCosto             = req.getParameter(TXT_CENTRO_COSTOS) == null ? CADENA_VACIA : req.getParameter(TXT_CENTRO_COSTOS);
		final String                           nombreCorresponsal      = req.getParameter(TXT_NOMBRE_CORRESPO) == null ? CADENA_VACIA : req.getParameter(TXT_NOMBRE_CORRESPO);
		final String                           cuentaCheques           = req.getParameter(TXT_CUENTA_CHEQUES) == null ? CADENA_VACIA : req.getParameter(TXT_CUENTA_CHEQUES);
		final String                           cuentaCredito           = req.getParameter(TXT_CUENTA_CREDITO) == null ? CADENA_VACIA : req.getParameter(TXT_CUENTA_CREDITO);
		final String                           limiteAlterta           = req.getParameter(TXT_LIMITE_IMPORTE) == null ? CADENA_VACIA : req.getParameter(TXT_LIMITE_IMPORTE);
		final String                           diasPendientesConciliar = req.getParameter(TXT_DIAS_CONCILIAR) == null ? CADENA_VACIA : req.getParameter(TXT_DIAS_CONCILIAR);
		final String                           diasPendientesCompensar = req.getParameter(TXT_DIAS_COMPENSAR) == null ? CADENA_VACIA : req.getParameter(TXT_DIAS_COMPENSAR);
		final String                           telefonoAclaracion      = req.getParameter(TXT_TEL_ACLARA) == null ? CADENA_VACIA : req.getParameter(TXT_TEL_ACLARA);
		final String                           correoContacto          = req.getParameter(TXT_CORREO_CONTACTO) == null ? CADENA_VACIA : req.getParameter(TXT_CORREO_CONTACTO);
		final String                           correoAlternoContacto   = req.getParameter(TXT_CORREO_ALT_CONTANCTO) == null ? CADENA_VACIA : req.getParameter(TXT_CORREO_ALT_CONTANCTO);
		final String                           calleDomicilio          = req.getParameter(TXT_CALLE_DOMICILIO) == null ? CADENA_VACIA : req.getParameter(TXT_CALLE_DOMICILIO);
		final String                           numeroExt               = req.getParameter(TXT_NUM_EXT) == null ? CADENA_VACIA : req.getParameter(TXT_NUM_EXT);
		final String                           numeroInt               = req.getParameter(TXT_NUM_INT) == null ? CADENA_VACIA : req.getParameter(TXT_NUM_INT);
		final String                           coloniaDomicilio        = req.getParameter(TXT_COLONIA) == null ? CADENA_VACIA : req.getParameter(TXT_COLONIA);
		final String                           delegacionMunicipio     = req.getParameter(TXT_DELEGACION) == null ? CADENA_VACIA : req.getParameter(TXT_DELEGACION);
		final String                           ciudad                  = req.getParameter(TXT_CIUDAD) == null ? CADENA_VACIA : req.getParameter(TXT_CIUDAD);
		final String                           estado                  = req.getParameter(TXT_ESTADO) == null ? CADENA_VACIA : req.getParameter(TXT_ESTADO);
		final String                           codigoPostal            = req.getParameter(TXT_CP) == null ? CADENA_VACIA : req.getParameter(TXT_CP);
		final String                           paisProcedencia         = req.getParameter(TXT_PAIS) == null ? CADENA_VACIA : req.getParameter(TXT_PAIS);
		final String                           telefonoCorresponsal    = req.getParameter(TXT_TELEFONO) == null ? CADENA_VACIA : req.getParameter(TXT_TELEFONO);		
		final String                           informaComision         = req.getParameter("opcIndicaValidaComision") == null ? CADENA_VACIA : req.getParameter("opcIndicaValidaComision");
		final String                           codigoCorresponsal      = req.getParameter(ID_CORRESPO) == null ? CADENA_VACIA : req.getParameter(ID_CORRESPO);
		String                           lstrCodErrorPage        = "";
		String                           lstrMsgErrorPage        = "";
		
		debug("CodigoCliente            :"+codigoCliente);
		debug("SecuenciaDomicilio       :"+secuenciaDomicilio);
		debug("GiroEmpresa              :"+giroEmpresa);
		debug("CentroCosto              :"+centroCosto);
		debug("NombreCorresponsal       :"+nombreCorresponsal);
		debug("CuentaCheques            :"+cuentaCheques);
		debug("CuentaCredito            :"+cuentaCredito);
		debug("LimiteAlterta            :"+limiteAlterta);
		debug("DiasPendientesConciliar  :"+diasPendientesConciliar);
		debug("DiasPendientesCompensar  :"+diasPendientesCompensar);
		debug("Indicador Valida Comision:"+informaComision);
		debug("TelefonoAclaracion       :"+telefonoAclaracion);
		debug("CorreoContacto           :"+correoContacto);
		debug("CorreoAlternoContacto    :"+correoAlternoContacto);
		debug("CalleDomicilio           :"+calleDomicilio);
		debug("NumeroExt                :"+numeroExt);
		debug("NumeroInt                :"+numeroInt);
		debug("ColoniaDomicilio         :"+coloniaDomicilio);
		debug("DelegacionMunicipio      :"+delegacionMunicipio);
		debug("Ciudad                   :"+ciudad);
		debug("Estado                   :"+estado);
		debug("CodigoPostal             :"+codigoPostal);
		debug("PaisProcedencia          :"+paisProcedencia);
		debug("TelefonoCorresponsal     :"+telefonoCorresponsal);
		debug("Codigo Corresponsal      :"+codigoCorresponsal);
		
		beanModifica.setCodigoCorresponsalia(Parametros.getParametroAplicacion(CORRESPONSALIA));
		beanModifica.setCodigoCorresponsal(codigoCorresponsal);
		//beanModifica.setCodigoCliente(codigoCliente);
		//Datos no informados a la DLA1 en Modificacion
		beanModifica.setEstatusCorresponsal("CS2");
		beanModifica.setIndicadorConciliacion("A");
		beanModifica.setIndicadorValidaComision(informaComision); // 3ca, Se debe informar el valor del Combo
		beanModifica.setDivisaCuentaCheques(TXT_MXP);
		beanModifica.setDivisaAsocLineaCredito(TXT_MXP);
		//Termina bloque de datos no informados
		beanModifica.setSecuenciaDomicilio(secuenciaDomicilio);
		beanModifica.setGiroEmpresa(giroEmpresa);
		beanModifica.setCentroCosto(centroCosto);
		beanModifica.setNombreCorresponsal(nombreCorresponsal);
		beanModifica.setCuentaChequesCorresponsal(cuentaCheques);
		beanModifica.setLineaCreditoCorresponsal(cuentaCredito);
		beanModifica.setLimiteImporteCorresponsalFront(limiteAlterta);
		beanModifica.setParamDiasPendientesConciliar(diasPendientesConciliar);
		beanModifica.setParamDiasPendientesCompensar(diasPendientesCompensar);
		beanModifica.setTelefonoAclaracion(telefonoAclaracion);
		beanModifica.setCorreoContacto(correoContacto);
		beanModifica.setCorreoAlternoContacto(correoAlternoContacto);
		beanModifica.setCalleDomicilioCorresponsal(calleDomicilio);
		beanModifica.setNumeroExteriorCorresponsal(numeroExt);
		beanModifica.setNumeroInteriorCorresponsal(numeroInt);
		beanModifica.setColoniaDomicilioCorresponsal(coloniaDomicilio);
		beanModifica.setDelegacionMunicipioCorresponsal(delegacionMunicipio);
		beanModifica.setCiudadCorresponsal(ciudad);
		beanModifica.setEstadoCorresponsal(estado);
		beanModifica.setCodigoPostalCorresponsal(codigoPostal);
		beanModifica.setPaisProcedenciaCorresponsal(paisProcedencia);
		beanModifica.setTelefonoCorresponsal(telefonoCorresponsal);
		beanModifica.setTipoOperacion("M");

		
		try{
			final BeanResultadoCorresponsalia beanRegreso = bOCorresponsalesMantenimiento.modificacionMantenimientoCorresponsalia(beanModifica, getArchitechBean());
			lstrCodErrorPage = beanRegreso.getCodError();
			lstrMsgErrorPage = beanRegreso.getMsgError();
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
			req.setAttribute(COD_ERROR, lstrCodErrorPage);
			req.setAttribute(MSG_ERROR, lstrMsgErrorPage);
			return new ModelAndView("ModificaMttoCorresponsales",null);			
		}

		debug(CODIGO_ERROR + lstrCodErrorPage);
		debug(MENSAJE_ERROR + lstrMsgErrorPage);
		return consultaMttoCorresponsales(req, res);
	}
	
	/**
	 * Exportar corresponsalia.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="ExportarCorresponsalia.do")
	public ModelAndView exportarCorresponsalia(HttpServletRequest req,
			HttpServletResponse res){
		final HttpSession session = req.getSession(); 
		final HashMap<String, Object> map = new HashMap<String, Object>();
		final List<BeanCorresponsal>  listaRegistros = 
			(List<BeanCorresponsal>) session.getAttribute(LISTA_REG_CANAL_CORR);

		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		if(listaRegistros==null ||( listaRegistros != null && listaRegistros.isEmpty())){
			info("Registro para exportar" + listaRegistros.size());
			final HashMap<String,Object>  lhsmParametros   = new HashMap<String,Object>();
			lhsmParametros.put(COD_ERROR, "NOEXPORT");
			lhsmParametros.put(MSG_ERROR, "NO HAY REGISTROS PARA EXPORTAR");
			return new ModelAndView("ConsultaMttoCorresponsal",lhsmParametros);			
		}		
		final JRDataSource dataSource = new JRBeanArrayDataSource(listaRegistros.toArray());	
		final DateFormat Dformat = new  SimpleDateFormat("yyyy/MM/dd HH:mm:ss aa", 
				new Locale("ES","MX")); 
		map.put("fechaReporte", Dformat.format(java.util.Calendar.getInstance().getTime()));  //  pasamos la fecha al reporte	
		
		return new ModelAndView(new ExportaXLS("Corresponsales","/com/isban/corresponsalia/reportes/doctos/corresponsales.jasper",dataSource), map);	
		
	}
	
	/**
	 * Pre alta.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="PreAlta.do")
	public ModelAndView preAlta(HttpServletRequest req, HttpServletResponse res) {
		info("Inicia Prea Alta Corresponsal");
		
		final HashMap<String,Object>           mapParametros        = new HashMap<String,Object>();
		final BeanABMMantenimientoCorresponsal beanPreAlta          = new BeanABMMantenimientoCorresponsal();
		BeanConsultaPreAlta              beanRespuestaPreAlta = new BeanConsultaPreAlta();
		final String                           codigoCliente        = req.getParameter(TXT_CODIGO_CLIENTE) == null ? CADENA_VACIA : req.getParameter(TXT_CODIGO_CLIENTE);
		final String                           secuenciaDomicilio   = req.getParameter(TXT_SEC_DOMIC) == null ? CADENA_VACIA : req.getParameter(TXT_SEC_DOMIC);
		final String                           centroCosto          = req.getParameter(TXT_CENTRO_COSTOS) == null ? CADENA_VACIA : req.getParameter(TXT_CENTRO_COSTOS);
		final String                           giroEmpresa          = req.getParameter(TXT_GIRO_EMP) == null ? CADENA_VACIA : req.getParameter(TXT_GIRO_EMP);
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));		
		debug("CodigoCliente     :"+codigoCliente);
		debug("SecuenciaDomicilio:"+secuenciaDomicilio);
		debug("CentroCosto       :"+centroCosto);
		
		beanPreAlta.setCodigoCorresponsalia(Parametros.getParametroAplicacion(CORRESPONSALIA));
		beanPreAlta.setCodigoCorresponsal("0014");
		beanPreAlta.setTipoOperacion("P");
		beanPreAlta.setCodigoCliente(codigoCliente);
		beanPreAlta.setSecuenciaDomicilio(secuenciaDomicilio);
		beanPreAlta.setCentroCosto(centroCosto);
		
		try{
			beanRespuestaPreAlta = bOCorresponsalesMantenimiento.consultaPreAltaCorresponsal(beanPreAlta, getArchitechBean());
			if("".equals(beanRespuestaPreAlta.getCodError())){
				mapParametros.put("codigoCliente"     , codigoCliente);
				mapParametros.put("secuenciaDomicilio", secuenciaDomicilio);
				mapParametros.put("centroCosto", centroCosto);
				mapParametros.put("giroEmpresa", giroEmpresa);							
				mapParametros.put("beanRespuestaPreAlta", beanRespuestaPreAlta);
				mapParametros.put(PRE_ALTA , "");				
			}
			else{
				mapParametros.put("msgResultado", beanRespuestaPreAlta.getCodError() + ":" + beanRespuestaPreAlta.getMsgError());
				mapParametros.put(PRE_ALTA , "class=\"Campos_Des\" disabled=\"disabled\"");
			}
			
			mapParametros.put("exitoConsulta", "true");
		}
		catch(BusinessException e){
			showException(e);
			mapParametros.put("msgResultado", e.getCode() + ":" + e.getMessage());
			mapParametros.put(PRE_ALTA , "class=\"Campos_Des\" disabled=\"disabled\"");
		}

		return new ModelAndView("AltaMttoCorresponsales", mapParametros);
		
	}
	
	
	/**
	 * Activar desactivar corresponsal.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="ActivarDesactivarCorresponsal.do")
	public ModelAndView activarDesactivarCorresponsal(HttpServletRequest req, HttpServletResponse res) {
		info("Inicia Baja Operaciones Corresponsalia");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH));
		final String                           regSel              = req.getParameter(REG_SEL) == null ? CADENA_VACIA : req.getParameter(REG_SEL);
		final List<BeanCorresponsal>           listaCorresponsales = (List<BeanCorresponsal>)req.getSession().getAttribute(LISTA_REG_CANAL_CORR);
		BeanCorresponsal                 beanCorresponsal    = null;
		String                           lstrCodErrorPage         = "";
		String                           lstrMsgErrorPage         = "";

		debug("Registro a Modificar:"+regSel);

		try{
			beanCorresponsal = listaCorresponsales.get(Integer.parseInt(regSel));
		}
		catch(IndexOutOfBoundsException  e){
			debug("No se pudo obtener el bean");
		}
			
		
		try{
			final BeanResultadoCorresponsalia beanRegreso = bOCorresponsalesMantenimiento.activaDesactivaCorresponsalia(beanCorresponsal, getArchitechBean());
			lstrCodErrorPage = beanRegreso.getCodError();
			lstrMsgErrorPage = beanRegreso.getMsgError();
			req.setAttribute(COD_AVISO, lstrCodErrorPage);
			req.setAttribute(MSG_AVISO, lstrMsgErrorPage);
		}
		catch(BusinessException e){
			showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
			//mapParametros.put(COD_ERROR, lstrCodErrorPage);
			//mapParametros.put(MSG_ERROR, lstrMsgErrorPage);
			req.setAttribute(COD_ERROR, lstrCodErrorPage);
			req.setAttribute(MSG_ERROR, lstrMsgErrorPage);
		}
		
		debug(CODIGO_ERROR + lstrCodErrorPage);
		debug(MENSAJE_ERROR + lstrMsgErrorPage);


		
		return consultaMttoCorresponsales(req, res);
	}

		
}

