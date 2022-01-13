package com.isban.corresponsalia.bo.corresponsales;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.comunes.BeanSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoSucursal;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaCorresponsalesDLA2;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaSucursalDLA6;
import com.isban.corresponsalia.dao.corresponsales.DAOSucursalesDLA5;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;


/**
 * Session Bean implementation class BOCorresponsalesSucursalesImp.
 */
@Stateless
public class BOCorresponsalesSucursalesImp extends ArchitechEBE implements BOCorresponsalesSucursales {

    
	/** The dao abm. */
	
	@EJB 
	transient private DAOSucursalesDLA5 daoABM;
	
	/** The dao consulta sucursal. */
	
	@EJB 
	transient private DAOConsultaSucursalDLA6 daoConsultaSucursal;
	
	/** The dao consulta corresponsales. */
	
	@EJB 
	transient private DAOConsultaCorresponsalesDLA2 daoConsultaCorresponsales;

	/**
	 * Alta sucursal corresponsalia.
	 *
	 * @param beanAlta the bean alta
	 * @param psession the psession
	 * @return the bean resultado sucursal
	 */
	@Override
	public BeanResultadoSucursal altaSucursalCorresponsalia(BeanSucursal beanAlta,
			ArchitechSessionBean psession) {
		final BeanResultadoSucursal beanRespuesta= new BeanResultadoSucursal();
		BeanError respuestaBo;
		info("Inicio Alta Corresponsalia BO ");
		
/*		if (validaExisteSucursales(beanAlta)){
			beanRespuesta.setCodError(BeanResultadoSucursal.CODE_ERROR_CONSULTA);
			beanRespuesta.setMsgError("El registro ya se encuentra almacenado.");
			info("Error en Alta Corresponsalia BO Valida duplicados.s");
			return beanRespuesta;
		}*/
		respuestaBo = daoABM.Sucursales(beanAlta, psession);
		beanRespuesta.setCodError(respuestaBo.getCodigoError());
		beanRespuesta.setMsgError(respuestaBo.getMsgError());
			
		info("Fin Alta Sucursal BO ");
		return beanRespuesta;
	}

	/**
	 * Consulta corresponsales.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoCorresponsalia consultaCorresponsales(
			BeanMttoConsultaCorresponsal beanConsulta,
			ArchitechSessionBean psession) {
		
		final BeanResultadoCorresponsalia beanRespuesta= new BeanResultadoCorresponsalia();
		//String respuestaBo;
		info("Inicia BO Consulta Corresponsalia / Sucursales");
		
		Object regresoConsulta = daoConsultaCorresponsales.consultaCorresponsalias(beanConsulta, psession);
		
		if(regresoConsulta instanceof BeanError){
			final BeanError beanError = (BeanError) regresoConsulta;
			beanRespuesta.setCodError(beanError.getCodigoError());
			beanRespuesta.setMsgError(beanError.getMsgError());
		} else {
			final ArrayList<BeanCorresponsal> argResultado = (ArrayList<BeanCorresponsal>) regresoConsulta;
			info("BO datos:" + argResultado.size());
			beanRespuesta.setRegistros(argResultado);
		}
			
		info("Fin  BO Consulta Corresponsalia / Sucursales");
		return beanRespuesta;
	}
	
	
	/**
	 * Consulta sucursal corresponsalia.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado sucursal
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoSucursal consultaSucursalCorresponsalia(BeanConsultaSucursal beanConsulta,
			ArchitechSessionBean psession) {
		final BeanResultadoSucursal beanRespuesta= new BeanResultadoSucursal();
		
		info("Inicio Consulta Sucursales BO ");
		Object regresoConsulta = null;
		regresoConsulta = daoConsultaSucursal.ConsultaSucursales(beanConsulta, psession);
		if(regresoConsulta instanceof BeanError){
			final BeanError beanError = (BeanError) regresoConsulta;
			beanRespuesta.setCodError(beanError.getCodigoError());
			beanRespuesta.setMsgError(beanError.getMsgError());
		} else {
			beanRespuesta.setRegistros((ArrayList<BeanSucursal>) regresoConsulta);
		}		
		if(!"".equals(beanRespuesta.getCodError())){
			info("Error al salir de BO");
		} else {
			info("Exito al salir de BO");
		}
		
		info("Fin Consulta Sucursales BO ");
		return beanRespuesta;
	}	
	
}
