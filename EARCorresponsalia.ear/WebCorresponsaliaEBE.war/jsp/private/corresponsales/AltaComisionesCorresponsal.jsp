<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="comisiones" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/confis.js"></script>

<form id="formaConsultaConstanciaFiscal" action="" method="post">

	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Comisiones por Corresponsal-Operaci&oacute;n</span>
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
		<div class="titleBuscadorSimple">Corresponsal- Operaci&oacute;n</div>
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
						<td class="odd">*Operaci&oacute;n:</td>
						<td>
							<!--<select id="tipDoc" name="tipDoc" class="CamposCompletar" onchange="filtro();limpiaDatos();">-->
							<select id="tipDoc" name="tipDoc" class="Campos" disabled="disabled" onchange="filtro();limpiaDatos();">
										<option VALUE="uno" selected="selected">Pago en efectivo (TDC)</option>
										<option value="dos">Depocito en efectivo (TDD)</option>
							</select>
						</td>
					</tr>
				</table>
			</div> <!--
			<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table>
					<tr>
						<td width="279" class="izq"><span><a href="../ConfisWeb/Constancia.do" onclick="" >Modificar</a></span></td>
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="der"><span><a href="#" onclick="limpiar();" ></a>Consultar</span></td>
					</tr>
				</table>
			</div>-->
			
		<div class="titleBuscadorSimple">Pago en efectivo (TDC)<span class="textosin"> - Montos a pagar (Pesos)</span></div>
			<div class="contentFormularioB">
				<table>
					<tr>
						<td class="odd">*Por el cliente:</td>
						<td>
							<input type="text" value="7.00" class="CamposCompletar" id="txtNombre" name="txtNombre" size="20"/>							
						</td>
						<td class="odd">*Por el banco:</td>
						<td>
							<input type="text" value="8.00" class="CamposCompletar" id="txtNombre" name="txtNombre" size="20"/>							
						</td>
					</tr>
					<tr>
						<td class="odd">*Total:</td>
						<td>
							<input type="text" value="15.00" class="CamposCompletar" id="txtNombre" name="txtNombre" size="20"/>							
						</td>
					</tr>
				</table>
			</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table>
					<tr>
						<td width="279" class="izq"><span><a href="#" onclick="guardar();" >Guardar</a></span></td>
					</tr>
				</table>
			</div>
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
						<th class="text_izquierda">Operaci&oacute;n</th>
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
				<tbody><!--
				<%
					String estilo="odd1";				
				%>
			    <c:forEach var="perfil" items="${listaParams}" >  
			    	<tr class="<%= estilo%>">
						<td width="128" class="text_izquierda">${perfil.perfilNombre}</td>
						<td class="text_izquierda">${perfil.descPerfil}</td>
						<td class="text_centro"><div align="center" ><input type="checkbox" class="Campos" onclick="return false;" <c:if test="${perfil.valida}"> checked="checked" </c:if>/></div></td>
						<td class="text_centro">
							<a href="../ConfisWeb/ConsGenCorresp.do" onclick="" ><div class="contentPieContenedor">Detalle</div></a>
						</td>
						<td class="text_centro">
							<a href="../ConfisWeb/ModCorresp.do" onclick="" ><div class="contentPieContenedor">Modificar</div></a>
						</td>
						<td class="text_centro">
							<a href="#" onclick="eliminarParam('${perfil.idPerfil}');" ><div class="contentPieContenedor">Eliminar</div></a>
						</td>
			    	</tr>
			    <%
			    	if (estilo.equals("odd1")) estilo="odd2";
			    	else estilo="odd1";
			    %>
			    </c:forEach>-->
			    <tr class="odd1">
						<td width="128" class="text_izquierda">Dep&oacute;sito en efectivo (TDD)</td>
						<td class="text_izquierda">15.00</td>
						<td class="text_izquierda">7.00</td>
						<td class="text_izquierda">5.93</td>
						<td class="text_izquierda">2.07</td>
						<td class="text_centro">
							<a href="../ConfisWeb/altaSucursal.do" onclick="" ><div class="contentPieContenedor">Modificar</div></a>
						</td>
					</tr>
			    	<tr class="odd2">
						<td width="128" class="text_izquierda">Pago en efectivo (TDC)</td>
						<td class="text_izquierda">15.00</td>
						<td class="text_izquierda">7.00</td>
						<td class="text_izquierda">5.93</td>
						<td class="text_izquierda">2.07</td>
						<td class="text_centro">
							<a href="../ConfisWeb/altaSucursal.do" onclick="" ><div class="contentPieContenedor">Modificar</div></a>
						</td>
			    	</tr>
				</tbody>
			</table>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table>
					<tr>
						<td width="279" class="izq"><span><a href="#" onclick="limpiar();" >Asignar</a></span></td>
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="text_centro"><span><a href="#" onclick="limpiar();" >Exportar</a></span></td>
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="der"><span><a href="#" onclick="limpiar();" >Cerrar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
		
		
		
