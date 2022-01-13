package com.isban.corresponsalia.bo.consultas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.consultas.BeanRegistroConsultaNoCompensacion;
import com.isban.corresponsalia.beans.consultas.RBeanAutorizaCompensacion;
import com.isban.corresponsalia.beans.consultas.RBeanConsultaNoCompensacion;
import com.isban.corresponsalia.beans.consultas.RBeanCorresponsalesConsultaCompensacion;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.comunes.Parametros;
import com.isban.corresponsalia.dao.auditoria.DAOAuditor;
import com.isban.corresponsalia.dao.consultas.DAOAutorizacionOperacionesCompensadasDLB0;
import com.isban.corresponsalia.dao.consultas.DAOConsultaNoCompenzadasDLB7;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaCorresponsalesDLA2;
import com.isban.corresponsalia.dao.sesiones.DAOSesiones;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;


/**
 * Session Bean implementation class BOConsultasCompensacionImp.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOConsultasCompensacionImp extends ArchitechEBE implements BOConsultasCompensacion {

	/** The consultas no compenzadas. */
	@EJB 
	transient private DAOConsultaNoCompenzadasDLB7              consultasNoCompenzadas;
	
	/** The autoriza compensadas. */
	@EJB 
	transient private DAOAutorizacionOperacionesCompensadasDLB0 autorizaCompensadas;
	
	/** The consulta corresponsales. */
	@EJB 
	transient private DAOConsultaCorresponsalesDLA2             consultaDLA2Corresponsales;
	
	/** The sesiones. */
	@EJB 
	transient private DAOSesiones                               sesiones;
	
	/** The auditor. */
	@EJB 
	transient private DAOAuditor                                auditor;
	
	/** The monitoreo credito. */
//	@EJB private DAOMonitoreoCreditoDLB4                   monitoreoCredito;
	

	/**
	 * Consulta corresponsales.
	 *
	 * @param beanArq the bean arq
	 * @return the r bean corresponsales consulta compensacion
	 * @throws BusinessException the business exception
	 */
	@SuppressWarnings("unchecked")
	public RBeanCorresponsalesConsultaCompensacion consultaCorresponsales(ArchitechSessionBean beanArq)throws BusinessException{
		
		debug("BOConsultasCompensacion->consultaCorresponsales");

		sesiones.actualizaSession(beanArq);
		auditor.auditaoperacion("ConsultasCompensacion::ConsultaCorresponsales", "CORBAN", "Consulta Corresponsal", beanArq);

		final RBeanCorresponsalesConsultaCompensacion   rBeanCorresponsalesConsultaCompensacion = new RBeanCorresponsalesConsultaCompensacion();
	    ArrayList<BeanCorresponsal>               listaCorresponsales                     = new ArrayList<BeanCorresponsal>();
		
	    final String lstrCodigoCorresponsalia = Parametros.getParametroAplicacion("CORRESPONSALIA");
	    debug("Codigo Corresponsalia:" + lstrCodigoCorresponsalia);
	    final BeanMttoConsultaCorresponsal beanConsulta = new BeanMttoConsultaCorresponsal();
	    
	    beanConsulta.setCodigoCorresponsalia(lstrCodigoCorresponsalia);
	    beanConsulta.setTipoConsulta("L");
	    beanConsulta.setIndicadorPaginacion("N");
	    
	    final Object objDaoResult = consultaDLA2Corresponsales.consultaCorresponsalias(beanConsulta, getArchitechBean());
	    if(objDaoResult instanceof BeanError){
	    	if(((BeanError)objDaoResult).getCodigoError().indexOf("DLA")<0){
	    		throw new BusinessException(this.getClass().getName(),((BeanError)objDaoResult).getCodigoError(),((BeanError)objDaoResult).getMsgError());
	    	}
	    	else{
	    	    rBeanCorresponsalesConsultaCompensacion.setCodError(((BeanError)objDaoResult).getCodigoError());
	    	    rBeanCorresponsalesConsultaCompensacion.setMsgError(((BeanError)objDaoResult).getMsgError());
	    	    rBeanCorresponsalesConsultaCompensacion.setListaCorresponsales(listaCorresponsales);
	    		return rBeanCorresponsalesConsultaCompensacion;	    			
	    		}	
	    }
	   
	    listaCorresponsales = (ArrayList<BeanCorresponsal>)objDaoResult;
	    
	    if(listaCorresponsales == null){
	    	throw new BusinessException(this.getClass().getName(),"BITA9999","Error al consultar corresponsales");
	    }
	    debug("Numero de Corresponsales encontrados:" + listaCorresponsales.size());
	    
	    rBeanCorresponsalesConsultaCompensacion.setListaCorresponsales(listaCorresponsales);
	    rBeanCorresponsalesConsultaCompensacion.setCodError("BITA0000");
	    rBeanCorresponsalesConsultaCompensacion.setMsgError("Consulta de corresponsales regreso resultados");
		return rBeanCorresponsalesConsultaCompensacion;
	}
  
	/**
	 * Autoriza compensacion.
	 *
	 * @param beanArq the bean arq
	 * @param beanCorresponsal the bean corresponsal
	 * @return the r bean autoriza compensacion
	 * @throws BusinessException the business exception
	 */
    public RBeanAutorizaCompensacion autorizaCompensacion(ArchitechSessionBean beanArq, BeanCorresponsal beanCorresponsal)throws BusinessException{
		
		debug("BOConsultasCompensacion->autorizaCompensacion");

		sesiones.actualizaSession(beanArq);
		auditor.auditaoperacion("ConsultasCompensacion::AutorizaCompensacion", "CORBAN", "Autoriza Compensacion", beanArq);

		final RBeanAutorizaCompensacion  rBeanAutorizaCompensacion = new RBeanAutorizaCompensacion();
		final Object objResultAutorizar = autorizaCompensadas.autorizaCompensacion(getArchitechBean(), beanCorresponsal);

	    if(objResultAutorizar instanceof BeanError){
	    	rBeanAutorizaCompensacion.setCodError(((BeanError)objResultAutorizar).getCodigoError());
	    	rBeanAutorizaCompensacion.setMsgError(((BeanError)objResultAutorizar).getMsgError());
	    }
	    else{
	    	rBeanAutorizaCompensacion.setCodError("Error Desconocido");
	    	rBeanAutorizaCompensacion.setMsgError("No fue posible Autorizar");	    	
	    }
		
	    return rBeanAutorizaCompensacion;
	}
    
    /**
     * Consulta no compensacion.
     *
     * @param beanArq the bean arq
     * @param beanCorresponsal the bean corresponsal
     * @param pstrAvanzaRetrocede the pstr avanza retrocede
     * @param pstrFechaInferior the pstr fecha inferior
     * @param pstrFechaSuperior the pstr fecha superior
     * @return the r bean consulta no compensacion
     * @throws BusinessException the business exception
     */
 	@SuppressWarnings("unchecked")
	public RBeanConsultaNoCompensacion consultaNoCompensacion(ArchitechSessionBean beanArq, BeanCorresponsal beanCorresponsal, String pstrAvanzaRetrocede, String pstrFechaInferior, String pstrFechaSuperior )throws BusinessException{
		
		debug("BOConsultasCompensacion->consultaNoCompensacion");
		
		sesiones.actualizaSession(beanArq);
		auditor.auditaoperacion("ConsultasCompensacion::ConsultasCompensacion", "CORBAN", "Consultas Compensacion", beanArq);

		final RBeanConsultaNoCompensacion rBeanConsultaNoCompensacion = new RBeanConsultaNoCompensacion();
		
		final BeanMttoConsultaCorresponsal beanConsulta = new BeanMttoConsultaCorresponsal();
		BeanCorresponsal             beanDetalle  = new BeanCorresponsal();
	//	final BeanMonitoreoCredito         beanConsultaCre = new BeanMonitoreoCredito(Parametros.getParametroAplicacion("CORRESPONSALIA"));
	    
	    String lstrCodigoCorresponsalia = Parametros.getParametroAplicacion("CORRESPONSALIA");
	    debug("Codigo Corresponsalia:" + lstrCodigoCorresponsalia);
	    beanConsulta.setCodigoCorresponsalia(lstrCodigoCorresponsalia);
	    beanConsulta.setCodigoCorresponsal(beanCorresponsal.getCodigoCorresponsal());
	    beanConsulta.setTipoConsulta("D");
	    beanConsulta.setIndicadorPaginacion("N");
	    
	    Object objDaoResultD = consultaDLA2Corresponsales.consultaCorresponsalias(beanConsulta, getArchitechBean());
	    //List<BeanRegistroMonitoreoCredito> listaRegistrosMonitoreoCredito = monitoreoCredito.monitoreoCredito(getArchitechBean(), beanConsultaCre);
	   
	    if(!(objDaoResultD instanceof BeanError)){
	    	ArrayList<BeanCorresponsal> listaCorresponsales = (ArrayList<BeanCorresponsal>)objDaoResultD;
	    	if(listaCorresponsales == null || listaCorresponsales.isEmpty()){
	    		//throw new BusinessException(this.getClass().getName(),"COMP9999","Error al consultar compensacion");
	    	}
	    	beanDetalle = listaCorresponsales.get(0);
	    }
	     	
	    
	    final Object objDaoResultO = consultasNoCompenzadas.consultaNoCompensacion(getArchitechBean(), beanCorresponsal,pstrAvanzaRetrocede,pstrFechaInferior,pstrFechaSuperior);
		
	    if(objDaoResultO instanceof BeanError){
	    	if(((BeanError)objDaoResultO).getCodigoError().indexOf("DLA")==-1){
	    		throw new BusinessException(this.getClass().getName(),((BeanError)objDaoResultO).getCodigoError(),((BeanError)objDaoResultO).getMsgError());
	    	}
	    	else{
	    		rBeanConsultaNoCompensacion.setCodError(((BeanError)objDaoResultO).getCodigoError());
	    		rBeanConsultaNoCompensacion.setMsgError(((BeanError)objDaoResultO).getMsgError());
	    		rBeanConsultaNoCompensacion.setListaRegistrosConsultaNoCompensacion(new ArrayList<BeanRegistroConsultaNoCompensacion>());
	    		rBeanConsultaNoCompensacion.setDetalleCorresponsal(beanDetalle);
	    		return rBeanConsultaNoCompensacion;	    			
	    		}	
	    }
		    
	    final List<BeanRegistroConsultaNoCompensacion> listaRegistrosConsultaNoCompensacion = (List<BeanRegistroConsultaNoCompensacion>)((HashMap<String,Object>)objDaoResultO).get("registros");
	    final String lstrFechaInferior = (String)((HashMap<String,Object>)objDaoResultO).get("fechaInferior");
	    final String lstrFechaSuperior = (String)((HashMap<String,Object>)objDaoResultO).get("fechaSuperior");
		
		if(listaRegistrosConsultaNoCompensacion == null || listaRegistrosConsultaNoCompensacion.isEmpty()){
			throw new BusinessException(this.getClass().getName(),"COMP9999","Error al consultar compensacion");
		}
		rBeanConsultaNoCompensacion.setListaRegistrosConsultaNoCompensacion((ArrayList<BeanRegistroConsultaNoCompensacion>) listaRegistrosConsultaNoCompensacion);
		rBeanConsultaNoCompensacion.setDetalleCorresponsal(beanDetalle);
		rBeanConsultaNoCompensacion.setFechaInferior(lstrFechaInferior);
		rBeanConsultaNoCompensacion.setFechaSuperior(lstrFechaSuperior);		
		rBeanConsultaNoCompensacion.setCodError("COMP0000");
		rBeanConsultaNoCompensacion.setMsgError("Ejecucion exitosa de consulta de compensacion");
		
		return rBeanConsultaNoCompensacion;
	}
}
