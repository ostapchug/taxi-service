package com.example.taxiservice.model.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.example.taxiservice.model.Car;

/**
 * Provide records for trip details page.
 */
public class TripDto {
	private Long id;
	private String personPhone;
	private String origin;
	private String destination;
	private BigDecimal distance;
	private Timestamp date;
	private BigDecimal bill;
	private String status;
	private List<Car> cars;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getPersonPhone() {
		return personPhone;
	}
	
	public void setPersonPhone(String personPhone) {
		this.personPhone = personPhone;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
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
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<Car> getCars() {
		return cars;
	}
	
	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

}
