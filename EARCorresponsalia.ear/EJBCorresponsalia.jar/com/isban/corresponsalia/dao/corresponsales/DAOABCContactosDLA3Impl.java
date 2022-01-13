package com.isban.corresponsalia.dao.corresponsales;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.corresponsales.BeanAltaContactos;
import com.isban.corresponsalia.comunes.Utils;
import com.isban.corresponsalia.comunes.UtilsCadenas;
import com.isban.dataaccess.DataAccess;
import com.isban.dataaccess.channels.cics.dto.RequestMessageCicsDTO;
import com.isban.dataaccess.channels.cics.dto.ResponseMessageCicsDTO;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.ExceptionDataAccess;



/**
 * The Class DAOABCContactosDLA3Impl.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOABCContactosDLA3Impl extends ArchitechEBE implements DAOABCContactosDLA3 {
	
	/** The Constant AVDL. */
	private static final String AVDL ="@AVDLA000";
	/** The TRA n390. */
	private static final String TRAN390  = "DLA3";
	
	/** The codoper. */
	private static final String CODOPER  = "ABMContactosDLA3";
	
	/** The resultado consulta. */
	//private ArrayList<String[]> resultadoConsulta;
	
	/** The arrg resultado. */
	transient private String[] arrgResultado;
	

	
	/**
	 * alta Contacto
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the bean ArchitechSessionBean 
	 * @return the BeanError
	 */
	@Override
	public BeanError altaContacto(BeanAltaContactos beanConsulta, ArchitechSessionBean psession) {
		
		info("Inicia DAO Consulta Corresponsalia");
		
		BeanError respuesta = null;
		String codigoError;
		String tramaSalida;
		ejecutaTransaccion(beanConsulta);
		
		codigoError = arrgResultado[0];		
		tramaSalida = arrgResultado[2];
		
		if (tramaSalida.indexOf("@ER") >-1) {
			respuesta = UtilsCadenas.desentramaError(tramaSalida);
		} else {
			info("Consulta Exitosa");
			respuesta = desentrama(tramaSalida);
		}

		info("Finaliza DAO Consulta Corresponsalia :" + codigoError);
		return respuesta;
	}
	
	/**
	 * Ejecuta la transaccion DLA0I Consulta de Corresponsalia.
	 *
	 * @param beanConsulta the bean consulta
	 * @return the string[]
	 */
	public String[] ejecutaTransaccion(BeanAltaContactos beanConsulta){
    	
    	info("Ejecutando DAOConsultaCorresponsaliaDLA0->consultaCorresponsalia");
    	arrgResultado = new String[3];
    	ResponseMessageCicsDTO responseDTO = new ResponseMessageCicsDTO();
		
		try {
			final RequestMessageCicsDTO requestDTO  = new RequestMessageCicsDTO();
			final DataAccess             dataAccess  = DataAccess.getInstance(requestDTO,getLoggingBean());
			
			requestDTO.setTransaction(TRAN390);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setMessage(generaTramaEntrada(beanConsulta));
			info("TRAMA ENTRADA : " + requestDTO.getMessage());
			responseDTO = (ResponseMessageCicsDTO) dataAccess.execute(getIdCanalDAO("CICS_CORRESPONSALIA"));
			info("TRAMA SALIDA -: " + responseDTO.getResponseMessage());
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
			arrgResultado[0] = "ERRDA01";
			arrgResultado[1] = e.getMessage();
			arrgResultado[2] = e.getMessage();			
		}
		return arrgResultado.clone();
	}
	
	/**
	 * Agrega la cabecera y arma la trama de entrada DLA2.
	 *
	 * @param beanConsulta the bean consulta
	 * @return the string
	 */
	private String generaTramaEntrada(BeanAltaContactos beanConsulta){
		final StringBuffer tramaEntrada = new StringBuffer();

    		tramaEntrada.append(Utils.rpad(beanConsulta.getEntidad(), 
    				" ", 4));
			tramaEntrada.append(Utils.rpad(beanConsulta.getCanal(), 
    				" ", 2));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getIdCorresponsal(), 
    				" ", 4));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getZonaUbicacion(), 
    				" ", 5));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getSeqId(), 
    				" ", 5));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getEstatus(), 
    				" ", 3));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getNombre(), 
    				" ", 60));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getPuesto(), 
    				" ", 20));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getArea(), 
    				" ", 10));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getCalle(), 
    				" ", 20));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getNumExt(), 
    				" ", 5));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getNumInt(), 
    				" ", 5));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getColonia(), 
    				" ", 20));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getDelegMcp(), 
    				" ", 20));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getCiudad(), 
    				" ", 20));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getEntidadFed(), 
    				" ", 20));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getCodPostal(), 
    				" ", 8));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getTelOficina(), 
    				" ", 10));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getTelMovil(), 
    				" ", 10));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getTelFax(), 
    				" ", 10));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getEmailPrin(), 
    				" ", 20));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getEmailAlt(), 
    				" ", 20));
    		tramaEntrada.append(Utils.rpad(beanConsulta.getTipoOper(), 
    				" ", 1));

		info("Sale de generar la trama :" + tramaEntrada.toString());
		return tramaEntrada.toString();
		
	}
	
	/**
	 * Desentrama la respuesta de 390.
	 *
	 * @param trama the trama
	 * @return the bean error
	 */
	private BeanError desentrama(String trama) {

		final BeanError respuesta = new BeanError();
		if(trama.indexOf(AVDL) > -1){
			trama = trama.substring(0, trama.length()-2);
			respuesta.setCodigoError(trama.substring(trama.indexOf(AVDL) + 1, trama.indexOf(AVDL) + 9));
			respuesta.setMsgError(trama.substring(trama.indexOf(AVDL) + 10));
		}
		return respuesta;
	}

}
