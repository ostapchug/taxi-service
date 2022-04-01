package com.example.taxiservice.web.command.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.DBManager;
import com.example.taxiservice.dao.mysql.MySqlPersonDao;
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
		String errorMessage = null;
		
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String passwordConfirm = request.getParameter("passwordConfirm");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		
		PersonService personService = new PersonService(new MySqlPersonDao(DBManager.getDataSource()));
		
		if(!personService.validatePhone(phone)) {
			errorMessage = "phone_format";
		}else if(!personService.validatePassword(password)) {
			errorMessage = "password";
		}else if(!personService.validatePassword(password, passwordConfirm)) {
			errorMessage = "password_confirm";
		}else if(!name.isEmpty() && !personService.validateText(name)) {
			errorMessage = "name";
		}else if(!surname.isEmpty() && !personService.validateText(surname)) {
			errorMessage = "surname";
		}
		
		Person personRecord = personService.find(phone);
		
		if(errorMessage == null && personRecord != null) {
			errorMessage ="phone_exist";
		}
		
		if(errorMessage == null) {
			personService.hashPassword(password);
			personService.hashPassword(passwordConfirm);
			Person person = new Person();
			person.setPhone(phone);
			person.setPassword(password);
			person.setName(name);
			person.setSurname(surname);
			person.setRoleId(1);
				
			boolean inserted = personService.insert(person);
			
			if(inserted) {
				result = new Page(Path.COMMAND__SIGN_IN_PAGE, true);
			}else {
				result = new Page(Path.COMMAND__SIGN_UP_PAGE + "&error=profile_create", true);
			}			
				
		}else {			
			result = new Page(Path.COMMAND__SIGN_UP_PAGE + "&error=" + errorMessage, true);
		}
		
		return result;
	}
	
}