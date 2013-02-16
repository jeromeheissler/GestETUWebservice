$(document).ready(function() {
	function loadUrl() {
		if(HashSearch.keyExists("promotion"))	{
			loadPromotion();
		}else if(HashSearch.keyExists("student"))	{
			loadStudent();
		}else	{
			loadPromotion();
		}
		//Maj hashsearch
		HashSearch.push();
	}
	$(window).bind('hashchange', function () { //detect hash change
        var hash = window.location.hash.slice(1);
        HashSearch.load();
        loadUrl();
    });
});