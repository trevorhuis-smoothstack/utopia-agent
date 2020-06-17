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
     * @return
     */
    public List<Airport> readAirports() {
        try {
            List<Airport> airports = airportDAO.findAll();
            return airports;
        } catch (Throwable t) {
            return null;
        }
    }
}