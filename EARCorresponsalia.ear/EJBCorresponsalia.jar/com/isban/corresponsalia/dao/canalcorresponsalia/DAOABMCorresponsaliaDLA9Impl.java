package com.isban.corresponsalia.dao.canalcorresponsalia;



import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanABMMantenimientoOperCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
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
 * Session Bean implementation class DAOAltaCorresponsaliaDLA9Impl.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOABMCorresponsaliaDLA9Impl extends ArchitechEBE implements DAOABMCorresponsaliaDLA9 {

	/** The TRA n390. */
	private static final String TRAN390  = "DLA9";
	
	/** The codoper. */
	private static final String CODOPER  = "AltaCorresponsaliaDLA9";

	
	/**
	 * ABM corresponsalias.
	 *
	 * @param beanABM the bean abm
	 * @param psession the psession
	 * @return the object
	 */
	@Override
	public Object aBMCorresponsalias(BeanABMMantenimientoOperCorresponsal beanABM,
			ArchitechSessionBean psession) {		
    	
		info("Inicia DAO Corresponsalia");
		String[] resultadoOperacion;
		final BeanError beanRespuesta = new BeanError();
		debug("Validando facultades del usuario sobre transaccion " + TRAN390);
    	if(!Facultades.getInstanceOf().trasaccionAutorizada(psession.getPerfil(), TRAN390)){
    		debug("El usuario no tiene facultades sobre transaccion " + TRAN390);
    		beanRespuesta.setCodigoError(Facultades.CODIGO_ERROR);
    		beanRespuesta.setCodigoError(Facultades.MENSAJE_ERROR);
    		return beanRespuesta;
    	}
		String codigoRespuesta;
		String msgRespuesta;
		
		resultadoOperacion = ejecutaTransaccion(beanABM);
		final String mensajeRespuesta = resultadoOperacion[2];
		
		if(!TratamientoTrama.esRespuestaCorrecta(mensajeRespuesta)){
			info("La transaccion[" + TRAN390 +"] regreso el siguiente error...");
			codigoRespuesta = TratamientoTrama.getCodigoError(mensajeRespuesta);
			msgRespuesta = TratamientoTrama.getMensajeError(mensajeRespuesta);
			info("Codigo de Error :" + codigoRespuesta);
			info("Mensaje de Error:" + msgRespuesta);
		}else{			
			codigoRespuesta   = TratamientoTrama.getCodigoAviso(mensajeRespuesta);
			msgRespuesta   = TratamientoTrama.getMensajeAviso(mensajeRespuesta);
			info("La transaccion[" + TRAN390 +"] regreso el siguiente aviso..." + codigoRespuesta + msgRespuesta);
		}
		beanRespuesta.setCodigoError(codigoRespuesta);
		beanRespuesta.setMsgError(msgRespuesta);
		info("Fin Alta DAO Corresponsalia");
		return beanRespuesta;
	}

	
	/**
	 * Ejecuta la transaccion DLA9 Alta, Baja y Modificacion de Corresponsalia.
	 *
	 * @param beanABM the bean abm
	 * @return the string[]
	 */
	public String[] ejecutaTransaccion(BeanABMMantenimientoOperCorresponsal beanABM){
    	
    	info("Ejecutando DAOAltaCorresponsaliaDLA9->altaCorresponsalia");
    	final String[] respuestaAlta = new String[3];
    	ResponseMessageCicsDTO responseDTO = new ResponseMessageCicsDTO();
    	
		try {
			final RequestMessageCicsDTO requestDTO  = new RequestMessageCicsDTO();
			final DataAccess             dataAccess  = DataAccess.getInstance(requestDTO,getLoggingBean());
			
			requestDTO.setTransaction(TRAN390);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setMessage(generaTramaEntrada(beanABM));
			info("TRAMA ENTRADA : " + requestDTO.getMessage());
			responseDTO = (ResponseMessageCicsDTO) dataAccess.execute(getIdCanalDAO("CICS_CORRESPONSALIA"));
			info("TRAMA SALIDA : " + responseDTO.getResponseMessage());
			debug("Codigo de Error     :" + responseDTO.getCodeError());
			debug("Mensaje de Error    :" + responseDTO.getMessageError());
			debug("Mensaje de Respuesta:" + responseDTO.getResponseMessage());
			
			respuestaAlta[0] = responseDTO.getCodeError();
			respuestaAlta[1] = responseDTO.getMessageError();
			respuestaAlta[2] = responseDTO.getResponseMessage();
		} 
		catch (ExceptionDataAccess e) {
			showException(e);
		}
		return respuestaAlta.clone();
	}
	
	/**
	 * Arma la trama de entrada DLA9.
	 *
	 * @param beanABM the bean abm
	 * @return the string
	 */
	private String generaTramaEntrada(BeanABMMantenimientoOperCorresponsal beanABM) {
    	final StringBuffer trama = new StringBuffer();
    		trama.append(Utils.rpad(beanABM.getEntidad(), 
    				" ", 4));
    		trama.append(Utils.rpad(beanABM.getCanal(), 
    				" ", 2));
    		trama.append(Utils.rpad(beanABM.getIdCorresponsal(), 
    				" ", 4));
    		trama.append(Utils.rpad(beanABM.getCodigoSucursal(), 
    				" ", 10));
    		trama.append(Utils.rpad(beanABM.getNivelParametria(), 
    				" ", 2));
    		trama.append(Utils.rpad(beanABM.getTransaccion(), 
    				" ", 4));
    		trama.append(Utils.lpad(beanABM.getCodigoOperacion(), 
    				"0", 6));
    		trama.append(Utils.rpad(beanABM.getTipoOperacionCorr(), 
    				" ", 10));
    		trama.append(Utils.rpad(beanABM.getDivisa(), 
    				" ", 3));
    		trama.append(Utils.rpad(beanABM.getFechaAltaParametria(), 
    				" ", 10));
    		trama.append(Utils.rpad(beanABM.getFechaBajaParametria(), 
    				" ", 10));
    		trama.append(Utils.rpad(beanABM.getFechaCancelacionParametria(), 
    				" ", 10));
    		trama.append(Utils.rpad(beanABM.getHoraInicio(), " ", 6));
    		trama.append(Utils.rpad(beanABM.getHoraFinal(), " ", 6));

    		/*trama.append(Utils.rpad(beanABM.getHoraInicio(), 
    				"0", 6));
    		trama.append(Utils.rpad(beanABM.getHoraFinal(), 
    				"0", 6));*/
    		trama.append(Utils.lpad(beanABM.getImporteMinimoOperacion(), 
    				"0", 15));
    		trama.append(Utils.lpad(beanABM.getImporteMaximoOperacion(), 
    				"0", 15));
    		trama.append(Utils.lpad(beanABM.getAcumuladoDiario(), 
    				"0", 15));
    		trama.append(Utils.lpad(beanABM.getAcumuladoMensual(), 
    				"0", 15));
    		trama.append(Utils.rpad(beanABM.getCodigoOperacionTrx(), 
    				"0", 4));
    		trama.append(Utils.rpad(beanABM.getCodigoContratoTrx(), 
    				" ", 4));
    		trama.append(Utils.rpad(beanABM.getCodigoOperacionTrxSec(), 
    				" ", 4));
    		trama.append(Utils.rpad(beanABM.getCodigoContratoTrxSec(), 
    				" ", 4));
    		trama.append(Utils.rpad(beanABM.getTipoMovimiento(), 
    				" ", 1));
    	debug("Longitud Trama:" + trama.length());
    	
    	return trama.toString();
    }		
}
