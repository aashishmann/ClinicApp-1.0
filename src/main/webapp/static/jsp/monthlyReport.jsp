<!-- Monthly Report -->
		<div id="monthlyReport" class="monthlyReport">
			<div class="add_patient_header">
				<h2 style="text-align: center;">Monthly Report</h2>
			</div>
			<hr>
			
			<!-- Month selection form for which report is to be generated -->
			<!-- <form action="generateMonthlyReport" method="get"> -->
			<form name="mReportForm" id="mReportForm" action="#" method="get">
			<table>
					<tr>
						<div class="form-group">
							<td><label for="monthname" class="form-label">Month:</label></td>
							<td>
								<select id="month" class="month-year-select" name="month">
									<option value="1">Jan</option>
									<option value="2">Feb</option>
									<option value="3">Mar</option>
									<option value="4">Apr</option>
									<option value="5">May</option>
									<option value="6">Jun</option>
									<option value="7">Jul</option>
									<option value="8">Aug</option>
									<option value="9">Sep</option>
									<option value="10">Oct</option>
									<option value="11">Nov</option>
									<option value="12">Dec</option>
								</select>
							</td>
								
							<td><label for="yearname" class="form-label">Year:</label></td>
							<td>
								<select id="year" class="month-year-select" name="year">
									<script>
										  var myDate = new Date();
										  var year = myDate.getFullYear();
										  for(var i = 2010; i < year+1; i++){
											  document.write('<option value="'+i+'">'+i+'</option>');
										  }
	  								</script>
  								</select>
							</td>	
						</div>
					</tr>
					<tr>
						<td></td>
						<!-- <td><input type="submit" value="Submit" class="btn btn-info"></td> -->
						<td><input type="button" id="submitButton" value="Submit" onclick="monthlyReportCall()" class="btn btn-info"></td>
					</tr>
			</table>
			</form>	<!-- monthly report form -->
			<br><br>
			<table id="monthlyReportCard" align="center">
			</table>
			
		</div>