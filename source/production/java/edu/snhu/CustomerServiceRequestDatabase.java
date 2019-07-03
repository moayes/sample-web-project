package edu.snhu;

import java.util.HashMap;
import java.util.Map;

public class CustomerServiceRequestDatabase {
	private static Map<Integer, CustomerServiceRequest> requests = new HashMap<>();

	public static Map<Integer, CustomerServiceRequest> allRequests(){
		return requests;
	}

	public static void addRequest(CustomerServiceRequest request, int id) {
		requests.put(id, request);
	}
}
