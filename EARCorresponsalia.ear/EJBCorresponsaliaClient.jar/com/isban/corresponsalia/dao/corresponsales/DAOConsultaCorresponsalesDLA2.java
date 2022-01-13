package com.isban.corresponsalia.dao.corresponsales;


import javax.ejb.Local;

import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

/**
 * The Interface DAOConsultaCorresponsalesDLA2.
 */
@Local
public interface DAOConsultaCorresponsalesDLA2 {
	
	/**
	 * Consulta corresponsalias.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the object
	 */
	public Object consultaCorresponsalias(BeanMttoConsultaCorresponsal beanConsulta, ArchitechSessionBean psession);
}

