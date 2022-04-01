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

public class HomePageCommand extends Command{

	private static final long serialVersionUID = -9130781253259901563L;
	private static final Logger LOG = LoggerFactory.getLogger(HomePageCommand.class);

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command start");
		// do nothing
		LOG.debug("Command finish");
		return new Page(Path.PAGE__HOME_PAGE);
	}
	

}
