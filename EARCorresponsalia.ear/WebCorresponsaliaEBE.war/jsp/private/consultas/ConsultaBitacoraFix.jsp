<jsp:include page="../myHeader.jsp" flush="true" />
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="consultas" />
	<jsp:param name="menuSubitem" value="bitacora" />
</jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"%>

<script>

function esCampoAlfanumerico(campo) {
	var RegExPattern = /\w/;
	
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
	function obtenerOperacionesSucursales(){
    	document.forms["formConsultaBitacora"].action = "BitacoraConsultaOperacionesSucursales.do";
    	document.forms["formConsultaBitacora"].submit();
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
		
		var campo = document.forms["formConsultaBitacora"].txtImporte.value;
		if(campo!=""){
			if(!esCampoAlfanumerico(campo))
				return false;
		}
		
		return true;
	}

	function validaFolio(){
		var valOk = true;
		var campo = document.forms["formConsultaBitacora"].txtFolio.value;
		if(campo!=""){
			if(!esCampoEntero(campo))
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
			if(campo.length!=11 && campo.length!=16 && campo.length!=16)
				return false;
		}
		
		return true;
	}

	function validaCamposObligatorios(){
		var camposOk = true;
		if(document.forms["formConsultaBitacora"].optCorresponsal.value=="sinsel"){
			camposOk = false;
    		jAlert('Debe de seleccionar un corresponsal', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitacora',          //TITULO ERROR
		   	       'CAPSI001',                            //CODIGO ERROR
		   	       '');  
		}
		else if(document.forms["formConsultaBitacora"].optSucursal.value=="sinsel"){
			camposOk = false;
    		jAlert('Debe de seleccionar una sucursal', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitacora',          //TITULO ERROR
		   	       'CAPSI001',                            //CODIGO ERROR
		   	       '');  
		}	
		else if(document.forms["formConsultaBitacora"].txtFecha.value==""){
			camposOk = false;
    		jAlert('Debe informar la fecha', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitacora',          //TITULO ERROR
		   	       'CAPSI002',                            //CODIGO ERROR
		   	       '');  
		}
		else if(!validaReferenciaInterna()){
			camposOk = false;
    		jAlert('El campo Referencia Interna es incorrecto', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitacora',          //TITULO ERROR
		   	       'CAPSI003',                            //CODIGO ERROR
		   	       'El campo Referencia Interna debe de ser alfanumerico');  
		}
		else if(!validaFolio()){
			camposOk = false;
    		jAlert('El campo Folio es incorrecto', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitacora',          //TITULO ERROR
		   	       'CAPSI004',                            //CODIGO ERROR
		   	       'El campo Folio debe de ser alfanumerico');  
		}
		else if(!validaCuentaTarjeta()){
			camposOk = false;
    		jAlert('El campo Cuenta/Tarjeta es incorrecto', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitacora',          //TITULO ERROR
		   	       'CAPSI005',                            //CODIGO ERROR
		   	       'El campo Cuenta/Tarjeta de ser numerico \n Con longitud de 11, 16 o 20 digitos');  
		}
		else if(!validaImporte()){
			camposOk = false;
    		jAlert('El campo Importe es incorrecto', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitacora',          //TITULO ERROR
		   	       'CAPSI006',                            //CODIGO ERROR
		   	       'El campo Importe de ser decimal');  
		}
		
		
		return camposOk;
	}
			
	function consultar(avanzarRetroceder){
		if(validaCamposObligatorios()){
	    	document.forms["formConsultaBitacora"].opcAvanzarRetroceder.value=avanzarRetroceder; 
    		document.forms["formConsultaBitacora"].action = "RealizaConsultaBitacora.do";
    		document.forms["formConsultaBitacora"].submit();
    	}
    	else{
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
	}
		else{
			document.forms["formConsultaBitacora"].optOperacion.disabled=true;
			document.forms["formConsultaBitacora"].txtReferencia.disabled=true;
			document.forms["formConsultaBitacora"].txtFolio.disabled=true;
			document.forms["formConsultaBitacora"].txtImporte.disabled=true;
			document.forms["formConsultaBitacora"].txtCuentaTarjeta.disabled=true;
		}
		
	}
	
	function ini(){
		createCalendar('txtFecha','calBusqueda');
	}
			
</script>
		
<form id="formConsultaBitacora" action="" method="post">
		
	<input type="hidden" value="" id="opcAvanzarRetroceder" name="opcAvanzarRetroceder" />
	<input type="hidden" value="" id="regAnular" name="regAnular" />

	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Consultas</span> - Bit&aacute;cora
	</div>
	
	
	<!-- Componente buscador  -->
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Filtros</div>
		<div class="contentFormularioB">
			<table>
				<tbody>
					<tr>
						<td width="138" class="odd">Corresponsal:</td>
						<td width="160">
							<select name="optCorresponsal" class="CamposCompletar" id="optCorresponsal" onchange="javascript:obtenerOperacionesSucursales();" />
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
						<td width="138" class="text_derecha">Sucursal:</td>
						<td width="" class="text_dinero">
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
						<td width="138" class="text_derecha">Estatus:</td>
						<td width="" class="text_dinero">
							<select name="optEstatus" class="CamposCompletar" id="optEstatus" onchange="javascript:habilitadeshabilita();" />
								<c:choose>
									<c:when test="${empty beanConsultaBitacora.estatus}">
										<option VALUE="NIN" selected="selected">NINGUNO</option>
										<option VALUE="A01">PREVALIDACION</option>           
										<option VALUE="A02">REGISTRADA</option>              
										<option VALUE="A03">CONCILIADA</option>              
										<option VALUE="A04">PENDIENTE CONCILIAR</option>     
										<option VALUE="A05">NO CONCILIADA</option>           
										<option VALUE="A06">POR COMPENSAR</option>           
										<option VALUE="A07">PENDIENTE COMPENSAR</option>     
										<option VALUE="A08">VENCIDA POR COMPENSAR</option>   
										<option VALUE="A09">ANULADA CONTRAOPERACION</option> 
										<option VALUE="A10">COMPENSADA</option>              
										<option VALUE="A11">RECHAZADA</option>               
										<option VALUE="A12">ANULADA LINEA</option>           
										<option VALUE="A13">INCIDENCIA</option> 
									</c:when>
									<c:when test="${beanConsultaBitacora.estatus=='A01'}">
										<option VALUE="NIN">NINGUNO</option>
										<option VALUE="A01" selected="selected">PREVALIDACION</option>           
										<option VALUE="A02">REGISTRADA</option>              
										<option VALUE="A03">CONCILIADA</option>              
										<option VALUE="A04">PENDIENTE CONCILIAR</option>     
										<option VALUE="A05">NO CONCILIADA</option>           
										<option VALUE="A06">POR COMPENSAR</option>           
										<option VALUE="A07">PENDIENTE COMPENSAR</option>     
										<option VALUE="A08">VENCIDA POR COMPENSAR</option>   
										<option VALUE="A09">ANULADA CONTRAOPERACION</option> 
										<option VALUE="A10">COMPENSADA</option>              
										<option VALUE="A11">RECHAZADA</option>               
										<option VALUE="A12">ANULADA LINEA</option>           
										<option VALUE="A13">INCIDENCIA</option> 
									</c:when>
									<c:when test="${beanConsultaBitacora.estatus=='A02'}">
										<option VALUE="NIN">NINGUNO</option>
										<option VALUE="A01">PREVALIDACION</option>           
										<option VALUE="A02" selected="selected">REGISTRADA</option>              
										<option VALUE="A03">CONCILIADA</option>              
										<option VALUE="A04">PENDIENTE CONCILIAR</option>     
										<option VALUE="A05">NO CONCILIADA</option>           
										<option VALUE="A06">POR COMPENSAR</option>           
										<option VALUE="A07">PENDIENTE COMPENSAR</option>     
										<option VALUE="A08">VENCIDA POR COMPENSAR</option>   
										<option VALUE="A09">ANULADA CONTRAOPERACION</option> 
										<option VALUE="A10">COMPENSADA</option>              
										<option VALUE="A11">RECHAZADA</option>               
										<option VALUE="A12">ANULADA LINEA</option>           
										<option VALUE="A13">INCIDENCIA</option> 
									</c:when>
									<c:when test="${beanConsultaBitacora.estatus=='A03'}">
										<option VALUE="NIN">NINGUNO</option>
										<option VALUE="A01">PREVALIDACION</option>           
										<option VALUE="A02">REGISTRADA</option>              
										<option VALUE="A03" selected="selected">CONCILIADA</option>              
										<option VALUE="A04">PENDIENTE CONCILIAR</option>     
										<option VALUE="A05">NO CONCILIADA</option>           
										<option VALUE="A06">POR COMPENSAR</option>           
										<option VALUE="A07">PENDIENTE COMPENSAR</option>     
										<option VALUE="A08">VENCIDA POR COMPENSAR</option>   
										<option VALUE="A09">ANULADA CONTRAOPERACION</option> 
										<option VALUE="A10">COMPENSADA</option>              
										<option VALUE="A11">RECHAZADA</option>               
										<option VALUE="A12">ANULADA LINEA</option>           
										<option VALUE="A13">INCIDENCIA</option> 
									</c:when>
									<c:when test="${beanConsultaBitacora.estatus=='A04'}">
										<option VALUE="NIN">NINGUNO</option>
										<option VALUE="A01">PREVALIDACION</option>           
										<option VALUE="A02">REGISTRADA</option>              
										<option VALUE="A03">CONCILIADA</option>              
										<option VALUE="A04" selected="selected">PENDIENTE CONCILIAR</option>     
										<option VALUE="A05">NO CONCILIADA</option>           
										<option VALUE="A06">POR COMPENSAR</option>           
										<option VALUE="A07">PENDIENTE COMPENSAR</option>     
										<option VALUE="A08">VENCIDA POR COMPENSAR</option>   
										<option VALUE="A09">ANULADA CONTRAOPERACION</option> 
										<option VALUE="A10">COMPENSADA</option>              
										<option VALUE="A11">RECHAZADA</option>               
										<option VALUE="A12">ANULADA LINEA</option>           
										<option VALUE="A13">INCIDENCIA</option> 
									</c:when>
									<c:when test="${beanConsultaBitacora.estatus=='A05'}">
										<option VALUE="NIN">NINGUNO</option>
										<option VALUE="A01">PREVALIDACION</option>           
										<option VALUE="A02">REGISTRADA</option>              
										<option VALUE="A03">CONCILIADA</option>              
										<option VALUE="A04">PENDIENTE CONCILIAR</option>     
										<option VALUE="A05" selected="selected">NO CONCILIADA</option>           
										<option VALUE="A06">POR COMPENSAR</option>           
										<option VALUE="A07">PENDIENTE COMPENSAR</option>     
										<option VALUE="A08">VENCIDA POR COMPENSAR</option>   
										<option VALUE="A09">ANULADA CONTRAOPERACION</option> 
										<option VALUE="A10">COMPENSADA</option>              
										<option VALUE="A11">RECHAZADA</option>               
										<option VALUE="A12">ANULADA LINEA</option>           
										<option VALUE="A13">INCIDENCIA</option> 
									</c:when>
									<c:when test="${beanConsultaBitacora.estatus=='A06'}">
										<option VALUE="NIN">NINGUNO</option>
										<option VALUE="A01">PREVALIDACION</option>           
										<option VALUE="A02">REGISTRADA</option>              
										<option VALUE="A03">CONCILIADA</option>              
										<option VALUE="A04">PENDIENTE CONCILIAR</option>     
										<option VALUE="A05">NO CONCILIADA</option>           
										<option VALUE="A06" selected="selected">POR COMPENSAR</option>           
										<option VALUE="A07">PENDIENTE COMPENSAR</option>     
										<option VALUE="A08">VENCIDA POR COMPENSAR</option>   
										<option VALUE="A09">ANULADA CONTRAOPERACION</option> 
										<option VALUE="A10">COMPENSADA</option>              
										<option VALUE="A11">RECHAZADA</option>               
										<option VALUE="A12">ANULADA LINEA</option>           
										<option VALUE="A13">INCIDENCIA</option> 
									</c:when>
									<c:when test="${beanConsultaBitacora.estatus=='A07'}">
										<option VALUE="NIN">NINGUNO</option>
										<option VALUE="A01">PREVALIDACION</option>           
										<option VALUE="A02">REGISTRADA</option>              
										<option VALUE="A03">CONCILIADA</option>              
										<option VALUE="A04">PENDIENTE CONCILIAR</option>     
										<option VALUE="A05">NO CONCILIADA</option>           
										<option VALUE="A06">POR COMPENSAR</option>           
										<option VALUE="A07" selected="selected">PENDIENTE COMPENSAR</option>     
										<option VALUE="A08">VENCIDA POR COMPENSAR</option>   
										<option VALUE="A09">ANULADA CONTRAOPERACION</option> 
										<option VALUE="A10">COMPENSADA</option>              
										<option VALUE="A11">RECHAZADA</option>               
										<option VALUE="A12">ANULADA LINEA</option>           
										<option VALUE="A13">INCIDENCIA</option> 
									</c:when>
									<c:when test="${beanConsultaBitacora.estatus=='A08'}">
										<option VALUE="NIN">NINGUNO</option>
										<option VALUE="A01"selected="selected">PREVALIDACION</option>           
										<option VALUE="A02">REGISTRADA</option>              
										<option VALUE="A03">CONCILIADA</option>              
										<option VALUE="A04">PENDIENTE CONCILIAR</option>     
										<option VALUE="A05">NO CONCILIADA</option>           
										<option VALUE="A06">POR COMPENSAR</option>           
										<option VALUE="A07">PENDIENTE COMPENSAR</option>     
										<option VALUE="A08" selected="selected">VENCIDA POR COMPENSAR</option>   
										<option VALUE="A09">ANULADA CONTRAOPERACION</option> 
										<option VALUE="A10">COMPENSADA</option>              
										<option VALUE="A11">RECHAZADA</option>               
										<option VALUE="A12">ANULADA LINEA</option>           
										<option VALUE="A13">INCIDENCIA</option> 
									</c:when>
									<c:when test="${beanConsultaBitacora.estatus=='A09'}">
										<option VALUE="NIN">NINGUNO</option>
										<option VALUE="A01">PREVALIDACION</option>           
										<option VALUE="A02">REGISTRADA</option>              
										<option VALUE="A03">CONCILIADA</option>              
										<option VALUE="A04">PENDIENTE CONCILIAR</option>     
										<option VALUE="A05">NO CONCILIADA</option>           
										<option VALUE="A06">POR COMPENSAR</option>           
										<option VALUE="A07">PENDIENTE COMPENSAR</option>     
										<option VALUE="A08">VENCIDA POR COMPENSAR</option>   
										<option VALUE="A09" selected="selected">ANULADA CONTRAOPERACION</option> 
										<option VALUE="A10">COMPENSADA</option>              
										<option VALUE="A11">RECHAZADA</option>               
										<option VALUE="A12">ANULADA LINEA</option>           
										<option VALUE="A13">INCIDENCIA</option> 
									</c:when>
									<c:when test="${beanConsultaBitacora.estatus=='A10'}">
										<option VALUE="NIN">NINGUNO</option>
										<option VALUE="A01">PREVALIDACION</option>           
										<option VALUE="A02">REGISTRADA</option>              
										<option VALUE="A03">CONCILIADA</option>              
										<option VALUE="A04">PENDIENTE CONCILIAR</option>     
										<option VALUE="A05">NO CONCILIADA</option>           
										<option VALUE="A06">POR COMPENSAR</option>           
										<option VALUE="A07">PENDIENTE COMPENSAR</option>     
										<option VALUE="A08">VENCIDA POR COMPENSAR</option>   
										<option VALUE="A09">ANULADA CONTRAOPERACION</option> 
										<option VALUE="A10" selected="selected">COMPENSADA</option>              
										<option VALUE="A11">RECHAZADA</option>               
										<option VALUE="A12">ANULADA LINEA</option>           
										<option VALUE="A13">INCIDENCIA</option> 
									</c:when>
									<c:when test="${beanConsultaBitacora.estatus=='A11'}">
										<option VALUE="NIN">NINGUNO</option>
										<option VALUE="A01">PREVALIDACION</option>           
										<option VALUE="A02">REGISTRADA</option>              
										<option VALUE="A03">CONCILIADA</option>              
										<option VALUE="A04">PENDIENTE CONCILIAR</option>     
										<option VALUE="A05">NO CONCILIADA</option>           
										<option VALUE="A06">POR COMPENSAR</option>           
										<option VALUE="A07">PENDIENTE COMPENSAR</option>     
										<option VALUE="A08">VENCIDA POR COMPENSAR</option>   
										<option VALUE="A09">ANULADA CONTRAOPERACION</option> 
										<option VALUE="A10">COMPENSADA</option>              
										<option VALUE="A11" selected="selected">RECHAZADA</option>               
										<option VALUE="A12">ANULADA LINEA</option>           
										<option VALUE="A13">INCIDENCIA</option> 
									</c:when>
									<c:when test="${beanConsultaBitacora.estatus=='A12'}">
										<option VALUE="NIN">NINGUNO</option>
										<option VALUE="A01">PREVALIDACION</option>           
										<option VALUE="A02">REGISTRADA</option>              
										<option VALUE="A03">CONCILIADA</option>              
										<option VALUE="A04">PENDIENTE CONCILIAR</option>     
										<option VALUE="A05">NO CONCILIADA</option>           
										<option VALUE="A06">POR COMPENSAR</option>           
										<option VALUE="A07">PENDIENTE COMPENSAR</option>     
										<option VALUE="A08">VENCIDA POR COMPENSAR</option>   
										<option VALUE="A09">ANULADA CONTRAOPERACION</option> 
										<option VALUE="A10">COMPENSADA</option>              
										<option VALUE="A11">RECHAZADA</option>               
										<option VALUE="A12" selected="selected">ANULADA LINEA</option>           
										<option VALUE="A13">INCIDENCIA</option> 
									</c:when>
									<c:when test="${beanConsultaBitacora.estatus=='A13'}">
										<option VALUE="NIN">NINGUNO</option>
										<option VALUE="A01">PREVALIDACION</option>           
										<option VALUE="A02">REGISTRADA</option>              
										<option VALUE="A03">CONCILIADA</option>              
										<option VALUE="A04">PENDIENTE CONCILIAR</option>     
										<option VALUE="A05">NO CONCILIADA</option>           
										<option VALUE="A06">POR COMPENSAR</option>           
										<option VALUE="A07">PENDIENTE COMPENSAR</option>     
										<option VALUE="A08">VENCIDA POR COMPENSAR</option>   
										<option VALUE="A09">ANULADA CONTRAOPERACION</option> 
										<option VALUE="A10">COMPENSADA</option>              
										<option VALUE="A11">RECHAZADA</option>               
										<option VALUE="A12">ANULADA LINEA</option>           
										<option VALUE="A13" selected="selected">INCIDENCIA</option> 
									</c:when>
									<c:otherwise>
										<option VALUE="NIN" selected="selected">NINGUNO</option>
										<option VALUE="A01">PREVALIDACION</option>           
										<option VALUE="A02">REGISTRADA</option>              
										<option VALUE="A03">CONCILIADA</option>              
										<option VALUE="A04">PENDIENTE CONCILIAR</option>     
										<option VALUE="A05">NO CONCILIADA</option>           
										<option VALUE="A06">POR COMPENSAR</option>           
										<option VALUE="A07">PENDIENTE COMPENSAR</option>     
										<option VALUE="A08">VENCIDA POR COMPENSAR</option>   
										<option VALUE="A09">ANULADA CONTRAOPERACION</option> 
										<option VALUE="A10">COMPENSADA</option>              
										<option VALUE="A11">RECHAZADA</option>               
										<option VALUE="A12">ANULADA LINEA</option>           
										<option VALUE="A13">INCIDENCIA</option> 
									</c:otherwise>									
								</c:choose>
							</select>
						</td>
						<td class="text_derecha">
							<span class="text_dinero">Fecha:</span>
						</td>
						<td class="text_dinero">
							<input name="txtFecha" type="text" class="CamposCompletar" id="txtFecha" readonly="readonly" value="${beanConsultaBitacora.fechaAlta}" style="width:100px;"/>
							<img id="calBusqueda" src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/calendar/calendar.png">
						</td>						
						
					</tr>
					
					
					<tr>
						<td class="text_derecha">Tipo Operacion:</td>
						<td class="text_dinero">
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
						<td class="text_derecha"><span class="text_dinero">Referencia Interna:</span></td>
						<td class="text_dinero">
							<input name="txtReferencia" type="text" class="Campos" id="txtReferencia" value="${beanConsultaBitacora.numReferenciaOper}" maxlength="24"/>
						</td>
						
					</tr>
					<tr>
						<td class="text_derecha"><span class="text_dinero">Folio:</span></td>
						<td class="text_dinero">
							<input name="txtFolio" type="text" class="Campos" id="txtFolio" value="${beanConsultaBitacora.folio}"/>
						</td>
						<td class="text_derecha"><span class="text_dinero">Importe:</span></td>
						<td class="text_dinero">
							<input name="txtImporte" type="text" class="Campos" id="txtImporte" value="${beanConsultaBitacora.importeOperacion}"/>
						</td>
					</tr>
					<tr>
						<td class="text_derecha"><span class="text_dinero">Cuenta/Tarjeta:</span></td>
						<td class="text_dinero">
							<input name="txtCuentaTarjeta" type="text" class="Campos" id="txtCuentaTarjeta" value="${beanConsultaBitacora.cuentaTarjeta}"/>
						</td>
					</tr>

				</tbody>
			</table>
		</div>
		
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table>
					<tr>
						<td width="265" class="izq"><a href="javascript:reset();">Limpiar</a></td>
						<td width="5" class="odd">&nbsp;</td>
						<td width="265" class="der"><a href="javascript:consultar(' ');">Consultar</a></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<!-- Componente tabla estandar -->
		 
	<div class="frameTablaEstandar">
		<div class="titleTablaEstandar">Bit&aacute;cora</div>
		<div class="contentTablaEstandar">
			<table>
				<thead>
					<tr>
						<th class="text_centro">Anular</th>
						<th class="text_centro">Descripci&oacute;n</th>
						<!-- 
						<th class="text_centro">Referencia Operaci&oacute;n</th>
						<th class="text_centro">N&uacute;mero Tarjeta</th>     
						<th class="text_centro">Clave Estado</th>
						<th class="text_centro">Clave Transacci&oacute;n</th>  
						<th class="text_centro">Operaci&oacute;n Corresponsal</th>       
						<th class="text_centro">Id Caja</th>            
						<th class="text_centro">Fecha Consiliaci&oacute;n</th> 
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
						 -->					
					</tr>
				</thead>
				<tr>
					<Td colspan="2" class="special"></Td>
				</tr>
				<tbody>
				<c:set var="i" value="0"/>
				<c:set var="estilo" value="odd1"/>
			    <c:forEach var="reg" items="${listaRegistrosBitacora}">  
			    	<tr class="${estilo}">
						<th class="text_centro"><a href="javascript:anular('${i}');">Anular</a></th>
						<th class="text_izquierda">${reg.descOperacion}</th>
						<!-- 
						<th class="text_derecha">${reg.numRefOper}</th>
						<th class="text_derecha">${reg.numeroTarjeta}</th>     
						<th class="text_derecha">${reg.claveTranOper}</th>
						<th class="text_derecha">${reg.claveRefTran}</th>  
						<th class="text_derecha">${reg.claveOperCorresponsal}</th>       
						<th class="text_derecha">${reg.idCaja}</th>            
						<th class="text_izquierda">${reg.fechaConsiliacion}</th> 
						<th class="text_izquierda">${reg.fechaCompensacion}</th> 
						<th class="text_derecha">${reg.numRefPampa}</th> 
						<th class="text_derecha">${reg.numCuentaCheques}</th>    
						<th class="text_izquierda">${reg.fechaOperacion}</th>    
						<th class="text_izquierda">${reg.horaOperacion}</th>     
						<th class="text_centro">${reg.divisaOperacion}</th>   
						<th class="text_derecha">${reg.importeOperacion}</th>  
						<th class="text_derecha">${reg.numeroFactura}</th>     
						<th class="text_derecha">${reg.idEntidadAdquiriente}</th>
						<th class="text_derecha">${reg.importeComisionBanco}</th>
						<th class="text_derecha">${reg.importeComisionCorresponsal}</th>
						<th class="text_derecha">${reg.importeComisionCliente}</th>
						<th class="text_derecha">${reg.importeIvaBanco}</th>  
						<th class="text_derecha">${reg.importeIvaCorresponsal}</th>
						<th class="text_derecha">${reg.importeIvaCliente}</th>
						<th class="text_izquierda">${reg.terminalCompatible}</th>
						<th class="text_izquierda">${reg.datosAdicionales}</th>  
						<th class="text_izquierda">${reg.nombreLocalizacion}</th>
						<th class="text_izquierda">${reg.campoObservaciones2}</th>
						<th class="text_izquierda">${reg.campoObservaciones1}</th>
						 -->						
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
			<c:if test="${not empty  beanConsultaBitacora.referenciaRetroceder}">
				<a href="javascript:consultar('R');">Anterior</a>
			</c:if>
			<c:if test="${not empty  beanConsultaBitacora.referenciaAvanzar}">
				<a href="javascript:consultar('A');">Siguiente</a>
			</c:if>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table>
					<tr>
						<td width="279" class="izq"><span>&nbsp;</span></td>
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="text_centro"><span>&nbsp;</span></td>
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="der"><span><a href="javascript:exportar();">Exportar</a></span></td>
					</tr>
				</table>
			</div>
		</div>

</form>

	<c:if test="${not empty  codError}">
		<script>
			jError('${msgError}',                               //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitacora', //TITULO ERROR
		   	   	   '${codError}',                               //CODIGO ERROR
		   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR		
		</script>
	</c:if>
