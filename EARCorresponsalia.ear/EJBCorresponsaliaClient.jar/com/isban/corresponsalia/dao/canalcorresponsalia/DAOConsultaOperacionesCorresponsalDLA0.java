package com.isban.corresponsalia.dao.canalcorresponsalia;

import java.util.Map;

import javax.ejb.Local;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCorresponsal;
import com.isban.ebe.commons.beans.ArchitechSessionBean;


/**
 * The Interface DAOConsultaOperacionesCorresponsalDLA0.
 */
@Local
public interface DAOConsultaOperacionesCorresponsalDLA0 {
	
	/**
	 * Consulta operaciones.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the hash map
	 */
	public Map<String, Object> consultaOperaciones(BeanConsultaOperacionesCorresponsal beanConsulta, ArchitechSessionBean psession);
}

