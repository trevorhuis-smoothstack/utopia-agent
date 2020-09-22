package com.ss.training.utopia.agent.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.training.utopia.agent.entity.Booking;
import com.ss.training.utopia.agent.service.AgentBookingService;
import com.ss.training.utopia.agent.service.AgentCancelService;

/**
 * @author Trevor Huis in 't Veld
 */
@WebMvcTest(AgentBookingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AgentControllerCancelTests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    AgentBookingService bookingService;

    @MockBean
    AgentCancelService cancelService;

    @Test
    public void cancelFlightTest() throws Exception {
        Booking booking = new Booking(6l, 4l, 2l, false, "StripeId");
        String uri = "/agent/booking", body = mapper.writeValueAsString(booking);
        String expectedContent = mapper.writeValueAsString(booking);
        when(cancelService.cancelBooking(booking)).thenReturn("Already Refunded", "Internal Server Error",
                "Flight Cancelled");
        mvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isBadRequest())
                .andExpect(content().string(expectedContent));
        mvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isInternalServerError()).andExpect(content().string(expectedContent));
        mvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }

}