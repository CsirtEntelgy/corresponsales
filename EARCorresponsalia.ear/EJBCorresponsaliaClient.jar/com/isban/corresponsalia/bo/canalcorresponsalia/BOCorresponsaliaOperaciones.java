package com.isban.corresponsalia.bo.canalcorresponsalia;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanABMMantenimientoOperCorresponsal;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanAltaOperacionesCatalogo;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCatalogo;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCorresponsal;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanResultadoOperacionesCatalogo;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanResultadoOperacionesCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface BOCorresponsaliaOperaciones.
 */
public interface BOCorresponsaliaOperaciones {
	
	/**
	 * Consulta operaciones corresponsalia.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado operaciones corresponsal
	 */
	public BeanResultadoOperacionesCorresponsal consultaOperacionesCorresponsalia(BeanConsultaOperacionesCorresponsal beanConsulta, ArchitechSessionBean psession);
	
	/**
	 * Alta operaciones corresponsalia.
	 *
	 * @param beanAlta the bean alta
	 * @param psession the psession
	 * @return the bean resultado operaciones corresponsal
	 */
	public BeanResultadoOperacionesCorresponsal altaOperacionesCorresponsalia(BeanABMMantenimientoOperCorresponsal beanAlta, ArchitechSessionBean psession);
	
	/**
	 * Modificacion operaciones corresponsalia.
	 *
	 * @param beanBaja the bean baja
	 * @param psession the psession
	 * @return the bean resultado operaciones corresponsal
	 */
	public BeanResultadoOperacionesCorresponsal modificacionOperacionesCorresponsalia(BeanABMMantenimientoOperCorresponsal beanBaja, ArchitechSessionBean psession);
	
	/**
	 * Elimina operaciones corresponsalia.
	 *
	 * @param beanBaja the bean baja
	 * @param psession the psession
	 * @return the bean resultado operaciones corresponsal
	 */
	public BeanResultadoOperacionesCorresponsal eliminaOperacionesCorresponsalia(BeanABMMantenimientoOperCorresponsal beanBaja, ArchitechSessionBean psession);
	
	/**
	 * Alta operaciones catalogo.
	 *
	 * @param beanAlta the bean alta
	 * @param psession the psession
	 * @return the bean error
	 */
	public BeanError altaOperacionesCatalogo(BeanAltaOperacionesCatalogo beanAlta, ArchitechSessionBean psession);
	
	/**
	 * Consulta operaciones catalogo.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado operaciones catalogo
	 */
	public BeanResultadoOperacionesCatalogo consultaOperacionesCatalogo(BeanConsultaOperacionesCatalogo beanConsulta, ArchitechSessionBean psession);
	
}
