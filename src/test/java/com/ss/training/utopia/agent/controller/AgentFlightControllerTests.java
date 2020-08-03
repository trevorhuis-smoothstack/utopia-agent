package com.ss.training.utopia.agent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.training.utopia.agent.entity.Flight;
import com.ss.training.utopia.agent.entity.FlightQuery;
import com.ss.training.utopia.agent.service.AgentReadFlightsService;
import com.ss.training.utopia.agent.service.AgentReadService;

@WebMvcTest(AgentFlightController.class)
@AutoConfigureMockMvc(addFilters = false)

public class AgentFlightControllerTests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AgentReadFlightsService service;
    
    @MockBean
	private AgentReadService readService;

    @Test
    public void readPremierFlights() throws Exception {
        final Long HOUR = 3_600_000l;
		Long now = Instant.now().toEpochMilli();
		Timestamp futureOne = new Timestamp(now + HOUR), futureTwo = new Timestamp(now + 2 * HOUR);
		Flight[] flights = { new Flight(1l, 2l, futureOne, 3l, (short) 8, 150f),
			new Flight(2l, 1l, futureTwo, 3l, (short) 5, 151f) };
		String uri = "/agent/flights/premier", expectedContent = mapper.writeValueAsString(flights);
		when(service.readPremierFlights()).thenReturn(flights, new Flight[0], null);
		mvc.perform(get(uri)).andExpect(status().isOk()).andExpect(content().string(expectedContent));
        mvc.perform(get(uri)).andExpect(status().isNoContent()).andExpect(content().string("[]"));
        mvc.perform(get(uri)).andExpect(status().isInternalServerError()).andExpect(content().string(""));
    }

    @Test
    public void readAvailableFlights() throws Exception {
        final Long HOUR = 3_600_000l;
        LocalDate today = LocalDate.now();
        String todaysDate = today.toString();
        String dateBegin = todaysDate;

		Long now = Instant.now().toEpochMilli();
		Timestamp futureOne = new Timestamp(now + HOUR), futureTwo = new Timestamp(now + 2 * HOUR);
		Flight[] flights = { new Flight(1l, 2l, futureOne, 3l, (short) 8, 50f),
            new Flight(2l, 1l, futureTwo, 3l, (short) 5, 51f) };
        FlightQuery fq = new FlightQuery("", "", dateBegin, "2100-01-01", 100.0f);
		String uri = "/agent/flights", expectedContent = mapper.writeValueAsString(flights);
		when(service.readAvailableFlights(fq)).thenReturn(flights, new Flight[0], null);
		mvc.perform(get(uri).queryParam("price", "100.0")).andExpect(status().isOk()).andExpect(content().string(expectedContent));
        mvc.perform(get(uri).queryParam("price", "100.0")).andExpect(status().isNoContent()).andExpect(content().string("[]"));
        mvc.perform(get(uri).queryParam("price", "100.0")).andExpect(status().isInternalServerError()).andExpect(content().string(""));
    }

}