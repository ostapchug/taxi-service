package com.example.taxiservice.model;

import java.math.BigDecimal;

/**
 * Location entity.
 */
public class Location extends Entity {

	private static final long serialVersionUID = 3768408533435589862L;
	
	private String streetName;
	private String streetNumber;
	private BigDecimal latitude;
	private BigDecimal longitude;
	
	public String getStreetName() {
		return streetName;
	}
	
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	
	public String getStreetNumber() {
		return streetNumber;
	}
	
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return streetName + ", " + streetNumber;
	}
		
}
