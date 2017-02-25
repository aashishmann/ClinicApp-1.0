<!DOCTYPE html>
<html>
<head>
<title>Dahiya Clinic App</title>
<!-- core css -->
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<!-- custom css for this file -->
<link rel="stylesheet" type="text/css" href="css/isignin.css">
<link rel="stylesheet" type="text/css" href="css/ifooter.css">
</head>
<body>

	<div class="container">

		<form class="form-signin" action="validateLogin" method="GET">
			<h2 class="form-signin-heading">Login</h2>
			<label for="inputUname" class="sr-only">Username</label> <input
				type="text" id="inputUname" class="form-control"
				placeholder="Enter Username" name="username" required autofocus>
			<label for="inputPassword" class="sr-only">Password</label> <input
				type="password" id="inputPassword" class="form-control"
				placeholder="Enter Password" name="password" required>
			<p style="color: red; text-align: center;">${error}</p>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
				in</button>
		</form>

	</div>
	<!-- /container -->

	<footer class="footer">
		<div class="container">
			<p class="text-muted">All rights reserved. Dahiya Clinic.</p>
		</div>
	</footer>

	<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
</body>
</html>