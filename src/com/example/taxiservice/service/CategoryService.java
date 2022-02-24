package com.example.taxiservice.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.CategoryDao;
import com.example.taxiservice.dao.DBManager;
import com.example.taxiservice.dao.mysql.MySqlCategoryDao;
import com.example.taxiservice.model.Category;


public class CategoryService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);
	
	private CategoryDao categoryDao;
	
	public CategoryService() {
		this.categoryDao = new MySqlCategoryDao(DBManager.getInstance());
	}
	
	public BigDecimal getPrice(String name) {
		Category category = categoryDao.find(name);
		return category.getPrice();
	}
	
	public List<Category> findAll(){
		return categoryDao.findAll();
	}

}
