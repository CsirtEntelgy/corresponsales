<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="consultas" />
	<jsp:param name="menuSubitem" value="estatusOperacion" />
</jsp:include>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"    prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt"%>


<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/comunes/validaciones.js"></script>

<script src="${pageContext.servletContext.contextPath}/js/private/consultas/EstatusOperacion.js" type="text/javascript"></script>



<form id="formConsultaEstatusOperacion" action="" method="post" >
    <input type="hidden" id="SES" name="SES" value="${SES}" />
	<input type="hidden" value="" id="opcAvanzarRetroceder" name="opcAvanzarRetroceder" />
	<input type="hidden" value="" id="regAnular" name="regAnular" />
	<input type="hidden" value="sinsel" id="comVal" name="comVal" />
	
	<!-- Componente titulo de pagina -->
	<div class="pageTitleContainer">
		<span class="pageTitle"><fmt:message key="menu.consultas.estatusOperacion" /></span>
	</div>
	
	<!-- Componente formulario -->
	
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Filtros</div>
			<div class="contentFormularioB">
				<table>
					<tr>
						<td class="odd">*Corresponsal:</td>
						<td>
							<select name="optCorresponsal" class="CamposCompletar" id="optCorresponsal" onchange="javascript:obtenerOperacionesSucursales();" 
							onfocus="javascript:comVal.value = this.value;" onfocusin="javascript:comVal.value = this.value;">
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
						<td class="odd">*Fecha:</td>
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
					</tr>
					<tr>
						<td class="odd">Folio:</td>
						<td>
							<input name="txtFolio" type="text" class="Campos" id="txtFolio" value="${beanConsultaBitacora.folio}" maxlength="20"/>
						</td>
						<td class="odd">Importe:</td>
						<td>
							<input name="txtImporte" type="text" class="Campos" maxlength="13" id="txtImporte" value="${beanConsultaBitacora.importeOperacion}"/>
						</td>						
					</tr>
					<tr>
						<td class="odd">Cuenta/Tarjeta:</td>
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
		<c:when test="${not empty listaRegistrosEstatusOperacion}">	
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
					    <c:forEach var="reg" items="${listaRegistrosEstatusOperacion}">  
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

</script>