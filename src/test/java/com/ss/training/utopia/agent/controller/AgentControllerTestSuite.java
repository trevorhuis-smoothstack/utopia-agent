package com.ss.training.utopia.agent.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({AgentControllerUserTestSuite.class,AgentControllerReadTestSuite.class,AgentControllerCancelTestSuite.class,AgentControllerBookingTestSuite.class})
public class AgentControllerTestSuite {
}