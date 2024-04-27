package com.onlinebankingsystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.onlinebankingsystem.entity.Ticket;

public interface TicketDao extends JpaRepository<Ticket, Integer> {
	
	Ticket findById(long id);
	
	List<Ticket> findByUserId(long id);
}
