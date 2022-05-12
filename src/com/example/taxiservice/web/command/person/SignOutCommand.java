package com.example.taxiservice.web.command.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.web.Attribute;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

/**
 * Sign out command.
 */
public class SignOutCommand extends Command {

	private static final long serialVersionUID = 4523142544313588535L;
	private static final Logger LOG = LoggerFactory.getLogger(SignOutCommand.class);

	public SignOutCommand() {
		LOG.info("SignOutCommand initialized");
	}

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command start");

		HttpSession session = request.getSession(false);

		session.removeAttribute(Attribute.PERSON__ID);
		session.removeAttribute(Attribute.PERSON__PHONE);
		session.removeAttribute(Attribute.PERSON__NAME);
		session.removeAttribute(Attribute.PERSON__SURNAME);
		session.removeAttribute(Attribute.PERSON__ROLE);

		LOG.debug("Command finish");

		return new Page(Path.COMMAND__HOME_PAGE, true);
	}

}
