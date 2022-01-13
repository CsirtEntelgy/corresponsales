<jsp:include page="../myHeader.jsp" flush="true" >
	<jsp:param name="menuItem" value="corresponsales" />
	<jsp:param name="menuSubitem" value="asocOperCorresp" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/comunes/validaciones.js"></script>

<script type = "text/javascript">
function guardar() {
	document.getElementById("corresponsal").disabled=false;
	document.getElementById("operacion");
	document.forms["formaDetOperaciones"].action = "GuardaOperacionesCorresponsal.do";
	
	if(validaNumeros(document.getElementById("txtCodigoOperacion").value, "Cve. de asociación", 1, 10,"Operaciones"))
		return;
	if(validaCadenaTrx(document.getElementById("txtCveTransaccion").value, "Transacción","Operaciones"))
		return;
	if(validaImporte(document.getElementById("txtImporteMaximoOperacionFormateado").value, "Importe máximo","Operaciones"))
		return;
	if(validaImporte(document.getElementById("txtImporteMinimoOperacionFormateado").value, "Importe minimo","Operaciones"))
		return;
	if(validaImporte(document.getElementById("txtAcumuladoDiarioFormateado").value, "Acumulado diario","Operaciones"))
		return;
	if(validaHora(document.getElementById("txtHoraInicioFormateada").value, "Hora inicio","Operaciones"))
		return;
	if(validaHora(document.getElementById("txtHoraFinalFormateada").value, "Hora fin", "Operaciones"))
		return;
	if(validaImporte(document.getElementById("txtAcumuladoMensualFormateado").value, "Acumulado mensual","Operaciones"))
		return;
	if(validaNumeros(document.getElementById("txtCodigoOperacionTransaccion").value, "Clacon",4,4,"Operaciones"))
		return;
	if(validaNumeros(document.getElementById("txtCodigoOperacionContraOper").value, "Clacon contraoperación",4,4,"Operaciones"))
		return;
	document.forms["formaDetOperaciones"].corresponsal.value = document.forms["formaDetOperaciones"].corresponsal1.value;
	
	document.forms["formaDetOperaciones"].submit();
}
function regresar() {
	document.forms["formaDetOperaciones"].operacion.value = "consultarOperaciones";
	document.forms["formaDetOperaciones"].corresponsal.value = document.forms["formaDetOperaciones"].corresponsal1.value;
	document.forms["formaDetOperaciones"].action = "ConsultaOperacionesCorresponsal.do";
	document.forms["formaDetOperaciones"].submit();
}
</script>

<form id="formaDetOperaciones" action="" method="post">
    <input type="hidden" id="SES" name="SES" value="${SES}" />
	<input type="hidden" id="operacion" name="operacion" value="      " />
	<input type="hidden" id="idOperacion" name="idOperacion" value="${idOperacion}"/>
	<input type="hidden" id="txtCodigoOperacion" name="txtCodigoOperacion" value="${operacionDet.equivalencia}"/>
	<input type="hidden" id="corresponsal" name="corresponsal"/>

<div class="pageTitleContainer">
	<c:choose>
		<c:when test="${disabled!=null}">
			<span class="pageTitle">Detalle operaci&oacute;n</span>
		</c:when>
		<c:otherwise>
			<span class="pageTitle">Modificaci&oacute;n operaci&oacute;n</span>
		</c:otherwise>
	</c:choose>								
</div>

<div class="frameBuscadorSimple">
<div class="titleBuscadorSimple">Corresponsal</div>
<div class="contentBuscadorSimple">
<table>
	<tbody>
		<tr>
			<td class="text_centro" width="50%">Corresponsal: 
			<select id="corresponsal1" name="corresponsal1" class="Campos_Des" disabled="disabled"> 
				<option VALUE="<%=request.getSession().getAttribute("idCorresponsal")%>" selected><%=request.getSession().getAttribute("nombre")%></option>			</select></td>
		</tr>
	</tbody>
</table>
</div>
</div> 	
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Datos generales de la operaci&oacute;n <span class="textosin">- OXXO</span> </div>
		<div class="contentBuscadorSimple">
		<!-- Campos para la modificacion -->
		<c:if test="${disabled==null}">
			<table>
					<tr>
						<td class="text_izquierda">Nombre:</td>
						<td colspan="" class="text_izquierda">Cve. de asociaci&oacute;n:</td>
						<td colspan="" class="text_izquierda">*Transacci&oacute;n:</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="Campos_Des" disabled="disabled" value="${operacionDet.desCorta}" id="txtDesCorta" name="txtDesCorta"  maxlength="30" size="25"/>
						</td>
						<td colspan="">
							<input type="text" class="Campos_Des" disabled="disabled" value="${operacionDet.equivalencia}" id="txtCodigoOperacion1" name="txtCodigoOperacion1"  maxlength="10" size=""/>
						</td>
						<td colspan="">
							<input type="text" class="CamposCompletar" ${disabled}  value="${operacionDet.cveTransaccion}" id="txtCveTransaccion" name="txtCveTransaccion"  maxlength="4" size=""/>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*Importe m&aacute;ximo:</td>
						<td class="text_izquierda">*Importe m&iacute;nimo:</td>
						<td class="text_izquierda">*Acumulado diario:</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="CamposCompletar" ${disabled}   value="${operacionDet.importeMaximoOperacionFront}" id="txtImporteMaximoOperacionFormateado" name="txtImporteMaximoOperacionFormateado"  maxlength="16" size=""/>
						</td>
						<td>
							<input type="text" class="CamposCompletar" ${disabled}   value="${operacionDet.importeMinimoOperacionFront}" id="txtImporteMinimoOperacionFormateado" name="txtImporteMinimoOperacionFormateado"  maxlength="16" />
						</td>
						<td>
							<input type="text" class="CamposCompletar" ${disabled}   value="${operacionDet.acumuladoDiarioFront}" id="txtAcumuladoDiarioFormateado" name="txtAcumuladoDiarioFormateado"  maxlength="16"/>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*Hora inicio:</td>
						<td class="text_izquierda">*Hora fin:</td>
						<td class="text_izquierda">*Acumulado mensual:</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="CamposCompletar" ${disabled}   value="${operacionDet.horaInicioFront}" id="txtHoraInicioFormateada" name="txtHoraInicioFormateada"  maxlength="5" size=""/>
						</td>
						<td>
							<input type="text" class="CamposCompletar" ${disabled}   value="${operacionDet.horaFinalFront}" id="txtHoraFinalFormateada" name="txtHoraFinalFormateada"  maxlength="5" />
						</td>
						<td>
							<input type="text" class="CamposCompletar" ${disabled}  value="${operacionDet.acumuladoMensualFront}" id="txtAcumuladoMensualFormateado" name="txtAcumuladoMensualFormateado"  maxlength="16" />
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*Clacon:</td>
						<td class="text_izquierda">*Clacon contraoperación:</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="CamposCompletar" ${disabled}   value="${operacionDet.codigoOperacionTransaccion}" id="txtCodigoOperacionTransaccion" name="txtCodigoOperacionTransaccion"  maxlength="4" size=""/>
						</td>
						<td>
							<input type="text" class="CamposCompletar" ${disabled}   value="${operacionDet.codigoOperacionContraOper}" id="txtCodigoOperacionContraOper" name="txtCodigoOperacionContraOper"  maxlength="4" />
						</td>
					</tr>
			</table>
			</c:if>
			<!-- Campos para la consulta detalle -->
			<c:if test="${disabled!=null}">
			<table>
					<tr>
						<td class="text_izquierda">*Nombre:</td>
						<td colspan="" class="text_izquierda">*Cve. de asociaci&oacute;n:</td>
						<td colspan="" class="text_izquierda">*Transacci&oacute;n:</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="Campos_Des"  value="${operacionDet.desCorta}" id="txtDesCorta" name="txtDesCorta"  maxlength="30" size="25" disabled="disabled"/>
						</td>
						<td colspan="">
							<input type="text" class="Campos_Des" ${disabled} value="${operacionDet.equivalencia}" id="txtCodigoOperacion" name="txtCodigoOperacion"  maxlength="6" size=""/>
						</td>
						<td colspan="">
							<input type="text" class="Campos_Des" ${disabled}  value="${operacionDet.cveTransaccion}" id="txtCveTransaccion" name="txtCveTransaccion"  maxlength="4" size=""/>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*Importe m&aacute;ximo:</td>
						<td class="text_izquierda">*Importe m&iacute;nimo:</td>
						<td class="text_izquierda">*Acumulado diario:</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="Campos_Des" ${disabled}   value="${operacionDet.importeMaximoOperacionFront}" id="txtImporteMaximoOperacionFormateado" name="txtImporteMaximoOperacionFormateado"  maxlength="16" size=""/>
						</td>
						<td>
							<input type="text" class="Campos_Des" ${disabled}   value="${operacionDet.importeMinimoOperacionFront}" id="txtImporteMinimoOperacionFormateado" name="txtImporteMinimoOperacionFormateado"  maxlength="16" />
						</td>
						<td>
							<input type="text" class="Campos_Des" ${disabled}   value="${operacionDet.acumuladoDiarioFront}" id="txtAcumuladoDiarioFormateado" name="txtAcumuladoDiarioFormateado"  maxlength="16"/>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*Hora inicio:</td>
						<td class="text_izquierda">*Hora fin:</td>
						<td class="text_izquierda">*Acumulado mensual:</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="Campos_Des" ${disabled}   value="${operacionDet.horaInicioFront}" id="txtHoraInicioFormateada" name="txtHoraInicioFormateada"  maxlength="5" size=""/>
						</td>
						<td>
							<input type="text" class="Campos_Des" ${disabled}   value="${operacionDet.horaFinalFront}" id="txtHoraFinalFormateada" name="txtHoraFinalFormateada"  maxlength="5" />
						</td>
						<td>
							<input type="text" class="Campos_Des" ${disabled}  value="${operacionDet.acumuladoMensualFront}" id="txtAcumuladoMensualFormateado" name="txtAcumuladoMensualFormateado"  maxlength="16" />
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*Clacon:</td>
						<td class="text_izquierda">*Clacon contraoperación:</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="Campos_Des" ${disabled}   value="${operacionDet.codigoOperacionTransaccion}" id="txtCodigoOperacionTransaccion" name="txtCodigoOperacionTransaccion"  maxlength="4" size=""/>
						</td>
						<td>
							<input type="text" class="Campos_Des" ${disabled}   value="${operacionDet.codigoOperacionContraOper}" id="txtCodigoOperacionContraOper" name="txtCodigoOperacionContraOper"  maxlength="4" />
						</td>
					</tr>
			</table>
			</c:if>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table width="100%">
					<tr>
						<c:if test="${disabled==null}">
						<td class="izq"><span><a href="javascript:guardar();" >Guardar</a></span></td>
						</c:if>
						<c:if test="${disabled!=null}">
						<td ><span></span></td>
						</c:if>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="der"><span><a href="javascript:regresar();" >Cerrar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</form>

