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


public class FilterTripsCommand extends Command {
	
	private static final long serialVersionUID = -6207169506311667353L;
	
	private static final Logger LOG = LoggerFactory.getLogger(FilterTripsCommand.class);

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
		Page result = null;
		boolean error = false;
		
		HttpSession session = request.getSession(false);
		String dateRange = request.getParameter("dateFilter");
		String phone = request.getParameter("phoneFilter");

		if(dateRange != null && !dateRange.isEmpty()) {				
				try {
					new SimpleDateFormat("dd.MM.yyyy - dd.MM.yyyy").parse(dateRange);
					session.setAttribute("dateRange", dateRange);
				} catch (ParseException e) {
					error = true;
					LOG.debug(e.getMessage());
				}
		}else {
			session.setAttribute("dateRange", null);
		}
		
		if(phone != null && !phone.isEmpty()) {
			PersonService personService = new PersonService();
			
			if(personService.validatePhone(phone)) {
				session.setAttribute("phone", phone);
			}else {
				error = true;
			}	
		}else {
			session.setAttribute("phone", null);
		}
		
		if(error) {
			result = new Page(Path.COMMAND__TRIPS_PAGE + "&error=true", true);
		}else {
			result = new Page(Path.COMMAND__TRIPS_PAGE, true);
		}

		return result;
		
	}

}