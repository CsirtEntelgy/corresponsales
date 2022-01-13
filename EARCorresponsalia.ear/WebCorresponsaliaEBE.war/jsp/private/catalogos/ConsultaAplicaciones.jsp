<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags"    prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt" %>

<jsp:include page="../myHeader.jsp" flush="true">
	<jsp:param name="menuItem"    value="catalogos" />
	<jsp:param name="menuSubitem"    value="catalogoAplicaciones" />
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

<script src="${pageContext.servletContext.contextPath}/js/private/catalogos/ConsultaAplicaciones.js" 
type="text/javascript"></script>

<form id="formCatalogoAplicaciones" action="" method="post" >

<%-- Componente titulo de pagina --%>
    <div class="pageTitleContainer">
        <span class="pageTitle">Catalogo de Aplicaciones</span>
        <input type="hidden" id="SES" name="SES" value="${SES}" />
        <input type="hidden" value="" id="opcAvanzarRetroceder" name="opcAvanzarRetroceder" />
        <input type="hidden" value="" id="regAnular" name="regAnular" />
        <input type="hidden" value="sinsel" id="comVal" name="comVal" />
        <input type="hidden" value="${hdnIdAplicacion}" id="hdnIdAplicacion" name="hdnIdAplicacion" />
        <input type="hidden" value="${hdnDescripcion}" id="hdnDescripcion" name="hdnDescripcion" />
        <input type="hidden" value="${idAplicacion}" id="auxIdAplicacion" name="auxIdAplicacion" />
    </div>

<%-- Componente formulario --%>
    <div class="frameBuscadorSimple">
        <div class="titleBuscadorSimple"><label>${filtros}</label></div>
            <div class="contentFormularioB">
                <table>
                      <tr>
                         <td>
                            <label>Id. Aplicacion: </label>
                         </td>
                         <td>
<input name="txtIdAplicacion" type="text" class="Campos" id="txtIdAplicacion" value="${idAplicacion}" 
style="width:80px;"/>
                         </td>
                         <td>
                            <label>Descripcion: </label>
                         </td>
                         <td>
<input name="txtDescripcion" type="text" class="Campos" id="txtDescripcion" value="${descripcion}" 
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
                        <div class="titleTablaEstandar">${aplicaciones}</div>
                        <div class="contentTablaEstandar" style="overflow:scroll; height:680px; text-align:center;">
                            <table>
                                  <thead>
                                        <tr>
                                           <th class="text_centro"><label>${lblIdAplicacion}</label></th>
                                           <th class="text_centro" style="width:250px;"><label>${descripcionApp}</label></th>
                                           <th class="text_centro"><label>${modificar}</label></th>
                                           <th class="text_centro"><label>${eliminar}</label></th>
                                        </tr>
                                   </thead>
                                   <tr>
                                      <td colspan="4" class="special"></td>
                                   </tr>
                                   <tbody>
                                         <c:set var="i" value="0"/>
                                         <c:set var="estilo" value="odd1"/>
                                         <c:forEach var="reg" items="${lista}">  
                                                   <tr class="${estilo}">
                                                      <td class="text_centro">${reg.idAplicacion}</td>
                                                      <td class="text_izquierda">${reg.descripcion}</td>
                                                      <td class="text_centro">
<img id="imgModificar" alt="Modificar Registro" 
src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/edit.gif" 
onclick="javascript:editar('${reg.idAplicacion}', '${reg.descripcion}');"></img>
                                                       </td>
                                                      <td class="text_centro">
<img id="imgBorrar" alt="Borrar Registro" 
src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/delete.gif" 
onclick="javascript:anular('${reg.idAplicacion}');"></img>
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
                          jAlert('${mensaje}','Consulta','${codAviso}', ''); 
                       }
                </script>
                <script type="text/javascript">
                       if('${codError}' == "error"){
                          jError('${mensaje}', 'Error al realizar la consulta', '${codError}', '');
                       }
                </script>
                <script type="text/javascript">
                       if('${codError}' == "OK"){
                          jInfo('${mensaje}', 'Los datos se han guardado exitosamente.', '${codError}', ''); 
                       }
                </script>
                <script type="text/javascript">
                            if('${codError}' == "MOD000"){ 
                               jConfirm("¿Seguro que desea modificar el registro?","Modificar Registro","","",function(e){
							   		if(e){
							   			document.forms["formCatalogoAplicaciones"].action = "ModificaRegistroCatalogoAplicaciones.do";
    									document.forms["formCatalogoAplicaciones"].submit();
									}
									return e;
								});
                            }
                     </script>
                     <script type="text/javascript">
                            if('${codError}' == "MOD001"){ 
                               jConfirm("¿Desea ingresar un nuevo elemento?","Nuevo Elemento","","",function(e){
							   		if(e){
							   			document.forms["formCatalogoAplicaciones"].action = "AltaRegistroCatalogoAplicaciones.do";
    									document.forms["formCatalogoAplicaciones"].submit();
									}
									return e;
								});
                            }
                     </script>
                     <script type="text/javascript">
                       if('${codError}' == "CBE001"){
                          jInfo('${mensaje}', 'Permiso Denegado', '${codError}', ''); 
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
<c:if test="${not empty codError}">
jError('${msgError}',//DESCRIPCION ERROR
'Error al realizar la consulta',//TITULO ERROR
'${codError}',//CODIGO ERROR
'');//TIP PARA QUE NO SUCEDA EL ERROR		
</c:if>
<c:if test="${not empty  codAviso}">
jAlert('${msgAviso}',//DESCRIPCION Aviso
'Resultado en Bitácora',//TITULO ERROR
'${codAviso}',//CODIGO ERROR
'');//TIP PARA QUE NO SUCEDA EL ERROR		
</c:if>
</script> --%>