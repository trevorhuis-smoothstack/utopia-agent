package com.ss.training.utopia.agent.dao;

import com.ss.training.utopia.agent.entity.Airport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Trevor Huis in 't Veld
 */
@Repository
public interface AirportDAO extends JpaRepository<Airport, Long> {
    Airport findByAirportId(Integer id);
}
