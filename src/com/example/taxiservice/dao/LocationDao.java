package com.example.taxiservice.dao;

import java.math.BigDecimal;
import java.util.List;

import com.example.taxiservice.model.Location;

/**
 * Interface for Location DAO object
 */
public interface LocationDao extends EntityDao<Location> {

    /**
     * Finds location in DB by specified id and language
     *
     * @param id   - location id.
     * 
     * @param lang - language.
     * 
     * @return Location entity with given parameters.
     */
    Location find(Long id, String lang);

    /**
     * Finds distance between two locations
     *
     * @param origin      - origin location.
     * 
     * @param destination - destination location.
     * 
     * @return BigDecimal distance.
     */
    BigDecimal findDistance(Location origin, Location destination);

    /**
     * Finds all locations in DB
     *
     * @return List of location entities.
     */
    List<Location> findAll();

    /**
     * Finds all locations in DB by specified language
     * 
     * @param lang - language.
     *
     * @return List of location entities.
     */
    List<Location> findAll(String lang);
}
