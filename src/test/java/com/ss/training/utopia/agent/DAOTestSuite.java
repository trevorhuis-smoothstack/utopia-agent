package com.ss.training.utopia.agent;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.time.Instant;

import com.ss.training.utopia.agent.dao.FlightDAO;
import com.ss.training.utopia.agent.entity.Flight;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgentApplication.class, 
H2TestProfileJPAConfig.class})
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class DAOTestSuite {

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
}