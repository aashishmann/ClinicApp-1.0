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
.pres-table{
	padding: 5px;
}
</style>
</head>
<%@include file="receptionist.jsp"%>
<body>
	<div class="wrapper">
		<table class="wrapper-table">
			<tr>
				<td style="vertical-align:top">
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
				<td style="vertical-align:top">
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
				<!-- medical details -->
			</tr>
			<tr>
				<td style="vertical-align:top">
					<div class="add_patient_header">
						<h2>Last Five Prescriptions : </h2>
					</div>
					<hr>
					<table id="lastfive" border="1">
					</table>
					<br>
				</td>
				<!-- last five prescriptions -->
				<td style="vertical-align:top">
					<div class="add_patient_header">
						<h2>Medical Details : </h2>
					</div>
					<hr>
					<form class="patient-history" action="" method="get">
						<textarea id="" name="" placeholder="Patient History" class="inputfield" rows="10" cols="90" style="resize: none;" data-role="none"></textarea>
						<br>
						<input type="submit" value="Submit" class="btn btn-info">
					</form>
				</td>
				<!-- current visit details -->
			</tr>
		</table>
	</div>
	<!-- wrapper -->
</body>
<script type="text/javascript" src="js/prescription.js"></script>
<script>
var x = document.cookie;
//console.log("cookie : "+JSON.stringify(x));
console.log("cookie : "+x);
var split_arr = x.split("+");
console.log("length : "+split_arr.length);
var flag=0;
for(var i=0; i<split_arr.length; i++){
	console.log(i+" : "+split_arr[i]);
	if(split_arr[i]=='%#%@&'){
		flag=i;
	}
}

//setting patient personal details
document.getElementById("id").innerHTML = split_arr[0];
document.getElementById("name").innerHTML = split_arr[1];
document.getElementById("mo").innerHTML = split_arr[2];

//setting last five prescriptions of the patient
var str="";

//patient history will begin with '%#%@&' pattern so break at that point

var old_pres_table="<tr><td class='pres-table'>Revisit Date</td><td class='pres-table'>Medicines</td><td class='pres-table'>Followup Remark</td><td class='pres-table'>Charges</td></tr>";
for(var i=4;i<flag; i+=5){
	if(split_arr[i]=='%#%@&'){
		alert("in i : "+i);
		flag=i;
		break;
	}
	if(split_arr[i+1]=='%#%@&'){
		alert("in i+1 : "+(i+1));
		flag=i+1;
		break;
	}
	if(split_arr[i+2]=='%#%@&'){
		alert("in i+2 : "+(i+2));
		flag=i+2;
		break;
	}
	if(split_arr[i+3]=='%#%@&'){
		alert("in i+3 : "+(i+3));
		flag=i+3;
		break;
	}
	old_pres_table += "<tr><td class='pres-table'>" +split_arr[i+2]+ "</td><td class='pres-table'>"+split_arr[i]+"</td><td class='pres-table'>"+split_arr[i+1]+"</td><td class='pres-table'>"+split_arr[i+3]+"</td></tr>";
}
$('#lastfive').html(old_pres_table);
console.log("flag : "+flag);
//no history available for this patient
if(split_arr[flag]=='%#%@&'){
	console.log("No history available. div will be set when design is fixed");
}
/* document.getElementById("lastfive-info").innerHTML = str; */

</script>
</html>