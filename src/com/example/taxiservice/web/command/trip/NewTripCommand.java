package com.example.taxiservice.web.command.trip;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.DBManager;
import com.example.taxiservice.dao.mysql.MySqlCarDao;
import com.example.taxiservice.dao.mysql.MySqlCategoryDao;
import com.example.taxiservice.dao.mysql.MySqlLocationDao;
import com.example.taxiservice.dao.mysql.MySqlTripDao;
import com.example.taxiservice.model.Car;
import com.example.taxiservice.model.Category;
import com.example.taxiservice.model.dto.TripConfirmDto;
import com.example.taxiservice.service.CarService;
import com.example.taxiservice.service.CategoryService;
import com.example.taxiservice.service.LocationService;
import com.example.taxiservice.service.TripService;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

public class NewTripCommand extends Command {
	
	private static final long serialVersionUID = 698775434526628638L;
	private static final Logger LOG = LoggerFactory.getLogger(NewTripCommand.class);
	private static final int SCALE = 2;

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command start");
		Page result = null;
		
		if("GET".contentEquals(request.getMethod())){
			result = new Page(Path.COMMAND__NEW_TRIP_PAGE, true);
		}else if("POST".contentEquals(request.getMethod())) {
			result = doPost(request, response);		
		}	
		
		LOG.debug("Command finish");
		return result;
	}
	
	private Page doPost(HttpServletRequest request, HttpServletResponse response) {
		
		Page result = null;
		String errorMessage = null;
		
		long categoryId = -1;
		int capacity = -1;
		long originId = -1;
		long destinationId = -1;
		
		try {			
			categoryId = Long.parseLong(request.getParameter("category"));
			originId = Long.parseLong(request.getParameter("origin"));
			destinationId = Long.parseLong(request.getParameter("destination"));
			capacity = Integer.parseInt(request.getParameter("capacity"));
			errorMessage = capacity > 0 ? null : "capacity"; 
		}catch (NumberFormatException e) {
			errorMessage = "input_params";
			LOG.debug(e.getMessage());
		}
		
		HttpSession session = request.getSession(false);
		
		if(errorMessage == null) {			
			result = setTripConfirmDto(session, categoryId, capacity, originId, destinationId);
		}else {
			result = new Page(Path.COMMAND__NEW_TRIP_PAGE + "&error=" + errorMessage, true);
		}
		
		return result;
	}
	
	private Page setTripConfirmDto (HttpSession session, long categoryId, int capacity, long originId, long destinationId) {
		Page result = null;
		
		long personId = (long) session.getAttribute("personId");
		TripService tripService = new TripService(new MySqlTripDao(DBManager.getDataSource()));
		CategoryService categoryService = new CategoryService(new MySqlCategoryDao(DBManager.getDataSource()));
		CarService carService = new CarService(new MySqlCarDao(DBManager.getDataSource()));
		LocationService locationService = new LocationService(new MySqlLocationDao(DBManager.getDataSource()));
		BigDecimal distance = locationService.findDistance(originId, destinationId).setScale(SCALE, RoundingMode.HALF_UP);
		Category category = categoryService.find(categoryId);		
		Car car = carService.find(categoryId, capacity);
		
		if(distance.compareTo(new BigDecimal(1)) != -1) {
			
			if (car != null) {	
				
				BigDecimal categoryPrice = category.getPrice();
				BigDecimal price = categoryPrice.multiply(distance).setScale(SCALE, RoundingMode.HALF_UP);
				BigDecimal discount = tripService.getDiscount(personId, price).setScale(SCALE, RoundingMode.HALF_UP);
				BigDecimal total = price.subtract(discount);		

				BigDecimal distanceToCar = locationService.findDistance(originId, car.getLocationId());
				int waitTime = distanceToCar.divide(new BigDecimal(0.50)).setScale(0, RoundingMode.HALF_UP).intValueExact();
				
				TripConfirmDto tripConfirm = new TripConfirmDto();
				tripConfirm.setCategoryId(categoryId);
				tripConfirm.setCapacity(capacity);
				tripConfirm.setOriginId(originId);
				tripConfirm.setDestinationId(destinationId);
				tripConfirm.setDistance(distance);
				tripConfirm.setPrice(price);
				tripConfirm.setDiscount(discount);
				tripConfirm.setTotal(total);
				tripConfirm.setWaitTime(waitTime);
				tripConfirm.setCars(new Car [] {car});

				session.setAttribute("tripConfirm", tripConfirm);
				result = new Page(Path.COMMAND__TRIP_CONFIRM_PAGE, true);
				
			}else {
			
				TripConfirmDto tripOffer = new TripConfirmDto();			
				tripOffer.setCategoryId(categoryId);
				tripOffer.setCapacity(capacity);
				tripOffer.setOriginId(originId);
				tripOffer.setDestinationId(destinationId);
				tripOffer.setDistance(distance);
				
				session.setAttribute("tripOffer", tripOffer);
				result = new Page(Path.COMMAND__TRIP_OFFER_PAGE, true);
			}
			
		}else {
			result = new Page(Path.COMMAND__NEW_TRIP_PAGE + "&error=distance", true);
		}
		
		return result;
	}
	
}
