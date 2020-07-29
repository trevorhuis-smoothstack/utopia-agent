package com.ss.training.utopia.agent.controller;

import com.ss.training.utopia.agent.entity.User;
import com.ss.training.utopia.agent.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Trevor Huis in 't Veld
 */
@RestController
@RequestMapping(path = "/agent")
public class AgentUserController {

    @Autowired UserService userService;

    @GetMapping(path = "/user/username/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		User user = null;
		HttpStatus status = HttpStatus.OK;

		try {
			user = userService.getUserByUsername(username);
        } catch (Throwable t) {
            return new ResponseEntity<User>(user, HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
		if (user == null) {
			status = HttpStatus.NO_CONTENT;
		}

		return new ResponseEntity<User>(user, status);
	}

	@GetMapping(path = "/traveler/{username}")
	public ResponseEntity<User> getTravelerByUsername(@PathVariable String username) {
		User user = null;
		HttpStatus status = HttpStatus.OK;

		try {
			user = userService.getUserAndCheckTraveler(username);
        } catch (Throwable t) {
            return new ResponseEntity<User>(user, HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
		if (user == null) {
			status = HttpStatus.NO_CONTENT;
		}

		return new ResponseEntity<User>(user, status);
	}

	@GetMapping(path = "/user/id/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable Long userId) {
		User user = null;
		HttpStatus status = HttpStatus.OK;

		try {
			user = userService.getUserById(userId);
        } catch (Throwable t) {
            return new ResponseEntity<User>(user, HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
		if (user == null) {
			status = HttpStatus.NOT_FOUND;
		}

		return new ResponseEntity<User>(user, status);
	}

	@PostMapping(path = "/user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		HttpStatus status = HttpStatus.CREATED;
		try {
			user = userService.createUser(user);
		} catch (Throwable t) {
			user = null;
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<User>(user, status);

	}

}