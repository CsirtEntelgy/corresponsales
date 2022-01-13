<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="mantenimiento" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>

	function cerrar(){
    	document.forms["formaDetalleMttoCorresponsales"].action = "ConsultaMttoCorresponsales.do";
    	document.forms["formaDetalleMttoCorresponsales"].submit();
	}
			
</script>

<form id="formaDetalleMttoCorresponsales" action="" method="post">
<input type="hidden" id="SES" name="SES" value="${SES}" />
<!-- Componente titulo de página -->
<div class="pageTitleContainer"><span class="pageTitle">Detalle del Corresponsal</span></div>
<div class="frameBuscadorSimple">
<div class="titleBuscadorSimple">Datos generales</div>
<div class="contentBuscadorSimple"> 
<table>	 	 
	<tr>
		<td class="text_izquierda">*BUC (c&oacute;digo de cliente):</td>
		<td class="text_izquierda">*Secuencia domicilio:</td>
		<td class="text_izquierda">*Giro:</td>
	</tr>
	<tr>
		<td><input type="text" value="" class="Campos_Des"  disabled="disabled" id="txtCodigoCliente" name="txtCodigoCliente" maxlength="30" size="" /></td>
		<td><input type="text" value="${beanResultado.secuenciaDomicilio}" class="Campos_Des"  disabled="disabled" id="txtSecuenciaDomicilio" name="txtSecuenciaDomicilio" maxlength="30" size="" /></td>
		<td><input type="text" value="${beanResultado.giroEmpresa}" class="Campos_Des"  disabled="disabled" id="txtGiroEmpresa" name="txtGiroEmpresa" maxlength="30" size="" /></td>
	</tr>
	<tr>
		<td class="text_izquierda">*Centro de costos:</td>
		<td colspan="" class="text_izquierda">*Nombre:</td>
		<td size="25" class="text_izquierda">*Informa la comisi&oacute;n:</td>
	</tr>
	<tr>
		<td><input type="text" value="${beanResultado.centroCosto}" class="Campos_Des" disabled="disabled" id="txtCentroConsto" name="txtCentroConsto" maxlength="30" size="" /></td>
		<td><input type="text" value="${beanResultado.nombreCorresponsal}" class="Campos_Des" disabled="disabled" id="txtNombreCorresponsal" name="txtNombreCorresponsal" maxlength="30" /></td>
		<td>
		<!--  
			<select id="opcIndicaValidaComision" name="opcIndicaValidaComision" class="Campos_Des" disabled="disabled" onchange="filtro();limpiaDatos();">
				<option VALUE="${beanResultado.indicadorValidaComision}" selected="selected">${beanResultado.indicadorValidaComision}</option>
			</select>
		-->
			<select id="opcIndicaValidaComision" name="opcIndicaValidaComision" class="Campos_Des" disabled="disabled" style="width: 40px;" onchange="filtro();limpiaDatos();">
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
		<td><input type="text" value="${beanResultado.cuentaChequesCorresponsal}" class="Campos_Des" disabled="disabled" id="txtCuentaCheques" name="txtCuentaCheques" maxlength="30" /></td>
		<td><input type="text" value="${beanResultado.lineaCreditoCorresponsal}" class="Campos_Des" disabled="disabled" id="txtCuentaCredito" name="txtCuentaCredito" maxlength="30" /></td>
		<td><input type="text" value="${beanResultado.limiteImporteCorresponsalFront}" class="Campos_Des" disabled="disabled" id="txtLimiteImporteCorresponsal" name="txtLimiteImporteCorresponsal" maxlength="30" /></td>
	</tr>
	<tr>
		<td class="text_izquierda">*D&iacute;as para conciliar:</td>
		<td class="text_izquierda">*D&iacute;as para compensar:</td>
		<td class="text_izquierda">*Tel&eacute;fono:</td>
	</tr>
	<tr>
		<td><input type="text" value="${beanResultado.paramDiasPendientesConciliar}" class="Campos_Des" disabled="disabled" id="txtDiasPendientesConciliar" name="txtDiasPendientesConciliar" maxlength="30" /></td>
		<td><input type="text" value="${beanResultado.paramDiasPendientesCompensar}" class="Campos_Des" disabled="disabled" id="txtDiasPendientesCompensar" name="txtDiasPendientesCompensar" maxlength="30" /></td>
		<td><input type="text" value="${beanResultado.telefonoAclaracionCorresponsal}" class="Campos_Des" disabled="disabled" id="txtTelefonoAclaracionCorresponsal" name="txtTelefonoAclaracionCorresponsal" maxlength="30" /></td>
	</tr>
	<tr>
		<td colspan="2" class="text_izquierda">*E-mail Aclaraciones (Para):</td>
		<td colspan="2" class="text_izquierda">*E-mail Aclaraciones (CC):</td>
	</tr>
	<tr>
		<td colspan="2"><input type="text" value="${beanResultado.correoContacto}" class="Campos_Des" disabled="disabled" id="txtCorreoContacto" name="txtCorreoContacto" maxlength="30" size="25" /></td>
		<td colspan="2"><input type="text" value="${beanResultado.correoAlternoContacto}" class="Campos_Des" disabled="disabled" id="txtCorreoAlternoContacto" name="txtCorreoAlternoContacto" maxlength="30" size="25" /></td>
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
		<td colspan="2"><input type="text" value="${beanResultado.calleDomicilioCorresponsal}" class="Campos_Des" disabled="disabled" id="txtCalleDomicilioCorresponsal" name="txtCalleDomicilioCorresponsal" maxlength="30" size="30" /></td>
		<td><input type="text" value="${beanResultado.numeroExteriorCorresponsal}" class="Campos_Des" disabled="disabled" id="txtNumeroExteriorCorresponsal" name="txtNumeroExteriorCorresponsal" maxlength="5" size="5" /></td>
		<td><input type="text" value="${beanResultado.numeroInteriorCorresponsal}" class="Campos_Des" disabled="disabled" id="txtNumeroInteriorCorresponsal" name="txtNumeroInteriorCorresponsal" maxlength="5" size="5" /></td>
	</tr>
	<tr>
		<td colspan="2" class="text_izquierda">*Colonia:</td>
		<td class="text_izquierda">*Delegaci&oacute;n/Municipio:</td>
		<td class="text_izquierda">*Ciudad:</td>
	</tr>
	<tr>
		<td colspan="2"><input type="text" value="${beanResultado.coloniaDomicilioCorresponsal}"  class="Campos_Des" disabled="disabled" id="txtColoniaDomicilioCorresponsal" name="txtColoniaDomicilioCorresponsal" maxlength="30" size="30" /></td>
		<td colspan=""><input type="text" value="${beanResultado.delegacionMunicipioCorresponsal}" class="Campos_Des" disabled="disabled" id="txtDelegacionMunicipioCorresponsal" name="txtDelegacionMunicipioCorresponsal" maxlength="30" size="20" /></td>
		<td colspan=""><input type="text" value="${beanResultado.ciudadCorresponsal}" class="Campos_Des" disabled="disabled" id="txtCiudadCorresponsal" name="txtCiudadCorresponsal" maxlength="30" size="15" /></td>
	</tr>
	<tr>
		<td class="text_izquierda">*Estado:</td>
		<td class="text_izquierda">*C&oacute;digo Postal:</td>
		<td class="text_izquierda">*Pa&iacute;s:</td>
		<td class="text_izquierda">*Tel&eacute;fono:</td>
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
<table width ="100%">
	<tr>
		<td class=""><span>&nbsp;</span></td>
		<td width="4px" class="odd">&nbsp;</td>
		<td  class="der"><span><a href="javascript:cerrar();">Cerrar</a></span></td>
	</tr>
</table>
</div>
</div>
</div>


</form>