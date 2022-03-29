package com.example.taxiservice.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.DBManager;
import com.example.taxiservice.dao.Fields;
import com.example.taxiservice.dao.TripDao;
import com.example.taxiservice.dao.mysql.MySqlTripDao;
import com.example.taxiservice.model.Car;
import com.example.taxiservice.model.Trip;

public class TripService {
	private static final Logger LOG = LoggerFactory.getLogger(TripService.class);
	private static final String SORT_DESC = " DESC"; //leading space is very important
	
	private TripDao tripDao;

	public TripService() {
		this.tripDao = new MySqlTripDao(DBManager.getInstance());
	}
	
	public Trip find(Long id) {
		return tripDao.find(id);
	}
	
	public void insert (Trip trip, Car... cars) {
		tripDao.insert(trip, cars);
	}
	
	public List<Trip> findAll(int offset, int count, String sorting){
		String field = getFieldName(sorting);
		
		return tripDao.findAll(offset, count, field);
	}
	
	public List<Trip> findAllByDate(String dateRange, int offset, int count, String sorting){
		
		String field = getFieldName(sorting);			
		Timestamp [] range = getDateRange(dateRange);
		
		return tripDao.findAllByDate(range, offset, count, field);
	}
	
	public List<Trip> findAllByPersonId(Long personId, int offset, int count, String sorting){
		String field = getFieldName(sorting);
		
		return tripDao.findAllByPersonId(personId, offset, count, field);
	}
	
	public List<Trip> findAllByPersonIdAndDate(Long personId, String dateRange, int offset, int count, String sorting){
		
		String field = getFieldName(sorting);			
		Timestamp [] range = getDateRange(dateRange);
		
		return tripDao.findAllByPersonIdAndDate(personId, range, offset, count, field);
	}
	
	public void updateStatus(Trip trip, String status) {
		tripDao.updateStatus(trip, status);
	}
	
	public BigDecimal getDiscount(Long personId) {
		BigDecimal result = null;
		BigDecimal totalBill = tripDao.getTotalBill(personId);
		
		if(totalBill.compareTo(new BigDecimal(100)) >= 0) {
			result = totalBill.multiply(new BigDecimal(0.02));
			
		}else if(totalBill.compareTo(new BigDecimal(500)) >= 0) {
			result = totalBill.multiply(new BigDecimal(0.05));
			
		}else if(totalBill.compareTo(new BigDecimal(1000)) >= 0) {
			result = totalBill.multiply(new BigDecimal(0.10));
		}else {
			result = new BigDecimal(0);
		}
		
		return result;
		
	}
	
	public int getPages(int count) {
		double result = tripDao.getCount();
		result = Math.ceil(result / count);
		
		return (int) result;
	}
	
	public int getPages(String dateRange, int count) {
		Timestamp [] range = getDateRange(dateRange);
		double result = tripDao.getCountByDate(range);
		result = Math.ceil(result / count);
		
		return (int) result;
	}
	
	public int getPages(Long personId, int count) {
		double result = tripDao.getCountByPersonId(personId);
		result = Math.ceil(result / count);
		
		return (int) result;
	}
	
	public int getPages(Long personId, String dateRange, int count) {
		Timestamp [] range = getDateRange(dateRange);
		double result = tripDao.getCountByPersonIdAndDate(personId, range);
		result = Math.ceil(result / count);
		
		return (int) result;
	}
	
	private Timestamp [] getDateRange (String dateRange) {
		Timestamp [] result = new Timestamp [2];
		Date start = null;
		Date end = null;
		String [] range = dateRange.split(" - ");
		try {
			start = new SimpleDateFormat("dd.MM.yyyy").parse(range[0]);
			end = new SimpleDateFormat("dd.MM.yyyy").parse(range[1]);
		} catch (ParseException e) {
			LOG.debug(e.getMessage());
		}
		
		result[0] = new Timestamp(start.getTime());
		result[1] = new Timestamp(end.getTime());
		
		return result;
	}

	private String getFieldName(String sorting) {
		String field = null;
		
		switch(sorting) {
			case "date_asc":
				field = Fields.TRIP__DATE;
				break;
			case "date_desc":
				field = Fields.TRIP__DATE + SORT_DESC;
				break;
			case "bill_asc":
				field = Fields.TRIP__BILL;
				break;
			case "bill_desc":
				field = Fields.TRIP__BILL + SORT_DESC;
				break;
			default:
				field = Fields.TRIP__DATE + SORT_DESC;
				break;
		}
		
		return field;	
	}

}
