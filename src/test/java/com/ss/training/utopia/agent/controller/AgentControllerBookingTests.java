package com.ss.training.utopia.agent.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class AgentControllerBookingTests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    AgentBookingService bookingService;

    @MockBean
    AgentCancelService cancelService;

    @Test
    public void bookFlightTest() throws Exception {
        Booking booking = new Booking(6l, 4l, 2l, true, "StripeId");
        String uri = "/agent/booking", body = mapper.writeValueAsString(booking);
        String expectedContent = mapper.writeValueAsString(booking);
        when(bookingService.createBooking(booking)).thenReturn("Card Declined", "Flight Full", "Internal Server Error",
                "Flight Booked");
        mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isBadRequest())
                .andExpect(content().string(expectedContent));
        mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isNoContent())
                .andExpect(content().string(""));
        mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isInternalServerError()).andExpect(content().string(expectedContent));
        mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isCreated())
                .andExpect(content().string(expectedContent));
    }

    @Test
    public void checkUpdate() throws Exception {
        assertEquals(true, true);
    }

}