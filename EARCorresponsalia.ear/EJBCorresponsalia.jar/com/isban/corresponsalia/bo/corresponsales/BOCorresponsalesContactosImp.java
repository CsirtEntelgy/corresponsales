package com.isban.corresponsalia.bo.corresponsales;

import java.util.ArrayList;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.isban.corresponsalia.beans.comunes.BeanContacto;
import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;

import com.isban.corresponsalia.beans.corresponsales.BeanAltaContactos;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaContactos;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoAltaContacto;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoContactos;

import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.dao.auditoria.DAOAuditor;
import com.isban.corresponsalia.dao.corresponsales.DAOABCContactosDLA3;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaContactosDLA4;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaCorresponsalesDLA2;
import com.isban.corresponsalia.dao.sesiones.DAOSesiones;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;


/**
 * Session Bean implementation class BOCorresponsalesContactosImp.
 */
@Stateless
public class BOCorresponsalesContactosImp extends ArchitechEBE implements
		BOCorresponsalesContactos {

	/** The dao corresp. */
	@EJB
	transient private DAOConsultaCorresponsalesDLA2 daoCorresp;

	/** The dao contactos. */
	@EJB
	transient private DAOConsultaContactosDLA4 daoContactos;

	/** The dao abc contactos. */
	@EJB
	transient private DAOABCContactosDLA3 daoABCContactos;

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
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoCorresponsalia consultaCorresponsales(
			BeanMttoConsultaCorresponsal beanConsulta,
			ArchitechSessionBean psession) {

		sesiones.actualizaSession(psession);
		auditor.auditaoperacion("Contactos::ConsultaCorresponsales", "CORBAN",
				"Consulta Corresponsales", psession);

		final BeanResultadoCorresponsalia beanRespuesta = new BeanResultadoCorresponsalia();
		// String respuestaBo;
		info("Inicio Alta Corresponsalia BO ");

		Object regresoConsulta = daoCorresp.consultaCorresponsalias(
				beanConsulta, psession);

		if (regresoConsulta instanceof BeanError) {
			final BeanError beanError = (BeanError) regresoConsulta;
			beanRespuesta.setCodError(beanError.getCodigoError());
			beanRespuesta.setMsgError(beanError.getMsgError());
		} else {
			final ArrayList<BeanCorresponsal> argResultado = (ArrayList<BeanCorresponsal>) regresoConsulta;
			info("BO datos:" + argResultado.size());
			beanRespuesta.setCodError("0");
			beanRespuesta.setRegistros(argResultado);
		}

		info("Fin Alta Corresponsalia BO ");
		return beanRespuesta;
	}

	/**
	 * Consulta contactos.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado contactos
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoContactos consultaContactos(
			BeanConsultaContactos beanConsulta, ArchitechSessionBean psession) {

		sesiones.actualizaSession(psession);
		auditor.auditaoperacion("Contactos::ConsultaContactos", "CORBAN",
				"Consulta Contactos", psession);

		final BeanResultadoContactos beanRespuesta = new BeanResultadoContactos();
		// String respuestaBo;
		info("Inicio Alta Corresponsalia BO ");

		Map<String, Object> resultado = daoContactos.consultaContactos(
				beanConsulta, psession);
		final BeanError error = (BeanError) resultado.get("error");

		debug("Error: " + error.getMsgError());
		beanRespuesta.setCodError(error.getCodigoError());
		beanRespuesta.setMsgError(error.getMsgError());

		final ArrayList<BeanContacto> respuestaBo = (ArrayList<BeanContacto>) resultado
				.get("registros");

		if (respuestaBo != null) {
			beanRespuesta.setRegistros(respuestaBo);
			beanRespuesta.setReferenciaAvanzar((String) resultado
					.get("referenciaAvanzar"));
			beanRespuesta.setReferenciaRetroceder((String) resultado
					.get("referenciaRetroceder"));
			beanRespuesta
					.setMasAdelante((Boolean) resultado.get("masAdelante"));
			beanRespuesta.setMasAtras((Boolean) resultado.get("masAtras"));
		}

		beanRespuesta.setReferenciaAvanzar((String) resultado
				.get("referenciaAvanzar"));
		beanRespuesta.setReferenciaRetroceder((String) resultado
				.get("referenciaRetroceder"));

		info("Fin Alta Corresponsalia BO ");
		return beanRespuesta;
	}

	/**
	 * Alta contacto.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado alta contacto
	 */
	public BeanResultadoAltaContacto altaContacto(
			BeanAltaContactos beanConsulta, ArchitechSessionBean psession) {

		sesiones.actualizaSession(psession);
		auditor.auditaoperacion("Contactos::ABCContactos", "CORBAN",
				"ABCContactos", psession);

		final BeanResultadoAltaContacto resultado = new BeanResultadoAltaContacto();
		final BeanError respuesta = daoABCContactos.altaContacto(beanConsulta,
				psession);
		resultado.setCodError(respuesta.getCodigoError());
		resultado.setMsgError(respuesta.getMsgError());
		return resultado;
	}

}