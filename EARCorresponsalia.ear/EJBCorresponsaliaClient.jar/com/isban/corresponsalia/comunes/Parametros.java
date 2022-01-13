package com.isban.corresponsalia.comunes;

import com.isban.configuracion.Configuracion;

// TODO: Auto-generated Javadoc
/**
 * The Class Parametros.
 */
public class Parametros {

	/** The idapp. */
	private static String IDAPP = "ParametrosAplicacion";
	
	/**
	 * Gets the parametro aplicacion.
	 *
	 * @param pstrLlave the pstr llave
	 * @return the parametro aplicacion
	 */
	public static String getParametroAplicacion(String pstrLlave){
		String lstrValorParametro = "";

		try{
			lstrValorParametro = Configuracion.getInstance().getParametro(Configuracion.getInstance().getId(IDAPP), pstrLlave);
		}
		catch(Exception e){
			System.out.println("No fue posible ontener el valor parametro[" + pstrLlave + "] solicitado");
		}
	
		return lstrValorParametro;
	}
	
	/**
	 * Gets the parametro componente.
	 *
	 * @param pstrIdComponente the pstr id componente
	 * @param pstrLlave the pstr llave
	 * @return the parametro componente
	 */
	public static String getParametroComponente(String pstrIdComponente, String pstrLlave){
		String lstrValorParametro = "";

		try{
			lstrValorParametro = Configuracion.getInstance().getParametro(Configuracion.getInstance().getId(pstrIdComponente), pstrLlave);
		}
		catch(Exception e){
			System.out.println("No fue posible ontener el valor parametro[" + pstrLlave + "] solicitado");
		}
	
		return lstrValorParametro;
	}

}
