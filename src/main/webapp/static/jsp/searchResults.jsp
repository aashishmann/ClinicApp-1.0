<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<%@include file="receptionist.jsp" %>

<body>
<!-- Search Results to be displayed -->
		<div class="search_results" id="search_results">
			<div class="add_patient_header">
				<h2>Search Results</h2>
			</div>
			<hr>
			<table>
				<tr>
					<th class="queue-row">ID</th>
					<th class="queue-row">Name</th>
				</tr>
				<c:forEach items="${patientList}" var="patientList">
					<tr>
						<td class="queue-row">${patientList.id}</td>
						<td class="queue-row">${patientList.firstname}
							${patientList.lastname}</td>
						<td class="queue-row">
							<button type="button" class="btn btn-success">View</button>
						</td>
						<td class="queue-row">
							<button type="button" class="btn btn-danger"
								onclick="deletePatient(${patientList.id})">Delete</button>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<!-- search results -->
</body>
<script type="text/javascript" src="js/searchResults.js"></script>
</html>