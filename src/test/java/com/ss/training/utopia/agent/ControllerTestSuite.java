package com.ss.training.utopia.agent;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import com.ss.training.utopia.agent.controller.AgentController;
import com.ss.training.utopia.agent.dao.BookingDAO;
import com.ss.training.utopia.agent.dao.FlightDAO;
import com.ss.training.utopia.agent.entity.Airport;
import com.ss.training.utopia.agent.entity.Booking;
import com.ss.training.utopia.agent.entity.Flight;
import com.ss.training.utopia.agent.service.AgentService;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgentApplication.class, 
H2TestProfileJPAConfig.class})
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class ControllerTestSuite {
    private MockMvc mockMvc;
    
    @Autowired
    private AgentController controller;

	@BeforeAll
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    
    @Test
	public void testReadAirports() throws Exception {
        AgentService mockService = mock(AgentService.class);

        List<Airport> airports = new ArrayList<Airport>();
        airports.add(new Airport((long) 1, "airport"));
        
        JSONArray array = new JSONArray();
		JSONObject item = new JSONObject();
		item.put("airportId", 1);
        item.put("name", "airport");
        array.add(item);
		
		Mockito.when(mockService.readAirports()).thenReturn(airports);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/agent/airport").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(array.toString()));

	}
}