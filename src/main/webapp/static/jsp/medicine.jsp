<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Clinic App</title>
<!-- core css -->
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<!-- custom css for this file -->
<link rel="stylesheet" type="text/css" href="css/navbar-fixed-top.css">
<link rel="stylesheet" type="text/css" href="css/receptionist.css">
<link rel="stylesheet" type="text/css" href="css/medicine.css">
<link rel="stylesheet" type="text/css" href="css/queue.css">
<link rel="stylesheet" type="text/css" href="css/ifooter.css">
</head>
<body>

	<div class="container">

		<!-- Fixed navbar -->
		<nav class="navbar navbar-default navbar-fixed-top">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">Clinic Name</a>
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav navbar-right">
						<li><a href="logout">Log Out</a></li>
					</ul>
				</div>
				<!-- nav-collapse -->
			</div>
		</nav>
		<!-- fixed navbar -->

		<!-- Displaying Queue -->
		<!-- <div class="patient_queue" id="patient_queue">
			<div class="add_patient_header">
				<h2>Daily Patient Queue</h2>
			</div>
			<hr>
			<table class="add-patient-table">
				<tr>
					<th class="queue-row">ID</th>
					<th class="queue-row">Name</th>
				</tr>
				<forEach items="${patientQueue}" var="patientQueue">
				<tr>
					<td id="patientId" class="queue-row">${patientQueue.patient.id}</td>
					<td id="patientName" class="queue-row">${patientQueue.patient.firstname}
						${patientQueue.patient.lastname}</td>
					<td class="queue-row">
						<button type="button" class="btn btn-success">View</button>
					</td>
					<td class="queue-row">
						<button type="button" class="btn btn-danger"
							onclick="deletePatient(${patientQueue.id})">Delete</button>
					</td>
				</tr>
				<forEach>
			</table>
		</div> -->
		<div id="medicine_queue" class="medicine_queue">
			<div class="search_details_header">
				<h2>Medicine Details</h2>
			</div>
			<hr>
			<table class="add-patient-table">
				<tr>
					<th class="queue-row">Name</th>
					<th class="queue-row">Medicine</th>
					<th class="queue-row">Charges</th>
				</tr>
				<forEach items="${medicineQueue}" var="medicineQueue">
				<tr>
					<td class="queue-row">${medicineQueue.patient.id}</td>
					<td class="queue-row">${medicineQueue.patient.firstname}
						${medicineQueue.patient.lastname}</td>
					<td class="queue-row">${medicineQueue.medicine}</td>
					<td class="queue-row">${medicineQueue.charges}</td>
				</tr>
				<forEach>
			</table>
		</div>
		<!-- Queue information -->

	</div>
	<!-- /container -->

	<footer class="footer">
		<div class="container">
			<p class="text-muted">All rights reserved. **** Clinic.</p>
		</div>
	</footer>

	<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script>
	setInterval(function()
			{ 
			    $.ajax({
			      type:"GET",
			      url:"getMedicineInfo",
			      /* datatype:"html", */
			      success:function(data)
			      {
			          //do something with response data
			          $('#medicine_queue').html("in ajax call");
			      }
			    });
			}, 50000);//time in milliseconds 
	</script>
</body>
</html>