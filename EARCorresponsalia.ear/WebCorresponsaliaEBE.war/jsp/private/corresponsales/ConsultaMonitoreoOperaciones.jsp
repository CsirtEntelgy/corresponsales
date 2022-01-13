<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="consultaComisiones" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	alert("asd");
function exportarMonitoreoOperacion(){
alert("exportar");
	//document.forms["formaModificaComisiones"].action = "ModificarComisiones.do";
	//document.forms["formaModificaComisiones"].submit();
}
function cerrar(){
	document.forms["formaModificaComisiones"].action = "../principal/inicio.do";
	document.forms["formaModificaComisiones"].submit();
}
</script>

<form id="formaConsultaMinitoreoOperaciones" action="" method="post">
<input type="hidden" id="codigoCorresponsalia" name="codigoCorresponsalia" />
<input type="hidden" id="nombreCorresponsal" name="nombreCorresponsal" />
<input type="hidden" id="descripcionOperacion" name="descripcionOperacion" />
<input type="hidden" id="entidad" name="entidad" />


	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Monitoreo - Operaciones</span>
	</div>
	
	<!-- Componente formulario -->

	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Filtros</div>
		<div class="contentFormularioB">
			<table>
				<tr>
					<td class="odd">* Corresponsal:</td>
						<td>
							<select id="cmbNombreCorresponsal" name="cmbNombreCorresponsal" class="Campos" >
									<option VALUE="${codigoCorresponsalia}" >${nombreCorresponsal}</option>
							</select>
						</td>
						<td class="odd">* Sucursal:</td>
						<td>
							<select id="cmbSucursal" name="cmbSucursal" class="Campos" >
									<option >${sucursal}</option>
							</select>
						</td>
						<td></td>
				</tr>
				<tr>
					<td class="odd">* Hora Inicio:</td>
					<td><input type="text" class="CamposCompletar"  id="txtHoraInicio" name="txtHoraInicio" maxlength="30" size="" /></td>
					<td class="odd">* Hora Fin:</td>
					<td><input type="text" class="CamposCompletar"  id="txtHoraFin" name="txtHoraFin" maxlength="30" size="" /></td>
					<td></td>					
				</tr>
				<tr>
					<td class="odd">Operaci&oacuten:</td>
						<td>
							<select id="cmbOperaciones" name="cmbOperaciones" class="Campos" >
									<option VALUE="${operaciones}" >${operaciones}</option>
							</select>
						</td>
						<td class="odd">* Importe entre:</td>
						<td><input type="text" class="CamposCompletar"  id="txtImporteMinimo" name="txtImporteMinimo" maxlength="30" size="" /></td>
							y
						<td><input type="text" class="CamposCompletar"  id="txtImporteMaximo" name="txtImporteMaximo" maxlength="30" size="" /></td>			
				</tr>
			</table>
		</div>	
	</div>
	
<c:if test="${consultaMonitoreoOperaciones!=null}">
	<div class="frameTablaEstandar">
		<div class="titleTablaEstandar">
			Comisiones <span class="textosin">- Los montos son en Pesos Mexicanos</span>
		</div>
		<div class="contentTablaEstandar">
			<table>
				<thead>
					<tr>
						<th class="text_izquierda">Sucursal</th>
						<th width="147" class="text_centro">Operaci&oacute;n</th>
						<th width="147" class="text_centro">No. Operaciones</th>
						<th width="147" class="text_centro">Total por operaci&oacute;n</th>
					</tr>
				</thead>
				<tr>
					<Td colspan="6" class="special"></Td>
				</tr>
				<tbody>
				<%
					String estilo="odd1";				
				%>
			    <c:forEach var="datos" items="${registrosMonitoreo}" >  
			    	<tr class="<%= estilo%>">
						<td width="128" class="text_izquierda">${datos.sucursal}</td>
						<td class="text_izquierda">${datos.operacion}</td>
						<td class="text_izquierda">${datos.numeroOperacion}</td>
						<td class="text_izquierda">${datos.totalOperacion}</td>						
			    	</tr>
			    <%
			    	if (estilo.equals("odd1")) estilo="odd2";
			    	else estilo="odd1";
			    %>
			    </c:forEach>
			    <tr>
					<td class="odd">Total Operado por el corresponsal :</td>
					<td><input type="text" class="CamposCompletar"  disabled = "disabled" id="txtTotalOperadoCorresponsal" name="txtTotalOperadoCorresponsal" maxlength="10" size="" /></td>
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
						<td width="279" class="izq"><span>
						<c:if test="${registrosMonitoreo !=null && not empty registrosMonitoreo}">
							<a href="javascript:exportarMonitoreoOperacion();">Exportar</a>
						</c:if>
						</span></td>
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="der"><span><a href="javascript:cerrar()" >Cerrar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	</c:if>
</form>
