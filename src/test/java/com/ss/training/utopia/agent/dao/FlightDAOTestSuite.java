package com.ss.training.utopia.agent.dao;

import static org.junit.Assert.assertNull;
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

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { AgentApplication.class, H2TestProfileJPAConfig.class })
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class FlightDAOTestSuite {

    @Autowired
    FlightDAO flightDAO;

    @Autowired
    BookingDAO bookingDAO;

    @Test
    public void findByFlightIdTest() {

        Timestamp timestamp = new Timestamp(Instant.now().toEpochMilli());

        Flight flightOne = new Flight(1l, 2l, timestamp, 1l, (short) 0, null);
        Flight flightTwo = new Flight(2l, 1l, timestamp, 2l, (short) 0, null);

        flightDAO.save(flightOne);
        flightDAO.save(flightTwo);

        assertEquals(flightOne, flightDAO.findByFlightId(1l));
        assertNull(flightDAO.findByFlightId(3l));
    }

    @Test
    public void findCancellableByBookerAndTravelerIdTest() {

        Timestamp timestamp = new Timestamp(Instant.now().toEpochMilli());

        Flight flightOne = new Flight(1l, 2l, timestamp, 1l, (short) 0, null);
        Flight flightTwo = new Flight(2l, 1l, timestamp, 2l, (short) 0, null);

        Booking bookingToFind = new Booking(1l, 1l, 1l, true, null);
        Booking bookingNotByAgent = new Booking(1l, 2l, 2l, true, null);
        Booking bookingNotByTraveler = new Booking(2l, 1l, 1l, true, null);

        flightDAO.save(flightOne);
        flightDAO.save(flightTwo);

        bookingDAO.save(bookingToFind);
        bookingDAO.save(bookingNotByAgent);
        bookingDAO.save(bookingNotByTraveler);

        assertEquals(flightOne, flightDAO.findCancellableFlightsByTravelerId(1l, 1l).get(0));
    }

    @Test
    public void findPremierFlightsTest() {
        // Times
        final Long HOUR = (long) 3_600_000;
        Long now = Instant.now().toEpochMilli();
        Timestamp future = new Timestamp(now + HOUR);

        Flight expensiveFlight = new Flight(2l, 2l, future, 1l, (short) 5, 95.0f);
        Flight cheapFlight = new Flight(1l, 2l, future, 2l, (short) 5, 10.0f);

        flightDAO.save(expensiveFlight);
        flightDAO.save(cheapFlight);

        List<Flight> premierFlights = flightDAO.findPremier();

        assertEquals(premierFlights.size(), 1);
        assertEquals(premierFlights.get(0).getFlightId(), 1l);
        assertEquals((float) premierFlights.get(0).getPrice(), 95.0f);
    }
}