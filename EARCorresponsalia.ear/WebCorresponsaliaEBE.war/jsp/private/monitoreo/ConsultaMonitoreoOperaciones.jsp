<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="monitoreo" />
	<jsp:param name="menuSubitem" value="operacionesm" />
</jsp:include>
<%@ taglib uri="http://www.springframework.org/tags"    prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt"%>

<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/comunes/validaciones.js"></script>

<script src="${pageContext.servletContext.contextPath}/js/private/monitoreo/ConsultaMonitoreoOperaciones.js" type="text/javascript"></script>

<form id="formaConsultaMonitoreoOperaciones" action="" method="post">
    <input type="hidden" id="SES" name="SES" value="${SES}" />
	<input type="hidden" value="" id="opcAvanzarRetroceder" name="opcAvanzarRetroceder" />
	
	<input type="hidden"  id="optCorresponsal" name="optCorresponsal" />
	<input type="hidden"  id="comboValor" name="comboValor" />	
	<input type="hidden"  id="optSucursal" name="optSucursal" />
	<input type="hidden"  id="txtHoraInicio" name="txtHoraInicio" />
	<input type="hidden"  id="txtHoraFin" name="txtHoraFin" />
	<input type="hidden"  id="optOperacion" name="optOperacion" />
	<input type="hidden"  id="txtImporteMinimo" name="txtImporteMinimo" />
	<input type="hidden"  id="txtImporteMaximo" name="txtImporteMaximo" />
	<input type="hidden"  id="pagina" name="pagina" value="${pagina}"/>
	
	<input type="hidden"  id="referenciaAvanzar"    name="referenciaAvanzar"    value="${referenciaAvanzar}"/>
	<input type="hidden"  id="operacionAvanzar"     name="operacionAvanzar"     value="${operacionAvanzar}"/>
	<input type="hidden"  id="horaAvanzar"          name="horaAvanzar"          value="${horaAvanzar}"/>
	<input type="hidden"  id="referenciaRetroceder" name="referenciaRetroceder" value="${referenciaRetroceder}"/>
	<input type="hidden"  id="operacionRetroceder"  name="operacionRetroceder"  value="${operacionRetroceder}"/>
	<input type="hidden"  id="horaRetroceder"       name="horaRetroceder"       value="${horaRetroceder}"/>
	
	<input type="hidden" id="msgError" name="msgError" value="${msgError}"/>
	<input type="hidden" id="codError" name="codError" value="${codError}"/>
	<input type="hidden" id="msgAviso" name="msgAviso" value="${msgAviso}"/>
	<input type="hidden" id="codAviso" name="codAviso" value="${codAviso}"/>
	<input type="hidden" id="compError" name="compError" value="Error en Monitoreo Operaciones"/>
	<input type="hidden" id="compAviso" name="compAviso" value="Aviso Monitoreo Operaciones"/>

	<%-- Componente titulo de página --%>
	<div class="pageTitleContainer">
		<span class="pageTitle">Monitoreo de Operaciones</span>
	</div>

	<%-- Componente formulario --%>
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple"><fmt:message key="catalogos.aplicaciones.filtros" /></div>
			<div class="contentFormularioB">
				<table>
				<caption></caption>
					<tr>
						<td class="odd">*Corresponsal:</td>
						<td>
							<%-- <select id="optCorresponsal" name="optCorresponsal" class="CamposCompletar" onchange="javascript:consultaSucursalesOperaciones();" style="width: 170px;" --%>
							<label for="optCorresponsal1"></label>
							<select id="optCorresponsal1" name="optCorresponsal1" ${clase} ${disabled} onchange="javascript:consultaSucursalesOperaciones();" 
							onClick="comboValor.value = this.value;" style="width: 170px;">
										<option VALUE="0" selected="selected">--Seleccione un corresponsal</option>
										<c:forEach var="resultadoCorresponsales" items="${listaCorresponsalesMonitoreoOperaciones}">
											<option VALUE="${resultadoCorresponsales.codigoCorresponsal}"<c:if test="${resultadoCorresponsales.codigoCorresponsal==beanConsultaMonitoreoOperaciones.identificacionCorresponsal}"> selected</c:if>>${resultadoCorresponsales.nombreCorresponsal}</option>
										</c:forEach>
							</select>
						</td>
						<%-- Modificado CEC --%>
						<td class="odd">Sucursal:</td>
						<td>
						<label for="optSucursal1"></label>
						<input type="text" list="mySelectDropdwonList" id="optSucursal1" name="optSucursal1"/>
						<datalist id="mySelectDropdwonList">
							<option VALUE=" " selected="selected"><fmt:message key="general.todas" /></option>
							<c:forEach var="resultadoSucursales" items="${listaSucursalesMonitoreoOperaciones}">
								<option VALUE="${resultadoSucursales.numId}"<c:if test="${resultadoSucursales.numId==beanConsultaMonitoreoOperaciones.codigoIdentificacionSucursal}"> selected</c:if>>${resultadoSucursales.nombreSucursal}</option>
							</c:forEach>
						</datalist>
						</td>
					</tr>
					<tr>
						<td class="odd">*Hora inicio:</td>
						<td>
							<label for="txtHoraInicio1"></label>
			    			<input type="text" ${clase} ${disabled} id="txtHoraInicio1" name="txtHoraInicio1"  maxlength="5" size="10" value="${beanConsultaMonitoreoOperaciones.horaInicioFront}"/>
			    		</td>
						<td class="odd">*Hora fin:</td>
						<td>
							<label for="txtHoraFin1"></label>
							<input type="text" ${clase} ${disabled} id="txtHoraFin1" name="txtHoraFin1"  maxlength="5" size="10" value="${beanConsultaMonitoreoOperaciones.horaFinFront}"/>
			    		</td>
					</tr>
					<tr>
						<td class="odd">Operaci&oacute;n:</td>
						<td>
							<label for="optOperacion1"></label>
							<select id="optOperacion1" name="optOperacion1" class="Campos_Des" ${disabled} style="width: 170px;">
										<option VALUE=" " selected="selected">Todas las operaciones</option>
										<c:forEach var="resultadoOperaciones" items="${listaOperacionesMonitoreoOperaciones}">
											<option VALUE="${resultadoOperaciones.consecutivoOperacionNivelCanal}"<c:if test="${resultadoOperaciones.consecutivoOperacionNivelCanal == beanConsultaMonitoreoOperaciones.claveReferenteTipoOperacion}"> selected</c:if>>${resultadoOperaciones.descripcionCorta}</option>
										</c:forEach>
							</select>
						</td>
						<td class="odd">Importe entre:</td>
						<td>
							<label for="txtImporteMinimo1"></label>
							<input type="text" class="Campos_Des" ${disabled} id="txtImporteMinimo1" name="txtImporteMinimo1"  maxlength="16" size="10" value="${beanConsultaMonitoreoOperaciones.importeInicialFront}"/>
						</td>
						<td class="odd"> y </td>
						<td>
							<label for="txtImporteMaximo1"></label>
							<input type="text" class="Campos_Des" ${disabled} id="txtImporteMaximo1" name="txtImporteMaximo1"  maxlength="16" size="10" value="${beanConsultaMonitoreoOperaciones.importeFinalFront}"/>
						</td>
					</tr>
				</table>
			</div>
			<c:if test="${listaRegistrosMonitoreoOperaciones==null}">
				<div class="framePieContenedor">
					<div class="contentPieContenedor">
						<table style="width: 100%;">
						<caption></caption>
							<tr>
								<td class="izq"><span><a href="javascript:consultaMonitoreoOperaciones(' ');"><fmt:message key="general.consulta" /></a></span></td>
								<td style="width: 4px;" class="odd"><fmt:message key="general.espacioSimple" /></td>
								<td class="der"><span><a href="javascript:cerrar();"><fmt:message key="general.cerrar" /></a></span></td>
							</tr>
						</table>
					</div>
				</div>
			</c:if>
		</div>

<c:if test="${listaRegistrosMonitoreoOperaciones!=null}">
	<%-- Componente tabla estandar --%>
	<div class="frameTablaEstandar"> 
		<div class="titleTablaEstandar"><fmt:message key="general.operaciones" /><span class="textosin"></span>
		</div>
		<div class="contentTablaEstandar">
			<table>
			<caption></caption>
				<thead>
					<tr>
						<th id="sucursal" style="width: 140px;" class="text_izquierda"><fmt:message key="general.sucursal" /></th>
						<th id="operacion" style="width: 140px;" class="text_centro"><fmt:message key="general.operacion" /></th>
						<th id="nOperaciones" style="width: 140px;" class="text_centro">No. Operaciones</th>
						<th id="totalOperacion" style="width: 150px;" class="text_derecha">Total por Operaci&oacute;n</th>
					</tr>
				</thead>
				<tr>
					<Td colspan="6" class="special"></Td>
				</tr>
				<tbody>
				
				<c:set var="i" value="0"/>
				<c:set var="estilo" value="odd1"/>
			    <c:forEach var="resultadoOperaciones" items="${listaRegistrosMonitoreoOperaciones}" >  
			    	<tr class="${estilo}">
						<td style="width: 128px;" class="text_izquierda">${resultadoOperaciones.descripcionSucursal}</td>
						<td class="text_izquierda">${resultadoOperaciones.descripcionOperacion}</td>
						<td class="text_izquierda">${resultadoOperaciones.totalOperaciones}</td>
						<td class="text_izquierda">${resultadoOperaciones.importeTotalOperacionesFront}</td>
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
		
		<div class="framePieContenedor">
		<table>
		<caption></caption>
			    	<td class="odd"> Total operado por el corresponsal:</td>
					<td>
						<label for="txtTotOperado"></label>
						<input type="text" class="Campos_Des" disabled="disabled" id="txtTotOperado" name="txtTotOperado"  maxlength="6" size="10" value="${totalOperado}"/>
					</td>
			</table>
		</div>
		
		
		<div class="paginador">	
			<c:if test="${pagina > 1}">			
				<a href="javascript:consultaMonitoreoOperaciones('B');"><fmt:message key="general.atras" /></a>
			</c:if>	
			<c:if test="${paginar!=null}">
				|
				<a href="javascript:consultaMonitoreoOperaciones('A');"><fmt:message key="general.adelante" /></a>
			</c:if>
		</div>
		
			
			
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table style="width: 100%;">
				<caption></caption>
					<tr>
						<td class="izq"><span>
						<c:if test="${listaRegistrosMonitoreoOperaciones !=null && not empty listaRegistrosMonitoreoOperaciones}">
							<a href="javascript:exportar();"  ><fmt:message key="general.exportar" /></a>
						</c:if>
						</span></td>
						<td style="width: 4px;" class="odd"><fmt:message key="general.espacioSimple" /></td>
						<td class="der"><span><a href="javascript:limpiar();"><fmt:message key="general.limpiar" /></a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	</c:if>
</form>

	<c:if test="${not empty  codError}">
		<script src="${pageContext.servletContext.contextPath}/js/comunes/codError.js" type="text/javascript"></script>
	</c:if>

	<c:if test="${not empty  codAviso}">
		<script src="${pageContext.servletContext.contextPath}/js/comunes/codAviso.js" type="text/javascript"></script>
	</c:if>
	