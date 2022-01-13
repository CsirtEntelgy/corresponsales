	<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt"%>
	<spring:message code="menu.menuItemOne"    			var="principal"/>
	<spring:message code="menu.menuItemTwo"    			var="operaciones"/>
	<spring:message code="menu.menuItemThree"  			var="plantillas"/>
	<spring:message code="menu.menuItemFour"  			var="isbandataaccess"/>
	<spring:message code="menu.ItemThreeSubmenuOne"   	var="listaComponentes"/>
	<spring:message code="menu.ItemThreeSubmenuTwo"   	var="plantilla1"/>
	<spring:message code="menu.ItemThreeSubmenuTree"  	var="plantilla2"/>
	<spring:message code="menu.ItemThreeSubmenuFour"  	var="plantilla3"/>
	<spring:message code="menu.ItemThreeSubmenuFive"  	var="plantilla4"/>
	<spring:message code="menu.ItemFourSubmenuOne"   	var="canalCICS"/>
	<spring:message code="menu.ItemFourSubmenuTwo"   	var="canalDataBase"/>
	<spring:message code="menu.ItemFourSubmenuTree"  	var="canalTuxedo"/>
	<spring:message code="menu.ItemFourSubmenuFour"  	var="canalGenericMQ"/>

	<spring:message code="menu.canalcorresponsalia"				var="canalcorresponsalia"/>
	<spring:message code="menu.canalcorresponsalia.operaciones"	var="operacionescc"/>
	<spring:message code="menu.corresponsales"					var="corresponsales"/>
	<spring:message code="menu.corresponsales.mantenimiento"	var="mantenimiento"/>
	<spring:message code="menu.corresponsales.operaciones"		var="operacionesc"/>
	<spring:message code="menu.corresponsales.comisiones"		var="comisiones"/>
	<spring:message code="menu.corresponsales.altamasivac"		var="altamasivac"/>
	<spring:message code="menu.corresponsales.sucursales"		var="sucursales"/>
	<spring:message code="menu.corresponsales.excepcionoper"	var="excepcionoper"/>
	<spring:message code="menu.corresponsales.altamasivas"		var="altamasivas"/>
	<spring:message code="menu.corresponsales.contactos"		var="contactos"/>
	<spring:message code="menu.corresponsales.paramasiva"	    var="paramasiva"/>
	<spring:message code="menu.monitoreo"						var="monitoreo"/>
	<spring:message code="menu.monitoreo.operaciones"			var="operacionesm"/>
	<spring:message code="menu.monitoreo.lineacredito"			var="lineacredito"/>
	<spring:message code="menu.monitoreo.sucursalescorresponsal"			var="sucursalescorresponsal"/>
	
	<spring:message code="menu.consultas"						var="consultas"/>
	<spring:message code="menu.consultas.compensacion"			var="compensacion"/>
	<spring:message code="menu.consultas.bitacora"				var="bitacora"/>
	<spring:message code="menu.consultas.logtrx"                var="logtrx"/>
	<spring:message code="menu.consultas.lotesPorCompensar"     var="lotesPorCompensar"/>
	<spring:message code="menu.consultas.conciliacionManual"     var="conciliacionManual"/>
	<spring:message code="menu.consultas.estatusOperacion"			var="estatusOperacion"/>
	
	<spring:message code="menu.catalogos"						var="catalogos"/>
	<spring:message code="menu.catalogos.aplicaciones"			var="catalogoAplicaciones"/>
	<spring:message code="menu.catalogos.codigosOperacion"		var="catalogoCodigoOperacion"/>
	
	<!-- <body onload="initialize('${param.menuItem}', '${param.menuSubitem}'); addMenuItem('eight','Mi opcion dinamica','','', 'true', 'true'); disabledMenuItem('three'); disabledMenuItem('fiveDotTwo');"> -->	
	<div id="top04">
		<c:if test="${LyFBean.menuHabilitado}">
		<form id= "MenuPrincipal" name = "MenuPrincipal" method="post">
		  <input type="hidden" name="SES" value="${SES}"/>
			<div class="frameMenuContainer">
				<ul id="mainMenu">
					<!-- 
					<li id="principal"      class="startMenuGroup">             <a href="../principal/inicio.do?login=no">       <span>${principal}</span></a></li>
					<li id="operaciones"    class="startMenuGroup">             <a href="../operaciones/consultaContactos.do">   <span>${operaciones}</span></a></li>
					<li id="plantillas"  class="withSubMenus startMenuGroup"><a href="javascript:selectMenuItem('plantillas')"><span>${plantillas}</span></a>
						<ul>
							<li id="listaComponentes"><a href="../templetes/listaComponentes.do">&gt;<span class="subMenuText">${listaComponentes}</span></a></li>
							<li id="plantilla1">      <a href="../templetes/plantilla1.do">      &gt;<span class="subMenuText">${plantilla1}</span></a></li>
							<li id="plantilla2">      <a href="../templetes/plantilla2.do">      &gt;<span class="subMenuText">${plantilla2}</span></a></li>
							<li id="plantilla3">      <a href="../templetes/plantilla3.do">      &gt;<span class="subMenuText">${plantilla3}</span></a></li>
							<li id="plantilla4">      <a href="../templetes/plantilla4.do">      &gt;<span class="subMenuText">${plantilla4}</span></a></li>
						</ul>
					</li>
					 -->
					<li id="principal"      class="startMenuGroup">             <a href="javaScript:document.getElementById('MenuPrincipal').action='../principal/inicio.do';document.getElementById('MenuPrincipal').submit()">       <span>${principal}</span></a></li>					 
					<li id="canalcorresponsalia" class="withSubMenus startMenuGroup"><a href="javascript:selectMenuItem('canalcorresponsalia')"><span>${canalcorresponsalia}</span></a>
						<ul>
							<li id="operacionescc">     <a href="javaScript:document.getElementById('MenuPrincipal').action='../canalcorresponsalia/ConsultaOperaciones.do?ent=0014&can=14&nivPar=01';MenuPrincipal.submit()">&gt;<span class="subMenuText">${operacionescc}</span></a></li>
						</ul>
					</li>
					<li id="corresponsales" class="withSubMenus startMenuGroup"><a href="javascript:selectMenuItem('corresponsales')"><span>${corresponsales}</span></a>
						<ul>
							<li id="mantenimiento">     <a href="javaScript:document.getElementById('MenuPrincipal').action='../corresponsales/ConsultaMttoCorresponsales.do';document.getElementById('MenuPrincipal').submit()">&gt;<span class="subMenuText">${mantenimiento}</span></a></li>
							<li id="operacionesc">     <a href="javaScript:document.getElementById('MenuPrincipal').action='../corresponsales/ConsultaOperacionesCorresponsal.do';document.getElementById('MenuPrincipal').submit()">&gt;<span class="subMenuText">${operacionesc}</span></a></li>
							<li id="comisiones">     <a href="javaScript:document.getElementById('MenuPrincipal').action='../corresponsales/MuestraCorresponsalesComision.do';document.getElementById('MenuPrincipal').submit()">&gt;<span class="subMenuText">${comisiones}</span></a></li>
							<li id="altamasivac">     <a href="javaScript:document.getElementById('MenuPrincipal').action='../corresponsales/AltaMasivaCorresponsales.do';document.getElementById('MenuPrincipal').submit()">&gt;<span class="subMenuText">${altamasivac}</span></a></li>
							<li id="sucursales">     <a href="javaScript:document.getElementById('MenuPrincipal').action='../corresponsales/MuestraCorresponsalesSucursales.do';document.getElementById('MenuPrincipal').submit()">&gt;<span class="subMenuText">${sucursales}</span></a></li>
							<li id="excepcionoper">     <a href="javaScript:document.getElementById('MenuPrincipal').action='../corresponsales/ExcepcionOperacionesCorresponsal.do';document.getElementById('MenuPrincipal').submit()">&gt;<span class="subMenuText">${excepcionoper}</span></a></li>
							<li id="altamasivas">     <a href="javaScript:document.getElementById('MenuPrincipal').action='../corresponsales/AltaMasivaSucursales.do';document.getElementById('MenuPrincipal').submit()">&gt;<span class="subMenuText">${altamasivas}</span></a></li>
							<li id="contactos">       <a href="javaScript:document.getElementById('MenuPrincipal').action='../corresponsales/ConsultaContacto.do';document.getElementById('MenuPrincipal').submit()">&gt;<span class="subMenuText">${contactos}</span></a></li>
							<li id="paramasiva">       <a href="javaScript:document.getElementById('MenuPrincipal').action='../corresponsales/ParaMasiva.do';document.getElementById('MenuPrincipal').submit()">&gt;<span class="subMenuText">${paramasiva}</span></a></li>
							<li id="conciliacionManual">     <a href="javaScript:document.getElementById('MenuPrincipal').action='../consultas/ConciliacionManual.do';document.getElementById('MenuPrincipal').submit()"><fmt:message key="general.mayorQue" /><span class="subMenuText">${conciliacionManual}</span></a></li>
						</ul>
					</li>					 
					<li id="monitoreo" class="withSubMenus startMenuGroup"><a href="javascript:selectMenuItem('monitoreo')"><span>${monitoreo}</span></a>
						<ul>
							<li id="operacionesm">     <a href="javaScript:document.getElementById('MenuPrincipal').action='../monitoreo/MonitoreoOperaciones.do';document.getElementById('MenuPrincipal').submit()">&gt;<span class="subMenuText">${operacionesm}</span></a></li>
							<li id="lineacredito">     <a href="javaScript:document.getElementById('MenuPrincipal').action='../monitoreo/MonitoreoCredito.do';document.getElementById('MenuPrincipal').submit()">&gt;<span class="subMenuText">${lineacredito}</span></a></li>
						<!-- 
							<li id="mcomisiones">     <a href="javaScript: MenuPrincipal.action='../monitoreo/MonitoreoComisiones.do';MenuPrincipal.submit()">&gt;<span class="subMenuText">${mcomisiones}</span></a></li>
						-->
						</ul>
					</li>
					<li id="consultas" class="withSubMenus startMenuGroup"><a href="javascript:selectMenuItem('consultas')"><span>${consultas}</span></a>
						<ul>
							<li id="compensacion"> <a href="javaScript:document.getElementById('MenuPrincipal').action='../consultas/ConsultaCorresponsalesCompensacion.do';document.getElementById('MenuPrincipal').submit()">&gt;<span class="subMenuText">${compensacion}</span></a></li>
							<li id="bitacora">     <a href="javaScript:document.getElementById('MenuPrincipal').action='../consultas/BitacoraConsultaCorresponsales.do';document.getElementById('MenuPrincipal').submit()">&gt;<span class="subMenuText">${bitacora}</span></a></li>
							<li id="logtrx">     <a href="javaScript:document.getElementById('MenuPrincipal').action='../consultas/ConsultaLogTransaccional.do';document.getElementById('MenuPrincipal').submit()">&gt;<span class="subMenuText">${logtrx}</span></a></li>
							<li id="lotesPorCompensar">     <a href="javaScript:document.getElementById('MenuPrincipal').action='../consultas/LotesPorCompensar.do';document.getElementById('MenuPrincipal').submit()"><fmt:message key="general.mayorQue" /><span class="subMenuText">${lotesPorCompensar}</span></a></li>
							<li id="sucursalescorresponsal">     <a href="javaScript:document.getElementById('MenuPrincipal').action='../monitoreo/SucursalesCorresponsal.do';document.getElementById('MenuPrincipal').submit()"><fmt:message key="general.mayorQue" /><span class="subMenuText">${sucursalescorresponsal}</span></a></li>
							<li id="estatusOperacion">     <a href="javaScript:document.getElementById('MenuPrincipal').action='../consultas/EstatusOperacionConsultaCorresponsales.do';document.getElementById('MenuPrincipal').submit()"><fmt:message key="general.mayorQue" /><span class="subMenuText">${estatusOperacion}</span></a></li>
						</ul>
					</li>
					<li id="catalogos" class="withSubMenus startMenuGroup"><a href="javascript:selectMenuItem('catalogos')"><span>${catalogos}</span></a>
						<ul>
							<li id="catalogoAplicaciones"> <a href="javaScript:document.getElementById('MenuPrincipal').action='../catalogos/CatalogoConsultaAplicaciones.do';document.getElementById('MenuPrincipal').submit()">&gt;<span class="subMenuText">${catalogoAplicaciones}</span></a></li>
							<li id="catalogoCodigoOperacion"> <a href="javaScript:document.getElementById('MenuPrincipal').action='../catalogos/CatalogoConsultaCodigosOperacion.do';document.getElementById('MenuPrincipal').submit()">&gt;<span class="subMenuText">${catalogoCodigoOperacion}</span></a></li>
						</ul>
					</li>
					<!-- 
					<li id="isbandataaccess" class="withSubMenus startMenuGroup"><a href="javascript:selectMenuItem('isbandataaccess')"><span>${isbandataaccess}</span></a>
						<ul>
							<li id="canalCICS">     <a href="../isbandataaccess/canalCICS.do">&gt;<span class="subMenuText">${canalCICS}</span></a></li>
							<li id="canalGenericMQ"><a href="../isbandataaccess/canalGenericMQ.do">&gt;<span class="subMenuText">${canalGenericMQ}</span></a></li>
							<li id="canalDataBase"> <a href="../isbandataaccess/canalDataBase.do">&gt;<span class="subMenuText">${canalDataBase}</span></a></li>
						</ul>
					</li>
					 -->
				</ul>
			
				<div id="menuFooter">
					<div></div>
				</div>
			
			</div>
			</form>
		</c:if>
	</div>
<!-- </div>  -->
<!-- </body> -->
<!-- </html> -->

