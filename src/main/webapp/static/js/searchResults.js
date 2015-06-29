$(document).ready(function() {

	$("#patient").click(function() {
		$("#search_results").hide();
		$('#updatePatientDetails').hide();
	});
	$("#search").click(function() {
		$("#search_results").hide();
		$('#updatePatientDetails').hide();
	});
	$("#patient_queue_tab").click(function() {
		$("#search_results").hide();
		$('#updatePatientDetails').hide();
	});

	$("#patient_queue").hide();

	$('#view-patient').click(function() {
		$('.view-patient-details').show();
		$('#updatePatientDetails').hide();
	});

	$.editPatient = function(id){
		console.log("editing details for id : " + id);
		
		$.ajax({
		    type: 'GET',
		    url: "findPatientById",
		    data: "id="+id,
		    contentType : "application/json; charset=utf-8",
			dataType : "html",
		    success: function(data) {
		    	console.log("patient details fetched : "+data);
		    	var json_obj = $.parseJSON(data);
		    	
		    	if(data=="null"){
					console.log("handle for null json");
					var output = "<tr><td style='font:20px normal arial;color : red;'>"
									+"Patient Details not found.</td></tr>";					
					$('#edit-details-table').html(output);
				}
				else{
					console.log("filling info");
					
					//handle for all undefined fields
					if(json_obj.firstname==undefined)
						json_obj.firstname="";
					if(json_obj.lastname==undefined)
						json_obj.lastname="";
					if(json_obj.age==undefined)
						json_obj.age=0;
					if(json_obj.mobile==undefined)
						json_obj.mobile="";
					if(json_obj.landline==undefined)
						json_obj.landline="";
					if(json_obj.refferedBy==undefined)
						json_obj.refferedBy="";
					if(json_obj.dependent==undefined)
						json_obj.dependent="";
					if(json_obj.address==undefined)
						json_obj.address="";
					if(json_obj.occupation==undefined)
						json_obj.occupation="";
					
					var output = "<tr><td><label for='id' class='form-label'>ID	:</label></td>"
						+"<td><input type='text' name='id' value='"+json_obj.id+"' class='inputfield' size='30' readonly></td></tr>"
						
						+"<tr><td><label for='firstname' class='form-label'>Firstname	:</label></td>"
						+"<td><input type='text' name='firstname' value='"+json_obj.firstname+"' class='inputfield' size='30' required autofocus></td></tr>"
						
						+"<tr><td><label for='lastname' class='form-label'>lastname	:</label></td>"
						+"<td><input type='text' name='lastname' value='"+json_obj.lastname+"' class='inputfield' size='30' required autofocus></td></tr>"

						+"<tr><td><label for='sex' class='form-label'>Sex :</label></td>"
						+"<td><input type='radio' name='sex' id='sex' value='m' required>Male <input type='radio' name='sex' id='sex' value='f'>Female</td></tr>"
						
						+"<tr><td><label for='age' class='form-label'>Age :</label></td>"
						+"<td><input type='number' id='age' class='inputfield' size='30' name='age' value='"+json_obj.age+"' placeholder='Enter Age' required></td></tr>"
						
						+"<tr><td><label for='mobile' class='form-label'>Mobile	:</label></td>"
						+"<td><input type='text' id='mobile' class='inputfield' size='30' name='mobile' value='"+json_obj.mobile+"' placeholder='10 Digit Mobile Number'></td></tr>"
						
						+"<tr><td><label for='landline' class='form-label'>Landline :</label></td>"
						+"<td><input type='text' id='landline' class='inputfield' size='30' name='landline' value='"+json_obj.landline+"' placeholder='8 Digit Landline Number'></td></tr>"
						
						+"<tr><td><label for='maritalStatus' class='form-label'>Marital Status	:</label></td>"
						+"<td><select name='maritalStatus' required><option value='' disabled='disabled' selected='selected'>Please select status</option>"
						+"<option value='unmarried'>Unmarried</option><option value='married'>Married</option></select></td></tr>"
						
						+"<tr><td><label for='refferedBy' class='form-label'>Reffered By :</label></td>"
						+"<td><input type='text' id='refferedBy' class='inputfield' size='30' name='refferedBy' value='"+json_obj.refferedBy+"' placeholder='Person who reffered'></td></tr>"
						
						+"<tr><td><label for='dependent' class='form-label'>Dependents :</label></td>"
						+"<td><textarea id='dependent' class='inputfield' name='dependent' value='"+json_obj.dependent+"' placeholder='Dependent name' rows='2' cols='33'></textarea></td></tr>"
						
						
						+"<tr><td><label for='address' class='form-label'>Address :</label></td>"
						+"<td><textarea id='address' class='inputfield' name='address' value='"+json_obj.address+"' placeholder='Enter Address' rows='3' cols='33'></textarea></td></tr>"
						
						+"<tr><td><label for='occupation' class='form-label'>Occupation :</label></td>"
						+"<td><input type='text' id='occupation' class='inputfield' size='30' name='occupation' value='"+json_obj.occupation+"' placeholder='Occupation'></td></tr>"
			
						+ "<tr><td></td><td><button id='savePatientDetails' type='button' class='btn btn-info' onclick='savePatient("+json_obj.id+")'>Save</button></td></tr>";
					$('#edit-details-table').html(output);
				}
		    },
		    error : function(data) {
				console.log("some error occured :"+data);
				window.location.reload();
			}
		    
		});//ajax call ends
		
		//hide the required divs
		$("#add_patient").hide();
	    $('.search_details').hide();
	    $("#search_results").hide();
	    $("#patient_queue").hide();
	    $('#dailyReport').hide();
	    $('#displayPatientDetails').hide();
		
	    //display the self div
		$('#updatePatientDetails').show();
		
	};//editPatient ends
});