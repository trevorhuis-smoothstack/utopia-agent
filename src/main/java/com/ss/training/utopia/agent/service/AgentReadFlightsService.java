package com.ss.training.utopia.agent.service;

import java.util.List;

import com.ss.training.utopia.agent.entity.Flight;
import com.ss.training.utopia.agent.entity.FlightQuery;
import com.ss.training.utopia.agent.dao.FlightDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Trevor Huis in 't Veld
 */
@Component
public class AgentReadFlightsService {
    @Autowired
    FlightDAO flightDAO;

    public Flight[] readAvailableFlights(FlightQuery fq) {
        try {
            List<Flight> flights = flightDAO.findAvailable(fq.getDepartId(), fq.getArriveId(), fq.getPrice(), fq.getDateBegin(), fq.getDateEnd());
            return flights.toArray(new Flight[flights.size()]);
        } catch (Throwable t) {
            return null;
        }
    }

    public Flight[] readPremierFlights() {
        try {
            List<Flight> flights = flightDAO.findPremier();
            return flights.toArray(new Flight[flights.size()]);
        } catch (Throwable t) {
            return null;
        }
    }

}