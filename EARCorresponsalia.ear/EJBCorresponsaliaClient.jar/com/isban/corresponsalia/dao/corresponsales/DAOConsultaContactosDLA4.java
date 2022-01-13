package com.isban.corresponsalia.dao.corresponsales;

import java.util.Map;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaContactos;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

/**
 * The Interface DAOConsultaContactosDLA4.
 */
public interface DAOConsultaContactosDLA4 {

	/**
	 * Consulta contactos.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the hash map
	 */
	Map<String, Object> consultaContactos(
			BeanConsultaContactos beanConsulta,
			ArchitechSessionBean psession);


}
