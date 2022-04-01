package com.example.taxiservice.dao;

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
	private static DataSource dataSource;
	
	private DBManager() {
	}
	
	static {
		try {
			Context initContext = new InitialContext();
			dataSource = (DataSource) initContext.lookup("java:/comp/env/jdbc/taxi-service-db");
			LOG.info("DBManager init");
		} catch (NamingException e) {
			LOG.error(e.getMessage());
		}
	}
	
	/**
	 * Returns a DB DataSource. Before using this
	 * method you must configure the DataSource and the ConnectionPool in your
	 * WEB_APP_ROOT/META-INF/context.xml file
	 * 
	 * @return A DB DataSource
	 */	
	public static DataSource getDataSource(){	
		return dataSource;
	}
}
