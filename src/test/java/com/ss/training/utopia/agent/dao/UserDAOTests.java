// package com.ss.training.utopia.agent.dao;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNull;

// import org.junit.Test;
// import org.springframework.beans.factory.annotation.Autowired;

// import com.ss.training.utopia.agent.entity.User;

// import org.springframework.test.annotation.DirtiesContext;
// import org.springframework.test.annotation.DirtiesContext.ClassMode;

// import org.junit.runner.RunWith;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.context.junit4.SpringRunner;
// import com.ss.training.utopia.agent.AgentApplication;
// import com.ss.training.utopia.agent.H2TestProfileJPAConfig;

// /**
//  * @author Trevor Huis in 't Veld
//  */
// @RunWith(SpringRunner.class)
// @SpringBootTest(classes = {AgentApplication.class, 
// H2TestProfileJPAConfig.class})
// @ActiveProfiles("test")
// @DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
// public class UserDAOTests {

// 	@Autowired
// 	UserDAO userDAO;

// 	@Test
// 	public void findByUsernameTest() {
// 		String thisUsername = "ThisUsername", otherUsername = "OtherUsername", notAUserName = "NotAUserName";
// 		User thisUser = new User(null, thisUsername, null, null, null);
// 		User otherUser = new User(null, otherUsername, null, null, null);
// 		userDAO.save(thisUser);
// 		userDAO.save(otherUser);
// 		assertEquals(thisUser, userDAO.findByUsername(thisUsername));
// 		assertNull(userDAO.findByUsername(notAUserName));
// 	}

// }