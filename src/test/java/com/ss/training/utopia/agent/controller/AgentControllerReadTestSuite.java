package com.ss.training.utopia.agent.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.training.utopia.agent.entity.Airport;
import com.ss.training.utopia.agent.entity.Booking;
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
	public void getAgentBookings() throws Exception {
		Booking[] bookings = { new Booking(1l, 1l, 1l, true, null),
            new Booking(2l, 2l, 1l, true, null) };
		String uri = "/agent/bookings/" + "1", expectedContent = mapper.writeValueAsString(bookings);
		when(readService.readAgentBookings(1l)).thenReturn(bookings, new Booking[0], null);
		mvc.perform(get(uri)).andExpect(status().isOk()).andExpect(content().string(expectedContent));
        mvc.perform(get(uri)).andExpect(status().isNoContent()).andExpect(content().string("[]"));
        mvc.perform(get(uri)).andExpect(status().isInternalServerError()).andExpect(content().string(""));
	}

}