package com.example.taxiservice.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.example.taxiservice.model.Car;
import com.example.taxiservice.model.Trip;

public interface TripDao extends EntityDao<Trip> {
	
	boolean insert(Trip trip, Car... cars);
	
	List<Trip> findAll(int offset, int count, String sorting);
	
	List<Trip> findAllByDate(Timestamp [] dateRange, int offset, int count, String sorting);
	
	List<Trip> findAllByPersonId(Long personId, int offset, int count, String sorting);
	
	List<Trip> findAllByPersonIdAndDate(Long personId, Timestamp [] dateRange, int offset, int count, String sorting);
	
	Integer getCount();
	
	Integer getCountByDate(Timestamp [] dateRange);
	
	Integer getCountByPersonId(Long personId);
	
	Integer getCountByPersonIdAndDate(Long personId, Timestamp [] dateRange);
	
	boolean updateStatus(Trip trip, String status);
	
	BigDecimal getTotalBill(Long personId);

}
