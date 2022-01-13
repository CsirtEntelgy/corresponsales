package com.isban.corresponsalia.dao.auditoria;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.comunes.Alias;
import com.isban.dataaccess.DataAccess;
import com.isban.dataaccess.channels.database.dto.RequestMessageDataBaseDTO;
import com.isban.dataaccess.channels.database.dto.ResponseMessageDataBaseDTO;
import com.isban.dataaccess.factories.jdbc.ConfigFactoryJDBC;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.ExceptionDataAccess;

/**
 * Session Bean implementation class DAOAuditorImp
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOAuditorImp extends ArchitechEBE implements DAOAuditor {

	
	/** The Constant CODOPER. */
	final static private String CODOPER  = "AUDITOR_CORRESPONSALIA";
		
	/** The id canal. */
	final private String idCanal = Alias.getAlias("AUDITORIA");
	/**
	 * Auditaoperacion.
	 *
	 * @param pstrOperacion the pstr operacion
	 * @param pstrCanalAplicacio the pstr canal aplicacio
	 * @param pstrDescripcion the pstr descripcion
	 * @param beanArquitectura the bean arquitectura
	 */
	@Override
	public void auditaoperacion(String pstrOperacion, String pstrCanalAplicacio, 
			String pstrDescripcion, ArchitechSessionBean beanArquitectura) {

		
		try {
			final RequestMessageDataBaseDTO  requestDTO  = new RequestMessageDataBaseDTO();
			ResponseMessageDataBaseDTO responseDTO = new ResponseMessageDataBaseDTO();
			final DataAccess dataAccess = DataAccess.getInstance(requestDTO,beanArquitectura);
			String      queryAudita = "INSERT INTO CRBANC_PISTAUDI (ID_PISTA, " +
					"SERVICIO, FECHA_HR, DIR_IP, CANAL_AP, USUARIO_OP, TIPO_OPER," +
					" ID_INSTW, HOST_NAMEW,ID_SESSION) " +
					"VALUES (@ID_PISTA, @SERVICIO, @FECHA_HR, @DIR_IP, @CANAL_AP, " +
					"@USUARIO_OP, @TIPO_OPER, @ID_INSTW, @HOST_NAMEW, @SESSION )";
			String queryID     = "SELECT SQ_PISTAUDI.NEXTVAL FROM DUAL";
			
			if(pstrDescripcion != null && !"".equals(pstrDescripcion) && pstrDescripcion.length()>20){
				pstrDescripcion = pstrDescripcion.substring(0,19); 
			}
			queryAudita = queryAudita.replaceFirst("@SERVICIO"  , "'"+pstrOperacion+"'");
			queryAudita = queryAudita.replaceFirst("@FECHA_HR"  , "sysdate");
			queryAudita = queryAudita.replaceFirst("@DIR_IP"    , "'"+beanArquitectura.getIPCliente()+"'");
			queryAudita = queryAudita.replaceFirst("@CANAL_AP"  , "'"+pstrCanalAplicacio+"'");
			queryAudita = queryAudita.replaceFirst("@USUARIO_OP", "'"+beanArquitectura.getUsuario()+"'");
			queryAudita = queryAudita.replaceFirst("@TIPO_OPER" , "'"+pstrDescripcion+"'");
			queryAudita = queryAudita.replaceFirst("@ID_INSTW"  , "'"+ beanArquitectura.getIPServidor()+"'");
			queryAudita = queryAudita.replaceFirst("@HOST_NAMEW", "'"+beanArquitectura.getNombreServidor()+"'");
			queryAudita = queryAudita.replaceFirst("@SESSION", "'"+beanArquitectura.getIdSesion()+"'");
//			debug("Query Armado:" + queryAudita);
			requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setQuery(queryID);
			
			responseDTO = (ResponseMessageDataBaseDTO) dataAccess.execute(idCanal);
			requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_INSERT);
			requestDTO.setCodeOperation(CODOPER);
			queryAudita = queryAudita.replaceFirst("@ID_PISTA"  , ""+responseDTO.getResultQuery().get(0).get("NEXTVAL"));
			requestDTO.setQuery(queryAudita);			
			
			responseDTO = (ResponseMessageDataBaseDTO) dataAccess.execute(idCanal);
			} catch (ExceptionDataAccess e) {
				error(e.getMessage());
			}
		
	}

}
