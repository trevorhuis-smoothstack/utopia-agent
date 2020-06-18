package com.ss.training.utopia.agent;

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
    
    @Autowired private BookingDAO bookingDao;
    
    @Autowired private FlightDAO flightDao;

    @Autowired private AgentService service;
    
    @Test
	public void readBookingsByAgent() {
        Booking bookingByAgent = new Booking((long) 1, (long) 1, (long) 1, true, null);
        Booking bookingByAgent2 = new Booking((long) 2, (long) 1, (long) 1, true, null);
        Booking bookingNotByAgent = new Booking((long) 3, (long) 1, (long) 2, true, null);

        bookingDao.save(bookingByAgent);
        bookingDao.save(bookingByAgent2);
        bookingDao.save(bookingNotByAgent);

        List<Booking> bookingsByAgent = service.readAgentBookings((long) 1);

        assertEquals(bookingsByAgent.size(), 2);
    }

    @Test
	public void readBookingsByAgentNoBookings() {

        List<Booking> bookingsByAgent = service.readAgentBookings((long) 1);

        assertEquals(bookingsByAgent.size(), 0);
    }






    @Test
	public void readAvailableFlights() {
       //Longs
       Long oneLong = (long) 1;
       Long twoLong = (long) 2;
       Long threeLong = (long) 3;

       // Times
       final Long HOUR = (long) 3_600_000;
       Long now = Instant.now().toEpochMilli();
       Timestamp past = new Timestamp(now - HOUR);
       Timestamp future = new Timestamp(now + HOUR);

       Flight futureFlight = new Flight(oneLong, twoLong, future, oneLong, null, null);
       Flight pastFlight = new Flight(oneLong, twoLong, past, twoLong, null, null);
       Flight otherFutureFlight = new Flight(twoLong, oneLong, future, threeLong, null, null);

       flightDao.save(futureFlight);
       flightDao.save(pastFlight);
       flightDao.save(otherFutureFlight);

       List<Flight> flights = service.readAvailableFlights();

        assertEquals(flights.size(), 2);
    }

    @Test
	public void readNoAvailableFlights() {

        List<Flight> flights = service.readAvailableFlights();

        assertEquals(flights.size(), 0);
    }
}