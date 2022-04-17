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
import com.example.taxiservice.model.CarModel;

@RunWith(MockitoJUnitRunner.class)
public class CarModelDaoMySqlTest {
	
	@Mock
	private DataSource dataSource;
	@Mock
	private Connection connection;
	@Mock
	private PreparedStatement statement;
	@Mock
	private ResultSet set;
	
	@InjectMocks
	private CarModelDaoMySql carModelDaoMySql;
	
	private CarModel carModel;

	@Before
	public void setUp() throws Exception {
		carModel = new CarModel();
		carModel.setId(1L);
		carModel.setBrand("Ford");
		carModel.setName("Focus");
		carModel.setColor("Blue");
		carModel.setYear(2015);
		carModel.setSeatCount(3);
		
		when(dataSource.getConnection()).thenReturn(connection);
		
		when(connection.prepareStatement(anyString())).thenReturn(statement);
		when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
		
		when(statement.executeQuery()).thenReturn(set);
		when(statement.getGeneratedKeys()).thenReturn(set);
		
		when(set.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
		when(set.getLong(Fields.CAR_MODEL__ID)).thenReturn(carModel.getId());
		when(set.getString(Fields.CAR_MODEL__BRAND)).thenReturn(carModel.getBrand());
		when(set.getString(Fields.CAR_MODEL__NAME)).thenReturn(carModel.getName());
		when(set.getString(Fields.CAR_MODEL__COLOR)).thenReturn(carModel.getColor());
		when(set.getInt(Fields.CAR_MODEL__YEAR)).thenReturn(carModel.getYear());
		when(set.getInt(Fields.CAR_MODEL__SEAT_COUNT)).thenReturn(carModel.getSeatCount());
		
	}

	@Test
	public void testFindById() {
		CarModel result = carModelDaoMySql.find(1L);
		assertEquals(carModel, result);
	}
	
	@Test
	public void testInsert() throws SQLException {
		
		carModelDaoMySql.insert(carModel);
		
		// verify and assert
		verify(connection, times(1)).prepareStatement(anyString(), anyInt());
		verify(statement, times(3)).setString(anyInt(), anyString());
		verify(statement, times(2)).setInt(anyInt(), anyInt());
		verify(statement, times(1)).executeUpdate();
		verify(set, times(2)).next();
		verify(set, times(1)).getLong(anyInt());
		verify(connection, times(1)).commit();
	}
	
	@Test
	public void testUpdate() throws SQLException {
		
		carModelDaoMySql.update(carModel);
		
		// verify and assert
		verify(connection, times(1)).prepareStatement(anyString());
		verify(statement, times(3)).setString(anyInt(), anyString());
		verify(statement, times(2)).setInt(anyInt(), anyInt());
		verify(statement, times(1)).setLong(anyInt(), anyLong());
		verify(statement, times(1)).executeUpdate();
		verify(connection, times(1)).commit();
	}

}