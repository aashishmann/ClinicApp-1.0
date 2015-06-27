$(document).ready(function() {

	$('#addUser').click(function() {
		$('#addNewUser').show();
		$('#updateUserCred').hide();
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
								   +"<button id='changeCredentials' type='button' class='btn btn-success'>Update</button></td>"
								   +"<td class='queue-row'>"
								   +"<button type='button' class='btn btn-danger' onclick='deleteUser("+json_obj[i].id+")'>Delete</button>"
								   +"</td></tr>";
					}
					$('#addusertable').html(output);
				}
			},
			error : function(data) {
				console.log("data:"+data);
			}

		});
		
		console.log("show the div");
		//hide and show the required divs
		$('#addNewUser').hide();
		$('#updateUserCred').show();
	});

});