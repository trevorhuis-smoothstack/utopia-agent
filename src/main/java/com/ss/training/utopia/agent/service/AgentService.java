package com.ss.training.utopia.agent.service;

import java.util.List;
import com.ss.training.utopia.agent.dao.AirportDAO;
import com.ss.training.utopia.agent.dao.BookingDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.training.utopia.agent.entity.Airport;
import com.ss.training.utopia.agent.entity.Booking;

/**
 * @author Trevor Huis in 't Veld
 */
@Component
public class AgentService {
    @Autowired
    AirportDAO airportDAO;

    @Autowired
    BookingDAO bookingDAO;

    /**
     * 
     * @return
     */
    public List<Airport> readAirports() {
        List<Airport> airports = airportDAO.findAll();
        return airports;
    }


    public Booking cancelBooking(Booking booking) {
        // HOW TO DO A STRIPE REFUND
        // Stripe.apiKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dc";

        // Map<String, Object> params = new HashMap<>();
        //     params.put(
        //     "charge",
        //     "ch_1GuO552eZvKYlo2CEgo1O2iQ"
        // );

        // Refund refund = Refund.create(params);

        // Handle the Stripe request

        booking.setActive(false);

        bookingDAO.save(booking);

        return booking;
    }
}