package com.isban.corresponsalia.dao.canalcorresponsalia;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanAltaOperacionesCatalogo;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.ebe.commons.beans.ArchitechSessionBean;


/**
 * The Interface DAOAltaOperacionesCatalogoDLC5.
 */
public interface DAOAltaOperacionesCatalogoDLC5 {
	
	/**
	 * Ata operaciones catalogo.
	 *
	 * @param beanAltaOperacionCatalogo the bean alta operacion catalogo
	 * @param psession the psession
	 * @return the bean error
	 */
	public BeanError altaOperacionesCatalogo(BeanAltaOperacionesCatalogo beanAltaOperacionCatalogo, ArchitechSessionBean psession);

}
