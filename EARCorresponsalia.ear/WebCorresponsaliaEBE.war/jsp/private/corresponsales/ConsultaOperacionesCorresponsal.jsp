<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"    prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt"%>
<jsp:include page="../myHeader.jsp" flush="true" >
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="operacionesc" />
</jsp:include>
<script type = "text/javascript">


function consultar() {
		document.forms["formaConsultaOperaciones"].action = "ConsultaOperacionesCorresponsal.do";
		if (document.getElementById("corresponsal").value == "inicial") {
			document.getElementById("operacion").value = "inicial";
		} else {
			document.getElementById("operacion").value = "consultarOperaciones";
		}
		document.forms["formaConsultaOperaciones"].submit();
	
}
function adelante() {
	document.getElementById("direccion").value = "A";
	consultar();
}

function atras() {
	document.getElementById("direccion").value = "B";
	consultar();
}

function exportar() {
	document.forms["formaConsultaOperaciones"].action = "ExportarOperacionesCorresponsal.do";
	document.forms["formaConsultaOperaciones"].submit();
}

function asociar() {
	document.forms["formaConsultaOperaciones"].action = "AsocOperacionesCorresponsal.do";
	document.getElementById("operacion").value = "inicial";
	document.forms["formaConsultaOperaciones"].submit();
}

function detalle(idOperacion) {
	document.forms["formaConsultaOperaciones"].action = "DetalleOperacionesCorresponsal.do";
	document.getElementById("operacion").value = "detalle";
	document.getElementById("idOperacion").value = idOperacion;
	document.forms["formaConsultaOperaciones"].submit();
}

function modificar(idOperacion) {
	document.forms["formaConsultaOperaciones"].action = "DetalleOperacionesCorresponsal.do";
	document.getElementById("operacion").value = "modificar";
	document.getElementById("idOperacion").value = idOperacion;
	document.forms["formaConsultaOperaciones"].submit();
}

function eliminar(idOperacion,tipOperCorr) {
	document.forms["formaConsultaOperaciones"].action = "EliminaOperacionesCorresponsal.do";
	document.getElementById("tipoOperCorr").value = tipOperCorr;
	document.getElementById("operacion").value = "eliminar";
	document.getElementById("idOperacion").value = idOperacion;
		jConfirm('¿Desea eliminar la operación?', 
		   	   	   'Eliminar operación',          
		   	       'ELIMOPE',                          
		   	       '',function(e){
						if(e){
							document.forms["formaConsultaOperaciones"].submit();						
							}
						return e;
					}); 
}

function regresar() {
	document.forms["formaConsultaOperaciones"].action = "ConsultaOperacionesCorresponsal.do";
	document.forms["formaConsultaOperaciones"].submit();
}

	<c:if test="${not empty  msgError}">
			jError('${msgError}',                               //DESCRIPCION ERROR
		   	   	   'Error en Consulta Operaciones', 			//TITULO ERROR
		   	   	   '${codError}',                               //CODIGO ERROR
		   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR		
	</c:if>
	
</script>

<form id="formaConsultaOperaciones" name="formaConsultaOperaciones" action="" method="post">
    <input type="hidden" id="SES" name="SES" value="${SES}" />
	<input type="hidden" id="operacion" name="operacion" value="default" />
	<input type="hidden" id="idOperacion" name="idOperacion" value="default" />
	<input type="hidden" id="direccion" name="direccion" value="" />
	<input type="hidden" id="tipoOperCorr" name="tipoOperCorr" value="" />
	<input type="hidden" id="nombre" name="nombre" value="" />
	
	<!-- Componente titulo de pagina -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Operaciones por corresponsal</span>
	</div>
	
	<!-- Componente formulario -->
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Filtro</div>
			<div class="contentFormularioB">
				<table>
					<tr>
						<td class="odd">*Corresponsal:</td>
						<td>
							<!-- FHS <select id="corresponsal" name="corresponsal" class="Campos" onchange="filtro();limpiaDatos();">-->
							<select id="corresponsal" name="corresponsal" class="Campos" onchange="javascript:consultar();">
										<option VALUE="inicial" selected="selected">----</option>
										<c:forEach var="resultado" items="${lista}">
											<option VALUE="${resultado.codigoCorresponsal}"<c:if test="${resultado.codigoCorresponsal==corresponsalBuscar}"> selected</c:if>>${resultado.nombreCorresponsal}</option>
										</c:forEach>
							</select>
						</td>
				</table>
			</div>
		<!--FHS <div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table>
					<tr>
						<td width="279" class="izq">&nbsp;</td>
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="der"><span><a href="javascript:consultar();">Consultar</a></span></td>
					</tr>
				</table>
			</div>
		</div>-->
	</div> 

<!-- corresponsalBuscar!=null -->
	<!-- Componente tabla estandar -->
	<div class="frameTablaEstandar" id="tablaResultados">
		<div class="titleTablaEstandar">
			Operaciones <span class="textosin">- Seleccione la acci&oacute;n</span>
		</div>
		<div class="contentTablaEstandar">
			<table width="100%">
				<thead>
					<tr>
						<th class="text_izquierda">C&oacute;digo</th>
						<th  class="text_centro">Nombre</th>
						<th  class="text_centro">Detalle</th>
						<th class="text_centro">Modificar</th>
						<th class="text_centro">Eliminar</th>
					</tr>
				</thead>
				<tr>
					<Td colspan="7" class="special"></Td>
				</tr>
				<tbody>
			    <c:if test="${listaOperaciones!=null}">
			    <c:forEach var="itemOper" items="${listaOperaciones}" >
			    <tr class="odd1">
						<td class="text_izquierda">${itemOper.codigoOperacion}</td>
						<td class="text_izquierda">${itemOper.desCorta}</td>
						<td class="text_centro" disabled="disabled">
							<a href="javascript:detalle('${itemOper.codigoOperacion}');" onclick="" ><div class="contentPieContenedor">Detalle</div></a>
						</td>
						<td class="text_centro">
							<a href="javascript:modificar('${itemOper.codigoOperacion}');" onclick="" ><div class="contentPieContenedor">Modificar</div></a>
						</td>
						<td class="text_centro">
							<a href="javascript:eliminar('${itemOper.codigoOperacion}','${itemOper.tipoOperacionCorresponsal}');" onclick="" ><div class="contentPieContenedor">Eliminar</div></a>
						</td>
			    	</tr>
			    </c:forEach>
			    </c:if>
				</tbody>
			</table>
		</div>
		<div class="paginador">
			<c:if test="${resultado.masAtras}">
				<a href="javascript:atras();">Atr&aacute;s</a>
			</c:if>
			<c:if test="${resultado.masAdelante}">
						<a href="javascript:adelante();">Adelante</a>
			</c:if>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table width="100%">
					<tr>
						<td <c:if test="${operacion != 'inicial'}"> class="izq"</c:if>><c:if test="${operacion != 'inicial'}"><span><a href="javascript:asociar();" onclick="" >Asociar</a></span></c:if></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="text_centro"><c:if test="${hayRegistros == true}"><span><a href="javascript:exportar();" onclick="" >Exportar</a></span></c:if></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="der"><span><a href="javascript:regresar();">Cerrar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	</form>
	
		<c:if test="${not empty  codError}">
		<script>
			jError('${msgError}',                               
		   	   	   'Error de operaciones', 
		   	   	   '${codError}',                          
		   	       '');                 		
		</script>
	</c:if>

	<c:if test="${not empty  codAviso}">
		<script>
			jAlert('${msgAviso}',                               
		   	   	   'Aviso Sucursales', 
		   	   	   '${codAviso}',                          
		   	       '');                 		
		</script>
	</c:if>
	