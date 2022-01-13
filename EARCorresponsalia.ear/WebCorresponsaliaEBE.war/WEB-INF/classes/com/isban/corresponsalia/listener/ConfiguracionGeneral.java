/**
 * ISBAN México - (c) Banco Santander Central Hispano
 * Todos los derechos reservados
 * ConfiguracionGeneral.java
 *
 * Control de versiones:
 *
 * Version	Date/Hour		By					Company		Description
 * ---------	---------------	-----------------	-----------	-------------
 * 1.0 		25-08-10 12:00 	David Aguilar G.	Stefanini 	Creación
 * 1.1 		26-10-10 12:22 	Ricardo Gutierrez.	Stefanini 	Lectura de archivo de backup 
 * 1.2       11-11-10 11:28 	Ricardo Gutierrez.	Stefanini 	Ajustes modo distribuido
 *
 */

package com.isban.corresponsalia.listener;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.isban.configuracion.Configuracion;
import com.isban.dataaccess.DataAccess;
import com.isban.dataaccess.channels.database.dto.RequestMessageDataBaseDTO;
import com.isban.dataaccess.channels.database.dto.ResponseMessageDataBaseDTO;
import com.isban.dataaccess.factories.jdbc.ConfigFactoryJDBC;
import com.isban.ebe.commons.architech.ArchitechEBELocal;
import com.isban.ebe.commons.beans.ConfiguracionBean;
import com.isban.ebe.commons.beans.ConfiguracionGlobalBean;
import com.isban.ebe.commons.beans.LoggingBean;
import com.isban.ebe.commons.exception.ConfiguracionException;
import com.isban.ebe.commons.exception.ConfiguracionGlobalException;
import com.isban.ebe.commons.exception.ExceptionDataAccess;
import com.isban.logger.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfiguracionGeneral.
 */
public class ConfiguracionGeneral extends ArchitechEBELocal {

	/** The Id app. */
	public static String idApp = "IdConfigApp";

	/** The Constant ARCHIVO_INIT. */
	public static final String ARCHIVO_INIT = "cmpConfiguracionRuta-config";

	/** The Constant ARCHIVO_LOCAL_INIT. */
	public static final String ARCHIVO_LOCAL_INIT = "ArchivoConfiguracionLocal";


	/** The Constant ATTR_REPLICABLE. */
	public static final String ATTR_REPLICABLE = "replicable";

	/** The Constant ID_EXTENSION. */
	public static final String ID_EXTENSION = ".";

	/** The Constant POSFIX. */
	public static final String POSFIX = "_dis";

	/** The Constant PREFIX. */
	public static final String PREFIX = "";

	/** The Constant CONSULTA_APLICACIONES. */
	public static final String CONSULTA_APLICACIONES = "Select ID_APLIC, IDENTIFICADOR From EBE_APLIC";

	/** The Constant CONSULTA_HOSTS. */
	public static final String CONSULTA_HOSTS = "Select ID_HOST, DESC_HOST From EBE_HOST";

	/** The Constant CONSULTA_PARAMETROS_POR_APL_HOST_TEMP. */
	public final static String CONSULTA_PARAMETROS_POR_APL_HOST_TEMP = "\n"
			+ "SELECT KE.REPLICABLE, KE.NOMBRE, KE.ID_TEMPLATE, KE.ID_KEYTEMPLATE, KE.DEFAULT_VALUE AS VALORPARAM, NULL AS ID_VAL_TEMPLATE"
			+ "\n"
			+ "FROM ("
			+ "\n"
			+ "SELECT DISTINCT GG.NOMBRE, GG.ID_KEY_TEMPLATE, GG.DEFAULT_VALUE, GG.ID_KEY_TEMPLATE AS ID_KEYTEMPLATE, GG.ID_TEMPLATE, GG.REPLICABLE, DD.ID_APL_TEMPLATE"
			+ "\n"
			+ "FROM EBE_APLIC AA"
			+ "\n"
			+ "    JOIN EBE_APL_APP_HOST BB ON AA.ID_APLIC = BB.ID_APLIC AND AA.IDENTIFICADOR = '?'"
			+ "\n"
			+ "    JOIN EBE_HOST CC ON CC.ID_HOST = BB.ID_HOST AND CC.DESC_HOST = '?'"
			+ "\n"
			+ "    JOIN EBE_APL_TEMPLATE DD ON BB.ID_APL_APP_HOST = DD.ID_APL_APP_HOST"
			+ "\n"
			+ "    JOIN EBE_TEMPLATE EE ON EE.ID_TEMPLATE = DD.ID_TEMPLATE AND EE.NOMBRE = '?'"
			+ "\n"
			+ "    JOIN EBE_KEY_TEMPLATE GG ON EE.ID_TEMPLATE=GG.ID_TEMPLATE"
			+ "\n"
			+ "WHERE GG.ID_KEY_TEMPLATE NOT IN (SELECT EVT.ID_KEY_TEMPLATE FROM EBE_VAL_TEMPLATE EVT WHERE EVT.ID_APL_TEMPLATE=DD.ID_APL_TEMPLATE)"
			+ "\n"
			+ ") KE"
			+ "\n"
			+ "UNION"
			+ "\n"
			+ "SELECT KE.REPLICABLE, KE.NOMBRE, KE.ID_TEMPLATE, KE.ID_KEYTEMPLATE, DECODE(VAL.VALOR,NULL,KE.DEFAULT_VALUE,VAL.VALOR) AS VALORPARAM, VAL.ID_VAL_TEMPLATE"
			+ "\n"
			+ "FROM ("
			+ "\n"
			+ "SELECT DISTINCT GG.NOMBRE, GG.ID_KEY_TEMPLATE, GG.DEFAULT_VALUE, GG.ID_KEY_TEMPLATE AS ID_KEYTEMPLATE, GG.ID_TEMPLATE, GG.REPLICABLE"
			+ "\n"
			+ "FROM EBE_APLIC AA"
			+ "\n"
			+ "LEFT OUTER JOIN EBE_APL_APP_HOST BB ON AA.ID_APLIC = BB.ID_APLIC AND AA.IDENTIFICADOR = '?'"
			+ "\n"
			+ "LEFT OUTER JOIN EBE_HOST CC ON CC.ID_HOST = BB.ID_HOST AND CC.DESC_HOST = '?'"
			+ "\n"
			+ "LEFT OUTER JOIN EBE_APL_TEMPLATE DD ON BB.ID_APL_APP_HOST = DD.ID_APL_APP_HOST"
			+ "\n"
			+ "LEFT OUTER JOIN EBE_TEMPLATE EE ON EE.ID_TEMPLATE = DD.ID_TEMPLATE AND EE.NOMBRE = '?'"
			+ "\n"
			+ "LEFT OUTER JOIN EBE_VAL_TEMPLATE FF ON DD.ID_APL_TEMPLATE = FF.ID_APL_TEMPLATE"
			+ "\n"
			+ "LEFT OUTER JOIN EBE_KEY_TEMPLATE GG ON (EE.ID_TEMPLATE=GG.ID_TEMPLATE) OR (GG.ID_TEMPLATE IS NULL)"
			+ "\n"
			+ "      )KE,"
			+ "\n"
			+ "     ("
			+ "\n"
			+ "     SELECT FF.VALOR, FF.ID_KEY_TEMPLATE AS ID_VALTEMPLATE, FF.ID_VAL_TEMPLATE, FF.ID_APL_TEMPLATE"
			+ "\n"
			+ "            FROM EBE_APL_APP_HOST BB"
			+ "\n"
			+ "            JOIN EBE_APLIC AA ON BB.ID_APLIC = AA.ID_APLIC AND AA.IDENTIFICADOR = '?'"
			+ "\n"
			+ "            JOIN EBE_HOST CC ON BB.ID_HOST = CC.ID_HOST AND CC.DESC_HOST = '?'"
			+ "\n"
			+ "            JOIN EBE_APL_TEMPLATE DD  ON BB.ID_APL_APP_HOST = DD.ID_APL_APP_HOST"
			+ "\n"
			+ "            JOIN EBE_TEMPLATE EE ON  DD.ID_TEMPLATE = EE.ID_TEMPLATE AND EE.NOMBRE = '?'"
			+ "\n"
			+ "            JOIN EBE_KEY_TEMPLATE GG ON ((GG.ID_TEMPLATE IS NULL) OR (EE.ID_TEMPLATE=GG.ID_TEMPLATE))"
			+ "\n"
			+ "            JOIN EBE_VAL_TEMPLATE FF ON DD.ID_APL_TEMPLATE = FF.ID_APL_TEMPLATE AND GG.ID_KEY_TEMPLATE = FF.ID_KEY_TEMPLATE"
			+ "\n"
			+ "                                                                              ) VAL"
			+ "\n" + "WHERE KE.ID_KEYTEMPLATE(+) = VAL.ID_VALTEMPLATE";

	/** The Constant BLANK_STRING. */
	private static final String BLANK_STRING = "";

	/** The Constant CLASE. */
	transient private static final String CLASE = "ConfiguracionGeneral";

	/** The archivos configuraciones dist. */
	transient private final List<String> archivosConfiguracionesDist = new ArrayList<String>();

	/** The aplicaciones configuradas. */
	transient private final List<String> aplicacionesConfiguradas = new ArrayList<String>();

	/** The instance. */
	private ConfiguracionGlobalBean instance = null;

	/**
	 * Instantiates a new configuracion general.
	 */
	public ConfiguracionGeneral() {
		instance = ConfiguracionGlobalBean.getInstance();
		final LoggingBean logbean = new LoggingBean();
		setLoggingBean(logbean);
		getLoggingBean().setUsuario("Sistema");
		try {
			getLoggingBean().setIp(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			Log.showException(e);
		}
	}

	/**
	 * Gets the single instance of ConfiguracionGeneral.
	 *
	 * @return single instance of ConfiguracionGeneral
	 */
	public ConfiguracionGlobalBean getInstance() {
		return instance;
	}

	/**
	 * Sets the instance.
	 *
	 * @param instance the new instance
	 */
	public void setInstance(ConfiguracionGlobalBean instance) {
		this.instance = instance;
	}

	/**
	 * Inits the.
	 *
	 * @param pstrArchivoCLA the pstr archivo cla
	 * @param pstrIdApp the pstr id app
	 * @throws ConfiguracionGlobalException the configuracion global exception
	 * @throws SAXException the sAX exception
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void init(String pstrArchivoCLA, String pstrIdApp)
			throws ConfiguracionGlobalException, SAXException,
			ParserConfigurationException, IOException {

		getLoggingBean().setIdAplicacion(pstrIdApp);
		info("Iniciando configuracion global");
		debug("Archivo CLA con las configuraciones:" + pstrArchivoCLA);

		final StringBuffer lstrContenidoArchivoCLA = new StringBuffer();
		String[] lstrArchivosConfiguracion = null;
		final File lflFileConfigs = new File(pstrArchivoCLA);
		String lstrArchivoCargar = "";

		if (!lflFileConfigs.exists()) {
			error("El archivo .cla con las configuraciones no existe");
			throw new ConfiguracionGlobalException("CONF99991", "El archivo "
					+ pstrArchivoCLA + " con las configuraciones no existe",
					this.getClass().getName());
		}
		final DataInputStream disConfig = new DataInputStream(
				new FileInputStream(lflFileConfigs));
		int iChar = 0;
		while ((iChar = disConfig.read()) != -1) {
			lstrContenidoArchivoCLA.append((char) iChar);
		}

		debug("Contenido del Archivo CLA:" + lstrContenidoArchivoCLA);

		if (BLANK_STRING.equals(lstrContenidoArchivoCLA)) {
			throw new ConfiguracionGlobalException(
					"CONF99993",
					"El archivo CLA no contiene ninguna referencia a archivos de configuracion",
					this.getClass().getName());
		}

		// se inicializa el mapa
		instance.mapaConfiguraciones = new HashMap<String, ConfiguracionBean>();

		final String lstrContenidoArchCLA = lstrContenidoArchivoCLA.toString()
				.replaceAll("\n", BLANK_STRING);
		lstrArchivosConfiguracion = lstrContenidoArchCLA.split(",");

		for (String archivoCfg : lstrArchivosConfiguracion) {
			lstrArchivoCargar = /*
								 * lflFileConfigs.getParent() +
								 * File.separatorChar +
								 */archivoCfg.trim();
			debug("Archivo de configuracion a cargar:" + lstrArchivoCargar);
			if (!"".equals(lstrArchivoCargar)) {
				if (!primeraCarga(lstrArchivoCargar, false)) {
					debug("No fue posible realizar la carga del archivo...");
				}
			} else {
				debug("Archivo invalido para realizar carga...");
			}

		}

		instance.setListaAplicaciones(aplicacionesConfiguradas);
		cargaComponentesLocales(pstrIdApp);
	}
	
	/**
	 * Carga componentes locales.
	 *
	 * @param pstrIdApp the pstr id app
	 */
	@SuppressWarnings("unchecked")
	private void cargaComponentesLocales(String pstrIdApp){
		info("Inicia carga de componentes locales...");

		final Configuracion config = Configuracion.getInstance();
		try {
			config.init(pstrIdApp);
			for (int initComp = 0; initComp < config.getListInitComponentes()
					.size(); initComp++) {
				final String lstrClassInit = config.getListInitComponentes()
						.get(initComp);
				try {
					debug("Inicializando[" + lstrClassInit
							+ "]...");
					final Class classFactory = Class.forName(lstrClassInit);
					Method meth= null;
					try {
						meth = classFactory
								.getMethod("init", (Class[]) null);
					} catch (SecurityException e) {
						showException(e);
					} catch (NoSuchMethodException e) {
						showException(e);
					}
					try {
						meth.invoke(null,(Object[])null);
					} catch (IllegalArgumentException e) {
						showException(e);					} catch (IllegalAccessException e) {
							showException(e);					} catch (InvocationTargetException e) {
								showException(e);					}
				} catch (ClassNotFoundException e) {
					showException(e);
				}

			}
			debug("Carga de componentes locales exitosa...");

		} catch (ConfiguracionException e) {
			showException(e);
		}
	}

	/**
	 * Primera carga.
	 *
	 * @param pstrArchivoCargar the pstr archivo cargar
	 * @param initDistribuida the init distribuida
	 * @return true, if successful
	 * @throws ConfiguracionGlobalException the configuracion global exception
	 * @throws SAXException the sAX exception
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private boolean primeraCarga(String pstrArchivoCargar,
			boolean initDistribuida) throws ConfiguracionGlobalException,
			SAXException, ParserConfigurationException, IOException {

		debug("Inicia primera carga del archivo:" + pstrArchivoCargar);

		int max = 0;
		final String cmpConfig = "cmpConfiguracion";
		final String cmpIsban = "IsbanDataAccess";
		final String activo = "activo";
		final String id = "id";
		final String className = "class";
		final String initComp = "init";
		final String si = "true";
		StringBuffer sbActivo = new StringBuffer();
		StringBuffer sbID = new StringBuffer();
		Document doc = null;
		boolean resp = false;
		Node nodoRaiz = null;
		Node unAtributo = null;
		Node unID = null;
		NodeList listaNodosHijos = null;
		Node nodo = null;
		NamedNodeMap atributos = null;
		final HashMap<String, String> mapaConfiguracion = new HashMap<String, String>();
		final ArrayList<String> listClassInitComponentes = new ArrayList<String>();
		final ArrayList<String> listComponentes = new ArrayList<String>(10);

		// Se guarda el elemento en el mapa
		setParametro(ARCHIVO_INIT, BLANK_STRING, pstrArchivoCargar,
				mapaConfiguracion);

		// Se obtiene el document del xml para cargar la lista
		doc = getDocument(mapaConfiguracion);

		if (doc != null) {
				nodoRaiz = doc.getFirstChild();
				listaNodosHijos = nodoRaiz.getChildNodes();
				max = listaNodosHijos.getLength();
				// Se carga la lista
				for (int i = 0; i < max; i++) {
					nodo = listaNodosHijos.item(i);
					atributos = nodo.getAttributes();
					if (atributos != null) {
						// Obtiene el atributo activo
						unAtributo = atributos.getNamedItem(activo);
						if (unAtributo != null) {
							sbActivo = new StringBuffer(unAtributo
									.getNodeValue());
							if (sbActivo != null
									&& si.equals(sbActivo.toString())) {
								// obtiene el atributo del id
								unID = atributos.getNamedItem(id);
								if (unID != null) {
									sbID = new StringBuffer(unID.getNodeValue());
									if (sbID != null
											&& sbID.length() > 0) {
										// Si activo=true, se carga el
										// componente
										listComponentes.add(nodo.getNodeName());
										setParametro(nodo.getNodeName(),
												BLANK_STRING, sbID.toString(),
												mapaConfiguracion);

										final String lstrInit = atributos
												.getNamedItem(initComp) != null ? atributos
												.getNamedItem(initComp)
												.getNodeValue()
												: BLANK_STRING;
										if (si.equals(lstrInit)) {
											final String lstrClass = atributos
													.getNamedItem(className) != null ? atributos
													.getNamedItem(className)
													.getNodeValue()
													: BLANK_STRING;
											if (!BLANK_STRING.equals(lstrClass)){
												listClassInitComponentes
														.add(lstrClass);
											}
										}
									}
								}
							}
						}
					}
				}

				/**
				 * Se carga el componente de configuracion por default
				 */
				cargaLocal(cmpConfig, mapaConfiguracion);
				cargaLocal(cmpIsban, mapaConfiguracion);

				final String idCmpConfig = getId(cmpConfig, mapaConfiguracion);

				debug("Mapa obtenido       :" + mapaConfiguracion);
				debug("idCmpConfig a cargar:" + idCmpConfig);

				if (idCmpConfig == null || "".equals(idCmpConfig)){
					return resp;
				}

				final String idConfig = getParametro(idCmpConfig, idApp,
						mapaConfiguracion);
				final String lstrModo = getParametro(idCmpConfig, "modo",
						mapaConfiguracion);
				aplicacionesConfiguradas.add(idConfig);
				debug("Id configuracion  :" + idConfig);
				debug("Modo configuracion:" + lstrModo);

				if ("local".equals(lstrModo)) {
					debug("Cargando la configuracion local...");
					debug("Inicia carga de componentes de la configuracion...");
					final Iterator<String> it = listComponentes.iterator();
					debug("Numero de componentes a cargar: "
							+ listComponentes.size());
					final int size = listComponentes.size();
					for (int i = 0; i < size; i++) {
						final StringBuffer sbComp = new StringBuffer(it.next());
						debug("Componentes a cargar: " + sbComp.toString());

						/*
						 * if(!sbComp.toString().equals(cmpConfig) &&
						 * !sbComp.toString().equals(cmpIsban)){
						 * if(!cargaInicial(sbComp.toString(),
						 * mapaConfiguracion)){
						 * debug("No se pudo realizar la carga del componente..."
						 * ); } } else
						 * debug("El componente ya fue previamente cargado");
						 */
						if (!cargaInicial(sbComp.toString(), mapaConfiguracion)) {
							debug("No se pudo realizar la carga del componente...");
						}

					}

					debug("Agregando bean de configuracion");
					final ConfiguracionBean bean = new ConfiguracionBean();
					bean.setListComponentes(listComponentes);
					bean.setListClassInitComponentes(listClassInitComponentes);
					bean.setMapaConfiguracion(mapaConfiguracion);
					instance.mapaConfiguraciones.put(idConfig, bean);
				} else if (initDistribuida) {
					debug("Cargando la configuracion distribuida...");
					debug("Inicia carga de componentes de la configuracion...");
					final Iterator<String> it = listComponentes.iterator();
					debug("Numero de componentes a cargar: "
							+ listComponentes.size());
					final int size = listComponentes.size();
					boolean cargaOK = true;
					for (int i = 0; i < size; i++) {
						final StringBuffer sbComp = new StringBuffer(it.next());
						debug("Componentes a cargar: " + sbComp.toString());
						/*
						 * if(!sbComp.toString().equals(cmpConfig) &&
						 * !sbComp.toString().equals(cmpIsban)){ cargaOK =
						 * cargaInicial(sbComp.toString(), mapaConfiguracion);
						 * if(!cargaOK) break; } else
						 * debug("El componente ya fue previamente cargado");
						 */
						cargaOK = cargaInicial(sbComp.toString(),
								mapaConfiguracion);
						if (!cargaOK){
							i=size; //condicion de salida
						}

					}
					debug("Carga exitosa:" + cargaOK);
					if (cargaOK) {
						debug("Agregando bean de configuracion");
						final ConfiguracionBean bean = new ConfiguracionBean();
						bean.setListComponentes(listComponentes);
						bean
								.setListClassInitComponentes(listClassInitComponentes);
						bean.setMapaConfiguracion(mapaConfiguracion);
						instance.mapaConfiguraciones.put(idConfig, bean);
						debug("Guardando archivo contingencia...");

						final String lstrArchivoBackup = pstrArchivoCargar
								.replaceAll(".xml", "_dis.xml");
						debug("Archivo Backup:" + lstrArchivoBackup);

						if (creaArchivoContingencia(bean, lstrArchivoBackup)){
							info("Archivo de contingencia creado exitosamente");
						}
						else{
							info("No fue posible crear sel archivo de contingencia");
							}
					} else {
						debug("No se pudo realizar la carga por tal motivo se utiliza la contingencia...");
						debug("Obteniendo configuracion de archivo contingencia...");
						final String lstrArchivoBackup = pstrArchivoCargar
								.replaceAll(".xml", "_dis.xml");
						debug("Archivo Contingencia:" + lstrArchivoBackup);
						if (new File(lstrArchivoBackup).exists()) {
							final ConfiguracionBean bean = (ConfiguracionBean) leeArchivoContingencia(lstrArchivoBackup);
							instance.mapaConfiguraciones.put(idConfig, bean);
						} else{
							debug("El archivo de contingencia no existe...");
						}
					}

				} else {
					archivosConfiguracionesDist.add(pstrArchivoCargar);
				}
				resp = true;
		}
		debug("Termina primera carga...");
		return resp;
	}

	/**
	 * Sets the parametro.
	 *
	 * @param id the id
	 * @param llave the llave
	 * @param valor the valor
	 * @param mapaConfiguracion the mapa configuracion
	 */
	private void setParametro(String id, String llave, String valor,
			Map<String, String> mapaConfiguracion) {
		final StringBuffer sbKey = new StringBuffer();
		debug("setParametro[id : " + id + ", llave: " + llave + ", valor: "
				+ valor + "]");
		mapaConfiguracion.put(sbKey.append(id).append(llave).toString(), valor);
	}

	/**
	 * Gets the parametro.
	 *
	 * @param id the id
	 * @param llave the llave
	 * @param mapaConfiguracion the mapa configuracion
	 * @return the parametro
	 */
	private String getParametro(String id, String llave,
			Map<String, String> mapaConfiguracion) {
		final StringBuffer dato = new StringBuffer();
		final StringBuffer valor = new StringBuffer();
		valor.append(mapaConfiguracion.get(dato.append(id).append(llave)
				.toString()));
		debug("getParametro[id: " + id + ", llave: " + llave + "]:"
				+ valor.toString());
		return valor.toString();
	}

	/**
	 * Gets the id.
	 *
	 * @param componente the componente
	 * @param mapaConfiguracion the mapa configuracion
	 * @return the id
	 */
	private String getId(String componente,
			Map<String, String> mapaConfiguracion) {
		final StringBuffer valor = new StringBuffer();
		valor.append(mapaConfiguracion.get(componente));
		debug("getId[" + componente + "]:" + valor.toString());
		return valor.toString();
	}

	/**
	 * Gets the document.
	 *
	 * @param mapaConfiguracion the mapa configuracion
	 * @return the document
	 * @throws ConfiguracionGlobalException the configuracion global exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the sAX exception
	 */
	private Document getDocument(Map<String, String> mapaConfiguracion)
			throws ConfiguracionGlobalException, IOException,
			ParserConfigurationException, SAXException {

		debug("Inicia obtencion de documento");

		File file = null;
		Document doc = null;

		/* Se obtiene el archivo de carga */
		file = new File(getParametro(ARCHIVO_INIT, BLANK_STRING,
				mapaConfiguracion));
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		final DocumentBuilder db = dbf.newDocumentBuilder();
		doc = db.parse(file);
		doc.getDocumentElement().normalize();
		debug("Finaliza obtencion de documento[" + doc + "]");
		return doc;
	}


	/**
	 * Carga inicial.
	 *
	 * @param componente the componente
	 * @param mapa the mapa
	 * @return true, if successful
	 */
	private boolean cargaInicial(String componente, Map<String, String> mapa) {

		debug("Inicia cargaInicial del componente:" + componente);

		final String cmpConfig = "cmpConfiguracion";
		final String modo = "modo";
		final String distribuido = "distribuido";
		String idConfig = null;
		final StringBuffer sbModo = new StringBuffer();
		boolean resp = true;

		try {
			idConfig = getParametro(cmpConfig, BLANK_STRING, mapa);
			sbModo.append(getParametro(idConfig, modo, mapa));
			debug("Modo: " + sbModo.toString());
			debug("Carga Local del componente...");
			cargaLocal(componente, mapa);
			debug("Mapa Local del componente:" + mapa);
			if (distribuido.equals(sbModo.toString())) {
				debug("Actualizando carga del componente debido a que el modo es distribuido...");
				cargaDistribuida(componente, mapa);
				debug("Mapa actualizado distribudamente del componente:" + mapa);
			}
		} catch (IOException e) {
			showException(e);
			resp = false;
		} catch (ConfiguracionGlobalException e) {
			showException(e);
			resp = false;
		}

		debug("Termina cargaInicial del componente:" + componente);

		return resp;
	}

	/**
	 * Carga local.
	 *
	 * @param componente the componente
	 * @param mapa the mapa
	 * @throws ConfiguracionGlobalException the configuracion global exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void cargaLocal(String componente, Map<String, String> mapa)
			throws ConfiguracionGlobalException, IOException {
		int max = 0;
		final StringBuffer codigo = new StringBuffer();
		final StringBuffer mensaje = new StringBuffer();
		final StringBuffer sbId = new StringBuffer();
		
		Document doc = null;
		Node lan = null;
		NodeList nodeLst = null;
		NodeList lis = null;
		Element elemento = null;
		debug("Inicia ConfiguracionGeneral.cargaLocal, entrada componente: "
				+ componente);
		try {
			// obtiene el id del componente
			sbId.append(getId(componente, mapa));
			// se obtiene el document del archivo xml
			doc = getDocument(mapa);
			// obtención de nodos del componente
			nodeLst = doc.getElementsByTagName(componente);
			elemento = (Element) nodeLst.item(0);
			lis = elemento.getChildNodes();
			max = lis.getLength();
			// Se cargan todos los elementos del componente
			for (int i = 0; i < max; i++) {
				lan = lis.item(i);
				if (lan.getFirstChild() != null) {
					setParametro(sbId.toString(), lan.getNodeName().toString(),
							lan.getFirstChild().getNodeValue(), mapa);
				}
			}
		} catch (ParserConfigurationException e) {
			debug("No se encuentran la definición de parámetros en archivo_confgi.xml:"
					+ e.getMessage());
			final ConfiguracionGlobalException exceptionGlobal = new ConfiguracionGlobalException(
					codigo.append("Conf004").toString(),
					mensaje
							.append(
									"No se encuentran la definición de parámetros en archivo_confgi.xml")
							.toString(), CLASE);
			exceptionGlobal.setStackTrace(e.getStackTrace());
			throw exceptionGlobal;
		}

		catch (SAXException e) {
			debug("No se encuentran la definición de parámetros en archivo_confgi.xml:"
					+ e.getMessage());
			final ConfiguracionGlobalException exceptionGlobal = new ConfiguracionGlobalException(
					codigo.append("Conf004").toString(),
					mensaje
							.append(
									"No se encuentran la definición de parámetros en archivo_confgi.xml")
							.toString(), CLASE);
			exceptionGlobal.setStackTrace(e.getStackTrace());
			throw exceptionGlobal;
		}

		debug("Termina ConfiguracionGeneral.cargaLocal");
	}

	/**
	 * Carga distribuida.
	 *
	 * @param componente the componente
	 * @param mapa the mapa
	 * @throws ConfiguracionGlobalException the configuracion global exception
	 */
	private void cargaDistribuida(String componente, Map<String, String> mapa)
			throws ConfiguracionGlobalException {
		final String cmpConfig = "cmpConfiguracion";
		final String servidor = "servidor";

		DataAccess dataAccess = null;
		final RequestMessageDataBaseDTO requestMessageDataBaseDTO = new RequestMessageDataBaseDTO();
		final StringBuilder query = new StringBuilder(
				CONSULTA_PARAMETROS_POR_APL_HOST_TEMP);
		final String idCmpConfig = getId(cmpConfig, mapa);
		final String idComponente = getId(componente, mapa);

		query.replace(query.indexOf("?"), query.indexOf("?") + 1, getParametro(
				idCmpConfig, idApp, mapa));
		query.replace(query.indexOf("?"), query.indexOf("?") + 1, getParametro(
				idCmpConfig, servidor, mapa));
		query.replace(query.indexOf("?"), query.indexOf("?") + 1, componente);
		query.replace(query.indexOf("?"), query.indexOf("?") + 1, getParametro(
				idCmpConfig, idApp, mapa));
		query.replace(query.indexOf("?"), query.indexOf("?") + 1, getParametro(
				idCmpConfig, servidor, mapa));
		query.replace(query.indexOf("?"), query.indexOf("?") + 1, componente);
		query.replace(query.indexOf("?"), query.indexOf("?") + 1, getParametro(
				idCmpConfig, idApp, mapa));
		query.replace(query.indexOf("?"), query.indexOf("?") + 1, getParametro(
				idCmpConfig, servidor, mapa));
		query.replace(query.indexOf("?"), query.indexOf("?") + 1, componente);

		requestMessageDataBaseDTO.setCodeOperation("CONSPARAM009");
		requestMessageDataBaseDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY);
		requestMessageDataBaseDTO.setQuery(query.toString());

		try {
			dataAccess = DataAccess.getInstance(requestMessageDataBaseDTO,
					getLoggingBean());
			final String nombre = "NOMBRE";
			final ResponseMessageDataBaseDTO responseMessageDataBaseDTO = (ResponseMessageDataBaseDTO) dataAccess
					.execute(null);
			final List<HashMap<String, Object>> resultQuery = responseMessageDataBaseDTO
					.getResultQuery();
			if (resultQuery != null && !resultQuery.isEmpty()) {
				final StringBuilder sb = new StringBuilder(BLANK_STRING);
				final Iterator<HashMap<String, Object>> itResultQuery = resultQuery
						.iterator();
				while (itResultQuery.hasNext()) {
					final HashMap<String, Object> registro = itResultQuery
							.next();
					setParametro(idComponente, (String) registro.get(nombre),
							(String) registro.get("VALORPARAM"), mapa);
					setParametro(
							ATTR_REPLICABLE,
							sb.append(idComponente).append(
									(String) registro.get(nombre)).toString(),
							("1".equals((String) registro.get("REPLICABLE"))) ? "true"
									: "false", mapa);
					sb.delete(0, sb.length());
				}
			} else {
				debug("No se encontro informacion en la BD del componente");
			}

		} catch (ExceptionDataAccess e) {
			showException(e);
			final ConfiguracionGlobalException exceptionGlobal = new ConfiguracionGlobalException(
					"DLE0001", "ERROR AL CARGAR CONFIGURACIÓN INICIAL.", this
							.getClass().toString());
			exceptionGlobal.setStackTrace(e.getStackTrace());
			throw exceptionGlobal;
		}
	}

	/**
	 * Gets the child node info.
	 *
	 * @param child the child
	 * @param idsParentsNodes the ids parents nodes
	 * @param parentsNodes the parents nodes
	 * @param parentsNodeToExclude the parents node to exclude
	 * @return the child node info
	 */
	public String[] getChildNodeInfo(String child,
			Map<String, String> idsParentsNodes, List<String> parentsNodes,
			List<String> parentsNodeToExclude) {
		StringBuilder sb = null;
		if (!parentsNodes.contains(child)) {
			sb = new StringBuilder("");
			final Iterator<String> itParentsNodes = parentsNodes.iterator();
			while (itParentsNodes.hasNext()) {
				final String parentNode = itParentsNodes.next();
				if (!parentsNodeToExclude.contains(parentNode)) {
					if (child.startsWith(idsParentsNodes.get(parentNode))) {
						sb.append(ATTR_REPLICABLE).append(child);
						return new String[] {
								parentNode,
								child.substring(idsParentsNodes.get(parentNode)
										.length()),
								idsParentsNodes.get(sb.toString()) };
					}
					sb.delete(0, sb.length());
				}
			}
		}
		return new String[0];
	}

	/**
	 * Adds the child to node.
	 *
	 * @param doc the doc
	 * @param parentNode the parent node
	 * @param childName the child name
	 * @param replicable the replicable
	 * @param childValue the child value
	 */
	public void addChildToNode(Document doc, String parentNode,
			String childName, String replicable, String childValue) {
		final Node pNode = doc.getElementsByTagName(parentNode).item(0);
		final Element childNode = doc.createElement(childName);
		childNode.setAttribute(ATTR_REPLICABLE, replicable);
		childNode.appendChild(doc.createTextNode(childValue));
		pNode.appendChild(childNode);
	}

	/**
	 * Crea archivo contingencia.
	 *
	 * @param pObject the object
	 * @param ArchivoContingencia the archivo contingencia
	 * @return true, if successful
	 */
	private static boolean creaArchivoContingencia(Object pObject,
			String ArchivoContingencia) {
		boolean archivoCreado = false;
		try {
			final ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(ArchivoContingencia));
			oos.writeObject(pObject);
			oos.close();
			archivoCreado = true;
		} catch (IOException e) {
			Log.showException(e);
		}
		return archivoCreado;
	}

	/**
	 * Lee archivo contingencia.
	 *
	 * @param ArchivoContingencia the archivo contingencia
	 * @return the object
	 */
	private static Object leeArchivoContingencia(String ArchivoContingencia) {
		Object lobj = null;
		try {
			final ObjectInputStream loisSerializa = new ObjectInputStream(
					new FileInputStream(ArchivoContingencia));
			lobj = loisSerializa.readObject();
			loisSerializa.close();
		} catch (ClassNotFoundException e) {
			Log.showException(e);
		} catch (IOException e) {
			Log.showException(e);
		}
		return lobj;
	}

	/*
	 * public static void main(String args[]){ ConfiguracionGeneral confG = new
	 * ConfiguracionGeneral(); try {
	 * confG.init("d:/arquitecturaEBE/DistTequila/Configuracion/Configuracion.cla"
	 * , "AplicacionBaseEBE"); } catch (Exception e) { e.printStackTrace(); } }
	 */

}
