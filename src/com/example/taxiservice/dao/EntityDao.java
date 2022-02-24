package com.example.taxiservice.dao;

import java.sql.ResultSet;
import java.util.List;

/**
 *Interface for all DAO objects
 *
 * @param <T> - object type
 */
public interface EntityDao<T> {
	
	/**
	 * Finds object in DB by it's id
	 *
	 * @param id - id of the object that should be found
	 * @return object type <code>T</code>, which id is equal to specified
	 * 
	 */	
	T find (Long id);
	
	/**
	 *Saves object in DB
	 *
	 * @param entity - object to be saved
	 */
	void insert (T entity);
	
	/**
	 *Updates object in DB
	 *
	 * @param entity - object to be updated
	 */
	void update (T entity);
	
	/**
	 *Deletes object from DB
	 *
	 * @param entity - object to be deleted
	 */
	void delete (T entity);
	
	/**
	 * Finds all objects of type <code>T</code> in DB
	 *
	 * @return all objects that DB has at this point
	 */
	List<T> findAll();
	
	/**
     * Extracts an entity from the ResultSet row
     * Implementations are not supposed to move cursor of the resultSet via next() method
	 *
	 * @param set - database ResultSet
	 * @return object type <code>T</code>, entity that were extracted
	 * 
	 */		
	T mapRow(ResultSet set);

}
