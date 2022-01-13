/**
 * 
 */
package com.isban.corresponsalia.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.isban.corresponsalia.comunes.Parametros;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.beans.LookAndFeelEBE;

/**
 * @author Z016503
 * 
 */
public class FilterCorresponsalia extends ArchitechEBE implements Filter {

	// private FilterConfig gobjConfigFiltro = null;

	/**
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		info("FilterCorresponsalia destroy....");
		// gobjConfigFiltro = null;
	}

	/**
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	/**
	 * doFilter
	 * @param request Request
	 * @param response Response
	 * @param filter Filter
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filter) throws IOException, ServletException {
		final String SES = "SES";
		HttpSession lobjSession = ((HttpServletRequest) request).getSession();
		LookAndFeelEBE lobjLyFBean = (LookAndFeelEBE) lobjSession
				.getAttribute("LyFBean");
		setArchitechBean((ArchitechSessionBean) lobjSession
				.getAttribute("NewArchitechSession"));
		String lstrURLIrInicio = Parametros.getParametroAplicacion("URL_HOME");
		String lstrURLLogin = Parametros.getParametroAplicacion("URL_LOGIN");
		debug("URL_HOME          :" + lstrURLIrInicio);
		debug("URL_LOGIN:" + lstrURLLogin);
		String sesionFlag = (String) request.getAttribute(SES);
		try {
			if (((HttpServletRequest) request).getRequestURI().contains(
					"salir.do")) {
				debug("Se permite continuar con peticion...");
				filter.doFilter(request, response);
				return;

			} else {

				if (sesionFlag != null
						&& !("CORR" + lobjSession.getId())
								.equalsIgnoreCase(sesionFlag)
						&& lobjLyFBean != null) {
					((HttpServletResponse) response)
							.sendRedirect("../publico/denegarAcceso.go");
				} else {
					sesionFlag = request.getParameter(SES);
					if (sesionFlag != null
							&& ("CORR" + lobjSession.getId())
									.equalsIgnoreCase(sesionFlag)) {
						request.setAttribute(SES, "CORR" + lobjSession.getId());
					} else {
						/*
						 * try{
						 * if(!("CORR"+lobjSession.getId()).equalsIgnoreCase
						 * ((new
						 * Utils()).getParameterMultipart(SES,((HttpServletRequest
						 * ) request)))){ ((HttpServletResponse)
						 * response).sendRedirect
						 * ("../publico/denegarAcceso.go"); }
						 * }catch(FileUploadException e){
						 */
						((HttpServletResponse) response)
								.sendRedirect("../publico/denegarAcceso.go");
						// }
					}
				}
			}
			if (lobjLyFBean == null) {
				debug("No se encontro sesion reenviando a HOME...");
				((HttpServletResponse) response).sendRedirect(lstrURLLogin);
			} else {
				debug("Se permite continuar con peticion...");
				filter.doFilter(request, response);
			}
		} catch (IllegalStateException e) {
			debug("Redireccion a home");
		}
	}

	/** 
	 * init
	 * @param filterConfig Filter
	 * @throws ServletException
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		debug("FilterLogin init....");
		// gobjConfigFiltro = filterConfig;

	}

}
