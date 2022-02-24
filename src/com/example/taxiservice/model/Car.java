package com.example.taxiservice.model;

public class Car extends Entity{

	private static final long serialVersionUID = 4963338483931961954L;
	
	private String regNumber;
	private Long modelId;
	private Long categoryId;
	private Long locationId;
	private int statusId;
	
	public String getRegNumber() {
		return regNumber;
	}
	
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	
	public Long getModelId() {
		return modelId;
	}
	
	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	public Long getLocationId() {
		return locationId;
	}
	
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	
	public int getStatusId() {
		return statusId;
	}
	
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
	@Override
	public String toString() {
		return "Car [regNumber=" + regNumber + ", modelId=" + modelId + ", categoryId=" + categoryId + ", locationId="
				+ locationId + ", statusId=" + statusId + "]";
	}

}
