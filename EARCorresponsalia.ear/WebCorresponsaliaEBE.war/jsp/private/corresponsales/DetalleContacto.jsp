<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="contactos" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/comunes/validaciones.js"></script>
<script>

function cerrar(){
    	document.forms["formaAltaContacto"].action = "ConsultaContacto.do";
    	document.forms["formaAltaContacto"].submit();
	}

function guardar() {
	document.forms["formaAltaContacto"].action = "altContacto.do";
	document.getElementById("operacion").value = "modificaContacto";
	if(validaSoloLetras(document.getElementById("txtNombre").value, "Nombre", 1, 60,"Contactos"))
		return;
	if(validaCadena(document.getElementById("txtPuesto").value, "Puesto", 1, 20,"Contactos"))
		return;
	if(validaCadena(document.getElementById("txtArea").value, "Área", 1, 10,"Contactos"))
		return;
	if(validaNumeros(document.getElementById("txtTelOf").value, "Tel. Oficina", 10, 10,"Contactos"))
		return;
	if(validaNumeros(document.getElementById("txtTelMov").value, "Tel. Movil", 10, 10,"Contactos"))
		return;
	/*if(validaCorreo(document.getElementById("txtEmail").value, "E-mail", 20,"Contactos"))
		return;*/
	if(validaNumeros(document.getElementById("txtTelFax").value, "Fax", 10, 10,"Contactos"))
		return;
	/*if(validaCorreo(document.getElementById("txtEmailAlt").value, "E-mail alterno", 20,"Contactos"))
		return;*/
	if(validaNumeros(document.getElementById("txtCodPostal").value, "Código postal", 5, 5,"Contactos"))
		return;
	if(document.getElementById("txtCalle").value !='' && validaCadena(document.getElementById("txtCalle").value, "Calle", 1, 20,"Contactos"))
		return;
	if(document.getElementById("txtNumExt").value!=''&& validaCadena(document.getElementById("txtNumExt").value, "Número Exterior", 1, 5,"Contactos"))
		return;
	if(document.getElementById("txtNumInt").value!='' && validaCadena(document.getElementById("txtNumInt").value, "Número Interior", 1, 5,"Contactos"))
		return;
	if(document.getElementById("txtColonia").value!='' && validaCadena(document.getElementById("txtColonia").value, "Colonia", 1, 20,"Contactos"))
		return;
	if(document.getElementById("txtDelegMunc").value!='' && validaSoloLetras(document.getElementById("txtDelegMunc").value, "Delegación", 1, 20,"Contactos"))
		return;
	if(document.getElementById("txtCiudad").value!='' && validaSoloLetras(document.getElementById("txtCiudad").value, "Ciudad", 1, 20,"Contactos"))
		return;
	if(document.getElementById("txtEstado").value!='' && validaSoloLetras(document.getElementById("txtEstado").value, "Estado", 1, 20,"Contactos"))
		return;	
	
	document.forms["formaAltaContacto"].submit();
}

	<c:if test="${not empty  mensajeRespuesta}">
			jAlert('${mensajeRespuesta}',                               //DESCRIPCION ERROR
		   	   	   'Contactos', 						//TITULO ERROR
		   	   	   '',                               //CODIGO ERROR
		   	       '${mensajeRespuesta}');                                         		//TIP PARA QUE NO SUCEDA EL ERROR		
	</c:if>
	<c:if test="${not empty  listaErrores}">
			jError('Errores en la captura de datos',                               //DESCRIPCION ERROR
		   	   	   'Contactos', 						//TITULO ERROR
		   	   	   'Datos Inválidos',                               //CODIGO ERROR
		   	       '${listaErrores}');                                         		//TIP PARA QUE NO SUCEDA EL ERROR		
	</c:if>

</script>

<form id="formaAltaContacto" action="" method="post">
    <input type="hidden" id="SES" name="SES" value="${SES}" />
    <input type="hidden" id="operacion" name="operacion" value="default" />
<input type="hidden" id="consecutivo" name="consecutivo" value="${contacto.consecutivo }" />

	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">${titulo }</span>
	</div>
	
	<!-- Componente formulario -->
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Datos generales</div>
			<div class="contentFormularioB">
				<table>
					<tr>
						<td class="odd">*Corresponsal:</td>
						<td>
							<!-- <select id="tipDoc" name="tipDoc" class="CamposCompletar" onchange="filtro();limpiaDatos();">-->
							 <select id="idCorresponsal" name="idCorresponsal" class="Campos">
										<option VALUE="${contacto.idCorresponsal}" selected="selected">${nombre}</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="odd">*Nombre:</td>
						<td>
							<input type="text" value="${contacto.nombre}" class="Campos" ${disabled } id="txtNombre" name="txtNombre" size="30" value="" maxlength="60"/>							
						</td>
						<td class="odd">*Puesto:</td>
						<td>
							<input type="text" value="${contacto.puesto }" class="Campos" ${disabled } id="txtPuesto" name="txtPuesto" value=""  maxlength="20"/>							
						</td>
					</tr>
					<tr>
						<td class="odd">*&Aacute;rea:</td>
						<td>
							<input type="text" value="${contacto.area }" class="Campos" ${disabled } id="txtArea" name="txtArea" value="" maxlength="10"/>							
						</td>
						<td class="odd">*Tel. Oficina:</td>
						<td>
							<input type="text" value="${contacto.telefono }" class="Campos" ${disabled } id="txtTelOf" name="txtTelOf" value="" maxlength="10"/>							
						</td>
					</tr>
					<tr>
						<td class="odd">*Tel. Movil:</td>
						<td>
							<input type="text" value="${contacto.telMovil }" class="Campos" ${disabled } id="txtTelMov" name="txtTelMov" value="${template.nombre}" maxlength="10"/>							
						</td>
						<td class="odd">E-mail:</td>
						<td>
							<input type="text" value="${contacto.email1 }" class="Campos" ${disabled } id="txtEmail" name="txtEmail" value="${template.nombre}" maxlength="20"/>							
						</td>
					</tr>
					<tr>
						<td class="odd">*Fax:</td>
						<td>
							<input type="text" value="${contacto.telFax }" class="Campos" ${disabled } id="txtTelFax" name="txtTelFax" value="${template.nombre}" maxlength="10"/>							
						</td>
						<td class="odd">E-mail alterno:</td>
						<td>
							<input type="text" value="${contacto.email2 }" class="Campos" ${disabled } id="txtEmailAlt" name="txtEmailAlt" value="${template.nombre}" maxlength="20"/>							
						</td>
					</tr>
				</table>
			</div>
		<div class="titleBuscadorSimple">Direcci&oacute;n del contacto</div>
			<div class="contentBuscadorSimple">
				<table>
					<tr>
						<td colspan="2" class="text_izquierda">Calle:</td>
						<td class="text_izquierda">No. Exterior:</td>
						<td class="text_izquierda">No. Interior:</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="text" value="${contacto.calle }" class="Campos" ${disabled } id="txtCalle" name="txtCalle"  maxlength="20" size="50"/>
						</td>
						<td>
							<input type="text" value="${contacto.numExt }" class="Campos" ${disabled } id="txtNumExt" name="txtNumExt"  maxlength="5" size="15"/>
						</td>
						<td>
							<input type="text" value="${contacto.numInt }" class="Campos" ${disabled } id="txtNumInt" name="txtNumInt"  maxlength="5" size="15"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="text_izquierda">Colonia:</td>
						<td class="text_izquierda">Delegación/Municipio:</td>
						<td class="text_izquierda">Ciudad:</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="text" value="${contacto.colonia }" class="Campos" ${disabled } id="txtColonia" name="txtColonia"  maxlength="20" size="45"/>
						</td>
						<td colspan="">
							<input type="text" value="${contacto.delegMunic }" class="Campos" ${disabled } id="txtDelegMunc" name="txtDelegMunc"  maxlength="20" size="20"/>
						</td>
						<td colspan="">
							<input type="text" value="${contacto.ciudad }" class="Campos" ${disabled } id="txtCiudad" name="txtCiudad"  maxlength="20" size="20"/>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">Estado:</td>
						<td class="text_izquierda">*C&oacute;digo Postal:</td>
						<td class="text_izquierda">*Zona:</td>
					</tr>
					<tr>
						<td colspan="">
							<input type="text" value="${contacto.entFede }" class="Campos" ${disabled } id="txtEstado" name="txtEstado"  maxlength="20" size="20"/>
						</td>
						<td>
							<input type="text" value="${contacto.codPostal }" class="Campos" ${disabled } id="txtCodPostal" name="txtCodPostal"  maxlength="5" />
						</td>
						<td> 
							<input type="text" value="${contacto.zonaUbic != '00000' ? '00000' : contacto.zonaUbic }" class="Campos" ${disabled } id="txtZona" name="txtZona"  maxlength="5" readonly/>
						</td>
					</tr>					
				</table>
			</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table width="100%">
					<tr>
						<td class="${modificar ==null ? '' : 'izq' }" > <span><c:if test="${modificar!=null}"><a href="javascript:guardar();" onclick="" >Guardar</a></c:if></span></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="der"><span><a href="JavaScript:cerrar(-1);" >Cerrar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</form>