<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="sucursales" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/comunes/validaciones.js"></script>
<script language="javascript">

	function altaSucursal(){
		/*
		if(validaCamposObligatoriosAltaAucursal()){
			if(validaFormatoCamposAltaSucursal()){
				document.forms["formaAltaSucursal"].action = "RealizaAltaSucursal.do";
    			document.forms["formaAltaSucursal"].submit();
			}
		}
		else{
			jError('Todos los campos son obligatorios',                               
		   	   	   'Error en Alta Sucursales', 
		   	   	   'OBLICAMP',                               
		   	       '');                                         		
    	}
    	*/
    	if(validaCombo(document.getElementById("cmbNombreCorresponsal").value, "Corresponsal","Sucursal"))
			return;
    	if(validaCadena(document.getElementById("nombreSucursal").value, "Nombre de sucursal",1,40,"Sucursal"))
			return;
		if(validaCadena(document.getElementById("numId").value, "No. de identificación",4,10,"Sucursal"))
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
		
		document.forms["formaAltaSucursal"].action = "RealizaAltaSucursal.do";
    	document.forms["formaAltaSucursal"].submit();
	}
	
	function cerrar(){
    	document.forms["formaAltaSucursal"].action = "MuestraCorresponsalesSucursales.do";
    	document.forms["formaAltaSucursal"].submit();
	}
/*
	function validaNombre(valor, campo){
	    var regexp= new RegExp('^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ| ]{1,40}$');
	
	    if(!regexp.test(valor)){
	        alert('Error en el tipo de datos, por favor verifique la información del campo : '+campo);
	        return true;
	    }else
	        return false;
	}
	function validaCadenaRFC(valor, campo){
	    var regexp= new RegExp('^[A-Z]{4}[0-9]{6}[A-Z0-9]{3}$');
	
	    if(!regexp.test(valor)){
	        alert('Error en el tipo de datos, por favor verifique la información del campo : '+campo);
	        return true;
	    }else
	        return false;
	}
	function validaCadena(valor, campo, lonMin, lonMax){
	    var regexp= new RegExp('^[a-zA-Z0-9]{1,10}$');
	
	    if(!regexp.test(valor)){
	        alert('Error en el tipo de datos, por favor verifique la información del campo : '+campo);
	        return true;
	    }else
	        return false;
	}*/
	function validaCombo(valor, campo){
	    if(valor == "0"){
          			jError('Error en el tipo de datos, por favor verifique la información del campo : '+campo,                               //DESCRIPCION ERROR
		   	   	   'Error en Consulta Operaciones', 			//TITULO ERROR
		   	   	   '${error}',                               //CODIGO ERROR
		   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR
	        return true;
	    }else
	        return false;
	}
	/*function validaNumeros(valor, campo, lonMin, lonMax){
	    var regexp= new RegExp('^[0-9]{' + lonMin + ',' + lonMax + '}$');
	
	    if(!regexp.test(valor)){
	        alert('Error en el tipo de datos, por favor verifique la información del campo : '+campo);
	        return true;
	    }else
	        return false;
	}*/
</script>

<form id="formaAltaSucursal" action="" method="post">
<input type="hidden" id="sucursal" name="sucursal" value="default" />
    <input type="hidden" id="SES" name="SES" value="${SES}" />
	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Alta de Sucursal</span></div>
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
						<input type="text" class="CamposCompletar" id="nombreSucursal" name="nombreSucursal"  maxlength="40" value="${beanAlta.nombreSucursal}"/>
					</td>
					<td>
						<input type="text" class="CamposCompletar" id="numId" name="numId"  maxlength="10" value="${beanAlta.numId}"/>
					</td>
					<td>
						<select id="codigoEstatus" name="codigoEstatus" class="CamposCompletar">					 
							<option value="CS1" <c:if test="${beanAlta.codigoEstatus=='CS1'}"> selected</c:if>>Implementacion</option>
							<option value="CS2" <c:if test="${beanAlta.codigoEstatus=='CS2'}"> selected</c:if> default>Activo</option>
							<option value="CS3" <c:if test="${beanAlta.codigoEstatus=='CS3'}"> selected</c:if>>Bloqueado</option>
							<option value="CS4" <c:if test="${beanAlta.codigoEstatus=='CS4'}"> selected</c:if>>Baja</option>
							<option value="CS5" <c:if test="${beanAlta.codigoEstatus=='CS5'}"> selected</c:if>>Cancelado</option>
						</select>
					</td>
				</tr>
					<td class="text_izquierda">*RFC:</td>
					<td class="text_izquierda">*C&oacute;digo interno:</td>
					<td class="text_izquierda">*Tel&eacute;fono:</td>
					<tr>
						<td>
							<input type="text" class="CamposCompletar" id="rfc" name="rfc"  maxlength="13" value="${beanAlta.rfc}"/>
						</td>
						<td>
							<input type="text" class="CamposCompletar" id="codigoInterno" name="codigoInterno"  maxlength="10" value="${beanAlta.codigoInterno}"/>
						</td>
						<td>
							<input type="text" class="CamposCompletar" id="telefono" name="telefono"  maxlength="10" value="${beanAlta.telefono}" />
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
							<input type="text" class="CamposCompletar" id="calle" name="calle"  maxlength="20" size="50" value="${beanAlta.calle}"/>
						</td>
						<td>
							<input type="text" class="CamposCompletar" id="noExterior" name="noExterior"  maxlength="5" size="15" value="${beanAlta.noExterior}"/>
						</td>
						<td>
							<input type="text" class="CamposCompletar" id="noInterior" name="noInterior"  maxlength="5" size="15" value="${beanAlta.noInterior}"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="text_izquierda">*Colonia:</td>
						<td class="text_izquierda">Entre calles:</td>
						<td class="text_izquierda">*Delegaci&oacute;n/Municipio:</td>						
					</tr>
					<tr>
						<td colspan="2">
							<input type="text" class="CamposCompletar" id="colonia" name="colonia"  maxlength="20" size="45" value="${beanAlta.colonia}"/>
						</td>
						<td colspan="">
							<input type="text" class="CamposCompletar" id="entreCalles" name="entreCalles"  maxlength="40" value="${beanAlta.entreCalles}"/>
						</td>
						<td colspan="">
							<input type="text" class="CamposCompletar" id="delegMunicipio" name="delegMunicipio"  maxlength="20" size="25" value="${beanAlta.delegMunicipio}"/>
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
							<input type="text" class="CamposCompletar" id="ciudad" name="ciudad"  maxlength="20" size="20" value="${beanAlta.ciudad}"/>
						</td>
						<td colspan="">
							<input type="text" class="CamposCompletar" id="estado" name="estado"  maxlength="20" size="20" value="${beanAlta.estado}"/>
						</td>
						<td>
							<input type="text" class="CamposCompletar" id="codigoPostal" name="codigoPostal"  maxlength="8" value="${beanAlta.codigoPostal}"/>
						</td>
						<td>
							<select id="fronteriza" name="fronteriza" class="CamposCompletar">
									<option value="S" <c:if test="${beanAlta.fronteriza=='S'}"> selected</c:if>>SI</option>
									<option value="N" <c:if test="${beanAlta.fronteriza=='N'}"> selected</c:if>>NO</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*Zona geogr&aacute;fica:</td>
						<td class="text_izquierda">*Descripci&aacute;n de la Zona:</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="CamposCompletar" id="zonaGeografica" name="zonaGeografica"  maxlength="5" value="${beanAlta.zonaGeografica}"/>
						</td>
						<td colspan="2">
							<input type="text" class="CamposCompletar" id="descZona" name="descZona"  maxlength="30" value="${beanAlta.descZona}"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="text_izquierda">*Region:</td>
					</tr>
					<tr>
						<td colspan="3">						
							<input type="text" class="CamposCompletar" id="region" name="region"  size="30" maxlength="4" value="${beanAlta.region}"/>
						</td>
					</tr>
				</table>
			</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table width="100%">
					<tr>
						<td class="izq"><span><a href="javascript:altaSucursal();">Guardar</a></span></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="der"><span><a href="javascript:cerrar();">Cerrar</a></span></td>
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
