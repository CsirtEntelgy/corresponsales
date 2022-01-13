<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags"    prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt" %>

<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="catalogos" />
	<jsp:param name="menuSubitem"    value="catalogoCodigoOperacion" />
</jsp:include>

<spring:message code="general.buscar" var="buscar"/>
<spring:message code="general.cancelar" var="cancelar"/>
<spring:message code="general.eliminar" var="eliminar"/>
<spring:message code="general.espacioSimple" var="espacio"/>
<spring:message code="general.guardar" var="guardar"/>
<spring:message code="general.limpiar" var="limpiar"/>
<spring:message code="general.modificar" var="modificar"/>

<spring:message code="catalogos.aplicaciones.aplicaciones" var="aplicaciones"/>
<spring:message code="catalogos.aplicaciones.descripcion" var="descripcionApp"/>
<spring:message code="catalogos.aplicaciones.filtros" var="filtros"/>
<spring:message code="catalogos.aplicaciones.idAplicacion" var="lblIdAplicacion"/>
<spring:message code="catalogos.codigosOperacion.codigoOperacion" var="lblCodigoOperacion"/>
<spring:message code="catalogos.codigosOperacion.codigoISO" var="lblCodigoISO"/>

<script src="${pageContext.servletContext.contextPath}/js/private/catalogos/ConsultaCodigoOperacion.js" 
type="text/javascript"></script>

<form id="formCatalogoCodigosOperacion" action="" method="post" >

<%-- Componente titulo de pagina --%>
     <div class="pageTitleContainer">
         <span class="pageTitle">Catalogo de C&oacute;digos de Operaci&oacute;n</span>
         <input type="hidden" id="SES" name="SES" value="${SES}" />
         <input type="hidden" value="" id="opcAvanzarRetroceder" name="opcAvanzarRetroceder" />
         <input type="hidden" value="" id="regAnularApp" name="regAnularApp" />
         <input type="hidden" value="" id="regAnularOper" name="regAnularOper" />
         <input type="hidden" value="" id="regAnularISO" name="regAnularISO" />
         <input type="hidden" value="sinsel" id="comVal" name="comVal" />
         <input type="hidden" value="${hdnIdAplicacion}" id="hdnIdAplicacion" name="hdnIdAplicacion" />
         <input type="hidden" value="${hdnCodigoOperacion}" id="hdnCodigoOperacion" name="hdnCodigoOperacion" />
         <input type="hidden" value="${hdnDescCodigoOp}" id="hdnDescCodigoOp" name="hdnDescCodigoOp" />
         <input type="hidden" value="${hdnCodigoISO}" id="hdnCodigoISO" name="hdnCodigoISO" />
         <input type="hidden" value="${hdnDescISO}" id="hdnDescISO" name="hdnDescISO" />
     </div>

<%-- Componente formulario --%>

     <div class="frameBuscadorSimple">
         <div class="titleBuscadorSimple">${filtros}</div>
         <div class="contentFormularioB">
             <table>
                   <tr>
                      <td>
                         <label>Id. Aplicacion: </label>
                      </td>
                      <td>
<input name="txtIdAplicacion" type="text" class="Campos" id="txtIdAplicacion" value="${idAplicacion}" 
style="width:80px;" onclick="";/>
                      </td>
                      <td>
                         <label>C&oacute;digo de Operaci&oacute;n: </label>
                      </td>
                      <td>
<input name="txtCodigoOperacion" type="text" class="Campos" id="txtCodigoOperacion" value="${codigoOperacion}" 
style="width:80px;" onclick="";/>
                      </td>
                   </tr>
                   <tr>
                      <td>
                         <label>Descripci&oacute;n C&oacute;digo de Operaci&oacute;n: </label>
                      </td>
                      <td>
<input name="txtDescOp" type="text" class="Campos" id="txtDescOp" value="${descripcionOperacion}" 
style="width:80px;"/>
                      </td>
                      <td>
                         <label>C&oacute;digo ISO: </label>
                      </td>
                      <td>
<input name="txtCodigoISO" type="text" class="Campos" id="txtCodigoISO" value="${codigoISO}" 
style="width:80px;" onclick="";/>
                      </td>
                    </tr>
                    <tr>
                       <td></td>
                       <td></td>
                       <td>
                          <label>Descripci&oacute;n C&oacute;digo ISO: </label>
                       </td>
                       <td>
<input name="txtDescISO" type="text" class="Campos" id="txtDescISO" value="${descripcionISO}" 
style="width:80px;"/>
                       </td>
                    </tr>
             </table>
        </div>
        <div class="framePieContenedor">
            <div class="contentPieContenedor">
                <table width="100%">
                      <tr>
                         <td class="izq"><span><a href="#" onclick="guardar();">${guardar}</a></span></td>
                         <td class="odd">${espacio}</td>
                         <td class="der"><span><a href="#" onclick="consultar();">${buscar}</a></span></td>
                      </tr>
                </table>
            </div>
        </div>
     </div>
<%-- Componente tabla estandar listaRegistrosBitacora = lista --%>
     <c:choose>
              <c:when test="${not empty lista}">
                     <div class="frameTablaEstandar" style="display:block" id="tFrameTable">
                         <div class="titleTablaEstandar">
                             C&oacute;digos de Operaci&oacute;n
                         </div>
                         <div class="contentTablaEstandar" style="overflow:scroll; height:680px; text-align:center;">
                             <table>
                                   <thead>
                                         <tr>
                                            <th class="text_centro">${lblIdAplicacion}</th>
                                            <th class="text_centro">${lblCodigoOperacion}</th>
                                            <th class="text_centro" style="width:200px;">${descripcionApp}</th>
                                            <th class="text_centro">${lblCodigoISO}</th>
                                            <th class="text_centro" style="width:200px;">${descripcionApp}</th>
                                            <th class="text_centro">${modificar}</th>
                                            <th class="text_centro">${eliminar}</th>
                                         </tr>
                                   </thead>
                                   <tr>
                                      <td colspan="7" class="special"></td>
                                   </tr>
                                   <tbody>
                                         <c:set var="i" value="0"/>
                                         <c:set var="estilo" value="odd1"/>
                                         <c:forEach var="reg" items="${lista}">  
                                                   <tr class="${estilo}">
                                                      <td class="text_centro">${reg.idAplicacion}</td>
                                                      <td class="text_centro">${reg.codigoOperacion}</td>
                                                      <td class="text_izquierda">${reg.descripcionCodigoOperacion}</td>
                                                      <td class="text_centro">${reg.codigoIso}</td>
                                                      <td class="text_izquierda">${reg.descripcionIso}</td>
                                                      <td class="text_centro">
<img id="imgModificar" alt="Modificar Registro" 
src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/edit.gif" 
onclick="javascript:editar('${reg.idAplicacion}', '${reg.codigoOperacion}', 
'${reg.descripcionCodigoOperacion}', '${reg.codigoIso}','${reg.descripcionIso}');"></img>
                                                      </td>
                                                      <td class="text_centro">
<img id="imgBorrar" alt="Borrar Registro"
src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/delete.gif" 
onclick="javascript:anular('${reg.idAplicacion}', '${reg.codigoOperacion}', '${reg.codigoIso}');"></img>
                                                      </td>
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
<%-- <div class="paginador">
<c:if test="${not empty beanConsultaBitacora.referenciaRetroceder}">
<a href="javascript:consultar('B');">Anterior</a>
</c:if>
<c:if test="${not empty  beanConsultaBitacora.referenciaAvanzar}">
<a href="javascript:consultar('A');">Siguiente</a>
</c:if>
</div> --%>
                          <div class="framePieContenedor">
                              <div class="contentPieContenedor">
                                  <table width="100%">
                                        <tr>
                                           <td class="izq"><span><a href="javascript:reset();">${limpiar}</a></span></td>
                                           <td class="odd">${espacio}</td>
                                           <td class="der"><span><a href="javascript:cerrar();">${cancelar}</a></span></td>
                                        </tr>
                                  </table>
                              </div>
                         </div>
                      </div>
              </c:when>
              <c:otherwise>
                          <div class="frameTablaEstandar" style="display:none" id="tFrameTable"></div>
              </c:otherwise>
     </c:choose>
</form>
     <c:choose>
              <c:when test="${not empty codError}">
                     <script type="text/javascript">
                     /** DESCRIPCION Aviso, TITULO ERROR, CODIGO ERROR **/
                            if('${codError}' == "aviso"){
                               jAlert('${mensaje}', 'Consulta','${codAviso}','');
                            }
                     </script>
                     <script type="text/javascript">
                            if('${codError}' == "error"){
                               jError('${mensaje}','Error al realizar la consulta','${codError}','');
                            }
                     </script>
                     <script type="text/javascript">
                            if('${codError}' == "OK"){
                               jInfo('${mensaje}', 'Los datos se han guardado exitosamente.', '${codError}',''); 
                            }
                     </script>
                     <script type="text/javascript">
                            if('${codError}' == "MOD000"){ 
                               jConfirm("¿Seguro que desea modificar el registro?","Modificar Registro","","",function(e){
							   		if(e){
							   			document.forms["formCatalogoCodigosOperacion"].action = "ModificaRegistroCatalogoCodOperacion.do";
    									document.forms["formCatalogoCodigosOperacion"].submit();
									}
									return e;
								});
                            }
                     </script>
                     <script type="text/javascript">
                            if('${codError}' == "MOD001"){ 
                               jConfirm("¿Desea ingresar un nuevo elemento?","Nuevo Elemento","","",function(e){
							   		if(e){
							   			document.forms["formCatalogoCodigosOperacion"].action = "AltaRegistroCatalogoCodOperacion.do";
    									document.forms["formCatalogoCodigosOperacion"].submit();
									}
									return e;
								});
                            }
                     </script>
                	<script type="text/javascript">
                       if('${codError}' == "ERR000"){
                          jInfo('${mensaje}', 'No hay datos', '${codError}', ''); 
                       }
                	</script>
               </c:when>
               <c:otherwise>
                           <div class="frameTablaEstandar" style="display:none" id="tFrameTable"></div>
               </c:otherwise>
      </c:choose>
<%-- <script>
<c:if test="${not empty  codError}">
jError('${msgError}',
'Error al realizar la consulta',
'${codError}',
'');		
</c:if>
<c:if test="${not empty  codAviso}">
jAlert('${msgAviso}',
'Resultado en Bitácora',
'${codAviso}','');
</c:if>
</script> --%>