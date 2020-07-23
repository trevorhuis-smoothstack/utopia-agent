package com.ss.training.utopia.agent.service;

import java.util.HashMap;
import java.util.Map;

import com.ss.training.utopia.agent.dao.AirportDAO;
import com.ss.training.utopia.agent.dao.BookingDAO;
import com.ss.training.utopia.agent.dao.FlightDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ss.training.utopia.agent.entity.Booking;
import com.ss.training.utopia.agent.entity.Flight;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

/**
 * @author Trevor Huis in 't Veld
 */
@Component
public class AgentBookingService {

    @Autowired BookingDAO bookingDAO;

    @Autowired AirportDAO airportDAO;

    @Autowired FlightDAO flightDAO;

    /**
     * 
     * @param booking
     * @return
     */
    
    public String createBooking(Booking booking) {
        try {
            String transactionResult = createBookingTransaction(booking);
            return transactionResult;
        } catch (CardException e) {
            return "Card Declined";
        } catch (Throwable t) {
            return "Internal Server Error";
        }
    }    

    /**
     * 
     * @param booking
     * @return
     * @throws APIException
     * @throws CardException
     * @throws APIConnectionException
     * @throws InvalidRequestException
     * @throws AuthenticationException
     */
    @Transactional
    public String createBookingTransaction(Booking booking) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, CardException, APIException {

        Flight flight;
		
		flight = flightDAO.findByFlightId(booking.getFlightId());

		if (flight.getSeatsAvailable() < 1)
            return "Flight Full";

        booking.setStripeId(stripePurchase(booking));    

        bookingDAO.save(booking);  

        flight.setSeatsAvailable((short) (flight.getSeatsAvailable()-1));

        flightDAO.save(flight);

        return "Flight Booked";
    }
  
    public String stripePurchase(Booking booking) throws AuthenticationException, InvalidRequestException,
    APIConnectionException, CardException, APIException {
        
        Integer flightPrice = (int) (flightDAO.findByFlightId(booking.getFlightId()).getPrice() * 100);

        Map<String, Object> params = new HashMap<>();
        params.put("amount", flightPrice);
        params.put("currency", "usd");
        params.put("source", booking.getStripeId());

        return stripeCharge(params);
    }

    public String stripeCharge(Map<String, Object> params) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, CardException, APIException {
        Stripe.apiKey = System.getenv("STRIPE_KEY");
        Charge charge = Charge.create(params);
        return charge.getId();
    }
}