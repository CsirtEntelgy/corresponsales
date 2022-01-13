<jsp:include page="../../../jsp/private/myHeader.jsp" flush="true"/>
<jsp:include page="../../../jsp/private/myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="plantillas" />
	<jsp:param name="menuSubitem" value="plantilla1" />
</jsp:include>

	<!-- Componente titulo de página -->
	<div class="pageTitleContainer">
		<span class="pageTitle">Plantillas</span> - Plantilla Uno
	</div>
	
	<!-- Componente contenedor de contexto -->
	<div class="contextContainer">
		<div>
			<table>
				<tr><th>N&uacute;mero de contrato:</th><td>030 1001 34 0001622271</td></tr>
				<tr><th>Titular:</th><td>Cliente Prueba</td></tr>
				<tr><th>Saldo:</th><td>+1.000.000,00 Euros</td></tr>
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
	
	<!-- Componente pie de pagina -->
	<div class="commentContainer">
		El saldo total del dep&oacute;sito es la suma de contravalores de las diferentes imposiciones realizadas en euros y en divisas. El contravalor de las imposiciones en divisas se calcula tomando como referencia el &uacute;ltimo cambio fixing disponible.
	</div>

