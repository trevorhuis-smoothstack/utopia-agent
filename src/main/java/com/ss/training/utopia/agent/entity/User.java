package com.ss.training.utopia.agent.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author Trevor Huis in 't Veld
 */
@Entity
@Table(name = "tbl_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1439160334764663455L;

	@Id
	@Column
	@GeneratedValue
	private Long userId;

	@Column(unique = true)
	private String username;

	@Column
	private String name, password, role;

	@JsonBackReference
	@OneToMany(mappedBy = "traveler")
	private Set<Booking> bookingsFor;

	@JsonBackReference
	@OneToMany(mappedBy = "booker")
	private Set<Booking> bookingsBy;

	/**
	 * 
	 */
	public User() {
	}

	/**
	 * @param userId
	 * @param username
	 * @param name
	 * @param password
	 * @param role
	 */
	public User(Long userId, String username, String name, String password, String role) {
		this.userId = userId;
		this.username = username;
		this.name = name;
		this.password = password;
		this.role = role;
	}

	/**
	 * @return the bookingsFor
	 */
	public Set<Booking> getBookingsFor() {
		return bookingsFor;
	}

	/**
	 * @return the bookingsBy
	 */
	public Set<Booking> getBookingsBy() {
		return bookingsBy;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}
