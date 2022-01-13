package com.isban.corresponsalia.reportes;

/**
	 * ISBAN Mexico - (c) Banco Santander Central Hispano
	 * Todos los derechos reservados
	 * ExportToCsvFile.java
	 *
	 * Control de versiones:
	 *
	 * Version	Date/Hour		By				Company		Description
	 * -------	--------------	-------------	--------	-----------
     *
	 *
	 */

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;

import org.springframework.web.servlet.view.AbstractView;

	// TODO: Auto-generated Javadoc
/**
	 * The Class ExportaXLS.
	 */
	public class ExportaXLS  extends AbstractView {

		/** The report file name. */
		String reportFileName = "";
		
		/** The report name. */
		String reportName = "";
		
		/** The data source. */
		JRDataSource dataSource = null;
		
		/**
		 * Instantiates a new exporta xls.
		 *
		 * @param reportName the report name
		 * @param reportFileName the report file name
		 * @param dataSource the data source
		 */
		public ExportaXLS(String reportName, String reportFileName,JRDataSource dataSource){
			super();
			this.reportFileName = reportFileName;
			this.reportName = reportName;
			this.dataSource = dataSource;
		}
		
		/**
		 * Render merged output model.
		 *
		 * @param map the map
		 * @param req objeto HttpServletRequest.
		 * @param res objeto HttpServletResponse.
		 * @throws Exception the exception
		 */

		@Override
		protected void renderMergedOutputModel(Map<String, Object> map,
				HttpServletRequest req, HttpServletResponse res) throws Exception {
	        ReporteJasper jrv = new ReporteJasper(res, dataSource);
	        try{
	        	jrv.renderExcellByJasper(this.reportName,this.reportFileName, map);
	        	//jrv.renderHtmlByJasper("/com/isban/corresponsalia/reportes/doctos/corresponsales.jasper", map);	        	
	        	//jrv.renderPdfByJrxml("/com/isban/corresponsalia/reportes/doctos/corresponsales.jrxml", map);	        	
	        }catch(IOException ioE){
	        	throw new Exception(ioE);
	        }catch(JRException jrE){
	        	throw new Exception(jrE);
	        }			
/*			List<Object> listBean = null;
			titulos = (String[])map.get("titulos");
			propiedades = (String[])map.get("propiedades");
			listBean = (List<Object>)map.get("listBean");
			OutputStream out = res.getOutputStream();
			res.setContentType("application/csv");
		    res.setHeader("content-disposition", "attachment; filename=file.csv");
			out.write(exportarToCsv(titulos, propiedades, listBean).getBytes());
			out.flush();
			out.close();*/	
		}
		
		


	}
