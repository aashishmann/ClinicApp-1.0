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
<link rel="stylesheet" type="text/css" href="css/queue.css">
<link rel="stylesheet" type="text/css" href="css/ifooter.css">
<script type="text/javascript">
	var check='${addRecord}';
	if(check.length!=0){
		alert('${addRecord}');
	}
	var searchMessage='${patientList}';
	if(searchMessage=="No Results Found"){
		alert('${patientList}');
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
						<!-- <li class="active"><a href="#">Home</a></li> -->
						<li><a href="#" id="patient">Add New Patient</a></li>
						<li><a href="#" id="search">Search</a></li>
						<li><a href="#" id="patient_queue_tab">Queue</a></li>
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
						<li><a href="logout">Log Out</a></li>
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
			<form class="search-details-form" action="findPatient" method="post">
				<table class="search-details-table">
					<tr>
						<div class="form-group">
							<td><label for="firstname" class="form-label">Firstname
									:</label></td>
							<td><input type="text" id="search-firstname"
								class="inputfield" size="30" name="firstname"
								placeholder="Enter Firstname"></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="lastname" class="form-label">Lastname
									:</label></td>
							<td><input type="text" id="search-lastname"
								class="inputfield" size="30" name="lastname"
								placeholder="Enter Lastname"></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="mobile" class="form-label">Mobile :</label></td>
							<td><input type="text" id="search-mobile" class="inputfield"
								size="30" name="mobile" placeholder="10 Digit Mobile Number"></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="dependent" class="form-label">Dependent(s)
									:</label></td>
							<td><input type="text" id="search-dependent"
								class="inputfield" size="30" name="dependent"
								placeholder="Dependent name"></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="refferedby" class="form-label">Reffered
									By :</label></td>
							<td><input type="text" id="search-refferedby"
								class="inputfield" size="30" name="refferedBy"
								placeholder="Person who reffered"></td>
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
			<form class="add-patient-form" action="addPatientDetails"
				method="post">
				<table class="add-patient-table">
					<tr>
						<div class="form-group">
							<td><label for="firstname" class="form-label">Firstname
									:</label></td>
							<td><input type="text" id="firstname" class="inputfield"
								size="30" name="firstname" placeholder="Enter Firstname"
								required></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="lastname" class="form-label">Lastname
									:</label></td>
							<td><input type="text" id="lastname" class="inputfield"
								size="30" name="lastname" placeholder="Enter Lastname"></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="age" class="form-label">Age :</label></td>
							<td><input type="number" id="age" class="inputfield"
								size="30" name="age" placeholder="Enter Age" required></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="sex" class="form-label">Sex :</label></td>
							<td><input type="radio" name="sex" id="sex" value="m"
								checked>Male <input type="radio" name="sex" id="sex"
								value="f">Female</td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="mobile" class="form-label">Mobile :</label></td>
							<td><input type="text" id="mobile" class="inputfield"
								size="30" name="mobile" placeholder="10 Digit Mobile Number"></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="landline" class="form-label">Landline
									:</label></td>
							<td><input type="text" id="landline" class="inputfield"
								size="30" name="landline" placeholder="8 Digit Landline Number"></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="dependent" class="form-label">Dependent(s)
									:</label></td>
							<td><textarea id="dependent" class="inputfield"
									name="dependent" placeholder="Dependent name" rows="2"
									cols="33"></textarea></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="address" class="form-label">Address
									:</label></td>
							<td><textarea id="address" class="inputfield" name="address"
									placeholder="Enter Address" rows="5" cols="33"></textarea></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="refferedby" class="form-label">Reffered
									By :</label></td>
							<td><input type="text" id="refferedby" class="inputfield"
								size="30" name="refferedBy" placeholder="Person who reffered"></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="refferedby" class="form-label">Marital
									Status :</label></td>
							<td><select name="maritalStatus">
									<option value="" disabled="disabled" selected="selected">Please
										select status</option>
									<option value="unmarried">Unmarried</option>
									<option value="married">Married</option>
							</select></td>
						</div>
					</tr>

					<tr>
						<div class="form-group">
							<td><label for="refferedby" class="form-label">Occupation
									:</label></td>
							<td><input type="text" id="occupation" class="inputfield"
								size="30" name="occupation" placeholder="Occupation"></td>
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

		<!-- Displaying Queue -->
		<div class="patient_queue" id="patient_queue">
			<div class="add_patient_header">
				<h2>Daily Patient Queue</h2>
			</div>
			<hr>
			<table class="patientqueueinfo">
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
	<script type="text/javascript" src="js/addpatient.js"></script>
</body>
</html>