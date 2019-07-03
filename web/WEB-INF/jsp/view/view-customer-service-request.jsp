<%@ page session="false" import="edu.snhu.CustomerServiceRequest" import="edu.snhu.Attachment"%>
<%
    String ticketId = (String)request.getAttribute("ticketId");
    CustomerServiceRequest csRequest = (CustomerServiceRequest)request.getAttribute("ticket");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <h2>Ticket #<%= ticketId %>: <%= csRequest.getSubject() %></h2>
        <i>Customer Name - <%= csRequest.getCustomerName() %></i><br /><br />
        <%= csRequest.getBody() %><br /><br />
        <%
            if(csRequest.getNumberOfAttachments() > 0)
            {
                %>Attachments: <%
                int i = 0;
                for(Attachment a : csRequest.getAttachments())
                {
                    if(i++ > 0)
                        out.print(", ");
                    %><a href="<c:url value="customer-service">
                        <c:param name="action" value="download-attachment" />
                        <c:param name="ticketId" value="<%= ticketId %>" />
                        <c:param name="attachment" value="<%= a.getName() %>" />
                    </c:url>"><%= a.getName() %></a><%
                }
                %><br /><br /><%
            }
        %>
        <hr/>
        <ul>
          <li>
            <a href="<c:url value="/customer-service">
        	  <c:param name="action" value="view-customer-service-requests" />
            </c:url>">See Filed Requests</a>
          </li>
            <a href="<c:url value="customer-service" />">Return to list tickets</a>
          <li>
          </li>
        </ul>

        <hr><br><a href="<c:url value = "customer-service"/>">Main Menu</a>
    </body>
</html>
