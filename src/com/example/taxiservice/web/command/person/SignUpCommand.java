package com.example.taxiservice.web.command.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.factory.annotation.InjectByType;
import com.example.taxiservice.model.Person;
import com.example.taxiservice.service.PersonService;
import com.example.taxiservice.web.Error;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Parameter;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

/**
 * Sign up command.
 */
public class SignUpCommand extends Command {
    private static final long serialVersionUID = -3445237114266973954L;
    private static final Logger LOG = LoggerFactory.getLogger(SignUpCommand.class);

    @InjectByType
    private PersonService personService;

    public SignUpCommand() {
        LOG.info("SignUpCommand initialized");
    }

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("Command start");
        Page result = null;

        if ("GET".contentEquals(request.getMethod())) {
            result = new Page(Path.COMMAND__SIGN_UP_PAGE, true);
        } else if ("POST".contentEquals(request.getMethod())) {
            result = doPost(request, response);
        }
        LOG.debug("Command finish");
        return result;
    }

    /**
     * Register person in system. As first page displays a sign in page.
     *
     * @return Page object which contain path to the view of sign in page.
     */
    private Page doPost(HttpServletRequest request, HttpServletResponse response) {
        Page result = null;
        String errorMessage = null;

        // obtain person registration data from the request
        String phone = request.getParameter(Parameter.PERSON__PHONE);
        String password = request.getParameter(Parameter.PERSON__PASSWORD);
        String passwordConfirm = request.getParameter(Parameter.PERSON__PASSWORD_CONFIRM);
        String name = request.getParameter(Parameter.PERSON__NAME);
        String surname = request.getParameter(Parameter.PERSON__SURNAME);

        // validate person registration data from the request
        if (!PersonService.validatePhone(phone)) {
            errorMessage = Error.PHONE__FORMAT;
        } else if (!PersonService.validatePassword(password)) {
            errorMessage = Error.PASSWORD__FORMAT;
        } else if (!PersonService.validatePasswordConfirm(password, passwordConfirm)) {
            errorMessage = Error.PASSWORD__CONFIRM_WRONG;
        } else if (name != null && !name.isEmpty() && !PersonService.validateText(name)) {
            errorMessage = Error.NAME__FORMAT;
        } else if (surname != null && !surname.isEmpty() && !PersonService.validateText(surname)) {
            errorMessage = Error.SURNAME__FORMAT;
        }

        Person personRecord = personService.find(phone);

        if (errorMessage == null && personRecord != null) {
            errorMessage = Error.PHONE__EXIST;
        }

        if (errorMessage == null) {
            password = PersonService.hashPassword(password);
            Person person = new Person();
            person.setPhone(phone);
            person.setPassword(password);
            person.setName(name);
            person.setSurname(surname);
            person.setRoleId(1);

            boolean inserted = personService.insert(person);

            if (inserted) {
                result = new Page(Path.COMMAND__SIGN_IN_PAGE, true);
            } else {
                result = new Page(Path.COMMAND__SIGN_UP_PAGE + Parameter.ERROR__QUERY + Error.PROFILE__CREATE, true);
            }
        } else {
            result = new Page(Path.COMMAND__SIGN_UP_PAGE + Parameter.ERROR__QUERY + errorMessage, true);
        }
        return result;
    }
}
