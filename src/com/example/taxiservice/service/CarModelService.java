package com.example.taxiservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.CarModelDao;
import com.example.taxiservice.factory.annotation.InjectByType;
import com.example.taxiservice.factory.annotation.Singleton;
import com.example.taxiservice.model.CarModel;

/**
 * Service layer for CarModel DAO object.
 */

@Singleton
public class CarModelService {
    private static final Logger LOG = LoggerFactory.getLogger(CarModelService.class);

    @InjectByType
    private CarModelDao carModelDao;

    public CarModelService() {
        LOG.info("CarModelService initialized");
    }

    public CarModel find(Long id) {
        return carModelDao.find(id);
    }

    public boolean insert(CarModel carModel) {
        return carModelDao.insert(carModel);
    }

    public boolean update(CarModel carModel) {
        return carModelDao.update(carModel);
    }
}
