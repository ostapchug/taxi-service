package com.example.taxiservice.model;

public class Location extends Entity {

	private static final long serialVersionUID = 3768408533435589862L;
	
	private String streetName;
	private String streetNumber;
	
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

	@Override
	public String toString() {
		return "Location [streetName=" + streetName + ", streetNumber=" + streetNumber + "]";
	}
		
}
