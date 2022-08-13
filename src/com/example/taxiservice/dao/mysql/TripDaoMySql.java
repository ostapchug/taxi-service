package com.example.taxiservice.dao.mysql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.AbstractDao;
import com.example.taxiservice.dao.Fields;
import com.example.taxiservice.dao.TripDao;
import com.example.taxiservice.factory.annotation.Singleton;
import com.example.taxiservice.model.Car;
import com.example.taxiservice.model.Trip;

/**
 * Data access object for Trip entity.
 */
@Singleton
public class TripDaoMySql extends AbstractDao<Trip> implements TripDao {
    private static final String SQL__FIND_TRIP_BY_ID = "SELECT * FROM trip WHERE t_id=?";
    private static final String SQL__INSERT_TRIP = "INSERT INTO trip (t_person, t_origin, t_destination, t_distance, t_bill) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL__INSERT_TRIP_CAR = "INSERT INTO m2m_trip_car (t_id, c_id) VALUES (?, ?)";
    private static final String SQL__UPDATE_TRIP = "UPDATE trip SET t_person = ?, t_origin = ?, t_destination = ?, t_distance = ?, t_bill = ? WHERE l_id = ?";
    private static final String SQL__SELECT_ALL_TRIPS = "SELECT * FROM trip ORDER BY %s LIMIT ?, ?";
    private static final String SQL__SELECT_ALL_TRIPS_BY_DATE = "SELECT * FROM trip WHERE t_date BETWEEN ? AND ? ORDER BY %s LIMIT ?, ?";
    private static final String SQL__SELECT_COUNT_TRIPS = "SELECT COUNT(*) FROM trip";
    private static final String SQL__SELECT_COUNT_TRIPS_BY_DATE = "SELECT COUNT(*) FROM trip WHERE t_date BETWEEN ? AND ?";
    private static final String SQL__SELECT_ALL_TRIPS_BY_PERSON = "SELECT * FROM trip WHERE t_person=? ORDER BY %s LIMIT ?, ?";
    private static final String SQL__SELECT_COUNT_TRIPS_BY_PERSON = "SELECT COUNT(*) FROM trip WHERE t_person=?";
    private static final String SQL__SELECT_ALL_TRIPS_BY_PERSON_AND_DATE = "SELECT * FROM trip WHERE t_person=? AND t_date BETWEEN ? AND ? ORDER BY %s LIMIT ?, ?";
    private static final String SQL__SELECT_COUNT_TRIPS_BY_PERSON_AND_DATE = "SELECT COUNT(*) FROM trip WHERE t_person=? AND t_date BETWEEN ? AND ?";
    private static final String SQL__UPDATE_TRIP_STATUS = "UPDATE trip SET t_status=(SELECT ts_id FROM trip_status WHERE ts_name=?) WHERE t_id = ?";
    private static final String SQL__SELECT_TRIP_TOTAL_BILL = "SELECT SUM(t_bill) FROM trip WHERE t_person = ? AND t_status = 2";
    private static final Logger LOG = LoggerFactory.getLogger(TripDaoMySql.class);

    public TripDaoMySql() {
        LOG.info("MySqlTripDao initialized");
    }

    @Override
    public Trip find(Long id) {
        Trip trip = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQL__FIND_TRIP_BY_ID);
            statement.setLong(1, id);
            set = statement.executeQuery();

            while (set.next()) {
                trip = mapRow(set);
            }

            commit(connection);
        } catch (SQLException e) {
            rollback(connection);
            LOG.error(e.getMessage());
        } finally {
            close(set, statement, connection);
        }
        return trip;
    }

    @Override
    public boolean insert(Trip trip) {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQL__INSERT_TRIP, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, trip.getPersonId());
            statement.setLong(2, trip.getOriginId());
            statement.setLong(3, trip.getDestinationId());
            statement.setBigDecimal(4, trip.getDistance());
            statement.setBigDecimal(5, trip.getBill());
            statement.executeUpdate();
            set = statement.getGeneratedKeys();

            while (set.next()) {
                trip.setId(set.getLong(1));
            }

            commit(connection);
            result = true;
        } catch (SQLException e) {
            rollback(connection);
            LOG.error(e.getMessage());
        } finally {
            close(set, statement, connection);
        }
        return result;
    }

    @Override
    public boolean insert(Trip trip, Car... cars) {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        ResultSet set = null;

        try {
            connection = getConnection();
            statement1 = connection.prepareStatement(SQL__INSERT_TRIP, Statement.RETURN_GENERATED_KEYS);
            statement1.setLong(1, trip.getPersonId());
            statement1.setLong(2, trip.getOriginId());
            statement1.setLong(3, trip.getDestinationId());
            statement1.setBigDecimal(4, trip.getDistance());
            statement1.setBigDecimal(5, trip.getBill());
            statement1.executeUpdate();
            set = statement1.getGeneratedKeys();

            while (set.next()) {
                trip.setId(set.getLong(1));
            }

            statement2 = connection.prepareStatement(SQL__INSERT_TRIP_CAR);
            for (Car car : cars) {
                statement2.setLong(1, trip.getId());
                statement2.setLong(2, car.getId());
                statement2.executeUpdate();
            }

            commit(connection);
            result = true;
        } catch (SQLException e) {
            rollback(connection);
            LOG.error(e.getMessage());
        } finally {
            close(statement2);
            close(set, statement1, connection);
        }
        return result;
    }

    @Override
    public boolean update(Trip trip) {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQL__UPDATE_TRIP);
            statement.setLong(1, trip.getPersonId());
            statement.setLong(2, trip.getOriginId());
            statement.setLong(4, trip.getDestinationId());
            statement.setBigDecimal(5, trip.getDistance());
            statement.setBigDecimal(6, trip.getBill());
            statement.setInt(7, trip.getStatusId());
            statement.executeUpdate();
            commit(connection);
            result = true;

        } catch (SQLException e) {
            rollback(connection);
            LOG.error(e.getMessage());
        } finally {
            close(statement, connection);
        }
        return result;
    }

    @Override
    public List<Trip> findAll(int offset, int count, String sorting) {
        List<Trip> result = new ArrayList<Trip>();
        Trip trip = null;
        String sql = String.format(SQL__SELECT_ALL_TRIPS, sorting);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, offset);
            statement.setInt(2, count);
            set = statement.executeQuery();

            while (set.next()) {
                trip = mapRow(set);
                result.add(trip);
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
    public List<Trip> findAllByDate(Timestamp[] dateRange, int offset, int count, String sorting) {
        List<Trip> result = new ArrayList<Trip>();
        Trip trip = null;
        String sql = String.format(SQL__SELECT_ALL_TRIPS_BY_DATE, sorting);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, dateRange[0]);
            statement.setTimestamp(2, dateRange[1]);
            statement.setInt(3, offset);
            statement.setInt(4, count);
            set = statement.executeQuery();

            while (set.next()) {
                trip = mapRow(set);
                result.add(trip);
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
    public List<Trip> findAllByPersonId(Long personId, int offset, int count, String sorting) {
        List<Trip> result = new ArrayList<Trip>();
        Trip trip = null;
        String sql = String.format(SQL__SELECT_ALL_TRIPS_BY_PERSON, sorting);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, personId);
            statement.setInt(2, offset);
            statement.setInt(3, count);
            set = statement.executeQuery();

            while (set.next()) {
                trip = mapRow(set);
                result.add(trip);
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
    public List<Trip> findAllByPersonIdAndDate(Long personId, Timestamp[] dateRange, int offset, int count,
            String sorting) {
        List<Trip> result = new ArrayList<Trip>();
        Trip trip = null;
        String sql = String.format(SQL__SELECT_ALL_TRIPS_BY_PERSON_AND_DATE, sorting);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, personId);
            statement.setTimestamp(2, dateRange[0]);
            statement.setTimestamp(3, dateRange[1]);
            statement.setInt(4, offset);
            statement.setInt(5, count);
            set = statement.executeQuery();

            while (set.next()) {
                trip = mapRow(set);
                result.add(trip);
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
    public Integer getCount() {
        Integer result = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQL__SELECT_COUNT_TRIPS);
            set = statement.executeQuery();

            while (set.next()) {
                result = set.getInt(1);
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
    public Integer getCountByDate(Timestamp[] dateRange) {
        Integer result = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQL__SELECT_COUNT_TRIPS_BY_DATE);
            statement.setTimestamp(1, dateRange[0]);
            statement.setTimestamp(2, dateRange[1]);
            set = statement.executeQuery();

            while (set.next()) {
                result = set.getInt(1);
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
    public Integer getCountByPersonId(Long personId) {
        Integer result = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQL__SELECT_COUNT_TRIPS_BY_PERSON);
            statement.setLong(1, personId);
            set = statement.executeQuery();

            while (set.next()) {
                result = set.getInt(1);
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
    public Integer getCountByPersonIdAndDate(Long personId, Timestamp[] dateRange) {
        Integer result = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQL__SELECT_COUNT_TRIPS_BY_PERSON_AND_DATE);
            statement.setLong(1, personId);
            statement.setTimestamp(2, dateRange[0]);
            statement.setTimestamp(3, dateRange[1]);
            set = statement.executeQuery();

            while (set.next()) {
                result = set.getInt(1);
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
    public boolean updateStatus(Trip trip, String status) {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQL__UPDATE_TRIP_STATUS);
            statement.setString(1, status);
            statement.setLong(2, trip.getId());
            statement.executeUpdate();
            commit(connection);
            result = true;
        } catch (SQLException e) {
            rollback(connection);
            LOG.error(e.getMessage());
        } finally {
            close(statement, connection);
        }
        return result;
    }

    @Override
    public BigDecimal getTotalBill(Long personId) {
        BigDecimal result = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQL__SELECT_TRIP_TOTAL_BILL);
            statement.setLong(1, personId);
            set = statement.executeQuery();

            while (set.next()) {
                result = set.getBigDecimal(1);
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
    protected Trip mapRow(ResultSet set) {
        Trip trip = null;

        try {
            trip = new Trip();
            trip.setId(set.getLong(Fields.TRIP__ID));
            trip.setPersonId(set.getLong(Fields.TRIP__PERSON_ID));
            trip.setOriginId(set.getLong(Fields.TRIP__ORIGIN_ID));
            trip.setDestinationId(set.getLong(Fields.TRIP__DESTINATION_ID));
            trip.setDistance(set.getBigDecimal(Fields.TRIP__DISTANCE));
            trip.setDate(set.getTimestamp(Fields.TRIP__DATE));
            trip.setBill(set.getBigDecimal(Fields.TRIP__BILL));
            trip.setStatusId(set.getInt(Fields.TRIP__STATUS_ID));

        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return trip;
    }
}
