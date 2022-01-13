package com.isban.corresponsalia.bo.corresponsales;
import javax.ejb.Local;

import com.isban.corresponsalia.beans.corresponsales.BeanConsultaSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoSucursal;
import com.isban.corresponsalia.beans.comunes.BeanSucursal;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

/**
 * The Interface BOCorresponsalesSucursales.
 */
@Local
public interface BOCorresponsalesSucursales {
	
	/**
	 * Alta sucursal corresponsalia.
	 *
	 * @param beanAlta the bean alta
	 * @param psession the psession
	 * @return the bean resultado sucursal
	 */
	public BeanResultadoSucursal altaSucursalCorresponsalia(BeanSucursal beanAlta, ArchitechSessionBean psession);
	
	/**
	 * Consulta corresponsales.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 */
	public BeanResultadoCorresponsalia consultaCorresponsales(BeanMttoConsultaCorresponsal beanConsulta, ArchitechSessionBean psession);
	
	/**
	 * Consulta sucursal corresponsalia.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado sucursal
	 */
	public BeanResultadoSucursal consultaSucursalCorresponsalia(BeanConsultaSucursal beanConsulta, ArchitechSessionBean psession);

}
