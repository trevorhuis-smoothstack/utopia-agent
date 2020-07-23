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
    public void readSingleFlightTest() {
    	final Long HOUR = (long) 3_600_000;
    	Long now = Instant.now().toEpochMilli();
    	Timestamp future = new Timestamp(now + HOUR);
    	Flight futureFlight = new Flight(2l, 2l, future, 1l,(short) 5, null);
    	Mockito.when(flightDAO.findByFlightId(1l)).thenReturn(futureFlight);
    	
    	Flight foundFlight = readService.readFlight(1l);
    	assertEquals(foundFlight, futureFlight);
    }

    @Test
    public void readSingleFlightTestException() {
        Mockito.doThrow(NullPointerException.class).when(flightDAO).findByFlightId(1l);
        Flight foundFlight = readService.readFlight(1l);
    	assertEquals(foundFlight, null);
    }

  
    @Test
	public void readTravelerFlightsByAgent() {
        final Long HOUR = (long) 3_600_000;
       Long now = Instant.now().toEpochMilli();
       Timestamp future = new Timestamp(now + HOUR);
		
		Flight flightOne = new Flight(1l, 2l, future, 1l, (short) 0, null);

        List<Flight> flights = new ArrayList<Flight>();
        flights.add(flightOne);

        Mockito.when(flightDAO.findCancellableFlightsByTravelerId(1l, 1l)).thenReturn(flights);

        Flight[] flightsByAgentAndTraveler = readService.readTravelerFlightsByAgent(1l, 1l);

        assertEquals(flightsByAgentAndTraveler[0], flightOne);
    }
    
    @Test
    public void readBookingsByAgentException() {
    	Mockito.doThrow(NullPointerException.class).when(flightDAO).findCancellableFlightsByTravelerId(1l, 1l);
        Flight[] flightsByAgentAndTraveler = readService.readTravelerFlightsByAgent(1l, 1l);
    	assertEquals(flightsByAgentAndTraveler, null);
    }

    @Test
	public void readNoAirports() {

        Airport[] airports = readService.readAirports();

        assertEquals(airports.length, 0);
    }

    @Test
	public void readAirports() {
        Airport airportOne = new Airport(1L, "Austin");
        Airport airportTwo = new Airport(2L, "Houston");

        List<Airport> airports = new ArrayList<Airport>();
        airports.add(airportOne);
        airports.add(airportTwo);

        Mockito.when(airportDAO.findAll()).thenReturn(airports);

        Airport[] foundAirports = readService.readAirports();

        assertEquals(foundAirports.length, 2);
    }
    
    @Test
    public void readAirportsException() {
    	Mockito.doThrow(NullPointerException.class).when(airportDAO).findAll();
        Airport[] foundAirports = readService.readAirports();
    	assertEquals(foundAirports, null);
    }
}