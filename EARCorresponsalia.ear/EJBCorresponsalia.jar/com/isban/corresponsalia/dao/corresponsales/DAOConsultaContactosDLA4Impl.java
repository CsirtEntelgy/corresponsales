package com.isban.corresponsalia.dao.corresponsales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.comunes.BeanContacto;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaContactos;
import com.isban.corresponsalia.comunes.Utils;
import com.isban.corresponsalia.comunes.UtilsCadenas;
import com.isban.dataaccess.DataAccess;
import com.isban.dataaccess.channels.cics.dto.RequestMessageCicsDTO;
import com.isban.dataaccess.channels.cics.dto.ResponseMessageCicsDTO;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.ExceptionDataAccess;

/**
 * The Class DAOConsultaContactosDLA4Impl.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOConsultaContactosDLA4Impl extends ArchitechEBE implements
		DAOConsultaContactosDLA4 {

	/** The TRA n390. */
	private final static String TRAN390 = "DLA4";

	/** The codoper. */
	private static final String CODOPER = "ConsultaContactosDLA4";

	/** The id canal. */
	transient private final String idCanal = getIdCanalDAO("CICS_CORRESPONSALIA");

	/** The arrg resultado. */
	transient private String[] arrgResultado;

	/** The bean resultado consulta. */
	transient private List<BeanContacto> beanResultadoConsulta;

	/** The referencia avanzar. */
	transient private String referenciaAvanzar = "";

	/** The referencia retroceder. */
	transient private String referenciaRetroceder = "";

	/** The mas adelante. */
	transient private boolean masAdelante = false;

	/** The mas atras. */
	transient private boolean masAtras = false;

	/** The bean consulta. */
	transient private BeanConsultaContactos beanConsulta = null;

	/**
	 * Consulta contactos.
	 * 
	 * @param beanConsulta
	 *            the bean consulta
	 * @param psession
	 *            the psession
	 * @return the hash map
	 */
	@Override
	public Map<String, Object> consultaContactos(
			BeanConsultaContactos beanConsulta, ArchitechSessionBean psession) {
		info("Inicia DAO Consulta Corresponsalia");
		final Map<String, Object> mapaResp = new HashMap<String, Object>();
		BeanError error = new BeanError();

		referenciaAvanzar = "";
		referenciaRetroceder = "";
		masAdelante = false;
		masAtras = false;

		this.beanConsulta = beanConsulta;
		String codigoError;
		// String mensajeError;
		String tramaSalida;
		ejecutaTransaccion(beanConsulta);

		codigoError = arrgResultado[0];
		// mensajeError = arrgResultado[1];
		tramaSalida = arrgResultado[2];

		if (tramaSalida.indexOf("@ER") > -1) {
			error = UtilsCadenas.desentramaError(tramaSalida);
		} else {
			info("Consulta Exitosa");
			desentrama(tramaSalida, beanConsulta.getTipoConsulta());
		}

		info("Finaliza DAO Consulta Corresponsalia :" + codigoError);
		mapaResp.put("error", error);
		mapaResp.put("registros", beanResultadoConsulta);
		mapaResp.put("referenciaAvanzar", referenciaAvanzar);
		mapaResp.put("referenciaRetroceder", referenciaRetroceder);
		mapaResp.put("masAtras", masAtras);
		mapaResp.put("masAdelante", masAdelante);
		return mapaResp;
	}

	/**
	 * Ejecuta la transaccion DLA0I Consulta de Corresponsalia.
	 * 
	 * @param beanConsulta
	 *            the bean consulta
	 * @return the string[]
	 */
	public String[] ejecutaTransaccion(BeanConsultaContactos beanConsulta) {

		info("Ejecutando DAOConsultaCorresponsaliaDLA0->consultaCorresponsalia");
		arrgResultado = new String[3];
		ResponseMessageCicsDTO responseDTO = null;

		try {
			final RequestMessageCicsDTO requestDTO = new RequestMessageCicsDTO();
			final DataAccess dataAccess = DataAccess.getInstance(requestDTO,
					getLoggingBean());

			requestDTO.setTransaction(TRAN390);
			requestDTO.setCodeOperation(CODOPER);
			requestDTO.setMessage(generaTramaEntrada(beanConsulta));
			info("TRAMA ENTRADA : " + requestDTO.getMessage());
			responseDTO = (ResponseMessageCicsDTO) dataAccess.execute(idCanal);
			info("TRAMA SALIDA -: " + responseDTO.getResponseMessage());
			debug("Codigo de Error     :" + responseDTO.getCodeError());
			debug("Mensaje de Error    :" + responseDTO.getMessageError());
			debug("Mensaje de Respuesta:" + responseDTO.getResponseMessage());
			if (!"".equals(responseDTO.getCodeError())) {
				info("Respuesta exitosa");
			}
			arrgResultado[0] = responseDTO.getCodeError();
			arrgResultado[1] = responseDTO.getMessageError();
			arrgResultado[2] = responseDTO.getResponseMessage();

		} catch (ExceptionDataAccess e) {
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
	 * @param beanConsulta
	 *            the bean consulta
	 * @return the string
	 */
	private String generaTramaEntrada(BeanConsultaContactos beanConsulta) {
		final StringBuffer tramaEntrada = new StringBuffer();

		tramaEntrada.append(Utils.rpad(beanConsulta.getCanal(), " ", 2));
		tramaEntrada.append(Utils
				.rpad(beanConsulta.getIdCorresponsal(), " ", 4));
		tramaEntrada.append(Utils.rpad(beanConsulta.getZonaUbicacionContacto(),
				" ", 5));
		tramaEntrada
				.append(Utils.rpad(beanConsulta.getSeqIdContacto(), " ", 5));
		tramaEntrada
				.append(Utils.rpad(beanConsulta.getIndPaginacion(), " ", 1));
		tramaEntrada.append(Utils.rpad(beanConsulta.getIndDireccion(), " ", 1));
		tramaEntrada.append(Utils.rpad(beanConsulta.getTipoConsulta(), " ", 1));
		tramaEntrada.append(Utils.rpad(beanConsulta.getLimInferior(), " ", 5));
		tramaEntrada.append(Utils.rpad(beanConsulta.getLimSuperior(), " ", 5));
		info("Sale de generar la trama :" + tramaEntrada.toString());
		return tramaEntrada.toString();

	}

	/**
	 * Desentrama la respuesta de 390.
	 * 
	 * @param trama
	 *            the trama
	 * @param tipoConsulta
	 *            the tipo consulta
	 */
	private void desentrama(String trama, String tipoConsulta) {

		BeanContacto beanRespuesta;
		beanResultadoConsulta = new ArrayList<BeanContacto>();
		String tramaRespuesta = "";
		String[] argResultadoTrama;
		if ("L".equals(tipoConsulta)) {
			if (trama.indexOf("@AVDLA0004") > -1
					|| trama.indexOf("@AVDLA0005") > -1) {
				trama = trama.substring(0, trama.length() - 2);
				argResultadoTrama = trama.split("Ó@DCDLMA410 P");
				for (int i = 1; i < argResultadoTrama.length; i++) {
					beanRespuesta = new BeanContacto();
					tramaRespuesta = argResultadoTrama[i];
					beanRespuesta
							.setConsecutivo(tramaRespuesta.substring(0, 5));
					beanRespuesta.setNombre(tramaRespuesta.substring(5, 65));
					beanRespuesta.setPuesto(tramaRespuesta.substring(65, 85));
					beanRespuesta.setTelefono(tramaRespuesta.substring(85, 95));
					beanRespuesta
							.setZonaUbic(tramaRespuesta.substring(95, 100));
					beanResultadoConsulta.add(beanRespuesta);
				}
			}// ERRROR
			if (trama.indexOf("@DCDLMA420") > -1) {
				trama = trama.substring(0, trama.length() - 2);
				argResultadoTrama = trama.split("Ó@DCDLMA420 P");
				for (int i = 1; i < argResultadoTrama.length; i++) {
					tramaRespuesta = argResultadoTrama[i];
					referenciaAvanzar = tramaRespuesta.substring(0, 5);
					referenciaRetroceder = tramaRespuesta.substring(5, 10);
					if ("B".equals(beanConsulta.getIndDireccion())) {
						masAdelante = true;
						if (!trama.contains("NO HAY MAS DATOS")) {
							masAtras = true;
						} else {
							masAtras = false;
						}
						debug("primer if");
					} else {
						if ("P".equals(beanConsulta.getIndPaginacion())) {
							masAtras = true;
						}
						if (trama.indexOf("AVDLA0005") > -1) {
							masAdelante = true;
						}
						debug("else");
					}
					debug("MasAtras: " + masAtras);
					debug("masAdelante: " + masAdelante);
				}
			}// ERRROR
		} else if ("D".equals(tipoConsulta) && trama.indexOf("@AVDLA0001") > -1) {
			/*
			 * [@112345@AVDLA0001 LA CONSULTA HA SIDO REALIZADAÓ@DCDLMA430
			 * P69960000000001MIGUEL ANGEL JIMÉNEZ CENTENO GERENTE SUCURSAL
			 * GERENCIA BLVD BERNARDO QUINTA962 A SAN PABLO SANTIAGO DE QRO
			 * QUERETARO QUERETARO 76130
			 * 442170447044236853814421704472MIGUELAJC1@OXXO.MX
			 * MIKE169@YAHOO.COM.MXÓ@SGDLA4 Ó?]|0
			 */
			trama = trama.substring(0, trama.length() - 2);
			argResultadoTrama = trama.split("Ó@DCDLMA430 P");
			for (int i = 1; i < argResultadoTrama.length; i++) {
				beanRespuesta = new BeanContacto();
				tramaRespuesta = argResultadoTrama[i];
				beanRespuesta.setIdCorresponsal(tramaRespuesta.substring(0, 4));
				beanRespuesta.setZonaUbic(tramaRespuesta.substring(4, 9));
				beanRespuesta.setConsecutivo(tramaRespuesta.substring(9, 14));
				beanRespuesta.setNombre(tramaRespuesta.substring(14, 74));
				beanRespuesta.setPuesto(tramaRespuesta.substring(74, 94));
				beanRespuesta.setArea(tramaRespuesta.substring(94, 104));
				beanRespuesta.setCalle(tramaRespuesta.substring(104, 124));
				beanRespuesta.setNumExt(tramaRespuesta.substring(124, 129));
				beanRespuesta.setNumInt(tramaRespuesta.substring(129, 134));
				beanRespuesta.setColonia(tramaRespuesta.substring(134, 154));
				beanRespuesta.setDelegMunic(tramaRespuesta.substring(154, 174));
				beanRespuesta.setCiudad(tramaRespuesta.substring(174, 194));
				beanRespuesta.setEntFede(tramaRespuesta.substring(194, 214));
				beanRespuesta.setCodPostal(tramaRespuesta.substring(214, 222));
				beanRespuesta.setTelefono(tramaRespuesta.substring(222, 232));
				beanRespuesta.setTelMovil(tramaRespuesta.substring(232, 242));
				beanRespuesta.setTelFax(tramaRespuesta.substring(242, 252));
				beanRespuesta.setEmail1(tramaRespuesta.substring(252, 272));
				beanRespuesta.setEmail2(tramaRespuesta.substring(272, 292));
				beanResultadoConsulta.add(beanRespuesta);
			}
		}
	}
}
