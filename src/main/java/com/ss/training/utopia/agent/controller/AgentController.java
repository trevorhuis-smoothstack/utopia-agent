package com.ss.training.utopia.agent.controller;

import java.util.List;

import com.ss.training.utopia.agent.entity.Booking;
import com.ss.training.utopia.agent.entity.Flight;
import com.ss.training.utopia.agent.service.AgentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping(path = "/agent")
public class AgentController {

	@Autowired
	AgentService service;


    @GetMapping(path="/airports")
    public ResponseEntity<Airport[]> getAllAirports() {
		List<Airport> airportList = null;
		Airport[] airportArray = null;

	@PostMapping(path = "/booking")
	public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		String bookingResult = service.createBooking(booking);
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
			case("Charge Created"):
				status = HttpStatus.CREATED;
				break;
		}

		return new ResponseEntity<Booking>(booking, status);
	}

	@GetMapping(path = "/bookings/{agentId}")
	public ResponseEntity<Booking[]> getAllBookingsByAgent(@PathVariable long agentId) {
		List<Booking> bookingList = null;
		Booking[] bookingArray = null;
		HttpStatus status = HttpStatus.OK;
		bookingList = service.readAgentBookings(agentId);
		if (bookingList == null)
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		else if (bookingList.size() == 0) // no bookings exist in the database
			status = HttpStatus.NO_CONTENT;
		else
			bookingArray = bookingList.toArray(new Booking[bookingList.size()]);
		return new ResponseEntity<Booking[]>(bookingArray, status);
	}

	@PutMapping(path="/booking")
	public ResponseEntity<Booking> cancelBooking(@RequestBody Booking booking) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		String bookingResult = service.createBooking(booking);
		switch (bookingResult) {
			case("Already Refunded"):
				break;
			case("Internal Server Error"):
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				break;
			case("Refund Processed"):
				status = HttpStatus.OK;
				break;
		}
		
		return new ResponseEntity<Booking>(booking, status);
	}

	@GetMapping(path="/flights")
    public ResponseEntity<Flight[]> getAllAvailableFlights() {
		List<Flight> flightList = null;
		Flight[] flightArray = null;
		HttpStatus status = HttpStatus.OK;
		flightList = service.readAvailableFlights();
		if (flightList == null)
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		else if (flightList.size() == 0) // no flights exist in the database
			status = HttpStatus.NO_CONTENT;
		else
        	flightArray = flightList.toArray(new Flight[flightList.size()]);
		return new ResponseEntity<Flight[]>(flightArray, status);
	}
}
