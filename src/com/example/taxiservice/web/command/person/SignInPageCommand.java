package com.example.taxiservice.web.command.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.web.Attribute;
import com.example.taxiservice.web.Error;
import com.example.taxiservice.web.ErrorMsg;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Parameter;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

/**
 * Sign in page command.
 */
public class SignInPageCommand extends Command {

	private static final long serialVersionUID = -4092142808306722870L;
	private static final Logger LOG = LoggerFactory.getLogger(SignInPageCommand.class);

	public SignInPageCommand() {
		LOG.info("SignInPageCommand initialized");
	}

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		LOG.debug("Command start");

		// obtain error message from the request
		String errorMessage = request.getParameter(Parameter.ERROR);

		// handle error message from the request, if not null set appropriate attribute
		if (errorMessage != null) {
			switch (errorMessage) {
			case Error.PHONE__NOT_FOUND:
				request.setAttribute(Attribute.ERROR__PHONE, ErrorMsg.PHONE__NOT_FOUND);
				break;
			case Error.PASSWORD__WRONG:
				request.setAttribute(Attribute.ERROR__PASSWORD, ErrorMsg.PASSWORD_WRONG);
				break;
			default:
				break;
			}
		}

		LOG.debug("Command finish");
		return new Page(Path.PAGE__SIGN_IN);
	}

}
