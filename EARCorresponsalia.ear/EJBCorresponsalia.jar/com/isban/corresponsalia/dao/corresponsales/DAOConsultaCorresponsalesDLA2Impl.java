package com.isban.corresponsalia.dao.corresponsales;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
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
 * The Class DAOConsultaCorresponsalesDLA2Impl.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOConsultaCorresponsalesDLA2Impl extends ArchitechEBE implements
		DAOConsultaCorresponsalesDLA2 {


	/** The TRA n390. */
	private static final String TRAN390 = "DLA2";

	/** The codoper. */
	private static final String CODOPER = "ConsultaMttoCorresponsaliaDLA2";

	/** The id canal. */
	transient private final String idCanal = Alias.getAlias("CORRESPONSALIA");
	/** The registro inicial. */
	private String registroInicial = "";

	/** The registro final. */
	private String registroFinal = "";

	/** The mas adelante. */
	private boolean masAdelante = false;

	/** The mas atras. */
	private boolean masAtras = false;

	/**
	 * Gets the registro inicial.
	 * 
	 * @return the registro inicial
	 */
	public String getRegistroInicial() {
		return registroInicial;
	}

	/**
	 * Sets the registro inicial.
	 * 
	 * @param registroInicial
	 *            the new registro inicial
	 */
	public void setRegistroInicial(String registroInicial) {
		this.registroInicial = registroInicial;
	}

	/**
	 * Gets the registro final.
	 * 
	 * @return the registro final
	 */
	public String getRegistroFinal() {
		return registroFinal;
	}

	/**
	 * Sets the registro final.
	 * 
	 * @param registroFinal
	 *            the new registro final
	 */
	public void setRegistroFinal(String registroFinal) {
		this.registroFinal = registroFinal;
	}

	/**
	 * Checks if is mas adelante.
	 * 
	 * @return true, if is mas adelante
	 */
	public boolean isMasAdelante() {
		return masAdelante;
	}

	/**
	 * Sets the mas adelante.
	 * 
	 * @param masAdelante
	 *            the new mas adelante
	 */
	public void setMasAdelante(boolean masAdelante) {
		this.masAdelante = masAdelante;
	}

	/**
	 * Checks if is mas atras.
	 * 
	 * @return true, if is mas atras
	 */
	public boolean isMasAtras() {
		return masAtras;
	}

	/**
	 * Sets the mas atras.
	 * 
	 * @param masAtras
	 *            the new mas atras
	 */
	public void setMasAtras(boolean masAtras) {
		this.masAtras = masAtras;
	}

	/**
	 * Consulta corresponsalias.
	 * 
	 * @param beanConsulta      the bean consulta           
	 * @param beanArq 			the bean arch            
	 * @return the object
	 */
	@Override
	public Object consultaCorresponsalias(
			BeanMttoConsultaCorresponsal beanConsulta,
			ArchitechSessionBean beanArq) {
		info("Inicia DAO Consulta Corresponsalia");
		debug("DAOConsultaCorresponsalesDLA2->ConsultaCorresponsalias");

		final BeanError beanError = new BeanError();

		debug("Validando facultades del usuario sobre transaccion " + TRAN390);
		if (!Facultades.getInstanceOf().trasaccionAutorizada(
				beanArq.getPerfil(), TRAN390)) {
			debug("El usuario no tiene facultades sobre transaccion " + TRAN390);
			beanError.setCodigoError(Facultades.CODIGO_ERROR);
			beanError.setMsgError(Facultades.MENSAJE_ERROR);
			return beanError;
		}
		List<BeanCorresponsal> listaRegistros = null;
		Object objetoRegreso = null;
		try {
			final RequestMessageCicsDTO requestDTO = new RequestMessageCicsDTO();
			ResponseMessageCicsDTO responseDTO = null;
			final DataAccess dataAccess = DataAccess.getInstance(requestDTO,
					getLoggingBean());
			requestDTO.setTransaction(TRAN390);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setMessage(getMessage(beanConsulta));
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
							lstrTramaDesentramar, "@DCDLMA220 P")) {
						debug("La trama contiene un formato de paginacion....");
						final boolean hayMasDatos = lstrTrama
								.contains("NO HAY MAS DATOS");
						final String lstrTramaPaginacion = TratamientoTrama
								.obtenerTramaPaginacion(lstrTramaDesentramar,
										"@DCDLMA220 P");
						debug("Trama Paginacion:" + lstrTramaPaginacion);
						setRegistroInicial(getReferenciaRetroceder(lstrTramaPaginacion));
						setRegistroFinal(getReferenciaAvanzar(lstrTramaPaginacion));
						debug("Registro inicial :" + lstrReferenciaAvanzar);
						debug("Registro final:" + lstrReferenciaRetroceder);
						lstrTramaDesentramar = TratamientoTrama
								.eliminarTramaPaginacion(lstrTramaDesentramar,
										"@DCDLMA220 P");
						if ("".equalsIgnoreCase(beanConsulta
								.getLimiteInferiorConsulta())
								&& "".equalsIgnoreCase(beanConsulta
										.getLimiteSuperiorConsulta())) {
							setMasAtras(false); // no hay pagina atras, estamos
												// en la pagina uno
						} else{
							setMasAtras(true); // estamos en la paginas
						}
												// siguientes

						if (!hayMasDatos) { // hay pagina a avanzar
							setMasAdelante(true);
						} else{
							setMasAdelante(false); // ya no hay paginas a avanzar
						}
													
					} else {
						setMasAtras(false); // no hay paginacion
						setMasAdelante(false);

					}

					debug("Trama desentramar Registros:" + lstrTramaDesentramar);
					if ("D".equals(beanConsulta.getTipoConsulta())){
						listaRegistros = getRegistrosDetalle(lstrTramaDesentramar);
						}
					else{
						listaRegistros = getRegistrosLista(lstrTramaDesentramar);
					}
					objetoRegreso = listaRegistros;

				}
			} else {
				beanError.setCodigoError(responseDTO.getCodeError());
				beanError.setCodigoError(responseDTO.getMessageError());
				objetoRegreso = beanError;
			}
		} catch (ExceptionDataAccess e) {
			showException(e);
			beanError.setCodigoError(e.getCode());
			beanError.setCodigoError(e.getMessage());
			objetoRegreso = beanError;
		}
		return objetoRegreso;
	}

	/**
	 * Gets the message.
	 * 
	 * @param beanConsulta
	 *            the bean consulta
	 * @return the message
	 */
	private String getMessage(BeanMttoConsultaCorresponsal beanConsulta) {
		String message = "";
		final StringBuffer trama = new StringBuffer();
		trama
				.append(Utils.rpad(beanConsulta.getCodigoCorresponsalia(), " ",
						2));
		trama.append(Utils.rpad(beanConsulta.getCodigoCorresponsal(), " ", 4));
		trama.append(Utils.rpad(beanConsulta.getIndicadorPaginacion(), " ", 1));
		trama.append(Utils.rpad(beanConsulta.getIndicadorDireccion(), " ", 1));
		trama.append(Utils.rpad(beanConsulta.getTipoConsulta(), " ", 1));
		trama.append(Utils.rpad(beanConsulta.getLimiteInferiorConsulta(), " ",
				4));
		trama.append(Utils.rpad(beanConsulta.getLimiteSuperiorConsulta(), " ",
				4));
		debug("Longitud Trama:" + trama.length());

		message = trama.toString();
		debug("Trama:" + message);

		return message;
	}

	/**
	 * Gets the registros detalle.
	 * 
	 * @param reesponseMessage
	 *            the reesponse message
	 * @return the registros detalle
	 */
	private List<BeanCorresponsal> getRegistrosDetalle(String reesponseMessage) {

		final List<BeanCorresponsal> listaRegistros = new ArrayList<BeanCorresponsal>();
		final List<String> listaRegistrosTratamiento = TratamientoTrama
				.parteTrama(reesponseMessage, "@DCDLMA230 P");
		debug("Numero de Registros Obtenidos:"
				+ listaRegistrosTratamiento.size());
		for (String registro : listaRegistrosTratamiento) {
			debug("Registro:" + registro);
			final BeanCorresponsal beanConsulta = new BeanCorresponsal();

			beanConsulta.setCodigoCorresponsalia(registro.substring(0, 2)
					.trim());
			beanConsulta.setCodigoCorresponsal(registro.substring(2, 6).trim());
			beanConsulta.setCentroCosto(registro.substring(2, 6).trim());
			beanConsulta
					.setEstatusCorresponsal(registro.substring(6, 9).trim());
			beanConsulta.setIndicadorConciliacion(registro.substring(9, 10)
					.trim());
			beanConsulta.setSecuenciaDomicilio(registro.substring(10, 13)
					.trim());
			beanConsulta.setGiroEmpresa(registro.substring(13, 17).trim());
			beanConsulta.setNombreCorresponsal(registro.substring(17, 57)
					.trim());
			beanConsulta.setParamDiasPendientesConciliar(registro.substring(57,
					60).trim());
			beanConsulta.setParamDiasPendientesCompensar(registro.substring(60,
					63).trim());
			beanConsulta.setIndicadorValidaComision(registro.substring(63, 64)
					.trim());
			beanConsulta.setCuentaChequesCorresponsal(registro
					.substring(64, 84).trim());
			beanConsulta.setDivisaAsocCuentaCheques(registro.substring(84, 87)
					.trim());
			beanConsulta.setLineaCreditoCorresponsal(registro
					.substring(87, 107).trim());
			beanConsulta.setDivisaAsocLineaCredito(registro.substring(107, 110)
					.trim());
			beanConsulta.setLimiteImporteCorresponsal(registro.substring(110,
					125).trim());
			beanConsulta.setCorreoContacto(registro.substring(125, 145).trim());
			beanConsulta.setCorreoAlternoContacto(registro.substring(145, 165)
					.trim());
			beanConsulta.setTelefonoAclaracionCorresponsal(registro.substring(
					165, 175).trim());
			beanConsulta.setCalleDomicilioCorresponsal(registro.substring(175,
					205).trim());
			beanConsulta.setNumeroExteriorCorresponsal(registro.substring(205,
					210).trim());
			beanConsulta.setNumeroInteriorCorresponsal(registro.substring(210,
					219).trim());
			beanConsulta.setColoniaDomicilioCorresponsal(registro.substring(
					219, 249).trim());
			beanConsulta.setDelegacionMunicipioCorresponsal(registro.substring(
					249, 279).trim());
			beanConsulta.setCiudadCorresponsal(registro.substring(279, 299)
					.trim());
			beanConsulta.setEstadoCorresponsal(registro.substring(299, 319)
					.trim());
			beanConsulta.setCodigoPostalCorresponsal(registro.substring(319,
					327).trim());
			beanConsulta.setPaisProcedenciaCorresponsal(registro.substring(327,
					347).trim());
			beanConsulta.setTelefonoCorresponsal(registro.substring(347, 357)
					.trim());

			listaRegistros.add(beanConsulta);
		}

		return listaRegistros;
	}

	/**
	 * Gets the registros lista.
	 * 
	 * @param reesponseMessage
	 *            the reesponse message
	 * @return the registros lista
	 */
	private List<BeanCorresponsal> getRegistrosLista(String reesponseMessage) {

		final List<BeanCorresponsal> listaRegistros = new ArrayList<BeanCorresponsal>();
		final List<String> listaRegistrosTratamiento = TratamientoTrama
				.parteTrama(reesponseMessage, "@DCDLMA210 P");
		debug("Numero de Registros Obtenidos:"
				+ listaRegistrosTratamiento.size());
		for (String registro : listaRegistrosTratamiento) {
			debug("Registro:" + registro);
			final BeanCorresponsal beanConsulta = new BeanCorresponsal();

			beanConsulta.setCodigoCorresponsalia(registro.substring(0, 2)
					.trim());
			beanConsulta.setCodigoCorresponsal(registro.substring(2, 6).trim());
			beanConsulta
					.setEstatusCorresponsal(registro.substring(6, 9).trim());
			beanConsulta
					.setNombreCorresponsal(registro.substring(9, 49).trim());
			if ("CS2".equals(beanConsulta.getEstatusCorresponsal())) {
				beanConsulta.setActivo(true);
			} else {
				beanConsulta.setActivo(false);
			}
			listaRegistros.add(beanConsulta);
		}
		return listaRegistros;
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
		lstrReferencia = tramaPaginacion.substring(4, 8).trim();
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
		lstrReferencia = tramaPaginacion.substring(0, 4).trim();
		return lstrReferencia;
	}
}
