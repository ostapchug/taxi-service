package com.example.taxiservice.dao.mysql;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.AbstractDao;
import com.example.taxiservice.dao.Fields;
import com.example.taxiservice.dao.LocationDao;
import com.example.taxiservice.factory.annotation.Singleton;
import com.example.taxiservice.model.Location;

/**
 * Data access object for Location entity.
 */
@Singleton
public class LocationDaoMySql extends AbstractDao<Location> implements LocationDao {
	
	private static final String SQL__FIND_LOCATION_BY_ID = "SELECT l_id, l_street_name, l_street_number, ST_X(l_coordinates) AS l_longitude, ST_Y(l_coordinates) AS l_latitude FROM location WHERE l_id = ?";
	private static final String SQL__SELECT_ALL_LOCATION = "SELECT l_id, l_street_name, l_street_number, ST_X(l_coordinates) AS l_longitude, ST_Y(l_coordinates) AS l_latitude FROM location ORDER BY l_street_name, l_street_number";
	private static final String SQL__FIND_LOCATION_BY_ID_AND_LOCALE = "SELECT l_id, lt_street_name AS l_street_name, lt_street_number AS l_street_number, ST_X(l_coordinates) AS l_longitude, ST_Y(l_coordinates) AS l_latitude " +
			 														  "FROM location INNER JOIN location_translation ON l_id = lt_location INNER JOIN language ON lt_lang = lang_id " +
			 														  "WHERE l_id = ? AND lang_name = ?";
	private static final String SQL__SELECT_ALL_LOCATION_BY_LOCALE = "SELECT l_id, lt_street_name AS l_street_name, lt_street_number AS l_street_number, ST_X(l_coordinates) AS l_longitude, ST_Y(l_coordinates) AS l_latitude " +
																	 "FROM location INNER JOIN location_translation ON l_id = lt_location INNER JOIN language ON lt_lang = lang_id " +
																	 "WHERE lang_name = ? ORDER BY l_street_name, l_street_number";
	private static final String SQL__INSERT_LOCATION = "INSERT INTO location (l_street_name, l_street_number, l_coordinates) VALUES (?, ?, POINT(?,?))";
	private static final String SQL__UPDATE_LOCATION = "UPDATE location SET l_street_name = ?, l_street_number = ?, l_coordinates = POINT(?,?) WHERE l_id = ?";
	private static final String SQL__CALL_DISTANCE_SPHERE = "{?= CALL ST_Distance_Sphere (POINT(?,?), POINT(?,?))}";
	
	private static final Logger LOG = LoggerFactory.getLogger(LocationDaoMySql.class);
	
	public LocationDaoMySql() {
		LOG.info("MySqlLocationDao initialized");
	}

	@Override
	public Location find(Long id) {
		Location result = null;
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__FIND_LOCATION_BY_ID);
			statement.setLong(1, id);
			set = statement.executeQuery();
			
			while (set.next()) {
				result = mapRow(set);						
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
	public Location find(Long id, String lang) {
		Location result = null;
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__FIND_LOCATION_BY_ID_AND_LOCALE);
			statement.setLong(1, id);
			statement.setString(2, lang);
			set = statement.executeQuery();
			
			while (set.next()) {
				result = mapRow(set);						
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
	public boolean insert(Location location) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__INSERT_LOCATION, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, location.getStreetName());
			statement.setString(2, location.getStreetNumber());
			statement.setBigDecimal(3, location.getLongitude());
			statement.setBigDecimal(4, location.getLatitude());
			statement.executeUpdate();
			set = statement.getGeneratedKeys();
			
			while (set.next()) {
				location.setId(set.getLong(1));							
			}
			
			commit(connection);
			result = true;
			
		} catch (SQLException e) {
			rollback(connection);
			LOG.error(e.getMessage());
		} finally {
			close(set, statement, connection);	
		}
		
		return result;
	}

	@Override
	public boolean update(Location location) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__UPDATE_LOCATION);
			statement.setString(1, location.getStreetName());
			statement.setString(2, location.getStreetNumber());
			statement.setBigDecimal(3, location.getLongitude());
			statement.setBigDecimal(4, location.getLatitude());
			statement.setLong(5, location.getId());
			statement.executeUpdate();
			
			commit(connection);
			result = true;
			
		}catch (SQLException e) {
			rollback(connection);
			LOG.error(e.getMessage());
		} finally {
			close(statement, connection);	
		}
		
		return result;
	}
	
	@Override
	public List<Location> findAll() {
		List<Location> result = new ArrayList<>();
		Location location = null;
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__SELECT_ALL_LOCATION);
			set = statement.executeQuery();
			
			while (set.next()) {
				location = mapRow(set);
				result.add(location);
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
	public List<Location> findAll(String lang) {
		List<Location> result = new ArrayList<>();
		Location location = null;
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__SELECT_ALL_LOCATION_BY_LOCALE);
			statement.setString(1, lang);
			set = statement.executeQuery();
			
			while (set.next()) {
				location = mapRow(set);
				result.add(location);
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
	public BigDecimal findDistance(Location origin, Location destination) {
		BigDecimal result = null;
		
		Connection connection = null;
		CallableStatement statement = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareCall(SQL__CALL_DISTANCE_SPHERE);
			statement.setBigDecimal(2, origin.getLongitude());
			statement.setBigDecimal(3, origin.getLatitude());
			statement.setBigDecimal(4, destination.getLongitude());
			statement.setBigDecimal(5, destination.getLatitude());
			statement.registerOutParameter(1, Types.DECIMAL);
			statement.execute();
			
			result = statement.getBigDecimal(1);
			
			commit(connection);
			
		} catch (SQLException e) {
			rollback(connection);
			LOG.error(e.getMessage());
		} finally {
			close(statement, connection);			
		}		
		
		return result;
	}

	@Override
	protected Location mapRow(ResultSet set) {
		Location location = null;
		
		try {
			location = new Location();
			
			location.setId(set.getLong(Fields.LOCATION__ID));
			location.setStreetName(set.getString(Fields.LOCATION__STREET_NAME));
			location.setStreetNumber(set.getString(Fields.LOCATION__STREET_NUMBER));
			location.setLatitude(set.getBigDecimal(Fields.LOCATION__LATITUDE));
			location.setLongitude(set.getBigDecimal(Fields.LOCATION__LONGITUDE));
			
		}catch (SQLException e) {
        	LOG.error(e.getMessage());
        }
		
		return location;
	}
	
}
