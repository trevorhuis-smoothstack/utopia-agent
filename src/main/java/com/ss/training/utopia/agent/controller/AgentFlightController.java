package com.ss.training.utopia.agent.controller;

import com.ss.training.utopia.agent.entity.Flight;
import com.ss.training.utopia.agent.entity.FlightQuery;
import com.ss.training.utopia.agent.service.AgentReadFlightsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Trevor Huis in 't Veld
 */
@RestController
@RequestMapping(path = "/agent")
public class AgentFlightController {

	@Autowired
	AgentReadFlightsService service;

	@GetMapping(path = "/flights")
	public ResponseEntity<Flight[]> getAllAvailableFlights(@RequestParam() Float price,
			@RequestParam(required = false, defaultValue = "") String departId,
			@RequestParam(required = false, defaultValue = "") String arriveId,
			@RequestParam(required = false, defaultValue = "1900-01-01") String dateBegin,
			@RequestParam(required = false, defaultValue = "2100-01-01") String dateEnd) {
		FlightQuery fq = new FlightQuery(departId, arriveId, dateBegin, dateEnd, price);
		Flight[] flightArray = null;
		HttpStatus status = HttpStatus.OK;
		flightArray = service.readAvailableFlights(fq);
		if (flightArray == null)
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		else if (flightArray.length == 0) // no flights exist in the database
			status = HttpStatus.NO_CONTENT;
		return new ResponseEntity<Flight[]>(flightArray, status);
	}

	@GetMapping(path = "/flights/premier")
	public ResponseEntity<Flight[]> getAllAvailableFlights() {
		Flight[] flightArray = null;
		HttpStatus status = HttpStatus.OK;
		flightArray = service.readPremierFlights();
		if (flightArray == null)
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		else if (flightArray.length == 0) // no flights exist in the database
			status = HttpStatus.NO_CONTENT;
		return new ResponseEntity<Flight[]>(flightArray, status);
	}
}