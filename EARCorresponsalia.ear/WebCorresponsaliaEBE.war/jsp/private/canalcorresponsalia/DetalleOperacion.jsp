<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="canalcorresponsalia" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<form id="formaDetalleOperaciones" action="ConsultaOperaciones.do" method="post">
<input type="hidden" name="SES" value="${SES}" />
	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Detalle de Operaciones</span>
	</div>

	<!-- Componente formulario Parametria -->
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Datos generales de la operaci&oacute;n</div>
		<div class="contentBuscadorSimple">
			<table>
					<tr>
						<td class="text_izquierda">*Nombre:</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="Campos_Des" value="${nombre}" id="txtNombre" name="txtNombre"  maxlength="30" size="" readonly/>
						</td>
					</tr><tr>
                        <td width="60%" class="text_izquierda">*Descripci&oacute;n:</td>
                        <td  width="20%" class="text_izquierda">*Desc. Corta:</td>
                        <td width="20%" class="text_izquierda">*Valor en R26:</td>
                    </tr>
                    <tr>
                        <td width="60%">
                            <input type="text" class="Campos_Des" id="txtDescripcion" name="txtDescripcion"  maxlength="50" size="50" value="${descLarga}"/>
                        </td>
                        <td width="20%">
                            <input type="text" class="Campos_Des" id="txtDescripcionCorta" name="txtDescripcionCorta"  maxlength="20" size="" value="${descCorta}"/>
                        </td>
                        <td width="20%">
                            <input type="text" class="Campos_Des" id="txtValorR26" name="txtValorR26"  maxlength="10" size="10" value="${equiTipOperDos}"/>
                        </td>
                    </tr>
					<tr>
						<td class="text_izquierda">*Importe m&aacute;ximo:</td>
						<td class="text_izquierda">*Importe m&iacute;nimo:</td>
						<td class="text_izquierda">*Acumulado diario:</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="Campos_Des" value="${importeMaximo}" id="txtImporteMaximo" name="txtImporteMaximo"  maxlength="30" size="" readonly/>
						</td>
						<td>
							<input type="text" class="Campos_Des" value="${importeMinimo}" id="txtImporteMinimo" name="txtImporteMinimo"  maxlength="30" readonly/>
						</td>
						<td>
							<input type="text" class="Campos_Des" value="${acumuladoDiario}" id="txtAcumuladoDiario" name="txtAcumuladoDiario"  maxlength="30" size="" readonly/>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*Hora inicio:</td>
						<td class="text_izquierda">*Hora fin:</td>
						<td class="text_izquierda">*Acumulado mensual:</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="Campos_Des" value="${horaInicio}" id="txtHoraInicio" name="txtHoraInicio"  maxlength="30" size="" readonly/>
						</td>
						<td>
							<input type="text" class="Campos_Des" value="${horaFinal}" id="txtHoraFinal" name="txtHoraFinal"  maxlength="30" readonly/>
						</td>
						<td>
							<input type="text" class="Campos_Des" value="${acumuladoMensual}" id="txtAcumuladoMensual" name="txtAcumuladoMensual"  maxlength="30" readonly/>
						</td>
					</tr>
			</table>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table width="100%">
					<tr>
						<td><span>&nbsp;</span></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="der"><span><a href="javaScript: formaDetalleOperaciones.submit();" onclick="" >Cerrar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</form>