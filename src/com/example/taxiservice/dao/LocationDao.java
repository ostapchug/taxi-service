package com.example.taxiservice.dao;

import java.math.BigDecimal;
import java.util.List;

import com.example.taxiservice.model.Location;

public interface LocationDao extends EntityDao<Location> {
	Location find(String name, String number);
	
	List<String> findAllNumbers(String streetName);
	
	List<String> findNamesDistinct();
	
	BigDecimal findDistance(Long originId, Long destId);
}
