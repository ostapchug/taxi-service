package com.example.taxiservice.dao.mysql;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.taxiservice.dao.Fields;
import com.example.taxiservice.model.Car;

@RunWith(MockitoJUnitRunner.class)
public class CarDaoMySqlTest {
	
	@Mock
	private DataSource dataSource;
	@Mock
	private Connection connection;
	@Mock
	private PreparedStatement statement;
	@Mock
	private ResultSet set;
	
	@InjectMocks
	private CarDaoMySql carDaoMySql;
	
	private Car car;

	@Before
	public void setUp() throws Exception {
		car = new Car();
		car.setId(1L);
		car.setRegNumber("AA1234HH");
		car.setModelId(1L);
		car.setCategoryId(1L);
		car.setLocationId(1L);
		car.setStatusId(1);
		
		when(dataSource.getConnection()).thenReturn(connection);
		
		when(connection.prepareStatement(anyString())).thenReturn(statement);
		when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
		
		when(statement.executeQuery()).thenReturn(set);
		when(statement.getGeneratedKeys()).thenReturn(set);
		
		when(set.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
		when(set.getLong(Fields.CAR__ID)).thenReturn(car.getId());
		when(set.getString(Fields.CAR__REG_NUMBER)).thenReturn(car.getRegNumber());
		when(set.getLong(Fields.CAR__MODEL)).thenReturn(car.getModelId());
		when(set.getLong(Fields.CAR__CATEGORY)).thenReturn(car.getCategoryId());
		when(set.getLong(Fields.CAR__LOCATION)).thenReturn(car.getLocationId());
		when(set.getInt(Fields.CAR__STATUS)).thenReturn(car.getStatusId());
	}

	@Test
	public void testFindById() {
		Car result = carDaoMySql.find(1L);
		assertEquals(car, result);	
	}
	
	@Test
	public void testInsert() throws SQLException {
		
		carDaoMySql.insert(car);
		
		// verify and assert
		verify(connection, times(1)).prepareStatement(anyString(), anyInt());
		verify(statement, times(1)).setString(anyInt(), anyString());
		verify(statement, times(3)).setLong(anyInt(), anyLong());
		verify(statement, times(1)).executeUpdate();
		verify(set, times(2)).next();
		verify(set, times(1)).getLong(anyInt());
		verify(connection, times(1)).commit();
		
	}
	
	@Test
	public void testUpdate() throws SQLException {
		
		carDaoMySql.update(car);
		
		// verify and assert
		verify(connection, times(1)).prepareStatement(anyString());
		verify(statement, times(1)).setString(anyInt(), anyString());
		verify(statement, times(4)).setLong(anyInt(), anyLong());
		verify(statement, times(1)).setInt(anyInt(), anyInt());
		verify(statement, times(1)).executeUpdate();
		verify(connection, times(1)).commit();
		
	}

}
