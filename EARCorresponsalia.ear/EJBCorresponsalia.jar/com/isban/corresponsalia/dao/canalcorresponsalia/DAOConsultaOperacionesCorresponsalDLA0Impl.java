package com.isban.corresponsalia.dao.canalcorresponsalia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.HashMap;

import javax.ejb.Stateless;

import javax.ejb.TransactionManagement;

import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCorresponsal;

import com.isban.corresponsalia.beans.comunes.BeanError;

import com.isban.corresponsalia.beans.comunes.BeanOperacion;

import com.isban.corresponsalia.comunes.Facultades;

import com.isban.corresponsalia.comunes.TratamientoTrama;

import com.isban.corresponsalia.comunes.Utils;

import com.isban.corresponsalia.comunes.UtilsCadenas;

import com.isban.dataaccess.DataAccess;

import com.isban.dataaccess.channels.cics.dto.RequestMessageCicsDTO;

import com.isban.dataaccess.channels.cics.dto.ResponseMessageCicsDTO;

import com.isban.ebe.commons.architech.ArchitechEBE;

import com.isban.ebe.commons.beans.ArchitechSessionBean;

import com.isban.ebe.commons.exception.ExceptionDataAccess;


/**
 * Session Bean implementation class DAOConsultaCorresponsaliaDLA0Impl.
 */

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOConsultaOperacionesCorresponsalDLA0Impl extends ArchitechEBE
		implements DAOConsultaOperacionesCorresponsalDLA0 {

	/** The TRA n390. */
	static final private String TRAN390 = "DLA0";

	/** The codoper. */
	static final private String CODOPER = "ConsultaCorresponsaliaDLA0";

	/** The id canal. */
	transient final  private String idCanal = getIdCanalDAO("CICS_CORRESPONSALIA");

	/** The arrg resultado. */
	transient private String[] arrgResultado;

	/** The bean resultado consulta. */
	transient private List<BeanOperacion> beanResultadoConsulta;

	/** The referencia avanzar. */
	//private String referenciaAvanzar = "";

	/** The referencia retroceder. */
	//private String referenciaRetroceder = "";

	/** The mas adelante. */
	transient private boolean masAdelante = false;

	/** The mas atras. */
	transient private boolean masAtras = false;

	/** The bean consulta. */
	transient private BeanConsultaOperacionesCorresponsal beanConsulta;

	/**
	 * Consulta operaciones.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the hash map
	 */
	@Override
	public Map<String, Object> consultaOperaciones(
	BeanConsultaOperacionesCorresponsal beanConsulta,
			ArchitechSessionBean psession) {

		info("Inicia DAO Consulta Corresponsalia");

//		referenciaAvanzar = "";

		//referenciaRetroceder = "";

		masAdelante = false;

		masAtras = false;

		this.beanConsulta = beanConsulta;

		BeanError beanError = new BeanError();

		final Map<String, Object> mapaResp = new HashMap<String, Object>();

		String codigoError;

//		String mensajeError;

		String tramaSalida;

		debug("Validando facultades del usuario sobre transaccion " + TRAN390);

		if (!Facultades.getInstanceOf().trasaccionAutorizada(
				psession.getPerfil(), TRAN390)) {

			debug("El usuario no tiene facultades sobre transaccion " + TRAN390);

			beanError.setCodigoError(Facultades.CODIGO_ERROR);

			beanError.setCodigoError(Facultades.MENSAJE_ERROR);

		}

		ejecutaTransaccion(beanConsulta);

		codigoError = arrgResultado[0];

		//mensajeError = arrgResultado[1];

		tramaSalida = arrgResultado[2];

		beanError = UtilsCadenas.desentramaError(tramaSalida);
		if (tramaSalida == null) {
			beanError.setCodigoError("DLE 0001");
			beanError.setMsgError("Transaccion no disponible");
			mapaResp.put("error", beanError);
		} else {
			if (tramaSalida.indexOf("@ER") < 0) {

				desentrama(tramaSalida, beanConsulta.getTipoConsulta());

			}

			String lstrTramaDesentramar = TratamientoTrama
					.getTramaTratamiento(tramaSalida);

			String lstrReferenciaAvanzar = "";

			String lstrReferenciaRetroceder = "";

			if (TratamientoTrama.tramaTieneFormatoPaginacion(
					lstrTramaDesentramar, "@DCDLMA020 P")) {

				debug("La trama contiene un formato de paginacion....");

				final String lstrTramaPaginacion = TratamientoTrama
						.obtenerTramaPaginacion(lstrTramaDesentramar,
								"@DCDLMA020 P");

				debug("Trama Paginacion:" + lstrTramaPaginacion);

				lstrReferenciaAvanzar = getReferenciaAvanzar(lstrTramaPaginacion);

				lstrReferenciaRetroceder = getReferenciaRetroceder(lstrTramaPaginacion);

				debug("Referencia Avanzar   :" + lstrReferenciaAvanzar);

				debug("Referencia Retroceder:" + lstrReferenciaRetroceder);

			}

			if (TratamientoTrama.tramaTieneFormatoPaginacion(tramaSalida,
					"@AVDLA0004")&& "P".equals(beanConsulta.getIndicadorPag())) {// No hay más datos
					if ("A".equals(beanConsulta.getIndicadorDirreccion())) {
						lstrReferenciaAvanzar = null;
					} else {
						lstrReferenciaRetroceder = null;
					}				
			}
			lstrTramaDesentramar = TratamientoTrama.eliminarTramaPaginacion(
					lstrTramaDesentramar, "@DCDLMA020 P");
			info("Finaliza DAO Consulta Corresponsalia :" + codigoError);
			mapaResp.put("error", beanError);
			mapaResp.put("registros", beanResultadoConsulta);
			mapaResp.put("referenciaAvanzar", lstrReferenciaAvanzar);
			mapaResp.put("referenciaRetroceder", lstrReferenciaRetroceder);
			mapaResp.put("masAtras", masAtras);
			mapaResp.put("masAdelante", masAdelante);
		}
		return mapaResp;
	}

	/**
	 * Desentrama la respuesta de 390.
	 *
	 * @param trama the trama
	 * @param tipoConsulta the tipo consulta
	 */

	private void desentrama(String trama, String tipoConsulta) {

		BeanOperacion beanRespuesta;

		beanResultadoConsulta = new ArrayList<BeanOperacion>();

		String tramaRespuesta = "";

		String[] argResultadoTrama;

		// Dummy@112345@AVDLA0007 OK. OPERACION EFECTUADAÓ@DCDLMA030
		// P00141400000000000000010000010000000000PAGO EFECTIVO TDC
		// TX0108000017000000000000001000000000001000000000000001000000000000010000000001000200

		if ("L".equals(tipoConsulta)) {

			if (trama.indexOf("@AVDLA0004") > -1
					|| trama.indexOf("@AVDLA0005") > -1) { // revisar dato

				trama = trama.substring(0, trama.indexOf("Ó@DCDLMA020"));

				argResultadoTrama = trama.split("Ó@DCDLMA010 P");

				for (int i = 1; i < argResultadoTrama.length; i++) {

					beanRespuesta = new BeanOperacion();

					tramaRespuesta = argResultadoTrama[i];

					beanRespuesta.setEntidad(tramaRespuesta.substring(0, 4));

					beanRespuesta.setIdCanal(tramaRespuesta.substring(4, 6));

					beanRespuesta.setIdCorresponsal(tramaRespuesta.substring(6,
							10));

					beanRespuesta.setIdSucursal(tramaRespuesta
							.substring(10, 20));

					beanRespuesta.setNivelParametria(tramaRespuesta.substring(
							20, 22));

					beanRespuesta.setTipoOperacionCorresponsal(tramaRespuesta
							.substring(22, 32));

					beanRespuesta.setDesCorta(tramaRespuesta.substring(32, 52));

					beanRespuesta.setCodigoOperacion(tramaRespuesta.substring(
							52, 58));

					beanRespuesta.setCveTransaccion(tramaRespuesta.substring(
							58, 62));

					beanResultadoConsulta.add(beanRespuesta);

				}

			}

			if (trama.indexOf("@DCDLMA420") > -1) {

				trama = trama.substring(0, trama.length() - 2);

				argResultadoTrama = trama.split("Ó@DCDLMA420 P");

				for (int i = 1; i < argResultadoTrama.length; i++) {

					tramaRespuesta = argResultadoTrama[i];

//					referenciaAvanzar = tramaRespuesta.substring(0, 5);

					//referenciaRetroceder = tramaRespuesta.substring(5, 10);

					if ("B".equals(beanConsulta.getIndicadorDirreccion())) {

						masAdelante = true;

						if (trama.indexOf("AVDLA0005") > -1) {

							masAtras = true;

						}

						debug("primer if");

					}

					else {

						if ("P".equals(beanConsulta.getIndicadorPag())) {

							masAtras = true;

						}

						if (trama.indexOf("AVDLA0005") > -1) {

							masAdelante = true;

						}

						debug("else");

					}

					debug("MasAtras: " + masAtras);

					debug("masAdelante: " + masAdelante);

					// beanResultadoConsulta;

				}

			}

		} else {

			// String codigoExito = "Ó@DCDLMA030 P";

			final String codigoExito = "@DCDLMA030 P";

			final int posicionCodigoExito = trama.indexOf(codigoExito);

			if (posicionCodigoExito > -1) {

				tramaRespuesta = trama.substring(
						trama.indexOf(codigoExito) + 12, trama.length() - 2);

				beanRespuesta = new BeanOperacion();

				beanRespuesta.setEntidad(tramaRespuesta.substring(0, 4).trim());

				beanRespuesta.setIdCanal(tramaRespuesta.substring(4, 6).trim());

				beanRespuesta.setIdCorresponsal(tramaRespuesta.substring(6, 10)
						.trim());

				beanRespuesta.setIdSucursal(tramaRespuesta.substring(10, 20)
						.trim());

				beanRespuesta.setNivelParametria(tramaRespuesta.substring(20,
						22).trim());

				beanRespuesta.setTipoOperacionCorresponsal(tramaRespuesta
						.substring(22, 28).trim());

				beanRespuesta.setCodigoOperacion(tramaRespuesta.substring(22,
						28).trim());

				beanRespuesta.setEquivalencia(tramaRespuesta.substring(28, 38)
						.trim());

				beanRespuesta.setDesCorta(tramaRespuesta.substring(38, 58)
						.trim());

				beanRespuesta.setCveTransaccion(tramaRespuesta
						.substring(58, 62).trim());

				beanRespuesta.setHoraInicio(tramaRespuesta.substring(62, 68)
						.trim());

				beanRespuesta.setHoraFinal(tramaRespuesta.substring(68, 74)
						.trim());

				beanRespuesta.setImporteMinimoOperacion(tramaRespuesta
						.substring(74, 89).trim());

				beanRespuesta.setImporteMaximoOperacion(tramaRespuesta
						.substring(89, 104).trim());

				beanRespuesta.setLimiteImporteMaximoDiarioAcum(tramaRespuesta
						.substring(104, 119).trim());

				beanRespuesta.setLimiteImporteMaximoMensualAcum(tramaRespuesta
						.substring(119, 134).trim());

				beanRespuesta.setCodigoOperacionTransaccion(tramaRespuesta
						.substring(134, 138).trim());

				beanRespuesta.setCodigoOperacionContraOper(tramaRespuesta
						.substring(138, 142).trim());

				beanResultadoConsulta.add(beanRespuesta);

				info("Sale desentramado:" + beanResultadoConsulta.size());

			}

		}

	}

	/**
	 * Ejecuta la transaccion DLA0I Consulta de Corresponsalia.
	 *
	 * @param beanConsulta the bean consulta
	 * @return the string[]
	 */

	public String[] ejecutaTransaccion(
			BeanConsultaOperacionesCorresponsal beanConsulta) {

		info("Ejecutando DAOConsultaCorresponsaliaDLA0->consultaCorresponsalia");

		arrgResultado = new String[3];

		ResponseMessageCicsDTO responseDTO = new ResponseMessageCicsDTO();

		try {

			final RequestMessageCicsDTO requestDTO = new RequestMessageCicsDTO();

			final DataAccess dataAccess = DataAccess.getInstance(requestDTO,
					getLoggingBean());

			requestDTO.setTransaction(TRAN390);

			requestDTO.setCodeOperation(CODOPER);

			requestDTO.setMessage(generaTramaEntrada(beanConsulta));

			info("TRAMA ENTRADA : " + requestDTO.getMessage());

			// Dummy

			/*
			 * arrgResultado[0] = "Exito";
			 * 
			 * arrgResultado[1] = "ConsultaDummy";
			 * 
			 * arrgResultado[2] = "Resultado Consulta Dummy";
			 * 
			 * if(arrgResultado[0].equals("Exito")){
			 * 
			 * return arrgResultado;
			 * 
			 * }
			 */

			// Dummy
			responseDTO = (ResponseMessageCicsDTO) dataAccess.execute(idCanal);

			info("TRAMA SALIDA : " + responseDTO.getResponseMessage());

			debug("Codigo de Error     :" + responseDTO.getCodeError());

			debug("Mensaje de Error    :" + responseDTO.getMessageError());

			debug("Mensaje de Respuesta:" + responseDTO.getResponseMessage());

			if (!"".equals(responseDTO.getCodeError())) {

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
	 * Agrega la cabecera y arma la trama de entrada DLA0.
	 *
	 * @param beanConsulta the bean consulta
	 * @return the string
	 */

	private String generaTramaEntrada(
			BeanConsultaOperacionesCorresponsal beanConsulta) {

		final StringBuffer tramaEntrada = new StringBuffer();

			tramaEntrada.append(Utils.rpad(beanConsulta.getEntidad(),

			" ", 4));

			tramaEntrada.append(Utils.rpad(beanConsulta.getCanal(),

			" ", 2));

			tramaEntrada.append(Utils.rpad(beanConsulta.getIdCorresponsal(),

			" ", 4));

			tramaEntrada.append(Utils.rpad(beanConsulta.getCodigoSucursal(),

			" ", 10));

			tramaEntrada.append(Utils.rpad(beanConsulta.getNivelParametria(),

			" ", 2));

			tramaEntrada.append(Utils.lpad(beanConsulta.getCodigoOperacion(),

			"0", 6));

			tramaEntrada.append(Utils.lpad(
					beanConsulta.getTipoOperacionCorre(),

					" ", 10));

			tramaEntrada.append(Utils.rpad(beanConsulta.getIndicadorPag(),

			" ", 1));

			tramaEntrada.append(Utils.rpad(beanConsulta
					.getIndicadorDirreccion(),

			" ", 1));

			tramaEntrada.append(Utils.rpad(beanConsulta.getTipoConsulta(),

			" ", 1));

			if ("P".equals(beanConsulta.getIndicadorPag())) {

				if ("A".equals(beanConsulta.getIndicadorDirreccion())) {

					tramaEntrada.append(Utils.lpad(beanConsulta
							.getLimiteInferiorConsulta(),

					" ", 6));

				} else {

					tramaEntrada.append(Utils.lpad(beanConsulta
							.getLimiteSuperiorConsulta(),

					" ", 6));

				}

			} else {

				tramaEntrada.append(Utils.lpad(beanConsulta
						.getLimiteInferiorConsulta(),

				" ", 6));

			}

		info("Sale de generar la trama :" + tramaEntrada.toString());

		return tramaEntrada.toString();

	}

	/**
	 * Gets the referencia avanzar.
	 *
	 * @param tramaPaginacion the trama paginacion
	 * @return the referencia avanzar
	 */
	private String getReferenciaAvanzar(String tramaPaginacion) {

		String lstrReferencia = "";

		try {

			lstrReferencia = tramaPaginacion.substring(6, 12).trim();

		}

		catch (StringIndexOutOfBoundsException e) {

			debug("No fue posible obtener la referencia");

		}

		return lstrReferencia;

	}

	/**
	 * Gets the referencia retroceder.
	 *
	 * @param tramaPaginacion the trama paginacion
	 * @return the referencia retroceder
	 */
	private String getReferenciaRetroceder(String tramaPaginacion) {

		String lstrReferencia = "";

		try {

			lstrReferencia = tramaPaginacion.substring(0, 6).trim();

		}

		catch (StringIndexOutOfBoundsException e) {

			debug("No fue posible obtener la referencia");

		}

		return lstrReferencia;

	}

}
