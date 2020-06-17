package com.ss.training.utopia.agent.controller;

import java.util.List;

import com.ss.training.utopia.agent.entity.Airport;
import com.ss.training.utopia.agent.service.AgentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Trevor Huis in 't Veld
 */
@RestController
@RequestMapping(path="/utopia/agent")
public class AgentController {

    @Autowired
	AgentService service;

    @GetMapping(path="/airports")
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
}
