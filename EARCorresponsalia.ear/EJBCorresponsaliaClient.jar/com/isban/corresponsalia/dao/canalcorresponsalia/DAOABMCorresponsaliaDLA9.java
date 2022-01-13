package com.isban.corresponsalia.dao.canalcorresponsalia;


import javax.ejb.Local;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanABMMantenimientoOperCorresponsal;
import com.isban.ebe.commons.beans.ArchitechSessionBean;


/**
 * The Interface DAOABMCorresponsaliaDLA9.
 */
@Local
public interface DAOABMCorresponsaliaDLA9 {
	
	/**
	 * ABM corresponsalias.
	 *
	 * @param beanABM the bean abm
	 * @param psession the psession
	 * @return the object
	 */
	public Object aBMCorresponsalias(BeanABMMantenimientoOperCorresponsal beanABM, ArchitechSessionBean psession);
}

