package com.ss.training.utopia.agent.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(AgentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AgentControllerTestSuite {

}