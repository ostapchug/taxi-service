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

import com.example.taxiservice.factory.annotation.InjectByType;
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

/**
 * New trip command.
 */
public class NewTripCommand extends Command {
	
	private static final long serialVersionUID = 698775434526628638L;
	private static final Logger LOG = LoggerFactory.getLogger(NewTripCommand.class);
	private static final int SCALE = 2;
	private static final BigDecimal AVG_SPEED = new BigDecimal(0.50); // car average speed in km/min
	
	@InjectByType
	private CarService carService;
	
	@InjectByType
	private CategoryService categoryService;
	
	@InjectByType
	private LocationService locationService;
	
	@InjectByType
	private TripService tripService;
	
	public NewTripCommand() {
		LOG.info("NewTripCommand initialized");
	}

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
	
	/**
	 * Creates new trip from request data and store it in the session. As first page displays a trip confirm page or trip offer page.
	 *
	 * @return Page object which contain path to the view of trip confirm page or trip offer page.
	 */	
	private Page doPost(HttpServletRequest request, HttpServletResponse response) {
		
		Page result = null;
		String errorMessage = null;
		
		long categoryId = -1;
		int capacity = -1;
		long originId = -1;
		long destinationId = -1;
		
		// obtain and validate trip data from the request
		try {			
			categoryId = Long.parseLong(request.getParameter("category"));
			originId = Long.parseLong(request.getParameter("origin"));
			destinationId = Long.parseLong(request.getParameter("destination"));
			capacity = Integer.parseInt(request.getParameter("capacity"));
			errorMessage = capacity > 0 ? null : "capacity"; // capacity must be > 0
		}catch (NumberFormatException e) {
			errorMessage = "input_params";
			LOG.debug(e.getMessage());
		}
		
		HttpSession session = request.getSession(false);
		
		// if validation fail set error request parameter
		if(errorMessage == null) {			
			result = setTripConfirmDto(session, categoryId, capacity, originId, destinationId);
		}else {
			result = new Page(Path.COMMAND__NEW_TRIP_PAGE + "&error=" + errorMessage, true);
		}
		
		return result;
	}
	
	/**
	 * Sets trip data as session attribute.
	 * 
	 * @param session - current session.
	 * 
	 * @param categoryId - category entity identifier.
	 * 
	 * @param capacity - required capacity.
	 * 
	 * @param originId - origin location entity identifier.
	 * 
	 * @return Page - object with path to the view page.
	 */
	private Page setTripConfirmDto (HttpSession session, long categoryId, int capacity, long originId, long destinationId) {
		Page result = null;
		
		// calculate distance
		BigDecimal distance = locationService.findDistance(originId, destinationId).setScale(SCALE, RoundingMode.HALF_UP);
		Category category = categoryService.find(categoryId);		
		Car car = carService.find(categoryId, capacity);
		long personId = (long) session.getAttribute("personId");
		
		// validate distance
		if(distance.compareTo(new BigDecimal(1)) != -1) {
			
			// if car not null create and set trip confirm session attribute
			if (car != null) {	
				
				// calculate price, discount and total
				BigDecimal categoryPrice = category.getPrice();
				BigDecimal price = categoryPrice.multiply(distance).setScale(SCALE, RoundingMode.HALF_UP);
				BigDecimal discount = tripService.getDiscount(personId, price).setScale(SCALE, RoundingMode.HALF_UP);
				BigDecimal total = price.subtract(discount);		
				
				// calculate distance to car and waiting time
				BigDecimal distanceToCar = locationService.findDistance(originId, car.getLocationId());
				int waitTime = distanceToCar.divide(AVG_SPEED).setScale(0, RoundingMode.HALF_UP).intValueExact();
				
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
				
				// if car is null create and set trip offer session attribute
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
