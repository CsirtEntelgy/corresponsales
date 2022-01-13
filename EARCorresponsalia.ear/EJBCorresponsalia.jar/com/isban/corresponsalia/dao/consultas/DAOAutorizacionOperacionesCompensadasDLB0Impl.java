package com.isban.corresponsalia.dao.consultas;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
//import com.isban.corresponsalia.comunes.Alias;
import com.isban.corresponsalia.comunes.Alias;
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
 * Session Bean implementation class DAOAutorizacionOperacionesCompensadasDLB0Impl.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOAutorizacionOperacionesCompensadasDLB0Impl extends ArchitechEBE implements DAOAutorizacionOperacionesCompensadasDLB0 {

	
	/** The TRA n390. */
	private static final String TRAN390  = "DLB0";
	
	/** The codoper. */
	private static final String CODOPER  = "ConsultasCompensadasDLB0";	

	/** The id canal. */
	transient private final String idCanal = Alias.getAlias("CORRESPONSALIA");


    /**
	 * Autoriza compensacion.
	 *
	 * @param beanArq the bean arq
	 * @param beanCorresponsal the bean corresponsal
	 * @return the object
	 */
    public Object  autorizaCompensacion(ArchitechSessionBean beanArq, BeanCorresponsal beanCorresponsal){
    	debug("DAOAutorizacionOperacionesCompensadasDLB0->autorizaCompensacion");
    	
    	final BeanError beanError = new BeanError();
    	debug("Validando facultades del usuario sobre transaccion " + TRAN390);
    	if(!Facultades.getInstanceOf().trasaccionAutorizada(beanArq.getPerfil(), TRAN390)){
    		debug("El usuario no tiene facultades sobre transaccion " + TRAN390);
    		beanError.setCodigoError(Facultades.CODIGO_ERROR);
    		beanError.setMsgError(Facultades.MENSAJE_ERROR);
    	}
    	else{
    		try {
    			final RequestMessageCicsDTO  requestDTO  = new RequestMessageCicsDTO();
    			ResponseMessageCicsDTO responseDTO = null;
    			final DataAccess             dataAccess  = DataAccess.getInstance(requestDTO,getLoggingBean());

    			requestDTO.setTransaction(TRAN390);
    			requestDTO.setCodeOperation(CODOPER);
    			requestDTO.setMessage(getMessage(beanCorresponsal));
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
    					return beanError;
    				}
    				debug("La transaccion[" + TRAN390 +"] regreso el siguiente aviso...");
    				final String lstrCodAviso   = TratamientoTrama.getCodigoAviso(lstrTrama);
    				final String lstrMsgAviso   = TratamientoTrama.getMensajeAviso(lstrTrama);
    				final String lstrFormatoRes = TratamientoTrama.getFormatoRespuesta(lstrTrama);
    				debug("Codigo de Aviso  :" + lstrCodAviso);
    				debug("Mensaje de Aviso :" + lstrMsgAviso);
    				debug("Formato Respuesta:" + lstrFormatoRes);
    				beanError.setCodigoError(lstrCodAviso);
    				beanError.setMsgError(lstrMsgAviso);

    			}
    			else{
    				beanError.setCodigoError(responseDTO.getCodeError());
    				beanError.setCodigoError(responseDTO.getMessageError());
    			}


    		} 
    		catch (ExceptionDataAccess e) {
    			showException(e);
    		}
    	}
    	
    	return beanError;   	
    }
    
    /**
     * Gets the message.
     *
     * @param beanCorresponsal the bean corresponsal
     * @return the message
     */
    private String getMessage(BeanCorresponsal beanCorresponsal){
    	String message = "";
    	final StringBuffer trama = new StringBuffer();
    		/*
			2	Req ICODCOR	Codigo de Identificacion del canal Corresponsalia	 
			4	Req ICORRES	Codigo de Identificacion del Corresponsal (Centro Costos Asignado por Usuario)	 
			1	Req IAUTCOM	Autorización de compensación (S = Autoriza, N= No Autoriza)	
    		*/
    		trama.append(Utils.rpad(beanCorresponsal.getCodigoCorresponsalia()     , " ", 2));
    		trama.append(Utils.rpad(beanCorresponsal.getCodigoCorresponsal()       , " ", 4));
    		trama.append(Utils.rpad("S"                                            , " ", 1));
    	message = trama.toString();
    	debug("Longitud Trama:" + message.length());
    	debug("Trama         :" + message);    	
    	return message;
    }   
}
