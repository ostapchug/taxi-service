package com.example.taxiservice.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.web.command.Command;
import com.example.taxiservice.web.command.CommandContainer;

/**
 * Main servlet controller
*/
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = LoggerFactory.getLogger(Controller.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.debug("Controller start");
		// extract command name from the request
		String commandName = request.getParameter("command");
		LOG.debug("Request parameter: command --> " + commandName);
		
		// obtain command object by its name
		Command command = CommandContainer.get(commandName);
		LOG.debug("Obtained command --> " + command);
		
		// execute command and get address
		Page page = command.execute(request, response);
		
		// if the address is not null go to the address
		if (page != null) {
			LOG.debug("Address --> " + page.getPath());
			
			if(page.isRedirect()){
				LOG.debug("Controller finished, now go to redirect address --> " + page.getPath());	
				response.sendRedirect(page.getPath());				
				
			}else {			
				LOG.debug("Controller finished, now go to forward address --> " + page.getPath());
				RequestDispatcher dispatcher = request.getRequestDispatcher(page.getPath());
				dispatcher.forward(request, response);
			}
			
		}
	}
}
