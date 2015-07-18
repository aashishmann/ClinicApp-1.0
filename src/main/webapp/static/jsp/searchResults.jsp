<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Clinic App | Search Results</title>
<script>

	var updateStatus = '${updatePatientStatus}';
	if (updateStatus.length != 0) {
		alert(updateStatus);
	}

	function deletePatient(id){
		jQuery.ajax({
		    'type': 'GET',
		    'url': "deletePatient",
		    'data': "id="+id,
		    'contentType': 'application/json',
		    'success': function(data) {
		    	alert(data);
		    	//window.location.replace("receptionist");
		    	window.location.reload();
		    	}
		});
	}//delete function ends
	
	function addPatientToQueue(id){
		console.log("making request for id : "+id);
		jQuery.ajax({
		    'type': 'GET',
		    'url': "addPatientToQueue",
		    'data': "id="+id,
		    'contentType': 'application/json',
		    'dataType' : "html",
		    'success': function(data) {
		    	alert(data);
		    	window.location.reload();
		    	},
		    'error': function(data){
		    	alert("Some error occured."+data);
		    	window.location.reload();
		    }
		});
	}//addPatientToQueue ends
	
	function editPatientDetails(id){
		if (id > 0) {
			$.editPatient(id);
		}
	}
	
	function givePrescription(id){
		console.log("doctor giving prescription for id : "+id);
		jQuery.ajax({
			'type':'POST',
			'url':'givePrescription',
			'data':'id='+id,
			'dataType' : "text",
		    'success': function(data) {
		    	//console.log("returned resposne : "+data);
		    	alert("response : "+data);
		    	console.log("response : "+data);
		    	
		    	var x=$.parseJSON(data);	//use this to send individual elements
		    	console.log("prescription list : "+x.prescriptionList[0].id+x.prescriptionList[0].medicines);
		    	
		    	//fill necessary patient details
		    	var str = x.patient.id+"+"+x.patient.firstname+" "+x.patient.lastname+"+"+x.patient.mobile+"+";

		    	//fill prescription details
		    	for(var i=0; i<x.prescriptionList.length; i++){
			    	str += x.prescriptionList[i].id + "+" + x.prescriptionList[i].medicines + "+";
			    	str += x.prescriptionList[i].followupRemark+"+"+x.prescriptionList[i].revisitDate+"+"+x.prescriptionList[i].charges+"+";
			    	console.log("str : "+str);
		    	}
		    	
		    	//set information in cookie
		    	document.cookie=str;
		    	
		    	//redirect to prescription page
		    	location.href="pres";
		    },
		    'error':function(data){
		    	alert("Some error occured. Try again later. : "+data);
		    	console.log("Some error occured :"+JSON.stringify(data));
				window.location.reload();
		    }
		});//ajax call ends
	}//givePrescription
	
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
					var output = "<tr><td>Name : </td><td>"+json_obj.firstname+json_obj.lastname+"</td></tr>";
					
					if(json_obj.sex!="" && json_obj.sex!=undefined)
						output += "<tr><td>Sex : </td><td>"+json_obj.sex+"</td></tr>"
					if(json_obj.age!="" && json_obj.age!=undefined)
						output += "<tr><td>Age : </td><td>"+json_obj.age+"</td></tr>"
					if(json_obj.mobile!="" && json_obj.mobile!=undefined)	
						output += "<tr><td>Mobile : </td><td>"+json_obj.mobile+"</td></tr>"
					if(json_obj.landline!="" && json_obj.landline!=undefined)
						output += "<tr><td>Landline : </td><td>"+json_obj.landline+"</td></tr>"
					if(json_obj.maritalStatus!="" && json_obj.maritalStatus!=undefined)
						output += "<tr><td>Marital Status : </td><td>"+json_obj.maritalStatus+"</td></tr>"
					if(json_obj.refferedBy!="" && json_obj.refferedBy!=undefined)
						output += "<tr><td>Reffered By : </td><td>"+json_obj.refferedBy+"</td></tr>"
					if(json_obj.dependent!="" && json_obj.dependent!=undefined)
						output += "<tr><td>Dependents : </td><td>"+json_obj.dependent+"</td></tr>"
					if(json_obj.address!="" && json_obj.address!=undefined)
						output += "<tr><td>Address : </td><td>"+json_obj.address+"</td></tr>"
					if(json_obj.occupation!="" && json_obj.occupation!=undefined)
						output += "<tr><td>Occupation : </td><td>"+json_obj.occupation+"</td></tr>"
						
						output += "<tr><td><button id='editPatient' type='button' class='btn btn-success' onclick='editPatientDetails("+json_obj.id+")'>Edit</button></td>"
						output += "<td><button id='addOldPatientToQueue' type='button' class='btn btn-success' onclick='addPatientToQueue("+json_obj.id+")'>Add To Queue</button></td></tr>";
						
						//if doctor is logged in then display this as well
						output += "<c:if test='${ROLETYPE == \'DOC\'}'><tr><td><button id='prescription' type='button' class='btn btn-success' onclick='givePrescription("+json_obj.id+")'>Give Prescription</button></td>";
						output += "<td><button id='deleteOldPatientToQueue' type='button' class='btn btn-danger' onclick='deletePatient("+json_obj.id+")'>Delete</button></td></tr></c:if>";
						
						output += "<c:if test='${ROLETYPE == \'REC\'}'><tr><td><button id='deleteOldPatientToQueue' type='button' class='btn btn-danger' onclick='deletePatient("+json_obj.id+")'>Delete</button></td></tr></c:if>";
						
						console.log('Role on search results page: '+'${ROLETYPE}');
						
					$('#displayPatientDetailsTable').html(output);
					$('#displayPatientDetails').show();
				}
		    },
		    'error' : function(data) {
				console.log("some error occured :"+data);
				window.location.reload();
			}
		}); //ajax call ends
		
	}//display function ends
</script>
<style>
.view-patient-details {
	display: none;
}
#displayPatientDetailsTable button {
  width: 100%;
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
	<!-- display patient details -->
	
	<div id="updatePatientDetails" class="updatePatientDetails">
		<div class="add_patient_header">
			<h2 style="text-align: center;">Edit Patient Details</h2>
		</div>
		<hr>
		<form class="edit-details-form" action="savePatientDetails" method="post">
				<table id="edit-details-table">
				</table>
		</form>
	</div>
	<!-- Edit/update details form -->
	
</body>
<script type="text/javascript" src="js/searchResults.js"></script>
</html>