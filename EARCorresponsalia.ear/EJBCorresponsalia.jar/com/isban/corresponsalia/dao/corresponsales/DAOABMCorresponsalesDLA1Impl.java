package com.isban.corresponsalia.dao.corresponsales;


import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanABMMantenimientoCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaPreAlta;
import com.isban.corresponsalia.comunes.TratamientoTrama;
import com.isban.corresponsalia.comunes.Utils;
import com.isban.dataaccess.DataAccess;
import com.isban.dataaccess.channels.cics.dto.RequestMessageCicsDTO;
import com.isban.dataaccess.channels.cics.dto.ResponseMessageCicsDTO;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.ExceptionDataAccess;


/**
 * The Class DAOABMCorresponsalesDLA1Impl.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOABMCorresponsalesDLA1Impl extends ArchitechEBE implements DAOABMCorresponsalesDLA1{

	/** The TRA n390. */
	private static final String TRAN390  = "DLA1";
	
	/** The codoper. */
	private static final String CODOPER  = "AltaBajaMttoCorresponsalDLA1";
	
	/** The arrg resultado. */
	transient private String[] arrgResultado;
	
	/**
	 * ABM corresponsalias.
	 *
	 * @param beanABM the bean abm
	 * @param psession the psession
	 * @return the object
	 */
	@Override
	public Object aBMCorresponsalias(BeanABMMantenimientoCorresponsal beanABM,
			ArchitechSessionBean psession) {
		info("Inicia DAO ABM Corresponsal");
		final BeanError beanRespuesta = new BeanError();
		
		String tramaSalida;
		ejecutaTransaccion(beanABM);
		
		tramaSalida = arrgResultado[2];
		
		if(!TratamientoTrama.esRespuestaCorrecta(tramaSalida)){
			debug("La transaccion[" + TRAN390 +"] regreso el siguiente error...");
			final String lstrCodError = TratamientoTrama.getCodigoError(tramaSalida);
			final String lstrMsgError = TratamientoTrama.getMensajeError(tramaSalida);
			debug("Codigo de Error :" + lstrCodError);
			debug("Mensaje de Error:" + lstrMsgError);
			beanRespuesta.setCodigoError(lstrCodError);
			beanRespuesta.setMsgError(lstrMsgError);
			return beanRespuesta;
		}else if(tramaSalida.length()>182){
			return desentrama(tramaSalida);
		}
		debug("La transaccion[" + TRAN390 +"] regreso el siguiente mensaje...");
		final String lstrCodMsg = TratamientoTrama.getCodigoAviso(tramaSalida);
		final String lstrMsgMsg = TratamientoTrama.getMensajeAviso(tramaSalida);
		debug("Codigo de Exito :" + lstrCodMsg);
		debug("Mensaje de Exito:" + lstrMsgMsg);
		beanRespuesta.setCodigoError(lstrCodMsg);
		beanRespuesta.setMsgError(lstrMsgMsg);
		info("Sale con exito ABM Corresponsal");
		return beanRespuesta;
			
	}

	
	/**
	 * Ejecuta la transaccion DLA1 Alta Baja corresponsalia.
	 *
	 * @param beanConsulta the bean consulta
	 * @return the string[]
	 */
	public String[] ejecutaTransaccion(BeanABMMantenimientoCorresponsal beanConsulta){
    	
    	info("Ejecutando DAOABMCorresponsalesDLA1Impl->"+ beanConsulta.getTipoOperacion());
    	arrgResultado = new String[3];
    	ResponseMessageCicsDTO responseDTO = new ResponseMessageCicsDTO();
		
		try {
			final RequestMessageCicsDTO requestDTO  = new RequestMessageCicsDTO();
			final DataAccess             dataAccess  = DataAccess.getInstance(requestDTO,getLoggingBean());
			
			requestDTO.setTransaction(TRAN390);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setMessage(generaTramaEntrada(beanConsulta));
			info("TRAMA ENTRADA : ----" + requestDTO.getMessage()+"----");
			responseDTO = (ResponseMessageCicsDTO) dataAccess.execute(getIdCanalDAO("CICS_CORRESPONSALIA"));
			info("TRAMA SALIDA : " + responseDTO.getResponseMessage());
			debug("Codigo de Error     :" + responseDTO.getCodeError());
			debug("Mensaje de Error    :" + responseDTO.getMessageError());
			debug("Mensaje de Respuesta:" + responseDTO.getResponseMessage());
			if(!"".equals(responseDTO.getCodeError())) {
				info("Respuesta exitosa");
			}
			arrgResultado[0] = responseDTO.getCodeError();
			arrgResultado[1] = responseDTO.getMessageError();
			arrgResultado[2] = responseDTO.getResponseMessage();
			
		} 
		catch (ExceptionDataAccess e) {
			showException(e);
		}
		return arrgResultado.clone();
	}
	
	/**
	 * Agrega la cabecera y arma la trama de entrada DLA1.
	 *
	 * @param beanConsulta the bean consulta
	 * @return the string
	 */
	private String generaTramaEntrada(BeanABMMantenimientoCorresponsal beanConsulta){
		final StringBuffer tramaEntrada = new StringBuffer();

    		tramaEntrada.append(Utils.rpad(beanConsulta.getCodigoCorresponsalia(), 
    				" ", 2));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getCentroCosto(), 
    				" ", 4));// 3ca, se debe informar el Centro de Costos como ID Corresponsal.
    		tramaEntrada.append(Utils.rpad(beanConsulta.getEstatusCorresponsal(), 
    				" ", 3));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getCodigoCliente(), 
    				" ", 8));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getSecuenciaDomicilio(), 
    				" ", 3));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getNombreCorresponsal(), 
    				" ", 40));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getGiroEmpresa(), 
    				" ", 4));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getIndicadorConciliacion(), 
    				" ", 1));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getParamDiasPendientesConciliar(), 
    				" ", 3));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getParamDiasPendientesCompensar(), 
    				" ", 3));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getIndicadorValidaComision(), 
    				" ", 1));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getCuentaChequesCorresponsal(), 
    				" ", 20));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getDivisaCuentaCheques(), 
    				" ", 3));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getLineaCreditoCorresponsal(), 
    				" ", 20));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getDivisaAsocLineaCredito(), 
    				" ", 3));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getLimiteImporteCorresponsal(), 
    				" ", 15));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getCorreoContacto(), 
    				" ", 20));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getCorreoAlternoContacto(), 
    				" ", 20));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getTelefonoAclaracion(), 
    				" ", 10));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getTipoOperacion(), 
    				" ", 1));

		info("Sale de generar la trama :" + tramaEntrada.toString());
		return tramaEntrada.toString();
		
	}
	
	/**
	 * Desentrama la respuesta de 390.
	 *
	 * @param trama the trama
	 * @return the bean consulta pre alta
	 */
	private BeanConsultaPreAlta desentrama(String trama) {
		final BeanConsultaPreAlta beanSalida = new BeanConsultaPreAlta();
		if(trama.indexOf("@DCDLMA111 P") > -1){
			trama = trama.substring(trama.indexOf("@DCDLMA111 P") + 12, trama.length()-2);
			beanSalida.setCalleDomicilio(trama.substring(0, 30));
			beanSalida.setNumeroExtDomicilio(trama.substring(30, 35));
			beanSalida.setNumeroIntDomicilio(trama.substring(35, 44));
			beanSalida.setColoniaDomicilio(trama.substring(44, 74));
			beanSalida.setDelegacionMunicipioDomicilio(trama.substring(74, 104));
			beanSalida.setCiudadDomicilio(trama.substring(104, 124));
			beanSalida.setEstadoDomicilio(trama.substring(124, 144));
			beanSalida.setCodigoPostalDomicilio(trama.substring(144, 152));
			beanSalida.setPaisDomicilio(trama.substring(152, 172));
			beanSalida.setTelefonoDomicilio(trama.substring(172, 182));
		}
		return beanSalida;
	} 

	

}
