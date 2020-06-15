package com.ss.training.utopia.agent.controller;

import java.util.List;

import com.ss.training.utopia.agent.entity.Airport;
import com.ss.training.utopia.agent.entity.Booking;
import com.ss.training.utopia.agent.service.AgentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author Trevor Huis in 't Veld
 */
@RestController
@RequestMapping(path="/agent")
public class AgentController {

    @Autowired
	AgentService service;

    @GetMapping(path="/airport")
    public ResponseEntity<Airport[]> getAllAirports() {
		List<Airport> airportList = null;
		Airport[] airportArray = null;
		HttpStatus status = HttpStatus.OK;
		airportList = service.readAirports();
		if (airportList == null) // no airports exist in the database
			status = HttpStatus.NO_CONTENT;
		else
        airportArray = airportList.toArray(new Airport[airportList.size()]);
		return new ResponseEntity<Airport[]>(airportArray, status);
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
