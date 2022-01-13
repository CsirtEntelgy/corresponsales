<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="sucursales" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script language="javascript">

	function cerrar(){
    	document.forms["formaDetalleSucursal"].action = "MuestraCorresponsalesSucursales.do";
    	document.forms["formaDetalleSucursal"].submit();
	}

</script>

<form id="formaDetalleSucursal" action="" method="post">
    <input type="hidden" id="SES" name="SES" value="${SES}" />
<input type="hidden" id="sucursal" name="sucursal" value="default" />
	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Detalle de Sucursal</span></div>
	 <div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Datos generales</div>
		<div class="contentBuscadorSimple">
			<table>
				<tr>
					<td class="text_izquierda">Corresponsal:</td>
						<td>
							<select id="cmbNombreCorresponsal" name="cmbNombreCorresponsal" class="Campos">
										<option VALUE="${corresponsalSel.codigoCorresponsal}" selected="selected">${corresponsalSel.nombreCorresponsal}</option>
							</select>
						</td>
				</tr>
				<tr>
						<td class="text_izquierda">Nombre de sucursal:</td>
						<td class="text_izquierda">No. de identificaci&oacute;n:</td>
						<td class="text_izquierda">Estatus:</td>
				</tr>
				<tr>
					<td>
						<input type="text" class="CamposCompletar" disabled="disabled" id="nombreSucursal" name="nombreSucursal"  maxlength="30" value="${detalleSucursal.nombreSucursal}"/>
					</td>
					<td>
						<input type="text" class="CamposCompletar" disabled="disabled" id="numId" name="numId"  maxlength="30" value="${detalleSucursal.numId}"/>
					</td>
					<td>
						<select id="codigoEstatus" disabled="disabled" name="codigoEstatus" class="CamposCompletar" onchange="filtro();limpiaDatos();">					 
							<option value="${detalleSucursal.codigoEstatus}" selected="selected">${detalleSucursal.descEstatus}</option>
						</select>
					</td>
				</tr>
					<td class="text_izquierda">RFC:</td>
					<td class="text_izquierda">C&oacute;digo interno:</td>
					<td class="text_izquierda">Tel&eacute;fono:</td>
					<tr>
						<td>
							<input type="text" class="CamposCompletar" disabled="disabled" id="rfc" name="rfc"  maxlength="30" value="${detalleSucursal.rfc}"/>
						</td>
						<td>
							<input type="text" class="CamposCompletar" disabled="disabled" id="codigoInterno" name="codigoInterno"  maxlength="30" value="${detalleSucursal.codigoInterno}"/>
						</td>
						<td>
							<input type="text" class="CamposCompletar" disabled="disabled" id="telefono" name="telefono"  maxlength="30" value="${detalleSucursal.telefono}" />
						</td>
					</tr>
			</table>
		</div>
		<div class="titleBuscadorSimple">Direcci&oacute;n</div>
			<div class="contentBuscadorSimple">
				<table>
					<tr>
						<td colspan="2" class="text_izquierda">Calle:</td>
						<td class="text_izquierda">No. Exterior:</td>
						<td class="text_izquierda">No. Interior:</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="text" class="CamposCompletar" disabled="disabled" id="calle" name="calle"  maxlength="30" size="50" value="${detalleSucursal.calle}"/>
						</td>
						<td>
							<input type="text" class="CamposCompletar" disabled="disabled" id="noExterior" name="noExterior"  maxlength="30" size="15" value="${detalleSucursal.noExterior}"/>

						</td>
						<td>
							<input type="text" class="CamposCompletar" disabled="disabled" id="noInterior" name="noInterior"  maxlength="30" size="15" value="${detalleSucursal.noInterior}"/>

						</td>
					</tr>
					<tr>
						<td colspan="2" class="text_izquierda">Colonia:</td>
						<td class="text_izquierda">Entre calles:</td>
						<td class="text_izquierda">Delegación/Municipio:</td>						
					</tr>
					<tr>
						<td colspan="2">
							<input type="text" class="CamposCompletar" disabled="disabled" id="colonia" name="colonia"  maxlength="30" size="45" value="${detalleSucursal.colonia}"/>
						</td>
						<td colspan="">
							<input type="text" class="CamposCompletar" disabled="disabled" id="entreCalles" name="entreCalles"  maxlength="30" value="${detalleSucursal.entreCalles}"/>
						</td>
						<td colspan="">
							<input type="text" class="CamposCompletar" disabled="disabled" id="delegMunicipio" name="delegMunicipio"  maxlength="30" size="25" value="${detalleSucursal.delegMunicipio}"/>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">Ciudad:</td>
						<td class="text_izquierda">Estado:</td>
						<td class="text_izquierda">C&oacute;digo Postal:</td>
						<td class="text_izquierda">Fronteriza:</td>
					</tr>
					<tr>
						<td colspan="">
							<input type="text" class="CamposCompletar" disabled="disabled" id="ciudad" name="ciudad"  maxlength="30" size="20" value="${detalleSucursal.ciudad}"/>
						</td>
						<td colspan="">
							<input type="text" class="CamposCompletar" disabled="disabled" id="estado" name="estado"  maxlength="30" size="20" value="${detalleSucursal.estado}"/>
						</td>
						<td>
							<input type="text" class="CamposCompletar" disabled="disabled" id="codigoPostal" name="codigoPostal"  maxlength="30" value="${detalleSucursal.codigoPostal}"/>
						</td>
						<td>
							<select id="fronteriza" name="fronteriza" class="CamposCompletar" disabled="disabled" onchange="filtro();limpiaDatos();">
							<!--<select id="tipDoc" name="tipDoc" class="CamposCompletar" onchange="filtro();limpiaDatos();">-->
									<option VALUE="0" selected="selected">${detalleSucursal.fronteriza}</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">Zona Geogr&aacute;fica:</td>
						<td class="text_izquierda">Descripci&oacute;n de la Zona:</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="CamposCompletar" disabled="disabled" id="zonaGeografica" name="zonaGeografica"  maxlength="30" value="${detalleSucursal.zonaGeografica}"/>
						</td>
						<td colspan="2">
							<input type="text" class="CamposCompletar" disabled="disabled" id="descZona" name="descZona"  maxlength="50" value="${detalleSucursal.descZona}"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="text_izquierda">Regi&oacute;n:</td>
					</tr>
					<tr>
						<td colspan="3">						
							<input type="text" class="CamposCompletar" disabled="disabled" id="region" name="region"  size="30" maxlength="30" value="${detalleSucursal.region}"/>
						</td>
					</tr>
				</table>
			</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table width="100%">
					<tr>
						<td class=""><span>&nbsp;</span></td>
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
