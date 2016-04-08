$(document).ready(function() {
	$('#monthly_report_tab').click(function() {

		// Hide all other divs
		$("#add_patient").hide();
		$('.search_details').hide();
		$("#patient_queue").hide();
		$('#displayPatientDetails').hide();
		$('#search_results').hide();
		$('#updatePatientDetails').hide();
		$('#dailyReport').hide();

		// Show monthly report div
		$('#monthlyReport').show();

		console.log('Generating Monthly Report');
	}); // Monthly report generation ends here.
});