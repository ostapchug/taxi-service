package com.example.taxiservice.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.model.Category;
import com.example.taxiservice.service.CategoryService;
import com.example.taxiservice.service.LocationService;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;

public class NewTripPageCommand extends Command {
	
	private static final long serialVersionUID = 698775434526628638L;
	
	private static final Logger LOG = LoggerFactory.getLogger(NewTripPageCommand.class);

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command start");
		Page result = null;
		
		CategoryService tripService = new CategoryService();
		LocationService locationService = new LocationService();
		
		
		List<Category> categories = tripService.findAll();
		List<String> streetNames = locationService.findNamesDistinct();
		
		if(categories != null && streetNames != null) {
			request.setAttribute("categories", categories);
			request.setAttribute("streetNames", streetNames);

			result = new Page(Path.PAGE__TRIP);
			
		}else {
			result = new Page(Path.PAGE__ERROR);
		}
		
		LOG.debug("Command finish");
		return result;
	}

}
