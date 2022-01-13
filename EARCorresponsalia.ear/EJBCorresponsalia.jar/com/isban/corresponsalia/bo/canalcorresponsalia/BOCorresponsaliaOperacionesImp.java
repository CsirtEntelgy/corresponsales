package com.isban.corresponsalia.bo.canalcorresponsalia;

import java.util.ArrayList;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.isban.corresponsalia.beans.canalcorresponsalia.BeanABMMantenimientoOperCorresponsal;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanAltaOperacionesCatalogo;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCatalogo;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanConsultaOperacionesCorresponsal;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanResultadoOperacionesCatalogo;
import com.isban.corresponsalia.beans.canalcorresponsalia.BeanResultadoOperacionesCorresponsal;
import com.isban.corresponsalia.beans.comunes.BeanError;
import com.isban.corresponsalia.beans.comunes.BeanOperacion;
import com.isban.corresponsalia.beans.comunes.BeanOperacionCatalogo;
import com.isban.corresponsalia.dao.canalcorresponsalia.DAOABMCorresponsaliaDLA9;
import com.isban.corresponsalia.dao.canalcorresponsalia.DAOAltaOperacionesCatalogoDLC5;
import com.isban.corresponsalia.dao.canalcorresponsalia.DAOConsultaOperacionesCatalogoDLC6;
import com.isban.corresponsalia.dao.canalcorresponsalia.DAOConsultaOperacionesCorresponsalDLA0;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;


/**
 * Session Bean implementation class BOCorresponsaliaOperacionesImp.
 */
@Stateless
public class BOCorresponsaliaOperacionesImp extends ArchitechEBE implements BOCorresponsaliaOperaciones {

	/** The dao consulta. */
	@EJB 
	transient private DAOConsultaOperacionesCorresponsalDLA0 daoConsulta;
	
	/** The dao abm. */
	@EJB 
	transient private DAOABMCorresponsaliaDLA9 daoABM;
	
	/** The dao alta operaciones catalogo. */
	@EJB 
	transient private DAOAltaOperacionesCatalogoDLC5 daoAltaOperacionesCatalogo;
	
	/** The dao consulta operaciones catalogo. */
	@EJB 
	transient private DAOConsultaOperacionesCatalogoDLC6 daoConsultaOperacionesCatalogo;
	
	
	/**
	 * Consulta operaciones corresponsalia.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado operaciones corresponsal
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoOperacionesCorresponsal consultaOperacionesCorresponsalia(
			BeanConsultaOperacionesCorresponsal beanConsulta, ArchitechSessionBean psession) {
		
		final BeanResultadoOperacionesCorresponsal beanResultado = new BeanResultadoOperacionesCorresponsal();
		info("Llama DAO Operacion Corresponsalia");
		
		final Map<String, Object> resultado = daoConsulta.consultaOperaciones (beanConsulta, psession);
		final BeanError error = (BeanError) resultado.get("error");
		
		if(error.getCodigoError().indexOf("DLE") > -1){
			beanResultado.setCodError(error.getCodigoError());
			beanResultado.setMsgError(error.getMsgError());
			return beanResultado;
		}else{
			ArrayList<BeanOperacion> respuestaBo = (ArrayList<BeanOperacion>) resultado.get("registros");
			if(respuestaBo != null) {
			beanResultado.setReferenciaAvanzar((String) resultado.get("referenciaAvanzar"));
			beanResultado.setReferenciaRetroceder((String) resultado.get("referenciaRetroceder"));
			beanResultado.setMasAdelante((Boolean) resultado.get("masAdelante"));
			beanResultado.setMasAtras((Boolean) resultado.get("masAtras"));

			for (BeanOperacion beanAux : respuestaBo) {
				beanAux.setImporteMaximoOperacionFront(formatoImportesToFront(beanAux.getImporteMaximoOperacion()));
				beanAux.setImporteMinimoOperacionFront(formatoImportesToFront(beanAux.getImporteMinimoOperacion()));
				beanAux.setAcumuladoDiarioFront(formatoImportesToFront(beanAux.getLimiteImporteMaximoDiarioAcum()));
				beanAux.setAcumuladoMensualFront(formatoImportesToFront(beanAux.getLimiteImporteMaximoMensualAcum()));
				beanAux.setHoraInicioFront(formatoHoraToFront(beanAux.getHoraInicio()));
				beanAux.setHoraFinalFront(formatoHoraToFront(beanAux.getHoraFinal()));
			}						
				beanResultado.setRegistros(respuestaBo);
			}
		}
		return beanResultado;
	}

	/**
	 * Alta operaciones corresponsalia.
	 *
	 * @param beanAlta the bean alta
	 * @param psession the psession
	 * @return the bean resultado operaciones corresponsal
	 */
	@Override
	public BeanResultadoOperacionesCorresponsal altaOperacionesCorresponsalia(BeanABMMantenimientoOperCorresponsal beanAlta,
			ArchitechSessionBean psession) {
		final BeanResultadoOperacionesCorresponsal beanRespuesta= new BeanResultadoOperacionesCorresponsal();
		BeanError beanRespuestaErr;
		info("Inicio Alta Corresponsalia BO ");		
		beanAlta.setImporteMaximoOperacion(formatoImportesTo390(beanAlta.getImporteMaximoOperacionFront()));
		beanAlta.setImporteMinimoOperacion(formatoImportesTo390(beanAlta.getImporteMinimoOperacionFront()));
		beanAlta.setAcumuladoDiario(formatoImportesTo390(beanAlta.getAcumuladoDiarioFront()));
		beanAlta.setAcumuladoMensual(formatoImportesTo390(beanAlta.getAcumuladoMensualFront()));
		beanAlta.setHoraInicio(formatoHoraTo390(beanAlta.getHoraInicioFront()));
		beanAlta.setHoraFinal(formatoHoraTo390(beanAlta.getHoraFinalFront()));		
		beanRespuestaErr = (BeanError) daoABM.aBMCorresponsalias(beanAlta, psession);
		beanRespuesta.setCodError(beanRespuestaErr.getCodigoError());
		beanRespuesta.setMsgError(beanRespuestaErr.getMsgError());			
		info("Fin Alta Corresponsalia BO ");
		return beanRespuesta;
	}

	/**
	 * Elimina operaciones corresponsalia.
	 *
	 * @param beanEliminacion the bean baja
	 * @param psession the psession
	 * @return the bean resultado operaciones corresponsal
	 */
	
	@Override
	public BeanResultadoOperacionesCorresponsal eliminaOperacionesCorresponsalia(
			BeanABMMantenimientoOperCorresponsal beanEliminacion, ArchitechSessionBean psession) {
		final BeanResultadoOperacionesCorresponsal beanRespuesta= new BeanResultadoOperacionesCorresponsal();
		BeanError beanRespuestaErr;
		info("Inicio Eliminacion Corresponsalia BO ");
		
		beanRespuestaErr = (BeanError) daoABM.aBMCorresponsalias(beanEliminacion, psession);

		beanRespuesta.setCodError(beanRespuestaErr.getCodigoError());
		beanRespuesta.setMsgError(beanRespuestaErr.getMsgError());
			
		info("Fin Eliminacion Corresponsalia BO ");
		return beanRespuesta;
	}

	/**
	 * Modificacion operaciones corresponsalia.
	 *
	 * @param beanModificacion the bean modificar
	 * @param psession the psession
	 * @return the bean resultado operaciones corresponsal
	 */
	@Override
	public BeanResultadoOperacionesCorresponsal modificacionOperacionesCorresponsalia(
			BeanABMMantenimientoOperCorresponsal beanModificacion, ArchitechSessionBean psession) {
		final BeanResultadoOperacionesCorresponsal beanRespuesta= new BeanResultadoOperacionesCorresponsal();
		BeanError beanRespuestaErr;
		info("Inicio Modificacion Corresponsalia BO ");		
		beanModificacion.setImporteMaximoOperacion(formatoImportesTo390(beanModificacion.getImporteMaximoOperacionFront()));
		beanModificacion.setImporteMinimoOperacion(formatoImportesTo390(beanModificacion.getImporteMinimoOperacionFront()));
		beanModificacion.setAcumuladoDiario(formatoImportesTo390(beanModificacion.getAcumuladoDiarioFront()));
		beanModificacion.setAcumuladoMensual(formatoImportesTo390(beanModificacion.getAcumuladoMensualFront()));
		beanModificacion.setHoraInicio(formatoHoraTo390(beanModificacion.getHoraInicioFront()));
		beanModificacion.setHoraFinal(formatoHoraTo390(beanModificacion.getHoraFinalFront()));	
		beanRespuestaErr = (BeanError) daoABM.aBMCorresponsalias(beanModificacion, psession);		
		beanRespuesta.setCodError(beanRespuestaErr.getCodigoError());
		beanRespuesta.setMsgError(beanRespuestaErr.getMsgError());			
		info("Fin Modificacion Corresponsalia BO ");
		return beanRespuesta;
	}
	
	/**
	 * Alta operaciones catalogo.
	 *
	 * @param beanAlta the bean alta
	 * @param psession the psession
	 * @return the bean error
	 */
	@Override
	public BeanError altaOperacionesCatalogo(
			BeanAltaOperacionesCatalogo beanAlta, ArchitechSessionBean psession) {
		info("Inicio Modificacion Corresponsalia BO ");
		final BeanError beanResultado = daoAltaOperacionesCatalogo.altaOperacionesCatalogo(beanAlta, getArchitechBean());		
		info("Fin Modificacion Corresponsalia BO ");
		return beanResultado;
	}

	/**
	 * Consulta operaciones catalogo.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado operaciones catalogo
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanResultadoOperacionesCatalogo consultaOperacionesCatalogo(
			BeanConsultaOperacionesCatalogo beanConsulta, ArchitechSessionBean psession) {
		final BeanResultadoOperacionesCatalogo beanRegreso = new BeanResultadoOperacionesCatalogo();
		info("Inicio Consulta Corresponsalia BO Catalogo");
		Object objetoRegreso = null;
		
		objetoRegreso = daoConsultaOperacionesCatalogo.consultaOperacionesCatalogo(
				beanConsulta, getArchitechBean());
		
		if(objetoRegreso instanceof BeanError){
			((BeanError) objetoRegreso).getCodigoError();
			beanRegreso.setCodError(((BeanError) objetoRegreso).getCodigoError());
			beanRegreso.setMsgError(((BeanError) objetoRegreso).getMsgError());
			return beanRegreso;
		}else{
			beanRegreso.setRegistros((ArrayList<BeanOperacionCatalogo>) objetoRegreso);
		}		

		info("Fin Consulta Corresponsalia BO Catalogo");
		return beanRegreso;
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
		try{
			importeToFront = importe.substring(0, importe.length()-2).concat(".").concat
											(importe.substring(importe.length()-2));			
			for (int aux=0; aux<importe.length()-3; aux++) {
				if (importeToFront.indexOf('0')==0) {
					importeToFront = importeToFront.substring(1);
				}
			}
		}catch(StringIndexOutOfBoundsException e){
			debug("No puede ser procesado el importe");
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
			horaToFront = hora.substring(0, hora.length()-4).concat(":").concat			
			(hora.substring(hora.length()-4, hora.length()-2));
		}catch(StringIndexOutOfBoundsException e){
			debug("formato no se pudo procesar");
		}
		return horaToFront;
	}
}
