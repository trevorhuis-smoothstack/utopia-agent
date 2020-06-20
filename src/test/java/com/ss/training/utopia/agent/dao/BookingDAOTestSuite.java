package com.ss.training.utopia.agent.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import com.ss.training.utopia.agent.AgentApplication;
import com.ss.training.utopia.agent.H2TestProfileJPAConfig;
import com.ss.training.utopia.agent.entity.Booking;
import com.ss.training.utopia.agent.entity.Flight;

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
public class BookingDAOTestSuite {

    @Autowired BookingDAO bookingDAO;

    @Autowired FlightDAO flightDAO;
    
    @Test
    public void findCancellableBookings() {
        //Longs
        Long oneLong = (long) 1;
        Long twoLong = (long) 2;
        Long threeLong = (long) 3;
        Long fourLong = (long) 4;

        // Times
        final Long HOUR = (long) 3_600_000;
        Long now = Instant.now().toEpochMilli();
        Timestamp future = new Timestamp(now + HOUR);
        Timestamp past = new Timestamp(now - HOUR);


        Flight flightFuture = new Flight(oneLong, twoLong, future, oneLong, (short) 5, null);
        Flight flightPast = new Flight(twoLong, oneLong, past, twoLong, (short) 5, null);
        Flight otherFlightFuture = new Flight(twoLong, twoLong, future, fourLong, (short) 5, null);

        flightDAO.save(flightFuture);
        flightDAO.save(flightPast);
        flightDAO.save(otherFlightFuture);

        Booking cancellableBookingOne = new Booking(oneLong, oneLong, oneLong, true, null);
        Booking cancellableBookingTwo = new Booking(twoLong, oneLong, oneLong, true, null);

        bookingDAO.save(cancellableBookingOne);
        bookingDAO.save(cancellableBookingTwo);

        Booking nonCancellableBookingDifferentBooker = new Booking(threeLong, fourLong, twoLong, true, null);
        Booking nonCancellableBookingOldFlight = new Booking(oneLong, twoLong, oneLong, true, null);
        Booking nonCancellableBookingNonActive = new Booking(fourLong, fourLong, oneLong, false, null);

        bookingDAO.save(nonCancellableBookingDifferentBooker);
        bookingDAO.save(nonCancellableBookingOldFlight);
        bookingDAO.save(nonCancellableBookingNonActive);


        List<Booking> foundBookings;
        foundBookings = bookingDAO.findCancellable(oneLong);

        assertEquals(foundBookings.size(), 2);
        assertEquals(foundBookings.get(0).getBookerId(), 1);
        assertEquals(foundBookings.get(1).getBookerId(), 1);
        assertEquals(foundBookings.get(0).getFlightId(), 1);
        assertEquals(foundBookings.get(1).getFlightId(), 1);
    }

    @Test
    public void findByTravelerIdAndFlightIdTest() {
        //Longs
        Long oneLong = (long) 1;
        Long twoLong = (long) 2;


        Booking bookingOne = new Booking(oneLong, twoLong, oneLong, true, null);
        Booking bookingtwo = new Booking(twoLong, oneLong, oneLong, true, null);

        bookingDAO.save(bookingOne);
        bookingDAO.save(bookingtwo);

        Booking foundBookingOne = bookingDAO.findByTravelerIdAndFlightId(oneLong, twoLong);
        Booking foundBookingTwo = bookingDAO.findByTravelerIdAndFlightId(twoLong, oneLong);

        assertEquals(foundBookingOne, bookingOne);
        assertEquals(foundBookingTwo, bookingtwo);
    }


    @Test
    public void findAgentBookings() {
        //Longs
        Long oneLong = (long) 1;
        Long twoLong = (long) 2;
        Long threeLong = (long) 3;
        Long fourLong = (long) 4;

        Booking bookingByAgent = new Booking(oneLong, oneLong, oneLong, true, null);
        Booking bookingByAgentTwo = new Booking(twoLong, oneLong, oneLong, true, null);

        Booking bookingByOtherAgent = new Booking(threeLong, threeLong, twoLong, true, null);
        Booking bookingByOtherAgentTwo = new Booking(fourLong, threeLong, twoLong, true, null);

        bookingDAO.save(bookingByAgent);
        bookingDAO.save(bookingByAgentTwo);
        bookingDAO.save(bookingByOtherAgent);
        bookingDAO.save(bookingByOtherAgentTwo);

        List<Booking> foundBookings;
        foundBookings = bookingDAO.findByBookerId(oneLong);

        assertEquals(foundBookings.size(), 2);
        assertEquals(foundBookings.get(0).getFlightId(), 1);
        assertEquals(foundBookings.get(1).getFlightId(), 1);
    }
}