package com.isban.corresponsalia.controllers.principal;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.isban.corresponsalia.bo.sesiones.BOSesiones;
import com.isban.corresponsalia.comunes.Parametros;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

/**
 * The Class ControllerPrincipal.
 */
@Controller
public class ControllerPrincipal extends ArchitechEBE{
	/** The b o sesiones. */
	transient private BOSesiones bOSesiones;

	/**
	 * Sets the bO sesiones.
	 * 
	 * @param bOSesiones
	 *            the new bO sesiones
	 */
	public void setBOSesiones(BOSesiones bOSesiones) {
		this.bOSesiones = bOSesiones;
	}

	/**
	 * Salir.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view	
	 */
	@RequestMapping(value="salir.do")
	public ModelAndView salir(HttpServletRequest req,HttpServletResponse res) {
		final String NEW_ARCH = "NewArchitechSession";
		final String LOGOUT_SAM = "LOGOUT_SAM";
                final ArchitechSessionBean bean = (ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH);
		setArchitechBean(bean);
		debug("Saliendo de aplicación.. " + (bean == null ? "No esta " : " Si esta ") + NEW_ARCH);
		if (bean != null){
		  bOSesiones.registraSalida(bean);
                }
		req.getSession().removeAttribute(NEW_ARCH);
		req.getSession().invalidate();		
		debug("Enviando a hacer logut a SAM..."  + Parametros.getParametroAplicacion(LOGOUT_SAM));
		return new ModelAndView("redirect:" + Parametros.getParametroAplicacion(LOGOUT_SAM));
	}	

	/**
	 * Inicio.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view	
	 */
	@RequestMapping(value="inicio.do")
	public ModelAndView inicio(HttpServletRequest req,	HttpServletResponse res) {
		final HashMap<String, Object> lhsmParametros = new HashMap<String, Object>();
		String urlInicio = Parametros.getParametroAplicacion("URL_INICIO");
		if(urlInicio == null || ("".equals(urlInicio))){
			urlInicio = "inicio";
		}
		req.setAttribute("SES", "CORR"+req.getSession().getId() );
		lhsmParametros.put("SES", "CORR"+req.getSession().getId());
		lhsmParametros.put("urlInicio", urlInicio);		
		
		return new ModelAndView("inicio",lhsmParametros);
	}
			
}

