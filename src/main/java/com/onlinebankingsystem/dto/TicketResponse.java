package com.onlinebankingsystem.dto;

import java.util.ArrayList;
import java.util.List;
import com.onlinebankingsystem.entity.Ticket;


public class TicketResponse extends CommonApiResponse  {
	List<Ticket> ticketDetails = new ArrayList<>();

	public List<Ticket> getTicketDetails() {
		return ticketDetails;
	}

	public void setTicketDetails(List<Ticket> ticketDetails) {
		this.ticketDetails = ticketDetails;
	}


}
