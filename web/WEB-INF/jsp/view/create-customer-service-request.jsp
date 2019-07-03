<%@ page session="false" %>
<!-- This JavaServer Page prompts the user to input the information necessary
to create a new CustomerServiceRequest object -->
<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <h2>Create a Ticket</h2>
        <form method="POST" action="customer-service" enctype="multipart/form-data">
            <input type="hidden" name="action" value="create"/>
            Your Name<br/>
            <input type="text" name="customer-name"><br/><br/>
            Subject<br/>
            <input type="text" name="subject"><br/><br/>
            Body<br/>
            <textarea name="body" rows="5" cols="30"></textarea><br/><br/>
            <b>Attachments</b><br/>
            <input type="file" name="file1"/><br/><br/>
            <input type="submit" value="Submit"/>
        </form>
        <hr><br><a href="<c:url value = "customer-service"/>">Main Menu</a>
    </body>
</html>
