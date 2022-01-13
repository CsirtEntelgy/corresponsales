package com.isban.corresponsalia.dao.canalcorresponsalia;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCatalogo;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.comunes.BeanOperacionCatalogo;
import com.isban.corresponsalia.comunes.Facultades;
import com.isban.corresponsalia.comunes.Utils;
import com.isban.corresponsalia.comunes.UtilsCadenas;
import com.isban.dataaccess.DataAccess;
import com.isban.dataaccess.channels.cics.dto.RequestMessageCicsDTO;
import com.isban.dataaccess.channels.cics.dto.ResponseMessageCicsDTO;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.ExceptionDataAccess;


/**
 * The Class DAOConsultaOperacionesCatalogoDLC6Impl.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOConsultaOperacionesCatalogoDLC6Impl extends ArchitechEBE implements DAOConsultaOperacionesCatalogoDLC6 {

	/** The id canal. */
	transient final private String idCanal = getIdCanalDAO("CICS_CORRESPONSALIA");
	
	/** The TRA n390. */
	transient final private String TRAN390  = "DLC6";
	
	/** The codoper. */
	transient final private String CODOPER  = "ConsultaOperacionesCatalogoDLC6";
	
	/** The arrg resultado. */
	transient private String[] arrgResultado;
	
	/** The bean resultado consulta. */
	transient private List<BeanOperacionCatalogo> beanResultadoConsulta;
	/**
	 * Consulta operaciones catalogo.
	 *
	 * @param beanConsultaOperCatalogo the bean consulta oper catalogo
	 * @param psession the psession
	 * @return the object
	 */
	@Override
	public Object consultaOperacionesCatalogo(
			BeanConsultaOperacionesCatalogo beanConsultaOperCatalogo,
			ArchitechSessionBean psession) {
		BeanError beanError = new BeanError();
    	info("Ejecutando DAOConsultaOperacionesCatalogoDLC6->ConsultaOperaciones");
    	//String codigoError;
		//String mensajeError;
		String tramaSalida;
		debug("Validando facultades del usuario sobre transaccion " + TRAN390);
    	if(!Facultades.getInstanceOf().trasaccionAutorizada(psession.getPerfil(), TRAN390)){
    		debug("El usuario no tiene facultades sobre transaccion " + TRAN390);
    		beanError.setCodigoError(Facultades.CODIGO_ERROR);
    		beanError.setMsgError(Facultades.MENSAJE_ERROR);
    		return beanError;
    	}
		ejecutaTransaccion(beanConsultaOperCatalogo);
		
		//codigoError = arrgResultado[0];
		//mensajeError = arrgResultado[1];
		tramaSalida = arrgResultado[2];
		if(tramaSalida == null) {
    		beanError.setCodigoError("DLE0001");
    		beanError.setMsgError("Servicio No Disponible");
    		return beanError;			
		}
		
		beanError = UtilsCadenas.desentramaError(tramaSalida);
		if(!beanError.getCodigoError().equals("")){
			return UtilsCadenas.desentramaError(tramaSalida);
		} else {
			desentrama(tramaSalida);
		}	
		
		info("Finaliza DAOConsultaOperacionesCatalogoDLC6->ConsultaOperaciones");
		return beanResultadoConsulta;
	}
	
	/**
	 * Ejecuta la transaccion DLC6 Consula Operaciones Catalogo.
	 *
	 * @param beanConsulta the bean consulta
	 * @return the string[]
	 */
	public String[] ejecutaTransaccion(BeanConsultaOperacionesCatalogo beanConsulta){
    	
    	info("Ejecutando DAOConsultaCorresponsaliaDLA0->consultaCorresponsalia");
    	arrgResultado = new String[3];
    	ResponseMessageCicsDTO responseDTO = null;
		
		try {
			final RequestMessageCicsDTO requestDTO  = new RequestMessageCicsDTO();
			final DataAccess             dataAccess  = DataAccess.getInstance(requestDTO,getLoggingBean());
			
			requestDTO.setTransaction(TRAN390);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setMessage(generaTramaEntrada(beanConsulta));
			info("TRAMA ENTRADA : " + requestDTO.getMessage());
			responseDTO = (ResponseMessageCicsDTO) dataAccess.execute(idCanal);
			info("TRAMA SALIDA : " + responseDTO.getResponseMessage());
			debug("Codigo de Error     :" + responseDTO.getCodeError());
			debug("Mensaje de Error    :" + responseDTO.getMessageError());
			debug("Mensaje de Respuesta:" + responseDTO.getResponseMessage());
			if(!responseDTO.getCodeError().equals("")) {
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
	 * Arma la trama de entrada DLC6.
	 *
	 * @param beanConsulta the bean consulta
	 * @return the string
	 */
	private String generaTramaEntrada(BeanConsultaOperacionesCatalogo beanConsulta) {
    	final StringBuffer trama = new StringBuffer();
    		trama.append(Utils.rpad(beanConsulta.getTipoOperacion(), 
    				" ", 1));
    		trama.append(Utils.rpad(beanConsulta.getEntidad(), 
    				" ", 4));
    		trama.append(Utils.rpad(beanConsulta.getCanalCorresponsal(), 
    				" ", 2));
    		trama.append(Utils.rpad(beanConsulta.getIdentificacionCorresponsal(), 
    				" ", 4));
    		trama.append(Utils.rpad(beanConsulta.getNivelRegistroOperacion(), 
    				" ", 2));
    		trama.append(Utils.rpad(beanConsulta.getConsecutivoNivelCorresponsal(), 
    				" ", 6));
    		trama.append(Utils.rpad(beanConsulta.getIndicadorPaginacion(), 
    				" ", 1));
    		trama.append(Utils.rpad(beanConsulta.getIndicadorAvanceRetroceso(), 
    				" ", 1));
    		trama.append(Utils.rpad(beanConsulta.getIndicadorConsecutivoAvance(), 
    				" ", 6));
    		trama.append(Utils.rpad(beanConsulta.getIndicadorCorresponsalAvance(), 
    				" ", 4));
    		trama.append(Utils.rpad(beanConsulta.getIndicadorNivelRegistroAvance(), 
    				" ", 2));
    		trama.append(Utils.rpad(beanConsulta.getIndicadorConsecutivoRetroceso(), 
    				" ", 6));
    		trama.append(Utils.rpad(beanConsulta.getIndicadorCorresponsalRetroceso(), 
    				" ", 4));
    		trama.append(Utils.rpad(beanConsulta.getIndicadorNivelRegistroRetroceso(), 
    				" ", 2));
    	debug("Longitud Trama:" + trama.length());    	
    	return trama.toString();
    }
	
	/**
	 * Desentrama la respuesta de 390.
	 *
	 * @param trama the trama
	 */
	private void desentrama(String trama) {

		BeanOperacionCatalogo beanConsulta= null;
		beanResultadoConsulta = new ArrayList<BeanOperacionCatalogo>();
		String tramaRespuesta ="";
		String[] argDesentramado;
		//Dummy
//		String codigoExito = "Ó@DCDLMA030 P";
		argDesentramado = trama.split("Ó@DCDLMC610 P");
		for (int i = 1; i < argDesentramado.length; i++) {
			
			tramaRespuesta = argDesentramado[i];
			info("---"+tramaRespuesta);
			if(tramaRespuesta.indexOf("Ó@DCDLMC620 P") > -1){
				tramaRespuesta = tramaRespuesta.substring(0,tramaRespuesta.indexOf("Ó@DCDLMC620 P"));
			}
			if(tramaRespuesta.length()==124){
				beanConsulta = new BeanOperacionCatalogo();
				beanConsulta.setFechaAltaOperacion(tramaRespuesta.substring(0, 10));
				beanConsulta.setConsecutivoOperacionNivelCanal(tramaRespuesta.substring(10, 16));
				beanConsulta.setDescripcionCorta(tramaRespuesta.substring(16, 36));
				beanConsulta.setDescripcionLarga(tramaRespuesta.substring(36, 86));
				beanConsulta.setEquivalenciaTipoOeracionUno(tramaRespuesta.substring(86, 96));
				beanConsulta.setEquivalenciaTipoOeracionDos(tramaRespuesta.substring(96, 106));
				beanConsulta.setEquivalenciaTipoOeracionTres(tramaRespuesta.substring(106, 116));
				beanConsulta.setClaveTransaccionAsociada(tramaRespuesta.substring(116, 120));
				beanConsulta.setClaveTransaccionAsociadaAnterior(tramaRespuesta.substring(120, 124));
				beanResultadoConsulta.add(beanConsulta);
			}			
		}
		info("Sale desentramado:" + beanResultadoConsulta.size());
	}
}
