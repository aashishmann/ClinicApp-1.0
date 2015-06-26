$(document).ready(function(){
	$("#patient").click(function(){
	    $("#add_patient").show();
	    $('.search_details').hide();
	    $("#patient_queue").hide();
	    $('#dailyReport').hide();
	});

	//display search form
	$("#search").click(function(){
		$('.search_details').show();
		$("#add_patient").hide();
		$("#patient_queue").hide();
		$('#dailyReport').hide();
	});

	//display queue
	$("#patient_queue_tab").click(function(){
	
		$('.search_details').hide();
		$("#add_patient").hide();
		$('#dailyReport').hide();
		//ajax call to display queue
		console.log("making call to getQueueInfo");
		$.ajax({
			type : "GET",
			url : "getQueueInfo",
			contentType : "application/json; charset=utf-8",
			dataType : "html",
			success : function(data) {
				//do something with response data
				var json_obj = $.parseJSON(data);
				console.log("Data received : "+data);

				if(data=="null"){
					console.log("handle for null json");
					var output = "<tr><td style='font:20px normal arial;color : red;'>"
									+"Queue is Empty.</td></tr>";					
					$('#patientqueueinfo').html(output);
				}
				else{
					var output = "<tr><th class='queue-row'>ID</th>"
								    +"<th class='queue-row'>Name</th>"
								    +"</tr>";
					for ( var i in json_obj) {
						output += "<tr><td>" + json_obj[i].id + "</td>";
						output += "<td>" + json_obj[i].patient.firstname + " "
								+ json_obj[i].patient.lastname + "</td></tr>";
						//alert(json_obj[i].id+" : "+json_obj[i].patient.firstname);
					}
					$('#patientqueueinfo').html(output);
				}
			},
			error : function(data) {
				console.log("data:"+data);
			}

		});
		
		 $("#patient_queue").show();
		console.log("after call to getQueueInfo");
	});
	
	//display daily report
	$('#daily_report_tab').click(function(){
		$("#add_patient").hide();
	    $('.search_details').hide();
	    $("#patient_queue").hide();
	    
	    $.ajax({
			type : "GET",
			url : "generateDailyReport",
			contentType : "application/json; charset=utf-8",
			dataType : "html",
			success : function(data) {
				//do something with response data
				var json_obj = $.parseJSON(data);
				console.log("Data received : "+data);

				if(data=="null"){
					console.log("handle for null json");
					var output = "<tr><td style='font:20px normal arial;color : red;'>"
									+"Queue is Empty.</td></tr>";					
					$('#patientqueueinfo').html(output);
				}
				else{
					var output = "<tr><th class='queue-row'>ID</th>"
								    +"<th class='queue-row'>Name</th>"
								    +"</tr>";
					for ( var i in json_obj) {
						output += "<tr><td>" + json_obj[i].id + "</td>";
						output += "<td>" + json_obj[i].patient.firstname + " "
								+ json_obj[i].patient.lastname + "</td></tr>";
						//alert(json_obj[i].id+" : "+json_obj[i].patient.firstname);
					}
					$('#patientqueueinfo').html(output);
				}
			},
			error : function(data) {
				console.log("data:"+data);
			}

		});
	    
	    $('#dailyReport').show();
	});
});
