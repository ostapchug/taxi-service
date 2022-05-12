package com.example.taxiservice.dao.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.AbstractDao;
import com.example.taxiservice.dao.CarDao;
import com.example.taxiservice.dao.Fields;
import com.example.taxiservice.factory.annotation.Singleton;
import com.example.taxiservice.model.Car;

/**
 * Data access object for Car entity.
 */
@Singleton
public class CarDaoMySql extends AbstractDao<Car> implements CarDao {

	private static final String SQL__FIND_CAR_BY_ID = "SELECT * FROM car WHERE c_id=?";
	private static final String SQL__FIND_CAR_BY_CATEGORY_AND_CAPACITY = "SELECT * FROM car INNER JOIN car_model ON c_model = cm_id "
			+ "WHERE c_category=? AND c_status=0 AND cm_seat_count >=? ORDER BY cm_seat_count LIMIT 1";
	private static final String SQL__FIND_CAR_BY_CAPACITY = "SELECT * FROM car INNER JOIN car_model ON c_model = cm_id "
			+ "WHERE c_status=0 AND cm_seat_count >=? ORDER BY cm_seat_count LIMIT 1";
	private static final String SQL__FIND_CARS_BY_TRIP_ID = "SELECT * FROM car INNER JOIN m2m_trip_car ON car.c_id = m2m_trip_car.c_id WHERE t_id = ?";
	private static final String SQL__INSERT_CAR = "INSERT INTO car (c_reg_number, c_model, c_category, c_location) VALUES (?,?,?,?)";
	private static final String SQL__UPDATE_CAR = "UPDATE car SET c_reg_number=?, c_model=?, c_category=?, c_location=?, c_status=?  WHERE c_id = ?";
	private static final String SQL__CALL_GET_CARS = "{CALL get_cars(?,?)}";

	private static final Logger LOG = LoggerFactory.getLogger(CarDaoMySql.class);

	public CarDaoMySql() {
		LOG.info("MySqlCarDao initialized");
	}

	@Override
	public Car find(Long id) {
		Car car = null;

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__FIND_CAR_BY_ID);
			statement.setLong(1, id);
			set = statement.executeQuery();

			while (set.next()) {
				car = mapRow(set);
			}

			commit(connection);

		} catch (SQLException e) {
			rollback(connection);
			LOG.error(e.getMessage());
		} finally {

			close(set, statement, connection);
		}

		return car;
	}

	@Override
	public boolean insert(Car car) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__INSERT_CAR, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, car.getRegNumber());
			statement.setLong(2, car.getModelId());
			statement.setLong(3, car.getCategoryId());
			statement.setLong(4, car.getLocationId());
			statement.executeUpdate();
			set = statement.getGeneratedKeys();

			while (set.next()) {
				car.setId(set.getLong(1));
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
	public boolean update(Car car) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__UPDATE_CAR);
			statement.setString(1, car.getRegNumber());
			statement.setLong(2, car.getModelId());
			statement.setLong(3, car.getCategoryId());
			statement.setLong(4, car.getLocationId());
			statement.setInt(5, car.getStatusId());
			statement.setLong(6, car.getId());
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
	public Car find(Long categoryId, Integer capacity) {
		Car car = null;

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__FIND_CAR_BY_CATEGORY_AND_CAPACITY);
			statement.setLong(1, categoryId);
			statement.setInt(2, capacity);
			set = statement.executeQuery();

			while (set.next()) {
				car = mapRow(set);
			}

			commit(connection);

		} catch (SQLException e) {
			rollback(connection);
			LOG.error(e.getMessage());
		} finally {
			close(set, statement, connection);
		}

		return car;
	}

	@Override
	public Car findByCapacity(Integer capacity) {
		Car car = null;

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();
			statement = connection.prepareStatement(SQL__FIND_CAR_BY_CAPACITY);
			statement.setInt(1, capacity);
			set = statement.executeQuery();

			while (set.next()) {
				car = mapRow(set);
			}

			commit(connection);

		} catch (SQLException e) {
			rollback(connection);
			LOG.error(e.getMessage());
		} finally {
			close(set, statement, connection);
		}

		return car;
	}

	@Override
	public List<Car> findCars(Long categoryId, Integer capacity) {
		List<Car> result = new ArrayList<>();
		Car car = null;

		Connection connection = null;
		CallableStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();
			statement = connection.prepareCall(SQL__CALL_GET_CARS);
			statement.setInt(1, capacity);
			statement.setLong(2, categoryId);
			set = statement.executeQuery();

			while (set.next()) {
				car = mapRow(set);
				result.add(car);
			}

			commit(connection);

		} catch (SQLException e) {
			rollback(connection);
			LOG.error(e.getMessage());
		} finally {
			close(statement, connection);
		}

		return result;
	}

	@Override
	public List<Car> findCarsByTripId(Long tripId) {
		List<Car> result = new ArrayList<>();
		Car car = null;

		Connection connection = null;
		CallableStatement statement = null;
		ResultSet set = null;

		try {
			connection = getConnection();
			statement = connection.prepareCall(SQL__FIND_CARS_BY_TRIP_ID);
			statement.setLong(1, tripId);
			set = statement.executeQuery();

			while (set.next()) {
				car = mapRow(set);
				result.add(car);
			}

			commit(connection);

		} catch (SQLException e) {
			rollback(connection);
			LOG.error(e.getMessage());
		} finally {
			close(statement, connection);
		}

		return result;
	}

	@Override
	protected Car mapRow(ResultSet set) {
		Car car = null;

		try {
			car = new Car();

			car.setId(set.getLong(Fields.CAR__ID));
			car.setRegNumber(set.getString(Fields.CAR__REG_NUMBER));
			car.setModelId(set.getLong(Fields.CAR__MODEL));
			car.setCategoryId(set.getLong(Fields.CAR__CATEGORY));
			car.setLocationId(set.getLong(Fields.CAR__LOCATION));
			car.setStatusId(set.getInt(Fields.CAR__STATUS));

		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}

		return car;
	}

}
