package com.isban.corresponsalia.reportes;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.design.JasperDesign;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;


	// TODO: Auto-generated Javadoc
/**
	 * The Class ReporteJasper.
	 */
	public class ReporteJasper { 


	    /** The response. */
    	HttpServletResponse response;
	    
    	/** The jasper report. */
    	JasperReport jasperReport;
	    
    	/** The jasper print. */
    	JasperPrint jasperPrint;
	    
    	/** The jasper design. */
    	JasperDesign jasperDesign;
	    
    	/** The data source. */
    	JRDataSource dataSource;
    	
    	/** constante TXT_CON_DISP */
    	private static final String TXT_CON_DISP = "Content-Disposition";
    	
    	/** constante TXT_UTF8 */
    	private static final String TXT_UTF8 = "UTF-8";
	    
    	/** The pdf report. */
    	byte[] pdfReport;
	    
    	/**
    	 * Instantiates a new reporte jasper.
    	 *
    	 * @param response the response
    	 * @param dataSource the data source
    	 */
    	public ReporteJasper(HttpServletResponse response,JRDataSource dataSource) {
	        this.response = response;
	        this.dataSource = dataSource;
	    }

	    /**
    	 * Render pdf by jasper.
    	 *
    	 * @param jasper the jasper
    	 * @param parameters the parameters
    	 * @throws IOException Signals that an I/O exception has occurred.
    	 * @throws JRException the jR exception
    	 * @throws SQLException the sQL exception
    	 */
    	public void renderPdfByJasper(String jasper, Map<String,Object> parameters) throws IOException, JRException, SQLException {
	        InputStream is = this.getClass().getResource(jasper).openStream();
	        try {
	            jasperReport = (JasperReport) JRLoader.loadObject(is);
	            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, /*new JREmptyDataSource()*/dataSource);	            
	            pdfReport = JasperExportManager.exportReportToPdf(jasperPrint);
	            response.setContentType("APPLICATION/OCTET-STREAM");
	            response.addHeader(TXT_CON_DISP, "attachment; filename=report.pdf");
	            OutputStream out = response.getOutputStream();
	            out.write(pdfReport);
	            out.close();
	        }
	        finally {
	            is.close();
	        }
	    }

	    /**
    	 * Render excell by jasper.
    	 *
    	 * @param nombreArchivo the nombre archivo
    	 * @param jasper the jasper
    	 * @param parameters the parameters
    	 * @throws IOException Signals that an I/O exception has occurred.
    	 * @throws JRException the jR exception
    	 */
    	public void renderExcellByJasper(String nombreArchivo, String jasper, Map<String, Object> parameters) throws IOException, JRException {
	        InputStream is = this.getClass().getResource(jasper).openStream();
	            OutputStream out = response.getOutputStream();
	            ByteArrayOutputStream outBAO = new ByteArrayOutputStream(); 
	        	jasperReport = (JasperReport) JRLoader.loadObject(is);
	            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, /*new JREmptyDataSource()*/dataSource);
	            JRXlsExporter exporterXLS = new JRXlsExporter();
	            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
	            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outBAO);
	            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
	            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
	            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
	            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	            exporterXLS.exportReport();	             
	            response.setContentType("APPLICATION/OCTET-STREAM");
	            response.addHeader(TXT_CON_DISP, "attachment; filename="+ nombreArchivo +".xls");
	            out.write(outBAO.toByteArray());
	            out.flush();
	            out.close();
	            is.close();
	    }

	    /**
    	 * Render pdf by jrxml.
    	 *
    	 * @param jrxml the jrxml
    	 * @param parameters the parameters
    	 * @throws IOException Signals that an I/O exception has occurred.
    	 * @throws JRException the jR exception
    	 * @throws SQLException the sQL exception
    	 */
    	public void renderPdfByJrxml(String jrxml, Map<String, Object> parameters) throws IOException, JRException, SQLException {
	        InputStream is = this.getClass().getResource(jrxml).openStream();
	        try {
	            jasperDesign = JRXmlLoader.load(is);
	            jasperReport = JasperCompileManager.compileReport(jasperDesign);
	            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, /*new JREmptyDataSource()*/dataSource);
	            pdfReport = JasperExportManager.exportReportToPdf(jasperPrint);
	            response.setContentType("APPLICATION/OCTET-STREAM");
	            response.addHeader(TXT_CON_DISP, "attachment; filename=report.pdf");
	            OutputStream out = response.getOutputStream();
	            out.write(pdfReport);
	            out.close();
	        }
	        finally {
	            is.close();
	        }
	    }

	    /**
    	 * Render html by jasper.
    	 *
    	 * @param jasper the jasper
    	 * @param parameters the parameters
    	 * @throws IOException Signals that an I/O exception has occurred.
    	 * @throws JRException the jR exception
    	 * @throws SQLException the sQL exception
    	 */
    	public void renderHtmlByJasper(String jasper, Map<String, Object> parameters) throws IOException, JRException, SQLException {
	        InputStream is = this.getClass().getResource(jasper).openStream();
	        try {
	            jasperReport = (JasperReport) JRLoader.loadObject(is);
	            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,  /*new JREmptyDataSource()*/dataSource);
	            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	            OutputStream out = response.getOutputStream();
	            JRHtmlExporter exporterHTML = new JRHtmlExporter();
	            exporterHTML.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
	            exporterHTML.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
	            exporterHTML.setParameter(JRXlsExporterParameter.CHARACTER_ENCODING, TXT_UTF8);
	            exporterHTML.exportReport();
	            out.write(byteArrayOutputStream.toByteArray());
	            out.flush();
	            out.close();
	        }
	        finally {
	            is.close();
	        }
	    }

	    /**
    	 * Render html by jrxml.
    	 *
    	 * @param jrxml the jrxml
    	 * @param parameters the parameters
    	 * @throws IOException Signals that an I/O exception has occurred.
    	 * @throws JRException the jR exception
    	 * @throws SQLException the sQL exception
    	 */
    	public void renderHtmlByJrxml(String jrxml, Map<String, Object> parameters) throws IOException, JRException, SQLException {
	        InputStream is = this.getClass().getResource(jrxml).openStream();
	        try {
	            jasperDesign = JRXmlLoader.load(is);
	            jasperReport = JasperCompileManager.compileReport(jasperDesign);
	            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, /*new JREmptyDataSource()*/dataSource);
	            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	            OutputStream out = response.getOutputStream();
	            JRHtmlExporter jrHtmlExporter = new JRHtmlExporter();
	            jrHtmlExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
	            jrHtmlExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
	            jrHtmlExporter.setParameter(JRXlsExporterParameter.CHARACTER_ENCODING, TXT_UTF8);
	            jrHtmlExporter.exportReport();
	            out.write(byteArrayOutputStream.toByteArray());
	            out.flush();
	            out.close();
	        }
	        finally {
	            is.close();
	        }
	    }

	    /**
    	 * Render csv by jasper.
    	 *
    	 * @param jasper the jasper
    	 * @param parameters the parameters
    	 * @throws IOException Signals that an I/O exception has occurred.
    	 * @throws JRException the jR exception
    	 * @throws SQLException the sQL exception
    	 */
    	public void renderCsvByJasper(String jasper, Map<String, Object> parameters) throws IOException, JRException, SQLException {
	        InputStream is = this.getClass().getResource(jasper).openStream();
	        try {
	            jasperReport = (JasperReport) JRLoader.loadObject(is);
	            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, /*new JREmptyDataSource()*/dataSource);
	            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	            OutputStream out = response.getOutputStream();
	            JRCsvExporter jrCsvExporter = new JRCsvExporter();
	            jrCsvExporter.setParameter(JRCsvExporterParameter.JASPER_PRINT, jasperPrint);
	            jrCsvExporter.setParameter(JRCsvExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
	            jrCsvExporter.setParameter(JRCsvExporterParameter.CHARACTER_ENCODING, TXT_UTF8);
	            jrCsvExporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, ",");
	            jrCsvExporter.setParameter(JRCsvExporterParameter.RECORD_DELIMITER, "\r\n");	            
	            jrCsvExporter.exportReport();
	            response.addHeader(TXT_CON_DISP, "attachment; filename=logTransaccional.txt");
	            out.write(byteArrayOutputStream.toByteArray());
	            out.flush();
	            out.close();
	        }
	        finally {
	            is.close();
	        }
	    }

	    /**
    	 * Render csv by jrxml.
    	 *
    	 * @param jrxml the jrxml
    	 * @param parameters the parameters
    	 * @throws IOException Signals that an I/O exception has occurred.
    	 * @throws JRException the jR exception
    	 * @throws SQLException the sQL exception
    	 */
    	public void renderCsvByJrxml(String jrxml, Map<String, Object> parameters) throws IOException, JRException, SQLException {
	        InputStream is = this.getClass().getResource(jrxml).openStream();
	        try {
	            jasperDesign = JRXmlLoader.load(is);
	            jasperReport = JasperCompileManager.compileReport(jasperDesign);
	            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, /*new JREmptyDataSource()*/dataSource);
	            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	            OutputStream out = response.getOutputStream();
	            JRCsvExporter exporterCSV = new JRCsvExporter();
	            exporterCSV.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
	            exporterCSV.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
	            exporterCSV.setParameter(JRXlsExporterParameter.CHARACTER_ENCODING, TXT_UTF8);
	            exporterCSV.exportReport();
	            response.addHeader(TXT_CON_DISP, "attachment; filename=report.csv");
	            out.write(byteArrayOutputStream.toByteArray());
	            out.flush();
	            out.close();
	        }
	        finally {
	            is.close();
	        }
	    }
	}
