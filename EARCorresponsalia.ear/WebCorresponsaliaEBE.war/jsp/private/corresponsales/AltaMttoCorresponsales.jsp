<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="mantenimiento" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/comunes/validaciones.js"></script>
<script>
var  exitoConsulta = "<c:out value="${exitoConsulta}"/>";

if(exitoConsulta != ""){
			document.getElementById("txtGiroEmpresa").disabled=false;
			document.getElementById("txtNombreCorresponsal").disabled=false;
			document.getElementById("opcInformaComision").disabled=false;
			document.getElementById("txtCuentaCheques").disabled=false;
			document.getElementById("txtCuentaCredito").disabled=false;
			document.getElementById("txtLimiteImporteCorresponsal").disabled=false;
			document.getElementById("txtDiasPendientesConciliar").disabled=false;
			document.getElementById("txtDiasPendientesCompensar").disabled=false;
			document.getElementById("txtTelefonoAclaracionCorresponsal").disabled=false;
			document.getElementById("txtCorreoContacto").disabled=false;
			document.getElementById("txtCorreoAlternoContacto").disabled=false;
			
			document.getElementById("txtCalleDomicilioCorresponsal").disabled=true;
			document.getElementById("txtNumeroExteriorCorresponsal").disabled=true;
			document.getElementById("txtNumeroInteriorCorresponsal").disabled=true;
			document.getElementById("txtColoniaDomicilioCorresponsal").disabled=true;
			document.getElementById("txtDelegacionMunicipioCorresponsal").disabled=true;
			document.getElementById("txtCiudadCorresponsal").disabled=true;
			document.getElementById("txtEstadoCorresponsal").disabled=true;
			document.getElementById("txtCodigoPostal").disabled=true;
			document.getElementById("txtPaisProcedenciaCorresponsal").disabled=true;
			document.getElementById("txtTelefonoCorresponsal").disabled=true;
}

	function ValudaBUC(){
    	document.forms["formaAltaMttoCorresponsales"].action = "AltaMttoCorresponsales.do";
    	if(validaNumeros(document.getElementById("txtCodigoCliente").value, "BUC", 8, 8,"Corresponsales")){
    		document.getElementById("txtCodigoCliente").focus();
			return;
		}
	}
	function altaCorresponsal(){
    	document.forms["formaAltaMttoCorresponsales"].action = "AltaMttoCorresponsales.do";
    	if(validaNumeros(document.getElementById("txtCodigoCliente").value, "BUC", 8, 8,"Corresponsales")){
    		document.getElementById("txtCodigoCliente").focus();
			return;
		}
			
		if(validaNumeros(document.getElementById("txtSecuenciaDomicilio").value, "Secuencia domicilio", 3, 3,"Corresponsales"))
			return;
		if(validaNumeros(document.getElementById("txtGiroEmpresa").value, "Giro", 4, 4,"Corresponsales"))
			return;
		if(validaNumeros(document.getElementById("txtCentroConsto").value, "Centro de costos", 4, 4,"Corresponsales"))
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
		/*if(validaCorreo(document.getElementById("txtCorreoContacto").value, "E-mail Aclaraciones (Para)", 20,"Corresponsales"))
			return;*/
		/*if(validaCorreo(document.getElementById("txtCorreoAlternoContacto").value, "E-mail Aclaraciones (CC)", 20,"Corresponsales"))
			return;*/
    	document.forms["formaAltaMttoCorresponsales"].submit();
	}
	function cerrar(){
	
    	document.forms["formaAltaMttoCorresponsales"].action = "ConsultaMttoCorresponsales.do";
    	document.forms["formaAltaMttoCorresponsales"].submit();
    	
	}
	function changeDomicilio(secDomicilio){
		var valorSecuencia = document.getElementById("txtSecuenciaDomicilio").value.length;
		var valorCodigoCliente = document.getElementById("txtCodigoCliente").value.length;
		
		if(validaNumeros(document.getElementById("txtCentroConsto").value, "Centro de costos", 4, 4)){
			document.getElementById("txtCodigoCliente").focus();
			return;
			}
		
		if(valorCodigoCliente < 1){
			    	jAlert('Es necesario capturar el campo BUC (código de cliente).',                               //DESCRIPCION ERROR
	    			"Corresponsales", 						//TITULO ERROR
			   	   	   '---',                               //CODIGO ERROR
			   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR
			   	       document.getElementById("txtCodigoCliente").focus();			
			return;
		}
		if(document.getElementById("txtCodigoCliente").value==""){
			    	jAlert('Es necesario capturar el campo BUC (código de cliente).',                               //DESCRIPCION ERROR
	    			"Corresponsales", 						//TITULO ERROR
			   	   	   '---',                               //CODIGO ERROR
			   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR
			   	       document.getElementById("txtCodigoCliente").focus();			
			return;
		}
		if(valorSecuencia == 3){
			document.forms["formaAltaMttoCorresponsales"].action = "PreAlta.do";
			document.getElementById("txtCalleDomicilioCorresponsal").disabled=false;
			document.getElementById("txtGiroEmpresa").disabled=false;
			document.getElementById("txtNombreCorresponsal").disabled=false;
			document.getElementById("opcInformaComision").disabled=false;
			document.getElementById("txtCuentaCheques").disabled=false;
			document.getElementById("txtCuentaCredito").disabled=false;
			document.getElementById("txtLimiteImporteCorresponsal").disabled=false;
			document.getElementById("txtDiasPendientesConciliar").disabled=false;
			document.getElementById("txtDiasPendientesCompensar").disabled=false;
			document.getElementById("txtTelefonoAclaracionCorresponsal").disabled=false;
			document.getElementById("txtCorreoContacto").disabled=false;
			document.getElementById("txtCorreoAlternoContacto").disabled=false;
			
			document.getElementById("txtCalleDomicilioCorresponsal").disabled=false;
			document.getElementById("txtNumeroExteriorCorresponsal").disabled=false;
			document.getElementById("txtNumeroInteriorCorresponsal").disabled=false;
			document.getElementById("txtColoniaDomicilioCorresponsal").disabled=false;
			document.getElementById("txtDelegacionMunicipioCorresponsal").disabled=false;
			document.getElementById("txtCiudadCorresponsal").disabled=false;
			document.getElementById("txtEstadoCorresponsal").disabled=false;
			document.getElementById("txtCodigoPostal").disabled=false;
			document.getElementById("txtPaisProcedenciaCorresponsal").disabled=false;
			document.getElementById("txtTelefonoCorresponsal").disabled=false;
    		document.forms["formaAltaMttoCorresponsales"].submit();
		}else if(valorSecuencia < 3){
			document.getElementById("txtCalleDomicilioCorresponsal").disabled=true;
			document.getElementById("txtGiroEmpresa").disabled=true;
			document.getElementById("txtNombreCorresponsal").disabled=true;
			document.getElementById("opcInformaComision").disabled=true;
			document.getElementById("txtCuentaCheques").disabled=true;
			document.getElementById("txtCuentaCredito").disabled=true;
			document.getElementById("txtLimiteImporteCorresponsal").disabled=true;
			document.getElementById("txtDiasPendientesConciliar").disabled=true;
			document.getElementById("txtDiasPendientesCompensar").disabled=true;
			document.getElementById("txtTelefonoAclaracionCorresponsal").disabled=true;
			document.getElementById("txtCorreoContacto").disabled=true;
			document.getElementById("txtCorreoAlternoContacto").disabled=true;
			
			document.getElementById("txtCalleDomicilioCorresponsal").disabled=true;
			document.getElementById("txtNumeroExteriorCorresponsal").disabled=true;
			document.getElementById("txtNumeroInteriorCorresponsal").disabled=true;
			document.getElementById("txtColoniaDomicilioCorresponsal").disabled=true;
			document.getElementById("txtDelegacionMunicipioCorresponsal").disabled=true;
			document.getElementById("txtCiudadCorresponsal").disabled=true;
			document.getElementById("txtEstadoCorresponsal").disabled=true;
			document.getElementById("txtCodigoPostal").disabled=true;
			document.getElementById("txtPaisProcedenciaCorresponsal").disabled=true;
			document.getElementById("txtTelefonoCorresponsal").disabled=true;
		}
	}
		
	
</script>
<form id="formaAltaMttoCorresponsales" action="" method="post">
<input type="hidden" id="SES" name="SES" value="${SES}" />
<!-- Componente titulo de página -->
<div class="pageTitleContainer"><span class="pageTitle">Alta del Corresponsal</span></div>


<div class="frameBuscadorSimple">
<div class="titleBuscadorSimple">Datos generales</div>
<div class="contentBuscadorSimple">
<table>	 	 
	<tr>
		<td class="text_izquierda">*BUC (c&oacute;digo de cliente):</td>
		<td class="text_izquierda">*Secuencia domicilio:</td>
		<td class="text_izquierda">*Centro de costos:</td>
	</tr>
	<tr>
		<td><input type="text" value="${codigoCliente}" class="CamposCompletar"   id="txtCodigoCliente" name="txtCodigoCliente" maxlength="8" size="" /></td>
		<!--
		<td><input type="text" value="${codigoCliente}" class="CamposCompletar"   onchange="return changeDomicilio()" id="txtCodigoCliente" name="txtCodigoCliente" maxlength="8" size="" /></td>		  
		<td><input type="text" value="${secuenciaDomicilio}" onchange="return changeDomicilio()" class="CamposCompletar"   id="txtSecuenciaDomicilio" name="txtSecuenciaDomicilio" maxlength="3" size="" /></td>
		-->
		<td><input type="text" value="${secuenciaDomicilio}"  class="CamposCompletar"   id="txtSecuenciaDomicilio" name="txtSecuenciaDomicilio" maxlength="3" size="" /></td>
		<td><input type="text" value="${centroCosto}" class="CamposCompletar"  onchange="return changeDomicilio()" id="txtCentroConsto" name="txtCentroConsto" maxlength="4" size="" /></td>
	</tr>
	<tr>
		<td class="text_izquierda">*Giro:</td>
		<td colspan="" class="text_izquierda">*Nombre:</td>
		<td  class="text_izquierda">*Informa la comisión:</td>
	</tr>
	<tr>
		 
		<td><input type="text"   value="${giroEmpresa}" class="CamposCompletar"   id="txtGiroEmpresa" name="txtGiroEmpresa" maxlength="4" size="" /></td>
		 
		<!--
		<td><input type="text" value="${centroConsto}" class="CamposCompletar"  id="txtCentroConsto" name="txtCentroConsto" maxlength="4" size="" /></td>
		-->
		<td><input type="text"  ${Prealta} value="${nombreCorresponsal}" class="CamposCompletar"  id="txtNombreCorresponsal" name="txtNombreCorresponsal" maxlength="30" /></td>
		<td>
			<select id="opcInformaComision" name="opcInformaComision" style="width: 80px;" class="Campos"  ${PrealtaOk}>
				<option VALUE="1" selected="selected">SI</option>
				<option value="0">NO</option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="" class="text_izquierda">*Cuenta de cheques:</td>
		<td class="text_izquierda">*Cuenta de cr&eacute;dito:</td>
		<td class="text_izquierda">*L&iacute;mite de alerta:</td>
	</tr>
	<tr>
		<td><input type="text" ${Prealta}  value="${cuentaChequesCorresponsal}" class="CamposCompletar"  id="txtCuentaCheques" name="txtCuentaCheques" maxlength="20" /></td>
		<td><input type="text" ${Prealta}  value="${cuentaCredito}" class="CamposCompletar"  id="txtCuentaCredito" name="txtCuentaCredito" maxlength="20" /></td>
		<td><input type="text" ${Prealta}  value="${limiteImporteCorresponsal}" class="CamposCompletar"  id="txtLimiteImporteCorresponsal" name="txtLimiteImporteCorresponsal" maxlength="16" /></td>
	</tr>
	<tr>
		<td class="text_izquierda">*Dias para conciliar:</td>
		<td class="text_izquierda">*Dias para compensar:</td>
		<td class="text_izquierda">*Tel&eacute;fono:</td>
	</tr>
	<tr>
		<td><input type="text"  ${Prealta} value="${diasPendientesConciliar}" class="CamposCompletar"  id="txtDiasPendientesConciliar" name="txtDiasPendientesConciliar" maxlength="3"  /></td>
		<td><input type="text"  ${Prealta} value="${diasPendientesCompensar}" class="CamposCompletar"  id="txtDiasPendientesCompensar" name="txtDiasPendientesCompensar" maxlength="3" /></td>
		<td><input type="text"  ${Prealta} value="${telefonoAclaracionCorresponsal}" class="CamposCompletar"  id="txtTelefonoAclaracionCorresponsal" name="txtTelefonoAclaracionCorresponsal" maxlength="10" /></td>
	</tr>
	<tr>
		<td colspan="2" class="text_izquierda">E-mail Aclaraciones (Para):</td>
		<td colspan="2" class="text_izquierda">E-mail Aclaraciones (CC):</td>
	</tr>
	<tr>
		<td colspan="2"><input type="text" ${Prealta} value="${correoContacto}" class="Campos"  id="txtCorreoContacto" name="txtCorreoContacto" maxlength="20" size="25" /></td>
		<td colspan="2"><input type="text" ${Prealta} value="${correoAlternoContacto}" class="Campos"  id="txtCorreoAlternoContacto" name="txtCorreoAlternoContacto" maxlength="20" size="25" /></td>
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
		<td colspan="2"><input type="text" value="${beanRespuestaPreAlta.calleDomicilio}"   class="Campos_Des" id="txtCalleDomicilioCorresponsal" name="txtCalleDomicilioCorresponsal" maxlength="30" size="30" disabled/></td>
		<td><input type="text" value="${beanRespuestaPreAlta.numeroExtDomicilio}"   class="Campos_Des" id="txtNumeroExteriorCorresponsal" name="txtNumeroExteriorCorresponsal" maxlength="5" size="5" readonly disabled/></td>
		<td><input type="text" value="${beanRespuestaPreAlta.numeroIntDomicilio}"  class="Campos_Des" id="txtNumeroInteriorCorresponsal" name="txtNumeroInteriorCorresponsal" maxlength="5" size="5" readonly disabled/></td>
	</tr>
	<tr>
		<td colspan="2" class="text_izquierda">*Colonia:</td>
		<td class="text_izquierda">*Delegación/Municipio:</td>
		<td class="text_izquierda">*Ciudad:</td>
	</tr>
	<tr>
		<td colspan="2"><input type="text" value="${beanRespuestaPreAlta.coloniaDomicilio}"   class="Campos_Des" id="txtColoniaDomicilioCorresponsal" name="txtColoniaDomicilioCorresponsal" maxlength="30" size="30" readonly disabled/></td>
		<td colspan=""><input type="text" value="${beanRespuestaPreAlta.delegacionMunicipioDomicilio}"   class="Campos_Des" id="txtDelegacionMunicipioCorresponsal" name="txtDelegacionMunicipioCorresponsal" maxlength="30" size="20" readonly disabled/></td>
		<td colspan=""><input type="text" value="${beanRespuestaPreAlta.ciudadDomicilio}"   class="Campos_Des" id="txtCiudadCorresponsal" name="txtCiudadCorresponsal" maxlength="30" size="15" readonly disabled/></td>
	</tr>
	<tr>
		<td class="text_izquierda">*Estado:</td>
		<td class="text_izquierda">*C&oacute;digo Postal:</td>
		<td class="text_izquierda">*Pa&iacute;s:</td>
		<td class="text_izquierda">*Tel&eacute;fono:</td>
	</tr>
	<tr>
		<td colspan=""><input type="text" value="${beanRespuestaPreAlta.estadoDomicilio}"   class="Campos_Des" id="txtEstadoCorresponsal" name="txtEstadoCorresponsal" maxlength="30" size="20" readonly disabled/></td>
		<td><input type="text" value="${beanRespuestaPreAlta.codigoPostalDomicilio}"   class="Campos_Des" id="txtCodigoPostal" name="txtCodigoPostal" maxlength="30" size="7" disabled readonly/></td>
		<td><input type="text" value="${beanRespuestaPreAlta.paisDomicilio}"   class="Campos_Des" id="txtPaisProcedenciaCorresponsal" name="txtPaisProcedenciaCorresponsal" maxlength="30" size="15" readonly disabled/></td>
		<td><input type="text" value="${beanRespuestaPreAlta.telefonoDomicilio}"  class="Campos_Des" id="txtTelefonoCorresponsal" name="txtTelefonoCorresponsal" maxlength="30" size="15" readonly disabled/></td>
</table>
</div>
<div class="framePieContenedor">
<div class="contentPieContenedor">
<table width="100%">
	<tr>
		<td class="izq"><span><a href="javascript:altaCorresponsal();">Guardar</a></span></td>
		<td width="2px" class="odd">&nbsp;</td>
		<td  class="der"><span><a href="javascript:cerrar();">Cerrar</a></span></td>
	</tr>
</table>
</div>
</div>
</div>


</form>
<script>
document.forms["formaAltaMttoCorresponsales"].action = "AltaMttoCorresponsales.do";
document.getElementById("txtCodigoCliente").focus();
</script>
<c:if test="${not empty  msgResultado}">
		<script>
			jAlert('${msgResultado}',                               //DESCRIPCION ERROR
		   	   	   'MSG', //TITULO ERROR
		   	   	   '',                               //CODIGO ERROR
		   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR		
		</script>
	</c:if>