package com.example.taxiservice.dao;

import java.util.List;

import com.example.taxiservice.model.Category;

/**
 * Interface for Category DAO object
 */
public interface CategoryDao extends EntityDao<Category> {
	
	/**
	 * Finds category in DB by specified id and language
	 *
	 * @param id - category id.
	 * 
	 * @param lang - language.
	 * 
	 * @return Category entity with given parameters.
	 */
	Category find(Long id, String lang);
	
	/**
	 * Finds all categories in DB
	 *
	 * @return List of category entities.
	 */
	List<Category> findAll();
	
	/**
	 * Finds all categories in DB by specified language
	 *
	 * @return List of category entities.
	 */	
	List<Category> findAll(String lang);
}
