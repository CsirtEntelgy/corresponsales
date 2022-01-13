package com.isban.corresponsalia.dao.monitoreo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.monitoreo.BeanMonitoreoCredito;
import com.isban.corresponsalia.beans.monitoreo.BeanRegistroMonitoreoCredito;
import com.isban.corresponsalia.comunes.Facultades;
import com.isban.corresponsalia.comunes.TratamientoTrama;
import com.isban.corresponsalia.comunes.Utils;
import com.isban.dataaccess.DataAccess;
import com.isban.dataaccess.channels.cics.dto.RequestMessageCicsDTO;
import com.isban.dataaccess.channels.cics.dto.ResponseMessageCicsDTO;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.ExceptionDataAccess;

/**
 * Session Bean implementation class DAOMonitoreoCreditoDLB4Imp.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOMonitoreoCreditoDLB4Imp extends ArchitechEBE implements DAOMonitoreoCreditoDLB4 {

	/** The TRA n390. */
	private static final String TRAN390  = "DLB4";
	/** The id canal. */
	transient private final  String idCanal = getIdCanalDAO("CICS_CORRESPONSALIA");
	
	
	/** The codoper. */
	private static final String CODOPER  = "MonitoreoCreditoDLB4";	
	
    
	/**
	 * Monitoreo credito.
	 *
	 * @param beanArq the bean arq
	 * @param beanConsulta the bean consulta
	 * @return the list
	 */
    public List<BeanRegistroMonitoreoCredito>  monitoreoCredito(ArchitechSessionBean beanArq, BeanMonitoreoCredito beanConsulta){
    	
    	debug("DAOMonitoreoCreditoDLB5->MonitoreoCredito");
    	final BeanError beanError = new BeanError();
    	
    	debug("Validando facultades del usuario sobre transaccion " + TRAN390);
    	if(!Facultades.getInstanceOf().trasaccionAutorizada(beanArq.getPerfil(), TRAN390)){
    		debug("El usuario no tiene facultades sobre transaccion " + TRAN390);
    		beanError.setCodigoError(Facultades.CODIGO_ERROR);
    		beanError.setCodigoError(Facultades.MENSAJE_ERROR);
    		//return beanError;
    	}
    	
    	List<BeanRegistroMonitoreoCredito> listaRegistrossMonitoreoCredito = null;
    	
		try {
			final RequestMessageCicsDTO  requestDTO  = new RequestMessageCicsDTO();
			ResponseMessageCicsDTO responseDTO = null;
			final DataAccess             dataAccess  = DataAccess.getInstance(requestDTO,getLoggingBean());
			
			requestDTO.setTransaction(TRAN390);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setMessage(getMessage(beanConsulta));
			responseDTO = (ResponseMessageCicsDTO) dataAccess.execute(idCanal);
			debug("Codigo de Error     :" + responseDTO.getCodeError());
			debug("Mensaje de Error    :" + responseDTO.getMessageError());
			debug("Mensaje de Respuesta:" + responseDTO.getResponseMessage());
			
			final String lstrTrama = responseDTO.getResponseMessage();
			
			if("DAE000".equals(responseDTO.getCodeError())){
				debug("La transaccion[" + TRAN390 +"] fue ejecutada exitosamente...");
				if(!TratamientoTrama.esRespuestaCorrecta(lstrTrama)){
					debug("La transaccion[" + TRAN390 +"] regreso el siguiente error...");
					final String lstrCodError = TratamientoTrama.getCodigoError(lstrTrama);
					final String lstrMsgError = TratamientoTrama.getMensajeError(lstrTrama);
					debug("Codigo de Error :" + lstrCodError);
					debug("Mensaje de Error:" + lstrMsgError);
		    		beanError.setCodigoError(lstrCodError);
		    		beanError.setMsgError(lstrMsgError);
		    		//return beanError;
				}
				debug("La transaccion[" + TRAN390 +"] regreso el siguiente aviso...");
				final String lstrCodAviso   = TratamientoTrama.getCodigoAviso(lstrTrama);
				final String lstrMsgAviso   = TratamientoTrama.getMensajeAviso(lstrTrama);
				final String lstrFormatoRes = TratamientoTrama.getFormatoRespuesta(lstrTrama);
				debug("Codigo de Aviso  :" + lstrCodAviso);
				debug("Mensaje de Aviso :" + lstrMsgAviso);
				debug("Formato Respuesta:" + lstrFormatoRes);
				if(lstrMsgAviso.indexOf("NO EXISTEN DATOS")>-1){
					beanError.setCodigoError(lstrCodAviso);
		    		beanError.setMsgError(lstrMsgAviso);
				}
				listaRegistrossMonitoreoCredito = getRegistrosFix(TratamientoTrama.getTramaTratamiento(lstrTrama));
			}
			else{
	    		beanError.setCodigoError(responseDTO.getCodeError());
	    		beanError.setCodigoError(responseDTO.getMessageError());
			}
		} 
		catch (ExceptionDataAccess e) {
			showException(e);
		}
		return listaRegistrossMonitoreoCredito;
    }
    
    /**
     * Gets the message.
     *
     * @param beanConsulta the bean consulta
     * @return the message
     */
    private String getMessage(BeanMonitoreoCredito beanConsulta){
    	String message = "";
    	final  StringBuffer trama = new StringBuffer();
   		trama.append(Utils.rpad(beanConsulta.getIdCanalCorresponsalia()    , " ", 2));
    	debug("Longitud Trama:" + trama.length());
    	if(trama.length()==2){
    		message = trama.toString();
    		debug("Trama:" + message);
    	}
    	return message;
    }

    
   /**
    * Gets the registros fix.
    *
    * @param reesponseMessage the reesponse message
    * @return the registros fix
    */
   private List<BeanRegistroMonitoreoCredito> getRegistrosFix(String reesponseMessage){
    	
    	final List<BeanRegistroMonitoreoCredito> listaRegistrossMonitoreoCredito = new ArrayList<BeanRegistroMonitoreoCredito>();
    	debug("Trama tratar:" + reesponseMessage);
    	
    	final List<String> listaRegistros = TratamientoTrama.parteTrama(reesponseMessage, "@DCDLMB410 P");
    	debug("Numero de Registros obtenidos:" + listaRegistros.size());
    	for(String registro:listaRegistros){
    		debug("Registro:" + registro);
    		final BeanRegistroMonitoreoCredito beanRegMonitoreoCred = new BeanRegistroMonitoreoCredito();
			/*
			 Mapa Salida DLB4 
			 
			 Codigo de Identificacion del Corresponsal	4
			 Saldo Disponible cuenta de cheques			15
			 Saldo disponible linea de credito			15
			 Saldo por compensar. 						15
			 Limite de credito 							15
			 Limite de alerta 							15 			 
			 Total Posiciones							79 
			 */	
    		/* Modificación de la longitud inicial y final de la Salida DBL4*/
    		final String lstrCorresponsal      = registro.substring(0, 4);
    		final String lstrDisponibleCheques = registro.substring(4, 22);
    		final String lstrDisponibleCredito = registro.substring(22, 40);
    		final String lstrSaldoOperacion    = registro.substring(40, 58);
    		final String lstrCreditoOtorgado   = registro.substring(58, 76);
    		final String lstrLimiteAlerta      = registro.substring(76, 94);
    		
    		beanRegMonitoreoCred.setCorresponsal(lstrCorresponsal);
    		beanRegMonitoreoCred.setDisponibleCheques(lstrDisponibleCheques);
    		beanRegMonitoreoCred.setDisponibleCredito(lstrDisponibleCredito);
    		beanRegMonitoreoCred.setSaldoOperado(lstrSaldoOperacion);
    		beanRegMonitoreoCred.setCreditoOtorgado(lstrCreditoOtorgado);
    		beanRegMonitoreoCred.setLimiteAlerta(lstrLimiteAlerta);
    		
    		listaRegistrossMonitoreoCredito.add(beanRegMonitoreoCred);
    	}

    	return listaRegistrossMonitoreoCredito;
    }
    
}
