/*
 * 
 */
package com.isban.corresponsalia.controllers.consultas;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.isban.corresponsalia.beans.consultas.BeanConsultaLogTrx;
import com.isban.corresponsalia.beans.consultas.BeanFiltroConsultaLogTrx;
import com.isban.corresponsalia.bo.consultas.BOConsultasLogTrx;
import com.isban.corresponsalia.reportes.ExportaCSV;
import com.isban.ebe.commons.architech.ArchitechEBE;
import com.isban.ebe.commons.beans.ArchitechSessionBean;
import com.isban.ebe.commons.exception.BusinessException;
import com.isban.ebe.commons.exception.ExceptionDataAccess;


/**
 * The Class ControllerConsultasBitacora.
 */
@Controller
public class ControllerConsultasLogTrx extends ArchitechEBE{
	
	
	/** The Constant NEW_ARCH. */
	private static final String NEW_ARCH = "NewArchitechSession";
	
	/** The Constant TXT_SES. */
	private static final String TXT_SES = "SES";
	
	/** The Constant TXT_PAG. */
	private static final String TXT_PAG = "pagina";
	

	/** The b o consultas bitacora. */
	transient private BOConsultasLogTrx bOConsultasLogTrx;

	/**
	 * bOConsultasBitacora  Sets the bO consultas bitacora.
	 *
	 * @param bOConsultasLogTrx the new consultas log tran
	 */
	public void setBOConsultasLogTrx(BOConsultasLogTrx bOConsultasLogTrx){
		this.bOConsultasLogTrx = bOConsultasLogTrx;
	}
	
	/** consultaLogTransaccional
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="ConsultaLogTransaccional.do",method = RequestMethod.POST)
	public ModelAndView consultaLogTransaccional(HttpServletRequest req, HttpServletResponse res){		
		debug("ConsultaLogTransaccional.do");
		final HashMap<String,Object>  lhsmParametros   = new HashMap<String,Object>();
		lhsmParametros.put(TXT_SES, req.getParameter(TXT_SES));
		lhsmParametros.put(TXT_PAG, "1");		
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));		
		return new ModelAndView("ConsultaLogTransaccional",lhsmParametros);
	}	
	
	/** realizaConsultaLogTransaccional
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="RealizaConsultaLogTransaccional.do",method = RequestMethod.POST )
	public ModelAndView realizaConsultaLogTransaccional(HttpServletRequest req, HttpServletResponse res){
		debug("ConsultaLogTransaccional.do");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));
		final BeanFiltroConsultaLogTrx beanFiltro= new BeanFiltroConsultaLogTrx(); 
		/*parametros para filtrado y paginacion*/
		final String pagina= req.getParameter(TXT_PAG);
		final String txtFechaIni= req.getParameter("txtFechaIni");
		final String txtFechaFin= req.getParameter("txtFechaFin");
		final String txtTarjeta= req.getParameter("txtTarjeta");
		final String txtFolio= req.getParameter("txtFolio");
		final String estatusOperacion= req.getParameter("estatusOperacion");
		final String direccionPaginacion = req.getParameter("direccionPaginacion");
		final String operacion =  req.getParameter("operacion");
		final HashMap<String,Object>  lhsmParametros   = new HashMap<String,Object>();
		final HttpSession session = req.getSession();
		List<BeanConsultaLogTrx> listConsulta = null;
		beanFiltro.setFechaFinal(txtFechaFin);
		beanFiltro.setFechaInicio(txtFechaIni);
		beanFiltro.setFolio(txtFolio);
		beanFiltro.setNumeroTarjeta(txtTarjeta);
		lhsmParametros.put(TXT_PAG, pagina);	
		beanFiltro.setStatusOperacion(estatusOperacion);
		beanFiltro.setOperacion(operacion);
		if(direccionPaginacion != null && !"".equalsIgnoreCase(direccionPaginacion)){
			int paginaNum = Integer.parseInt(pagina);
			if("ADELANTE".equalsIgnoreCase(direccionPaginacion)){
				beanFiltro.setPagina(++paginaNum);		
			}else{
				beanFiltro.setPagina(--paginaNum);
			}
			lhsmParametros.put(TXT_PAG, (Integer.valueOf(paginaNum)).toString());
		}else{
			beanFiltro.setPagina(1); 
		}
		
		try {
			listConsulta = bOConsultasLogTrx.consultaLogTrx(this.getArchitechBean(), beanFiltro);
			Map<String, Object> mapGrupos =bOConsultasLogTrx.consultaNumRegistrosLogTrx(this.getArchitechBean(), beanFiltro);
			final BigDecimal numRegistros = (BigDecimal) mapGrupos.get("NUM_RENGLONES");
			final String sumaImportes  = (String)mapGrupos.get("TOTAL_IMPORTE");
			lhsmParametros.put("listaConsultaLogTrx", listConsulta);			
			lhsmParametros.put("numRegistros",numRegistros.toString());
			lhsmParametros.put("totalImporte",sumaImportes);
			if (numRegistros.longValue() > 0) {
				session.setAttribute("beanFiltro", beanFiltro);
			}
		} catch (BusinessException busEx) {
    		error("Exception: Error al realizar la consulta del log en realizaConsultaLogTransaccional...");
    		error(busEx.getMessage());
        	error(busEx.getCause().toString());
		} catch (ExceptionDataAccess dataEx) {		
			error("Exception: Error al obtener los datos en la consulta al log en realizaConsultaLogTransaccional... ");
    		error(dataEx.getMessage());
        	error(dataEx.getCause().toString());
		}
		lhsmParametros.put("txtFechaIni",txtFechaIni);
		lhsmParametros.put("txtFechaFin",txtFechaFin);
		lhsmParametros.put("txtTarjeta",txtTarjeta);
		lhsmParametros.put("txtFolio",txtFolio);
		lhsmParametros.put("estatusOperacion",estatusOperacion);
		lhsmParametros.put("operacion",operacion);
		lhsmParametros.put(TXT_SES, req.getParameter(TXT_SES));
		
		if(listConsulta == null || (listConsulta != null && listConsulta.isEmpty())){
			info("Registros encontrados: 0");
			lhsmParametros.put("codAviso", "NO HAY DATOS");
			lhsmParametros.put("msgAviso", "NO SE ENCONTRARON REGISTROS");
			//return new ModelAndView("ConsultaLogTransaccional",lhsmParametros);			
		}
		
		return new ModelAndView("ConsultaLogTransaccional",lhsmParametros);
	}	
	
	/**
	 * * * Exportar operaciones.
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="exportLogTransac.do")
	public ModelAndView exportLogTransac(HttpServletRequest req, HttpServletResponse res) {
		final HttpSession session = req.getSession(); 
		final HashMap<String, Object> map = new HashMap<String, Object>();
		List<BeanConsultaLogTrx>  listaRegistros = 
			(List<BeanConsultaLogTrx>) session.getAttribute("listaConsultaLogTrx");
		setArchitechBean((ArchitechSessionBean)req.getSession().getAttribute(NEW_ARCH ));
		StringBuffer queryReport = null;
		DataSource dataSourceConn = null;
		
		try {
			final BeanFiltroConsultaLogTrx beanFiltro = (BeanFiltroConsultaLogTrx) session.getAttribute("beanFiltro");
			if (beanFiltro != null) {

				
//				listaRegistros = bOConsultasLogTrx.consultaTodoLogTrx(this.getArchitechBean(), beanFiltro);
				queryReport = bOConsultasLogTrx.armaQueryTodoLogTrx(this.getArchitechBean(), beanFiltro);
				dataSourceConn = bOConsultasLogTrx.getBDConnection(this.getArchitechBean());
			}
		}  catch (BusinessException busEx) {
    		error("Exception: Error al realizar la consulta del log en exportLogTransac ...");
    		error(busEx.getMessage());
        	error(busEx.getCause().toString());
		}  catch (ExceptionDataAccess dataEx) {		
			error("Exception: Error al obtener los datos en la consulta al log en exportLogTransac... ");
    		error(dataEx.getMessage());
        	error(dataEx.getCause().toString());
		} catch (Exception ex) {
			error("Exception: Error en exportLogTransac... ");
    		error(ex.getMessage());
        	error(ex.getCause().toString());
		}
		
		if (dataSourceConn == null) {
			info("No se generó conexión a la BD");
			final HashMap<String,Object>  lhsmParametros = new HashMap<String,Object>();
			lhsmParametros.put("codAviso", "NOEXPORT");
			lhsmParametros.put("msgAviso", "NO SE PUEDE GENERAR EL REPORTE");
			return new ModelAndView("ConsultaLogTransaccional", lhsmParametros);
		}
		
		final DateFormat Dformat = new  SimpleDateFormat("yyyy/MM/dd HH:mm:ss aa",new Locale("la","MX"));
		map.put("fechaReporte", Dformat.format(java.util.Calendar.getInstance().getTime()));
		map.put("queryReport", queryReport);
		
		return new ModelAndView(new ExportaCSV("/com/isban/corresponsalia/reportes/doctos/logTransaccional.jasper", dataSourceConn), map);
		
	}
}

