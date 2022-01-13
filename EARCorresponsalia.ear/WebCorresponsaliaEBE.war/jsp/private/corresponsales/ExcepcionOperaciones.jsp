<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="excepcionoper" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type = "text/javascript">
function actualizaSucursal() {
	document.forms["formaExcepcionesOperacions"].action = "ExcepcionOperacionesSuc.do";
	document.forms["formaExcepcionesOperacions"].submit();
}
function cargaOperaciones() {
	document.forms["formaExcepcionesOperacions"].action = "ExcepcionOperacionesOpe.do";
	document.forms["formaExcepcionesOperacions"].submit();
}
function cerrar(){
	document.forms["formaExcepcionesOperacions"].action = "../principal/inicio.do";
	document.forms["formaExcepcionesOperacions"].submit();
}

function asociar(){
	if(document.getElementById("lbTmplNoAsoc").value == '') {
		return
	}
	document.forms["formaExcepcionesOperacions"].action = "ExcepcionOperacionesAsc.do";
	document.getElementById("operacion").value = "asociar";
	document.forms["formaExcepcionesOperacions"].submit();
}

function desasociar(){
	if(document.getElementById("lbTmplAsoc").value == '') {
		return
	}
	document.forms["formaExcepcionesOperacions"].action = "ExcepcionOperacionesAsc.do";
	document.getElementById("operacion").value = "desasociar";
	document.forms["formaExcepcionesOperacions"].submit();
}
	<c:if test="${not empty  mensajeRespuesta}">
			jAlert('${mensajeRespuesta}',                               //DESCRIPCION ERROR
		   	   	   'Resultado en Excepciones Operaciones', 						//TITULO ERROR
		   	   	   '',                               //CODIGO ERROR
		   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR		
	</c:if>
	
	<c:if test="${empty  mensajeRespuesta && not empty msjRespuesta}">
			jAlert('${msjRespuesta}',                               //DESCRIPCION ERROR
		   	   	   'Resultado en Excepciones Operaciones', 						//TITULO ERROR
		   	   	   '',                               //CODIGO ERROR
		   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR		
	</c:if>


</script>


<form id="formaExcepcionesOperacions" action="" method="post">
<input type="hidden" id="SES" name="SES" value="${SES}" />
	<input type="hidden" id="operacion" name="operacion" value="default" />

	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Excepciones de Operaci&oacute;n</span>
	</div>
	
	<!-- Componente formulario -->
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Excepciones para</div>
			<div class="contentFormularioB">
				<table>
					<tr>
						<td class="odd">*Corresponsal:</td>
						<td>
							<!--<select id="tipDoc" name="tipDoc" class="CamposCompletar" onchange="filtro();limpiaDatos();">-->
							<select id="corresponsal" name="corresponsal" class="Campos" onChange="actualizaSucursal();" style="width: 180px;">
										<!-- <option VALUE="0000" selected="selected">Todo el canal</option>-->
										<option VALUE="">---</option>
										<c:forEach var="resultado" items="${lista}">
											<option VALUE="${resultado.codigoCorresponsal}"<c:if test="${resultado.codigoCorresponsal==corresponsalBuscar}"> selected</c:if>>${resultado.nombreCorresponsal}</option>
										</c:forEach>
							</select>
						</td>
						<td class="odd">*Sucursal:</td>
						<td>
							<!--<select id="tipDoc" name="tipDoc" class="CamposCompletar" onchange="cargaOperaciones();">-->
							<select id="sucursal" name="sucursal" class="Campos" style="width: 180px;" onchange="cargaOperaciones();">
										<option VALUE="">---</option>
										<c:forEach var="resultado" items="${listaSuc}">
											<option VALUE="${resultado.numId}"<c:if test="${resultado.numId==sucursalBuscar}"> selected</c:if>>${resultado.nombreSucursal}</option>
										</c:forEach>
							</select>
						</td>
				</table>
			</div><!-- 
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table>
					<tr>
						<td width="279" class="izq"><span><a href="" onclick="" >Consultar</a></span></td>
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="der"><span><a href="" onclick="limpiar();" ></a>Cerrar</span></td>
					</tr>
				</table>
			</div>
		</div>-->
	</div> 
	<!-- Componente tabla estandar -->
<div class="frameBuscadorSimple">
<div class="titleFormularioB">Operaciones <span class="textosin"></span></div>
<div class="contentBuscadorSimple">
<table>
	<tbody>
		<tr>
			<td class="text_centro" width="40%">Operaciones corresponsal:</td>
			<td></td>
			<td></td>			
			<td class="text_centro" width="40%">*Excepciones sucursal:</td>
		</tr>
		<tr>
			<td class="text_centro" width="40%"><select class="Campos_Des"
				id="lbTmplNoAsoc" name="lbTmplNoAsoc" size="10"
				style="width: 20em;">
				<c:forEach var="operacion" items="${operaciones}">
					<option value="${operacion.codigoOperacion}">
					${operacion.desCorta}</option>
				</c:forEach>
			</select></td>
			<td  class="text_centro"></td>
			<td class="text_centro">
				<span><a href="#" onclick="asociar();"> &gt; </a></span>
				<br/>
				<br/>
				<span><a href="#" onclick="desasociar();">&lt;</a></span>
				<br/>
				<br/>
				</td>
			<td class="text_centro" width="40%"><select class="Campos_Des"
				id="lbTmplAsoc" name="lbTmplAsoc" size="10"
				style="width: 15em;">
				<c:forEach var="operacion" items="${operacionesSuc}">
					<option value="${operacion.codigoOperacion}">
					${operacion.desCorta}</option>
				</c:forEach>
			</select></td>
		</tr>
	</tbody>
</table>
</div>
<div class="framePieContenedor">
<div class="contentPieContenedor">
<table width ="100%">
	<tr>
		<td><span>&nbsp;</span></td>
		<td width="4px" class="odd">&nbsp;</td>
		<td class="der"><span><a href="javascript:cerrar();">Cerrar</a></span></td>
	</tr>
</table>
</div>
</div>
</div>
	</form>
	