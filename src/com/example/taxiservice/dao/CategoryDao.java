package com.example.taxiservice.dao;

import com.example.taxiservice.model.Category;

public interface CategoryDao extends EntityDao<Category> {
	
	Category find(String name);
}
