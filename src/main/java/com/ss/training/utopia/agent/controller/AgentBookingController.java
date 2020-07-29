package com.ss.training.utopia.agent.controller;

import com.ss.training.utopia.agent.entity.Booking;
import com.ss.training.utopia.agent.service.AgentBookingService;
import com.ss.training.utopia.agent.service.AgentCancelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Trevor Huis in 't Veld
 */
@RestController
@RequestMapping(path = "/agent")
public class AgentBookingController {
	@Autowired AgentBookingService bookingService;

	@Autowired AgentCancelService cancelService;

    @PostMapping(path = "/booking")
	public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		String bookingResult = bookingService.createBooking(booking);
		switch (bookingResult) {
			case("Card Declined"):
				break;
			case("Flight Full"):
				status = HttpStatus.NO_CONTENT;
				booking = null;
				return new ResponseEntity<Booking>(booking, status);
			case("Internal Server Error"):
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				break;
			case("Flight Booked"):
				status = HttpStatus.CREATED;
				break;
		}

		return new ResponseEntity<Booking>(booking, status);
    }
    
    @PutMapping(path="/booking")
	public ResponseEntity<Booking> cancelBooking(@RequestBody Booking booking) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		String bookingResult = cancelService.cancelBooking(booking);
		switch (bookingResult) {
			case("Already Refunded"):
				break;
			case("Internal Server Error"):
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				break;
			case("Flight Cancelled"):
				status = HttpStatus.OK;
				break;
		}
		
		return new ResponseEntity<Booking>(booking, status);
	}

}