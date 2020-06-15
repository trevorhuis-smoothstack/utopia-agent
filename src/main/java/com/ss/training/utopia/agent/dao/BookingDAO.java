package com.ss.training.utopia.agent.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ss.training.utopia.agent.entity.Booking;
import com.ss.training.utopia.agent.entity.BookingPk;

/**
 * @author Trevor Huis in 't Veld
 */
@Repository
public interface BookingDAO extends JpaRepository<Booking, BookingPk> {

    @Query(
        value = "SELECT * FROM tbl_booking b WHERE b.bookerId = ?1 AND b.active = true AND b.flightId NOT IN (SELECT f.flightId FROM tbl_flight f WHERE f.departTime <= CURRENT_TIMESTAMP)", 
        nativeQuery = true)
    public List<Booking> findCancellable(Long agentId);

    List<Booking> findByTravelerIdAndFlightId(Long travelerId, Long flightId);
    

	List<Booking> findByBookerId(Long bookerId);

}