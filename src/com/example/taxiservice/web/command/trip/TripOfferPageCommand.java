package com.example.taxiservice.web.command.trip;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.model.dto.TripConfirmDto;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

/**
 * Trip offer page command.
 */
public class TripOfferPageCommand extends Command {
	
	private static final long serialVersionUID = -3078249138900404841L;
	private static final Logger LOG = LoggerFactory.getLogger(TripOfferPageCommand.class);
	
	public TripOfferPageCommand() {
		LOG.info("TripOfferPageCommand initialized");
	}

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command start");
		Page result = null;
		
		HttpSession session = request.getSession(false);
		TripConfirmDto tripConfirmDto = (TripConfirmDto) session.getAttribute("tripOffer");
		
		if(tripConfirmDto != null) {
			result = new Page(Path.PAGE__TRIP_OFFER);
		}else {
			result = new Page(Path.COMMAND__NEW_TRIP_PAGE, true);
		}
		
		LOG.debug("Command finish");
		return result;
	}

}
