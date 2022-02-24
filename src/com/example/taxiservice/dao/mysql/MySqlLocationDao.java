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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.AbstractDao;
import com.example.taxiservice.dao.DBManager;
import com.example.taxiservice.dao.Fields;
import com.example.taxiservice.dao.LocationDao;
import com.example.taxiservice.model.Location;

public class MySqlLocationDao extends AbstractDao<Location> implements LocationDao {
	
	private static final String SQL__FIND_LOCATION_BY_ID = "SELECT * FROM location WHERE l_id=?";
	private static final String SQL__FIND_LOCATION_BY_NAME = "SELECT * FROM location WHERE l_street_name=? AND l_street_number=?";
	private static final String SQL__SELECT_NAMES_DISTINCT = "SELECT DISTINCT l_street_name FROM location";
	private static final String SQL__SELECT_NUMBERS_BY_NAME = "SELECT l_street_number FROM location WHERE l_street_name=?";
	private static final String SQL__CALL_DISTANCE_SPHERE = "{?= CALL ST_Distance_Sphere ((SELECT l_coordinates FROM location WHERE l_id=?), (SELECT l_coordinates FROM location WHERE l_id=?))}";
	
	private static final Logger LOG = LoggerFactory.getLogger(MySqlLocationDao.class);
	
	public MySqlLocationDao(DBManager dbManager) {
		super(dbManager);
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
	public Location find(String name, String number) {
		Location result = null;
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__FIND_LOCATION_BY_NAME);
			statement.setString(1, name);
			statement.setString(2, number);
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
	public void insert(Location entity) {
				
	}

	@Override
	public void update(Location entity) {
				
	}

	@Override
	public void delete(Location entity) {
				
	}


	@Override
	public Location mapRow(ResultSet set) {
		Location location = null;
		
		try {
			location = new Location();
			
			location.setId(set.getLong(Fields.LOCATION__ID));
			location.setStreetName(set.getString(Fields.LOCATION__STREET_NAME));
			location.setStreetNumber(set.getString(Fields.LOCATION__STREET_NUMBER));
			
		}catch (SQLException e) {
        	LOG.error(e.getMessage());
        }
		
		return location;
	}

	@Override
	public List<Location> findAll() {
		return null;
	}

	@Override
	public List<String> findNamesDistinct() {
		List<String> result = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__SELECT_NAMES_DISTINCT);
			set = statement.executeQuery();
			
			while (set.next()) {
				result.add(set.getString(1));						
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
	public List<String> findAllNumbers(String streetName) {
		List<String> result = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__SELECT_NUMBERS_BY_NAME);
			statement.setString(1, streetName);
			set = statement.executeQuery();
			
			while (set.next()) {
				result.add(set.getString(1));						
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
	public BigDecimal findDistance(Long originId, Long destId) {
		BigDecimal result = null;
		
		Connection connection = null;
		CallableStatement statement = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareCall(SQL__CALL_DISTANCE_SPHERE);
			statement.setLong(2, originId);
			statement.setLong(3, destId);
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
	
}
