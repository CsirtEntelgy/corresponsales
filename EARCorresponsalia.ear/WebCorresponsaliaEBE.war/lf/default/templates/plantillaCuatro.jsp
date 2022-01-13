<jsp:include page="../../../jsp/private/myHeader.jsp" flush="true"/>
<jsp:include page="../../../jsp/private/myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="plantillas" />
	<jsp:param name="menuSubitem" value="plantilla4" />
</jsp:include>



	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Plantillas</span> - Plantilla cuatro
	</div>
	
	<!-- Componente contenedor de contexto -->
	<div class="contextContainer">
		<div>
			<table>
				<tr><th>N&uacute;mero de contrato:</th><td>030 1001 34 0001622271</td></tr>
				<tr><th>Titular:</th><td>Cliente Prueba</td></tr>
				<tr><th>Saldo disponible:</th><td>2.500 Euros</td></tr>
			</table>
		</div>
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
								<img src="${pageContext.servletContext.contextPath}/img/menu/lupa.jpg" alt="buscar" width="22" height="22" />
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

