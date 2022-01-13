package com.isban.corresponsalia.bo.consultas;
import javax.ejb.Local;

import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.consultas.BeanConsultaBitacora;
import com.isban.corresponsalia.beans.consultas.BeanRegistroConsultaBitacora;
import com.isban.corresponsalia.beans.consultas.RBeanAnulacion;
import com.isban.corresponsalia.beans.consultas.RBeanConsultaBitacora;
import com.isban.corresponsalia.beans.consultas.RBeanCorresponsalesConsultaEstatusOperacion;
import com.isban.corresponsalia.beans.consultas.RBeanOperacionesSucursalesConsultaBitacora;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;

// TODO: Auto-generated Javadoc
/**
 * The Interface BOConsultasEstatusOperacion.
 */
@Local
public interface BOConsultasEstatusOperacion {

	/**
	 * Consulta corresponsales.
	 *
	 * @param beanArq the bean arq
	 * @return the r bean corresponsales consulta bitacora
	 * @throws BusinessException the business exception
	 */
	public RBeanCorresponsalesConsultaEstatusOperacion consultaCorresponsales(ArchitechSessionBean beanArq)throws BusinessException;
	
	/**
	 * Consulta operaciones sucursales.
	 *
	 * @param beanArq the bean arq
	 * @param beanCorresponsal the bean corresponsal
	 * @return the r bean operaciones sucursales consulta bitacora
	 * @throws BusinessException the business exception
	 */
	public RBeanOperacionesSucursalesConsultaBitacora consultaOperacionesSucursales(ArchitechSessionBean beanArq, BeanCorresponsal beanCorresponsal)throws BusinessException;
	
	/**
	 * Consulta bitacora sin referencia.
	 *
	 * @param beanArq the bean arq
	 * @param beanConsulta the bean consulta
	 * @return the r bean consulta bitacora
	 * @throws BusinessException the business exception
	 */
	public RBeanConsultaBitacora consultaBitacoraSinReferencia(ArchitechSessionBean beanArq, BeanConsultaBitacora beanConsulta)throws BusinessException;
	
	/**
	 * Consulta bitacora con referencia.
	 *
	 * @param beanArq the bean arq
	 * @param beanConsulta the bean consulta
	 * @return the r bean consulta bitacora
	 * @throws BusinessException the business exception
	 */
	public RBeanConsultaBitacora consultaBitacoraConReferencia(ArchitechSessionBean beanArq, BeanConsultaBitacora beanConsulta)throws BusinessException;
	
	/**
	 * Anulacion.
	 *
	 * @param beanArq the bean arq
	 * @param beanRegistro the bean registro
	 * @return the r bean anulacion
	 * @throws BusinessException the business exception
	 */
	public RBeanAnulacion anulacion(ArchitechSessionBean beanArq, BeanRegistroConsultaBitacora beanRegistro)throws BusinessException;
}
