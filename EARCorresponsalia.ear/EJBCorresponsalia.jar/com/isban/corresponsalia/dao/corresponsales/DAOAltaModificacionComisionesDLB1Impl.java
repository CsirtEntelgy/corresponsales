package com.isban.corresponsalia.dao.corresponsales;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.corresponsales.BeanAltaModificaComisiones;
import com.isban.corresponsalia.comunes.Utils;
import com.isban.corresponsalia.comunes.UtilsCadenas;
import com.isban.dataaccess.DataAccess;
import com.isban.dataaccess.channels.cics.dto.RequestMessageCicsDTO;
import com.isban.dataaccess.channels.cics.dto.ResponseMessageCicsDTO;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.ExceptionDataAccess;


/**
 * The Class DAOAltaModificacionComisionesDLB1Impl.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOAltaModificacionComisionesDLB1Impl extends ArchitechEBE implements DAOAltaModificacionComisionesDLB1{

	/** The TRA n390. */
	private static final String TRAN390  = "DLB1";
	
	/** The codoper. */
	private static final String CODOPER  = "AltaMantenimientoComisionesDLB1";
	
	/** The arrg resultado. */
	transient private String[] arrgResultado;
	
	/**
	 * Modificacion comisiones.
	 *
	 * @param beanAltaModificacion the bean alta modificacion
	 * @param psession the psession
	 * @return the bean error
	 */
	@Override
	public BeanError modificacionComisiones(
			BeanAltaModificaComisiones beanAltaModificacion,
			ArchitechSessionBean psession) {
		info("Inicia DAO Modificacion Alta Comisiones");
		final BeanError beanRespuesta = new BeanError();
		
		String tramaSalida;
		ejecutaTransaccion(beanAltaModificacion);
		tramaSalida = arrgResultado[2];
		if(contieneFormatoExitoso(tramaSalida)) {
			info("Operacion Exitosa");
			desentrama(tramaSalida);
		} else {
			return UtilsCadenas.desentramaError(tramaSalida);
		}
			
		info("Finaliza DAO Modificacion Alta Comisiones:" + beanRespuesta.getCodigoError());
		return beanRespuesta;
	}
	
	/**
	 * Contiene formato exitoso.
	 *
	 * @param tramaSalida the trama salida
	 * @return true, if successful
	 */
	private boolean contieneFormatoExitoso(String tramaSalida){
		
		if(tramaSalida.indexOf("@AVDLA0002") > -1 || tramaSalida.indexOf("@AVDLA0003") > -1 || tramaSalida.indexOf("@AVDLA0001") > -1){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Ejecuta la transaccion DLB1 Consulta Comisiones.
	 *
	 * @param beanAltaModificacion the bean alta modificacion
	 * @return the string[]
	 */
	public String[] ejecutaTransaccion(BeanAltaModificaComisiones beanAltaModificacion){
    	
    	info("Ejecutando DAOConsultaComisionesDLB1Impl->"+ beanAltaModificacion.getTipoOperacion());
    	arrgResultado = new String[3];
    	ResponseMessageCicsDTO responseDTO = new ResponseMessageCicsDTO();
		
		try {
			final RequestMessageCicsDTO requestDTO  = new RequestMessageCicsDTO();
			final DataAccess             dataAccess  = DataAccess.getInstance(requestDTO,getLoggingBean());
			
			requestDTO.setTransaction(TRAN390);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setMessage(generaTramaEntrada(beanAltaModificacion));
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
	 * Agrega la cabecera y arma la trama de entrada DLB1.
	 *
	 * @param beanConsulta the bean consulta
	 * @return the string
	 */
	private String generaTramaEntrada(BeanAltaModificaComisiones beanConsulta){
		final StringBuffer tramaEntrada = new StringBuffer();

		
    		tramaEntrada.append(Utils.rpad(beanConsulta.getTipoOperacion(), 
    				" ", 1));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getEntidad(), 
    				" ", 4));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getCanalCorresponsal(), 
    				" ", 2));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getIdentificacionCorresponsal(), 
    				" ", 4));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getRegionSucursal(), 
    				" ", 4));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getZonaSucursal(), 
    				" ", 5));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getIdentificacionSucursal(), 
    				" ", 10));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getClaveReferenteTipoOperacion(), 
    				" ", 6));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getNivelComision(), 
    				" ", 2));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getInicioVigencia(), 
    				" ", 10));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getFinVigencia(), 
    				" ", 10));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getImporteOperacionRangoMaximo(), 
    				" ", 15));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getTipoComision(), 
    				" ", 3));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getImporteComisionTotal(), 
    				" ", 15));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getImporteComisionBancoMontoFijo(), 
    				" ", 15));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getImporteComisionCorresponsalMontoFijo(), 
    				" ", 15));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getImporteComisionClienteMontoFijo(), 
    				" ", 15));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getImporteComisionBancoPorcentaje(), 
    				" ", 5));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getImporteComisionCorresponsalPorcentaje(), 
    				" ", 5));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getImporteComisionClientePorcentaje(), 
    				" ", 5));
		
    		
		info("Sale de generar la trama :" + tramaEntrada.toString());
		return tramaEntrada.toString();
		
	}
	
	/**
	 * Desentrama la respuesta de 390.
	 *
	 * @param trama the trama
	 */
	private void desentrama(String trama) {
		final BeanError beanRespuesta = new BeanError();
		final String formatoExito = "@AV";
		beanRespuesta.setCodigoError(trama.substring(
				trama.indexOf(formatoExito)+3, trama.indexOf(formatoExito)+11).trim());
		beanRespuesta.setMsgError(trama.substring(
				trama.indexOf(formatoExito)+11, trama.length()-2));
	}

}
