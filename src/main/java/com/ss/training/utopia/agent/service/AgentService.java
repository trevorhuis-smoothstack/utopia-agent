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










































    public Booking cancelBooking(Booking booking) {
        // HOW TO DO A STRIPE REFUND

        // STORE A STRIPE KEY
        // Stripe.apiKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dc";

        // GET THE PRICE FROM FLIGHTS TABLE
        // float price = flightDAO.getByFlightId().getPrice();

        // CREATE A REFUND OBJECT
        // Refund refund = Refund.create(RefundCreateParams.builder()
        //   .setPaymentIntent("pi_Aabcxyz01aDfoo")
        //   .build());

        // Handle the Stripe request

        booking.setActive(false);

        bookingDAO.save(booking);

        return booking;
    }













    
}