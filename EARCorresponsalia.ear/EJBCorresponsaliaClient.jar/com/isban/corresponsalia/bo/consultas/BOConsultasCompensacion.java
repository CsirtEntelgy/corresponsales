package com.isban.corresponsalia.bo.consultas;
import javax.ejb.Local;

import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.consultas.RBeanAutorizaCompensacion;
import com.isban.corresponsalia.beans.consultas.RBeanConsultaNoCompensacion;
import com.isban.corresponsalia.beans.consultas.RBeanCorresponsalesConsultaCompensacion;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;

// TODO: Auto-generated Javadoc
/**
 * The Interface BOConsultasCompensacion.
 */
@Local
public interface BOConsultasCompensacion {

	/**
	 * Autoriza compensacion.
	 *
	 * @param beanArq the bean arq
	 * @param beanCorresponsal the bean corresponsal
	 * @return the r bean autoriza compensacion
	 * @throws BusinessException the business exception
	 */
	public RBeanAutorizaCompensacion autorizaCompensacion(ArchitechSessionBean beanArq, BeanCorresponsal beanCorresponsal)throws BusinessException;
    
    /**
     * Consulta no compensacion.
     *
     * @param beanArq the bean arq
     * @param beanCorresponsal the bean corresponsal
     * @param pstrAvanzaRetrocede the pstr avanza retrocede
     * @param pstrFechaInferior the pstr fecha inferior
     * @param pstrFechaSuperior the pstr fecha superior
     * @return the r bean consulta no compensacion
     * @throws BusinessException the business exception
     */
    public RBeanConsultaNoCompensacion consultaNoCompensacion(ArchitechSessionBean beanArq, BeanCorresponsal beanCorresponsal, String pstrAvanzaRetrocede, String pstrFechaInferior, String pstrFechaSuperior )throws BusinessException;
	
	/**
	 * Consulta corresponsales.
	 *
	 * @param beanArq the bean arq
	 * @return the r bean corresponsales consulta compensacion
	 * @throws BusinessException the business exception
	 */
	public RBeanCorresponsalesConsultaCompensacion consultaCorresponsales(ArchitechSessionBean beanArq)throws BusinessException;
}
