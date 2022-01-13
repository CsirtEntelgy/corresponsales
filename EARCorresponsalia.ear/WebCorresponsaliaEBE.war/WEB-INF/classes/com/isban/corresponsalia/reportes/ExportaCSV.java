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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;
import java.io.Serializable;

import com.isban.corresponsalia.comunes.Facultades;
import com.isban.corresponsalia.comunes.GlobalArchitech;
import com.isban.ebe.commons.architech.ArchitechEBE;

import org.springframework.web.servlet.view.AbstractView;

	// TODO: Auto-generated Javadoc
     /**
	 * The Class ExportaXLS.
	 */

	public class ExportaCSV  extends AbstractView implements Serializable {

		/**
		 * The Constant serialVersionUID.
		 */
		private static final long serialVersionUID = 8823864417947380870L;

		/** The report file name. */
		 String reportFileName = "";
		
		/** The Data Source connection*/
		 DataSource dataSourceConn = null;
		
		/**
		 * Instantiates a new exporta xls.
		 *
		 * @param reportFileName the report file name
		 * @param dataSourceConn the data source
		 */
		public ExportaCSV(String reportFileName, DataSource dataSourceConn){
			super();
			this.reportFileName = reportFileName;
			this.dataSourceConn = dataSourceConn;
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
			ByteArrayOutputStream byteArrayOutputStream = null;
			JRFileVirtualizer virtualizer = null;
			JRCsvExporter jrCsvExporter = null;
			JasperReport jasperReport = null;
	        JasperPrint jasperPrint = null;
	        OutputStream out = null;
	        InputStream is = null;
	        GlobalArchitech archEbe = new GlobalArchitech();
	        
	        try {
	        	is = this.getClass().getResource(this.reportFileName).openStream();
	    		jasperReport = (JasperReport) JRLoader.loadObject(is);
	    		
	    		virtualizer = new JRFileVirtualizer(2, "/proarchivapp");
		        map.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);		        
	    		
		        jasperPrint = JasperFillManager.fillReport(jasperReport, map, dataSourceConn.getConnection());
		        byteArrayOutputStream = new ByteArrayOutputStream();		        
		        
		        jrCsvExporter = new JRCsvExporter();
		        jrCsvExporter.setParameter(JRCsvExporterParameter.JASPER_PRINT, jasperPrint);
		        jrCsvExporter.setParameter(JRCsvExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
		        jrCsvExporter.setParameter(JRCsvExporterParameter.CHARACTER_ENCODING, "UTF-8");
		        jrCsvExporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, ",");
		        jrCsvExporter.setParameter(JRCsvExporterParameter.RECORD_DELIMITER, "\r\n");	            
		        jrCsvExporter.exportReport();
		        
		        res.addHeader("Content-Disposition", "attachment; filename=logTransaccional.txt");
		        out = res.getOutputStream();
		        out.write(byteArrayOutputStream.toByteArray());
	        } catch (IOException ioE) {
	        	archEbe.error("BusinessExcepIO: Error al recuperar archivo *.jasper");
	        	archEbe.error(ioE.getMessage());
	        	archEbe.error(ioE.getCause().toString());				
		    } catch (JRException jrE) {
		    	archEbe.error("BusinessExcepJR: Error al cargar archivo *.jasper");
		    	archEbe.error(jrE.getMessage());
		    	archEbe.error(jrE.getCause().toString());
		    } catch (SQLException sqlex) {
		    	archEbe.error("BusinessExcepSQL: Error al obtener conexión al BD");
		    	archEbe.error(sqlex.getMessage());
		    	archEbe.error(sqlex.getCause().toString());		 	
			} catch (Exception ex) {
				archEbe.error(ex.getMessage());
				archEbe.error(ex.getStackTrace().toString());
		    } finally {		    	
		    	if (is != null) {
		    		is.close();
		    	}		    	
		    	if (out != null) {
		    		out.flush();
		    		out.close();
		    	}
		    	if (jrCsvExporter != null) {
		    		jrCsvExporter = null;
		    	}
		    	jasperReport = null;
		    	if (virtualizer != null) {
		    		virtualizer.cleanup();
		    	}
		    	if (dataSourceConn != null && !dataSourceConn.getConnection().isClosed()) {
		    		dataSourceConn.getConnection().close();
				}
		    }
	        
		}

	}
