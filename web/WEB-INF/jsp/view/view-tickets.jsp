<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ page import="edu.snhu.Ticket" import="java.util.*" import="java.util.Map" import="edu.snhu.TicketDatabase"%>

<!DOCTYPE html>
<html>
    <head>
        <title>View Tickets</title>
    </head>
    <body>
    <h2>Available Ticket(s)</h2>
    <%
      Map<Integer, Ticket> tickets = (Map<Integer, Ticket>)request.getAttribute("search-tickets-database");
      if (tickets == null) {
    	  tickets = TicketDatabase.allTickets();
      }
    %>
    <%
    	if (tickets.size() > 0) {
    		for(int ticketId : tickets.keySet()) {
    			Ticket ticket = tickets.get(ticketId);
    			String departure = ticket.getDeparture();
    			String destination = ticket.getDestination();
    			int seats = ticket.getSeats();
    %>

    [<%= ticketId %>] Departure: <%= departure%>, Destination: <%=destination %>. Seats available: <%=seats %><br/><br/>

    <%
    		}   		
    	} else {
    		%>There are no tickets available at this time.<%
    	}
    %>

    <hr><br><a href="<c:url value = "customer-service"/>">Main Menu</a>

    </body>
</html>