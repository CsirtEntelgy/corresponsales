<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="monitoreo" />
	<jsp:param name="menuSubitem" value="lineacredito" />
</jsp:include>

<form id="formaConsultaConstanciaFiscal" action="" method="post">
    <input type="hidden" name="SES" value="${SES}" />
	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Monitoreo de l&iacute;nea de cr&eacute;dito</span>
	</div>		
	<!-- Componente tabla estandar -->
	<div class="frameTablaEstandar"> 
		<div class="titleTablaEstandar">Operaciones<span class="textosin"></span>
		</div>
		<div class="contentTablaEstandar">
			<table>
				<thead>
					<tr>						
						<th width="120" class="text_centro">Corresponsal</th>
						<th width="150" class="text_centro">Saldo Chequera</th>
						<th width="150" class="text_centro">Cr&eacute;dito Otorgado</th>
						<th width="150" class="text_centro">Saldo Dispuesto</th>
						<th width="150" class="text_centro">Total Disponible</th>
						<th width="150" class="text_centro">Estimado por Compensar</th>
						<th width="150" class="text_centro">L&iacute;mite Alerta</th>
					</tr>
				</thead>
				<tr>
					<Td colspan="6" class="special"></Td>
				</tr>
				<tbody>
				
					<c:set var="estilo" value="odd1"/>				
				    <c:forEach var="reg" items="${listaRegistrosCreditos}" >
				    <c:if test="${reg.inicadorAlerta}">
				       	<tr bgcolor="#C00000">
						<td width="120" class="text_izquierda">${reg.nomCorresponsal}</td>
						<td class="text_centro">${reg.saldoCheques}</td>
						<td class="text_centro">${reg.credOtorgado}</td>
						<td class="text_centro">${reg.credDispuesto}</td>
						<td class="text_centro">${reg.credDisponible}</td>
						<td class="text_centro">${reg.pendienteComp}</td>
						<td class="text_centro">${reg.limiteAlerta}</td>						
		    		</tr>
		    		</c:if>
				    <c:if test="${!reg.inicadorAlerta}">
		    		<tr class="${estilo}">
						<td width="120" class="text_izquierda">${reg.nomCorresponsal}</td>
						<td class="text_centro">${reg.saldoCheques}</td>
						<td class="text_centro">${reg.credOtorgado}</td>
						<td class="text_centro">${reg.credDispuesto}</td>
						<td class="text_centro">${reg.credDisponible}</td>
						<td class="text_centro">${reg.pendienteComp}</td>
						<td class="text_centro">${reg.limiteAlerta}</td>						
		    		</tr>
			    	<c:choose>
						<c:when test="${estilo == 'odd2'}">
			    			<c:set var="estilo" value="odd1"/>
			    		</c:when>
			    		<c:otherwise>
			    			<c:set var="estilo" value="odd2"/>
			    		</c:otherwise>
					</c:choose>	
					</c:if>		    
			    </c:forEach>
				
				</tbody>
			</table>
		</div>
		<div class="paginador">
		<!-- Valida si la TRX regresó parametros de paginación	
			<a href="#" onclick="paginaAnt();">Atrás</a> | 
			<a href="#" onclick="paginaSig();">Adelante</a>
		 -->
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table>
					<tr>
						<td width="479" class="izq"><span>
						  <a href="JavaScript: formaConsultaConstanciaFiscal.action='ExportarMonitoreoCredito.do';formaConsultaConstanciaFiscal.submit();" onclick="" >Exportar</a></span></td>
						<td width="16" class="odd">&nbsp;</td>
						<td width="479" class="der"><span><a href="JavaScript: formaConsultaConstanciaFiscal.action='../principal/inicio.do';formaConsultaConstanciaFiscal.submit();" onclick="" >Cerrar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</form>
	<c:if test="${not empty  codError}">
		<script type="text/javascript" >
			jError('${msgError}',                               //DESCRIPCION ERROR
		   	   	   'Error en Monitoreo Operaciones', //TITULO ERROR
		   	   	   '${codError}',                               //CODIGO ERROR
		   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR		
		</script>
	</c:if>

	<c:if test="${not empty  codAviso}">
		<script type="text/javascript">
			jAlert('${msgAviso}',                               //DESCRIPCION ERROR
		   	   	   'Aviso Monitoreo Operaciones', //TITULO ERROR
		   	   	   '${codAviso}',                               //CODIGO ERROR
		   	       '');                                         //TIP PARA QUE NO SUCEDA EL ERROR		
		</script>
	</c:if>	
