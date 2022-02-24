package com.example.taxiservice.web.command;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.service.CategoryService;
import com.example.taxiservice.service.LocationService;
import com.example.taxiservice.web.Page;

public class NewTripCommand extends Command {
	
	private static final long serialVersionUID = 698775434526628638L;
	
	private static final Logger LOG = LoggerFactory.getLogger(NewTripCommand.class);

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command start");
		Page result = null;
		
		String category = request.getParameter("category");
		String capacity = request.getParameter("capacity");
		String originName = request.getParameter("origin_street");
		String originNumber = request.getParameter("origin_street_number");
		String destName = request.getParameter("dest_street");
		String destNumber = request.getParameter("dest_street_number");
		
		LocationService locationService = new LocationService();
		BigDecimal distance = locationService.findDistance(originName, originNumber, destName, destNumber);
		
		CategoryService categoryService = new CategoryService();
		
		BigDecimal price = categoryService.getPrice(category);
		
		LOG.debug(distance.toString() + " " + price.toString());
		
		LOG.debug("Command finish");
		return null;
	}
	
	

}
