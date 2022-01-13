package com.isban.corresponsalia.bo.corresponsales;
import javax.ejb.Local;

import com.isban.corresponsalia.beans.corresponsales.BeanConsultaComision;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface BOConsultaComisiones.
 */
@Local
public interface BOConsultaComisiones {
	
	/**
	 * Consulta comisiones.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 */
	public BeanResultadoCorresponsalia ConsultaComisiones(BeanConsultaComision beanConsulta, ArchitechSessionBean psession);

}
