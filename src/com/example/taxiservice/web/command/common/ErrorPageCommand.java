package com.example.taxiservice.web.command.common;

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
 * Error page command.
 */
public class ErrorPageCommand extends Command {

	private static final long serialVersionUID = -7664292755277429211L;
	private static final Logger LOG = LoggerFactory.getLogger(ErrorPageCommand.class);

	public ErrorPageCommand() {
		LOG.info("ErrorPageCommand initialized");
	}

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command start");
		String errorMessage = request.getParameter(Parameter.ERROR);

		if (errorMessage != null) {
			switch (errorMessage) {
			case Error.ACCESS_DENIED:
				request.setAttribute(Attribute.ERROR__MESSAGE, ErrorMsg.ACCESS_DENIED);
				break;
			case Error.NOT_FOUND:
				request.setAttribute(Attribute.ERROR__MESSAGE, ErrorMsg.NOT_FOUND);
				break;
			default:
				break;
			}

		}

		LOG.debug("Command finish");
		return new Page(Path.PAGE__ERROR);
	}

}
