package com.example.taxiservice.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.AbstractDao;
import com.example.taxiservice.dao.CategoryDao;
import com.example.taxiservice.dao.DBManager;
import com.example.taxiservice.dao.Fields;
import com.example.taxiservice.model.Category;

public class MySqlCategoryDao extends AbstractDao<Category> implements CategoryDao{
	
	private static final String SQL__FIND_CATEGORY_BY_ID = "SELECT * FROM car_category WHERE cc_id=?";
	private static final String SQL__INSERT_CATEGORY = "INSERT INTO car_category (cc_name, cc_price) VALUES (?,?)";
	private static final String SQL__UPDATE_CATEGORY = "UPDATE car_category SET cc_name=?, cc_price=? WHERE cc_id = ?";
	private static final String SQL__DELETE_CATEGORY = "DELETE FROM car_category WHERE cc_id = ?";
	private static final String SQL__SELECT_ALL_CATEGORY = "SELECT * FROM car_category";
	private static final String SQL__FIND_CATEGORY_BY_ID_AND_LOCALE = "SELECT cc_id, cct_name AS cc_name, cc_price FROM car_category " +
																	  "INNER JOIN car_category_translation ON cc_id = cct_car_category " +
																	  "INNER JOIN language ON cct_lang = lang_id WHERE cc_id = ? AND lang_name = ?";
	private static final String SQL__SELECT_ALL_CATEGORY_BY_LOCALE = "SELECT cc_id, cct_name AS cc_name, cc_price FROM car_category " +
																	 "INNER JOIN car_category_translation ON cc_id = cct_car_category " +
																	 "INNER JOIN language ON cct_lang = lang_id WHERE lang_name = ?";
	
	private static final Logger LOG = LoggerFactory.getLogger(MySqlCategoryDao.class);

	public MySqlCategoryDao(DBManager dbManager) {
		super(dbManager);
	}

	@Override
	public Category find(Long id) {
		Category category = null;
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__FIND_CATEGORY_BY_ID);
			statement.setLong(1, id);
			set = statement.executeQuery();
			
			while (set.next()) {
				category = mapRow(set);						
			}
			
			commit(connection);
			
		} catch (SQLException e) {
			rollback(connection);
			LOG.error(e.getMessage());
		} finally {
			
			close(set, statement, connection);			
		}
				
		return category;
	}
	
	@Override
	public Category find(Long id, String lang) {
		Category category = null;
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__FIND_CATEGORY_BY_ID_AND_LOCALE);
			statement.setLong(1, id);
			statement.setString(2, lang);
			set = statement.executeQuery();
			
			while (set.next()) {
				category = mapRow(set);						
			}
			
			commit(connection);
			
		} catch (SQLException e) {
			rollback(connection);
			LOG.error(e.getMessage());
		} finally {
			
			close(set, statement, connection);			
		}
				
		return category;
	}

	@Override
	public void insert(Category category) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__INSERT_CATEGORY, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, category.getName());
			statement.setBigDecimal(2, category.getPrice());
			statement.executeUpdate();
			set = statement.getGeneratedKeys();
			
			while (set.next()) {
				category.setId(set.getLong(1));							
			}
			
			commit(connection);
			
		} catch (SQLException e) {
			rollback(connection);
			LOG.error(e.getMessage());
		} finally {
			close(set, statement, connection);	
		}
		
	}

	@Override
	public void update(Category category) {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__UPDATE_CATEGORY);
			statement.setString(1, category.getName());
			statement.setBigDecimal(2, category.getPrice());
			statement.setLong(3, category.getId());
			statement.executeUpdate();
			
			commit(connection);
			
		}catch (SQLException e) {
			rollback(connection);
			LOG.error(e.getMessage());
		} finally {
			close(statement, connection);	
		}	
		
	}

	@Override
	public void delete(Category category) {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__DELETE_CATEGORY);
			statement.setLong(1, category.getId());
			statement.executeUpdate();
			
			commit(connection);
			
		}catch (SQLException e) {
			rollback(connection);
			LOG.error(e.getMessage());
		} finally {
			close(statement, connection);	
		}
		
	}

	@Override
	public List<Category> findAll() {
		List<Category> result = new ArrayList<>();
		Category category = null;
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__SELECT_ALL_CATEGORY);
			set = statement.executeQuery();
			
			while (set.next()) {
				category = mapRow(set);
				result.add(category);
			}
			
			commit(connection);
			
		} catch (SQLException e) {
			rollback(connection);
			LOG.error(e.getMessage());
		} finally {
			close(set, statement, connection);			
		}	
		
		return result;
		
	}
	
	@Override
	public List<Category> findAll(String lang) {
		List<Category> result = new ArrayList<>();
		Category category = null;
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__SELECT_ALL_CATEGORY_BY_LOCALE);
			statement.setString(1, lang);
			set = statement.executeQuery();
			
			while (set.next()) {
				category = mapRow(set);
				result.add(category);
			}
			
			commit(connection);
			
		} catch (SQLException e) {
			rollback(connection);
			LOG.error(e.getMessage());
		} finally {
			close(set, statement, connection);			
		}	
		
		return result;
	}

	@Override
	protected Category mapRow(ResultSet set) {
		Category category = null;
		
		try {
			category = new Category();
			category.setId(set.getLong(Fields.CATEGORY__ID));
			category.setName(set.getString(Fields.CATEGORY__NAME));
			category.setPrice(set.getBigDecimal(Fields.CATEGORY__PRICE));
          
        } catch (SQLException e) {
        	LOG.error(e.getMessage());
        }
		
		return category;
	}

}
