package com.ss.training.utopia.agent.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.ss.training.utopia.agent.dao.BookingDAO;
import com.ss.training.utopia.agent.dao.UserDAO;
import com.ss.training.utopia.agent.entity.User;

/**
 * @author Trevor Huis in 't Veld
 */
public class UserServiceTests {

	@Mock
	UserDAO userDAO;

	@InjectMocks
	UserService userService;
	
	@InjectMocks
	AgentReadService readService;

	@Mock
	BookingDAO bookingDao;


	@BeforeEach
	private void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createUserTest() {
		String password = "password";
		User user = new User(null, null, null, password, null);
		Mockito.when(userDAO.save(user)).thenReturn(null);
		assertEquals(userService.createUser(user), user);
		Mockito.when(userDAO.save(user)).thenThrow(new RuntimeException());
	}
	
	@Test
    public void readUserByUsername() {
		String password = "password";
		User user = new User(1l, "username", null, password, null);
    	Mockito.when(userDAO.findByUsername("username")).thenReturn(user);
    	
    	User foundUser = userService.getUserByUsername("username");
    	assertEquals(foundUser, user);
    }
	
	@Test
    public void readUserByUsernameException() {
		try {
			Mockito.doThrow(NullPointerException.class).when(userDAO).findByUsername("username");
		} catch (Exception e){
			User foundUser = userService.getUserByUsername("username");
    		assertEquals(foundUser, null);
		}
    }

    @Test
    public void readUserByUserId() {
    	String password = "password";
		User user = new User(1l, "username", null, password, null);
    	Mockito.when(userDAO.findByUserId(1l)).thenReturn(user);
    	
    	User foundUser = userService.getUserById(1l);
    	assertEquals(foundUser, user);
    }
    
    @Test
    public void readUserByUserIdException() {
    	try {
			Mockito.doThrow(NullPointerException.class).when(userDAO).findByUserId(1l);
		} catch (Exception e){
			User foundUser = userService.getUserById(1l);
			assertEquals(foundUser, null);
		}
    }
    
    @Test
    public void readUserByUsernameAndTravelerTest() {
    	String password = "password";
		User user = new User(1l, "username", null, password, "TRAVELER");
    	Mockito.when(userDAO.findByUsername("username")).thenReturn(user);
    	
    	User foundUser = userService.getUserAndCheckTraveler("username");
    	assertEquals(foundUser, user);
    }
    
    @Test
    public void readUserByUsernameAndTravelerException() {
		try {
			Mockito.doThrow(NullPointerException.class).when(userDAO).findByUsername("username");
		} catch (Exception e){
			User foundUser = userService.getUserAndCheckTraveler("username");
    		assertEquals(foundUser, null);
		}
    	
    }
}