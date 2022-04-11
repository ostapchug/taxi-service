package com.example.taxiservice.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.example.taxiservice.model.Car;
import com.example.taxiservice.model.Trip;

/**
 * Interface for Trip DAO object
 */
public interface TripDao extends EntityDao<Trip> {
	
	/**
	 * Saves Trip object in DB
	 *
	 * @param trip - object to be saved.
	 * 
	 * @param List - list of bounded cars.
	 * 
	 * @return boolean - true if operation successful, false otherwise.
	 */
	boolean insert(Trip trip, Car... cars);
	
	/**
	 * Finds all trips in DB
	 * 
	 * @param offset - start position.
	 * 
	 * @param count - end position.
	 * 
	 * @param sorting - ordering method.
	 *
	 * @return List - list of trip entities.
	 */	
	List<Trip> findAll(int offset, int count, String sorting);
	
	/**
	 * Finds all trips in DB by specified dateRange
	 * 
	 * @param dateRange - filtering condition.
	 * 
	 * @param offset - start position.
	 * 
	 * @param count - end position.
	 * 
	 * @param sorting - ordering method.
	 *
	 * @return List - list of trip entities.
	 */		
	List<Trip> findAllByDate(Timestamp [] dateRange, int offset, int count, String sorting);
	
	/**
	 * Finds all trips in DB by specified personId
	 * 
	 * @param personId - filtering condition.
	 * 
	 * @param offset - start position.
	 * 
	 * @param count - end position.
	 * 
	 * @param sorting - sorting method.
	 *
	 * @return List - list of trip entities.
	 */		
	List<Trip> findAllByPersonId(Long personId, int offset, int count, String sorting);
	
	
	/**
	 * Finds all trips in DB by specified personId and dateRange
	 * 
	 * @param personId - filtering condition.
	 * 
	 * @param dateRange - filtering condition.
	 * 
	 * @param offset - start position.
	 * 
	 * @param count - end position.
	 * 
	 * @param sorting - sorting method.
	 *
	 * @return List - list of trip entities.
	 */		
	List<Trip> findAllByPersonIdAndDate(Long personId, Timestamp [] dateRange, int offset, int count, String sorting);
	
	/**
	 * Counts all trips in DB
	 * 
	 * @return Integer - count of all objects that DB has at this point. 
	 */
	Integer getCount();
	
	/**
	 * Counts all trips in DB by specified dateRange
	 * 
	 * @return Integer - count of all objects that DB has at this point. 
	 */
	Integer getCountByDate(Timestamp [] dateRange);
	
	/**
	 * Counts all trips in DB by specified personId
	 * 
	 * @return Integer - count of all objects that DB has at this point. 
	 */
	Integer getCountByPersonId(Long personId);
	
	/**
	 * Counts all trips in DB by specified personId and dateRange
	 * 
	 * @return Integer - count of all objects that DB has at this point. 
	 */
	Integer getCountByPersonIdAndDate(Long personId, Timestamp [] dateRange);
	
	/**
	 * Sets a status of specified trip
	 * 
	 * @return boolean - true if operation successful, false otherwise. 
	 */
	boolean updateStatus(Trip trip, String status);
	
	/**
	 * Counts total price of all completed trips by specified personId
	 * 
	 * @return BigDecimal - total price of all completed trips. 
	 */
	BigDecimal getTotalBill(Long personId);

}
