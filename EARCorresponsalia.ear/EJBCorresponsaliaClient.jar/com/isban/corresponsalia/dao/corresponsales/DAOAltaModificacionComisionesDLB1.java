package com.isban.corresponsalia.dao.corresponsales;


import javax.ejb.Local;

import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.corresponsales.BeanAltaModificaComisiones;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

/**
 * The Interface DAOAltaModificacionComisionesDLB1.
 */
@Local
public interface DAOAltaModificacionComisionesDLB1 {
	
	/**
	 * Modificacion comisiones.
	 *
	 * @param beanAltaModificacion the bean alta modificacion
	 * @param psession the psession
	 * @return the bean error
	 */
	public BeanError modificacionComisiones(BeanAltaModificaComisiones beanAltaModificacion, ArchitechSessionBean psession);
}

