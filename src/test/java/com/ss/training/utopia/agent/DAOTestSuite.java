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
public class DAOTestSuite {

	@Autowired private BookingDAO bookingDao;
    
    @Autowired private FlightDAO flightDAO;
    
    @Test
	public void findCancellableTest() {
        //Longs
        Long oneLong = (long) 1;
        Long twoLong = (long) 2;
        Long threeLong = (long) 3;

        // Times
        final Long HOUR = (long) 3_600_000;
        Long now = Instant.now().toEpochMilli();
        Timestamp past = new Timestamp(now - HOUR);
        Timestamp future = new Timestamp(now + HOUR);

        // Future flight and booking - Booker ID: 1, only cancellable flight by user 1
        Flight futureFlight = new Flight(oneLong, twoLong, future, oneLong, null, null);
        Booking cancellableBooking = new Booking(oneLong, oneLong, oneLong, true, null);

        // Past flight and booking - Booker ID: 1, will not be able to be cancelled because it already flew
        Flight pastFlight = new Flight(oneLong, twoLong, past, twoLong, null, null);
        Booking pastFlightBooking = new Booking(oneLong, twoLong, oneLong, true, null);

        // Already Cancelled flight - Booker ID: 1, cannot be cancelled
        Flight otherFutureFlight = new Flight(twoLong, oneLong, future, threeLong, null, null);
        Booking inactiveBooking = new Booking(oneLong, threeLong, oneLong, false, null);

        // Booking by another agent - Booker ID: 2, will not show up
		Booking otherTravelerBooking = new Booking(twoLong, oneLong, twoLong, true, null);
				
        List<Booking> foundBookings;
        flightDAO.save(futureFlight);
        bookingDao.save(cancellableBooking);
        flightDAO.save(pastFlight);
		bookingDao.save(pastFlightBooking);
		flightDAO.save(otherFutureFlight);
		bookingDao.save(inactiveBooking);
        bookingDao.save(otherTravelerBooking);
        foundBookings = bookingDao.findCancellable(oneLong);
        assertNotNull(foundBookings);
		assertEquals(foundBookings.size(), 1);
		assertEquals(foundBookings.get(0), cancellableBooking);
	}
}