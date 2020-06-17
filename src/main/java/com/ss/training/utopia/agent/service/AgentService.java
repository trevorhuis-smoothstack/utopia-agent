package com.ss.training.utopia.agent.service;

import java.util.List;
import com.ss.training.utopia.agent.dao.BookingDAO;
import com.ss.training.utopia.agent.dao.FlightDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.training.utopia.agent.entity.Booking;
import com.ss.training.utopia.agent.entity.Flight;

/**
 * @author Trevor Huis in 't Veld
 */
@Component
public class AgentService {

    @Autowired
    BookingDAO bookingDAO;

    @Autowired
    FlightDAO flightDAO;

    /**
     * 
     * @return
     */
    public List<Booking> readAgentBookings(Long bookerId) {
        try {
            List<Booking> bookings = bookingDAO.findByBookerId(bookerId);
            return bookings;
        } catch (Throwable t) {
            return null;
        }
    }

        public List<Flight> readAvailableFlights() {
            
            try {
                List<Flight> flights = flightDAO.findAvailable();
                return flights;
            } catch (Throwable t) {
                return null;
            }
        }
}