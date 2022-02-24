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
import com.example.taxiservice.service.PersonService;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

public class ProfileUpdateCommand extends Command{

	private static final long serialVersionUID = -7438992088781538965L;
	
	private static final Logger LOG = LoggerFactory.getLogger(ProfileUpdateCommand.class);

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response)	throws IOException, ServletException {
		LOG.debug("Command start");
		Page result = null;
		
		if("GET".contentEquals(request.getMethod())){
			result = new Page(Path.COMMAND__PROFILE_UPDATE, true);
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
		String passwordConfirm = request.getParameter("passwordConfirm");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		
		PersonService personService = new PersonService();
		
		if(!personService.validatePhone(phone)) {
			errors.put("phone", "error.label.anchor.format");
		}else if(!password.isEmpty() && !personService.validatePassword(password)) {
			errors.put("password", "error.label.anchor.format");
		}else if(!password.isEmpty() && !personService.validatePassword(password, passwordConfirm)) {
			errors.put("passwordConfirm", "error.label.anchor.wrong_password_confirm");
		}
		
		if(!name.isEmpty() && !personService.validateText(name)) {
			errors.put("name", "error.label.anchor.format");
		}
		
		if(!surname.isEmpty() && !personService.validateText(surname)) {
			errors.put("surname", "error.label.anchor.format");
		}
		
		HttpSession session = request.getSession(false);
		String personIdString = String.valueOf(session.getAttribute("personId"));
		Long personId = Long.valueOf(personIdString);
		
		Person personById = personService.find(personId);
		Person personByPhone = personService.find(phone);
				
		if(personById == null) {
			errors.put("person","Can't find person with id= " + personId);
		}else if(personByPhone != null && !personByPhone.getPhone().equals(personById.getPhone())) {
			errors.put("phone","error.label.anchor.already_exist_phone");
		}
		
		if(errors.isEmpty()) {
			Person person = new Person();
			person.setId(personId);
			person.setPhone(phone);
			person.setPassword(password);
			person.setName(name);
			person.setSurname(surname);
			person.setRoleId(1);
				
			personService.update(person);
			LOG.debug("Person record updated: " + person);
			
			//update session attributes
			session.setAttribute("personPhone", phone);
			session.setAttribute("personName", name);
			session.setAttribute("personSurname", surname);
			
			result = new Page(Path.COMMAND__PROFILE_PAGE, true);	
		}else {
			String errorMessage = "Cannot update profile ";
			LOG.error("errorMessage: " + errorMessage);
			
			request.setAttribute("errorPhone", errors.get("phone"));
			request.setAttribute("errorPassword", errors.get("password"));
			request.setAttribute("errorPasswordConfirm", errors.get("passwordConfirm"));
			request.setAttribute("errorName", errors.get("name"));
			request.setAttribute("errorSurname", errors.get("surname"));
			request.setAttribute("phone", phone);
			request.setAttribute("name", name);
			request.setAttribute("surname", surname);
			
			result = new Page(Path.PAGE__PROFILE_UPDATE);
		}
		
		return result;
	}

}
