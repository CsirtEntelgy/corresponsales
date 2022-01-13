package com.isban.corresponsalia.controllers.errores;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.isban.ebe.commons.architech.ArchitechEBE;

/**
 * The Class ControllerErrores.
 */
@Controller
public class ControllerErrores extends ArchitechEBE{

	/**
	 * Error ebe.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="errorEBE.do")
	public ModelAndView errorEBE(HttpServletRequest req,
			HttpServletResponse res){
		return new ModelAndView("excepcionInesperadaEBE");
	}	

	/**
	 * Error grl.
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 */
	@RequestMapping(value="errorGrl.do")
	public ModelAndView errorGrl(HttpServletRequest req,
			HttpServletResponse res){
		return new ModelAndView("excepcionInesperada");
	}
	
}

