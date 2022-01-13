package com.isban.corresponsalia.bo.corresponsales;
import javax.ejb.Local;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCorresponsal;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanResultadoOperacionesCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.corresponsales.BeanAltaModificaComisiones;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaComision;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoComision;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;

// TODO: Auto-generated Javadoc
/**
 * The Interface BOCorresponsalesComisiones.
 */
@Local
public interface BOCorresponsalesComisiones {
	
	/**
	 * Consulta corresponsales.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 * @throws BusinessException the business exception
	 */
	public BeanResultadoCorresponsalia      consultaCorresponsales     (BeanMttoConsultaCorresponsal beanConsulta, ArchitechSessionBean psession)throws BusinessException;
	
	/**
	 * Consulta comisiones.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado comision
	 * @throws BusinessException the business exception
	 */
	public BeanResultadoComision            consultaComisiones         (BeanConsultaComision beanConsulta, ArchitechSessionBean psession)throws BusinessException;
	
	/**
	 * Alta modifica comisiones.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean error
	 * @throws BusinessException the business exception
	 */
	public BeanError                        altaModificaComisiones     (BeanAltaModificaComisiones beanConsulta, ArchitechSessionBean psession) throws BusinessException;	
	
	/**
	 * Consulta operaciones.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado operaciones corresponsal
	 * @throws BusinessException the business exception
	 */
	public BeanResultadoOperacionesCorresponsal consultaOperaciones		(BeanConsultaOperacionesCorresponsal beanConsulta, ArchitechSessionBean psession)throws BusinessException;
}
