<%@ page session="false"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Ticket Search</title>
	</head>
	<body>
	  <h2>Ticket Search</h2>
		<form method="POST" action="search-tickets">
		<input type="hidden" name="action" value="ticket-search">
		Departure:<br>
		<input type="text" name="departure"><br><br>
		Destination:<br>
		<input type="text" name="destination"><br><br>
		<input type="submit" value="Search">
		</form>
		<hr><br><a href="<c:url value = "customer-service"/>">Main Menu</a>
	</body>
</html>