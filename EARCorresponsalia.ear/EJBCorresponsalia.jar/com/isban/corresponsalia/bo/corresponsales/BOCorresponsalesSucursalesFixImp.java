package com.isban.corresponsalia.bo.corresponsales;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.comunes.BeanSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoSucursal;
import com.isban.corresponsalia.dao.auditoria.DAOAuditor;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaCorresponsalesDLA2;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaSucursalDLA6Fix;
import com.isban.corresponsalia.dao.corresponsales.DAOSucursalesDLA5Fix;
import com.isban.corresponsalia.dao.sesiones.DAOSesiones;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;

/**
 * Session Bean implementation class BOCorresponsalesSucursalesImp.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOCorresponsalesSucursalesFixImp extends ArchitechEBE implements BOCorresponsalesSucursalesFix {
    
	/** The abc sucursales. */
   
	@EJB 
	transient private DAOSucursalesDLA5Fix          abcSucursales;
	
	/** The consulta sucursal. */    
	@EJB 
	transient private DAOConsultaSucursalDLA6Fix    consultaSucursal;
	
	/** The consulta corresponsales. */
    
	@EJB 
	transient private DAOConsultaCorresponsalesDLA2 consultaDLA2Corresponsales;
	
	/** The sesiones. */
    
	@EJB 
	transient private DAOSesiones                   sesiones;
	
	/** The auditor. */
    
	@EJB 
	transient private DAOAuditor                    auditor;

    /**
	 * Alta sucursal corresponsalia.
	 *
	 * @param beanAlta the bean alta
	 * @param psession the psession
	 * @return the bean resultado sucursal
	 * @throws BusinessException the business exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoSucursal altaSucursalCorresponsalia(BeanSucursal beanAlta, 	ArchitechSessionBean psession)throws BusinessException {

		debug("BOCorresponsalesSucursalesFix->abcSucursales");
		
		sesiones.actualizaSession(psession);
		auditor.auditaoperacion("Sucursales::abcSucursales", "CORBAN", "Sucursales", psession);
		
		if("B".equals(beanAlta.getIdOperacion())){
			final BeanConsultaSucursal consultaDetalle = new BeanConsultaSucursal();
			consultaDetalle.setCodigoCorresponsalia(beanAlta.getIdCorresponsal());
			consultaDetalle.setCodigoSucursal(beanAlta.getNumId());
			consultaDetalle.setIndicadorPaginacion("N");
			consultaDetalle.setIndicadorFuncional("D");
			final Object regresoConsulta = consultaSucursal.consultaSucursales(consultaDetalle, psession);
			
			if(regresoConsulta instanceof BeanError) {
		    	throw new BusinessException(this.getClass().getName(),((BeanError)regresoConsulta).getCodigoError(),((BeanError)regresoConsulta).getMsgError());
			}

			final HashMap<String, Object> result = (HashMap<String, Object>)regresoConsulta;			
			final ArrayList<BeanSucursal> registros = (ArrayList<BeanSucursal>)result.get("registros");
			
			beanAlta = registros.get(0);
			beanAlta.setIdOperacion("B");
		}
		
		final BeanResultadoSucursal beanRespuesta = new BeanResultadoSucursal();
		final BeanError             respuestaBo   = abcSucursales.sucursales(beanAlta, psession);
		
		if(respuestaBo.getCodigoError().indexOf("QCE")>-1 || respuestaBo.getCodigoError().indexOf("DLE")>-1) {
	    	throw new BusinessException(this.getClass().getName(),respuestaBo.getCodigoError(),respuestaBo.getMsgError());
		}
		
		beanRespuesta.setCodError(respuestaBo.getCodigoError());
		beanRespuesta.setMsgError(respuestaBo.getMsgError());
		return beanRespuesta;
	}

	/**
	 * Consulta corresponsales.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 * @throws BusinessException the business exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoCorresponsalia consultaCorresponsales(BeanMttoConsultaCorresponsal beanConsulta, ArchitechSessionBean psession)throws BusinessException{
		
		debug("BOCorresponsalesSucursalesFix->ConsultaCorresponsales");
		
		sesiones.actualizaSession(psession);
		auditor.auditaoperacion("Sucursales::ConsultaCorresponsales", "CORBAN", "Sucursales", psession);
		final BeanResultadoCorresponsalia beanRespuesta= new BeanResultadoCorresponsalia();
		
		final Object regresoConsulta = consultaDLA2Corresponsales.consultaCorresponsalias(beanConsulta, psession);
		
		/*
		if(regresoConsulta instanceof BeanError)
	    	throw new BusinessException(this.getClass().getName(),((BeanError)regresoConsulta).getCodigoError(),((BeanError)regresoConsulta).getMsgError());
		 */
		if(regresoConsulta instanceof BeanError){
			beanRespuesta.setCodError(((BeanError)regresoConsulta).getCodigoError());
			beanRespuesta.setMsgError(((BeanError)regresoConsulta).getMsgError());
			beanRespuesta.setRegistros(new ArrayList<BeanCorresponsal>());
		}
		else{		
			ArrayList<BeanCorresponsal> argResultado = (ArrayList<BeanCorresponsal>) regresoConsulta;
			beanRespuesta.setRegistros(argResultado);
		}		
		return beanRespuesta;
	}
	
	
	/**
	 * Consulta sucursal corresponsalia.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado sucursal
	 * @throws BusinessException the business exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoSucursal consultaSucursalCorresponsalia(BeanConsultaSucursal beanConsulta, 	ArchitechSessionBean psession)throws BusinessException {
		final BeanResultadoSucursal beanRespuesta= new BeanResultadoSucursal();
		
		debug("BOCorresponsalesSucursalesFix->ConsultaSucursalCorresponsalia");
		
		sesiones.actualizaSession(psession);
		auditor.auditaoperacion("Sucursales::ConsultaSucursalCorresponsalia", "CORBAN", "ConsultaSucursalCorresponsalia", psession);
		
		final Object regresoConsulta = consultaSucursal.consultaSucursales(beanConsulta, psession);
		
		/*
		if(regresoConsulta instanceof BeanError)
	    	throw new BusinessException(this.getClass().getName(),((BeanError)regresoConsulta).getCodigoError(),((BeanError)regresoConsulta).getMsgError());
		*/
		
		if(regresoConsulta instanceof BeanError){
			beanRespuesta.setCodError(((BeanError)regresoConsulta).getCodigoError());
			beanRespuesta.setMsgError(((BeanError)regresoConsulta).getMsgError());
			beanRespuesta.setRegistros(new ArrayList<BeanSucursal>());
		}
		else{		
			final HashMap<String, Object> result = (HashMap<String, Object>)regresoConsulta;
			beanRespuesta.setRegistros((ArrayList<BeanSucursal>)result.get("registros"));
			beanRespuesta.setReferenciaAdelante((String)result.get("referenciaAvanzar"));
			beanRespuesta.setReferenciaAtras((String)result.get("referenciaRetroceder"));
			beanRespuesta.setMasAdelante((Boolean) result.get("masAdelante"));
			beanRespuesta.setMasAtras((Boolean) result.get("masAtras"));
			beanRespuesta.setNombreSucursalAtras((String)result.get("nombreSucursalAtras"));
			beanRespuesta.setNombreSucursal((String)result.get("nombreSucursal"));
			debug("BO nombre sucursal: " + beanRespuesta.getNombreSucursal());
		}
		return beanRespuesta;
	}
}
