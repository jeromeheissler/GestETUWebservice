
function loadPromotion()	{
	$.get("/promotion", function(data)	{
		$("#content").html(data);
	});
}