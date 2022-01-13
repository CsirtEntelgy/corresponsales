package com.isban.corresponsalia.bo.catalogos;

import javax.ejb.Local;
import javax.sql.DataSource;

import com.isban.corresponsalia.beans.catalogos.BeanCatalogoAplicaciones;
import com.isban.ebe.commons.exception.BusinessException;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.ExceptionDataAccess;

/**
 * Stefanini® 2015
 * Interfaz de aplicacion para la llamada de los metodos del catalogo de aplicaciones
 * @author Irvin Misael Herrera Chávez
 */
@Local
public interface BOCatalogoAplicaciones {

	/**
	 * Realiza la consulta del catalogo
	 * @param bean bean de entrada de datos
	 * @param beanArquitectura bean de sesion
	 * @return BeanCatalogoAplicaciones
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	public BeanCatalogoAplicaciones consultarCatalogo(BeanCatalogoAplicaciones bean,
			ArchitechSessionBean beanArquitectura) throws BusinessException, ExceptionDataAccess;
	/**
	 * Da de alta un registro en BD
	 * @param bean bean de entrada de datos
	 * @param beanArquitectura bean de sesion
	 * @return BeanCatalogoAplicaciones
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	public BeanCatalogoAplicaciones altaRegistro(BeanCatalogoAplicaciones bean, 
			ArchitechSessionBean beanArquitectura) throws BusinessException, ExceptionDataAccess;
	/**
	 * Elimina el registro en BD
	 * @param bean bean de entrada de datos
	 * @param beanArquitectura bean de sesion
	 * @return BeanCatalogoAplicaciones
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	public BeanCatalogoAplicaciones bajaRegistro(BeanCatalogoAplicaciones bean,
			ArchitechSessionBean beanArquitectura) throws BusinessException, ExceptionDataAccess;
	/**
	 * Actualiza el registro en BD
	 * @param bean bean de entrada de datos
	 * @param beanArquitectura bean de sesion
	 * @return BeanCatalogoAplicaciones
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	public BeanCatalogoAplicaciones modificarRegistro(BeanCatalogoAplicaciones bean,
			ArchitechSessionBean beanArquitectura) throws BusinessException, ExceptionDataAccess;
	
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
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 * @return DataSource
	 */
	public DataSource getBDConnection(ArchitechSessionBean beanArq) throws BusinessException, ExceptionDataAccess;
}
