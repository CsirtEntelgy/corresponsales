<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="mantenimiento" />
</jsp:include>

<script>
function eliminarCorresponsalia(eleReg){
		jConfirm('¿Estás seguro que deseas eliminar el registro?',
				'Confirmación eliminación',
				'',
				'',	function(e){
						if(e){
							document.forms["formaConsultaMttoCorresponsal"].regSel.value = eleReg;
				   			document.forms["formaConsultaMttoCorresponsal"].action = "BajaMttoCorresponsales.do";
				   			document.forms["formaConsultaMttoCorresponsal"].submit();
						}
					return e;
					});
}
	
	function activarDesactivar(actDesReg, estado){
		document.forms["formaConsultaMttoCorresponsal"].regSel.value = actDesReg;
		document.forms["formaConsultaMttoCorresponsal"].estadoActual.value = estado;
		document.forms["formaConsultaMttoCorresponsal"].action = "ActivarDesactivarCorresponsal.do";
		document.forms["formaConsultaMttoCorresponsal"].submit();
	}
		
	function modificaCorresponsalia(corresponsalia, corresponsal,paramModifica){
		document.getElementById("tipoAltaModifica").value=paramModifica;
		document.getElementById("idCorresponsalia").value=corresponsalia;
    	document.getElementById("idCorresponsal").value=corresponsal;
    	document.forms["formaConsultaMttoCorresponsal"].action = "MuestraAltaCorresponsalia.do";
    	document.forms["formaConsultaMttoCorresponsal"].submit();
	}
	function detalleCorresponsalia(corresponsalia, corresponsal){
    	document.getElementById("idCorresponsalia").value=corresponsalia;
    	document.getElementById("idCorresponsal").value=corresponsal;
    	document.forms["formaConsultaMttoCorresponsal"].action = "DetalleMttoCorresponsales.do";
    	document.forms["formaConsultaMttoCorresponsal"].submit();
	}
	function nuevaCorresponsalia(paramAlta){
		document.getElementById("tipoAltaModifica").value=paramAlta;
    	document.forms["formaConsultaMttoCorresponsal"].action = "MuestraAltaCorresponsalia.do";
    	document.forms["formaConsultaMttoCorresponsal"].submit();
	}
	function exportarCorresponsalia(){
    	document.forms["formaConsultaMttoCorresponsal"].action = "ExportarCorresponsalia.do";
    	document.forms["formaConsultaMttoCorresponsal"].submit();
	}
	function cerrar(){
    	document.forms["formaConsultaMttoCorresponsal"].action = "../principal/inicio.do";
    	document.forms["formaConsultaMttoCorresponsal"].submit();
	}
	
	function consultar(avanzarRetroceder){
	        var intPagina = 1;
	        intPagina = parseInt(document.forms['formaConsultaMttoCorresponsal'].paginaNum.value);
	        if( avanzarRetroceder == 'A'){
	          intPagina ++;
	        }else{
	          intPagina --;
	        } 			
	        document.forms['formaConsultaMttoCorresponsal'].paginaNum.value = '' +intPagina; 
	    	document.forms["formaConsultaMttoCorresponsal"].opcAvanzarRetroceder.value=avanzarRetroceder; 
    		document.forms["formaConsultaMttoCorresponsal"].action = "ConsultaMttoCorresponsales.do";
    		document.forms["formaConsultaMttoCorresponsal"].submit();
	}	
</script>

<form id="formaConsultaMttoCorresponsal" action="" method="post">
    <input type="hidden" id="SES" name="SES" value="${SES}" />
	<input type="hidden" id="regSel" name="regSel">
	<input type="hidden" id="estadoActual" name="estadoActual">
	<input type="hidden" id="idCorresponsalia" name="idCorresponsalia">
	<input type="hidden" id="idCorresponsal" name="idCorresponsal">
	<input type="hidden" id="tipoAltaModifica" name="tipoAltaModifica">
	<input type="hidden" value="" id="opcAvanzarRetroceder" name="opcAvanzarRetroceder" />
	<input type="hidden" value="${referenciaAvanzar}" id="referenciaAvanzar" name="referenciaAvanzar" />
	<input type="hidden" value="${referenciaRetroceder}" id="referenciaRetroceder" name="referenciaRetroceder" />
	<input type="hidden" value="${(empty paginaNum )? "1": paginaNum }" id = "paginaNum" name="paginaNum" >
	
	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Corresponsales</span>
	</div>

	<!-- Componente tabla estandar -->
	<div class="frameTablaEstandar">
		<div class="titleTablaEstandar">
			Corresponsales <span class="textosin">- Seleccione la acción</span>
		</div>
		<div class="contentTablaEstandar">
			<table>
				<thead>
					<tr>
						<th class="text_izquierda">Nombre</th>
						<th width="147" class="text_centro">Centro Costos</th>
						<th width="147" class="text_centro">Activo</th>
						<th width="147" class="text_centro">Detalle</th>
						<th width="147" class="text_centro">Modificar</th>
						<th width="147" class="text_centro">Eliminar</th>
					</tr>
				</thead>
				<tr>
					<Td colspan="6" class="special"></Td>
				</tr>
				<tbody>
				
				<c:set var="i" value="0"/>
				<c:set var="estilo" value="odd1"/>
			    <c:forEach var="resultadoConsulta" items="${listaRegistrosCanalCorresponsalia}" >  
			    	<tr class="${estilo}">
						<td width="128" class="text_izquierda">${resultadoConsulta.nombreCorresponsal}</td>
						<td class="text_izquierda">${resultadoConsulta.codigoCorresponsal}</td>
						<td class="text_centro"><div align="center" ><input type="checkbox" class="Campos" onclick="javascript:activarDesactivar('${i}','${resultadoConsulta.activo}')" <c:if test="${resultadoConsulta.estatusCorresponsal=='CS2'}"> checked="checked" </c:if>/></div></td>
						<td class="text_centro"><a href="javascript:detalleCorresponsalia('${resultadoConsulta.codigoCorresponsalia}','${resultadoConsulta.codigoCorresponsal}');">Detalle</a></td>
						<td class="text_centro"><a href="javascript:modificaCorresponsalia('${resultadoConsulta.codigoCorresponsalia}','${resultadoConsulta.codigoCorresponsal}', 'M');">Modificar</a></td>
						<td class="text_centro"><a href="javascript:eliminarCorresponsalia('${i}');">Eliminar</a></td>						
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
				<a href="javascript:consultar('B');">Anterior</a>
			</c:if>
			<c:if test="${hayAdelante}">
				<a href="javascript:consultar('A');">Siguiente</a>
			</c:if>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table>
					<tr>
						<td width="279" class="izq"><span><a href="javascript:nuevaCorresponsalia('A');" >Nuevo</a></span></td>
						<td width="6" class="odd">&nbsp;</td>					
						<td width="279" class="text_centro"><span>
						<c:if test="${listaRegistrosCanalCorresponsalia !=null && not empty listaRegistrosCanalCorresponsalia}">
							<a href="javascript:exportarCorresponsalia();">Exportar</a>
						</c:if>
						</span>
						</td>						
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="der"><span><a href="javascript:cerrar();">Cerrar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
		
	</div>
	</form>
	
	<c:if test="${not empty  codError}">
		<script>
			jError('${msgError}',                               //DESCRIPCION ERROR
		   	   	   'Corresponsalia', //TITULO ERROR
		   	   	   '${codError}',                               //CODIGO ERROR
		   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR		
		</script>
	</c:if>

	<c:if test="${not empty  codAviso}">
		<script>
			jAlert('${msgAviso}',                               //DESCRIPCION ERROR
		   	   	   'Corresponsalia', //TITULO ERROR
		   	   	   '${codAviso}',                               //CODIGO ERROR
		   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR		
		</script>
	</c:if>
	</body>
	</html>