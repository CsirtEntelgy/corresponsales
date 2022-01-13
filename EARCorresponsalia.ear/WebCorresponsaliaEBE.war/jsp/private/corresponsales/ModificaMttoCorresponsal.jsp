<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="mantenimiento" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/comunes/validaciones.js"></script>
<script>
	function cerrar(){
    	document.forms["formaModificaMttoCorresponsales"].action = "ConsultaMttoCorresponsales.do";
    	document.forms["formaModificaMttoCorresponsales"].submit();
	}
	
	function modificaCorresponsal(){
		if(validaNumeros(document.getElementById("txtGiroEmpresa").value, "Giro", 4, 4,"Corresponsales"))
			return;
		if(validaCadena(document.getElementById("txtNombreCorresponsal").value, "Nombre",5,30,"Corresponsales"))
			return;
		if(validaCuenta(document.getElementById("txtCuentaCheques").value, "Cuenta de cheques","Corresponsales"))
			return;
		if(validaCuenta(document.getElementById("txtCuentaCredito").value, "Cuenta de crédito","Corresponsales"))
			return;
		if(validaImporte(document.getElementById("txtLimiteImporteCorresponsal").value, "Límite de alerta","Corresponsales"))
			return;
		if(validaNumeros(document.getElementById("txtDiasPendientesConciliar").value, "Dias para conciliar", 1, 3,"Corresponsales"))
			return;
		if(validaNumeros(document.getElementById("txtDiasPendientesCompensar").value, "Dias para compensar", 1, 3,"Corresponsales"))
			return;
		if(validaNumeros(document.getElementById("txtTelefonoAclaracionCorresponsal").value, "Teléfono", 10, 10,"Corresponsales"))
			return;
		if(validaCorreo(document.getElementById("txtCorreoContacto").value, "E-mail Aclaraciones (Para)", 20,"Corresponsales"))
			return;
		if(validaCorreo(document.getElementById("txtCorreoAlternoContacto").value, "E-mail Aclaraciones (CC)", 20,"Corresponsales"))
			return;
	
     	document.forms["formaModificaMttoCorresponsales"].txtCentroConsto.disabled=false;
		document.forms["formaModificaMttoCorresponsales"].opcIndicaValidaComision.disabled=false;     
		// alert("Indicador Valida Comision: " + document.forms["formaModificaMttoCorresponsales"].opcIndicaValidaComision.value);	
     	document.forms["formaModificaMttoCorresponsales"].action = "ModificaMttoCorresponsales.do";
    	document.forms["formaModificaMttoCorresponsales"].submit();
	}

	
	
			
</script>

<form id="formaModificaMttoCorresponsales" action="" method="post">
<input type="hidden" id="SES" name="SES" value="${SES}" />
<!-- Componente titulo de página -->
<div class="pageTitleContainer"><span class="pageTitle">Modificar Corresponsal</span></div>
<input type="hidden" id="idCorresponsal" name="idCorresponsal" value="${codigoCorresponsal}">


<div class="frameBuscadorSimple">
<div class="titleBuscadorSimple">Datos generales</div>
<div class="contentBuscadorSimple">
 
<table>	 	 
	<tr>
		<td class="text_izquierda">BUC (c&oacute;digo de cliente):</td>
		<td class="text_izquierda">Secuencia domicilio:</td>
		<td class="text_izquierda">*Giro:</td>
	</tr>
	<tr>
		<td><input type="text" value="" class="Campos_Des"  disabled="disabled" id="txtCodigoCliente" name="txtCodigoCliente" maxlength="8" size="" readonly/></td>
		<td><input type="text" value="${beanResultado.secuenciaDomicilio}" class="Campos_Des"  id="txtSecuenciaDomicilio" name="txtSecuenciaDomicilio" maxlength="3" size="" disabled/></td>
		<td><input type="text" value="${beanResultado.giroEmpresa}" class="CamposCompletar"  id="txtGiroEmpresa" name="txtGiroEmpresa" maxlength="4" size="" /></td>
	</tr>
	<tr>
		<td class="text_izquierda">Centro de costos:</td>
		<td colspan="" class="text_izquierda">*Nombre:</td>
		<td size="25" class="text_izquierda">*Informa la comisión:</td>
	</tr>
	<tr>
		<td><input type="text" value="${beanResultado.centroCosto}" class="Campos_Des"  id="txtCentroConsto" name="txtCentroConsto" maxlength="4" size="" disabled/></td>
		<td><input type="text" value="${beanResultado.nombreCorresponsal}" class="CamposCompletar"  id="txtNombreCorresponsal" name="txtNombreCorresponsal" maxlength="30" /></td>
		<td>
			<select id="opcIndicaValidaComision" name="opcIndicaValidaComision" class="CamposCompletar" style="width: 40px;" onchange="filtro();limpiaDatos();">
				<c:if test="${beanResultado.indicadorValidaComision=='1'}"> 
				<option VALUE="1" selected="selected">SI</option>
				<option VALUE="0">NO</option>
				</c:if>
				<!-- La trama de respuesta está regresando un espacio en blanco, por el momento se valida y se deja por Default "SI" 
				140890CS2A0011111STEFA CORR 001                          00 00140029056722552017MXP00140561086465058859MXP000000020000000A@B.COM             A@B.COM             4421006000CONTRALOR_PRUEBALN7                         COZ Z H NORTE                 COZUMEL                       COZUMEL                                 00077600                    001015    Ó?
				-->
				<c:if test="${beanResultado.indicadorValidaComision==' '}"> 
				<option VALUE="1" selected="selected">SI</option>
				<option VALUE="0">NO</option>
				</c:if>

				<c:if test="${beanResultado.indicadorValidaComision=='0'}"> 
				<option VALUE="1">SI</option>
				<option VALUE="0" selected="selected">NO</option>
				</c:if>			
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="" class="text_izquierda">*Cuenta de cheques:</td>
		<td class="text_izquierda">*Cuenta de cr&eacute;dito:</td>
		<td class="text_izquierda">*L&iacute;mite de alerta:</td>
	</tr>
	<tr>
		<td><input type="text" value="${beanResultado.cuentaChequesCorresponsal}" class="CamposCompletar" id="txtCuentaCheques" name="txtCuentaCheques" maxlength="20" /></td>
		<td><input type="text" value="${beanResultado.lineaCreditoCorresponsal}" class="CamposCompletar"  id="txtCuentaCredito" name="txtCuentaCredito" maxlength="20" /></td>
		<td><input type="text" value="${beanResultado.limiteImporteCorresponsalFront}" class="CamposCompletar" id="txtLimiteImporteCorresponsal" name="txtLimiteImporteCorresponsal" maxlength="16" /></td>
	</tr>
	<tr>
		<td class="text_izquierda">*D&iacute;as para conciliar:</td>
		<td class="text_izquierda">*D&iacute;as para compensar:</td>
		<td class="text_izquierda">*Tel&eacute;fono:</td>
	</tr>
	<tr>
		<td><input type="text" value="${beanResultado.paramDiasPendientesConciliar}" class="CamposCompletar" id="txtDiasPendientesConciliar" name="txtDiasPendientesConciliar" maxlength="3" /></td>
		<td><input type="text" value="${beanResultado.paramDiasPendientesCompensar}" class="CamposCompletar" id="txtDiasPendientesCompensar" name="txtDiasPendientesCompensar" maxlength="3" /></td>
		<td><input type="text" value="${beanResultado.telefonoAclaracionCorresponsal}" class="CamposCompletar" id="txtTelefonoAclaracionCorresponsal" name="txtTelefonoAclaracionCorresponsal" maxlength="10" /></td>
	</tr>
	<tr>
		<td colspan="2" class="text_izquierda">*E-mail Aclaraciones (Para):</td>
		<td colspan="2" class="text_izquierda">*E-mail Aclaraciones (CC):</td>
	</tr>
	<tr>
		<td colspan="2"><input type="text" value="${beanResultado.correoContacto}" class="CamposCompletar" id="txtCorreoContacto" name="txtCorreoContacto" maxlength="20" size="25" /></td>
		<td colspan="2"><input type="text" value="${beanResultado.correoAlternoContacto}" class="CamposCompletar" id="txtCorreoAlternoContacto" name="txtCorreoAlternoContacto" maxlength="20" size="25" /></td>
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
		<td colspan="2"><input type="text" value="${beanResultado.calleDomicilioCorresponsal}" class="Campos_Des" id="txtCalleDomicilioCorresponsal" name="txtCalleDomicilioCorresponsal" maxlength="30" size="30" disabled/></td>
		<td><input type="text" value="${beanResultado.numeroExteriorCorresponsal}" class="Campos_Des" disabled="disabled" id="txtNumeroExteriorCorresponsal" name="txtNumeroExteriorCorresponsal" maxlength="5" size="5" /></td>
		<td><input type="text" value="${beanResultado.numeroInteriorCorresponsal}" class="Campos_Des" disabled="disabled" id="txtNumeroInteriorCorresponsal" name="txtNumeroInteriorCorresponsal" maxlength="5" size="5" /></td>
	</tr>
	<tr>
		<td colspan="2" class="text_izquierda">Colonia:</td>
		<td class="text_izquierda">Delegación/Municipio:</td>
		<td class="text_izquierda">Ciudad:</td>
	</tr>
	<tr>
		<td colspan="2"><input type="text" value="${beanResultado.coloniaDomicilioCorresponsal}"  class="Campos_Des" disabled="disabled" id="txtColoniaDomicilioCorresponsal" name="txtColoniaDomicilioCorresponsal" maxlength="30" size="30" /></td>
		<td colspan=""><input type="text" value="${beanResultado.delegacionMunicipioCorresponsal}" class="Campos_Des" disabled="disabled" id="txtDelegacionMunicipioCorresponsal" name="txtDelegacionMunicipioCorresponsal" maxlength="30" size="20" /></td>
		<td colspan=""><input type="text" value="${beanResultado.ciudadCorresponsal}" class="Campos_Des" disabled="disabled" id="txtCiudadCorresponsal" name="txtCiudadCorresponsal" maxlength="30" size="15" /></td>
	</tr>
	<tr>
		<td class="text_izquierda">Estado:</td>
		<td class="text_izquierda">C&oacute;digo Postal:</td>
		<td class="text_izquierda">Pa&iacute;s:</td>
		<td class="text_izquierda">Tel&eacute;fono:</td>
	</tr>
	<tr>
		<td colspan=""><input type="text" value="${beanResultado.estadoCorresponsal}" class="Campos_Des" disabled="disabled" id="txtEstadoCorresponsal" name="txtEstadoCorresponsal" maxlength="30" size="20" /></td>
		<td><input type="text" value="${beanResultado.codigoCorresponsal}" class="Campos_Des" disabled="disabled" id="txtCodigoCorresponsal" name="txtCodigoCorresponsal" maxlength="30" size="7"/></td>
		<td><input type="text" value="${beanResultado.paisProcedenciaCorresponsal}" class="Campos_Des" disabled="disabled" id="txtPaisProcedenciaCorresponsal" name="txtPaisProcedenciaCorresponsal" maxlength="30" size="15"/></td>
		<td><input type="text" value="${beanResultado.telefonoCorresponsal}" class="Campos_Des" disabled="disabled" id="txtTelefonoCorresponsal" name="txtTelefonoCorresponsal" maxlength="30" size="15"/></td>
</table>
</div>
<div class="framePieContenedor">
<div class="contentPieContenedor">
<table width="100%">
	<tr>
		<td class="izq"><span><a href="javascript:modificaCorresponsal();">Guardar</a></span></td>
		<td width="4px" class="odd">&nbsp;</td>
		<td class="der"><span><a href="javascript:cerrar();">Cerrar</a></span></td>
	</tr>
</table>
</div>
</div>
</div>


</form>