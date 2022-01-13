package com.isban.corresponsalia.bo.corresponsales;
import javax.ejb.Local;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanABMMantenimientoCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaPreAlta;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;

// TODO: Auto-generated Javadoc
/**
 * The Interface BOCorresponsalesMantenimiento.
 */
@Local
public interface BOCorresponsalesMantenimiento {
	
	/**
	 * Consulta mantenimiento corresponsalia.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 * @throws BusinessException the business exception
	 */
	public BeanResultadoCorresponsalia consultaMantenimientoCorresponsalia    (BeanMttoConsultaCorresponsal beanConsulta, ArchitechSessionBean psession)throws BusinessException;
	
	/**
	 * Alta mantenimiento corresponsalia.
	 *
	 * @param beanAlta the bean alta
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 * @throws BusinessException the business exception
	 */
	public BeanResultadoCorresponsalia altaMantenimientoCorresponsalia        (BeanABMMantenimientoCorresponsal beanAlta, ArchitechSessionBean psession)throws BusinessException;
	
	/**
	 * Modificacion mantenimiento corresponsalia.
	 *
	 * @param beanModificacion the bean modificacion
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 * @throws BusinessException the business exception
	 */
	public BeanResultadoCorresponsalia modificacionMantenimientoCorresponsalia(BeanABMMantenimientoCorresponsal beanModificacion, ArchitechSessionBean psession)throws BusinessException;
	
	/**
	 * Consulta pre alta corresponsal.
	 *
	 * @param preAlta the pre alta
	 * @param psession the psession
	 * @return the bean consulta pre alta
	 * @throws BusinessException the business exception
	 */
	public BeanConsultaPreAlta         consultaPreAltaCorresponsal            (BeanABMMantenimientoCorresponsal preAlta, ArchitechSessionBean psession)throws BusinessException;
	
	/**
	 * Elimina mantenimiento corresponsalia.
	 *
	 * @param beanCorresponsal the bean corresponsal
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 * @throws BusinessException the business exception
	 */
	public BeanResultadoCorresponsalia eliminaMantenimientoCorresponsalia     (BeanCorresponsal beanCorresponsal, ArchitechSessionBean psession)throws BusinessException;
	
	/**
	 * Activa desactiva corresponsalia.
	 *
	 * @param beanCorresponsal the bean corresponsal
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 * @throws BusinessException the business exception
	 */
	public BeanResultadoCorresponsalia activaDesactivaCorresponsalia          (BeanCorresponsal beanCorresponsal, ArchitechSessionBean psession)throws BusinessException;
}
