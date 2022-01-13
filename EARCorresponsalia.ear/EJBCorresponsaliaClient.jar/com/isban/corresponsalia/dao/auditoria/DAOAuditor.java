package com.isban.corresponsalia.dao.auditoria;
import javax.ejb.Local;

import com.isban.ebe.commons.beans.ArchitechSessionBean;


/**
 * The Interface DAOAuditor.
 */
@Local
public interface DAOAuditor {
	
	/**
	 * Auditaoperacion.
	 *
	 * @param pstrOperacion the pstr operacion
	 * @param pstrCanalAplicacio the pstr canal aplicacio
	 * @param pstrDescripcion the pstr descripcion
	 * @param beanArquitectura the bean arquitectura
	 */
	public void auditaoperacion(String pstrOperacion,String pstrCanalAplicacio, String pstrDescripcion, ArchitechSessionBean beanArquitectura);
}
