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
 * Trip offer command.
 */
public class TripOfferCommand extends Command {
	
	private static final long serialVersionUID = -1973599160864921712L;
	private static final Logger LOG = LoggerFactory.getLogger(TripOfferCommand.class);
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
	
	public TripOfferCommand() {
		LOG.info("TripOfferCommand initialized");
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
	 * Creates new trip from session data and store it in the DB. As first page displays a trips page.
	 *
	 * @return Page object which contain path to the view of trips page.
	 */	
	private Page doPost(HttpServletRequest request, HttpServletResponse response) {
		Page result = null;
		
		HttpSession session = request.getSession(false);
		TripConfirmDto tripOffer = (TripConfirmDto) session.getAttribute("tripOffer");
		session.removeAttribute("tripOffer");
		long personId = (long) session.getAttribute("personId");

		// obtain offer variant from the request
		String offer = request.getParameter("offer");
		
		if(tripOffer != null) {
			long categoryId = tripOffer.getCategoryId();
			int capacity = tripOffer.getCapacity();
			long originId = tripOffer.getOriginId();
			long destinationId = tripOffer.getDestinationId();
			BigDecimal distance = tripOffer.getDistance();
			
			// get car from another category
			if("another_category".equals(offer)) {
				Car car = carService.findByCapacity(capacity);
				
				if(car != null) {
					BigDecimal distanceToCar = locationService.findDistance(originId, car.getLocationId());
					int waitTime = distanceToCar.divide(AVG_SPEED).setScale(0, RoundingMode.HALF_UP).intValueExact();
					
					TripConfirmDto tripConfirm = getTripConfirm(personId, car.getCategoryId(), capacity, originId, destinationId, distance, waitTime, new Car[] {car});
					session.setAttribute("tripConfirm", tripConfirm);
					
					result = new Page(Path.COMMAND__TRIP_CONFIRM_PAGE, true);
				
				}else {
					result = new Page(Path.COMMAND__NEW_TRIP_PAGE + "&error=car", true);
				}
			
			// get more cars from the same category	
			}else if("more_cars".equals(offer)) {
				List<Car> carList = carService.findCars(categoryId, capacity);
				
				if(carList != null && !carList.isEmpty()) {
					List<Integer> waitTimeList = new ArrayList<>();
					
					// get list with waiting time for each car
					for(Car car : carList) {
						BigDecimal distanceToCar = locationService.findDistance(originId, car.getLocationId());
						int waitTime = distanceToCar.divide(AVG_SPEED).setScale(0, RoundingMode.HALF_UP).intValueExact();
						waitTimeList.add(waitTime);
					}
					
					// get max waiting time
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
	
	/**
	 * Prepares trip data to be sent to the view.
	 * 
	 * @param personId - person entity identifier.
	 * 
	 * @param categoryId - category entity identifier.
	 * 
	 * @param capacity - required capacity.
	 * 
	 * @param originId - origin location entity identifier.
	 * 
	 * @param destId - destination location entity identifier.
	 * 
	 * @param distance - distance between locations.
	 * 
	 * @param waitTime - waiting time in minutes.
	 * 
	 * @param cars - bounded cars.
	 * 
	 * @return TripConfirmDto - object with required data for trip confirmation.
	 */			
	private TripConfirmDto getTripConfirm(long personId, long categoryId, int capacity, long originId, long destId, BigDecimal distance, int waitTime, Car [] cars) {
		Category category = categoryService.find(categoryId);
		
		BigDecimal categoryPrice = category.getPrice().multiply(new BigDecimal(cars.length));
		BigDecimal price = categoryPrice.multiply(distance).setScale(SCALE, RoundingMode.HALF_UP);
		BigDecimal discount = tripService.getDiscount(personId, price).setScale(SCALE, RoundingMode.HALF_UP);
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
