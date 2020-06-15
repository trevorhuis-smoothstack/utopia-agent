package com.ss.training.utopia.agent.dao;

import com.ss.training.utopia.agent.entity.Flight;
import com.ss.training.utopia.agent.entity.FlightPk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 
 * @author Trevor Huis in 't Veld
 */

@Repository
public interface FlightDAO extends JpaRepository<Flight, FlightPk> {

}