package com.example.taxiservice.dao.mysql;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.AbstractDao;
import com.example.taxiservice.dao.Fields;
import com.example.taxiservice.dao.LocationDao;
import com.example.taxiservice.model.Location;

public class MySqlLocationDao extends AbstractDao<Location> implements LocationDao {
	
	private static final String SQL__FIND_LOCATION_BY_ID = "SELECT * FROM location WHERE l_id=?";
	private static final String SQL__SELECT_ALL_LOCATION = "SELECT * FROM location ORDER BY l_street_name, l_street_number";
	private static final String SQL__CALL_DISTANCE_SPHERE = "{?= CALL ST_Distance_Sphere (?,?)}";
	private static final String SQL__FIND_LOCATION_BY_ID_AND_LOCALE = "SELECT l_id, lt_street_name AS l_street_name, lt_street_number AS l_street_number, l_coordinates " +
			 														  "FROM location INNER JOIN location_translation ON l_id = lt_location INNER JOIN language ON lt_lang = lang_id " +
			 														  "WHERE l_id = ? AND lang_name = ?";
	private static final String SQL__SELECT_ALL_LOCATION_BY_LOCALE = "SELECT l_id, lt_street_name AS l_street_name, lt_street_number AS l_street_number, l_coordinates " +
																	 "FROM location INNER JOIN location_translation ON l_id = lt_location INNER JOIN language ON lt_lang = lang_id " +
																	 "WHERE lang_name = ? ORDER BY l_street_name, l_street_number";
	
	private static final Logger LOG = LoggerFactory.getLogger(MySqlLocationDao.class);
	
	public MySqlLocationDao(DataSource dataSource) {
		super(dataSource);
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
	public boolean insert(Location entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Location entity) {
		// TODO Auto-generated method stub
		return false;
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
			statement.setBlob(2, origin.getCoordinates());
			statement.setBlob(3, destination.getCoordinates());
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
			location.setCoordinates(set.getBlob(Fields.LOCATION__COORDINATES));
			
		}catch (SQLException e) {
        	LOG.error(e.getMessage());
        }
		
		return location;
	}
	
}
