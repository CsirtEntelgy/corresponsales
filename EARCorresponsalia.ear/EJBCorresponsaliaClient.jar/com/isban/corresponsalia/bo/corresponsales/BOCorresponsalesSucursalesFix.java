package com.isban.corresponsalia.bo.corresponsales;
import javax.ejb.Local;

import com.isban.corresponsalia.beans.corresponsales.BeanConsultaSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoSucursal;
import com.isban.corresponsalia.beans.comunes.BeanSucursal;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;


/**
 * The Interface BOCorresponsalesSucursalesFix.
 */
@Local
public interface BOCorresponsalesSucursalesFix {
	
	/**
	 * Alta sucursal corresponsalia.
	 *
	 * @param beanAlta the bean alta
	 * @param psession the psession
	 * @return the bean resultado sucursal
	 * @throws BusinessException the business exception
	 */
	public BeanResultadoSucursal altaSucursalCorresponsalia(BeanSucursal beanAlta, ArchitechSessionBean psession)throws BusinessException;
	
	/**
	 * Consulta corresponsales.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 * @throws BusinessException the business exception
	 */
	public BeanResultadoCorresponsalia consultaCorresponsales(BeanMttoConsultaCorresponsal beanConsulta, ArchitechSessionBean psession)throws BusinessException;
	
	/**
	 * Consulta sucursal corresponsalia.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado sucursal
	 * @throws BusinessException the business exception
	 */
	public BeanResultadoSucursal consultaSucursalCorresponsalia(BeanConsultaSucursal beanConsulta, ArchitechSessionBean psession)throws BusinessException;
}
