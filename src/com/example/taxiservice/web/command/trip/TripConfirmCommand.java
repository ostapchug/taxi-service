package com.example.taxiservice.web.command.trip;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.factory.annotation.InjectByType;
import com.example.taxiservice.model.Trip;
import com.example.taxiservice.model.dto.TripConfirmDto;
import com.example.taxiservice.service.TripService;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

/**
 * Trip confirm command.
 */
public class TripConfirmCommand extends Command {
	
	private static final long serialVersionUID = -6632486558571801735L;
	private static final Logger LOG = LoggerFactory.getLogger(TripConfirmCommand.class);
	
	@InjectByType
	private TripService tripService;
	
	public TripConfirmCommand() {
		LOG.info("TripConfirmCommand initialized");
	}

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command start");
		Page result = null;
		
		if("GET".contentEquals(request.getMethod())){
			result = new Page(Path.COMMAND__TRIPS_PAGE, true);
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
		TripConfirmDto tripConfirm = (TripConfirmDto) session.getAttribute("tripConfirm");
		long personId = (long) session.getAttribute("personId");
		session.removeAttribute("tripConfirm");
		
		// obtain confirmation from the request
		String confirm  = request.getParameter("confirm");
			
		if(tripConfirm != null && "confirmed".equals(confirm)) {
			Trip trip = new Trip();
			trip.setPersonId(personId);
			trip.setOriginId(tripConfirm.getOriginId());
			trip.setDestinationId(tripConfirm.getDestinationId());
			trip.setDistance(tripConfirm.getDistance());
			trip.setBill(tripConfirm.getTotal());
			
			boolean inserted = tripService.insert(trip, tripConfirm.getCars());
			
			if(inserted) {
				result = new Page(Path.COMMAND__TRIPS_PAGE, true);
			}else {
				result = new Page(Path.COMMAND__NEW_TRIP_PAGE + "&error=trip_create", true);
			}
			
		}else {
			result = new Page(Path.COMMAND__NEW_TRIP_PAGE + "&error=trip_create", true);
		}		
		
		return result;
	}

}
