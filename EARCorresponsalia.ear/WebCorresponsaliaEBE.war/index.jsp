<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"    prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt"%>
		
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<!-- meta HTTP-EQUIV="REFRESH"      content="0; url=publico/login.go"/-->
		<!--  meta HTTP-EQUIV="REFRESH"      content="0; url=principal/inicio.do"-->

		<link href="${pageContext.servletContext.contextPath}/lf/default/css/calendar/calendar.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.servletContext.contextPath}/lf/default/css/dialogBox/jquery.alerts.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.servletContext.contextPath}/lf/default/js/global.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/lf/default/js/dialogBox/jquery-1.2.6.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/lf/default/js/dialogBox/jquery.ui.draggable.js" type="text/javascript"></script>		
		<script src="${pageContext.servletContext.contextPath}/lf/default/js/dialogBox/jquery.alerts.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/lf/default/js/calendar/calendar.js" type="text/javascript" ></script>
		<script src="${pageContext.servletContext.contextPath}/lf/default/js/calendar/calendarSetup.js" type="text/javascript" ></script>
		<script src="${pageContext.servletContext.contextPath}/lf/default/js/calendar/calendarES.js" type="text/javascript" ></script>
		<script src="${pageContext.servletContext.contextPath}/lf/default/js/dialogBox/jquery.bgiframe.js" type="text/javascript"></script>
		

		<script src="${pageContext.servletContext.contextPath}/lf/default/js/menu/dynamicMenu.js" type="text/javascript"></script>
		<link href="${pageContext.servletContext.contextPath}/lf/default/css/menu/estilos.css"            rel="stylesheet" type="text/css">
		<link href="${pageContext.servletContext.contextPath}/lf/default/css/menu/elementos_interfaz.css" rel="stylesheet" type="text/css">
	</head>
	<body>
   <div id="allContent">
		<div id="header">
		</div>
	</div>
	&nbsp;
 	<center>
 	  <form name ="login" method="post" action ="publico/login.go">
		<table height="600" width="400">
			<tr height="40%">
				<th>&nbsp;</th>
			</tr>
			<tr>
				<th  class="text_centro"><a>Accesando a la aplicaci&oacute;n, por favor espere...</a></th>
			</tr>
			<tr height="50%">
				<th >&nbsp;</th>
			</tr>
		</table>
		<script type="text/javascript" >
		  document.forms['login'].submit();
		</script>
		</form>
		<br>		
	</center>
	</body>	
</html>

