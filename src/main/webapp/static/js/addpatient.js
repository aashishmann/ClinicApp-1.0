$(document).ready(function(){
	$("#patient").click(function(){
	    $("#add_patient").show();
	    $('.search_details').hide();
	    $("#patient_queue").hide();
	});

	$("#search").click(function(){
		$('.search_details').show();
		$("#add_patient").hide();
		$("#patient_queue").hide();
	});
	
	$("#patient_queue_tab").click(function(){
		$("#patient_queue").show();
		$('.search_details').hide();
		$("#add_patient").hide();
	});
});
