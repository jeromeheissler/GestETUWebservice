function loadMark()	{
	$("#menu li").removeClass("active");
	$("#mark").addClass("active");
	
	$("#content").html('<ul id="nav-mark" class="nav nav-tabs">'+
			  '<li id="addOne">'+
			  ' <a href="/dashboard/mark/addone">add one mark</a>'+
			  '</li>'+
			  '<li id="import"><a href="/dashboard/mark/import">import CSV file</a></li>'+
			'</ul>');
	$('<div/>', {
		'id': 'markContent'
	}).appendTo('#content');
}

function addMark()	{
	
}

function importMark()	{
	
}