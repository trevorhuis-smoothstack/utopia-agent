package com.ss.training.utopia.agent.dao;

import java.sql.Timestamp;
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
    
    @Query(
        value = "SELECT * FROM tbl_flight f WHERE f.seatsAvailable > 0 " + 
        "AND f.departId LIKE CONCAT('%', ?1) " +
        "AND f.arriveId LIKE CONCAT('%', ?2) " +
        "AND f.price <= ?3 " +
        "AND f.departTime BETWEEN STR_TO_DATE(?4, '%Y-%m-%d %h:%i') AND STR_TO_DATE(?5, '%Y-%m-%d %h:%i');", 
        nativeQuery = true)
    public List<Flight> findAvailable(String departId, String arriveId, Float price, String dateBegin, String dateEnd);

    // public List<Flight> findAvailable(String departId, String arriveId, Float price, Timestamp dateBegin, Timestamp dateEnd);
    // value = "SELECT * FROM tbl_flight f WHERE f.seatsAvailable > 0 AND WHERE f.departId LIKE ?1 AND f.departTime BETWEEN ?4 AND ?5",


}