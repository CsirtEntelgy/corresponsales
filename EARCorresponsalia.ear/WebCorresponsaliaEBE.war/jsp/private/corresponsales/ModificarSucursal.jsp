<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="sucursales" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/comunes/validaciones.js"></script>
<script language="javascript">

	function cerrar(){
    	document.forms["formaModificarSucursal"].action = "MuestraCorresponsalesSucursales.do";
    	document.forms["formaModificarSucursal"].submit();
	}

	function guardar(){
    	if(validaCadena(document.getElementById("nombreSucursal").value, "Nombre de sucursal",1,40,"Sucursal"))
			return;
		if(validaCadena(document.getElementById("numId").value, "No. de identificación",2,10,"Sucursal"))
			return;
		if(validaCadena(document.getElementById("rfc").value, "RFC",12,13,"Sucursal"))
			return;
		if(validaCadena(document.getElementById("codigoInterno").value, "Código interno",1,10,"Sucursal"))
			return;
		if(validaNumeros(document.getElementById("telefono").value, "Teléfono", 10, 10,"Sucursal"))
		return;
		if(validaNumeros(document.getElementById("codigoPostal").value, "codigo Postal", 5, 8,"Sucursal"))
		return;
		if(validaSoloLetras(document.getElementById("estado").value, "Estado",1,20,"Sucursal"))
		return;
		if(validaSoloLetras(document.getElementById("ciudad").value, "Ciudad",1,20,"Sucursal"))
		return;
		
		if(validaSoloLetras(document.getElementById("delegMunicipio").value, "Delegacion Municipio",1,20,"Sucursal"))
		return;
		if(validaCadena(document.getElementById("colonia").value, "Colonia",1,20,"Sucursal"))
		return;
		if(validaCadena(document.getElementById("noExterior").value, "Numero Exterior",1,5,"Sucursal"))
		return;
		if(validaCadena(document.getElementById("noInterior").value, "Numero Interior",1,5,"Sucursal"))
		return;
		if(validaCadena(document.getElementById("calle").value, "Calle",1,20,"Sucursal"))
		return;
		if(validaCadena(document.getElementById("descZona").value, "Descripcion Zona Geografica",1,30,"Sucursal"))
		return;
		if(validaCadena(document.getElementById("zonaGeografica").value, "Zona Geografica",2,5,"Sucursal"))
		return;
		if(validaCadena(document.getElementById("region").value, "Region",2,4,"Sucursal"))
		return;
    	document.forms["formaModificarSucursal"].action = "RealizaModificacionSucursal.do";
		document.forms["formaModificarSucursal"].numId.disabled = false;
    	document.forms["formaModificarSucursal"].submit();
	}

</script>

<form id="formaModificarSucursal" action="" method="post">
    <input type="hidden" id="SES" name="SES" value="${SES}" />
<input type="hidden" id="sucursal" name="sucursal" value="default" />
	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Modificar Sucursal</span></div>
	 <div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Datos generales</div>
		<div class="contentBuscadorSimple">
			<table>
				<tr>
					<td class="text_izquierda">*Corresponsal:</td>
						<td>
							<select id="cmbNombreCorresponsal" name="cmbNombreCorresponsal" class="Campos">
										<option VALUE="${corresponsalSel.codigoCorresponsal}" selected="selected">${corresponsalSel.nombreCorresponsal}</option>
							</select>
						</td>
				</tr>
				<tr>
						<td class="text_izquierda">*Nombre de sucursal:</td>
						<td class="text_izquierda">*No. de identificaci&oacute;n:</td>
						<td class="text_izquierda">*Estatus:</td>
				</tr>
				<tr>
					<td>
						<input type="text" class="CamposCompletar" id="nombreSucursal" name="nombreSucursal"  maxlength="40" value="${detalleSucursal.nombreSucursal}"/>
					</td>
					<td>
						<input type="text" class="CamposCompletar" id="numId" name="numId"  maxlength="10" value="${detalleSucursal.numId}" disabled="disabled"/>
					</td>
					<td>
						<strong>${detalleSucursal.codigoEstatus =='CS1' ? 'Implementacion': detalleSucursal.codigoEstatus=='CS2' ? 'Activo': detalleSucursal.codigoEstatus=='CS3'? 'Bloqueado':detalleSucursal.codigoEstatus=='CS4' ? 'Baja' : detalleSucursal.codigoEstatus=='CS5' ? 'Cancelado':''}</strong>
						<input type="hidden" id="codigoEstatus" name="codigoEstatus" class="CamposCompletar" value ="${detalleSucursal.codigoEstatus}" /> 
						
					</td>
				</tr>
					<td class="text_izquierda">*RFC:</td>
					<td class="text_izquierda">*Código interno:</td>
					<td class="text_izquierda">*Teléfono:</td>
					<tr>
						<td>
							<input type="text" class="CamposCompletar"  id="rfc" name="rfc"  maxlength="13" value="${detalleSucursal.rfc}"/>
						</td>
						<td>
							<input type="text" class="CamposCompletar"  id="codigoInterno" name="codigoInterno"  maxlength="10" value="${detalleSucursal.codigoInterno}"/>
						</td>
						<td>
							<input type="text" class="CamposCompletar"  id="telefono" name="telefono"  maxlength="10" value="${detalleSucursal.telefono}" />
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
							<input type="text" class="CamposCompletar"  id="calle" name="calle"  maxlength="20" size="50" value="${detalleSucursal.calle}"/>
						</td>
						<td>
							<input type="text" class="CamposCompletar"  id="noExterior" name="noExterior"  maxlength="5" size="15" value="${detalleSucursal.noExterior}"/>
						</td>
						<td>
							<input type="text" class="CamposCompletar"  id="noInterior" name="noInterior"  maxlength="5" size="15" value="${detalleSucursal.noInterior}"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="text_izquierda">*Colonia:</td>
						<td class="text_izquierda">Entre calles:</td>
						<td class="text_izquierda">*Delegación/Municipio:</td>						
					</tr>
					<tr>
						<td colspan="2">
							<input type="text" class="CamposCompletar"  id="colonia" name="colonia"  maxlength="20" size="45" value="${detalleSucursal.colonia}"/>
						</td>
						<td colspan="">
							<input type="text" class="CamposCompletar"  id="entreCalles" name="entreCalles"  maxlength="40" value="${detalleSucursal.entreCalles}"/>
						</td>
						<td colspan="">
							<input type="text" class="CamposCompletar"  id="delegMunicipio" name="delegMunicipio"  maxlength="20" size="25" value="${detalleSucursal.delegMunicipio}"/>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*Ciudad:</td>
						<td class="text_izquierda">*Estado:</td>
						<td class="text_izquierda">*C&oacute;digo Postal:</td>
						<td class="text_izquierda">*Fronteriza:</td>
					</tr>
					<tr>
						<td colspan="">
							<input type="text" class="CamposCompletar"  id="ciudad" name="ciudad"  maxlength="20" size="20" value="${detalleSucursal.ciudad}"/>
						</td>
						<td colspan="">
							<input type="text" class="CamposCompletar"  id="estado" name="estado"  maxlength="20" size="20" value="${detalleSucursal.estado}"/>
						</td>
						<td>
							<input type="text" class="CamposCompletar"  id="codigoPostal" name="codigoPostal"  maxlength="8" value="${detalleSucursal.codigoPostal}"/>
						</td>
						<td>
							<select id="fronteriza" name="fronteriza" class="CamposCompletar">
									<option value="S" <c:if test="${detalleSucursal.fronteriza=='S'}"> selected</c:if>>SI</option>
									<option value="N" <c:if test="${detalleSucursal.fronteriza=='N'}"> selected</c:if>>NO</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*Zona geografica:</td>
						<td class="text_izquierda">*Descripción de la Zona:</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="CamposCompletar"  id="zonaGeografica" name="zonaGeografica"  maxlength="5" value="${detalleSucursal.zonaGeografica}"/>
						</td>
						<td colspan="2">
							<input type="text" class="CamposCompletar"  id="descZona" name="descZona"  maxlength="30" value="${detalleSucursal.descZona}"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="text_izquierda">*Region:</td>
					</tr>
					<tr>
						<td colspan="3">						
							<input type="text" class="CamposCompletar"  id="region" name="region"  size="30" maxlength="4" value="${detalleSucursal.region}"/>
						</td>
					</tr>
				</table>
			</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table width="100%">
					<tr>
						<td class="izq"><span><a href="javascript:guardar();">Guardar</a></span></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="der"><span><a href="javascript:cerrar();" >Cerrar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</form>

	<c:if test="${not empty  codError}">
		<script>
			jError('${msgError}',                               
		   	   	   'Error en Sucursales', 
		   	   	   '${codError}',                               
		   	       '');                                         		
		</script>
	</c:if>

	<c:if test="${not empty  codAviso}">
		<script>
			jAlert('${msgAviso}',                               
		   	   	   'Error en Sucursales', 
		   	   	   '${codAviso}',                               
		   	       '');                                         		
		</script>
	</c:if>
	