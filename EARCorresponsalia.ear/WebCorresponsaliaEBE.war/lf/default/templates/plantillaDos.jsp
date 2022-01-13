<jsp:include page="../../../jsp/private/myHeader.jsp" flush="true"/>
<jsp:include page="../../../jsp/private/myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="plantillas" />
	<jsp:param name="menuSubitem" value="plantilla2" />
</jsp:include>
<script type = "text/javascript" src = "${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/js/templates/plantillaDos.js"></script>

	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Plantillas</span> - Plantilla Dos
	</div>
	
	<!-- Componente contenedor de contexto -->
	<div class="contextContainer">
		<div>
			<table>
				<tr><th>N&uacute;mero de cuenta:</th><td>030 1001 34 0001622271</td></tr>
				<tr><th>Saldo:</th><td>2.000,00 Euros</td></tr>
			</table>
		</div>
	</div>
			
	<!-- Componente navegador de contenidos (pestañas) -->
	<div class="tabsContainer">
		<ul id="tabs">
			<li id="one" class="active"><a href="javascript:changeSelectedTab('one', 'divOneTab');">Item one</a></li>
			<li id="two"><a href="javascript:changeSelectedTab('two', 'divTwoTab');">Item two</a></li>
			<li id="three"><a href="javascript:changeSelectedTab('three', 'divThreeTab');">Item three</a></li>
			<li id="four"><a href="javascript:changeSelectedTab('four', 'divFourTab');">Item four</a></li>
			<li id="five"><a href="javascript:changeSelectedTab('five', 'divFiveTab');">Item five</a></li>
		</ul>
	</div>
	<!-- Contenido primer pestaña -->
	<div id="divOneTab" class="currentFrameTabContainer">
		<div class="contentTabContainer">
			<div class="contentFormulario">
				<table>
					<tbody>
						<tr>
							<th colspan="4" class="text_izquierda">Datos de la Orden:
								<span class="textosin">.......................................................................................................</span>
							</th>
						</tr>
						<tr>
							<td width="154" class="odd">Importe:</td>
							<td colspan="3">
								<label><input name="textfield22" type="text" class="Campos_Des" id="textfield23" /> Pesos </label>
							</td>
						</tr>
						<tr>
							<td class="odd">Concepto:</td>
							<td colspan="3"><input name="textfield2" type="text" class="CamposErroneos" id="textfield2" /></td>
						</tr>
						<tr>
							<td class="odd">Periodicidad:</td>
							<td><input name="textfield3" type="text" class="Campos" id="textfield3" /></td>
							<td>&nbsp;</td>
							<td class="text_dinero">&nbsp;</td>
						</tr>
						<tr>
							<td class="odd">Fecha inicio:</td>
							<td colspan="3">
								<input name="startDate" type="text" class="Campos" id="startDate" readonly="readonly" size="14" />
								<img id="cal" src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/calendar/calendar.png" alt="img calendar">
							</td>
						</tr>
						<tr>
							<td class="odd">Fecha fin:</td>
							<td><input name="radio6" type="radio" class="Campos" id="radio7" value="radio6" /> Indicar fecha</td>
							<td colspan="2">
								<input name="endDate" type="text" class="Campos" id="endDate" readonly="readonly" size="14" />
								<img id="cal2" src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/calendar/calendar.png" alt="img calendar">
							</td>
						</tr>
						<tr>
							
							<td width="139">
								<label><input name="radio6" type="radio" class="Campos" id="radio6" value="radio6" /></label>
								Indefinida
							</td>
							<td width="201">&nbsp;</td>
							<td width="54" class="text_dinero">&nbsp;</td>
						</tr>
					</tbody>
				</table>
				<table>
					<tbody>
						<tr>
							<th colspan="6" class="text_dinero">
								Datos beneficiario:<span class="textosin">......................................................................................................</span>
							</th>
						</tr>
						<tr>
							<td width="225" class="odd">Nombre del beneficiario:</td>
							<td colspan="5">
								<label><input name="textfield8" type="text" class="Campos" id="textfield8" size="40" /></label>
							</td>
						</tr>
						<tr>
							<td class="odd">Residente Mexicano:</td>
							<td colspan="5">
								<label><input name="radio6" type="radio" class="Campos" id="radio8" value="radio6" /></label>
								Si<label><input name="radio6" type="radio" class="Campos" id="radio9" value="radio6" /> </label> No
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>Banco</td>
							<td>Oficina</td>
							<td>DC</td>
							<td>Cuenta</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td class="odd">Nombre:</td>
							<td width="37"><input name="textfield15" type="text" class="CamposCompletar" id="textfield9" size="4" /></td>
							<td width="37"><input name="textfield16" type="text" class="CamposCompletar" id="textfield10" size="4" /></td>
							<td width="20"><input name="textfield13" type="text" class="CamposCompletar" id="textfield17" size="2" /></td>
							<td width="62"><input name="textfield14" type="text" class="CamposCompletar" id="textfield18" size="12" /></td>
							<td width="157" class="Pie button">Buscador de Bancos</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<!-- Contenido segunda pestaña -->
	<div id="divTwoTab" class="activeFrameTabContainer">
		<div class="contentTabContainer">Texto muestra tab dos</div>
	</div>
	<!-- Contenido tercer pestaña -->
	<div id="divThreeTab" class="activeFrameTabContainer">
		<div class="contentTabContainer">Texto muestra tab tres</div>
	</div>
	<!-- Contenido cuarta pestaña -->
	<div id="divFourTab" class="activeFrameTabContainer">
		<div class="contentTabContainer">Texto muestra tab cuatro</div>
	</div>
	<!-- Contenido quinta pestaña -->
	<div id="divFiveTab" class="activeFrameTabContainer">
		<div class="contentTabContainer">Texto muestra tab cinco</div>
	</div>
	
	<!-- Componente pie de pagina -->
	<div class="PiePag"><a href="#">Canelar</a> <a href="#">Aceptar</a></div>
			
