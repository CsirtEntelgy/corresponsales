package com.isban.corresponsalia.dao.corresponsales;

import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.corresponsales.BeanAltaContactos;
import com.isban.ebe.commons.beans.ArchitechSessionBean;


/**
 * The Interface DAOABCContactosDLA3.
 */
public interface DAOABCContactosDLA3 {

	/**
	 * Alta contacto.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean error
	 */
	BeanError altaContacto(
			BeanAltaContactos beanConsulta,
			ArchitechSessionBean psession);

}
