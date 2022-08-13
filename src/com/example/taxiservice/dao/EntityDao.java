package com.example.taxiservice.dao;

/**
 * CRUD interface for DAO objects
 *
 * @param <T> - object type.
 */
public interface EntityDao<T> {

    /**
     * Finds object in DB by it's id
     *
     * @param id - id of the object that should be found.
     * 
     * @return object type <code>T</code>, which id is equal to specified.
     * 
     */
    T find(Long id);

    /**
     * Saves object in DB
     *
     * @param entity - object to be saved.
     * 
     * @return boolean - true if operation successful otherwise false.
     */
    boolean insert(T entity);

    /**
     * Updates object in DB
     *
     * @param entity - object to be updated.
     * 
     * @return boolean - true if operation successful, false otherwise.
     */
    boolean update(T entity);
}
