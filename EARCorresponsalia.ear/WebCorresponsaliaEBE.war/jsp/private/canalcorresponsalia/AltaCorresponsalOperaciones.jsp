<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"    prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt"%>

<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="canalcorresponsalia" />
</jsp:include>
<script>
	function guardaOperacion(){
		if(validaNoEspeciales(document.getElementById("txtDescripcion").value, "Descripcion",1,50))
			return;
		if(validaNoEspeciales(document.getElementById("txtDescripcionCorta").value, "Descripcion Corta",1,20))
			return;
		if(validaNoEspeciales(document.getElementById("txtValorR26").value, "ValorR26",1,10))
			return;
		document.getElementById("operacion").value='A';
    	document.forms["formaAltaOperacionesCatalogo"].action = "AltaOperacionesCatalogo.do";//"AltaOperacionesCatalogo.do";
    	document.forms["formaAltaOperacionesCatalogo"].submit();
    	
	}
	
	function validaBlanco(valor, campo){
	    var regexp= new RegExp('^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ| ]{1,50}$');

	    if(!regexp.test(valor)){
	        //alert('Error en el tipo de datos, por favor verifique la información del campo : '+campo);
	        jAlert('Por favor verifique la información del campo : '+campo,//DESCRIPCION ERROR
			   	   	   'Alerta Alta de Operaciones', //TITULO ERROR
			   	   	   'Error en el tipo de datos',                               //CODIGO ERROR
			   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR
	        return true;
	    }else
	        return false;
	    
	    
	}
	function validaNoEspeciales(valor, campo,lonMin,lonMax){
		var regexp= new RegExp('^[a-zA-Z0-9.,-/| ]{' + lonMin + ',' + lonMax + '}$');	
//	    var regexp= new RegExp('^([a-zA-Z0-9]+[ ]*[a-zA-Z0-9]+){' + lonMin + ',' + lonMax + '}$');

	    if(!regexp.test(valor)){
	        //alert('Error en el tipo de datos, por favor verifique la información del campo : '+campo);
	        jAlert('Por favor verifique la información del campo : '+campo,//DESCRIPCION ERROR
			   	   	   'Alerta Alta de Operaciones', //TITULO ERROR
			   	   	   'Error en el tipo de datos',                               //CODIGO ERROR
			   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR
	        return true;
	    }else
	        return false;
	}   
	function cerrar(){
		document.getElementById("operacion").value='A';
		document.forms["formaAltaOperacionesCatalogo"].action = "MuestraAltaModificacion.do";
    	document.forms["formaAltaOperacionesCatalogo"].submit();
	}
		
</script>

<form id="formaAltaOperacionesCatalogo" action="" method="post">
<input type="hidden" name="SES" value="${SES}" />
<input type="hidden" id="operacion" name="operacion" />
	<div class="pageTitleContainer">
		<span class="pageTitle">Alta de operaciones</span>
	</div>
	
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Datos generales de la operaci&oacute;n</div>
		<div class="contentBuscadorSimple">
			<table>
					<tr>
						<td width="60%" class="text_izquierda">*Descripci&oacute;n:</td>
						<td  width="20%" class="text_izquierda">*Desc. Corta:</td>
						<td width="20%" class="text_izquierda">*Valor en R26:</td>
					</tr>
					<tr>
						<td width="60%">
							<input type="text" class="CamposCompletar" id="txtDescripcion" name="txtDescripcion"  maxlength="50" size="50" />
						</td>
						<td width="20%">
							<input type="text" class="CamposCompletar" id="txtDescripcionCorta" name="txtDescripcionCorta"  maxlength="20" size="20"/>
						</td>
						<td width="20%">
							<input type="text" class="CamposCompletar" id="txtValorR26" name="txtValorR26"  maxlength="10" size="10"/>
						</td>
					</tr>
			</table>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table WIDTH="100%">
					<tr>
						<td class="izq"><span><a href="javascript:guardaOperacion();" >Guardar</a></span></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="der"><span><a href="javascript:cerrar();">Cerrar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</form>
<c:if test="${not empty  msgResultado}">
		<script>
			jError('${msgResultado}',                               //DESCRIPCION ERROR
		   	   	   'MSG', //TITULO ERROR
		   	   	   '',                               //CODIGO ERROR
		   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR		
		</script>
	</c:if>