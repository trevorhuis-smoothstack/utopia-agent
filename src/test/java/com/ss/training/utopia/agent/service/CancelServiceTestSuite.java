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

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class CancelServiceTestSuite {

    @Mock
    FlightDAO flightDAO;

    @Mock
    BookingDAO bookingDAO;

    @InjectMocks
    @Spy
    AgentCancelService service;

    @BeforeEach
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void cancelBookingTestException() throws AuthenticationException, InvalidRequestException,
            APIConnectionException, CardException, APIException {


        Booking booking = new Booking(1l, 1l, 1l, true, null);        
        Mockito.doThrow(InvalidRequestException.class).when(service).cancelBookingTransaction(booking);
        String cancelBookingResult = service.cancelBooking(booking);

        assertEquals(cancelBookingResult, "Already Refunded");

        Mockito.doThrow(APIConnectionException.class).when(service).cancelBookingTransaction(booking);
        cancelBookingResult = service.cancelBooking(booking);

        assertEquals(cancelBookingResult, "Internal Server Error");
    }

    @Test
    public void cancelBookingTest() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            CardException, APIException {
        Booking booking = new Booking(1l, 1l, 1l, true, null);        
        Mockito.doReturn("Flight Cancelled").when(service).cancelBookingTransaction(booking);
        String cancelBookingResult = service.cancelBooking(booking);

        assertEquals(cancelBookingResult, "Flight Cancelled");
    }
    
    @Test
    public void cancelBookingTransactionTest() throws AuthenticationException, InvalidRequestException, APIConnectionException,
            CardException, APIException {
        Booking booking = new Booking(1l, 1l, 1l, true, null);
        Flight flight = new Flight(1l, 1l, null, 1l, (short) 5, 10f);
        
        Mockito.when(flightDAO.findByFlightId(booking.getFlightId())).thenReturn(flight);  
//        Mockito.doNothing.when(service).stripePurchase(booking);
        String cancelBookingResult = service.cancelBookingTransaction(booking);

        assertEquals(cancelBookingResult, "Flight Cancelled");
    }
    

    
}