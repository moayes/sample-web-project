package edu.snhu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "TicketSearchServlet",
        urlPatterns = {"/search-tickets"},
        loadOnStartup = 1
)

public class TicketSearchServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String action = request.getParameter("action");
		
		if (action == null) {
			action = "main-menu";
		}

		switch(action) {
			case "create-ticket":
				break;
			case "view-tickets":
				this.viewAllTickets(request, response);
				break;
			case "search-tickets":
				this.viewTicketSearch(request, response);
				break;
			case "main-menu":
			default:
				this.mainMenu(request, response);
				
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action==null) {
			action = "main-menu";
		}

		switch(action) {
		  case "ticket-search":
			  this.processTicketSearch(request, response);
			  break;
		  default:
			  this.mainMenu(request, response);
			  break;
		}
	}

	public void processTicketSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String departure = request.getParameter("departure").toLowerCase();
		String destination = request.getParameter("destination").toLowerCase();
		Map<Integer, Ticket> results = new HashMap<>();
		
		for(int id : TicketDatabase.allTickets().keySet()) {
			Ticket ticket = TicketDatabase.allTickets().get(id);
			if (ticket.getDeparture().toLowerCase().equals(departure) || ticket.getDestination().toLowerCase().equals(destination)) {
				results.put(id, ticket);
			}
		}
		
		request.setAttribute("search-tickets-database", results);
		request.getRequestDispatcher("/WEB-INF/jsp/view/view-tickets.jsp").forward(request,response);
	}
	
	public void viewAllTickets(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("/WEB-INF/jsp/view/view-tickets.jsp")
			.forward(request,response);
	}
	
	public void viewTicketSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("/WEB-INF/jsp/view/search-tickets.jsp").forward(request,response);
	}
	
	public void mainMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("/WEB-INF/jsp/view/homepage.jsp").forward(request,response);
	}
}
