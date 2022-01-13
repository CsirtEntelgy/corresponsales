package com.isban.corresponsalia.bo.catalogos;

import javax.ejb.Local;
import javax.sql.DataSource;

import com.isban.corresponsalia.beans.catalogos.BeanCatalogoCodigosOperacion;
import com.isban.ebe.commons.exception.BusinessException;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.ExceptionDataAccess;

/**
 * Stefanini® 2015
 * Interfaz de aplicacion para la llamada de los metodos del catalogo de codigos de operacion
 * @author Irvin Misael Herrera Chávez
 */
@Local
public interface BOCatalogoCodigosOperacion {

	/**
	 * Realiza la consulta del catalogo
	 * @param bean bean de entrada de datos
	 * @param beanArquitectura bean de sesion
	 * @return BeanCatalogoCodigosOperacion
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	public BeanCatalogoCodigosOperacion consultarCatalogo(BeanCatalogoCodigosOperacion bean,
			ArchitechSessionBean beanArquitectura) throws BusinessException, ExceptionDataAccess;
	/**
	 * Da de alta un registro en BD
	 * @param bean bean de entrada de datos
	 * @param beanArquitectura bean de sesion
	 * @return BeanCatalogoCodigosOperacion
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	public BeanCatalogoCodigosOperacion altaRegistro(BeanCatalogoCodigosOperacion bean, 
			ArchitechSessionBean beanArquitectura) throws BusinessException, ExceptionDataAccess;
	/**
	 * Elimina el registro en BD
	 * @param bean bean de entrada de datos
	 * @param beanArquitectura bean de sesion
	 * @return BeanCatalogoCodigosOperacion
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	public BeanCatalogoCodigosOperacion bajaRegistro(BeanCatalogoCodigosOperacion bean,
			ArchitechSessionBean beanArquitectura) throws BusinessException, ExceptionDataAccess;
	/**
	 * Actualiza el registro en BD
	 * @param bean bean de entrada de datos
	 * @param beanArquitectura bean de sesion
	 * @return BeanCatalogoCodigosOperacion
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	public BeanCatalogoCodigosOperacion modificarRegistro(BeanCatalogoCodigosOperacion bean,
			ArchitechSessionBean beanArquitectura) throws BusinessException, ExceptionDataAccess;
	
	/**
	 * Verifica si el registro existe en BD
	 * @param bean bean de entrada de datos
	 * @param beanArquitectra bean de sesion
	 * @return true or false
	 * @throws ExceptionDataAccess the exception data access
	 */
	public boolean buscarRegistro(BeanCatalogoCodigosOperacion bean,
			ArchitechSessionBean beanArquitectra) throws ExceptionDataAccess, BusinessException;
	
	/**
	 * Genera conexión a BD por medio de DataSource 
	 * @param beanArq bean de sesion
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 * @return DataSource
	 */
	public DataSource getBDConnection(ArchitechSessionBean beanArq) throws BusinessException, ExceptionDataAccess;
	
}
