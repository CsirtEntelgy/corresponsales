package com.isban.corresponsalia.bo.monitoreo;
import javax.ejb.Local;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCatalogo;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanResultadoOperacionesCatalogo;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoSucursal;
import com.isban.corresponsalia.beans.monitoreo.BeanConsultaMonitoreoOperaciones;
import com.isban.corresponsalia.beans.monitoreo.BeanResultadoMonitoreoOperaciones;
import com.isban.corresponsalia.beans.monitoreo.RBeanOperacionesSucursalesMonitoreoOperaciones;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;

// TODO: Auto-generated Javadoc
/**
 * The Interface BOMonitoreoOperaciones.
 */
@Local
public interface BOMonitoreoOperaciones {
	
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
	 * Consulta operaciones catalogo.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado operaciones catalogo
	 * @throws BusinessException the business exception
	 */
	public BeanResultadoOperacionesCatalogo consultaOperacionesCatalogo(BeanConsultaOperacionesCatalogo beanConsulta, ArchitechSessionBean psession)throws BusinessException;
	
	/**
	 * Consulta sucursales.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado sucursal
	 * @throws BusinessException the business exception
	 */
	public BeanResultadoSucursal consultaSucursales(BeanConsultaSucursal beanConsulta, ArchitechSessionBean psession)throws BusinessException;
	
	/**
	 * Consulta operaciones sucursales.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the r bean operaciones sucursales monitoreo operaciones
	 * @throws BusinessException the business exception
	 */
	public RBeanOperacionesSucursalesMonitoreoOperaciones consultaOperacionesSucursales(BeanConsultaMonitoreoOperaciones beanConsulta, ArchitechSessionBean psession)throws BusinessException;
	
	/**
	 * Consulta monitoreo opearciones.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado monitoreo operaciones
	 * @throws BusinessException the business exception
	 */
	public BeanResultadoMonitoreoOperaciones consultaMonitoreoOpearciones (BeanConsultaMonitoreoOperaciones beanConsulta, ArchitechSessionBean psession)throws BusinessException;
}
