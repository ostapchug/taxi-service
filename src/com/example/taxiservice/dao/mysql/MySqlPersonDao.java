package com.example.taxiservice.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.AbstractDao;
import com.example.taxiservice.dao.DBManager;
import com.example.taxiservice.dao.Fields;
import com.example.taxiservice.dao.PersonDao;
import com.example.taxiservice.model.Person;

public class MySqlPersonDao extends AbstractDao<Person> implements PersonDao {
	
	private static final String SQL__FIND_PERSON_BY_ID = "SELECT * FROM person WHERE p_id=?";
	private static final String SQL__FIND_PERSON_BY_PHONE = "SELECT * FROM person WHERE p_phone=?";
	private static final String SQL__INSERT_PERSON = "INSERT INTO person (p_phone, p_password, p_name, p_surname) VALUES (?,?,?,?)";
	private static final String SQL__UPDATE_PERSON = "UPDATE person SET p_phone=?, p_password=?, p_name=?, p_surname=? WHERE p_id = ?";
	private static final String SQL__DELETE_PERSON = "DELETE FROM person WHERE p_id = ?";
	
	private static final Logger LOG = LoggerFactory.getLogger(MySqlPersonDao.class);



	public MySqlPersonDao(DBManager dbManager) {
		super(dbManager);
	}

	@Override
	public Person find(Long id) {
		Person person = null;
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__FIND_PERSON_BY_ID);
			statement.setLong(1, id);
			set = statement.executeQuery();
			
			while (set.next()) {
				person = mapRow(set);						
			}
			
			commit(connection);
			
		} catch (SQLException e) {
			rollback(connection);
			LOG.error(e.getMessage());
		} finally {
			
			close(set, statement, connection);			
		}
		
		return person;
	}
	
	@Override
	public Person find(String phone) {
		Person person = null;
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__FIND_PERSON_BY_PHONE);
			statement.setString(1, phone);
			set = statement.executeQuery();
			
			while (set.next()) {
				person = mapRow(set);						
			}
			
			commit(connection);
			
		} catch (SQLException e) {
			rollback(connection);
			LOG.error(e.getMessage());
		} finally {
			close(set, statement, connection);			
		}
		
		return person;
	}

	@Override
	public void insert(Person person) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__INSERT_PERSON, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, person.getPhone());
			statement.setString(2, person.getPassword());
			statement.setString(3, person.getName());
			statement.setString(4, person.getSurname());
			statement.executeUpdate();
			set = statement.getGeneratedKeys();
			
			while (set.next()) {
				person.setId(set.getLong(1));							
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
	public void update(Person person) {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__UPDATE_PERSON);
			statement.setString(1, person.getPhone());
			statement.setString(2, person.getPassword());
			statement.setString(3, person.getName());
			statement.setString(4, person.getSurname());
			statement.setLong(5, person.getId());
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
	public void delete(Person person) {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__DELETE_PERSON);
			statement.setLong(1, person.getId());
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
	protected Person mapRow(ResultSet set) {
		Person person = null;
		
		try {
			person = new Person();
            person.setId(set.getLong(Fields.PERSON__ID));
            person.setPhone(set.getString(Fields.PERSON__PHONE));
            person.setPassword(set.getString(Fields.PERSON__PASSWORD));
            person.setName(set.getString(Fields.PERSON__NAME));
            person.setSurname(set.getString(Fields.PERSON__SURNAME));
            person.setRoleId(set.getInt(Fields.PERSON__ROLE_ID));
            
        } catch (SQLException e) {
        	LOG.error(e.getMessage());
        }
		
		return person;
	}	
}
