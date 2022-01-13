<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="comisiones" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	
function ModificarComisiones(){   
    document.forms["formaModificaComisiones"].cmbNombreCorresponsal.disabled = false;
    document.forms["formaModificaComisiones"].cmbNombreOperacion.disabled = false;
   	if(validaImporte(document.getElementById("importeComisionCliente").value, "Por el Cliente"))
		return;
	if(validaImporte(document.getElementById("importeComisionCorresponsal").value, "Por el Banco"))
		return;
	if(validaImporte(document.getElementById("importeComisionTotal").value, "Total"))
		return;
	document.forms["formaModificaComisiones"].action = "RealizaModificacionComision.do";
	document.forms["formaModificaComisiones"].submit();
}
function cerrar(){
    document.forms["formaModificaComisiones"].cmbNombreCorresponsal.disabled = false;
	document.forms["formaModificaComisiones"].action = "ConsultaComisiones.do";
	document.forms["formaModificaComisiones"].submit();
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
function sumaComision(asd){
	var montoCliente = document.getElementById("importeComisionCliente").value;  
	var montoBanco = document.getElementById("importeComisionCorresponsal").value;
	total = parseFloat(montoCliente) + parseFloat(montoBanco);
	if(montoCliente != "" && montoBanco != ""){
		document.getElementById("importeComisionTotal").value = total.toFixed(2);
	}else{
		document.getElementById("importeComisionTotal").value = "";
	}
}

</script>

<form id="formaModificaComisiones" action="" method="post">
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
					<td class="odd"> Corresponsal:</td>
						<td>
							<select id="cmbNombreCorresponsal" name="cmbNombreCorresponsal" class="Campos" disabled="disabled">
									<option VALUE="${beanCorresponsal.codigoCorresponsal}" >${beanCorresponsal.nombreCorresponsal}</option>
							</select>
						</td>
						<td class="odd"> Operación:</td>
						<td>
							<select id="cmbNombreOperacion" name="cmbNombreOperacion" class="Campos" disabled="disabled">
									<option >${beanComisiones.descripcionOperacion}</option>
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
					<td><input type="text" value="${beanComisiones.importeComisionClienteMontoFijoFront}" onChange="sumaComision(this)" class="CamposCompletar"   id="importeComisionCliente" name="importeComisionCliente" maxlength="16" size="" /></td>
					<td class="odd">* Por el Banco:</td>
					<td><input type="text" value="${beanComisiones.importeComisionBancoMontoFijoFront}" onChange="sumaComision(this)" class="CamposCompletar"   id="importeComisionCorresponsal" name="importeComisionCorresponsal" maxlength="16" size="" /></td>
					</tr>
					<tr>
					<td class="odd">* Total:</td>
					<td><input type="text" value="${beanComisiones.importeComisionTotalFront}" class="CamposCompletar"   id="importeComisionTotal" name="importeComisionTotal" maxlength="16" size="" readonly/></td>
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
							<td class="izq"><span><a href="javascript:ModificarComisiones();">Guardar</a></span></td>	
							<td width="4px" class="odd">&nbsp;</td>
							<td class="der"><span><a href="javascript:cerrar()">Cerrar</a></span></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
</form>
