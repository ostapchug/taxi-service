package com.example.taxiservice.dao;

import java.util.List;

import com.example.taxiservice.model.Category;

public interface CategoryDao extends EntityDao<Category> {
	
	Category find(Long id, String lang);
	
	/**
	 * Finds all objects of type <code>Category</code> in DB
	 *
	 * @return all objects that DB has at this point
	 */
	List<Category> findAll();
	
	List<Category> findAll(String lang);
}
