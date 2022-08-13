package com.example.taxiservice.web.command.trip;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.factory.annotation.InjectByType;
import com.example.taxiservice.model.Car;
import com.example.taxiservice.model.CarModel;
import com.example.taxiservice.model.Role;
import com.example.taxiservice.model.Trip;
import com.example.taxiservice.model.TripStatus;
import com.example.taxiservice.model.dto.TripDto;
import com.example.taxiservice.service.CarModelService;
import com.example.taxiservice.service.CarService;
import com.example.taxiservice.service.LocationService;
import com.example.taxiservice.service.PersonService;
import com.example.taxiservice.service.TripService;
import com.example.taxiservice.web.Attribute;
import com.example.taxiservice.web.Error;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Parameter;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

/**
 * Trip page command.
 */
public class TripPageCommand extends Command {
    private static final long serialVersionUID = -4793599624749188289L;
    private static final Logger LOG = LoggerFactory.getLogger(TripPageCommand.class);

    @InjectByType
    private CarService carService;

    @InjectByType
    private CarModelService carModelService;

    @InjectByType
    private LocationService locationService;

    @InjectByType
    private PersonService personService;

    @InjectByType
    private TripService tripService;

    public TripPageCommand() {
        LOG.info("TripPageCommand initialized");
    }

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("Command start");
        Page result = null;
        HttpSession session = request.getSession(false);

        // obtain required data from session
        String role = (String) session.getAttribute(Attribute.PERSON__ROLE);
        String lang = (String) session.getAttribute(Attribute.LOCALE);
        long personId = (long) session.getAttribute(Attribute.PERSON__ID);
        long tripId;
        Trip trip = null;
        String errorMessage = null;

        // obtain trip id from the request
        String id = request.getParameter(Parameter.TRIP_ID);

        // validate trip id
        try {
            tripId = Long.parseLong(id);
            trip = tripService.find(tripId);
        } catch (NumberFormatException e) {
            errorMessage = Error.TRIP__NOT_FOUND;
        }

        // if no errors found proceed
        if (errorMessage == null && trip != null) {
            Map<Car, CarModel> cars = new HashMap<>();
            List<Car> carList = carService.findCarsByTripId(trip.getId());
            carList.stream().forEach(c -> cars.put(c, carModelService.find(c.getModelId())));

            // if role equals admin then load trip
            if (Role.ADMIN.getName().equals(role)) {
                TripDto tripDto = getTripDto(trip, cars, lang);
                request.setAttribute(Attribute.TRIP, tripDto);
                result = new Page(Path.PAGE__TRIP);
            } else {
                // load trip if trip belongs to person
                if (trip.getPersonId().equals(personId)) {
                    TripDto tripDto = getTripDto(trip, cars, lang);
                    request.setAttribute(Attribute.TRIP, tripDto);
                    result = new Page(Path.PAGE__TRIP);
                } else {
                    result = new Page(Path.COMMAND__ERROR_PAGE + Parameter.ERROR__QUERY + Error.ACCESS_DENIED, true);
                }
            }
        } else {
            result = new Page(Path.COMMAND__ERROR_PAGE + Parameter.ERROR__QUERY + Error.NOT_FOUND, true);
        }
        LOG.debug("Command finish");
        return result;
    }

    private TripDto getTripDto(Trip trip, Map<Car, CarModel> cars, String lang) {
        TripDto result = new TripDto();
        String origin = locationService.find(trip.getOriginId(), lang).toString();
        String destination = locationService.find(trip.getDestinationId(), lang).toString();
        String phone = personService.find(trip.getPersonId()).getPhone();
        TripStatus tripStatus = TripStatus.getTripStatus(trip);
        result.setId(trip.getId());
        result.setPersonPhone(phone);
        result.setOrigin(origin);
        result.setDestination(destination);
        result.setDistance(trip.getDistance());
        result.setDate(trip.getDate());
        result.setBill(trip.getBill());
        result.setStatus(tripStatus.getName());
        result.setCars(cars);
        return result;
    }
}
