function reset(){
		document.getElementById('txtIdAplicacion').value = "";
		document.getElementById('txtCodigoOperacion').value = "";
		document.getElementById('txtDescOp').value = "";
		document.getElementById('txtCodigoISO').value = "";
		document.getElementById('txtDescISO').value = "";
		
    	document.forms["formCatalogoCodigosOperacion"].action = "CatalogoConsultaCodigosOperacion.do";
    	document.forms["formCatalogoCodigosOperacion"].submit();
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
    		document.forms["formCatalogoCodigosOperacion"].action = "CatalogoConsultaCodigosOperacion.do";
    		document.forms["formCatalogoCodigosOperacion"].submit();
    	//}else{
    	  //  document.forms["formConsultaBitacora"].optCorresponsal.value = comboVal.value;
    	//}	    	
	}
	
	function editar(idApp, codOp, descCod, codIso, descIso){
		document.getElementById('hdnIdAplicacion').value = idApp;
		document.getElementById('hdnCodigoOperacion').value = codOp;
		document.getElementById('hdnDescCodigoOp').value = descCod; 
		document.getElementById('hdnCodigoISO').value = codIso;
		document.getElementById('hdnDescISO').value = descIso;
		
		document.getElementById('txtIdAplicacion').value = idApp;
		document.getElementById('txtCodigoOperacion').value = codOp;
		document.getElementById('txtDescOp').value = descCod; 
		document.getElementById('txtCodigoISO').value = codIso;
		document.getElementById('txtDescISO').value = descIso;
		
		document.getElementById('txtIdAplicacion').readOnly = true;
		document.getElementById('txtCodigoOperacion').readOnly = true;
		document.getElementById('txtCodigoISO').readOnly = true;
	}
	
	function guardar(){
			var campoAplicacion = document.getElementById('txtIdAplicacion').value;
			var campoCampoOperacion = document.getElementById('txtCodigoOperacion').value;
			var campoDescOperacion = document.getElementById('txtDescOp').value;
			var campoCodigoISO = document.getElementById('txtCodigoISO').value;
			var campoDescISO = document.getElementById('txtDescISO').value;
			
			document.getElementById('hdnIdAplicacion').value = campoAplicacion;
			document.getElementById('hdnCodigoOperacion').value = campoCampoOperacion;
			document.getElementById('hdnDescCodigoOp').value = campoDescOperacion; 
			document.getElementById('hdnCodigoISO').value = campoCodigoISO;
			document.getElementById('hdnDescISO').value = campoDescISO;
			
			var auxAplicacion = document.getElementById('hdnIdAplicacion').value;
			var auxCodigoOperacion = document.getElementById('hdnCodigoOperacion').value;
			var auxCodigoISO = document.getElementById('hdnCodigoISO').value;
			
			if(campoAplicacion != null & campoAplicacion != ""){
				document.forms["formCatalogoCodigosOperacion"].action = "RevisandoRegistroCodigosOperacion.do";
				document.forms["formCatalogoCodigosOperacion"].submit();
			}
	}
		
			
	function anular(idApp, codOper, codISO ){
			jConfirm("¿Está seguro que desea eliminar el registro?","Eliminar Registro","","",function(e){
					if(e){
	    				document.forms["formCatalogoCodigosOperacion"].regAnularApp.value=idApp;
	    				document.forms["formCatalogoCodigosOperacion"].regAnularOper.value=codOper;
	    				document.forms["formCatalogoCodigosOperacion"].regAnularISO.value=codISO; 
     					document.forms["formCatalogoCodigosOperacion"].action = "EliminarRegistroCatalogoCodOperacion.do";  	
    					document.forms["formCatalogoCodigosOperacion"].submit();
					}
					return e;
				});
	}

	function cerrar(){
		document.getElementById('tFrameTable').style.display = 'none';
    	document.forms["formCatalogoCodigosOperacion"].action = "../principal/inicio.do";
    	document.forms["formCatalogoCodigosOperacion"].submit();
	}