package com.ss.training.utopia.agent.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.training.utopia.agent.entity.Airport;
import com.ss.training.utopia.agent.entity.Flight;
import com.ss.training.utopia.agent.service.AgentBookingService;
import com.ss.training.utopia.agent.service.AgentCancelService;
import com.ss.training.utopia.agent.service.AgentReadService;
import com.ss.training.utopia.agent.service.UserService;

@WebMvcTest(AgentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AgentControllerReadTestSuite {

	@Autowired
	private MockMvc mvc;
	@Autowired
    private ObjectMapper mapper;
    
    @MockBean 
    AgentBookingService bookingService;

    @MockBean 
    AgentCancelService cancelService;

	@MockBean
    private UserService userService;
    
    @MockBean
	private AgentReadService readService;

	@Test
	public void getAllAirportsTest() throws Exception {
        Airport[] airports = { new Airport(4l, "City 1"), new Airport(6l, "City 2") }, noAirports = {};
		String uri = "/agent/airports", expectedContent = mapper.writeValueAsString(airports);
		Mockito.when(readService.readAirports()).thenReturn(airports, noAirports, null);
		mvc.perform(get(uri)).andExpect(status().isOk()).andExpect(content().string(expectedContent));
        mvc.perform(get(uri)).andExpect(status().isNoContent()).andExpect(content().string("[]"));
        mvc.perform(get(uri)).andExpect(status().isInternalServerError()).andExpect(content().string(""));
    }
    
    @Test
	public void getBookableFlightsTest() throws Exception {
		final Long HOUR = 3_600_000l;
		Long departId = 4l, arriveId = 2l, now = Instant.now().toEpochMilli();
		Timestamp futureOne = new Timestamp(now + HOUR), futureTwo = new Timestamp(now + 2 * HOUR);
		Flight[] flights = { new Flight(departId, arriveId, futureOne, 3l, (short) 8, 150f),
				new Flight(departId, arriveId, futureTwo, 7l, (short) 5, 151f) };
		String uri = "/agent/flights", expectedContent = mapper.writeValueAsString(flights);
		when(readService.readAvailableFlights()).thenReturn(flights, new Flight[0], null);
		mvc.perform(get(uri)).andExpect(status().isOk()).andExpect(content().string(expectedContent));
        mvc.perform(get(uri)).andExpect(status().isNoContent()).andExpect(content().string("[]"));
        mvc.perform(get(uri)).andExpect(status().isInternalServerError()).andExpect(content().string(""));
    }
    
    @Test
	public void getAgentFlightsByTraveler() throws Exception {
		final Long HOUR = 3_600_000l;
		Long now = Instant.now().toEpochMilli();
		Timestamp futureOne = new Timestamp(now + HOUR), futureTwo = new Timestamp(now + 2 * HOUR);
		Flight[] flights = { new Flight(1l, 2l, futureOne, 3l, (short) 8, 150f),
			new Flight(2l, 1l, futureTwo, 3l, (short) 5, 151f) };
		String uri = "/agent/flights/" + "1" + "/traveler/" + "1", expectedContent = mapper.writeValueAsString(flights);
		when(readService.readTravelerFlightsByAgent(1l, 1l)).thenReturn(flights, new Flight[0], null);
		mvc.perform(get(uri)).andExpect(status().isOk()).andExpect(content().string(expectedContent));
        mvc.perform(get(uri)).andExpect(status().isNoContent()).andExpect(content().string("[]"));
        mvc.perform(get(uri)).andExpect(status().isInternalServerError()).andExpect(content().string(""));
	}

}