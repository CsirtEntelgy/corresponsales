<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"    prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt"%>

<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="consultas" />
	<jsp:param name="menuSubitem" value="compensacion" />
</jsp:include>

<script src="${pageContext.servletContext.contextPath}/js/private/consultas/ConsultaCompensacion.js" type="text/javascript"></script>

<script>
		
	function obtenerOperacionesSucursales(){
    	document.forms["formConsultaCompensacion"].action = "BitacoraConsultaOperacionesSucursales.do";
    	document.forms["formConsultaCompensacion"].submit();
	}
	
	function reset(){
		document.forms["formConsultaCompensacion"].txtFecha.value="";
		document.forms["formConsultaCompensacion"].txtFolio.value="";
		document.forms["formConsultaCompensacion"].txtReferencia.value="";
		document.forms["formConsultaCompensacion"].txtCuentaTarjeta.value="";
    	document.forms["formConsultaCompensacion"].txtImporte.value="";
	}
			
	function autorizar(){
		document.forms["formConsultaCompensacion"].txtSaldoDisponible.value=document.forms["formConsultaCompensacion"].txtSaldoDisponible1.value;
		document.forms["formConsultaCompensacion"].txtSaldoAutorizado.value=document.forms["formConsultaCompensacion"].txtSaldoAutorizado1.value;
    	document.forms["formConsultaCompensacion"].action = "Autorizar.do";
    	document.forms["formConsultaCompensacion"].submit();
	}

	function exportar(){
    	document.forms["formConsultaCompensacion"].action = "ExportarCompensacion.do";
    	document.forms["formConsultaCompensacion"].submit();
	}
	
	function consultar(avanzarRetroceder){
		if(document.forms["formConsultaCompensacion"].optCorresponsal.value!="sinsel"){
			console.log($("#optCorresponsal").val());
			localStorage.setItem("select",$("#optCorresponsal").val());
	    	document.forms["formConsultaCompensacion"].opcAvanzarRetroceder.value=avanzarRetroceder; 
    		document.forms["formConsultaCompensacion"].action = "RealizaConsultaCompensacion.do";
    		document.forms["formConsultaCompensacion"].submit();
    	}
	}
			
</script>
		
<form id="formConsultaCompensacion" action="" method="post">
    <input type="hidden" id="SES" name="SES" value="${SES}" />
	<input type="hidden" value="" id="opcAvanzarRetroceder" name="opcAvanzarRetroceder" />
	<input type="hidden" value="" id="txtSaldoDisponible" name="txtSaldoDisponible" />
	<input type="hidden" value="" id="txtSaldoAutorizado" name="txtSaldoAutorizado" />
	
	<input type="hidden" id="archivoTexto" value='<%=request.getSession().getAttribute("archivoTexto")%>' />
	<input type="hidden" id="codSucursal" value = '<%= request.getSession().getAttribute("codSucursal") %>'/>

	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Consulta</span> - Compensaciones
	</div>
	
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Corresponsal a Consultar</div>
			<div class="contentFormularioB">
				<table>
					<tr>
						<td width="12%" class="text_derecha">Corresponsal:</td>
						<td width="30%" class="text_dinero">
							<select name="optCorresponsal" class="CamposCompletar" id="optCorresponsal" onchange="javascript:consultar(' ');"/>
								<c:choose>
									<c:when test="${not empty reqCorresponsal}">
										<option VALUE="sinsel" selected="selected">--Seleccione un corresponsal</option>
									</c:when>
									<c:otherwise>
										<option VALUE="sinsel">--Seleccione un corresponsal</option>
									</c:otherwise>									
								</c:choose>
								<c:forEach var="corresponsal" items="${listaCorresponsalesCompensacion}" >
									<c:choose>
										<c:when test="${reqCorresponsal == corresponsal.codigoCorresponsal}">
											<option VALUE="${corresponsal.codigoCorresponsal}" selected="selected">${corresponsal.nombreCorresponsal}</option>
										</c:when>
										<c:otherwise>
											<option VALUE="${corresponsal.codigoCorresponsal}">${corresponsal.nombreCorresponsal}</option>
										</c:otherwise>
									</c:choose>
			    					  
			    				</c:forEach>						
								
							</select>
						</td>
						<td class="odd">Saldo disponible:</td>
						<td>
							<input type="text" value="${disponible}" class="Campos_Des" disabled="disabled" id="txtSaldoDisponible1" name="txtSaldoDisponible1"  maxlength="30" />
						</td>
					</tr>
					<tr>
						<td>
						</td>
						<td>
						</td>
						<td class="odd">Saldo autorizado:</td>
						<td>
							<input type="text" value="${autorizado}" class="Campos_Des" disabled="disabled" id="txtSaldoAutorizado1" name="txtSaldoAutorizado1"  maxlength="30" />
						</td>
					</tr>
				</table>
			</div>
		</div> 
	<!-- Componente tabla estandar -->
	<c:if test="${listaRegistrosCompensacion!=null}">	 
	<div class="frameTablaEstandar">
		<div class="titleTablaEstandar">
			Compensaci&oacute;n <span class="textosin">- Seleccione la acci&oacute;n</span>
		</div>
		<div class="contentTablaEstandar">
			<table width="100%">
				<thead>
					<tr>
						<th width="90" class="text_centro">Fecha</th>
						<th width="70" class="text_centro">No. Oper.</th>
						<th width="120" class="text_centro">Monto</th>
						<th width="150" class="text_centro">Estatus</th>
						<th width="100" class="text_centro">Cargo Comisiones</th>
						<th width="100" class="text_centro">Abono Comisiones</th>
						<th width="100" class="text_centro">Abono I.V.A.</th>
					</tr>
				</thead>
				<tr>
					<Td colspan="7" class="special"></Td>
				</tr>
				<tbody>
				<c:set var="i" value="0"/>
				<c:set var="estilo" value="odd1"/>
			    <c:forEach var="reg" items="${listaRegistrosCompensacion}" >  
			    	<tr class="${estilo}">
						<td width="80" class="text_izquierda">${reg.fechaLoteFormat}</td>
						<td width="80" class="text_derecha">${reg.totalOperacionesFormat}</td>
						<td width="120" class="text_derecha">${reg.importeTotalFormat}</td>
						<td width="150" class="text_centro">${reg.estatus}</td>
						<td width="100" class="text_derecha">${reg.cargoComisionesFormat}</td>												
						<td width="100" class="text_derecha">${reg.abonoComisionesFormat}</td>						
						<td width="100" class="text_derecha">${reg.abonoIvaFormat}</td>						
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
			<c:if test="${not empty  fechaInferior}">
				<a href="javascript:consultaSucursales('B');">Anterior</a>
			</c:if>
			<c:if test="${not empty  fechaSuperior}">
				<a href="javascript:consultaSucursales('A');">Siguiente</a>
			</c:if>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table width="100%">
					<tr>
						<td class="izq"><span><a href="javascript:exportar();">Exportar</a></span></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="izq"><span><a href="javascript:obtenerDetalle();">Obtener Detalle</a></span></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="der"><span><a href="javascript:autorizar();">Autorizar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	</c:if>
 
		
</form>
	<c:if test="${not empty codAviso}">
		<script>
			jAlert('${msgAviso}',                               //DESCRIPCION ERROR
		   	   	   'Aviso Consulta Compensacion', //TITULO ERROR
		   	   	   '${codAviso}',                               //CODIGO ERROR
		   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR		
		</script>
	</c:if>
	
	<c:if test="${not empty codError}">
		<script>
			jError('${msgError}',                               //DESCRIPCION ERROR
		   	   	   'Error en Consulta Compensacion', //TITULO ERROR
		   	   	   '${codError}',                               //CODIGO ERROR
		   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR		
		</script>
	</c:if>
	
	