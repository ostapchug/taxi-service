package com.example.taxiservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.CategoryDao;
import com.example.taxiservice.factory.annotation.InjectByType;
import com.example.taxiservice.factory.annotation.Singleton;
import com.example.taxiservice.model.Category;

/**
 * Service layer for Category DAO object.
 */
@Singleton
public class CategoryService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);
	
	@InjectByType
	private CategoryDao categoryDao;
	
	public CategoryService() {
		LOG.info("CategoryService initialized");
	}
	
	public Category find(Long id) {
		return categoryDao.find(id);
	}
	
	public Category find(Long id, String lang) {
		Category result = null;
		
		if(lang == null) {
			result = categoryDao.find(id);
		}else {
			result = categoryDao.find(id, lang);
		}
		
		return result;
	}
	
	public List<Category> findAll(String lang){
		List<Category> result = null;
		
		if(lang == null) {
			result = categoryDao.findAll();
		}else {
			result = categoryDao.findAll(lang);
		}
		
		return result;
	}

}
