package com.isban.corresponsalia.dao.consultas;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.isban.corresponsalia.beans.consultas.BeanConsultaLogTrx;
import com.isban.corresponsalia.beans.consultas.BeanFiltroConsultaLogTrx;
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
 * Session Bean implementation class DAOAuditorImp.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOConsultasLogTrxImp extends ArchitechEBE implements
		DAOConsultasLogTrx {


	/** The codoper. */
	transient private static final  String CODOPER = "AUDITOR_CORRESPONSALIA";
	/** The id canal. */
	transient private final String idCanal = Alias.getAlias("AUDITORIA");
	/** The corbanc DB jndi lookup name*/
	final String RESCE_DB_CRBNC = "DATABASE.DB_CORRESPONSALIA_AUDITORIA.JNDI_NAME";

	/**
	 * Consulta log trx.
	 * 
	 * @param filtro
	 *            the filtro
	 * @param beanArquitectura
	 *            the bean arquitectura
	 * @return the array list
	 * @throws ExceptionDataAccess
	 *             the exception data access
	 */
	@Override
	public List<BeanConsultaLogTrx> consultaLogTrx(
			BeanFiltroConsultaLogTrx filtro,
			ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess {
		final List<BeanConsultaLogTrx> regresaLista = new ArrayList<BeanConsultaLogTrx>();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		ResponseMessageDataBaseDTO responseDTO = null;
		final DataAccess dataAccess = DataAccess.getInstance(requestDTO,
				getLoggingBean());
		final StringBuffer cadenaWhere = new StringBuffer();
		// log transaccional

		final StringBuffer querySesion = new StringBuffer(
				"select RN, ID, TIPO, FCH_INICIO, FCH_FIN, NUM_TARJ,"
						+ "NUM_CUENTA, TO_CHAR(IMPORTE/100,'999999999990.99')IMPORTE, DURACION, COD_ERROR, RESULTADO, DESC_ERROR"
						+ " from ("
						+ "SELECT ROWNUM RN, ID, TIPO, FCH_INICIO, FCH_FIN, NUM_TARJ,"
						+ "NUM_CUENTA, IMPORTE, DURACION, COD_ERROR, RESULTADO, DESC_ERROR "
						+ "FROM LOGTRAN ");
		// parametros para filtrado
		cadenaWhere.append(armaFiltro(filtro));
		final int limiteInferior = filtro.getRenglonesPorPagina()
				* (filtro.getPagina() - 1);
		final int limiteSuperior = filtro.getRenglonesPorPagina()
				* filtro.getPagina();
		querySesion.append(cadenaWhere);
		querySesion.append(")where  ");
		querySesion.append(" RN between " + limiteInferior + " and "
				+ limiteSuperior);

		debug("Query Armado:" + querySesion);
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY);
		requestDTO.setCodeOperation(CODOPER);
		requestDTO.setQuery(querySesion.toString());

		responseDTO = (ResponseMessageDataBaseDTO) dataAccess.execute(idCanal);
		debug("Codigo de Error     :" + responseDTO.getCodeError());
		debug("Mensaje de Error    :" + responseDTO.getMessageError());
		final List<HashMap<String, Object>> listaResultado = responseDTO
				.getResultQuery();
		if (!listaResultado.isEmpty()) {
			for (HashMap<String, Object> items : listaResultado) {
				final BeanConsultaLogTrx beanConsultaTrx = new BeanConsultaLogTrx();
				beanConsultaTrx.setFolio((BigDecimal) items.get("ID"));
				beanConsultaTrx.setDuracion((BigDecimal) items.get("DURACION"));
				beanConsultaTrx
						.setFechaFin((Timestamp) items.get("FCH_INICIO"));
				beanConsultaTrx
						.setFechaInicio((Timestamp) items.get("FCH_FIN"));
				beanConsultaTrx.setImporte((String) items.get("IMPORTE"));
				beanConsultaTrx.setNumeroCuenta((String) items
						.get("NUM_CUENTA"));
				beanConsultaTrx
						.setNumeroTarjeta((String) items.get("NUM_TARJ"));
				beanConsultaTrx.setResultado((BigDecimal) items
						.get("RESULTADO"));
				beanConsultaTrx.setTipo((BigDecimal) items.get("TIPO"));
				beanConsultaTrx.setCodigoError((String) items.get("COD_ERROR"));
				beanConsultaTrx.setDescripcionError((String) items.get("DESC_ERROR"));
				regresaLista.add(beanConsultaTrx);
			}

		}
		return regresaLista;
	}

	/**
	 * Consulta numero log trx.
	 * 
	 * @param filtro
	 *            the filtro
	 * @param beanArquitectura
	 *            the bean arquitectura
	 * @return the int
	 * @throws ExceptionDataAccess
	 *             the exception data access
	 */
	@Override
	public Map<String,Object> consultaGruposLogTrx(BeanFiltroConsultaLogTrx filtro,
			ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess {
		final HashMap<String, Object> respuesta = new HashMap<String,Object>();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		ResponseMessageDataBaseDTO responseDTO = null;
		final DataAccess dataAccess = DataAccess.getInstance(requestDTO,
				getLoggingBean());
		final StringBuffer cadenaWhere = new StringBuffer();
		// log transaccional

		final StringBuffer querySesion = new StringBuffer(
				"SELECT count(ID) as NUM_RENGLONES, to_char(NVL(sum (IMPORTE/100),0),'999999999990.99') AS TOTAL_IMPORTE " + "FROM LOGTRAN ");
		// parametros para filtrado
		cadenaWhere.append(armaFiltro(filtro));
		querySesion.append(cadenaWhere);
		debug("Query Armado:" + querySesion);
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY);
		requestDTO.setCodeOperation(CODOPER);
		requestDTO.setQuery(querySesion.toString());

		responseDTO = (ResponseMessageDataBaseDTO) dataAccess.execute(idCanal);
		debug("Codigo de Error     :" + responseDTO.getCodeError());
		debug("Mensaje de Error    :" + responseDTO.getMessageError());
		final List<HashMap<String, Object>> listaResultado = responseDTO
				.getResultQuery();
		if (!listaResultado.isEmpty()) {
			for (Map<String, Object> items : listaResultado) {
				respuesta.put("NUM_RENGLONES",(BigDecimal) items.get("NUM_RENGLONES"));
				respuesta.put("TOTAL_IMPORTE",(String) items.get("TOTAL_IMPORTE"));
			}
		}
		return respuesta;
	}

	/**
	 * Cosulta todos los registros de Log Transaccional.
	 * 
	 * @param filtro
	 *            the filtro
	 * @return retorna la lista de registros
	 * @throws ExceptionDataAccess
	 *             excepcion en caso de fallo
	 */
	@Override
	public List<BeanConsultaLogTrx> consultaTodoLogTrx(
			BeanFiltroConsultaLogTrx filtro,
			ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess {
		final List<BeanConsultaLogTrx> regresaLista = new ArrayList<BeanConsultaLogTrx>();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		ResponseMessageDataBaseDTO responseDTO = null;
		final DataAccess dataAccess = DataAccess.getInstance(requestDTO,
				getLoggingBean());
		final StringBuffer querySesion = armaQueryTodoLogTrx(filtro,beanArquitectura);
//		final StringBuffer cadenaWhere = new StringBuffer();
//		final StringBuffer querySesion = new StringBuffer(
//				"select RN, ID, TIPO, FCH_INICIO, FCH_FIN, NUM_TARJ"
//						+ ",NUM_CUENTA, TO_CHAR(IMPORTE/100,'999999999990.99') IMPORTE, DURACION, COD_ERROR, RESULTADO "
//						+ "from ("
//						+ "SELECT ROWNUM RN, ID, TIPO, FCH_INICIO, FCH_FIN, NUM_TARJ"
//						+ ", NUM_CUENTA, IMPORTE, DURACION, COD_ERROR, RESULTADO "
//						+ "FROM LOGTRAN ");
//		// parametros para filtrado
//		cadenaWhere.append(armaFiltro(filtro));
//		querySesion.append(cadenaWhere);
//		querySesion.append(')');
		debug("Query Armado:" + querySesion);
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY);
		requestDTO.setCodeOperation(CODOPER);
		requestDTO.setQuery(querySesion.toString());

		responseDTO = (ResponseMessageDataBaseDTO) dataAccess.execute(idCanal);
		debug("Codigo de Error     :" + responseDTO.getCodeError());
		debug("Mensaje de Error    :" + responseDTO.getMessageError());
		final List<HashMap<String, Object>> listaResultado = responseDTO
				.getResultQuery();
		if (!listaResultado.isEmpty()) {
			for (Map<String, Object> items : listaResultado) {
				final BeanConsultaLogTrx beanConsultaTrx = new BeanConsultaLogTrx();
				beanConsultaTrx.setFolio((BigDecimal) items.get("ID"));
				beanConsultaTrx.setDuracion((BigDecimal) items.get("DURACION"));
				beanConsultaTrx
						.setFechaFin((Timestamp) items.get("FCH_INICIO"));
				beanConsultaTrx
						.setFechaInicio((Timestamp) items.get("FCH_FIN"));
				beanConsultaTrx.setImporte((String) items.get("IMPORTE"));
				beanConsultaTrx.setNumeroCuenta((String) items
						.get("NUM_CUENTA"));
				beanConsultaTrx
						.setNumeroTarjeta((String) items.get("NUM_TARJ"));
				beanConsultaTrx.setResultado((BigDecimal) items
						.get("RESULTADO"));
				beanConsultaTrx.setTipo((BigDecimal) items.get("TIPO"));
				beanConsultaTrx.setCodigoError((String) items.get("COD_ERROR"));
				regresaLista.add(beanConsultaTrx);
			}

		}
		return regresaLista;
	}
	
	/**
	 * Arma el query para consultar todos los registros del Log Transaccional.
	 * 
	 * @param filtro
	 *            the filtro
	 * @return retorna la lista de registros
	 * @throws ExceptionDataAccess
	 *             excepcion en caso de fallo
	 */
	@Override
	public StringBuffer armaQueryTodoLogTrx(BeanFiltroConsultaLogTrx filtro, ArchitechSessionBean beanArq) throws ExceptionDataAccess {
		final StringBuffer cadenaWhere = new StringBuffer();
		final StringBuffer querySesion = new StringBuffer(
				"select ID, TIPO, FCH_INICIO, FCH_FIN, NUM_TARJ"
						+ ",NUM_CUENTA, TO_CHAR(IMPORTE/100,'999999999990.99') IMPORTE, DURACION, COD_ERROR, RESULTADO "
						+ "from ("
						+ "SELECT ID, TIPO, FCH_INICIO, FCH_FIN, NUM_TARJ"
						+ ", NUM_CUENTA, IMPORTE, DURACION, COD_ERROR, RESULTADO "
						+ "FROM LOGTRAN ");
		// parametros para filtrado
		cadenaWhere.append(armaFiltro(filtro));
		querySesion.append(cadenaWhere);
		querySesion.append(')');
		debug("Query Armado:" + querySesion);
		return querySesion;
	}

	/**
	 * Arma filtro.
	 *
	 * @param filtro the filtro
	 * @return the string
	 */
	private String armaFiltro(BeanFiltroConsultaLogTrx filtro) {
		final StringBuffer cadenaWhere = new StringBuffer();
		final String and = " AND ";
		// parametros para filtrado
		if (filtro != null) {
			if (!filtro.getFechaInicio().isEmpty()) {
				if (cadenaWhere.length() > 0) {
					cadenaWhere.append(and);
				}
				if (filtro.getFechaFinal().isEmpty()) {
					cadenaWhere.append(" FCH_INICIO >= to_date('");
					cadenaWhere.append(filtro.getFechaInicio());
					cadenaWhere.append("','dd/mm/yyyy')");
				} else {
					cadenaWhere.append(" FCH_INICIO BETWEEN to_date('");
					cadenaWhere.append( filtro.getFechaInicio());
					cadenaWhere.append(" 00:00:00','dd/mm/yyyy HH24:MI:SS') ");
					cadenaWhere.append("AND to_date('");
					cadenaWhere.append(filtro.getFechaFinal());
					cadenaWhere.append(" 23:59:59','dd/mm/yyyy HH24:MI:SS')");
				}
			}
			if (!filtro.getFolio().isEmpty()) {
				if (cadenaWhere.length() > 0) {
					cadenaWhere.append(and);
				}
				cadenaWhere.append(" ID = " );
				cadenaWhere.append(filtro.getFolio());
			}
			if (!filtro.getNumeroTarjeta().isEmpty()) {
				if (cadenaWhere.length() > 0) {
					cadenaWhere.append(and);
				}
				cadenaWhere.append(" NUM_TARJ = '" );
				cadenaWhere.append(filtro.getNumeroTarjeta());
				cadenaWhere.append("'");
			}

			if (!filtro.getStatusOperacion().isEmpty()) {
				if (cadenaWhere.length() > 0) {
					cadenaWhere.append(and);
				}
				if ("0".equalsIgnoreCase(filtro.getStatusOperacion())) {
					cadenaWhere.append(" RESULTADO = ");
					cadenaWhere.append(filtro.getStatusOperacion());
				} else {
					cadenaWhere.append(" RESULTADO <> 0 ");
				}

			}
			if (!filtro.getOperacion().isEmpty()) {
				if (cadenaWhere.length() > 0) {
					cadenaWhere.append(and);
				}
				cadenaWhere.append(" TIPO in (");
				cadenaWhere.append(filtro.getOperacion()); 
				cadenaWhere.append(")");
			}

			if (cadenaWhere.length() > 0) {
				cadenaWhere.insert(0, " WHERE ");
			}

		}
		return cadenaWhere.toString();
	}
	
	/**
	 * Genera conexión a BD por medio de DataSource 
	 * @throws BusinessException the business exception
	 * @throws Exception the exception data access
	 */
	public DataSource getBDConnection(ArchitechSessionBean beanArq) {
		
		DataSource ds = null;
		Context ctx = null;
		
		try {
			final String jndiCorB = Alias.getIsbanConf(RESCE_DB_CRBNC);
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(jndiCorB);			
		} catch (NamingException nex) {
	    	System.out.println("BusinessExcepNaming: Error al encontrar nombre JNDI");
	    	System.out.println(nex.getMessage());
	    	System.out.println(nex.getCause());
	    	ds = null;
		} catch (Exception ex) {
	    	System.out.println("BusinessException: Error al generar conexión");
	    	System.out.println(ex.getMessage());
	    	System.out.println(ex.getCause());
	    	ds = null;
		}    	
		return ds;
    }

}
