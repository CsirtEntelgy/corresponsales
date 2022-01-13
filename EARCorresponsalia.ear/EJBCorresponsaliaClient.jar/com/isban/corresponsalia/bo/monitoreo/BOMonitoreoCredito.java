package com.isban.corresponsalia.bo.monitoreo;

import javax.ejb.Local;

import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.beans.monitoreo.BeanMonitoreoCredito;
import com.isban.corresponsalia.beans.monitoreo.RBeanMonitoreoCredito;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;

/**
 * The Interface BOMonitoreoCredito.
 */
@Local
public interface BOMonitoreoCredito {

	/**
	 * Monitoreo credito.
	 *
	 * @param beanArq the bean arq
	 * @param beanConsulta the bean consulta
	 * @return the r bean monitoreo credito
	 * @throws BusinessException the business exception
	 */
	public RBeanMonitoreoCredito monitoreoCredito(ArchitechSessionBean beanArq, BeanMonitoreoCredito beanConsulta)throws BusinessException;
	
	/**
	 * Consulta corresponsales.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 * @throws BusinessException the business exception
	 */
	public BeanResultadoCorresponsalia consultaCorresponsales(BeanMttoConsultaCorresponsal beanConsulta, ArchitechSessionBean psession)throws BusinessException;
}
