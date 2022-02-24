package com.example.taxiservice.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.web.command.person.ProfileDeleteCommand;
import com.example.taxiservice.web.command.person.ProfilePageCommand;
import com.example.taxiservice.web.command.person.ProfileUpdateCommand;
import com.example.taxiservice.web.command.person.ProfileUpdatePageCommand;
import com.example.taxiservice.web.command.person.SignInCommand;
import com.example.taxiservice.web.command.person.SignInPageCommand;
import com.example.taxiservice.web.command.person.SignOutCommand;
import com.example.taxiservice.web.command.person.SignUpCommand;
import com.example.taxiservice.web.command.person.SignUpPageCommand;


/**
 * Holder for all commands 
*/

public class CommandContainer {
	
	private static final Logger LOG = LoggerFactory.getLogger(CommandContainer.class);
	
	private static Map<String, Command> commands = new TreeMap<String, Command>();
	
	static {
		// common commands
		commands.put("home_page", new HomePageCommand());
		commands.put("sign_in_page", new SignInPageCommand());
		commands.put("sign_up_page", new SignUpPageCommand());
		commands.put("sign_in", new SignInCommand());
		commands.put("sign_out", new SignOutCommand());
		commands.put("sign_up", new SignUpCommand());
		commands.put("no_command", new NoCommand());
		
		// client commands
		commands.put("profile_page", new ProfilePageCommand());
		commands.put("profile_update_page", new ProfileUpdatePageCommand());
		commands.put("profile_update", new ProfileUpdateCommand());
		commands.put("profile_delete", new ProfileDeleteCommand());
		commands.put("new_trip_page", new NewTripPageCommand());
		commands.put("new_trip", new NewTripCommand());
		commands.put("trips_page", new TripsPageCommand());
		commands.put("get_street_numbers", new GetStreetNumbersCommand());
		
		// admin commands
		commands.put("", null);
		
		LOG.debug("Command container was successfully initialized");
		LOG.debug("Number of commands --> " + commands.size());
	}
	
	/**
	 * Returns command object with the given name
	 * 
	 * @param commandName - Name of the command
	 * @return Command object
	 */
	public static Command get(String commandName) {
		if (commandName == null) {
			return commands.get("home_page");
		}else if(!commands.containsKey(commandName)) {
			LOG.debug("Command not found, name --> " + commandName);
			return commands.get("no_command"); 			
		}
		
		return commands.get(commandName);
	}



}
