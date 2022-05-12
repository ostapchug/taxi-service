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
import com.example.taxiservice.service.PersonService;
import com.example.taxiservice.web.Attribute;
import com.example.taxiservice.web.Error;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Parameter;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

/**
 * Profile update command.
 */
public class ProfileUpdateCommand extends Command {

	private static final long serialVersionUID = -7438992088781538965L;
	private static final Logger LOG = LoggerFactory.getLogger(ProfileUpdateCommand.class);

	@InjectByType
	private PersonService personService;

	public ProfileUpdateCommand() {
		LOG.info("ProfileUpdateCommand initialized");
	}

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command start");
		Page result = null;

		if ("GET".contentEquals(request.getMethod())) {
			result = new Page(Path.COMMAND__PROFILE_UPDATE, true);
		} else if ("POST".contentEquals(request.getMethod())) {
			result = doPost(request, response);
		}

		LOG.debug("Command finish");
		return result;

	}

	/**
	 * Updates person data. As first page displays a profile page.
	 *
	 * @return Page object which contain path to the view of profile page.
	 */
	private Page doPost(HttpServletRequest request, HttpServletResponse response) {
		Page result = null;
		String errorMessage = null;

		// obtain person data from the request
		String phone = request.getParameter(Parameter.PERSON__PHONE);
		String password = request.getParameter(Parameter.PERSON__PASSWORD);
		String passwordConfirm = request.getParameter(Parameter.PERSON__PASSWORD_CONFIRM);
		String name = request.getParameter(Parameter.PERSON__NAME);
		String surname = request.getParameter(Parameter.PERSON__SURNAME);

		// validate person data from the request
		if (!PersonService.validatePhone(phone)) {
			errorMessage = Error.PHONE__FORMAT;
		} else if (password != null && !password.isEmpty() && !PersonService.validatePassword(password)) {
			errorMessage = Error.PASSWORD__FORMAT;
		} else if (password != null && !password.isEmpty()
				&& !PersonService.validatePasswordConfirm(password, passwordConfirm)) {
			errorMessage = Error.PASSWORD__CONFIRM_WRONG;
		} else if (name != null && !name.isEmpty() && !PersonService.validateText(name)) {
			errorMessage = Error.NAME__FORMAT;
		} else if (surname != null && !surname.isEmpty() && !PersonService.validateText(surname)) {
			errorMessage = Error.SURNAME__FORMAT;
		}

		HttpSession session = request.getSession(false);
		long personId = (long) session.getAttribute(Attribute.PERSON__ID);

		Person personById = personService.find(personId);
		Person personByPhone = personService.find(phone);

		if (personByPhone != null && !personById.getPhone().equals(personByPhone.getPhone())) {
			errorMessage = Error.PHONE__EXIST;
		}

		if (errorMessage == null) {
			password = PersonService.hashPassword(password);
			Person person = new Person();
			person.setId(personId);
			person.setPhone(phone);
			person.setPassword(password);
			person.setName(name);
			person.setSurname(surname);
			person.setRoleId(1);

			boolean updated = personService.update(person);

			if (updated) {
				// update session attributes
				session.setAttribute(Attribute.PERSON__PHONE, phone);
				session.setAttribute(Attribute.PERSON__NAME, name);
				session.setAttribute(Attribute.PERSON__SURNAME, surname);
				result = new Page(Path.COMMAND__PROFILE_PAGE, true);
			} else {
				result = new Page(Path.COMMAND__PROFILE_UPDATE_PAGE + Parameter.ERROR__QUERY + Error.PROFILE__UPDATE,
						true);
			}

		} else {
			result = new Page(Path.COMMAND__PROFILE_UPDATE_PAGE + Parameter.ERROR__QUERY + errorMessage, true);
		}

		return result;
	}

}
