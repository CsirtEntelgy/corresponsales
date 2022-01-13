<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="sucursales" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib uri="http://www.springframework.org/tags"    prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt"%>

<script src="${pageContext.servletContext.contextPath}/js/private/corresponsales/ConsultaSucursal.js" type="text/javascript"></script>

<form id="formaConsultaSucursales" action="" method="post">
    <input type="hidden" id="SES" name="SES" value="${SES}" />
	<input type="hidden" id="adelanteAtras" name="adelanteAtras" value="">
	<input type="hidden" id="sucursalSel"   name="sucursalSel" value="">
	<input type="hidden" id="regSel" name="regSel">
	<input type="hidden" id="estadoActual" name="estadoActual">
	<input type="hidden" value="${referenciaAvanzar}" id="referenciaAvanzar" name="referenciaAvanzar" />
	<input type="hidden" value="${referenciaRetroceder}" id="referenciaRetroceder" name="referenciaRetroceder" />	
	<input type="hidden" value="${nombreReferencia}" id="nombreReferencia" name="nombreReferencia" />
	<input type="hidden" value="${(empty paginaNum) ? "1" : paginaNum }" id = "paginaNum" name="paginaNum" >
	
	<input type="hidden" id="msgError" name="msgError" value="${msgError}"/>
	<input type="hidden" id="codError" name="codError" value="${codError}"/>
	<input type="hidden" id="msgAviso" name="msgAviso" value="${msgAviso}"/>
	<input type="hidden" id="codAviso" name="codAviso" value="${codAviso}"/>
	<input type="hidden" id="compError" name="compError" value="Error en Sucursales"/>
	<input type="hidden" id="compAviso" name="compAviso" value="Aviso Sucursales"/>


	<%-- Componente titulo de página --%>
	<div class="pageTitleContainer">
		<span class="pageTitle">Sucursales por corresponsal</span>
	</div>
	
	<%-- Componente formulario --%>

	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Corresponsal a Consultar</div>
		<div class="contentFormularioB">
			<table>
			<caption></caption>
				<tr>
					<td class="odd">* Corresponsal:</td>
						<td>
							<label for="cmbNombreCorresponsal"></label>
							<select id="cmbNombreCorresponsal" name="cmbNombreCorresponsal" class="Campos" onchange="javascript:consultaSucursales(' ');" 
							onfocus="javascript:sucursalSel.value = this.value;">
										<option VALUE="0" selected="selected">--Seleccione un corresponsal</option>
										<c:forEach var="resultado" items="${listaCorresponsalesSucursales}">										
											<option VALUE="${resultado.codigoCorresponsal}"<c:if test="${resultado.codigoCorresponsal==beanConsultaSucursal.codigoCorresponsalia}"> selected</c:if>>${resultado.nombreCorresponsal}</option>
										</c:forEach>
							</select>
						</td>
				</tr>
			</table>
		</div>	
	</div>
	<%-- Componente tabla estandar --%>
		 
	<div class="frameTablaEstandar">
		<div class="titleTablaEstandar">
			Sucursales <span class="textosin">- Seleccione la acción</span>
		</div>
		<div class="contentTablaEstandar">
			<table>
			<caption></caption>
				<thead>
					<tr>
						<th id="codigo" class="text_izquierda"><fmt:message key="general.codigo" /></th>
						<th id="nombre" style="width: 147px;" class="text_centro"><fmt:message key="general.nombre" /></th>
						<th id="estatus" style="width: 147px;" class="text_centro"><fmt:message key="general.estatus" /></th>
						<th id="detalle" style="width: 147px;" class="text_centro"><fmt:message key="general.detalle" /></th>
						<th id="modificar" style="width: 147px;" class="text_centro"><fmt:message key="general.modificar" /></th>
						<th id="eliminar" style="width: 147px;" class="text_centro"><fmt:message key="general.eliminar" /></th>
					</tr>
				</thead>
				<tr>
					<Td colspan="6" class="special"></Td>
				</tr>
				<tbody>
				<c:set var="i" value="0"/>
				<c:set var="estilo" value="odd1"/>
			    <c:forEach var="datos" items="${registrosSucursales}" >  
			    	<tr class="${estilo}">
						<td style="width: 128px;" class="text_izquierda">${datos.numId}</td>
						<td class="text_izquierda">
						 
						${fn:substring(datos.nombreSucursal,0,30)}</td>
						<td class="text_centro"><div style="align-items: center" ><label for="codigoEstatus"></label><input type="checkbox" id="codigoEstatus" class="Campos" onclick="javascript:activarDesactivar('${i}','${datos.codigoEstatus}')" <c:if test="${datos.codigoEstatus=='CS2'}"> checked="checked" </c:if>/></div></td>
						<td class="text_centro"> 
						<a  href="javascript:detalleSucursal('${i}');"><div class="contentPieContenedor" ><fmt:message key="general.detalle" /></div></a>
						</td>
						<td class="text_centro">
							<a href="javascript:modificarSucursal('${i}');"><div class="contentPieContenedor"><fmt:message key="general.modificar" /></div></a>
						</td>												
						<td class="text_centro">
							<a href="javascript:eliminarSucursal('${i}');"><div class="contentPieContenedor"><fmt:message key="general.eliminar" /></div></a>
						</td>						
			    	</tr>
			    	<c:set var="i" value="${i + 1}"/>
			    	<c:choose>
						<c:when test="${estilo == 'odd2'}">
			    			<c:set var="estilo" value="odd1"/>
			    		</c:when>
			    		<c:otherwise>
			    			<c:set var="estilo" value="odd2"/>
			    		</c:otherwise>
			    	</c:choose>
			    </c:forEach>
				</tbody>
			</table>
		</div>
		<div class="paginador">
			<c:if test="${hayAtras}">
				<a href="javascript:consultaSucursales('B');"><fmt:message key="general.anterior" /></a>
			</c:if>
			<c:if test="${hayAdelante}">
				<a href="javascript:consultaSucursales('A');"><fmt:message key="general.siguiente" /></a>
			</c:if>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table style="width: 100%;">
				<caption></caption>
					<tr>
						<td class="izq"><span><a href="javascript:altaSucursal();" onclick=""  ><fmt:message key="general.alta" /></a></span></td>
						<td style="width: 4px;" class="odd"><fmt:message key="general.espacioSimple" /></td>
						<td class="text_centro"><span>
						<c:if test="${registrosSucursales!=null && not empty registrosSucursales}">
							<a href="javascript:exportar();"><fmt:message key="general.exportar" /></a>
						</c:if>							
						</span></td>
						<td style="width: 4px;" class="odd"><fmt:message key="general.espacioSimple" /></td>
						<td class="der"><span><a href="javascript:cerrar();"><fmt:message key="general.cerrar" /></a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
 
</form>

	<c:if test="${not empty  codError}">
		<script src="${pageContext.servletContext.contextPath}/js/comunes/codError.js" type="text/javascript"></script>
	</c:if>

	<c:if test="${not empty  codAviso}">
		<script src="${pageContext.servletContext.contextPath}/js/comunes/codAviso.js" type="text/javascript"></script>
	</c:if>

