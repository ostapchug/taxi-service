package com.example.taxiservice.web.command.trip;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.taxiservice.factory.annotation.InjectByType;
import com.example.taxiservice.model.dto.TripConfirmDto;
import com.example.taxiservice.service.CategoryService;
import com.example.taxiservice.service.LocationService;
import com.example.taxiservice.web.Attribute;
import com.example.taxiservice.web.Page;
import com.example.taxiservice.web.Path;
import com.example.taxiservice.web.command.Command;

/**
 * Trip confirm page command.
 */
public class TripConfirmPageCommand extends Command {
    private static final long serialVersionUID = -3427103898352236639L;
    private static final Logger LOG = LoggerFactory.getLogger(TripConfirmPageCommand.class);

    @InjectByType
    private CategoryService categoryService;

    @InjectByType
    private LocationService locationService;

    public TripConfirmPageCommand() {
        LOG.info("TripConfirmPageCommand initialized");
    }

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("Command start");
        Page result = null;
        HttpSession session = request.getSession(false);
        TripConfirmDto tripConfirmDto = (TripConfirmDto) session.getAttribute(Attribute.TRIP__CONFIRM);
        String lang = (String) session.getAttribute(Attribute.LOCALE);

        if (tripConfirmDto != null) {

            // load category and location by specified language
            String category = categoryService.find(tripConfirmDto.getCategoryId(), lang).getName();
            String origin = locationService.find(tripConfirmDto.getOriginId(), lang).toString();
            String destination = locationService.find(tripConfirmDto.getDestinationId(), lang).toString();
            request.setAttribute(Attribute.CAR__CATEGORY, category);
            request.setAttribute(Attribute.TRIP__ORIGIN, origin);
            request.setAttribute(Attribute.TRIP__DEST, destination);
            result = new Page(Path.PAGE__TRIP_CONFIRM);
        } else {
            result = new Page(Path.COMMAND__NEW_TRIP_PAGE, true);
        }
        LOG.debug("Command finish");
        return result;
    }
}
