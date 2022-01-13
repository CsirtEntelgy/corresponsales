package com.isban.corresponsalia.dao.corresponsales;


import javax.ejb.Local;

import com.isban.corresponsalia.beans.corresponsales.BeanConsultaSucursal;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface DAOConsultaSucursalDLA6Fix.
 */
@Local
public interface DAOConsultaSucursalDLA6Fix{
	
	/**
	 * Consulta sucursales.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the object
	 */
	public Object consultaSucursales(BeanConsultaSucursal beanConsulta, ArchitechSessionBean psession);
}

