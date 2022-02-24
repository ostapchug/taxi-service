package com.example.taxiservice.web.command.person;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.model.Person;
import com.example.taxiservice.service.PersonService;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

public class SignUpCommand extends Command {

	private static final long serialVersionUID = -3445237114266973954L;
	
	private static final Logger LOG = LoggerFactory.getLogger(SignUpCommand.class);

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command start");
		Page result = null;
		
		if("GET".contentEquals(request.getMethod())){
			result = new Page(Path.COMMAND__SIGN_UP_PAGE, true);
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
		}else if(!personService.validatePassword(password)) {
			errors.put("password", "error.label.anchor.format");
		}else if(!personService.validatePassword(password, passwordConfirm)) {
			errors.put("passwordConfirm", "error.label.anchor.wrong_password_confirm");
		}
		
		if(!name.isEmpty() && !personService.validateText(name)) {
			errors.put("name", "error.label.anchor.format");
		}
		
		if(!surname.isEmpty() && !personService.validateText(surname)) {
			errors.put("surname", "error.label.anchor.format");
		}
		
		Person personRecord = personService.find(phone);
		
		if(errors.isEmpty() && personRecord != null) {
			errors.put("phone", "error.label.anchor.already_exist_phone");
		}
		
		if(errors.isEmpty()) {
			Person person = new Person();
			person.setPhone(phone);
			person.setPassword(password);
			person.setName(name);
			person.setSurname(surname);
			person.setRoleId(1);
				
			personService.insert(person);
			LOG.debug("Person record created: " + person);
						
			result = new Page(Path.COMMAND__SIGN_IN_PAGE, true);	
		}else {
			
			LOG.error("errorMessage: Can't create account");
			
			request.setAttribute("errorPhone", errors.get("phone"));
			request.setAttribute("errorPassword", errors.get("password"));
			request.setAttribute("errorPasswordConfirm", errors.get("passwordConfirm"));
			request.setAttribute("errorName", errors.get("name"));
			request.setAttribute("errorSurname", errors.get("surname"));
			request.setAttribute("phone", phone);
			request.setAttribute("name", name);
			request.setAttribute("surname", surname);
			
			result = new Page(Path.PAGE__SIGN_UP);
		}
		
		return result;
	}
	
}
