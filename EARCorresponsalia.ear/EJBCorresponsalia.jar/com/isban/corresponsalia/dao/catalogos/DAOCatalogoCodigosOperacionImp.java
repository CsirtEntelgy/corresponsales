package com.isban.corresponsalia.dao.catalogos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.isban.corresponsalia.beans.catalogos.BeanCatalogoCodigosOperacion;
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
 * Implementacion de aplicacion para el acceso a datos del catalogo de codigos de operacion
 * @author Irvin Misael Herrera Chavez
 */
@Stateless
@Local(DAOCatalogoCodigosOperacion.class)
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOCatalogoCodigosOperacionImp extends ArchitechEBE implements
		DAOCatalogoCodigosOperacion {
		
	/** The corbanc DB jndi lookup name*/
	transient private static final String RESCE_DB_CRBNC = "DATABASE.DB_CORRESPONSALIA_AUDITORIA.JNDI_NAME";

	/** The codoper. */
	transient private static final  String CODOPER = "AUDITOR_CORRESPONSALIA";
	
	/** The id canal. */
	transient private final String idCanal = Alias.getAlias("AUDITORIA");
	
	
	/**
	 * {@inheritDoc}
	 */
	public BeanCatalogoCodigosOperacion consultarCatalogo(
			BeanCatalogoCodigosOperacion bean,
			ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess, BusinessException{

		List<BeanCatalogoCodigosOperacion> listReturn = new ArrayList<BeanCatalogoCodigosOperacion>(); 
		
		BeanCatalogoCodigosOperacion beanResponse = new BeanCatalogoCodigosOperacion();
		
		RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		
		ResponseMessageDataBaseDTO responseDTO = null;
		
		DataAccess dataAccess = DataAccess.getInstance(requestDTO, getLoggingBean());
		
		StringBuffer queryConsulta = new StringBuffer();
		
		queryConsulta.append("SELECT * FROM EBE_CAT_CODOPER_CORE_BANC WHERE ID_APLICACION LIKE '%");
		queryConsulta.append(bean.getIdAplicacion().toString());
		queryConsulta.append("%' AND CODIGO_OPER LIKE '%");
		queryConsulta.append(bean.getCodigoOperacion());
		queryConsulta.append("%' AND CODIGO_ISO LIKE '%");
		queryConsulta.append(bean.getCodigoIso());
		queryConsulta.append("%' AND DESC_OPER LIKE '%");
		queryConsulta.append(bean.getDescripcionCodigoOperacion());
		queryConsulta.append("%' AND DESC_ISO LIKE '%");
		queryConsulta.append(bean.getDescripcionIso()).append("%'");
		
		debug("Query CONSULTA CODIGOS OPERACION :::::: " + queryConsulta.toString());
		
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY);
		requestDTO.setCodeOperation(CODOPER);
		requestDTO.setQuery(queryConsulta.toString());
		
		responseDTO = (ResponseMessageDataBaseDTO) dataAccess.execute(idCanal);
		
		final List<HashMap<String, Object>> listaResultado = responseDTO.getResultQuery();
		if (!listaResultado.isEmpty()) {
			for (HashMap<String, Object> items : listaResultado) {
				
				BeanCatalogoCodigosOperacion beanAux = new BeanCatalogoCodigosOperacion();
				
				beanAux.setIdAplicacion((String) items.get("ID_APLICACION"));
				beanAux.setCodigoOperacion((String) items.get("CODIGO_OPER"));
				beanAux.setDescripcionCodigoOperacion((String) items.get("DESC_OPER"));
				beanAux.setCodigoIso((String) items.get("CODIGO_ISO"));
				beanAux.setDescripcionIso((String) items.get("DESC_ISO"));
				
				listReturn.add(beanAux);
			}			
			beanResponse.setListaBeanCatalogoCodigosOperacion(listReturn);
			beanResponse.setErrorCode(responseDTO.getCodeError());
		}
		
		return beanResponse;
	}

	/**
	 * {@inheritDoc}
	 */
	public BeanCatalogoCodigosOperacion altaRegistro(
			BeanCatalogoCodigosOperacion bean,
			ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess, BusinessException{
		
		BeanCatalogoCodigosOperacion beanResponse = new BeanCatalogoCodigosOperacion(); 
		
		RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		
		ResponseMessageDataBaseDTO responseDTO = null;
		
		DataAccess dataAccess = DataAccess.getInstance(requestDTO, getLoggingBean());
		
		altaRegistroNuevo(bean);
		
			StringBuffer queryInserta = new StringBuffer();
			
			queryInserta.append("INSERT INTO EBE_CAT_CODOPER_CORE_BANC ");
			queryInserta.append(" (ID_APLICACION, CODIGO_OPER, DESC_OPER, CODIGO_ISO, DESC_ISO) ");
			queryInserta.append("VALUES");
			queryInserta.append(" ('").append(bean.getIdAplicacion().toString());
			queryInserta.append("', '").append(bean.getCodigoOperacion().toString());
			queryInserta.append("', '").append(bean.getDescripcionCodigoOperacion().toString());
			queryInserta.append("', '").append(bean.getCodigoIso().toString());
			queryInserta.append("', '").append(bean.getDescripcionIso().toString()).append("') ");
			
			debug("Query ALTA CODIGOS OPERACION :::::: " + queryInserta.toString());
			
			requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_INSERT);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setQuery(queryInserta.toString());
			
			responseDTO = (ResponseMessageDataBaseDTO) dataAccess.execute(idCanal);
			
			beanResponse.setErrorCode(responseDTO.getCodeError());
			beanResponse.setErrorDesc(responseDTO.getMessageError());
			
		
			beanResponse.setErrorCode("CBE002");
			beanResponse.setErrorDesc("El registro que intenta dar de alta no existe en el catalogo");
		
		
		return beanResponse;

	}

	/**
	 * {@inheritDoc}
	 */
	public BeanCatalogoCodigosOperacion bajaRegistro(
			BeanCatalogoCodigosOperacion bean,
			ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess, BusinessException{

		BeanCatalogoCodigosOperacion beanResponse = new BeanCatalogoCodigosOperacion(); 
		
		RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		
		ResponseMessageDataBaseDTO responseDTO = null;
		
		DataAccess dataAccess = DataAccess.getInstance(requestDTO, getLoggingBean());
		
		StringBuffer queryDelete = new StringBuffer();
			queryDelete.append("DELETE FROM EBE_CAT_CODOPER_CORE_BANC ");
			queryDelete.append("WHERE ID_APLICACION = '");
			queryDelete.append(bean.getIdAplicacion().toString());
			queryDelete.append('\'');
			
			debug("Query BAJA CODIGOS OPERACION :::::: " + queryDelete.toString());
			
			requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_DELETE);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setQuery(queryDelete.toString());
			
			responseDTO = (ResponseMessageDataBaseDTO) dataAccess.execute(idCanal);
	
			beanResponse.setErrorCode(responseDTO.getCodeError());
			beanResponse.setIdAplicacion(responseDTO.getCodeError());
		
		return beanResponse;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public BeanCatalogoCodigosOperacion modificarRegistro(
			BeanCatalogoCodigosOperacion bean,
			ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess, BusinessException{

		BeanCatalogoCodigosOperacion beanResponse = new BeanCatalogoCodigosOperacion(); 
		
		RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		
		ResponseMessageDataBaseDTO responseDTO = null;
		
		DataAccess dataAccess = DataAccess.getInstance(requestDTO, getLoggingBean());
		
		StringBuffer queryDelete = new StringBuffer();
		
		queryDelete.append("UPDATE EBE_CAT_CODOPER_CORE_BANC ");
		queryDelete.append("SET CODIGO_OPER = '");
		queryDelete.append(bean.getCodigoOperacion().toString());
		queryDelete.append("' , DESC_OPER = '");
		queryDelete.append(bean.getDescripcionCodigoOperacion().toString());
		queryDelete.append("' , CODIGO_ISO = '");
		queryDelete.append(bean.getCodigoIso().toString());
		queryDelete.append("' , DESC_ISO = '");
		queryDelete.append(bean.getDescripcionIso().toString());
		queryDelete.append("' WHERE ID_APLICACION = '");
		queryDelete.append(bean.getIdAplicacion().toString());
		queryDelete.append('\'');
		
		debug("Query MODIFICA CODIGOS OPERACION :::::: " + queryDelete.toString());
		
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_UPDATE);
		requestDTO.setCodeOperation(CODOPER);
		requestDTO.setQuery(queryDelete.toString());
		
		responseDTO = (ResponseMessageDataBaseDTO) dataAccess.execute(idCanal);
		
		
			beanResponse.setErrorCode(responseDTO.getCodeError());
			beanResponse.setIdAplicacion(responseDTO.getCodeError());
		
		
		return beanResponse;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean buscarRegistro(
			BeanCatalogoCodigosOperacion bean,
			ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess, BusinessException{
		
		RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		
		ResponseMessageDataBaseDTO responseDTO = null;
		
		DataAccess dataAccess = DataAccess.getInstance(requestDTO, getLoggingBean());
		
		StringBuffer queryConsulta = new StringBuffer();
		
		queryConsulta.append("SELECT ID_APLICACION FROM EBE_CAT_CODOPER_CORE_BANC WHERE ID_APLICACION = '");
		queryConsulta.append(bean.getIdAplicacion().toString());
		queryConsulta.append('\'');
		
		debug("Query CONSULTA CODIGOS OPERACION :::::: " + queryConsulta.toString());
		
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
	
	
	private void altaRegistroNuevo(BeanCatalogoCodigosOperacion bean) throws ExceptionDataAccess, BusinessException {
		
		BeanCatalogoCodigosOperacion beanResponse = new BeanCatalogoCodigosOperacion(); 
		Boolean inserto = false;
		RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		
		ResponseMessageDataBaseDTO responseDTO = null;
		
		DataAccess dataAccess = DataAccess.getInstance(requestDTO, getLoggingBean());
		
		Date fecha = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		
		String cadena = "Alta desde codigos de operacion: " + formato.format(fecha).toString();
		if(!consultarTablaExterna(bean.getIdAplicacion().toString())){
			StringBuffer queryInserta = new StringBuffer();
			
			queryInserta.append("INSERT INTO EBE_CAT_APLIC_CORE_BANC ");
			queryInserta.append(" (ID_APLICACION, DESCRIPCION) ");
			queryInserta.append("VALUES ('");
			queryInserta.append(bean.getIdAplicacion().toString());
			queryInserta.append("', '").append(cadena).append("')");
			
			debug("Query ALTA APLICACIONES :::::: " + queryInserta.toString());
			
			requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_INSERT);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setQuery(queryInserta.toString());
			
			responseDTO = (ResponseMessageDataBaseDTO) dataAccess.execute(idCanal);
			inserto = true;
		} else{
			inserto = false;
		}
		
		
	}
	
	private Boolean consultarTablaExterna(String idAplicacion) throws ExceptionDataAccess {
        
		RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		
		ResponseMessageDataBaseDTO responseDTO = null;
		
		DataAccess dataAccess = DataAccess.getInstance(requestDTO, getLoggingBean());
		
		Boolean existe = false;
		
		StringBuffer queryConsulta = new StringBuffer();
		
		queryConsulta.append("SELECT ID_APLICACION FROM EBE_CAT_APLIC_CORE_BANC WHERE ID_APLICACION = '");
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
