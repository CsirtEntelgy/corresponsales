<jsp:include page="../../../jsp/private/myHeader.jsp" flush="true"/>
<jsp:include page="../../../jsp/private/myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="plantillas" />
	<jsp:param name="menuSubitem" value="listaComponentes" />
</jsp:include>

<script type = "text/javascript" src = "${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/js/templates/listaComponentes.js"></script>

	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">T&iacute;tutlo unidad de presentaci&oacute;n</span> - Subt&iacute;tulo unidad de presentaci&oacute;n
	</div>

	<!-- Componente contenedor de contexto -->
	<div class="contextContainer">
		<div>
			<table>
				<tr>
					<th>N&uacute;mero de contrato:</th>
					<td>030 1001 34 0001622271</td>
				</tr>
				<tr>
					<th>Titular:</th>
					<td>Cliente Prueba</td>
				</tr>
				<tr>
					<th>Producto:</th>
					<td>3000001 C.C.VISTA</td>
				</tr>
				<tr>
					<th>Saldo disponible:</th>
					<td>1.000.000 Euros</td>
				</tr>
			</table>
		</div>
	</div>

	<!-- Componente Contenedor Simple -->
	<div class="frameSimpleContainer">
		<div class="titleSimpleContainer">T&iacute;tulo de formulario</div>
		<div class="contentSimpleContainer">
			When using these properties,we're primarily working with the &lt;div&gt; tag, which you may not have 
			been properly introduced to yet. These DIVisions are the ultimate block-level tag, as they can contain 
			whole pages within them. You can wrap divs around large blocks of text and style away. divs are used to 
			create what used to be known as layers, and can be used as a replacement for tabled layout. We get down 
			to that fully in CSS Layout.
			<br><br>
			<input type="button" value="Alert" onclick="javascript:jAlert('Los campos de color azul y marcados con el simbolo (*) son obligatorios. Recuerde que debe completarlos.', 
																				'Faltan campos por completar',
																				'ER-001', 
																				'-Verifique que los campos obligatorios esten completados.');" />
			<input type="button" value="Info" onclick="javascript:jInfo('Los campos de color azul y marcados con el simbolo (*) son obligatorios. Recuerde que debe completarlos.', 
																				'Faltan campos por completar',
																				'ER-001', 
																				'-Verifique que los campos obligatorios esten completados.');" />
			<input type="button" value="Error" onclick="javascript:jError('Los campos de color azul y marcados con el simbolo (*) son obligatorios. Recuerde que debe completarlos.', 
																				'Faltan campos por completar',
																				'ER-001', 
																				'-Verifique que los campos obligatorios esten completados.');" />
			<input type="button" value="ayuda" onclick="javascript:jHelp('Los campos de color azul y marcados con el simbolo (*) son obligatorios. Recuerde que debe completarlos.', 
																				'Faltan campos por completar', 
																				'ER-001', 
																				'-Verifique que los campos obligatorios esten completados.');" />
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
	
	<!-- Componente buscador  -->
	<div class="frameBuscador">
		<div class="titleBuscador">Buscador</div>
		<div class="contentBuscador">
			<table>
				<tbody>
					<tr>
						<td width="138" class="text_derecha">Primer apellido:</td>
						<td width="400" class="text_dinero">
							<label><input name="textfield" type="text" class="Campos" id="textfield" /></label>
						</td>
					</tr>
					<tr>
						<td class="text_derecha">Segundo apellido:</td>
						<td class="text_dinero"><input name="textfield2" type="text" class="Campos" id="textfield2" /></td>
					</tr>
					<tr>
						<td class="text_derecha">Nombre:</td>
						<td class="text_dinero"><input name="textfield17" type="text" class="Campos" id="textfield19" /></td>
					</tr>
					<tr>
						<td colspan="2" class="text_derecha">
							<hr />
						</td>
					</tr>
					<tr>
						<td class="text_derecha">
							<span class="text_dinero">(!) País de residencia:</span>
						</td>
						<td class="text_dinero">
							<span class="text_fecha">
								<input name="textfield7" type="text" class="Campos_Des" id="textfield4" value="MX" size="2" />
								<select name="select" class="Campos_Des" id="select">
									<option>México</option>
									<option>País 2</option>
									<option>País 2</option>
								</select>
							</span>
							<span class="text_izquierda">
								<img src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/menu/lupa.jpg" alt="buscar" width="22" height="22" />
								Pulse sobre el icono para buscar un país
							</span>
						</td>
					</tr>
					<tr>
						<td class="text_derecha"><span class="text_dinero">Plaza:</span></td>
						<td class="text_dinero"><input name="textfield4" type="text" class="Campos_Des" id="textfield5" /></td>
					</tr>
					<tr>
						<td class="text_derecha"><span class="text_dinero">Banco Oficina:</span></td>
						<td class="text_dinero">
							<input name="textfield5" type="text" class="Campos_Des" id="textfield6" size="15" /> 
							<input name="textfield6" type="text" class="Campos_Des" id="textfield7" size="15" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table>
					<tr>
						<td width="279" class="cero">&nbsp;</td>
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="der"><a href="#">Detalles</a></td>
					</tr>
				</table>
			</div>
		</div>
	</div>

	<!-- Componente buscador simple -->
	<div class="frameBuscadorSimple">
		<div class="titleBuscadorSimple">Buscador simple</div>
		<div class="contentBuscadorSimple">
			Introduzca el rango de puntos y pulse &quot;Buscar&quot;
			<table>
				<tbody>
					<tr>
						<td class="text_izquierda">
							Puntos: Dese<input name="textfield18" type="text" class="Campos" id="textfield20" size="10" /> 
							hasta <input name="textfield19" type="text" class="Campos" id="textfield21" size="10" />
							<span><a href="#">Buscar</a></span></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	
	<!-- Componente tabla estandar -->
	<div class="frameTablaEstandar">
		<div class="titleTablaEstandar">
			Tabla estándar <span class="textosin">- Seleccione una y pulse detalle</span>
		</div>
		<div class="contentTablaEstandar">
			<table>
				<thead>
					<tr>
						<th colspan="2" class="text_izquierda">Periodo Inicial</th>
						<th width="147" class="text_centro">Periodo Final</th>
						<th width="147" class="text_centro">Importe debe</th>
						<th width="147" class="text_centro">Importe haber</th>
					</tr>
				</thead>
				<tr>
					<Td colspan="5" class="special"></Td>
				</tr>
				<tbody>
					<tr class="odd">
						<td width="20"><input name="radio" type="radio" class="Campos"
							id="radio" value="radio" /></td>
						<td width="128" class="text_izquierda">30-06-2000</td>
						<td class="text_centro">30/06/2000</td>
						<td class="text_derecha">+2.000,00</td>
						<td class="text_derecha">+2.000,00</td>
					</tr>
					<tr class="odd1">
						<td><input name="radio" type="radio" class="Campos" id="radio2"
							value="radio" /></td>
						<td class="text_izquierda">30-06-2000</td>
						<td class="text_centro">30/06/2000</td>
						<td class="text_derecha">+2.000,00</td>
						<td class="text_derecha">+2.000,00</td>
					</tr>
					<tr class="odd2">
						<td><input name="radio" type="radio" class="Campos" id="radio3"
							value="radio" /></td>
						<td class="text_izquierda">30-06-2000</td>
						<td class="text_centro">30/06/2000</td>
						<td class="text_derecha">+2.000,00</td>
						<td class="text_derecha">+2.000,00</td>
					</tr>
					<tr class="odd1">
						<td><input name="radio" type="radio" class="Campos" id="radio4"
							value="radio" /></td>
						<td class="text_izquierda">30-06-2000</td>
						<td class="text_centro">30/06/2000</td>
						<td class="text_derecha">+2.000,00</td>
						<td class="text_derecha">+2.000,00</td>
					</tr>
					<tr class="odd2">
						<td><input name="radio" type="radio" class="Campos" id="radio5"
							value="radio" /></td>
						<td class="text_izquierda">30-06-2000</td>
						<td class="text_centro">30/06/2000</td>
						<td class="text_derecha">+2.000,00</td>
						<td class="text_derecha">+2.000,00</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table>
					<tr>
						<td width="279" class="cero">&nbsp;</td>
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="der"><a href="#">Detalles</a></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="paginador"><a href="a">&lt;&lt;10 anteriores</a> | <a
			href="#b">10 siguientes&gt;&gt;</a></div>
	</div>
	
	<!-- Componente tabla estandar -->
	<div class="frameTablaEstandar">
	<div class="titleTablaEstandar">
		Tabla est&aacute;ndar <span class="textosin">- Seleccione una y pulse detalle</span>
	</div>
	<div class="contentTablaEstandar">
		<table>
			<thead>
				<tr>
					<th colspan="2" class="text_izquierda">Periodo Inicial</th>
					<th width="147" class="text_centro">Periodo Final</th>
					<th width="147" class="text_centro">Importe debe</th>
					<th width="147" class="text_centro">Importe haber</th>
				</tr>
			</thead>
			<tr>
				<td colspan="5" class="special"></td>
			</tr>
			<tbody>
				<tr class="odd">
					<td width="20"><input name="radio" type="radio" class="Campos"
						id="radio15" value="radio" /></td>
					<td width="128" class="text_izquierda">30-06-2000</td>
					<td class="text_centro">30/06/2000</td>
					<td class="text_derecha">+2.000,00</td>
					<td class="text_derecha">+2.000,00</td>
				</tr>
				<tr class="odd1">
					<td><input name="radio" type="radio" class="Campos" id="radio16"
						value="radio" /></td>
					<td class="text_izquierda">30-06-2000</td>
					<td class="text_centro">30/06/2000</td>
					<td class="text_derecha">+2.000,00</td>
					<td class="text_derecha">+2.000,00</td>
				</tr>
				<tr class="odd2">
					<td><input name="radio" type="radio" class="Campos" id="radio17"
						value="radio" /></td>
					<td class="text_izquierda">30-06-2000</td>
					<td class="text_centro">30/06/2000</td>
					<td class="text_derecha">+2.000,00</td>
					<td class="text_derecha">+2.000,00</td>
				</tr>
				<tr class="odd1">
					<td><input name="radio" type="radio" class="Campos" id="radio18"
						value="radio" /></td>
					<td class="text_izquierda">30-06-2000</td>
					<td class="text_centro">30/06/2000</td>
					<td class="text_derecha">+2.000,00</td>
					<td class="text_derecha">+2.000,00</td>
				</tr>
				<tr class="odd2">
					<td><input name="radio" type="radio" class="Campos" id="radio19"
						value="radio" /></td>
					<td class="text_izquierda">30-06-2000</td>
					<td class="text_centro">30/06/2000</td>
					<td class="text_derecha">+2.000,00</td>
					<td class="text_derecha">+2.000,00</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="framePieContenedor">
		<div class="contentPieContenedor">
			<table>
				<tr>
					<td width="279" class="Tizq">Emisión de transferencias</td>
					<td width="6" class="odd">&nbsp;</td>
					<td width="279" class="Tder">Consulta de transferencias</td>
				</tr>
				<tr>
					<td class="izq"><a href="#">A una de sus cuentas en Banesto</a></td>
					<td class="odd">&nbsp;</td>
					<td class="der"><a href="#">Emitidas hoy nacionales</a></td>
				</tr>
				<tr>
					<td class="izq"><a href="#">A otras cuentas</a></td>
					<td class="odd">&nbsp;</td>
					<td class="der"><a href="#">Emitidas</a></td>
				</tr>
				<tr>
					<td class="izq"><a href="#">Periódicas</a></td>
					<td class="odd">&nbsp;</td>
					<td class="der"><a href="#">Recibidas</a></td>
				</tr>
				<tr>
					<td class="izq"><a href="#">Pago de Nómina</a></td>
					<td class="odd">&nbsp;</td>
					<td class="cero">&nbsp;</td>
				</tr>
				<tr>
					<td class="izq"><a href="#">Donaciones</a></td>
					<td class="odd">&nbsp;</td>
					<td class="cero">&nbsp;</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="paginador">
		<a href="a">&lt;&lt;10 anteriores</a> | <a href="#b">10 siguientes&gt;&gt;</a></div>
	</div>

	<!-- Componente tabla de una columna -->
	<div class="frameTablaColumna">
		<div class="titleTablaColumna">Tabla listado de una columna</div>
		<div class="contentTablaColumna">
			<table>
				<thead>
					<tr>
						<th scope="col">Descripción</th>
					</tr>
				</thead>
				<tr>
					<td colspan="1" class="special"></td>
				</tr>
				<tbody>
					<tr class="odd1">
						<td class="text_izquierda">Espacio reservado para el registro</td>
					</tr>
					<tr class="odd1">
						<td class="text_izquierda">Espacio reservado para el registro</td>
					</tr>
					<tr class="odd1">
						<td class="text_izquierda">Espacio reservado para el registro</td>
					</tr>
					<tr class="odd1">
						<td class="text_izquierda">Espacio reservado para el registro</td>
					</tr>
					<tr class="odd1">
						<td class="text_izquierda">Espacio reservado para el registro</td>
					</tr>
				</tbody>
			</table>
			<div class="paginador">
				<a href="a">&lt;&lt;10 anteriores</a> | <a href="#b">10 siguientes&gt;&gt;</a>
			</div>
		</div>
	</div>

	<!-- Componente tabla de varias columnas -->
	<div class="frameTablaVariasColumnas">
		<div class="titleTablaVariasColumnas">Tabla listado varias columnas</div>
		<div class="contentTablaVariasColumnas">
			<table>
				<tr>
					<th width="122" class="text_izquierda">Documento</th>
					<th width="223" class="text_centro" scope="col">Nombre</th>
					<th width="122" class="text_centro" scope="col">Tipo de cliente</th>
					<th width="122" class="text_centro" scope="col">Plaza</th>
				</tr>
			
				<tr>
			
					<Td colspan="4" class="special"></Td>
				</tr>
				<tbody>
					<tr class="odd2">
						<td class="text_izquierda">A1234567</td>
						<td class="text_izquierda">Aparsur Metalurúrgica</td>
						<td class="text_izquierda">Cliente</td>
						<td class="text_derecha">J130013</td>
					</tr>
					<tr class="odd1">
						<td class="text_izquierda">B7654321</td>
						<td class="text_izquierda">Arpaes S.A.</td>
						<td class="text_izquierda">Cliente</td>
						<td class="text_derecha">J130015</td>
					</tr>
					<tr class="odd2">
						<td class="text_izquierda">A0987654</td>
						<td class="text_izquierda">Arpaes S.A.</td>
						<td class="text_izquierda">Potencial</td>
						<td class="text_derecha">J130035</td>
					</tr>
					<tr class="odd1">
						<td class="text_izquierda">B6789045</td>
						<td class="text_izquierda">Industrias Aripal S.A.</td>
						<td class="text_izquierda">Cliente</td>
						<td class="text_derecha">J130045</td>
					</tr>
					<tr class="odd2">
						<td class="text_izquierda">A4326541</td>
						<td class="text_izquierda">Arpa Solution</td>
						<td class="text_izquierda">Potencial</td>
						<td class="text_derecha">J130055</td>
					</tr>
				</tbody>
			</table>
			<div class="paginador"><a href="a">&lt;&lt;10 anteriores</a> | <a href="#b">10 siguientes&gt;&gt;</a></div>
		</div>
	</div>

	<!-- Componente tabla con registros anidados -->
	<div class="frameTablaAnidado">
		<div class="titleTablaAnidado">
			<table>
				<tr>
					<td width="290">Tabla con líneas anidadas</td>
					<td width="120">&nbsp;</td>
					<td width="90"><img src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/menu/tabla/separador.gif" width="6"
						height="17" /><img src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/menu/tabla/SobreCerrado.gif" width="30"
						height="17" /><img src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/menu/tabla/SobreAbierto.gif" width="30"
						height="17" /></td>
				</tr>
			</table>
		</div>
		<div class="contentTablaAnidado">
			<table>
				<tr>
					<th class="text_izquierda">Cabecera columna uno</th>
					<th scope="col">Cabecera dos</th>
				</tr>
				<tr>
					<Td colspan="2" class="special"></Td>
				</tr>
				<tbody>
					<tr class="prim">
						<td class="prim1">Lorem Ipsum</td>
						<td class="text_derecha">50.000,00</td>
					</tr>
					<tr class="subCon">
						<td class="subCon1">Lorem Ipsum</td>
						<td class="text_derecha">10.000,00</td>
					</tr>
					<tr class="renglonContinuoSin">
						<td class="renglonContinuo1Sin">Lorem Ipsum</td>
						<td class="text_derecha">10.000,00</td>
					</tr>
					<tr class="odd">
						<td class="SubSin1">Lorem Ipsum</td>
						<td class="text_derecha">50.000,00</td>
					</tr>
					<tr class="subtot">
						<td class="text_derecha">Subtotal:</td>
						<td class="text_derecha">120.000,00</td>
					</tr>
					<tr class="prim">
						<td class="prim1">Lorem Ipsum</td>
						<td class="text_derecha">80.000,00</td>
					</tr>
					<tr class="odd">
						<td class="SubSin1">lorem</td>
						<td class="text_derecha">80.000,00</td>
					</tr>
					<tr class="subtot">
						<td class="text_derecha">Subtotal:</td>
						<td class="text_derecha">80.000,00</td>
					</tr>
					<tr class="tot">
						<td class="text_derecha">Total:</td>
						<td class="text_derecha">200.000,00</td>
					</tr>
				</tbody>
			</table>
			<div class="paginador"><a href="a">&lt;&lt;10 anteriores</a> | <a href="#b">10 siguientes&gt;&gt;</a></div>
		</div>
	</div>

	<!-- Componente formulario -->
	<div class="frameFormularioB">
		<div class="contentFormularioB">
			<div class="titleFormularioB">Formulario - <span class="textosin">Lorem ipsum asit abec</span></div>
			<table>
				<tbody>
					<tr>
						<td colspan="4" class="ind">Todos los campos son obligatorios</td>
					</tr>
					<tr>
						<th colspan="4" class="text_izquierda">Datos de la Orden:<span
							class="textosin">.......................................................................................................</span></th>
					</tr>
					<tr>
						<td width="154" class="odd">Importe:</td>
						<td colspan="3"><label> <input name="textfield22"
							type="text" class="Campos_Des" id="textfield23" /> Pesos </label></td>
					</tr>
					<tr>
						<td class="odd">Concepto:</td>
						<td colspan="3"><input name="textfield2" type="text"
							class="CamposErroneos" id="textfield2" /></td>
					</tr>
					<tr>
						<td class="odd">Periodicidad:</td>
						<td><input name="textfield3" type="text" class="Campos"
							id="textfield3" /></td>
						<td>&nbsp;</td>
						<td class="text_dinero">&nbsp;</td>
					</tr>
					<tr>
						<td class="odd">Fecha inicio:</td>
						<td colspan="3">
							<input name="startDate2" type="text" class="Campos" id="startDate2" readonly="readonly" size="14" />
							<img id="cal3" src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/calendar/calendar.png" alt="img calendar">
						</td>
					</tr>
					<tr>
						<td class="odd">Fecha fin:</td>
						<td><input name="radio6" type="radio" class="Campos" id="radio7"
							value="radio6" /> Indicar fecha</td>
						<td colspan="2">
							<input name="endDate2" type="text" class="Campos" id="endDate2" readonly="readonly" size="14" />
							<img id="cal4" src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/calendar/calendar.png" alt="img calendar">
						</td>
					</tr>
					<tr>						
						<td width="139"><label> <input name="radio6"
							type="radio" class="Campos" id="radio6" value="radio6" /> </label>
						Indefinida</td>
						<td width="201">&nbsp;</td>
						<td width="54" class="text_dinero">&nbsp;</td>
					</tr>
				</tbody>
			</table>
			<table>
				<tbody>
					<tr>
						<th colspan="6" class="text_dinero">Datos beneficiario:<span
							class="textosin">......................................................................................................</span></th>
					</tr>
					<tr>
						<td width="225" class="odd">Nombre del beneficiario:</td>
						<td colspan="5"><label> <input name="textfield8"
							type="text" class="Campos" id="textfield8" size="40" /> </label></td>
					</tr>
					<tr>
						<td class="odd">Residente Mexicano:</td>
						<td colspan="5"><label> <input name="radio6"
							type="radio" class="Campos" id="radio8" value="radio6" /> </label> Si <label>
						<input name="radio6" type="radio" class="Campos" id="radio9"
							value="radio6" /> </label> No</td>
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
						<td width="37"><input name="textfield15" type="text"
							class="CamposCompletar" id="textfield9" size="4" /></td>
						<td width="37"><input name="textfield16" type="text"
							class="CamposCompletar" id="textfield10" size="4" /></td>
						<td width="20"><input name="textfield13" type="text"
							class="CamposCompletar" id="textfield17" size="2" /></td>
						<td width="62"><input name="textfield14" type="text"
							class="CamposCompletar" id="textfield18" size="12" /></td>
						<td width="157" class="Pie button">Buscador de Bancos</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="framePieContenedor">
			<div class="contentPieContenedor">
				<table>
					<tr>
						<td class="izq"><a href="#">Otros Domicilios</a></td>
						<td class="odd">&nbsp;</td>
						<td class="der"><a href="#">Tramitar</a></td>
					</tr>
					<tr>
						<td width="279" class="cero">&nbsp;</td>
						<td width="6" class="odd">&nbsp;</td>
						<td width="279" class="der"><a href="#">Simular</a></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<!-- Componente formulario de confirmacion -->
	<div class="frameFormularioConfirmacion">
		<div class="titleFormularioConfirmacion">
			Formulario confirmación de operación <span class="textosin">indicación lorem ipsum: </span>
		</div>
		<div class="contentFormularioConfirmacion">
			<table>
				<tbody>
					<tr>
						<td width="206" class="odd">Número de cuenta:</td>
						<td width="353" class="text_izquierda"><label>0030 1001
						37 0000002271</label></td>
					</tr>
					<tr>
						<td class="odd">Titular:</td>
						<td class="text_izquierda">Cliente de prueba</td>
					</tr>
					<tr>
						<td class="odd">Nombre de interviniente:</td>
						<td class="text_izquierda">Cliente de prueba</td>
					</tr>
					<tr>
						<td class="odd">Tipo de intervención:</td>
						<td class="text_izquierda">Autorizado</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	
	<!-- Componente pie de pagina -->
	<div class="PiePag"><a href="#">Canelar</a> <a href="#">Aceptar</a></div>
