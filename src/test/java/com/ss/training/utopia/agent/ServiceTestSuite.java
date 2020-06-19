package com.ss.training.utopia.agent;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import com.ss.training.utopia.agent.dao.BookingDAO;
import com.ss.training.utopia.agent.dao.FlightDAO;
import com.ss.training.utopia.agent.entity.Booking;
import com.ss.training.utopia.agent.entity.Flight;
import com.ss.training.utopia.agent.service.AgentService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgentApplication.class, 
H2TestProfileJPAConfig.class})
@ActiveProfiles("test")
public class ServiceTestSuite {
    
    @Autowired private BookingDAO bookingDAO;
    
    @Autowired private FlightDAO flightDAO;

    @Autowired private AgentService service;
    
    @Test
	public void createBooking() {
        //Longs
        Long oneLong = (long) 1;
        Long twoLong = (long) 2;

        // Times
        final Long HOUR = (long) 3_600_000;
        Long now = Instant.now().toEpochMilli();
        Timestamp future = new Timestamp(now + HOUR);

        // Future flight and booking - Booker ID: 1, only cancellable flight by user 1
        Flight flight = new Flight(oneLong, twoLong, future, oneLong, null, null);
        Booking newBooking = new Booking(oneLong, oneLong, oneLong, true, null);

        flightDAO.save(flight);

        List<Booking> foundBookings;
        foundBookings = bookingDAO.findCancellable(oneLong);
		assertEquals(foundBookings.size(), 0);

        service.createBooking(newBooking);

        foundBookings = bookingDAO.findCancellable(oneLong);
		assertEquals(foundBookings.size(), 1);
    }

	public void readBookingsByAgent() {
        Booking bookingByAgent = new Booking((long) 1, (long) 1, (long) 1, true, null);
        Booking bookingByAgent2 = new Booking((long) 2, (long) 1, (long) 1, true, null);
        Booking bookingNotByAgent = new Booking((long) 3, (long) 1, (long) 2, true, null);

        bookingDAO.save(bookingByAgent);
        bookingDAO.save(bookingByAgent2);
        bookingDAO.save(bookingNotByAgent);

        List<Booking> bookingsByAgent = service.readAgentBookings((long) 1);

        assertEquals(bookingsByAgent.size(), 2);
    }

    @Test
	public void readBookingsByAgentNoBookings() {

        List<Booking> bookingsByAgent = service.readAgentBookings((long) 1);

        assertEquals(bookingsByAgent.size(), 0);
    }

    @Test
	public void cancelBooking() {
        //Longs
        Long oneLong = (long) 1;
        Long twoLong = (long) 2;

        // Times
        final Long HOUR = (long) 3_600_000;
        Long now = Instant.now().toEpochMilli();
        Timestamp future = new Timestamp(now + HOUR);

        // Future flight and booking - Booker ID: 1, only cancellable flight by user 1
        Flight futureFlight = new Flight(oneLong, twoLong, future, oneLong, null, null);
        Booking cancellableBooking = new Booking(oneLong, oneLong, oneLong, true, null);

        flightDAO.save(futureFlight);
        bookingDAO.save(cancellableBooking);

        List<Booking> foundBookings;
        foundBookings = bookingDAO.findCancellable(oneLong);
		assertEquals(foundBookings.size(), 1);

        service.cancelBooking(cancellableBooking);
        
        Booking cancelledBooking = bookingDAO.findAll().get(0);
        assertEquals(cancelledBooking.getActive(), false);


        foundBookings = bookingDAO.findCancellable(oneLong);
		assertEquals(foundBookings.size(), 0);
    }

    @Test
	public void readAvailableFlights() {
       //Longs
       Long oneLong = (long) 1;
       Long twoLong = (long) 2;
       Long threeLong = (long) 3;
       Long fourLong = (long) 4;

       // Times
       final Long HOUR = (long) 3_600_000;
       Long now = Instant.now().toEpochMilli();
       Timestamp past = new Timestamp(now - HOUR);
       Timestamp future = new Timestamp(now + HOUR);

       Flight futureFlight = new Flight(twoLong, twoLong, future, oneLong,(short) 5, null);
       Flight pastFlight = new Flight(oneLong, twoLong, past, twoLong, null, null);
       Flight otherFutureFlight = new Flight(twoLong, oneLong, future, threeLong, (short) 5, null);
       Flight otherFutureFlightNoSeats = new Flight(oneLong, threeLong, future, fourLong, (short) 0, null);

       flightDAO.save(futureFlight);
       flightDAO.save(pastFlight);
       flightDAO.save(otherFutureFlight);
       flightDAO.save(otherFutureFlightNoSeats);

       List<Flight> flights = service.readAvailableFlights();

        assertEquals(flights.size(), 2);
        assertEquals( flights.get(0).getDepartId(), twoLong);
        assertEquals( flights.get(1).getDepartId(), twoLong);
        assertNotEquals((short) flights.get(0).getSeatsAvailable(), (short) 0);
        assertNotEquals((short) flights.get(1).getSeatsAvailable(), (short) 0);
    }

    @Test
	public void readNoAvailableFlights() {

        List<Flight> flights = service.readAvailableFlights();

        assertEquals(flights.size(), 0);
    }
}