package com.example.taxiservice.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.LocationDao;
import com.example.taxiservice.factory.annotation.InjectByType;
import com.example.taxiservice.factory.annotation.Singleton;
import com.example.taxiservice.model.Location;

/**
 * Service layer for Location DAO object.
 */
@Singleton
public class LocationService {
    private static final Logger LOG = LoggerFactory.getLogger(LocationService.class);

    @InjectByType
    private LocationDao locationDao;

    public LocationService() {
        LOG.info("LocationService initialized");
    }

    public Location find(Long id) {
        return locationDao.find(id);
    }

    public Location find(Long id, String lang) {
        Location result = locationDao.find(id);

        if (lang != null) {
            result = locationDao.find(id, lang);
        }
        return result;
    }

    public List<Location> findAll(String lang) {
        List<Location> result = locationDao.findAll();

        if (lang != null) {
            result = locationDao.findAll(lang);
        }
        return result;
    }

    public BigDecimal findDistance(Long originId, Long destinationId) {
        Location origin = find(originId);
        Location destination = find(destinationId);
        return locationDao.findDistance(origin, destination).divide(new BigDecimal(1000.00));
    }
}
