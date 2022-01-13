<jsp:include page="../myHeader.jsp" flush="true">
</jsp:include>
 	  <form name ="login" method="post" action ="${urlInicio}">
 	  	<input type="hidden" id="SES" name="SES" value="${SES}" />
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

