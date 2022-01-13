package com.isban.corresponsalia.dao.sesiones;
import javax.ejb.Local;

import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.ExceptionDataAccess;

// TODO: Auto-generated Javadoc
/**
 * The Interface DAOSesiones.
 */
@Local
public interface DAOSesiones {

	/**
	 * Sesion existente.
	 *
	 * @param beanArquitectura the bean arquitectura
	 * @return true, if successful
	 * @throws ExceptionDataAccess the exception data access
	 */
	public boolean sesionExistente  (ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess;
	
	/**
	 * Tiempo inactividad.
	 *
	 * @param beanArquitectura the bean arquitectura
	 * @return the int
	 */
	public int     tiempoInactividad(ArchitechSessionBean beanArquitectura);
	
	/**
	 * Crea sesion.
	 *
	 * @param beanArquitectura the bean arquitectura
	 */
	public void    creaSesion       (ArchitechSessionBean beanArquitectura);
	
	/**
	 * Actualiza session.
	 *
	 * @param beanArquitectura the bean arquitectura
	 */
	public void    actualizaSession (ArchitechSessionBean beanArquitectura);
	
	/**
	 * Sesion en otra session.
	 *
	 * @param beanArquitectura the bean arquitectura
	 * @return true, if successful
	 * @throws ExceptionDataAccess the exception data access
	 */
	public boolean    sesionEnOtraSession(ArchitechSessionBean beanArquitectura)throws ExceptionDataAccess;

}
