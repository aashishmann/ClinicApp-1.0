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

		<div id="medicine_queue" class="medicine_queue">
			<div class="search_details_header">
				<h2>Medicine Details</h2>
			</div>
			<hr>
			<table class="add-patient-table">
				<!-- <tr>
					<th class="queue-row">Name</th>
					<th class="queue-row">Medicine</th>
					<th class="queue-row">Charges</th>
				</tr> -->
			</table>
			<table id="test">
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
		setInterval(function() {
			$.ajax({
				type : "GET",
				url : "getMedicineInfo",
				contentType : "application/json; charset=utf-8",
				dataType : "html",
				success : function(data) {
					//do something with response data
					var json_obj = $.parseJSON(data);
					//console.log("data :" + json_obj[0].id);
					console.log(data);
					
		             var output="<tr><th class='queue-row'>Name</th>"+
		             				"<th class='queue-row'>Medicine</th>"+
		             				"<th class='queue-row'>Charges</th>"+
		             			"</tr><tr>";
		            for (var i in json_obj) 
		            {
		                output+="<td>" + json_obj[i].firstname + " " + json_obj[i].lastname + "</td>";
		                output+="<td>" + json_obj[i].medicines +"</td>";
		                output+="<td>" + json_obj[i].charges +"</td>";
		            }
		            output+="</tr>";
		            
					$('#test').html(output);
				},
				error : function(data) {
					console.log(data);
					alert(data);
				}

			});
		}, 5000);//time in milliseconds
	</script>
</body>
</html>