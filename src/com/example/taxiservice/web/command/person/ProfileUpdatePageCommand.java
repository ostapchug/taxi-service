package com.example.taxiservice.web.command.person;

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
 * Profile update page command.
 */
public class ProfileUpdatePageCommand extends Command {
    private static final long serialVersionUID = 4207767322581464759L;
    private static final Logger LOG = LoggerFactory.getLogger(ProfileUpdatePageCommand.class);

    public ProfileUpdatePageCommand() {
        LOG.info("ProfileUpdatePageCommand initialized");
    }

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("Command start");

        // obtain error message from the request
        String errorMessage = request.getParameter(Parameter.ERROR);

        // handle error message from the request, if not null set appropriate attribute
        if (errorMessage != null) {
            switch (errorMessage) {
            case Error.PHONE__FORMAT:
                request.setAttribute(Attribute.ERROR__PHONE, ErrorMsg.FORMAT);
                break;
            case Error.PHONE__EXIST:
                request.setAttribute(Attribute.ERROR__PHONE, ErrorMsg.PHONE__EXIST);
                break;
            case Error.PASSWORD__FORMAT:
                request.setAttribute(Attribute.ERROR__PASSWORD, ErrorMsg.FORMAT);
                break;
            case Error.PASSWORD__CONFIRM_WRONG:
                request.setAttribute(Attribute.ERROR__PASSWORD_CONFIRM, ErrorMsg.PASSWORD__CONFIRM_WRONG);
                break;
            case Error.NAME__FORMAT:
                request.setAttribute(Attribute.ERROR__NAME, ErrorMsg.FORMAT);
                break;
            case Error.SURNAME__FORMAT:
                request.setAttribute(Attribute.ERROR__SURNAME, ErrorMsg.FORMAT);
                break;
            case Error.PROFILE__UPDATE:
                request.setAttribute(Attribute.ERROR__MESSAGE, ErrorMsg.PROFILE__UPDATE);
                break;
            default:
                break;
            }
        }
        LOG.debug("Command finish");
        return new Page(Path.PAGE__PROFILE_UPDATE);
    }
}
