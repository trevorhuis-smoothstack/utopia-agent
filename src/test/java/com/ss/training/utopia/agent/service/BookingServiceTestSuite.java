package com.ss.training.utopia.agent.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ss.training.utopia.agent.dao.BookingDAO;
import com.ss.training.utopia.agent.dao.FlightDAO;
import com.ss.training.utopia.agent.entity.Booking;
import com.ss.training.utopia.agent.entity.Flight;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BookingServiceTestSuite {

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
        Flight emptyFlight = new Flight(1l, 1l, null, 1l, (short) 0, 10f);
        Mockito.when(flightDAO.findByFlightId(booking.getFlightId())).thenReturn(emptyFlight);        
        String createBookingResult = service.createBookingTransaction(booking);

        assertEquals(createBookingResult, "Flight Full");
    }

    @Test
    public void createBookingTransactionTest() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            CardException, APIException {

        Booking booking = new Booking(1l, 1l, 1l, true, null);
        Flight flight = new Flight(1l, 1l, null, 1l, (short) 5, 10f);

        Charge charge = new Charge();
        charge.setId("token");

        Mockito.when(flightDAO.findByFlightId(booking.getFlightId())).thenReturn(flight);  
        Mockito.doReturn(charge).when(service).stripePurchase(booking);

        String createBookingResult = service.createBookingTransaction(booking);

        assertEquals(createBookingResult, "Flight Booked");
    }
}