package com.isban.corresponsalia.comunes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.isban.ebe.commons.architech.ArchitechEBE;



/**
 * The Class Facultades.
 */
public class Facultades extends ArchitechEBE{

	/** The instance. */
	private static Facultades                   instance        = null;
	
	/** The lista facultades. */
	private static HashMap<String, List<String>> listaFacultades = null;
	
	/** The Constant CODIGO_ERROR. */
	public final static String CODIGO_ERROR  = "NOFACU"; 
	
	/** The Constant MENSAJE_ERROR. */
	public final static String MENSAJE_ERROR = "NO CUENTAS CON FACULTADES"; 
	
	/**
	 * Instantiates a new facultades.
	 */
	private Facultades(){
	}
	
	/**
	 * Inits the.
	 *
	 * @param pstrArchivo the pstr archivo
	 */
	public static void init(String pstrArchivo){
		getInstanceOf().cargaFacultades(pstrArchivo);
		getInstanceOf().despliegaRelacionPerfilFacultad();

	}
	
	/**
	 * Despliega relacion perfil facultad.
	 */
	private void despliegaRelacionPerfilFacultad(){
		if(listaFacultades!=null){
			Set<String> keySet = listaFacultades.keySet();
			for(String strKeyTemp:keySet){
				debug("Perfil:" + strKeyTemp);
				List<String> listaFacultadesPerfil = listaFacultades.get(strKeyTemp);
				for(String lstrFacultadTemp:listaFacultadesPerfil){
					debug("\tTRX:" + lstrFacultadTemp);
                                }
			}
		}
	}
	
	/**
	 * Carga facultades.
	 *
	 * @param lstrArchivoPerfiles the lstr archivo perfiles
	 */
	private void cargaFacultades(String lstrArchivoPerfiles){
		
		debug("Archivo Facultades:" + lstrArchivoPerfiles);

		if(lstrArchivoPerfiles==null){
			debug("No esta configurado el archivo de facultades vs perfil");
			return;
		}
		else{
			try {
				Document doc  = null;
				debug("Parseando documento...");
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder       db = dbf.newDocumentBuilder();

				doc = db.parse(lstrArchivoPerfiles); 
				Node     nodeRaiz      = doc.getFirstChild();
				NodeList listaPerfiles = nodeRaiz.getChildNodes();
				
				listaFacultades = new HashMap<String, List<String>> ();
				for (int iNode = 0; iNode < listaPerfiles.getLength(); iNode++) {
					Node nodePerfil = listaPerfiles.item(iNode);
					if("PERFIL".equals(nodePerfil.getNodeName())){
						String lstrNombrePerfil = nodePerfil.getAttributes().getNamedItem("NOMBRE").getNodeValue(); 
						debug("Perfil:" + lstrNombrePerfil);
						
						NodeList listaIds = nodePerfil.getChildNodes();
						debug("Numero TRX:" + listaIds.getLength());
						ArrayList<String> listaTrxPermitidas = new ArrayList<String>();
						for(int idTrx=0;idTrx<listaIds.getLength();idTrx++){
							Node nodoIdMenu = listaIds.item(idTrx);
							String ltsrNodeName  = nodoIdMenu.getNodeName();
							String ltsrNodeValue = nodoIdMenu.getTextContent();
							if("TRX".equals(ltsrNodeName)){
								listaTrxPermitidas.add(ltsrNodeValue);
                                                        }
						}
						listaFacultades.put(lstrNombrePerfil, listaTrxPermitidas);
					}
				} 
			} catch (Exception e){
				showException(e);
			} 		
		}
		
		
	}
	
	/**
	 * Gets the instance of.
	 *
	 * @return the instance of
	 */
	public static Facultades getInstanceOf(){
		
		if(instance == null){
			instance = new Facultades();
		}
		
		return instance;
	}
	
	
	/**
	 * Trasaccion autorizada.
	 *
	 * @param pstrPerfil the pstr perfil
	 * @param pstrTransaccion the pstr transaccion
	 * @return true, if successful
	 */
	public boolean trasaccionAutorizada(String pstrPerfil, String pstrTransaccion){
		boolean bolFacultad = false;
		debug("Validando perfil[" + pstrPerfil + "] tenga permisos para la transaccion[" + pstrTransaccion + "]..");
		if(pstrPerfil == null  || "".equals(pstrPerfil)){
			 bolFacultad = true;
		}else{
			try{
				List<String> listaFacultadesPerfil = listaFacultades.get(pstrPerfil);
				for(String lstrFacultad:listaFacultadesPerfil){
					if(lstrFacultad.equals(pstrTransaccion)){
						bolFacultad = true;
						break;
					}
				}
			} catch(NullPointerException e){
				debug("ERROR: " + e.getMessage());
				debug("ERROR: " + e);
				debug("No fue posible obtener lista de facultades para perfil:" + pstrPerfil);
				bolFacultad = true;
			}
		}
		
		debug("El perfil[" + pstrPerfil + "] tiene permisos para ejecutar la transaccion[" + pstrTransaccion + "]:" + bolFacultad);
		
		return bolFacultad;
	}
	
}
