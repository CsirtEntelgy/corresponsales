package com.isban.corresponsalia.bo.corresponsales;
import javax.ejb.Local;

import com.isban.corresponsalia.beans.corresponsales.BeanAltaContactos;
import com.isban.corresponsalia.beans.corresponsales.BeanConsultaContactos;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoAltaContacto;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoContactos;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.ebe.commons.beans.ArchitechSessionBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface BOCorresponsalesContactos.
 */
@Local
public interface BOCorresponsalesContactos {

	/**
	 * Consulta corresponsales.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado corresponsalia
	 */
	BeanResultadoCorresponsalia consultaCorresponsales(
			BeanMttoConsultaCorresponsal beanConsulta,
			ArchitechSessionBean psession);

	/**
	 * Consulta contactos.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado contactos
	 */
	BeanResultadoContactos consultaContactos(
			BeanConsultaContactos beanConsulta, ArchitechSessionBean psession);
	
	/**
	 * Alta contacto.
	 *
	 * @param beanConsulta the bean consulta
	 * @param psession the psession
	 * @return the bean resultado alta contacto
	 */
	BeanResultadoAltaContacto altaContacto(
			BeanAltaContactos beanConsulta, ArchitechSessionBean psession);	

}
