<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ page import="java.util.*" import="java.util.Map" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Main Menu</title>
    </head>
    <body>
      <h1>Welcome</h1>
      <ul>
        <li>
          <a href="<c:url value="/search-tickets">
           <c:param name="action" value="view-tickets" />
           </c:url>">View Tickets</a>
        </li>
        <li>
          <a href="<c:url value="/search-tickets">
          <c:param name="action" value="search-tickets" />
          </c:url>">Search tickets</a>
        </li>
        <li>
          <a href="<c:url value="/customer-service">
          <c:param name="action" value="create-customer-service-request" />
          </c:url>">Customer Service</a>
        </li>
      </ul>
      <hr>
    </body>
</html>