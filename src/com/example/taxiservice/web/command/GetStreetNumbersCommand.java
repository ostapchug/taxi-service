package com.example.taxiservice.web.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.service.LocationService;
import com.example.taxiservice.web.Page;
public class GetStreetNumbersCommand extends Command {
	
	private static final long serialVersionUID = 7981206025368571753L;
	
	private static final Logger LOG = LoggerFactory.getLogger(GetStreetNumbersCommand.class);

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command start");
		
		response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String streetName = request.getParameter("street_name");
        
        LocationService locationService = new LocationService();
        
        if(streetName != null) {
        	List<String> streetNumbers = locationService.findAllNumbers(streetName);
        	out.print("<option></option>");        	
        	for(String streetNumber : streetNumbers) {
        		out.print("<option>" + streetNumber + "</option>");
        	}
        }
		
		LOG.debug("Command finish");
		return null;
	}

}
