package com.isban.corresponsalia.bo.corresponsales;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanABMMantenimientoCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.comunes.BeanSucursal;
import com.isban.corresponsalia.dao.corresponsales.DAOABMCorresponsalesDLA1;
import com.isban.corresponsalia.dao.corresponsales.DAOSucursalesDLA5Fix;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;


/**
 * Session Bean implementation class BOCorresponsalesAltaMasivaImp.
 */
@Stateless
public class BOCorresponsalesAltaMasivaImp extends ArchitechEBE implements BOCorresponsalesAltaMasiva {

	/** The dao abc suc. */
	@EJB 
	transient private DAOSucursalesDLA5Fix daoABCSuc;
	
	/** The dao abc cor. */
	@EJB 
	transient private DAOABMCorresponsalesDLA1 daoABCCor;
	
	/**
	 * Alta sucursales.
	 *
	 * @param listaSucursales the lista sucursales
	 * @param listaErrores the lista errores
	 * @param psession the psession
	 */
	@Override
	public void altaSucursales(List<BeanSucursal> listaSucursales,
			Map<Integer, String> listaErrores, ArchitechSessionBean psession){
		BeanSucursal sucMto;
		BeanError respuesta;
		for(int i = 0; i < listaSucursales.size(); i++) {
			sucMto = listaSucursales.get(i);
			respuesta = daoABCSuc.sucursales(sucMto, psession);
			debug("COD_ERROR: " + respuesta.getCodigoError());
			debug("MSG_ERROR: " + respuesta.getMsgError());
			if(respuesta.getCodigoError().startsWith("DLE")) {
				listaErrores.put(sucMto.getLinea(), respuesta.getMsgError());
			}
		}
	}

	/**
	 * Alta corresponsales.
	 *
	 * @param listaCorresponsales the lista corresponsales
	 * @param listaErrores the lista errores
	 * @param psession the psession
	 */
	@Override
	public void altaCorresponsales(List<BeanABMMantenimientoCorresponsal> listaCorresponsales, Map<Integer, String> listaErrores, ArchitechSessionBean psession) {
		BeanABMMantenimientoCorresponsal corMto;
		Object objetoRegreso;
		BeanError respuesta;
		for(int i = 0; i < listaCorresponsales.size(); i++) {
			corMto = listaCorresponsales.get(i);
			objetoRegreso = daoABCCor.aBMCorresponsalias(corMto, psession);
			if(objetoRegreso instanceof BeanError){
				respuesta = new BeanError();
				debug("COD_ERROR: " + respuesta.getCodigoError());
				debug("MSG_ERROR: " + respuesta.getMsgError());
				if(respuesta.getCodigoError().startsWith("DLE")) {
					listaErrores.put(corMto.getLinea(), respuesta.getMsgError());
				}
			}			
		}
	}

}
