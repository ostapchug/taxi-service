package com.example.taxiservice.model;

import java.sql.Blob;

public class Location extends Entity {

	private static final long serialVersionUID = 3768408533435589862L;
	
	private String streetName;
	private String streetNumber;
	private Blob coordinates;
	
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
	
	public Blob getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Blob coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public String toString() {
		return streetName + ", " + streetNumber;
	}
		
}
