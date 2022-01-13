<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="consultas" />
	<jsp:param name="menuSubitem" value="consultasConciliacion" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/confis.js"></script>

<form id="formaConsultaConstanciaFiscal" action="" method="post">

	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Consulta de Consiliaci&oacute;n</span>
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
	 
	 
	 %><!-- -->
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Corresponsal a Consultar</div>
			<div class="contentFormularioB">
				<table>
					<tr>
						<td class="odd">*Corresponsal:</td>
						<td>
							<!--<select id="tipDoc" name="tipDoc" class="CamposCompletar" onchange="filtro();limpiaDatos();">-->
							<select id="tipDoc" name="tipDoc" class="Campos" disabled="disabled" onchange="filtro();limpiaDatos();">
										<option VALUE="uno" selected="selected">OXXO</option>
										<option value="dos">7Eleven</option>
										<option value="tres">Extra</option>
							</select>
						</td>
					</tr>
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
	<div class="frameTablaEstandar">
		<div class="titleTablaEstandar">
			Corresponsales <span class="textosin">- Seleccione la acción</span>
		</div>
		<div class="contentTablaEstandar">
			<table>
				<thead>
					<tr>
						<th class="text_izquierda">Corresponsal</th>
						<th width="147" class="text_centro">Referenc&iacute;a</th>
						<th width="147" class="text_centro">Tipo de operaci&oacute;n.</th>
						<th width="147" class="text_centro">Monto</th>
						<th width="147" class="text_centro">Fecha</th>
						<th width="147" class="text_centro">Hora</th>
						<th width="147" class="text_centro">Estatus</th>
						<th width="147" class="text_centro">Aut. Conciliar</th>
						<th width="147" class="text_centro">Cancelar</th>
					</tr>
				</thead>
				<tr>
					<Td colspan="9" class="special"></Td>
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
						<td class="text_izquierda">OXXO</td>
						<td class="text_izquierda">REF123456789</td>
						<td class="text_izquierda">Pago en efectivo (TDC)</td>
						<td class="text_izquierda">10000</td>
						<td width="128" class="text_izquierda">12/03/2012</td>
						<td class="text_izquierda">09:00</td>
						<td class="text_izquierda">Pendiente de compensar</td>
						<td class="text_centro"><div align="center" ><input type="checkbox" class="Campos" onclick=""/></div></td>
						<td class="text_centro">
							<a href="../ConfisWeb/excepcionOperaciones.do" onclick="" ><div class="contentPieContenedor">Cancelar</div></a>
						</td>
			    	</tr>
			    	<tr class="odd2">
						<td class="text_izquierda">OXXO</td>
						<td class="text_izquierda">REF123456790</td>
						<td class="text_izquierda">Pago en efectivo (TDC)</td>
						<td class="text_izquierda">10000</td>
						<td width="128" class="text_izquierda">12/03/2012</td>
						<td class="text_izquierda">09:00</td>
						<td class="text_izquierda">Pendiente de conciliar</td>
						<td class="text_centro"><div align="center" ><input type="checkbox" class="Campos" onclick=""/></div></td>
			  			<td class="text_centro">
							<a href="../ConfisWeb/excepcionOperaciones.do" onclick="" ><div class="contentPieContenedor">Cancelar</div></a>
						</td>  	
			    	</tr>
			    	<tr class="odd1">
						<td class="text_izquierda">OXXO</td>
						<td class="text_izquierda">REF123456791</td>
						<td class="text_izquierda">Pago en efectivo (TDC)</td>
						<td class="text_izquierda">10000</td>
						<td width="128" class="text_izquierda">12/03/2012</td>
						<td class="text_izquierda">09:05</td>
						<td class="text_izquierda">Pendiente de conciliar</td>
						<td class="text_centro"><div align="center" ><input type="checkbox" class="Campos" onclick=""/></div></td><td class="text_centro">
							<a href="../ConfisWeb/excepcionOperaciones.do" onclick="" ><div class="contentPieContenedor">Cancelar</div></a>
						</td>
			    	</tr>
			    	<tr class="odd2">
						<td class="text_izquierda">OXXO</td>
						<td class="text_izquierda">REF123456792</td>
						<td class="text_izquierda">Pago en efectivo (TDC)</td>
						<td class="text_izquierda">10000</td>
						<td width="128" class="text_izquierda">12/03/2012</td>
						<td class="text_izquierda">09:08</td>
						<td class="text_izquierda">Pendiente de conciliar</td>
						<td class="text_centro"><div align="center" ><input type="checkbox" class="Campos" onclick=""/></div></td><td class="text_centro">
							<a href="../ConfisWeb/excepcionOperaciones.do" onclick="" ><div class="contentPieContenedor">Cancelar</div></a>
						</td>
			    	</tr>
			    	<tr class="odd1">
						<td class="text_izquierda">OXXO</td>
						<td class="text_izquierda">REF123456793</td>
						<td class="text_izquierda">Pago en efectivo (TDC)</td>
						<td class="text_izquierda">10000</td>
						<td width="128" class="text_izquierda">12/03/2012</td>
						<td class="text_izquierda">09:10</td>
						<td class="text_izquierda">Pendiente de conciliar</td>
						<td class="text_centro"><div align="center" ><input type="checkbox" class="Campos" onclick=""/></div></td><td class="text_centro">
							<a href="../ConfisWeb/excepcionOperaciones.do" onclick="" ><div class="contentPieContenedor">Cancelar</div></a>
						</td>
			    	</tr>
			    	<tr class="odd2">
						<td class="text_izquierda">OXXO</td>
						<td class="text_izquierda">REF123456794</td>
						<td class="text_izquierda">Pago en efectivo (TDC)</td>
						<td class="text_izquierda">10000</td>
						<td width="128" class="text_izquierda">12/03/2012</td>
						<td class="text_izquierda">09:15</td>
						<td class="text_izquierda">Pendiente de conciliar</td>
						<td class="text_centro"><div align="center" ><input type="checkbox" class="Campos" onclick=""/></div></td><td class="text_centro">
							<a href="../ConfisWeb/excepcionOperaciones.do" onclick="" ><div class="contentPieContenedor">Cancelar</div></a>
						</td>
			    	</tr>
			    	<tr class="odd1">
						<td class="text_izquierda">OXXO</td>
						<td class="text_izquierda">REF123456795</td>
						<td class="text_izquierda">Pago en efectivo (TDC)</td>
						<td class="text_izquierda">10000</td>
						<td width="128" class="text_izquierda">12/03/2012</td>
						<td class="text_izquierda">09:15</td>
						<td class="text_izquierda">Pendiente de conciliar</td>
						<td class="text_centro"><div align="center" ><input type="checkbox" class="Campos" onclick=""/></div></td><td class="text_centro">
							<a href="../ConfisWeb/excepcionOperaciones.do" onclick="" ><div class="contentPieContenedor">Cancelar</div></a>
						</td>
			    	</tr>
			    	<tr class="odd2">
						<td class="text_izquierda">OXXO</td>
						<td class="text_izquierda">REF123456796</td>
						<td class="text_izquierda">Pago en efectivo (TDC)</td>
						<td class="text_izquierda">10000</td>
						<td width="128" class="text_izquierda">12/03/2012</td>
						<td class="text_izquierda">09:18</td>
						<td class="text_izquierda">Pendiente de conciliar</td>
						<td class="text_centro"><div align="center" ><input type="checkbox" class="Campos" onclick=""/></div></td><td class="text_centro">
							<a href="../ConfisWeb/excepcionOperaciones.do" onclick="" ><div class="contentPieContenedor">Cancelar</div></a>
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
						<td width="279" class="izq"><span><a href="../ConfisWeb/Constancia.do" onclick="" >Guardar</a></span></td>
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="text_centro"><span><a href="#" onclick="limpiar();" >Exportar</a></span></td>
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="der"><span><a href="#" onclick="limpiar();" >Cerrar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>