package com.example.taxiservice.web.command.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

public class ProfileUpdatePageCommand extends Command {

	private static final long serialVersionUID = 4207767322581464759L;
	
	private static final Logger LOG = LoggerFactory.getLogger(ProfileUpdatePageCommand.class);
	

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		LOG.debug("Command start");
		HttpSession session = request.getSession(false);
		String phone = session.getAttribute("personPhone") != null ? String.valueOf(session.getAttribute("personPhone")) : "";
		String name = session.getAttribute("personName") != null ?  String.valueOf(session.getAttribute("personName")) : "";
		String surname = session.getAttribute("personSurname") != null ? String.valueOf(session.getAttribute("personSurname")) : "";
		
		request.setAttribute("phone", phone);
		request.setAttribute("name", name);
		request.setAttribute("surname", surname);
		LOG.debug("Command finish");
		
		return new Page(Path.PAGE__PROFILE_UPDATE);
	}
	
	

}
