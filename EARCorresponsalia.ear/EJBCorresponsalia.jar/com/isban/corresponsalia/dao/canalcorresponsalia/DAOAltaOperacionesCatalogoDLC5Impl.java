package com.isban.corresponsalia.dao.canalcorresponsalia;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanAltaOperacionesCatalogo;
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
 * The Class DAOAltaOperacionesCatalogoDLC5Impl.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOAltaOperacionesCatalogoDLC5Impl extends ArchitechEBE implements DAOAltaOperacionesCatalogoDLC5 {

	
	/** The TRA n390. */
	private static final String TRAN390  = "DLC5";
	
	/** The codoper. */
	private static final String CODOPER  = "AltaOperacionesCatalogoDLC5";
	
	
	/**
	 * Ata operaciones catalogo.
	 *
	 * @param beanAltaOperacionCatalogo the bean alta operacion catalogo
	 * @param psession the psession
	 * @return the bean error
	 */
	@Override
	public BeanError altaOperacionesCatalogo(
			BeanAltaOperacionesCatalogo beanAltaOperacionCatalogo,
			ArchitechSessionBean psession) {
		final BeanError beanSalida = new BeanError();
		
		debug("Validando facultades del usuario sobre transaccion " + TRAN390);
    	if(!Facultades.getInstanceOf().trasaccionAutorizada(psession.getPerfil(), TRAN390)){
    		debug("El usuario no tiene facultades sobre transaccion " + TRAN390);
    		beanSalida.setCodigoError(Facultades.CODIGO_ERROR);
    		beanSalida.setCodigoError(Facultades.MENSAJE_ERROR);
    		return beanSalida;
    	}
		info("Inicia DAO Alta Operacion Catalogo");
		String[] resultadoOperacion;
		String codigoRespuesta;
		String msgRespuesta;
		
		resultadoOperacion = ejecutaTransaccion(beanAltaOperacionCatalogo);
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
		beanSalida.setCodigoError(codigoRespuesta);
		beanSalida.setMsgError(msgRespuesta);

		info("Fin DAO Alta Operacion Catalogo");
		return beanSalida;
	}

	/**
	 * Ejecuta la transaccion DLC5 Alta de Operaciones Corresponsalia Catalogo.
	 *
	 * @param beanABM the bean abm
	 * @return the string[]
	 */
	public String[] ejecutaTransaccion(BeanAltaOperacionesCatalogo beanABM){
    	
    	info("Ejecutando DAOAltaOperacionesCatalogoDLC5->altaOperaciones");
    	final String[] respuestaAlta = new String[3];
    	ResponseMessageCicsDTO responseDTO = new ResponseMessageCicsDTO();
    	
		try {
			final RequestMessageCicsDTO requestDTO  = new RequestMessageCicsDTO();
			final DataAccess             dataAccess  = DataAccess.getInstance(requestDTO,getLoggingBean());
			
			requestDTO.setTransaction(TRAN390);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setMessage(generaTramaEntrada(beanABM));
			info("TRAMA ENTRADA : " + requestDTO.getMessage());
			responseDTO = (ResponseMessageCicsDTO) dataAccess.execute( getIdCanalDAO("CICS_CORRESPONSALIA"));
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
	 * Arma la trama de entrada DLC5.
	 *
	 * @param beanAlta the bean alta
	 * @return the string
	 */
	private String generaTramaEntrada(BeanAltaOperacionesCatalogo beanAlta) {
    	final StringBuffer trama = new StringBuffer();
    		trama.append(Utils.rpad(beanAlta.getTipoOpcion(), 
    				" ", 1));
    		trama.append(Utils.rpad(beanAlta.getEntidad(), 
    				" ", 4));
    		trama.append(Utils.rpad(beanAlta.getCanalCorresponsal(), 
    				" ", 2));
    		trama.append(Utils.rpad(beanAlta.getIdentificacionCorresponsal(), 
    				" ", 4));
    		trama.append(Utils.rpad(beanAlta.getFechAlta(), 
    				" ", 10));
    		trama.append(Utils.rpad(beanAlta.getNivelRegistroOperacion(), 
    				" ", 2));
    		trama.append(Utils.rpad(beanAlta.getConsecutivoIdentificacionOperacion(), 
    				" ", 6));
    		trama.append(Utils.rpad(beanAlta.getDescripcionCorta(), 
    				" ", 20));
    		trama.append(Utils.rpad(beanAlta.getDescripcionLarga(), 
    				" ", 50));
    		trama.append(Utils.rpad(beanAlta.getEquivalenciaTipoOperacionUno(), 
    				" ", 10));
    		trama.append(Utils.rpad(beanAlta.getEquivalenciaTipoOperacionDos(), 
    				" ", 10));
    		trama.append(Utils.rpad(beanAlta.getEquivalenciaTipoOperacionTres(), 
    				" ", 10));
    		trama.append(Utils.rpad(beanAlta.getClaveTransaccionAsociada(), 
    				" ", 4));
    		trama.append(Utils.rpad(beanAlta.getClaveTransaccionAsociadaAnterior(), 
    				" ", 4));
    	debug("Longitud Trama:" + trama.length());
    	
    	return trama.toString();
    }

}
