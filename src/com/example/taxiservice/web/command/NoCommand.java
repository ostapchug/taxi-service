package com.example.taxiservice.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;

public class NoCommand extends Command {
	
	private static final long serialVersionUID = 200567575397116250L;
	private static final Logger LOG = LoggerFactory.getLogger(NoCommand.class);

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command start");
		String errorMessage = "error_jsp.anchor.no_command";
		request.setAttribute("errorMessage", errorMessage);
		LOG.error("Set the request attribute: errorMessage --> No such command");
		LOG.debug("Command finish");
		return new Page(Path.PAGE__ERROR);
	}

}
