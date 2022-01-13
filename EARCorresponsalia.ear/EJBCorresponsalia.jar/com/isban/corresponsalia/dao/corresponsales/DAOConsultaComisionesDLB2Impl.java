package com.isban.corresponsalia.dao.corresponsales;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.comunes.BeanComisiones;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaComision;
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
 * The Class DAOConsultaComisionesDLB2Impl.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOConsultaComisionesDLB2Impl extends ArchitechEBE implements DAOConsultaComisionesDLB2{

	
	/** The TRA n390. */
	transient private static final String TRAN390  = "DLB2";
	
	/** The codoper. */
	transient private static final String CODOPER  = "ConsultaCmisionesDLB2";
	
	/** The id canal. */
	transient private final String idCanal = Alias.getAlias("CORRESPONSALIA");
	
	/**
	 * Consulta comisiones.
	 *
	 * @param beanConsulta the bean consulta
	 * @param beanArq the bean Arq
	 * @return the object
	 */
	@Override
	public Object consultaComisiones(BeanConsultaComision beanConsulta, ArchitechSessionBean beanArq) {
    	debug("DAOConsultaComisionesDLB2->ConsultaComisiones");

    	final BeanError beanError = new BeanError();
    	
    	debug("Validando facultades del usuario sobre transaccion " + TRAN390);
    	if(!Facultades.getInstanceOf().trasaccionAutorizada(beanArq.getPerfil(), TRAN390)){
    		debug("El usuario no tiene facultades sobre transaccion " + TRAN390);
    		beanError.setCodigoError(Facultades.CODIGO_ERROR);
    		beanError.setMsgError(Facultades.MENSAJE_ERROR);
    		return beanError;
    	}
    	List<BeanComisiones> listaRegistros = null;
		final Map<String,Object>   objReturn      = new HashMap<String,Object>();
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

			String lstrTrama = responseDTO.getResponseMessage();


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
					final String lstrReferenciaAvanzar    = "";
					final String lstrReferenciaRetroceder = "";
					if(TratamientoTrama.tramaTieneFormatoPaginacion(lstrTramaDesentramar, "@DCDLMB220 P")){
						debug("La trama contiene un formato de paginacion....");
						final String lstrTramaPaginacion      = TratamientoTrama.obtenerTramaPaginacion(lstrTramaDesentramar,  "@DCDLMB220 P");
						debug("Trama Paginacion:" + lstrTramaPaginacion);
						//lstrReferenciaAvanzar    = getReferenciaAvanzar(lstrTramaPaginacion);
						//lstrReferenciaRetroceder = getReferenciaRetroceder(lstrTramaPaginacion);
						debug("Referencia Avanzar   :" + lstrReferenciaAvanzar);
						debug("Referencia Retroceder:" + lstrReferenciaRetroceder);
						lstrTramaDesentramar = TratamientoTrama.eliminarTramaPaginacion(lstrTramaDesentramar, "@DCDLMB220 P");


					}
					debug("Trama desentramar Registros:" + lstrTramaDesentramar);
					listaRegistros = getRegistros(lstrTramaDesentramar);

					objReturn.put("registros"           , listaRegistros);
					objReturn.put("referenciaAvanzar"   , lstrReferenciaAvanzar);
					objReturn.put("referenciaRetroceder", lstrReferenciaRetroceder);
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
    private String getMessage(BeanConsultaComision beanConsulta){
       	String message = "";
    	final StringBuffer trama = new StringBuffer();
    		trama.append(Utils.rpad(beanConsulta.getTipoOperacion()                  , " ", 1));
    		trama.append(Utils.rpad(beanConsulta.getEntidad()                        , " ", 4));
    		trama.append(Utils.rpad(beanConsulta.getCanalCorresponsal()              , " ", 2));
    		trama.append(Utils.rpad(beanConsulta.getIdentificacionCorresponsal()     , " ", 4));
    		trama.append(Utils.rpad(beanConsulta.getInicioVigencia()                 , " ", 10));
    		trama.append(Utils.rpad(beanConsulta.getFinVigencia()                    , " ", 10));
    		trama.append(Utils.rpad(beanConsulta.getTipoComision()                   , " ", 3));
    		trama.append(Utils.rpad(beanConsulta.getIndicadorPaginacion()            , " ", 1));
    		trama.append(Utils.rpad(beanConsulta.getIndicadorAvanceRetroceso()       , " ", 1));
    		trama.append(Utils.rpad(beanConsulta.getIndicadorCorresponsalAvance()    , " ", 4));
    		trama.append(Utils.rpad(beanConsulta.getIndicadorAvanceRetroceso()       , " ", 4));
    		trama.append(Utils.rpad(beanConsulta.getIndicadorTipoOperacionAvance()   , " ", 6));
    		trama.append(Utils.rpad(beanConsulta.getIndicadorTipoOperacionRetroceso(), " ", 6));
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
    private List<BeanComisiones> getRegistros(String reesponseMessage){
    	
    	final List<BeanComisiones> listaRegistros	= new ArrayList<BeanComisiones>();   	   	
    	final List<String>            listaRegistrosTratamiento = TratamientoTrama.parteTrama(reesponseMessage, "@DCDLMB210 P"); 
    	debug("Numero de Registros Obtenidos:" + listaRegistrosTratamiento.size());
    	for(String registro:listaRegistrosTratamiento){
    		debug("Registro:" + registro);
    		final BeanComisiones beanTemp = new BeanComisiones();    		
  		
			beanTemp.setEntidad                              (registro.substring(0, 4)); //4
			beanTemp.setCanalCorresponsal                    (registro.substring(4, 6)); //2
			beanTemp.setIdentificacionCorresponsal           (registro.substring(6, 10)); //4
			beanTemp.setDescripcionOperacion                 (registro.substring(10, 30).trim()); //20
			beanTemp.setRegionSucursal                       (registro.substring(30, 34)); //4
			beanTemp.setZonaSucursal                         (registro.substring(34, 39)); //5
			beanTemp.setIdentificacionSucursal               (registro.substring(39, 49)); //10
			beanTemp.setNivelComision                        (registro.substring(49, 51)); //2
			beanTemp.setInicioVigencia                       (registro.substring(51, 61)); //10
			beanTemp.setFinVigencia                          (registro.substring(61, 71)); //10
			beanTemp.setImporteOperacionRangoMaximo          (registro.substring(71, 86)); //15
			beanTemp.setTipoComision                         (registro.substring(86, 89)); //3
			beanTemp.setIdentificadorEstatus                 (registro.substring(89, 92)); //3
			beanTemp.setImporteComisionTotal                 (registro.substring(92, 107)); //15
			beanTemp.setImporteComisionBancoMontoFijo        (registro.substring(107, 122)); //15
			beanTemp.setImporteComisionCorresponsalMontoFijo (registro.substring(122, 137)); //15
			beanTemp.setImporteComisionClienteMontoFijo      (registro.substring(137, 152)); //15
			beanTemp.setImporteComisionBancoPorcentaje       (registro.substring(152, 157)); //5
			beanTemp.setImporteComisionCorresponsalPorcentaje(registro.substring(157, 162)); //5
			beanTemp.setImporteComisionClientePorcentaje     (registro.substring(162, 167)); //5
			beanTemp.setIvaTotalComision                     (registro.substring(167, 182)); //15
 		
			listaRegistros.add(beanTemp);
    	}    	  	   	
    	return listaRegistros;
    }
}
