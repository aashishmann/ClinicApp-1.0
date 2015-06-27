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
<link rel="stylesheet" type="text/css" href="css/admin.css">
<link rel="stylesheet" type="text/css" href="css/ifooter.css">
<script type="text/javascript">
	var check = '${addLoginDetails}';
	if (check.length != 0) {
		alert('${addLoginDetails}');
	}

	function deleteUser(id) {
		console.log("id : " + id);
		jQuery.ajax({
		    'type': 'GET',
		    'url': "deleteUser",
		    'data': "id="+id,
		    'contentType': 'application/json',
		    'success': function(data) {
		    	console.log("response received:"+data);
		    	if(data=="true"){
		    		alert("User Deleted!!");
		    		//same page will be loaded and same div also
		    		window.location.reload();
		    	}
		    	else if(data=="false"){
		    		//some error occured while deleting
		    		alert("Unable to delete. Please try again later.");
		    		window.location.reload();
		    	}
		    	else{
		    		window.location.replace("admin");
		    	}
		    	//window.location.replace("receptionist");
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
						<li><a href="#" id="addUser">Add New User</a></li>
						<li><a href="#" id="updateUser">Update User Credentials</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li><a href="logout">Log Out</a></li>
					</ul>
				</div>
				<!-- nav-collapse -->
			</div>
		</nav>
		<!-- fixed navbar -->

		<div id="addNewUser" class="addNewUser">
			<div class="search_details_header">
				<h2>Add New User</h2>
			</div>
			<hr>
			<form class="add-user-form" action="addUserLogin" method="post">
				<table class="add-user-table">
					<tr>
						<div class="form-group">
							<td><label for="username" class="form-label">Username
									:</label></td>
							<td><input type="text" id="username" class="inputfield"
								size="30" name="username" placeholder="Enter Username" required></td>
						</div>
					</tr>
					<tr>
						<div class="form-group">
							<td><label for="password" class="form-label">Password
									:</label></td>
							<td><input type="password" id="password" class="inputfield"
								size="30" name="password" placeholder="Enter Password" required></td>
						</div>
					</tr>
					<tr>
						<div class="form-group">
							<td><label for="roletype" class="form-label">Role :</label></td>
							<td><select name="roleType">
									<option value="DOC">Doctor</option>
									<option value="MED">Medicine</option>
									<option value="REC">Receptionist</option>
									<option value="ASS">Assitant</option>
									<option value="ADM">Admin</option>
							</select>
						</div>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Submit" class="btn btn-info"></td>
					</tr>
				</table>
			</form>
		</div>

		<div id="updateUserCred" class="updateUserCred">
			<div class="search_details_header">
				<h2>Update User Credentials</h2>
			</div>
			<hr>
			<table id="addusertable">
			</table>
		</div>

	</div>
	<!-- /container -->

	<footer class="footer">
		<div class="container">
			<p class="text-muted">All rights reserved. **** Clinic.</p>
		</div>
	</footer>

	<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/admin.js"></script>
</body>
</html>