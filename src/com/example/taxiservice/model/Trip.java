package com.example.taxiservice.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Trip entity
 */
public class Trip extends Entity {

	private static final long serialVersionUID = -4514764917915340920L;
	
	private Long personId;
	private Long originId;
	private Long destinationId;
	private BigDecimal distance;
	private Timestamp date;
	private BigDecimal bill;
	private int statusId;
	
	public Long getPersonId() {
		return personId;
	}
	
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	
	public Long getOriginId() {
		return originId;
	}
	
	public void setOriginId(Long originId) {
		this.originId = originId;
	}
	
	public Long getDestinationId() {
		return destinationId;
	}
	
	public void setDestinationId(Long destinationId) {
		this.destinationId = destinationId;
	}
	
	public BigDecimal getDistance() {
		return distance;
	}
	
	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}
	
	public Timestamp getDate() {
		return date;
	}
	
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	public BigDecimal getBill() {
		return bill;
	}
	
	public void setBill(BigDecimal bill) {
		this.bill = bill;
	}
	
	public int getStatusId() {
		return statusId;
	}
	
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	@Override
	public String toString() {
		return "Trip [personId=" + personId + ", originId=" + originId + ", destinationId=" + destinationId
				+ ", distance=" + distance + ", date=" + date + ", bill=" + bill + ", statusId=" + statusId + "]";
	}
	
}
