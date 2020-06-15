package com.ss.training.utopia.agent.service;

import java.util.List;
import com.ss.training.utopia.agent.dao.BookingDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.training.utopia.agent.entity.Booking;

/**
 * @author Trevor Huis in 't Veld
 */
@Component
public class AgentService {

    @Autowired
    BookingDAO bookingDAO;

    /**
     * 
     * @return
     */
    public List<Booking> readAgentBookings(Long bookerId ) {
        List<Booking> bookings = bookingDAO.findByBookerId(bookerId);
        return bookings;
    }

}