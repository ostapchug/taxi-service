package com.example.taxiservice.web.command.trip;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.factory.annotation.InjectByType;
import com.example.taxiservice.model.Category;
import com.example.taxiservice.model.Location;
import com.example.taxiservice.service.CategoryService;
import com.example.taxiservice.service.LocationService;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

/**
 * New trip page command.
 */
public class NewTripPageCommand extends Command {
	
	private static final long serialVersionUID = 698775434526628638L;
	private static final Logger LOG = LoggerFactory.getLogger(NewTripPageCommand.class);
	
	@InjectByType
	private CategoryService tripService;
	
	@InjectByType
	private LocationService locationService;
	
	public NewTripPageCommand() {
		LOG.info("NewTripPageCommand initialized");
	}

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command start");
		Page result = null;
		
		// error handler
		setErrorMessage(request);
		
		HttpSession session = request.getSession(false);
		
		String lang = (String) session.getAttribute("locale");
		
		// load all categories and locations by specified language
		List<Category> categories = tripService.findAll(lang);
		List<Location> locations = locationService.findAll(lang);	
		
		if(categories != null && locations != null) {
			request.setAttribute("categories", categories);
			request.setAttribute("locations", locations);

			result = new Page(Path.PAGE__NEW_TRIP);
			
		}else {
			result = new Page(Path.COMMAND__ERROR_PAGE, true);
		}
		
		LOG.debug("Command finish");
		return result;
	}
	
	private void setErrorMessage(HttpServletRequest request) {
		
		// obtain error message from the request
		String errorMessage = request.getParameter("error");
		
		// handle error message from the request, if not null set appropriate attribute
		if(errorMessage != null) {
			switch (errorMessage) {
				case "capacity":
					request.setAttribute("errorCapacity", "error.label.anchor.capacity");			
					break;
				case "distance":
					request.setAttribute("errorMessage", "error.label.anchor.distance");			
					break;
				case "input_params":
					request.setAttribute("errorMessage", "error.label.anchor.input_params");			
					break;
				case "car":
					request.setAttribute("errorMessage", "error.label.anchor.car");			
					break;
				case "trip_create":
					request.setAttribute("errorMessage", "error.label.anchor.trip_create");			
					break;
				default:
					break;
			}
		}
		
	}

}
