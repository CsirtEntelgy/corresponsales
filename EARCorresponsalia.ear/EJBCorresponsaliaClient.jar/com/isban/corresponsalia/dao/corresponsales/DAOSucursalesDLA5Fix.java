package com.isban.corresponsalia.dao.corresponsales;


import javax.ejb.Local;

import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.comunes.BeanSucursal;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface DAOSucursalesDLA5Fix.
 */
@Local
public interface DAOSucursalesDLA5Fix {
	
	/**
	 * Sucursales.
	 *
	 * @param beanABM the bean abm
	 * @param psession the psession
	 * @return the bean error
	 */
	public BeanError sucursales(BeanSucursal beanABM, ArchitechSessionBean psession);
}

