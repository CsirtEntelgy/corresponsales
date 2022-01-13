$(document).ready(
    function () {
        console.log("Descargando");
        var file = $("#textFile").val();
        var codigoSucursal = $("#codigoSucursal").val();
        var nombreArchivo = codigoSucursal + "_ope_por_comp.txt";
        console.log(file);

        if (file !== null && file !== "null") {
            if (file !== "") {
                download(nombreArchivo, file);
            } else {
                jAlert('No se encontro el archivo.',   //DESCRIPCION ERROR
                    'Lotes por compensar', 		       //TITULO ERROR
                    '---',                             //CODIGO ERROR
                    '');                               //TIP PARA QUE NO SUCEDA EL ERROR
            }
        }

    }
);

function download(filename, text) {
    var element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
    element.setAttribute('download', filename);

    element.style.display = 'none';
    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element);
}


function llenaDatos() {
    document.forms["formaLotesPorCompensar"].optCorresponsal.value = document.forms["formaLotesPorCompensar"].optCorresponsal1.value;
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
        document.getElementById("operacion").value = "consultaLotesPorCompensar";
        document.forms["formaLotesPorCompensar"].action = "consultaLotesPorCompensar.do";
        document.forms["formaLotesPorCompensar"].submit();
    }

}
function consultaMonitoreoOperaciones(avanzarRetroceder) {
    llenaDatos();
    document.forms["formaLotesPorCompensar"].opcAvanzarRetroceder.value = avanzarRetroceder;
    if (validaCombo(document.getElementById("optCorresponsal").value, "Corresponsal"))
        return;
    document.forms["formaLotesPorCompensar"].action = "RealizaConsultaMonitoreoOperaciones.do";
    document.forms["formaLotesPorCompensar"].submit();
}

function cerrar() {
    document.forms["formaLotesPorCompensar"].action = "../principal/inicio.do?login=no";
    document.forms["formaLotesPorCompensar"].submit();

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