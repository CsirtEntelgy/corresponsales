package com.isban.corresponsalia.bo.monitoreo;

import javax.ejb.Local;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCatalogo;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanResultadoOperacionesCatalogo;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;

/**
 * The Interface BOSucursalesCorresponsal CEC.
 */
@Local
public interface BOSucursalesCorresponsal {
	
	/**
	 * Lotes por compensar.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 * @throws BusinessException the business exception
	 */
	BeanResultadoCorresponsalia consultaCorresponsales(BeanMttoConsultaCorresponsal beanConsulta, ArchitechSessionBean psession)throws BusinessException;
	
	/**
	 * Consulta operaciones catalogo.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado operaciones catalogo
	 * @throws BusinessException the business exception
	 */
	BeanResultadoOperacionesCatalogo consultaOperacionesCatalogo(BeanConsultaOperacionesCatalogo beanConsulta, ArchitechSessionBean psession)throws BusinessException;
	
}
