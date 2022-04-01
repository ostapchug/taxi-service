package com.example.taxiservice.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.web.command.common.ErrorPageCommand;
import com.example.taxiservice.web.command.common.HomePageCommand;
import com.example.taxiservice.web.command.person.ProfilePageCommand;
import com.example.taxiservice.web.command.person.ProfileUpdateCommand;
import com.example.taxiservice.web.command.person.ProfileUpdatePageCommand;
import com.example.taxiservice.web.command.person.SignInCommand;
import com.example.taxiservice.web.command.person.SignInPageCommand;
import com.example.taxiservice.web.command.person.SignOutCommand;
import com.example.taxiservice.web.command.person.SignUpCommand;
import com.example.taxiservice.web.command.person.SignUpPageCommand;
import com.example.taxiservice.web.command.trip.FilterTripsCommand;
import com.example.taxiservice.web.command.trip.NewTripCommand;
import com.example.taxiservice.web.command.trip.NewTripPageCommand;
import com.example.taxiservice.web.command.trip.TripConfirmCommand;
import com.example.taxiservice.web.command.trip.TripConfirmPageCommand;
import com.example.taxiservice.web.command.trip.TripOfferCommand;
import com.example.taxiservice.web.command.trip.TripOfferPageCommand;
import com.example.taxiservice.web.command.trip.TripPageCommand;
import com.example.taxiservice.web.command.trip.TripStatusCommand;
import com.example.taxiservice.web.command.trip.TripsPageCommand;


/**
 * Holder for all commands 
*/

public class CommandContainer {
	
	private static final Logger LOG = LoggerFactory.getLogger(CommandContainer.class);
	
	private static Map<String, Command> commands = new TreeMap<String, Command>();
	
	static {
		// common commands
		commands.put("home_page", new HomePageCommand());
		commands.put("error_page", new ErrorPageCommand());
		commands.put("sign_in_page", new SignInPageCommand());
		commands.put("sign_up_page", new SignUpPageCommand());
		commands.put("sign_in", new SignInCommand());
		commands.put("sign_out", new SignOutCommand());
		commands.put("sign_up", new SignUpCommand());
		
		// client commands
		commands.put("profile_page", new ProfilePageCommand());
		commands.put("profile_update_page", new ProfileUpdatePageCommand());
		commands.put("profile_update", new ProfileUpdateCommand());
		commands.put("new_trip_page", new NewTripPageCommand());
		commands.put("trip_page", new TripPageCommand());
		commands.put("new_trip", new NewTripCommand());
		commands.put("trips_page", new TripsPageCommand());
		commands.put("trip_confirm_page", new TripConfirmPageCommand());
		commands.put("trip_offer_page", new TripOfferPageCommand());
		commands.put("trip_offer", new TripOfferCommand());
		commands.put("trip_confirm", new TripConfirmCommand());
		commands.put("filter_trips", new FilterTripsCommand());
		commands.put("trip_status", new TripStatusCommand());
		
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
			return commands.get("home_page");		
		}
		
		return commands.get(commandName);
	}



}
