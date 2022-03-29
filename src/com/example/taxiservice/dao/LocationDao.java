package com.example.taxiservice.dao;

import java.math.BigDecimal;
import java.util.List;

import com.example.taxiservice.model.Location;

public interface LocationDao extends EntityDao<Location> {
	
	Location find(Long id, String lang);
	
	BigDecimal findDistance(Location origin, Location destination);
	
	/**
	 * Finds all objects of type <code>Location</code> in DB
	 *
	 * @return all objects that DB has at this point
	 */
	List<Location> findAll();
	
	List<Location> findAll(String lang);
}
