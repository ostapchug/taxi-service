package com.example.taxiservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.CarDao;
import com.example.taxiservice.factory.annotation.InjectByType;
import com.example.taxiservice.factory.annotation.Singleton;
import com.example.taxiservice.model.Car;

/**
 * Service layer for Car DAO object.
 */
@Singleton
public class CarService {

	private static final Logger LOG = LoggerFactory.getLogger(CarService.class);

	@InjectByType
	private CarDao carDao;

	public CarService() {
		LOG.info("CarService initialized");
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

	public List<Car> findCars(Long categoryId, Integer capacity) {
		return carDao.findCars(categoryId, capacity);
	}

	public List<Car> findCarsByTripId(Long tripId) {
		return carDao.findCarsByTripId(tripId);
	}

}
