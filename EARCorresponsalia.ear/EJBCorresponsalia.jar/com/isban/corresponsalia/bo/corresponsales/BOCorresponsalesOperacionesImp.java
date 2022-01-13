package com.isban.corresponsalia.bo.corresponsales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanABMMantenimientoOperCorresponsal;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCorresponsal;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanResultadoOperacionesCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.comunes.BeanOperacion;
import com.isban.corresponsalia.beans.comunes.BeanSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaSucursal;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoSucursal;
import com.isban.corresponsalia.dao.auditoria.DAOAuditor;
import com.isban.corresponsalia.dao.canalcorresponsalia.DAOABMCorresponsaliaDLA9;
import com.isban.corresponsalia.dao.canalcorresponsalia.DAOConsultaOperacionesCorresponsalDLA0;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaCorresponsalesDLA2;
import com.isban.corresponsalia.dao.corresponsales.DAOConsultaSucursalDLA6Fix;
import com.isban.corresponsalia.dao.sesiones.DAOSesiones;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;


/**
 * Session Bean implementation class BOCorresponsalesOperacionesImp.
 */
@Stateless
public class BOCorresponsalesOperacionesImp extends ArchitechEBE implements BOCorresponsalesOperaciones {

	/** The dao consulta. */
	
	@EJB 
	transient private DAOConsultaOperacionesCorresponsalDLA0 daoConsulta;
	
	/** The dao corresp. */
	
	@EJB 
	transient private DAOConsultaCorresponsalesDLA2 daoCorresp;
	
	/** The dao abc oper. */
	
	@EJB 
	transient private DAOABMCorresponsaliaDLA9 daoABCOper;
	
	/** The sesiones. */	
	@EJB 
	transient private DAOSesiones                            sesiones;
	
	/** The auditor. */
	
	@EJB 
	transient private DAOAuditor                             auditor;
	
	/** The dao consulta sucursal. */
	
	@EJB 
	transient private DAOConsultaSucursalDLA6Fix daoConsultaSucursal;

	
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
			BeanMttoConsultaCorresponsal beanConsulta, ArchitechSessionBean psession) {
		
		sesiones.actualizaSession(psession);
		auditor.auditaoperacion("Operaciones::ConsultaCorresponsales", "CORBAN", "Consulta Corresponsales", psession);

		final BeanResultadoCorresponsalia beanRespuesta= new BeanResultadoCorresponsalia();
		//String respuestaBo;
		info("Inicio Alta Corresponsalia BO ");
		
		final Object regresoConsulta = daoCorresp.consultaCorresponsalias(beanConsulta, psession);
		
		if(regresoConsulta instanceof BeanError){
			info("BO Error");
			final BeanError beanError = (BeanError) regresoConsulta;
			beanRespuesta.setCodError(beanError.getCodigoError());
			beanRespuesta.setMsgError(beanError.getMsgError());
		} else {
			final ArrayList<BeanCorresponsal> argResultado = (ArrayList<BeanCorresponsal>) regresoConsulta;
			info("BO datos:" + argResultado.size());
			beanRespuesta.setRegistros(argResultado);
		}
			
		info("Fin Alta Corresponsalia BO " );
		return beanRespuesta;
	}

	/**
	 * Consulta operaciones corresponsal.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado operaciones corresponsal
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoOperacionesCorresponsal consultaOperacionesCorresponsal(
			BeanConsultaOperacionesCorresponsal beanConsulta, ArchitechSessionBean psession) {
		
		sesiones.actualizaSession(psession);
		auditor.auditaoperacion("Operaciones::ConsultaOperacionesCorresponsal", "CORBAN", "Consulta Operaciones", psession);
		
		final BeanResultadoOperacionesCorresponsal beanRespuesta= new BeanResultadoOperacionesCorresponsal();
		//String respuestaBo;
		info("Inicio Consulta Operaciones");
		
		ArrayList<BeanOperacion> respuestaBo = null; 
		
		final Map<String, Object> resultado = daoConsulta.consultaOperaciones(beanConsulta, psession);
		final BeanError error = (BeanError) resultado.get("error");

		
		
		
		if(error.getCodigoError().indexOf("DLE") > -1) {
			beanRespuesta.setCodError(error.getCodigoError());
			beanRespuesta.setMsgError(error.getMsgError());
			return beanRespuesta;
		}
		else {
			respuestaBo = (ArrayList<BeanOperacion>) resultado.get("registros");			
			beanRespuesta.setReferenciaAvanzar((String) resultado.get("referenciaAvanzar"));
			beanRespuesta.setReferenciaRetroceder((String) resultado.get("referenciaRetroceder"));
			beanRespuesta.setMasAdelante((Boolean) resultado.get("masAdelante"));
			beanRespuesta.setMasAtras((Boolean) resultado.get("masAtras"));
			
			for (BeanOperacion beanAux : respuestaBo) {
				beanAux.setImporteMaximoOperacionFront(formatoImportesToFront(beanAux.getImporteMaximoOperacion()));
				beanAux.setImporteMinimoOperacionFront(formatoImportesToFront(beanAux.getImporteMinimoOperacion()));
				beanAux.setAcumuladoDiarioFront(formatoImportesToFront(beanAux.getLimiteImporteMaximoDiarioAcum()));
				beanAux.setAcumuladoMensualFront(formatoImportesToFront(beanAux.getLimiteImporteMaximoMensualAcum()));
				beanAux.setHoraInicioFront(formatoHoraToFront(beanAux.getHoraInicio()));
				beanAux.setHoraFinalFront(formatoHoraToFront(beanAux.getHoraFinal()));
			}
		}
		
		if(respuestaBo != null){
			beanRespuesta.setRegistros(respuestaBo);
			beanRespuesta.setCodError(error.getCodigoError());
			beanRespuesta.setMsgError(error.getMsgError());
		}else{
			beanRespuesta.setCodError("ERROR");
			beanRespuesta.setMsgError("Error al consultar Corresponsalias");
		}
			
		info("Fin Consulta Operaciones BO ");
		return beanRespuesta;
	}
	
	/**
	 * Actualiza operacion.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean error
	 */
	@Override
	public BeanError actualizaOperacion(
			BeanABMMantenimientoOperCorresponsal beanConsulta, ArchitechSessionBean psession) {
		
		sesiones.actualizaSession(psession);
		auditor.auditaoperacion("Operaciones::ABCOperacionesCorresponsal", "CORBAN", "ABC Operaciones Corresponsal", psession);
		
		info("Inicio ABM Operaciones");
		
		beanConsulta.setImporteMaximoOperacion(formatoImportesTo390(beanConsulta.getImporteMaximoOperacionFront()));
		beanConsulta.setImporteMinimoOperacion(formatoImportesTo390(beanConsulta.getImporteMinimoOperacionFront()));
		beanConsulta.setAcumuladoDiario(formatoImportesTo390(beanConsulta.getAcumuladoDiarioFront()));
		beanConsulta.setAcumuladoMensual(formatoImportesTo390(beanConsulta.getAcumuladoMensualFront()));
		beanConsulta.setHoraInicio(formatoHoraTo390(beanConsulta.getHoraInicioFront()));
		beanConsulta.setHoraFinal(formatoHoraTo390(beanConsulta.getHoraFinalFront()));
		
		final BeanError respuesta = (BeanError) daoABCOper.aBMCorresponsalias(beanConsulta, psession);
		
		return respuesta;
	}
	
	/**
	 * Consulta sucursal corresponsalia.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado sucursal
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoSucursal consultaSucursalCorresponsalia(BeanConsultaSucursal beanConsulta,
			ArchitechSessionBean psession) {
		final BeanResultadoSucursal beanRespuesta= new BeanResultadoSucursal();
		
		info("Inicio Consulta Sucursales BO ");
		Object regresoConsulta;
		regresoConsulta = daoConsultaSucursal.consultaSucursales(beanConsulta, psession);
		if(regresoConsulta instanceof BeanError){
			final BeanError beanError = (BeanError) regresoConsulta;
			beanRespuesta.setCodError(beanError.getCodigoError());
			beanRespuesta.setMsgError(beanError.getMsgError());
		} else {
			final HashMap <String, Object> datos = (HashMap <String, Object>) regresoConsulta;
			beanRespuesta.setRegistros((ArrayList<BeanSucursal>) datos.get("registros"));
		}		
		if(!"".equals(beanRespuesta.getCodError())){
			info("Error al salir de BO");
		} else {
			info("Exito al salir de BO");
		}
		
		info("Fin Consulta Sucursales BO ");
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
			if (importe.indexOf('.')>0) {
				importeTo390 = importe.substring(0, importe.length()-3).concat
											(importe.substring(importe.length()-2));
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
	 * @param hora the hora
	 * @return the string
	 */
	private String formatoHoraTo390(String hora) {
		String horaTo390 = "";
		
		try {
			horaTo390 = hora.substring(0, hora.length()-3).concat
											(hora.substring(hora.length()-2)).concat("00");
		} catch (StringIndexOutOfBoundsException e) {
			error("Dato incorrecto en hora");
		}
		
		return horaTo390;
	}

	/**
	 * Formato importes to front.
	 *
	 * @param importe the importe
	 * @return the string
	 */
	private String formatoImportesToFront(String importe) {
		
		String importeToFront = "";
		
		try {
			importeToFront = importe.substring(0, importe.length()-2).concat(".").concat
											(importe.substring(importe.length()-2));
			
			for (int aux=0; aux<importe.length()-3; aux++) {
				if (importeToFront.indexOf('0')==0) {
					importeToFront = importeToFront.substring(1);
				}
			}
			
		} catch (StringIndexOutOfBoundsException e) {
			error("Dato incorrecto en importe");
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
		
		try {
			horaToFront = hora.substring(0, hora.length()-4).concat(":").concat
											(hora.substring(hora.length()-4, hora.length()-2));
		} catch (StringIndexOutOfBoundsException e) {
			error("Dato incorrecto en hora");
		}
		
		return horaToFront;
	}
	
}
