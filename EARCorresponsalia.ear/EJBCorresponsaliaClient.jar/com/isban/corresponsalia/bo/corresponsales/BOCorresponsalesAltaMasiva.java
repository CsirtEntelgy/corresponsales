package com.isban.corresponsalia.bo.corresponsales;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanABMMantenimientoCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanSucursal;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

/**
 * The Interface BOCorresponsalesAltaMasiva.
 */
@Local
public interface BOCorresponsalesAltaMasiva {

	/**
	 * Alta sucursales.
	 *
	 * @param listaSucursales the lista sucursales
	 * @param listaErrores the lista errores
	 * @param psession the psession
	 */
	void altaSucursales(List<BeanSucursal> listaSucursales,
			Map<Integer, String> listaErrores, ArchitechSessionBean psession);

	/**
	 * Alta corresponsales.
	 *
	 * @param listaCorresponsales the lista corresponsales
	 * @param listaErrores the lista errores
	 * @param psession the psession
	 */
	void altaCorresponsales(
			List<BeanABMMantenimientoCorresponsal> listaCorresponsales,
			Map<Integer, String> listaErrores, ArchitechSessionBean psession);

}
