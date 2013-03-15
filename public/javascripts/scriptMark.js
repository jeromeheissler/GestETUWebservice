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
	$.get("/markAdd", function(data)	{
		$("#markContent").html(data);
		$.getJSON("/student", function(data)	{
			students = [];
		    map = {};
			$.each(data, function(key, val) {
				map[val.firstname+" "+val.lastname] = val.id;
				students.push(val.firstname+" "+val.lastname);
			});	
			$('#inputStudentTypeHead').typeahead({
				source: students,
				matcher: function (item) {
				    if (item.toLowerCase().indexOf(this.query.trim().toLowerCase()) != -1) {
				        return true;
				    }
				},
				updater: function (item) {
				    id = map[item];
				    $("#numStu").val(id);
				    $("#valid").removeAttr("disabled");
				    return item;
				}
			});
		});
		$.getJSON("/subject", function(data)	{
			subject = [];
		    mapsubject = {};
			$.each(data, function(key, val) {
				mapsubject[val.name] = val.id;
				subject.push(val.name);
			});	
			$('#inputSubject').typeahead({
				source: subject,
				matcher: function (item) {
				    if (item.toLowerCase().indexOf(this.query.trim().toLowerCase()) != -1) {
				        return true;
				    }
				},
				updater: function (item) {
				    id = mapsubject[item];
				    $("#subjectID").val(id);
				    return item;
				}
			});
		});
	});
}

function importMark()	{
	
}

var saveMarkValue = {};var saveSubject = {};var saveBtn = {};
function editMark(id)	{
	saveSubject[id] = $($("#"+id+" td")[0]).text();
	saveMarkValue[id] = $($("#"+id+" td")[1]).text();
	saveBtn[id] = $($("#"+id+" td")[2]).html();
	$($("#"+id+" td")[0]).html('<input id="inputsubject" class="input-small" type="text" name="subject" value="'+saveSubject[id]+'">');
	$($("#"+id+" td")[1]).html('<input id="inputmark" class="input-small" type="text" name="mark" value="'+saveMarkValue[id]+'">');
	$($("#"+id+" td")[2]).html('<div class="btn-group"><button onclick="saveMark(\''+id+'\')"  class="btn btn-info">Save</button><button onclick="cancelMark(\''+id+'\')" class="btn">Cancel</button></div>');
}

function saveMark(id)	{
	var jqxhr = $.post("/saveMark", {"id":id, "subject":$($("#"+id+" td input")[0]).val(), "mark":$($("#"+id+" td input")[1]).val()} , function(data) {
		  if(data.state == "success")	{
			  var str1 = $($("#"+id+" td input")[0]).val();
			  var str2 = $($("#"+id+" td input")[1]).val();
			  $($("#"+id+" td")[0]).html(str1);
			  $($("#"+id+" td")[1]).html(str2);
			  $($("#"+id+" td")[2]).html(saveBtn[id]);
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

function cancelMark(id)	{
	$($("#"+id+" td")[0]).html(saveSubject[id]);
	$($("#"+id+" td")[1]).html(saveMarkValue[id]);
	$($("#"+id+" td")[2]).html(saveBtn[id]);	
}

function deleteMark(id)	{
	var jqxhr = $.post("/delMark", {"id":id } , function(data) {
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
