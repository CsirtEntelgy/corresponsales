package com.isban.corresponsalia.dao.consultas;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.consultas.BeanRegistroAnular;
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

// TODO: Auto-generated Javadoc
/**
 * Session Bean implementation class DAOConsultaBitacoraSinReferenciaDLB5Imp.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOAnulacionDLC4Imp extends ArchitechEBE implements DAOAnulacionDLC4 {

	/** The TRA n390. */
	private static final String TRAN390  = "DLC4";
	
	/** The codoper. */
	private static final String CODOPER  = "AnulacionDLC4";
	

	/**
	 * Anular.
	 *
	 * @param beanArq the bean arq
	 * @param beanRegistroAnular the bean registro anular
	 * @return the object
	 */	@Override
	public Object anular(ArchitechSessionBean beanArq, BeanRegistroAnular beanRegistroAnular) {
		debug("DAOAnulacionDLC4->anular");
		
    	final BeanError beanError = new BeanError();
    	
    	debug("Validando facultades del usuario sobre transaccion " + TRAN390);
    	if(!Facultades.getInstanceOf().trasaccionAutorizada(beanArq.getPerfil(), TRAN390)){
    		debug("El usuario no tiene facultades sobre transaccion " + TRAN390);
    		beanError.setCodigoError(Facultades.CODIGO_ERROR);
    		beanError.setMsgError(Facultades.MENSAJE_ERROR);
    		return beanError;
    	}
		
		try {
			final RequestMessageCicsDTO  requestDTO  = new RequestMessageCicsDTO();
			ResponseMessageCicsDTO responseDTO = new ResponseMessageCicsDTO();
			final DataAccess             dataAccess  = DataAccess.getInstance(requestDTO,getLoggingBean());
			
			requestDTO.setTransaction(TRAN390);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setMessage(getMessage(beanRegistroAnular));
			
			responseDTO = (ResponseMessageCicsDTO) dataAccess.execute(Alias.getAlias("CORRESPONSALIA"));
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
				}
				else{
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
					
			}
			else{
	    		beanError.setCodigoError(responseDTO.getCodeError());
	    		beanError.setCodigoError(responseDTO.getMessageError());
			}		
		} 
		catch (ExceptionDataAccess e) {
			showException(e);
		}
		
		return beanError;
	}

    
 
    /**
     * Gets the message.
     *
     * @param beanConsulta the bean consulta
     * @return the message
     */
    private String getMessage(BeanRegistroAnular beanConsulta){
    	String message = "";
    	final StringBuffer trama = new StringBuffer();
    		/*
				PIC X(04)	    Req IENTIDA	Entidad	
				PIC X(02)	    Req ICORRES	Identificador de corresponsalia	
				PIC X(04)	    Req IIDCORR	Identificacion Corresponsal	
				PIC X(10)	    Req ICODSUC	Codigo de Identificacion de la Sucursal	
				PIC X(20)	    Opc IREFOPE	Numero de Referencia correspondiente a la operación	
				PIC X(20)	    Opc IREFINT	Numero de Referencia interna (Pampa o Captación)	
				PIC X(20)	    Opc ITARJET	Numero de tarjeta o cuenta	
				PIC X(10)	    Opc ICVEOPE	Clave referente al tipo de operación (Pago debito, credito, servicios etc.)	
				PIC X(10)	    Opc IFECOPE	Fecha en que se realizo la operación	
				PIC X(04)	    Opc INUMFAC	Numero de factura correspondiente a Pampa	
				PIC X(20)	    Opc IFOLOPE	Folio de la operación. 	
				PIC 9(13)V9(02)	Opc IIMPOPE	Importe de la operación 	 
    		*/
   	   		trama.append(Utils.rpad(beanConsulta.getEntidad()                     , " ", 4));
    		trama.append(Utils.rpad(beanConsulta.getIdentificadorCorresponsalia() , " ", 2));
    		trama.append(Utils.rpad(beanConsulta.getIdentificadorCorresponsal()   , " ", 4));
    		trama.append(Utils.rpad(beanConsulta.getCodigoIdentificadorSucursal() , " ", 10));
    		trama.append(Utils.rpad(""                                            , " ", 20));
   	   		trama.append(Utils.rpad(""                                            , " ", 20));
    		trama.append(Utils.rpad(""                                            , " ", 22));
    		trama.append(Utils.rpad(""                                            , " ", 10));
    		trama.append(Utils.rpad(beanConsulta.getFecha()                       , " ", 10));
    		trama.append(Utils.rpad(""                                            , " ", 4));
   	   		trama.append(Utils.lpad(beanConsulta.getFolioOperacion()              , "0", 20));
    		trama.append(Utils.lpad(beanConsulta.getImporteOperacion()            , "0", 15));
    	debug("Longitud Trama:" + trama.length());
    	
    	message = trama.toString();
    	debug("Trama:" + message);
    	
    	return message;
    }

 
}
