package edu.snhu;

import java.util.HashMap;
import java.util.Map;

public class TicketDatabase {
	private static Map<Integer, Ticket> tickets;

	static {
		tickets = new HashMap<>();
		tickets.put(1, new Ticket("Boston", "New York", 120));
		tickets.put(2, new Ticket("Manchester", "Detroit", 130));
		tickets.put(3, new Ticket("Washington", "Albany", 140));
	}
	
	public static void addTicket(Ticket ticket, int id) {
		tickets.put(id, ticket);
	}
	
	public static Map<Integer, Ticket> allTickets() {
		return tickets;
	}
}

