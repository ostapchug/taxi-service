package com.example.taxiservice.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parent for all DAO objects
 * Declares and implements methods for working with Statements, ResultSets and
 * Connections
 * 
 * @param <T> - entity type
 */
public abstract class AbstractDao<T> implements EntityDao<T> {
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractDao.class);
	
	protected final DBManager dbManager;
	
	public AbstractDao(DBManager dbManager) {
		this.dbManager = dbManager;
	}

	/**
	 * @return DB connection from the pool
	 * @throws SQLException
	 */
	protected Connection getConnection() throws SQLException {
		return dbManager.getConnection();
	}
	
	/**
	 * Commits the given connection
	 * 
	 * @param connection Connection to be committed
	 */
	public void commit(Connection connection) {
		if(connection != null) {
			try {
				connection.commit();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}		
		}
	}
	
	/**
	 * Rollbacks the  given connection
	 * 
	 * @param connection Connection to be rollbacked
	 */
	public void rollback(Connection connection) {
		if(connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}	
		}
	}
	
	/**
	 * Closes the given connection
	 * 
	 */
	public void close(Connection connection) {
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		}
	}
	
	/**
	 * Closes the  given statement
	 * 
	 */
	public void close(Statement statement) {
		if(statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		}
	}
	
	/**
	 * Closes the  given result set
	 * 
	 */	
	public void close(ResultSet set) {
		if(set != null) {
			try {
				set.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		}
	}
	
	/**
	 * Closes the  given statement and connection
	 * 
	 */		
	public void close(Statement statement, Connection connection) {
		close(statement);
		close(connection);
	}
	
	/**
	 * Closes the  given statement, result set and connection
	 * 
	 */	
	public void close(ResultSet set, Statement statement, Connection connection) {
		close(set);
		close(statement);
		close(connection);
	}

}
