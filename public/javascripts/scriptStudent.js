

function loadStudent()	{
	$("#menu li").removeClass("active");
	$("#student").addClass("active");
	
	$("#content").html('<ul id="nav-student" class="nav nav-tabs">'+
			  '<li id="all">'+
			  ' <a href="javascript:allStudent()">All</a>'+
			  '</li>'+
			  '<li id="search"><a href="javascript:searchStudent()">Search</a></li>'+
			  '<li id="add"><a href="javascript:addStudent()">Add</a></li>'+
			'</ul>');
	$('<div/>', {
		'id': 'studentContent'
	}).appendTo('#content');
	allStudent();
}

function allStudent()	{
	$("#nav-student li").removeClass("active");
	$("#all").addClass("active");
	$.getJSON("/student", function(data)	{
		var items = [];
		items.push('<tr><th>Fistname</th><th>LastName</th><th></th></tr>');  
 
		$.each(data, function(key, val) {
		    items.push('<tr id="' + val.id + '"><td>'+val.firstname+'</td><td>'+val.lastname+'</td><td><div class="btn-group"><button onclick="editPromo(\''+val.id+'\')"  class="btn">Edit</button><button onclick="delPromo(\''+val.id+'\')" class="btn btn-danger">Delete</button></div></td></tr>');
		});
		
		$('#studentContent').html($('<table/>', {
		    'class': 'table',
		    'id' : 'studentTable',
		    html: items.join('')
		}));
	});
}


function searchStudent()	{
	$("#nav-student li").removeClass("active");
	$("#search").addClass("active");
	
	$.getJSON("/student", function(data)	{
		var datasource = [];
		datasource.push("jerome");
		datasource.push("toto");
		
		$.each(data, function(key, val) {
			datasource.push(val);
		});
		
		$("#studentContent").html($('<input/>', {
			'type': 'text',
			'class': 'span3',
			'style': 'margin: 0 auto',
			'data-provide': 'typeahead',
			'data-items':'10',
			'data-source': "[\""+datasource.join('","')+"\"]"
		}));
	});
}

function addStudent()	{
	$("#nav-student li").removeClass("active");
	$("#add").addClass("active");
	$.get("/studentAdd", function(data)	{
		$("#studentContent").html(data);
	});
	
}


