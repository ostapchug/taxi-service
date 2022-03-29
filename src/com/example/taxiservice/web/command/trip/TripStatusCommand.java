package com.example.taxiservice.web.command.trip;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.model.Role;
import com.example.taxiservice.model.Trip;
import com.example.taxiservice.model.TripStatus;
import com.example.taxiservice.service.TripService;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

public class TripStatusCommand extends Command {

	private static final long serialVersionUID = -6013088980177879712L;
	
	private static final Logger LOG = LoggerFactory.getLogger(TripStatusCommand.class);
	
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
	
	private Page doPost(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		String role = (String) session.getAttribute("personRole");
		long personId = (long) session.getAttribute("personId");
		
		String status = request.getParameter("tripStatus");
		String id = request.getParameter("tripId");
		long tripId;
		Trip trip = null;
		TripService tripService = new TripService();
		
		try {
			tripId = Long.parseLong(id);
			trip = tripService.find(tripId);
		}catch(NumberFormatException e) {
			LOG.debug("tipId parameter is not a number");		
		}		
		
		if(Role.ADMIN.getName().equals(role)) {
			
			if(status != null && trip != null) {
				tripService.updateStatus(trip, status);
			}
			
		}else {
			
			if(status != null && trip != null && status.equals(TripStatus.CANCELLED.getName())) {
				if(trip.getPersonId().equals(personId)) {
					tripService.updateStatus(trip, status);
				}
			}
		}
		
		return new Page(Path.COMMAND__TRIPS_PAGE, true);
	}
	

}
