package com.example.taxiservice.model;

/**
 * TripStatus entity.
 */
public enum TripStatus {
	NEW, ACCEPTED, COMPLETED, CANCELLED;

	public static TripStatus getTripStatus(Trip trip) {
		int tripStatusId = trip.getStatusId();
		return TripStatus.values()[tripStatusId];
	}

	public String getName() {
		return name().toLowerCase();
	}
}
