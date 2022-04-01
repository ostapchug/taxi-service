package com.example.taxiservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.CategoryDao;
import com.example.taxiservice.model.Category;


public class CategoryService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);
	
	private CategoryDao categoryDao;
	
	public CategoryService(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
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
