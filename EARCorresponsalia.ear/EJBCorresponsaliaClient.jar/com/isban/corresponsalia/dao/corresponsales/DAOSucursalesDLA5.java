package com.isban.corresponsalia.dao.corresponsales;


import javax.ejb.Local;

import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.comunes.BeanSucursal;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface DAOSucursalesDLA5.
 */
@Local
public interface DAOSucursalesDLA5 {
	
	/**
	 * Sucursales.
	 *
	 * @param beanABM the bean abm
	 * @param psession the psession
	 * @return the bean error
	 */
	public BeanError Sucursales(BeanSucursal beanABM, ArchitechSessionBean psession);
}

