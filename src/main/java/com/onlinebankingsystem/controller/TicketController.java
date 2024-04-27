package com.onlinebankingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlinebankingsystem.dto.CommonApiResponse;
import com.onlinebankingsystem.dto.TicketRequestDto;
import com.onlinebankingsystem.dto.TicketResponse;
import com.onlinebankingsystem.dto.UserListResponseDto;
import com.onlinebankingsystem.resource.TicketResource;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/ticket/")
@CrossOrigin
public class TicketController {
	
	@Autowired
	private TicketResource ticketResource;

	@GetMapping("/fetch/all")
	@Operation(summary = "Api to get ticket By Role")
	public ResponseEntity<TicketResponse> fetchAllTicket() {
		return ticketResource.fetchAllTicket();
	}

	@GetMapping("/fetch/id")
	@Operation(summary = "Api to get ticket By user  id")
	public ResponseEntity<TicketResponse> fetchById(@RequestParam("id") int id) {
		return ticketResource.fetchById(id);
	}
	@PostMapping("/add")
	@Operation(summary = "Api to get ticket By user  id")
	public ResponseEntity<CommonApiResponse> addTicket(@RequestBody TicketRequestDto ticketRequestDto) {
		return ticketResource.addTicket(ticketRequestDto);
	}
}
