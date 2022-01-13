<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="altamasivac" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type = "text/javascript">
function enviaArchivo() {
	if (document.getElementById("fbArchivo").value == "") {
		jAlert('No se ha seleccionado el archivo.',
				'Error en la carga',
				'',
				''
				);
	} else {
        document.getElementById("operacion").value="envioArchivo";	   
		document.forms["altaMasivaCorresponsales"].action = "AltaMasivaCorresponsales.do?SES=${SES}&operacion=envioArchivo";
		document.forms["altaMasivaCorresponsales"].submit();
	}
}
	<c:if test="${not empty  errorArchivo}">
			jError('Errores en la copia del archivo',                               //DESCRIPCION ERROR
		   	   	   'Resultado en Alta Masiva Corresponsales', 						//TITULO ERROR
		   	   	   '',                               //CODIGO ERROR
		   	       '${errorArchivo}');                                         		//TIP PARA QUE NO SUCEDA EL ERROR		
	</c:if>
</script>

<form id="altaMasivaCorresponsales" name="altaMasivaCorresponsales" method="post" enctype="multipart/form-data">
    <input type="hidden" id="SES" name="SES" value="${SES}" />
	<input type="hidden" id="operacion" name="operacion" value="default" />
	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Alta masiva de Corresponsales</span>
	</div>

		
	<div class="frameBuscadorSimple">
		<!-- Componente buscador simple -->
		<div class="titleBuscadorSimple">Alta de Corresponsales</div>
			<div class="contentBuscadorSimple">
				<table>
					<tbody>
						<tr>
							<td class="text_izquierda">
								Archivo:
								<input type="file" class="Campos_Des" id="fbArchivo" name="fbArchivo" />							
								<span width="100">&nbsp;&nbsp;&nbsp;</span>
								<span><a href="javascript:enviaArchivo();" >Cargar</a></span>
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
			    <c:forEach var="error" items="${errores}" >
				<tr class="odd1">
						<td class="text_izquierda" style="color:red">${error.key}</td>
						<td class="text_izquierda" style="color:red">${error.value}</td>
			    	</tr>
			    	</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
</form>
<div style="color:#C90212;" >
<strong> 
 ${aviso}
 </strong>
</div>
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