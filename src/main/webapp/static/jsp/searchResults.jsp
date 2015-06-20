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
<link rel="stylesheet" type="text/css" href="css/search.css">
<link rel="stylesheet" type="text/css" href="css/ifooter.css">
<script>
	function deletePatient(id){
		jQuery.ajax({
		    'type': 'GET',
		    'url': "deletePatient/"+id,
		    'contentType': 'application/json',
		    'success': function(data) {
		    	alert("Patient Record Deleted!");
		    	window.location.replace("receptionist");
		    	}
		});
	}
</script>
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
					<ul class="nav navbar-nav">
						<li><a href="#" id="patient">Add New Patient</a></li>
						<li><a href="#" id="search">Search</a></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-expanded="false">Utilities
								<span class="caret"></span>
						</a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="#">Medical Certificate</a></li>
								<li class="divider"></li>
								<li class="dropdown-header">Generate Reports</li>
								<li><a href="#">Daily Report</a></li>
								<li><a href="#">Weekly Report</a></li>
								<li><a href="#">Monthly Report</a></li>
							</ul></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li><a href="#">Log Out</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</nav>
		<!-- fixed navbar -->

		<div class="search_details" id="search_details">
			<div class="search_details_header">
				<h2>Search</h2>
			</div>
			<hr>
			<form class="search-details-form" action="" method="post">
				<table class="search-details-table">
					<tr>
						<div class="form-group">
							<td><label for="firstname" class="form-label">Firstname
									:</label></td>
							<td><input type="text" id="search-firstname"
								class="inputfield" size="30" placeholder="Enter Firstname"></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="lastname" class="form-label">Lastname
									:</label></td>
							<td><input type="text" id="search-lastname"
								class="inputfield" size="30" placeholder="Enter Lastname"></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="mobile" class="form-label">Mobile :</label></td>
							<td><input type="text" id="search-mobile" class="inputfield"
								size="30" placeholder="10 Digit Mobile Number"></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="dependent" class="form-label">Dependent(s)
									:</label></td>
							<td><input type="text" id="search-dependent"
								class="inputfield" size="30" placeholder="Dependent name"></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="refferedby" class="form-label">Reffered
									By :</label></td>
							<td><input type="text" id="search-refferedby"
								class="inputfield" size="30" placeholder="Person who reffered"></td>
						</div>
					</tr>

					<tr>
						<td></td>
						<td><input type="submit" value="Submit" class="btn btn-info"></td>
					</tr>
				</table>
			</form>
		</div>

		<div class="add_patient" id="add_patient">
			<div class="add_patient_header">
				<h2>Add New Patient</h2>
			</div>
			<hr>
			<form class="add-patient-form" action="" method="post">
				<table class="add-patient-table">
					<tr>
						<div class="form-group">
							<td><label for="firstname" class="form-label">Firstname
									:</label></td>
							<td><input type="text" id="firstname" class="inputfield"
								size="30" placeholder="Enter Firstname" required></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="lastname" class="form-label">Lastname
									:</label></td>
							<td><input type="text" id="lastname" class="inputfield"
								size="30" placeholder="Enter Lastname"></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="age" class="form-label">Age :</label></td>
							<td><input type="number" id="age" class="inputfield"
								size="30" placeholder="Enter Age" required></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="sex" class="form-label">Sex :</label></td>
							<td><input type="radio" name="sex" id="sex" value="male"
								checked>Male <input type="radio" name="sex" id="sex"
								value="female">Female</td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="mobile" class="form-label">Mobile :</label></td>
							<td><input type="text" id="mobile" class="inputfield"
								size="30" placeholder="10 Digit Mobile Number"></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="landline" class="form-label">Landline
									:</label></td>
							<td><input type="text" id="landline" class="inputfield"
								size="30" placeholder="8 Digit Landline Number"></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="dependent" class="form-label">Dependent(s)
									:</label></td>
							<td><textarea id="dependent" class="inputfield"
									placeholder="Dependent name" rows="2" cols="33"></textarea></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="address" class="form-label">Address
									:</label></td>
							<td><textarea id="address" class="inputfield"
									placeholder="Enter Address" rows="5" cols="33"></textarea></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="refferedby" class="form-label">Reffered
									By :</label></td>
							<td><input type="text" id="refferedby" class="inputfield"
								size="30" placeholder="Person who reffered"></td>
						</div>
					</tr>

					<tr>
						<td></td>
						<td><input type="submit" value="Submit" class="btn btn-info"></td>
					</tr>

				</table>
			</form>
		</div>
		<!-- addpatient -->

		<!-- Search Results to be displayed -->
		<div class="search_results" id="search_results">
			<div class="add_patient_header">
				<h2>Search Results</h2>
			</div>
			<hr>
			<table>
				<tr>
					<th>ID</th>
					<th>Firstname</th>
					<th>Lastname</th>
				</tr>
				<c:forEach items="${patientList}" var="patientList">
					<tr>
						<td>${patientList.id}</td>
						<td>${patientList.firstname}</td>
						<td>${patientList.lastname}</td>
						<td>
							<button type="button" class="btn btn-success">View</button>
						</td>
						<td>
							<button type="button" class="btn btn-danger" onclick="deletePatient(${patientList.id})">Delete</button>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<!-- search results -->

	</div>
	<!-- /container -->

	<footer class="footer">
		<div class="container">
			<p class="text-muted">All rights reserved. **** Clinic.</p>
		</div>
	</footer>

	<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/addpatient.js"></script>
</body>
</html>