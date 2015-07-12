<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Clinic App | Prescription</title>
<style>
.wrapper{
	margin: 2%;
	background: #fff;
	padding: 10px 25px;
	border-radius: 20px;
	box-shadow: 0 0 10px #0cf;
	opacity: 0.9;
}
.inputfield {
  border-radius: 2px;
  border: 0;
  box-shadow: 0 0 5px #09F;
  padding: 5px;
  color: #3366FF;
}
</style>
</head>
<%@include file="receptionist.jsp"%>
<body>
	<div class="wrapper">
		<table class="wrapper-table">
			<tr>
				<td>
					<div class="add_patient_header">
						<h2>Patient Details : </h2>
					</div>
					<hr>
					<form class="patient-information-form" action="" method="get">
						<table class="patient-information">
							<tr>
								<td>Id:</td>
								<td>id</td>
							</tr>
							<tr>
								<td>Name:</td>
								<td>id</td>
							</tr>
							<tr>
								<td>Age:</td>
								<td>id</td>
							</tr>
							<tr>
								<td>Mobile:</td>
								<td>id</td>
							</tr>
							<tr>
								<td>Landline:</td>
								<td>id</td>
							</tr>
							<tr>
								<td><input type="submit" value="Submit" class="btn btn-info"></td>
							</tr>
						</table>
					</form>
					<!-- patient-information table -->
				</td>
				<td>
					<div class="add_patient_header">
						<h2>Medical Details : </h2>
					</div>
					<hr>
					<form class="patient-prescription-form" action="" method="get">
						<textarea id="" name="" placeholder="Enter prescription" class="inputfield" rows="10" cols="90" style="resize: none;" data-role="none"></textarea>
						<br>
						<input type="submit" value="Submit" class="btn btn-info">
					</form>
				</td>
				<!-- medical details -->
			</tr>
			<tr>
				<td>
					<div class="add_patient_header">
						<h2>Last Five Prescriptions : </h2>
					</div>
					<hr>
					<form class="patient-visit-form" action="" method="get">
						<textarea id="" name="" placeholder="Enter Visit Details" class="inputfield" rows="10" cols="70" style="resize: none;" data-role="none"></textarea>
						<br>
						<input type="submit" value="Submit" class="btn btn-info">
					</form>
				</td>
				<!-- last five prescriptions -->
				<td>
					<div class="add_patient_header">
						<h2>Current Visit Details : </h2>
					</div>
					<hr>
					<form class="patient-visit-form" action="" method="get">
						<textarea id="" name="" placeholder="Enter Visit Details" class="inputfield" rows="10" cols="90" style="resize: none;" data-role="none"></textarea>
						<br>
						<input type="submit" value="Submit" class="btn btn-info">
					</form>
				</td>
				<!-- current visit details -->
			</tr>
		</table>
	</div>
	<!-- wrapper -->
<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>