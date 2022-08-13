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
import com.example.taxiservice.model.Location;

@RunWith(MockitoJUnitRunner.class)
public class LocationDaoMySqlTest {

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement statement;
    @Mock
    private ResultSet set;

    @InjectMocks
    private LocationDaoMySql locationDaoMySql;
    private Location location;

    @Before
    public void setUp() throws Exception {
        location = new Location();
        location.setId(1L);
        location.setStreetName("Molodizhna");
        location.setStreetNumber("36");
        location.setLatitude(new BigDecimal(48.925541084296924));
        location.setLongitude(new BigDecimal(24.737374909778257));

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(set);
        when(statement.getGeneratedKeys()).thenReturn(set);
        when(set.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(set.getLong(Fields.LOCATION__ID)).thenReturn(location.getId());
        when(set.getString(Fields.LOCATION__STREET_NAME)).thenReturn(location.getStreetName());
        when(set.getString(Fields.LOCATION__STREET_NUMBER)).thenReturn(location.getStreetNumber());
        when(set.getBigDecimal(Fields.LOCATION__LATITUDE)).thenReturn(location.getLatitude());
        when(set.getBigDecimal(Fields.LOCATION__LONGITUDE)).thenReturn(location.getLongitude());
    }

    @Test
    public void testFindById() {
        Location result = locationDaoMySql.find(1L);
        assertEquals(location, result);
    }

    @Test
    public void testInsert() throws SQLException {

        locationDaoMySql.insert(location);

        // verify and assert
        verify(connection, times(1)).prepareStatement(anyString(), anyInt());
        verify(statement, times(2)).setString(anyInt(), anyString());
        verify(statement, times(2)).setBigDecimal(anyInt(), any());
        verify(statement, times(1)).executeUpdate();
        verify(set, times(2)).next();
        verify(set, times(1)).getLong(anyInt());
        verify(connection, times(1)).commit();
    }

    @Test
    public void testUpdate() throws SQLException {

        locationDaoMySql.update(location);

        // verify and assert
        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(2)).setString(anyInt(), anyString());
        verify(statement, times(2)).setBigDecimal(anyInt(), any());
        verify(statement, times(1)).setLong(anyInt(), anyLong());
        verify(statement, times(1)).executeUpdate();
        verify(connection, times(1)).commit();
    }
}
