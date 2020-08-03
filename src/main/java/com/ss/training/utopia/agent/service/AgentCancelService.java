package com.ss.training.utopia.agent.service;

import com.ss.training.utopia.agent.dao.BookingDAO;
import com.ss.training.utopia.agent.dao.FlightDAO;
import com.ss.training.utopia.agent.dao.StripeDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ss.training.utopia.agent.entity.Booking;
import com.ss.training.utopia.agent.entity.Flight;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;

/**
 * @author Trevor Huis in 't Veld
 */
@Component
public class AgentCancelService {

    @Autowired BookingDAO bookingDAO;

    @Autowired FlightDAO flightDAO;

    @Autowired StripeDAO stripeDAO;

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
                
        stripeDAO.stripeRefund(booking);

        booking.setActive(false);
        bookingDAO.save(booking);

        Flight flight;
		
		flight = flightDAO.findByFlightId(booking.getFlightId());

        flight.setSeatsAvailable((short) (flight.getSeatsAvailable()+1));
        
        flightDAO.save(flight);

        return "Flight Cancelled";
    }

   
}