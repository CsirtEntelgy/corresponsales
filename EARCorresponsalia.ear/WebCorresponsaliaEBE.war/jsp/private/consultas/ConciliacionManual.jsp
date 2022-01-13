<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="consultas" />
	<jsp:param name="menuSubitem" value="conciliacionManual" />
</jsp:include>
<%@ taglib uri="http://www.springframework.org/tags"    prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt"%>

<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/comunes/validaciones.js"></script>

<script src="${pageContext.servletContext.contextPath}/js/private/consultas/ConciliacionManual.js" type="text/javascript"></script>

<c:if test="${not empty  errorArchivo}">
	<script src="${pageContext.servletContext.contextPath}/js/comunes/errorArchivo.js" type="text/javascript"></script>
</c:if>

<form id="formaConciliacionManual" action="" method="post" enctype="multipart/form-data">
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
	
	<input type="hidden" id="errorArchivo" name="errorArchivo" value="${errorArchivo}"/>
	
	<input type="hidden" id="textFile" value='<%=request.getSession().getAttribute("textFile")%>' />
	<input type="hidden" id="operacion" name="operacion" value="default" />

	<%-- Componente titulo de página --%>
	<div class="pageTitleContainer">
		<span class="pageTitle">Conciliación Manual</span>
	</div>
	
	<%-- Componente formulario --%>
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple"><fmt:message key="general.conciliacion" /></div>
		<div class="contentBuscadorSimple">
				<table>
				<caption></caption>
					<tbody>
						<tr>
							<td class="text_izquierda">
								Archivo:
								<label for="fbArchivo"></label>
								<input type="file" class="Campos_Des" id="fbArchivo" name="fbArchivo" />							
								<span width="100"><fmt:message key="general.espacioSimple" /><fmt:message key="general.espacioSimple" /><fmt:message key="general.espacioSimple" /></span>
								<span><a href="javascript:enviaArchivo();" ><fmt:message key="general.cargar" /></a></span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		
	<c:if test="${errores!=null}">
	<div class="contentTablaEstandar">
	<table>
		<thead>
			<tr>
				<th width="193" class="text_izquierda">Registros Importados</th>
				<th width="193" class="text_centro">Registros Exitosos</th>
				<th width="193" class="text_centro">Registros con Error</th>
			</tr>
		</thead>
		<tr>
			<td colspan="3" class="special"></Td>
		</tr>
		<tbody>
			<tr class="odd1">
				<td class="text_izquierda">${totalRegistros}</td>
				<td class="text_izquierda">${totalExitosos}</td>
				<td class="text_izquierda">${totalErrores}</td>
			</tr>
		</tbody>
	</table>
	</div>

	<div class="contentTablaEstandar">
	<table>
		<thead>
			<tr>
				<th width="50" class="text_izquierda">L&iacute;nea</th>
				<th width="535" class="text_centro">Descripci&oacute;n</th>
			</tr>
		</thead>
		<tr>
			<td colspan="2" class="special"></Td>
		</tr>
		<tbody>
			<c:forEach var="error" items="${errores}">
				<tr class="odd1">
					<td class="text_izquierda" style="color: red">${error.key}</td>
					<td class="text_izquierda" style="color: red">${error.value}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<div style="color: #C90212;"><strong> ${aviso} </strong></div>

</c:if>

</form>

	<c:if test="${not empty  msgResultado}">
		<script>
				jAlert('${msgResultado}',                               //DESCRIPCION ERROR
			   	   	   'MSG', //TITULO ERROR
			   	   	   '',                               //CODIGO ERROR
			   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR		
			</script>
	</c:if>
	
	<c:if test="${not empty  errorArchivo}">
		<script>
				jError('${errorArchivo}',                               //DESCRIPCION ERROR
			   	   	   'MSG', //TITULO ERROR
			   	   	   '',                               //CODIGO ERROR
			   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR		
			</script>
	</c:if>

	<c:if test="${not empty  codError}">
		<script src="${pageContext.servletContext.contextPath}/js/comunes/codError.js" type="text/javascript"></script>
	</c:if>

	<c:if test="${not empty  codAviso}">
		<script src="${pageContext.servletContext.contextPath}/js/comunes/codAviso.js" type="text/javascript"></script>
	</c:if>
	