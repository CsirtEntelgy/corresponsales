<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"    prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt"%>
<jsp:include page="myHeaderP.jsp" flush="true" />

<script>
		
	function continuar(){
    	document.forms["formSesionExpirada"].action = "${pageContext.servletContext.contextPath}/publico/salir.do";
    	document.forms["formSesionExpirada"].submit();
	}
				
</script>
		
<form id="formSesionExpirada" action="" method="post">
	&nbsp;
	<center>
		<table height="600" width="400">
			<tr height="40%">
				<th>&nbsp;</th>
			</tr>
			<tr>
				<th  class="text_centro"><a>Ya existe una sesion activa con el usuario ${usuarioSesionado}</a></th>
			</tr>
			<tr>
				<th height="5%" class="text_centro"><a href="javascript:continuar();">Continuar</a></th>
			</tr>
			<tr height="50%">
				<th >&nbsp;</th>
			</tr>
		</table>
		
		<br>		
	</center>
</form>

