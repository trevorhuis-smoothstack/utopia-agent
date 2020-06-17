package com.ss.training.utopia.agent.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.ss.training.utopia.agent.entity.User;

/**
 * @author Trevor Huis in 't Veld
 */
@DataJpaTest
public class UserDAOTests {

	@Autowired
	private TestEntityManager testEntityManager;
	@Autowired
	private UserDAO userDao;

	@Test
	public void findByUsernameTest() {
		String thisUsername = "ThisUsername", otherUsername = "OtherUsername", notAUserName = "NotAUserName";
		User thisUser = new User(null, thisUsername, null, null, null),
				otherUser = new User(null, otherUsername, null, null, null);
		testEntityManager.persist(thisUser);
		testEntityManager.persist(otherUser);
		testEntityManager.flush();
		assertEquals(thisUser, userDao.findByUsername(thisUsername));
		assertNull(userDao.findByUsername(notAUserName));
	}

}