package com.ss.training.utopia.dao;

import com.ss.training.utopia.agent.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Trevor Huis in 't Veld
 */
@Repository
public interface UserDAO extends JpaRepository<User, Long> {
	public User findByUsername(String username);
}