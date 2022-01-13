package com.isban.corresponsalia.dao.corresponsales;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.comunes.BeanSucursal;
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
 * Session Bean implementation class DAOAltaCorresponsaliaDLA5Impl.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOSucursalesDLA5FixImpl extends ArchitechEBE implements DAOSucursalesDLA5Fix {

	
	/** The TRA n390. */
	transient private static final String TRAN390  = "DLA5";
	/** The id canal. */
	transient private final String idCanal = Alias.getAlias("CORRESPONSALIA");
	
	/** The codoper. */
	transient private static final String CODOPER  = "AltaSucursalDLA5";

	
	/**
	 * Sucursales.
	 *
	 * @param beanABM the bean abm
	 * @param beanArq the bean Arq
	 * @return the bean error
	 */
	@Override
	public BeanError sucursales(BeanSucursal beanABM, ArchitechSessionBean beanArq) {

		debug("DAOSucursalesDLA5->altaSucursales");
		
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
			ResponseMessageCicsDTO responseDTO = null;
			final DataAccess             dataAccess  = DataAccess.getInstance(requestDTO,getLoggingBean());
			
			requestDTO.setTransaction(TRAN390);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setMessage(getMessage(beanABM));
			
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
   	 * @param beanABM the bean abm
   	 * @return the message
   	 */
   	private String getMessage(BeanSucursal beanABM){
	    	String message = "";
	    	final StringBuffer trama = new StringBuffer();
	
	      		trama.append(Utils.rpad(beanABM.getIdCorresponsal(), " ", 4));
	    		trama.append(Utils.rpad(beanABM.getNumId()         , " ", 10));
	    		trama.append(Utils.rpad(beanABM.getCodigoEstatus() , " ", 3));
	    		trama.append(Utils.rpad(beanABM.getRfc()           , " ", 13));
	    		trama.append(Utils.rpad(beanABM.getRegion()        , " ", 4));
	    		trama.append(Utils.rpad(beanABM.getZonaGeografica(), " ", 5));
	    		trama.append(Utils.rpad(beanABM.getDescZona()      , " ", 30));
	    		trama.append(Utils.rpad(beanABM.getFronteriza()    , " ", 1));
	    		trama.append(Utils.rpad(beanABM.getNombreSucursal(), " ", 40));
	    		trama.append(Utils.rpad(beanABM.getCodigoInterno() , " ", 10));
	    		trama.append(Utils.rpad(beanABM.getCalle()         , " ", 20));    		    		
	    		trama.append(Utils.rpad(beanABM.getNoExterior()    , " ", 5));
	    		trama.append(Utils.rpad(beanABM.getNoInterior()    , " ", 5));
	    		trama.append(Utils.rpad(beanABM.getColonia()       , " ", 20));
	    		trama.append(Utils.rpad(beanABM.getDelegMunicipio(), " ", 20));
	    		trama.append(Utils.rpad(beanABM.getCiudad()        , " ", 20));
	    		trama.append(Utils.rpad(beanABM.getEstado()        , " ", 20));
	    		trama.append(Utils.rpad(beanABM.getCodigoPostal()  , " ", 8));
	    		trama.append(Utils.rpad(beanABM.getEntreCalles()   , " ", 40));
	    		trama.append(Utils.rpad(beanABM.getTelefono()      , " ", 10));
	    		trama.append(Utils.rpad(beanABM.getIdOperacion()   , " ", 1));
	    	debug("Longitud Trama:" + trama.length());
	    	
	    	message = trama.toString();
	    	debug("Trama:" + message);
	    	
	    	return message;
	    }
}
