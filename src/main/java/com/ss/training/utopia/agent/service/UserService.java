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
		return user;

	}

	public User getUser(String username) {
		User user = userDao.findByUsername(username);
		user.setPassword(null);

		return user;
	}

}
