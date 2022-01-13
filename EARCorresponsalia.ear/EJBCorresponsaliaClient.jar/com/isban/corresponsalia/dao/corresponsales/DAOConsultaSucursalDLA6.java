package com.isban.corresponsalia.dao.corresponsales;


import java.util.ArrayList;

import javax.ejb.Local;

import com.isban.corresponsalia.beans.corresponsales.BeanConsultaSucursal;
import com.isban.corresponsalia.beans.comunes.BeanSucursal;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface DAOConsultaSucursalDLA6.
 */
@Local
public interface DAOConsultaSucursalDLA6 {
	
	/**
	 * Consulta sucursales.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the array list
	 */
	public ArrayList<BeanSucursal> ConsultaSucursales(BeanConsultaSucursal beanConsulta, ArchitechSessionBean psession);
}

