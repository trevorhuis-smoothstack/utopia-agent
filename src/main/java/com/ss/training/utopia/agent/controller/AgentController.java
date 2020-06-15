package com.ss.training.utopia.agent.controller;


import com.ss.training.utopia.agent.entity.Booking;
import com.ss.training.utopia.agent.service.AgentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author Trevor Huis in 't Veld
 */
@RestController
@RequestMapping(path="/agent")
public class AgentController {

    @Autowired
	AgentService service;

	@PostMapping(path="/booking")
	public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if(booking == null || booking.getTravelerId() == null || booking.getBookerId() == null || booking.getStripeId() == null)
				return new ResponseEntity<Booking>(booking, HttpStatus.BAD_REQUEST);

		service.createBooking(booking);
		status = HttpStatus.CREATED;
		
		return new ResponseEntity<Booking>(booking, status);
	}
}
