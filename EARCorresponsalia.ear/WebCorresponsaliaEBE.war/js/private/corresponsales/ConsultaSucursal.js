function trim(stringToTrim) {
    return stringToTrim.replace(/^\s+|\s+$/g, "");
}

function consultaSucursales(accionPagina) {
    var comboSucursal = document.forms["formaConsultaSucursales"].cmbNombreCorresponsal;

    if (comboSucursal.options[comboSucursal.selectedIndex].value != '0') {
        var intPagina = 1;
        intPagina = parseInt(document.forms['formaConsultaSucursales'].paginaNum.value);
        if (accionPagina == 'A') {
            intPagina++;
        } else
            if (accionPagina == 'B') {
                intPagina--;
            }
        document.forms['formaConsultaSucursales'].paginaNum.value = '' + intPagina;
        document.forms["formaConsultaSucursales"].adelanteAtras.value = accionPagina;
        document.forms["formaConsultaSucursales"].action = "ConsultaSucursal.do";
        document.forms["formaConsultaSucursales"].submit();
    } else {
        comboSucursal.value = document.forms["formaConsultaSucursales"].sucursalSel.value;
        jAlert('Seleccione un Corresponsal',     //DESCRIPCION ERROR
            'Sucursales', 						 //TITULO ERROR
            '---',                               //CODIGO ERROR
            '');                                 //TIP PARA QUE NO SUCEDA EL ERROR	

    }
}

function detalleSucursal(registroSel) {
    document.forms["formaConsultaSucursales"].sucursalSel.value = registroSel;
    document.forms["formaConsultaSucursales"].action = "DetalleSucursal.do";
    document.forms["formaConsultaSucursales"].submit();
}

function activarDesactivar(actDesReg, estatus) {
    document.forms["formaConsultaSucursales"].regSel.value = actDesReg;
    document.forms["formaConsultaSucursales"].estadoActual.value = estatus;
    document.forms["formaConsultaSucursales"].action = "ActivarDesactivarSucursal.do";
    document.forms["formaConsultaSucursales"].submit();
}

function modificarSucursal(registroSel) {
    document.forms["formaConsultaSucursales"].sucursalSel.value = registroSel;
    document.forms["formaConsultaSucursales"].action = "ModificarSucursal.do";
    document.forms["formaConsultaSucursales"].submit();
}

function eliminarSucursal(registroSel) {
    document.forms["formaConsultaSucursales"].sucursalSel.value = registroSel;
    jConfirm('¿Desea eliminar la sucursal?',
        'Eliminar sucursal',
        'ELIMSUC',
        '', function (e) {
            if (e) {
                document.forms["formaConsultaSucursales"].action = "EliminarSucursal.do";
                document.forms["formaConsultaSucursales"].submit();
            }
            return e;
        });

}

function altaSucursal() {
    var comboSucursal = document.forms["formaConsultaSucursales"].cmbNombreCorresponsal;
    if (comboSucursal.options[comboSucursal.selectedIndex].value != '0') {
        document.forms["formaConsultaSucursales"].action = "AltaSucursal.do";
        document.forms["formaConsultaSucursales"].submit();
    } else {
        jAlert('Seleccione un Corresponsal',     //DESCRIPCION ERROR
            'Sucursales', 						 //TITULO ERROR
            '---',                               //CODIGO ERROR
            '');                                 //TIP PARA QUE NO SUCEDA EL ERROR	

    }

}

function cerrar() {
    document.forms["formaConsultaSucursales"].action = "MuestraCorresponsalesSucursales.do";
    document.forms["formaConsultaSucursales"].submit();
}

function exportar() {
    document.forms["formaConsultaSucursales"].action = "ExportaSucursales.do";
    document.forms["formaConsultaSucursales"].submit();
}