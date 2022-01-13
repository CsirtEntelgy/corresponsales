package com.isban.corresponsalia.bo.catalogos;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;

import com.isban.corresponsalia.beans.catalogos.BeanCatalogoAplicaciones;
import com.isban.corresponsalia.dao.auditoria.DAOAuditor;
import com.isban.corresponsalia.dao.catalogos.DAOCatalogoAplicaciones;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;
import com.isban.ebe.commons.exception.ExceptionDataAccess;

/**
 * Stefanini® 2015
 * Implementacion de aplicacion para la llamada de los metodos del catalogo de aplicaciones
 * @author Irvin Misael Herrera Chavez/Hector Javier Avila Hernandez
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOCatalogoAplicacionesImp extends ArchitechEBE implements BOCatalogoAplicaciones {

	/**
	 * la constante CORE
	 */
	private static final String CORE = "CORBAN";
	
	/**
	 * daoCatalogoAplicaciones
	 */
	@EJB
	transient private DAOCatalogoAplicaciones daoCatalogoAplicaciones;
	
	/**
	 * daoAuditor
	 */
	@EJB
	transient private DAOAuditor daoAuditor;
	
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public BeanCatalogoAplicaciones consultarCatalogo(
			BeanCatalogoAplicaciones bean, ArchitechSessionBean beanArquitectura)
			throws BusinessException, ExceptionDataAccess {

		daoAuditor.auditaoperacion("Consulta::ConsultaCatalogo", CORE, "Consulta Catalogo Aplicaciones", beanArquitectura);
		
		return daoCatalogoAplicaciones.consultarCatalogo(bean, beanArquitectura);
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BeanCatalogoAplicaciones altaRegistro(BeanCatalogoAplicaciones bean,
			ArchitechSessionBean beanArquitectura) throws BusinessException,
			ExceptionDataAccess {
		
		BeanCatalogoAplicaciones consulta = new BeanCatalogoAplicaciones();
		BeanCatalogoAplicaciones respuestaDAO = new BeanCatalogoAplicaciones();
		consulta.setIdAplicacion("");
		consulta.setDescripcion("");
		
		daoAuditor.auditaoperacion("Insertar::AltaRegistro", CORE, "Alta en Catalogo Aplicaciones", beanArquitectura);
		respuestaDAO = daoCatalogoAplicaciones.altaRegistro(bean, beanArquitectura);
		consulta = daoCatalogoAplicaciones.consultarCatalogo(consulta, beanArquitectura);
		respuestaDAO.setListaBeanCatalogoAplicaciones(consulta.getListaBeanCatalogoAplicaciones());
		
		return respuestaDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BeanCatalogoAplicaciones bajaRegistro(BeanCatalogoAplicaciones bean,
			ArchitechSessionBean beanArquitectura) throws BusinessException,
			ExceptionDataAccess {
		
		BeanCatalogoAplicaciones consulta = new BeanCatalogoAplicaciones();
		BeanCatalogoAplicaciones respuestaDAO = new BeanCatalogoAplicaciones();
		consulta.setIdAplicacion("");
		consulta.setDescripcion("");
		daoAuditor.auditaoperacion("Baja::BajaRegistro", CORE, "Baja en Catalogo Aplicaciones", beanArquitectura);
		respuestaDAO = daoCatalogoAplicaciones.bajaRegistro(bean, beanArquitectura);
		consulta = daoCatalogoAplicaciones.consultarCatalogo(consulta, beanArquitectura);
		respuestaDAO.setListaBeanCatalogoAplicaciones(consulta.getListaBeanCatalogoAplicaciones());
		
		return respuestaDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BeanCatalogoAplicaciones modificarRegistro(
			BeanCatalogoAplicaciones bean, ArchitechSessionBean beanArquitectura)
			throws BusinessException, ExceptionDataAccess {

		BeanCatalogoAplicaciones beanResponse = new BeanCatalogoAplicaciones();
		BeanCatalogoAplicaciones consulta = new BeanCatalogoAplicaciones();
		consulta.setIdAplicacion("");
		consulta.setDescripcion("");
		
		daoAuditor.auditaoperacion("Actualizar::ModificarRegistro", CORE, "Modificacion Catalogo Aplicaciones", beanArquitectura);
		
		if("00000".equals(daoCatalogoAplicaciones.modificarRegistro(bean, beanArquitectura).getErrorCode())){
			beanResponse = daoCatalogoAplicaciones.consultarCatalogo(bean, beanArquitectura);
		}else{
			debug("Ocurri� un error durante el llamado de base de datos");
		}
		consulta = daoCatalogoAplicaciones.consultarCatalogo(consulta, beanArquitectura);
		beanResponse.setListaBeanCatalogoAplicaciones(consulta.getListaBeanCatalogoAplicaciones());
		
		return beanResponse;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean buscarRegistro(BeanCatalogoAplicaciones bean,
			ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess, BusinessException {
		daoAuditor.auditaoperacion("Consulta::ConsultaCatalogo", CORE, "Consulta Catalogo Aplicaciones", beanArquitectura);
				
		return daoCatalogoAplicaciones.buscarRegistro(bean, beanArquitectura);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DataSource getBDConnection(ArchitechSessionBean beanArq)
			throws BusinessException, ExceptionDataAccess {
		
		return daoCatalogoAplicaciones.getBDConnection(beanArq);
	}

}
