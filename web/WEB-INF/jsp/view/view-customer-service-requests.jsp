<%@ page session="false" import="java.util.Map" 
         import="edu.snhu.CustomerServiceRequest" import="edu.snhu.CustomerServiceRequestDatabase" %>
<%
    @SuppressWarnings("unchecked")
    Map<Integer, CustomerServiceRequest> csrDatabase =
    		CustomerServiceRequestDatabase.allRequests();
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <h2>Tickets</h2>
        <a href="<c:url value="customer-service">
            <c:param name="action" value="create-customer-service-request" />
        </c:url>">Create Ticket</a><br /><br />
        <%
        	if (csrDatabase.size() == 0)
            {
                %><i>There are no customer service requests in the system.</i><%
            }
            //loops across all customer service requests, and displays them to the web form
        	else
            {
                for(int id : csrDatabase.keySet())
                {
                    String idString = Integer.toString(id);
                    CustomerServiceRequest ticket = csrDatabase.get(id);
                    %>Ticket #<%= idString %>: <a href="<c:url value="customer-service">
                        <c:param name="action" value="view-customer-service-request" />
                        <c:param name="ticketId" value="<%= idString %>" />
                    </c:url>"><%= ticket.getSubject() %></a> (customer:
        <%= ticket.getCustomerName() %>)<br /><%
                }
            }
        %>,
        <hr><br><a href="<c:url value = "customer-service"/>">Main Menu</a>
    </body>
</html>
