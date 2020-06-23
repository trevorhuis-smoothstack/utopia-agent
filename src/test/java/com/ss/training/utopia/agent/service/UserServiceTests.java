package com.ss.training.utopia.agent.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ss.training.utopia.agent.dao.BookingDAO;
import com.ss.training.utopia.agent.dao.UserDAO;
import com.ss.training.utopia.agent.entity.User;

/**
 * @author Trevor Huis in 't Veld
 */
public class UserServiceTests {

	@Mock
	private UserDAO userDAO;

	@InjectMocks
	private UserService userService;

	@Mock
	private BookingDAO bookingDao;


	@BeforeEach
	private void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createUserTest() {
		String password = "password";
		User user = new User(null, null, null, password, null);
		Mockito.when(userDAO.save(user)).thenReturn(null);
		assertTrue(userService.createUser(user));
		assertTrue(new BCryptPasswordEncoder().matches(password, user.getPassword()));
		Mockito.when(userDAO.save(user)).thenThrow(new RuntimeException());
		assertNull(userService.createUser(user));
	}

	@Test
	public void userIsTravelerTest() {
		String username = "Username";
		User traveler = new User(null, null, null, null, "TRAVELER"),
				nonTraveler = new User(null, null, null, null, "AGENT");
		Mockito.when(userDAO.findByUsername(username)).thenReturn(traveler, nonTraveler, null);
		assertTrue(userService.userIsTraveler(username));
		assertFalse(userService.userIsTraveler(username));
		assertFalse(userService.userIsTraveler(username));
		Mockito.when(userDAO.findByUsername(username)).thenThrow(new RuntimeException());
		assertNull(userService.userIsTraveler(username));
	}

}