package com.example.taxiservice.web.command.trip;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class TripOfferCommand extends Command {
	
	private static final long serialVersionUID = -1973599160864921712L;
	private static final Logger LOG = LoggerFactory.getLogger(TripOfferCommand.class);
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
		
		HttpSession session = request.getSession(false);
		TripConfirmDto tripOffer = (TripConfirmDto) session.getAttribute("tripOffer");
		session.removeAttribute("tripOffer");
		long personId = (long) session.getAttribute("personId");

		String offer = request.getParameter("offer");
		
		CarService carService = new CarService();
		LocationService locationService = new LocationService();
		
		if(tripOffer != null) {
			long categoryId = tripOffer.getCategoryId();
			int capacity = tripOffer.getCapacity();
			long originId = tripOffer.getOriginId();
			long destinationId = tripOffer.getDestinationId();
			BigDecimal distance = tripOffer.getDistance();
			
			if("another_category".equals(offer)) {
				Car car = carService.findByCapacity(capacity);
				
				if(car != null) {
					BigDecimal distanceToCar = locationService.findDistance(originId, car.getLocationId());
					int waitTime = distanceToCar.divide(new BigDecimal(0.50)).setScale(0, RoundingMode.HALF_UP).intValueExact();
					
					TripConfirmDto tripConfirm = getTripConfirm(personId, car.getCategoryId(), capacity, originId, destinationId, distance, waitTime, new Car[] {car});
					session.setAttribute("tripConfirm", tripConfirm);
					
					result = new Page(Path.COMMAND__TRIP_CONFIRM_PAGE, true);
				
				}else {
					result = new Page(Path.COMMAND__NEW_TRIP_PAGE + "&error=car", true);
				}
				
			}else if("more_cars".equals(offer)) {
				List<Car> carList = carService.findCars(categoryId, capacity);
				
				if(carList != null && !carList.isEmpty()) {
					List<Integer> waitTimeList = new ArrayList<>();
					
					for(Car car : carList) {
						BigDecimal distanceToCar = locationService.findDistance(originId, car.getLocationId());
						int waitTime = distanceToCar.divide(new BigDecimal(0.50)).setScale(0, RoundingMode.HALF_UP).intValueExact();
						waitTimeList.add(waitTime);
					}
	
					int maxWaitTime = waitTimeList.stream().mapToInt(v -> v).max().orElse(0);
					Car [] cars = carList.toArray(Car[]::new);
					
					TripConfirmDto tripConfirm = getTripConfirm(personId, categoryId, capacity, originId, destinationId, distance, maxWaitTime, cars);
					session.setAttribute("tripConfirm", tripConfirm);
					
					result = new Page(Path.COMMAND__TRIP_CONFIRM_PAGE, true);

				}else {
					result = new Page(Path.COMMAND__NEW_TRIP_PAGE + "&error=car", true);
				}
			}	
		}else {
			result = new Page(Path.COMMAND__NEW_TRIP_PAGE + "&error=trip", true);
		}
		
		return result;
	}
	
	private TripConfirmDto getTripConfirm(long personId, long categoryId, int capacity, long originId, long destId, BigDecimal distance, int waitTime, Car [] cars) {
		TripService tripService = new TripService();
		CategoryService categoryService = new CategoryService();
		Category category = categoryService.find(categoryId);
		
		BigDecimal categoryPrice = category.getPrice().multiply(new BigDecimal(cars.length));
		BigDecimal price = categoryPrice.multiply(distance).setScale(SCALE, RoundingMode.HALF_UP);
		BigDecimal discount = tripService.getDiscount(personId).setScale(SCALE, RoundingMode.HALF_UP);
		BigDecimal total = price.subtract(discount);
		
		TripConfirmDto result = new TripConfirmDto();
		
		result.setCategoryId(categoryId);
		result.setCapacity(capacity);
		result.setOriginId(originId);
		result.setDestinationId(destId);
		result.setDistance(distance);
		result.setPrice(price);
		result.setDiscount(discount);
		result.setTotal(total);
		result.setWaitTime(waitTime);
		result.setCars(cars);
		
		return result;
	}

}
