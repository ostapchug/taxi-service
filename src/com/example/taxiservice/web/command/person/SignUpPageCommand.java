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

public class SignUpPageCommand extends Command {
	
	private static final long serialVersionUID = 5964706657242219655L;
	
	private static final Logger LOG = LoggerFactory.getLogger(SignUpPageCommand.class);

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response)	throws IOException, ServletException {
		LOG.debug("Command start");
		String errorMessage = request.getParameter("error");
		
		if(errorMessage != null) {
			switch (errorMessage) {
				case "phone_format":
					request.setAttribute("errorPhone", "error.label.anchor.format");
					break;
				case "phone_exist":
					request.setAttribute("errorPhone", "error.label.anchor.already_exist_phone");
					break;
				case "password":
					request.setAttribute("errorPassword", "error.label.anchor.format");
					break;
				case "password_confirm":
					request.setAttribute("errorPasswordConfirm", "error.label.anchor.wrong_password_confirm");
					break;
				case "name":
					request.setAttribute("errorName", "error.label.anchor.format");
					break;
				case "surname":
					request.setAttribute("errorSurname", "error.label.anchor.format");
					break;
	
				default:
					break;
				}
		}
		LOG.debug("Command finish");
		return new Page(Path.PAGE__SIGN_UP);
	}

}
