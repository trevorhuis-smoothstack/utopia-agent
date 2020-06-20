package com.ss.training.utopia.agent.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import com.ss.training.utopia.agent.AgentApplication;
import com.ss.training.utopia.agent.H2TestProfileJPAConfig;
import com.ss.training.utopia.agent.entity.Airport;
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
public class AitportDAOTestSuite {
    
    @Autowired AirportDAO airportDAO;

    @Test
    public void findByAirportIdTest() {

        Airport airportOne = new Airport(1L, "Austin");
        Airport airportTwo = new Airport(2L, "Houston");

        airportDAO.save(airportOne);
        airportDAO.save(airportTwo);

        Airport foundAirportOne = airportDAO.findByAirportId(1L);
        assertEquals(foundAirportOne, airportOne);

        Airport foundAirportTwo = airportDAO.findByAirportId(2L);
        assertEquals(foundAirportTwo, airportTwo);

        assertNull(airportDAO.findByAirportId(3L));
    }
}