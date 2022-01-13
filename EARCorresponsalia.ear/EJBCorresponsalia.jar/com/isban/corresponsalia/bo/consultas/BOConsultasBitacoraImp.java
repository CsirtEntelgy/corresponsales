package com.isban.corresponsalia.bo.consultas;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCatalogo;
import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.comunes.BeanOperacion;
import com.isban.corresponsalia.beans.comunes.BeanSucursal;
import com.isban.corresponsalia.beans.consultas.BeanConsultaBitacora;
import com.isban.corresponsalia.beans.consultas.BeanRegistroAnular;
import com.isban.corresponsalia.beans.consultas.BeanRegistroConsultaBitacora;
import com.isban.corresponsalia.beans.consultas.RBeanAnulacion;
import com.isban.corresponsalia.beans.consultas.RBeanConsultaBitacora;
import com.isban.corresponsalia.beans.consultas.RBeanCorresponsalesConsultaBitacora;
import com.isban.corresponsalia.beans.consultas.RBeanOperacionesSucursalesConsultaBitacora;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.comunes.Parametros;
import com.isban.corresponsalia.dao.auditoria.DAOAuditor;
import com.isban.corresponsalia.dao.canalcorresponsalia.DAOConsultaOperacionesCatalogoDLC6;
import com.isban.corresponsalia.dao.consultas.DAOAnulacionDLC4;
import com.isban.corresponsalia.dao.consultas.DAOConsultaBitacoraConReferenciaDLB6;
import com.isban.corresponsalia.dao.consultas.DAOConsultaBitacoraSinReferenciaDLB5;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaCorresponsalesDLA2;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaSucursalDLA6Fix;
import com.isban.corresponsalia.dao.sesiones.DAOSesiones;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;


/**
 * Session Bean implementation class BOConsultaBitacoraImp.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOConsultasBitacoraImp extends ArchitechEBE implements
		BOConsultasBitacora {

	/** The Constant CORBAN. */
	private static final String CORBAN =  "CORBAN" ;
	/** The consulta consultaDLA2Corresponsales. */
	@EJB
	transient private DAOConsultaCorresponsalesDLA2 consultaDLA2Corresponsales;
	
	/** The consulta operaciones. */
	@EJB
	transient private DAOConsultaOperacionesCatalogoDLC6 consultaOperaciones;
	
	/** The consulta sucursal. */
	@EJB
	transient private DAOConsultaSucursalDLA6Fix consultaSucursal;
	
	/** The consulta bitacora sin referencia. */
	@EJB
	transient private DAOConsultaBitacoraSinReferenciaDLB5 consultaDLB5BitacoraSinReferencia;
	
	/** The consulta bitacora con referencia. */
	@EJB
	transient private DAOConsultaBitacoraConReferenciaDLB6 consultaDLB6BitacoraConReferencia;
	
	/** The anulacion. */
	@EJB
	transient private DAOAnulacionDLC4 anulacionDLC4;
	
	/** The sesiones. */
	@EJB
	transient private DAOSesiones sesiones;
	
	/** The auditor. */
	@EJB
	transient private DAOAuditor auditor;
	
	/**
	 * Consulta corresponsales.
	 *
	 * @param beanArq the bean arq
	 * @return the r bean corresponsales consulta bitacora
	 * @throws BusinessException the business exception
	 */
	@SuppressWarnings("unchecked")
	public RBeanCorresponsalesConsultaBitacora consultaCorresponsales(
			ArchitechSessionBean beanArq) throws BusinessException {
		debug("BOConsultasBitacora->consultaCorresponsales");

		sesiones.actualizaSession(beanArq);
		auditor.auditaoperacion("ConnsultaBitacora::ConsultaCorresponsales",
				CORBAN, "Consulta Corresponsales", beanArq);

		final RBeanCorresponsalesConsultaBitacora rBeanCorresponsalesConsultaBitacora = new RBeanCorresponsalesConsultaBitacora();
		ArrayList<BeanCorresponsal> listaCorresponsales = new ArrayList<BeanCorresponsal>();

		final String lstrCodigoCorresponsalia = Parametros
				.getParametroAplicacion("CORRESPONSALIA");
		debug("Codigo Corresponsalia:" + lstrCodigoCorresponsalia);
		BeanMttoConsultaCorresponsal beanConsulta = new BeanMttoConsultaCorresponsal();

		beanConsulta.setCodigoCorresponsalia(lstrCodigoCorresponsalia);
		beanConsulta.setTipoConsulta("L");
		beanConsulta.setIndicadorPaginacion("N");

		Object objDaoResult = consultaDLA2Corresponsales.consultaCorresponsalias(
				beanConsulta, getArchitechBean());
		if (objDaoResult instanceof BeanError){
			throw new BusinessException(this.getClass().getName(),
					((BeanError) objDaoResult).getCodigoError(),
					((BeanError) objDaoResult).getMsgError());
		}

		listaCorresponsales = (ArrayList<BeanCorresponsal>) objDaoResult;

		if (listaCorresponsales == null){
			throw new BusinessException(this.getClass().getName(), "BITA9999",
					"Error al consultar corresponsales");
		}
		debug("Numero de Corresponsales encontrados:"
				+ listaCorresponsales.size());

		rBeanCorresponsalesConsultaBitacora
				.setListaCorresponsales(listaCorresponsales);
		rBeanCorresponsalesConsultaBitacora.setCodError("BITA0000");
		rBeanCorresponsalesConsultaBitacora
				.setMsgError("Consulta de corresponsales regreso resultados");
		return rBeanCorresponsalesConsultaBitacora;
	}

	/**
	 * Consulta operaciones sucursales.
	 *
	 * @param beanArq the bean arq
	 * @param beanCorresponsal the bean corresponsal
	 * @return the r bean operaciones sucursales consulta bitacora
	 * @throws BusinessException the business exception
	 */
	@SuppressWarnings( { "unchecked" })
	public RBeanOperacionesSucursalesConsultaBitacora consultaOperacionesSucursales(
			ArchitechSessionBean beanArq, BeanCorresponsal beanCorresponsal)
			throws BusinessException {

		debug("BOConsultasBitacora->consultaOperacionesSucursales");

		sesiones.actualizaSession(beanArq);
		auditor.auditaoperacion(
				"ConnsultaBitacora::ConsultaOperacionesSucursales", CORBAN,
				"Consulta Operaciones y Sucursales", beanArq);

		final RBeanOperacionesSucursalesConsultaBitacora rBeanOperacionesSucursalesConsultaBitacora = new RBeanOperacionesSucursalesConsultaBitacora();
		ArrayList<BeanOperacion> listaOperaciones = new ArrayList<BeanOperacion>();
		ArrayList<BeanSucursal> listaSucursales = new ArrayList<BeanSucursal>();

		BeanConsultaOperacionesCatalogo beanConsultaOperaciones = new BeanConsultaOperacionesCatalogo();

		beanConsultaOperaciones.setEntidad("0014");
		beanConsultaOperaciones.setIdentificacionCorresponsal(beanCorresponsal
				.getCodigoCorresponsal());
		beanConsultaOperaciones.setTipoOperacion("C");
		beanConsultaOperaciones.setNivelRegistroOperacion("02");
		beanConsultaOperaciones.setIndicadorPaginacion("N");
		beanConsultaOperaciones.setCanalCorresponsal(beanCorresponsal
				.getCodigoCorresponsalia());
		final Object regresoConsultaOper = consultaOperaciones
				.consultaOperacionesCatalogo(beanConsultaOperaciones,
						getArchitechBean());

		if (regresoConsultaOper instanceof BeanError) {
			rBeanOperacionesSucursalesConsultaBitacora
					.setCodError(((BeanError) regresoConsultaOper)
							.getCodigoError());
			rBeanOperacionesSucursalesConsultaBitacora
					.setMsgError(((BeanError) regresoConsultaOper)
							.getMsgError());
			rBeanOperacionesSucursalesConsultaBitacora
					.setListaOperaciones(new ArrayList<BeanOperacion>());

		} else {
			listaOperaciones = (ArrayList<BeanOperacion>) regresoConsultaOper;
		}

		final BeanConsultaSucursal beanConsultaSucursal = new BeanConsultaSucursal();

		beanConsultaSucursal.setCodigoCorresponsalia(beanCorresponsal
				.getCodigoCorresponsal());
		beanConsultaSucursal.setIndicadorFuncional("L");
		beanConsultaSucursal.setIndicadorPaginacion("N");
		beanConsultaSucursal.setPaginada(false);
		Object regresoConsulta = consultaSucursal.consultaSucursales(
				beanConsultaSucursal, getArchitechBean());

		if (regresoConsulta instanceof BeanError) {
			rBeanOperacionesSucursalesConsultaBitacora
					.setCodError(((BeanError) regresoConsulta).getCodigoError());
			rBeanOperacionesSucursalesConsultaBitacora
					.setMsgError(((BeanError) regresoConsulta).getMsgError());
			rBeanOperacionesSucursalesConsultaBitacora
					.setListaSucursales(new ArrayList<BeanSucursal>());
		} else {
			final HashMap<String, Object> result = (HashMap<String, Object>) regresoConsulta;
			listaSucursales = (ArrayList<BeanSucursal>) result.get("registros");
		}

		if ((listaOperaciones == null || listaOperaciones.isEmpty())
				&& (listaSucursales == null || listaSucursales.isEmpty())) {
			rBeanOperacionesSucursalesConsultaBitacora.setCodError("BITA0002");
			rBeanOperacionesSucursalesConsultaBitacora
					.setMsgError("No se encontraron ni operaciones y ni sucursales");
		} else if (listaSucursales == null || listaSucursales.isEmpty()) {
			rBeanOperacionesSucursalesConsultaBitacora.setCodError("BITA0003");
			rBeanOperacionesSucursalesConsultaBitacora
					.setMsgError("No se encontraron sucursales");
		} else if (listaOperaciones == null || listaOperaciones.isEmpty()) {
			rBeanOperacionesSucursalesConsultaBitacora.setCodError("BITA0004");
			rBeanOperacionesSucursalesConsultaBitacora
					.setMsgError("No se encontraron operaciones");
		} else {
			rBeanOperacionesSucursalesConsultaBitacora.setCodError("BITA0000");
			rBeanOperacionesSucursalesConsultaBitacora
					.setMsgError("Ejecucion exitosa de operaciones y sucursales");
		}

		rBeanOperacionesSucursalesConsultaBitacora
				.setListaOperaciones(listaOperaciones);
		rBeanOperacionesSucursalesConsultaBitacora
				.setListaSucursales(listaSucursales);

		return rBeanOperacionesSucursalesConsultaBitacora;
	}

	/**
	 * Consulta bitacora sin referencia.
	 *
	 * @param beanArq the bean arq
	 * @param beanConsulta the bean consulta
	 * @return the r bean consulta bitacora
	 * @throws BusinessException the business exception
	 */
	@SuppressWarnings("unchecked")
	public RBeanConsultaBitacora consultaBitacoraSinReferencia(
			ArchitechSessionBean beanArq, BeanConsultaBitacora beanConsulta)
			throws BusinessException {

		debug("BOConsultasBitacora->consultaBitacoraSinReferencia");

		sesiones.actualizaSession(beanArq);
		auditor.auditaoperacion(
				"ConnsultaBitacora::ConsultaBitacoraSinReferencia", CORBAN,
				"Consulta Bitacora", beanArq);

		final RBeanConsultaBitacora rBeanConsultaBitacora = new RBeanConsultaBitacora();

		final Object objDaoResult = consultaDLB5BitacoraSinReferencia.consultaBitacora(
				getArchitechBean(), beanConsulta);

		if (objDaoResult instanceof BeanError) {
			rBeanConsultaBitacora.setListaRegistrosConsultaBitacora(null);
			rBeanConsultaBitacora.setReferenciaAvanzar("");
			rBeanConsultaBitacora.setReferenciaRetroceder("");
			rBeanConsultaBitacora.setCodError(((BeanError) objDaoResult)
					.getCodigoError());
			rBeanConsultaBitacora.setMsgError(((BeanError) objDaoResult)
					.getMsgError());
		} else {
			final ArrayList<BeanRegistroConsultaBitacora> listaRegistrosConsultaBitacora = (ArrayList<BeanRegistroConsultaBitacora>) ((HashMap<String, Object>) objDaoResult)
					.get("registros");
			final String lstrReferenciaAvanzar = (String) ((HashMap<String, Object>) objDaoResult)
					.get("referenciaAvanzar");
			final String lstrReferenciaRetroceder = (String) ((HashMap<String, Object>) objDaoResult)
					.get("referenciaRetroceder");
			final String codigoAviso = (String) ((HashMap<String, Object>) objDaoResult)
					.get("CodigoAviso");
			final String mensajeAviso = (String) ((HashMap<String, Object>) objDaoResult)
					.get("MensajeAviso");

			/*
			 * if(listaRegistrosConsultaBitacora == null ||
			 * listaRegistrosConsultaBitacora.isEmpty()){ throw new
			 * BusinessException
			 * (this.getClass().getName(),"BITA9999","Error al consultar bitacora"
			 * ); }
			 */

			rBeanConsultaBitacora
					.setListaRegistrosConsultaBitacora(listaRegistrosConsultaBitacora);
			rBeanConsultaBitacora.setReferenciaAvanzar(lstrReferenciaAvanzar);
			rBeanConsultaBitacora
					.setReferenciaRetroceder(lstrReferenciaRetroceder);
			rBeanConsultaBitacora.setCodAviso(codigoAviso);
			rBeanConsultaBitacora.setMsgAviso(mensajeAviso);
		}
		/*
		 * rBeanConsultaBitacora.setCodError("BITA0000");
		 * rBeanConsultaBitacora.setMsgError
		 * ("Ejecucion exitosa de consulta de bitacora");
		 */

		return rBeanConsultaBitacora;
	}

	/**
	 * Consulta bitacora con referencia.
	 *
	 * @param beanArq the bean arq
	 * @param beanConsulta the bean consulta
	 * @return the r bean consulta bitacora
	 * @throws BusinessException the business exception
	 */
	@SuppressWarnings("unchecked")
	public RBeanConsultaBitacora consultaBitacoraConReferencia(
			ArchitechSessionBean beanArq, BeanConsultaBitacora beanConsulta)
			throws BusinessException {

		debug("BOConsultasBitacora->consultaBitacoraConReferencia");

		sesiones.actualizaSession(beanArq);
		auditor.auditaoperacion(
				"ConnsultaBitacora::ConsultaBitacoraConReferencia", CORBAN,
				"Consulta Bitacora", beanArq);

		final RBeanConsultaBitacora rBeanConsultaBitacora = new RBeanConsultaBitacora();

		final Object objDaoResult = consultaDLB6BitacoraConReferencia.consultaBitacora(
				getArchitechBean(), beanConsulta);

		
		 if(objDaoResult instanceof BeanError) {
				rBeanConsultaBitacora.setListaRegistrosConsultaBitacora(null);
				rBeanConsultaBitacora.setReferenciaAvanzar("");
				rBeanConsultaBitacora.setReferenciaRetroceder("");
				rBeanConsultaBitacora.setCodError(((BeanError) objDaoResult)
						.getCodigoError());
				rBeanConsultaBitacora.setMsgError(((BeanError) objDaoResult)
						.getMsgError());

		 }else {
		/*	 throw new
		 * BusinessException(this
		 * .getClass().getName(),((BeanError)objDaoResult).
		 * getCodigoError(),((BeanError)objDaoResult).getMsgError());
		 */

				final ArrayList<BeanRegistroConsultaBitacora> listaRegistrosConsultaBitacora = 
					(ArrayList<BeanRegistroConsultaBitacora>) ((HashMap<String, Object>) objDaoResult)
						.get("registros");
				final String lstrReferenciaAvanzar = (String) ((HashMap<String, Object>) objDaoResult)
						.get("referenciaAvanzar");
				final String lstrReferenciaRetroceder = (String) ((HashMap<String, Object>) objDaoResult)
						.get("referenciaRetroceder");
				final String codigoAviso = (String) ((HashMap<String, Object>) objDaoResult)
						.get("CodigoAviso");
				final String mensajeAviso = (String) ((HashMap<String, Object>) objDaoResult)
						.get("MensajeAviso");

				/*
				 * if(listaRegistrosConsultaBitacora == null ||
				 * listaRegistrosConsultaBitacora.isEmpty()){ throw new
				 * BusinessException
				 * (this.getClass().getName(),"BITA9999","Error al consultar bitacora"
				 * ); }
				 */

				rBeanConsultaBitacora
						.setListaRegistrosConsultaBitacora(listaRegistrosConsultaBitacora);
				rBeanConsultaBitacora.setReferenciaAvanzar(lstrReferenciaAvanzar);
				rBeanConsultaBitacora
						.setReferenciaRetroceder(lstrReferenciaRetroceder);
				rBeanConsultaBitacora.setCodAviso(codigoAviso);
				rBeanConsultaBitacora.setMsgAviso(mensajeAviso);
			}

		return rBeanConsultaBitacora;
	}

	/**
	 * Anulacion.
	 *
	 * @param beanArq the bean arq
	 * @param beanRegistro the bean registro
	 * @return the r bean anulacion
	 * @throws BusinessException the business exception
	 */
	public RBeanAnulacion anulacion(ArchitechSessionBean beanArq,
			BeanRegistroConsultaBitacora beanRegistro) throws BusinessException {

		debug("BOConsultasBitacora->anulacion");
		sesiones.actualizaSession(beanArq);
		auditor.auditaoperacion("ConnsultaBitacora::Anulacion ", CORBAN,
				"Anulacion", beanArq);

		final RBeanAnulacion rBeanAnulacion = new RBeanAnulacion();

		BeanRegistroAnular registroAnular = new BeanRegistroAnular();
		registroAnular.setEntidad(Parametros
				.getParametroAplicacion("ENTIDAD_BANCO"));
		registroAnular.setIdentificadorCorresponsalia(Parametros
				.getParametroAplicacion("CORRESPONSALIA"));
		registroAnular.setIdentificadorCorresponsal(beanRegistro
				.getIdCorresponsal());
		registroAnular.setCodigoIdentificadorSucursal(beanRegistro
				.getIdSucursal());
		registroAnular.setFecha(beanRegistro.getFechaOperacion());
		registroAnular.setFolioOperacion(beanRegistro.getFolioOperacion());
		registroAnular.setImporteOperacion(beanRegistro.getImporteOperacion()
				.trim());
		final BeanError beanError = (BeanError) anulacionDLC4.anular(getArchitechBean(),
				registroAnular);
		rBeanAnulacion.setCodError(beanError.getCodigoError());
		rBeanAnulacion.setMsgError(beanError.getMsgError());

		return rBeanAnulacion;
	}
}