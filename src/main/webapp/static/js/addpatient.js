$(document).ready(function(){
	
	// Add new patient
	$("#patient").click(function(){
	    $("#add_patient").show();
	    $('.search_details').hide();
	    $("#patient_queue").hide();
	    $('#dailyReport').hide();
	    $('#displayPatientDetails').hide();
	    $('#monthlyReport').hide();
	});

	//display search form
	$("#search").click(function(){
		$('.search_details').show();
		$("#add_patient").hide();
		$("#patient_queue").hide();
		$('#dailyReport').hide();
		$('#displayPatientDetails').hide();
		$('#monthlyReport').hide();
	});

	//display queue
	$("#patient_queue_tab").click(function(){
	
		$('.search_details').hide();
		$("#add_patient").hide();
		$('#dailyReport').hide();
		$('#displayPatientDetails').hide();
		$('#monthlyReport').hide();
		
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
                var id = 0;
                var name = '';
				if(data=="null"){
					console.log("handle for null json");
					var output = "<tr><ftd style='font:20px normal arial;color : red;'>"
									+"Queue is Empty. Please wait for patients to come.</td></tr>";					
					$('#patientqueueinfo').html(output);
				}
				else{
					var output = "<tr><th class='queue-row'>ID</th>"
								    +"<th class='queue-row'>Name</th>"
								    +"</tr>";
					for ( var i in json_obj) {
						output += "<tr><td>" + json_obj[i].patient.id + "</td>";
						output += "<td>" + json_obj[i].patient.firstname + " "
								//+ json_obj[i].patient.lastname + "</td>";
						//alert(json_obj[i].id+" : "+json_obj[i].patient.firstname);
                        if(json_obj[i].patient.lastname != "" && json_obj[i].patient.lastname != undefined)
                            output += " " + json_obj[i].patient.lastname
                        output += "</td>";
						// Prescription will be provided to the patient against whose name button is clicked
						// So saving id and name to be retrieved later
						id = json_obj[i].patient.id;
						name = json_obj[i].patient.firstname;
						
						//if doctor is logged in then display this as well
                        console.log('Role : '+'${ROLETYPE}');
                        output += "<c:if test='${ROLETYPE == \'DOC\'}'><td><button id='prescription' type='button' class='btn btn-success'>Give Prescription</button></td></c:if></tr>";
						
					}
					$('#patientqueueinfo').html(output);
					
					$("#prescription").click(function(){
				    	console.log('Giving prescription for :'+id + ' and patient : '+name);
				    	
				    	jQuery.ajax({
							'type':'POST',
							'url':'givePrescription',
							'data':'id='+id,
							'dataType' : "text",
						    'success': function(data) {
						    	console.log("response : "+data);
						    	var x=$.parseJSON(data);	//use this to send individual elements
						    	console.log(JSON.stringify(x));
						    	//alert("patient history : "+x.patientHistory.id+x.patientHistory.chiefComplaints+x.patientHistory.mentalSymptoms);
						    	
						    	//fill necessary patient details
						    	var str = x.patient.id+"+"+x.patient.firstname+" "+x.patient.lastname+"+"+x.patient.mobile+"+";

						    	//fill prescription details
						    	if(x.prescriptionList!=null){
						    		console.log("prescription list : "+x.prescriptionList[0].id+x.prescriptionList[0].medicines);
							    	for(var i=0; i<x.prescriptionList.length; i++){
								    	str += x.prescriptionList[i].id + "+" + x.prescriptionList[i].medicines + "+";
								    	str += x.prescriptionList[i].followupRemark+"+"+x.prescriptionList[i].revisitDate+"+"+x.prescriptionList[i].charges+"+";
								    	console.log("str : "+str);
							    	}
						    	}
						    	else{
						    		console.log("No prescriptions found");
						    	}
						    	
						    	//fill the patient history if history of patient exists
						    	if(x.patientHistory!=null){
							    	str += "%#%@&+"+x.patientHistory.id+"+"+ x.patientHistory.purposeOfVisit+"+"+x.patientHistory.chiefComplaints+"+"+x.patientHistory.mentalSymptoms+"+";
							    	str += x.patientHistory.physicalSymptoms+"+"+x.patientHistory.investigation+"+"+x.patientHistory.familyHistory+"+";
							    	str += x.patientHistory.pastHistory+"+"+x.patientHistory.thermal+"+"+x.patientHistory.desire+"+"+x.patientHistory.aversion+"+";
						    	}
						    	else{
						    		console.log("patient history not found");
						    		str += "%#%@&+";
						    	}
						    	//alert("Final str : "+str);
						    	//set and send all info through window.name to prescription page
						    	window.name = str;
						    	
						    	//redirect to prescription page
						    	location.href="pres";
						    },
						    'error':function(data){
						    	alert("Some error occured. Try again later. : "+data);
						    	console.log("Some error occured :"+JSON.stringify(data));
								window.location.reload();
						    }
						});//ajax call ends
				    	
					}); // prescription ends here
				}//else ends
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
	    $('#displayPatientDetails').hide();
	    $('#search_results').hide();
	    $('#updatePatientDetails').hide();
	    $('#monthlyReport').hide();
	    
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
					$('#dailyReportCard').html(output);
				}
				else{
                    var totalCharges = 0;
					var output = "<tr><th class='queue-row'>Patient Name</th>"
								    +"<th class='queue-row'>Charges</th>"
								    +"</tr>";
					for ( var i in json_obj) {
						output += "<tr><td>" + json_obj[i].firstname + " "
								+ json_obj[i].lastname + "</td>";
                        output += "<td class='reportcharges'>" + json_obj[i].charges + "</td></tr>";
                        totalCharges += json_obj[i].charges;

					}
					//if total charges are not zero then display charges
					if(totalCharges!=0){
	                    output+="<tr><td></td><td>______</td>"
	                    output += "<tr><td>Total Charges : </td><td class='reportcharges'>" + totalCharges + "</td></tr>";
	                    console.log("total charges "+ totalCharges);
					}
					//message if daily report charges are zero
					else{
						output = "<tr><td style='font:24px bold arial;color : red;'>Nothing to generate. Try sometime later.</td></tr>";
					}
					$('#dailyReportCard').html(output);
				}
			},
			error : function(data) {
				console.log("data:"+data);
			}

		});
	    
	    $('#dailyReport').show();
	});
});
