<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="sucursales" />
	<jsp:param name="menuSubitem" value="sucConsulta" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>

	var  codigoError = "<c:out value="${codPagError}"/>";  
	var  descError = "<c:out value="${msgPageError}"/>";
	
	if(codigoError != ""){
		    	jAlert(descError,                               //DESCRIPCION ERROR
		   	   	   codigoError, 						//TITULO ERROR
		   	   	   '---',                               //CODIGO ERROR
		   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR	
		
	}
	
function eliminarSucursales(){
	document.forms["formaConsultaSucursal"].action = "EliminacionSucursal.do";
	document.forms["formaConsultaSucursal"].submit();
}
function modificaSucursales(){
	document.forms["formaConsultaSucursal"].action = "ABModificacionSucursales.do?oper=M";
	document.forms["formaConsultaSucursal"].submit();
}
function detalleSucursales(){
	document.forms["formaConsultaSucursal"].action = "DetalleSucursal.do";
	document.forms["formaConsultaSucursal"].submit();
}
function nuevoSucursal(){
	document.forms["formaConsultaSucursal"].action = "ABModificacionSucursales.do?oper=A";
	document.forms["formaConsultaSucursal"].submit();
}
function cerrar(){
	document.forms["formaConsultaSucursal"].action = "../principal/inicio.do";
	document.forms["formaConsultaSucursal"].submit();
}
	
	
	
	
			
</script>

<form id="formaConsultaSucursal" action="" method="post">

	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Sucursales por corresponsal</span>
	</div>
	
	<!-- Componente formulario -->

	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Corresponsal a Consultar</div>
		<div class="contentFormularioB">
			<table>
				<tr>
					<td class="odd">*Corresponsal:</td>
						<td>
							<select id="corresponsal" name="corresponsal" class="Campos" onchange="filtro();limpiaDatos();">
										<option VALUE="0" selected="selected">Todo el canal</option>
										<c:forEach var="resultado" items="${lista}">
											<option VALUE="${resultado.codigoCorresponsal}"<c:if test="${resultado.codigoCorresponsal==corresponsalBuscar}"> selected</c:if>>${resultado.nombreCorresponsal}</option>
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
			Sucursales <span class="textosin">- Seleccione la acción</span>
		</div>
		<div class="contentTablaEstandar">
			<table>
				<thead>
					<tr>
						<th class="text_izquierda">C&oacute;digo</th>
						<th width="147" class="text_centro">Nombre</th>
						<th width="147" class="text_centro">Estatus</th>
						<th width="147" class="text_centro">Detalle</th>
						<th width="147" class="text_centro">Modificar</th>
						<th width="147" class="text_centro">Eliminar</th>
					</tr>
				</thead>
				<tr>
					<Td colspan="6" class="special"></Td>
				</tr>
				<tbody>
				<%
					String estilo="odd1";				
				%>
			    <c:forEach var="perfil" items="${listaParams}" >  
			    	<tr class="<%= estilo%>">
						<td width="128" class="text_izquierda">${perfil.perfilNombre}</td>
						<td class="text_izquierda">${perfil.descPerfil}</td>
						<td class="text_centro"><div align="center" ><input type="checkbox" class="Campos" onclick="return false;" <c:if test="${perfil.valida}"> checked="checked" </c:if>/></div></td>
						<td class="text_centro">
							<a href="DetalleMttoSucursales.do" onclick="" ><div class="contentPieContenedor">Detalle</div></a>
						</td>
						<td class="text_centro">
							<a href="ModificacionSucursal.do" onclick="" ><div class="contentPieContenedor">Modificar</div></a>
						</td>
						<td class="text_centro">
							<a href="BajaSucursal.do" onclick="eliminarParam('${perfil.idPerfil}');" ><div class="contentPieContenedor">Eliminar</div></a>
						</td>
			    	</tr>
			    <%
			    	if (estilo.equals("odd1")) estilo="odd2";
			    	else estilo="odd1";
			    %>
			    </c:forEach>				
			    </tbody>
			</table>
		</div>
		<c:if test="${listaParams !=null && not empty listaParams}">
		<div class="paginador">
			<a href="#" onclick="paginaAnt();">Atrás</a> | 
			<a href="#" onclick="paginaSig();">Adelante</a>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table>
					<tr>
						<td width="279" class="izq"><span><a href="javascript:nuevoSucursal();" onclick="" >Nuevo</a></span></td>
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="text_centro"><span><a href="ConsultaSucursal.do" onclick="limpiar();" >Exportar</a></span></td>
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="der"><span><a href="javascript:history.go(-1)" onclick="limpiar();" >Cerrar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
		</c:if>
	</div>
	
