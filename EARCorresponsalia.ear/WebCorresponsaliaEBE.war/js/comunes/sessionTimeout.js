var secs = 0;
var timerID = null;
var timerRunning = false;
var delay = 480;

function InitializeTimer(timeSession){
    secs = timeSession;
    StopTheClock();
    StartTheTimer();
}

function StopTheClock(){
    if(timerRunning){
        clearTimeout(timerID);
    	timerRunning = false;
    }
}
	
function StartTheTimer(){
    if (secs==240){
    	jAlert("Su sesi\363n terminar\341 en 2 minutos, para continuar es necesario que haga clic en alguna opci\363n del men\372","Sesi\363n", ["", ""] );
    }

    if(secs==0){
		StopTheClock();
		window.location = "../publico/salir.do";
    }else{
        secs = secs - 1;
        timerRunning = true;
        timerID = self.setTimeout("StartTheTimer()", delay);
    }
}