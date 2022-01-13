<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"    prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt"%>

<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="contactos" />
	
</jsp:include>

<script type = "text/javascript">
function cerrar(){
	document.forms["formaConsultaContactos"].action = "../principal/inicio.do";
	document.forms["formaConsultaContactos"].submit();
}
function consultar() {
	
	if (document.getElementById("corresponsal").value =="0"){
			jAlert('Debe seleccionar una opción',                               //DESCRIPCION ERROR
		   	   	   'Contactos', 						//TITULO ERROR
		   	   	   '----',                               //CODIGO ERROR
		   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR		
	}else{
		document.forms["formaConsultaContactos"].action = "ConsultaContacto.do";
		document.getElementById("operacion").value = "consultarContactos";
		document.forms["formaConsultaContactos"].submit();
	}
}

function paginar(direccion) {
	        var intPagina = 1;
	        intPagina = parseInt(document.forms['formaConsultaContactos'].paginaNum.value);
	        if( direccion == 'A'){
	          intPagina ++;
	        }else
	        if( direccion == 'B'){
	          intPagina --;
	        }		
	document.getElementById("direccion").value = direccion;
	consultar();
}

function atras() {
	document.getElementById("direccion").value = "B";
	consultar();
}

function detalle(consecutivo, zonaUbic) {
	document.forms["formaConsultaContactos"].action = "detContacto.do";
	document.getElementById("operacion").value = "detalle";
	document.getElementById("consecutivo").value = consecutivo;
	document.getElementById("zonaUbic").value = zonaUbic;
	document.forms["formaConsultaContactos"].submit();
}

function modifica(consecutivo, zonaUbic) {
	document.forms["formaConsultaContactos"].action = "detContacto.do";
	document.getElementById("operacion").value = "modifica";
	document.getElementById("consecutivo").value = consecutivo;
	document.getElementById("zonaUbic").value = zonaUbic;
	document.forms["formaConsultaContactos"].submit();
}

function elimina(consecutivo, zonaUbic) {
	document.forms["formaConsultaContactos"].action = "altContacto.do";
	document.getElementById("operacion").value = "borraContacto";
	document.getElementById("consecutivo").value = consecutivo;
	document.getElementById("zonaUbic").value = zonaUbic;
		jConfirm('¿Desea eliminar el contacto?', 
		   	   	   'Eliminar contacto',          
		   	       'ELIMCTO',                          
		   	       '',function(e){
						if(e){
							document.forms["formaConsultaContactos"].submit();
							}
						return e;
					}); 
	
	
}



	<c:if test="${not empty  mensajeRespuesta}">
			jAlert('${mensajeRespuesta}',                               //DESCRIPCION ERROR
		   	   	   'Resultado en Alta Contactos', 						//TITULO ERROR
		   	   	   '${mensajeRespuesta}',                               //CODIGO ERROR
		   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR		
	</c:if>

	<c:if test="${not empty  codError}">
			jAlert('${msgError}',                               //DESCRIPCION ERROR
		   	   	   'Contactos', 						//TITULO ERROR
		   	   	   '${codError}',                               //CODIGO ERROR
		   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR		
	</c:if>


</script>


<form id="formaConsultaContactos" action="" method="post">
    <input type="hidden" id="SES" name="SES" value="${SES}" />
	<input type="hidden" id="operacion" name="operacion" value="      " />
	<input type="hidden" id="consecutivo" name="consecutivo" value="" />	
	<input type="hidden" id="zonaUbic" name="zonaUbic" value="" />
	<input type="hidden" id="direccion" name="direccion" value="" />
	<input type="hidden" value="${(empty paginaNum )? "1": paginaNum }" id = "paginaNum" name="paginaNum" />
	
	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Contactos por corresponsal</span>
	</div>
	
	<!-- Componente formulario -->
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Corresponsal a Consultar</div>
			<div class="contentFormularioB">
				<table width="100%">
					<tr>
						<td class="odd">*Corresponsal:</td>
						<td>
							<select id="corresponsal" name="corresponsal" class="Campos" onchange="consultar();">
										<option VALUE="0" selected="selected">Seleccione una opción...</option>
										<c:forEach var="resultado" items="${lista}">
											<option VALUE="${resultado.codigoCorresponsal}"<c:if test="${resultado.codigoCorresponsal==corresponsalBuscar}"> selected</c:if>>${resultado.nombreCorresponsal}</option>
										</c:forEach>
							</select>
						</td>
					</tr>
				</table>
			</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table width="100%">
					<tr>
						<td class="izq"><span><a href="javascript:consultar();">Consultar</a></span></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="der"><span><a href="javascript:cerrar();">Cerrar</a></span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>

<c:if test="${corresponsalBuscar!=null}" >
	<!-- Componente tabla estandar -->
	<div class="frameTablaEstandar">
		<div class="titleTablaEstandar">
			Contactos <span class="textosin">- Seleccione la acción</span>
		</div>
		<div class="contentTablaEstandar">
			<table>
				<thead>
					<tr>
						<th width="220"class="text_izquierda">Nombre</th>
						<th width="147" class="text_centro">Puesto</th>
						<th width="100" class="text_centro">Teléfono</th>
						<th width="100" class="text_centro">Detalle</th>
						<th width="100" class="text_centro">Modificar</th>
						<th width="100" class="text_centro">Eliminar</th>
					</tr>
				</thead>
				<tr>
					<Td colspan="6" class="special"></Td>
				</tr>
				<tbody>
				
				<c:set var="estilo" value="odd1"/>				
				<c:forEach var="contacto" items="${listaContactos}">
				
				
				<tr class="${estilo}">
						<td width="128" class="text_izquierda">${contacto.nombre}</td>
						<td class="text_izquierda">${contacto.puesto}</td>
						<td class="text_izquierda">${contacto.telefono}</td>
						<td class="text_centro">
							<a href="javascript:detalle('${contacto.consecutivo}', '${contacto.zonaUbic}');" onclick="" ><div class="contentPieContenedor">Detalle</div></a>
						</td>
						<td class="text_centro">
							<a href="javascript:modifica('${contacto.consecutivo}', '${contacto.zonaUbic}');" onclick="" ><div class="contentPieContenedor">Modificar</div></a>
						</td>
						<td class="text_centro">
							<a href="javascript:elimina('${contacto.consecutivo}', '${contacto.zonaUbic}');" onclick="" ><div class="contentPieContenedor">Eliminar</div></a>
						</td>
			    	</tr>
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
			<c:if test="${resultado.masAtras}">
				<a href="javascript:paginar('B');">Atr&aacute;s</a>
			</c:if>
			<c:if test="${resultado.masAdelante}">
				<a href="javascript:paginar('A');">Adelante</a>
			</c:if>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table width="100%">
					<tr>
						<td class="izq"><span>
						<a href="javaScript: formaConsultaContactos.action='altContacto.do';formaConsultaContactos.submit();" onclick="" >Nuevo</a>
						</span></td>
						<td width="4px" class="odd">&nbsp;</td>
						<td class="der"><span>
						<c:if test="${listaContactos !=null && not empty listaContactos}">
							<a href="javaScript: formaConsultaContactos.action='ExportarContactos.do';formaConsultaContactos.submit();" onclick="" >Exportar</a>
						</c:if>
						</span></td>
					</tr>
				</table>
			</div>
		</div>
				
	</c:if>
	</form>
