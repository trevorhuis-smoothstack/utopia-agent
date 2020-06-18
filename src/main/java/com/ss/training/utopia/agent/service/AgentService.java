package com.ss.training.utopia.agent.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.stripe.model.Charge;
import com.stripe.model.Refund;

/**
 * @author Trevor Huis in 't Veld
 */
@PropertySource("classpath:api.properties")
@Component
public class AgentService {

    @Value("${STRIPE_API_SECRET}")
    private String stripeKey;

    @Autowired
    BookingDAO bookingDAO;

    @Autowired FlightDAO flightDAO;

    /**
     * 
     * @param booking
     * @return
     */
    @Transactional
    public String createBooking(Booking booking) {

        Flight flight;
		try {
			flight = flightDAO.findByFlightId(booking.getFlightId());
		} catch (Throwable t) {
			return null;
		}
		if (flight.getSeatsAvailable() <= 0)
            return "Flight Full";

        try {
            Charge charge = stripePurchase(booking);
            booking.setStripeId(charge.getId());
            bookingDAO.save(booking);
          } catch (CardException e) {
            return "Card Declined";
          } catch (Throwable t) {
            return "Internal Server Error";
          }

        return "Charge Created";
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
    public Charge stripePurchase(Booking booking) throws AuthenticationException, InvalidRequestException,
    APIConnectionException, CardException, APIException {
        
        Integer flightPrice = (int) (flightDAO.findByFlightId(booking.getFlightId()).getPrice() * 100);

        Stripe.apiKey = stripeKey;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", flightPrice);
        params.put("currency", "usd");
        params.put("source", booking.getStripeId());

        Charge charge = Charge.create(params);
        return charge;
        }

    /**
     * 
     * @param bookerId
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

    /**
     * 
     * @param booking
     * @return
     */
    @Transactional
    public String cancelBooking(Booking booking) {

        try {
            stripeRefund(booking);
            booking.setActive(false);
            bookingDAO.save(booking);
        } catch (InvalidRequestException e) {
            return "Already Refunded";
        } catch (Throwable t) {
            return "Internal Server Error";
        }

        return "Refund Processed";
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
