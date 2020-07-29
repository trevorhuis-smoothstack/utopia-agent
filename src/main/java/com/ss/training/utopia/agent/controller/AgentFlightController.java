package com.ss.training.utopia.agent.controller;

import com.ss.training.utopia.agent.entity.Flight;
import com.ss.training.utopia.agent.entity.FlightQuery;
import com.ss.training.utopia.agent.service.AgentReadFlightsService;
import com.ss.training.utopia.agent.entity.Airport;
import com.ss.training.utopia.agent.service.AgentReadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

/**
 * @author Trevor Huis in 't Veld
 */
@RestController
@RequestMapping(path = "/agent")
public class AgentFlightController {

	@Autowired
	AgentReadService readService;
	
	@Autowired
	AgentReadFlightsService flightService;

	@GetMapping(path = "/flights")
	public ResponseEntity<Flight[]> getAllAvailableFlights(@RequestParam() Float price,
			@RequestParam(required = false, defaultValue = "") String departId,
			@RequestParam(required = false, defaultValue = "") String arriveId,
			@RequestParam(required = false, defaultValue = "today") String dateBegin,
			@RequestParam(required = false, defaultValue = "2100-01-01") String dateEnd) {

		if(dateBegin.equals("today")) {
			LocalDate today = LocalDate.now();
			String todaysDate = today.toString();
			dateBegin = todaysDate;
		}

		FlightQuery fq = new FlightQuery(departId, arriveId, dateBegin, dateEnd, price);
		Flight[] flightArray = null;
		flightArray = flightService.readAvailableFlights(fq);

		HttpStatus status = HttpStatus.OK;
		if (flightArray == null)
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		else if (flightArray.length == 0) // no flights with that criteria exist in the database
			status = HttpStatus.NO_CONTENT;
		return new ResponseEntity<Flight[]>(flightArray, status);
	}

	@GetMapping(path = "/flights/premier")
	public ResponseEntity<Flight[]> getAllAvailableFlights() {
		Flight[] flightArray = null;
		HttpStatus status = HttpStatus.OK;
		flightArray = flightService.readPremierFlights();
		if (flightArray == null)
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		else if (flightArray.length == 0) // no flights exist in the database
			status = HttpStatus.NO_CONTENT;
		return new ResponseEntity<Flight[]>(flightArray, status);
	}

	
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

	

	@GetMapping(path = "/flights/{agentId}/traveler/{travelerId}")
	public ResponseEntity<Flight[]> getAllflightsByAgentAndTraveler(@PathVariable long agentId, @PathVariable long travelerId) {
		Flight[] flightArray = null;
		HttpStatus status = HttpStatus.OK;
		flightArray = readService.readTravelerFlightsByAgent(agentId, travelerId);
		if (flightArray == null)
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		else if (flightArray.length == 0) // no flights exist in the database
			status = HttpStatus.NO_CONTENT;
		return new ResponseEntity<Flight[]>(flightArray, status);
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

	
}