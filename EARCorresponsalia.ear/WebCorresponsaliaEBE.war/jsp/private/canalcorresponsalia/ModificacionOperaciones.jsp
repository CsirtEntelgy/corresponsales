<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="canalcorresponsalia" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	
	function cerrar(){
    	document.forms["formaModificaOperacion"].action = "ConsultaOperaciones.do";
    	document.forms["formaModificaOperacion"].submit();
	}	
	function consultaOperacionesCatalogo(){
	
	var indiceSeleccionado = document.forms["formaModificaOperacion"].cmbOperacionCatalogo.selectedIndex;
	var codigoCorresponsalia= document.forms["formaModificaOperacion"].cmbOperacionCatalogo.options[indiceSeleccionado].value;

	if(codigoCorresponsalia == '1'){
		document.forms["formaModificaOperacion"].action = "MuestraAltaOperacionesCatalogo.do";
		document.forms["formaModificaOperacion"].submit();
	}
		//document.forms["formaAltaOperaciones"].action = "AltaOperaciones.do";
    	//document.forms["formaAltaOperaciones"].submit();
	}
	function modificaOperacion(codigoOperacion){
	//document.getElementById("codigoOperacion").value=codigoOperacion;
	document.forms["formaModificaOperacion"].codigoOperacion.value=codigoOperacion;
		document.forms["formaModificaOperacion"].action = "ModificacionOperaciones.do";
		if(validaNoEspeciales(document.getElementById("txtDescripcion").value, "Descripción", 1, 50))
			return;
		if(validaNoEspeciales(document.getElementById("txtDescripcionCorta").value, "Desc. Corta", 1, 20))
			return;
		if(validaNoEspeciales(document.getElementById("txtValorR26").value, "ValorR26",1, 10))
			return;
		if(validaCombo(document.getElementById("txtNombre").value, "Nombre"))
			return;
		if(validaImporte(document.getElementById("txtImporteMaximo").value, "Importe máximo"))
			return;
		if(validaImporte(document.getElementById("txtImporteMinimo").value, "Importe mínimo"))
			return;
		if(validaImporte(document.getElementById("txtAcumuladoDiario").value, "Acumulado diario"))
			return;
		if(validaImporte(document.getElementById("txtAcumuladoMensual").value, "Acumulado mensual"))
			return;
		if(validaHora(document.getElementById("txtHoraInicio").value, "Hora inicio"))
			return;
		if(validaHora(document.getElementById("txtHoraFinal").value, "Hora fin"))
			return;
		if (validaVacio(document.getElementById("txtDescripcion").value,"Descripción"))
			return;
		if (validaVacio(document.getElementById("txtDescripcionCorta").value,"Desc. Corta"))
			return;
		if (validaVacio(document.getElementById("txtValorR26").value,"Valor en R26"))
			return;
		document.forms["formaModificaOperacion"].submit();
	}
	function validaVacio(valor,campo){
		if (valor.length<=0){
			
			jAlert('Por favor verifique la información del campo : '+campo,//DESCRIPCION ERROR
			   	   	   'Alerta Modificación de Operaciones', //TITULO ERROR
			   	   	   'Campo vacio',                               //CODIGO ERROR
			   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR
	        return true;
	    }else
	        return false;
	}
	
	function validaCombo(valor, campo){
	    if(valor == "0"){
	        //alert('Error en el tipo de datos, por favor verifique la información del campo : '+campo);
	        jAlert('Por favor verifique la información del campo : '+campo,//DESCRIPCION ERROR
			   	   	   'Alerta Modificación de Operaciones', //TITULO ERROR
			   	   	   'Error en el tipo de datos',                               //CODIGO ERROR
			   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR
	        return true;
	    }else
	        return false;
	}
	function validaImporte(valor, campo){
	    //var regexp= new RegExp('^[0-9]{1,5}(\.[0-9]{2})?$');
		var regexp= new RegExp('^[0-9]{1,13}$');
		
		if (valor.indexOf(".")>0) {
			if (valor.length>=4 && valor.length<=16) {
				if (valor.indexOf(".")==valor.length-3) {
					return false;
				} else {
					//alert('Error en el tipo de datos, por favor verifique la información del campo : '+campo);
					jAlert('Por favor verifique la información del campo : '+campo,//DESCRIPCION ERROR
			   	   	   'Alerta Modificación de Operaciones', //TITULO ERROR
			   	   	   'Error en el tipo de datos',                               //CODIGO ERROR
			   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR
					return true;
				}
			} else {
				//alert('Error en el tipo de datos, por favor verifique la información del campo : '+campo);
				jAlert('Por favor verifique la información del campo : '+campo,//DESCRIPCION ERROR
			   	   	   'Alerta Modificación de Operaciones', //TITULO ERROR
			   	   	   'Error en el tipo de datos',                               //CODIGO ERROR
			   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR
				return true;
			}
		} else {
			if(!regexp.test(valor)){
		        //alert('Error en el tipo de datos, por favor verifique la información del campo : '+campo);
		        jAlert('Por favor verifique la información del campo : '+campo,//DESCRIPCION ERROR
			   	   	   'Alerta Modificación de Operaciones', //TITULO ERROR
			   	   	   'Error en el tipo de datos',                               //CODIGO ERROR
			   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR
		        return true;
		    }else
		        return false;
		}
	}
	function validaHora(valor, campo){
	    var regexp= new RegExp('^([0-9]{2}):([0-9]{2})$');

	    if(!regexp.test(valor)){
	        //alert('Error en el tipo de datos, por favor verifique la información del campo : '+campo);
	        jAlert('Por favor verifique la información del campo : '+campo,//DESCRIPCION ERROR
			   	   	   'Alerta Modificación de Operaciones', //TITULO ERROR
			   	   	   'Error en el tipo de datos',                               //CODIGO ERROR
			   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR
	        return true;
	    }else
	        return false;
	}
	
	function validaNoEspeciales(valor, campo,lonMin,lonMax){
		var regexp= new RegExp('^[a-zA-Z0-9.,-/| ]{' + lonMin + ',' + lonMax + '}$');
	    //var regexp= new RegExp('^([a-zA-Z0-9]+[ ]*[a-zA-Z0-9]+){' + lonMin + ',' + lonMax + '}$');

	    if(!regexp.test(valor)){
	        //alert('Error en el tipo de datos, por favor verifique la información del campo : '+campo);
	        jAlert('Por favor verifique la información del campo : '+campo,//DESCRIPCION ERROR
			   	   	   'Alerta Modificación de Operaciones', //TITULO ERROR
			   	   	   'Error en el tipo de datos',                               //CODIGO ERROR
			   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR
	        return true;
	    }else
	        return false;
	}
	
</script>
<form id="formaModificaOperacion" action="" method="post">
<input type="hidden" id="codigoOperacion" name="codigoOperacion" />
<input type="hidden" name="SES" value="${SES}" />
<input type="hidden" name="trxAsoc" value="${trxAsoc}" />
<input type="hidden" name="trxAsocAnt" value="${trxAsocAnt}" />
<input type="hidden" name="consecOperCanal" value="${consecOperCanal}" />
<input type="hidden" name="equiTipOperUno" value="${equiTipOperUno}" />
<input type="hidden" name="equiTipOperTres" value="${equiTipOperTres}" />
<input type="hidden" name="fechAlta" value="${fechAlta}" />
	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Modificaci&oacute;n de Operaciones</span>
	</div>

	<!-- Componente formulario Parametria -->
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Datos generales de la operaci&oacute;n</div>
		<div class="contentBuscadorSimple">
			<table>
					<tr>
						<td colspan="3" class="text_izquierda">*Nombre:</td>
					</tr>
					<tr>
						<td colspan="3">
							<input type="text" class="Campos_Des" value="${nombre}" id="txtNombre" name="txtNombre"  maxlength="30" size="" readonly/>
						</td>
					</tr>
					<tr>
                        <td width="60%" class="text_izquierda">*Descripci&oacute;n:</td>
                        <td  width="20%" class="text_izquierda">*Desc. Corta:</td>
                        <td width="20%" class="text_izquierda">*Valor en R26:</td>
                    </tr>
                    <tr>
                        <td width="60%">
                            <input type="text" class="CamposCompletar" id="txtDescripcion" name="txtDescripcion"  maxlength="50" size="50" value="${descLarga}"/>
                        </td>
                        <td width="20%">
                            <input type="text" class="CamposCompletar" id="txtDescripcionCorta" name="txtDescripcionCorta"  maxlength="20" size="" value="${descCorta}"/>
                        </td>
                        <td width="20%">
                            <input type="text" class="CamposCompletar" id="txtValorR26" name="txtValorR26"  maxlength="10" size="10" value="${equiTipOperDos}"/>
                        </td>
                    </tr>
					<tr>
						<td class="text_izquierda">*Importe m&aacute;ximo:</td>
						<td class="text_izquierda">*Importe m&iacute;nimo:</td>
						<td class="text_izquierda">*Acumulado diario:</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="CamposCompletar" value="${importeMaximo}" id="txtImporteMaximo" name="txtImporteMaximo"  maxlength="30" size=""/>
						</td>
						<td>
							<input type="text" class="CamposCompletar" value="${importeMinimo}" id="txtImporteMinimo" name="txtImporteMinimo"  maxlength="30" />
						</td>
						<td>
							<input type="text" class="CamposCompletar" value="${acumuladoDiario}" id="txtAcumuladoDiario" name="txtAcumuladoDiario"  maxlength="30" size=""/>
						</td>
					</tr>
					<tr>
						<td class="text_izquierda">*Hora inicio:</td>
						<td class="text_izquierda">*Hora fin:</td>
						<td class="text_izquierda">*Acumulado mensual:</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="CamposCompletar" value="${horaInicio}" id="txtHoraInicio" name="txtHoraInicio"  maxlength="5" />
						</td>
						<td>
							<input type="text" class="CamposCompletar" value="${horaFinal}" id="txtHoraFinal" name="txtHoraFinal"  maxlength="5" />
						</td>
						<td>
							<input type="text" class="CamposCompletar" value="${acumuladoMensual}" id="txtAcumuladoMensual" name="txtAcumuladoMensual"  maxlength="30" />
						</td>
					</tr>
			</table>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table WIDTH="100%">
					<tr>
						<td  class="izq"><span><a href="javascript:modificaOperacion('${codigoOperacion}');" >Guardar</a></span></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td  class="der"><span><a href="javascript:cerrar();">Cerrar</a></span></td>
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