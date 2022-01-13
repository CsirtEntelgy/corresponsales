package com.isban.corresponsalia.bo.monitoreo;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCatalogo;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanResultadoOperacionesCatalogo;
import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.comunes.BeanOperacionCatalogo;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.dao.canalcorresponsalia.DAOConsultaOperacionesCatalogoDLC6;
import com.isban.corresponsalia.dao.canalcorresponsalia.DAOConsultaOperacionesCorresponsalDLA0;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaCorresponsalesDLA2;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaSucursalDLA6Fix;
import com.isban.corresponsalia.dao.monitoreo.DAOMonitoreoOperacionesDLB3;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;

/**
 * Session Bean implementation class BOSucursalesCorresponsalImp.
 */

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOSucursalesCorresponsalImp extends ArchitechEBE implements
		BOSucursalesCorresponsal {

	/** The dao consulta corresponsalia. */

	@EJB
	private transient DAOConsultaCorresponsalesDLA2 daoConsultaCorresponsalia;

	/** The dao consulta monitoreo operaciones. */

	@EJB
	private transient DAOMonitoreoOperacionesDLB3 daoConsultaMonitoreoOperaciones;

	/** The dao consulta sucursal. */

	@EJB
	private transient DAOConsultaSucursalDLA6Fix daoConsultaSucursal;

	/** The dao consulta operaciones catalogo. */

	@EJB
	private transient DAOConsultaOperacionesCatalogoDLC6 daoConsultaOperacionesCatalogo;

	/** The dao consulta operaciones corr. */
	@EJB
	private transient DAOConsultaOperacionesCorresponsalDLA0 daoConsultaOperacionesCorr;
	
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
			BeanMttoConsultaCorresponsal beanConsultaSucursal,
			ArchitechSessionBean psession) throws BusinessException {

		// Inicio de Bean Resultado Corresponsalia
		info("BOSucursalesCorresponsalImp->ConsultaCorresponsales");
		final BeanResultadoCorresponsalia beanResultadoSucursal = new BeanResultadoCorresponsalia();

		// Consulta a DAO Consulta Corresponsalia 
		final Object regresoConsultaSucursal = daoConsultaCorresponsalia
				.consultaCorresponsalias(beanConsultaSucursal, psession);

		// Validacion de consulta correcta
		if (regresoConsultaSucursal instanceof BeanError) {
			final BeanError beanError = (BeanError) regresoConsultaSucursal;
			beanResultadoSucursal.setCodError(beanError.getCodigoError());
			beanResultadoSucursal.setMsgError(beanError.getMsgError());
		} else {
			beanResultadoSucursal
					.setRegistros((ArrayList<BeanCorresponsal>) regresoConsultaSucursal);
		}

		return beanResultadoSucursal;
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
			BeanConsultaOperacionesCatalogo beanConsultaSucursal,
			ArchitechSessionBean psession) throws BusinessException {
		
		// Inicio de Bean Resultado Operaciones Catalogo
		BeanResultadoOperacionesCatalogo beanRegresoSucursal = new BeanResultadoOperacionesCatalogo();
		info("Inicio Consulta Corresponsalia BO Catalogo");
		
		// Consulta a DAO Consulta Operaciones Catalogo
		Object objetoRegresoSucursal;

		objetoRegresoSucursal = daoConsultaOperacionesCatalogo
				.consultaOperacionesCatalogo(beanConsultaSucursal, getArchitechBean());

		// Validacion de consulta
		if (objetoRegresoSucursal instanceof BeanError) {
			((BeanError) objetoRegresoSucursal).getCodigoError();
			beanRegresoSucursal.setCodError(((BeanError) objetoRegresoSucursal)
					.getCodigoError());
			beanRegresoSucursal.setMsgError(((BeanError) objetoRegresoSucursal).getMsgError());
			return beanRegresoSucursal;
		} else {
			beanRegresoSucursal
					.setRegistros((ArrayList<BeanOperacionCatalogo>) objetoRegresoSucursal);
		}

		info("Fin Consulta Corresponsalia BO Catalogo");
		return beanRegresoSucursal;
	}

}
