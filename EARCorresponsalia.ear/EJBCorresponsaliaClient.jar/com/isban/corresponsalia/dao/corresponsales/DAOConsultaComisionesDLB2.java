package com.isban.corresponsalia.dao.corresponsales;


import javax.ejb.Local;

import com.isban.corresponsalia.beans.corresponsales.BeanConsultaComision;
import com.isban.ebe.commons.beans.ArchitechSessionBean;


/**
 * The Interface DAOConsultaComisionesDLB2.
 */
@Local
public interface DAOConsultaComisionesDLB2 {
	
	/**
	 * Consulta comisiones.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the object
	 */
	public Object consultaComisiones(BeanConsultaComision beanConsulta, ArchitechSessionBean psession);
}

