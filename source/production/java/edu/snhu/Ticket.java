package edu.snhu;

import java.util.HashMap;
import java.util.Map;

public class Ticket {
	private String departure;
	private String destination;
	private int seats;
	
	public void setDeparture(String str) {
		this.departure = str;
	}
	
	public void setDestination(String str) {
		this.destination = str;
	}
	
	public void setSeats(int n) {
		this.seats = n;
	}
	
	public String getDeparture() {
		return this.departure;
	}
	
	public String getDestination() {
		return this.destination;
	}
	
	public int getSeats() {
		return this.seats;
	}

	Ticket(String departure, String destination, int seats) {
		setDeparture(departure);
		setDestination(destination);
		setSeats(seats);
	}
}