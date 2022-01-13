package com.isban.corresponsalia.controllers.monitoreo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.isban.corresponsalia.beans.comunes.BeanCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanMttoConsultaCorresponsal;
import com.isban.corresponsalia.beans.corresponsales.BeanResultadoCorresponsalia;
import com.isban.corresponsalia.beans.monitoreo.BeanMonitoreoCredito;
import com.isban.corresponsalia.beans.monitoreo.BeanMonitorLineaCred;
import com.isban.corresponsalia.beans.monitoreo.BeanRegistroMonitoreoCredito;
import com.isban.corresponsalia.beans.monitoreo.RBeanMonitoreoCredito;
import com.isban.corresponsalia.bo.monitoreo.BOMonitoreoCredito;
import com.isban.corresponsalia.comunes.Parametros;
import com.isban.corresponsalia.comunes.Utils;
import com.isban.corresponsalia.reportes.ExportaXLS;

import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;

/**
 * The Class ControllerMonitoreoLineaCredito.
 */
@Controller
public class ControllerMonitoreoLineaCredito extends ArchitechEBE{
	

	/** The b o monitoreo credito. */
	transient private BOMonitoreoCredito bOMonitoreoCredito;

	/**
	 * Sets the bO monitoreo credito.
	 *
	 * @param bOMonitoreoCredito the new bO monitoreo credito
	 */
	public void setBOMonitoreoCredito(BOMonitoreoCredito bOMonitoreoCredito){
		this.bOMonitoreoCredito = bOMonitoreoCredito;
	}
	
	/**
	 * Realiza monitoreo credito.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="MonitoreoCredito.do",method = RequestMethod.POST)
	public ModelAndView realizaMonitoreoCredito(HttpServletRequest req, HttpServletResponse res) {
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute("NewArchitechSession"));		
		debug("MonitoreoCredito.do");
		final BeanMonitoreoCredito beanMonitoreoCredito = new BeanMonitoreoCredito(Parametros.getParametroAplicacion("CORRESPONSALIA"));
		RBeanMonitoreoCredito rBeanMonitoreoCredito = new RBeanMonitoreoCredito();
		String lstrCodErrorPage = "";
		String lstrMsgErrorPage = "";
		
		req.getSession().removeAttribute("listaRegistrosCreditos");
		final BeanMttoConsultaCorresponsal beanConsulta         = new BeanMttoConsultaCorresponsal();		
		beanConsulta.setCodigoCorresponsalia(Parametros.getParametroAplicacion("CORRESPONSALIA"));
		beanConsulta.setIndicadorPaginacion("N");
		beanConsulta.setTipoConsulta("L");
		try{
			rBeanMonitoreoCredito = bOMonitoreoCredito.monitoreoCredito(getArchitechBean(), beanMonitoreoCredito);
			
			lstrCodErrorPage = rBeanMonitoreoCredito.getCodError();
			lstrMsgErrorPage = rBeanMonitoreoCredito.getMsgError();
			if("MNCR0000".equals(lstrCodErrorPage)){
				BeanResultadoCorresponsalia beanResultadoCorresponsalia = bOMonitoreoCredito.consultaCorresponsales(beanConsulta, getArchitechBean());
				List<BeanMonitorLineaCred> lstMonitoreoCredito = new ArrayList<BeanMonitorLineaCred>();
				
				if(!beanResultadoCorresponsalia.getRegistros().isEmpty()){
					lstMonitoreoCredito = getListaFront(beanResultadoCorresponsalia.getRegistros(),rBeanMonitoreoCredito.getListaRegistrosMonitoreoCredito());
				}
				else{
					lstrCodErrorPage = beanResultadoCorresponsalia.getCodError();
					lstrMsgErrorPage = beanResultadoCorresponsalia.getMsgError();
					req.getSession().setAttribute("listaCorresponsales", new ArrayList<BeanCorresponsal>());
				}
				req.getSession().setAttribute("listaRegistrosCreditos", lstMonitoreoCredito);
			}
		}
		catch(BusinessException e){
			//showException(e);
			lstrCodErrorPage = e.getCode();
			lstrMsgErrorPage = e.getMessage();
		}
		
		debug("Codigo error :" + lstrCodErrorPage);
		debug("Mensaje error:" + lstrMsgErrorPage);		
		return new ModelAndView("MonitoreoCredito");
	}	
	
	/**
	 * Exportar bitacora.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="ExportarMonitoreoCredito.do")
	public ModelAndView exportarBitacora(HttpServletRequest req, HttpServletResponse res){
		final HashMap<String, Object> map = new HashMap<String, Object>();
		final List<BeanMonitorLineaCred> listaRegistros = (List<BeanMonitorLineaCred>) req.getSession().getAttribute("listaRegistrosCreditos");
		
		if(listaRegistros==null || (listaRegistros != null && listaRegistros.isEmpty())){
			final HashMap<String,Object>  lhsmParametros   = new HashMap<String,Object>();
			lhsmParametros.put("codError", "NOEXPORT");
			lhsmParametros.put("msgError", "NO HAY REGISTROS PARA EXPORTAR");
			return new ModelAndView("MonitoreoCredito",lhsmParametros);			
		}
		final JRDataSource dataSource = new JRBeanArrayDataSource(listaRegistros.toArray());
		final DateFormat Dformat = 
			new  SimpleDateFormat("yyyy/MM/dd HH:mm:ss aa",new Locale("ES","MX")); 
		map.put("fechaReporte", Dformat.format(java.util.Calendar.getInstance().getTime()));  //  pasamos la fecha al reporte
		return new ModelAndView(new ExportaXLS("monitoreolineacredito","/com/isban/corresponsalia/reportes/doctos/monitoreolineacredito.jasper",dataSource), map);		
	}
	
	/**
	 * Gets the lista front.
	 *
	 * @param lstCorresponsales the lst corresponsales
	 * @param lstCreditos the lst creditos
	 * @return the lista front
	 */
	public List<BeanMonitorLineaCred> getListaFront(List<BeanCorresponsal> lstCorresponsales, List<BeanRegistroMonitoreoCredito> lstCreditos){
		
		final List<BeanMonitorLineaCred> lstMonitoreoCredito = new ArrayList<BeanMonitorLineaCred>();
		double cheqDisponible;
		double credOtor;
		double credDisponible;
		double credDispuesto;
		double limAlerta;
		double operado;
		boolean alarma;
			for(BeanCorresponsal datCorres:lstCorresponsales){
				for(BeanRegistroMonitoreoCredito datCredito:lstCreditos){
					if(datCredito.getCorresponsal().equals(datCorres.getCodigoCorresponsal())){						
						cheqDisponible = Double.parseDouble(Utils.formateaDobles(datCredito.getDisponibleCheques()));
						credOtor = Double.parseDouble(Utils.formateaDobles(datCredito.getCreditoOtorgado()));
						credDisponible = Double.parseDouble(Utils.formateaDobles(datCredito.getDisponibleCredito()));
						credDispuesto = credOtor - credDisponible;
						limAlerta = Double.parseDouble(Utils.formateaDobles(datCredito.getLimiteAlerta()));
						operado = Double.parseDouble(Utils.formateaDobles(datCredito.getSaldoOperado()));
						alarma = ((credDisponible + cheqDisponible - operado)< limAlerta?true:false);
						
						BeanMonitorLineaCred beanFront = new BeanMonitorLineaCred();
						beanFront.setCodCorresponsal(datCredito.getCorresponsal());
						beanFront.setCredDisponible(String.format("%.2f", Double.valueOf(datCredito.getDisponibleCredito())));
						beanFront.setCredDispuesto(String.format("%.2f", Double.valueOf(credDispuesto)));
						beanFront.setCredOtorgado(String.format("%.2f", Double.valueOf(datCredito.getCreditoOtorgado())));
						beanFront.setInicadorAlerta(alarma);
////						prueba para la alerta -->quitar la linea siguiente
//						beanFront.setInicadorAlerta(datCorres.getNombreCorresponsal().indexOf("FHS")>-1?!alarma:alarma);
////						fin de prueba quitar la linea anterior
						beanFront.setLimiteAlerta(String.format("%.2f", Double.valueOf(datCredito.getLimiteAlerta().trim())));
						beanFront.setNomCorresponsal(datCorres.getNombreCorresponsal());
						beanFront.setPendienteComp(String.format("%.2f", Double.valueOf(datCredito.getSaldoOperado().trim())));
						beanFront.setSaldoCheques(String.format("%.2f", Double.valueOf(datCredito.getDisponibleCheques())));
						
						lstMonitoreoCredito.add(beanFront);
						debug("beanFront: " + beanFront);
					}
				}
			}	
	return lstMonitoreoCredito; 
	}
}

