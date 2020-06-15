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
    
    @Query(
        value = "SELECT * FROM tbl_flight f WHERE f.seatsAvailable > 0 AND f.departTime <= CURRENT_TIMESTAMP", 
        nativeQuery = true)
    public List<Flight> findAvailable();
}