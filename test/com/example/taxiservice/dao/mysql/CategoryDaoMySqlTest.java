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
import com.example.taxiservice.model.Category;

@RunWith(MockitoJUnitRunner.class)
public class CategoryDaoMySqlTest {
	
	@Mock
	private DataSource dataSource;
	@Mock
	private Connection connection;
	@Mock
	private PreparedStatement statement;
	@Mock
	private ResultSet set;
	
	@InjectMocks
	private CategoryDaoMySql categoryDaoMySql;
	
	private Category category;

	@Before
	public void setUp() throws Exception {
		category = new Category();
		category.setId(1L);
		category.setName("Economy");
		category.setPrice(new BigDecimal(25));
		
		when(dataSource.getConnection()).thenReturn(connection);
		
		when(connection.prepareStatement(anyString())).thenReturn(statement);
		when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
		
		when(statement.executeQuery()).thenReturn(set);
		when(statement.getGeneratedKeys()).thenReturn(set);
		
		when(set.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
		when(set.getLong(Fields.CATEGORY__ID)).thenReturn(category.getId());
		when(set.getString(Fields.CATEGORY__NAME)).thenReturn(category.getName());
		when(set.getBigDecimal(Fields.CATEGORY__PRICE)).thenReturn(category.getPrice());
	}

	@Test
	public void testFindById() {
		Category result = categoryDaoMySql.find(1L);
		assertEquals(category, result);
	}
	
	@Test
	public void testInsert() throws SQLException {
		
		categoryDaoMySql.insert(category);

		// verify and assert
		verify(connection, times(1)).prepareStatement(anyString(), anyInt());
		verify(statement, times(1)).setString(anyInt(), anyString());
		verify(statement, times(1)).setBigDecimal(anyInt(), any());
		verify(statement, times(1)).executeUpdate();
		verify(set, times(2)).next();
		verify(set, times(1)).getLong(anyInt());
		verify(connection, times(1)).commit();
		
	}
	
	@Test
	public void testUpdate() throws SQLException {
		
		categoryDaoMySql.update(category);

		// verify and assert
		verify(connection, times(1)).prepareStatement(anyString());
		verify(statement, times(1)).setString(anyInt(), anyString());
		verify(statement, times(1)).setBigDecimal(anyInt(), any());
		verify(statement, times(1)).setLong(anyInt(), anyLong());
		verify(statement, times(1)).executeUpdate();
		verify(connection, times(1)).commit();
		
	}

}
