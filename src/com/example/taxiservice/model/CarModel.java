package com.example.taxiservice.model;

public class CarModel extends Entity {

	private static final long serialVersionUID = 6047354025419332304L;
	
	private String brand;
	private String name;
	private String color;
	private int year;
	private int seatCount;
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getSeatCount() {
		return seatCount;
	}
	
	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}
	
	@Override
	public String toString() {
		return "CarModel [brand=" + brand + ", name=" + name + ", color=" + color + ", year=" + year + ", seatCount="
				+ seatCount + "]";
	}

}
