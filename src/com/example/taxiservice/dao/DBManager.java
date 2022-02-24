package com.example.taxiservice.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DB manager. Works with MySQL DB
 */
public class DBManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(DBManager.class);
	
	private DataSource dataSource = null;
	
	// singleton	
	private static DBManager instance;
	
	public static synchronized DBManager getInstance() {
		if (instance == null)
			instance = new DBManager();
		return instance;
	}
	
	private DBManager() {		
		try {
			Context initContext = new InitialContext();
			dataSource = (DataSource) initContext.lookup("java:/comp/env/jdbc/taxi-service-db");
			LOG.info("DBManager init");
		} catch (NamingException e) {
			LOG.error(e.getMessage());
		}
	}
	
	/**
	 * Returns a DB connection from the Connections Pool. Before using this
	 * method you must configure the DataSource and the ConnectionPool in your
	 * WEB_APP_ROOT/META-INF/context.xml file
	 * 
	 * @return A DB connection
	 */	
	public Connection getConnection() throws SQLException {	
		return dataSource.getConnection();
	}
	
	/**
	 * Returns a DB connection. This method use the DriverManager to obtain a DB connection
	 * It does not use a ConnectionPool and used only for testing
	 * 
	 * @return A DB connection
	 */
	public Connection getConnectionWithDriverManager() throws SQLException {	
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi-service?user=root&password=test");
		connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		connection.setAutoCommit(false);

		return connection;
	}
}
