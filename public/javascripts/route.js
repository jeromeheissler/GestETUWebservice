$(document).ready(function(){
	state = window.history.pushState !== undefined;
	$.address.state("/dashboard").init(function(event) {
		$('a').address();
	}).change(function(event){
/*		console.info("**********");
		console.info(event.value);
		console.info(event.pathNames[0]);
		console.info(event.pathNames[1]);*/
		if(event.value != "/") {
			if(event.pathNames[0] == "promotion"){
				loadPromotion();				
			}else if(event.pathNames[0] == "student"){
				loadStudent();
				if(event.pathNames[1] == "search")	{
					searchStudent();
				}else if(event.pathNames[1] == "add")	{
					addStudent();
				}else if(event.pathNames[1] != undefined)	{
					loadOneStudent(event.pathNames[1]);
				}else	{
					allStudent();
				}
			}else if(event.pathNames[0] == "logout"){
				window.location = "/logout";
			}
		}
		return null;
	}); 
});
