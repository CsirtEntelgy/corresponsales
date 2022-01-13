package com.isban.corresponsalia.bo.corresponsales;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanABMMantenimientoCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaPreAlta;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.dao.auditoria.DAOAuditor;
import com.isban.corresponsalia.dao.corresponsales.DAOABMCorresponsalesDLA1;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaCorresponsalesDLA2;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaCorresponsalesDLA2Pag;
import com.isban.corresponsalia.dao.sesiones.DAOSesiones;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;


/**
 * Session Bean implementation class BOCorresponsalesMantenimientoImp.
 */
@Stateless
public class BOCorresponsalesMantenimientoImp extends ArchitechEBE implements
		BOCorresponsalesMantenimiento {
	
	/** The Constant CORBAN. */
	final static private String CORBAN = "CORBAN";
	
	/** The Constant ERDES. */
	final static private String ERDES = "ERDES";
	
	/** The Constant ERROR_DESCONOCIDO. */
	final  static private String ERROR_DESCONOCIDO = "Error Desconicido";
	
	/** The dao consulta corresponsaliapag. */
	@EJB
	transient private DAOConsultaCorresponsalesDLA2Pag daoConsultaCorresponsaliapag;
	
	/** The dao consulta corresponsalia. */
	@EJB
	transient private DAOConsultaCorresponsalesDLA2 daoConsultaCorresponsalia;
	
	/** The dao abm corresponsalia. */
	@EJB
	transient private DAOABMCorresponsalesDLA1 daoABMCorresponsalia;
	
	/** The sesiones. */
	@EJB
	transient private DAOSesiones sesiones;
	
	/** The auditor. */
	@EJB
	transient private DAOAuditor auditor;

	/**
	 * Alta mantenimiento corresponsalia.
	 *
	 * @param beanAlta the bean alta
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 * @throws BusinessException the business exception
	 */
	@Override
	public BeanResultadoCorresponsalia altaMantenimientoCorresponsalia(
			BeanABMMantenimientoCorresponsal beanAlta,
			ArchitechSessionBean psession) throws BusinessException {
		debug("BOCorresponsalesMantenimiento->AltaMantenimientoCorresponsalia");

		sesiones.actualizaSession(psession);
		auditor.auditaoperacion(
				"MantenimientoCorresponsales::AltaMantenimientoCorresponsalia",
				CORBAN, "AltaMantenimientoCorresponsalia", psession);

		final BeanResultadoCorresponsalia beanRespuesta = new BeanResultadoCorresponsalia();
		Object objetoRegreso = null;

		beanAlta.setLimiteImporteCorresponsal(formatoImportesTo390(beanAlta
				.getLimiteImporteCorresponsalFront()));

		objetoRegreso = daoABMCorresponsalia.aBMCorresponsalias(beanAlta,
				psession);

		if (objetoRegreso instanceof BeanError) {
			final String lstrCodError = ((BeanError) objetoRegreso).getCodigoError();
			final String lstrMsgError = ((BeanError) objetoRegreso).getMsgError();
			if (lstrCodError.indexOf("ER") > -1){
				throw new BusinessException(this.getClass().getName(),
						lstrCodError, lstrMsgError);
			}
			beanRespuesta.setCodError(lstrCodError);
			beanRespuesta.setMsgError(lstrMsgError);
		} else{
			throw new BusinessException(this.getClass().getName(), ERDES,
					ERROR_DESCONOCIDO);
		}

		return beanRespuesta;
	}

	/**
	 * Consulta mantenimiento corresponsalia.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 * @throws BusinessException the business exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoCorresponsalia consultaMantenimientoCorresponsalia(
			BeanMttoConsultaCorresponsal beanConsulta,
			ArchitechSessionBean psession) throws BusinessException {
		debug("BOCorresponsalesMantenimiento->ConsultaMantenimientoCorresponsalia");

		sesiones.actualizaSession(psession);
		auditor
				.auditaoperacion(
						"MantenimientoCorresponsales::ConsultaMantenimientoCorresponsalia",
						CORBAN, "ConsultaMantenimientoCorresponsalia",
						psession);

		final BeanResultadoCorresponsalia beanResultado = new BeanResultadoCorresponsalia();
		final Object regresoConsulta = daoConsultaCorresponsaliapag
				.consultaCorresponsalias(beanConsulta, psession);

		if (regresoConsulta instanceof BeanError) {
			final BeanError beanError = (BeanError) regresoConsulta;
			beanResultado.setCodError(beanError.getCodigoError());
			beanResultado.setMsgError(beanError.getMsgError());
		} else {
			if (beanResultado != null) {
				final HashMap<String, Object> objResult = (HashMap<String, Object>) regresoConsulta;
				final ArrayList<BeanCorresponsal> listaCorresponsales = (ArrayList<BeanCorresponsal>) objResult
						.get("registros");
				if (listaCorresponsales != null && !listaCorresponsales.isEmpty()) {
					final String referenciaAvanzar = (String) objResult
							.get("referenciaAvanzar");
					final String referenciaRetroceder = (String) objResult
							.get("referenciaRetroceder");
					beanResultado.setMasAdelante((Boolean) objResult
							.get("masAdelante"));
					beanResultado.setMasAtras((Boolean) objResult
							.get("masAtras"));

					for (BeanCorresponsal beanAux : listaCorresponsales){
						beanAux.setLimiteImporteCorresponsalFront(formatoImportesToFront(beanAux
										.getLimiteImporteCorresponsal()));
					}
					beanResultado.setRegistros(listaCorresponsales);
					beanResultado.setReferenciaAvanzar(referenciaAvanzar);
					beanResultado.setReferenciaRetroceder(referenciaRetroceder);
				}else{					
					beanResultado.setCodError("ERR0001");
					beanResultado.setMsgError("NO EXISTEN REGISTROS");					
				}
			}
		}

		return beanResultado;
	}

	/**
	 * Elimina mantenimiento corresponsalia.
	 *
	 * @param beanCorresponsal the bean corresponsal
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 * @throws BusinessException the business exception
	 */
	@Override
	public BeanResultadoCorresponsalia eliminaMantenimientoCorresponsalia(
			BeanCorresponsal beanCorresponsal, ArchitechSessionBean psession)
			throws BusinessException {
		debug("BOCorresponsalesMantenimiento->EliminaMantenimientoCorresponsalia");

		sesiones.actualizaSession(psession);
		auditor
				.auditaoperacion(
						"MantenimientoCorresponsales::EliminaMantenimientoCorresponsalia",
						CORBAN, "EliminaMantenimientoCorresponsalia",
						psession);

		final BeanResultadoCorresponsalia beanRespuesta = new BeanResultadoCorresponsalia();
		final BeanABMMantenimientoCorresponsal beanEliminar = new BeanABMMantenimientoCorresponsal();

		debug("Eliminar Corresponsal:"
				+ beanCorresponsal.getCodigoCorresponsal());

		// beanEliminar.setCodigoCorresponsal(beanCorresponsal.getCodigoCorresponsal());
		beanEliminar.setCentroCosto(beanCorresponsal.getCodigoCorresponsal());
		beanEliminar.setCodigoCorresponsalia(beanCorresponsal
				.getCodigoCorresponsalia());
		beanEliminar.setTipoOperacion("B");

		final Object objetoRegreso = daoABMCorresponsalia.aBMCorresponsalias(
				beanEliminar, psession);

		if (objetoRegreso instanceof BeanError) {
			final String lstrCodError = ((BeanError) objetoRegreso).getCodigoError();
			final String lstrMsgError = ((BeanError) objetoRegreso).getMsgError();
			if (lstrCodError.indexOf("ER") > -1){
				throw new BusinessException(this.getClass().getName(),
						lstrCodError, lstrMsgError);
			}
			beanRespuesta.setCodError(lstrCodError);
			beanRespuesta.setMsgError(lstrMsgError);
		} else{
			throw new BusinessException(this.getClass().getName(), ERDES,
					ERROR_DESCONOCIDO);
		}
		return beanRespuesta;
	}

	/**
	 * Modificacion mantenimiento corresponsalia.
	 *
	 * @param beanModificacion the bean modificacion
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 * @throws BusinessException the business exception
	 */
	@Override
	public BeanResultadoCorresponsalia modificacionMantenimientoCorresponsalia(
			BeanABMMantenimientoCorresponsal beanModificacion,
			ArchitechSessionBean psession) throws BusinessException {
		debug("BOCorresponsalesMantenimiento->ModificacionMantenimientoCorresponsalia");

		sesiones.actualizaSession(psession);
		auditor
				.auditaoperacion(
						"MantenimientoCorresponsales::ModificacionMantenimientoCorresponsalia",
						CORBAN, "ModificacionMantenimientoCorresponsalia",
						psession);

		beanModificacion
				.setLimiteImporteCorresponsal(formatoImportesTo390(beanModificacion
						.getLimiteImporteCorresponsalFront()));

		final BeanResultadoCorresponsalia beanRespuesta = new BeanResultadoCorresponsalia();
		final Object objetoRegreso = daoABMCorresponsalia.aBMCorresponsalias(
				beanModificacion, psession);

		if (objetoRegreso instanceof BeanError) {
			String lstrCodError = ((BeanError) objetoRegreso).getCodigoError();
			String lstrMsgError = ((BeanError) objetoRegreso).getMsgError();
			if (lstrCodError.indexOf("ER") > -1){
				throw new BusinessException(this.getClass().getName(),
						lstrCodError, lstrMsgError);
			}
			beanRespuesta.setCodError(lstrCodError);
			beanRespuesta.setMsgError(lstrMsgError);
		} else{
			throw new BusinessException(this.getClass().getName(), ERDES,
					ERROR_DESCONOCIDO);
		}
		return beanRespuesta;
	}

	/**
	 * Consulta pre alta corresponsal.
	 *
	 * @param preAlta the pre alta
	 * @param psession the psession
	 * @return the bean consulta pre alta
	 * @throws BusinessException the business exception
	 */
	@Override
	public BeanConsultaPreAlta consultaPreAltaCorresponsal(
			BeanABMMantenimientoCorresponsal preAlta,
			ArchitechSessionBean psession) throws BusinessException {
		debug("BOCorresponsalesMantenimiento->ConsultaPreAltaCorresponsal");

		sesiones.actualizaSession(psession);
		auditor.auditaoperacion(
				"MantenimientoCorresponsales::ConsultaPreAltaCorresponsal",
				CORBAN, "ConsultaPreAltaCorresponsal", psession);

		final BeanConsultaPreAlta beanPreAlta = new BeanConsultaPreAlta();
		final Object objetoRegreso = daoABMCorresponsalia.aBMCorresponsalias(preAlta,
				getArchitechBean());

		if (objetoRegreso instanceof BeanError) {
			final String lstrCodError = ((BeanError) objetoRegreso).getCodigoError();
			final String lstrMsgError = ((BeanError) objetoRegreso).getMsgError();
			if (lstrCodError.indexOf("ER") > -1){
				throw new BusinessException(this.getClass().getName(),
						lstrCodError, lstrMsgError);
			}
			beanPreAlta.setCodError(lstrCodError);
			beanPreAlta.setMsgError(lstrMsgError);
		} else if (objetoRegreso instanceof BeanConsultaPreAlta) {
			return (BeanConsultaPreAlta) objetoRegreso;
		} else{
			throw new BusinessException(this.getClass().getName(), ERDES,
					ERROR_DESCONOCIDO);
		}
		return beanPreAlta;

	}

	/**
	 * Activa desactiva corresponsalia.
	 *
	 * @param beanCorresponsal the bean corresponsal
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 * @throws BusinessException the business exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoCorresponsalia activaDesactivaCorresponsalia(
			BeanCorresponsal beanCorresponsal, ArchitechSessionBean psession)
			throws BusinessException {
		debug("BOCorresponsalesMantenimiento->ActivaDesactivaCorresponsalia");

		sesiones.actualizaSession(psession);
		auditor.auditaoperacion(
				"MantenimientoCorresponsales::ActivaDesactivaCorresponsalia",
				CORBAN, "ActivaDesactivaCorresponsalia", psession);

		final BeanResultadoCorresponsalia beanRespuesta = new BeanResultadoCorresponsalia();
		final BeanABMMantenimientoCorresponsal beanModificar = new BeanABMMantenimientoCorresponsal();
		final BeanMttoConsultaCorresponsal beanConsulta = new BeanMttoConsultaCorresponsal();
		final BeanResultadoCorresponsalia beanResultado = new BeanResultadoCorresponsalia();

		beanConsulta.setCodigoCorresponsal(beanCorresponsal
				.getCodigoCorresponsal());
		beanConsulta.setCodigoCorresponsalia(beanCorresponsal
				.getCodigoCorresponsalia());
		beanConsulta.setTipoConsulta("D");
		beanConsulta.setIndicadorPaginacion("N");

		Object regresoConsulta = daoConsultaCorresponsalia
				.consultaCorresponsalias(beanConsulta, psession);

		if (regresoConsulta instanceof BeanError) {
			BeanError beanError = (BeanError) regresoConsulta;
			throw new BusinessException(this.getClass().getName(), beanError
					.getCodigoError(), beanError.getMsgError());
		} else{
			beanResultado
					.setRegistros((ArrayList<BeanCorresponsal>) regresoConsulta);
		}

		if (beanResultado.getRegistros().size() != 1){
			throw new BusinessException(this.getClass().getName(), "NODET",
					"No fue pósible obtener el detalle");
		}

		BeanCorresponsal beanDetalle = beanResultado.getRegistros().get(0);

		// beanModificar.setCodigoCorresponsal(beanCorresponsal.getCodigoCorresponsal());
		beanModificar.setCentroCosto(beanCorresponsal.getCodigoCorresponsal());
		beanModificar.setCodigoCorresponsalia(beanCorresponsal
				.getCodigoCorresponsalia());
		beanModificar.setCalleDomicilioCorresponsal(beanDetalle
				.getCalleDomicilioCorresponsal());
		beanModificar
				.setCiudadCorresponsal(beanDetalle.getCiudadCorresponsal());
		beanModificar.setCodigoPostalCorresponsal(beanDetalle.getCentroCosto());
		beanModificar.setColoniaDomicilioCorresponsal(beanDetalle
				.getCodigoPostalCorresponsal());
		beanModificar.setCorreoAlternoContacto(beanDetalle
				.getCorreoAlternoContacto());
		beanModificar.setCorreoContacto(beanDetalle.getCorreoContacto());
		beanModificar.setCuentaChequesCorresponsal(beanDetalle
				.getCuentaChequesCorresponsal());
		beanModificar.setDelegacionMunicipioCorresponsal(beanDetalle
				.getDelegacionMunicipioCorresponsal());
		beanModificar.setDivisaAsocLineaCredito(beanDetalle
				.getDivisaAsocLineaCredito());
		beanModificar.setDivisaCuentaCheques(beanDetalle
				.getDivisaAsocCuentaCheques());
		beanModificar
				.setEstadoCorresponsal(beanDetalle.getEstadoCorresponsal());
		beanModificar.setEstatusCorresponsal(beanDetalle
				.getEstatusCorresponsal());
		beanModificar.setGiroEmpresa(beanDetalle.getGiroEmpresa());
		beanModificar.setIndicadorConciliacion(beanDetalle
				.getIndicadorConciliacion());
		beanModificar.setIndicadorValidaComision(beanDetalle
				.getIndicadorValidaComision());
		beanModificar.setLimiteImporteCorresponsal(beanDetalle
				.getLimiteImporteCorresponsal());
		beanModificar.setLineaCreditoCorresponsal(beanDetalle
				.getLineaCreditoCorresponsal());
		beanModificar.setNombreCorresponsal(beanCorresponsal
				.getNombreCorresponsal());
		beanModificar.setNumeroExteriorCorresponsal(beanDetalle
				.getNumeroExteriorCorresponsal());
		beanModificar.setNumeroInteriorCorresponsal(beanDetalle
				.getNumeroInteriorCorresponsal());
		beanModificar.setPaisProcedenciaCorresponsal(beanDetalle
				.getPaisProcedenciaCorresponsal());
		beanModificar.setParamDiasPendientesCompensar(beanDetalle
				.getParamDiasPendientesCompensar());
		beanModificar.setParamDiasPendientesConciliar(beanDetalle
				.getParamDiasPendientesConciliar());
		beanModificar
				.setSecuenciaDomicilio(beanDetalle.getSecuenciaDomicilio());
		beanModificar.setTelefonoAclaracion(beanDetalle
				.getTelefonoAclaracionCorresponsal());
		beanModificar.setTelefonoCorresponsal(beanDetalle
				.getTelefonoCorresponsal());
		// Esta condicion se emplea para la interacción Activar/Desactivar
		// Corresponsal procedente del JSP
		if (!beanCorresponsal.getEstatusCorresponsal().equals("CS2")){
			beanModificar.setEstatusCorresponsal("CS2");
		}
		else{
			beanModificar.setEstatusCorresponsal("CS3");
		}
		beanModificar.setTipoOperacion("M");

		final Object objetoRegreso = daoABMCorresponsalia.aBMCorresponsalias(
				beanModificar, psession);

		if (objetoRegreso instanceof BeanError) {
			final String lstrCodError = ((BeanError) objetoRegreso).getCodigoError();
			final String lstrMsgError = ((BeanError) objetoRegreso).getMsgError();
			if (lstrCodError.indexOf("ER") > -1){
				throw new BusinessException(this.getClass().getName(),
						lstrCodError, lstrMsgError);
			}
			beanRespuesta.setCodError(lstrCodError);
			beanRespuesta.setMsgError(lstrMsgError);
		} else{
			throw new BusinessException(this.getClass().getName(), ERDES,
					ERROR_DESCONOCIDO);
		}

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
		if (!"".equalsIgnoreCase(importe)) {
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

		String importeToFront = "0.00";
		if (!"".equalsIgnoreCase(importe)) {
			try {
				importeToFront = importe.substring(0, importe.length() - 2)
						.concat(".").concat(
								importe.substring(importe.length() - 2));

				for (int aux = 0; aux < importe.length() - 3; aux++) {
					if (importeToFront.indexOf('0') == 0) {
						importeToFront = importeToFront.substring(1);
					}
				}

			} catch (StringIndexOutOfBoundsException e) {
				error("Dato incorrecto en importe");
			}
		}
		return importeToFront;
	}

}
