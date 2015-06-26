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
						<button id="view-patient" type="button" class="btn btn-success">View</button>
					</td>
					<td class="queue-row">
						<button type="button" class="btn btn-danger"
							onclick="deletePatient(${patientList.id})">Delete</button>
					</td>
				</tr>
				<tr class="view-patient-details">
					<td>Name</td>
					<td>Sex</td>
					<td>Age</td>
					<td>Mobile</td>
					<td>Landline</td>
					<td>Reffered By</td>
					<td>Dependents</td>
					<td>Martial Status</td>
					<td>Address</td>
					<td>Occupation</td>
				</tr>
				<tr class="view-patient-details">
					<td>${patientList.firstname} ${patientList.lastname}</td>
					<td>${patientList.sex}</td>
					<td>${patientList.age}</td>
					<td>${patientList.mobile}</td>
					<td>${patientList.landline}</td>
					<td>${patientList.refferedBy}</td>
					<td>${patientList.dependent}</td>
					<td>${patientList.maritalStatus}</td>
					<td>${patientList.address}</td>
					<td>${patientList.occupation}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<!-- search results -->
</body>
<script type="text/javascript" src="js/searchResults.js"></script>
</html>