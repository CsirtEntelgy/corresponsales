package com.isban.corresponsalia.controllers.publico;

import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.isban.corresponsalia.bo.sesiones.BOSesiones;
import com.isban.corresponsalia.comunes.Parametros;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

import com.isban.ebe.commons.beans.LookAndFeelEBE;
import com.isban.ebe.commons.exception.BusinessException;
import com.isban.ebe.commons.exception.ExceptionDataAccess;

/**
 * The Class ControllerPublico.
 */
@Controller
public class ControllerPublico extends ArchitechEBE {

	/** The Constant NEW_ARCH. */
	private static final String NEW_ARCH = "NewArchitechSession";

	/** The Constant IV_USER. */
	private static final String IV_USER = "iv-user";
	/** The b o sesiones. */
	transient private BOSesiones bOSesiones;

	/**
	 * Instantiates a new controller publico.
	 */
	public ControllerPublico() {
		this.debug("init");
	}

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
	 * Login.
	 * 
	 * @param req
	 *            the req
	 * @param res
	 *            the res
	 * @return the model and view
	 */
	@RequestMapping(value = "login.go",method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest req, HttpServletResponse res) {
		ArchitechSessionBean lobjArchitechBean = null;
		String idUsuario = "";
		final String lstrPerfilesSAM = Parametros
				.getParametroAplicacion("PERFILES_SAM");
		final String lstrURLIrInicio = Parametros
				.getParametroAplicacion("URL_HOME");
		final String lstrIdAplic = getConfigParam("IdConfigApp");
		final String lstrNombreApp = getConfigParam("nombreAplicacion");
		final String lstrIdServidor = getConfigParam("servidor");
		final String lstrIdRefApp = getConfigParam("refIdApp");
		
		debug("Haciendo Login...");
		req.getSession().removeAttribute(NEW_ARCH);
		debug("URL_INICIO    :" + lstrURLIrInicio);
		debug("Perfiles SAM:" + lstrPerfilesSAM);
		idUsuario = validaSAM(req, lstrPerfilesSAM);
		if (idUsuario!= null && ! "".equals(idUsuario)) {
			final HashMap<String, Object> lhsmParametros = new HashMap<String, Object>();	
			final String lstrIpCliente = req.getHeader("iv-remote-address") == null ? req
					.getRemoteAddr()
					: req.getHeader("iv-remote-address");

			lobjArchitechBean = new ArchitechSessionBean();
			lobjArchitechBean.setUsuario(idUsuario);
			lobjArchitechBean.setPerfil(lstrPerfilesSAM);
			lobjArchitechBean.setIPServidor(((HttpServletRequest) req)
					.getLocalAddr());
			lobjArchitechBean.setIdApp(lstrIdAplic);
			lobjArchitechBean.setIPCliente(lstrIpCliente);
			lobjArchitechBean.setNombreServidor(lstrIdServidor);
			lobjArchitechBean.setIdRefApp(lstrIdRefApp);
			lobjArchitechBean.setNombreAplicacion(lstrNombreApp);
			lobjArchitechBean.setHoraAcceso("");
			lobjArchitechBean.setFechaAcceso("");
			lobjArchitechBean.setIdSesion(req.getSession().getId());

			req.getSession().setAttribute(NEW_ARCH, lobjArchitechBean);

			if (validaSesionUnica(lobjArchitechBean)) {
				final LookAndFeelEBE lobjLyFBean = new LookAndFeelEBE();
				final SimpleDateFormat Sfecha = new SimpleDateFormat(
						"EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
				lobjLyFBean.setArchitechBean(lobjArchitechBean);
				lobjLyFBean.showInfoLyF();
				req.getSession().setAttribute("Fecha_ES",
						Sfecha.format(java.util.Calendar.getInstance()
								.getTime()));
				req.getSession().setAttribute("LyFBean", lobjLyFBean);
				req.getSession().setAttribute(NEW_ARCH, lobjArchitechBean);
				req.getSession().setAttribute("ArchitechSession",
						lobjArchitechBean);
				setArchitechBean(lobjArchitechBean);
				req.setAttribute("SES", "CORR" + req.getSession().getId());
				lhsmParametros.put("SES", "CORR" + req.getSession().getId());
				return new ModelAndView("forward:" + "/principal/inicio.do",
						lhsmParametros);
			} else {
				String lstrUsuarioApp = req.getHeader(IV_USER) == null ? ""
						: req.getHeader(IV_USER);
				req.getSession().removeAttribute(NEW_ARCH);
				if (lstrUsuarioApp.charAt(0) == 'Y') {
					lstrUsuarioApp = lstrUsuarioApp.substring(1, lstrUsuarioApp
							.length());
				}
				lhsmParametros.put("usuarioSesionado", lstrUsuarioApp);
				return new ModelAndView("sesionExistente", lhsmParametros);
			}
		} else {
			return new ModelAndView("accesoDenegado");
		}
	}

	/**
	 * Denegar acceso.
	 * 
	 * @param req
	 *            the req
	 * @param res
	 *            the res
	 * @return the model and view
	 */
	@RequestMapping(value = "denegarAcceso.go")
	public ModelAndView denegarAcceso(HttpServletRequest req,
			HttpServletResponse res) {
		debug("Acceso no permitido...");

		return new ModelAndView("accesoDenegado");
	}

	/**
	 * Inicio.
	 * 
	 * @param req
	 *            the req
	 * @param res
	 *            the res
	 * @return the model and view
	 */
	@RequestMapping(value = "inicio.go")
	public ModelAndView inicio(HttpServletRequest req, HttpServletResponse res) {
		debug("Reenviando al inicio...");
		req.setAttribute("SESION", Boolean.valueOf(true));
		final String lstrURLIrInicio = Parametros
				.getParametroAplicacion("URL_HOME");
		return new ModelAndView("redirect:" + lstrURLIrInicio);
	}

	/**
	 * Valida sesion unica.
	 * 
	 * @param archBean
	 *            the arch bean
	 * @return true, if successful
	 */
	private boolean validaSesionUnica(ArchitechSessionBean archBean) {
		debug("Valida sesion unica...");
		boolean bolSesionOK = false;
		final String lstrSesionUnicaHabilitada = Parametros
				.getParametroAplicacion("SESION_UNICA");

		debug("Sesion unica habilitada:" + lstrSesionUnicaHabilitada);

		if (Boolean.parseBoolean(lstrSesionUnicaHabilitada)) {
			debug("Validando sesion unica...");

			try {
				try {
					bolSesionOK = bOSesiones.validaSesionUsuario(archBean);
				} catch (ExceptionDataAccess e) {
					showException(e);
				}
			} catch (BusinessException e) {
				showException(e);
			}
		} else {
			debug("La sesion unica no esta habilitada por lo cual se permite la sesion multiple...");
			bolSesionOK = true;
		}
		return bolSesionOK;
	}

	/**
	 * Valida sam.
	 * 
	 * @param req
	 *            the req
	 * @param gstrGrupoApp
	 *            the gstr grupo app
	 * @return true, if successful
	 */
	@SuppressWarnings("unchecked")
	private String validaSAM(HttpServletRequest req, String gstrGrupoApp) {
		debug("validaSAM...");

		final Enumeration<String> enumHeders = req.getHeaderNames();
		String lstrUsuarioApp = "";
		String lstrPerfil = "";
		String lstrIpCliente = "";

		while (enumHeders.hasMoreElements()) {
			final String lstrNameHeader = enumHeders.nextElement();
			debug("HeaderName:" + lstrNameHeader);
			debug("Contenido:" + req.getHeader(lstrNameHeader));
		}

		boolean lbolSAMOK = false;
		final String lstrGrupo = /*"grp_coba_mantenimiento"; /*/req
				.getHeader("iv-groups"); //*/
		debug("Grupos Usuario   :" + lstrGrupo);
		debug("Grupos Aplicacion:" + gstrGrupoApp);

		if (lstrGrupo == null || "".equals(lstrGrupo)) {
			debug("La aplicación no viene asegurada por SAM...");
			lbolSAMOK = false;
		} else if (gstrGrupoApp == null) {
			debug("No hay grupos defenidos en la aplicacion para validar...");
			lbolSAMOK = true;
		} else {
			debug("Validando grupos usuario vs grupos aplicacion...");
			final String lstrGruposUsuario[] = lstrGrupo.split(",");
			final String ltsrGruposApp[] = gstrGrupoApp.split(",");

			if (ltsrGruposApp == null) {
				debug("La aplicacion no tiene configurado ningun grupo, por tal motivo no se validan los grupos del usuario");
				lbolSAMOK = true;
			} else if (ltsrGruposApp.length > 1 && lstrGruposUsuario == null) {
				debug("La aplicacion valida al menos un grupo y el usuario no cuenta con ningun grupo");
				lbolSAMOK = false;
			} else {
				debug("");
				for (int iGrupoApp = 0; (iGrupoApp < ltsrGruposApp.length)
						&& !lbolSAMOK; iGrupoApp++) {
					final String lstrGrupoApp = ltsrGruposApp[iGrupoApp];
					for (int iGrupoUsr = 0; iGrupoUsr < lstrGruposUsuario.length; iGrupoUsr++) {
						final String lstrGrupoUsuario = lstrGruposUsuario[iGrupoUsr]
								.replaceAll("\"", "");
						debug("Grupo usuario[" + lstrGrupoUsuario
								+ "] vs Grupo Aplicacion[" + lstrGrupoApp + "]");
						if (lstrGrupoApp.equals(lstrGrupoUsuario)) {
							lbolSAMOK = true;
							lstrPerfil = lstrGrupoApp;

						}
					}
				}
				debug("");
			}
		}

		if (lbolSAMOK) {
			debug("validacion SAM OK...");
			lstrUsuarioApp = /*"RAM"+Math.random(); /*/req.getHeader(IV_USER) == null ? ""
					: req.getHeader(IV_USER); //*/;
			// lstrUsuarioApp = lstrUsuarioApp.substring(0,10);
			if (lstrUsuarioApp.charAt(0) == 'Y') {
				lstrUsuarioApp = lstrUsuarioApp.substring(1, lstrUsuarioApp
						.length());
			}
			debug("Usuario :" + lstrUsuarioApp);
			debug("Perfil  :" + lstrPerfil);
			debug("IP      :" + lstrIpCliente);
		} else {
			debug("validacion SAM fallo...");
		}
		return lstrUsuarioApp;
	}

	/**
	 * Resolve exception.
	 * 
	 * @param request the request
	 * @param response the response
	 * @param handler
	 *            the handler
	 * @param ex
	 *            the ex
	 * @return the model and view
	 */
	@ExceptionHandler
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		debug("°°Sucedio un error inesperado...");
		debug("°°Origen      :" + request.getRequestURL());
		debug("°°HandlerError:" + handler.getClass().getName());
		showException(ex);
		final String lstrPaginaException = "errorPublico";
		debug("Pagina destino:" + lstrPaginaException);
		return new ModelAndView(lstrPaginaException);

	}

}
