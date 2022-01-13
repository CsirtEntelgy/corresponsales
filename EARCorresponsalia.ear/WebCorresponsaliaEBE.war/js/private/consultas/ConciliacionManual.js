// Alta
function enviaArchivo() {
    if (document.getElementById("fbArchivo").value == "") {
        jAlert('No se ha seleccionado el archivo.',
            'Error en la carga',
            '',
            ''
        );
    } else {
		var SES = document.getElementById("SES").value;		
        document.getElementById("operacion").value = "envioArchivo";
        document.forms["formaConciliacionManual"].action = "ConciliacionManual.do?SES=" + SES + "&operacion=envioArchivo";
        document.forms["formaConciliacionManual"].submit();
    }
}

function llenaDatos() {
    document.forms["formaConciliacionManual"].optCorresponsal.value = document.forms["formaConciliacionManual"].optCorresponsal1.value;
}
function consultaConciliacionManual() {
    if (document.getElementById("optCorresponsal1").value == 0) {
        document.getElementById("optCorresponsal1").value = document.getElementById("comboValor").value;
        jAlert('Seleccione un Corresponsal',    //DESCRIPCION ERROR
            'Monitoreo operaciones', 			//TITULO ERROR
            '---',                              //CODIGO ERROR
            '');                                //TIP PARA QUE NO SUCEDA EL ERROR	

    } else {

        llenaDatos();
        document.getElementById("operacion").value = "consultaLotesPorCompensar";
        document.forms["formaConciliacionManual"].action = "consultaLotesPorCompensar.do";
        document.forms["formaConciliacionManual"].submit();
    }

}
function consultaMonitoreoOperaciones(avanzarRetroceder) {
    llenaDatos();
    document.forms["formaConciliacionManual"].opcAvanzarRetroceder.value = avanzarRetroceder;
    if (validaCombo(document.getElementById("optCorresponsal").value, "Corresponsal"))
        return;
    document.forms["formaConciliacionManual"].action = "RealizaConsultaMonitoreoOperaciones.do";
    document.forms["formaConciliacionManual"].submit();
}

function cerrar() {
    document.forms["formaConciliacionManual"].action = "../principal/inicio.do?login=no";
    document.forms["formaConciliacionManual"].submit();

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