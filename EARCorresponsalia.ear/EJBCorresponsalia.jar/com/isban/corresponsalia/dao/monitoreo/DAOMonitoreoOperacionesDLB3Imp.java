package com.isban.corresponsalia.dao.monitoreo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.monitoreo.BeanConsultaMonitoreoOperaciones;
import com.isban.corresponsalia.beans.monitoreo.BeanMonitoreoOperaciones;
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
 * Session Bean implementation class DAOMonitoreoOperacionesDLB3Imp.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOMonitoreoOperacionesDLB3Imp extends ArchitechEBE implements DAOMonitoreoOperacionesDLB3 {

	/** The TRA n390. */
	transient private static final String TRAN390  = "DLB3";
	/** The id canal. */
	transient private final String idCanal = Alias.getAlias("CORRESPONSALIA");
	
	
	/** The codoper. */
	transient private static final String CODOPER  = "ConsultaMonitoreoOperacionesDLB3";
	
	/**
	 * Monitoreo operaciones.
	 *
	 * @param beanConsulta the bean consulta
	 * @param beanArq the bean arq
	 * @return the object
	 */
	@Override
	public Object monitoreoOperaciones(BeanConsultaMonitoreoOperaciones beanConsulta, ArchitechSessionBean beanArq){
    	debug("DAOMonitoreoOperacionesDLB3->monitoreoOperaciones");

    	final BeanError beanError = new BeanError();
    	
    	debug("Validando facultades del usuario sobre transaccion " + TRAN390);
    	if(!Facultades.getInstanceOf().trasaccionAutorizada(beanArq.getPerfil(), TRAN390)){
    		debug("El usuario no tiene facultades sobre transaccion " + TRAN390);
    		beanError.setCodigoError(Facultades.CODIGO_ERROR);
    		beanError.setMsgError(Facultades.MENSAJE_ERROR);
    		return beanError;
    	}
    	List<BeanMonitoreoOperaciones> listaRegistros = null;
		final HashMap<String,Object>              objReturn      = new HashMap<String,Object>();
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
					return beanError;
				}
				else{
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
						return beanError;
					}

					String lstrTramaDesentramar = TratamientoTrama.getTramaTratamiento(lstrTrama);
					String lstrReferenciaAvanzar    = "";
					String lstrOperacionAvanzar = "";
					String lstrHoraAvanzar = "";
					String lstrReferenciaRetroceder = "";
					String lstrOperacionRetroceder = "";
					String lstrHoraRetroceder = "";
//					if(TratamientoTrama.tramaTieneFormatoPaginacion(lstrTramaDesentramar, "@DCDLMB320 P")&& lstrCodAviso.equals("DLA0005")){
					if(TratamientoTrama.tramaTieneFormatoPaginacion(lstrTramaDesentramar, "@DCDLMB320 P")){
						debug("La trama contiene un formato de paginacion....");
						final String lstrTramaPaginacion      = TratamientoTrama.obtenerTramaPaginacion(lstrTramaDesentramar,  "@DCDLMB320 P");
						debug("Trama Paginacion:" + lstrTramaPaginacion);
						beanError.setCodigoError(lstrCodAviso);
						beanError.setMsgError(lstrMsgAviso);
						lstrReferenciaAvanzar    = getReferenciaAvanzar(lstrTramaPaginacion);
						lstrOperacionAvanzar     = getOperacioAvanzar(lstrTramaPaginacion);
						lstrHoraAvanzar          = getHoraAvanzar(lstrTramaPaginacion);
						lstrReferenciaRetroceder = getReferenciaRetroceder(lstrTramaPaginacion);
						lstrOperacionRetroceder  = getOperacionRetroceder(lstrTramaPaginacion);
						lstrHoraRetroceder       = getHoraRetroceder(lstrTramaPaginacion);
						debug("Referencia Avanzar   :" + lstrReferenciaAvanzar);
						debug("Operacion Avanzar   :" + lstrOperacionAvanzar);
						debug("Hora Avanzar   :" + lstrHoraAvanzar);
						debug("Referencia Retroceder:" + lstrReferenciaRetroceder);
						debug("Operacion Retroceder:" + lstrOperacionRetroceder);
						debug("Hora Retroceder:" + lstrHoraRetroceder);
						lstrTramaDesentramar = TratamientoTrama.eliminarTramaPaginacion(lstrTramaDesentramar, "@DCDLMB320 P");
					}
					debug("Trama desentramar Registros:" + lstrTramaDesentramar);
					listaRegistros = getRegistros(lstrTramaDesentramar);
					
					objReturn.put("error"           , beanError);
					objReturn.put("registros"           , listaRegistros);
					objReturn.put("referenciaAvanzar"   , lstrReferenciaAvanzar);
					objReturn.put("operacionAvanzar"    , lstrOperacionAvanzar);
					objReturn.put("horaAvanzar"         , lstrHoraAvanzar);
					objReturn.put("referenciaRetroceder", lstrReferenciaRetroceder);
					objReturn.put("operacionRetroceder" , lstrOperacionRetroceder);
					objReturn.put("horaRetroceder"      , lstrHoraRetroceder);
				}

			}
			else{
				beanError.setCodigoError(responseDTO.getCodeError());
				beanError.setCodigoError(responseDTO.getMessageError());
				return beanError;			
			}

		} 
		catch (ExceptionDataAccess e) {
			showException(e);
		}
	
		return objReturn;   	
	}
	
    /**
     * Gets the message.
     *
     * @param beanConsulta the bean consulta
     * @return the message
     */
    private String getMessage(BeanConsultaMonitoreoOperaciones beanConsulta){
       	String message = "";
    	final StringBuffer trama = new StringBuffer();
    		trama.append(Utils.rpad(beanConsulta.getIdentificacionCorresponsal()              ,	" ", 4));
    		trama.append(Utils.rpad(beanConsulta.getCodigoIdentificacionSucursal()            , " ", 10));
    		trama.append(Utils.rpad(beanConsulta.getHoraInicio()                              , " ", 6));
    		trama.append(Utils.rpad(beanConsulta.getHoraFin()                                 , " ", 6));
    		trama.append(Utils.rpad(beanConsulta.getClaveReferenteTipoOperacion()             ,	" ", 6));
    		trama.append(Utils.rpad(beanConsulta.getImporteInicial()                          , " ", 15));
    		trama.append(Utils.rpad(beanConsulta.getImporteFinal()                            , " ", 15));
    		trama.append(Utils.rpad(beanConsulta.getIndicadorPaginacion()                     ,	" ", 1));
    		trama.append(Utils.rpad(beanConsulta.getIndicadorPaginacionAvanzaRetrocede()      , " ", 1));
    		trama.append(Utils.rpad(beanConsulta.getCodigoIdentificacionSucursalIndicadorPag(),	" ", 10));
    		trama.append(Utils.rpad(beanConsulta.getClaveReferenteTipoOperacionIndicadorPag() ,	" ", 6));
    		trama.append(Utils.rpad(beanConsulta.getHora()                                    , " ", 6));
    	debug("Longitud Trama:" + trama.length());
    	message = trama.toString();
    	debug("Trama:" + message);
    	return message;
    }

    /**
     * Gets the referencia avanzar.
     *
     * @param tramaPaginacion the trama paginacion
     * @return the referencia avanzar
     */
    private String getReferenciaAvanzar(String tramaPaginacion){
    	return tramaPaginacion.substring(0, 10).trim();
    }
    
    /**
     * Gets the operacio avanzar.
     *
     * @param tramaPaginacion the trama paginacion
     * @return the operacio avanzar
     */
    private String getOperacioAvanzar(String tramaPaginacion){
    	return tramaPaginacion.substring(10, 16).trim();
    }
    
    /**
     * Gets the hora avanzar.
     *
     * @param tramaPaginacion the trama paginacion
     * @return the hora avanzar
     */
    private String getHoraAvanzar(String tramaPaginacion){
    	return tramaPaginacion.substring(16, 22).trim();
    }
    
    /**
     * Gets the referencia retroceder.
     *
     * @param tramaPaginacion the trama paginacion
     * @return the referencia retroceder
     */
    private String getReferenciaRetroceder(String tramaPaginacion){
    	return tramaPaginacion.substring(22, 32).trim();
    }
    
    /**
     * Gets the operacion retroceder.
     *
     * @param tramaPaginacion the trama paginacion
     * @return the operacion retroceder
     */
    private String getOperacionRetroceder(String tramaPaginacion){
    	return tramaPaginacion.substring(32, 38).trim();
    }
    
    /**
     * Gets the hora retroceder.
     *
     * @param tramaPaginacion the trama paginacion
     * @return the hora retroceder
     */
    private String getHoraRetroceder(String tramaPaginacion){
    	return tramaPaginacion.substring(38, 44).trim();
    }
    
    
    /**
     * Gets the registros.
     *
     * @param reesponseMessage the reesponse message
     * @return the registros
     */
    private List<BeanMonitoreoOperaciones> getRegistros(String reesponseMessage){
    	
    	final List<BeanMonitoreoOperaciones> listaRegistros            = new ArrayList<BeanMonitoreoOperaciones>();   	   	
    	final List<String>                        listaRegistrosTratamiento = TratamientoTrama.parteTrama(reesponseMessage, "@DCDLMB310 P");
 
    	debug("Numero de Registros Obtenidos:" + listaRegistrosTratamiento.size());
    	for(String registro:listaRegistrosTratamiento){
    		debug("Registro:" + registro);
    		final BeanMonitoreoOperaciones beanMonitoreoOperaciones = new BeanMonitoreoOperaciones();    		
  		
    		beanMonitoreoOperaciones.setCodigoIdentificacionSucursal(registro.substring(0,10));
    		beanMonitoreoOperaciones.setDescripcionSucursal         (registro.substring(10,50));
    		beanMonitoreoOperaciones.setDescripcionOperacion        (registro.substring(50,70));
    		beanMonitoreoOperaciones.setTotalOperaciones            (registro.substring(70,83));
    		beanMonitoreoOperaciones.setImporteTotalOperaciones     (registro.substring(83,98));  		
			listaRegistros.add(beanMonitoreoOperaciones);
    	}    	  	   	
    	return listaRegistros;
    }  
}
