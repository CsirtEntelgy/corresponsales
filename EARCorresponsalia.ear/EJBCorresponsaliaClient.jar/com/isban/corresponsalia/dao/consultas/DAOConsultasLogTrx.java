package com.isban.corresponsalia.dao.consultas;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.sql.DataSource;

import com.isban.corresponsalia.beans.consultas.BeanConsultaLogTrx;
import com.isban.corresponsalia.beans.consultas.BeanFiltroConsultaLogTrx;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;
import com.isban.ebe.commons.exception.ExceptionDataAccess;

/**
 * The Interface DAOAuditor.
 */
@Local
public interface DAOConsultasLogTrx {
	
	/**
	 * Consulta log trx.
	 *
	 * @param filtro the filtro
	 * @param beanArquitectura the bean arquitectura
	 * @return the array list
	 * @throws ExceptionDataAccess the exception data access
	 */
	public List<BeanConsultaLogTrx> consultaLogTrx(BeanFiltroConsultaLogTrx filtro
			, ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess ;
	
	/**
	 * Consulta numero log trx.
	 *
	 * @param filtro the filtro
	 * @param beanArquitectura the bean arquitectura
	 * @return the int
	 * @throws ExceptionDataAccess the exception data access
	 */
	public Map<String,Object> consultaGruposLogTrx(BeanFiltroConsultaLogTrx filtro
			, ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess ;
	
	/**
	 * Cosulta todos los registros de Log Transaccional.
	 * @param filtro the filtro
	 * @return retorna la lista de registros
	 * @throws ExceptionDataAccess excepcion en caso de fallo
	 */
	public List<BeanConsultaLogTrx> consultaTodoLogTrx(BeanFiltroConsultaLogTrx filtro
			, ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess;
	
	/**
	 * Arma el query para consultar todos los registros del Log Transaccional.
	 * @param filtro the filtro
	 * @return retorna la lista de registros
	 * @throws ExceptionDataAccess excepcion en caso de fallo
	 */
	public StringBuffer armaQueryTodoLogTrx(BeanFiltroConsultaLogTrx filtro
			, ArchitechSessionBean beanArquitectura) throws ExceptionDataAccess;
	
	/**
	 * Genera conexión a BD por medio de DataSource 
	 * @throws BusinessException the business exception
	 * @throws Exception the exception data access
	 */
	public DataSource getBDConnection(ArchitechSessionBean beanArq);
	
}
