package com.isban.corresponsalia.dao.monitoreo;

import javax.ejb.Local;

import com.isban.corresponsalia.beans.monitoreo.BeanConsultaMonitoreoOperaciones;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface DAOMonitoreoOperacionesDLB3.
 */
@Local
public interface DAOMonitoreoOperacionesDLB3 {
	
	/**
	 * Monitoreo operaciones.
	 *
	 * @param beanConsulta the bean consulta
	 * @param beanArq the bean arq
	 * @return the object
	 */
	public Object monitoreoOperaciones(BeanConsultaMonitoreoOperaciones beanConsulta, ArchitechSessionBean beanArq);
}
