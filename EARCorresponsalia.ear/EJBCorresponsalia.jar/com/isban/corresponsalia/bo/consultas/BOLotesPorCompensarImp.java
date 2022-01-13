package com.isban.corresponsalia.bo.consultas;

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
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;

/**
 * Session Bean implementation class BOLotesPorCompensarImp.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOLotesPorCompensarImp extends ArchitechEBE implements
		BOLotesPorCompensar {

	/** The dao consulta corresponsalia. */

	@EJB
	private transient DAOConsultaCorresponsalesDLA2 daoConsultaCorresponsalia;

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
			BeanMttoConsultaCorresponsal beanConsulta,
			ArchitechSessionBean psession) throws BusinessException {

		info("BOLotesPorCompensarImp->ConsultaCorresponsales");
		final BeanResultadoCorresponsalia beanResultadoLotes = new BeanResultadoCorresponsalia();
		
		// Se realiza consulta a DAO Consulta Corresponsalia
		final Object regresoConsultaLotes = daoConsultaCorresponsalia
				.consultaCorresponsalias(beanConsulta, psession); // Parametro 'beanConsulta' y
																  // Parametro 'psession'
		// Validacion de consulta
		if (regresoConsultaLotes instanceof BeanError) {
			final BeanError beanError = (BeanError) regresoConsultaLotes;
			beanResultadoLotes.setCodError(beanError.getCodigoError());
			beanResultadoLotes.setMsgError(beanError.getMsgError());
		} else {
			// En caso de consulta exitosa se ingresa el registro
			beanResultadoLotes
					.setRegistros((ArrayList<BeanCorresponsal>) regresoConsultaLotes);
		}
		
		return beanResultadoLotes;
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
			BeanConsultaOperacionesCatalogo beanConsultaLotes,
			ArchitechSessionBean psession) throws BusinessException {
		
		// Inicio de Bean Resultado Operaciones Catalogo
		BeanResultadoOperacionesCatalogo beanRegresoLotes = new BeanResultadoOperacionesCatalogo();
		info("Inicio Consulta Corresponsalia BO Lotes por Compensar");
		
		// Consulta a DAO Consulta Operaciones Catalogo
		Object objetoRegreso;
		objetoRegreso = daoConsultaOperacionesCatalogo
				.consultaOperacionesCatalogo(beanConsultaLotes, getArchitechBean());

		// Validacion de consulta
		if (objetoRegreso instanceof BeanError) {
			((BeanError) objetoRegreso).getCodigoError();
			beanRegresoLotes.setCodError(((BeanError) objetoRegreso)
					.getCodigoError());
			beanRegresoLotes.setMsgError(((BeanError) objetoRegreso).getMsgError());
			return beanRegresoLotes;
		} else {
			beanRegresoLotes
					.setRegistros((ArrayList<BeanOperacionCatalogo>) objetoRegreso);
		}

		info("Fin Consulta Corresponsalia BO Catalogo");
		return beanRegresoLotes;
	}

	

}
