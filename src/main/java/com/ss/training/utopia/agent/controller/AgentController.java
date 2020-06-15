package com.ss.training.utopia.agent.controller;

import java.util.List;

import com.ss.training.utopia.agent.entity.Booking;
import com.ss.training.utopia.agent.service.AgentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Trevor Huis in 't Veld
 */
@RestController
@RequestMapping(path="/agent")
public class AgentController {

    @Autowired
	AgentService service;

	@GetMapping(path="/bookings/{agentId}")
    public ResponseEntity<Booking[]> getAllBookingsByAgent(@PathVariable long agentId) {
		List<Booking> bookingList = null;
		Booking[] bookingArray = null;
		HttpStatus status = HttpStatus.OK;
		bookingList = service.readAgentBookings(agentId);
		if (bookingList.size() == 0) // no bookings exist in the database
			status = HttpStatus.NO_CONTENT;
		else
        bookingArray = bookingList.toArray(new Booking[bookingList.size()]);
		return new ResponseEntity<Booking[]>(bookingArray, status);
	}








































	@PutMapping(path="/booking")
	public ResponseEntity<Booking> cancelBooking(@RequestBody Booking booking) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if(booking == null || booking.getTravelerId() == null || booking.getBookerId() == null || booking.getStripeId() == null)
				return new ResponseEntity<Booking>(booking, HttpStatus.BAD_REQUEST);

		service.cancelBooking(booking);
		status = HttpStatus.OK;
		
		return new ResponseEntity<Booking>(booking, status);
	}













}
