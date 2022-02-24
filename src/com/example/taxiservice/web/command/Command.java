package com.example.taxiservice.web.command;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.taxiservice.web.Page;

/**
 * Main interface for the Command pattern implementation
*/
public abstract class Command implements Serializable {

	private static final long serialVersionUID = 4393881322523840683L;
	
	/**
	 * Execution method for command.
	 * @return Address to go once the command is executed.
	 */
	public abstract Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	
	@Override
	public final String toString() {
		return getClass().getSimpleName();
	}

}
