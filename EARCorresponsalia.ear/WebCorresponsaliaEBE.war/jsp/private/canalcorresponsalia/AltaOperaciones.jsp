<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"    prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt"%>


<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="canalcorresponsalia" />
</jsp:include>

<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/comunes/validaciones.js"></script>
<script>
	function consultaOperacionesCatalogo(){
	var indiceSeleccionado = document.forms["formaAltaOperaciones"].cmbOperacionCatalogo.selectedIndex;
	var codigoCorresponsalia = document.forms["formaAltaOperaciones"].cmbOperacionCatalogo.options[indiceSeleccionado].value;
	document.getElementById("codigoOperacion").value = codigoCorresponsalia;
	if(codigoCorresponsalia == '1'){
		document.forms["formaAltaOperaciones"].action = "MuestraAltaOperacionesCatalogo.do";
		document.forms["formaAltaOperaciones"].submit();
	}
		//document.forms["formaAltaOperaciones"].action = "AltaOperaciones.do";
    	//document.forms["formaAltaOperaciones"].submit();
	}
	function altaOperaciones(){
    	document.forms["formaAltaOperaciones"].action = "AltaOperaciones.do";
    	if(validaCombo(document.getElementById("cmbOperacionCatalogo").value, "Nombre"))
			return;
		if(validaImporte(document.getElementById("txtImporteMax").value, "Importe máximo","Canal Operaciones"))
			return;
		if(validaImporte(document.getElementById("txtImporteMin").value, "Importe minimo","Canal Operaciones"))
			return;
		if(validaImporte(document.getElementById("txtAcumuladoDiario").value, "Acumulado diario","Canal Operaciones"))
			return;
		if(validaImporte(document.getElementById("txtAcumuladoMes").value, "Acumulado mensual","Canal Operaciones"))
			return;
		if(validaHora(document.getElementById("txtHoraIni").value, "Hora inicio", "Canal Operaciones"))
			return;
		if(validaHora(document.getElementById("txtHoraFin").value, "Hora fin","Canal Operaciones"))
			return;
    	document.forms["formaAltaOperaciones"].submit();
	}
	function cerrar(){
    	document.forms["formaAltaOperaciones"].action = "ConsultaOperaciones.do";
    	document.forms["formaAltaOperaciones"].submit();
	}
	
	function validaCombo(valor, campo){
	    if(valor == "0"){
	        //alert('Error en el tipo de datos, por favor verifique la información del campo : '+campo);
	        jAlert('Por favor verifique la información del campo : '+campo,//DESCRIPCION ERROR
			   	   	   'Alerta Alta de Operaciones', //TITULO ERROR
			   	   	   'Error en el tipo de datos',                               //CODIGO ERROR
			   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR
	        return true;
	    }else
	        return false;
	}
			
</script>

<form id="formaAltaOperaciones" action="" method="post">
	<input type="hidden" id="codigoOperacion" name="codigoOperacion" />
	<input type="hidden" name="SES" value="${SES}" />
	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Alta de operaciones</span>
	</div>
	
	<!-- Componente formulario Parametria -->
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Datos generales de la operaci&oacute;n</div>
		<div class="contentBuscadorSimple">
			<table>
					<tr>
						<td class="text_izquierda">*Nombre:</td>
					</tr>
					<tr>
						<td>
						
						<select id="cmbOperacionCatalogo" name="cmbOperacionCatalogo" class="Campos" onchange="javascript:consultaOperacionesCatalogo();">
							<!-- 
							<option VALUE="0" selected="selected">-----</option>
							<option VALUE="1">Nueva Operaci&oacute;n</option>
							<c:forEach var="resultado" items="${listaOperacionesCatalogo}">
								<option VALUE="${resultado.consecutivoOperacionNivelCanal}|${resultado.claveTransaccionAsociada}|${resultado.claveTransaccionAsociadaAnterior}"<c:if test="${resultado.consecutivoOperacionNivelCanal==corresponsaliaSeleccionada}"> selected</c:if>>${resultado.descripcionCorta}</option>
							</c:forEach>-->							
							
								<option VALUE="0" selected="selected">---Seleccione operaci&oacute;n</option>
								<option VALUE="1">Nueva Operaci&oacute;n</option>
									
								<c:forEach var="resultado" items="${listaOperacionesCatalogo}" >
									<c:choose>
										<c:when test="${resultado.consecutivoOperacionNivelCanal == codigoOperacion}">
											<option VALUE="${resultado.consecutivoOperacionNivelCanal}|${resultado.claveTransaccionAsociada}|${resultado.claveTransaccionAsociadaAnterior}" selected="selected">${resultado.descripcionCorta}</option>
										</c:when>
										<c:otherwise>
											<option VALUE="${resultado.consecutivoOperacionNivelCanal}|${resultado.claveTransaccionAsociada}|${resultado.claveTransaccionAsociadaAnterior}">${resultado.descripcionCorta}</option>
										</c:otherwise>
									</c:choose>
			    					  
			    				</c:forEach>
							
							
							
						</select>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*Importe m&aacute;ximo:</td>
						<td class="text_izquierda">*Importe m&iacute;nimo:</td>
						<td class="text_izquierda">*Acumulado diario:</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="CamposCompletar" id="txtImporteMax" value="${txtImporteMax}" name="txtImporteMax"  maxlength="16" size=""/>
						</td>
						<td>
							<input type="text" class="CamposCompletar" id="txtImporteMin" value="${txtImporteMin}" name="txtImporteMin"  maxlength="16" />
						</td>
						<td>
							<input type="text" class="CamposCompletar"id="txtAcumuladoDiario" value="${txtAcumuladoDiario}" name="txtAcumuladoDiario"  maxlength="16" size=""/>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*Hora inicio:</td>
						<td class="text_izquierda">*Hora fin:</td>
						<td class="text_izquierda">*Acumulado mensual:</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="CamposCompletar" id="txtHoraIni" value="${txtHoraIni}" name="txtHoraIni"  maxlength="5" size=""/>
						</td>
						<td>
							<input type="text" class="CamposCompletar" id="txtHoraFin" value="${txtHoraFin}" name="txtHoraFin"  maxlength="5" />
						</td>
						<td>
							<input type="text" class="CamposCompletar" id="txtAcumuladoMes" value="${txtAcumuladoMes}" name="txtAcumuladoMes"  maxlength="16" />
						</td>
					</tr>
			</table>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table WIDTH="100%">
					<tr>
						<td class="izq"><span><a href="javascript:altaOperaciones();" >Guardar</a></span></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="der"><span><a href="javascript:cerrar();">Cerrar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
</form>
<c:if test="${not empty  msgResultado}">
		<script>
			jAlert('${msgResultado}',                               //DESCRIPCION ERROR
		   	   	   'MSG', //TITULO ERROR
		   	   	   '',                               //CODIGO ERROR
		   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR		
		</script>
	</c:if>