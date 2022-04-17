package com.example.taxiservice.web.command.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.factory.annotation.InjectByType;
import com.example.taxiservice.model.Person;
import com.example.taxiservice.model.Role;
import com.example.taxiservice.service.PersonService;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

/**
 * Sign in command.
 */
public class SignInCommand extends Command {

	private static final long serialVersionUID = -4092142808306722870L;
	private static final Logger LOG = LoggerFactory.getLogger(SignInCommand.class);
	
	@InjectByType
	private PersonService personService;
	
	public SignInCommand() {
		LOG.info("SignInCommand initialized");
	}

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
	
	/**
	 * Logins user in system. As first page displays a home page.
	 *
	 * @return Page object which contain path to the view of home page.
	 */
	private Page doPost(HttpServletRequest request, HttpServletResponse response) {
		
		Page result = null;
		String errorMessage = null;
		
		// obtain login and password from the request
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		
		Person person = personService.find(phone);
		password = PersonService.hashPassword(password);
		
		// validate login and password from the request
		if(person == null) {
			errorMessage = "phone";
		}else if(errorMessage == null && password != null && !password.equals(person.getPassword())){
			errorMessage = "password";
		}
		
		if(errorMessage == null) {
			
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
			LOG.info("Person: " + person.getPhone() + " logged with role: " + personRole.getName());
			
			result = new Page(Path.COMMAND__HOME_PAGE, true);
			
		}else {
			result = new Page(Path.COMMAND__SIGN_IN_PAGE + "&error=" + errorMessage, true);
		}
		
		return result;
	}

}
