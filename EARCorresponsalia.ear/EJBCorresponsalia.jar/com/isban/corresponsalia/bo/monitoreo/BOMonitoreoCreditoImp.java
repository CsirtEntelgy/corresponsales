package com.isban.corresponsalia.bo.monitoreo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.beans.monitoreo.BeanMonitoreoCredito;
import com.isban.corresponsalia.beans.monitoreo.BeanRegistroMonitoreoCredito;
import com.isban.corresponsalia.beans.monitoreo.RBeanMonitoreoCredito;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaCorresponsalesDLA2;
import com.isban.corresponsalia.dao.monitoreo.DAOMonitoreoCreditoDLB4;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;

/**
 * Session Bean implementation class BOMonitoreoCreditoImp.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOMonitoreoCreditoImp extends ArchitechEBE implements BOMonitoreoCredito {
	
	/** The entidad. */
//	private static String ENTIDAD       = Parametros.getParametroAplicacion("ID_ENTIDAD_BANCO");
	
	/** The canal. */
//	private static String CANAL         = Parametros.getParametroAplicacion("CANAL");
	
	/** The centro costos. */
//	private static String CENTRO_COSTOS = Parametros.getParametroAplicacion("CENTRO_COSTOS");
	
	/** The monitoreo credito. */
	
	@EJB 
	transient private DAOMonitoreoCreditoDLB4       daoMonitoreoCredito;
	
	/** The dao consulta corresponsalia. */
	
	@EJB 
	transient private DAOConsultaCorresponsalesDLA2      daoConsultaCorresponsalia;
	
	
	/**
	 * Monitoreo credito.
	 *
	 * @param beanArq the bean arq
	 * @param beanConsulta the bean consulta
	 * @return the r bean monitoreo credito
	 * @throws BusinessException the business exception
	 */
	public RBeanMonitoreoCredito monitoreoCredito(ArchitechSessionBean beanArq, BeanMonitoreoCredito beanConsulta)throws BusinessException{
		
		debug("BOMonitoreoCredito->monitoreoCredito");
		
		final RBeanMonitoreoCredito rBeanMonitoreoCredito = new RBeanMonitoreoCredito();
		
		final List<BeanRegistroMonitoreoCredito> listaRegistrosMonitoreoCredito = daoMonitoreoCredito.monitoreoCredito(getArchitechBean(), beanConsulta);
		if(listaRegistrosMonitoreoCredito == null || listaRegistrosMonitoreoCredito.isEmpty()){
			throw new BusinessException(this.getClass().getName(),"MNCR9999","Error en Monitoreo de Creditos");
		}
		rBeanMonitoreoCredito.setListaRegistrosMonitoreoCredito((ArrayList<BeanRegistroMonitoreoCredito>) listaRegistrosMonitoreoCredito);
		rBeanMonitoreoCredito.setCodError("MNCR0000");
		rBeanMonitoreoCredito.setMsgError("Ejecucion exitosa de Mnotoreo de Creditos");
		
		return rBeanMonitoreoCredito;
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
	public BeanResultadoCorresponsalia consultaCorresponsales(BeanMttoConsultaCorresponsal beanConsulta,ArchitechSessionBean psession) throws BusinessException {
		
		info("BOMonitoreoCredito->ConsultaCorresponsales");
		final BeanResultadoCorresponsalia beanResultado = new BeanResultadoCorresponsalia();
		
		final Object regresoConsulta = daoConsultaCorresponsalia.consultaCorresponsalias(beanConsulta, psession);
		
		if(regresoConsulta instanceof BeanError){
			BeanError beanError = (BeanError) regresoConsulta;
			beanResultado.setCodError(beanError.getCodigoError());
			beanResultado.setMsgError(beanError.getMsgError());
		} 
		else {
			beanResultado.setRegistros((ArrayList<BeanCorresponsal>) regresoConsulta);
		}

		return beanResultado;
	}
	
    
}