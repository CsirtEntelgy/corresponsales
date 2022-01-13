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

import com.isban.corresponsalia.beans.catalogos.BeanCatalogoAplicaciones;
import com.isban.corresponsalia.bo.catalogos.BOCatalogoAplicaciones;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;
import com.isban.ebe.commons.exception.ExceptionDataAccess;


/**
 * The Class ControllerConsultasBitacora.
 */
@Controller
public class ControllerCatalogoAplicaciones extends ArchitechEBE{
	
	
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
	private static final String VIEW = "ConsultaAplicaciones";
	
	/** The Constant CAMPO_ID_APLICACION **/
	private static final String CAMPO_ID_APLICACION = "txtIdAplicacion";
	
	/** The Constant CAMPO_DESCRIPCION **/
	private static final String CAMPO_DESCRIPCION = "txtDescripcion";
	
	/** The Constant MENSAJE_ERROR_1 **/
	private static final String MENSAJE_ERROR_1 = "No se pudo realizar la operacion";
	
	/** The Constant MENSAJE_ERROR_2 **/
	private static final String MENSAJE_ERROR_2 = "No se pudo conectar a la base de datos";
	
	/** The Constant PARAM_ERROR **/
	private static final String PARAM_ERROR = "error";
	
	/** The Constant PARAM_ID_APLICACION **/
	private static final String PARAM_ID_APLICACION = "idAplicacion";
	
	/** The Constant PARAM_DESCRIPCION **/
	private static final String PARAM_DESCRIPCION = "descripcion";
	
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
	
	/** The Constant HDN_ID_APLICACION **/
	private static final String HDN_ID_APLICACION = "hdnIdAplicacion";
	
	/** The Constant HDN_DESCRIPCION **/
	private static final String HDN_DESCRIPCION = "hdnDescripcion";
	
	/** The bo catalogos Aplicaciones. */
	transient private BOCatalogoAplicaciones boCatalogoAplicaciones;

	/**
	 * Gets the boCatalogoAplicaciones
	 * @return boCatalogoAplicaciones
	 */
	public BOCatalogoAplicaciones getBoCatalogoAplicaciones() {
		return boCatalogoAplicaciones;
	}


	/**
	 * Sets the boCatalogoAplicaciones
	 * @param boCatalogoAplicaciones el nuevo boCatalogoAplicaciones a colocar
	 */
	public void setBoCatalogoAplicaciones(BOCatalogoAplicaciones boCatalogoAplicaciones) {
		this.boCatalogoAplicaciones = boCatalogoAplicaciones;
	}

	/**
	 * Consulta Catalogo de Aplicaciones.
	 * @param req Peticion de la Pagina Catalogo de Aplicaciones
	 * @param res Respuesta a la Pagina Catalogo de Aplicaciones
	 * @return the model and view
	 */
	@RequestMapping(value="CatalogoConsultaAplicaciones.do")
	public ModelAndView catalogoConsultaAplicaciones(HttpServletRequest req, HttpServletResponse res){
		debug("CatalogoConsultaAplicaciones.do");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));
		final HashMap<String,Object>  model   = new HashMap<String,Object>();
		BeanCatalogoAplicaciones llamadaBO = new BeanCatalogoAplicaciones();
		BeanCatalogoAplicaciones respuestaBO = new BeanCatalogoAplicaciones();
		String idAplicacion = req.getParameter(CAMPO_ID_APLICACION) != null ? req.getParameter(CAMPO_ID_APLICACION) : "";
		String descripcion = req.getParameter(CAMPO_DESCRIPCION) != null ? req.getParameter(CAMPO_DESCRIPCION) : "";
		String mensaje = "";
		String codError = "";
		
		llamadaBO.setIdAplicacion(idAplicacion);
		llamadaBO.setDescripcion(descripcion);
		
		try{
			respuestaBO = boCatalogoAplicaciones.consultarCatalogo(llamadaBO, getArchitechBean());
			mensaje = PARAM_VACIO;
			codError = PARAM_VACIO;
			if(respuestaBO.getListaBeanCatalogoAplicaciones() == null){
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
		model.put(PARAM_DESCRIPCION, descripcion);
		model.put(HDN_ID_APLICACION, idAplicacion);
		model.put(HDN_DESCRIPCION, descripcion);
		model.put(PARAM_LISTA, respuestaBO.getListaBeanCatalogoAplicaciones());
		model.put(PARAM_COD_ERROR, codError);
		model.put(PARAM_MENSAJE, mensaje);
		
		return new ModelAndView(VIEW, model);
	}	
	
	/**
	 * Modifica Registro del Catalogo de Aplicaciones.
	 * @param req Peticion de la Pagina Catalogo de Aplicaciones
	 * @param res Respuesta a la Pagina Catalogo de Aplicaciones
	 * @return the model and view
	 */
	@RequestMapping(value="ModificaRegistroCatalogoAplicaciones.do")
	public ModelAndView modificaRegistro(HttpServletRequest req, HttpServletResponse res){
		debug("ModificaRegistro.do");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));
		final HashMap<String,Object>  model   = new HashMap<String,Object>();
		BeanCatalogoAplicaciones llamadaBO = new BeanCatalogoAplicaciones();
		BeanCatalogoAplicaciones respuestaBO = new BeanCatalogoAplicaciones();
		
		String idAplicacion = req.getParameter(CAMPO_ID_APLICACION) != null ? req.getParameter(CAMPO_ID_APLICACION) : "";
		String nuevoDesc = req.getParameter(CAMPO_DESCRIPCION) != null ? req.getParameter(CAMPO_DESCRIPCION) : "";
		
		String mensaje = "";
		String codError = "";
		
		llamadaBO.setIdAplicacion(idAplicacion);
		llamadaBO.setDescripcion(nuevoDesc);
		
		try{
			respuestaBO = boCatalogoAplicaciones.modificarRegistro(llamadaBO, getArchitechBean());
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
		model.put(PARAM_DESCRIPCION, "");
		model.put(PARAM_LISTA, respuestaBO.getListaBeanCatalogoAplicaciones());
		model.put(PARAM_COD_ERROR, codError);
		model.put(PARAM_MENSAJE, mensaje);
		
		return new ModelAndView(VIEW, model);
	}
	
	/**
	 * Da de alta una nueva Aplicacion
	 * @param req Peticion de la Pagina Catalogo de Aplicaciones
	 * @param res Respuesta a la Pagina Catalogo de Aplicaciones
	 * @return ModelAndView
	 */
	@RequestMapping(value="AltaRegistroCatalogoAplicaciones.do")
	public ModelAndView altaRegistro(HttpServletRequest req, HttpServletResponse res){
		debug("AltaRegistro.do");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));
		final HashMap<String,Object>  model   = new HashMap<String,Object>();
		BeanCatalogoAplicaciones llamadaBO = new BeanCatalogoAplicaciones();
		BeanCatalogoAplicaciones respuestaBO = new BeanCatalogoAplicaciones();
		
		String nuevoIdApp = req.getParameter(CAMPO_ID_APLICACION) != null ? req.getParameter(CAMPO_ID_APLICACION) : "";
		String nuevoDesc = req.getParameter(CAMPO_DESCRIPCION) != null ? req.getParameter(CAMPO_DESCRIPCION) : "";
		
		String mensaje = "";
		String codError = "";
		
		llamadaBO.setIdAplicacion(nuevoIdApp);
		llamadaBO.setDescripcion(nuevoDesc);
		
		try{
			respuestaBO = boCatalogoAplicaciones.altaRegistro(llamadaBO, getArchitechBean());
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
		model.put(PARAM_DESCRIPCION, "");
		model.put(PARAM_LISTA, respuestaBO.getListaBeanCatalogoAplicaciones());
		model.put(PARAM_COD_ERROR, codError);
		model.put(PARAM_MENSAJE, mensaje);
		
		return new ModelAndView(VIEW, model);
	}
	
	/**
	 * Borra un registro dado por el usuario
	 * @param req Peticion de la Pagina Catalogo de Aplicaciones
	 * @param res Respuesta a la Pagina Catalogo de Aplicaciones
	 * @return ModelAndView
	 */
	@RequestMapping(value="EliminarRegistroCatalogoAplicaciones.do")
	public ModelAndView eliminarRegistro(HttpServletRequest req, HttpServletResponse res){
		debug("EliminarRegistro.do");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));
		final HashMap<String,Object>  model   = new HashMap<String,Object>();
		BeanCatalogoAplicaciones llamadaBO = new BeanCatalogoAplicaciones();
		BeanCatalogoAplicaciones respuestaBO = new BeanCatalogoAplicaciones();
		
		String idAplicacion = req.getParameter("regAnular") != null ? req.getParameter("regAnular") : "";
		
		String mensaje = "";
		String codError = "";
		
		llamadaBO.setIdAplicacion(idAplicacion);
		
		try{
			respuestaBO = boCatalogoAplicaciones.bajaRegistro(llamadaBO, getArchitechBean());
			if("CBE001".equals(respuestaBO.getErrorCode())){
				mensaje = respuestaBO.getErrorDesc();
				codError = respuestaBO.getErrorCode();
			} else {
				mensaje = "El registro fue borrado exitosamente.";
				codError = PARAM_OK;
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
		
		model.put(PARAM_ID_APLICACION, "");
		model.put(PARAM_DESCRIPCION, "");
		model.put(PARAM_LISTA, respuestaBO.getListaBeanCatalogoAplicaciones());
		model.put(PARAM_COD_ERROR, codError);
		model.put(PARAM_MENSAJE, mensaje);
		
		return new ModelAndView(VIEW, model);
	}
	
	/**
	 * Verifica si el registro ya se encuentra en base de datos.
	 * @param req Peticion de la Pagina Catalogo de Aplicaciones
	 * @param res Respuesta a la Pagina Catalogo de Aplicaciones
	 * @return the model and view
	 */
	@RequestMapping(value="RevisandoRegistroAplicaciones.do")
	public ModelAndView revisandoRegistro(HttpServletRequest req, HttpServletResponse res){
		debug("RevisandoRegistro.do");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));
		final HashMap<String,Object>  model   = consultaFiltroOculto(req);
		BeanCatalogoAplicaciones llamadaBO = new BeanCatalogoAplicaciones();
		Boolean existeRegistro = false;
		
		String idAplicacion = req.getParameter(CAMPO_ID_APLICACION) != null ? req.getParameter(CAMPO_ID_APLICACION) : "";
		String descripcion = req.getParameter(CAMPO_DESCRIPCION) != null ? req.getParameter(CAMPO_DESCRIPCION) : "";
		
		
		String mensaje = "";
		String codError = "";
		
		llamadaBO.setIdAplicacion(idAplicacion);
		
		try{
			existeRegistro = boCatalogoAplicaciones.buscarRegistro(llamadaBO, getArchitechBean());
			if (existeRegistro){
				mensaje = "El registro ya existe.";
				codError = "MOD000";
			}
			else{
				mensaje = "Nuevo registro.";
				codError = "MOD001";
			}
			llamadaBO = null;
			llamadaBO = new BeanCatalogoAplicaciones();
			llamadaBO.setIdAplicacion(idAplicacion);
			llamadaBO.setDescripcion(descripcion);
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
		model.put(PARAM_DESCRIPCION, descripcion);
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
		debug("Consultando catalogo de aplicaciones");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));
		final HashMap<String,Object>  model   = new HashMap<String,Object>();
		BeanCatalogoAplicaciones llamadaBO = new BeanCatalogoAplicaciones();
		BeanCatalogoAplicaciones respuestaBO = new BeanCatalogoAplicaciones();
		String idAplicacion = req.getParameter(HDN_ID_APLICACION) != null ? req.getParameter(HDN_ID_APLICACION) : PARAM_VACIO;
		String descripcion = req.getParameter(HDN_DESCRIPCION) != null ? req.getParameter(HDN_DESCRIPCION) : PARAM_VACIO;
		
		llamadaBO.setIdAplicacion(idAplicacion);
		llamadaBO.setDescripcion(descripcion);
		
		try{
			respuestaBO = boCatalogoAplicaciones.consultarCatalogo(llamadaBO, getArchitechBean());
		}
		
		catch(BusinessException e){
			showException(e);;
		} catch (ExceptionDataAccess e) {
			showException(e);
		}
		
		model.put(HDN_ID_APLICACION, idAplicacion);
		model.put(HDN_DESCRIPCION, descripcion);
		model.put(PARAM_LISTA, respuestaBO.getListaBeanCatalogoAplicaciones());
		
		return model;
	}
}