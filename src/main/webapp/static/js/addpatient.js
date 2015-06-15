$(document).ready(function(){
	$("#patient").click(function(){
	    $("#add_patient").show();
	    $('.search_details').hide();
	});

	$("#search").click(function(){
		$("#add_patient").hide();
		$('.search_details').show();
	});
});
