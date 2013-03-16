function loadSettings()	{
	$("#menu li").removeClass("active");
	$("#settings").addClass("active");
	
	$("#content").html('<ul id="nav-settings" class="nav nav-tabs">'+
			  '<li id="myinfo"><a href="/dashboard/settings">My Account</a></li>'+
			  '<li id="administration"><a href="/dashboard/settings/administration">Administration</a></li>'+
			'</ul>');
	$('<div/>', {
		'id': 'settings-content'
	}).appendTo('#content');
}

function loadAccount()	{
	$("#nav-settings li").removeClass("active");
	$("#myinfo").addClass("active");
	$.get("/account", function(data){
		$("#settings-content").html(data);
	});
}

function saveInfo()	{
	var jqxhr = $.post("/saveTeacher", {"mail":$("#inputEmail").val(), "lastname":$("#inputlastname").val(), "firstname":$("#inputfirstname").val()  } , function(data) {
		  if(data.state == "success")	{
			  $("#content").prepend('<div class="alert alert-info">'+
					  '<button type="button" class="close" data-dismiss="alert">&times;</button>'+
					  '<strong>ok !</strong> your info are updated.'+
					'</div>');
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

function changePassword()	{
	var jqxhr = $.post("/saveTeacher", {"inputpassword1":$("#inputpassword1").val(), "inputpassword2":$("#inputpassword2").val(), "inputpassword3":$("#inputpassword3").val()  } , function(data) {
		  if(data.state == "success")	{
			  $("#content").prepend('<div class="alert alert-info">'+
					  '<button type="button" class="close" data-dismiss="alert">&times;</button>'+
					  '<strong>ok !</strong> your info are updated.'+
					'</div>');
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

function loadAdministration() {
	$("#nav-settings li").removeClass("active");
	$("#administration").addClass("active");
	$.get("/administration", function(data){
		$("#settings-content").html(data);
	});
}

function addTeacher()	{
	var jqxhr = $.post("/addTeacher", {"mail":$("#inputmail").val(), "lastname":$("#inputlastname").val(), "firstname":$("#inputfirstname").val()  } , function(data) {
		  if(data.state == "success")	{
			  $("#promoTable").append('<tr id="'+data.id+'"><td>'+$("#inputmail").val()+'</td><td>'+$("#inputfirstname").val()+'</td><td>'+$("#inputlastname").val()+'</td></tr>');
			  $("#inputmail").val("");
			  $("#inputlastname").val("");
			  $("#inputfirstname").val("");
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

function resetPassword(id)	{
	var jqxhr = $.post("/resetPassword", {"id":id } , function(data) {
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

function delTeacher(id)	{
	var jqxhr = $.post("/delTeacher", {"id":id } , function(data) {
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