package com.ss.training.utopia.agent.service;

import java.util.List;
import com.ss.training.utopia.agent.dao.AirportDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.training.utopia.agent.entity.Airport;

/**
 * @author Trevor Huis in 't Veld
 */
@Component
public class AgentService {
    @Autowired
    AirportDAO airportDAO;

        /**
     * 
     * @param airportId
     * @return
     */
    public Airport readAirport(Integer airportId) {
        return airportDAO.findByAirportId(airportId);
    }

    /**
     * 
     * @return
     */
    public List<Airport> readAirports() {
        List<Airport> airports = airportDAO.findAll();
        return airports;
    }
}