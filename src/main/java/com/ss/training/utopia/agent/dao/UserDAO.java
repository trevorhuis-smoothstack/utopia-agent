package com.ss.training.utopia.agent.dao;

import com.ss.training.utopia.agent.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Trevor Huis in 't Veld
 */
public interface UserDAO extends JpaRepository<User, Long> {
	public User findByUsername(String username);

	public User findByUserId(Long userId);
}