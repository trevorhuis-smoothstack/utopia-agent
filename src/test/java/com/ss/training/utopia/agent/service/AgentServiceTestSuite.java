package com.ss.training.utopia.agent.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.utopia.agent.AgentApplication;
import com.ss.training.utopia.agent.H2TestProfileJPAConfig;
import com.ss.training.utopia.agent.dao.AirportDAO;
import com.ss.training.utopia.agent.dao.BookingDAO;
import com.ss.training.utopia.agent.dao.FlightDAO;
import com.ss.training.utopia.agent.entity.Airport;
import com.ss.training.utopia.agent.entity.Booking;
import com.ss.training.utopia.agent.entity.Flight;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(classes = { AgentApplication.class, H2TestProfileJPAConfig.class })
@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class AgentServiceTestSuite {

    @Mock
    BookingDAO bookingDAO;

    @Mock
    FlightDAO flightDAO;

    @Mock
    AirportDAO airportDAO;

    @InjectMocks
    @Spy
    AgentReadService readService;

    @BeforeEach
    private void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
	public void readBookingsByAgentNoBookings() {

        List<Booking> bookingsByAgent = readService.readAgentBookings((long) 1);

        assertEquals(bookingsByAgent.size(), 0);
    }

    // @Test
	// public void readAvailableFlights() {
    //    //Longs
    //    Long oneLong = (long) 1;
    //    Long twoLong = (long) 2;
    //    Long threeLong = (long) 3;
    //    Long fourLong = (long) 4;

    //    // Times
    //    final Long HOUR = (long) 3_600_000;
    //    Long now = Instant.now().toEpochMilli();
    //    Timestamp past = new Timestamp(now - HOUR);
    //    Timestamp future = new Timestamp(now + HOUR);

    //    Flight futureFlight = new Flight(twoLong, twoLong, future, oneLong,(short) 5, null);
    //    Flight pastFlight = new Flight(oneLong, twoLong, past, twoLong, null, null);
    //    Flight otherFutureFlight = new Flight(twoLong, oneLong, future, threeLong, (short) 5, null);
    //    Flight otherFutureFlightNoSeats = new Flight(oneLong, threeLong, future, fourLong, (short) 0, null);

    //    flightDAO.save(futureFlight);
    //    flightDAO.save(pastFlight);
    //    flightDAO.save(otherFutureFlight);
    //    flightDAO.save(otherFutureFlightNoSeats);

    //    List<Flight> flights = readService.readAvailableFlights();

    //     assertEquals(flights.size(), 2);
    //     assertEquals( flights.get(0).getDepartId(), twoLong);
    //     assertEquals( flights.get(1).getDepartId(), twoLong);
    //     assertNotEquals((short) flights.get(0).getSeatsAvailable(), (short) 0);
    //     assertNotEquals((short) flights.get(1).getSeatsAvailable(), (short) 0);
    // }

    @Test
	public void readAvailableFlights() {
               // Times
       final Long HOUR = (long) 3_600_000;
       Long now = Instant.now().toEpochMilli();
       Timestamp past = new Timestamp(now - HOUR);
       Timestamp future = new Timestamp(now + HOUR);

        Flight futureFlight = new Flight(2l, 2l, future, 1l,(short) 5, null);
       Flight pastFlight = new Flight(1l, 2l, past, 2l, null, null);

        List<Flight> flights = new ArrayList<Flight>();
        flights.add(futureFlight);
        flights.add(pastFlight);

        Mockito.when(flightDAO.findAvailable()).thenReturn(flights);

        List<Flight> foundFlights = readService.readAvailableFlights();

        assertEquals(foundFlights.size(), 2);
    }

    @Test
	public void readNoAvailableFlights() {

        List<Flight> flights = readService.readAvailableFlights();

        assertEquals(flights.size(), 0);
    }

    @Test
	public void readBookingsByAgent() {
        Booking bookingByAgent = new Booking((long) 1, (long) 1, (long) 1, true, null);
        Booking bookingNotByAgent = new Booking((long) 3, (long) 1, (long) 2, true, null);

        List<Booking> bookings = new ArrayList<Booking>();
        bookings.add(bookingByAgent);
        bookings.add(bookingNotByAgent);

        Mockito.when(bookingDAO.findByBookerId(1l)).thenReturn(bookings);

        List<Booking> bookingsByAgent = readService.readAgentBookings((long) 1);

        assertEquals(bookingsByAgent.size(), 2);
    }

    @Test
	public void readNoAirports() {

        List<Airport> airports = readService.readAirports();

        assertEquals(airports.size(), 0);
    }

    @Test
	public void readAirports() {
        Airport airportOne = new Airport(1L, "Austin");
        Airport airportTwo = new Airport(2L, "Houston");

        List<Airport> airports = new ArrayList<Airport>();
        airports.add(airportOne);
        airports.add(airportTwo);

        Mockito.when(airportDAO.findAll()).thenReturn(airports);

        List<Airport> foundAirports = readService.readAirports();

        assertEquals(foundAirports.size(), 2);
    }
}