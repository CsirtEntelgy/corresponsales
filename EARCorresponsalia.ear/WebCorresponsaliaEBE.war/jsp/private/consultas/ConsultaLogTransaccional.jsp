<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"    prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt"%>

<jsp:include page="../myHeader.jsp" flush="true">
    <jsp:param name="menuItem"    value="consultas" />
    <jsp:param name="menuSubitem" value="logtrx" />
</jsp:include>
<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/comunes/validaciones.js"></script>
<script type="text/javascript">
	function ini(){
		createCalendar('txtFechaFin','calFechaFin');
		createCalendar('txtFechaIni','calFechaIni');
	}
	
	function consultar(direccioPaginacion){
    	if(validaCadena(document.getElementById("txtFechaIni").value, "Fecha Inicial",10,10,"Log Transaccional"))
			return;
    	if(validaCadena(document.getElementById("txtFechaFin").value, "Fecha Final",10,10,"Log Transaccional"))
			return;			
		if(document.getElementById("txtFolio").value!= '')
			if(validaNumeros(document.getElementById("txtFolio").value, "Folio", 1, 10,"Log Transaccional"))
			return;
		if(document.getElementById("txtTarjeta").value != '')
			if(validaNumeros(document.getElementById("txtTarjeta").value, "Número de tarjeta", 16, 16,"Sucursal"))
			return;
		var fechaSep;
		var fechaIni;
		var fechaFin;
		var fechaHoy = new Date();
		
		if(document.getElementById("txtFechaIni").value != ''){
		  fechaSep = document.getElementById("txtFechaIni").value.split("/");
		  fechaIni = new Date(fechaSep[2],fechaSep[1]-1,fechaSep[0]);
		}	
		if(document.getElementById("txtFechaFin").value != ''){
		  fechaSep = document.getElementById("txtFechaFin").value.split("/");
		  fechaFin = new Date(fechaSep[2],fechaSep[1]-1,fechaSep[0]);
		}
	    
	     
		if((fechaIni.getTime() > fechaFin.getTime())){
    	jAlert('Fecha inicial mayor a la fecha final : '+fechaIni.toLocaleDateString()+' -- '+fechaFin.toLocaleDateString(),                               //DESCRIPCION ERROR
		   	   	   'Log Transaccional', 						//TITULO ERROR
		   	   	   '---',                               //CODIGO ERROR
		   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR	
        
		
			return;
		}
		if (((fechaHoy.getTime()-7918173101) > fechaIni.getTime())||(fechaFin.getTime() - fechaIni.getTime()) > 518400000) {
			jAlert('Solo se permite consultar los 90 dias previos con un intervalo máximo de 7 días.',  
		   	   	   'Log Transaccional', 						//TITULO ERROR
		   	   	   '---',                               //CODIGO ERROR
		   	       '');
			return;
		}
		document.getElementById("direccionPaginacion").value = direccioPaginacion;		
		document.forms["formaConsultaLogTrx"].action = "RealizaConsultaLogTransaccional.do";
		if(direccioPaginacion == ''){
			document.getElementById("pagina").value = 1;
		}
    	document.forms["formaConsultaLogTrx"].submit();
	
	}

	function exportar(){
    	document.forms["formaConsultaLogTrx"].action = "exportLogTransac.do";
    	document.forms["formaConsultaLogTrx"].submit();
	} 

	function cerrar(){
    	document.forms["formaConsultaLogTrx"].action = "../principal/inicio.do";
    	document.forms["formaConsultaLogTrx"].submit();
	}

	function reset(){
    	document.forms["formaConsultaLogTrx"].action = "ConsultaLogTransaccional.do";
    	document.forms["formaConsultaLogTrx"].submit();
	}
	
	function cambiaFechaInicio(fechaIni){
	
		if (fechaIni!= ''){
			document.getElementById("txtFechaFin").value = fechaIni; 
		}
	}
</script>
<form id="formaConsultaLogTrx" action="" method="post">
    <input type="hidden" id="SES" name="SES" value="${SES}" />
    <input type="hidden" id="pagina" name="pagina" value="${pagina}" />
    <input type="hidden" id="direccionPaginacion" name="direccionPaginacion" value="" />
	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Log transaccional</span>
	</div>
	
	<!-- Componente formulario -->
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Fitros</div>
		<div class="contentFormularioB">
			<table>
				<tr>
					<td width="" class="odd">*Fecha inicio:</td>
					<td width="">
						<input onchange ="cambiaFechaInicio(this.value)" type="text" class="CamposCompletar"  id="txtFechaIni" name="txtFechaIni"  maxlength="10" size="14" value="${txtFechaIni}" readonly="readonly"/>
							<img id="calFechaIni" src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/calendar/calendar.png">
					</td>
					<td class="odd">*Fecha fin:</td>
					<td>
						<input type="text" class="CamposCompletar"  id="txtFechaFin" name="txtFechaFin"  maxlength="10" size="14" value="${txtFechaFin}" readonly="readonly"/>
							<img id="calFechaFin" src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/calendar/calendar.png">
					</td>
				</tr>
				<tr>
					<td class="odd">Folio:</td>
					<td>
						<input type="text" class="Campos" id="txtFolio" name="txtFolio"  maxlength="10" size="10" value="${txtFolio}"/>
					</td>
					<td class="odd">No. Tarjeta:</td>
					<td>
						<input type="text" class="Campos"   id="txtTarjeta" name="txtTarjeta"  maxlength="16" size="16" value="${txtTarjeta}"/>
					</td>
				</tr>
				<tr>
					<td class="odd">Operaci&oacute;n:</td>
					<td>
						<select id="operacion" name="operacion" class="Campos" >
							<option VALUE="" <c:if test="${operacion =='' }"> selected="selected"</c:if>>Todas</option>
							<option VALUE="280000" <c:if test="${operacion == '280000'}"> selected="selected"</c:if>>Dep&oacute;sito (TDD)</option>
							<option value="-280000"<c:if test="${operacion == '-280000'}"> selected="selected"</c:if>>Anulaci&oacute;n dep&oacute;sito (TDD)</option>
							<option VALUE="210000" <c:if test="${operacion == '210000'}"> selected="selected"</c:if>>Dep&oacute;sito (TDC)</option>
							<option value="-210000"<c:if test="${operacion == '-210000'}"> selected="selected"</c:if>>Anulaci&oacute;n dep&oacute;sito (TDC)</option>							
							<option VALUE="210000,280000" <c:if test="${operacion == '210000,280000,34000,32000'}"> selected="selected"</c:if>>Dep&oacute;sitos</option>
							<option value="-210000,-280000"<c:if test="${operacion == '-210000,-280000,-34000,-32000'}"> selected="selected"</c:if>>Anulaci&oacute;nes de dep&oacute;sitos</option>
							<option VALUE="340000" <c:if test="${operacion == '340000'}"> selected="selected"</c:if>>Dep&oacute;sito Cuenta Cheques</option>
							<option VALUE="-340000" <c:if test="${operacion == '-340000'}"> selected="selected"</c:if>>Anulaci&oacute;n de dep&oacute;sito Cuenta Cheques</option>
							<option VALUE="320000" <c:if test="${operacion == '320000'}"> selected="selected"</c:if>>Dep&oacute;sito Celular</option>
							<option VALUE="-320000" <c:if test="${operacion == '-320000'}"> selected="selected"</c:if>>Anulaci&oacute;n de dep&oacute;sito Celular</option>
						</select>
					</td>
					<td class="odd">Estatus de operaci&oacute;n:
					</td>
					<td>
						<select id="estatusOperacion" name="estatusOperacion" class="Campos">
							<option VALUE="" <c:if test="${estatusOperacion =='' }"> selected="selected"</c:if>>Todas</option>
							<option VALUE="0" <c:if test="${estatusOperacion == '0'}"> selected="selected"</c:if>>Exitosas</option>
							<option value="1"<c:if test="${estatusOperacion == '1'}"> selected="selected"</c:if>>Erroneas</option>
						</select>
					</td>
				</tr>
			</table>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table width="100%">
					<tr>
						<td class="izq"><span><a href="javascript: consultar('');">Consultar</a></span></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="der"><span><a href="javascript:reset();">Limpiar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div> 
		
	<!-- Componente tabla estandar -->
	
	<c:if test="${not empty listaConsultaLogTrx}">
	<div class="titleTablaEstandar">Transacciones
		<span class="textosin">
			: ${numRegistros} 
		</span>
		&nbsp;
		Importe total  
		<span class="textosin">
			$${totalImporte}
		</span>
	</div>
	<div class="contentTablaEstandar">		
		<table width="100%">
			<thead>
				<tr>
					<th width="" class="text_centro">Folio</th>
					<th width="" class="text_centro">Operaci&oacute;n</th>
					<th width="" class="text_centro">Fecha de operaci&oacute;n</th>
					<th width="" class="text_centro">Tarjeta</th>
					<th width="" class="text_centro">Cuenta</th>
					<th width="" class="text_centro">Importe</th>
					<th width="" class="text_centro">Tiempo de operaci&oacute;n</th>
					<th width="" class="text_centro">C&oacute;digo 390</th>
					<th width="" class="text_centro">Descripci&oacute;n</th>
					<th width="" class="text_centro">C&oacute;digo Respuesta</th>
				</tr>
			</thead>
			<tr>
				<Td colspan="9" class="special"></Td>
			</tr>
			<tbody>
			<%
				String estilo="odd1";				
			%>
		    <c:forEach var="tmplista" items="${listaConsultaLogTrx}" >  
		    	<tr class="<%= estilo%>">
					<td width="" class="text_derecha">${tmplista.folio}</td>
					<td class="text_izquierda">${tmplista.tipo}</td>
					<td class="text_izquierda">${tmplista.fechaInicio}</td>
					<td class="text_izquierda">${tmplista.numeroTarjeta}</td>
					<td class="text_izquierda">${tmplista.numeroCuenta}</td>
					<td class="text_izquierda">${tmplista.importe}</td>
					<td class="text_izquierda">${tmplista.duracion}</td>
					<td class="text_centro">${tmplista.codigoError}</td>
					<td class="text_centro">${tmplista.descripcionError}</td>
					<td class="text_centro">${tmplista.resultado}</td>
					
		    	</tr>
		    <%
		    	if (estilo.equals("odd1")) estilo="odd2";
		    	else estilo="odd1";
		    %>
		    </c:forEach>
			</tbody>
		</table>	
		<div class="paginador">
			<c:if test="${pagina > 1}">
				<a href="#" onclick="consultar('atras');">Anterior</a> |
			</c:if>
			<c:if test="${numRegistros > 0 && (numRegistros /20 >= pagina) }">
				&nbsp;Pagina: ${pagina} &nbsp;|			
				<a href="#" onclick="consultar('adelante');">Siguiente</a>
			</c:if>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table width="100%">
					<tr>
						<td class="izq"><span><a href="javascript:exportar();">Exportar</a></span></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="der"><span><a href="javascript:cerrar();" onclick="" >Cerrar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	</c:if>		
</form>
    <c:if test="${not empty  codError}">
        <script>
            jError('${msgError}',                               
                   'Error en Log Transaccional', 
                   '${codError}',                               
                   '');                                                 
        </script>
    </c:if>

    <c:if test="${not empty  codAviso}">
        <script>
            jAlert('${msgAviso}',                               
                   'Error en Log Transaccional', 
                   '${codAviso}',                               
                   '');                                                 
        </script>
    </c:if>
    