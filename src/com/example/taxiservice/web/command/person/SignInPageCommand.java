package com.example.taxiservice.web.command.person;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.web.Page;
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
	public Page execute(HttpServletRequest request, HttpServletResponse response)	throws IOException, ServletException {
		
		LOG.debug("Command start");
		
		// obtain error message from the request
		String errorMessage = request.getParameter("error");
		
		// handle error message from the request, if not null set appropriate attribute
		if(errorMessage != null) {
			switch (errorMessage) {
				case "phone":
					request.setAttribute("errorPhone", "error.label.anchor.wrong_phone");
					break;
				case "password":
					request.setAttribute("errorPassword", "error.label.anchor.wrong_password");
					break;
				default:
					break;
				}
		}
		
		LOG.debug("Command finish");
		return new Page(Path.PAGE__SIGN_IN);
	}

}
