package com.example.taxiservice.web.command.person;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.model.Person;
import com.example.taxiservice.model.Role;
import com.example.taxiservice.service.PersonService;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

public class SignInCommand extends Command {

	private static final long serialVersionUID = -4092142808306722870L;
	
	private static final Logger LOG = LoggerFactory.getLogger(SignInCommand.class);

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response)	throws IOException, ServletException {
		
		LOG.debug("Command start");
		Page result = null;
		
		if("GET".contentEquals(request.getMethod())){
			result = new Page(Path.COMMAND__SIGN_IN_PAGE, true);
		}else if("POST".contentEquals(request.getMethod())) {
			result = doPost(request, response);		
		}
		
		LOG.debug("Command finish");
		return result;
	}
	
	private Page doPost(HttpServletRequest request, HttpServletResponse response) {
		
		Page result = null;
		Map <String, String> errors = new TreeMap<>();
		
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		
		PersonService personService = new PersonService();
		Person person = personService.find(phone);
		LOG.debug("Found person:" + person);
		
		if(person == null) {
			errors.put("phone", "error.label.anchor.wrong_phone");
		}else if(errors.isEmpty() && !password.equals(person.getPassword())){
			errors.put("password", "error.label.anchor.wrong_password");
		}
		
		if(errors.isEmpty()) {
			
			HttpSession session = request.getSession(true);
									
			session.setAttribute("personId", person.getId());
			LOG.debug("Set the session attribute 'personId' = " + person.getId());
			
			session.setAttribute("personPhone", person.getPhone());
			LOG.debug("Set the session attribute 'personPhone' = " + person.getPhone());
			
			session.setAttribute("personName", person.getName());
			LOG.debug("Set the session attribute 'personName' = " + person.getName());
			
			session.setAttribute("personSurname", person.getSurname());
			LOG.debug("Set the session attribute 'personSurname' = " + person.getSurname());
			
			Role personRole = Role.getRole(person);
			session.setAttribute("personRole", personRole.getName());
			LOG.info("Person: " + person + " logged with role: " + personRole.getName());
			
			result = new Page(Path.COMMAND__PROFILE_PAGE, true);
			
		}else {
			
			LOG.error("errorMessage: Can't find account with such phone or password");
			
			request.setAttribute("errorPhone", errors.get("phone"));
			request.setAttribute("errorPassword", errors.get("password"));
			request.setAttribute("phone", phone);	
			
			result = new Page(Path.PAGE__SIGN_IN);
		}
		
		return result;
		
	}

}
