package com.isban.corresponsalia.dao.corresponsales;


import javax.ejb.Local;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanABMMantenimientoCorresponsal;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

/**
 * The Interface DAOABMCorresponsalesDLA1.
 */
@Local
public interface DAOABMCorresponsalesDLA1 {
	
	/**
	 * ABM corresponsalias.
	 *
	 * @param beanABM the bean abm
	 * @param psession the psession
	 * @return the object
	 */
	public Object aBMCorresponsalias(BeanABMMantenimientoCorresponsal beanABM, ArchitechSessionBean psession);
}
