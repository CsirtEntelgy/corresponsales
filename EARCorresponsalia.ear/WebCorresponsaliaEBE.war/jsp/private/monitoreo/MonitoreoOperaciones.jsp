<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="monitoreo" />
	<jsp:param name="menuSubitem" value="monitorOperaciones" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/confis.js"></script>

<form id="formaConsultaConstanciaFiscal" action="" method="post">

	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Monitoreo de Operaciones</span>
	</div>
	
	<!-- Componente formulario -->
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Fitros</div>
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
						<td class="odd">Sucursal:</td>
						<td>
							<!--<select id="tipDoc" name="tipDoc" class="CamposCompletar" onchange="filtro();limpiaDatos();">-->
							<select id="tipDoc" name="tipDoc" class="Campos" disabled="disabled" onchange="filtro();limpiaDatos();">
										<option VALUE="uno" selected="selected">Arquitos</option>
										<option value="dos">Calesa</option>
										<option value="tres">5 de Febrero</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="odd">*Hora inicio:</td>
						<td>
							<input type="text" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="6" size="10" value="09:30"/>
						</td>
						<td class="odd">*Hora fin:</td>
						<td>
							<input type="text" class="Campos" disabled="disabled"  id="txtCuenta" name="txtCuenta"  maxlength="6" size="10" value="14:30"/>
						</td>
					</tr>
					<tr>
						<td class="odd">Operacion:</td>
						<td>
							<!--<select id="tipDoc" name="tipDoc" class="Campos_Des" onchange="filtro();limpiaDatos();">-->
							<select id="tipDoc" name="tipDoc" class="Campos" disabled="disabled" onchange="filtro();limpiaDatos();">										<option VALUE="uno" selected="selected">Todas</option>
										<option VALUE="uno">Pago en efectivo (TDC)</option>
										<option value="dos">Dep&oacute;sito en efectivo (TDD)</option>
							</select>
						</td>
						<td class="odd">Importe entre:</td>
						<td>
							<input type="text" class="Campos_Des" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="6" size="10"/>
						</td>
						<td class="odd"> y </td>
						<td>
							<input type="text" class="Campos_Des" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="6" size="10"/>
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
						<td width="279" class="der"><span><a href="" onclick="limpiar();" ></a>Limpiar</span></td>
					</tr>
				</table>
			</div>
		</div>  -->
	</div> 
		
	<!-- Componente tabla estandar -->
	<div class="frameTablaEstandar">
		<div class="titleTablaEstandar">Operaciones<span class="textosin"></span>
		</div>
		<div class="contentTablaEstandar">
			<table>
				<thead>
					<tr>
						<th width="200" class="text_centro">Sucursal</th>
						<th width="200" class="text_centro">Operaci&oacute;n</th>
						<th width="120" class="text_centro">No. Operaciones</th>
						<th width="120" class="text_centro">Total por operaci&oacute;n</th>
					</tr>
				</thead>
				<tr>
					<Td colspan="6" class="special"></Td>
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
						<td class="text_izquierda">Arquitos</td>
						<td class="text_izquierda">Dep&oacute;sito en efectivo (TDD)</td>
						<td class="text_izquierda">30</td>
						<td class="text_izquierda">2500</td>
			    	</tr>
			    	<tr class="odd2">
						<td class="text_izquierda">Arquitos</td>
						<td class="text_izquierda">Pago en efectivo (TDC)</td>
						<td class="text_izquierda">20</td>
						<td class="text_izquierda">10000</td>
			    	</tr>
				</tbody>
			</table>
		</div>
		<div class="framePieContenedor">
			<table>
				<tr>
			    	<td class="odd"> Total operado por el corresponsal:</td>
						<td>
							<input type="text" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="6" size="10" value="12500"/>
						</td>
			    	</tr>
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
						<td width="279" class="izq"><span><a href="../monitoreo/.do" onclick="" >Exportar</a></span></td>
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="der"><span><a href="../monitoreo/Constancia.do" onclick="" >Cerrar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</form>
