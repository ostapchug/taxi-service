package com.example.taxiservice.dao.mysql;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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
import com.example.taxiservice.model.Trip;

@RunWith(MockitoJUnitRunner.class)
public class TripDaoMySqlTest {

	@Mock
	private DataSource dataSource;
	@Mock
	private Connection connection;
	@Mock
	private PreparedStatement statement;
	@Mock
	private ResultSet set;

	@InjectMocks
	private TripDaoMySql tripDaoMySql;

	private Trip trip;
	private Car car;
	private Car[] cars;

	@Before
	public void setUp() throws Exception {
		trip = new Trip();
		trip.setId(1L);
		trip.setPersonId(1L);
		trip.setOriginId(1L);
		trip.setDestinationId(2L);
		trip.setDistance(new BigDecimal("1.5"));
		trip.setBill(new BigDecimal("37.5"));

		car = new Car();
		car.setId(1L);
		car.setRegNumber("AA1234HH");
		car.setModelId(1L);
		car.setCategoryId(1L);
		car.setLocationId(1L);
		car.setStatusId(1);

		cars = new Car[] { car };

		when(dataSource.getConnection()).thenReturn(connection);

		when(connection.prepareStatement(anyString())).thenReturn(statement);
		when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);

		when(statement.executeQuery()).thenReturn(set);
		when(statement.getGeneratedKeys()).thenReturn(set);

		when(set.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
		when(set.getLong(Fields.TRIP__ID)).thenReturn(trip.getId());
		when(set.getLong(Fields.TRIP__PERSON_ID)).thenReturn(trip.getPersonId());
		when(set.getLong(Fields.TRIP__ORIGIN_ID)).thenReturn(trip.getOriginId());
		when(set.getLong(Fields.TRIP__DESTINATION_ID)).thenReturn(trip.getDestinationId());
		when(set.getBigDecimal(Fields.TRIP__DISTANCE)).thenReturn(trip.getDistance());
		when(set.getBigDecimal(Fields.TRIP__BILL)).thenReturn(trip.getBill());
	}

	@Test
	public void testFindById() {
		Trip result = tripDaoMySql.find(1L);
		assertEquals(trip, result);
	}

	@Test
	public void testInsert() throws SQLException {

		tripDaoMySql.insert(trip);

		// verify and assert
		verify(connection, times(1)).prepareStatement(anyString(), anyInt());
		verify(statement, times(3)).setLong(anyInt(), anyLong());
		verify(statement, times(2)).setBigDecimal(anyInt(), any());
		verify(statement, times(1)).executeUpdate();
		verify(set, times(2)).next();
		verify(set, times(1)).getLong(anyInt());
		verify(connection, times(1)).commit();

	}

	@Test
	public void testInsertWithCars() throws SQLException {

		tripDaoMySql.insert(trip, cars);

		// verify and assert
		verify(connection, times(1)).prepareStatement(anyString());
		verify(connection, times(1)).prepareStatement(anyString(), anyInt());
		verify(statement, times(5)).setLong(anyInt(), anyLong());
		verify(statement, times(2)).setBigDecimal(anyInt(), any());
		verify(statement, times(2)).executeUpdate();
		verify(set, times(2)).next();
		verify(set, times(1)).getLong(anyInt());
		verify(connection, times(1)).commit();

	}

	@Test
	public void testUpdate() throws SQLException {

		tripDaoMySql.update(trip);

		// verify and assert
		verify(connection, times(1)).prepareStatement(anyString());
		verify(statement, times(3)).setLong(anyInt(), anyLong());
		verify(statement, times(2)).setBigDecimal(anyInt(), any());
		verify(statement, times(1)).setInt(anyInt(), anyInt());
		verify(statement, times(1)).executeUpdate();
		verify(connection, times(1)).commit();

	}

	@Test
	public void testUpdateStatus() throws SQLException {

		tripDaoMySql.updateStatus(trip, "accepted");

		// verify and assert
		verify(connection, times(1)).prepareStatement(anyString());
		verify(statement, times(1)).setString(anyInt(), anyString());
		verify(statement, times(1)).setLong(anyInt(), anyLong());
		verify(statement, times(1)).executeUpdate();
		verify(connection, times(1)).commit();

	}

}
