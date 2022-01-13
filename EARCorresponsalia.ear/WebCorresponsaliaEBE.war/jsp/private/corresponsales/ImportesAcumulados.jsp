<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="importes" />
	<jsp:param name="menuSubitem" value="importesAcumulados" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/confis.js"></script>

<form id="formaConsultaConstanciaFiscal" action="" method="post">

	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Mantenimiento de Importes Acumulados Diarios por Cuenta</span>
	</div>
	
	<!-- Componente formulario -->
	<%
		String codClien = "";
		String cta = "";
		String credit = "";
		codClien = request.getParameter("txtCodClien")== null?"":request.getParameter("txtCodClien");
		cta = request.getParameter("txtCuenta")== null?"":request.getParameter("txtCuenta");
		credit = request.getParameter("txtCredito")== null?"":request.getParameter("txtCredito");
		
	//    SessionDTO perfil = (SessionDTO) request.getSession(false).getAttribute("confSession");
	 
	 
	 %>
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Para el canal de corresponsalia</div>
			<div class="contentFormularioB">
				<table>
					<tr>
						<td class="odd">*Importe acumulado permitido:</td>
						<td>
							<input type="text" value="5000" class="CamposCompletar" id="txtNombre" name="txtNombre" size="20"/>
						</td>
				</table>
			</div><!-- -->
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table>
					<tr>
						<td width="279" class="izq"><span><a href="" onclick="" >Guardar</a></span></td>
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="der"><span><a href="" onclick="limpiar();" ></a>Cerrar</span></td>
					</tr>
				</table>
			</div>
		</div>
	</div> 
		
	<!-- Componente tabla estandar -->
	<div class="frameTablaEstandar">
		<div class="titleTablaEstandar">
			Por corresponsal <span class="textosin">- Seleccione la acción</span>
		</div>
		<div class="contentTablaEstandar">
			<table>
				<thead>
					<tr>
						<th class="text_izquierda">Centro de costos</th>
						<th width="220" class="text_centro">Nombre</th>
						<th width="135" class="text_centro">Importe permitido</th>
						<th width="120" class="text_centro">Modificar</th>
					</tr>
				</thead>
				<tr>
					<Td colspan="7" class="special"></Td>
				</tr>
				<tbody><!--
				<%
					String estilo="odd1";				
				%>
			    <c:forEach var="tmplAsig" items="${listaOper}" >  
			    	<tr class="<%= estilo%>">
						<td width="128" class="text_izquierda">${tmplAsig.descPerfil}</td>
						<td class="text_izquierda">OXXO</td>
						<td class="text_izquierda">${tmplAsig.idAccion}</td>
						<td class="text_izquierda">${tmplAsig.idPerfil}</td>
						<td class="text_izquierda">${tmplAsig.descripcion}</td>
						<td class="text_izquierda">${tmplAsig.user}</td>
						<td class="text_izquierda">${tmplAsig.perfilNombre}</td>
						<td class="text_centro"><div align="center" ><input type="checkbox" class="Campos" onclick="return false;" <c:if test="${tmplAsig.valida}"> checked="checked" </c:if>/></div></td>
			    	</tr>
			    <%
			    	if (estilo.equals("odd1")) estilo="odd2";
			    	else estilo="odd1";
			    %>
			    </c:forEach>  --> 
			    <tr class="odd1">
						<td width="128" class="text_izquierda">COR0001</td>
						<td class="text_izquierda">OXXO</td>
						<td class="text_izquierda">5000</td>
						<td class="text_centro">
							<a href="../ConfisWeb/altaSucursal.do" onclick="" ><div class="contentPieContenedor">Modificar</div></a>
						</td>
			    	</tr>
			    	<tr class="odd2">
						<td width="128" class="text_izquierda">COR0002</td>
						<td class="text_izquierda">Extra</td>
						<td class="text_izquierda">5000</td>
						<td class="text_centro">
							<a href="../ConfisWeb/modificaOperaciones.do" onclick="" ><div class="contentPieContenedor">Modificar</div></a>
						</td>
			    	</tr>
			    	<tr class="odd1">
						<td width="128" class="text_izquierda">COR0003</td>
						<td class="text_izquierda">7Eleven</td>
						<td class="text_izquierda">5000</td>
						<td class="text_centro">
							<a href="../ConfisWeb/altaSucursal.do" onclick="" ><div class="contentPieContenedor">Modificar</div></a>
						</td>
			    	</tr>
				</tbody>
			</table>
		</div>
		<div class="paginador">
			<a href="#" onclick="paginaAnt();">Atrás</a> | 
			<a href="#" onclick="paginaSig();">Adelante</a>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table>
					<tr>
						<td width="279" class="izq"><span><a href="../ConfisWeb/Constancia.do" onclick="" >Exportar</a></span></td>
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="der"><span><a href="../ConfisWeb/Constancia.do" onclick="" >Cerrar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>