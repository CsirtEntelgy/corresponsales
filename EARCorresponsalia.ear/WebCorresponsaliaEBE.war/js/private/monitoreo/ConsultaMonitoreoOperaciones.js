function llenaDatos() {
    document.forms["formaConsultaMonitoreoOperaciones"].optCorresponsal.value = document.forms["formaConsultaMonitoreoOperaciones"].optCorresponsal1.value;

    document.forms["formaConsultaMonitoreoOperaciones"].optSucursal.value = document.forms["formaConsultaMonitoreoOperaciones"].optSucursal1.value;

    document.forms["formaConsultaMonitoreoOperaciones"].txtHoraInicio.value = document.forms["formaConsultaMonitoreoOperaciones"].txtHoraInicio1.value;

    document.forms["formaConsultaMonitoreoOperaciones"].txtHoraFin.value = document.forms["formaConsultaMonitoreoOperaciones"].txtHoraFin1.value;

    document.forms["formaConsultaMonitoreoOperaciones"].optOperacion.value = document.forms["formaConsultaMonitoreoOperaciones"].optOperacion1.value;

    document.forms["formaConsultaMonitoreoOperaciones"].txtImporteMinimo.value = document.forms["formaConsultaMonitoreoOperaciones"].txtImporteMinimo1.value;

    document.forms["formaConsultaMonitoreoOperaciones"].txtImporteMaximo.value = document.forms["formaConsultaMonitoreoOperaciones"].txtImporteMaximo1.value;
}
function consultaSucursalesOperaciones() {
    if (document.getElementById("optCorresponsal1").value == 0) {
        document.getElementById("optCorresponsal1").value = document.getElementById("comboValor").value;
        jAlert('Seleccione un Corresponsal',     //DESCRIPCION ERROR
            'Monitoreo operaciones', 			 //TITULO ERROR
            '---',                               //CODIGO ERROR
            '');                                 //TIP PARA QUE NO SUCEDA EL ERROR	

    } else {

        llenaDatos();
        document.forms["formaConsultaMonitoreoOperaciones"].action = "MonitoreoOperacionesConsultaSucursalesOperaciones.do";
        document.forms["formaConsultaMonitoreoOperaciones"].submit();
    }

}
function consultaMonitoreoOperaciones(avanzarRetroceder) {
    llenaDatos();
    document.forms["formaConsultaMonitoreoOperaciones"].opcAvanzarRetroceder.value = avanzarRetroceder;
    if (validaCombo(document.getElementById("optCorresponsal").value, "Corresponsal"))
        return;

    if (validaHora(document.getElementById("txtHoraInicio").value, "Hora inicio", "Monitoreo de Operaciones"))
        return;
    if (validaHora(document.getElementById("txtHoraFin").value, "Hora fin", "Monitoreo de Operaciones"))
        return;
    if (document.getElementById("txtImporteMinimo").value != '' && validaImporte(document.getElementById("txtImporteMinimo").value, "Importe inicio", "Monitoreo de Operaciones"))
        return;
    if (document.getElementById("txtImporteMaximo").value != '' && validaImporte(document.getElementById("txtImporteMaximo").value, "Importe fin", "Monitoreo de Operaciones"))
        return;
    document.forms["formaConsultaMonitoreoOperaciones"].action = "RealizaConsultaMonitoreoOperaciones.do";
    document.forms["formaConsultaMonitoreoOperaciones"].submit();
}

// Modificado CEC Filtro por sucursal
function consultaSucursalesFiltro(letras) {
    jAlert('Seleccione un Corresponsal',    //DESCRIPCION ERROR
        letras, 						    //TITULO ERROR
        '',                                 //CODIGO ERROR
        '');  							    //TIP PARA QUE NO SUCEDA EL ERROR
    if (document.getElementById("optCorresponsal1").value == 0) {
        document.getElementById("optCorresponsal1").value = document.getElementById("comboValor").value;

    } else {
        llenaDatos();
        document.forms["formaConsultaMonitoreoOperaciones"].action = "MonitoreoOperacionesConsultaSucursalesOperaciones.do";
        document.forms["formaConsultaMonitoreoOperaciones"].submit();
    }
}



function cerrar() {
    document.forms["formaConsultaMonitoreoOperaciones"].action = "../principal/inicio.do?login=no";
    document.forms["formaConsultaMonitoreoOperaciones"].submit();

}
function limpiar() {
    llenaDatos();
    document.forms["formaConsultaMonitoreoOperaciones"].action = "MonitoreoOperaciones.do";
    document.forms["formaConsultaMonitoreoOperaciones"].submit();
}
function exportar() {
    llenaDatos();
    document.forms["formaConsultaMonitoreoOperaciones"].action = "ExportarConsultaMonitoreoOperaciones.do";
    document.forms["formaConsultaMonitoreoOperaciones"].submit();
}

function validaCombo(valor, campo) {
    if (valor == "0") {
        jError('Error en el tipo de datos, por favor verifique la información del campo : ' + campo,
            'Monitoreo de Operaciones',
            'OBLICAMP',
            '');

        return true;
    } else
        return false;
}