package com.ss.training.utopia.agent.dao;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import com.ss.training.utopia.agent.AgentApplication;
import com.ss.training.utopia.agent.H2TestProfileJPAConfig;
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
@SpringBootTest(classes = {AgentApplication.class, 
H2TestProfileJPAConfig.class})
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class FlightDAOTestSuite {
    
    @Autowired FlightDAO flightDAO;

    @Test
	public void findByFlightIdTest() {
        //Longs
        Long oneLong = (long) 1;
        Long twoLong = (long) 2;
        Long threeLong = (long) 3;

        Timestamp timestamp = new Timestamp(Instant.now().toEpochMilli());

		
		Flight flightOne = new Flight(oneLong, twoLong, timestamp, oneLong, (short) 0, null);
		Flight flightTwo = new Flight(twoLong, oneLong, timestamp, twoLong, (short) 0, null);
        
        flightDAO.save(flightOne);
        flightDAO.save(flightTwo);     

		assertEquals(flightOne, flightDAO.findByFlightId(oneLong));
		assertNull(flightDAO.findByFlightId(threeLong));
    }
    
    @Test
	public void findBookableFlights() {
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

       List<Flight> availableFlights = flightDAO.findAvailable();

       assertEquals(availableFlights.size(), 2);
        assertEquals( availableFlights.get(0).getDepartId(), twoLong);
        assertEquals( availableFlights.get(1).getDepartId(), twoLong);
        assertNotEquals((short) availableFlights.get(0).getSeatsAvailable(), (short) 0);
        assertNotEquals((short) availableFlights.get(1).getSeatsAvailable(), (short) 0);
	}
}