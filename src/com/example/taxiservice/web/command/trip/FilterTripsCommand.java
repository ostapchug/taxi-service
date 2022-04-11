package com.example.taxiservice.web.command.trip;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.service.PersonService;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

/**
 * Filter trips command.
 */
public class FilterTripsCommand extends Command {
	
	private static final long serialVersionUID = -6207169506311667353L;
	private static final Logger LOG = LoggerFactory.getLogger(FilterTripsCommand.class);

	public FilterTripsCommand() {
		LOG.info("FilterTripsCommand initialized");
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
	 * Sets filter parameter value in the session. As first page displays a trips page.
	 *
	 * @return Page object which contain path to the view of trips page.
	 */			
	private Page doPost(HttpServletRequest request, HttpServletResponse response) {
		Page result = null;
		String errorMessage = null;
		
		HttpSession session = request.getSession(false);
		
		// obtain filter data from the request
		String dateRange = request.getParameter("dateFilter");
		String phone = request.getParameter("phoneFilter");
		
		// validate filter data from the request and set appropriate session attribute
		if(dateRange != null && !dateRange.isEmpty()) {				
				try {
					new SimpleDateFormat("dd.MM.yyyy - dd.MM.yyyy").parse(dateRange);
					session.setAttribute("dateRange", dateRange);
				} catch (ParseException e) {
					errorMessage = "filter_format";
					LOG.debug(e.getMessage());
				}
		}else {
			session.setAttribute("dateRange", null);
		}
		
		if(phone != null && !phone.isEmpty()) {
			
			if(PersonService.validatePhone(phone)) {
				session.setAttribute("phone", phone);
			}else {
				errorMessage = "filter_format";
			}	
		}else {
			session.setAttribute("phone", null);
		}
		
		// if validation fail set error request parameter
		if(errorMessage != null) {
			result = new Page(Path.COMMAND__TRIPS_PAGE + "&error=" + errorMessage, true);
		}else {
			result = new Page(Path.COMMAND__TRIPS_PAGE, true);
		}

		return result;
		
	}

}