<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="canalcorresponsalia" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	function modificaOperaciones(codigoOperacion, parametroOperacion){
	document.getElementById("operacion").value=parametroOperacion;
	document.getElementById("codigoOperacion").value=codigoOperacion;
    	document.forms["formaConsultaOperaciones"].action = "MuestraAltaModificacion.do";
    	document.forms["formaConsultaOperaciones"].submit();
	}
	function detalleOperaciones(codigoOperacion){
	document.getElementById("codigoOperacion").value=codigoOperacion;
    	document.forms["formaConsultaOperaciones"].action = "DetalleOperacion.do";
    	document.forms["formaConsultaOperaciones"].submit();
	}
	function nuevoOperacion(parametroOperacion){
	document.getElementById("operacion").value=parametroOperacion;
    	document.forms["formaConsultaOperaciones"].action = "MuestraAltaModificacion.do";
    	document.forms["formaConsultaOperaciones"].submit();
	}
	function cerrar(){
    	document.forms["formaConsultaOperaciones"].action = "../principal/inicio.do";
    	document.forms["formaConsultaOperaciones"].submit();
	}	
	function exportar(){
    	document.forms["formaConsultaOperaciones"].action = "exportarOperaciones.do";
    	document.forms["formaConsultaOperaciones"].submit();
	}
	function consultar(avanzarRetroceder) {
    	document.forms["formaConsultaOperaciones"].opcAvanzarRetroceder.value=avanzarRetroceder; 
		document.forms["formaConsultaOperaciones"].action = "ConsultaOperaciones.do";
		document.forms["formaConsultaOperaciones"].submit();
	}
</script>

<form id="formaConsultaOperaciones" action="" method="post">
<input type="hidden" name="SES" value="${SES}" />
<input type="hidden" id="operacion" name="operacion" />
<input type="hidden" id="codigoOperacion" name="codigoOperacion" />
<input type="hidden" value="" id="opcAvanzarRetroceder" name="opcAvanzarRetroceder" />
<input type="hidden" value="${referenciaAvanzar}" id="referenciaAvanzar" name="referenciaAvanzar" />
<input type="hidden" value="${referenciaRetroceder}" id="referenciaRetroceder" name="referenciaRetroceder" />
<input type="hidden" value="${refContadorPag}" id="refContadorPag" name="refContadorPag" />
	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Operaciones para el canal</span>
	</div>
		
	<!-- Componente tabla estandar -->
	<div class="frameTablaEstandar">
		<div class="titleTablaEstandar">
			Operaciones <span class="textosin">- Seleccione la acci&oacute;n</span>
		</div>
		<div class="contentTablaEstandar">
			<table width="100%" >
				<thead>
					<tr>
						<th id="txtCodigo" class="text_izquierda">C&oacute;digo</th>
						<th id="txtNombre" class="text_centro">Nombre</th>
						<th id="txtDetalle" class="text_centro">Detalle</th>
						<th id="txModificar" class="text_centro">Modificar</th>
					</tr>
				</thead>
				<tr>
					<Td colspan="7" class="special"></Td>
				</tr>
				<tbody>
				
				<c:set var="estilo" value="odd1"/>
			    <c:forEach var="resultado" items="${registros}" >  
			    	<tr class="${estilo}">
						<td width="128" class="text_izquierda">${resultado.codigoOperacion}</td>
						<td class="text_izquierda">${resultado.desCorta}</td>
						<td class="text_centro">
							<a href="javascript:detalleOperaciones('${resultado.codigoOperacion}');"><div class="contentPieContenedor">Detalle</div></a>
						</td>
						<td class="text_centro">
							<a href="javascript:modificaOperaciones('${resultado.codigoOperacion}','M');" ><div class="contentPieContenedor">Modificar</div></a>
						</td>
			    	</tr>
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
			<c:if test="${not empty referenciaRetroceder}">
				<a href="javascript:consultar('B');">Anterior</a>
			</c:if>
			<c:if test="${not empty  referenciaAvanzar}">
				<a href="javascript:consultar('A');">Siguiente</a>
			</c:if>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table>
					<tr>
						<td width="279" class="izq">
						<c:if test="${empty  msgError}">
						    <span><a href="javascript:nuevoOperacion('A');" onclick="" >Nuevo</a></span>
						</c:if>
						</td>
						<td width="6" class="odd">&nbsp;</td>						
						<td width="279" class="text_centro"><span>
						<c:if test="${registros!=null && not empty registros}">
							<a href="javascript:exportar();"  >Exportar</a>
						</c:if>
						</span></td>						
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="der"><span><a href="javascript:cerrar();">Cerrar</a></span></td>
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
	
		<c:if test="${not empty  msgError}">
		<script>
			jAlert('${msgError}',                               //DESCRIPCION ERROR
		   	   	   'MSGERR', //TITULO ERROR
		   	   	   '',                               //CODIGO ERROR
		   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR		
		</script>
	</c:if>
	