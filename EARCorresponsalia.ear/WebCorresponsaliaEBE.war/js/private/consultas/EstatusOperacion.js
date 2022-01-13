function esCampoAlfanumerico(campo) {
	//var RegExPattern = /\w/;
	var RegExPattern = new RegExp('^[a-zA-Z0-9| ]{1,20}$');
	
	  if(RegExPattern.test(campo)) 
	    return true;
	    
    return false;
}

	function esCampoEntero(campo) {	
		var RegExPattern = /^(?:\+|-)?\d+$/;		
	    if (RegExPattern.test(campo)) 
	    	return true;	    
	    return false;
	}
	function validaDecimales(campo) {
		if (campo.indexOf(".") >= 0) {
			if ((campo.length - (campo.indexOf(".") + 1)) > 2)
				return false;
		}	    
	    return true;
	}
	
	function obtenerOperacionesSucursales(){
		if(document.forms["formConsultaEstatusOperacion"].optCorresponsal.value!="sinsel"){
    		document.forms["formConsultaEstatusOperacion"].action = "EstatusOperacionConsultaOperacionesSucursales.do";
    		document.forms["formConsultaEstatusOperacion"].submit();
    	}else{
    		document.forms["formConsultaEstatusOperacion"].optCorresponsal.value = document.forms["formConsultaEstatusOperacion"].comVal.value;    		
        	jAlert('Seleccione un Corresponsal',                               //DESCRIPCION ERROR
		   	   	   'Bitacora', 						//TITULO ERROR
		   	   	   '---',                               //CODIGO ERROR
		   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR	
	
    		
    	}
	}
	
	function reset(){
		/*
		document.forms["formConsultaEstatusOperacion"].txtFecha.value="";
		document.forms["formConsultaEstatusOperacion"].txtFolio.value="";
		document.forms["formConsultaEstatusOperacion"].txtCuentaTarjeta.value="";
    	document.forms["formConsultaEstatusOperacion"].txtImporte.value="";
    	*/
    	document.forms["formConsultaEstatusOperacion"].action = "EstatusOperacionConsultaCorresponsales.do";
    	document.forms["formConsultaEstatusOperacion"].submit();
	}

	
	function validaImporte(){
	//var regexp= new RegExp('^[0-9]{1,13}$');
	var regexp= new RegExp('^([^a-zA-Z]|[^a-zA-Z]([0-9]{1,13})|([0-9]{1,13})+(.[0-9]{2}))$');
	
		if(!regexp.test(document.forms["formConsultaEstatusOperacion"].txtImporte.value)){
	        return false;
	    }else
	        return true;	
		return true;
	}

	function validaFolio(){
		var valOk = true;
		var campo = document.forms["formConsultaEstatusOperacion"].txtFolio.value;
		if(campo!=""){
			if(!esCampoAlfanumerico(campo))
				return false;
		}
		
		return true;
	}

	function validaCuentaTarjeta(){
		var valOk = true;
		var campo = document.forms["formConsultaEstatusOperacion"].txtCuentaTarjeta.value;
		if(campo!=""){
			if(!esCampoEntero(campo))
				return false;
			if(campo.length!=10 && campo.length!=11 && campo.length!=16 && campo.length!=20)
				return false;
		}
		
		return true;
	}

	function validaCamposObligatorios(){
		var camposOk = true;
		if(document.forms["formConsultaEstatusOperacion"].optCorresponsal.value=="sinsel"){
			camposOk = false;
    		jAlert('Debe de seleccionar un corresponsal', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Estatus Operacion',          //TITULO ERROR
		   	       'CAPSI001',                            //CODIGO ERROR
		   	       '');  
		}
		else if(document.forms["formConsultaEstatusOperacion"].optSucursal.value=="sinsel"){
			camposOk = false;
    		jAlert('Debe de seleccionar una sucursal', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Estatus Operacion',          //TITULO ERROR
		   	       'CAPSI001',                            //CODIGO ERROR
		   	       '');  
		}	
		else if(document.forms["formConsultaEstatusOperacion"].txtFecha.value==""){
			camposOk = false;
    		jAlert('Debe informar la fecha', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Estatus Operacion',          //TITULO ERROR
		   	       'CAPSI002',                            //CODIGO ERROR
		   	       '');  
		}
		else if(!validaFolio()){
			camposOk = false;
    		jAlert('El campo Folio es incorrecto', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Estatus Operacion',          //TITULO ERROR
		   	       'CAPSI004',                            //CODIGO ERROR
		   	       'El campo Folio debe de ser alfanum&eacute;rico');  
		}
		else if(!validaCuentaTarjeta()){
			camposOk = false;
    		jAlert('El campo Cuenta/Tarjeta es incorrecto', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Estatus Operacion',          //TITULO ERROR
		   	       'CAPSI005',                            //CODIGO ERROR
		   	       'El campo Cuenta/Tarjeta de ser num&eacute;rico \n Con longitud de 11, 16 o 20 digitos');  
		}
		else if(document.forms["formConsultaEstatusOperacion"].txtImporte.value!= "" && !validaImporte()){
			camposOk = false;
    		jAlert('El campo Importe es incorrecto', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Estatus Operacion',          //TITULO ERROR
		   	       'CAPSI006',                            //CODIGO ERROR
		   	       'El campo Importe de ser entero o decimal (no más de dos decimales)');  
		}
		
		
		return camposOk;
	}
	
	function guardaCombo(){		
	    comboVal.value = document.forms["formConsultaEstatusOperacion"].optCorresponsal.value;
	}
			
	function consultar(avanzarRetroceder){
		if(validaCamposObligatorios()){
	    	document.forms["formConsultaEstatusOperacion"].opcAvanzarRetroceder.value=avanzarRetroceder; 
    		document.forms["formConsultaEstatusOperacion"].action = "RealizaConsultaEstatusOperacion.do";
    		document.forms["formConsultaEstatusOperacion"].submit();
    	}else{
    	    document.forms["formConsultaEstatusOperacion"].optCorresponsal.value = comboVal.value;
    	}	    	
	}

		
			
	function anular(numReg){
			jConfirm("Seguro que desea eliminar el registro?","Eliminar Registro","","",function(e){
					if(e){
	    				document.forms["formConsultaEstatusOperacion"].regAnular.value=numReg; 
     					document.forms["formConsultaEstatusOperacion"].action = "EstatusOperacionAnular.do";  	
    					document.forms["formConsultaEstatusOperacion"].submit();
					}
					return e;
				});
	}

	function exportar(){
    	document.forms["formConsultaEstatusOperacion"].action = "ExportarEstatusOperacion.do";
    	document.forms["formConsultaEstatusOperacion"].submit();
	}
	
	function ini(){
		createCalendar('txtFecha','calBusqueda');
	}

	function cerrar(){
		document.getElementById('tFrameTable').style.display = 'none';
    	document.forms["formConsultaEstatusOperacion"].action = "../principal/inicio.do";
    	document.forms["formConsultaEstatusOperacion"].submit();
	}
