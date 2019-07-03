package edu.snhu;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(
        name = "customerServiceServlet",
        urlPatterns = {"/customer-service"},
        loadOnStartup = 1
)

@MultipartConfig(
        // 5 MB
        fileSizeThreshold = 5_242_880,
        // 20 MB
        maxFileSize = 20_971_520L,
        // 40 MB
        maxRequestSize = 41_943_040L
)

public class CustomerServiceServlet extends HttpServlet {
    private volatile int TICKET_ID_SEQUENCE = 1;
    private Map<Integer, CustomerServiceRequest> ticketDatabase = new LinkedHashMap<>();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "main-menu";
        }

        switch(action) {
            case "create-customer-service-request":
                this.showCustomerServiceRequestForm(request, response);
                break;
            case "view-customer-service-request":
                this.viewCustomerServiceRequest(request, response);
                break;
            case "download-attachment":
                this.downloadAttachment(request, response);
                break;    
            case "view-customer-service-requests":
            	  this.listCustomerServiceRequests(request, response);
            	  break;
            default:
            	  this.showMainMenu(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch(action) {
            case "create":
                this.createCustomerServiceRequest(request, response);
                break;
            case "list":
            default:
                response.sendRedirect("customer-service");
                break;
        }
    }

    private void showCustomerServiceRequestForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/view/create-customer-service-request.jsp")
               .forward(request, response);
    }

    private void showMainMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.getRequestDispatcher("/WEB-INF/jsp/view/homepage.jsp")
        	   .forward(request, response);
    }
    
    private void viewCustomerServiceRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String idString = request.getParameter("ticketId");
        CustomerServiceRequest csRequest = this.getTicket(idString, response);
        if (csRequest == null) {
            return;
        }
        
        request.setAttribute("ticketId", idString);
        request.setAttribute("ticket", csRequest);
        request.getRequestDispatcher("/WEB-INF/jsp/view/view-customer-service-request.jsp")
               .forward(request, response);
    }
    
    private void downloadAttachment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("ticketId");
        CustomerServiceRequest ticket = this.getTicket(idString, response);
        if (ticket == null) {
            return;
        }
        
        String name = request.getParameter("attachment");
        if (name == null) {
            response.sendRedirect("customer-service?action=view&ticketId=" + idString);
            return;
        }

        Attachment attachment = ticket.getAttachment(name);
        if (attachment == null) {
            response.sendRedirect("customer-service?action=view&ticketId=" + idString);
            return;
        }

        response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getName());
        response.setContentType("application/octet-stream");

        ServletOutputStream stream = response.getOutputStream();
        stream.write(attachment.getContents());
    }

    private void listCustomerServiceRequests(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("ticketDatabase", this.ticketDatabase);
        request.getRequestDispatcher("/WEB-INF/jsp/view/view-customer-service-requests.jsp")
               .forward(request, response);
    }

    private void createCustomerServiceRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerServiceRequest ticket = new CustomerServiceRequest();
        ticket.setCustomerName(request.getParameter("customer-name"));
        ticket.setSubject(request.getParameter("subject"));
        ticket.setBody(request.getParameter("body"));

        Part filePart = request.getPart("file1");
        if (filePart != null && filePart.getSize() > 0) {
            Attachment attachment = this.processAttachment(filePart);
            if (attachment != null)
                ticket.addAttachment(attachment);
        }

        int id;
        synchronized(this) {
            id = this.TICKET_ID_SEQUENCE++;
            CustomerServiceRequestDatabase.addRequest(ticket, id);
            
        }

        response.sendRedirect("customer-service?action=view&ticketId=" + id);
    }

    private Attachment processAttachment(Part filePart) throws IOException {
        InputStream inputStream = filePart.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int read;
        final byte[] bytes = new byte[1024];

        while((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }

        Attachment attachment = new Attachment();
        attachment.setName(filePart.getSubmittedFileName());
        attachment.setContents(outputStream.toByteArray());

        return attachment;
    }

    private CustomerServiceRequest getTicket(String idString, HttpServletResponse response) throws ServletException, IOException {
        if (idString == null || idString.length() == 0) {
            response.sendRedirect("customer-service");
            return null;
        }

        try {
            CustomerServiceRequest ticket = CustomerServiceRequestDatabase.allRequests().get(Integer.parseInt(idString));
            if (ticket == null) {
                response.sendRedirect("customer-service");
                return null;
            }
            return ticket;
        }
        catch(Exception e) {
            response.sendRedirect("customer-service");
            return null;
        }
    }
}