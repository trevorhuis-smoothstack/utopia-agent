package com.ss.training.utopia.agent.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Trevor Huis in 't Veld
 */
@Entity
@Table(name = "tbl_booking")
@IdClass(BookingPk.class)
public class Booking implements Serializable {

	/**
     *
     */
    private static final long serialVersionUID = 9152770559513294559L;

    @Id
	@Column
	private Long travelerId;
	
	@Id
	@Column
	private Long flightId;

	@Column
	private Long bookerId;

	@Column
	private Boolean active;

	@Column
	private String stripeId;

	@ManyToOne
	@JoinColumn(name="flightId")
	private Flight flight;

	@OneToOne
	@JoinColumn(name="bookerId", referencedColumnName="userId")
	private User booker;

	@OneToOne
	@JoinColumn(name="travelerId", referencedColumnName="userId")
	private User traveler;
	
	public Booking() {
	}

	public Booking(Long travelerId, Long flightId, Long bookerId, Boolean active, String stripeId) {
		this.travelerId = travelerId;
		this.flightId = flightId;
		this.bookerId = bookerId;
		this.active = active;
		this.stripeId = stripeId;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getTravelerId() {
		return travelerId;
	}

	public Long getFlightId() {
		return flightId;
	}

	public Long getBookerId() {
		return bookerId;
	}

	public String getStripeId() {
		return stripeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flightId == null) ? 0 : flightId.hashCode());
		result = prime * result + ((travelerId == null) ? 0 : travelerId.hashCode());
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
		Booking other = (Booking) obj;
		if (flightId == null) {
			if (other.flightId != null)
				return false;
		} else if (!flightId.equals(other.flightId))
			return false;
		if (travelerId == null) {
			if (other.travelerId != null)
				return false;
		} else if (!travelerId.equals(other.travelerId))
			return false;
		return true;
	}

	public void setStripeId(String stripeId) {
		this.stripeId = stripeId;
	}

}