package com.ss.training.utopia.agent.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.training.utopia.agent.entity.User;
import com.ss.training.utopia.agent.service.UserService;

@WebMvcTest(AgentUserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AgentControllerUserTestSuite {

	@Autowired
	private MockMvc mvc;
	@Autowired
    private ObjectMapper mapper;

	@MockBean
    private UserService userService;
    

	@Test
	public void userIsTravelerTest() throws Exception {
        User user = new User(1l, "Username", "Test", null, "Traveler");
        String username = "Username", uri = "/agent/traveler/" + username;
        String expectedContent = mapper.writeValueAsString(user);
		Mockito.when(userService.getUserAndCheckTraveler(username)).thenReturn(user);
		mvc.perform(get(uri)).andExpect(status().isOk()).andExpect(content().string(expectedContent));
		Mockito.when(userService.getUserAndCheckTraveler(username)).thenThrow(new RuntimeException());
		mvc.perform(get(uri)).andExpect(status().isInternalServerError()).andExpect(content().string(""));
	}

	@Test
	public void getUserByUsernameTest() throws Exception {
		User user = new User(1l, "Username", "Test", null, "Traveler");
        String username = "Username", uri = "/agent/user/username/" + username;
        String expectedContent = mapper.writeValueAsString(user);
		Mockito.when(userService.getUserByUsername(username)).thenReturn(user);
		mvc.perform(get(uri)).andExpect(status().isOk()).andExpect(content().string(expectedContent));
		Mockito.when(userService.getUserByUsername(username)).thenThrow(new RuntimeException());
		mvc.perform(get(uri)).andExpect(status().isInternalServerError()).andExpect(content().string(""));
    }
    
    @Test
	public void getUserByIdTest() throws Exception {
        User user = new User(1l, "Username", "Test", null, "Traveler");
        Long userId = 1l;
        String uri = "/agent/user/id/" + userId;
        String expectedContent = mapper.writeValueAsString(user);
		Mockito.when(userService.getUserById(userId)).thenReturn(user);
		mvc.perform(get(uri)).andExpect(status().isOk()).andExpect(content().string(expectedContent));
		Mockito.when(userService.getUserById(userId)).thenThrow(new RuntimeException());
		mvc.perform(get(uri)).andExpect(status().isInternalServerError()).andExpect(content().string(""));
	}

	@Test
	public void CreateUserTest() throws Exception {
		User newUser = new User(null, "username", "Name", "Password", "TRAVELER"),
				createdUser = new User(6l, "username", "Name", "HashedPassword", "TRAVELER");
		String uri = "/agent/user", body = mapper.writeValueAsString(newUser),
				expectedContent = mapper.writeValueAsString(createdUser);
		when(userService.createUser(newUser)).thenReturn(createdUser);
		mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isCreated())
				.andExpect(content().string(expectedContent));
		when(userService.createUser(newUser)).thenThrow(new RuntimeException());
		mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isInternalServerError()).andExpect(content().string(""));
	}

}