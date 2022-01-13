package com.isban.corresponsalia.dao.consultas;
import javax.ejb.Local;

import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface DAOConsultaNoCompenzadasDLB7.
 */
@Local
public interface DAOConsultaNoCompenzadasDLB7 {
	
	/**
	 * Consulta no compensacion.
	 *
	 * @param beanArq the bean arq
	 * @param beanCorresponsal the bean corresponsal
	 * @param pstrAvanzaRetrocede the pstr avanza retrocede
	 * @param pstrFechaInferior the pstr fecha inferior
	 * @param pstrFechaSuperior the pstr fecha superior
	 * @return the object
	 */
	public Object  consultaNoCompensacion(ArchitechSessionBean beanArq, BeanCorresponsal beanCorresponsal, String pstrAvanzaRetrocede, String pstrFechaInferior, String pstrFechaSuperior);
}
