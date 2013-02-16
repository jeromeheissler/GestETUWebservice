
function loadPromotion()	{
	$("#menu li").removeClass("active");
	$("#promo").addClass("active");
	$.getJSON("/promotion", function(data)	{
		$("#content").html("");
		var items = [];
		items.push('<tr><th>Nom</th><th>Annee</th><th></th></tr>');  
		items.push('<tr><td><input id="inputname" type="text" name="nom" placeholder="nom"></td>'+
		'<td><div class="input-append"><input type="text" id="inputannee" name="annee" placeholder="Annee"><button onclick="addPromotion()" class="btn" type="button">Save</button></div></td>'+
		'<td></td></tr>')
 
		$.each(data, function(key, val) {
		    items.push('<tr id="' + val.id + '"><td>'+val.name+'</td><td>'+val.annee+'</td><td><div class="btn-group"><button onclick="editPromo(\''+val.id+'\')"  class="btn">Edit</button><button onclick="delPromo(\''+val.id+'\')" class="btn btn-danger">Delete</button></div></td></tr>');
		});
		
		$('<table/>', {
		    'class': 'table',
		    'id' : 'promoTable',
		    html: items.join('')
		}).appendTo('#content');
	});
}

function addPromotion()	{
	var jqxhr = $.post("/addPromotion", {"name":$("#inputname").val(), "annee":$("#inputannee").val() } , function(data) {
		  if(data.state == "success")	{
			  $("#promoTable").append('<tr id="'+data.id+'"><td>'+$("#inputname").val()+'</td><td>'+$("#inputannee").val()+'</td></tr>');
			  $("#inputname").val("");
			  $("#inputannee").val("");
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

function delPromo(id)	{
	var jqxhr = $.post("/delPromotion", {"id":id } , function(data) {
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

var saveAnnee = {};var saveName = {};var saveBtn = {};
function editPromo(id)	{
	saveName[id] = $($("#"+id+" td")[0]).text();
	saveAnnee[id] = $($("#"+id+" td")[1]).text();
	saveBtn[id] = $($("#"+id+" td")[2]).html();
	$($("#"+id+" td")[0]).html('<input id="inputnameedit" type="text" name="nom" value="'+saveName[id]+'">');
	$($("#"+id+" td")[1]).html('<input id="inputanneeedit" type="text" name="annee" value="'+saveAnnee[id]+'">');
	$($("#"+id+" td")[2]).html('<div class="btn-group"><button onclick="savePromo(\''+id+'\')"  class="btn btn-info">Save</button><button onclick="cancelPromo(\''+id+'\')" class="btn">Cancel</button></div>');
}

function cancelPromo(id)	{
	$($("#"+id+" td")[0]).html(saveName[id]);
	$($("#"+id+" td")[1]).html(saveAnnee[id]);
	$($("#"+id+" td")[2]).html(saveBtn[id]);	
}

function savePromo(id)	{
	var jqxhr = $.post("/editPromotion", {"id":id, "name":$($("#"+id+" td input")[0]).val(), "annee":$($("#"+id+" td input")[1]).val() } , function(data) {
		  if(data.state == "success")	{
			  var str1 = $($("#"+id+" td input")[0]).val();
			  var str2 = $($("#"+id+" td input")[1]).val()
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
