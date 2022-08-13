package com.example.taxiservice.dao;

import java.util.List;

import com.example.taxiservice.model.Car;

/**
 * Interface for Car DAO object
 */
public interface CarDao extends EntityDao<Car> {

    /**
     * Finds car in DB by specified categoryId and capacity
     *
     * @param categoryId - car category identifier.
     * 
     * @param capacity   - car seat count.
     * 
     * @return Car entity with given parameters.
     */
    Car find(Long categoryId, Integer capacity);

    /**
     * Finds car in DB by specified capacity
     *
     * @param capacity - car seat count.
     * 
     * @return Car entity with given capacity.
     */
    Car findByCapacity(Integer capacity);

    /**
     * Finds all car in DB with specified trip id
     *
     * @param tripId - Trip identifier.
     * 
     * @return List of car entities.
     */
    List<Car> findCarsByTripId(Long tripId);

    /**
     * Finds all cars in DB by specified categoryId and capacity
     *
     * @param categoryId - car category identifier.
     * 
     * @param capacity   - car seat count.
     * 
     * @return List of car entities with given parameters.
     */
    List<Car> findCars(Long categoryId, Integer capacity);
}
