$(document).ready(
    function () {
    var a = localStorage.getItem("select");
        if(a.toLowerCase() !=="sinsel"){       
        	console.log(a);    	
    		$("#optCorresponsal").val(a);
    		localStorage.setItem("select","sinsel");
    	}
        console.log("Descargando");
        var file = $("#archivoTexto").val();
        var codigoSucursal = $("#codSucursal").val();
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
    localStorage.setItem("select", "sinsel");
}


function obtenerDetalle() {
    document.forms["formConsultaCompensacion"].action = "ObtenerDetalle.do";
    document.forms["formConsultaCompensacion"].submit();    
    console.log($("#optCorresponsal").val());
    localStorage.setItem("select",$("#optCorresponsal").val());
}
