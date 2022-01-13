package com.isban.corresponsalia.dao.canalcorresponsalia;




import javax.ejb.Local;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCatalogo;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface DAOConsultaOperacionesCatalogoDLC6.
 */
@Local
public interface DAOConsultaOperacionesCatalogoDLC6 {
	
	/**
	 * Consulta operaciones catalogo.
	 *
	 * @param beanConsultaOperCatalogo the bean consulta oper catalogo
	 * @param psession the psession
	 * @return the object
	 */
	public Object consultaOperacionesCatalogo(BeanConsultaOperacionesCatalogo beanConsultaOperCatalogo, ArchitechSessionBean psession);

}

