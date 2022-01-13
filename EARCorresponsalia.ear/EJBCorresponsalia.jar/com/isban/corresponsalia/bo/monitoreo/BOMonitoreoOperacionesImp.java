package com.isban.corresponsalia.bo.monitoreo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCatalogo;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCorresponsal;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanResultadoOperacionesCatalogo;
import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.comunes.BeanOperacion;
import com.isban.corresponsalia.beans.comunes.BeanOperacionCatalogo;
import com.isban.corresponsalia.beans.comunes.BeanSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoSucursal;
import com.isban.corresponsalia.beans.monitoreo.BeanConsultaMonitoreoOperaciones;
import com.isban.corresponsalia.beans.monitoreo.BeanMonitoreoOperaciones;
import com.isban.corresponsalia.beans.monitoreo.BeanResultadoMonitoreoOperaciones;
import com.isban.corresponsalia.beans.monitoreo.RBeanOperacionesSucursalesMonitoreoOperaciones;
import com.isban.corresponsalia.dao.canalcorresponsalia.DAOConsultaOperacionesCatalogoDLC6;
import com.isban.corresponsalia.dao.canalcorresponsalia.DAOConsultaOperacionesCorresponsalDLA0;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaCorresponsalesDLA2;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaSucursalDLA6Fix;
import com.isban.corresponsalia.dao.monitoreo.DAOMonitoreoOperacionesDLB3;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;

/**
 * Session Bean implementation class BOMonitoreoOperacionesImp.
 */
@Stateless
public class BOMonitoreoOperacionesImp extends ArchitechEBE implements
		BOMonitoreoOperaciones {

	/** The dao consulta corresponsalia. */

	@EJB
	transient private DAOConsultaCorresponsalesDLA2 daoConsultaCorresponsalia;

	/** The dao consulta monitoreo operaciones. */

	@EJB
	transient private DAOMonitoreoOperacionesDLB3 daoConsultaMonitoreoOperaciones;

	/** The dao consulta sucursal. */

	@EJB
	transient private DAOConsultaSucursalDLA6Fix daoConsultaSucursal;

	/** The dao consulta operaciones catalogo. */

	@EJB
	transient private DAOConsultaOperacionesCatalogoDLC6 daoConsultaOperacionesCatalogo;

	/** The dao consulta operaciones corr. */
	@EJB
	transient private DAOConsultaOperacionesCorresponsalDLA0 daoConsultaOperacionesCorr;

	/**
	 * Consulta corresponsales.
	 * 
	 * @param beanConsulta
	 *            the bean consulta
	 * @param psession
	 *            the psession
	 * @return the bean resultado corresponsalia
	 * @throws BusinessException
	 *             the business exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoCorresponsalia consultaCorresponsales(
			BeanMttoConsultaCorresponsal beanConsulta,
			ArchitechSessionBean psession) throws BusinessException {

		info("BOMonitoreoOperaciones->ConsultaCorresponsales");
		final BeanResultadoCorresponsalia beanResultado = new BeanResultadoCorresponsalia();

		final Object regresoConsulta = daoConsultaCorresponsalia
				.consultaCorresponsalias(beanConsulta, psession);

		if (regresoConsulta instanceof BeanError) {
			final BeanError beanError = (BeanError) regresoConsulta;
			beanResultado.setCodError(beanError.getCodigoError());
			beanResultado.setMsgError(beanError.getMsgError());
		} else {
			beanResultado
					.setRegistros((ArrayList<BeanCorresponsal>) regresoConsulta);
		}

		return beanResultado;
	}

	/**
	 * Consulta sucursales.
	 * 
	 * @param beanConsulta
	 *            the bean consulta
	 * @param psession
	 *            the psession
	 * @return the bean resultado sucursal
	 * @throws BusinessException
	 *             the business exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoSucursal consultaSucursales(
			BeanConsultaSucursal beanConsulta, ArchitechSessionBean psession)
			throws BusinessException {
		final BeanResultadoSucursal beanRespuesta = new BeanResultadoSucursal();

		info("BOMonitoreoOperaciones->ConsultaSucursales");
		Object regresoConsulta;
		regresoConsulta = daoConsultaSucursal.consultaSucursales(beanConsulta,
				psession);
		if (regresoConsulta instanceof BeanError) {
			final BeanError beanError = (BeanError) regresoConsulta;
			beanRespuesta.setCodError(beanError.getCodigoError());
			beanRespuesta.setMsgError(beanError.getMsgError());
		} else {
			beanRespuesta
					.setRegistros((ArrayList<BeanSucursal>) regresoConsulta);
		}
		if (!"".equals(beanRespuesta.getCodError())) {
			info("Error al salir de BO");
		} else {
			info("Exito al salir de BO");
		}

		info("Fin Consulta Sucursales BO ");
		return beanRespuesta;
	}

	/**
	 * Consulta operaciones catalogo.
	 * 
	 * @param beanConsulta
	 *            the bean consulta
	 * @param psession
	 *            the psession
	 * @return the bean resultado operaciones catalogo
	 * @throws BusinessException
	 *             the business exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoOperacionesCatalogo consultaOperacionesCatalogo(
			BeanConsultaOperacionesCatalogo beanConsulta,
			ArchitechSessionBean psession) throws BusinessException {
		BeanResultadoOperacionesCatalogo beanRegreso = new BeanResultadoOperacionesCatalogo();
		info("Inicio Consulta Corresponsalia BO Catalogo");
		Object objetoRegreso;

		objetoRegreso = daoConsultaOperacionesCatalogo
				.consultaOperacionesCatalogo(beanConsulta, getArchitechBean());

		if (objetoRegreso instanceof BeanError) {
			((BeanError) objetoRegreso).getCodigoError();
			beanRegreso.setCodError(((BeanError) objetoRegreso)
					.getCodigoError());
			beanRegreso.setMsgError(((BeanError) objetoRegreso).getMsgError());
			return beanRegreso;
		} else {
			beanRegreso
					.setRegistros((ArrayList<BeanOperacionCatalogo>) objetoRegreso);
		}

		info("Fin Consulta Corresponsalia BO Catalogo");
		return beanRegreso;
	}

	/**
	 * Consulta operaciones sucursales.
	 * 
	 * @param beanConsulta
	 *            the bean consulta
	 * @param psession
	 *            the psession
	 * @return the r bean operaciones sucursales monitoreo operaciones
	 * @throws BusinessException
	 *             the business exception
	 */
	@SuppressWarnings("unchecked")
	public RBeanOperacionesSucursalesMonitoreoOperaciones consultaOperacionesSucursales(
			BeanConsultaMonitoreoOperaciones beanConsulta,
			ArchitechSessionBean psession) throws BusinessException {
		final RBeanOperacionesSucursalesMonitoreoOperaciones rBeanOperacionesSucursalesMonitoreoOperaciones = new RBeanOperacionesSucursalesMonitoreoOperaciones();

		final BeanConsultaSucursal beanConsultaSucursales = new BeanConsultaSucursal();
		final BeanConsultaOperacionesCorresponsal beanConsultaOperCorr = new BeanConsultaOperacionesCorresponsal();
		Map<String, Object> objetoRegresoCatalogoOper = null;
		Object objetoRegresoSucursales = null;

		beanConsultaOperCorr.setEntidad("0014");
		beanConsultaOperCorr.setCanal("14");
		beanConsultaOperCorr.setIdCorresponsal(beanConsulta
				.getIdentificacionCorresponsal());
		beanConsultaOperCorr.setNivelParametria("02");
		beanConsultaOperCorr.setTipoConsulta("L");

		objetoRegresoCatalogoOper = daoConsultaOperacionesCorr
				.consultaOperaciones(beanConsultaOperCorr, getArchitechBean());

		BeanError beanError = new BeanError();
		beanError = (BeanError) objetoRegresoCatalogoOper.get("error");

		if (beanError.getCodigoError().indexOf("DLE") <= -1) {
			rBeanOperacionesSucursalesMonitoreoOperaciones
					.setListaOperacionesCorres((ArrayList<BeanOperacion>) objetoRegresoCatalogoOper
							.get("registros"));
		}

		beanConsultaSucursales.setCodigoCorresponsalia(beanConsulta
				.getIdentificacionCorresponsal());
		beanConsultaSucursales.setIndicadorFuncional("L");
		beanConsultaSucursales.setIndicadorPaginacion("N");
		beanConsultaSucursales.setPaginada(false);
		objetoRegresoSucursales = daoConsultaSucursal.consultaSucursales(
				beanConsultaSucursales, getArchitechBean());
		if (!(objetoRegresoSucursales instanceof BeanError)) {
			final HashMap<String, Object> result = (HashMap<String, Object>) objetoRegresoSucursales;
			ArrayList<BeanSucursal> registros = (ArrayList<BeanSucursal>) result
					.get("registros");
			rBeanOperacionesSucursalesMonitoreoOperaciones
					.setListaSucursales(registros);

		}

		return rBeanOperacionesSucursalesMonitoreoOperaciones;
	}

	/**
	 * Consulta monitoreo opearciones.
	 * 
	 * @param beanConsulta
	 *            the bean consulta
	 * @param psession
	 *            the psession
	 * @return the bean resultado monitoreo operaciones
	 * @throws BusinessException
	 *             the business exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoMonitoreoOperaciones consultaMonitoreoOpearciones(
			BeanConsultaMonitoreoOperaciones beanConsulta,
			ArchitechSessionBean psession) throws BusinessException {

		info("BOMonitoreoOperaciones->ConsultaMonitoreoOpearciones");
		final BeanResultadoMonitoreoOperaciones beanResultado = new BeanResultadoMonitoreoOperaciones();

		if (!beanConsulta.getIndicadorPaginacionAvanzaRetrocede().equals("")) {
			beanConsulta.setIndicadorPaginacion("P");
		} else {
			beanConsulta.setIndicadorPaginacion("N");
		}

		beanConsulta.setHoraInicio(formatoHoraTo390(beanConsulta
				.getHoraInicioFront()));
		beanConsulta
				.setHoraFin(formatoHoraTo390(beanConsulta.getHoraFinFront()));
		beanConsulta.setImporteInicial(formatoImportesTo390(beanConsulta
				.getImporteInicialFront()));
		beanConsulta.setImporteFinal(formatoImportesTo390(beanConsulta
				.getImporteFinalFront()));

		final Object regresoConsulta = daoConsultaMonitoreoOperaciones
				.monitoreoOperaciones(beanConsulta, psession);

		if (regresoConsulta instanceof BeanError) {
			final BeanError beanError = (BeanError) regresoConsulta;
			beanResultado.setCodError(beanError.getCodigoError());
			beanResultado.setMsgError(beanError.getMsgError());
		} else {
			final BeanError beanError = (BeanError) ((HashMap<String, Object>) regresoConsulta)
					.get("error");
			beanResultado.setCodError(beanError.getCodigoError());
			beanResultado.setMsgError(beanError.getMsgError());
			final ArrayList<BeanMonitoreoOperaciones> listRegistros = (ArrayList<BeanMonitoreoOperaciones>) ((HashMap<String, Object>) regresoConsulta)
					.get("registros");

			for (BeanMonitoreoOperaciones beanAux : listRegistros) {
				if (beanAux.getImporteTotalOperaciones().indexOf('.') < 0) {
					beanAux
							.setImporteTotalOperacionesFront(formatoImportesToFront(beanAux
									.getImporteTotalOperaciones()));
				} else {
					beanAux.setImporteTotalOperacionesFront(beanAux
							.getImporteTotalOperaciones());
				}
			}

			beanResultado.setRegistros(listRegistros);
			beanResultado
					.setReferenciaAvanzar((String) ((HashMap<String, Object>) regresoConsulta)
							.get("referenciaAvanzar"));
			beanResultado
					.setReferenciaRetroceder((String) ((HashMap<String, Object>) regresoConsulta)
							.get("referenciaRetroceder"));
			beanResultado
					.setHoraAvanzar((String) ((HashMap<String, Object>) regresoConsulta)
							.get("horaAvanzar"));
			beanResultado
					.setOperAvanzar((String) ((HashMap<String, Object>) regresoConsulta)
							.get("operacionAvanzar"));
			beanResultado
					.setHoraRetroceder((String) ((HashMap<String, Object>) regresoConsulta)
							.get("horaRetroceder"));
			beanResultado
					.setOperRetroceder((String) ((HashMap<String, Object>) regresoConsulta)
							.get("operacionRetroceder"));
		}

		return beanResultado;
	}

	/**
	 * Formato importes to390.
	 * 
	 * @param importe
	 *            the importe
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
	 * Formato hora to390.
	 * 
	 * @param hora
	 *            the hora
	 * @return the string
	 */
	private String formatoHoraTo390(String hora) {
		String horaTo390 = "";

		// try {
		horaTo390 = hora.substring(0, hora.length() - 3).concat(
				hora.substring(hora.length() - 2)).concat("00");
		/*
		 * } catch (Exception e) { error("Dato incorrecto en hora"); }
		 */

		return horaTo390;
	}

	/**
	 * Formato importes to front.
	 * 
	 * @param importe
	 *            the importe
	 * @return the string
	 */
	private String formatoImportesToFront(String importe) {

		String importeToFront = "";

		importeToFront = importe.substring(0, importe.length() - 2).concat(".")
				.concat(importe.substring(importe.length() - 2));

		for (int aux = 0; aux < importe.length() - 3; aux++) {
			if (importeToFront.indexOf('0') == 0) {
				importeToFront = importeToFront.substring(1);
			}
		}
		return importeToFront;
	}

}
