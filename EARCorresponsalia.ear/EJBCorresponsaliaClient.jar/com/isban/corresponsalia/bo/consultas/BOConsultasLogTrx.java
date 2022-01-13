package com.isban.corresponsalia.bo.consultas;
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
 * The Interface BOConsultasBitacora.
 */
@Local
public interface BOConsultasLogTrx {


	/**
	 * Consulta log trx.
	 *
	 * @param beanArq the bean arq
	 * @param beanFiltro the bean filtro
	 * @return the bean consulta log trx
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	public List<BeanConsultaLogTrx> consultaLogTrx(ArchitechSessionBean beanArq, 
			BeanFiltroConsultaLogTrx beanFiltro)throws BusinessException, ExceptionDataAccess;
	
	/**
	 * Consulta num registros log trx.
	 *
	 * @param beanArq the bean arq
	 * @param beanFiltro the bean filtro
	 * @return the map
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	public Map<String,Object> consultaNumRegistrosLogTrx(ArchitechSessionBean beanArq, 
			BeanFiltroConsultaLogTrx beanFiltro)throws BusinessException, ExceptionDataAccess;
	
	/**
	 * Consulta todo log trx.
	 *
	 * @param beanArq the bean arq
	 * @param beanFiltro the bean filtro
	 * @return the list
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	List<BeanConsultaLogTrx> consultaTodoLogTrx(ArchitechSessionBean beanArq,
			BeanFiltroConsultaLogTrx beanFiltro) throws BusinessException, ExceptionDataAccess;
	
	/**
	 * Arma el query para la consulta de todo log trx.
	 *
	 * @param beanArq the bean arq
	 * @param beanFiltro the bean filtro
	 * @return the list
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	StringBuffer armaQueryTodoLogTrx(ArchitechSessionBean beanArq,
			BeanFiltroConsultaLogTrx beanFiltro) throws BusinessException, ExceptionDataAccess;
	/**
	 * Genera conexión a BD por medio de DataSource
	 * @paramam beanArq the bean arq
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	DataSource getBDConnection(ArchitechSessionBean beanArq) throws BusinessException, ExceptionDataAccess;

}
