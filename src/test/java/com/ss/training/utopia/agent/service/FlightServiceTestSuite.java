package com.ss.training.utopia.agent.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.utopia.agent.AgentApplication;
import com.ss.training.utopia.agent.H2TestProfileJPAConfig;
import com.ss.training.utopia.agent.dao.AirportDAO;
import com.ss.training.utopia.agent.dao.BookingDAO;
import com.ss.training.utopia.agent.dao.FlightDAO;
import com.ss.training.utopia.agent.entity.Flight;
import com.ss.training.utopia.agent.entity.FlightQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(classes = { AgentApplication.class, H2TestProfileJPAConfig.class })
@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class FlightServiceTestSuite {

    @Mock
    BookingDAO bookingDAO;

    @Mock
    FlightDAO flightDAO;

    @Mock
    AirportDAO airportDAO;

    @InjectMocks
    @Spy
    AgentReadFlightsService service;

    @Test
    public void readPremierTest() {
        // Times
        final Long HOUR = (long) 3_600_000;
        Long now = Instant.now().toEpochMilli();
        Timestamp future = new Timestamp(now + HOUR);

        Flight flightOne = new Flight(2l, 2l, future, 1l, (short) 5, 95.0f);
        Flight flightTwo = new Flight(1l, 2l, future, 2l, (short) 5, 92.0f);

        List<Flight> flights = new ArrayList<Flight>();
        flights.add(flightOne);
        flights.add(flightTwo);

        Mockito.when(flightDAO.findPremier()).thenReturn(flights);

        Flight[] foundFlights = service.readPremierFlights();

        assertEquals(foundFlights.length, 2);
    }

    @Test
    public void readPremierTestException() {
        Mockito.doThrow(NullPointerException.class).when(flightDAO).findPremier();
        Flight[] foundFlights = service.readPremierFlights();
    	assertEquals(foundFlights, null);
    }

    @Test
    public void readFlightsTest() {
        // Times
        final Long HOUR = (long) 3_600_000;
        Long now = Instant.now().toEpochMilli();
        Timestamp future = new Timestamp(now + HOUR);
        LocalDate today = LocalDate.now();
        String todaysDate = today.toString();
        String dateBegin = todaysDate;

        FlightQuery fq = new FlightQuery("", "", dateBegin, "2100-01-01", 100.0f);

        Flight flightOne = new Flight(2l, 2l, future, 1l, (short) 5, 35.0f);
        Flight flightTwo = new Flight(1l, 2l, future, 2l, (short) 5, 52.0f);

        List<Flight> flights = new ArrayList<Flight>();
        flights.add(flightOne);
        flights.add(flightTwo);

        Mockito.when(flightDAO.findAvailable("", "", 100.0f, dateBegin, "2100-01-01")).thenReturn(flights);

        Flight[] foundFlights = service.readAvailableFlights(fq);

        assertEquals(foundFlights.length, 2);
    }

    @Test
    public void readFlightsTestException() {
        LocalDate today = LocalDate.now();
        String todaysDate = today.toString();
        String dateBegin = todaysDate;

        FlightQuery fq = new FlightQuery("", "", dateBegin, "2100-01-01", 100.0f);
        Mockito.doThrow(NullPointerException.class).when(flightDAO).findAvailable("", "", 100.0f, dateBegin, "2100-01-01");
        Flight[] foundFlights = service.readAvailableFlights(fq);
    	assertEquals(foundFlights, null);
    }
}