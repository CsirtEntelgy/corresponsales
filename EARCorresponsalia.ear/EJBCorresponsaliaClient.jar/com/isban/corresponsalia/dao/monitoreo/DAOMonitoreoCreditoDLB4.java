package com.isban.corresponsalia.dao.monitoreo;
import java.util.List;

import javax.ejb.Local;

import com.isban.corresponsalia.beans.monitoreo.BeanMonitoreoCredito;
import com.isban.corresponsalia.beans.monitoreo.BeanRegistroMonitoreoCredito;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface DAOMonitoreoCreditoDLB4.
 */
@Local
public interface DAOMonitoreoCreditoDLB4 {
	
	/**
	 * Monitoreo credito.
	 *
	 * @param beanArq the bean arq
	 * @param beanConsulta the bean consulta
	 * @return the list
	 */
	public List<BeanRegistroMonitoreoCredito>  monitoreoCredito(ArchitechSessionBean beanArq, BeanMonitoreoCredito beanConsulta);
}
