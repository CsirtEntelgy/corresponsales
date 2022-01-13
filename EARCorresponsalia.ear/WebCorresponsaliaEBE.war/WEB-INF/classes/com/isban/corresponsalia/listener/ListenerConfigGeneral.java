package com.isban.corresponsalia.listener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.isban.corresponsalia.comunes.Facultades;
import com.isban.corresponsalia.comunes.GlobalArchitech;
import com.isban.corresponsalia.comunes.Parametros;

import java.net.MalformedURLException;
import java.io.File;


public class ListenerConfigGeneral implements ServletContextListener {

	/** init Especifica si ya fue inicializada la configuración */
	private static boolean  init = false;
	/** archGlo Manejo de metodos de arquitectura EBE*/
	private static GlobalArchitech archGlo = new GlobalArchitech();
	
	
	/**
	 * contextDestroyed
	 * @param arg0
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		init = false;
		archGlo.info("El contexto fue destruido...");
	}

	/* (sin Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public synchronized void contextInitialized(ServletContextEvent psceContexto) {
		
		archGlo.info("Iniciando configuracion Basica...");
		
    	if(init){
    		archGlo.info("La configuracion ya fue inicializada previamente....");
    		return;
    	}
    	
    	String      lstrArchivoCLA = "";
    	String      lstrIdApp      = "";
    	InputStream lisArchivoCLA  = null;
		
    	lstrArchivoCLA   = psceContexto.getServletContext().getInitParameter("ArchivoCLA");
    	lstrIdApp        = psceContexto.getServletContext().getInitParameter("IdApp");
    	archGlo.info("Archivo CLA :" + lstrArchivoCLA);
    	archGlo.info("IdApp       :" + lstrIdApp);
    	archGlo.info("Context Path:" + psceContexto.getServletContext().getContextPath());
    try{
    		lisArchivoCLA = psceContexto.getServletContext().getResourceAsStream("/WEB-INF/" + lstrArchivoCLA);
    		if(lisArchivoCLA!=null){
        		String pathArchivo = psceContexto.getServletContext().getResource("/WEB-INF/" + lstrArchivoCLA).getPath();
        		archGlo.info("Archivo Configuracion     :" + pathArchivo);
        		
        		ConfiguracionGeneral config = new ConfiguracionGeneral();
    			config.init(pathArchivo, lstrIdApp);
    			
    			init = true;	
    			archGlo.debug("Termina inicializacion...");
    		}
    	}
    	catch (IOException ioEx){
    		archGlo.error("BusinessExcepIO: Error al recuperar archivo: " + lstrArchivoCLA);
        	archGlo.error(ioEx.getMessage());
        	archGlo.error(ioEx.getCause().toString());	
    	}
    	catch (NullPointerException nullEx) {
        	archGlo.error("BusinessExcepIO: Error al recuperar archivo de perfiles");
        	archGlo.error(nullEx.getMessage());
        	archGlo.error(nullEx.getCause().toString()); 	
		}
    	catch(Exception e){
    		archGlo.error("Exception: Error al inicializar el contexto... ");
    		archGlo.error(e.getMessage());
        	archGlo.error(e.getCause().toString());
    	}
    	String lstrArchivoPerfiles = Parametros.getParametroAplicacion("ARCHIVO_FACULTADES");
    	try {
                 File file = new File(lstrArchivoPerfiles);
                 if (file.exists()){
                 URL urlArchivoPerfiles = file.toURI().toURL();
			if(urlArchivoPerfiles == null){
				archGlo.info("No se pudo encontrar el recurso:" + lstrArchivoPerfiles);
				
			}
			else{
				archGlo.debug("Archivo:" + urlArchivoPerfiles.getFile());
				archGlo.debug("Path   :" + urlArchivoPerfiles.getPath());
				Facultades.init(urlArchivoPerfiles.getFile());
			}
                }
		} catch (IOException ioE) {
        	archGlo.error("BusinessExcepIO: Error al recuperar archivo de perfiles");
        	archGlo.error(ioE.getMessage());
        	archGlo.error(ioE.getCause().toString()); 	
		} catch (NullPointerException nullEx) {
        	archGlo.error("BusinessExcepIO: Error al recuperar archivo de perfiles");
        	archGlo.error(nullEx.getMessage());
        	archGlo.error(nullEx.getCause().toString()); 	
		} catch (Exception ex) {
			archGlo.error(ex.getMessage());
			archGlo.error(ex.getStackTrace().toString());
		}
	}

}

