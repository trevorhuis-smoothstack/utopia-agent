package com.ss.training.utopia.agent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ss.training.utopia.agent.dao.UserDAO;
import com.ss.training.utopia.agent.entity.User;

/**
 * @author Trevor Huis in 't Veld
 */
public class UserService {

	@Autowired
    UserDAO userDao;

    public Boolean createUser(User user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		try {
			userDao.save(user);
		} catch (Throwable t) {
			return null;
		}
		return true;
	}

	public Boolean usernameAvailable(String username) {
		User user;
		try {
			user = userDao.findByUsername(username);
		} catch (Throwable t) {
			return null;
		}
		return user == null;
	}

    public Boolean userIsTraveler(String username) {
		User user;
		try {
			user = userDao.findByUsername(username);
		} catch (Throwable t) {
			return null;
		}
		return (user != null && "TRAVELER".equals(user.getRole()));
	}

}
