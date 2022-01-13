package com.isban.corresponsalia.bo.corresponsales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;


import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCorresponsal;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanResultadoOperacionesCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanComisiones;
import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.comunes.BeanOperacion;

import com.isban.corresponsalia.beans.corresponsales.BeanAltaModificaComisiones;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaComision;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoComision;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.dao.auditoria.DAOAuditor;
import com.isban.corresponsalia.dao.canalcorresponsalia.DAOConsultaOperacionesCorresponsalDLA0;
import com.isban.corresponsalia.dao.corresponsales.DAOAltaModificacionComisionesDLB1;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaComisionesDLB2;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaCorresponsalesDLA2;
import com.isban.corresponsalia.dao.sesiones.DAOSesiones;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;


/**
 * Session Bean implementation class BOCorresponsalesComisionesImpl.
 */
@Stateless
public class BOCorresponsalesComisionesImpl extends ArchitechEBE implements
		BOCorresponsalesComisiones {

	/** The dao consulta comisiones. */
	@EJB
	transient private DAOConsultaComisionesDLB2 daoConsultaComisiones;
	
	/** The dao consulta corresponsales. */
	@EJB
	transient private DAOConsultaCorresponsalesDLA2 daoConsultaCorresponsales;
	
	/** The dao alta modificacion. */
	@EJB
	transient private DAOAltaModificacionComisionesDLB1 daoAltaModificacion;
	
	/** The dao consulta operaciones catalogo. */
	@EJB
	transient private DAOConsultaOperacionesCorresponsalDLA0 daoConsultaOperacionesCatalogo;
	
	/** The sesiones. */
	@EJB
	transient private DAOSesiones sesiones;
	
	/** The auditor. */
	@EJB
	transient private DAOAuditor auditor;

	/**
	 * Consulta corresponsales.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 * @throws BusinessException the business exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoCorresponsalia consultaCorresponsales(
			BeanMttoConsultaCorresponsal beanConsulta,
			ArchitechSessionBean psession) throws BusinessException {

		debug("BOCorresponsalesComisiones->ConsultaCorresponsales");

		sesiones.actualizaSession(psession);
		auditor.auditaoperacion("Comisiones::ConsultaCorresponsales", "CORBAN",
				"ConsultaCorresponsales", psession);

		final BeanResultadoCorresponsalia beanRespuesta = new BeanResultadoCorresponsalia();

		final Object regresoConsulta = daoConsultaCorresponsales
				.consultaCorresponsalias(beanConsulta, psession);

		if (regresoConsulta instanceof BeanError) {
			final BeanError beanError = (BeanError) regresoConsulta;
			beanRespuesta.setCodError(beanError.getCodigoError());
			beanRespuesta.setMsgError(beanError.getMsgError());
		} else {
			final ArrayList<BeanCorresponsal> argResultado = (ArrayList<BeanCorresponsal>) regresoConsulta;

			beanRespuesta.setRegistros(argResultado);
		}

		return beanRespuesta;
	}

	/**
	 * Consulta comisiones.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado comision
	 * @throws BusinessException the business exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoComision consultaComisiones(
			BeanConsultaComision beanConsulta, ArchitechSessionBean psession)
			throws BusinessException {
		debug("BOCorresponsalesComisiones->ConsultaComisiones");

		sesiones.actualizaSession(psession);
		auditor.auditaoperacion("Comisiones::ConsultaComisiones", "CORBAN",
				"Comisiones", psession);

		final BeanResultadoComision beanResultado = new BeanResultadoComision();
		final Object regresoConsulta = daoConsultaComisiones.consultaComisiones(
				beanConsulta, psession);
		if (regresoConsulta instanceof BeanError) {
			final BeanError beanError = (BeanError) regresoConsulta;
			beanResultado.setCodError(beanError.getCodigoError());
			beanResultado.setMsgError(beanError.getMsgError());
		} else {
			final Map<String, Object> result = (HashMap<String, Object>) regresoConsulta;
			if (regresoConsulta == null
					|| (regresoConsulta != null && (ArrayList<BeanComisiones>) result
							.get("registros") == null)) {

				beanResultado.setCodError("DLE001");
				beanResultado.setMsgError("Servicio No Disponible");
			} else {
				final ArrayList<BeanComisiones> registros = (ArrayList<BeanComisiones>) result
						.get("registros");

				for (BeanComisiones beanAux : (ArrayList<BeanComisiones>) registros) {
					beanAux
							.setImporteComisionTotalFront(formatoImportesToFront(beanAux
									.getImporteComisionTotal()));
					beanAux
							.setImporteComisionClienteMontoFijoFront(formatoImportesToFront(beanAux
									.getImporteComisionClienteMontoFijo()));
					beanAux
							.setImporteComisionBancoMontoFijoFront(formatoImportesToFront(beanAux
									.getImporteComisionBancoMontoFijo()));
					beanAux
							.setIvaTotalComisionFront(formatoImportesToFront(beanAux
									.getIvaTotalComision()));
				}

				beanResultado.setRegistros(registros);
			}
		}

		return beanResultado;
	}

	/**
	 * Alta modifica comisiones.
	 *
	 * @param beanAltaModificacion the bean Alta Modificacion
	 * @param psession the psession
	 * @return the bean error
	 * @throws BusinessException the business exception
	 */
	@Override
	public BeanError altaModificaComisiones(
			BeanAltaModificaComisiones beanAltaModificacion,
			ArchitechSessionBean psession) throws BusinessException {

		beanAltaModificacion
				.setImporteComisionClienteMontoFijo(formatoImportesTo390(beanAltaModificacion
						.getImporteComisionClienteMontoFijoFront()));
		beanAltaModificacion
				.setImporteComisionBancoMontoFijo(formatoImportesTo390(beanAltaModificacion
						.getImporteComisionBancoMontoFijoFront()));
		beanAltaModificacion
				.setImporteComisionTotal(formatoImportesTo390(beanAltaModificacion
						.getImporteComisionTotalFront()));

		final BeanError beanError = daoAltaModificacion.modificacionComisiones(
				beanAltaModificacion, psession);

		info("Fin BO Modificacion Comisiones");
		return beanError;
	}

	/**
	 * Consulta operaciones.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado operaciones corresponsal
	 * @throws BusinessException the business exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoOperacionesCorresponsal consultaOperaciones(
			BeanConsultaOperacionesCorresponsal beanConsulta,
			ArchitechSessionBean psession) throws BusinessException {

		final BeanResultadoOperacionesCorresponsal beanRespuesta = new BeanResultadoOperacionesCorresponsal();
		info("Inicio Consulta Corresponsalia BO Catalogo");
		//Object objetoRegreso;
		List<BeanOperacion> respuestaBo = null;

		final Map<String, Object> resultado = daoConsultaOperacionesCatalogo
				.consultaOperaciones(beanConsulta, psession);

		final BeanError error = (BeanError) resultado.get("error");

		respuestaBo = (ArrayList<BeanOperacion>) resultado.get("registros");

		if (error.getCodigoError().indexOf("DLE") > -1) {
			beanRespuesta.setCodError(error.getCodigoError());
			beanRespuesta.setMsgError(error.getMsgError());
			return beanRespuesta;
		} else {
			if (respuestaBo != null) {
				beanRespuesta.setCodError("0");
				beanRespuesta.setReferenciaAvanzar((String) resultado
						.get("referenciaAvanzar"));
				beanRespuesta.setReferenciaRetroceder((String) resultado
						.get("referenciaRetroceder"));
				beanRespuesta.setMasAdelante((Boolean) resultado
						.get("masAdelante"));
				beanRespuesta.setMasAtras((Boolean) resultado.get("masAtras"));

				for (BeanOperacion beanAux : respuestaBo) {
					beanAux
							.setImporteMaximoOperacionFront(formatoImportesToFront(beanAux
									.getImporteMaximoOperacion()));
					beanAux
							.setImporteMinimoOperacionFront(formatoImportesToFront(beanAux
									.getImporteMinimoOperacion()));
					beanAux
							.setAcumuladoDiarioFront(formatoImportesToFront(beanAux
									.getLimiteImporteMaximoDiarioAcum()));
					beanAux
							.setAcumuladoMensualFront(formatoImportesToFront(beanAux
									.getLimiteImporteMaximoMensualAcum()));
					beanAux.setHoraInicioFront(formatoHoraToFront(beanAux
							.getHoraInicio()));
					beanAux.setHoraFinalFront(formatoHoraToFront(beanAux
							.getHoraFinal()));
				}
				beanRespuesta.setRegistros(respuestaBo);
			} else {
				beanRespuesta.setCodError("ERROR");
				beanRespuesta.setMsgError("Error al consultar Corresponsalias");
			}
		}

		info("Fin Consulta Operaciones BO ");
		return beanRespuesta;
	}

	/**
	 * Formato importes to390.
	 *
	 * @param importe the importe
	 * @return the string
	 */
	private String formatoImportesTo390(String importe) {

		String importeTo390 = "";

		try {
			if (importe.indexOf('.') > 0) {
				importeTo390 = importe.substring(0, importe.length() - 3)
						.concat(importe.substring(importe.length() - 2));
			} else {
				importeTo390 = importe.concat("00");
			}
		} catch (StringIndexOutOfBoundsException e) {
			error("Dato incorrecto en importe");
		}

		return importeTo390;
	}

	/**
	 * Formato importes to front.
	 *
	 * @param importe the importe
	 * @return the string
	 */
	private String formatoImportesToFront(String importe) {

		String importeToFront = "";
		try{
		importeToFront = importe.substring(0, importe.length() - 2).concat(
					".").concat(importe.substring(importe.length() - 2));
		}catch(StringIndexOutOfBoundsException e){
			debug("Formato de importe no adecuado");
		}
			for (int aux = 0; aux < importe.length() - 3; aux++) {
				if (importeToFront.indexOf('0') == 0) {
					importeToFront = importeToFront.substring(1);
				}
			}

		return importeToFront;
	}

	/**
	 * Formato hora to front.
	 *
	 * @param hora the hora
	 * @return the string
	 */
	private String formatoHoraToFront(String hora) {
		String horaToFront = "";
		try{
			horaToFront = hora.substring(0, hora.length() - 4).concat(":")
					.concat(
							hora
									.substring(hora.length() - 4,
											hora.length() - 2));
		}catch(StringIndexOutOfBoundsException e){
			debug("Formato hora no valido");
		}
		return horaToFront;
	}

}
