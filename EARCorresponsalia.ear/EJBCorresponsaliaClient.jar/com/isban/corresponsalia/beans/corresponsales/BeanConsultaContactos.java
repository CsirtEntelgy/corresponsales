package com.isban.corresponsalia.beans.corresponsales;

import java.io.Serializable;


// TODO: Auto-generated Javadoc
/**
 * The Class BeanConsultaContactos.
 */
public class BeanConsultaContactos implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6858515588419825562L;

		/** The canal. */
		private String canal = "";
		
		/** The id corresponsal. */
		private String idCorresponsal = "";
		
		/** The zona ubicacion contacto. */
		private String zonaUbicacionContacto = "";
		
		/** The seq id contacto. */
		private String seqIdContacto = "";
		
		/** The ind paginacion. */
		private String indPaginacion = "";
		
		/** The ind direccion. */
		private String indDireccion = "";
		
		/** The tipo consulta. */
		private String tipoConsulta = "";
		
		/** The lim inferior. */
		private String limInferior = "";
		
		/** The lim superior. */
		private String limSuperior = "";
		
		/**
		 * Gets the canal.
		 *
		 * @return the canal
		 */
		public String getCanal() {
			return canal;
		}
		
		/**
		 * Sets the canal.
		 *
		 * @param canal the new canal
		 */
		public void setCanal(String canal) {
			this.canal = canal.trim();
		}
		
		/**
		 * Gets the id corresponsal.
		 *
		 * @return the id corresponsal
		 */
		public String getIdCorresponsal() {
			return idCorresponsal;
		}
		
		/**
		 * Sets the id corresponsal.
		 *
		 * @param idCorresponsal the new id corresponsal
		 */
		public void setIdCorresponsal(String idCorresponsal) {
			this.idCorresponsal = idCorresponsal.trim();
		}
		
		/**
		 * Gets the zona ubicacion contacto.
		 *
		 * @return the zona ubicacion contacto
		 */
		public String getZonaUbicacionContacto() {
			return zonaUbicacionContacto;
		}
		
		/**
		 * Sets the zona ubicacion contacto.
		 *
		 * @param zonaUbicacionContacto the new zona ubicacion contacto
		 */
		public void setZonaUbicacionContacto(String zonaUbicacionContacto) {
			this.zonaUbicacionContacto = zonaUbicacionContacto.trim();
		}
		
		/**
		 * Gets the seq id contacto.
		 *
		 * @return the seq id contacto
		 */
		public String getSeqIdContacto() {
			return seqIdContacto;
		}
		
		/**
		 * Sets the seq id contacto.
		 *
		 * @param seqIdContacto the new seq id contacto
		 */
		public void setSeqIdContacto(String seqIdContacto) {
			this.seqIdContacto = seqIdContacto;
		}
		
		/**
		 * Gets the ind paginacion.
		 *
		 * @return the ind paginacion
		 */
		public String getIndPaginacion() {
			return indPaginacion;
		}
		
		/**
		 * Sets the ind paginacion.
		 *
		 * @param indPaginacion the new ind paginacion
		 */
		public void setIndPaginacion(String indPaginacion) {
			this.indPaginacion = indPaginacion;
		}
		
		/**
		 * Gets the ind direccion.
		 *
		 * @return the ind direccion
		 */
		public String getIndDireccion() {
			return indDireccion;
		}
		
		/**
		 * Sets the ind direccion.
		 *
		 * @param indDireccion the new ind direccion
		 */
		public void setIndDireccion(String indDireccion) {
			this.indDireccion = indDireccion.trim();
		}
		
		/**
		 * Gets the tipo consulta.
		 *
		 * @return the tipo consulta
		 */
		public String getTipoConsulta() {
			return tipoConsulta;
		}
		
		/**
		 * Sets the tipo consulta.
		 *
		 * @param tipoConsulta the new tipo consulta
		 */
		public void setTipoConsulta(String tipoConsulta) {
			this.tipoConsulta = tipoConsulta.trim();
		}
		
		/**
		 * Gets the lim inferior.
		 *
		 * @return the lim inferior
		 */
		public String getLimInferior() {
			return limInferior;
		}
		
		/**
		 * Sets the lim inferior.
		 *
		 * @param limInferior the new lim inferior
		 */
		public void setLimInferior(String limInferior) {
			this.limInferior = limInferior.trim();
		}
		
		/**
		 * Gets the lim superior.
		 *
		 * @return the lim superior
		 */
		public String getLimSuperior() {
			return limSuperior;
		}
		
		/**
		 * Sets the lim superior.
		 *
		 * @param limSuperior the new lim superior
		 */
		public void setLimSuperior(String limSuperior) {
			this.limSuperior = limSuperior.trim();
		}	
}
