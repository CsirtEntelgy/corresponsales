<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"    prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt"%>

<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="consultas" />
	<jsp:param name="menuSubitem" value="bitacora" />
</jsp:include>

<script>

function esCampoAlfanumerico(campo) {
	//var RegExPattern = /\w/;
	var RegExPattern = new RegExp('^[a-zA-Z0-9| ]{1,20}$');
	
	  if(RegExPattern.test(campo)) 
	    return true;
	    
    return false;
}

	function esCampoEntero(campo) {	
		var RegExPattern = /^(?:\+|-)?\d+$/;		
	    if (RegExPattern.test(campo)) 
	    	return true;	    
	    return false;
	}
	function validaDecimales(campo) {
		if (campo.indexOf(".") >= 0) {
			if ((campo.length - (campo.indexOf(".") + 1)) > 2)
				return false;
		}	    
	    return true;
	}
	
	function obtenerOperacionesSucursales(){
		if(document.forms["formConsultaBitacora"].optCorresponsal.value!="sinsel"){
    		document.forms["formConsultaBitacora"].action = "BitacoraConsultaOperacionesSucursales.do";
    		document.forms["formConsultaBitacora"].submit();
    	}else{
    		document.forms["formConsultaBitacora"].optCorresponsal.value = document.forms["formConsultaBitacora"].comVal.value;    		
        	jAlert('Seleccione un Corresponsal',                               //DESCRIPCION ERROR
		   	   	   'Bitacora', 						//TITULO ERROR
		   	   	   '---',                               //CODIGO ERROR
		   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR	
	
    		
    	}
	}
	
	function reset(){
		/*
		document.forms["formConsultaBitacora"].txtFecha.value="";
		document.forms["formConsultaBitacora"].txtFolio.value="";
		document.forms["formConsultaBitacora"].txtReferencia.value="";
		document.forms["formConsultaBitacora"].txtCuentaTarjeta.value="";
    	document.forms["formConsultaBitacora"].txtImporte.value="";
    	*/
    	document.forms["formConsultaBitacora"].action = "BitacoraConsultaCorresponsales.do";
    	document.forms["formConsultaBitacora"].submit();
	}

	
	function validaReferenciaInterna(){
		
		var campo = document.forms["formConsultaBitacora"].txtReferencia.value;
		if(campo!=""){
			if(!esCampoAlfanumerico(campo))
				return false;
		}
		
		return true;
	}

	function validaImporte(){
	//var regexp= new RegExp('^[0-9]{1,13}$');
	var regexp= new RegExp('^([^a-zA-Z]|[^a-zA-Z]([0-9]{1,13})|([0-9]{1,13})+(.[0-9]{2}))$');
	
		if(!regexp.test(document.forms["formConsultaBitacora"].txtImporte.value)){
	        return false;
	    }else
	        return true;	
		return true;
	}

	function validaFolio(){
		var valOk = true;
		var campo = document.forms["formConsultaBitacora"].txtFolio.value;
		if(campo!=""){
			if(!esCampoAlfanumerico(campo))
				return false;
		}
		
		return true;
	}

	function validaCuentaTarjeta(){
		var valOk = true;
		var campo = document.forms["formConsultaBitacora"].txtCuentaTarjeta.value;
		if(campo!=""){
			if(!esCampoEntero(campo))
				return false;
			if(campo.length!=10 && campo.length!=11 && campo.length!=16 && campo.length!=20)
				return false;
		}
		
		return true;
	}

	function validaCamposObligatorios(){
		var camposOk = true;
		if(document.forms["formConsultaBitacora"].optCorresponsal.value=="sinsel"){
			camposOk = false;
    		jAlert('Debe de seleccionar un corresponsal', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitácora',          //TITULO ERROR
		   	       'CAPSI001',                            //CODIGO ERROR
		   	       '');  
		}
		else if(document.forms["formConsultaBitacora"].optSucursal.value=="sinsel"){
			camposOk = false;
    		jAlert('Debe de seleccionar una sucursal', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitácora',          //TITULO ERROR
		   	       'CAPSI001',                            //CODIGO ERROR
		   	       '');  
		}	
		else if(document.forms["formConsultaBitacora"].txtFecha.value==""){
			camposOk = false;
    		jAlert('Debe informar la fecha', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitácora',          //TITULO ERROR
		   	       'CAPSI002',                            //CODIGO ERROR
		   	       '');  
		}
		else if(!validaReferenciaInterna()){
			camposOk = false;
    		jAlert('El campo Referencia Interna es incorrecto', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitácora',          //TITULO ERROR
		   	       'CAPSI003',                            //CODIGO ERROR
		   	       'El campo Referencia Interna debe de ser alfanumerico');  
		}
		else if(!validaFolio()){
			camposOk = false;
    		jAlert('El campo Folio es incorrecto', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitácora',          //TITULO ERROR
		   	       'CAPSI004',                            //CODIGO ERROR
		   	       'El campo Folio debe de ser alfanum&eacute;rico');  
		}
		else if(!validaCuentaTarjeta()){
			camposOk = false;
    		jAlert('El campo Cuenta/Tarjeta es incorrecto', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitácora',          //TITULO ERROR
		   	       'CAPSI005',                            //CODIGO ERROR
		   	       'El campo Cuenta/Tarjeta de ser num&eacute;rico \n Con longitud de 11, 16 o 20 digitos');  
		}
		else if(document.forms["formConsultaBitacora"].txtImporte.value!= "" && !validaImporte()){
			camposOk = false;
    		jAlert('El campo Importe es incorrecto', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitácora',          //TITULO ERROR
		   	       'CAPSI006',                            //CODIGO ERROR
		   	       'El campo Importe de ser entero o decimal (no más de dos decimales)');  
		}
		
		
		return camposOk;
	}
	
	function guardaCombo(){		
	    comboVal.value = document.forms["formConsultaBitacora"].optCorresponsal.value;
	}
			
	function consultar(avanzarRetroceder){
		if(validaCamposObligatorios()){
	    	document.forms["formConsultaBitacora"].opcAvanzarRetroceder.value=avanzarRetroceder; 
    		document.forms["formConsultaBitacora"].action = "RealizaConsultaBitacora.do";
    		document.forms["formConsultaBitacora"].submit();
    	}else{
    	    document.forms["formConsultaBitacora"].optCorresponsal.value = comboVal.value;
    	}	    	
	}

		
			
	function anular(numReg){
			jConfirm("Seguro que desea eliminar el registro?","Eliminar Registro","","",function(e){
					if(e){
	    				document.forms["formConsultaBitacora"].regAnular.value=numReg; 
     					document.forms["formConsultaBitacora"].action = "BitacoraAnular.do";  	
    					document.forms["formConsultaBitacora"].submit();
					}
					return e;
				});
	}

	function exportar(){
    	document.forms["formConsultaBitacora"].action = "ExportarBitacora.do";
    	document.forms["formConsultaBitacora"].submit();
	}

	function habilitadeshabilita(){
		
		var valEstatus = document.forms["formConsultaBitacora"].optEstatus.value;
		if(valEstatus=="NIN"){
			document.forms["formConsultaBitacora"].optOperacion.disabled=false;
			document.forms["formConsultaBitacora"].txtReferencia.disabled=false;
			document.forms["formConsultaBitacora"].txtFolio.disabled=false;
			document.forms["formConsultaBitacora"].txtImporte.disabled=false;
			document.forms["formConsultaBitacora"].txtCuentaTarjeta.disabled=false;

			document.forms["formConsultaBitacora"].optOperacion.style.background = '#C6F3FF';
			document.forms["formConsultaBitacora"].txtReferencia.style.background = '#FFFFFF';
			document.forms["formConsultaBitacora"].txtFolio.style.background = '#FFFFFF';
			document.forms["formConsultaBitacora"].txtImporte.style.background = '#FFFFFF';
			document.forms["formConsultaBitacora"].txtCuentaTarjeta.style.background = '#FFFFFF';
		} else {
			document.forms["formConsultaBitacora"].optOperacion.disabled=true;
			document.forms["formConsultaBitacora"].txtReferencia.disabled=true;
			document.forms["formConsultaBitacora"].txtFolio.disabled=true;
			document.forms["formConsultaBitacora"].txtImporte.disabled=true;
			document.forms["formConsultaBitacora"].txtCuentaTarjeta.disabled=true;

			document.forms["formConsultaBitacora"].optOperacion.style.background = '#E5E5E5';
			document.forms["formConsultaBitacora"].txtReferencia.style.background = '#E5E5E5';
			document.forms["formConsultaBitacora"].txtFolio.style.background = '#E5E5E5';
			document.forms["formConsultaBitacora"].txtImporte.style.background = '#E5E5E5';
			document.forms["formConsultaBitacora"].txtCuentaTarjeta.style.background = '#E5E5E5';

			document.forms["formConsultaBitacora"].optOperacion.value = 'todas';
			document.forms["formConsultaBitacora"].txtReferencia.value = '';
			document.forms["formConsultaBitacora"].txtFolio.value = '';
			document.forms["formConsultaBitacora"].txtImporte.value = '';
			document.forms["formConsultaBitacora"].txtCuentaTarjeta.value = '';
		}
		
	}
	
	function ini(){
		createCalendar('txtFecha','calBusqueda');
	}

	function cerrar(){
		document.getElementById('tFrameTable').style.display = 'none';
    	document.forms["formConsultaBitacora"].action = "../principal/inicio.do";
    	document.forms["formConsultaBitacora"].submit();
	}

	function deshabilita() {
		if (document.getElementById('txtFolio').value != '') {
			document.getElementById('txtReferencia').value = '';
			document.getElementById('txtReferencia').disabled = true;
			document.getElementById('txtReferencia').style.background = '#E5E5E5';
		} else {
			document.getElementById('txtReferencia').disabled = false;
			document.getElementById('txtReferencia').style.background = '#FFFFFF';
		} 
		if (document.getElementById('txtReferencia').value != '') {
			document.getElementById('txtFolio').value = '';
			document.getElementById('txtFolio').disabled = true;
			document.getElementById('txtFolio').style.background = '#E5E5E5';
		} else {
			document.getElementById('txtFolio').disabled = false;
			document.getElementById('txtFolio').style.background = '#FFFFFF';
		}
	}
			
</script>


<form id="formConsultaBitacora" action="" method="post" >
    <input type="hidden" id="SES" name="SES" value="${SES}" />
	<input type="hidden" value="" id="opcAvanzarRetroceder" name="opcAvanzarRetroceder" />
	<input type="hidden" value="" id="regAnular" name="regAnular" />
	<input type="hidden" value="sinsel" id="comVal" name="comVal" />
	
	<!-- Componente titulo de pagina -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Consulta de Bit&aacute;cora</span>
	</div>
	
	<!-- Componente formulario -->
	
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Filtros</div>
			<div class="contentFormularioB">
				<table>
					<tr>
						<td class="odd">*Corresponsal:</td>
						<td>
							<select name="optCorresponsal" class="CamposCompletar" id="optCorresponsal" onchange="javascript:obtenerOperacionesSucursales();" /
							onfocus="javascript:comVal.value = this.value;" onFocusin="javascript:comVal.value = this.value;">
								<c:choose>
									<c:when test="${not empty beanConsultaBitacora.idCorresponsal}">
										<option VALUE="sinsel" selected="selected">--Seleccione un corresponsal</option>
									</c:when>
									<c:otherwise>
										<option VALUE="sinsel">--Seleccione un corresponsal</option>
									</c:otherwise>									
								</c:choose>
								<c:forEach var="corresponsal" items="${listaCorresponsalesBitacora}" >
									<c:choose>
										<c:when test="${beanConsultaBitacora.idCorresponsal == corresponsal.codigoCorresponsal}">
											<option VALUE="${corresponsal.codigoCorresponsal}" selected="selected">${corresponsal.nombreCorresponsal}</option>
										</c:when>
										<c:otherwise>
											<option VALUE="${corresponsal.codigoCorresponsal}">${corresponsal.nombreCorresponsal}</option>
										</c:otherwise>
									</c:choose>
			    					  
			    				</c:forEach>						
								
							</select>
						</td>
						<td class="odd">Sucursal:</td>
						<td>
							<select name="optSucursal" class="CamposCompletar" id="optSucursal"  style="width:130px;" />
								<c:choose>
									<c:when test="${not empty beanConsultaBitacora.codIdSucursal}">
										<option VALUE="sinsel" selected="selected">--Seleccione</option>
									</c:when>
									<c:otherwise>
										<option VALUE="sinsel">--Seleccione</option>
									</c:otherwise>									
								</c:choose>
								<c:forEach var="sucursal" items="${listaSucursalesBitacora}" >
									<c:choose>
										<c:when test="${beanConsultaBitacora.codIdSucursal == sucursal.numId}">
											<option VALUE="${sucursal.numId}" selected="selected">${sucursal.nombreSucursal}</option>
										</c:when>
										<c:otherwise>
											<option VALUE="${sucursal.numId}">${sucursal.nombreSucursal}</option>
										</c:otherwise>
									</c:choose>
			    				</c:forEach>						
							</select>
						</td>
					</tr>
					<tr>
						<td class="odd">Estatus:</td>
						<td size="50">
							<select name="optEstatus" class="CamposCompletar" id="optEstatus" onchange="javascript:habilitadeshabilita();" />
										<option VALUE="NIN" selected="selected">TODOS</option>
										<option VALUE="A01" <c:if test="${beanConsultaBitacora.estatus=='A01'}"> selected</c:if>>PREVALIDACION</option>           
										<option VALUE="A02" <c:if test="${beanConsultaBitacora.estatus=='A02'}"> selected</c:if>>REGISTRADA</option>              
										<option VALUE="A03" <c:if test="${beanConsultaBitacora.estatus=='A03'}"> selected</c:if>>CONCILIADA</option>              
										<option VALUE="A04" <c:if test="${beanConsultaBitacora.estatus=='A04'}"> selected</c:if>>PENDIENTE CONCILIAR</option>     
										           
										<option VALUE="A06" <c:if test="${beanConsultaBitacora.estatus=='A06'}"> selected</c:if>>POR COMPENSAR</option>           
										<option VALUE="A07" <c:if test="${beanConsultaBitacora.estatus=='A07'}"> selected</c:if>>PENDIENTE COMPENSAR</option>     
										<option VALUE="A08" <c:if test="${beanConsultaBitacora.estatus=='A08'}"> selected</c:if>>VENCIDA POR COMPENSAR</option>   
										<option VALUE="A09" <c:if test="${beanConsultaBitacora.estatus=='A09'}"> selected</c:if>>ANULADA CONTRAOPERACION</option> 
										<option VALUE="A10" <c:if test="${beanConsultaBitacora.estatus=='A10'}"> selected</c:if>>COMPENSADA</option>              
										           
										<option VALUE="A13" <c:if test="${beanConsultaBitacora.estatus=='A13'}"> selected</c:if>>INCIDENCIA</option>
									</select>
						</td>
						<td width="50" class="odd">*Fecha:</td>
						<td width="120">
							<input name="txtFecha" type="text" class="CamposCompletar" id="txtFecha" readonly="readonly" value="${beanConsultaBitacora.fechaAlta}" style="width:80px;"/>
							<img id="calBusqueda" src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/calendar/calendar.png">
						</td>
					</tr>
					<tr>
						<td class="odd">Tipo Operaci&oacute;n:</td>
						<td>
							<select name="optOperacion" class="CamposCompletar" id="optOperacion"/>								
								 
								<c:choose>
									<c:when test="${not empty beanConsultaBitacora.idOperacion}">
										<option VALUE="todas" selected="selected">TODAS</option>
									</c:when>
									<c:otherwise>
										<option VALUE="todas">TODAS</option>
									</c:otherwise>									
								</c:choose>								
								<c:forEach var="operacion" items="${listaOperacionesBitacora}" >
									<c:choose>
										<c:when test="${beanConsultaBitacora.idOperacion == operacion.consecutivoOperacionNivelCanal}">
											<option VALUE="${operacion.consecutivoOperacionNivelCanal}" selected="selected">${operacion.descripcionCorta}</option>
										</c:when>
										<c:otherwise>
											<option VALUE="${operacion.consecutivoOperacionNivelCanal}">${operacion.descripcionCorta}</option>
										</c:otherwise>
									</c:choose>			    					  
			    				</c:forEach>						
							</select>
						</td>
						<td size="50" class="odd">*Referencia interna:</td>
						<td>
							<input name="txtReferencia" type="text" class="Campos" id="txtReferencia" value="${beanConsultaBitacora.numReferenciaOper}" maxlength="24" onkeypress="javascript:deshabilita();" onkeyup="javascript:deshabilita();" onblur="javascript:deshabilita();" onchange="javascript:deshabilita();" onfocus="javascript:deshabilita();"/>
						</td>
					</tr>
					<tr>
						<td class="odd">Folio:</td>
						<td>
							<input name="txtFolio" type="text" class="Campos" id="txtFolio" value="${beanConsultaBitacora.folio}" onkeypress="javascript:deshabilita();" onkeyup="javascript:deshabilita();" onblur="javascript:deshabilita();" onchange="javascript:deshabilita();" onfocus="javascript:deshabilita();" maxlength="20"/>
						</td>
						<td class="odd">Importe:</td>
						<td>
							<input name="txtImporte" type="text" class="Campos" maxlength="13" id="txtImporte" value="${beanConsultaBitacora.importeOperacion}"/>
						</td>						
					</tr>
					<tr>
						<td class="odd">Cuenta/Tarjeta/Celular:</td>
						<td size="50">
							<input name="txtCuentaTarjeta" type="text" class="Campos" id="txtCuentaTarjeta" value="${beanConsultaBitacora.cuentaTarjeta}" maxlength="20"/>
						</td>
						
					</tr>
				</table>
			</div>			
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table width="100%">
					<tr>
						<td class="izq"><span><a href="javascript:consultar(' ');">Consultar</a></span></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="der"><span><a href="javascript:reset();">Limpiar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div> 
		
	<!-- Componente tabla estandar -->
	
	<c:choose>
		<c:when test="${not empty listaRegistrosBitacora}">	
			<div class="frameTablaEstandar" style='display:block' id="tFrameTable">
				<div class="titleTablaEstandar">
					Bit&aacute;cora 
				</div>
				<div class="contentTablaEstandar" style="overflow:scroll; height:680px;" align="center">
					<table>
						<thead>
							<tr>
								<th class="text_centro">Anular</th>
								<th class="text_centro">Folio</th>
								<th class="text_centro">Descripci&oacute;n</th>
								<th class="text_centro">Referencia Operaci&oacute;n</th>
								<th class="text_centro">Cuenta/Tarjeta/Celular</th>     
								<th class="text_centro">Clave Estado</th>
								<th class="text_centro">Clave Transacci&oacute;n</th>  
								<th class="text_centro">Operaci&oacute;n Corresponsal</th>       
								<th class="text_centro">Id Caja</th>            
								<th class="text_centro">Fecha Conciliaci&oacute;n</th> 
								<th class="text_centro">Fecha Compensaci&oacute;n</th> 
								<th class="text_centro">Referencia Interna</th> 
								<th class="text_centro">Referencia Cheques</th>    
								<th class="text_centro">Fecha Operaci&oacute;n</th>    
								<th class="text_centro">Hora Operaci&oacute;n</th>     
								<th class="text_centro">Divisa Operaci&oacute;n</th>   
								<th class="text_centro">Importe Operaci&oacute;n</th>  
								<th class="text_centro">N&uacute;mero Factura</th>     
								<th class="text_centro">Entidad Adquiriente</th>
								<th class="text_centro">Importe Comisi&oacute;n Banco</th>
								<th class="text_centro">Importe Comisi&oacute;n Corresponsal</th>
								<th class="text_centro">Importe Comisi&oacute;n Cliente</th>
								<th class="text_centro">Importe IVA Banco</th>  
								<th class="text_centro">Importe IVA Corresponsal</th>
								<th class="text_centro">Importe IVA Cliente</th>
								<th class="text_centro">Terminal Capability</th>
								<th class="text_centro">Datos Adicionales</th>  
								<th class="text_centro">Nombre Localizaci&oacute;n</th>
								<th class="text_centro">Notas</th>
								<th class="text_centro">Observaciones</th>
							</tr>
						</thead>
						<tr>
							<Td colspan="29" class="special"></Td>
						</tr>
						<tbody>
						
						<c:set var="i" value="0"/>
						<c:set var="estilo" value="odd1"/>
					    <c:forEach var="reg" items="${listaRegistrosBitacora}">  
					    	<tr class="${estilo}">						
								<td class="text_centro"><a href="javascript:anular('${i}');">Anular</a></td>
								<td class="text_izquierda">${reg.folioOperacion}</td>
								<td class="text_izquierda">${reg.descOperacion}</td>
								<td class="text_derecha">${reg.numRefOper}</td>
								<td class="text_derecha">${reg.numeroTarjeta}</td>     
								<td class="text_derecha">${reg.claveTranOper}</td>
								<td class="text_derecha">${reg.claveRefTran}</td>  
								<td class="text_derecha">${reg.claveOperCorresponsal}</td>       
								<td class="text_derecha">${reg.idCaja}</td>            
								<td class="text_izquierda">${reg.fechaConsiliacion}</td> 
								<td class="text_izquierda">${reg.fechaCompensacion}</td> 
								<td class="text_derecha">${reg.numRefPampa}</td> 
								<td class="text_derecha">${reg.numCuentaCheques}</td>    
								<td class="text_izquierda">${reg.fechaOperacion}</td>    
								<td class="text_izquierda">${reg.horaOperacion}</td>     
								<td class="text_centro">${reg.divisaOperacion}</td>   
								<td class="text_derecha">${reg.importeOperacion/100}</td>  
								<td class="text_derecha">${reg.numeroFactura}</td>     
								<td class="text_derecha">${reg.idEntidadAdquiriente}</td>
								<td class="text_derecha"><c:out value="${reg.importeComisionBanco / 100}"></c:out></td>
								<td class="text_derecha"><c:out value="${reg.importeComisionCorresponsal / 100}"></c:out></td>
								<td class="text_derecha"><c:out value="${reg.importeComisionCliente / 100}"></c:out></td>
								<td class="text_derecha"><c:out value="${reg.importeIvaBanco / 100}"></c:out></td>  
								<td class="text_derecha"><c:out value="${reg.importeIvaCorresponsal / 100}"></c:out></td>
								<td class="text_derecha"><c:out value="${reg.importeIvaCliente / 100}"></c:out></td>
								<td class="text_izquierda">${reg.terminalCompatible}</td>
								<td class="text_izquierda">${reg.datosAdicionales}</td>  
								<td class="text_izquierda">${reg.nombreLocalizacion}</td>
								<td class="text_izquierda">${reg.campoObservaciones2}</td>
								<td class="text_izquierda">${reg.campoObservaciones1}</td>
					    	</tr>
					    	<c:set var="i" value="${i + 1}"/>
					    	<c:choose>
								<c:when test="${estilo == 'odd2'}">
					    			<c:set var="estilo" value="odd1"/>
					    		</c:when>
					    		<c:otherwise>
					    			<c:set var="estilo" value="odd2"/>
					    		</c:otherwise>
					    	</c:choose>
					    </c:forEach>
						</tbody>				
					</table>
				</div>
				<div class="paginador">
				<c:if test="${not empty beanConsultaBitacora.referenciaRetroceder}">
					<a href="javascript:consultar('B');">Anterior</a>
				</c:if>
				<c:if test="${not empty  beanConsultaBitacora.referenciaAvanzar}">
					<a href="javascript:consultar('A');">Siguiente</a>
				</c:if>
			</div>
				<div class="framePieContenedor">
					<div class="contentPieContenedor">
						<table width="100%">
							<tr>
								<td class="izq"><span><a href="javascript:exportar();">Exportar</a></span></td>
								<td width="4px" class="odd">&nbsp;</td>
								<td class="der"><span><a href="javascript:cerrar();">Cerrar</a></span></td>
							</tr>
						</table>
					</div>
				</div>
			</div>	
		</c:when>
		<c:otherwise>
			<div class="frameTablaEstandar" style='display:none' id="tFrameTable"></div>
		</c:otherwise>
	</c:choose>
	
</form>

	
<script>
	<c:if test="${not empty  codError}">
			jError('${msgError}',                               //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitácora', //TITULO ERROR
		   	   	   '${codError}',                               //CODIGO ERROR
		   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR		
	</c:if>
	<c:if test="${not empty  codAviso}">
			jAlert('${msgAviso}',                               //DESCRIPCION Aviso
		   	   	   'Resultado en Bitácora', 						//TITULO ERROR
		   	   	   '${codAviso}',                               //CODIGO ERROR
		   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR		
	</c:if>

	habilitadeshabilita();
</script>