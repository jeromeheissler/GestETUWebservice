$(".edithover").live("mouseover", function(){
	$(this).append('<div class="editmsg"><p>edit</p></div>');
})
$(".edithover").live("mouseout", function(){
	$(this).children("div.editmsg").remove();
})