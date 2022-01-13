package com.isban.corresponsalia.dao.catalogos;

import javax.ejb.Local;
import javax.sql.DataSource;

import com.isban.corresponsalia.beans.catalogos.BeanCatalogoAplicaciones;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;
import com.isban.ebe.commons.exception.ExceptionDataAccess;

/**
 * Stefanini® 2015
 * Interfaz de aplicacion para el acceso a datos del catalogo de aplicaciones
 * @author Irvin Misael Herrera Chávez
 */
@Local
public interface DAOCatalogoAplicaciones {
	/**
	 * Devuelve los datos de la consulta
	 * @param bean bean de entradad de datos
	 * @param beanArquitectura bean de sesion
	 * @return BeanCatalogoAplicaciones
	 * @throws ExceptionDataAccess the exception data access
	 */
	public BeanCatalogoAplicaciones consultarCatalogo(BeanCatalogoAplicaciones bean,
			ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess, BusinessException;
	/**
	 * Alata en BD
	 * @param bean bean de entradad de datos
	 * @param beanArquitectura bean de sesion
	 * @return BeanCatalogoAplicaciones
	 * @throws ExceptionDataAccess the exception data access
	 */
	public BeanCatalogoAplicaciones altaRegistro(BeanCatalogoAplicaciones bean, 
			ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess, BusinessException;
	/**
	 * Baja en BD
	 * @param bean bean de entradad de datos
	 * @param beanArquitectura bean de sesion
	 * @return BeanCatalogoAplicaciones
	 * @throws ExceptionDataAccess the exception data access
	 */
	public BeanCatalogoAplicaciones bajaRegistro(BeanCatalogoAplicaciones bean,
			ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess, BusinessException;
	/**
	 * Modificacion de un registro en BD
	 * @param bean bean de entradad de datos
	 * @param beanArquitectura bean de sesion
	 * @return BeanCatalogoAplicaciones
	 * @throws ExceptionDataAccess the exception data access
	 */
	public BeanCatalogoAplicaciones modificarRegistro(BeanCatalogoAplicaciones bean,
			ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess, BusinessException;
	
	/**
	 * Verifica si la aplicacion existe
	 * @param bean bean de entrada de datos
	 * @param beanArquitectura bean de sesion
	 * @return True or false
	 * @throws ExceptionDataAccess the exception data access
	 */
	public boolean buscarRegistro(BeanCatalogoAplicaciones bean, 
			ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess, BusinessException;
	
	/**
	 * Genera conexión a BD por medio de DataSource 
	 * @param beanArq bean de sesion
	 * @return DataSource
	 */
	public DataSource getBDConnection(ArchitechSessionBean beanArq);
	
}
