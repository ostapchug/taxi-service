package com.example.taxiservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.CarDao;
import com.example.taxiservice.dao.DBManager;
import com.example.taxiservice.dao.mysql.MySqlCarDao;
import com.example.taxiservice.model.Car;

public class CarService {
	private static final Logger LOG = LoggerFactory.getLogger(CarService.class);
	
	private CarDao carDao;

	public CarService() {
		this.carDao = new MySqlCarDao(DBManager.getInstance());
	}
	
	public Car find(Long id) {
		return carDao.find(id);
	}
	
	public Car find(Long categoryId, Integer capacity) {
		return carDao.find(categoryId, capacity);
	}
	
	public Car findByCapacity(Integer capacity) {
		return carDao.findByCapacity(capacity);
	}
	
	public List<Car> findCars(Long categoryId, Integer capacity){
		return carDao.findCars(categoryId, capacity);
	}
	
}