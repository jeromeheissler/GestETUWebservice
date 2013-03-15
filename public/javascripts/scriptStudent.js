

function loadStudent()	{
	$("#menu li").removeClass("active");
	$("#student").addClass("active");
	
	$("#content").html('<ul id="nav-student" class="nav nav-tabs">'+
			  '<li id="all">'+
			  ' <a href="/dashboard/student">All</a>'+
			  '</li>'+
			  '<li id="search"><a href="/dashboard/student/search">Search</a></li>'+
			  '<li id="add"><a href="/dashboard/student/add">Add</a></li>'+
			'</ul>');
	$('<div/>', {
		'id': 'studentContent'
	}).appendTo('#content');
}

function loadOneStudent(id)	{
	$.get("/loadOneStudent/"+id, function(data)	{
		$("#studentContent").html(data);
	});
}

function allStudent()	{
	$("#nav-student li").removeClass("active");
	$("#all").addClass("active");
	$.getJSON("/student", function(data)	{
		var items = [];
		items.push('<tr><th>Fistname</th><th>LastName</th><th>Mail</th><th>Stu. Number</th><th>Promotion</th><th></th></tr>');  
 
		$.each(data, function(key, val) {
		    items.push('<tr id="' + val.id + '"><td>'+val.firstname+'</td><td>'+val.lastname+'</td><td>'+val.mail+'</td><td>'+val.numstu+'</td><td>'+val.promo+'</td><td><div class="btn-group"><button onclick="editStudent(\''+val.id+'\')"  class="btn">Edit</button><button onclick="delStudent(\''+val.id+'\')" class="btn btn-danger">Delete</button><button onclick="viewStudent(\''+val.id+'\')" class="btn btn-success">View</button></div><div></div</td></tr>');
		});
		
		$('#studentContent').html($('<table/>', {
		    'class': 'table',
		    'id' : 'studentTable',
		    html: items.join('')
		}));
	});
}

function viewStudent(id)	{
	window.location = "/dashboard/student/"+id;
}


function searchStudent()	{
	$("#nav-student li").removeClass("active");
	$("#search").addClass("active");
	
	$("#studentContent").append('<form class="form-search" method="POST">');
	$("#studentContent form").html($('<input/>', {
		'id':'searchTypeHead',
		'type': 'text',
		'class': 'search-query input-xlarge',
		'style': 'margin: 0 auto',
		'autocomplete':'off'
	}));
	$("#studentContent form").append('<button id="gogobtn" type="submit" disabled="disabled" class="btn">Search</button>');
	
	$.getJSON("/student", function(data)	{
		students = [];
	    map = {};
		$.each(data, function(key, val) {
			map[val.firstname+" "+val.lastname] = val.id;
			students.push(val.firstname+" "+val.lastname);
		});	
		$('#searchTypeHead').typeahead({
			source: students,
			matcher: function (item) {
			    if (item.toLowerCase().indexOf(this.query.trim().toLowerCase()) != -1) {
			        return true;
			    }
			},
			updater: function (item) {
			    id = map[item];
			    $("#gogobtn").removeAttr("disabled");
			    $("#studentContent form").attr("action", "/dashboard/student/"+id);
			    return item;
			}
		});
	});
	
	
}

function addStudent()	{
	$("#nav-student li").removeClass("active");
	$("#add").addClass("active");
	$.get("/studentAdd", function(data)	{
		$("#studentContent").html(data);
	});	
}

function delStudent(id)	{
	var jqxhr = $.post("/delStudent", {"id":id } , function(data) {
		  if(data.state == "success")	{
			  $("#"+id).remove();
		  }else	{
			  $("#content").prepend('<div class="alert alert-error">'+
					  '<button type="button" class="close" data-dismiss="alert">&times;</button>'+
					  '<strong>Error!</strong> Something is wrong when we tried to validate the form.'+
					'</div>');
		  }
		}, "json").fail(function() { 
			$("#content").prepend('<div class="alert alert-error">'+
					  '<button type="button" class="close" data-dismiss="alert">&times;</button>'+
					  '<strong>Error!</strong> Something is wrong when we tried to validate the form.'+
					'</div>');
		});
} 

var saveFirstname = {};var saveLastName = {};var saveMail = {};var saveNumber = {};var saveBtn = {};var savePromo = {};
function editStudent(id)	{
	saveFirstname[id] = $($("#"+id+" td")[0]).text();
	saveLastName[id] = $($("#"+id+" td")[1]).text();
	saveMail[id] = $($("#"+id+" td")[2]).text();
	saveNumber[id] = $($("#"+id+" td")[3]).text();
	savePromo[id]  = $($("#"+id+" td")[4]).text();
	saveBtn[id] = $($("#"+id+" td")[5]).html();
	$($("#"+id+" td")[0]).html('<input id="inputfirstname" class="input-small" type="text" name="firstname" value="'+saveFirstname[id]+'">');
	$($("#"+id+" td")[1]).html('<input id="inputlastname" class="input-small" type="text" name="lastname" value="'+saveLastName[id]+'">');
	$($("#"+id+" td")[2]).html('<input id="inputmail" class="input-small" type="text" name="mail" value="'+saveMail[id]+'">');
	$($("#"+id+" td")[3]).html('<input id="inputnumber" class="input-small" type="text" name="number" value="'+saveNumber[id]+'">');
	$($("#"+id+" td")[5]).html('<div class="btn-group"><button onclick="saveStudent(\''+id+'\')"  class="btn btn-info">Save</button><button onclick="cancelStudent(\''+id+'\')" class="btn">Cancel</button></div>');
	$($("#"+id+" td")[4]).html();
}

function cancelStudent(id)	{
	$($("#"+id+" td")[0]).html(saveFirstname[id]);
	$($("#"+id+" td")[1]).html(saveLastName[id]);
	$($("#"+id+" td")[2]).html(saveMail[id]);
	$($("#"+id+" td")[3]).html(saveNumber[id]);
	$($("#"+id+" td")[4]).html(savePromo[id]);
	$($("#"+id+" td")[5]).html(saveBtn[id]);	
}

function saveStudent(id)	{
	var jqxhr = $.post("/editStudent", {"id":id, "firstname":$($("#"+id+" td input")[0]).val(), "lastname":$($("#"+id+" td input")[1]).val(), "mail":$($("#"+id+" td input")[2]).val(), "numstu":$($("#"+id+" td input")[3]).val() } , function(data) {
		  if(data.state == "success")	{
			  var str1 = $($("#"+id+" td input")[0]).val();
			  var str2 = $($("#"+id+" td input")[1]).val();
			  var str3 = $($("#"+id+" td input")[2]).val();
			  var str4 = $($("#"+id+" td input")[3]).val();
			  $($("#"+id+" td")[0]).html(str1);
			  $($("#"+id+" td")[1]).html(str2);
			  $($("#"+id+" td")[2]).html(str3);
			  $($("#"+id+" td")[3]).html(str4);
			  $($("#"+id+" td")[4]).html(savePromo[id]);
			  $($("#"+id+" td")[5]).html(saveBtn[id]);
		  }else	{
			  $("#content").prepend('<div class="alert alert-error">'+
					  '<button type="button" class="close" data-dismiss="alert">&times;</button>'+
					  '<strong>Error!</strong> Something is wrong when we tried to validate the form.'+
					'</div>');
		  }
		}, "json").fail(function() { 
			$("#content").prepend('<div class="alert alert-error">'+
					  '<button type="button" class="close" data-dismiss="alert">&times;</button>'+
					  '<strong>Error!</strong> Something is wrong when we tried to validate the form.'+
					'</div>');
		});
}


function editStudentModel () {
	var jqxhr = $.post("/editStudentModal", {"id":$("#idstu").val(), "firstname":$($("#myModal form input")[0]).val(), "lastname":$($("#myModal form input")[1]).val(), "numstu":$($("#myModal form input")[2]).val(), "mail":$($("#myModal form input")[3]).val(), "promo":$("#myModal form select option:selected").val() } , function(data) {
		  if(data.state == "success")	{
			  var strfirstname = $($("#myModal form input")[0]).val();
			  var strlastname = $($("#myModal form input")[1]).val();
			  var strnumstu = $($("#myModal form input")[2]).val();
			  var strpromo = $("#myModal form select option:selected").text();
			  $("#headerDescription h2").html(strnumstu+" - "+strfirstname+" "+strlastname);
			  $("#headerDescription h4").html(strpromo);
			  $('#myModal').modal('hide');
		  }else	{
			  $("#content").prepend('<div class="alert alert-error">'+
					  '<button type="button" class="close" data-dismiss="alert">&times;</button>'+
					  '<strong>Error!</strong> Something is wrong when we tried to validate the form.'+
					'</div>');
			  $('#myModal').modal('hide');
		  }
		}, "json").fail(function() { 
			$("#content").prepend('<div class="alert alert-error">'+
					  '<button type="button" class="close" data-dismiss="alert">&times;</button>'+
					  '<strong>Error!</strong> Something is wrong when we tried to validate the form.'+
					'</div>');
			$('#myModal').modal('hide');
		});
}
