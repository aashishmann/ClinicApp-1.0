<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Clinic App | Search Results</title>
<script>
	function deletePatient(id){
		jQuery.ajax({
		    'type': 'GET',
		    'url': "deletePatient",
		    'data': "id="+id,
		    'contentType': 'application/json',
		    'success': function(data) {
		    	alert(data);
		    	window.location.replace("receptionist");
		    	}
		});
	}
	
	function displayDetails(id){
		console.log("finding details for id : "+id);
		jQuery.ajax({
		    'type': 'GET',
		    'url': "findPatientById",
		    'data': "id="+id,
		    'contentType' : "application/json; charset=utf-8",
			'dataType' : "html",
		    'success': function(data) {
		    	console.log("patient details fetched : "+data);
		    	var json_obj = $.parseJSON(data);
		    	
		    	if(data=="null"){
					console.log("handle for null json");
					var output = "<tr><td style='font:20px normal arial;color : red;'>"
									+"Patient Details not found.</td></tr>";					
					$('#displayPatientDetailsTable').html(output);
				}
				else{
					var output = "<tr><td>Name : </td><td>"+json_obj.firstname+json_obj.lastname+"</td></tr>"
								+"<tr><td>Sex : </td><td>"+json_obj.sex+"</td></tr>"
								+"<tr><td>Age : </td><td>"+json_obj.age+"</td></tr>"
								+"<tr><td>Mobile : </td><td>"+json_obj.mobile+"</td></tr>"
								+"<tr><td>Landline : </td><td>"+json_obj.landline+"</td></tr>"
								+"<tr><td>Marital Status : </td><td>"+json_obj.maritalStatus+"</td></tr>"
								+"<tr><td>Reffered By : </td><td>"+json_obj.refferedBy+"</td></tr>"
								+"<tr><td>Dependents : </td><td>"+json_obj.dependent+"</td></tr>"
								+"<tr><td>Address : </td><td>"+json_obj.address+"</td></tr>"
								+"<tr><td>Occupation : </td><td>"+json_obj.occupation+"</td></tr>"
					$('#displayPatientDetailsTable').html(output);
					$('#displayPatientDetails').show();
				}
		    },
		    'error' : function(data) {
				console.log("some error occured :"+data);
				window.location.reload();
			}
		});
		
	}
</script>
<style>
.view-patient-details {
	display: none;
}
</style>
</head>
<%@include file="receptionist.jsp"%>

<body>
	<!-- Search Results to be displayed -->
	<div class="search_results" id="search_results">
		<div class="add_patient_header">
			<h2>Search Results</h2>
		</div>
		<hr>
		<table>
			<tr>
				<th class="queue-row">ID</th>
				<th class="queue-row">Name</th>
			</tr>
			<c:forEach items="${patientList}" var="patientList">
				<tr>
					<td class="queue-row">${patientList.id}</td>
					<td class="queue-row">${patientList.firstname}
						${patientList.lastname}</td>
					<td class="queue-row">
						<button id="view-patient" type="button" class="btn btn-success" onclick="displayDetails(${patientList.id})">View</button>
					</td>
					<td class="queue-row">
						<button type="button" class="btn btn-danger"
							onclick="deletePatient(${patientList.id})">Delete</button>
					</td>																															
			</c:forEach>
		</table>
	</div>
	<!-- search results -->
	
	<div id="displayPatientDetails" class="displayPatientDetails">
		<div class="add_patient_header">
			<h2 style="text-align: center;">Patient Details</h2>
		</div>
		<hr>
		<table id="displayPatientDetailsTable" align="center">
		</table>
	</div>
</body>
<script type="text/javascript" src="js/searchResults.js"></script>
</html>