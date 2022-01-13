package com.isban.corresponsalia.dao.consultas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.consultas.BeanRegistroConsultaNoCompensacion;
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
 * Session Bean implementation class DAOConsultasNoCompenzadasDLB7Imp.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOConsultasNoCompenzadasDLB7Imp extends ArchitechEBE implements
		DAOConsultaNoCompenzadasDLB7 {


	/** The TRA n390. */
	transient private static final String TRAN390 = "DLB7";

	/** The codoper. */
	transient private static final String CODOPER = "ConsultasNoCompenzadasDLB7";

	/** The id canal. */
	transient private final String idCanal = Alias.getAlias("CORRESPONSALIA");
	/**
	 * Consulta no compensacion.
	 * 
	 * @param beanArq
	 *            the bean arq
	 * @param beanCorresponsal
	 *            the bean corresponsal
	 * @param pstrAvanzaRetrocede
	 *            the pstr avanza retrocede
	 * @param pstrFechaInferior
	 *            the pstr fecha inferior
	 * @param pstrFechaSuperior
	 *            the pstr fecha superior
	 * @return the object
	 */
	public Object consultaNoCompensacion(ArchitechSessionBean beanArq,
			BeanCorresponsal beanCorresponsal, String pstrAvanzaRetrocede,
			String pstrFechaInferior, String pstrFechaSuperior) {
		debug("DAOConsultasNoCompenzadasDLB7->consultaNoCompensacion");

		final BeanError beanError = new BeanError();

		debug("Validando facultades del usuario sobre transaccion " + TRAN390);
		if (!Facultades.getInstanceOf().trasaccionAutorizada(
				beanArq.getPerfil(), TRAN390)) {
			debug("El usuario no tiene facultades sobre transaccion " + TRAN390);
			beanError.setCodigoError(Facultades.CODIGO_ERROR);
			beanError.setMsgError(Facultades.MENSAJE_ERROR);
			return beanError;
		}

		List<BeanRegistroConsultaNoCompensacion> listaRegistrosConsultaNoCompensacion = null;
		final String lstrFechaInferior = "";
		final String lstrFechaSuperior = "";
		final Map<String, Object> objReturn = new HashMap<String, Object>();
		try {
			final RequestMessageCicsDTO requestDTO = new RequestMessageCicsDTO();
			ResponseMessageCicsDTO responseDTO = null;
			final DataAccess dataAccess = DataAccess.getInstance(requestDTO,
					getLoggingBean());

			requestDTO.setTransaction(TRAN390);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setMessage(getMessage(beanCorresponsal,
					pstrAvanzaRetrocede, pstrFechaInferior, pstrFechaSuperior));

			responseDTO = (ResponseMessageCicsDTO) dataAccess.execute(idCanal);
			debug("Codigo de Error     :" + responseDTO.getCodeError());
			debug("Mensaje de Error    :" + responseDTO.getMessageError());
			debug("Mensaje de Respuesta:" + responseDTO.getResponseMessage());

			final String lstrTrama = responseDTO.getResponseMessage();

			if ("DAE000".equals(responseDTO.getCodeError())) {
				debug("La transaccion[" + TRAN390
						+ "] fue ejecutada exitosamente...");
				if (!TratamientoTrama.esRespuestaCorrecta(lstrTrama)) {
					debug("La transaccion[" + TRAN390
							+ "] regreso el siguiente error...");
					final String lstrCodError = TratamientoTrama
							.getCodigoError(lstrTrama);
					final String lstrMsgError = TratamientoTrama
							.getMensajeError(lstrTrama);
					debug("Codigo de Error :" + lstrCodError);
					debug("Mensaje de Error:" + lstrMsgError);
					beanError.setCodigoError(lstrCodError);
					beanError.setMsgError(lstrMsgError);
					return beanError;
				} else {
					debug("La transaccion[" + TRAN390
							+ "] regreso el siguiente aviso...");
					final String lstrCodAviso = TratamientoTrama
							.getCodigoAviso(lstrTrama);
					final String lstrMsgAviso = TratamientoTrama
							.getMensajeAviso(lstrTrama);
					final String lstrFormatoRes = TratamientoTrama
							.getFormatoRespuesta(lstrTrama);
					debug("Codigo de Aviso  :" + lstrCodAviso);
					debug("Mensaje de Aviso :" + lstrMsgAviso);
					debug("Formato Respuesta:" + lstrFormatoRes);
					if (lstrMsgAviso.indexOf("NO EXISTEN DATOS") > -1) {
						beanError.setCodigoError(lstrCodAviso);
						beanError.setMsgError(lstrMsgAviso);
						return beanError;
					}
					String lstrTramaDesentramar = TratamientoTrama
							.getTramaTratamiento(lstrTrama);
					String lstrReferenciaAvanzar = "";
					String lstrReferenciaRetroceder = "";
					if (TratamientoTrama.tramaTieneFormatoPaginacion(
							lstrTramaDesentramar, "@DCDLMB720 P")) {
						debug("La trama contiene un formato de paginacion....");
						final String lstrTramaPaginacion = TratamientoTrama
								.obtenerTramaPaginacion(lstrTramaDesentramar,
										"@DCDLMB720 P");
						debug("Trama Paginacion:" + lstrTramaPaginacion);
						lstrReferenciaAvanzar = getReferenciaAvanzar(lstrTramaPaginacion);
						lstrReferenciaRetroceder = getReferenciaRetroceder(lstrTramaPaginacion);
						debug("Referencia Avanzar   :" + lstrReferenciaAvanzar);
						debug("Referencia Retroceder:"
								+ lstrReferenciaRetroceder);
						lstrTramaDesentramar = TratamientoTrama
								.eliminarTramaPaginacion(lstrTramaDesentramar,
										"@DCDLMB720 P");

					}

					listaRegistrosConsultaNoCompensacion = getRegistros(TratamientoTrama
							.getTramaTratamiento(lstrTrama));
					objReturn.put("registros",
							listaRegistrosConsultaNoCompensacion);
					objReturn.put("fechaInferior", lstrFechaInferior);
					objReturn.put("fechaSuperior", lstrFechaSuperior);
				}
			} else {
				beanError.setCodigoError(responseDTO.getCodeError());
				beanError.setCodigoError(responseDTO.getMessageError());
				return beanError;
			}
		} catch (ExceptionDataAccess e) {
			showException(e);
		}
		return objReturn;
	}

	/**
	 * Gets the message.
	 * 
	 * @param beanCorresponsal
	 *            the bean corresponsal
	 * @param pstrAvanzaRetrocede
	 *            the pstr avanza retrocede
	 * @param pstrFechaInferior
	 *            the pstr fecha inferior
	 * @param pstrFechaSuperior
	 *            the pstr fecha superior
	 * @return the message
	 */
	private String getMessage(BeanCorresponsal beanCorresponsal,
			String pstrAvanzaRetrocede, String pstrFechaInferior,
			String pstrFechaSuperior) {
		String message = "";
		final StringBuffer trama = new StringBuffer();
		/*
		 * 4 Req IIDCORR Identificacion Corresponsal 1 Req IINDPAG INDICADOR
		 * PAGINACION 1 Req IINDAVA Indicador de Paginación A = Avanza / R =
		 * Retrocede 10 Opc IFECLO1 Fecha lote inferior que hace referencia a la
		 * paginacion hacia atrás 10 Opc IFECLO2 Fecha lote superior que hace
		 * referencia a la paginacion hacia atrás
		 */
		if (pstrAvanzaRetrocede == null
				|| (!"A".equals(pstrAvanzaRetrocede) && !"B"
						.equals(pstrAvanzaRetrocede))) {
			trama.append(Utils.rpad(beanCorresponsal.getCodigoCorresponsal(),
					" ", 4));
			trama.append(Utils.rpad("N", " ", 1));
			trama.append(Utils.rpad("", " ", 1));
			trama.append(Utils.rpad("", " ", 10));
			trama.append(Utils.rpad("", " ", 10));
		} else {
			trama.append(Utils.rpad(beanCorresponsal.getCodigoCorresponsal(),
					" ", 4));
			trama.append(Utils.rpad("P", " ", 1));
			trama.append(Utils.rpad(pstrAvanzaRetrocede, " ", 1));
			trama.append(Utils.rpad(pstrFechaInferior, " ", 10));
			trama.append(Utils.rpad(pstrFechaSuperior, " ", 10));
		}

		debug("Longitud Trama:" + trama.length());

		message = trama.toString();
		debug("Trama:" + message);

		return message;
	}

	/**
	 * Gets the registros.
	 * 
	 * @param reesponseMessage
	 *            the reesponse message
	 * @return the registros
	 */
	private List<BeanRegistroConsultaNoCompensacion> getRegistros(
			String reesponseMessage) {

		final List<BeanRegistroConsultaNoCompensacion> listaRegistrosConsultaNoCompensacion = new ArrayList<BeanRegistroConsultaNoCompensacion>();

		final List<String> listaRegistros = TratamientoTrama.parteTrama(
				reesponseMessage, "@DCDLMB710 P");

		debug("Numero de Registros obtenidos:" + listaRegistros.size());
		for (String registro : listaRegistros) {
			debug("Registro:" + registro);
			final BeanRegistroConsultaNoCompensacion beanCompensacion = new BeanRegistroConsultaNoCompensacion();

			/*
			 * 10 OFECLOT Fecha del LOTE 13 OTOTOPE TOTAL de Operaciones 15
			 * OTOTIMP Importe TOTAL 20 OSTATUS Estatus 15 OIMPCOB Cargo
			 * Comisiones 15 OIMPABO Abono Comisiones 15 OIVAABO Abono IVA
			 */
			final String lstrFechaLote = registro.substring(0, 10);
			final String lstrTotalOperaciones = registro.substring(10, 23);
			final String lstrImporteTotal = registro.substring(23, 38);
			final String lstrEstatus = registro.substring(38, 58);
			final String lstrCargoComisiones = registro.substring(58, 73);
			final String lstrAbonoComisiones = registro.substring(73, 88);
			final String lstrAbonoIva = registro.substring(88, 103);

			beanCompensacion.setFechaLote(lstrFechaLote);
			beanCompensacion.setTotalOperaciones(lstrTotalOperaciones);
			beanCompensacion.setImporteTotal(lstrImporteTotal);
			beanCompensacion.setEstatus(lstrEstatus);
			beanCompensacion.setCargoComisiones(lstrCargoComisiones);
			beanCompensacion.setAbonoComisiones(lstrAbonoComisiones);
			beanCompensacion.setAbonoIva(lstrAbonoIva);

			listaRegistrosConsultaNoCompensacion.add(beanCompensacion);
		}

		return listaRegistrosConsultaNoCompensacion;
	}

	/**
	 * Gets the referencia avanzar.
	 * 
	 * @param tramaPaginacion
	 *            the trama paginacion
	 * @return the referencia avanzar
	 */
	private String getReferenciaAvanzar(String tramaPaginacion) {
		String lstrReferencia = "";
		lstrReferencia = tramaPaginacion.substring(10, 20).trim();
		return lstrReferencia;
	}

	/**
	 * Gets the referencia retroceder.
	 * 
	 * @param tramaPaginacion
	 *            the trama paginacion
	 * @return the referencia retroceder
	 */
	private String getReferenciaRetroceder(String tramaPaginacion) {
		String lstrReferencia = "";
		lstrReferencia = tramaPaginacion.substring(0, 10).trim();
		return lstrReferencia;
	}
}