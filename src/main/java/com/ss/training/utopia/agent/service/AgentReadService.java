package com.ss.training.utopia.agent.service;

import java.util.List;

import com.ss.training.utopia.agent.dao.AirportDAO;
import com.ss.training.utopia.agent.dao.BookingDAO;
import com.ss.training.utopia.agent.dao.FlightDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.ss.training.utopia.agent.entity.Airport;
import com.ss.training.utopia.agent.entity.Booking;
import com.ss.training.utopia.agent.entity.Flight;

/**
 * @author Trevor Huis in 't Veld
 */
@PropertySource("classpath:api.properties")
@Component
public class AgentReadService {

    @Autowired
    BookingDAO bookingDAO;

    @Autowired
    AirportDAO airportDAO;

    @Autowired
    FlightDAO flightDAO;

    public Flight readFlight(Long flightId) {
        try {
            Flight flight = flightDAO.findByFlightId(flightId);
            return flight;
        } catch (Throwable t) {
            return null;
        }
    }

    public Flight[] readAvailableFlights() {
        try {
            List<Flight> flights = flightDAO.findAvailable();
            return flights.toArray(new Flight[flights.size()]);
        } catch (Throwable t) {
            return null;
        }
    }

    public Airport[] readAirports() {
        try {
            List<Airport> airports = airportDAO.findAll();
		    return airports.toArray(new Airport[airports.size()]);
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * 
     * @param bookerId
     * @return
     */
    public Booking[] readAgentBookings(Long bookerId) {
        try {
            List<Booking> bookings = bookingDAO.findByBookerId(bookerId);
            return bookings.toArray(new Booking[bookings.size()]);
        } catch (Throwable t) {
            return null;
        }
    }
}
