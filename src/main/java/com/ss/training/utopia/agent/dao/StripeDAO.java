package com.ss.training.utopia.agent.dao;

import java.util.HashMap;
import java.util.Map;

import com.ss.training.utopia.agent.entity.Booking;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import org.springframework.stereotype.Component;

/**
 * @author Trevor Huis in 't Veld
 */
@Component
public class StripeDAO {

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

        Stripe.apiKey = System.getenv("STRIPE_KEY");

        Map<String, Object> params = new HashMap<>();
        params.put("charge", booking.getStripeId());

        Refund.create(params);
    }

    /**
     * 
     * @param params
     * @return
     * @throws AuthenticationException
     * @throws InvalidRequestException
     * @throws APIConnectionException
     * @throws CardException
     * @throws APIException
     */
    public String stripeCharge(Map<String, Object> params) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, CardException, APIException {
        Stripe.apiKey = System.getenv("STRIPE_KEY");
        Charge charge = Charge.create(params);
        return charge.getId();
    }
}