package com.example.taxiservice.web.command.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.model.Person;
import com.example.taxiservice.service.PersonService;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

public class ProfileDeleteCommand extends Command {
	
	private static final long serialVersionUID = -4615110536467451184L;
	
	private static final Logger LOG = LoggerFactory.getLogger(ProfileDeleteCommand.class);

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command start");
		Page result = null;
		
		HttpSession session = request.getSession(false);
		
		String idString = String.valueOf(session.getAttribute("personId"));
		Long id = Long.valueOf(idString);
		
		PersonService personService = new PersonService();
		Person person = personService.find(id);
		
		if(person != null) {
			personService.delete(person);
			result = new Page(Path.COMMAND__SIGN_OUT, true);
		}else {
			LOG.error("errorMessage: Can't delete account");
			result = new Page(Path.PAGE__ERROR);
		}
				
		LOG.debug("Command finish");		
		return result;
	}

}
