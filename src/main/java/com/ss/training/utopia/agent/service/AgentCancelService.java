package com.ss.training.utopia.agent.service;
import java.util.HashMap;
import java.util.Map;

import com.ss.training.utopia.agent.dao.AirportDAO;
import com.ss.training.utopia.agent.dao.BookingDAO;
import com.ss.training.utopia.agent.dao.FlightDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.stripe.model.Refund;

/**
 * @author Trevor Huis in 't Veld
 */
@PropertySource("classpath:api.properties")
@Component
public class AgentCancelService {

    @Value("${STRIPE_API_SECRET}")
    private String stripeKey;

    @Autowired BookingDAO bookingDAO;

    @Autowired AirportDAO airportDAO;

    @Autowired FlightDAO flightDAO;

    public String cancelBooking(Booking booking) {
        try {
            String transactionResult = cancelBookingTransaction(booking);
            return transactionResult;
        } catch (InvalidRequestException e) {
            return "Already Refunded";
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
    public String cancelBookingTransaction(Booking booking) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, CardException, APIException {

        String stripeId = bookingDAO.findByTravelerIdAndFlightId(booking.getTravelerId(), booking.getFlightId()).getStripeId();       

        booking.setStripeId(stripeId);        
                
        stripeRefund(booking);

        booking.setActive(false);
        bookingDAO.save(booking);

        Flight flight;
		
		flight = flightDAO.findByFlightId(booking.getFlightId());

        flight.setSeatsAvailable((short) (flight.getSeatsAvailable()+1));
        
        flightDAO.save(flight);

        return "Flight Cancelled";
    }

    /**
     * 
     * @param booking
     * @return
     * @throws AuthenticationException
     * @throws InvalidRequestException
     * @throws APIConnectionException
     * @throws CardException
     * @throws APIException
     */
    public void stripeRefund(Booking booking) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, CardException, APIException {
        
        Stripe.apiKey = stripeKey;

		Map<String, Object> params = new HashMap<>();
            params.put(
            "charge",
            booking.getStripeId()
            );

        Refund.create(params);
    }
}