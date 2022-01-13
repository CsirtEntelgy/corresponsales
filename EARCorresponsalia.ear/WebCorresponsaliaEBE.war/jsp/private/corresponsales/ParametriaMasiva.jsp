<jsp:include page="../myHeader.jsp" flush="true" >
	<jsp:param name="menuItem" value="corresponsales" />
	<jsp:param name="menuSubitem" value="paramasiva" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
function enviaArchivo() {
	if (document.getElementById("fbArchivo").value == "") {
		jAlert('No se ha seleccionado el archivo.',
				'Error en la carga',
				'',
				''
				);
	} else {
	        if(document.getElementById("tipoArchivo").value == "0"){
	        jAlert('Seleccione un tipo de archivo.',
				'Error en la carga',
				'',
				''
				);
	        }
	        else {
	              ejecutaEnvio();
	        }
		
	}
}
function ejecutaEnvio(){
    if (document.getElementById("tipoArchivo").value == "1"){
        document.forms["parametriaMasiva"].action = "ParaMasiva.do?operacion=envioContactos&SES=${SES}";
	    document.getElementById("operacion").value = "envioContactos";
	    document.forms["parametriaMasiva"].submit();
    }
    if (document.getElementById("tipoArchivo").value == "2"){
        document.forms["parametriaMasiva"].action = "ParaMasiva.do?operacion=envioComisiones&SES=${SES}";
	    document.getElementById("operacion").value = "envioComisiones";
	    document.forms["parametriaMasiva"].submit();
    }
    if (document.getElementById("tipoArchivo").value == "3"){
        document.forms["parametriaMasiva"].action = "ParaMasiva.do?operacion=envioParametria&SES=${SES}";
	    document.getElementById("operacion").value = "envioParametria";
	    document.forms["parametriaMasiva"].submit();
    }
}
	<c:if test="${not empty  errorArchivo}">
			jError('Errores en la copia del archivo',                               //DESCRIPCION ERROR
		   	   	   'Resultado en Parametría Masiva', 						//TITULO ERROR
		   	   	   '',                               //CODIGO ERROR
		   	       '${errorArchivo}');                                         		//TIP PARA QUE NO SUCEDA EL ERROR		
	</c:if>

</script>

<form id="parametriaMasiva" name="parametriaMasiva"
	method="post" enctype="multipart/form-data">
	<input type="hidden"id="operacion" name="operacion" value="default" />
	<input type="hidden" id="SES" name="SES" value="${SES}" /> 	
	<!-- Componente titulo de página -->
<div class="pageTitleContainer"><span class="pageTitle">Parametría Masiva</span></div>


<div class="frameBuscadorSimple"><!-- Componente buscador simple -->
	<div class="titleBuscadorSimple">Carga de Archivo</div>
	<div class="contentBuscadorSimple">
		<table>
			<tbody>
				<tr>
					<td width="30%">
			   			<select id="tipoArchivo" name="tipoArchivo" style="width: 180px;" class="CamposCompletar">
				   			<option value="0" selected="selected">Tipo de archivo a cargar</option>
				   			<option value="1">Contactos</option>
				   			<option value="2">Comisiones</option>
				   			<option value="3">Parametría Operativa</option>
						</select>
					</td>
			   		<td class="text_izquierda" width="50%" >Archivo: 
			   			<input type="file" class="Campos_Des" id="fbArchivo" name="fbArchivo"/>
			   			<span>&nbsp;&nbsp;&nbsp;<a href="javascript:enviaArchivo();">Cargar</a>
			   			</span>
		    		</td>
		    		<td width="20%"></td>
				</tr>
			</tbody>
		</table>	
	</div>
</div>
	<c:if test="${errores!=null}">
	<div class="frameTablaEstandar" id="tablaResultados">
		<div class="contentTablaEstandar">
			<table width="100%">
				<thead>
					<tr>
						<th width="33%" class="text_centro">Registros Importados</th>
						<th width="33%" class="text_centro">Registros Exitosos</th>
						<th width="34%" class="text_centro">Registros con Error</th>
					</tr>
				</thead>
				<tr>
					<td colspan="3" class="special"></Td>
				</tr>
				<tbody>
					<tr class="odd1">
						<td  class="text_izquierda">${totalRegistros}</td>
						<td  class="text_izquierda">${totalExitosos}</td>
						<td  class="text_izquierda">${totalErrores}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="contentTablaEstandar">
			<table width="100%">
				<thead>
					<tr>
						<th width="30%" class="text_izquierda">L&iacute;nea</th>
						<th width="70%" class="text_centro">Descripci&oacute;n</th>
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
	</div>
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