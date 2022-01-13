/**
 * ISBAN Mexico - (c) Banco Santander Central Hispano
 * Todos los derechos reservados
 * ExportToCsvFile.java
 *
 * Control de versiones:
 *
 * Version	Date/Hour		By				Company		Description
 * -------	--------------	-------------	--------	-----------
 * 1.0		16-11-10 13:10 	Carlos Chong	Stefanini	Creacion
 *
 */
package com.isban.corresponsalia.comunes;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.web.servlet.view.AbstractView;

// TODO: Auto-generated Javadoc
/**
 * Clase del tipo View que sirve para generar y descargar los archivos csv.
 * 
 * @author Carlos Chong
 */
public class ExportToCsvFile extends AbstractView {

	/**
	 * Metodo para realizar la exportacion de datos a formato CSV.
	 *
	 * @param map the map
	 * @param req objeto HttpServletRequest.
	 * @param res objeto HttpServletResponse.
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> map,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		String titulos[] =null;
		String propiedades[] =null;
		List<Object> listBean = null;
		titulos = (String[])map.get("titulos");
		propiedades = (String[])map.get("propiedades");
		listBean = (List<Object>)map.get("listBean");
		OutputStream out = res.getOutputStream();
		res.setContentType("application/csv");
	    res.setHeader("content-disposition", "attachment; filename=file.csv");
		out.write(exportarToCsv(titulos, propiedades, listBean).getBytes());
		out.flush();
		out.close();	
	}

	/**
	 * Exportar a CSV. Metodo que se encarga de tomar los atributos de cada bean
	 * (obtenido de la lista de beans), obteniendos los datos que almacena cada
	 * atributo separandolo por comas ','.
	 *
	 * @param titulos the titulos
	 * @param propiedades the propiedades
	 * @param listBean the list bean
	 * @return String con los datos obtenidos de la lista de Beans separados por comas ','.
	 */
	public String exportarToCsv(String[] titulos, String propiedades[],
			List<Object> listBean) {
		Object obj = null;
		StringBuilder stringBuilder = new StringBuilder();
		if (titulos != null) {
			for (int index = 0; index < titulos.length; index++) {
				stringBuilder.append(titulos[index]);
				if (index < titulos.length - 1) {
					stringBuilder.append(",");
				} else {
					stringBuilder.append("\r\n");
				}
			}
		}
		try {
			if (listBean != null && propiedades != null) {
				Iterator<Object> iteraBean = listBean.iterator();
				while (iteraBean.hasNext()) {
					obj = iteraBean.next();
					for (int index = 0; index < propiedades.length; index++) {
						stringBuilder.append(PropertyUtils.getSimpleProperty(
								obj, propiedades[index]));
						if (index < propiedades.length - 1) {
							stringBuilder.append(",");
						}
					}
					stringBuilder.append("\r\n");
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

}
