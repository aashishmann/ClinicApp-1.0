$(document).ready(function() {
	
	$("#patient").click(function() {
		$("#search_results").hide();
	});
	$("#search").click(function() {
		$("#search_results").hide();
	});
	$("#patient_queue_tab").click(function() {
		$("#search_results").hide();
	});

	$("#patient_queue").hide();

	$('#view-patient').click(function() {
		$('.view-patient-details').show();
	});
	
});