package com.example.taxiservice.model;

public enum CarStatus {
	READY, BUSY, INACTIVE;
	
	public static CarStatus getCarStatus(Car car){
		int carStatusId = car.getStatusId();
		return CarStatus.values()[carStatusId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}

}
