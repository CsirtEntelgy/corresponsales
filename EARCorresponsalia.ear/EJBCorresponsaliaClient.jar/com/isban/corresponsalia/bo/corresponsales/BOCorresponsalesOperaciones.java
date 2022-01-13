package com.isban.corresponsalia.bo.corresponsales;
import javax.ejb.Local;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanABMMantenimientoOperCorresponsal;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCorresponsal;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanResultadoOperacionesCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoSucursal;
import com.isban.ebe.commons.beans.ArchitechSessionBean;


/**
 * The Interface BOCorresponsalesOperaciones.
 */
@Local
public interface BOCorresponsalesOperaciones {

	/**
	 * Consulta corresponsales.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 */
	BeanResultadoCorresponsalia consultaCorresponsales(BeanMttoConsultaCorresponsal beanConsulta,
			ArchitechSessionBean psession);
	
	/**
	 * Consulta operaciones corresponsal.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado operaciones corresponsal
	 */
	BeanResultadoOperacionesCorresponsal consultaOperacionesCorresponsal(
			BeanConsultaOperacionesCorresponsal beanConsulta, ArchitechSessionBean psession);

	/**
	 * Actualiza operacion.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean error
	 */
	BeanError actualizaOperacion(
			BeanABMMantenimientoOperCorresponsal beanConsulta,
			ArchitechSessionBean psession);
	
	/**
	 * Consulta sucursal corresponsalia.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado sucursal
	 */
	BeanResultadoSucursal consultaSucursalCorresponsalia(
			BeanConsultaSucursal beanConsulta, 
			ArchitechSessionBean psession);


}
