package com.ss.training.utopia.agent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ss.training.utopia.agent.dao.UserDAO;
import com.ss.training.utopia.agent.entity.User;

/**
 * @author Trevor Huis in 't Veld
 */
@Component
public class UserService {

	@Autowired
    UserDAO userDao;

    public User createUser(User user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		userDao.save(user);
		user.setPassword(null);
		return user;
	}

	public User getUser(String username) {
		return userDao.findByUsername(username);
	}

	public User getUserById(Long userId) {
		User user = userDao.findByUserId(userId);
		user.setPassword(null);

		return user;
	}

	public User getUserAndCheckPassword(User user) {
		User foundUser =  userDao.findByUsername(user.getUsername());
		String sentPassword = new BCryptPasswordEncoder().encode(user.getPassword());
		System.out.println(sentPassword);
		System.out.println(foundUser.getPassword());

		if(sentPassword.matches(foundUser.getPassword()) && foundUser.getRole().equals("TRAVELER")) {
			foundUser.setPassword(null);
			return foundUser;
		}

		return null;
	}

}
