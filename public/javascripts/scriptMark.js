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