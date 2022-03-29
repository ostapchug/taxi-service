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
		String errorMessage = null;
			
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String passwordConfirm = request.getParameter("passwordConfirm");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		
		PersonService personService = new PersonService();
		
		if(!personService.validatePhone(phone)) {
			errorMessage = "phone_format";
		}else if(!password.isEmpty() && !personService.validatePassword(password)) {
			errorMessage = "password";
		}else if(!password.isEmpty() && !personService.validatePassword(password, passwordConfirm)) {
			errorMessage = "password_confirm";
		}else if(!name.isEmpty() && !personService.validateText(name)) {
			errorMessage = "name";
		}else if(!surname.isEmpty() && !personService.validateText(surname)) {
			errorMessage = "surname";
		}
		
		HttpSession session = request.getSession(false);
		long personId = (long) session.getAttribute("personId");
		
		Person personById = personService.find(personId);
		Person personByPhone = personService.find(phone);
				
		if(personByPhone != null && !personByPhone.getPhone().equals(personById.getPhone())) {
			errorMessage ="phone_exist";
		}
		
		if(errorMessage == null) {
			password = personService.hashPassword(password);
			Person person = new Person();
			person.setId(personId);
			person.setPhone(phone);
			person.setPassword(password);
			person.setName(name);
			person.setSurname(surname);
			person.setRoleId(1);
				
			personService.update(person);
			
			//update session attributes
			session.setAttribute("personPhone", phone);
			session.setAttribute("personName", name);
			session.setAttribute("personSurname", surname);
			
			result = new Page(Path.COMMAND__PROFILE_PAGE, true);	
		}else {			
			result = new Page(Path.COMMAND__PROFILE_UPDATE_PAGE + "&error=" + errorMessage, true);
		}
		
		return result;
	}

}
