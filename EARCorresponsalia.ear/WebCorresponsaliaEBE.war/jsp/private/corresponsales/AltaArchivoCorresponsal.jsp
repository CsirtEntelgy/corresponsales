<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="corresponsales" />
	<jsp:param name="menuSubitem" value="altamasivac" />
</jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type = "text/javascript" src = "${pageContext.request.contextPath}/js/confis.js"></script>

<form id="formaConsultaConstanciaFiscal" action="" method="post">

	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Alta masiva de corresponsales</span>
	</div>
	
	<!-- Componente formulario -->
	<%
		String codClien = "";
		String cta = "";
		String credit = "";
		codClien = request.getParameter("txtCodClien")== null?"":request.getParameter("txtCodClien");
		cta = request.getParameter("txtCuenta")== null?"":request.getParameter("txtCuenta");
		credit = request.getParameter("txtCredito")== null?"":request.getParameter("txtCredito");
		
	//    SessionDTO perfil = (SessionDTO) request.getSession(false).getAttribute("confSession");
	 
	 
	 %><!-- -->
	<div class="frameBuscadorSimple">
		<!-- Componente buscador simple -->
		<div class="titleBuscadorSimple">Alta de Corresponsales</div>
			<div class="contentBuscadorSimple">
				<table>
					<tbody>
						<tr>
							<td class="text_izquierda">
								Archivo:
								<input type="file" class="Campos_Des" id="fbArchivo" name="fbArchivo" />							
								<span width="100">&nbsp;&nbsp;&nbsp;</span>
								<span><a href="#" onclick="modificarParams();" >Cargar</a></span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
	</div>






	