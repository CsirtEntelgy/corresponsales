package com.isban.corresponsalia.bo.sesiones;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.isban.corresponsalia.comunes.Parametros;
import com.isban.corresponsalia.dao.auditoria.DAOAuditor;
import com.isban.corresponsalia.dao.sesiones.DAOSesiones;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;
import com.isban.ebe.commons.exception.ExceptionDataAccess;

/**
 * Session Bean implementation class BOSesionesImp
 */
@Stateless
public class BOSesionesImp extends ArchitechEBE implements BOSesiones {

	/** The sesiones. */
	@EJB
	transient private DAOSesiones sesiones;

	/** The auditor. */
	@EJB
	transient private DAOAuditor auditor;

	/**
	 * Valida sesion usuario.
	 *
	 * @param beanArquitectura the bean arquitectura
	 * @return true, if successful
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	@Override
	public boolean validaSesionUsuario(ArchitechSessionBean beanArquitectura)
			throws BusinessException, ExceptionDataAccess {

		final String lstrTiempoMaximoInactividad = Parametros
				.getParametroAplicacion("TIEMPO_INACTIVIDAD");
		boolean bolSesionOk = false;
		int iTiempoMaxInactividad = 0;

		try {
			iTiempoMaxInactividad = Integer
					.parseInt(lstrTiempoMaximoInactividad);
		} catch (NumberFormatException e) {
			debug("NO fue posible obtener el tiempo maximo de inactividad de la sesion...");
		}

		debug("Usuario a validar sesion              :"
				+ beanArquitectura.getUsuario());
		debug("Tiempo maximo permitido de inactividad:"
				+ lstrTiempoMaximoInactividad);

		if (sesiones.sesionExistente(beanArquitectura)
				|| sesiones.sesionEnOtraSession(beanArquitectura)) {
			debug("Ya existe el usuario " + beanArquitectura.getUsuario()
					+ " en sesion");
			if (sesiones.tiempoInactividad(beanArquitectura) > iTiempoMaxInactividad) {
				debug("El usuario " + beanArquitectura.getUsuario()
						+ " tiene una inactividad mayor a maxima permitida...");
				bolSesionOk = false;
			}

		} else {
			bolSesionOk = true;
			auditor.auditaoperacion("Login::Inicia Session", "CORBAN", "Login",
					beanArquitectura);

			if (!sesiones.sesionEnOtraSession(beanArquitectura)) {
				/* validar usuario en cualquien sesion */
				sesiones.creaSesion(beanArquitectura);
			} else{
				bolSesionOk = false;
			}
		}
		return bolSesionOk;
	}

	/**
	 * Registra salida.
	 *
	 * @param beanArquitectura the bean arquitectura
	 */
	@Override
	public void registraSalida(ArchitechSessionBean beanArquitectura){

		debug("Usuario sale de la aplicacion:" + beanArquitectura.getUsuario());
		auditor.auditaoperacion("Login::Termina Session", "CORBAN", "Logout",
				beanArquitectura);
	}

}
