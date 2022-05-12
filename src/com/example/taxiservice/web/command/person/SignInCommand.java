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
import com.example.taxiservice.web.Attribute;
import com.example.taxiservice.web.Error;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Parameter;
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
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		LOG.debug("Command start");
		Page result = null;

		if ("GET".contentEquals(request.getMethod())) {
			result = new Page(Path.COMMAND__SIGN_IN_PAGE, true);
		} else if ("POST".contentEquals(request.getMethod())) {
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
		String phone = request.getParameter(Parameter.PERSON__PHONE);
		String password = request.getParameter(Parameter.PERSON__PASSWORD);

		Person person = personService.find(phone);
		password = PersonService.hashPassword(password);

		// validate login and password from the request
		if (person == null) {
			errorMessage = Error.PHONE__NOT_FOUND;
		} else if (errorMessage == null && password != null && !password.equals(person.getPassword())) {
			errorMessage = Error.PASSWORD__WRONG;
		}

		if (errorMessage == null) {

			HttpSession session = request.getSession(true);

			session.setAttribute(Attribute.PERSON__ID, person.getId());
			session.setAttribute(Attribute.PERSON__PHONE, person.getPhone());
			session.setAttribute(Attribute.PERSON__NAME, person.getName());
			session.setAttribute(Attribute.PERSON__SURNAME, person.getSurname());

			Role personRole = Role.getRole(person);
			session.setAttribute(Attribute.PERSON__ROLE, personRole.getName());
			LOG.info("Person: " + person.getPhone() + " logged with role: " + personRole.getName());

			result = new Page(Path.COMMAND__HOME_PAGE, true);

		} else {
			result = new Page(Path.COMMAND__SIGN_IN_PAGE + Parameter.ERROR__QUERY + errorMessage, true);
		}

		return result;
	}

}
