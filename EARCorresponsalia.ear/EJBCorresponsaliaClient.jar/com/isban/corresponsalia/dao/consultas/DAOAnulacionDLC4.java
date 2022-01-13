package com.isban.corresponsalia.dao.consultas;
import javax.ejb.Local;

import com.isban.corresponsalia.beans.consultas.BeanRegistroAnular;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface DAOAnulacionDLC4.
 */
@Local
public interface DAOAnulacionDLC4 {
	
	/**
	 * Anular.
	 *
	 * @param beanArq the bean arq
	 * @param beanRegistroAnular the bean registro anular
	 * @return the object
	 */
	public Object anular(ArchitechSessionBean beanArq, BeanRegistroAnular beanRegistroAnular);
}
