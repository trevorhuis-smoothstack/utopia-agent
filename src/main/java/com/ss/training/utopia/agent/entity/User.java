package com.ss.training.utopia.agent.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Trevor Huis in 't Veld
 */
@Entity
@Table(name = "tbl_user")
public class User implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 4840389453992095555L;

	@Id
	@Column
	@GeneratedValue
	private Long userId;

	@Column(unique = true)
	private String username;

	@Column
    private String name, password, role;

    public User() {
	}
    
    public User(Long userId, String username, String name, String password, String role) {
		this.userId = userId;
		this.username = username;
		this.name = name;
		this.password = password;
		this.role = role;
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

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