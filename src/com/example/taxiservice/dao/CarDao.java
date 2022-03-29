package com.example.taxiservice.dao;

import java.util.List;

import com.example.taxiservice.model.Car;

public interface CarDao extends EntityDao<Car> {
	Car find(Long categoryId, Integer capacity);
	
	Car findByCapacity(Integer capacity);
	
	List<Car> findCars(Long categoryId, Integer capacity);
}
