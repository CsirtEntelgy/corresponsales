package com.isban.corresponsalia.dao.corresponsales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.comunes.BeanSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaSucursal;
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
 * The Class DAOConsultaSucursalDLA6FixImpl.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOConsultaSucursalDLA6FixImpl extends ArchitechEBE implements
		DAOConsultaSucursalDLA6Fix {
	
	/** The sucursal inicial */
	private String sucursalInicial = "";
	
	/** The sucursal final */
	private String sucursalFinal = "";
	
	/** The TRA n390. */
	transient private static final String TRAN390 = "DLA6";

	/** The codoper. */
	transient private static final String CODOPER = "ConsultaSucursalDLA6";

	/** The id canal. */
	transient private final String idCanal = Alias.getAlias("CORRESPONSALIA");

	/** The registro inicial. */
	transient private String registroInicial = "";

	/** The registro final. */
	transient private String registroFinal = "";

	/** The mas adelante. */
	transient private boolean masAdelante = false;

	/** The mas atras. */
	transient private boolean masAtras = false;

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
	 * @param registroInicial the new registro inicial
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
	 * @param registroFinal the new registro final
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
	 * @param masAdelante the new mas adelante
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
	 * @param masAtras the new mas atras
	 */
	public void setMasAtras(boolean masAtras) {
		this.masAtras = masAtras;
	}

	/**
	 * Gets the sucursal inicial.
	 * 
	 * @return
	 */
	public String getSucursalInicial() {
		return sucursalInicial;
	}

	/**
	 * Sets the sucursal inicial
	 * 
	 * @param sucursalInicial
	 */
	public void setSucursalInicial(String sucursalInicial) {
		this.sucursalInicial = sucursalInicial;
	}

	/**
	 * Gets the sucursal final.
	 * 
	 * @return the sucursalFinal
	 */
	public String getSucursalFinal() {
		return sucursalFinal;
	}

	/**
	 * Sets the sucursal final.
	 * 
	 * @param sucursalFinal the sucursalFinal to set
	 */
	public void setSucursalFinal(String sucursalFinal) {
		this.sucursalFinal = sucursalFinal;
	}

	/**
	 * Consulta sucursales.
	 *
	 * @param beanConsulta the bean consulta
	 * @param beanArq      the bean Arq
	 * @return the object
	 */
	@Override
	public Object consultaSucursales(BeanConsultaSucursal beanConsulta,
			ArchitechSessionBean beanArq) {
		debug("DAOConsultaSucursalDLA6Fix->ConsultaSucursales");

		final BeanError beanError = new BeanError();
		String lstrReferenciaAvanzar = "";
		String lstrReferenciaRetroceder = "";

		debug("Validando facultades del usuario sobre transaccion " + TRAN390);
		if (!Facultades.getInstanceOf().trasaccionAutorizada(
				beanArq.getPerfil(), TRAN390)) {
			debug("El usuario no tiene facultades sobre transaccion " + TRAN390);
			beanError.setCodigoError(Facultades.CODIGO_ERROR);
			beanError.setMsgError(Facultades.MENSAJE_ERROR);
			return beanError;
		}
		List<BeanSucursal> listaRegistros = new ArrayList<BeanSucursal>();
		final Map<String, Object> objReturn = new HashMap<String, Object>();
		try {

			final RequestMessageCicsDTO requestDTO = new RequestMessageCicsDTO();
			ResponseMessageCicsDTO responseDTO = null;
			final DataAccess dataAccess = DataAccess.getInstance(requestDTO,
					getLoggingBean());
			String lstrTrama = null;

			requestDTO.setTransaction(TRAN390);
			requestDTO.setCodeOperation(CODOPER);
			do {
				requestDTO.setMessage(getMessage(beanConsulta));

				responseDTO = (ResponseMessageCicsDTO) dataAccess
						.execute(idCanal);
				debug("Codigo de Error     :" + responseDTO.getCodeError());
				debug("Mensaje de Error    :" + responseDTO.getMessageError());
				debug("Mensaje de Respuesta:"
						+ responseDTO.getResponseMessage());

				lstrTrama = responseDTO.getResponseMessage();

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
						if (TratamientoTrama.tramaTieneFormatoPaginacion(
								lstrTramaDesentramar, "@DCDLMA630 P")) {
							debug("La trama contiene un formato de paginacion....");
							final String lstrTramaPaginacion = TratamientoTrama
									.obtenerTramaPaginacion(
											lstrTramaDesentramar,
											"@DCDLMA630 P");
							debug("Trama Paginacion:" + lstrTramaPaginacion);
							lstrReferenciaAvanzar = getReferenciaAvanzar(lstrTramaPaginacion);
							lstrReferenciaRetroceder = getReferenciaRetroceder(lstrTramaPaginacion);
							lstrTramaDesentramar = TratamientoTrama
									.eliminarTramaPaginacion(
											lstrTramaDesentramar,
											"@DCDLMA630 P");

						}
						debug("Trama desentramar Registros:"
								+ lstrTramaDesentramar);
						if ("D".equals(beanConsulta.getIndicadorFuncional())) {
							listaRegistros = getRegistrosDetalle(lstrTramaDesentramar);
						} else {
							listaRegistros
								.addAll(getRegistrosLista(lstrTramaDesentramar));
						}
						if (lstrTrama != null
								&& (lstrTrama.contains("NO HAY MAS DATOS")
								|| lstrTrama.contains("LA CONSULTA HA SIDO REALIZADA"))) {
							objReturn.put("masAdelante", Boolean.FALSE);
						} else {
							objReturn.put("masAdelante", Boolean.TRUE);
						}
						if ("".equalsIgnoreCase(beanConsulta
								.getLimiteInferiorConsulta())
								&& "".equals(beanConsulta
										.getLimiteSuperiorConsulta())) {
							objReturn.put("masAtras", Boolean.FALSE);
						} else {
							objReturn.put("masAtras", Boolean.TRUE);
						}

						objReturn.put("registros", listaRegistros);
						objReturn.put("referenciaAvanzar",
								lstrReferenciaAvanzar);
						objReturn.put("referenciaRetroceder",
								lstrReferenciaRetroceder);
						objReturn.put("nombreSucursalAtras", 
								getRegistrosLista(lstrTramaDesentramar).get(0).getNombreSucursal());
						objReturn.put("nombreSucursal", getRegistroFinal());
						
					}
				} else {
					beanError.setCodigoError(responseDTO.getCodeError());
					beanError.setCodigoError(responseDTO.getMessageError());
					return beanError;
				}
				if (!beanConsulta.isPaginada()) {
					beanConsulta.setIndicadorPaginacion("P");
					beanConsulta.setIndicadorFuncional("L");
					beanConsulta.setIndPaginacion("A");
					beanConsulta.setLimiteInferiorConsulta(lstrReferenciaRetroceder);
					beanConsulta.setLimiteSuperiorConsulta(lstrReferenciaAvanzar);
					beanConsulta.setNombreSucursal(getRegistroFinal());
				}
			} while (!beanConsulta.isPaginada()
					&& !lstrTrama.contains("NO HAY MAS DATOS")
					&& lstrTrama.contains("HAY MAS DATOS"));
		} catch (ExceptionDataAccess e) {
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
	private String getMessage(BeanConsultaSucursal beanConsulta) {
		String message = "";
		final StringBuffer trama = new StringBuffer();
		trama.append(Utils.rpad(beanConsulta.getCodigoCorresponsalia(), " ", 4));
		trama.append(Utils.rpad(beanConsulta.getCodigoSucursal(), " ", 10));
		trama.append(Utils.rpad(beanConsulta.getIdEstatusSuc(), " ", 3));
		trama.append(Utils.rpad(beanConsulta.getRegionSucursal(), " ", 4));
		trama.append(Utils.rpad(beanConsulta.getZonaSucursal(), " ", 5));
		trama.append(Utils.rpad(beanConsulta.getIndicadorFuncional(), " ", 1));
		trama.append(Utils.rpad(beanConsulta.getIndicadorPaginacion(), " ", 1));
		trama.append(Utils.rpad(beanConsulta.getIndPaginacion(), " ", 1));
		trama.append(Utils.rpad(beanConsulta.getLimiteInferiorConsulta(), " ", 10));
		trama.append(Utils.rpad(beanConsulta.getLimiteSuperiorConsulta(), " ", 10));
		trama.append(Utils.rpad(beanConsulta.getNombreSucursal(), " ", 40));
		debug("Longitud Trama:" + trama.length());
		message = trama.toString();
		debug("Trama:" + message);
		return message;
	}

	/**
	 * Gets the registros detalle.
	 *
	 * @param reesponseMessage the reesponse message
	 * @return the registros detalle
	 */
	private List<BeanSucursal> getRegistrosDetalle(String reesponseMessage) {

		final List<BeanSucursal> listaRegistros = new ArrayList<BeanSucursal>();
		final List<String> listaRegistrosTratamiento = TratamientoTrama.parteTrama(
				reesponseMessage, "@DCDLMA620 P");

		debug("Numero de Registros Obtenidos:"
				+ listaRegistrosTratamiento.size());
		for (String registro : listaRegistrosTratamiento) {
			debug("Registro:" + registro);
			final BeanSucursal beanSucursalTemp = new BeanSucursal();

			beanSucursalTemp.setIdCorresponsal(registro.substring(0, 4).trim()); 		// Id del corresponsal

			beanSucursalTemp.setNumId(registro.substring(4, 14).trim()); 				// Codigo de la sucursal
			
			beanSucursalTemp.setCodigoEstatus(registro.substring(14, 17).trim()); 		// Id estatus

			beanSucursalTemp.setDescEstatus(registro.substring(17, 57).trim()); 		// Descripcion estatus

			beanSucursalTemp.setRfc(registro.substring(57, 70).trim()); 				// RFC de la sucursal 

			beanSucursalTemp.setRegion(registro.substring(70, 74).trim()); 				// Region de la sucursal

			beanSucursalTemp.setZonaGeografica(registro.substring(74, 79).trim()); 		// Zona sucursal
			
			beanSucursalTemp.setDescZona(registro.substring(79, 109).trim()); 			// Desc zona sucursal

			beanSucursalTemp.setFronteriza(registro.substring(109, 110).trim());		// Fronteriza sucursal

			beanSucursalTemp.setNombreSucursal(registro.substring(110, 150).trim());	// Nombre de la sucursal
			
			beanSucursalTemp.setCodigoInterno(registro.substring(150, 160).trim()); 	// Codigo interno sucursal
			
			beanSucursalTemp.setCalle(registro.substring(160, 180).trim()); 			// Calle de la sucursal

			beanSucursalTemp.setNoExterior(registro.substring(180, 185).trim()); 		// Num exterior

			beanSucursalTemp.setNoInterior(registro.substring(185, 190).trim()); 		// Num interior

			beanSucursalTemp.setColonia(registro.substring(190, 210).trim()); 			// Colonia
			
			beanSucursalTemp.setDelegMunicipio(registro.substring(210, 230).trim()); 	// Delegacion o municipio
			
			beanSucursalTemp.setCiudad(registro.substring(230, 250).trim()); 			// Ciudad
			
			beanSucursalTemp.setEstado(registro.substring(250, 270).trim()); 			// Entidad federativa
			
			beanSucursalTemp.setCodigoPostal(registro.substring(270, 278).trim()); 		// Codigo postal
			
			beanSucursalTemp.setEntreCalles(registro.substring(278, 318).trim()); 		// Desc entre calles
			
			beanSucursalTemp.setTelefono(registro.substring(318, 328).trim()); 			// Telefono sucursal

			listaRegistros.add(beanSucursalTemp);
		}

		return listaRegistros;
	}

	/**
	 * Gets the registros lista.
	 *
	 * @param reesponseMessage the reesponse message
	 * @return the registros lista
	 */
	private List<BeanSucursal> getRegistrosLista(String reesponseMessage) {

		final List<BeanSucursal> listaRegistros = new ArrayList<BeanSucursal>();
		final List<String> listaRegistrosTratamiento = TratamientoTrama.parteTrama(
				reesponseMessage, "@DCDLMA610 P");

		debug("Numero de Registros obtenidos:"
				+ listaRegistrosTratamiento.size());
		for (String registro : listaRegistrosTratamiento) {
			debug("Registro:" + registro);
			final BeanSucursal beanSucursalTemp = new BeanSucursal();

			beanSucursalTemp.setNumId(registro.substring(0, 10).trim()); 			// Codigo sucursal

			beanSucursalTemp.setCodigoEstatus(registro.substring(10, 13).trim()); 	// Id estatus

			beanSucursalTemp.setDescEstatus(registro.substring(13, 53).trim()); 	// Descripcion estatus

			beanSucursalTemp.setNombreSucursal(registro.substring(53, 93).trim()); 	// Nombre de la sucursal

			listaRegistros.add(beanSucursalTemp);
			if ("".equals(getRegistroInicial())) {
				setRegistroInicial(registro.substring(53, 93).trim());
			}
			setRegistroFinal(registro.substring(53, 93).trim());
		}

		return listaRegistros;
	}

	/**
	 * Gets the referencia avanzar.
	 *
	 * @param tramaPaginacion the trama paginacion
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
	 * @param tramaPaginacion the trama paginacion
	 * @return the referencia retroceder
	 */
	private String getReferenciaRetroceder(String tramaPaginacion) {
		String lstrReferencia = "";
		lstrReferencia = tramaPaginacion.substring(0, 10).trim();
		return lstrReferencia;
	}
}
