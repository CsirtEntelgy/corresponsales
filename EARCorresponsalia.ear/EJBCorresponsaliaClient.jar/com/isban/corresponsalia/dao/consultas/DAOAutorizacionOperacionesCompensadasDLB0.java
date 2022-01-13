package com.isban.corresponsalia.dao.consultas;
import javax.ejb.Local;

import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface DAOAutorizacionOperacionesCompensadasDLB0.
 */
@Local
public interface DAOAutorizacionOperacionesCompensadasDLB0 {

	/**
	 * Autoriza compensacion.
	 *
	 * @param beanArq the bean arq
	 * @param beanCorresponsal the bean corresponsal
	 * @return the object
	 */
	public Object  autorizaCompensacion(ArchitechSessionBean beanArq, BeanCorresponsal beanCorresponsal);
}
