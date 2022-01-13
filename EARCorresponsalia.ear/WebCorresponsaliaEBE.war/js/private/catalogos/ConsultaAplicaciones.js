function reset(){
		document.getElementById('txtIdAplicacion').value = "";
		document.getElementById('txtDescripcion').value = "";
		
    	document.forms["formCatalogoAplicaciones"].action = "CatalogoConsultaAplicaciones.do";
    	document.forms["formCatalogoAplicaciones"].submit();
	}

	/** function validaCamposObligatorios(){
		var camposOk = true;
		if(document.forms["formConsultaBitacora"].optCorresponsal.value=="sinsel"){
			camposOk = false;
    		jAlert('Debe de seleccionar un corresponsal', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitácora',          //TITULO ERROR
		   	       'CAPSI001',                            //CODIGO ERROR
		   	       '');  
		}
		else if(document.forms["formConsultaBitacora"].optSucursal.value=="sinsel"){
			camposOk = false;
    		jAlert('Debe de seleccionar una sucursal', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitácora',          //TITULO ERROR
		   	       'CAPSI001',                            //CODIGO ERROR
		   	       '');  
		}	
		else if(document.forms["formConsultaBitacora"].txtFecha.value==""){
			camposOk = false;
    		jAlert('Debe informar la fecha', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitácora',          //TITULO ERROR
		   	       'CAPSI002',                            //CODIGO ERROR
		   	       '');  
		}
		else if(!validaReferenciaInterna()){
			camposOk = false;
    		jAlert('El campo Referencia Interna es incorrecto', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitácora',          //TITULO ERROR
		   	       'CAPSI003',                            //CODIGO ERROR
		   	       'El campo Referencia Interna debe de ser alfanumerico');  
		}
		else if(!validaFolio()){
			camposOk = false;
    		jAlert('El campo Folio es incorrecto', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitácora',          //TITULO ERROR
		   	       'CAPSI004',                            //CODIGO ERROR
		   	       'El campo Folio debe de ser alfanum&eacute;rico');  
		}
		else if(!validaCuentaTarjeta()){
			camposOk = false;
    		jAlert('El campo Cuenta/Tarjeta es incorrecto', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitácora',          //TITULO ERROR
		   	       'CAPSI005',                            //CODIGO ERROR
		   	       'El campo Cuenta/Tarjeta de ser num&eacute;rico \n Con longitud de 11, 16 o 20 digitos');  
		}
		else if(document.forms["formConsultaBitacora"].txtImporte.value!= "" && !validaImporte()){
			camposOk = false;
    		jAlert('El campo Importe es incorrecto', //DESCRIPCION ERROR
		   	   	   'Error en Consulta Bitácora',          //TITULO ERROR
		   	       'CAPSI006',                            //CODIGO ERROR
		   	       'El campo Importe de ser entero o decimal (no más de dos decimales)');  
		}
		
		
		return camposOk;
	} **/
	
	function consultar(){
		//if(validaCamposObligatorios()){
	    	//document.forms["formConsultaBitacora"].opcAvanzarRetroceder.value=avanzarRetroceder; 
    		document.forms["formCatalogoAplicaciones"].action = "CatalogoConsultaAplicaciones.do";
    		document.forms["formCatalogoAplicaciones"].submit();
    	//}else{
    	  //  document.forms["formConsultaBitacora"].optCorresponsal.value = comboVal.value;
    	//}	    	
	}
	
	function editar(idApp, desc){
		document.getElementById('txtIdAplicacion').value = idApp;
		document.getElementById('txtDescripcion').value = desc;
		document.getElementById('auxIdAplicacion').value = idApp; 
		document.getElementById('txtIdAplicacion').readOnly = true;
	}
	
	function guardar(){
			//almacenar();
			var campoAplicacion = document.getElementById('txtIdAplicacion').value;
			var campoDescripcion = document.getElementById('txtDescripcion').value;
			
			if(campoAplicacion != null & campoAplicacion != ""){
				document.forms["formCatalogoAplicaciones"].action = "RevisandoRegistroAplicaciones.do";
				document.forms["formCatalogoAplicaciones"].submit();
			}
			
			/**var auxAplicacion = document.getElementById('hdnIdAplicacion').value;
			
			if(auxAplicacion != '' && auxAplicacion != null){
				document.forms["formCatalogoAplicaciones"].action = "ModificaRegistroCatalogoAplicaciones.do";
				if(campoAplicacion != '' || campoDescripcion != ''){
					jConfirm("¿Seguro que desea modificar el registro?","Modificar Registro","","",function(e){
						if(e){
	    					document.forms["formCatalogoAplicaciones"].submit();
						}
						return e;
					});
				}
			}
			else{
				document.forms["formCatalogoAplicaciones"].action = "AltaRegistroCatalogoAplicaciones.do";
				document.forms["formCatalogoAplicaciones"].submit();
			}**/
	}
		
			
	function anular(numReg){
			jConfirm("¿Está seguro que desea eliminar el registro?","Eliminar Registro","","",function(e){
					if(e){
	    				document.forms["formCatalogoAplicaciones"].regAnular.value=numReg; 
     					document.forms["formCatalogoAplicaciones"].action = "EliminarRegistroCatalogoAplicaciones.do";  	
    					document.forms["formCatalogoAplicaciones"].submit();
					}
					return e;
				});
	}

	function cerrar(){
		document.getElementById('tFrameTable').style.display = 'none';
    	document.forms["formCatalogoAplicaciones"].action = "../principal/inicio.do";
    	document.forms["formCatalogoAplicaciones"].submit();
	}
	
	function almacenar(){
		var campoAplicacion = document.getElementById('txtIdAplicacion').value;
		var campoDescripcion = document.getElementById('txtDescripcion').value;
	
		document.getElementById('hdnIdAplicacion').value = campoAplicacion;
		document.getElementById('hdnDescripcion').value = campoDescripcion;
	}