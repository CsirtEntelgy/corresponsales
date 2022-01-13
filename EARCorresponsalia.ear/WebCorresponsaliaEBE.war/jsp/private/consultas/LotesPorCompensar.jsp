<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="consultas" />
	<jsp:param name="menuSubitem" value="lotesPorCompensar" />
</jsp:include>

<%@ taglib uri="http://www.springframework.org/tags"    prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt"%>

<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/comunes/validaciones.js"></script>

<script src="${pageContext.servletContext.contextPath}/js/private/consultas/LotesPorCompensar.js" type="text/javascript"></script>

<form id="formaLotesPorCompensar" action="" method="post">
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
	<input type="hidden" id="compError" name="compError" value="Error en Lotes por Compensar"/>
	<input type="hidden" id="compAviso" name="compAviso" value="Aviso Lotes por Compensar"/>

	<%-- Componente titulo de página --%>
	<div class="pageTitleContainer">
		<span class="pageTitle">Lotes por Compensar</span>
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
							<input type="hidden" id="textFile" value='<%=request.getSession().getAttribute("testVariable")%>' />
							<input type = "hidden" id = "codigoSucursal" value = '<%= request.getSession().getAttribute("codSucursal") %>'/>
							<label for="optCorresponsal1"></label>
							<select id="optCorresponsal1" name="optCorresponsal1" ${clase} ${disabled} 
							onClick="comboValor.value = this.value;" style="width: 170px;"
							onchange="javascript:formaLotesPorCompensar.action ='consultaLotesPorCompensar.do';formaLotesPorCompensar.submit();">
										<option VALUE="0" selected="selected">--Seleccione un corresponsal</option>
										<c:forEach var="resultadoCorresponsales" items="${listaCorresponsalesMonitoreoOperaciones}">
											<option VALUE="${resultadoCorresponsales.codigoCorresponsal}"<c:if test="${resultadoCorresponsales.codigoCorresponsal==beanConsultaMonitoreoOperaciones.identificacionCorresponsal}"> selected</c:if>>${resultadoCorresponsales.nombreCorresponsal}</option>
										</c:forEach>
							</select>
						</td>
					</tr>
				</table>
			</div>
		</div>
</form>

	<c:if test="${not empty  codError}">
		<script src="${pageContext.servletContext.contextPath}/js/comunes/codError.js" type="text/javascript"></script>
	</c:if>

	<c:if test="${not empty  codAviso}">
		<script src="${pageContext.servletContext.contextPath}/js/comunes/codAviso.js" type="text/javascript"></script>
	</c:if>
	