package com.ss.training.utopia.agent.controller;

import com.ss.training.utopia.agent.entity.Airport;
import com.ss.training.utopia.agent.entity.Booking;
import com.ss.training.utopia.agent.entity.Flight;
import com.ss.training.utopia.agent.entity.User;
import com.ss.training.utopia.agent.service.AgentBookingService;
import com.ss.training.utopia.agent.service.AgentCancelService;
import com.ss.training.utopia.agent.service.AgentReadService;
import com.ss.training.utopia.agent.service.UserService;

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

	@Autowired AgentReadService readService;

	@Autowired AgentBookingService bookingService;

	@Autowired AgentCancelService cancelService;

	@Autowired UserService userService;


    @GetMapping(path="/airports")
    public ResponseEntity<Airport[]> getAllAirports() {
		Airport[] airportArray = null;
		HttpStatus status = HttpStatus.OK;
		airportArray = readService.readAirports();
		if (airportArray == null)
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		else if (airportArray.length == 0) // no airports exist in the database
			status = HttpStatus.NO_CONTENT;
		return new ResponseEntity<Airport[]>(airportArray, status);
	}

	@GetMapping(path="/flights")
    public ResponseEntity<Flight[]> getAllAvailableFlights() {
		Flight[] flightArray = null;
		HttpStatus status = HttpStatus.OK;
		flightArray = readService.readAvailableFlights();
		if (flightArray == null)
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		else if (flightArray.length == 0) // no flights exist in the database
			status = HttpStatus.NO_CONTENT;
		return new ResponseEntity<Flight[]>(flightArray, status);
	}

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

	@GetMapping(path = "/bookings/{agentId}")
	public ResponseEntity<Booking[]> getAllBookingsByAgent(@PathVariable long agentId) {
		Booking[] bookingArray = null;
		HttpStatus status = HttpStatus.OK;
		bookingArray = readService.readAgentBookings(agentId);
		if (bookingArray == null)
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		else if (bookingArray.length == 0) // no bookings exist in the database
			status = HttpStatus.NO_CONTENT;
		return new ResponseEntity<Booking[]>(bookingArray, status);
	}

	@GetMapping(path = "/flight/{flightId}")
	public ResponseEntity<Flight> getFlight(@PathVariable Long flightId) {
		Flight flight = null;
		HttpStatus status = HttpStatus.OK;
		flight = readService.readFlight(flightId);

		if (flight == null) {
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<Flight>(flight, status);
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

	@GetMapping(path = "/user/username/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		User user = null;
		HttpStatus status = HttpStatus.OK;

		try {
			user = userService.getUserByUsername(username);
        } catch (Throwable t) {
            return new ResponseEntity<User>(user, HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
		if (user == null) {
			status = HttpStatus.NO_CONTENT;
		}

		return new ResponseEntity<User>(user, status);
	}

	@GetMapping(path = "/traveler/{username}")
	public ResponseEntity<User> getTravelerByUsername(@PathVariable String username) {
		User user = null;
		HttpStatus status = HttpStatus.OK;

		try {
			user = userService.getUserAndCheckTraveler(username);
        } catch (Throwable t) {
            return new ResponseEntity<User>(user, HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
		if (user == null) {
			status = HttpStatus.NO_CONTENT;
		}

		return new ResponseEntity<User>(user, status);
	}

	@GetMapping(path = "/user/id/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable Long userId) {
		User user = null;
		HttpStatus status = HttpStatus.OK;

		try {
			user = userService.getUserById(userId);
        } catch (Throwable t) {
            return new ResponseEntity<User>(user, HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
		if (user == null) {
			status = HttpStatus.NOT_FOUND;
		}

		return new ResponseEntity<User>(user, status);
	}

	@PostMapping(path = "/user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		HttpStatus status = HttpStatus.CREATED;
		try {
			user = userService.createUser(user);
		} catch (Throwable t) {
			user = null;
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<User>(user, status);

	}

	
}
