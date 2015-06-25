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
		
		console.log("making call to getQueueInfo");
		//ajax call to display queue
		$.ajax({
	         type: "GET",
	         url: "getQueueInfo",
	         data: "",
	         success: function(response){
	        	 alert("response received");
	        	 console.log("response : "+response);
	        	 //console.log("in the ajax call"+response);
	        	 /*alert("response: "+response);
	             $('.add-patient-table').load(response);*/
	         }
	    });
		console.log("after call to getQueueInfo");
	});
});
