package com.isban.corresponsalia.dao.consultas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.consultas.BeanConsultaBitacora;
import com.isban.corresponsalia.beans.consultas.BeanRegistroConsultaBitacora;
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
 * Session Bean implementation class DAOConsultaBitacoraSinReferenciaDLB6Imp.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOConsultaBitacoraConReferenciaDLB6Imp extends ArchitechEBE implements DAOConsultaBitacoraConReferenciaDLB6 {

	/** The TRA n390. */
	transient private static final String TRAN390  = "DLB6";
	
	/** The codoper. */
	transient private static final String CODOPER  = "ConsultaBitacoraDLB5";	
	
	/** The id canal. */
	transient private static String idCanal = Alias.getAlias("CORRESPONSALIA");
	
	/**
	 * Consulta bitacora.
	 *
	 * @param beanArq the bean arq
	 * @param beanConsulta the bean consulta
	 * @return the object
	 */
    public Object consultaBitacora(ArchitechSessionBean beanArq, BeanConsultaBitacora beanConsulta){
    	
    	debug("DAOConsultaBitacoraSinReferenciaDLB5->consultaBitacora");
    	
    	final BeanError beanError = new BeanError();
    	
    	debug("Validando facultades del usuario sobre transaccion " + TRAN390);
    	if(!Facultades.getInstanceOf().trasaccionAutorizada(beanArq.getPerfil(), TRAN390)){
    		debug("El usuario no tiene facultades sobre transaccion " + TRAN390);
    		beanError.setCodigoError(Facultades.CODIGO_ERROR);
    		beanError.setMsgError(Facultades.MENSAJE_ERROR);
    		return beanError;
    	}

    	
    	List<BeanRegistroConsultaBitacora> listaRegistrossConsultaBitacora = null;
		final Map<String,Object>                 objReturn                       = new HashMap<String,Object>();
		try {
   	
			final RequestMessageCicsDTO  requestDTO  = new RequestMessageCicsDTO();
			ResponseMessageCicsDTO responseDTO = new ResponseMessageCicsDTO();
			final DataAccess        dataAccess  = DataAccess.getInstance(requestDTO,getLoggingBean());

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
					String lstrTramaDesentramar     = TratamientoTrama.getTramaTratamiento(lstrTrama);
					String lstrReferenciaAvanzar    = "";
					String lstrReferenciaRetroceder = "";
					if(TratamientoTrama.tramaTieneFormatoPaginacion(lstrTramaDesentramar, "@DCDLMB620 P")){
						debug("La trama contiene un formato de paginacion....");
						final String lstrTramaPaginacion      = TratamientoTrama.obtenerTramaPaginacion(lstrTramaDesentramar,  "@DCDLMB620 P");
						debug("Trama Paginacion:" + lstrTramaPaginacion);
						if(lstrMsgAviso.indexOf("NO HAY MAS DATOS") < 0 ){
							lstrReferenciaAvanzar    = getReferenciaAvanzar(lstrTramaPaginacion);					
						}else {
							lstrReferenciaAvanzar    = "";
						}						

						lstrReferenciaRetroceder = getReferenciaRetroceder(lstrTramaPaginacion);
						debug("Referencia Avanzar   :" + lstrReferenciaAvanzar);
						debug("Referencia Retroceder:" + lstrReferenciaRetroceder);
						lstrTramaDesentramar = TratamientoTrama.eliminarTramaPaginacion(lstrTramaDesentramar, "@DCDLMB620 P");
					}

					listaRegistrossConsultaBitacora = getRegistros(TratamientoTrama.getTramaTratamiento(lstrTrama));

					objReturn.put("registros"           , listaRegistrossConsultaBitacora);
					objReturn.put("referenciaAvanzar"   , lstrReferenciaAvanzar);
					objReturn.put("referenciaRetroceder", lstrReferenciaRetroceder);
					objReturn.put("CodigoAviso" , lstrCodAviso);
					objReturn.put("MensajeAviso", lstrMsgAviso);					
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
    private String getMessage(BeanConsultaBitacora beanConsulta){
       	String message = "";
    	final StringBuffer trama = new StringBuffer();
    		/*
			4	Req IIDCORR	Identificacion Corresponsal	
			10	Opc IIDESUC	Codigo de Identificacion de la Sucursal	
			10	Req IFEALTA	Fecha de ALTA en bitacora, necesaria para iniciar el calculo de de dias T+N	
			3	Req ICVSTAT	Clave del estados de Transicion de la Operación (Registrada, Conciliada,Compensada etc.)	
			1	Req IINDPAG	Indicador de Paginación (S/N)	
			1	Req IINDAVA	Indicador de Paginación A = Avanza / R = Retrocede	
			10	Opc IFHOPEP	Fecha en que se realizo la operación para la paginación	
			20	Opc IREFOP1	Numero de Referencia correspondiente a la operación para la paginación Atrás	
			20	Opc IREFOP2	Numero de Referencia correspondiente a la operación para la paginación Adelante	
    		*/
   	   		trama.append(Utils.rpad(beanConsulta.getIdCorresponsal()    , " ", 4));
    		trama.append(Utils.rpad(beanConsulta.getCodIdSucursal()     , " ", 10));
    		trama.append(Utils.rpad(beanConsulta.getFechaAlta()         , " ", 10));
    		trama.append(Utils.rpad(beanConsulta.getEstatus()           , " ", 3));
    		debug("Indicador Paginacion:" + beanConsulta.getIndicadorPag().trim());
    		if("".equals(beanConsulta.getIndicadorPag().trim())){
    			trama.append(Utils.rpad("N", " ", 1));
        		trama.append(Utils.rpad("" , " ", 1));
       	   		trama.append(Utils.rpad("" , " ", 10));
        		trama.append(Utils.rpad("" , " ", 20));
        		trama.append(Utils.rpad("" , " ", 20));
          	}
    		else{   			
    			trama.append(Utils.rpad("P"                                   , " ", 1));
        		trama.append(Utils.rpad(beanConsulta.getIndicadorPag()        , " ", 1));
        		trama.append(Utils.rpad(beanConsulta.getFechaAlta()           , " ", 10));
        		if("A".equalsIgnoreCase(beanConsulta.getIndicadorPag())){
            		trama.append(Utils.rpad("", " ", 20));
            		trama.append(Utils.rpad(beanConsulta.getReferenciaAvanzar(), " ", 20));        			
        		}else
            		if("B".equalsIgnoreCase(beanConsulta.getIndicadorPag())){
                		trama.append(Utils.rpad(beanConsulta.getReferenciaRetroceder(), " ", 20));
                		trama.append(Utils.rpad("", " ", 20));        			
            		}else {
                		trama.append(Utils.rpad("", " ", 20));
                		trama.append(Utils.rpad("", " ", 20));            			
            		}            			
    		}
     	
    	debug("Longitud Trama:" + trama.length());
    	
    	message = trama.toString();
    	debug("Trama:" + message);
    	
    	return message;
    }

    /**
     * Gets the registros.
     *
     * @param reesponseMessage the reesponse message
     * @return the registros
     */
    private List<BeanRegistroConsultaBitacora> getRegistros(String reesponseMessage){
    	
    	final List<BeanRegistroConsultaBitacora> listaRegistrossConsultaBitacora = new ArrayList<BeanRegistroConsultaBitacora>();
    	   	
    	final List<String> listaRegistros = TratamientoTrama.parteTrama(reesponseMessage, "@DCDLMB610 P");
    	
    	debug("Numero de Registros obtenidos:" + listaRegistros.size());
    	for(String registro:listaRegistros){
    		debug("Registro:" + registro);
    		final BeanRegistroConsultaBitacora beanRespuestaTemp = new BeanRegistroConsultaBitacora();    		
 		
    		beanRespuestaTemp.setEntidadBanco               (registro.substring(0   ,4));         
    		beanRespuestaTemp.setIdCanal                    (registro.substring(4   ,6));      
    		beanRespuestaTemp.setIdCorresponsal             (registro.substring(6   ,10));  
    		beanRespuestaTemp.setIdSucursal                 (registro.substring(10  ,20));    
    		beanRespuestaTemp.setNumRefOper                 (registro.substring(20  ,40));     
    		beanRespuestaTemp.setFolioOperacion             (registro.substring(40  ,60));       
    		beanRespuestaTemp.setFechaAlta                  (registro.substring(60  ,70));            
    		beanRespuestaTemp.setNumeroTarjeta              (registro.substring(70  ,92));        
    		beanRespuestaTemp.setClaveTranOper              (registro.substring(92  ,95));     
    		beanRespuestaTemp.setClaveRefTran               (registro.substring(95  ,99));   
    		beanRespuestaTemp.setClaveOperCanal             (registro.substring(99  ,105));     
    		beanRespuestaTemp.setClaveOperCorresponsal      (registro.substring(105 ,115));   
    		beanRespuestaTemp.setIdCaja                     (registro.substring(115 ,131));          
    		beanRespuestaTemp.setFechaConsiliacion          (registro.substring(131 ,141));    
    		beanRespuestaTemp.setFechaCompensacion          (registro.substring(141 ,151));    
    		beanRespuestaTemp.setNumRefPampa                (registro.substring(151 ,175));         
    		beanRespuestaTemp.setNumCuentaCheques           (registro.substring(175 ,195));     
    		beanRespuestaTemp.setFechaOperacion             (registro.substring(195 ,205));       
    		beanRespuestaTemp.setHoraOperacion              (registro.substring(205 ,211));        
    		beanRespuestaTemp.setDivisaOperacion            (registro.substring(211 ,214));      
    		beanRespuestaTemp.setImporteOperacion           (registro.substring(214 ,229));     
    		beanRespuestaTemp.setNumeroFactura              (registro.substring(229 ,233));           
    		beanRespuestaTemp.setIdEntidadAdquiriente       (registro.substring(233 ,244));       
    		beanRespuestaTemp.setImporteComisionBanco       (registro.substring(244 ,259));    
    		beanRespuestaTemp.setImporteComisionCorresponsal(registro.substring(259 ,274));   
    		beanRespuestaTemp.setImporteComisionCliente     (registro.substring(274 ,289));  
    		beanRespuestaTemp.setImporteIvaBanco            (registro.substring(289 ,304));         
    		beanRespuestaTemp.setImporteIvaCorresponsal     (registro.substring(304 ,319));  
    		beanRespuestaTemp.setImporteIvaCliente          (registro.substring(319 ,334));       
    		beanRespuestaTemp.setTerminalCompatible         (registro.substring(334 ,337));    
    		beanRespuestaTemp.setDatosAdicionales           (registro.substring(337 ,439));     
    		beanRespuestaTemp.setNombreLocalizacion         (registro.substring(439 ,479));    
    		beanRespuestaTemp.setCampoObservaciones1        (registro.substring(479 ,529));   
    		beanRespuestaTemp.setCampoObservaciones2        (registro.substring(529 ,579));  
    		beanRespuestaTemp.setDescOperacion              (registro.substring(579 ,599));
  				
    		listaRegistrossConsultaBitacora.add(beanRespuestaTemp);
    	}
    	  	   	
    	return listaRegistrossConsultaBitacora;
    }
    
    /**
     * Gets the referencia avanzar.
     *
     * @param tramaPaginacion the trama paginacion
     * @return the referencia avanzar
     */
    private String getReferenciaAvanzar(String tramaPaginacion){
    	String lstrReferencia = "";
   		lstrReferencia = tramaPaginacion.substring(30,50).trim();
    	return lstrReferencia;
    }
    
    /**
     * Gets the referencia retroceder.
     *
     * @param tramaPaginacion the trama paginacion
     * @return the referencia retroceder
     */
    private String getReferenciaRetroceder(String tramaPaginacion){
    	String lstrReferencia = "";
   		lstrReferencia =  tramaPaginacion.substring(10, 30).trim();
    	return lstrReferencia;
    }   
}