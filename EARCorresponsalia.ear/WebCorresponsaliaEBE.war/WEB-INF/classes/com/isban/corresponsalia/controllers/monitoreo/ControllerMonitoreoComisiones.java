package com.isban.corresponsalia.controllers.monitoreo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.isban.ebe.commons.architech.ArchitechEBE;


/**
 * The Class ControllerMonitoreoComisiones.
 */
@Controller
public class ControllerMonitoreoComisiones extends ArchitechEBE{
	
	
	/**
	 * Monitoreo comisiones.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="MonitoreoComisiones.do")
	public ModelAndView monitoreoComisiones(HttpServletRequest req,
			HttpServletResponse res){
		return new ModelAndView("MonitoreoComisiones");
	}			
}

