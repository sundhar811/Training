$(document).ready(function(){
	$("#login").click(function(){
		function findAll() {
		    $.ajax({
		        type: 'POST',
		        url: rootURL + '/sign-in',
		        dataType: "json", // data type of response
		        success: renderList
		    });
		}
	});
});