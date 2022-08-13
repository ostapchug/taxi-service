package com.example.taxiservice.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.AbstractDao;
import com.example.taxiservice.dao.CarModelDao;
import com.example.taxiservice.dao.Fields;
import com.example.taxiservice.factory.annotation.Singleton;
import com.example.taxiservice.model.CarModel;

/**
 * Data access object for CarModel entity.
 */

@Singleton
public class CarModelDaoMySql extends AbstractDao<CarModel> implements CarModelDao {
    private static final String SQL__FIND_CAR_MODEL_BY_ID = "SELECT * FROM car_model WHERE cm_id=?";
    private static final String SQL__INSERT_CAR_MODEL = "INSERT INTO car_model (cm_brand, cm_name, cm_color, cm_year, cm_seat_count) VALUES (?,?,?,?,?)";
    private static final String SQL__UPDATE_CAR_MODEL = "UPDATE car_model SET cm_brand=?, cm_name=?, cm_color=?, cm_year=?, cm_seat_count=?  WHERE c_id = ?";
    private static final Logger LOG = LoggerFactory.getLogger(CarModelDaoMySql.class);

    public CarModelDaoMySql() {
        LOG.info("MySqlCarModelDao initialized");
    }

    @Override
    public CarModel find(Long id) {
        CarModel carModel = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQL__FIND_CAR_MODEL_BY_ID);
            statement.setLong(1, id);
            set = statement.executeQuery();

            while (set.next()) {
                carModel = mapRow(set);
            }

            commit(connection);
        } catch (SQLException e) {
            rollback(connection);
            LOG.error(e.getMessage());
        } finally {
            close(set, statement, connection);
        }
        return carModel;
    }

    @Override
    public boolean insert(CarModel carModel) {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQL__INSERT_CAR_MODEL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, carModel.getBrand());
            statement.setString(2, carModel.getName());
            statement.setString(3, carModel.getColor());
            statement.setInt(4, carModel.getYear());
            statement.setInt(5, carModel.getSeatCount());
            statement.executeUpdate();
            set = statement.getGeneratedKeys();

            while (set.next()) {
                carModel.setId(set.getLong(1));
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
    public boolean update(CarModel carModel) {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQL__UPDATE_CAR_MODEL);
            statement.setString(1, carModel.getBrand());
            statement.setString(2, carModel.getName());
            statement.setString(3, carModel.getColor());
            statement.setInt(4, carModel.getYear());
            statement.setInt(5, carModel.getSeatCount());
            statement.setLong(6, carModel.getId());
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
    protected CarModel mapRow(ResultSet set) {
        CarModel carModel = null;

        try {
            carModel = new CarModel();
            carModel.setId(set.getLong(Fields.CAR_MODEL__ID));
            carModel.setBrand(set.getString(Fields.CAR_MODEL__BRAND));
            carModel.setName(set.getString(Fields.CAR_MODEL__NAME));
            carModel.setColor(set.getString(Fields.CAR_MODEL__COLOR));
            carModel.setYear(set.getInt(Fields.CAR_MODEL__YEAR));
            carModel.setSeatCount(set.getInt(Fields.CAR_MODEL__SEAT_COUNT));

        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return carModel;
    }
}
