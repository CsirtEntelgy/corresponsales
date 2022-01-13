<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"    prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt"%>

<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="operacionesc" />
</jsp:include>
<c:set var="folEjer" value="${folEjer}" scope="session" />
<c:set var="nombre" value="${nombre}" scope="session" />
<c:set var="contratosConIngresos" value="${contratosConIngresos}"
	scope="session" />
<c:set var="constanciasIngresos" value="${constanciasIngresos}"
	scope="session" />
<c:set var="nombreInformacionIngresos"
	value="${nombreInformacionIngresos}" scope="session" />
<c:set var="perfil" value="${confSession.perfilNombre}" scope="session" />
<c:set var="infFiscal" value="${infFiscal}" scope="session" />
<c:set var="moneda" value="${moneda}" scope="session" />
<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/comunes/validaciones.js"></script>
<script type = "text/javascript">
function asociar() {
	if(document.getElementById("lbTmplNoAsoc").value == '') {
		return;
	}
	document.forms["formaAsocOperaciones"].action = "AsocOperacionesCorresponsal.do";
	document.getElementById("operacion").value = "asociar";	
	if(validaCadena(document.getElementById("txtCveRelac").value, "Cve. Relación",1,10,"Asociacion de cuentas"))
		return;		
	if(validaCadena(document.getElementById("txtTrx").value, "Transacción",4,4,"Asociacion de cuentas"))
		return;
		
	if(validaCadena(document.getElementById("txtClc").value, "Clacon",4,4,"Asociacion de cuentas"))
		return;
				
	if(validaCadena(document.getElementById("txtClcCon").value, "Clacon contraoperación",4,4,"Asociacion de cuentas"))
		return;
	document.forms["formaAsocOperaciones"].submit();
}

function desasociar() {
	if(document.getElementById("lbTmplAsoc").value == '') {
		return;
	}
	document.forms["formaAsocOperaciones"].action = "AsocOperacionesCorresponsal.do";
	document.getElementById("operacion").value = "desasociar";
	document.forms["formaAsocOperaciones"].submit();
}

function mostrarCampos() {
	if(document.getElementById("lbTmplNoAsoc").value != '') {
		document.getElementById("tablaCampos").style.visibility = "visible";	
	}	
}

function ocultarCampos() {
	document.getElementById("tablaCampos").style.visibility = "hidden";
}

function regresar() {
	document.forms["formaAsocOperaciones"].action = "ConsultaOperacionesCorresponsal.do";
	document.getElementById("operacion").value = "consultarOperaciones";
	document.forms["formaAsocOperaciones"].submit();
}

	<c:if test="${not empty  error}">
			jError('${error}',                               //DESCRIPCION ERROR
		   	   	   'Error en Consulta Operaciones', 			//TITULO ERROR
		   	   	   '${error}',                               //CODIGO ERROR
		   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR		
	</c:if>
</script>

<form id="formaAsocOperaciones" name="formaAsocOperaciones" action="" method="post">
<input type="hidden" id="SES" name="SES" value="${SES}" />
	<input type="hidden" id="operacion" name="operacion" value="inicial" />
	<input type="hidden" id="corresponsal" name="corresponsal" value="${corresponsalBuscar}" />

<!-- Componente titulo de página --> 
<div class="pageTitleContainer"><span class="pageTitle">Asociar operaciones</span></div>
<!-- Componente buscador simple -->
<div class="frameBuscadorSimple">
<div class="titleFormularioB">Operaciones <span class="textosin">-
Seleccione las operaciones para el corresponsal</span></div>
<div class="contentBuscadorSimple">
<table>
	<tbody>
		<tr>
			<td  class="text_centro" width="40%">Operaciones no asignadas:</td>
			<td ></td>
			<td class="text_centro" width="40%">*Operaciones asignadas:</td>
		</tr>
		<tr>
			<td class="text_centro" width="40%"><select class="Campos_Des"
				id="lbTmplNoAsoc" name="lbTmplNoAsoc" size="10"
				style="width: 20em;" onfocus="mostrarCampos();" onchange="mostrarCampos();" onclick="mostrarCampos();">
				<c:forEach var="operacion" items="${operaciones}">
					<option value="${operacion.codigoOperacion}">
					${operacion.desCorta}</option>
				</c:forEach>
			</select></td>
			<td  class="text_centro"> 
				<span><a href="#" onclick="asociar();">&gt;</a></span> <br>
				<span>&nbsp;&nbsp;&nbsp;</span> <br>
				<span><a href="#" onclick="desasociar();">&lt;</a></span></td>
			<td class="text_centro" width="40%"><select class="Campos_Des"
				id="lbTmplAsoc" name="lbTmplAsoc" size="10"
				style="width: 15em;"  onFocus="ocultarCampos();" onclick="ocultarCampos();">
				<c:forEach var="operacion" items="${operacionesCorr}">
					<option value="${operacion.codigoOperacion}">
					${operacion.desCorta}</option>
				</c:forEach>
			</select></td>
		</tr>
	</tbody>
	</table>

	<div style="visibility:hidden" id="tablaCampos">
	<table>
	<tbody>
		<tr>
			<td class="text_centro" width="20%">*Cve. Relación:
			<input type="text" value="" class="CamposCompletar" id="txtCveRelac" name="txtCveRelac" size="" maxlength="10"/>							
			</td>
			<td class="text_centro" width="20%">*Transacción:
			<input type="text" value="" class="CamposCompletar" id="txtTrx" name="txtTrx" maxlength="4"/>							
			</td>
			<td class="text_centro" width="20%">*Clacon:
			<input type="text" value="" class="CamposCompletar" id="txtClc" name="txtClc" maxlength="4"/>							
			</td>
			<td class="text_centro" width="40%">*Clacon contraoperación:
			<input type="text" value="" class="CamposCompletar" id="txtClcCon" name="txtClcCon" maxlength="4"/>							
			</td>
		</tr>
	
	</tbody>
	</table>
	</div>
</div>
<div class="framePieContenedor">
<div class="contentPieContenedor">
<table width="100%">
	<tr>
		<td class=""><span>&nbsp;</span></td>
		<td width="4px" class="odd">&nbsp;</td>
		<td class="der"><span><a href="javascript:regresar();">Cerrar</a></span></td>
	</tr>
</table>
</div>
</div>
</div>
</form>

