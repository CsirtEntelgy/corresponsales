<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="comisiones" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	
function AsignarComisiones(){

	document.forms["formaAsignacionComisiones"].txtTotal.disabled=false;
	if(validaCombo(document.getElementById("cmbNombreOperacion").value, "Operación"))
		return;
	if(validaImporte(document.getElementById("txtMontoCliente").value, "Por el Cliente"))
		return;
	if(validaImporte(document.getElementById("txtMontoBanco").value, "Por el Banco"))
		return;
	if(validaImporte(document.getElementById("txtTotal").value, "Total"))
		return;
	document.forms["formaAsignacionComisiones"].action = "RealizaAsignacionComision.do";
	document.forms["formaAsignacionComisiones"].submit();
}
function cerrar(){
	document.forms["formaAsignacionComisiones"].action = "ConsultaComisiones.do";
	document.forms["formaAsignacionComisiones"].submit();
}
function muestraEtiqueta(){
	var indiceSeleccionado = document.forms["formaAsignacionComisiones"].cmbNombreCorresponsal.selectedIndex;
	var nombreCorresponsal= document.forms["formaAsignacionComisiones"].cmbNombreCorresponsal.options[indiceSeleccionado].text;
	descripcionOperacion=nombreCorresponsal;
}


function sumaComision(asd){
	var montoCliente = parseFloat(document.getElementById("txtMontoCliente").value);  
	var montoBanco = parseFloat(document.getElementById("txtMontoBanco").value);
	total = montoCliente + montoBanco;
	if(montoCliente != "" && montoBanco != ""){
		document.getElementById("txtTotal").value = total.toFixed(2);
	}else{
		document.getElementById("txtTotal").value = "";
	}
}
function validaImporte(valor, campo){
	var regexp= new RegExp('^[0-9]{1,13}$');
	
	if (valor.indexOf(".")>0) {
		if (valor.length>=4 && valor.length<=16) {
			if (valor.indexOf(".")==valor.length-3) {
				return false;
			} else {
				jAlert('Por favor verifique la información del campo : '+campo,                               //DESCRIPCION ERROR
							'Error en el tipo de datos', //TITULO ERROR
				   	   	   '${codAviso}',                               //CODIGO ERROR
				   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR	
				
				return true;
			}
		} else {
			jAlert('Por favor verifique la información del campo : '+campo,                               //DESCRIPCION ERROR
					'Error en el tipo de datos', //TITULO ERROR
		   	   	   '${codAviso}',                               //CODIGO ERROR
		   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR
			return true;
		}
	} else {
		if(!regexp.test(valor)){
			jAlert('Por favor verifique la información del campo : '+campo,                               //DESCRIPCION ERROR
					'Error en el tipo de datos', //TITULO ERROR
		   	   	   '${codAviso}',                               //CODIGO ERROR
		   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR
	        return true;
	    }else
	        return false;
	}
}
function validaCombo(valor, campo){
    if(valor == "0"){
    	jAlert('Por favor verifique la información del campo : '+campo,                               //DESCRIPCION ERROR
				'Error en el tipo de datos', //TITULO ERROR
	   	   	   '${codAviso}',                               //CODIGO ERROR
	   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR
        return true;
    }else
        return false;
}
</script>

<form id="formaAsignacionComisiones" action="" method="post">
    <input type="hidden" id="SES" name="SES" value="${SES}" />
	<input type="hidden" id="corresponsaliaSeleccionada" name="corresponsaliaSeleccionada"  value="${beanCorresponsal.codigoCorresponsal}"/>


	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Comisiones por Corresponsal - Operación</span>
	</div>
	
	<!-- Componente formulario -->

	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Comisiones por Corresponsal - Operación</div>
		<div class="contentFormularioB">
			<table>
				<tr>
					<td class="odd">* Corresponsal:</td>
						<td>
							<select id="cmbNombreCorresponsal" name="cmbNombreCorresponsal" class="Campos">
										<option VALUE="${beanCorresponsal.codigoCorresponsal}" >${beanCorresponsal.nombreCorresponsal}</option>
							</select>
						</td>
						<td class="odd">* Operación:</td>
						<td>
							<select id="cmbNombreOperacion" name="cmbNombreOperacion" class="Campos" style="width: 120px;" onchange="javascript:muestraEtiqueta();">
										<option VALUE="0" selected="selected">Todas las operaciones</option>
										<c:forEach var="resultadoOperaciones" items="${listaOperacionesCatalogo}">
											<option VALUE="${resultadoOperaciones.codigoOperacion }">${resultadoOperaciones.desCorta}</option>
										</c:forEach>
							</select>
						</td>
				</tr>
			</table>
		</div>	
	</div>
	<!-- Componente tabla estandar -->
	<div class="frameTablaEstandar">
		<div class="titleTablaEstandar">
			${descripcionOperacion} <span class="textosin">- Montos a Pagar (Pesos)</span>
		</div>
		<table>
			<thead>
				<tr>
				<td class="odd">* Por el Cliente:</td>
				<td><input type="text" value="" class="CamposCompletar"   onChange="sumaComision(this)" id="txtMontoCliente" name="txtMontoCliente" maxlength="16" size="" /></td>
				<td class="odd">* Por el Banco:</td>
				<td><input type="text" value="" class="CamposCompletar"   onChange="sumaComision(this)" id="txtMontoBanco" name="txtMontoBanco" maxlength="16" size="" /></td>
				</tr>
				<tr>
				<td class="odd">* Total:</td>
				<td><input type="text" value="" class="CamposCompletar"   id="txtTotal" name="txtTotal" maxlength="16" size="" disabled/></td>
				<td></td>	
				<td></td>
				</tr>
			</thead>
			<tr>
				<Td colspan="6" class="special"></Td>
			</tr>

		</table>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table width="100%"> 
					<tr>
						<td class="izq"><span><a href="javascript:AsignarComisiones();">Guardar</a></span></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="der"><span><a href="javascript:cerrar()">Cerrar</a></span></td>
		
					</tr>
				</table>
			</div>
		</div>
	</div>
</form>
