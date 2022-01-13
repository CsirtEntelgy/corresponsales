/*
 * 
 */
package com.isban.corresponsalia.controllers.catalogos;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.isban.corresponsalia.beans.catalogos.BeanCatalogoCodigosOperacion;
import com.isban.corresponsalia.bo.catalogos.BOCatalogoCodigosOperacion;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;
import com.isban.ebe.commons.exception.ExceptionDataAccess;


/**
 * The Class ControllerConsultasBitacora.
 */
@Controller
public class ControllerCatalogoCodigosOperacion extends ArchitechEBE{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/** The Constant NEW_ARCH. */
	private static final String NEW_ARCH = "NewArchitechSession"; 
	
	
	/** The Constant CODIGO_ERROR. */
	private static final String CODIGO_ERROR = "Codigo error :";
	
	/** The Constant MENSAJE_ERROR. */
	private static final String MENSAJE_ERROR = "Mensaje error:";
	
	/** The Constant CONSULTA_BITA. */
	private static final String VIEW = "ConsultaCodigoOperacion";
	
	/** The Constant CAMPO_ID_APLICACION **/
	private static final String CAMPO_ID_APLICACION = "txtIdAplicacion";
	
	/** The Constant CAMPO_ID_APLICACION **/
	private static final String CAMPO_CODIGO_OPERACION = "txtCodigoOperacion";
	
	/** The Constant CAMPO_DESCRIPCION_OPERACION **/
	private static final String CAMPO_DESCRIPCION_OPERACION = "txtDescOp";
	
	/** The Constant CAMPO_CODIGO_ISO **/
	private static final String CAMPO_CODIGO_ISO = "txtCodigoISO";
	
	/** The Constant CAMPO_DESCRIPCION_ISO **/
	private static final String CAMPO_DESCRIPCION_ISO = "txtDescISO";
	
	/** The Constant MENSAJE_ERROR_1 **/
	private static final String MENSAJE_ERROR_1 = "No se pudo realizar la operacion";
	
	/** The Constant PARAM_ERROR **/
	private static final String PARAM_ERROR = "error";
	
	/** The Constant PARAM_ID_APLICACION **/
	private static final String PARAM_ID_APLICACION = "idAplicacion";
	
	/** The Constant PARAM_CODIGO_OPERACION **/
	private static final String PARAM_CODIGO_OPERACION = "codigoOperacion";
	
	/** The Constant PARAM_DESCRIPCION_OPERACION **/
	private static final String PARAM_DESCRIPCION_OPERACION = "descripcionOperacion";
	
	/** The Constant PARAM_CODIGO_ISO **/
	private static final String PARAM_CODIGO_ISO = "codigoISO";
	
	/** The Constant PARAM_DSESCRIPCION_ISO **/
	private static final String PARAM_DSESCRIPCION_ISO = "descripcionISO";
	
	/** The Constant PARAM_LISTA **/
	private static final String PARAM_LISTA = "lista";
	
	/** The Constant PARAM_COD_ERROR **/
	private static final String PARAM_COD_ERROR = "codError";
	
	/** The Constant PARAM_MENSAJE **/
	private static final String PARAM_MENSAJE = "mensaje";
	
	/** The Constant PARAM_VACIO **/
	private static final String PARAM_VACIO = "";
	
	/** The Constant PARAM_OK **/
	private static final String PARAM_OK = "OK";
	
	/** The Constant MENSAJE_ERROR_2 **/
	private static final String MENSAJE_ERROR_2 = "No se pudo conectar a la base de datos";
	
	/** The Constant HDN_ID_APLICACION **/
	private static final String HDN_ID_APLICACION = "hdnIdAplicacion";
	
	/** The Constant HDN_COD_OPERACION **/
	private static final String HDN_COD_OPERACION = "hdnCodigoOperacion";
	
	/** The Constant HDN_COD_ISO **/
	private static final String HDN_COD_ISO = "hdnCodigoISO";
	
	/** The Constant HDN_COD_ISO **/
	private static final String HDN_DESC_COD_OP = "hdnDescCodigoOp";
	
	/** The Constant HDN_COD_ISO **/
	private static final String HDN_DESC_COD_ISO = "hdnDescISO";
	
	/** The bo catalogos Codigos Operacion. */
	transient private BOCatalogoCodigosOperacion boCatalogoCodigosOperacion;

	/**
	 * Gets the boCatalogoCodigosOperacion
	 * @return boCatalogoCodigosOperacion
	 */
	public BOCatalogoCodigosOperacion getBoCatalogoCodigosOperacion() {
		return boCatalogoCodigosOperacion;
	}

	/**
	 * Sets the boCatalogoCodigosOperacion
	 * @param boCatalogoCodigosOperacion - El nuevo boCatalogoCodigosOperacion a colocar 
	 */
	public void setBoCatalogoCodigosOperacion(BOCatalogoCodigosOperacion boCatalogoCodigosOperacion) {
		this.boCatalogoCodigosOperacion = boCatalogoCodigosOperacion;
	}

	/**
	 * Consulta Catalogo de Codigos Operacion.
	 * @param req Peticion de la Pagina Catalogo de Codigos de Operacion
	 * @param res Respuesta a la Pagina Catalogo de Codigos de Operacion
	 * @return ModelAndView - Catalogo de Operaciones
	 */
	@RequestMapping(value="CatalogoConsultaCodigosOperacion.do")
	public ModelAndView catalogoConsultaCodigosOperacion(HttpServletRequest req, HttpServletResponse res){
		debug("CatalogoConsultaCodigosOperacion.do");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));
		final HashMap<String,Object>  model   = new HashMap<String,Object>();
		BeanCatalogoCodigosOperacion llamadaBO = new BeanCatalogoCodigosOperacion();
		BeanCatalogoCodigosOperacion respuestaBO = new BeanCatalogoCodigosOperacion();
		
		String idAplicacion = req.getParameter(CAMPO_ID_APLICACION) != null ? req.getParameter(CAMPO_ID_APLICACION) : "";
		String codigoOperacion = req.getParameter(CAMPO_CODIGO_OPERACION) != null ? req.getParameter(CAMPO_CODIGO_OPERACION) : "";
		String descripcionOperacion = req.getParameter(CAMPO_DESCRIPCION_OPERACION) != null ? req.getParameter(CAMPO_DESCRIPCION_OPERACION) : "";
		String codigoISO = req.getParameter(CAMPO_CODIGO_ISO) != null ? req.getParameter(CAMPO_CODIGO_ISO) : "";
		String descripcionISO = req.getParameter(CAMPO_DESCRIPCION_ISO) != null ? req.getParameter(CAMPO_DESCRIPCION_ISO) : "";
		
		String mensaje = "";
		String codError = "";
		
		llamadaBO.setIdAplicacion(idAplicacion);
		llamadaBO.setCodigoOperacion(codigoOperacion);
		llamadaBO.setDescripcionCodigoOperacion(descripcionOperacion);
		llamadaBO.setCodigoIso(codigoISO);
		llamadaBO.setDescripcionIso(descripcionISO);
		
		try{
			respuestaBO = boCatalogoCodigosOperacion.consultarCatalogo(llamadaBO, getArchitechBean());
			mensaje = PARAM_VACIO;
			codError = PARAM_VACIO;
			if(respuestaBO.getListaBeanCatalogoCodigosOperacion() == null){
				info("No se encontraron registros para la consulta especificada");
				mensaje = "No hay registros para la consulta especifica";
				codError = "ERR000";
			}
			
		}
		
		catch(BusinessException e){
			showException(e);
			mensaje = MENSAJE_ERROR_1;
			codError = PARAM_ERROR;
		} catch (ExceptionDataAccess e) {
			showException(e);
			mensaje = MENSAJE_ERROR_2;
			codError = PARAM_ERROR;
		}
		
		debug(CODIGO_ERROR + codError);
		debug(MENSAJE_ERROR + mensaje);	
		
		model.put(PARAM_ID_APLICACION, idAplicacion);
		model.put(PARAM_CODIGO_OPERACION, codigoOperacion);
		model.put(PARAM_DESCRIPCION_OPERACION, descripcionOperacion);
		model.put(PARAM_CODIGO_ISO, codigoISO);
		model.put(PARAM_DSESCRIPCION_ISO, descripcionISO);
		
		model.put(PARAM_LISTA, respuestaBO.getListaBeanCatalogoCodigosOperacion());
		model.put(PARAM_COD_ERROR, codError);
		model.put(PARAM_MENSAJE, mensaje);
		
		return new ModelAndView(VIEW, model);
	}	
	
	/**
	 * Modifica Registro del Catalogo de Aplicaciones.
	 * @param req Peticion de la Pagina Catalogo de Codigos de Operacion
	 * @param res Respuesta a la Pagina Catalogo de Codigos de Operacion
	 * @return ModelAndView - Catalogo de Operaciones
	 */
	@RequestMapping(value="ModificaRegistroCatalogoCodOperacion.do")
	public ModelAndView modificaRegistro(HttpServletRequest req, HttpServletResponse res){
		debug("ModificaRegistro.do");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));
		final HashMap<String,Object>  model   = new HashMap<String,Object>();
		BeanCatalogoCodigosOperacion llamadaBO = new BeanCatalogoCodigosOperacion();
		BeanCatalogoCodigosOperacion respuestaBO = new BeanCatalogoCodigosOperacion();
		
		String idAplicacion = req.getParameter(CAMPO_ID_APLICACION) != null ? req.getParameter(CAMPO_ID_APLICACION) : "";
		String codigoOperacion = req.getParameter(CAMPO_CODIGO_OPERACION) != null ? req.getParameter(CAMPO_CODIGO_OPERACION) : "";
		String descripcionOperacion = req.getParameter(CAMPO_DESCRIPCION_OPERACION) != null ? req.getParameter(CAMPO_DESCRIPCION_OPERACION) : "";
		String codigoISO = req.getParameter(CAMPO_CODIGO_ISO) != null ? req.getParameter(CAMPO_CODIGO_ISO) : "";
		String descripcionISO = req.getParameter(CAMPO_DESCRIPCION_ISO) != null ? req.getParameter(CAMPO_DESCRIPCION_ISO) : "";
		
		String mensaje = "";
		String codError = "";
		
		llamadaBO.setIdAplicacion(idAplicacion);
		llamadaBO.setCodigoOperacion(codigoOperacion);
		llamadaBO.setDescripcionCodigoOperacion(descripcionOperacion);
		llamadaBO.setCodigoIso(codigoISO);
		llamadaBO.setDescripcionIso(descripcionISO);
		
		try{
			respuestaBO = boCatalogoCodigosOperacion.modificarRegistro(llamadaBO, getArchitechBean());
			mensaje = "El registro fue actualizado correctamente.";
			codError = PARAM_OK;
		}
		
		catch(BusinessException e){
			showException(e);
			mensaje = MENSAJE_ERROR_1;
			codError = PARAM_ERROR;
		} catch (ExceptionDataAccess e) {
			showException(e);
			mensaje = MENSAJE_ERROR_2;
			codError = PARAM_ERROR;
		}
		
		debug(CODIGO_ERROR + codError);
		debug(MENSAJE_ERROR + mensaje);	
		
		model.put(PARAM_ID_APLICACION, "");
		model.put(PARAM_CODIGO_OPERACION, "");
		model.put(PARAM_DESCRIPCION_OPERACION, "");
		model.put(PARAM_CODIGO_ISO, "");
		model.put(PARAM_DSESCRIPCION_ISO, "");
		
		model.put(PARAM_LISTA, respuestaBO.getListaBeanCatalogoCodigosOperacion());
		model.put(PARAM_COD_ERROR, codError);
		model.put(PARAM_MENSAJE, mensaje);
		
		return new ModelAndView(VIEW, model);
	}
	
	/**
	 * Da de alta un nuevo registro al Catalogo de Codigos de Operacion
	 * @param req Peticion de la Pagina Catalogo de Codigos de Operacion
	 * @param res Respuesta a la Pagina Catalogo de Codigos de Operacion
	 * @return ModelAndView - Catalogo de Operaciones
	 */
	@RequestMapping(value="AltaRegistroCatalogoCodOperacion.do")
	public ModelAndView altaRegistro(HttpServletRequest req, HttpServletResponse res){
		debug("AltaRegistro.do");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));
		final HashMap<String,Object>  model   = new HashMap<String,Object>();
		BeanCatalogoCodigosOperacion llamadaBO = new BeanCatalogoCodigosOperacion();
		BeanCatalogoCodigosOperacion respuestaBO = new BeanCatalogoCodigosOperacion();
		
		String nuevoIdAplicacion = req.getParameter(CAMPO_ID_APLICACION) != null ? req.getParameter(CAMPO_ID_APLICACION) : "";
		String nuevoCodigoOperacion = req.getParameter(CAMPO_CODIGO_OPERACION) != null ? req.getParameter(CAMPO_CODIGO_OPERACION) : "";
		String nuevoDescripcionOperacion = req.getParameter(CAMPO_DESCRIPCION_OPERACION) != null ? req.getParameter(CAMPO_DESCRIPCION_OPERACION) : "";
		String nuevoCodigoISO = req.getParameter(CAMPO_CODIGO_ISO) != null ? req.getParameter(CAMPO_CODIGO_ISO) : "";
		String nuevoDescripcionISO = req.getParameter(CAMPO_DESCRIPCION_ISO) != null ? req.getParameter(CAMPO_DESCRIPCION_ISO) : "";
		
		String mensaje = "";
		String codError = "";
		
		llamadaBO.setIdAplicacion(nuevoIdAplicacion);
		llamadaBO.setCodigoOperacion(nuevoCodigoOperacion);
		llamadaBO.setDescripcionCodigoOperacion(nuevoDescripcionOperacion);
		llamadaBO.setCodigoIso(nuevoCodigoISO);
		llamadaBO.setDescripcionIso(nuevoDescripcionISO);	
		
		try{
			respuestaBO = boCatalogoCodigosOperacion.altaRegistro(llamadaBO, getArchitechBean());
			mensaje = "El registro fue dado de alta exitosamente.";
			codError = PARAM_OK;
		}
		catch(BusinessException e){
			showException(e);
			mensaje = MENSAJE_ERROR_1;
			codError = PARAM_ERROR;
		} catch (ExceptionDataAccess e) {
			showException(e);
			mensaje = MENSAJE_ERROR_2;
			codError = PARAM_ERROR;
		}
		
		debug(CODIGO_ERROR + codError);
		debug(MENSAJE_ERROR + mensaje);	
		
		model.put(PARAM_ID_APLICACION, "");
		model.put(PARAM_CODIGO_OPERACION, "");
		model.put(PARAM_DESCRIPCION_OPERACION, "");
		model.put(PARAM_CODIGO_ISO, "");
		model.put(PARAM_DSESCRIPCION_ISO, "");
		
		
		
		model.put(PARAM_LISTA, respuestaBO.getListaBeanCatalogoCodigosOperacion());
		model.put(PARAM_COD_ERROR, codError);
		model.put(PARAM_MENSAJE, mensaje);
		
		return new ModelAndView(VIEW, model);
	}
	
	/**
	 * Borra un registro dado del Catalogo de Codigos de Operacion
	 * @param req Peticion de la Pagina Catalogo de Codigos de Operacion
	 * @param res Respuesta a la Pagina Catalogo de Codigos de Operacion
	 * @return ModelAndView - Catalogo de Operaciones
	 */
	@RequestMapping(value="EliminarRegistroCatalogoCodOperacion.do")
	public ModelAndView eliminarRegistro(HttpServletRequest req, HttpServletResponse res){
		debug("EliminarRegistro.do");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));
		final HashMap<String,Object>  model   = new HashMap<String,Object>();
		BeanCatalogoCodigosOperacion llamadaBO = new BeanCatalogoCodigosOperacion();
		BeanCatalogoCodigosOperacion respuestaBO = new BeanCatalogoCodigosOperacion();
		
		String idAplicacion = req.getParameter("regAnularApp") != null ? req.getParameter("regAnularApp") : "";
		String codigoOperacion = req.getParameter("regAnularOper") != null ? req.getParameter("regAnularOper") : "";
		String codigoISO = req.getParameter("regAnularISO") != null ? req.getParameter("regAnularISO") : "";
		
		String mensaje = "";
		String codError = "";
		
		llamadaBO.setIdAplicacion(idAplicacion);
		llamadaBO.setCodigoOperacion(codigoOperacion);
		llamadaBO.setCodigoIso(codigoISO);
		
		try{
			respuestaBO = boCatalogoCodigosOperacion.bajaRegistro(llamadaBO, getArchitechBean());
			
				mensaje = "El registro fue borrado exitosamente.";
				codError = PARAM_OK;
			
		}
		catch(BusinessException e){
			showException(e);
			mensaje = MENSAJE_ERROR_1;
			codError = PARAM_ERROR;
		} catch (ExceptionDataAccess e) {
			showException(e);
			mensaje = MENSAJE_ERROR_2;
			codError = PARAM_ERROR;
		}
		
		debug(CODIGO_ERROR + codError);
		debug(MENSAJE_ERROR + mensaje);	
		
		model.put(PARAM_ID_APLICACION, "");
		model.put(PARAM_CODIGO_OPERACION, "");
		model.put(PARAM_DESCRIPCION_OPERACION, "");
		model.put(PARAM_CODIGO_ISO, "");
		model.put(PARAM_DSESCRIPCION_ISO, "");
		
		model.put(PARAM_LISTA, respuestaBO.getListaBeanCatalogoCodigosOperacion());
		model.put(PARAM_COD_ERROR, codError);
		model.put(PARAM_MENSAJE, mensaje);
		
		return new ModelAndView(VIEW, model);
	}
	
	/**
	 * Verifica si el registro ya se encuentra en base de datos.
	 * @param req Peticion de la Pagina Catalogo de Codigos
	 * @param res Respuesta a la Pagina Catalogo de Codigos
	 * @return the model and view
	 */
	@RequestMapping(value="RevisandoRegistroCodigosOperacion.do")
	public ModelAndView revisandoRegistro(HttpServletRequest req, HttpServletResponse res){
		debug("RevisandoRegistroCodigosOperacion.do");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));
		final HashMap<String,Object>  model   =  consultaFiltroOculto(req);
		BeanCatalogoCodigosOperacion  llamadaBO = new BeanCatalogoCodigosOperacion(); 
		Boolean existeRegistro = false;
		
		String idAplicacion = req.getParameter(CAMPO_ID_APLICACION) != null ? req.getParameter(CAMPO_ID_APLICACION) : "";
		String codigoOperacion = req.getParameter(CAMPO_CODIGO_OPERACION) != null ? req.getParameter(CAMPO_CODIGO_OPERACION) : "";
		String descripcionOperacion = req.getParameter(CAMPO_DESCRIPCION_OPERACION) != null ? req.getParameter(CAMPO_DESCRIPCION_OPERACION) : "";
		String codigoISO = req.getParameter(CAMPO_CODIGO_ISO) != null ? req.getParameter(CAMPO_CODIGO_ISO) : "";
		String descripcionISO = req.getParameter(CAMPO_DESCRIPCION_ISO) != null ? req.getParameter(CAMPO_DESCRIPCION_ISO) : "";
		
		String mensaje = "";
		String codError = "";
		
		llamadaBO.setIdAplicacion(idAplicacion);
		
		try{
			existeRegistro = boCatalogoCodigosOperacion.buscarRegistro(llamadaBO, getArchitechBean());
			if (existeRegistro){
				mensaje = "El registro ya existe.";
				codError = "MOD000";
			}
			else{
				mensaje = "Nuevo registro.";
				codError = "MOD001";
			}
			llamadaBO = null;
			llamadaBO = new BeanCatalogoCodigosOperacion();
			llamadaBO.setIdAplicacion(idAplicacion);
			llamadaBO.setCodigoOperacion(codigoOperacion);
			llamadaBO.setDescripcionCodigoOperacion(descripcionOperacion);
			llamadaBO.setCodigoIso(codigoISO);
			llamadaBO.setDescripcionIso(descripcionISO);
		}
		catch(BusinessException e){
			showException(e);
			mensaje = MENSAJE_ERROR_1;
			codError = PARAM_ERROR;
		} catch (ExceptionDataAccess e) {
			showException(e);
			mensaje = MENSAJE_ERROR_2;
			codError = PARAM_ERROR;
		}
		
		debug(CODIGO_ERROR + codError);
		debug(MENSAJE_ERROR + mensaje);	
		
		model.put(PARAM_ID_APLICACION, idAplicacion);
		model.put(PARAM_CODIGO_OPERACION, codigoOperacion);
		model.put(PARAM_DESCRIPCION_OPERACION, descripcionOperacion);
		model.put(PARAM_CODIGO_ISO, codigoISO);
		model.put(PARAM_DSESCRIPCION_ISO, descripcionISO);
		
		model.put(PARAM_COD_ERROR, codError);
		model.put(PARAM_MENSAJE, mensaje);
		
		return new ModelAndView(VIEW, model);
	}
	
	/**
	 * Consulta Catalogo de Aplicaciones mediante filtro salvado.
	 * @param req Peticion de la Pagina Catalogo de Aplicaciones
	 * @return the model and view
	 */
	private HashMap<String,Object> consultaFiltroOculto(HttpServletRequest req){
		debug("Consultando catalogo de codigos de operacion");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));
		final HashMap<String,Object>  model   = new HashMap<String,Object>();
		BeanCatalogoCodigosOperacion llamadaBO = new BeanCatalogoCodigosOperacion();
		BeanCatalogoCodigosOperacion respuestaBO = new BeanCatalogoCodigosOperacion();
		String idAplicacion = req.getParameter(HDN_ID_APLICACION) != null ? req.getParameter(HDN_ID_APLICACION) : PARAM_VACIO;
		String codigoOper = req.getParameter(HDN_COD_OPERACION) != null ? req.getParameter(HDN_COD_OPERACION) : PARAM_VACIO;
		String descCodOper = req.getParameter(HDN_DESC_COD_OP) != null ? req.getParameter(HDN_DESC_COD_OP) : PARAM_VACIO;
		String codigoISO = req.getParameter(HDN_COD_ISO) != null ? req.getParameter(HDN_COD_ISO) : PARAM_VACIO;
		String descCodISO = req.getParameter(HDN_DESC_COD_ISO) != null ? req.getParameter(HDN_DESC_COD_ISO) : PARAM_VACIO;
		
		llamadaBO.setIdAplicacion(idAplicacion);
		llamadaBO.setCodigoOperacion(codigoOper);
		llamadaBO.setDescripcionCodigoOperacion(descCodOper);
		llamadaBO.setCodigoIso(codigoISO);
		llamadaBO.setDescripcionIso(descCodISO);;
		
		
		try{
			respuestaBO = boCatalogoCodigosOperacion.consultarCatalogo(llamadaBO, getArchitechBean());
		}
		
		catch(BusinessException e){
			showException(e);;
		} catch (ExceptionDataAccess e) {
			showException(e);
		}
		
		model.put(HDN_ID_APLICACION, idAplicacion);
		model.put(HDN_COD_OPERACION, codigoOper);
		model.put(HDN_DESC_COD_OP, descCodOper);
		model.put(HDN_COD_ISO, codigoISO);
		model.put(HDN_DESC_COD_ISO, descCodISO);
		
		model.put(PARAM_LISTA, respuestaBO.getListaBeanCatalogoCodigosOperacion());
		
		return model;
	}
	
}