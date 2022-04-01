package com.example.taxiservice.web.command.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

public class ErrorPageCommand extends Command {
	
	private static final long serialVersionUID = -7664292755277429211L;
	private static final Logger LOG = LoggerFactory.getLogger(ErrorPageCommand.class);

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command start");
		String errorMessage = request.getParameter("error");
		if(errorMessage != null) {
			switch(errorMessage) {
				case "access_denied":
					request.setAttribute("errorMessasge", "error_jsp.anchor.access_denied");
					break;
				default:
					break;
			}
			
		}
		LOG.debug("Command finish");
		return new Page(Path.PAGE__ERROR);
	}
	
	

}
