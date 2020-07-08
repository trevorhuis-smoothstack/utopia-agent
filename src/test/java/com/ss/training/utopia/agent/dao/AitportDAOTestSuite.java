package com.ss.training.utopia.agent.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.ss.training.utopia.agent.AgentApplication;
import com.ss.training.utopia.agent.H2TestProfileJPAConfig;
import com.ss.training.utopia.agent.entity.Airport;

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