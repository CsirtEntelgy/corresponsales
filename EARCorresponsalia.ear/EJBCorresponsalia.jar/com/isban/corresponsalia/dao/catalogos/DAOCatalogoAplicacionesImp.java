package com.isban.corresponsalia.dao.catalogos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.isban.corresponsalia.beans.catalogos.BeanCatalogoAplicaciones;
import com.isban.corresponsalia.comunes.Alias;
import com.isban.dataaccess.DataAccess;
import com.isban.dataaccess.channels.database.dto.RequestMessageDataBaseDTO;
import com.isban.dataaccess.channels.database.dto.ResponseMessageDataBaseDTO;
import com.isban.dataaccess.factories.jdbc.ConfigFactoryJDBC;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;
import com.isban.ebe.commons.exception.ExceptionDataAccess;

/**
 * Stefanini 2015
 * Implementacion de aplicacion para el acceso a datos del catalogo de aplicaciones
 * @author Irvin Misael Herrera Chavez
 */
@Stateless
@Local(DAOCatalogoAplicaciones.class)
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOCatalogoAplicacionesImp extends ArchitechEBE implements DAOCatalogoAplicaciones {

	/** The corbanc DB jndi lookup name*/
	transient private static final String RESCE_DB_CRBNC = "DATABASE.DB_CORRESPONSALIA_AUDITORIA.JNDI_NAME";
	
	/** The codoper. */
	transient private static final  String CODOPER = "AUDITOR_CORRESPONSALIA";
	
	/** The id canal. */
	transient private final String idCanal = Alias.getAlias("AUDITORIA");

	
	/**
	 * {@inheritDoc}
	 */
	public BeanCatalogoAplicaciones consultarCatalogo(
			BeanCatalogoAplicaciones bean, ArchitechSessionBean beanArquitectura)
			throws ExceptionDataAccess, BusinessException {
		
        List<BeanCatalogoAplicaciones> listReturn = new ArrayList<BeanCatalogoAplicaciones>(); 
		
        BeanCatalogoAplicaciones beanResponse = new BeanCatalogoAplicaciones();
        
		RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		
		ResponseMessageDataBaseDTO responseDTO = null;
		
		DataAccess dataAccess = DataAccess.getInstance(requestDTO, getLoggingBean());
		
		StringBuffer queryConsulta = new StringBuffer();
		
		queryConsulta.append("SELECT * FROM EBE_CAT_APLIC_CORE_BANC WHERE ID_APLICACION LIKE '%");
		queryConsulta.append(bean.getIdAplicacion().toString());
		queryConsulta.append("%' AND DESCRIPCION LIKE '%");
		queryConsulta.append(bean.getDescripcion().toString()).append("%'");
		
		debug("Query CONSULTA APLICACIONES :::::: " + queryConsulta.toString());
		
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY);
		requestDTO.setCodeOperation(CODOPER);
		requestDTO.setQuery(queryConsulta.toString());
		
		responseDTO = (ResponseMessageDataBaseDTO) dataAccess.execute(idCanal);
		
		final List<HashMap<String, Object>> listaResultado = responseDTO.getResultQuery();
		if (!listaResultado.isEmpty()) {
			for (HashMap<String, Object> items : listaResultado) {
				
				BeanCatalogoAplicaciones beanAux = new BeanCatalogoAplicaciones();
				
				beanAux.setIdAplicacion(Integer.toString(Integer.parseInt(items.get("ID_APLICACION").toString())));
				beanAux.setDescripcion((String) items.get("DESCRIPCION"));
						
				listReturn.add(beanAux);
				
			}
			
			beanResponse.setListaBeanCatalogoAplicaciones(listReturn);
			beanResponse.setErrorCode(responseDTO.getCodeError());
			beanResponse.setIdAplicacion(responseDTO.getCodeError());
			
		}
		
		return beanResponse;
	}

	/**
	 * {@inheritDoc}
	 */
	public BeanCatalogoAplicaciones altaRegistro(BeanCatalogoAplicaciones bean,
			ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess, BusinessException {
		
		BeanCatalogoAplicaciones beanResponse = new BeanCatalogoAplicaciones(); 
		
		RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		
		ResponseMessageDataBaseDTO responseDTO = null;
		
		DataAccess dataAccess = DataAccess.getInstance(requestDTO, getLoggingBean());
		
		StringBuffer queryInserta = new StringBuffer();
		
		queryInserta.append("INSERT INTO EBE_CAT_APLIC_CORE_BANC ");
		queryInserta.append(" (ID_APLICACION, DESCRIPCION) ");
		queryInserta.append("VALUES ('");
		queryInserta.append(bean.getIdAplicacion().toString());
		queryInserta.append("', '").append(bean.getDescripcion().toString().isEmpty() ? "Sin Descripcion" : bean.getDescripcion().toString()).append("')");
		
		debug("Query ALTA APLICACIONES :::::: " + queryInserta.toString());
		
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_INSERT);
		requestDTO.setCodeOperation(CODOPER);
		requestDTO.setQuery(queryInserta.toString());
		
		responseDTO = (ResponseMessageDataBaseDTO) dataAccess.execute(idCanal);
						
		return beanResponse;
	}

	/**
	 * {@inheritDoc}
	 */
	public BeanCatalogoAplicaciones bajaRegistro(BeanCatalogoAplicaciones bean,
			ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess, BusinessException {
		
		BeanCatalogoAplicaciones beanResponse = new BeanCatalogoAplicaciones(); 
		
		RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		
		ResponseMessageDataBaseDTO responseDTO = null;
		
		DataAccess dataAccess = DataAccess.getInstance(requestDTO, getLoggingBean());
		
		StringBuffer queryDelete = new StringBuffer();
		
		if(!consultarTablaExterna(bean.getIdAplicacion().toString())){
			queryDelete.append("DELETE FROM EBE_CAT_APLIC_CORE_BANC ");
			queryDelete.append("WHERE ID_APLICACION = '");
			queryDelete.append(bean.getIdAplicacion().toString());
			queryDelete.append('\'');
			
			debug("Query BAJA APLICACIONES :::::: " + queryDelete.toString());
			
			requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_DELETE);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setQuery(queryDelete.toString());
			
			responseDTO = (ResponseMessageDataBaseDTO) dataAccess.execute(idCanal);
			
			beanResponse.setErrorCode(responseDTO.getCodeError());
			beanResponse.setErrorDesc(responseDTO.getMessageError());			
		} else {
			beanResponse.setErrorCode("CBE001");
			beanResponse.setErrorDesc("No es posible eliminar un registro asociado");		
		}

		
		return beanResponse;
	}

	/**
	 * {@inheritDoc}
	 */
	public BeanCatalogoAplicaciones modificarRegistro(
			BeanCatalogoAplicaciones bean, ArchitechSessionBean beanArquitectura)
			throws ExceptionDataAccess, BusinessException {
		
		BeanCatalogoAplicaciones beanResponse = new BeanCatalogoAplicaciones(); 
		
		RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		
		ResponseMessageDataBaseDTO responseDTO = null;
		
		DataAccess dataAccess = DataAccess.getInstance(requestDTO, getLoggingBean());
		
		StringBuffer queryDelete = new StringBuffer();
		
		queryDelete.append("UPDATE EBE_CAT_APLIC_CORE_BANC ");
		queryDelete.append("SET DESCRIPCION = '");
		queryDelete.append(bean.getDescripcion().toString());
		queryDelete.append("' WHERE ID_APLICACION = '");
		queryDelete.append(bean.getIdAplicacion().toString());
		queryDelete.append('\'');
		
		debug("Query MODIFICA APLICACIONES :::::: " + queryDelete.toString());
		
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_UPDATE);
		requestDTO.setCodeOperation(CODOPER);
		requestDTO.setQuery(queryDelete.toString());
		
		responseDTO = (ResponseMessageDataBaseDTO) dataAccess.execute(idCanal);
		
			beanResponse.setErrorCode(responseDTO.getCodeError());
			beanResponse.setIdAplicacion(responseDTO.getCodeError());
		
		return beanResponse;
	}
	
	public boolean buscarRegistro(
			BeanCatalogoAplicaciones bean, ArchitechSessionBean beanArquitectura)
			throws ExceptionDataAccess, BusinessException {
        
		RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		
		ResponseMessageDataBaseDTO responseDTO = null;
		
		DataAccess dataAccess = DataAccess.getInstance(requestDTO, getLoggingBean());
		
		StringBuffer queryConsulta = new StringBuffer();
		
		queryConsulta.append("SELECT ID_APLICACION FROM EBE_CAT_APLIC_CORE_BANC WHERE ID_APLICACION = '");
		queryConsulta.append(bean.getIdAplicacion().toString());
		queryConsulta.append('\'');
		
		debug("Query CONSULTA ID :::::: " + queryConsulta.toString());
		
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY);
		requestDTO.setCodeOperation(CODOPER);
		requestDTO.setQuery(queryConsulta.toString());
		
		responseDTO = (ResponseMessageDataBaseDTO) dataAccess.execute(idCanal);
		
		final List<HashMap<String, Object>> listaResultado = responseDTO.getResultQuery();
		if (!listaResultado.isEmpty()) {
			return true;						
		}		
		
		return false;
	}


	/**
	 * {@inheritDoc}
	 */
	public DataSource getBDConnection(ArchitechSessionBean beanArq) {
		DataSource ds = null;
		Context ctx = null;
		
		try {
			final String jndiCorB = Alias.getIsbanConf(RESCE_DB_CRBNC);
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(jndiCorB);			
		} catch (NamingException nex) {
	    	debug("BusinessExcepNaming: Error al encontrar nombre JNDI");
	    	debug(nex.getMessage());
	    	debug(nex.getCause().toString());
	    	ds = null;
		}    	
		return ds;
	}
	
	
	private Boolean consultarTablaExterna(String idAplicacion) throws ExceptionDataAccess {
        
		RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		
		ResponseMessageDataBaseDTO responseDTO = null;
		
		DataAccess dataAccess = DataAccess.getInstance(requestDTO, getLoggingBean());
		
		Boolean existe = false;
		
		StringBuffer queryConsulta = new StringBuffer();
		
		queryConsulta.append("SELECT ID_APLICACION FROM EBE_CAT_CODOPER_CORE_BANC WHERE ID_APLICACION = '");
		queryConsulta.append(idAplicacion.toString());
		queryConsulta.append('\'');
		
		debug("Query CONSULTA APLICACIONES :::::: " + queryConsulta.toString());
		
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY);
		requestDTO.setCodeOperation(CODOPER);
		requestDTO.setQuery(queryConsulta.toString());
		
		responseDTO = (ResponseMessageDataBaseDTO) dataAccess.execute(idCanal);
		
		final List<HashMap<String, Object>> listaResultado = responseDTO.getResultQuery();
		if (!listaResultado.isEmpty()) {
			existe = true;
		}
		
		return existe;
	}

}
