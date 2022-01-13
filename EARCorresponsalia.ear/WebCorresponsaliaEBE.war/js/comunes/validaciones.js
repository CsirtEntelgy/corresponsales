
function esCampoAlfanumerico(campo) {
	var RegExPattern = /\w/;
	
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


function validaNumeros(valor, campo, lonMin, lonMax, modulo){
    var regexp= new RegExp('^[0-9]{' + lonMin + ',' + lonMax + '}$');

    if(!regexp.test(valor)){
    	jAlert('Error en el tipo de datos, por favor verifique la informaci\u00f3n del campo : '+campo,                               //DESCRIPCION ERROR
		   	   	   modulo, 						//TITULO ERROR
		   	   	   '---',                               //CODIGO ERROR
		   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR	
        //alert();
        return true;
    }else
        return false;
}


function validaCadena(valor, campo, lonMin, lonMax, modulo){
    var regexp= new RegExp('^[a-zA-Z0-9.,-/| ]{' + lonMin + ',' + lonMax + '}$');

    if(!regexp.test(valor)){        
    	jAlert('Error en el tipo de datos, por favor verifique la informaci\u00f3n del campo : '+campo,                               //DESCRIPCION ERROR
    			modulo, 						//TITULO ERROR
		   	   	   '---',                               //CODIGO ERROR
		   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR	
        
        return true;
    }else
        return false;
}


function validaSoloLetras(valor, campo, lonMin, lonMax, modulo){
    var regexp= new RegExp('^[a-zA-Z| ]{' + lonMin + ',' + lonMax + '}$');

    if(!regexp.test(valor)){        
    	jAlert('Error en el tipo de datos, por favor verifique la informaci\u00f3n del campo : '+campo,                               //DESCRIPCION ERROR
    			modulo, 						//TITULO ERROR
		   	   	   '---',                               //CODIGO ERROR
		   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR	
        
        return true;
    }else
        return false;
}

function validaCorreo(valor, campo, longitud, modulo){
    var regexp= new RegExp('^[^@]+@[^@]+[.][^@]+$');
    //var regexp= new RegExp('\b[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b');

	if (valor.length<=longitud) {
	    if(!regexp.test(valor)){
    	jAlert('Error en el formato de correo, por favor verifique la informaci\u00f3n del campo : '+campo,                               //DESCRIPCION ERROR
    			modulo, 						//TITULO ERROR
		   	   	   '---',                               //CODIGO ERROR
		   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR	
	    
	        return true;
	    }else
	        return false;
    } else {
    	jAlert('Formato de correo incorrecto, por favor verifique la informaci\u00f3n del campo : '+campo,                               //DESCRIPCION ERROR
    			modulo, 						//TITULO ERROR
		   	   	   '---',                               //CODIGO ERROR
		   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR	
	    return true;
    }
    	
}

function validaImporte(valor, campo, modulo){
	//var regexp= new RegExp('^[0-9]{1,13}$');
	var regexp= new RegExp('^([^a-zA-Z]|[^a-zA-Z]([0-9]{1,13})|([0-9]{1,13})+(.[0-9]{2}))$');
	
		if(!regexp.test(valor)){
	    	jAlert('Error en el tipo de datos, por favor verifique la informaci\u00f3n del campo : '+campo,                               //DESCRIPCION ERROR
	    			modulo, 						//TITULO ERROR
			   	   	   '---',                               //CODIGO ERROR
			   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR	
	        return true;
	    }else
	        return false;	
}

function validaCuenta(valor, campo, modulo){
    var regexp1= new RegExp('^[0-9]{11}$');
    var regexp2= new RegExp('^[0-9]{20}$');

    if(!regexp1.test(valor)){
        if(!regexp2.test(valor)){
	    	jAlert('Error en el tipo de datos, por favor verifique la informaci\u00f3n del campo : '+campo,                               //DESCRIPCION ERROR
	    			modulo, 						//TITULO ERROR
			   	   	   '---',                               //CODIGO ERROR
			   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR	
	        return true;
	    }else {
	        return false;
	    }
    }else {
	    return false;
    }
}

function validaHora(valor, campo, modulo){
	var regexp = new RegExp('^([0-2]?[0-3]|0[0-9]|1[4-9]):([0-5]{1})+([0-9]{1})$');
    //var regexp = new RegExp('^([0-2]{1})+([0-9]{1}):([0-5]{1})+([0-9]{1})$');

    if(!regexp.test(valor)){
    	jAlert('Error en el tipo de datos, por favor verifique la informaci\u00f3n del campo : '+campo,                               //DESCRIPCION ERROR
    			modulo, 						//TITULO ERROR
		   	   	   '---',                               //CODIGO ERROR
		   	       'Formato Valido XX:XX ie: 08:00');                                         		//TIP PARA QUE NO SUCEDA EL ERROR	
        return true;
    }else
        return false;
}
function validaCadenaTrx(valor, campo, modulo){
    if(valor.length != 4){
    	jAlert('Error en el tipo de datos, por favor verifique la informaci\u00f3n del campo : '+campo,                               //DESCRIPCION ERROR
    			modulo, 						//TITULO ERROR
		   	   	   '---',                               //CODIGO ERROR
		   	       '');                                         		//TIP PARA QUE NO SUCEDA EL ERROR	

        return true;
    }else
        return false;
}
