package com.ss.training.utopia.agent.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author Trevor Huis in 't Veld
 */
@Entity
@Table(name = "tbl_flight")
@IdClass(FlightPk.class)
public class Flight implements Serializable {

	/**
     *
     */
    private static final long serialVersionUID = 1214165189031743850L;

    @Id
	@Column
	private Long departId, arriveId;

	@Id
	@Column
	private Timestamp departTime;

	@Column(unique = true)
	private Long flightId;

	@Column
	private Short seatsAvailable;

	@Column
	private Float price;

	public Flight() {
	}

	public Flight(Long departId, Long arriveId, Timestamp departTime, Long flightId, Short seatsAvailable,
			Float price) {
		this.departId = departId;
		this.arriveId = arriveId;
		this.departTime = departTime;
		this.flightId = flightId;
		this.seatsAvailable = seatsAvailable;
		this.price = price;
	}

	public Short getSeatsAvailable() {
		return seatsAvailable;
	}

	public void setSeatsAvailable(Short seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Long getDepartId() {
		return departId;
	}

	public Long getArriveId() {
		return arriveId;
	}

	public Timestamp getDepartTime() {
		return departTime;
	}

	public Long getFlightId() {
		return flightId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arriveId == null) ? 0 : arriveId.hashCode());
		result = prime * result + ((departId == null) ? 0 : departId.hashCode());
		result = prime * result + ((departTime == null) ? 0 : departTime.hashCode());
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
		Flight other = (Flight) obj;
		if (arriveId == null) {
			if (other.arriveId != null)
				return false;
		} else if (!arriveId.equals(other.arriveId))
			return false;
		if (departId == null) {
			if (other.departId != null)
				return false;
		} else if (!departId.equals(other.departId))
			return false;
		if (departTime == null) {
			if (other.departTime != null)
				return false;
		} else if (!departTime.equals(other.departTime))
			return false;
		return true;
	}

}