package com.example.taxiservice.web.command.trip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.dao.DBManager;
import com.example.taxiservice.dao.mysql.MySqlLocationDao;
import com.example.taxiservice.dao.mysql.MySqlPersonDao;
import com.example.taxiservice.dao.mysql.MySqlTripDao;
import com.example.taxiservice.model.Person;
import com.example.taxiservice.model.Role;
import com.example.taxiservice.model.Trip;
import com.example.taxiservice.model.TripStatus;
import com.example.taxiservice.model.dto.TripDto;
import com.example.taxiservice.service.LocationService;
import com.example.taxiservice.service.PersonService;
import com.example.taxiservice.service.TripService;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

public class TripsPageCommand extends Command {

	private static final long serialVersionUID = -4416385983983844193L;
	private static final Logger LOG = LoggerFactory.getLogger(TripsPageCommand.class);
	private static final int COUNT = 5;

	@Override
	public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command start");
		
		List<Trip> trips = null;
		List<TripDto> tripDtoList = null;
		HttpSession session = request.getSession(false);
		setErrorMessage(request);
			
		String sort = request.getParameter("sort");
		String page = request.getParameter("page");
		setSorting(session, sort);
		
		String lang = (String) session.getAttribute("locale");
		String role = (String) session.getAttribute("personRole");
		String phone = (String) session.getAttribute("phone");
		long personId = (long) session.getAttribute("personId");
		
		String sorting = (String) session.getAttribute("sorting");
		String dateRange = (String) session.getAttribute("dateRange");
		PersonService personService = new PersonService(new MySqlPersonDao(DBManager.getDataSource()));
				
		if(Role.ADMIN.getName().equals(role)) {
			if(phone != null) {
				Person person = personService.find(phone);
				Long id = person != null ? person.getId() : -1;
				trips = getTripsByPerson(session, id, dateRange, page, sorting);
			}else {
				trips = getTrips(session, dateRange, page, sorting);
			}
			
		}else {
			trips = getTripsByPerson(session, personId, dateRange, page, sorting);
		}
		
		tripDtoList = getTripDtoList(personService, trips, lang);
		request.setAttribute("tripList", tripDtoList);
		
		LOG.debug("Command finish");
		return new Page(Path.PAGE__TRIPS);
	}
	
	private List<Trip> getTrips(HttpSession session, String dateRange, String page, String sorting){
		List<Trip> trips = null;
		TripService tripService = new TripService(new MySqlTripDao(DBManager.getDataSource()));
		
		int pageCount = (dateRange == null) ? tripService.getPages(COUNT) : tripService.getPages(dateRange, COUNT);
		session.setAttribute("totalPages", pageCount);
		setPages(session, page);

		int currentPage = (int) session.getAttribute("currentPage");
		
		if(currentPage > pageCount) {
			currentPage = pageCount;
			setPages(session, String.valueOf(currentPage));
		}

		int offset = (currentPage-1)*COUNT;
		
		if(dateRange != null) {
			trips = tripService.findAllByDate(dateRange, offset, COUNT, sorting);
		}else {
			trips = tripService.findAll(offset, COUNT, sorting);
		}
		
		return trips;
	}
	
	private List<Trip> getTripsByPerson(HttpSession session, Long personId, String dateRange, String page, String sorting){
		List<Trip> trips = null;
		TripService tripService = new TripService(new MySqlTripDao(DBManager.getDataSource()));
		
		int pageCount = (dateRange == null) ? tripService.getPages(personId, COUNT) : tripService.getPages(personId, dateRange, COUNT);
		session.setAttribute("totalPages", pageCount);
		setPages(session, page);
		

		int currentPage = (int) session.getAttribute("currentPage");
		
		if(currentPage > pageCount) {
			currentPage = pageCount;
			setPages(session, String.valueOf(currentPage));
		}
		
		int offset = (currentPage-1)*COUNT;
		
		if(dateRange != null) {
			trips = tripService.findAllByPersonIdAndDate(personId, dateRange, offset, COUNT, sorting);
		}else {
			trips = tripService.findAllByPersonId(personId, offset, COUNT, sorting);
		}
		
		return trips;
	}
	
	private List<TripDto> getTripDtoList(PersonService personService, List<Trip> trips, String lang){
		List<TripDto> result = new ArrayList<>();
				
		LocationService locationService = new LocationService(new MySqlLocationDao(DBManager.getDataSource()));
		
		for (Trip trip : trips) {
			TripDto tripDto = new TripDto();
			String origin = locationService.find(trip.getOriginId(), lang).toString();
			String destination = locationService.find(trip.getDestinationId(), lang).toString();
			String phone = personService.find(trip.getPersonId()).getPhone();
			TripStatus tripStatus = TripStatus.getTripStatus(trip); 
			
			tripDto.setId(trip.getId());
			tripDto.setPersonPhone(phone);
			tripDto.setOrigin(origin);
			tripDto.setDestination(destination);
			tripDto.setDistance(trip.getDistance());
			tripDto.setDate(trip.getDate());
			tripDto.setBill(trip.getBill());
			tripDto.setStatus(tripStatus.getName());
			result.add(tripDto);
		}
		
		return result;
	}
	
	private void setSorting(HttpSession session, String sorting) {
		String defaultSorting = "date_desc";
		
		if(sorting != null) {
			switch(sorting) {
				case "date_asc":
					session.setAttribute("sorting", "date_asc");
					break;
				case "date_desc":
					session.setAttribute("sorting", "date_desc");
					break;
				case "bill_asc":
					session.setAttribute("sorting", "bill_asc");
					break;
				case "bill_desc":
					session.setAttribute("sorting", "bill_desc");
					break;
				default:
					session.setAttribute("sorting", defaultSorting);
					break;
			}
		}
		
		Object sort = session.getAttribute("sorting");
		
		if(sort == null) {
			session.setAttribute("sorting", defaultSorting);		
		}
		
	}
	
	private void setPages(HttpSession session, String page) {
		Object currentPage = session.getAttribute("currentPage");
		int pageCount = (int) session.getAttribute("totalPages");
		
		if(currentPage == null) {
			session.setAttribute("currentPage", 1);		
		}else {
			if(page != null) {
				int pageIntValue = 0;
				try {
					pageIntValue = Integer.parseInt(page);
					
				}catch(NumberFormatException e) {
					LOG.debug("page parameter is not a number");
				}
				
				if(pageIntValue > 0 && pageIntValue <= pageCount) {
					session.setAttribute("currentPage", pageIntValue);
				}
			}
		}
	}
	
	private void setErrorMessage(HttpServletRequest request) {
		
		String errorMessage = request.getParameter("error");
		
		if(errorMessage != null) {
			switch (errorMessage) {
				case "filter_format":
					request.setAttribute("errorMessage", "error.label.anchor.format");			
					break;
				case "trip_status":
					request.setAttribute("errorMessage", "error.label.anchor.trip_update_status");			
					break;
				default:
					break;
			}
		}
		
	}

}
