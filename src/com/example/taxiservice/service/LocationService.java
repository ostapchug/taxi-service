package com.example.taxiservice.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.DBManager;
import com.example.taxiservice.dao.LocationDao;
import com.example.taxiservice.dao.mysql.MySqlLocationDao;
import com.example.taxiservice.model.Location;

public class LocationService {
	private static final Logger LOG = LoggerFactory.getLogger(LocationService.class);
	
	private LocationDao locationDao;

	public LocationService() {
		this.locationDao = new MySqlLocationDao(DBManager.getInstance());
	}
	
	public List<String> findAllNumbers(String streetName) {
		return locationDao.findAllNumbers(streetName);
	}
	
	public List<String> findNamesDistinct(){
		return locationDao.findNamesDistinct();
	}
	
	public BigDecimal findDistance(String originName, String originNumber, String destName, String destNumber) {
		Location origin = locationDao.find(originName, originNumber); 
		Location destination = locationDao.find(destName, destNumber); 
		Long originId = origin.getId();
		Long destId = destination.getId();
		return locationDao.findDistance(originId, destId).divide(new BigDecimal(1000.00), 2, RoundingMode.HALF_UP);
	}
	
	

}
