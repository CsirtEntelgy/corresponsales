package com.isban.corresponsalia.bo.sesiones;
import javax.ejb.Local;

import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;
import com.isban.ebe.commons.exception.ExceptionDataAccess;

// TODO: Auto-generated Javadoc
/**
 * The Interface BOSesiones.
 */
@Local
public interface BOSesiones {

	/**
	 * Valida sesion usuario.
	 *
	 * @param beanArquitectura the bean arquitectura
	 * @return true, if successful
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	public boolean validaSesionUsuario(ArchitechSessionBean beanArquitectura)throws BusinessException, ExceptionDataAccess;
	
	/**
	 * Registra salida.
	 *
	 * @param beanArquitectura the bean arquitectura
	 */
	public void registraSalida(ArchitechSessionBean beanArquitectura);
}
