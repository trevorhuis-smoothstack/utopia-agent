package com.ss.training.utopia.agent.controller;

import com.ss.training.utopia.agent.entity.Flight;
import com.ss.training.utopia.agent.entity.FlightQuery;
import com.ss.training.utopia.agent.service.AgentReadFlightsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Trevor Huis in 't Veld
 */
@RestController
@RequestMapping(path = "/agent")
public class AgentFlightController {

    @Autowired AgentReadFlightsService service;
    
    @GetMapping(path="/flights")
    public ResponseEntity<Flight[]> getAllAvailableFlights(@RequestBody FlightQuery fq) {
		Flight[] flightArray = null;
		HttpStatus status = HttpStatus.OK;
		flightArray = service.readAvailableFlights(fq);
		if (flightArray == null)
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		else if (flightArray.length == 0) // no flights exist in the database
			status = HttpStatus.NO_CONTENT;
		return new ResponseEntity<Flight[]>(flightArray, status);
	}
}