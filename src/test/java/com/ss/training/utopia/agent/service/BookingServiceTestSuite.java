package com.ss.training.utopia.agent.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import com.ss.training.utopia.agent.dao.BookingDAO;
import com.ss.training.utopia.agent.dao.FlightDAO;
import com.ss.training.utopia.agent.entity.Booking;
import com.ss.training.utopia.agent.entity.Flight;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;

@RunWith(MockitoJUnitRunner.class)
@TestPropertySource(properties = {
    "STRIPE_API_SECRET=testValue",
})
public class BookingServiceTestSuite {
	
	@Value("${STRIPE_API_SECRET}")
    private String stripeKey;

    @Mock
    FlightDAO flightDAO;

    @Mock
    BookingDAO bookingDAO;

    @InjectMocks
    @Spy
    AgentBookingService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createBookingTestException() throws AuthenticationException, InvalidRequestException,
            APIConnectionException, CardException, APIException {


        Booking booking = new Booking(1l, 1l, 1l, true, null);        
        Mockito.doThrow(CardException.class).when(service).createBookingTransaction(booking);
        String createBookingResult = service.createBooking(booking);

        assertEquals(createBookingResult, "Card Declined");

        Mockito.doThrow(APIConnectionException.class).when(service).createBookingTransaction(booking);
        createBookingResult = service.createBooking(booking);

        assertEquals(createBookingResult, "Internal Server Error");
    }

    @Test
    public void createBookingTest() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            CardException, APIException {
        Booking booking = new Booking(1l, 1l, 1l, true, null);        
        Mockito.doReturn("Flight Booked").when(service).createBookingTransaction(booking);
        String createBookingResult = service.createBooking(booking);

        assertEquals(createBookingResult, "Flight Booked");
    }

    @Test
    public void createBookingTransactionTestNoSeats() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            CardException, APIException {


        Booking booking = new Booking(1l, 1l, 1l, true, null);
        Flight fullFlight = new Flight(1l, 1l, null, 1l, (short) 0, 10f);
        Mockito.when(flightDAO.findByFlightId(booking.getFlightId())).thenReturn(fullFlight);        
        String createBookingResult = service.createBookingTransaction(booking);

        assertEquals(createBookingResult, "Flight Full");
    }

    @Test
    public void createBookingTransactionTest() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            CardException, APIException {

        Booking booking = new Booking(1l, 1l, 1l, true, null);
        Flight flight = new Flight(1l, 1l, null, 1l, (short) 5, 10f);

        Mockito.when(flightDAO.findByFlightId(booking.getFlightId())).thenReturn(flight);  
        Mockito.doReturn("token").when(service).stripePurchase(booking);

        String createBookingResult = service.createBookingTransaction(booking);

        assertEquals(createBookingResult, "Flight Booked");
    }
    
    @Test
    public void stripePurchaseTest() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            CardException, APIException {

        Booking booking = new Booking(1l, 1l, 1l, true, "token");
        Flight flight = new Flight(1l, 1l, null, 1l, (short) 5, 10f);
        
        Map<String, Object> params = new HashMap<>();
        params.put("amount", 10f);
        params.put("currency", "usd");
        params.put("source", booking.getStripeId());

        Mockito.when(flightDAO.findByFlightId(booking.getFlightId())).thenReturn(flight);
        Mockito.doReturn("token").when(service).stripeCharge(params);
        
        String stripeToken = service.stripePurchase(booking);
        
        assertEquals(stripeToken, "token");
    }
}