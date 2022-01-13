package com.isban.corresponsalia.bo.catalogos;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;

import com.isban.corresponsalia.beans.catalogos.BeanCatalogoCodigosOperacion;
import com.isban.corresponsalia.dao.auditoria.DAOAuditor;
import com.isban.corresponsalia.dao.catalogos.DAOCatalogoCodigosOperacion;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;
import com.isban.ebe.commons.exception.ExceptionDataAccess;

/**
 * Stefanini® 2015
 * Implementcion de aplicacion para la llamada de los metodos del catalogo de codigos de operacion
 * @author Irvin Misael Herrera Chavez/Hector Javier Avila Hernandez
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOCatalogoCodigosOperacionImp extends ArchitechEBE implements
		BOCatalogoCodigosOperacion {
	
	/**
	 * la constante CORE
	 */
	private static final String CORE = "CORBAN";
	
	/**
	 * daoCatalogoCodigosOperacion
	 */
	@EJB
	transient private DAOCatalogoCodigosOperacion daoCatalogoCodigosOperacion;
	
	/**
	 * daoAuditor
	 */
	@EJB
	transient private DAOAuditor daoAuditor;

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public BeanCatalogoCodigosOperacion consultarCatalogo(
			BeanCatalogoCodigosOperacion bean,
			ArchitechSessionBean beanArquitectura) throws BusinessException,
			ExceptionDataAccess {
		
		daoAuditor.auditaoperacion("Consulta::ConsultaCatalogo", CORE, "Consulta Catalogo Codigos De Operacion", beanArquitectura);
		
		return daoCatalogoCodigosOperacion.consultarCatalogo(bean, beanArquitectura);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BeanCatalogoCodigosOperacion altaRegistro(
			BeanCatalogoCodigosOperacion bean,
			ArchitechSessionBean beanArquitectura) throws BusinessException,
			ExceptionDataAccess {
		
		BeanCatalogoCodigosOperacion consulta = new BeanCatalogoCodigosOperacion();
		BeanCatalogoCodigosOperacion respuestaDAO = new BeanCatalogoCodigosOperacion();
		consulta.setIdAplicacion("");
		consulta.setCodigoOperacion("");
		consulta.setDescripcionCodigoOperacion("");
		consulta.setCodigoIso("");
		consulta.setDescripcionIso("");
		
		
		daoAuditor.auditaoperacion("Insertar::AltaRegistro", CORE, "Alta en Catalogo Codigos De Operacion", beanArquitectura);
		respuestaDAO = daoCatalogoCodigosOperacion.altaRegistro(bean, beanArquitectura);
		consulta = daoCatalogoCodigosOperacion.consultarCatalogo(consulta, beanArquitectura);
		respuestaDAO.setListaBeanCatalogoCodigosOperacion(consulta.getListaBeanCatalogoCodigosOperacion());
		
		return respuestaDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BeanCatalogoCodigosOperacion bajaRegistro(
			BeanCatalogoCodigosOperacion bean,
			ArchitechSessionBean beanArquitectura) throws BusinessException,
			ExceptionDataAccess {
		
		BeanCatalogoCodigosOperacion consulta = new BeanCatalogoCodigosOperacion();
		BeanCatalogoCodigosOperacion respuestaDAO = new BeanCatalogoCodigosOperacion();
		consulta.setIdAplicacion("");
		consulta.setCodigoOperacion("");
		consulta.setDescripcionCodigoOperacion("");
		consulta.setCodigoIso("");
		consulta.setDescripcionIso("");
		
		daoAuditor.auditaoperacion("Baja::BajaRegistro", CORE, "Baja en Catalogo Codigos De Operacion", beanArquitectura);
		respuestaDAO = daoCatalogoCodigosOperacion.bajaRegistro(bean, beanArquitectura);
		consulta = daoCatalogoCodigosOperacion.consultarCatalogo(consulta, beanArquitectura);
		respuestaDAO.setListaBeanCatalogoCodigosOperacion(consulta.getListaBeanCatalogoCodigosOperacion());
		
		return respuestaDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BeanCatalogoCodigosOperacion modificarRegistro(
			BeanCatalogoCodigosOperacion bean,
			ArchitechSessionBean beanArquitectura) throws BusinessException,
			ExceptionDataAccess {
		
		BeanCatalogoCodigosOperacion beanResponse = new BeanCatalogoCodigosOperacion();
		BeanCatalogoCodigosOperacion consulta = new BeanCatalogoCodigosOperacion();
		consulta.setIdAplicacion("");
		consulta.setCodigoOperacion("");
		consulta.setDescripcionCodigoOperacion("");
		consulta.setCodigoIso("");
		consulta.setDescripcionIso("");
		
		daoAuditor.auditaoperacion("Actualizar::ModificarRegistro", CORE, "Modificacion Catalogo Codigos De Operacion", beanArquitectura);
		
		if("00000".equals(daoCatalogoCodigosOperacion.modificarRegistro(bean, beanArquitectura).getErrorCode())){
			beanResponse = daoCatalogoCodigosOperacion.consultarCatalogo(bean, beanArquitectura);
		}else{
			debug("Ocurri� un error durante el llamado de base de datos");
		}
		
		consulta = daoCatalogoCodigosOperacion.consultarCatalogo(consulta, beanArquitectura);
		beanResponse.setListaBeanCatalogoCodigosOperacion(consulta.getListaBeanCatalogoCodigosOperacion());
		
		return beanResponse;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DataSource getBDConnection(ArchitechSessionBean beanArq)
			throws BusinessException, ExceptionDataAccess {

		return daoCatalogoCodigosOperacion.getBDConnection(beanArq);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws BusinessException 
	 */
	@Override
	public boolean buscarRegistro(BeanCatalogoCodigosOperacion bean,
			ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess, BusinessException {
		daoAuditor.auditaoperacion("Consulta::ConsultaCatalogo", CORE, "Consulta Catalogo Codigos De Operacion", beanArquitectura);
		
		return daoCatalogoCodigosOperacion.buscarRegistro(bean, beanArquitectura);
	}


}
