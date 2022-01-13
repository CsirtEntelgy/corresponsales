<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="contactos" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/comunes/validaciones.js"></script>
<script type = "text/javascript">
function alta() {
	document.forms["formaAltaContacto"].action = "altContacto.do";
	document.getElementById("operacion").value = "altaContacto";
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
/*	if(validaCorreo(document.getElementById("txtEmail").value, "E-mail", 20,"Contactos"))
		return;*/
	if(validaNumeros(document.getElementById("txtTelFax").value, "Fax", 10, 10,"Contactos"))
		return;
	/*if(validaCorreo(document.getElementById("txtEmailAlt").value, "E-mail alterno", 20,"Contactos"))
		return;*/
	if(validaNumeros(document.getElementById("txtCodPostal").value, "Codigo Postal",5,5,"Contactos"))
		return;
	if(/*document.getElementById("txtCalle").value !='' && */validaCadena(document.getElementById("txtCalle").value, "Calle", 1, 20,"Contactos"))
		return;
	if(/*document.getElementById("txtNumExt").value!=''&& */validaCadena(document.getElementById("txtNumExt").value, "Número Exterior", 1, 5,"Contactos"))
		return;
	if(/*document.getElementById("txtNumInt").value!='' &&*/ validaCadena(document.getElementById("txtNumInt").value, "Número Interior", 1, 5,"Contactos"))
		return;
	if(/*document.getElementById("txtColonia").value!='' && */validaCadena(document.getElementById("txtColonia").value, "Colonia", 1, 20,"Contactos"))
		return;
	if(/*document.getElementById("txtDelegMunc").value!='' && */validaSoloLetras(document.getElementById("txtDelegMunc").value, "Delegación", 1, 20,"Contactos"))
		return;
	if(/*document.getElementById("txtCiudad").value!='' && */validaSoloLetras(document.getElementById("txtCiudad").value, "Ciudad", 1, 20,"Contactos"))
		return;
	if(/*document.getElementById("txtEstado").value!='' && */validaSoloLetras(document.getElementById("txtEstado").value, "Estado", 1, 20,"Contactos"))
		return;	
		
	document.forms["formaAltaContacto"].submit();
}

function limpiar() {
	document.getElementById("txtNombre").value = '';
	document.getElementById("txtPuesto").value = '';
	document.getElementById("txtArea").value = '';
	document.getElementById("txtTelOf").value = '';
	document.getElementById("txtTelMov").value = '';
	document.getElementById("txtEmail").value = '';
	document.getElementById("txtTelFax").value = '';
	document.getElementById("txtEmailAlt").value = '';
	document.getElementById("txtCalle").value = '';
	document.getElementById("txtNumExt").value = '';
	document.getElementById("txtNumInt").value = '';
	document.getElementById("txtColonia").value = '';
	document.getElementById("txtDelegMunc").value = '';
	document.getElementById("txtCiudad").value = '';
	document.getElementById("txtEstado").value = '';
	document.getElementById("txtCodPostal").value = '';

}

	<c:if test="${not empty  mensajeRespuesta}">
			jAlert('${mensajeRespuesta}',                               //DESCRIPCION ERROR
		   	   	   'Resultado en Alta Contactos', 						//TITULO ERROR
		   	   	   '',                               //CODIGO ERROR
		   	       '${mensajeRespuesta}');                                         		//TIP PARA QUE NO SUCEDA EL ERROR		
	</c:if>

	<c:if test="${not empty  listaErrores}">
			jError('Errores en la captura de datos',                               //DESCRIPCION ERROR
		   	   	   'Resultado en Alta Contactos', 						//TITULO ERROR
		   	   	   'Datos Inv&aacute;lidos',                               //CODIGO ERROR
		   	       '${listaErrores}');                                         		//TIP PARA QUE NO SUCEDA EL ERROR		
	</c:if>

</script>


<form id="formaAltaContacto" action="" method="post">
    <input type="hidden" id="SES" name="SES" value="${SES}" />
	<input type="hidden" id="operacion" name="operacion" value="default" />
	
	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Alta de contacto</span>
	</div>
	
		<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Datos generales</div>
			<div class="contentFormularioB">
				<table>
					<tr>
						<td class="odd">*Corresponsal:</td>
						<td>
							<!-- <select id="tipDoc" name="tipDoc" class="CamposCompletar" onchange="filtro();limpiaDatos();">-->
							 <select id="tipDoc" name="idCorresponsal" class="Campos" onchange="filtro();limpiaDatos();">
										<option VALUE="<%=request.getSession().getAttribute("idCorresponsal")%>" selected><%=request.getSession().getAttribute("nombre")%></option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="odd">*Nombre:</td>
						<td>
							<input type="text" class="CamposCompletar" id="txtNombre" name="txtNombre" size="30" value="${beanContacto.nombre }" maxlength="60"/>							
						</td>
						<td class="odd">*Puesto:</td>
						<td>
							<input type="text" class="CamposCompletar" id="txtPuesto" name="txtPuesto" maxlength="20" value="${beanContacto.puesto }"/>							
						</td>
					</tr>
					<tr>
						<td class="odd">*&Aacute;rea:</td>
						<td>
							<input type="text" class="CamposCompletar" id="txtArea" name="txtArea" maxlength="10" value="${beanContacto.area }"/>							
						</td>
						<td class="odd">*Tel. Oficina:</td>
						<td>
							<input type="text" class="CamposCompletar" id="txtTelOf" name="txtTelOf" maxlength="10" value="${beanContacto.telOficina }"/>							
						</td>
					</tr>
					<tr>
						<td class="odd">*Tel. Movil:</td>
						<td>
							<input type="text" class="CamposCompletar" id="txtTelMov" name="txtTelMov" maxlength="10" value="${beanContacto.telMovil }"/>							
						</td>
						<td class="odd">E-mail:</td>
						<td>
							<input type="text" class="Campos" id="txtEmail" name="txtEmail" maxlength="20" value="${beanContacto.emailPrin }"/>							
						</td>
					</tr>
					<tr>
						<td class="odd">*Fax:</td>
						<td>
							<input type="text"  class="CamposCompletar" id="txtTelFax" name="txtTelFax" maxlength="10" value="${beanContacto.telFax }"/>							
						</td>
						<td class="odd">E-mail alterno:</td>
						<td>
							<input type="text"  class="Campos" id="txtEmailAlt" name="txtEmailAlt" maxlength="20" value="${beanContacto.emailAlt }"/>							
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
							<input type="text" class="CamposCompletar" id="txtCalle" name="txtCalle"  maxlength="20" size="50" value="${beanContacto.calle }"/>
						</td>
						<td>
							<input type="text" class="CamposCompletar" id="txtNumExt" name="txtNumExt"  maxlength="5" size="15" value="${beanContacto.numInt }"/>
						</td>
						<td>
							<input type="text" class="CamposCompletar" id="txtNumInt" name="txtNumInt"  maxlength="5" size="15" value="${beanContacto.numExt }"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="text_izquierda">*Colonia:</td>
						<td class="text_izquierda">*Delegaci&oacute;n/Municipio:</td>
						<td class="text_izquierda">*Ciudad:</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="text" class="CamposCompletar" id="txtColonia" name="txtColonia"  maxlength="20" size="45" value="${beanContacto.colonia }"/>
						</td>
						<td colspan="">
							<input type="text"  class="CamposCompletar" id="txtDelegMunc" name="txtDelegMunc"  maxlength="20" size="20" value="${beanContacto.delegMcp }"/>
						</td>
						<td colspan="">
							<input type="text" class="CamposCompletar" id="txtCiudad" name="txtCiudad"  maxlength="20" size="20" value="${beanContacto.ciudad }"/>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*Estado:</td>
						<td class="text_izquierda">*C&oacute;digo Postal:</td>
						<td class="text_izquierda">*Zona:</td>
					</tr>
					<tr>
						<td colspan="">
							<input type="text" class="CamposCompletar" id="txtEstado" name="txtEstado"  maxlength="20" size="20" value="${beanContacto.entidadFed }"/>
						</td>
						<td>
							<input type="text" class="CamposCompletar" id="txtCodPostal" name="txtCodPostal"  maxlength="5" value="${beanContacto.codPostal }"/>
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
						<td class="izq"><span><a href="javascript:alta();" onclick="" >Guardar</a></span></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="der"><span><a href="#" onclick="limpiar();" >Limpiar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	