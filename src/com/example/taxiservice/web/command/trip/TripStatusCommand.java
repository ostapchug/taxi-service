package com.example.taxiservice.web.command.trip;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.factory.annotation.InjectByType;
import com.example.taxiservice.model.Role;
import com.example.taxiservice.model.Trip;
import com.example.taxiservice.model.TripStatus;
import com.example.taxiservice.service.TripService;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

/**
 * Trips status command.
 */
public class TripStatusCommand extends Command {

	private static final long serialVersionUID = -6013088980177879712L;
	private static final Logger LOG = LoggerFactory.getLogger(TripStatusCommand.class);
	
	@InjectByType
	private TripService tripService;
	
	public TripStatusCommand() {
		LOG.info("TripStatusCommand initialized");
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
	 * Sets trip status.
	 *
	 * @return Page object which contain path to the view of trips page.
	 */	
	private Page doPost(HttpServletRequest request, HttpServletResponse response) {
		Page result = null;
		String errorMessage = null;
		
		HttpSession session = request.getSession(false);
		
		// obtain session attributes
		String role = (String) session.getAttribute("personRole");
		long personId = (long) session.getAttribute("personId");
		
		// obtain trip id and trip status parameters from the request
		String status = request.getParameter("tripStatus");
		String id = request.getParameter("tripId");
		long tripId;
		Trip trip = null;
		
		// validate trip id
		try {
			tripId = Long.parseLong(id);
			trip = tripService.find(tripId);
		}catch(NumberFormatException e) {
			errorMessage = "trip_status";
		}		
		
		// if person role equals admin set trip status
		if(Role.ADMIN.getName().equals(role)) {
			
			if(status != null && trip != null) {
				boolean updated = tripService.updateStatus(trip, status);
				
				if(!updated) {
					errorMessage = "trip_status";
				}
			}
			
		}else {
			
			// if value of tripStatus parameter is 'cancelled' then proceed
			if(status != null && trip != null && status.equals(TripStatus.CANCELLED.getName())) {
				TripStatus tripStatus = TripStatus.getTripStatus(trip);
				
				// if trip belongs to person and current trip status is 'new' then set trip status
				if(trip.getPersonId().equals(personId) && tripStatus.getName().equals(TripStatus.NEW.getName())) {
					boolean updated = tripService.updateStatus(trip, status);
					
					if(!updated) {
						errorMessage = "trip_status";
					}
				}else {
					errorMessage = "trip_status";
				}
			}
		}
		
		// if validation fail set error request parameter
		if(errorMessage == null) {
			result = new Page(Path.COMMAND__TRIPS_PAGE, true);
		}else {
			result = new Page(Path.COMMAND__TRIPS_PAGE +"&error=" + errorMessage, true);
		}
		
		return result;
	}	

}
