package com.ss.training.utopia.agent.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({FlightDAOTestSuite.class,BookingDAOTestSuite.class,AitportDAOTestSuite.class,UserDAOTestSuite.class})
public class DAOTestSuite {
}