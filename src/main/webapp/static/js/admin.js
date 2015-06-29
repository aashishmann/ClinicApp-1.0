$(document).ready(function() {

	$('#addUser').click(function() {
		$('#addNewUser').show();
		$('#updateUserCred').hide();
		$('#updateUserForm').hide();
	});
	
	$('#updateUser').click(function(){
		
		console.log("update called");
		
		$.ajax({
			type : "GET",
			url : "getAllUsers",
			contentType : "application/json; charset=utf-8",
			dataType : "html",
			success : function(data) {
				//do something with response data
				var json_obj = $.parseJSON(data);
				console.log("Data received : "+data);

				if(data=="null"){
					console.log("handle for null json");
					var output = "<tr><td style='font:20px normal arial;color : red;'>"
									+"Login Details Not Found.</td></tr>";					
					$('#addusertable').html(output);
				}
				else{
					console.log("setting table data");
					var output = "<tr><th class='queue-row'>Username</th>"
									+"<th class='queue-row'>Password</th>"
									+"<th class='queue-row'>Role</th></tr>";
					for ( var i in json_obj) {
						output+="<tr><td>" + json_obj[i].username + "</td>"
								   +"<td>" + json_obj[i].password + "</td>"
								   +"<td>" + json_obj[i].roleType + "</td>"
								   +"<td class='queue-row'>"
								   +"<button id='changeCredentials' type='button' class='btn btn-success' onclick='changeUser("+json_obj[i].id+")'>Update</button></td>"
								   +"<td class='queue-row'>"
								   +"<button type='button' class='btn btn-danger' onclick='deleteUser("+json_obj[i].id+")'>Delete</button>"
								   +"</td></tr>";
					}
					$('#addusertable').html(output);
				}
			},
			error : function(data) {
				console.log("data:"+data);
				window.location.reload();
			}

		});
		
		console.log("show the div");
		//hide and show the required divs
		$('#addNewUser').hide();
		$('#updateUserForm').hide();
		$('#updateUserCred').show();
	});

	$.changeUserCred = function(id){
		console.log(id+" : receieved ");
		
		$.ajax({
			type : "GET",
			url : "getUserById",
			data : "id=" + id,
			contentType : "application/json; charset=utf-8",
			dataType : "html",
			success : function(data) {
				var json_obj = $.parseJSON(data);
				console.log("Data received : "+data);
				
				if(data=="null"){
					console.log("handle for null json");
					var output = "<tr><td style='font:20px normal arial;color : red;'>"
									+"Login Details Not Found.</td></tr>";					
					$('#updateUserTable').html(output);
				}
				else{
					var output = "<tr><td><label for='id' class='form-label'>ID	:</label></td>"
						+"<td><input type='text' name='id' value='"+json_obj.id+"' class='inputfield' size='30' readonly></td></tr>"
						+"<tr><td><label for='username' class='form-label'>Username	:</label></td>"
									+"<td><input type='text' name='username' value='"+json_obj.username+"' class='inputfield' size='30' required autofocus></td></tr>"
								+"<tr><td><label for='password' class='form-label'>Password :</label></td>"
									+"<td><input type='text' name='password' value='"+json_obj.password+"' class='inputfield' size='30' required autofocus></td></tr>"
								+"<tr><td><label for='roletype' class='form-label'>Role :</label></td>"
									+"<td><select name='roleType'><option value='DOC'>Doctor</option>"
									+"<option value='MED'>Medicine</option>"
									+"<option value='REC'>Receptionist</option>"
									+"<option value='ASS'>Assitant</option>"
									+"<option value='ADM'>Admin</option></select></td></tr>"
								+"<tr><td></td>"
								+"<td><input type='submit' value='Save' class='btn btn-info'></td></tr>";
							
					$('#updateUserTable').html(output);
					console.log("submit done");
				}
			},
			error : function(data) {
				console.log("data:"+data);
				window.location.reload();
			}
		});
		
		$('#addNewUser').hide();
		$('#updateUserCred').hide();
		$('#updateUserForm').show();
	}; //changeUserCred
	
});