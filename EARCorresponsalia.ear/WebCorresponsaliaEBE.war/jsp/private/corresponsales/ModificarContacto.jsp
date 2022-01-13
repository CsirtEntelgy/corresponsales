<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="contactos" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/confis.js"></script>

<form id="formaConsultaConstanciaFiscal" action="" method="post">
    <input type="hidden" id="SES" name="SES" value="${SES}" />
	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Modificar contacto</span>
	</div>
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Datos generales</div>
			<div class="contentFormularioB">
				<table>
					<tr>
						<td class="odd">*Corresponsal:</td>
						<td>
							<!-- <select id="tipDoc" name="tipDoc" class="CamposCompletar" onchange="filtro();limpiaDatos();">-->
							 <select id="tipDoc" name="tipDoc" class="Campos" disabled="disabled" onchange="filtro();limpiaDatos();">
										<option VALUE="uno" selected="selected">OXXO</option>
										<option value="dos">7Eleven</option>
										<option value="tres">Extra</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="odd">*Nombre:</td>
						<td>
							<input type="text" value="Enrique Arteaga Carbajal" class="CamposCompletar" id="txtNombre" name="txtNombre" size="30"value="" />							
						</td>
						<td class="odd">*Puesto:</td>
						<td>
							<input type="text" value="Gerente General" class="CamposCompletar" id="txtNombre" name="txtNombre" value="" />							
						</td>
					</tr>
					<tr>
						<td class="odd">*&Aacute;rea:</td>
						<td>
							<input type="text" value="Ventas" class="CamposCompletar" id="txtNombre" name="txtNombre" value="" />							
						</td>
						<td class="odd">*Tel. Oficina:</td>
						<td>
							<input type="text" value="(81) 83-21-32-54" class="CamposCompletar" id="txtNombre" name="txtNombre" value="" />							
						</td>
					</tr>
					<tr>
						<td class="odd">*Tel. Movil:</td>
						<td>
							<input type="text" value="044 81 52 52 52 52" class="CamposCompletar" id="txtNombre" name="txtNombre" value="${template.nombre}" />							
						</td>
						<td class="odd">*E-mail:</td>
						<td>
							<input type="text" value="earteaga@oxxo.com" class="CamposCompletar" id="txtNombre" name="txtNombre" value="${template.nombre}" />							
						</td>
					</tr>
					<tr>
						<td class="odd">*Fax:</td>
						<td>
							<input type="text" value="044 81 52 52 52 52" class="CamposCompletar" id="txtNombre" name="txtNombre" value="${template.nombre}" />							
						</td>
						<td class="odd">*E-mail alterno:</td>
						<td>
							<input type="text" value="earteaga@oxxo.com" class="CamposCompletar" id="txtNombre" name="txtNombre" value="${template.nombre}" />							
						</td>
					</tr>
				</table>
			</div>
		<div class="titleBuscadorSimple">Direcci&oacute;n del contacto</div>
			<div class="contentBuscadorSimple">
				<table>
					<tr>
						<td colspan="2" class="text_izquierda">*Calle:</td>
						<td class="text_izquierda">*No. Exterior:</td>
						<td class="text_izquierda">*No. Interior:</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="text" value="Constitución" class="CamposCompletar" id="txtCuenta" name="txtCuenta"  maxlength="30" size="50"/>
						</td>
						<td>
							<input type="text" value="1257" class="CamposCompletar" id="txtCuenta" name="txtCuenta"  maxlength="30" size="15"/>
						</td>
						<td>
							<input type="text" value="S/N" class="CamposCompletar" id="txtCuenta" name="txtCuenta"  maxlength="30" size="15"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="text_izquierda">*Colonia:</td>
						<td class="text_izquierda">*Delegación/Municipio:</td>
						<td class="text_izquierda">*Ciudad:</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="text" value="Centro" class="CamposCompletar" id="txtCuenta" name="txtCuenta"  maxlength="30" size="45"/>
						</td>
						<td colspan="">
							<input type="text" value="Monterrey" class="CamposCompletar" id="txtCuenta" name="txtCuenta"  maxlength="30" size="20"/>
						</td>
						<td colspan="">
							<input type="text" value="Monterrey" class="CamposCompletar" id="txtCuenta" name="txtCuenta"  maxlength="30" size="20"/>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*Estado:</td>
						<td class="text_izquierda">*C&oacute;digo Postal:</td>
						<td class="text_izquierda">*Zona:</td>
					</tr>
					<tr>
						<td colspan="">
							<input type="text" value="Nuevo Leon" class="CamposCompletar" id="txtCuenta" name="txtCuenta"  maxlength="30" size="20"/>
						</td>
						<td>
							<input type="text" value="33321" class="CamposCompletar" id="txtCuenta" name="txtCuenta"  maxlength="30" />
						</td>
						<td>
							<input type="text" value="18" class="CamposCompletar" id="txtCuenta" name="txtCuenta"  maxlength="30" />
						</td>
					</tr>
				</table>
			</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table width="100%">
					<tr>
						<td class="izq"><span><a href="../ConfisWeb/altContacto.do" onclick="" >Guardar</a></span></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="der"><span><a href="#" onclick="limpiar();" >Limpiar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
