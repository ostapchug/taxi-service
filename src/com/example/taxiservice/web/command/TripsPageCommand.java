package com.example.taxiservice.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;

public class TripsPageCommand extends Command {

	private static final long serialVersionUID = -4416385983983844193L;
	private static final Logger LOG = LoggerFactory.getLogger(TripsPageCommand.class);

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command start");
		
		LOG.debug("Command finish");
		return new Page(Path.PAGE__TRIPS);
	}

}
