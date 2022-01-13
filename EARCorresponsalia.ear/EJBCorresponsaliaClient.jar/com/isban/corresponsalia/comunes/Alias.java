package com.isban.corresponsalia.comunes;

import com.isban.configuracion.Configuracion;

// TODO: Auto-generated Javadoc
/**
 * The Class Alias.
 */
public class Alias {
	
	/** The idapp. */
	private static String IDAPP = "Alias";
	/** The idapp. */
	private static String IDACONF = "IsbanDataAccess";
	
	/**
	 * Gets the alias.
	 *
	 * @param pstrLlave the pstr llave
	 * @return the alias
	 */
	public static String getAlias(String pstrLlave){
		String lstrValorParametro = pstrLlave;

		try{
			//lstrValorParametro = Configuracion.getInstance().getParametro(IDAPP, pstrLlave);
			lstrValorParametro = Configuracion.getInstance().getParametro(Configuracion.getInstance().getId(IDAPP), pstrLlave);			
		}
		catch(Exception e){
			System.out.println("No fue posible ontener el alias[" + pstrLlave + "] solicitado");
		}
	
		return lstrValorParametro;
	}
	
	/**
	 * Gets the alias.
	 *
	 * @param pstrLlave the pstr llave
	 * @return the alias
	 */
	public static String getIsbanConf(String pstrLlave){
		String lstrValorParametro = pstrLlave;

		try{
			lstrValorParametro = Configuracion.getInstance().getParametro(Configuracion.getInstance().getId(IDACONF), pstrLlave);			
		}
		catch(Exception e){
			System.out.println("No fue posible ontener el alias[" + pstrLlave + "] solicitado");
		}
	
		return lstrValorParametro;
	}
	
}
