package com.isban.corresponsalia.bo.consultas;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;

import com.isban.corresponsalia.beans.consultas.BeanConsultaLogTrx;
import com.isban.corresponsalia.beans.consultas.BeanFiltroConsultaLogTrx;
import com.isban.corresponsalia.dao.auditoria.DAOAuditor;
import com.isban.corresponsalia.dao.consultas.DAOConsultasLogTrx;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;
import com.isban.ebe.commons.exception.ExceptionDataAccess;


/**
 * Session Bean implementation class BOConsultaBitacoraImp.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOConsultasLogTrxImp extends ArchitechEBE implements
	BOConsultasLogTrx {

	
	/** The sesiones. */
	@EJB
	transient private DAOConsultasLogTrx daoLogtrx;
	
	/** The auditor. */
	@EJB
	transient private DAOAuditor auditor;


	/**
	 * Consulta log trx.
	 *
	 * @param beanArq the bean arq
	 * @param beanFiltro the bean filtro
	 * @return the bean consulta log trx
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	@Override
	public List<BeanConsultaLogTrx> consultaLogTrx(ArchitechSessionBean beanArq,
			BeanFiltroConsultaLogTrx beanFiltro) throws BusinessException, ExceptionDataAccess {
		auditor.auditaoperacion("Consulta::ConsultaLogTrx", "CORBAN", "Consulta Log Transaccional", beanArq);
		return daoLogtrx.consultaLogTrx(beanFiltro, beanArq);
	}
	/**
	 * Consulta num registros log trx.
	 *
	 * @param beanArq the bean arq
	 * @param beanFiltro the bean filtro
	 * @return the map
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	@Override
	public Map<String,Object> consultaNumRegistrosLogTrx(ArchitechSessionBean beanArq,
			BeanFiltroConsultaLogTrx beanFiltro) throws BusinessException,
			ExceptionDataAccess {
		auditor.auditaoperacion("Consulta::ConsultaLogTrx", "CORBAN", "Consulta Log Transaccional numero registros", beanArq);
		return daoLogtrx.consultaGruposLogTrx(beanFiltro, beanArq);	}
	/**
	 * Consulta todo log trx.
	 *
	 * @param beanArq the bean arq
	 * @param beanFiltro the bean filtro
	 * @return the list
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	@Override
	public List<BeanConsultaLogTrx> consultaTodoLogTrx(ArchitechSessionBean beanArq,
			BeanFiltroConsultaLogTrx beanFiltro) throws BusinessException, ExceptionDataAccess {
		auditor.auditaoperacion("Consulta::ConsultaLogTrx", "CORBAN", "Consulta Log Transaccional todos los registros", beanArq);		
		return daoLogtrx.consultaTodoLogTrx(beanFiltro, beanArq);
	}
	
	/**
	 * Arma el query para la consulta de todo log trx.
	 *
	 * @param beanArq the bean arq
	 * @param beanFiltro the bean filtro
	 * @return the list
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	@Override
	public StringBuffer armaQueryTodoLogTrx(ArchitechSessionBean beanArq,
			BeanFiltroConsultaLogTrx beanFiltro) throws BusinessException, ExceptionDataAccess {
		auditor.auditaoperacion("Consulta::ConsultaLogTrx", "CORBAN", "Consulta Log Transaccional todos los registros", beanArq);		
		return daoLogtrx.armaQueryTodoLogTrx(beanFiltro, beanArq);
	}
	/**
	 * Genera conexión a BD por medio de DataSource
	 * @paramam beanArq the bean arq
	 * @throws BusinessException the business exception
	 * @throws ExceptionDataAccess the exception data access
	 */
	public DataSource getBDConnection(ArchitechSessionBean beanArq) throws BusinessException, ExceptionDataAccess {
		return daoLogtrx.getBDConnection(beanArq);		
	}

}