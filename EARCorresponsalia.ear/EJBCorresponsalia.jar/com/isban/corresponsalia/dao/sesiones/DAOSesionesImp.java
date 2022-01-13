package com.isban.corresponsalia.dao.sesiones;

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
 * Session Bean implementation class DAOSesionesImp.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOSesionesImp extends ArchitechEBE implements DAOSesiones {

	/** The Constant QUERY_ARMADO. */
	private final static String QUERY_ARMADO = "Query Armado:";
	/** The codoper. */
	private final static String CODOPER = "SESIONES_CORBAN";
	
	/** The Constant CODIGO_ERR. */
	private final static String CODIGO_ERR = "Codigo de Error :";
	
	/** The Constant MSG_ERR. */
	private final static String MSG_ERR ="Mensaje de Error    :";
	

	/** The id canal. */
	transient private final String idCanal = Alias.getAlias("SESIONES");

	/**
	 * Actualiza session.
	 *
	 * @param beanArquitectura the bean arquitectura
	 */
	@Override
	public void actualizaSession(ArchitechSessionBean beanArquitectura) {

		debug("DAOSesiones->actualizaSession");
		try {
			final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
			ResponseMessageDataBaseDTO responseDTO = null;
			final DataAccess dataAccess = DataAccess.getInstance(requestDTO,
					getLoggingBean());
			String querySesion = "UPDATE SESIONES_CORBAN SET FECHA = SYSDATE WHERE COD_CLIENTE = @COD_CLIENTE";

			querySesion = querySesion.replaceFirst("@COD_CLIENTE", "'"
					+ beanArquitectura.getUsuario() + "'");
			debug(QUERY_ARMADO + querySesion);

			requestDTO
					.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_UPDATE);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setQuery(querySesion);

			responseDTO = (ResponseMessageDataBaseDTO) dataAccess
					.execute(idCanal);
			debug(CODIGO_ERR + responseDTO.getCodeError());
			debug(MSG_ERR + responseDTO.getMessageError());
			if ("DAE000".equals(responseDTO.getCodeError())){
				debug("La actualizacion de la sesion fue ejecutada exitosamente...");
			}
			else{
				debug("La actualizacion de la sesion no fue ejecutada exitosamente...");
			}
		} catch (ExceptionDataAccess e) {
			showException(e);
			debug("No fue posible actualizar la sesion...");
		}
	}

	/**
	 * Crea sesion.
	 *
	 * @param beanArquitectura the bean arquitectura
	 */
	@Override
	public void creaSesion(ArchitechSessionBean beanArquitectura) {
		debug("DAOSesiones->creaSesion");

		try {
			final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
			ResponseMessageDataBaseDTO responseDTO = null;
			final DataAccess dataAccess = DataAccess.getInstance(requestDTO,
					getLoggingBean());
			String querySesion = "INSERT INTO SESIONES_CORBAN (ID, COD_CLIENTE, FECHA) VALUES (@ID, @COD_CLIENTE, @FECHA)";

			querySesion = querySesion.replaceFirst("@ID", "'"
					+ beanArquitectura.getIdSesion() + "'");
			querySesion = querySesion.replaceFirst("@COD_CLIENTE", "'"
					+ beanArquitectura.getUsuario() + "'");
			querySesion = querySesion.replaceFirst("@FECHA", "sysdate");
			debug(QUERY_ARMADO + querySesion);

			requestDTO
					.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_INSERT);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setQuery(querySesion);

			responseDTO = (ResponseMessageDataBaseDTO) dataAccess
					.execute(idCanal);
			debug(CODIGO_ERR + responseDTO.getCodeError());
			debug(MSG_ERR + responseDTO.getMessageError());
			if ("DAE000".equals(responseDTO.getCodeError())){
				debug("La creacion de la sesion fue ejecutada exitosamente...");
			}
			else{
				debug("La creacion de la sesion no fue ejecutada exitosamente...");
			}

		} catch (ExceptionDataAccess e) {
			showException(e);
			debug("No fue posible crear la sesion...");
		}
	}

	/**
	 * Sesion existente.
	 *
	 * @param beanArquitectura the bean arquitectura
	 * @return true, if successful
	 * @throws ExceptionDataAccess the exception data access
	 */
	@Override
	public boolean sesionExistente(ArchitechSessionBean beanArquitectura)
			throws ExceptionDataAccess {
		debug("DAOSesiones->sesionExistente");
		debug("DAOSesiones->sesionExistente=" + beanArquitectura.getIdSesion());
		boolean bolSesionExistente = false;

		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		ResponseMessageDataBaseDTO responseDTO = null;
		final DataAccess dataAccess = DataAccess.getInstance(requestDTO, getLoggingBean());
		final String querySesion = "SELECT sesiones_corban.ID FROM SESIONES_CORBAN " +
				"inner join sessions on sessions.id = sesiones_corban.id" +
				" WHERE sesiones_corban.COD_CLIENTE = '"
				+ beanArquitectura.getUsuario()
				+ "'  and"
				+ " sesiones_corban.ID = '"
				+ beanArquitectura.getIdSesion() + "'";
		debug(QUERY_ARMADO + querySesion);
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY);
		requestDTO.setCodeOperation(CODOPER);
		requestDTO.setQuery(querySesion);

		responseDTO = (ResponseMessageDataBaseDTO) dataAccess.execute(idCanal);
		debug(CODIGO_ERR + responseDTO.getCodeError());
		debug(MSG_ERR + responseDTO.getMessageError());
		if (!responseDTO.getResultQuery().isEmpty()) {
			debug("La session esta activa ....");
		} else{
			debug("La sesion no ha iniciado...");
		}
		return bolSesionExistente;
	}
	
	/**
	 * Sesion en otra session.
	 *
	 * @param beanArquitectura the bean arquitectura
	 * @return true, if successful
	 * @throws ExceptionDataAccess the exception data access
	 */
	@Override
	public boolean sesionEnOtraSession(ArchitechSessionBean beanArquitectura)
			throws ExceptionDataAccess {
		debug("DAOSesiones->sesionExistente");
		debug("DAOSesiones->sesionExistente " + beanArquitectura.getUsuario());
		debug("DAOSesiones->sesionExistente " + beanArquitectura.getIdSesion() == null ? "vacio" : beanArquitectura.getIdSesion());
		boolean bolSesionExistente = false;

		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		ResponseMessageDataBaseDTO responseDTO = null;
		final DataAccess dataAccess = DataAccess.getInstance(requestDTO,
				getLoggingBean());
		//existe el ususario en otra session		
		final String querySesion = "SELECT sesiones_corban.ID FROM SESIONES_CORBAN " +
				" WHERE sesiones_corban.COD_CLIENTE = '"
				+ beanArquitectura.getUsuario() + "'";
		debug(QUERY_ARMADO + querySesion);
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY);
		requestDTO.setCodeOperation(CODOPER);
		requestDTO.setQuery(querySesion);

		responseDTO = (ResponseMessageDataBaseDTO) dataAccess.execute(idCanal);
		debug(CODIGO_ERR + responseDTO.getCodeError());
		debug(MSG_ERR + responseDTO.getMessageError());
		if (!responseDTO.getResultQuery().isEmpty()) {
			debug("La session esta activa ....");
			bolSesionExistente = true;
		} else{
			debug("La sesion no ha iniciado...");
		}
		return bolSesionExistente;
	}

	
	/**
	 * Tiempo inactividad.
	 *
	 * @param beanArquitectura the bean arquitectura
	 * @return the int
	 */
	@Override
	public int tiempoInactividad(ArchitechSessionBean beanArquitectura) {
		debug("DAOSesiones->tiempoInactividad");
		int iTiempoInactividad = 0;

		try {
			final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
			ResponseMessageDataBaseDTO responseDTO = null;
			final DataAccess dataAccess = DataAccess.getInstance(requestDTO,
					getLoggingBean());
			final String querySesion = "SELECT to_date( to_char(FECHA,'dd/mm/yyyy HH24:MI:SS'),'dd/mm/yyyy HH24:MI:SS') FECHA FROM SESIONES_CORBAN WHERE COD_CLIENTE = "
					+ "'" + beanArquitectura.getUsuario() + "'";
			debug(QUERY_ARMADO + querySesion);
			requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setQuery(querySesion);

			responseDTO = (ResponseMessageDataBaseDTO) dataAccess
					.execute(idCanal);			
			final java.sql.Timestamp timestamp = (java.sql.Timestamp)responseDTO.getResultQuery().get(0).get("FECHA");
			
			iTiempoInactividad = timestamp.compareTo(new java.sql.Timestamp(java.util.GregorianCalendar.getInstance().getTimeInMillis()));
			
			debug(CODIGO_ERR + responseDTO.getCodeError());
			debug(MSG_ERR + responseDTO.getMessageError());
			if ("DAE000".equals(responseDTO.getCodeError())) {
				debug("La consulta de tiempo de inactividad de la sesion fue ejecutada exitosamente...");
				debug("Resultado:" + iTiempoInactividad);

			} else{
				debug("La consulta de tiempo de inactividad de la sesion no fue ejecutada exitosamente...");
			}

		} catch (ExceptionDataAccess e) {
			// showException(e);
			debug("No fue posible obtener el tiempo de inactividad de la sesion...");
		}
		return iTiempoInactividad;
	}
	
}
