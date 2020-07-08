package com.ss.training.utopia.agent.dao;

import java.util.List;

import com.ss.training.utopia.agent.entity.Flight;
import com.ss.training.utopia.agent.entity.FlightPk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** 
 * @author Trevor Huis in 't Veld
 */

@Repository
public interface FlightDAO extends JpaRepository<Flight, FlightPk> {
    Flight findByFlightId(Long flightId);
    
    @Query(
        value = "SELECT * FROM tbl_flight f WHERE f.seatsAvailable > 0 AND f.departTime > CURRENT_TIMESTAMP", 
        nativeQuery = true)
    public List<Flight> findAvailable();

    @Query(value = "SELECT * FROM tbl_flight f where f.flightId IN (SELECT b.flightId FROM tbl_booking b WHERE b.bookerId = ?1 AND b.travelerId = ?2 AND b.active = true)",
        nativeQuery = true)
        public List<Flight> findCancellableFlightsByTravelerId(Long agentId, Long travelerId);

}