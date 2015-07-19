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
						<table class="patient-information">
							<tr>
								<td>Id:</td>
								<td id="id"></td>
							</tr>
							<tr>
								<td>Name:</td>
								<td id="name"></td>
							</tr>
							<tr>
								<td>Mobile:</td>
								<td id="mo"></td>
							</tr>
						</table>
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
					<form id="form1" class="patient-visit-form" action="" method="get">
						<textarea id="lastfive" name="" placeholder="Last Five Prescriptions" class="inputfield" rows="10" cols="70" style="resize: none;" data-role="none"></textarea>
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
<script>
var x = document.cookie;
//console.log("cookie : "+JSON.stringify(x));
console.log("cookie : "+x);
var split_arr = x.split("+");
console.log("length : "+split_arr.length);
for(var i=0; i<split_arr.length; i++){
	console.log(i+" : "+split_arr[i]);
}

document.getElementById("id").innerHTML = split_arr[0];
document.getElementById("name").innerHTML = split_arr[1];
document.getElementById("mo").innerHTML = split_arr[2];
var medicines = split_arr[4];
console.log("medicines : "+medicines);
document.getElementById("lastfive").innerHTML = medicines;
console.log("Display split arr :"+split_arr[4]+" : "+split_arr[6]);
</script>
</body>
</html>