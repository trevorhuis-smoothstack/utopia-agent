package com.ss.training.utopia.agent;

import static org.junit.Assert.assertNotNull;
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

    @Autowired private FlightDAO flightDAO;
    
    @Autowired private BookingDAO bookingDao;
    
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
        foundBookings = bookingDao.findCancellable(oneLong);
        assertNotNull(foundBookings);
		assertEquals(foundBookings.size(), 0);

        service.createBooking(newBooking);

        foundBookings = bookingDao.findCancellable(oneLong);
        assertNotNull(foundBookings);
		assertEquals(foundBookings.size(), 1);
    }

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
        bookingDao.save(cancellableBooking);

        List<Booking> foundBookings;
        foundBookings = bookingDao.findCancellable(oneLong);
        assertNotNull(foundBookings);
		assertEquals(foundBookings.size(), 1);

        Booking cancelledBooking = service.cancelBooking(cancellableBooking);
        
        assertEquals(cancelledBooking.getActive(), false);
        foundBookings = bookingDao.findCancellable(oneLong);
        assertNotNull(foundBookings);
		assertEquals(foundBookings.size(), 0);
    }













}