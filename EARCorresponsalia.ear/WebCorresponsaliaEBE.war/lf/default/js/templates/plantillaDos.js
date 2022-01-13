function ini(){
	createCalendar('startDate','cal');
	createCalendar('endDate','cal2');
}

function hideDivsTabs() {
	var divOneTabElement = document.getElementById("divOneTab");
	var divTwoTabElement = document.getElementById("divTwoTab");
	var divThreeTabElement = document.getElementById("divThreeTab");
	var divFourTabElement = document.getElementById("divFourTab");
	var divFiveTabElement = document.getElementById("divFiveTab");
	divOneTabElement.style.display = "none";
	divTwoTabElement.style.display = "none";
	divThreeTabElement.style.display = "none";
	divFourTabElement.style.display = "none";
	divFiveTabElement.style.display = "none";
}
