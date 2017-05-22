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
.patient-visit{
	padding: 10px;
}
#lastfive{
	table-layout: fixed;
	width:100%;
}

</style>
</head>
<%@include file="receptionist.jsp"%>
<body>
	<div class="wrapper">
		<table class="wrapper-table" style="width:100%">
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
					<form class="patient-visit-form" action="addPrescription" method="post">
						<table id="patient-visit-table">
							<tr>
								<td class="patient-visit">Patient ID :</td>
								<td id="patient-id" class="patient-visit"></td>
							</tr>
							<tr>
								<td class="patient-visit">Medicines :</td>
								<td class="patient-visit"><input type="text" id="medicines" class="inputfield" size="30" name="medicines" placeholder="Enter Medicines"></td>
							</tr>
							<tr>
								<td class="patient-visit">FollowUp Remark :</td>
								<td class="patient-visit"><textarea id="followUp" class="inputfield"
									name="followupRemark" placeholder="Enter Followup Remark" rows="2"
									cols="33" style="resize: none;" data-role="none"></textarea></td>
							</tr>
							<tr>
								<td class="patient-visit">Revisit Date :</td>
								<td class="patient-visit"><input type="date" class="inputfield" name="revisitDate" min="2015-07-01"></td>
							</tr>
							<tr>
								<td class="patient-visit"><label for="amount" class="form-label">Charges :</label></td>
								<%--<td class="patient-visit"><input type="number" class="inputfield" name="charges" min="0"></td>--%>
                                <td>
                                    <select id="amount" class="month-year-select" name="charges">
                                        <option value="*">*</option>
                                        <option value="A">A</option>
                                        <option value="B">B</option>
                                        <option value="C">C</option>
                                        <option value="D">D</option>
                                        <option value="E">E</option>
                                        <option value="F">F</option>
                                        <option value="G">G</option>
                                        <option value="H">H</option>
                                        <option value="I">I</option>
                                        <option value="J">J</option>
                                        <option value="K">K</option>
                                        <option value="L">L</option>
                                    </select>
                                </td>
							</tr>
							<tr>
								<td class="patient-visit"><input type="submit" value="Submit" class="btn btn-info"></td>
								<td class="patient-visit"></td>
							</tr>
						</table>		
						<br>
					</form>
				</td>
				<!-- current visit details -->
			</tr>
			<tr>
				<td style="vertical-align:top">
					<div class="add_patient_header">
						<h2>Last Five Prescriptions : </h2>
					</div>
					<hr>
					<table id="lastfive" border="1">
					</table>
					<div id="no-prescriptions"></div>
					<br>
				</td>
				<!-- last five prescriptions -->
				
				<td style="vertical-align:top">
					<div class="add_patient_header">
						<h2>Medical Details : </h2>
					</div>
					<hr>
					<form class="patient-history" action="" method="get">
						<table id="patient-history-table" border="1">
						</table>
						<div id="no-history"></div>
						<br>
					</form>
				</td>
				<!-- medical details -->
			</tr>
		</table>
	</div>
	<!-- wrapper -->
</body>
<script type="text/javascript" src="js/prescription.js"></script>
<script>

//check if prescription is added by checking model attribute
var prescriptionAdded = '${prescriptionMessage}';
if(prescriptionAdded.length!=0){
	alert(prescriptionAdded);
}

//receive data about the patient
var x = window.name;
console.log("window.name : "+x);
var split_arr = x.split("+");
console.log("length : "+split_arr.length);
var flag=0;
for(var i=0; i<split_arr.length; i++){
	console.log(i+" : "+split_arr[i]);
	if(split_arr[i]=='%#%@&'){
		flag=i;
	}
}

//setting patient id for current visit details
//document.getElementById("patient-id").innerHTML = split_arr[0];
var patientid="<input type='text' id='patient-id' class='inputfield' size='30' name='patientId' value='"+split_arr[0]+"' readonly>";
$('#patient-id').html(patientid);


//setting patient personal details
document.getElementById("id").innerHTML = split_arr[0];
document.getElementById("name").innerHTML = split_arr[1];
document.getElementById("mo").innerHTML = split_arr[2];

//setting last five prescriptions of the patient
//patient history will begin with '%#%@&' pattern so break at that point
//if found the pattern just after patient details that means prescriptions also not available
if(split_arr[3]!='%#%@&'){
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
	
}
else{
	console.log("prescriptions for the patient are not available");
	$('#no-prescriptions').html("prescriptions Not Available.");
	$('#no-prescriptions').css({'color':'red','font':'normal 20px arial','text-align':'center'});
}

//check if no history available for this patient
if(split_arr[flag]=='%#%@&' && split_arr[flag+1].length==0){
	console.log("No history available for this patient");
	console.log("len"+split_arr[flag+1].length);
	$('#no-history').html("Patient History Not Available.");
	$('#no-history').css({'color':'red','font':'normal 20px arial','text-align':'center'});
}
else{
	//set patient history in table
	var output="";
	var j = flag+1;
	output += "<tr><td>ID :</td><td>"+split_arr[j++]+"</td></tr>";
	output += "<tr><td>Visit Date :</td><td>"+split_arr[j++]+"</td></tr>";
	output += "<tr><td>Visit Purpose :</td><td>"+split_arr[j++]+"</td></tr>";
	output += "<tr><td>Chief Complaints :</td><td>"+split_arr[j++]+"</td></tr>";
	output += "<tr><td>Mental Symptoms :</td><td>"+split_arr[j++]+"</td></tr>";
	output += "<tr><td>Physical Symptoms :</td><td>"+split_arr[j++]+"</td></tr>";
	output += "<tr><td>Investigation :</td><td>"+split_arr[j++]+"</td></tr>";
	output += "<tr><td>Family History :</td><td>"+split_arr[j++]+"</td></tr>";
	output += "<tr><td>Past History :</td><td>"+split_arr[j++]+"</td></tr>";
	output += "<tr><td>Thermal :</td><td>"+split_arr[j++]+"</td></tr>";
	output += "<tr><td>Desire :</td><td>"+split_arr[j++]+"</td></tr>";
	output += "<tr><td>Aversion :</td><td>"+split_arr[j++]+"</td></tr>";
	/* output += "<tr><td></td><td><input type='submit' value='Submit' class='btn btn-info'></td></tr>"; */
	
	//set the table so that it becomes visible
	$('#patient-history-table').html(output);
}
</script>
</html>