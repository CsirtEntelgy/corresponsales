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
 * Session Bean implementation class DAOConsultaBitacoraSinReferenciaDLB5Imp.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOConsultaBitacoraSinReferenciaDLB5Imp extends ArchitechEBE implements DAOConsultaBitacoraSinReferenciaDLB5 {

	
	/** The TRA n390. */
	transient private static final String TRAN390  = "DLB5";
	
	/** The codoper. */
	transient private static final String CODOPER  = "ConsultaBitacoraDLB5";	
	
	/** The id canal. */
	transient private final String idCanal = Alias.getAlias("CORRESPONSALIA");
    
    /**
     * Consulta bitacora.
     *
     * @param beanArq the bean arq
     * @param beanConsulta the bean consulta
     * @return the object
     */
    public  Object consultaBitacora(ArchitechSessionBean beanArq, BeanConsultaBitacora beanConsulta){
    	
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
		final Map<String,Object>           objReturn                       = new HashMap<String,Object>();
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
				debug("La transaccion[" + TRAN390 +"] regreso el siguiente aviso...");
				final String lstrCodAviso   = TratamientoTrama.getCodigoAviso(lstrTrama);
				final String lstrMsgAviso   = TratamientoTrama.getMensajeAviso(lstrTrama);
				final String lstrFormatoRes = TratamientoTrama.getFormatoRespuesta(lstrTrama);
				debug("Codigo de Aviso  :" + lstrCodAviso);
				debug("Mensaje de Aviso :" + lstrMsgAviso);
				debug("Formato Respuesta:" + lstrFormatoRes);
				/*if(lstrMsgAviso.indexOf("NO EXISTEN DATOS")>-1){
					beanError.setCodigoError(lstrCodAviso);
					beanError.setMsgError(lstrMsgAviso);
					return beanError;
				}*/

				String lstrTramaDesentramar     = TratamientoTrama.getTramaTratamiento(lstrTrama);
				String lstrReferenciaAvanzar    = "";
				String lstrReferenciaRetroceder = "";
				if(TratamientoTrama.tramaTieneFormatoPaginacion(lstrTramaDesentramar, "@DCDLMB520 P")){
					debug("La trama contiene un formato de paginacion....");
					String lstrTramaPaginacion      = TratamientoTrama.obtenerTramaPaginacion(lstrTramaDesentramar,  "@DCDLMB520 P");
					debug("Trama Paginacion:" + lstrTramaPaginacion);
					if(lstrMsgAviso.indexOf("NO HAY MAS DATOS") < 0 ){
						lstrReferenciaAvanzar    = getReferenciaAvanzar(lstrTramaPaginacion);					
					}else {
						lstrReferenciaAvanzar    = "";
					}						
					lstrReferenciaRetroceder = getReferenciaRetroceder(lstrTramaPaginacion);
					debug("Referencia Avanzar   :" + lstrReferenciaAvanzar);
					debug("Referencia Retroceder:" + lstrReferenciaRetroceder);
					lstrTramaDesentramar = TratamientoTrama.eliminarTramaPaginacion(lstrTramaDesentramar, "@DCDLMB520 P");
				}
				
				listaRegistrossConsultaBitacora = getRegistros(TratamientoTrama.getTramaTratamiento(lstrTrama));
				objReturn.put("registros"           , listaRegistrossConsultaBitacora);
				objReturn.put("referenciaAvanzar"   , lstrReferenciaAvanzar);
				objReturn.put("referenciaRetroceder", lstrReferenciaRetroceder);
				objReturn.put("CodigoAviso" , lstrCodAviso);
				objReturn.put("MensajeAviso", lstrMsgAviso);
			}
			else{
				beanError.setCodigoError(responseDTO.getCodeError());
				beanError.setCodigoError(responseDTO.getMessageError());
				return beanError;			
			}

		} 
		catch (ExceptionDataAccess e) {
			beanError.setCodigoError("DLE0001");
			beanError.setCodigoError(e.getMessage());
			return beanError;
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
			4	4	Req IIDCORR	Identificacion Corresponsal	
			10	10	Opc ICODSUC	Codigo de Identificacion de la Sucursal	
			20	20	Opc IREFOPE	Numero de Referencia correspondiente a la operación	
			20	24	Opc IREFINT	Numero de Referencia interna	
					6	6	OpcICVEOPE	Clave referente al tipo de operación (Pago debito, credito, servicios etc.)	
			10	10	Req IFECOPE	Fecha en que se realizo la operación	
			15	15	Opc IIMPOPE	Importe de la operación	
			1	1	Req IINDPAG	INDICADOR PAGINACION	
			1	1	Req IINDAVA	Indicador de Paginación A = Avanza / R = Retrocede	
			10	10	Opc IFECOPP	Fecha en que se realizo la operación para la paginación	
			20	20	Opc IREFOP1	Numero de Referencia correspondiente a la operación para la paginación Atrás	
			20	20	Opc IREFOP2	Numero de Referencia correspondiente a la operación para la paginación Adelante	
    		*/
   	   		trama.append(Utils.rpad(beanConsulta.getIdCorresponsal()    , " ", 4));
    		trama.append(Utils.rpad(beanConsulta.getCodIdSucursal()     , " ", 10));
    		trama.append(Utils.rpad(beanConsulta.getFolio()             , " ", 20));
    		trama.append(Utils.rpad(beanConsulta.getNumReferenciaOper() , " ", 24));
    		trama.append(Utils.rpad(beanConsulta.getCuentaTarjeta()     , " ", 22));
   	   		trama.append(Utils.rpad(beanConsulta.getClaveTipoOperacion(), " ", 6));
    		trama.append(Utils.rpad(beanConsulta.getFechaAlta()         , " ", 10));
    		trama.append(Utils.lpad(beanConsulta.getImporteOperacion()  , "0", 15));
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
    	final List<String> listaRegistros = TratamientoTrama.parteTrama(reesponseMessage, "@DCDLMB510 P");    	
    	debug("Numero de Registros obtenidos:" + listaRegistros.size());
    	for(String registro:listaRegistros){
    		debug("Registro:" + registro);
    		final BeanRegistroConsultaBitacora beanRespuestaTemp = new BeanRegistroConsultaBitacora();    		
    		final StringBuffer sHora = new StringBuffer();
    		beanRespuestaTemp.setEntidadBanco               (registro.substring(0,	4));
    		beanRespuestaTemp.setIdCanal                    (registro.substring(4,	6));
    		beanRespuestaTemp.setIdCorresponsal             (registro.substring(6,	10));
    		beanRespuestaTemp.setIdSucursal                 (registro.substring(10,	20));
    		beanRespuestaTemp.setFolioOperacion             (registro.substring(20,	40));
    		beanRespuestaTemp.setNumRefOper                 (registro.substring(40,	60));
    		beanRespuestaTemp.setFechaAlta                  (registro.substring(60,	70));
    		beanRespuestaTemp.setNumeroTarjeta              (registro.substring(70,	92));
    		beanRespuestaTemp.setClaveTranOper              (registro.substring(92,	95));
    		beanRespuestaTemp.setClaveRefTran               (registro.substring(95,	99));
    		beanRespuestaTemp.setClaveOperCanal             (registro.substring(99,	105));
    		beanRespuestaTemp.setClaveOperCorresponsal      (registro.substring(105,	115));
    		beanRespuestaTemp.setIdCaja                     (registro.substring(115,	131));
    		beanRespuestaTemp.setFechaConsiliacion          (registro.substring(131,	141));
    		beanRespuestaTemp.setFechaCompensacion          (registro.substring(141,	151));
    		beanRespuestaTemp.setNumRefPampa                (registro.substring(151,	175));
    		beanRespuestaTemp.setNumCuentaCheques           (registro.substring(175,	195));
    		beanRespuestaTemp.setFechaOperacion             (registro.substring(195,	205));
    		sHora.append(registro.substring(205,209));
    		sHora.insert(2, ':');    		
    		beanRespuestaTemp.setHoraOperacion              (sHora.toString());
    		beanRespuestaTemp.setDivisaOperacion            (registro.substring(211,	214));
    		beanRespuestaTemp.setImporteOperacion            (registro.substring(214,	229));
    		beanRespuestaTemp.setNumeroFactura              (registro.substring(229,	233));
    		beanRespuestaTemp.setIdEntidadAdquiriente       (registro.substring(233,	244));
    		beanRespuestaTemp.setImporteComisionBanco       (registro.substring(244,	259));
    		beanRespuestaTemp.setImporteComisionCorresponsal(registro.substring(259,	274));
    		beanRespuestaTemp.setImporteComisionCliente     (registro.substring(274,	289));
    		beanRespuestaTemp.setImporteIvaBanco            (registro.substring(289,	304));
    		beanRespuestaTemp.setImporteIvaCorresponsal     (registro.substring(304,	319));
    		beanRespuestaTemp.setImporteIvaCliente          (registro.substring(319,	334));
    		beanRespuestaTemp.setTerminalCompatible         (registro.substring(334,	337));
    		beanRespuestaTemp.setDatosAdicionales           (registro.substring(337,	439));
    		beanRespuestaTemp.setNombreLocalizacion         (registro.substring(439,	479));
    		beanRespuestaTemp.setCampoObservaciones1        (registro.substring(479,	529));
    		beanRespuestaTemp.setCampoObservaciones2        (registro.substring(529,	579));
    		beanRespuestaTemp.setDescOperacion              (registro.substring(579,	599));
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
