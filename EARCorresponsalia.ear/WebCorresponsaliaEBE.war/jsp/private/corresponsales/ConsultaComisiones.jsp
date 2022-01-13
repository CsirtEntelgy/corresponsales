<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="comisiones" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>

function consultaComisiones(avanzarRetroceder){
   	document.forms["formaConsultaComisiones"].opcAvanzarRetroceder.value=avanzarRetroceder; 
 	document.forms["formaConsultaComisiones"].action = "ConsultaComisiones.do";
	document.forms["formaConsultaComisiones"].submit();
}
	
function modificaComisiones(numReg){
	document.forms["formaConsultaComisiones"].regMod.value = numReg;
	document.forms["formaConsultaComisiones"].action = "ModificarComision.do";
	document.forms["formaConsultaComisiones"].submit();
}

function asignarComisiones(){
	document.forms["formaConsultaComisiones"].action = "AsignarComision.do";
	document.forms["formaConsultaComisiones"].submit();
}

function exportaComisiones(){
    	document.forms["formaConsultaComisiones"].action = "ExportarComisiones.do";
    	document.forms["formaConsultaComisiones"].submit();
}

function cerrar(){
	document.forms["formaConsultaComisiones"].action = "MuestraCorresponsalesComision.do";
	document.forms["formaConsultaComisiones"].submit();
}
	
			
</script>

<form id="formaConsultaComisiones" action="" method="post">
    <input type="hidden" id="SES" name="SES" value="${SES}" />
	<input type="hidden" value="" id="regMod" name="regMod" />
	<input type="hidden" value="" id="opcAvanzarRetroceder" name="opcAvanzarRetroceder" />
	

	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Comisiones por operaciones</span>
	</div>
	
	<!-- Componente formulario -->

	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Comisiones a Consultar</div>
		<div class="contentFormularioB">
			<table>
				<tr>
					<td class="odd">* Corresponsal:</td>
						<td>
							<select id="cmbNombreCorresponsal" name="cmbNombreCorresponsal" class="Campos" onchange="javascript:consultaComisiones(' ');">
										<option VALUE="0" selected="selected">Todo el canal</option>
										<c:forEach var="resultado" items="${listaCorresponsalesComision}">
											<option VALUE="${resultado.codigoCorresponsal}"<c:if test="${resultado.codigoCorresponsal==corresponsaliaSeleccionada}"> selected</c:if>>${resultado.nombreCorresponsal}</option>
										</c:forEach>
							</select>
						</td>
				</tr>
			</table>
		</div>	
	</div>
	<!-- Componente tabla estandar -->

	<div class="frameTablaEstandar">
		<div class="titleTablaEstandar">
			Comisiones <span class="textosin">- Los montos son en Pesos Mexicanos</span>
		</div>
		<div class="contentTablaEstandar">
			<table>
				<thead>
					<tr>
						<th class="text_centro">Operaci&oacute;n</th>
						<th width="147" class="text_centro">Comisi&oacute;n total</th>
						<th width="147" class="text_centro">Pagada por el cliente</th>
						<th width="147" class="text_centro">Pagada por el banco</th>
						<th width="147" class="text_centro">I.V.A.</th>
						<th width="147" class="text_centro">Modificar</th>
					</tr>
				</thead>
				<tr>
					<Td colspan="6" class="special"></Td>
				</tr>
				<tbody>
				<c:set var="i" value="0"/>
				<c:set var="estilo" value="odd1"/>
			    <c:forEach var="datos" items="${registrosComisiones}" >  
			    	<tr class="${estilo}">
						<td width="128" class="text_izquierda">${datos.descripcionOperacion}</td>
						<td class="text_derecha">${datos.importeComisionTotalFront}</td>
						<td class="text_derecha">${datos.importeComisionClienteMontoFijoFront}</td>
						<td class="text_derecha">${datos.importeComisionBancoMontoFijoFront}</td>
						<td class="text_derecha">${datos.ivaTotalComisionFront}</td>
						<td class="text_centro"><a href="javascript:modificaComisiones('${i}');">Modificar</a></td>						
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
		    <!-- 
			<a href="javascript:consultaComisiones('R')">Atrás</a> | 
			<a href="javascript:consultaComisiones('A')">Adelante</a>
			 -->
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table width="100%">
					<tr>
						<td ${(corresponsaliaSeleccionada > 0) ? 'class="izq"' :''}><span><a href="javascript:asignarComisiones();"><c:if test="${corresponsaliaSeleccionada > 0}">Asignar</c:if></a></span></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="text_centro"><span>
						<c:if test="${registrosComisiones!=null && not empty registrosComisiones}">
						<a href="javascript:exportaComisiones();">Exportar</a>
						</c:if>
						</span></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="der"><span><a href="javascript:cerrar()">Cerrar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
		
	</div>
	
</form>

<c:if test="${not empty  msgAviso}">
		<script>
			jAlert('${msgAviso}',                               //DESCRIPCION ERROR
		   	   	   'Alerta Consulta Comisiones', //TITULO ERROR
		   	   	   '${codAviso}',                               //CODIGO ERROR
		   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR		
		</script>
</c:if>