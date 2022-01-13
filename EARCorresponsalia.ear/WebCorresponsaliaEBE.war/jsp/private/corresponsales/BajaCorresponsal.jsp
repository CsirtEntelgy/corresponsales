<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="consultaCorresp" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/confis.js"></script>

<form id="formaConsultaConstanciaFiscal" action="" method="post">

	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Baja de Corresponsales</span>
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
		<div class="titleBuscadorSimple">Corresponsal a Eliminar</div>
			<div class="contentFormularioB">
				<table>
					<tr>
						<td class="odd">*Corresponsal:</td>
						<td>
							<select id="tipDoc" name="tipDoc" class="CamposCompletar" onchange="filtro();limpiaDatos();">
							<!--  <select id="tipDoc" name="tipDoc" class="Campos" disabled="disabled" onchange="filtro();limpiaDatos();">-->
										<option VALUE="uno" selected="selected">OXXO</option>
										<option value="dos">7Eleven</option>
										<option value="tres">Extra</option>
							</select>
						</td>
					</tr>
				</table>
			</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table>
					<tr>
						<td width="279" class="izq"><span><a href="#" onclick="guardar();" >Eliminar</a></span></td>
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="der"><span><a href="#" onclick="limpiar();" >Limpiar</a></span></td>
					</tr>
				</table>
			</div>
		</div><!--  
		<div class="titleBuscadorSimple">Datos generales</div>
		<div class="contentBuscadorSimple">
			<table>
					<tr>
						<td colspan="3" class="text_izquierda">*Raz&oacute;n social:</td>
						<td class="text_izquierda">*Monbre corto:</td>
					</tr>
					<tr>
						<td colspan="3">
							<input type="text" value="Gurpo OXXO S. A. de C.V." class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="120" size="75"/>
						</td>
						<td>
							<input type="text" value="OXXO" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" />
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*BUC (c&oacute;digo de cliente):</td>
						<td class="text_izquierda">*RFC:</td>
						<td class="text_izquierda">*Centro de costos:</td>
						<td class="text_izquierda">*Cuenta de cheques:</td>
					</tr>
					<tr>
						<td>
							<input type="text" value="12345678" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size=""/>
						</td>
						<td>
							<input type="text" value="GOSA123456CBB" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" />
						</td>
						<td>
							<input type="text" value="Corresp105" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size=""/>
						</td>
						<td>
							<input type="text" value="1234567890123456" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" />
						</td>
					</tr>
					<tr>						
						<td class="text_izquierda">*L&iacute;nea de cr&eacute;dito:</td>
						<td class="text_izquierda">*L&iacute;mite de alerta:</td>
						<td class="text_izquierda">*Tipo de corresponsal:</td>
						<td class="text_izquierda">*Fronterizo:</td>
					</tr>
					<tr>
						<td>
							<input type="text" value="1,000,000.00" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" />
						</td>
						<td>
							<input type="text" value="100,000.00" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" />
						</td>
						<td>
							<input type="text" value="Uno" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" />
						</td>
						<td>
							<input type="text" value="No" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" />
						</td>
					</tr>
			</table>
		</div>
		<div class="titleBuscadorSimple">Direcci&oacute;n</div>
			<div class="contentBuscadorSimple">
				<table>
					<tr>
						<td colspan="2" class="text_izquierda">*Calle:</td>
						<td class="text_izquierda">*No. Exterior:</td>
						<td class="text_izquierda">*No. Interior:</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="text" value="Espino" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size="50"/>
						</td>
						<td>
							<input type="text" value="370" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size="15"/>
						</td>
						<td>
							<input type="text" value="--" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size="15"/>
						</td>
					</tr>
					<tr>
						<td colspan ="2" class="text_izquierda">*Avenida:</td>
						<td class="text_izquierda">*Colonia:</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="text" value="Juaarez" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size="50"/>
						</td>
						<td colspan="2">
							<input type="text" value="Progreso" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size="45"/>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*Delegación/Municipio:</td>
						<td class="text_izquierda">*Ciuidad:</td>
						<td class="text_izquierda">*Estado:</td>
					</tr>
					<tr>
						<td colspan="">
							<input type="text" value="Monterrey" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size="20"/>
						</td>
						<td colspan="">
							<input type="text" value="Monterrey" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size="20"/>
						</td>
						<td colspan="">
							<input type="text" value="Nuevo Leon" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size="20"/>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*Entidad Federativa:</td>
						<td class="text_izquierda">*C&oacute;digo Postal:</td>
						<td class="text_izquierda">*Pa&iacute;s:</td>
						<td class="text_izquierda">*Tel&eacute;fono:</td>
					</tr>
					<tr>
						<td>
							<input type="text" value="Nvl." class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" />
						</td>
						<td>
							<input type="text" value="47000" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" />
						</td>
						<td>
							<input type="text" value="México" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" />
						</td>
						<td>
							<input type="text" value="(81) 83-45-21-00" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" />
						</td>
					</tr>
					<tr>
						<td colspan="2" class="text_izquierda">*E-mail Aclaraciones (Para):</td>
						<td colspan="2" class="text_izquierda">*E-mail Aclaraciones (CC):</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="text" value="aclara@oxxo.com" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size="50"/>
						</td>
						<td colspan="2">
							<input type="text" value="ccaclara@oxxo.com" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size="45"/>
						</td>
					</tr>
				</table>
			</div>
		<div class="titleBuscadorSimple">Datos del contacto</div>
			<div class="contentFormularioB">
				<table>
					<tr>
						<td class="odd">*Nombre:</td>
						<td>
							<input type="text" value="Enrique Arteaga Carbajal" class="Campos" disabled="disabled" id="txtNombre" name="txtNombre" size="30"value="" />							
						</td>
						<td class="odd">*Puesto:</td>
						<td>
							<input type="text" value="Gerente General" class="Campos" disabled="disabled" id="txtNombre" name="txtNombre" value="" />							
						</td>
					</tr>
					<tr>
						<td class="odd">*&Aacute;rea:</td>
						<td>
							<input type="text" value="Ventas" class="Campos" disabled="disabled" id="txtNombre" name="txtNombre" value="" />							
						</td>
						<td class="odd">*Tel. Oficina:</td>
						<td>
							<input type="text" value="(81) 83-21-32-54" class="Campos" disabled="disabled" id="txtNombre" name="txtNombre" value="" />							
						</td>
					</tr>
					<tr>
						<td class="odd">*Tel. Movil:</td>
						<td>
							<input type="text" value="044 81 52 52 52 52" class="Campos" disabled="disabled" id="txtNombre" name="txtNombre" value="${template.nombre}" />							
						</td>
						<td class="odd">*E-mail:</td>
						<td>
							<input type="text" value="earteaga@oxxo.com" class="Campos" disabled="disabled" id="txtNombre" name="txtNombre" value="${template.nombre}" />							
						</td>
					</tr>
				</table>
			</div>
		<div class="titleBuscadorSimple">Direcci&oacute;n del contacto</div><div class="contentBuscadorSimple">
			<div class="contentBuscadorSimple">
				<table>
					<tr>
						<td colspan="2" class="text_izquierda">*Calle:</td>
						<td class="text_izquierda">*No. Exterior:</td>
						<td class="text_izquierda">*No. Interior:</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="text" value="Constitución" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size="50"/>
						</td>
						<td>
							<input type="text" value="1257" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size="15"/>
						</td>
						<td>
							<input type="text" value="S/N" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size="15"/>
						</td>
					</tr>
					<tr>
						<td colspan ="2" class="text_izquierda">*Avenida:</td>
						<td class="text_izquierda">*Colonia:</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="text" value="Constitución" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size="50"/>
						</td>
						<td colspan="2">
							<input type="text" value="Centro" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size="45"/>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*Delegación/Municipio:</td>
						<td class="text_izquierda">*Ciuidad:</td>
						<td class="text_izquierda">*Estado:</td>
					</tr>
					<tr>
						<td colspan="">
							<input type="text" value="Monterrey" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size="20"/>
						</td>
						<td colspan="">
							<input type="text" value="Monterrey" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size="20"/>
						</td>
						<td colspan="">
							<input type="text" value="Nuevo Leon" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" size="20"/>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*Entidad Federativa:</td>
						<td class="text_izquierda">*C&oacute;digo Postal:</td>
					</tr>
					<tr>
						<td>
							<input type="text" value="Nuevo Leon" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" />
						</td>
						<td>
							<input type="text" value="33321" class="Campos" disabled="disabled" id="txtCuenta" name="txtCuenta"  maxlength="30" />
						</td>
					</tr>
				</table>
			</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table>
					<tr>
						<td width="279" class="izq"><span><a href="#" onclick="guardar();" >Limpiar</a></span></td>
					</tr>
				</table>
			</div>
		</div>-->
	</div>
