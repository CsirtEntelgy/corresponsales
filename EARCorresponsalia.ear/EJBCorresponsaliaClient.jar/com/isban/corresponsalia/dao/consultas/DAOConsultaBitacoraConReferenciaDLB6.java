package com.isban.corresponsalia.dao.consultas;
import javax.ejb.Local;

import com.isban.corresponsalia.beans.consultas.BeanConsultaBitacora;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface DAOConsultaBitacoraConReferenciaDLB6.
 */
@Local
public interface DAOConsultaBitacoraConReferenciaDLB6 {
	
	/**
	 * Consulta bitacora.
	 *
	 * @param beanArq the bean arq
	 * @param beanConsulta the bean consulta
	 * @return the object
	 */
	public Object consultaBitacora(ArchitechSessionBean beanArq, BeanConsultaBitacora beanConsulta);
}
