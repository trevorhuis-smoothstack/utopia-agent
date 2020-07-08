package com.ss.training.utopia.agent.controller;

public class AgentFlightControllerTestSuite {
    
    // @Test
	// public void readAvailableFlights() {
    //            // Times
    //    final Long HOUR = (long) 3_600_000;
    //    Long now = Instant.now().toEpochMilli();
    //    Timestamp past = new Timestamp(now - HOUR);
    //    Timestamp future = new Timestamp(now + HOUR);

    //     Flight futureFlight = new Flight(2l, 2l, future, 1l,(short) 5, null);
    //    Flight pastFlight = new Flight(1l, 2l, past, 2l, null, null);

    //     List<Flight> flights = new ArrayList<Flight>();
    //     flights.add(futureFlight);
    //     flights.add(pastFlight);

    //     Mockito.when(flightDAO.findAvailable()).thenReturn(flights);

    //     Flight[] foundFlights = readService.readAvailableFlights();

    //     assertEquals(foundFlights.length, 2);
    // }
    
    // @Test
    // public void readAvailableFlightsTestException() {
    //     Mockito.doThrow(NullPointerException.class).when(flightDAO).findAvailable();
    //     Flight[] foundFlights = readService.readAvailableFlights();
    // 	assertEquals(foundFlights, null);
    // }	
    

    // @Test
	// public void readNoAvailableFlights() {

    //     Flight[] flights = readService.readAvailableFlights();

    //     assertEquals(flights.length, 0);
    // }

}